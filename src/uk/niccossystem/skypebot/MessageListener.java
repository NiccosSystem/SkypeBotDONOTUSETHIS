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
	
	public static List<ChatMessage> receivedMessages = new ArrayList<ChatMessage>();
	private AdminFunctions adminFunctions = new AdminFunctions();
	private MiscFunctions miscFunctions = new MiscFunctions();
	private UHCFunctions uhcFunctions = new UHCFunctions();
	private YoutubeFunctions youtubeFunctions = new YoutubeFunctions();
	
	
	
	public MessageListener() {
	}
	
	@Override
	public void chatMessageReceived(ChatMessage arg0) throws SkypeException, NullPointerException {
		
		if (receivedMessages.contains(arg0)) return;
		receivedMessages.add(arg0);	
		
		try {
			doCommands(arg0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void chatMessageSent(ChatMessage arg0) throws SkypeException {		
		try {
			doCommands(arg0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public void doCommands(ChatMessage message) throws SkypeException, IOException {
		String command = null;
		Chat chat = message.getChat();
		String user = message.getSenderDisplayName();
		
		youtubeFunctions.checkForVideo(chat, message.getContent());
		
		if (message.getContent().startsWith("]")) command = message.getContent().substring(1).split(" ")[0];
		if (command == null) return;
		
		String[] stripped = message.getContent().replace("]" + command + " ", "").split(" ");		
		
		switch (command) {
		case "lastmsg":
			miscFunctions.lastMessage(message);
			break;
		case "getnblban":
			uhcFunctions.getBanned(message);
			break;
		case "match":
			uhcFunctions.getMatch(chat);
			break;
		case "setmatch":
			uhcFunctions.setMatch(chat, stripped);
			break;
		case "reddit":
			miscFunctions.convertToSubReddit(message);
			break;
		case "icelandify":
			miscFunctions.icelandify(chat, stripped, user);
			break;
		case "derpify":
			miscFunctions.derpify(chat, stripped, user);
			break;
		case "help":
			miscFunctions.help(chat);
			break;
		default:
			miscFunctions.chat(chat, "No such command! Type ]help for a list of commands!");
			break;
		}
		
		
		
		
		
	}
}