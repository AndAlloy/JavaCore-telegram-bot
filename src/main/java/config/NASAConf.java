package config;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import service.impl.NasaImpl;

import java.util.ArrayList;
import java.util.List;

public class NASAConf {
    public static synchronized SendMessage sendTodayInfo(String chat_id){
        var service = new NasaImpl();
        var data = service.getTodayNasaInfo();
        var expl = data[0];
        if (!expl.equals("404")) {
            var url = data[1];
            var o1 = "\uD83E\uDDE7 *Interesting story today:*\n-" + expl;

            return SendMessage // Create a message object object
                    .builder()
                    .chatId(chat_id)
                    .parseMode("Markdown")
                    .text(o1+"[.]("+url+")")
                    .build(); // Sending our message object to user
        }else{

            return SendMessage // Create a message object object
                    .builder()
                    .chatId(chat_id)
                    .parseMode("Markdown")
                    .text("Error on getting info")
                    .build();
        }
    }
    public static synchronized String[] sendArticle() {
        var service = new NasaImpl();
        var data = service.getArticleNasaInfo();
        var title = data[0]; // - title
        if (!title.equals("404")) {
            var desc = data[1];
            var benefits = data[2];
            //var o1 = "\uD83E\uDDE7 *Interesting story today:*\n\n-" + title;
            //String replacedTitle =title.replace('p','\n');
            return new String[]{title, desc, benefits};
        } else {
            return new String[]{"null","null","null"};
        }

    }
    public static synchronized void setNasaButton(SendMessage sendMessage) {
        // Create a keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        // Create a list of keyboard rows
        List<KeyboardRow> keyboard = new ArrayList<>();
        // First keyboard row
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardFirstRow2 = new KeyboardRow();
        // Add buttons to the keyboard row
        KeyboardButton key1 = new KeyboardButton("Наукова стаття");
        KeyboardButton key2 = new KeyboardButton("День в історії");
        key1.getRequestLocation();
        keyboardFirstRow.add(key1);
        keyboardFirstRow2.add(key2);
        // Add all of the keyboard rows to the list
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardFirstRow2);
        // and assign this list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}
