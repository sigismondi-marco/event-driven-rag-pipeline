package com.github.sigismondi.marco.domain.model;

import jakarta.persistence.*;
import java.time.Instant;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "position_events")
public class PositionEventEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id; 

    @Column(name = "event_id")
    public String eventId;

    @Column(name = "schema_version")
    public String schemaVersion;
    
    public Instant timestamp;

    @Column(name = "device_id")
    public String deviceId;

    @Column(name = "device_type")
    public String deviceType;


    @Embedded
    public Location location;

    @Embedded
    public Payload payload;
}