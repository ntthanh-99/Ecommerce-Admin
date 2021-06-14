package com.tienthanh.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.influx.InfluxDbOkHttpClientBuilderProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tienthanh.domain.employee.Employee;
import com.tienthanh.domain.product.Book;
import com.tienthanh.domain.product.Clothes;
import com.tienthanh.domain.product.Electronic;
import com.tienthanh.domain.product.Product;
import com.tienthanh.repository.AccountRepository;
import com.tienthanh.service.EmployeeService;
import com.tienthanh.service.ProductService;
import com.tienthanh.service.impl.FormatDateImpl;

@Controller
public class DeloyProductController {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ProductService productService;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private FormatDateImpl formatDate;

	private String typeProduct;
	private Long productId;

	@RequestMapping("/addtoWeb")
	public String addtoWeb(Model model, Principal principal) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);
		List<Product> products = productService.findAllUnactiveProduct();
		model.addAttribute("productList", products);
		return "addToWeb";
	}

	@RequestMapping("/modifyProduct{id}")
	public String addtoWeb(Model model, Principal principal, @RequestParam("id") Long id) {
		String username = principal.getName();
		Employee employee = employeeService.findByAccount(accountRepository.findByUsername(username));
		model.addAttribute("employee", employee);
		Product product = productService.findById(id);
		productId = id;
		if (productService.findBookByProduct(product) != null) {
			Book book = productService.findBookByProduct(product);
			model.addAttribute("bookInfo", true);
			model.addAttribute("book", book);
			typeProduct = "book";
		} else {
			if (productService.findClothesByProduct(product) != null) {
				Clothes clothes = productService.findClothesByProduct(product);
				model.addAttribute("clothesInfo", true);
				model.addAttribute("clothes", clothes);
				typeProduct = "clothes";
			} else {
				if (productService.findElectronicByProduct(product) != null) {
					Electronic electronic = productService.findElectronicByProduct(product);
					model.addAttribute("electronicInfo", true);
					model.addAttribute("electronic", electronic);
					typeProduct = "electronic";
				}
			}
		}
		return "modifyProduct";
	}

	@PostMapping("/modifyProduct")
	public String modifyProduct(@ModelAttribute("clothes") Clothes updateClothes,
			@ModelAttribute("book") Book updateBook, @ModelAttribute("electronic") Electronic updateElectronic) {
		if (typeProduct.equals("book")) {
			Book book = productService.findBookById(updateBook.getId());
			updateBook.setModifyDate(formatDate.convertLocalDateTimeToDate(LocalDateTime.now()));
			updateBook.getProduct().setQuanlity(book.getProduct().getQuanlity());
			updateBook.getProduct().setActive(true);
			productService.saveBook(updateBook);

			MultipartFile bookImage = updateBook.getProduct().getProductImage();
			try {
				byte[] bytes = bookImage.getBytes();
				String name = updateBook.getProduct().getId() + ".png";
				File file = new File("src/main/resources/static/image/book/" + name);
				file.delete();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/book/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (typeProduct.equals("electronic")) {
				Electronic electronic = productService.findElectronicById(updateElectronic.getId());
				updateElectronic.setModifyDate(formatDate.convertLocalDateTimeToDate(LocalDateTime.now()));
				updateElectronic.getProduct().setQuanlity(electronic.getProduct().getQuanlity());
				updateElectronic.getProduct().setActive(true);
				productService.saveElectronic(updateElectronic);

				MultipartFile electronicImage = updateElectronic.getProduct().getProductImage();
				try {
					byte[] bytes = electronicImage.getBytes();
					String name = updateElectronic.getId() + ".png";
					File file = new File("src/main/resources/static/image/electronic/" + name);
					file.delete();
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File("src/main/resources/static/image/electronic/" + name)));
					stream.write(bytes);
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				Clothes clothes = productService.findClothesById(updateClothes.getId());
				updateClothes.setModifyDate(formatDate.convertLocalDateTimeToDate(LocalDateTime.now()));
				updateClothes.getProduct().setQuanlity(clothes.getProduct().getQuanlity());
				updateClothes.getProduct().setActive(true);
				productService.saveClothes(updateClothes);
				MultipartFile clothesImage = updateClothes.getProduct().getProductImage();
				try {
					byte[] bytes = clothesImage.getBytes();
					String name = updateClothes.getId() + ".png";
					File file = new File("src/main/resources/static/image/clothes/" + name);
					file.delete();
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File("src/main/resources/static/image/clothes/" + name)));
					stream.write(bytes);
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "redirect:/home";
	}

}
