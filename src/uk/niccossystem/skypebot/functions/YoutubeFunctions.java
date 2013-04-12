package uk.niccossystem.skypebot.functions;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.skype.Chat;

public class YoutubeFunctions extends FunctionsClass {
	
	public void checkForVideo(Chat chat, String message) {
		if (message.contains("youtube.com/watch")) {
			String[] messageArgs = message.split(" ");
			String title = null;
			
			for (String s : messageArgs) {
				if (s.contains("youtube.com/watch")) {
						title = getTitle(s);					
				}				
			}			
			if (title != null) {
				chat(chat, title);				
				return;
			}			
			chat(chat, "Title not found");			
		}
	}

	//Method to find the title of a Youtube video. Possible rename to getYoutubeTitle.
 	private String getTitle(String url) {
 		if (!url.startsWith("http://") && !url.startsWith("https://")) {
 			url = "http://" + url;
 		}
		try {
			Document doc = Jsoup.connect(url).get();
			String title = doc.head().select("meta[property=og:title]").attr("content");
			String combined = "YouTube video: " + title;
			return combined;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
