package com.lottery;

import javax.ws.rs.*;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
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
	public String getLines(@QueryParam("ticketNo") int ticketNo) throws JSONException {
		System.out.println("Ticket no: " + ticketNo);
		return tickets.get(ticketNo).getLines().toString();
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
	public void amendTicket(@QueryParam("id") int id, @QueryParam("numOfLines") int numOfLines) {
		tickets.get(id).addLines(numOfLines);
	}

	@PUT
	@Path("/status/getStatus")
	@Produces("text/plain")
	public void getStatus(@QueryParam("id") int id) {
		HashMap<String, Integer> results = new HashMap<String, Integer>();
		for(int i = 1; i <= tickets.get(id).getLines().length(); i++) {
			try {
				System.out.println("Checking status of line " + i);
				System.out.println(tickets.get(id).getLines().getJSONArray("Line " + i));
				results.put("Line " + i, tickets.get(id).getResult(tickets.get(id).getLines().getJSONArray("Line " + i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Results");
		System.out.println(results);
		sortResults(results);
	}
	
	@SuppressWarnings("unchecked")
	public static void sortResults(HashMap<String, Integer> results){
		Object[] a = results.entrySet().toArray();
		Arrays.sort(a, new Comparator<Object>() {
		    public int compare(Object o1, Object o2) {
		        return ((Map.Entry<String, Integer>) o2).getValue()
		                   .compareTo(((Map.Entry<String, Integer>) o1).getValue());
		    }
		});
		for (Object e : a) {
		    System.out.println(((Map.Entry<String, Integer>) e).getKey() + " : "
		            + ((Map.Entry<String, Integer>) e).getValue());
		}
	}
}
