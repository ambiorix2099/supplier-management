package com.sample.supplier.saga.adapter;

import com.sample.supplier.api.SupplierVerificationRequest;
import com.sample.supplier.api.SupplierVerificationResponse;
import com.sample.supplier.api.SupplierVerificationResult;
import com.sample.supplier.saga.IdentityVerificationProvider;
import java.time.Duration;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SimulatedIdentityVerificationProvider implements IdentityVerificationProvider {

  @Override
  public Mono<SupplierVerificationResponse> verify(SupplierVerificationRequest request) {
    return Mono.just(SupplierVerificationResponse.newBuilder()
            .setTaxId(request.getTaxId())
            .setCountryCode(request.getCountryCode())
            .setResult(SupplierVerificationResult.PASSED)
            .build())
        .delayElement(Duration.ofMillis(1500));
  }
}
