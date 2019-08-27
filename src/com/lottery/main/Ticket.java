package com.lottery.main;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Ticket class
 * This class is used for creating the tickets as well as checking the 
 * results, amending tickets and other general methods
 */

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

	/**
	 * Generates a random number between 0 - 2.
	 * @return A random number between 0 and 2.
	 */
	public int getRandNum(){
		return (int)(Math.random() * ((2 - 0) + 1)); 
	}

	/**
	 * Creates a JSON Object containing the name of a the line
	 * and an array of numbers in the line.
	 * @return JSONObject with the Line index and an array of numbers.
	 */
	public JSONObject getLines(){
		checked = true;
		JSONObject jsonobj = new JSONObject();
		for(int i = 0; i < lines.size(); i++) {
			int[] tempArray = new int[3];
			JSONArray lineArr = new JSONArray();
			tempArray = lines.get(i);
			for(int j = 0; j < 3; j++) {
				lineArr.put(tempArray[j]);
			}
			try {
				jsonobj.put("Line " + (i + 1), lineArr);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return jsonobj;
	}

	/**
	 * This method is used in the amendLines method in the LotteryServices
	 * @param numOfLines The number of lines to be added to the ticket
	 */
	public void addLines(int numOfLines) {
		while(numOfLines > 0) {
			int[] line = new int[3];
			for(int i = 0; i < 3;i++) {
				line[i] = getRandNum();
			}
			lines.add(line);
			numOfLines--;
		}
	}

	/**
	 * Used to calcuate the results of each line in a ticket
	 * @param line A JSONArray repersenting a single line in a ticket
	 * @return The result of the 3 numbers contained in the line
	 */
	public int getResult(JSONArray line) {
		int result = 0;
		int total = 0;
		try {
			total = line.getInt(0) + line.getInt(1) + line.getInt(2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(total != 2) {
			try {
				if(line.getInt(0) == line.getInt(1) && line.getInt(1) == line.getInt(2)) {
					result = 5;
				}else if(line.getInt(0) != line.getInt(1) && line.getInt(0) != line.getInt(2)) {
					result = 1;
				}
				else {
					result = 0;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else {
			result = 10;
		}
		return result;
	}
	
	/**
	 * Gets the number of lines a ticket has.
	 * @return Number of lines of a ticket
	 */
	public int getNumberOfLines() {
		return lines.size();
	}
	
	/**
	 * Checks to see if a tickets status has been check already.
	 * If so, the ticket can not be amended.
	 * @return Boolean of the checked status
	 */
	public boolean getCheckedStatus() {
		return checked;
	}
	
	/**
	 * Preset lines with known results or test purposes.
	 */
	public void presetLines() {
		lines.clear();
		//Result = 10
		int[] line1 = {1,1,0};
		//result = 5
		int[] line2 = {2,2,2}; 
		//result = 1
		int[] line3 = {1,0,2};
		//result = 0
		int[] line4 = {0,1,0};
		lines.add(line1);
		lines.add(line2);
		lines.add(line3);
		lines.add(line4);
	}
	
}