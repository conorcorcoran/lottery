package com.lottery;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/")
public class LotteryService {

	private static HashMap<Integer, Ticket> tickets = new HashMap<Integer, Ticket>();
	private static final AtomicInteger count = new AtomicInteger(0);

	@POST
	@Path("/ticket/createTicket")
	@Produces(MediaType.APPLICATION_JSON)
	public String createTicket() {
		//Create new ticket with 3 lines
		Ticket ticket = new Ticket();
		tickets.put(count.incrementAndGet(), ticket);
		System.out.println("Ticket "+ count + " created");
		return "New ticket created with id " + count;
	}

	@GET
	@Path("/ticket/listTickets")
	@Produces(MediaType.APPLICATION_JSON)
	public String listTickets() {
		String str = "";
		for(Integer key: tickets.keySet()) {
			str += key + " ";
		}
		return str;
	}

	@GET
	@Path("/ticket/getTicket")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTicket(@QueryParam("id") int id){
		String ticket = "No tickets created yet";
		if(!tickets.isEmpty()) {
			for(int i = 0; i < tickets.size(); i++) {
				if(tickets.containsKey(id)) {
					ticket = "Ticket id " + id + " contains " + tickets.get(id).getNumberOfLines() + " lines" ;
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
	@Produces(MediaType.APPLICATION_JSON)
	public String amendTicket(@QueryParam("id") int id, @QueryParam("numOfLines") int numOfLines) {
		String response = "";
		if(tickets.containsKey(id)) {
			if(!tickets.get(id).getCheckedStatus()) {
				tickets.get(id).addLines(numOfLines);
				response = (numOfLines + " lines added to Ticket id: " + id);
			}
			else {
				System.out.println("Tickt already checked, can't be amended");
				response = "Tickt already checked, can't be amended";
			}
		}
		else {
			response = "Ticket id: " + id + " not found";
		}
		return response;
	}

	@PUT
	@Path("/status/getStatus")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStatus(@QueryParam("id") int id) {
		String response = "";
		if(tickets.containsKey(id)) {
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
			System.out.println("Sorted results");
			response = "Unsorted: " + results.toString() + " Sorted: " + sortResults(results).toString();
		}
		else {
			response = "Ticket id: " + id + " not found";
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Integer> sortResults(HashMap<String, Integer> results){
		Object[] numArray = results.entrySet().toArray();
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		Arrays.sort(numArray, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				return ((Map.Entry<String, Integer>) o2).getValue()
						.compareTo(((Map.Entry<String, Integer>) o1).getValue());
			}
		});
		for (Object e : numArray) {
			System.out.println(((Map.Entry<String, Integer>) e).getKey() + " : " + ((Map.Entry<String, Integer>) e).getValue());
			sortedMap.put(((Map.Entry<String, Integer>) e).getKey(), ((Map.Entry<String, Integer>) e).getValue());
		}
		return sortedMap;
	}

	public void deleteAllTickets() {
		tickets.clear();
		count.set(0);
	}

	public int getNumOfTickets(){
		System.out.println("Tickt size");
		System.out.println(tickets.size());
		return tickets.size();
	}

	public int getNumOfLines(int id){
		int numOfLines = tickets.get(id).getNumberOfLines();
		return numOfLines;
	}

	public void testTicketSetUp(int id){
		tickets.get(id).presetLines();
	}
}
