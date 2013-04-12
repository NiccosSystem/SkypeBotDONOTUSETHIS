package uk.niccossystem.skypebot.functions;

import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.SkypeException;
import com.skype.User;

public class MiscFunctions implements FunctionsClass {
	
	//Topic lock check.
	public void topicLockCheck(Chat chat) {
		if (AdminFunctions.chatTopics.containsKey(chat)) {
			try {
				if (!chat.getWindowTitle().equals(AdminFunctions.chatTopics.get(chat))) {
					chat.setTopic(AdminFunctions.chatTopics.get(chat));
					bot.chat(chat, "Topic is locked!");	
					return;
				}
			} catch (SkypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//SkypeBot will display your last message (Fix this; it finds messages from all chat instead of the current one.)	
	public void lastMessage(Chat chat, User user) {
		try {
			ChatMessage[] allMsg = user.getAllChatMessages();
				for (int i = allMsg.length - 1; i >= 0; i--) {
						if (allMsg[i].getChat().equals(chat)) {
							bot.chat(chat, user.getDisplayName() + " said at " + allMsg[allMsg.length - 2].getTime() + ": \n" + allMsg[allMsg.length - 2].getContent());	
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
					
				bot.chat(chat, arg0.getSenderDisplayName() + " meant: " + msg);
					
			}
		} catch (SkypeException e) {
			e.printStackTrace();
		}
	}	
}