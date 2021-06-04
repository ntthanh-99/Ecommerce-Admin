package com.tienthanh.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tienthanh.domain.employee.Employee;
import com.tienthanh.domain.product.ImportBill;
import com.tienthanh.domain.product.ImportProduct;
import com.tienthanh.domain.product.Product;
import com.tienthanh.domain.product.Supplier;
import com.tienthanh.repository.AccountRepository;
import com.tienthanh.service.EmployeeService;
import com.tienthanh.service.ProductService;
import com.tienthanh.service.impl.FormatDateImpl;

@Controller
public class ImportController {
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private FormatDateImpl formatDate;

	private List<ImportProduct> importProductList = new ArrayList<ImportProduct>();

	private double total = 0;

	@RequestMapping("/import")
	public String importProduct(Model model, Principal principal) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);

		List<Supplier> supplierList = productService.findAllSupplier();
		model.addAttribute("supplierList", supplierList);

		List<Product> productList = productService.findAll();
		model.addAttribute("productList", productList);

		return "importProduct";
	}

	@GetMapping("/addImportProduct")
	public String addImportProduct(Model model, Principal principal, @RequestParam("supplierId") Long supplierId,
			@RequestParam("productId") Long productId, @RequestParam("quanlity") int quanlity,
			@RequestParam("importPrice") double importPrice) {
		Product product = productService.findById(productId);
		Supplier supplier = productService.findSupplierById(supplierId);

		ImportProduct importProduct = new ImportProduct();
		importProduct.setProduct(product);
		importProduct.setSupplier(supplier);
		importProduct.setQuanlity(quanlity);
		importProduct.setImportPrice(importPrice);

		importProductList.add(importProduct);

		return "redirect:/import";
	}

	@RequestMapping("/createImportBill")
	public String createImportBill(Model model, Principal principal) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);

		for (ImportProduct importProduct : importProductList) {
			total += importProduct.getQuanlity() * importProduct.getImportPrice();
		}

		model.addAttribute("importProductList", importProductList);
		model.addAttribute("total", total);
		return "importBill";
	}

	@RequestMapping("/cancelImportBill")
	public String cancelImportBill(Model model, Principal principal) {
		importProductList.clear();
		total = 0;
		return "redirect:/import";
	}

	@RequestMapping("saveImportBill")
	public String saveImportBill(Model model, Principal principal) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);

		ImportBill importBill = new ImportBill();
		importBill.setTotal(total);
		importBill.setImportProductList(importProductList);
		LocalDateTime createDateNow = LocalDateTime.now();
		Date createDate = formatDate.convertLocalDateTimeToDate(createDateNow);
		importBill.setCreateDate(createDate);
		importBill.setEmployee(employee);

		productService.saveBill(importBill);

		importProductList.clear();
		total = 0;

		String message = "Tạo hóa đơn thành công!";
		model.addAttribute("message", message);

		return "importBill";
	}

	@RequestMapping("/historyImport")
	public String historyImportMaterial(Model model, Principal principal) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);

		Date localDate = formatDate.convertLocalDateTimeToDate(LocalDateTime.now());
		List<ImportBill> importlBillList = productService.findAllImportBill();
		model.addAttribute("importlBillList", importlBillList);

		return "historyImport";
	}

	@RequestMapping("/viewImportBill")
	public String viewImportBill(Model model, Principal principal, @ModelAttribute("id") Long id) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);

		ImportBill importBill = productService.findImportBillById(id);
		model.addAttribute("importProductList", importBill.getImportProductList());
		model.addAttribute("total", importBill.getTotal());
		return "viewImportBill";
	}
}
