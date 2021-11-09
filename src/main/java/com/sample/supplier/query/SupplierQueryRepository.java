package com.sample.supplier.query;

import com.sample.supplier.command.Supplier;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface SupplierQueryRepository extends ReactiveCrudRepository<SupplierSummary, String> {

  Mono<Supplier> getByName(String name);
}
