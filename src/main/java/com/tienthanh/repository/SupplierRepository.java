package com.tienthanh.repository;

import org.springframework.data.repository.CrudRepository;

import com.tienthanh.domain.product.Supplier;

public interface SupplierRepository extends CrudRepository<Supplier, Long> {

}
