package com.gamecloud.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gamecloud.exception.GameCloudException;
import com.gamecloud.http.property.ByteArrayProperty;
import com.gamecloud.http.property.Property;
import com.gamecloud.http.request.DeleteRequest;
import com.gamecloud.http.request.GetRequest;
import com.gamecloud.http.request.MultiPartPostRequest;
import com.gamecloud.http.request.PostRequest;
import com.gamecloud.http.request.RequestException;
import com.gamecloud.http.response.Response;
import com.gamecloud.utils.Utils;

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
	
	public void saveToCloud(Developer dev) throws GameCloudException, JSONException{
		List<Property> properties = Arrays.asList(new Property[]{
				new Property("developer_id", dev.getId()),
				new Property("dev_password", dev.getPassword()),
				new Property("player_id", this.name),
				new Property("password", this.pass),
				new Property("game_uuid", this.gameUUID)
		});
		try{
			PostRequest request = new PostRequest();
			URL url = new URL(Utils.ADDRESS+"/register_player");
			this.uuid = Utils.checkReturnString(request.request(url, properties));
		} catch (RequestException e) {
			throw new GameCloudException("Request failed: "+e.getMessage());
		} catch (IOException e) {
			throw new GameCloudException("Request failed: "+e.getMessage()); 
		}
	}
	
	public void delete() throws GameCloudException, JSONException, URISyntaxException{
		List<Property> properties = Arrays.asList(new Property[]{
				new Property("player_uuid", this.uuid),
				new Property("password", this.pass)
		});
		try{
			DeleteRequest request = new DeleteRequest();
			URL url = new URL(Utils.ADDRESS+"/delete_player");
			Utils.check(request.request(url, properties));
		} catch (RequestException e) {
			throw new GameCloudException("Request failed: "+e.getMessage());
		} catch (IOException e) {
			throw new GameCloudException("Request failed: "+e.getMessage()); 
		}
	}
	
	public String createSave(String name, List<ByteArrayProperty> contents) throws GameCloudException, JSONException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Property> properties = Arrays.asList(new Property[]{
				new Property("player_uuid", this.uuid),
				new Property("password", this.pass),
				new Property("save_name", name),
				new Property("date", df.format(Calendar.getInstance().getTime()))
		});
		try{
			URL url = new URL(Utils.ADDRESS+"/create_save");
			MultiPartPostRequest request = new MultiPartPostRequest();
			return Utils.checkReturnString(request.request(url, properties, contents));
		} catch (RequestException e) {
			throw new GameCloudException("Request failed: "+e.getMessage());
		} catch (IOException e) {
			throw new GameCloudException("Request failed: "+e.getMessage()); 
		}
	}
	
	public JSONObject readSave(String saveKey) throws GameCloudException, JSONException, URISyntaxException{
		List<Property> properties = Arrays.asList(new Property[]{
				new Property("player_uuid", this.uuid),
				new Property("password", this.pass),
				new Property("save_uuid", saveKey)
		});
		try{
			URL url = new URL(Utils.ADDRESS+"/read_save");
			GetRequest request = new GetRequest();
			Response res = request.request(url, properties);
			return Utils.checkReturnJSONObject(res);
		} catch (RequestException e) {
			throw new GameCloudException("Request failed: "+e.getMessage());
		} catch (IOException e) {
			throw new GameCloudException("Request failed: "+e.getMessage()); 
		}
	}
	
	public void deleteSave(String saveKey) throws GameCloudException, JSONException, URISyntaxException{
		List<Property> properties = Arrays.asList(new Property[]{
				new Property("player_uuid", this.uuid),
				new Property("password", this.pass),
				new Property("save_uuid", saveKey)
		});
		try{
			URL url = new URL(Utils.ADDRESS+"/delete_save");
			DeleteRequest request = new DeleteRequest();
			Utils.check(request.request(url, properties));
		} catch (RequestException e) {
			throw new GameCloudException("Request failed: "+e.getMessage());
		} catch (IOException e) {
			throw new GameCloudException("Request failed: "+e.getMessage()); 
		}
	}
	
	public JSONArray listSaves(String gameKey) throws GameCloudException, JSONException, URISyntaxException{
		List<Property> properties = Arrays.asList(new Property[]{
				new Property("player_uuid", this.uuid),
				new Property("password", this.pass)
		});
		try{
			URL url = new URL(Utils.ADDRESS+"/list_game_saves");
			GetRequest request = new GetRequest();
			Response res = request.request(url, properties);
			return Utils.checkReturnJSONArray(res);
		} catch (RequestException e) {
			throw new GameCloudException("Request failed: "+e.getMessage());
		} catch (IOException e) {
			throw new GameCloudException("Request failed: "+e.getMessage()); 
		}
	}
}