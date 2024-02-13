package org.example.demotgbot.bot;

import org.example.demotgbot.model.Weather;
import org.example.demotgbot.properties.BotProperties;
import org.example.demotgbot.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotProperties botProperties;
    private final WeatherService weatherService;

    @Autowired
    public TelegramBot(BotProperties botProperties, WeatherService weatherService) {
        this.botProperties = botProperties;
        this.weatherService = weatherService;
        List<BotCommand> commandList = new ArrayList<>();
        commandList.add(new BotCommand("/start", "Общая информация"));
        commandList.add(new BotCommand("/weather", "Узнать погоду"));
        commandList.add(new BotCommand("/help", "Помощь"));
        try {
            execute(new SetMyCommands(commandList, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botProperties.getBotName();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            switch (command) {
                case "/start":
                    sendMessage(chatId, "Приветствую, " + update.getMessage().getChat().getFirstName());
                    break;
                case "/weather":
                    Weather weather =  weatherService.getWeather();
                    sendMessage(chatId, "Погода в городе Москва:\n" +
                                        weather.getCurrent().getCondition().getText() + "\n" +
                                        "Температура: " + weather.getCurrent().getTemp_c());
                    break;
                case "/help":
                    sendMessage(chatId, "По вропросам сотрудничества:\n" +
                                        "Спасский Илья\n" +
                                        "тел.: 8(915)-483-56-36\n" +
                                        "почта: spasskiy.iv@yandex.ru");
                    break;
                default:
                    sendMessage(chatId, "Незнакомая команда, повторите попытку.");
            }
        }
    }

    private void sendMessage(long chatId, String msg) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(msg);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
