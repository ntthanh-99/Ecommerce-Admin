package com.tienthanh.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tienthanh.domain.oder.SaleOffForProduct;
import com.tienthanh.domain.oder.SaleOffForTotal;
import com.tienthanh.domain.product.Book;
import com.tienthanh.domain.product.Clothes;
import com.tienthanh.domain.product.Electronic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tienthanh.config.SecurityConfig;
import com.tienthanh.domain.Account;
import com.tienthanh.domain.employee.Employee;
import com.tienthanh.domain.product.Product;
import com.tienthanh.domain.security.AccountRole;
import com.tienthanh.domain.security.Role;
import com.tienthanh.repository.AccountRepository;
import com.tienthanh.service.EmployeeService;
import com.tienthanh.service.ProductService;
import com.tienthanh.service.impl.FormatDateImpl;

@Controller
public class ProductController {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private FormatDateImpl formatDate;

	@RequestMapping("/product")
	public String account(Model model, Principal principal) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);
		List<Product> productList = productService.findAll();
		model.addAttribute("productList", productList);
		return "product";
	}

	@RequestMapping("/addNewProduct")
	public String addNewProduct(Model model, Principal principal) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);

		model.addAttribute("book", new Book());
		model.addAttribute("electronic", new Electronic());
		model.addAttribute("clothes", new Clothes());
		model.addAttribute("classSaleOffForTotal", true);
		model.addAttribute("productList", productService.findAllActiveProduct());
		return "newProduct";
	}

	@PostMapping("/addNewProduct")
	public String addNewAccountPost(@ModelAttribute("newProduct") Product product) {
		LocalDateTime createDateNow = LocalDateTime.now();
		Date createDate = formatDate.convertLocalDateTimeToDate(createDateNow);
		product.setCreateDate(createDate);
		productService.saveProduct(product);
		return "redirect:/product";
	}

	@RequestMapping("/updateProduct{id}")
	public String update(Model model, Principal principal, @ModelAttribute("id") Long id) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);

		Product updateProduct = productService.findById(id);
		model.addAttribute("updateProduct", updateProduct);
		return "updateProduct";
	}

	@PostMapping("/updateProduct")
	public String updateAccount(@ModelAttribute("updateProduct") Product product) {
		LocalDateTime localDateTime = LocalDateTime.now();
		Date modifyDate = formatDate.convertLocalDateTimeToDate(localDateTime);
		productService.saveProduct(product);
		return "redirect:/product";
	}

	@RequestMapping("/deleteProduct{id}")
	public String delete(@ModelAttribute("id") Long id) {
		productService.deleteProductById(id);
		return "redirect:/product";
	}
}
