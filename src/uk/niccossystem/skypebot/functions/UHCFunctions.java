package uk.niccossystem.skypebot.functions;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.skype.Chat;

public class UHCFunctions implements FunctionsClass {
	
	String uhcBansUrl = "http://79.160.84.111/uhcbans/getbanned.php";
	String currentMatch = null;

	//For the /r/ultrahardcore Universal Ban List
	public void getBanned(Chat chat, String[] message) throws IOException {
		if (message.length == 1) {
			String userCheck = uhcBansUrl + "?user=" + message[0];
			Document doc = Jsoup.connect(userCheck).get();
			String banned = doc.select("p[class=isbanned]").text();
		
			if (banned != null) {
				if (banned.equalsIgnoreCase("1")) {
					bot.chat(chat, "Player is on the UBL. \n" +
							"Player: " + doc.select("p[class=user]").text() + "\n" +
					"Banned: " + (doc.select("p[class=isbanned]").text().equalsIgnoreCase("1") ? "Yes" : "No") + "\n" +
					"Reason: " + doc.select("p[class=reason]").text() + "\n" +
					"Length: " + doc.select("p[class=length]").text() + "\n" +
					"Date of ban: " + doc.select("p[class=date]").text());
				}
			}
			bot.chat(chat, "Player is not on the UBL.");
		}
	}

	public void getMatch(Chat chat) {
		bot.chat(chat, "Current Match: " + currentMatch);
	}	
	public void setMatch(Chat chat, String[] message) {
		if (message.length < 2) {
			bot.chat(chat, "Not enough arguments!");
			return;
		}
		currentMatch = message.toString().replaceFirst("!*setmatch ", "");
		bot.chat(chat, "Current match set.");
	}
}
