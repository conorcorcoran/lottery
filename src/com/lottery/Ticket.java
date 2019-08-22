package com.lottery;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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
	
}