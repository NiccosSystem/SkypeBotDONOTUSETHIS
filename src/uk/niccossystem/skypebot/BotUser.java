package uk.niccossystem.skypebot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.skype.ChatMessage;
import com.skype.SkypeException;

public class BotUser {
	private int accessLevel = 1;
	private String userName = null;
	private String userId = null;
	
	public BotUser (ChatMessage message) {
		try {
			accessLevel = getAccess(message);
			userName = message.getSenderDisplayName();
			userId = message.getSenderId();
		} catch (IOException | SkypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int getAccess(ChatMessage message) throws SkypeException, FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("./settings.properties"));
		String access = properties.getProperty(message.getSenderId());
			
		if (access != null) {
			return Integer.valueOf(access);
		}
	
		return 1;
	}
	
	public void setAccess(int a) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("./settings.properties"));
			properties.setProperty(userId, String.valueOf(a));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

}
