package uk.niccossystem.skypebot;

import java.io.IOException;
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
		} catch (SkypeException e) {
			e.printStackTrace();
		}
		while (true) {
			//Make the thread sleep a bit to avoid eating all resources.
			Thread.sleep(20);
		}		
	}
}