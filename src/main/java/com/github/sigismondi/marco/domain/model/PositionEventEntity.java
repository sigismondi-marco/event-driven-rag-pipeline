package com.github.sigismondi.marco.domain.model;

import jakarta.persistence.*;
import java.time.Instant;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "position_events")
public class PositionEventEntity extends PanacheEntityBase {

    @Id
    @Column(name = "event_id")
    public String eventId;

    public String schemaVersion;
    public Instant timestamp;
    public String deviceId;
    public String deviceType;

    @Embedded
    public Location location;

    @Embedded
    public Payload payload;
}

@Embeddable
class Location {
    public String type;
    public Double longitude;
    public Double latitude;
    public Double altitude;
}

@Embeddable
class Payload {
    public Double accX;
    public Double accY;
    public Double accZ;
    public String accUnit;
    
    public Integer engineRpm;
    public Double speedKmh;
    public Integer fuelLevelPercent;
    
    public String status;
}