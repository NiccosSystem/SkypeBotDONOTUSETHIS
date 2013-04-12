package uk.niccossystem.skypebot.functions;

import java.util.HashMap;

import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.SkypeException;
import com.skype.User;

public class AdminFunctions extends FunctionsClass {
	
	public static HashMap<Chat, String> chatTopics = new HashMap<Chat, String>();

	public void clearBotMessages(Chat chat, User user) {		
		try {
			ChatMessage[] messages = chat.getAllChatMessages();	
			
			for (ChatMessage m : messages) {			
				if (m.getContent().startsWith("[SkypeBot]") && m.getSender().equals(user)) {
					m.setContent("");
				}			
			}
		} catch (SkypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
}
	//Locks a topic. (If users topic spam)
	public void lockTopic(Chat chat) {
		if (!chatTopics.containsKey(chat)) {
			try {
				chatTopics.put(chat, chat.getWindowTitle());
				chat(chat, "Topic locked.");
			} catch (SkypeException e) {
				// TODO Auto-generated catch block
				chat(chat, "Topic lock failed.");
				e.printStackTrace();
			}
			
		} else {
			chat(chat, "Topic is already locked.");
		}
	}
			
	//Remove topic lock.
	public void removeTopicLock(Chat chat) {
		if (chatTopics.containsKey(chat)) {
			chatTopics.remove(chat);
			chat(chat, "Topic-lock removed.");
		} else {
			chat(chat, "Topic hasn't been locked.");
		}
	}
}