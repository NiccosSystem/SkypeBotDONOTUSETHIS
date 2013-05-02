package uk.niccossystem.skypebot.functions;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import uk.niccossystem.skypebot.BotUser;

import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.SkypeException;

public class FunctionsClass {
	
	public void chat(Chat chat, String message) {		
		try {
			chat.send(message);
		} catch (SkypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void callCommand(BotUser bUser, ChatMessage msg, String[] parameters) {
		for (Method m : this.getClass().getMethods()) {
			if (m.isAnnotationPresent(CommandAnnotation.class)) {
				CommandAnnotation a = m.getAnnotation(CommandAnnotation.class);
				if (bUser.getAccessLevel() < a.accessLevel()) {
					try {
						m.invoke(bUser, msg, parameters);
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
