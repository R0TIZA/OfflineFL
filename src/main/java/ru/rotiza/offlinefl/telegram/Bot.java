package ru.rotiza.offlinefl.telegram;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.rotiza.offlinefl.srvice.UpdateDispatcher;

import java.io.IOException;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Bot extends TelegramWebhookBot {

    TelegramProperties telegramProperties;
    UpdateDispatcher dispatcher;

    @Autowired
    public Bot(TelegramProperties telegramProperties, UpdateDispatcher dispatcher) {
        super(telegramProperties.getToken());
        this.telegramProperties = telegramProperties;
        this.dispatcher = dispatcher;

        setWebhookUrl();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return dispatcher.distribute(update, this);
    }

    @Override
    public String getBotPath() {
        return telegramProperties.getPath();
    }

    @Override
    public String getBotUsername() {
        return telegramProperties.getName();
    }

    private void setWebhookUrl() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.telegram.org/bot"+telegramProperties.getToken()+"/setWebhook?url="+telegramProperties.getPath())
                .build();
        try(Response response = client.newCall(request).execute()){
            System.out.println(response.isSuccessful());
        }
        catch (IOException e){
            e.fillInStackTrace();
        }
    }
}
