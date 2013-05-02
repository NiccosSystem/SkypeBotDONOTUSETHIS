package uk.niccossystem.skypebot.functions;

import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.SkypeException;
import com.skype.User;

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
	
	public void clearXChatMessages(ChatMessage m) {
		
		try {
			if (m.getContent().split(" ").length == 2) {
				
				Chat chat = m.getChat();
				ChatMessage[] messages = chat.getAllChatMessages();
				int count = Integer.valueOf(m.getContent().split(" ")[1]);
				int messageCount = messages.length - 1;
				
				for (int i = messageCount; i > messageCount - count; i--) {
					messages[i].setContent("");
				}
				
			}
		} catch (SkypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void icelandify(Chat chat, String[] params, String user) {
		String response = "";
		for (String s : params) {
			response += s + "ur ";
		}
		chat(chat, user + " meant: " + response);
	}
	
	public void derpify(Chat chat, String[] params, String user) {
		String response = "";
		for (String s : params) {
			response += s + " ";
		}
		response = response.replaceAll("l", "1");
		response = response.replaceAll("L", "1");
		
		response = response.replaceAll("e", "3");
		response = response.replaceAll("E", "3");
		
		response = response.replaceAll("a", "4");
		response = response.replaceAll("A", "4");
		
		response = response.replaceAll("g", "9");
		response = response.replaceAll("G", "9");
		
		response = response.replaceAll("b", "6");
		response = response.replaceAll("B", "6");
		
		response = response.replaceAll("s", "5");
		response = response.replaceAll("S", "5");
		
		response = response.replaceAll("t", "7");
		response = response.replaceAll("T", "7");
		
		chat(chat, response);
	}

	
	String[] cmds = {"help - Displays this message", 
			"getnblban - Gets a ban record from the Nicco Ban List database",
			"reddit - Creates a link to the specified subreddit", 
			"lastmsg - Displays your latest message", 
			"icelandify - Translates the specified text to Icelandic",
			"derpify - Translates the specified text into Derpian"};
	public void help(Chat chat) {
		String commandList = "";
		
		for (String s : cmds) {
			commandList = commandList + s + "\n";
		}
		
		chat(chat, "Commands:\n" + commandList);		
	}
}