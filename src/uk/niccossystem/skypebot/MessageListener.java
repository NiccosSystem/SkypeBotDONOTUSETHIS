package uk.niccossystem.skypebot;

import java.io.IOException;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.skype.*;

public class MessageListener implements ChatMessageListener {
	
	
	private static final String uhcBansUrl = "http://192.168.10.108/uhcbans/getbanned.php";
	HashMap<Chat, String> chatTopics = new HashMap<Chat, String>();
//	List<ChatMessage> botMessages = Wappa.getChatMessageArray();
	
	
	public MessageListener() {
	}
	
	@Override
	public void chatMessageReceived(ChatMessage arg0) throws SkypeException {
		// TODO Auto-generated method stub	
		doCommands(arg0);
		
		if (chatTopics.containsKey(arg0.getChat())) {
			if (!arg0.getChat().getWindowTitle().equals(chatTopics.get(arg0.getChat()))) {
//				botChat(arg0.getChat(), arg0.getChat().getWindowTitle());
//				botChat(arg0.getChat(), chatTopics.get(arg0.getChat()));
				arg0.getChat().setTopic(chatTopics.get(arg0.getChat()));
				botChat(arg0.getChat(), "Topic is locked!");				
			}
		}
	}

	@Override
	public void chatMessageSent(ChatMessage arg0) throws SkypeException {
		// TODO Auto-generated method stub
		doCommands(arg0);		
		
		
		
		if (arg0.getContent().split(" ")[0].equalsIgnoreCase(".locktopic")) {
			if (!chatTopics.containsKey(arg0.getChat())) {
				chatTopics.put(arg0.getChat(), arg0.getChat().getWindowTitle());
				botChat(arg0.getChat(), "Topic locked.");
			} else {
				botChat(arg0.getChat(), "Topic is already locked.");
			}
		}
		if (arg0.getContent().split(" ")[0].equalsIgnoreCase(".ignoretopic")) {
			if (chatTopics.containsKey(arg0.getChat())) {
				chatTopics.remove(arg0.getChat());
				botChat(arg0.getChat(), "Topic-lock removed.");
			} else {
				botChat(arg0.getChat(), "Topic hasn't been locked.");
			}
		}
		if (arg0.getContent().split(" ")[0].equalsIgnoreCase(".clearmsgs")) {
			clearMessages(arg0.getChat());
		}
	}
	
	private void doCommands(ChatMessage arg0) throws SkypeException {
		String[] messageArray = arg0.getContent().split(" ");
		
		if (messageArray[0].equalsIgnoreCase(".getbanned") && messageArray.length >= 2) {
			try {
				botChat(arg0.getChat(), getBanned(messageArray[1]));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
		if (messageArray[0].equalsIgnoreCase(".add") && messageArray.length >= 3) {
			
			Chat chat = arg0.getChat();
			double result = 0;
			
			for (int i = 1; i <= messageArray.length - 1; i++) {
				try {
				result = result + Double.valueOf(messageArray[i]);
				} catch (NumberFormatException e) {
					botChat(chat, arg0.getSenderDisplayName() + ": Please do only use numbers.");
					
				}
			}
			if (result == 69.0) {
			botChat(chat, arg0.getSenderDisplayName() + ": " + String.valueOf(result) + " ;)");
			
			return;
			} else {
			botChat(chat, arg0.getSenderDisplayName() + ": " + String.valueOf(result));
			
			return;
			}
		} else if (messageArray[0].equalsIgnoreCase(".x") && messageArray.length >= 3) {
			
			Chat chat = arg0.getChat();
			double result = 0;
			
			for (int i = 1; i <= messageArray.length - 1; i++) {
				if (i == 1) {
					try {
					result = Double.valueOf(messageArray[1]);
				} catch (NumberFormatException e) {
					botChat(chat, arg0.getSenderDisplayName() + ": Please do only use numbers.");
					
					return;
				}
				} else {
					try {
					result = result * Double.valueOf(messageArray[i]);
					} catch (NumberFormatException e) {
						botChat(chat, arg0.getSenderDisplayName() + ": Please do only use numbers.");
						
						return;
					}
				}
			}
			if (result == 69.0) {
				botChat(chat, arg0.getSenderDisplayName() + ": " + String.valueOf(result) + " ;)");
				
				return;
			} else {
				botChat(chat, arg0.getSenderDisplayName() + ": " + String.valueOf(result));
				
				return;
			}

		}
		
		if (arg0.getContent().contains("youtube.com/watch")) {
			String[] message = arg0.getContent().split(" ");
			String title = null;
			Chat chat = arg0.getChat();
			for (String s : message) {
				if (s.contains("youtube.com/watch")) {
					try {
						title = getTitle(s);
						
					} catch (IOException e) {
						title = null;
						e.printStackTrace();
					}
				}				
			}
			if (title != null) {
				botChat(chat, title);
				
				return;
			}
			botChat(chat, "Title not found");
			
		}
		if (messageArray[0].equalsIgnoreCase(".lastmsg")) {
			Chat chat = arg0.getChat();
			ChatMessage[] allMsg = arg0.getSender().getAllChatMessages();
			botChat(chat, arg0.getSenderDisplayName() + " said at " + allMsg[allMsg.length - 2].getTime() + ": \n" + allMsg[allMsg.length - 2].getContent());
		}
		
		String[] slashMessage = arg0.getContent().split("/");
		if (slashMessage[0].equalsIgnoreCase(".s") && slashMessage.length == 3) {
			Chat chat = arg0.getChat();
			ChatMessage[] allMsg = arg0.getSender().getAllChatMessages();
			String msg = allMsg[allMsg.length - 2].getContent();
			msg = msg.replaceAll(slashMessage[1], slashMessage[2]);
			
			botChat(chat, arg0.getSenderDisplayName() + " meant: " + msg);
			
		}
		if (messageArray[0].equalsIgnoreCase(".pi")) {
			Chat chat = arg0.getChat();
			String pi = "3.14159265358979323846264338327950288419716939937510582" +
					"097494459230781640628620899862803482534211706798214808651328230664709384" +
					"4609550582231725359408128481117450284102701938521105559644622948954930381964428810" +
					"97566593344612847564823378678316527120190914564856692346034861045432664821339360726024914127" +
					"3724587006606315588174881520920962829254091715364367892590360011330530548820466521384146951941511609...";
			botChat(chat, arg0.getSenderDisplayName() + ": " + pi);
			
		}
	}
	
 	private String getTitle(String url) throws IOException {
 		if (!url.startsWith("http://") && !url.startsWith("https://")) {
 			url = "http://" + url;
 		}
		Document doc = Jsoup.connect(url).get();
		String title = doc.head().select("meta[property=og:title]").attr("content");
		String combined = "YouTube video: " + title;
		return combined;
	}
 	
 	public String getBanned(String user) throws IOException {
		String userCheck = uhcBansUrl + "?user=" + user;		
		Document doc = Jsoup.connect(userCheck).get();			
		String banned = doc.select("p[class=isbanned]").text();		
		
		if (banned != null) {
			if (banned.equalsIgnoreCase("1")) {			
				return "Player is on the UBL. \n" +
				"Player: " + doc.select("p[class=user]").text() + "\n" +
				"Banned: " + (doc.select("p[class=isbanned]").text().equalsIgnoreCase("1") ? "Yes" : "No") + "\n" +
				"Reason: " + doc.select("p[class=reason]").text() + "\n" +
				"Length: " + doc.select("p[class=length]").text() + "\n" +
				"Date of ban: " + doc.select("p[class=date]").text();
			}
		}
		return "Player is not on the UBL.";
 		
 		
 	}

	private void botChat(Chat chat, String message) throws SkypeException {
		
		ChatMessage[] recent = chat.getRecentChatMessages();
		
		for (int i = recent.length - 1; i >= 0; i--) {
			
			if (recent[i].getContent().equals("[SkypeBot] " + message)) return;
			
		}
		
		chat.send("[SkypeBot] " + message);		
	}
	
	private void clearMessages(Chat chat) throws SkypeException {		
		ChatMessage[] recent = chat.getRecentChatMessages();		
		for (ChatMessage m : recent) {			
			if (m.getContent().startsWith("[SkypeBot]")) {
				m.setContent("");
			}			
		}		
	}	
}