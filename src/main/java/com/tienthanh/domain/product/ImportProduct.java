package com.tienthanh.domain.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ImportProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private int quanlity;
	private double importPrice;

	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "import_bill_id")
	private ImportBill importBill;

	public ImportProduct() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ImportBill getImportBill() {
		return importBill;
	}

	public void setImportBill(ImportBill importBill) {
		this.importBill = importBill;
	}

	public int getQuanlity() {
		return quanlity;
	}

	public void setQuanlity(int quanlity) {
		this.quanlity = quanlity;
	}

	public double getImportPrice() {
		return importPrice;
	}

	public void setImportPrice(double importPrice) {
		this.importPrice = importPrice;
	}

}
