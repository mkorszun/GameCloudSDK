package com.gamecloud.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gamecloud.exception.GameCloudException;
import com.gamecloud.http.response.Response;

public class Utils {

	public static final String ADDRESS="http://54.245.119.57:8080";
	
	public static void check(Response res) throws JSONException, GameCloudException{
		if(res.getStatusCode() != 200){
			String msg = new JSONObject(res.getMessage()).getString("error");
			throw new GameCloudException(msg);
		}
	}
	
	public static String checkReturnString(Response res) throws JSONException, GameCloudException{
		if(res.getStatusCode() != 200){
			String msg = new JSONObject(res.getMessage()).getString("error");
			throw new GameCloudException(msg);
		}else{
			return new JSONObject(res.getMessage()).getString("ok");
		}
	}
	
	public static JSONObject checkReturnJSONObject(Response res) throws JSONException, GameCloudException {
		if(res.getStatusCode() != 200){
			String msg = new JSONObject(res.getMessage()).getString("error");
			throw new GameCloudException(msg);
		}else{
			return new JSONObject(res.getMessage()).getJSONObject("ok");
		}
	}
	
	public static JSONArray checkReturnJSONArray(Response res) throws JSONException, GameCloudException{
		if(res.getStatusCode() != 200){
			String msg = new JSONObject(res.getMessage()).getString("error");
			throw new GameCloudException(msg);
		}else{
			System.out.println(new JSONObject(res.getMessage()));
			return new JSONObject(res.getMessage()).getJSONArray("ok");
		}
	}
}