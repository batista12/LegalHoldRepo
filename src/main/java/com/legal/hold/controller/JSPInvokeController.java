package com.legal.hold.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JSPInvokeController {

	@GetMapping(value = "/logoff")
	public String loggOff(Model model) {

		model.addAttribute("name", "test");
		return "logoff";

	}
}
