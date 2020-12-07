package config;

import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import service.impl.WeatherServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class WeatherConf {
    public static synchronized SendMessage sendWeatherByName(String chat_id, String message_text){
        var service = new WeatherServiceImpl();
        var data = service.getByCityName(message_text);
        var cityName = data[0];
        if (!cityName.equals("404")) {
            var temp = data[1];
            var clouds = data[2];
            var wind = data[3];
            var direction = data[4];
            var o1 = "🌤️ *Погода у місті:* " + cityName + "\n\uD83C\uDF21️ *Середня температура:* _" + temp + " °C_ \n\uD83C\uDF43 *Хмарність:* _" + clouds + " %_\n";
            var o2 = "\uD83C\uDF43 *Вітер:* _" + wind+" м/с,_ *Напрям:* _" + direction + "_\n ";
            return SendMessage // Create a message object object
                    .builder()
                    .chatId(chat_id)
                    .parseMode("Markdown")
                    .text(o1+o2)
                    .build(); // Sending our message object to user
        }else{

            return SendMessage // Create a message object object
                    .builder()
                    .chatId(chat_id)
                    .parseMode("Markdown")
                    .text("Введено неправильне місто")
                    .build();
        }
    }
    //@SneakyThrows
    public static synchronized SendMessage sendWeatherByGeo(String chat_id, Double longitude, Double latitude){
        var service = new WeatherServiceImpl();
        var data = service.getByGeolocation(longitude, latitude);
        var cityName = data[0];
        if (!cityName.equals("404")) {
            var temp = data[1];
            var clouds = data[2];
            var wind = data[3];
            var direction = data[4];
            var o1 = "🌤️ *Погода у місті:* " + cityName + "\n\uD83C\uDF21️ *Середня температура:* _" + temp + " °C_ \n\uD83C\uDF43 *Хмарність:* _" + clouds + " %_\n";
            var o2 = "\uD83C\uDF43 *Вітер:* _" + wind+" м/с,_ *Напрям:* _" + direction + "_\n ";

            return SendMessage // Create a message object object
                    .builder()
                    .chatId(chat_id)
                    .parseMode("Markdown")
                    .text(o1+o2)
                    .build(); // Sending our message object to user
        }else{

            return SendMessage // Create a message object object
                    .builder()
                    .chatId(chat_id)
                    .parseMode("Markdown")
                    .text("Введено неправильне місто")
                    .build();
        }
    }
    public static synchronized void setGeoButton(SendMessage sendMessage) {
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
        KeyboardButton key1 = new KeyboardButton("Поділитися місцезнаходженням");
        KeyboardButton key2 = new KeyboardButton("Ввести місто");
        key1.getRequestLocation();
        keyboardFirstRow.add(key1);
        keyboardFirstRow2.add(key2);
        // Add all of the keyboard rows to the list
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardFirstRow2);

        // and assign this list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
    public String getWindDirection() {
        return "h";
    }
}
