package ru.rotiza.offlinefl.srvice.manager;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rotiza.offlinefl.srvice.factory.AnswerMethodFactory;
import ru.rotiza.offlinefl.telegram.Bot;

public abstract class AbstractManager {

    protected final AnswerMethodFactory answerMethodFactory;

    public AbstractManager(AnswerMethodFactory answerMethodFactory) {
        this.answerMethodFactory = answerMethodFactory;
    }

    public abstract BotApiMethod<?> answerCommand(Message message, Bot bot);

    public abstract BotApiMethod<?> answerMessage(Message message, Bot bot);

    public abstract BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot);

}
