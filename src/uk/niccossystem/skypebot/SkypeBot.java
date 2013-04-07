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
			Skype.addChatMessageListener(new MessageListener());
		} catch (SkypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			Thread.sleep(20);
		}		
	}
}