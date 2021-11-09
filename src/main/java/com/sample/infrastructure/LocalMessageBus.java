package com.sample.infrastructure;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class LocalMessageBus {

  private final ApplicationEventPublisher eventPublisher;

  public LocalMessageBus(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  public void publish(Object event) {
    eventPublisher.publishEvent(event);
  }
}
