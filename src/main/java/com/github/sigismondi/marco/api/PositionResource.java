package com.github.sigismondi.marco.api;

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

    @POST
    @Transactional
    public Response receiveEvent(PositionEvent event) {
        PositionEventEntity entity = new PositionEventEntity();
        entity.deviceId = event.metadata().deviceId();
        //TO DO OTHERS        
        entity.persist(); 
        return Response.status(Response.Status.CREATED).entity(entity).build();
    }
}