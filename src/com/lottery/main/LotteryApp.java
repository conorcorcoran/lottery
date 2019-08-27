package com.lottery.main;

import javax.ws.rs.*;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class LotteryApp extends ResourceConfig {
	public LotteryApp(){
		packages("com.lottery.main");
	}
	
	public static void main(String args[]) {
		
	}
}
