package com.gamecloud.model;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.gamecloud.exception.GameCloudException;
import com.gamecloud.utils.Utils;

import main.java.com.http.property.ByteArrayProperty;
import main.java.com.http.property.Property;
import main.java.com.http.request.MultiPartRequest;
import main.java.com.http.request.Request;
import main.java.com.http.request.RequestException;
import main.java.com.http.response.Response;

public class Player {

	private String name;
	private String uuid;
	private String pass;
	private String gameUUID;
	
	public Player(String name, String pass, String gameUUID) {
		this.name = name;
		this.pass = pass;
		this.gameUUID = gameUUID;
	}
	
	public void saveToCloud() throws GameCloudException, JSONException{
		List<Property> properties = Arrays.asList(new Property[]{
				new Property("player_id", this.name),
				new Property("password", this.pass),
				new Property("game_uuid", this.gameUUID)
		});
		try{
			Request request = new Request();
			Response res = request.request(new URL(Utils.ADDRESS+"/register_player"), properties);
			if(res.getStatusCode() != 200){
				String msg = new JSONObject(res.getMessage()).getString("res");
				throw new GameCloudException(msg);
			}else{
				this.uuid = new JSONObject(res.getMessage()).getString("res");
			}
		} catch (RequestException e) {
			throw new GameCloudException("Request failed: "+e.getMessage());
		} catch (IOException e) {
			throw new GameCloudException("Request failed: "+e.getMessage()); 
		}
	}
	
	public String saveGame(String name, List<ByteArrayProperty> contents) throws GameCloudException, JSONException{
		List<Property> properties = Arrays.asList(new Property[]{
				new Property("player_uuid", this.uuid),
				new Property("password", this.pass),
				new Property("game_uuid", this.gameUUID),
				new Property("save_name", name)
		});
		try{
			URL url = new URL(Utils.ADDRESS+"/create_save");
			MultiPartRequest request1 = new MultiPartRequest();
			Response res = request1.request(url, properties, contents);
			if(res.getStatusCode() != 200){
				String msg = new JSONObject(res.getMessage()).getString("res");
				throw new GameCloudException(msg);
			}else{
				return new JSONObject(res.getMessage()).getString("res");
			}
		}catch (RequestException e) {
			throw new GameCloudException("Request failed: "+e.getMessage());
		} catch (IOException e) {
			throw new GameCloudException("Request failed: "+e.getMessage()); 
		}
	}
	
	public JSONObject readGame(String key) throws GameCloudException, JSONException{
		List<Property> properties = Arrays.asList(new Property[]{
				new Property("player_uuid", this.uuid),
				new Property("password", this.pass),
				new Property("save_uuid", key)
		});
		try{
			URL url = new URL(Utils.ADDRESS+"/read_save");
			Request request1 = new Request();
			Response res = request1.request(url, properties);
			if(res.getStatusCode() != 200){
				String msg = new JSONObject(res.getMessage()).getString("res");
				throw new GameCloudException(msg);
			}else{
				return new JSONObject(res.getMessage()).getJSONObject("res");
			}
		}catch (RequestException e) {
			throw new GameCloudException("Request failed: "+e.getMessage());
		} catch (IOException e) {
			throw new GameCloudException("Request failed: "+e.getMessage()); 
		}
	}

}
