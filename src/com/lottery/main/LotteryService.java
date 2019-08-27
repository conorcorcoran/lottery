package com.lottery.main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * LotteryService Class
 * This contains the rest api that is used to operate the Lottery.
 */

@Path("/")
public class LotteryService {

	private static HashMap<Integer, Ticket> tickets = new HashMap<Integer, Ticket>(); //This HashMap will contain all the tickets created
	private static final AtomicInteger count = new AtomicInteger(0); //This is used to create ID's for each ticket

	/**
	 * Creates a new ticket and adds it to the tickets list.
	 * @return Response confirming the ticket creation and what ID it has
	 */
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

	/**
	 * Gives a list of ticket ID's that are currently in the list.
	 * @return List of ticket ID's 
	 */
	@GET
	@Path("/ticket/listTickets")
	@Produces(MediaType.APPLICATION_JSON)
	public String listTickets() {
		String response = "";
		if(tickets.size() == 0) {
			response = "No tickets created yet";
		}else {
			response = "Ticket ID's: ";
			for(Integer key: tickets.keySet()) {
				response += key + ", ";
			}
			response = response.substring(0, response.length() - 2);
		}
		return response;
	}

	/**
	 * Gets a ticket and shows how many lines it contains.
	 * @param id id of ticket to get
	 * @return Response containing details about the ticket
	 */
	@GET
	@Path("/ticket/getTicket")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTicket(@QueryParam("id") int id){
		String response = "No tickets created yet";
		if(!tickets.isEmpty()) {
			for(int i = 0; i < tickets.size(); i++) {
				if(tickets.containsKey(id)) {
					response = "Ticket id " + id + " contains " + tickets.get(id).getNumberOfLines() + " lines" ;
				}
				else{
					response = "Ticket id: " + id + " does not exist";
				}
			}
		}	
		return response;
	}

	/**
	 * Amends ticket by adding addtional lines to it.
	 * @param id id of ticket to add lines to
	 * @param numOfLines Number of lines you want to add to the ticket
	 * @return Response with how many lines have been added to what ticket
	 */
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

	/**
	 * Gets the status of a ticket.
	 * @param id id of ticket to check
	 * @return The results unsorted and sorted of each line of the ticket
	 */
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

	/**
	 * Sorts the results using the value of the map.
	 * @param results Map of results (Line num, Result of the line) 
	 * @return Sorted LinkedHashMap
	 */
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

	/**
	 * Deletes all tickets currently in the map.
	 * Sets the counter back to 0, so next ticket created
	 * will have an id of 1.
	 */
	public void deleteAllTickets() {
		tickets.clear();
		count.set(0);
	}

	/**
	 * Gets a number of tickets of currently in the map of tickets.
	 * @return Number of tickets in the map
	 */
	public int getNumOfTickets(){
		System.out.println("Tickt size");
		System.out.println(tickets.size());
		return tickets.size();
	}

	/**
	 * Gets a number of how many lines a ticket has.
	 * @param id id number of the ticket to check.
	 * @return Number of lines in a ticket
	 */
	public int getNumOfLines(int id){
		int numOfLines = tickets.get(id).getNumberOfLines();
		return numOfLines;
	}

	/**
	 * Clears out the lines of a ticket and replaces them with preset lines.
	 * @param id The id number of the ticket to be changed
	 */
	public void testTicketSetUp(int id){
		tickets.get(id).presetLines();
	}
}
