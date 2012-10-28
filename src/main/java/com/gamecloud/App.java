package com.gamecloud;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;

import com.gamecloud.exception.GameCloudException;
import com.gamecloud.http.property.ByteArrayProperty;
import com.gamecloud.model.Developer;
import com.gamecloud.model.Player;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws GameCloudException, JSONException, FileNotFoundException, IOException, URISyntaxException
    {
        Developer dev = new Developer("Mateusz12", "berlin", "a@b");
        try{
        	dev.saveToCloud();
        }catch(GameCloudException e){
        	System.out.println(e.getMessage());
        }
        String key = dev.createGame("WarCraft", "sdsadasd"); 
        Player player = new Player("Player12", "Pass", key);
        player.saveToCloud(dev);
        
		List<ByteArrayProperty> files = Arrays.asList(new ByteArrayProperty[]{
			new ByteArrayProperty(new FileInputStream("test/file1.txt"), "application/octet-stream", "file1.txt"),
			new ByteArrayProperty(new FileInputStream("test/file2.bin"), "application/octet-stream", "file2.bin")
		});
		
		String saveKey1 = player.createSave("Save12345", files);
		String saveKey2 = player.createSave("Save6789", files);
		System.out.println(player.readSave(saveKey1));
		System.out.println(player.readSave(saveKey2));
		System.out.println(player.listSaves(key));
		
		player.deleteSave(saveKey1);
		player.deleteSave(saveKey2);
		player.delete();
		dev.deleteGame(key);
    }
}