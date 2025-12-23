package com.github.sigismondi.marco.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class RAGTopology {

    private static final Logger LOG = Logger.getLogger(RAGTopology.class);

    @ConfigProperty(name = "kafka-streams.topic.in")
    String inputTopic;

    @Produces
    public Topology buildTopology() {
        LOG.info("Start reading from " + inputTopic);
        StreamsBuilder builder = new StreamsBuilder();

        builder.stream(inputTopic, Consumed.with(Serdes.String(), Serdes.String()))
            .peek((key, value) -> {
                LOG.info("-------------------------------------------------");
                LOG.info("🔥 CDC Event Detected from Debezium!");
                //LOG.infof("🔑 Key (ID): %s", key);
                //LOG.infof("📦 Payload: %s", value);
                LOG.info("-------------------------------------------------");
            });

        return builder.build();
    }
}
