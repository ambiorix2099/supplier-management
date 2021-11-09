package com.sample.supplier.saga;

import com.sample.LocalMessageBus;
import com.sample.supplier.api.EnableSupplierCommand;
import com.sample.supplier.api.SupplierAddedEvent;
import com.sample.supplier.api.SupplierStatus;
import com.sample.supplier.api.SupplierVerificationRequest;
import com.sample.supplier.api.SupplierVerificationResult;
import java.time.Duration;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EnableSupplierSaga {

  private static final Logger LOGGER = LoggerFactory.getLogger(EnableSupplierSaga.class);

  private final LocalMessageBus messageBus;
  private final IdentityVerificationProvider verificationProvider;

  public EnableSupplierSaga(LocalMessageBus messageBus, IdentityVerificationProvider verifier) {
    this.messageBus = messageBus;
    this.verificationProvider = verifier;
  }

  @EventListener
  public void on(SupplierAddedEvent event) {
    LOGGER.info("Verifying identity of supplier with id '{}'", event.getId());

    verificationProvider
        .verify(SupplierVerificationRequest.newBuilder()
            .setTaxId(event.getTaxId())
            .setCountryCode(event.getCountryCode())
            .build())
        .filter(response -> SupplierVerificationResult.PASSED.equals(response.getResult()))
        .delayElement(Duration.ofMillis(2000))
        .subscribe(response -> messageBus.publish(EnableSupplierCommand.newBuilder()
            .setId(event.getId())
            .setTimestamp(Instant.now())
            .setStatus(SupplierStatus.ENABLED)
            .build()));
  }
}
