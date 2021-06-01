package com.tienthanh.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tienthanh.domain.employee.Employee;
import com.tienthanh.domain.oder.SaleOff;
import com.tienthanh.domain.oder.SaleOffForProduct;
import com.tienthanh.domain.oder.SaleOffForTotal;
import com.tienthanh.repository.AccountRepository;
import com.tienthanh.service.EmployeeService;
import com.tienthanh.service.ProductService;
import com.tienthanh.service.SaleOffService;
import com.tienthanh.service.impl.FormatDateImpl;

@Controller
public class SaleOffController {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private SaleOffService saleOffService;

	@Autowired
	private ProductService productService;

	@Autowired
	private FormatDateImpl formatDate;

	@RequestMapping("/saleoff")
	public String saleOff(Model model, Principal principal) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);

		List<SaleOff> saleOffs = saleOffService.findAll();
		model.addAttribute("saleOffList", saleOffs);

		return "saleOff";
	}

	@RequestMapping("/addNewSaleOff")
	public String newSaleOff(Model model, Principal principal) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);

		model.addAttribute("saleOffForProduct", new SaleOffForProduct());
		model.addAttribute("saleOffForTotal", new SaleOffForTotal());
		model.addAttribute("classSaleOffForProduct", true);
		model.addAttribute("classSaleOffForTotal", true);
		model.addAttribute("productList", productService.findAllActiveProduct());
		return "newSaleOff";

	}

	@PostMapping("/newSaleOffForProduct")
	public String newSaleOffForProduct(@ModelAttribute("saleOffForProduct") SaleOffForProduct saleOffForProduct) {
		saleOffForProduct.setCreateDate(formatDate.convertLocalDateTimeToDate(LocalDateTime.now()));
		saleOffService.saveSaleOffForProduct(saleOffForProduct);
		return "redirect:/saleoff";
	}

	@PostMapping("/newSaleOffForTotal")
	public String newSaleOffForTotal(@ModelAttribute("saleOffForTotal") SaleOffForTotal saleOffForTotal) {
		saleOffForTotal.setCreateDate(formatDate.convertLocalDateTimeToDate(LocalDateTime.now()));
		saleOffService.saveSaleOffForTotal(saleOffForTotal);
		return "redirect:/saleoff";
	}

	@RequestMapping("deleteSaleOff{id}")
	public String deleteSaleOff(@ModelAttribute("id") Long id) {
		SaleOff saleOff = saleOffService.findSaleOffById(id);
		SaleOffForProduct saleOffForProduct = saleOffService.findSaleOffForProductBySaleOff(saleOff);
		SaleOffForTotal saleForTotal = saleOffService.findSaleOffForTotalBySaleOff(saleOff);
		if (saleOffForProduct != null) {
			saleOffService.deleteSaleOffForProductById(saleOffForProduct.getId());
		} else {
			saleOffService.deleteSaleOffForTotalById(saleForTotal.getId());
		}
		return "redirect:/saleoff";
	}
}
