package com.tienthanh.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.influx.InfluxDbOkHttpClientBuilderProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tienthanh.domain.employee.Employee;
import com.tienthanh.domain.product.Book;
import com.tienthanh.domain.product.Clothes;
import com.tienthanh.domain.product.Electronic;
import com.tienthanh.domain.product.Product;
import com.tienthanh.repository.AccountRepository;
import com.tienthanh.service.EmployeeService;
import com.tienthanh.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ProductService productService;

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

	@RequestMapping("/myProfile")
	public String myProfile(Model model, Principal principal) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);
		return "myProfile";
	}

	@PostMapping("/updateProfile")
	public String updateProfile(Model model, @ModelAttribute("newPassword") String newPassword,
			@ModelAttribute("currentPassword") String curentPassword,
			@ModelAttribute("confirmPassword") String confirmPassword, @ModelAttribute("username") String username) {
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		// model.addAttribute("employee", employee);
		if (!employee.getAccount().getPassword().equals(curentPassword)) {
			model.addAttribute("incorrectPassword", true);
			return "myProfile";
		}
		if (!confirmPassword.equals(newPassword)) {
			model.addAttribute("equalPassword", true);
			return "myProfile";
		}
		employeeService.changePassword(employee, newPassword);
		return "myProfile";
	}
}
