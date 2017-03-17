package movie_list_helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SearchLogger {
	
	SearchLogger(){
		
	}
	public static void log(String text, String path){
		try
		{
		    Files.write(Paths.get(path), text.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) 
		{
	    		System.out.println("Exception Occurred:");
		        e.printStackTrace();
		}
	}
}
