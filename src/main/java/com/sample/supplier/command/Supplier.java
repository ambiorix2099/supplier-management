package com.sample.supplier.command;

import com.sample.supplier.api.SupplierStatus;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

@Data
@With
@Builder
@AllArgsConstructor
public class Supplier implements Persistable<String> {

  @Id
  private final String id;

  private final String name;
  private final String taxId;
  private final String countryCode;
  private final SupplierStatus status;
  private final Instant lastUpdated;

  @PersistenceConstructor
  public Supplier(String id, String name, String taxId, String countryCode,
      SupplierStatus status, Instant lastUpdated) {
    this.id = id;
    this.name = name;
    this.taxId = taxId;
    this.countryCode = countryCode;
    this.status = status;
    this.lastUpdated = lastUpdated;
  }

  @Transient
  private boolean newEntity;

  @Override
  public boolean isNew() {
    return newEntity;
  }
}
