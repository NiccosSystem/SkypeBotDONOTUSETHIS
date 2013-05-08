package uk.niccossystem.skypebot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.skype.*;

public class SkypeBot {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SkypeException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, SkypeException, InterruptedException {		
		try {
			//Add the Skype listener.
			Skype.addChatMessageListener(new MessageListener());
			Skype.addChatMessageEditListener(new EditListener());
			
			File file = new File("./settings.properties");
			if (!file.exists()) {
				Properties props = new Properties();
				props.setProperty("admins", "");
				props.store(new FileOutputStream("settings.properties"), null);
			}
		} catch (SkypeException | IOException e) {
			e.printStackTrace();
		}
		while (true) {
			//Make the thread sleep a bit to avoid eating all resources.
			Thread.sleep(20);
		}		
	}
}