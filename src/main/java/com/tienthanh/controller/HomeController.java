package com.tienthanh.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tienthanh.domain.employee.Employee;
import com.tienthanh.repository.AccountRepository;
import com.tienthanh.service.EmployeeService;

@Controller
public class HomeController {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AccountRepository accountRepository;

	@RequestMapping("/")
	public String redirectToHome() {
		return "redirect:/home";
	}
	@RequestMapping("/home")
	public String home(Model model, Principal principal) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);
		return "index";
	}
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
}
