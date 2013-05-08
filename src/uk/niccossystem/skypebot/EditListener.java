package uk.niccossystem.skypebot;

import java.util.Date;

import uk.niccossystem.skypebot.functions.FunctionsClass;

import com.skype.ChatMessage;
import com.skype.ChatMessageEditListener;
import com.skype.SkypeException;
import com.skype.User;

public class EditListener extends FunctionsClass implements ChatMessageEditListener {

	@Override
	public void chatMessageEdited(ChatMessage arg0, Date arg1, User arg2) {
		
//		if (MessageListener.receivedMessages.contains(arg0)) {			
//			try {
//				String origMessage = MessageListener.receivedMessages.get(MessageListener.receivedMessages.indexOf(arg0)).getContent();
//				chat(arg0.getChat(), arg0.getSenderDisplayName() + "'s message at " + arg0.getTime().toString() + " originally said: \n"
//						+ origMessage);
//			} catch (SkypeException e) {
//				e.printStackTrace();
//			}
//		}
		
	}

}
