import bot.Bot;
import lombok.SneakyThrows;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotMainApp {
    @SneakyThrows
    public static void main(String[] args) {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new Bot());
    }
}
