package com.sample.supplier.command;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.sample.infrastructure.LocalMessageBus;
import com.sample.supplier.api.AddSupplierCommand;
import com.sample.supplier.api.EnableSupplierCommand;
import com.sample.supplier.api.SupplierAddedEvent;
import com.sample.supplier.api.SupplierEnabledEvent;
import com.sample.supplier.api.SupplierStatus;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Mono;
import xyz.downgoon.snowflake.Snowflake;

@DgsComponent
public class SupplierService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SupplierService.class);

  private final Snowflake snowflake;
  private final SupplierRepository repository;
  private final LocalMessageBus messageBus;

  public SupplierService(Snowflake snowflake, SupplierRepository repository,
      LocalMessageBus messageBus) {
    this.snowflake = snowflake;
    this.repository = repository;
    this.messageBus = messageBus;
  }

  @DgsMutation
  public Mono<SupplierAddedEvent> addSupplier(@InputArgument("input") AddSupplierCommand command) {

    final AddSupplierCommand validatedCommand = AddSupplierCommand
        .newBuilder(command)
        .setId(String.valueOf(snowflake.nextId()))
        .setStatus(SupplierStatus.ENABLING)
        .setTimestamp(Instant.now())
        .build();

    return repository
        .existsById(validatedCommand.getId())
        .flatMap(exists -> exists
            ? supplierAlreadyExistsError(validatedCommand.getId())
            : repository.save(newSupplierEntity(validatedCommand)))
        .thenReturn(supplierAddedEvent(validatedCommand))
        .doOnNext(messageBus::publish);
  }

  @EventListener
  public void on(EnableSupplierCommand command) {
    LOGGER.info("About to enable supplier with id '{}'", command.getId());

    repository
        .findById(command.getId())
        .map(entity -> entity
            .withStatus(command.getStatus())
            .withLastUpdated(Instant.now()))
        .flatMap(repository::save)
        .map(entity -> SupplierEnabledEvent.newBuilder()
            .setId(entity.getId())
            .setStatus(entity.getStatus())
            .setTimestamp(entity.getLastUpdated())
            .setCommandTimestamp(command.getTimestamp())
            .build())
        .subscribe(messageBus::publish);
  }

  private static Mono<IllegalStateException> supplierAlreadyExistsError(String id) {
    final String message = String.format("Cannot add supplier with id %s, it already exists.", id);
    return Mono.error(new IllegalStateException(message));
  }

  private static Supplier newSupplierEntity(AddSupplierCommand command) {
    return Supplier.builder()
        .newEntity(true)
        .id(command.getId())
        .name(command.getName())
        .taxId(command.getTaxId())
        .countryCode(command.getCountryCode())
        .status(command.getStatus())
        .lastUpdated(command.getTimestamp())
        .build();
  }

  private static SupplierAddedEvent supplierAddedEvent(AddSupplierCommand command) {
    return SupplierAddedEvent.newBuilder()
        .setId(command.getId())
        .setName(command.getName())
        .setTaxId(command.getTaxId())
        .setCountryCode(command.getCountryCode())
        .setStatus(command.getStatus())
        .setCommandTimestamp(command.getTimestamp())
        .setTimestamp(Instant.now())
        .build();
  }
}
