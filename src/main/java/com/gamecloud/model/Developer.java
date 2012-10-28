package com.gamecloud.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;

import com.gamecloud.exception.GameCloudException;
import com.gamecloud.http.property.Property;
import com.gamecloud.http.request.DeleteRequest;
import com.gamecloud.http.request.PostRequest;
import com.gamecloud.http.request.RequestException;
import com.gamecloud.utils.Utils;

public class Developer {

	private String id;
	private String password;
	private String email;
	
	public Developer(String id, String password, String email) {
		this.id = id;
		this.password = password;
		this.email = email;
	}
		
	public void saveToCloud() throws GameCloudException, JSONException{
		List<Property> parameters = Arrays.asList(new Property[]{
				new Property("developer_id", id),
				new Property("password", password),
				new Property("email", email)
		});
		try{
			URL url = new URL(Utils.ADDRESS+"/register_developer");
			PostRequest request = new PostRequest();
			Utils.check(request.request(url, parameters));
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
			PostRequest request = new PostRequest();
			return Utils.checkReturnString(request.request(url, properties));
		} catch (RequestException e) {
			throw new GameCloudException("Request failed: "+e.getMessage());
		} catch (IOException e) {
			throw new GameCloudException("Request failed: "+e.getMessage()); 
		}
	}
	
	public void deleteGame(String gameKey) throws GameCloudException, JSONException, URISyntaxException{
		List<Property> properties = Arrays.asList(new Property[]{
				new Property("developer_id", this.id),
				new Property("password", this.password),
				new Property("game_uuid", gameKey)
		});
		try{
			URL url = new URL(Utils.ADDRESS+"/delete_game");
			DeleteRequest request = new DeleteRequest();
			Utils.check(request.request(url, properties));
		} catch (RequestException e) {
			throw new GameCloudException("Request failed: "+e.getMessage());
		} catch (IOException e) {
			throw new GameCloudException("Request failed: "+e.getMessage()); 
		}
	}
	
	public String getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}
}