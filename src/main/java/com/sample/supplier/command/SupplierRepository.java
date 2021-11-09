package com.sample.supplier.command;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SupplierRepository extends ReactiveCrudRepository<Supplier, String> {

}
