package com.tienthanh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.influx.InfluxDbOkHttpClientBuilderProvider;
import org.springframework.stereotype.Service;

import com.tienthanh.domain.product.Book;
import com.tienthanh.domain.product.Clothes;
import com.tienthanh.domain.product.Electronic;
import com.tienthanh.domain.product.ImportBill;
import com.tienthanh.domain.product.ImportProduct;
import com.tienthanh.domain.product.Product;
import com.tienthanh.domain.product.Supplier;
import com.tienthanh.repository.BookRepository;
import com.tienthanh.repository.ClothesRepository;
import com.tienthanh.repository.ElectronicRepository;
import com.tienthanh.repository.ImportBillRepository;
import com.tienthanh.repository.ProductRepository;
import com.tienthanh.repository.SupplierRepository;
import com.tienthanh.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ClothesRepository clothesRepository;

	@Autowired
	private ElectronicRepository electronicRepository;

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private ImportBillRepository importBillRepository;

	@Override
	public List<Product> findAllUnactiveProduct() {
		// TODO Auto-generated method stub
		List<Product> unactiveProducts = new ArrayList<>();
		List<Product> products = (List<Product>) productRepository.findAll();
		for (Product product : products) {
			if (product.isActive() == false) {
				unactiveProducts.add(product);
			}
		}
		return unactiveProducts;
	}

	@Override
	public Product saveProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}

	@Override
	public Product findById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id).get();
	}

	@Override
	public Book findBookByProduct(Product product) {
		// TODO Auto-generated method stub
		return bookRepository.findByProduct(product);
	}

	@Override
	public Clothes findClothesByProduct(Product product) {
		// TODO Auto-generated method stub
		return clothesRepository.findByProduct(product);
	}

	@Override
	public Electronic findElectronicByProduct(Product product) {
		// TODO Auto-generated method stub
		return electronicRepository.findByProduct(product);
	}

	@Override
	public Book findBookById(Long id) {
		// TODO Auto-generated method stub
		return bookRepository.findById(id).get();
	}

	@Override
	public Clothes findClothesById(Long id) {
		// TODO Auto-generated method stub
		return clothesRepository.findById(id).get();
	}

	@Override
	public Electronic findElectronicById(Long id) {
		// TODO Auto-generated method stub
		return electronicRepository.findById(id).get();
	}

	@Override
	public Book saveBook(Book book) {
		// TODO Auto-generated method stub
		return bookRepository.save(book);
	}

	@Override
	public Clothes saveClothes(Clothes clothes) {
		// TODO Auto-generated method stub
		return clothesRepository.save(clothes);
	}

	@Override
	public Electronic saveElectronic(Electronic electronic) {
		// TODO Auto-generated method stub
		return electronicRepository.save(electronic);
	}

	@Override
	public List<Product> findAllActiveProduct() {
		// TODO Auto-generated method stub
		List<Product> unactiveProducts = new ArrayList<>();
		List<Product> products = (List<Product>) productRepository.findAll();
		for (Product product : products) {
			if (product.isActive() == true) {
				unactiveProducts.add(product);
			}
		}
		return unactiveProducts;
	}

	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return (List<Product>) productRepository.findAll();
	}

	@Override
	public void deleteProductById(Long id) {
		// TODO Auto-generated method stub
		productRepository.deleteById(id);
	}

	@Override
	public List<Supplier> findAllSupplier() {
		// TODO Auto-generated method stub
		return (List<Supplier>) supplierRepository.findAll();
	}

	@Override
	public Supplier findSupplierById(Long id) {
		// TODO Auto-generated method stub
		return supplierRepository.findById(id).get();
	}

	@Override
	public ImportBill saveBill(ImportBill importBill) {
		// TODO Auto-generated method stub
		List<ImportProduct> importProducts = importBill.getImportProductList();
		for (ImportProduct importProduct : importProducts) {
			Product product = findById(importProduct.getProduct().getId());
			product.setQuanlity(product.getQuanlity() + importProduct.getQuanlity());
			productRepository.save(product);
			importProduct.setImportBill(importBill);
		}
		return importBillRepository.save(importBill);
	}

	@Override
	public List<ImportBill> findAllImportBill() {
		// TODO Auto-generated method stub
		return (List<ImportBill>) importBillRepository.findAll();
	}

	@Override
	public ImportBill findImportBillById(Long id) {
		// TODO Auto-generated method stub
		return importBillRepository.findById(id).get();
	}

}
