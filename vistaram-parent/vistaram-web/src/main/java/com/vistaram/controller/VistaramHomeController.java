package com.vistaram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VistaramHomeController {
	
	@RequestMapping("/bookingshome")
	public String bookingsHome(){
		return "bookingdetails";
	}

}
