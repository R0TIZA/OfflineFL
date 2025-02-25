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
public class EchoManager {

    final AnswerMethodFactory answerMethodFactory;
    final KeyboardFactory keyboardFactory;

    @Autowired
    public EchoManager(AnswerMethodFactory answerMethodFactory, KeyboardFactory keyboardFactory) {
        this.answerMethodFactory = answerMethodFactory;
        this.keyboardFactory = keyboardFactory;
    }

    public BotApiMethod<?> echo(Long chatId, String[] args) {
        if (args.length == 0)
            return answerMethodFactory.getSendMessage(chatId, "А в ответ тишина.", null);
        else
            return answerMethodFactory.getSendMessage(chatId, String.join(" ", args), null);
    }

    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, String[] args) {
        if (args.length == 0)
            return answerMethodFactory.getEditMessageText(
                    callbackQuery.getMessage().getChatId(),
                    ((Message)callbackQuery.getMessage()).getMessageId(),
                    "А в ответ тишина.",
                    null
            );
        else
            return answerMethodFactory.getEditMessageText(
                    callbackQuery.getMessage().getChatId(),
                    ((Message)callbackQuery.getMessage()).getMessageId(),
                    String.join(" ", args),
                    null
        );
    }

}
