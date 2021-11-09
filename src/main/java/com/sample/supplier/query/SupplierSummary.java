package com.sample.supplier.query;

import com.sample.supplier.api.SupplierStatus;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

@Data
@Builder
@With
public class SupplierSummary implements Persistable<String> {

  @Id
  private final String id;

  private final String name;
  private final String taxId;
  private final String countryCode;
  private final SupplierStatus status;
  private final Instant lastUpdated;

  @Transient
  private boolean newEntity;

  @Override
  public boolean isNew() {
    return newEntity;
  }
}
