package bot;

import config.AESConf;
import config.BotConf;
import config.NASAConf;
import config.WeatherConf;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;


public class Bot extends TelegramLongPollingBot {

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        long id = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        String chat_id = Long.toString(id);
        /*----------------------------------------------------------------------------------------*/
        if(Objects.equals(text, "/weather")) { //null-safe equals
            var msg = BotConf.constructMessage(chat_id, "Оберіть спосіб: ");
            weather_command_received = true; WeatherConf.setGeoButton(msg); execute(msg); }
        if(Objects.equals(text, "Поділитися місцезнаходженням") && weather_command_received) { //null-safe equals
            var msg = BotConf.constructMessage(chat_id, "Надішліть геолокацію: ");
            weather_command_received = false; weather_geo_received = true; execute(msg); }
            if (text == null && weather_geo_received) {
                Double longitude = update.getMessage().getLocation().getLongitude();
                Double latitude = update.getMessage().getLocation().getLatitude();
                var msg = BotConf.constructMessage(chat_id, "Ось що дав нам пошук: "); execute(msg);
                weather_geo_received = false; execute(WeatherConf.sendWeatherByGeo(chat_id, longitude, latitude)); }
        if(text.equals("Ввести місто") && weather_command_received) {
            var msg = BotConf.constructMessage(chat_id, "Введіть назву міста: ");
            weather_city_received = true; weather_command_received = false; execute(msg); }
            if(update.hasMessage() && weather_city_received && !text.equals("Ввести місто")) {
                var msg = BotConf.constructMessage(chat_id, "Ось що дав нам пошук: "); execute(msg);
                weather_city_received = false; execute(WeatherConf.sendWeatherByName(chat_id, text)); }
        /*---------------------------------------------------------------------------------------- */
        if(Objects.equals(text, "/nasa") || Objects.equals(text, "/NASA")) {
            var msg = BotConf.constructMessage(chat_id, "Оберіть варіант: ");
            nasa_command_received = true; NASAConf.setNasaButton(msg); execute(msg);}
        if(text.equals("Наукова стаття") && nasa_command_received) {
            var msg0 = BotConf.constructMessage(chat_id, "Шукаємо інформації для Вас..."); execute(msg0);
            nasa_command_received = false; var arr = NASAConf.sendArticle();
            var title = arr[0]; var desc = arr[1]; var benefits = arr[2];
            var msg = BotConf.constructMessage(chat_id, "\uD83E\uDDE7 *Interesting story today:*\n\n_"+title+"_");
            var msg1 = BotConf.constructMessage(chat_id, "*Desription:*\n\n_"+desc+"_");
            var msg2 = BotConf.constructMessage(chat_id, "*Benefits:*\n\n_"+benefits+"_");
            execute(msg); execute(msg1); execute(msg2); }
        if(text.equals("День в історії") && nasa_command_received) {
            nasa_command_received = false; execute(NASAConf.sendTodayInfo(chat_id));}
        /*-----------------------------------------------------------------------------------------*/
        if(Objects.equals(text, "/code")) { //null-safe 'equals'
            var msg = BotConf.constructMessage(chat_id, "Оберіть варіант: ");
            aes_command_received = true; AESConf.setAesButton(msg); execute(msg); }
        if(text.equals("Зашифрувати") && aes_command_received) {
            var msg = BotConf.constructMessage(chat_id, "Введіть повідомлення: ");
            aes_encrypt_received = true; aes_command_received = false; execute(msg); }
            if(update.hasMessage() && aes_encrypt_received && !text.equals("Зашифрувати")) {
                aes_encrypt_received = false; execute(AESConf.sendEncryptedMessage(chat_id, text)); }
        if(text.equals("Розшифрувати") && aes_command_received) {
            var msg = BotConf.constructMessage(chat_id, "Введіть повідомлення: ");
            aes_decrypt_received = true; aes_command_received = false; execute(msg); }
            if(update.hasMessage() && aes_decrypt_received && !text.equals("Розшифрувати")) {
                var msg = BotConf.constructMessage(chat_id, "Введіть ключ: ");
                aes_decrypt_received = false; aes_decrypt_key_received = true; word_to_decrypt = text; execute(msg); }
            if(update.hasMessage() && aes_decrypt_key_received && !text.equals(word_to_decrypt)) {
                aes_decrypt_key_received = false; execute(AESConf.sendDecryptedMessage(chat_id, word_to_decrypt, text)); }
        /*--------------------------------------------------------------------------------------------*/
    }
    public String word_to_decrypt;
    public boolean weather_command_received;
    public boolean weather_geo_received;
    public boolean weather_city_received;
    public boolean aes_command_received;
    public boolean aes_encrypt_received;
    public boolean aes_decrypt_key_received;
    public boolean aes_decrypt_received;
    public boolean nasa_command_received;

    @Override
    public String getBotUsername() {
        return "anexaety_bot";
    }

    @Override
    public String getBotToken() {
        return "1137876193:AAGbkmGUnbdOOzTaxiFkG-_kbIJO6CBqiQc";
    }
}

