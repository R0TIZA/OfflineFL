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

import static ru.rotiza.offlinefl.srvice.data.Texts.*;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfoManager {

    final AnswerMethodFactory answerMethodFactory;
    final KeyboardFactory keyboardFactory;

    @Autowired
    public InfoManager(KeyboardFactory keyboardFactory, AnswerMethodFactory answerMethodFactory) {
        this.keyboardFactory = keyboardFactory;
        this.answerMethodFactory = answerMethodFactory;
    }

    public BotApiMethod<?> sendInfo(Long chatId) {
        return answerMethodFactory.getSendMessage(chatId, INFO_TEXT, null);
    }

    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery.getMessage().getChatId(),
                ((Message)callbackQuery.getMessage()).getMessageId(),
                INFO_TEXT,
                null
        );
    }
}
