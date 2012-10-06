package com.gamecloud.model;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.gamecloud.exception.GameCloudException;
import com.gamecloud.utils.Utils;

import main.java.com.http.property.Property;
import main.java.com.http.request.Request;
import main.java.com.http.request.RequestException;
import main.java.com.http.response.Response;

public class Developer {

	private String id;
	private String password;
	private String email;
	
	public Developer(String id, String password, String email) 
	{
		this.id = id;
		this.password = password;
		this.email = email;
	}
	
	public void saveToCloud() throws GameCloudException, JSONException
	{
		
		List<Property> properties = Arrays.asList(new Property[]{
				new Property("developer_id", id),
				new Property("password", password),
				new Property("email", email)
		});
		
		try{
			URL url = new URL(Utils.ADDRESS+"/register_developer");
			Request request = new Request();
			Response res = request.request(url, properties);
			if(res.getStatusCode() != 200){
				String msg = new JSONObject(res.getMessage()).getString("res");
				throw new GameCloudException(msg);
			}
		} catch (RequestException e) {
			throw new GameCloudException("Request failed: "+e.getMessage());
		} catch (IOException e) {
			throw new GameCloudException("Request failed: "+e.getMessage()); 
		}
	}
	
	public String createGame(String gameName, String description) throws GameCloudException, JSONException{
		
		List<Property> properties = Arrays.asList(new Property[]{
				new Property("developer_id", this.id),
				new Property("password", this.password),
				new Property("game_id", gameName),
				new Property("description", description)
		});
		
		try{
			URL url = new URL(Utils.ADDRESS+"/register_game");
			Request request = new Request();
			Response res = request.request(url, properties);
			if(res.getStatusCode() != 200){
				String msg = new JSONObject(res.getMessage()).getString("res");
				throw new GameCloudException(msg);
			}else{
				return new JSONObject(res.getMessage()).getString("res");
			}
		} catch (RequestException e) {
			throw new GameCloudException("Request failed: "+e.getMessage());
		} catch (IOException e) {
			throw new GameCloudException("Request failed: "+e.getMessage()); 
		}
	}
}
