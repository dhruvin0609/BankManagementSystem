package com.jt.project.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jt.project.entity.Account;
import com.jt.project.entity.Loan;
import com.jt.project.entity.User;
import com.jt.project.repository.UserDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	private UserDao obj;
	
	@RequestMapping("register")
	public ModelAndView registerpage(HttpServletRequest req) {
		User user = new User();
		Account account = new Account();
		user.setEmail(req.getParameter("email"));
		user.setMobile_no(Long.parseLong(req.getParameter("mobile")));
		user.setName(req.getParameter("name"));
		user.setPassword(req.getParameter("password"));
		user.setPassword(req.getParameter("password"));
		user.setAccount_no(account);
		account.setAccount_type(req.getParameter("account_type"));
		obj.adduser(user, account);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		mv.addObject("msg", "SignUp Successful");
		mv.addObject("email", req.getParameter("email"));
		mv.addObject("password", req.getParameter("password"));
		return mv;
	}
	@RequestMapping("home")
	public ModelAndView home(HttpServletRequest req) {
//		String email = req.getParameter("email");
//		String pass = req.getParameter("password");
		return obj.hm();
	}
	@RequestMapping("Login")
	public ModelAndView logincheck(HttpServletRequest req) {
		String email = req.getParameter("email");
		String pass = req.getParameter("password");
		return obj.check(email, pass);
	}
	
	@RequestMapping("logout")
	public String logout() {
		obj.logout();
		return "login";
	}
	
	@RequestMapping("viewprofile")
	public String viewprofile() {
		return "viewprofile";
	}
	
	@RequestMapping("delete")
	public ModelAndView delete(HttpServletRequest req) {
		String email = req.getParameter("email");
		return obj.delete(email);
	}
	
	@RequestMapping("read")
	public ModelAndView read(HttpServletRequest req) {
		
		return obj.read();
	}
	@RequestMapping("deposit")
	public ModelAndView deposite(HttpServletRequest req) {
		
		int amount = Integer.parseInt(req.getParameter("depositvalue"));
		return obj.deposit(amount);
	}
	@RequestMapping("withdraw")
	public ModelAndView withdraw(HttpServletRequest req) {
		
		int amount = Integer.parseInt(req.getParameter("withdrawvalue"));
		return obj.withdraw(amount);
	}
	@RequestMapping("loan")
	public ModelAndView loan(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("loan");
		return mv;
	}
	@RequestMapping("ln")
	public ModelAndView ln(HttpServletRequest req) {
		
		String loantype = (String) req.getParameter("loantype");
		double amount =  Integer.parseInt(req.getParameter("amount"));
		int duration = Integer.parseInt(req.getParameter("duration"));
		int amount1 = (int) amount;
		Loan ln = new Loan();
		amount = amount + amount * 0.06;
		ln.setAmount(amount);
		ln.setDuration(duration);
		ln.setLoan_type(loantype);
		ln.setInstallment(amount / duration);
		return obj.loan(ln, amount1);
	}
	@RequestMapping("loandetails")
	public ModelAndView lndetails(HttpServletRequest req) {
		
		return obj.lndetails();
	}
	@RequestMapping("installment")
	public ModelAndView installment(HttpServletRequest req) {
		return obj.installment();
	}
	@RequestMapping("update")
	public ModelAndView update(HttpServletRequest req) {
		String email = req.getParameter("email");
		int count = 0;
		if(req.getParameter("name") != "") {
			String name = req.getParameter("name");
			obj.updatename(email, name);
			count++;
		}
		if(req.getParameter("nemail") != "") {
			String nemail = req.getParameter("nemail");
			obj.updateemail(email, nemail);
			count++;
		}
		if(req.getParameter("mobile") != "") {
			Long mobile = Long.parseLong(req.getParameter("mobile"));
			obj.updatemobile(email, mobile);
			count++;
		}
		if(req.getParameter("password") != "") {
			String password = req.getParameter("password");
			obj.updatepassword(email, password);
			count++;
		}
		ModelAndView mv = new ModelAndView("viewprofile");
		if(count > 0) {
			mv.addObject("message", "Profile Updated Successful");
		}
		else {
			mv.addObject("message", "Failed to Update Profile, Please try Again.");
		}
		return mv;
	}
	@RequestMapping("showtransection")
	public ModelAndView st(HttpServletRequest req) {
//		ModelAndView mv = new ModelAndView("transection");
		return obj.transection();
	}
	@RequestMapping("transfer")
	public String tr(HttpServletRequest req) {
		return "transferform";
	}
	@RequestMapping("Transfer")
	public ModelAndView Tr(HttpServletRequest req) {
		int amount = Integer.parseInt(req.getParameter("amount"));
		int acnum = Integer.parseInt(req.getParameter("acnum"));
		return obj.tr(acnum, amount);
	}
}
