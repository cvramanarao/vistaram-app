package com.vistaram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

@Controller
public class UsersController {
	
	@RequestMapping("/")
	public String home(){
		return "home";
	}

}
