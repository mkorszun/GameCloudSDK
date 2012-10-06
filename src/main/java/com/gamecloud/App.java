package com.gamecloud;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;

import com.gamecloud.exception.GameCloudException;
import com.gamecloud.model.Developer;
import com.gamecloud.model.Player;

import main.java.com.http.property.ByteArrayProperty;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws GameCloudException, JSONException, FileNotFoundException, IOException
    {
        Developer dev = new Developer("Mateusz1", "berlin", "a@b");
        try{
        	dev.saveToCloud();
        }catch(GameCloudException e){
        	System.out.println(e.getMessage());
        }
        String key = dev.createGame("WarCraft", "sdsadasd");
        Player player = new Player("Player1", "Pass1", key);
        player.saveToCloud();
        
		List<ByteArrayProperty> files = Arrays.asList(new ByteArrayProperty[]{
				new ByteArrayProperty(new FileInputStream("test/file1.txt"), "application/octet-stream", "file1.txt"),
			new ByteArrayProperty(new FileInputStream("test/file2.bin"), "application/octet-stream", "file2.bin")
		});
		
		String saveKey = player.saveGame("Save12345", files);
		System.out.println(player.readGame(saveKey));
    }
}
