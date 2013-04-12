package uk.niccossystem.skypebot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.niccossystem.skypebot.functions.AdminFunctions;
import uk.niccossystem.skypebot.functions.MiscFunctions;
import uk.niccossystem.skypebot.functions.UHCFunctions;
import uk.niccossystem.skypebot.functions.YoutubeFunctions;

import com.skype.*;

public class MessageListener implements ChatMessageListener {
	
	private List<ChatMessage> receivedMessages = new ArrayList<ChatMessage>();
	private AdminFunctions adminFunctions = new AdminFunctions();
	private MiscFunctions miscFunctions = new MiscFunctions();
	private UHCFunctions uhcFunctions = new UHCFunctions();
	private YoutubeFunctions youtubeFunctions = new YoutubeFunctions();
	
	public MessageListener() {
	}
	
	@Override
	public void chatMessageReceived(ChatMessage arg0) throws SkypeException, NullPointerException {
		
		if (receivedMessages.contains(arg0)) {
			return;
		}		
		receivedMessages.add(arg0);	
		
		try {
			doCommands(arg0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void chatMessageSent(ChatMessage arg0) throws SkypeException {		
		
		
		try {
			doCommands(arg0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public void doCommands(ChatMessage message) throws SkypeException, IOException {
		String command = null;
		Chat chat = message.getChat();
		User user = message.getSender();
		String[] messageArgs = message.getContent().split(" ");
//		String[] commandArgs = messageArgs.toString().substring(messageArgs[0].length() + 1).split(" ");
		
		youtubeFunctions.checkForVideo(chat, message.getContent());
		
		try {
			command = message.getContent().substring(2);
		} catch (SkypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		switch (command) {
		case "lastmsg":
			miscFunctions.lastMessage(chat, user);
			break;
		case "getublban":
			uhcFunctions.getBanned(chat, messageArgs);
			break;	
		case "getmatch":
			uhcFunctions.getMatch(chat);
			break;
		case "setmatch":
			uhcFunctions.setMatch(chat, messageArgs);
		}
		
		
		
		
		
	}
}