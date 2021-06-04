package com.tienthanh.service;

import java.util.List;

import com.tienthanh.domain.product.Book;
import com.tienthanh.domain.product.Clothes;
import com.tienthanh.domain.product.Electronic;
import com.tienthanh.domain.product.ImportBill;
import com.tienthanh.domain.product.Product;
import com.tienthanh.domain.product.Supplier;

public interface ProductService {
	List<Product> findAll();

	List<Product> findAllUnactiveProduct();

	List<Product> findAllActiveProduct();

	List<Supplier> findAllSupplier();

	List<ImportBill> findAllImportBill();

	Supplier findSupplierById(Long Id);

	Product saveProduct(Product product);

	Product findById(Long id);

	Book findBookByProduct(Product product);

	Clothes findClothesByProduct(Product product);

	Electronic findElectronicByProduct(Product product);

	Book findBookById(Long id);

	Clothes findClothesById(Long id);

	Electronic findElectronicById(Long id);

	ImportBill findImportBillById(Long id);

	Book saveBook(Book book);

	Clothes saveClothes(Clothes clothes);

	Electronic saveElectronic(Electronic electronic);

	void deleteProductById(Long id);

	ImportBill saveBill(ImportBill importBill);

}
