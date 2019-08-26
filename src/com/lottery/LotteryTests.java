package com.lottery;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.After;
import org.junit.jupiter.api.Test;

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
		tearDown();
	}
	
	@Test
	public void testClearingTickets() {
		lotteryService.deleteAllTickets();
		assertEquals( "No tickets created yet", lotteryService.getTicket(1));
	}
	
	@Test
	public void testListingTickets() {
		lotteryService.createTicket();
		lotteryService.createTicket();
		lotteryService.createTicket();
		assertEquals("1 2 3 ", lotteryService.listTickets());
		tearDown();
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
		tearDown();
	}
	
	@Test
	public void testAmendTicket(){
		lotteryService.createTicket();
		assertEquals(3, lotteryService.getNumOfLines(1));
		lotteryService.amendTicket(1, 3);
		assertEquals(6, lotteryService.getNumOfLines(1));
		tearDown();
	}
	
	@Test
	public void testGetStatus() {
		lotteryService.createTicket();
		lotteryService.testTicketSetUp(1);
		assertEquals("Unsorted: {Line 4=0, Line 3=1, Line 2=5, Line 1=10} Sorted: {Line 1=10, Line 2=5, Line 3=1, Line 4=0}", lotteryService.getStatus(1));
		tearDown();
	}

}
