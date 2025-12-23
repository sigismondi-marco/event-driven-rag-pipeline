package com.github.sigismondi.marco.api;

import java.util.List;

import org.jboss.logging.Logger;

import com.github.sigismondi.marco.domain.model.Location;
import com.github.sigismondi.marco.domain.model.Payload;
import com.github.sigismondi.marco.domain.model.PositionEventEntity;
import com.github.sigismondi.marco.streams.model.PositionEvent;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PositionResource {

    private static final Logger LOG = Logger.getLogger(PositionResource.class);

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response storeEvent(PositionEvent event) {
        PositionEventEntity entity = toPositionEntity(event);
        entity.persist(); 
        return Response.status(Response.Status.CREATED).entity(entity).build();
    }

    
    @GET
    public List<PositionEvent> getAll(){
        LOG.info("In GET");
        return PositionEventEntity.<PositionEventEntity>listAll()
        .stream()
        .map(this::toPositionEventRecord)
        .toList();        
    }

    private PositionEventEntity toPositionEntity(PositionEvent event) {
        PositionEventEntity entity = new PositionEventEntity();

        if(event.metadata() != null){
            entity.deviceId = event.metadata().deviceId();
            entity.deviceType = event.metadata().deviceType();
            entity.eventId = event.metadata().eventId();
            entity.schemaVersion = event.metadata().schemaVersion();
            entity.timestamp = event.metadata().timestamp();
        }


        if(event.payload() != null){
            Payload payload = new Payload();
            payload.accUnit = event.payload().accelerometer().unit();
            payload.accX = event.payload().accelerometer().x();
            payload.accY = event.payload().accelerometer().y();
            payload.accZ = event.payload().accelerometer().z();
            payload.engineRpm = event.payload().engineStats().rpm();
            payload.speedKmh = event.payload().engineStats().speedKmh();
            payload.fuelLevelPercent = event.payload().engineStats().fuelLevelPercent();
            payload.status = event.payload().status();
            entity.payload = payload;
        }

        if(event.location() != null){
            Location location = new Location();
            location.altitude = event.location().altitude();
            location.latitude = event.location().latitude();
            location.longitude = event.location().longitude();
            location.type = event.location().type();
            entity.location = location;
        }
        return entity;
    }

    private PositionEvent toPositionEventRecord(PositionEventEntity entity) {
    if (entity == null) {
        return null;
    }

    // 1. Map Metadata
    PositionEvent.Metadata metadata = new PositionEvent.Metadata(
        entity.eventId,
        entity.schemaVersion,
        entity.timestamp,
        entity.deviceId,
        entity.deviceType
    );

    // 2. Map Location
    PositionEvent.Location location = null;
    if (entity.location != null) {
        location = new PositionEvent.Location(
            entity.location.type,
            entity.location.longitude,
            entity.location.latitude,
            entity.location.altitude
        );
    }

    // 3. Map Payload (Accelerometer + EngineStats)
    PositionEvent.Payload payload = null;
    if (entity.payload != null) {
        PositionEvent.Accelerometer acc = new PositionEvent.Accelerometer(
            entity.payload.accX,
            entity.payload.accY,
            entity.payload.accZ,
            entity.payload.accUnit
        );

        PositionEvent.EngineStats engine = new PositionEvent.EngineStats(
            entity.payload.engineRpm,
            entity.payload.speedKmh,
            entity.payload.fuelLevelPercent
        );

        payload = new PositionEvent.Payload(
            acc,
            engine,
            entity.payload.status
        );
    }

    return new PositionEvent(metadata, location, payload);
}

}