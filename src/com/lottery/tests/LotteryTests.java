package com.lottery.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.After;
import org.junit.jupiter.api.Test;

import com.lottery.main.LotteryService;

/**
 * Lottery Test
 * This is a test class made up of unit tests.
 * It goes through each method in the LotteryService class.
 */
class LotteryTests {
	private final LotteryService lotteryService = new LotteryService();
	
	@After
	public void tearDown() {
		lotteryService.deleteAllTickets();
	}

	@Test
	public void testCreateTicket() {
		lotteryService.createTicket();
		assertEquals("Ticket id 1 contains 3 lines", lotteryService.getTicket(1));
	}
	
	@Test
	public void testClearingTickets() {
		lotteryService.deleteAllTickets();
		assertEquals( "No tickets created yet", lotteryService.getTicket(1));
		lotteryService.createTicket();
		assertEquals(1, lotteryService.getNumOfTickets());
		lotteryService.deleteAllTickets();
		assertEquals( "No tickets created yet", lotteryService.getTicket(1));
	}
	
	@Test
	public void testListingTickets() {
		assertEquals("No tickets created yet", lotteryService.listTickets());
		lotteryService.createTicket();
		lotteryService.createTicket();
		lotteryService.createTicket();
		assertEquals("Ticket ID's: 1, 2, 3", lotteryService.listTickets());
	}
	
	@Test
	public void testGetTicket() {
		lotteryService.createTicket();
		lotteryService.createTicket();
		lotteryService.createTicket();
		assertEquals("Ticket id 1 contains 3 lines", lotteryService.getTicket(1));
		assertEquals("Ticket id 2 contains 3 lines", lotteryService.getTicket(2));
		assertEquals("Ticket id 3 contains 3 lines", lotteryService.getTicket(3));
		assertEquals("Ticket id: 4 does not exist", lotteryService.getTicket(4));
	}
	
	@Test
	public void testAmendTicket(){
		lotteryService.createTicket();
		assertEquals(3, lotteryService.getNumOfLines(1));
		lotteryService.amendTicket(1, 3);
		assertEquals(6, lotteryService.getNumOfLines(1));
		assertEquals("Ticket id: 8 not found", lotteryService.amendTicket(8, 3));
		lotteryService.getStatus(1);
		assertEquals("Tickt already checked, can't be amended", lotteryService.amendTicket(1, 3));
	}
	
	@Test
	public void testGetStatus() {
		lotteryService.createTicket();
		lotteryService.testTicketSetUp(1);
		assertEquals("Unsorted: {Line 4=0, Line 3=1, Line 2=5, Line 1=10} Sorted: {Line 1=10, Line 2=5, Line 3=1, Line 4=0}", lotteryService.getStatus(1));
		assertEquals("Ticket id: 2 not found", lotteryService.getStatus(2));
		lotteryService.deleteAllTickets();
	}

}
