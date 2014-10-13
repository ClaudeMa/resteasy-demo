package fr.sewatech.demo.rest;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

@Path("/message")
@Consumes({ "application/json", "application/xml" }) 
@Produces({ "application/json", "application/xml" })
public class MessageEndpoint {

	private ConcurrentHashMap<Long, Message> messages = new ConcurrentHashMap<>();
	private AtomicLong nextId;
	
	public MessageEndpoint() {
		messages.put(1L, new Message(1L, "Text number 1"));
		messages.put(2L, new Message(2L, "Text number 2"));
		nextId = new AtomicLong(3);
	}
	
	@POST
	public Response create(Message message) {
		message.setId(nextId.getAndIncrement());
		messages.put(message.getId(), message);
		return Response.created(
				UriBuilder.fromResource(String.class)
						  .path(String.valueOf(message.getId()))
						  .build())
					   .build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") Long id) {
		Message message = messages.get(id);
		if ( message == null ) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(message).build();
	}

	@GET
	@Path("list")
	public Collection<Message> listAll() {
		return messages.values();
	}
	
	@GET
	public Response list() {
		return Response.ok(new GenericEntity<Collection<Message>>(listAll()) {})
				       .build();
//		return Response.ok(messages.values()).build();
	}	

	@GET
	@Path("array")
	public Message[] listArray() {
		return messages.values().toArray(new Message[0]);
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, Message message) {
		messages.put(id,  message);
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		messages.remove(id);
		return Response.noContent().build();
	}

}
