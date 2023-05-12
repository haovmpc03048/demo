package com.demo.controller;

import java.awt.Window;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
	@Autowired
	HttpServletRequest rq;
	@Autowired
	HttpServletResponse response;
	@GetMapping("/account")
	public String login1(Model model)  {
		 Cookie[] cookies = rq.getCookies();
		    if (cookies != null) {
		        for (Cookie cookie1 : cookies) {
		            if (cookie1.getName().equals("username")) {
		                String user = cookie1.getValue();
		                model.addAttribute("user", user);
		                System.out.println("Username: " + user);
		            }
		            if (cookie1.getName().equals("password")) {
		                String pass = cookie1.getValue();
		                model.addAttribute("pass", pass);
		                System.out.println("Password: " + pass);
		            }
		        }
		    }
		return "login";
	}
	@PostMapping("/account/login")
	public String login(
			@RequestParam("username") String un,
			@RequestParam("password") String pw,
			@RequestParam(name="remember" , defaultValue = "false") boolean rm)  {
		if(rm) {
			Cookie cookie = new Cookie("username", un);
		    cookie.setMaxAge(24 * 60 * 60); // 24 hours in seconds
		    response.addCookie(cookie);

		    Cookie passwordCookie = new Cookie("password", pw);
		    passwordCookie.setMaxAge(24 * 60 * 60); // 24 hours in seconds
		    response.addCookie(passwordCookie);

		    Cookie[] cookies = rq.getCookies();
		    if (cookies != null) {
		        for (Cookie cookie1 : cookies) {
		            if (cookie1.getName().equals("username")) {
		                String user = cookie1.getValue();
		                rq.setAttribute("user", user);
		                System.out.println("Username: " + user);
		            }
		            if (cookie1.getName().equals("password")) {
		                String pass = cookie1.getValue();
		                rq.setAttribute("pass", pass);
		                System.out.println("Password: " + pass);
		            }
		        }
		    }

		}
		if(un.equals("minhhaou") && pw.equals("123")) {
			
			System.out.println(un);
			System.out.println(pw);
			rq.setAttribute("user", un);
			rq.setAttribute("pass", pw);
			rq.setAttribute("rem", rm);
		}else {
			rq.setAttribute("massage", "Tai khoan hoac mat khau sai");
		}

		return "login";
	}
}
