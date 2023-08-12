package com.jt.project.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.jt.project.entity.Account;
import com.jt.project.entity.Loan;
import com.jt.project.entity.Transection;
import com.jt.project.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;

@Repository
public class UserDao {
	private EntityManager entityManager;
	private HttpSession sess;
	
	@Autowired
	public UserDao(EntityManager entityManager, HttpSession sess) {
		super();
		this.entityManager = entityManager;
		this.sess = sess;
	}

	public HttpSession getSess() {
		return sess;
	}

	public void setSess(HttpSession sess) {
		this.sess = sess;
	}

	@Transactional
	public void adduser(User record, Account ac) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.persist(ac);
		currentSession.persist(record);
		currentSession.close();
	}
	
	public ModelAndView check(String email, String pass) {
		Session session = entityManager.unwrap(Session.class);
		ModelAndView mv = new ModelAndView("home");
		Query<User> query = session.createQuery("from User where email = '" + email + "'", User.class);
		try {
			User obj = query.getSingleResult();
			if(obj.getPassword().equals(pass)) {
//				mv.setViewName("home");
				mv.addObject("message", "Login Successful");
				int id = obj.getUser_id();
//				Query<Loan> query1 = session.createQuery("from Loan where user_id = '" + id + "'", Loan.class);
				List <Loan> it =(List<Loan>) session.createQuery("from Loan").list();
				int flag = 1;
				for(Loan l : it) {
					if(l.getUser().getUser_id() == id) {
						mv.addObject("rslt", 1);
						flag = 0;
					}
				}
				if(flag == 1) {
					mv.addObject("rslt", 0);
				}
				
				mv.addObject("username", obj.getName());
				mv.addObject("balance", obj.getAccount_no().getBalance());
				sess.setAttribute("user_id", obj.getUser_id());
			}
			else {
				mv.setViewName("login");
				mv.addObject("message", "Incorrect Password");
			}
		} 
		catch (jakarta.persistence.NoResultException e) {
			mv.setViewName("login");
			mv.addObject("message", "Invalid Email ID!! Please try again.");
		}
		session.close();
		return mv;
	}
	
	public void logout() {
		sess.invalidate();
	}
	
	public ModelAndView delete(String email) {
		Session session = entityManager.unwrap(Session.class);
		ModelAndView mv = new ModelAndView("login");
		Query<User> query = session.createQuery("from User where email = '" + email + "'", User.class);
		User obj2;
		try {
			User obj = query.getSingleResult();
			obj2 = obj;
		}
		catch(jakarta.persistence.NoResultException e){
			mv.setViewName("viewprofile");
			mv.addObject("message", "Invalid Email-Id!!");
			return mv;
		}
		
		try {
			Query<Loan> query1 = session.createQuery("from Loan where user = '" + obj2.getUser_id() + "'", Loan.class);
			Loan ln = query1.getSingleResult();
			mv.setViewName("viewprofile");
			mv.addObject("message", "Pay loan first");
		} 
		catch (jakarta.persistence.NoResultException e) {
			Transaction tx = session.beginTransaction();
			session.remove(obj2);
			tx.commit();
		}
		session.close();
		return mv;
	}
	
	@Transactional
	public ModelAndView read() {
		int id = (int) sess.getAttribute("user_id");
		Session session = entityManager.unwrap(Session.class);
		ModelAndView mv = new ModelAndView("showdetails");
		Query<User> query = session.createQuery("from User where user_id = '" + id + "'", User.class);
		User obj = query.getSingleResult();
		mv.addObject("userobj", obj);
		session.close();
		return mv;
	}
	@Transactional 
	public ModelAndView deposit(int amount) {
		Session session = entityManager.unwrap(Session.class);
		ModelAndView mv = new ModelAndView("home");
		int id = (int) sess.getAttribute("user_id");
		Query<User> query = session.createQuery("from User where user_id = '" + id + "'", User.class);
		User obj = query.getSingleResult();
		Query<Account>query1 = session.createQuery("from Account where account_no = '" + obj.getAccount_no().getAccount_no() + "'", Account.class);
		Account obj2 = query1.getSingleResult();
		int total = obj2.getBalance() + amount;
		MutationQuery query2 = session.createMutationQuery("update Account set balance=:n where account_no=:i");
		query2.setParameter("n", total);
		query2.setParameter("i", obj.getAccount_no().getAccount_no());
		query2.executeUpdate();
		Transection t = new Transection();
		t.setAmount(amount);
		t.setTransection_type("Deposit");
		List<Transection> temp =obj.getT();
		temp.add(t);
		obj.setT(temp);
		session.persist(obj);
		session.persist(t);
		List <Loan> it =(List<Loan>) session.createQuery("from Loan").list();
		int flag = 1;
		for(Loan l : it) {
			if(l.getUser().getUser_id() == id) {
				mv.addObject("rslt", 1);
				flag = 0;
			}
		}
		if(flag == 1) {
			mv.addObject("rslt", 0);
		}
		mv.addObject("username", obj.getName());
		mv.addObject("balance", total);
		session.close();
		return mv;
		
	}
	@Transactional 
	public ModelAndView withdraw(int amount) {
		Session session = entityManager.unwrap(Session.class);
		ModelAndView mv = new ModelAndView("home");
		int id = (int) sess.getAttribute("user_id");
		Query<User> query = session.createQuery("from User where user_id = '" + id + "'", User.class);
		User obj = query.getSingleResult();
		Query<Account>query1 = session.createQuery("from Account where account_no = '" + obj.getAccount_no().getAccount_no() + "'", Account.class);
		Account obj2 = query1.getSingleResult();
		int total = obj2.getBalance() - amount;
		String result;
		if(total >= 0) {
			MutationQuery query2 = session.createMutationQuery("update Account set balance=:n where account_no=:i");
			query2.setParameter("n", total);
			query2.setParameter("i", obj.getAccount_no().getAccount_no());
			query2.executeUpdate();
			Transection t = new Transection();
			t.setAmount(amount);
			t.setTransection_type("Withdraw");
			List<Transection> temp =obj.getT();
			temp.add(t);
			obj.setT(temp);
			session.persist(obj);
			session.persist(t);
			List <Loan> it =(List<Loan>) session.createQuery("from Loan").list();
			int flag = 1;
			for(Loan l : it) {
				if(l.getUser().getUser_id() == id) {
					mv.addObject("rslt", 1);
					flag = 0;
				}
			}
			if(flag == 1) {
				mv.addObject("rslt", 0);
			}
			result = "withdraw successfully";
		}
		else {
			List <Loan> it =(List<Loan>) session.createQuery("from Loan").list();
			int flag = 1;
			for(Loan l : it) {
				if(l.getUser().getUser_id() == id) {
					mv.addObject("rslt", 1);
					flag = 0;
				}
			}
			if(flag == 1) {
				mv.addObject("rslt", 0);
			}
			result = "Not sufficient balance!!";
			total = obj2.getBalance();
		}
		mv.addObject("result", result);
		mv.addObject("username", obj.getName());
		mv.addObject("balance", total);
		session.close();
		return mv;
		
	}
	@Transactional
	public void updatename(String email, String name){
		Session session = entityManager.unwrap(Session.class);
		MutationQuery query = session.createMutationQuery("update User set name=:n where email=:i");
		query.setParameter("n", name);
		query.setParameter("i", email);
		query.executeUpdate();
		session.close();
	}
	
	@Transactional
	public void updateemail(String email, String nemail){
		Session session = entityManager.unwrap(Session.class);
		Query<User> query = session.createQuery("from User where email = '" + email + "'", User.class);
		try {
			User obj = query.getSingleResult();
			obj.setEmail(nemail);
			session.merge(obj);
		} 
		catch (jakarta.persistence.NoResultException e) {
			System.out.println(e);
		}
		session.close();
	}
	
	@Transactional
	public void updatemobile(String email, Long mobile){
		Session session = entityManager.unwrap(Session.class);
		MutationQuery query = session.createMutationQuery("update User set mobile_no=:n where email=:i");
		query.setParameter("n", mobile);
		query.setParameter("i", email);
		query.executeUpdate();
		session.close();
	}
	
	@Transactional
	public void updatepassword(String email, String password){
		Session session = entityManager.unwrap(Session.class);
		MutationQuery query = session.createMutationQuery("update User set password=:n where email=:i");
		query.setParameter("n", password);
		query.setParameter("i", email);
		query.executeUpdate();
		session.close();
	}
	@Transactional
	public ModelAndView loan(Loan ln, int amount) {
		Session session = entityManager.unwrap(Session.class);
		int id = (int) sess.getAttribute("user_id");
		Query<User> query = session.createQuery("from User where user_id = '" + id + "'", User.class);
		User obj = query.getSingleResult();
		ln.setUser(obj);
		Query<Account>query1 = session.createQuery("from Account where account_no = '" + obj.getAccount_no().getAccount_no() + "'", Account.class);
		Account obj2 = query1.getSingleResult();
		int total = (obj2.getBalance() + amount); 
		obj2.setBalance(total);
		session.persist(ln);
		session.persist(obj2);
		session.close();
		ModelAndView mv = new ModelAndView("loandetails");
		mv.addObject("loantype", ln.getLoan_type());
		mv.addObject("amount", ln.getAmount());
		mv.addObject("duration", ln.getDuration());
		mv.addObject("installment", ln.getInstallment());
		session.close();
		
		return mv;
	}
	@Transactional
	public ModelAndView hm() {
		Session session = entityManager.unwrap(Session.class);
		int id = (int) sess.getAttribute("user_id");
		
		Query<User> query1 = session.createQuery("from User where user_id = '" + id + "'", User.class);
		User obj1 = query1.getSingleResult();
		ModelAndView mv = new ModelAndView("home");
		List <Loan> it =(List<Loan>) session.createQuery("from Loan").list();
		int flag = 1;
		for(Loan l : it) {
			if(l.getUser().getUser_id() == id) {
				mv.addObject("rslt", 1);
				flag = 0;
			}
		}
		if(flag == 1) {
			mv.addObject("rslt", 0);
		}
		mv.addObject("username", obj1.getName());
		mv.addObject("balance", obj1.getAccount_no().getBalance());
		session.close();
		return mv;
	}
	@Transactional
	public ModelAndView lndetails() {
		Session session = entityManager.unwrap(Session.class);
		int id = (int) sess.getAttribute("user_id");
//		Query<Loan> query = session.createQuery("from Loan where user_id = '" + id + "'", Loan.class);
		ModelAndView mv = new ModelAndView("loandetails");
		List <Loan> it =(List<Loan>) session.createQuery("from Loan").list();
		Loan ln=it.get(0);
		for(Loan l : it) {
			if(l.getUser().getUser_id() == id) {
				ln = l;
			}
		}
		mv.addObject("loantype", ln.getLoan_type());
		mv.addObject("amount", ln.getAmount());
		mv.addObject("duration", ln.getDuration());
		mv.addObject("installment", ln.getInstallment());
		session.close();
		return mv;
	}
	
	public ModelAndView installment() {
		Session session = entityManager.unwrap(Session.class);
		ModelAndView mv; 
		
		int id = (int) sess.getAttribute("user_id");
		Query<User> query = session.createQuery("from User where user_id = '" + id + "'", User.class);
		User obj = query.getSingleResult();
		Query<Account>query1 = session.createQuery("from Account where account_no = '" + obj.getAccount_no().getAccount_no() + "'", Account.class);
		Account obj2 = query1.getSingleResult();
		
		List <Loan> it =(List<Loan>) session.createQuery("from Loan").list();
		Loan ln=it.get(0);
		for(Loan l : it) {
			if(l.getUser().getUser_id() == id) {
				ln = l;
			}
		}
		
		int  total = (int) (obj2.getBalance() - ln.getInstallment());
		double total1 = ln.getAmount() - ln.getInstallment();
		int duration = ln.getDuration() - 1;
		if(duration == 0) {
			Transaction tx = session.beginTransaction();
			session.remove(ln);
			tx.commit();
			session.close();
			mv = new ModelAndView("redirect:home");
			return mv;
		}
		mv = new ModelAndView("loandetails");
		String result;
		if(total >= 0) {
			Transaction tx = session.beginTransaction();
			ln.setAmount(total1);
			ln.setDuration(duration);
			session.merge(ln);
			
			obj2.setBalance(total);
			session.merge(obj2);
			
			result = "Installment successfully done";
			Transection t = new Transection();
			t.setAmount((int)ln.getInstallment());
			t.setTransection_type("Loan Installment");
			List<Transection> temp =obj.getT();
			temp.add(t);
			obj.setT(temp);
			session.persist(obj);
			session.persist(t);
			tx.commit();
			mv.addObject("amount", total1);
			mv.addObject("duration", duration);
		}
		else {
			result = "Installment not successfully done";
			total = obj2.getBalance();
			mv.addObject("amount", ln.getAmount());
			mv.addObject("duration", ln.getDuration());
		}
		mv.addObject("result", result);
		mv.addObject("loantype", ln.getLoan_type());
		mv.addObject("installment", ln.getInstallment());
		session.close();
		return mv;
	}

	public ModelAndView transection() {
		Session session = entityManager.unwrap(Session.class);
		ModelAndView mv = new ModelAndView("transection"); 
		
		int id = (int) sess.getAttribute("user_id");
		Query<User> query = session.createQuery("from User where user_id = '" + id + "'", User.class);
		User obj = query.getSingleResult();
		List<Transection> tra = obj.getT();
		mv.addObject("list", tra);
		return mv;
	}

	public ModelAndView tr(int acnum, int amount) {
		Session session = entityManager.unwrap(Session.class);
		ModelAndView mv; 
		
		int id = (int) sess.getAttribute("user_id");
		Query<User> query = session.createQuery("from User where user_id = '" + id + "'", User.class);
		User obj = query.getSingleResult();
		Query<Account>query1 = session.createQuery("from Account where account_no = '" + obj.getAccount_no().getAccount_no() + "'", Account.class);
		Account obj2 = query1.getSingleResult();
		Query<Account> query3 = session.createQuery("from Account where account_no = '" + acnum + "'", Account.class);
//		mv.addObject("msg", "Not efficient Balance!!!");
		try {
			mv = new ModelAndView("redirect:home");
			Account obj3 = query3.getSingleResult();
			int  total = obj2.getBalance() - amount;
			if(total>=0) {
				Transaction tx = session.beginTransaction();
				obj2.setBalance(total);
				session.merge(obj2);
				
				obj3.setBalance(obj3.getBalance() + amount);
				session.merge(obj2);
				
				Transection t = new Transection();
				t.setAmount(amount);
				t.setTransection_type("Withdraw Bank Transfer");
				List<Transection> temp =obj.getT();
				temp.add(t);
				obj.setT(temp);
				session.persist(obj);
				session.persist(t);
				Query<User> query4 = session.createQuery("from User where account_no = '" + acnum + "'", User.class);
				User obj4 = query4.getSingleResult();
				Transection t1 = new Transection();
				t1.setAmount(amount);
				t1.setTransection_type("Deposite Bank Transfer");
				List<Transection> temp1 =obj4.getT();
				temp1.add(t1);
				obj4.setT(temp1);
				session.persist(obj4);
				session.persist(t1);
				tx.commit();
			}
			else {
				mv = new ModelAndView("transferform");
				mv.addObject("msg", "Not efficient Balance!!!");
			}
		}		
		catch(jakarta.persistence.NoResultException e){
			
			mv = new ModelAndView("transferform");
			mv.addObject("msg", "Invalid Account Number!!!");
			return mv;
		}
		return mv;
	}
}
