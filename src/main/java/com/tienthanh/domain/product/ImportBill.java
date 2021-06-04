package com.tienthanh.domain.product;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.tienthanh.domain.AbstractClass;
import com.tienthanh.domain.employee.Employee;

@Entity
public class ImportBill extends AbstractClass {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private double total;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "importBill")
	private List<ImportProduct> importProductList;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	public ImportBill() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<ImportProduct> getImportProductList() {
		return importProductList;
	}

	public void setImportProductList(List<ImportProduct> importProductList) {
		this.importProductList = importProductList;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
