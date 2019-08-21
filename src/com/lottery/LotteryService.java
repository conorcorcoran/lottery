package com.lottery;

import javax.ws.rs.*;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/")
public class LotteryService {
	
	private static HashMap<Integer, Ticket> tickets = new HashMap<Integer, Ticket>();
	private static final AtomicInteger count = new AtomicInteger(0);
	
	@POST
	@Path("/ticket/createTicket")
	@Produces("text/plain")
	public String createTicket() {
		//Create new ticket with 3 lines
		Ticket ticket = new Ticket();
		tickets.put(count.incrementAndGet(), ticket);
		System.out.println("id counter "+ count);
		System.out.println(tickets.size());
		return "New ticket created";
	}
	
	@GET
	@Path("/ticket/listTickets")
	@Produces("text/plain")
	public String listTickets() {
		String str = "";
		for(Integer key: tickets.keySet()) {
			str += key + " ";
		}
		return str;
	}
	
	@GET
	@Path("/ticket/getLines")
	@Produces("text/plain")
	public void getLines() {
		tickets.get(2).printLines();
	}
	
	@GET
	@Path("/ticket/getTicket")	
	public String getTicket(@QueryParam("id") int id){
		String ticket = "No tickets created yet";
		if(!tickets.isEmpty()) {
			for(int i = 0; i < tickets.size(); i++) {
				if(tickets.containsKey(id)) {
					ticket = "Ticket id " + id;
				}
				else{
					ticket = "Ticket id: " + id + " does not exist";
				}
			}
	    }	
		return ticket;
	}
	
	@PUT
	@Path("/ticket/amendTicket")
	@Produces("text/plain")
	public void amendTicket() {
		//Add new lines to a ticket
	}
	
	@PUT
	@Path("/status/getStatus")
	@Produces("text/plain")
	@Consumes("text/plain")
	public void getStatus() {
		//check ticket for results
	}
	
}
