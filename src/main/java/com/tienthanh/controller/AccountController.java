package com.tienthanh.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tienthanh.config.SecurityConfig;
import com.tienthanh.domain.Account;
import com.tienthanh.domain.employee.Employee;
import com.tienthanh.domain.security.AccountRole;
import com.tienthanh.domain.security.Role;
import com.tienthanh.repository.AccountRepository;
import com.tienthanh.service.AccountService;
import com.tienthanh.service.EmployeeService;
import com.tienthanh.service.RoleService;
import com.tienthanh.service.impl.FormatDateImpl;

@Controller
public class AccountController {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private FormatDateImpl formatDate;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

	@RequestMapping("/account")
	public String account(Model model, Principal principal) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);
		List<Account> accountList = accountService.findAll();
		model.addAttribute("accountList", accountList);
		return "account";
	}

	@RequestMapping("/addNewAccount")
	public String addNewAccount(Model model, Principal principal) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);
		model.addAttribute("newAccount", new Account());
		return "newAccount";
	}

	@PostMapping("/addNewAccount")
	public String addNewAccountPost(@ModelAttribute("newAccount") Account account) {
		LocalDateTime createDateNow = LocalDateTime.now();
		Date createDate = formatDate.convertLocalDateTimeToDate(createDateNow);

		account.setPassword(SecurityConfig.passwordEncoder().encode(account.getPassword()));
		account.setEnabled(true);
		account.setCreateDate(createDate);

		Role role = roleService.findByName("EMPLOYEE_" + account.getPosition().toUpperCase());
		if (role == null) {
			role = new Role();
			role.setName("EMPLOYEE_" + account.getPosition().toUpperCase());
		}
		Set<AccountRole> accountRoles = new HashSet<AccountRole>();
		accountRoles.add(new AccountRole(role, account));

		accountService.createAccount(account, accountRoles);
		return "redirect:/account";
	}

	@RequestMapping("/updateAccount{id}")
	public String update(Model model, Principal principal, @ModelAttribute("id") Long id) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);

		Account updateAccount = accountService.findById(id);
		updateAccount.setPassword("*****");
		model.addAttribute("updateAccount", updateAccount);
		return "updateAccount";
	}

	@PostMapping("/updateAccount")
	public String updateAccount(@ModelAttribute("updateAccount") Account account) {
		Account updateAccount = accountService.findById(account.getId());
		updateAccount.setUsername(account.getUsername());
		updateAccount.setPassword(SecurityConfig.passwordEncoder().encode(account.getPassword()));
		updateAccount.setPosition(account.getPosition());
		// thay doi quyền truy cập

		LocalDateTime localDateTime = LocalDateTime.now();
		Date modifyDate = formatDate.convertLocalDateTimeToDate(localDateTime);
		updateAccount.setModifyDate(modifyDate);
		accountService.save(updateAccount);

		return "redirect:/account";
	}

	@RequestMapping("/deleteAccount{id}")
	public String delete(@ModelAttribute("id") Long id) {
		Account account = accountService.findById(id);
		if (account.isEnabled() == true) {
			account.setEnabled(false);
		} else {
			account.setEnabled(true);
		}
		accountService.save(account);
		return "redirect:/account";
	}
}
