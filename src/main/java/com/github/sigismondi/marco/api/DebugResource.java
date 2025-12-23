package com.github.sigismondi.marco.api;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;


@Path("/debug")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DebugResource {

    private static final Logger LOG = Logger.getLogger(DebugResource.class);


    @Inject
    @Channel("cdc-out")
    Emitter<String> eventEmitter;

    @POST
    @Path("/send")
    public Response sendManualEvent(String jsonPayload) {
        LOG.infof("Invio manuale dell'evento: %s", jsonPayload);
        
        // Invia l'evento al topic
        eventEmitter.send(jsonPayload);
        
        return Response.accepted().entity("{\"status\": \"Evento inviato!\"}").build();
    }

}
