package com.github.sigismondi.marco.streams;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class KafkaConsumer {

    private static final Logger LOG = Logger.getLogger(KafkaConsumer.class);

    @Incoming("cdc-check") // Questo nome deve corrispondere al file properties
    public void consume(String payload) {
        LOG.info("========================================");
        LOG.info("📥 RECORD RICEVUTO DA KAFKA (Reactive Messaging)");
        LOG.info("📦 Payload: " + payload);
        LOG.info("========================================");
    } 


}
