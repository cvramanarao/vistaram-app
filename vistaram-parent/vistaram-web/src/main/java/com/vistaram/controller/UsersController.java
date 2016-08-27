package com.vistaram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsersController {
	
	@RequestMapping("/")
	public String home(){
		return "home";
	}
	
	@RequestMapping("/home")
	public String homeAgain(){
		return "home";
	}

}
