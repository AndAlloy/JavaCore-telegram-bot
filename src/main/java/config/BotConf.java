package config;

import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class BotConf {
    public static synchronized SendMessage constructMessage(String chat_id, String text){
        return SendMessage // Create a message object object
                .builder()
                .chatId(chat_id)
                .parseMode("Markdown")
                .text(text)
                .build(); // Sending our message object to user
    }
}
