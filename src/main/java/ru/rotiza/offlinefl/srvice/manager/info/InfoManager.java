package ru.rotiza.offlinefl.srvice.manager.info;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rotiza.offlinefl.srvice.factory.AnswerMethodFactory;
import ru.rotiza.offlinefl.srvice.manager.AbstractManager;
import ru.rotiza.offlinefl.telegram.Bot;

import static ru.rotiza.offlinefl.srvice.data.Texts.*;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfoManager extends AbstractManager {

    @Autowired
    public InfoManager(AnswerMethodFactory answerMethodFactory) {
        super(answerMethodFactory);
    }

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return answerMethodFactory.getSendMessage(
                message,
                INFO_TEXT,
                null
        );
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                INFO_TEXT,
                null
        );
    }
}
