package ru.rotiza.offlinefl.srvice.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rotiza.offlinefl.srvice.factory.AnswerMethodFactory;
import ru.rotiza.offlinefl.srvice.factory.KeyboardFactory;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleMessageManager {

    final AnswerMethodFactory answerMethodFactory;
    final KeyboardFactory keyboardFactory;

    @Autowired
    public SimpleMessageManager(AnswerMethodFactory answerMethodFactory, KeyboardFactory keyboardFactory) {
        this.answerMethodFactory = answerMethodFactory;
        this.keyboardFactory = keyboardFactory;
    }

    public BotApiMethod<?> sendMessage(Long chatId, String text) {
        return answerMethodFactory.getSendMessage(chatId, text,null);
    }

    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, String text) {
        return answerMethodFactory.getEditMessageText(callbackQuery.getMessage().getChatId(),
                ((Message)callbackQuery.getMessage()).getMessageId(),
                text,
                null);
    }

}
