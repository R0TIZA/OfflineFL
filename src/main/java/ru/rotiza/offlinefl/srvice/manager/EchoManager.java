package ru.rotiza.offlinefl.srvice.manager;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class EchoManager {

    public BotApiMethod<?> echo(String chatId, String[] args) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.disableWebPagePreview();
        if (args.length == 0) message.setText("А в ответ тишина");
        else message.setText(String.join(" ", args));

        return message;
    }

    public BotApiMethod<?> echo(long chatId, String[] args) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.disableWebPagePreview();
        if (args.length == 0) message.setText("А в ответ тишина");
        else message.setText(String.join(" ", args));

        return message;
    }

    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, String[] args) {
        EditMessageText message = new EditMessageText();
        message.setChatId(callbackQuery.getMessage().getChatId());
        message.setMessageId(((Message)callbackQuery.getMessage()).getMessageId());
        message.disableWebPagePreview();
        if (args.length == 0) message.setText("А в ответ тишина");
        else message.setText(String.join(" ", args));

        return message;
    }

}
