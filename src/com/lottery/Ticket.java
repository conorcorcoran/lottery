package com.lottery;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
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

	private static int getRandNum(){
		return (int)(Math.random() * ((2 - 0) + 1)); 
	}

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
	
	public int getNumberOfLines() {
		return lines.size();
	}
	
	public boolean getCheckedStatus() {
		return checked;
	}
	
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