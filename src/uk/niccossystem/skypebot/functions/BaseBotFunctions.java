package uk.niccossystem.skypebot.functions;

import com.skype.Chat;
import com.skype.SkypeException;

public class BaseBotFunctions {
	
	public void chat(Chat chat, String message) {		
		try {
			chat.send("[SkypeBot] " + message);
		} catch (SkypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
	
}
