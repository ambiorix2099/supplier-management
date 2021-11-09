package com.sample.supplier.query;

import com.sample.supplier.api.SupplierAddedEvent;
import com.sample.supplier.api.SupplierEnabledEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SupplierQueryService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SupplierQueryService.class);

  private final SupplierQueryRepository repository;

  public SupplierQueryService(SupplierQueryRepository repository) {
    this.repository = repository;
  }

  @EventListener
  public void on(SupplierAddedEvent event) {

  }

  @EventListener
  public void on(SupplierEnabledEvent event) {
    LOGGER.info("About to update the supplier query repository with event:\n'{}'", event);
  }
}
