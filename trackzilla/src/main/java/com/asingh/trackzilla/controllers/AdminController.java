package com.asingh.trackzilla.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AdminController {

	@GetMapping("/login")
	public String goToLogin() {
		log.info("Request to Login page");
		return "login";
	}
}
