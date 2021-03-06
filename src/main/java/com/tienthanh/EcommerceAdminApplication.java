package com.tienthanh;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import com.tienthanh.config.SecurityConfig;
import com.tienthanh.domain.Account;
import com.tienthanh.domain.employee.Employee;
import com.tienthanh.domain.product.Product;
import com.tienthanh.domain.security.AccountRole;
import com.tienthanh.domain.security.Role;
import com.tienthanh.repository.RoleRepository;
import com.tienthanh.service.EmployeeService;
import com.tienthanh.service.ProductService;
import com.tienthanh.service.impl.FormatDateImpl;

@SpringBootApplication
public class EcommerceAdminApplication implements CommandLineRunner{
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private FormatDateImpl formateDate;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceAdminApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Employee employee = new Employee();
		Account account = new Account();
		account.setUsername("admin");
		account.setPassword(SecurityConfig.passwordEncoder().encode("admin"));
		account.setPosition("admin");
		account.setEnabled(true);
		employee.setAccount(account);
		employee.setCreateDate(formateDate.convertLocalDateTimeToDate(LocalDateTime.now()));
		
		
		Role role = roleRepository.findByName("EMPLOYEE_ADMIN");
		if (role == null) {
			role = new Role();
			role.setName("EMPLOYEE_ADMIN");
		}

		Set<AccountRole> accountRoles = new HashSet<AccountRole>();
		accountRoles.add(new AccountRole(role, account));
		
		employeeService.createEmployee(employee, accountRoles);
		

	}

}
