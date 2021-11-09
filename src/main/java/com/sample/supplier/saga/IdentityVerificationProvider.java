package com.sample.supplier.saga;

import com.sample.supplier.api.SupplierVerificationRequest;
import com.sample.supplier.api.SupplierVerificationResponse;
import reactor.core.publisher.Mono;

public interface IdentityVerificationProvider {

  Mono<SupplierVerificationResponse> verify(SupplierVerificationRequest request);
}
