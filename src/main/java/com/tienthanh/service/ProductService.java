package com.tienthanh.service;

import java.util.List;

import com.tienthanh.domain.product.Book;
import com.tienthanh.domain.product.Clothes;
import com.tienthanh.domain.product.Electronic;
import com.tienthanh.domain.product.Product;

public interface ProductService {
	List<Product> findAllUnactiveProduct();

	List<Product> findAllActiveProduct();

	Product saveProduct(Product product);

	Product findById(Long id);

	Book findBookByProduct(Product product);

	Clothes findClothesByProduct(Product product);

	Electronic findElectronicByProduct(Product product);

	Book findBookById(Long id);

	Clothes findClothesById(Long id);

	Electronic findElectronicById(Long id);

	Book saveBook(Book book);

	Clothes saveClothes(Clothes clothes);

	Electronic saveElectronic(Electronic electronic);
}
