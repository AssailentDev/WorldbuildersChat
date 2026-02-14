package me.assailent.Utilities;

import com.hypixel.hytale.server.core.Message;
import fi.sulku.hytale.TinyMsg;

import java.util.Map;

public class Formatting {
    public String format(String message, Map<String, String> args) {
        String newstring = message;
        for (Map.Entry<String, String> entry : args.entrySet()) {
            newstring = newstring.replace(entry.getKey(), entry.getValue());
        }
        return newstring;
    }

    public Message message(String message) {
        try { // No tiny msg
            return TinyMsg.parse(message);
        } catch (Exception e) {
            return Message.raw(message);
        }
    }

    public String removeTinyMsg(String message) {
        return message.replaceAll("<[^>]+>", "");
    }
}
