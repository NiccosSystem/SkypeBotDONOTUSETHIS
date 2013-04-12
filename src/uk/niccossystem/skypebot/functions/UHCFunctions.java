package uk.niccossystem.skypebot.functions;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.SkypeException;

public class UHCFunctions extends FunctionsClass {
	
	String uhcBansUrl = "http://192.168.10.108/uhcbans/getbanned.php";
	String currentMatch = null;

	//For the /r/ultrahardcore Universal Ban List
	public void getBanned(ChatMessage message) throws IOException {
		try {
			if (message.getContent().split(" ").length == 2) {
				String userCheck = uhcBansUrl + "?user=" + message.getContent().split(" ")[1];
				Document doc = Jsoup.connect(userCheck).get();
				String banned = doc.select("p[class=isbanned]").text();
			
				if (banned != null) {
					if (banned.equalsIgnoreCase("1")) {
						chat(message.getChat(), "Player is on the NBL (Nicco Ban List). \n" +
						"Player: " + doc.select("p[class=user]").text() + "\n" +
						"Banned: " + (doc.select("p[class=isbanned]").text().equalsIgnoreCase("1") ? "Yes" : "No") + "\n" +
						"Reason: " + doc.select("p[class=reason]").text() + "\n" +
						"Length: " + doc.select("p[class=length]").text() + "\n" +
						"Date of ban: " + doc.select("p[class=date]").text());
						return;
					}
				}
				chat(message.getChat(), "Player is not on the NBL (Nicco Ban List).");
			}
		} catch (SkypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getMatch(Chat chat) {
		chat(chat, "Current Match: " + currentMatch);
	}	
	public void setMatch(Chat chat, String[] message) {
		if (message.length < 2) {
			chat(chat, "Not enough arguments!");
			return;
		}
		currentMatch = message.toString().replaceFirst("\\.setmatch ", "");
		chat(chat, "Current match set.");
	}
}
