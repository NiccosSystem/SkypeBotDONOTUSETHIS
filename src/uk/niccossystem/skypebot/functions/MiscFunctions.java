package uk.niccossystem.skypebot.functions;

import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.SkypeException;

public class MiscFunctions extends FunctionsClass {
	
	//Topic lock check.
	public void topicLockCheck(Chat chat) {
		if (AdminFunctions.chatTopics.containsKey(chat)) {
			try {
				if (!chat.getWindowTitle().equals(AdminFunctions.chatTopics.get(chat))) {
					chat.setTopic(AdminFunctions.chatTopics.get(chat));
					chat(chat, "Topic is locked!");	
					return;
				}
			} catch (SkypeException e) {
				e.printStackTrace();
			}
		}
	}
	
	//SkypeBot will display your last message (Fix this; it finds messages from all chat instead of the current one.)	
	public void lastMessage(ChatMessage msg) {
		try {
			ChatMessage[] allMsg = msg.getSender().getAllChatMessages();
				for (int i = allMsg.length - 1; i >= 0; i--) {
						if (allMsg[i].getChat().equals(msg.getChat())) {
							chat(msg.getChat(), msg.getSenderDisplayName() + " said at " + allMsg[allMsg.length - 2].getTime() + ": \n" + allMsg[allMsg.length - 2].getContent());	
							return;
						}
				}
		} catch (SkypeException e) {
			e.printStackTrace();
		}			
	}
			
	//Correct your last message
	public void correctMessage(ChatMessage arg0) {
		try {
			String[] slashMessage = arg0.getContent().split("/");
			if (slashMessage[0].equalsIgnoreCase(".s") && slashMessage.length == 3) {
				Chat chat = arg0.getChat();
				ChatMessage[] allMsg = arg0.getSender().getAllChatMessages();
				String msg = allMsg[allMsg.length - 2].getContent();
				msg = msg.replaceAll(slashMessage[1], slashMessage[2]);
					
				chat(chat, arg0.getSenderDisplayName() + " meant: " + msg);
					
			}
		} catch (SkypeException e) {
			e.printStackTrace();
		}
	}
	
	public void convertToSubReddit(ChatMessage message) {
		try {
			String[] messageArray = message.getContent().split(" ");
			
			if (messageArray.length == 2) {				
				chat(message.getChat(), "Subreddit \"" + messageArray[1] + "\": http://reddit.com/r/" + messageArray[1]);	
				return;					
			}		
		} catch (SkypeException e) {
			e.printStackTrace();
		}		
	}
}