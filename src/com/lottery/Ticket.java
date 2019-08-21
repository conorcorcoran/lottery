package com.lottery;
import java.util.ArrayList;


public class Ticket {
	private ArrayList<int[]> lines = new ArrayList<int[]>();
	private boolean checked;

	public Ticket(){
		this.checked = false;
		int numOfLines = 3;
		
		while(numOfLines > 0) {
			int[] line = new int[3];
			for(int i = 0; i < 3;i++) {
				line[i] = getRandNum();
			}
			lines.add(line);
			numOfLines--;
		}
	}

	private static int getRandNum(){
		return (int)(Math.random() * ((2 - 0) + 1)); 
	}
	
	public ArrayList<int[]> getLines(){
		return lines;
	}

	public String printLines(){

		for(int i = 0; i < lines.size(); i++) {
			int[] tempArray = new int[3];
			String str = "";
			tempArray = lines.get(i);
			for(int j = 0; j < 3; j++) {
				str += tempArray[j] + " ";
			}
			System.out.println(str);
		}
		return "FILL OUT";
	}
}