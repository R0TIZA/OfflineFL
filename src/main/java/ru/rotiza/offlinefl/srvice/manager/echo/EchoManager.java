package ru.rotiza.offlinefl.srvice.manager.echo;

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

import java.util.Arrays;

import static ru.rotiza.offlinefl.srvice.data.Texts.*;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EchoManager extends AbstractManager {

    @Autowired
    public EchoManager(AnswerMethodFactory answerMethodFactory) {
        super(answerMethodFactory);
    }

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        String[] args = Arrays.stream(message.getText().split(" ")).skip(1).toArray(String[]::new);

        if (args.length == 0)
            return answerMethodFactory.getSendMessage(
                    message,
                    EMPTY_ECHO_ARGS_TEXT,
                    null
            );
        else
            return answerMethodFactory.getSendMessage(
                    message,
                    String.join(" ", args),
                    null
            );
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        String[] args = Arrays.stream(callbackQuery.getData().split(" ")).skip(1).toArray(String[]::new);

        if (args.length == 0)
            return answerMethodFactory.getEditMessageText(
                    callbackQuery,
                    EMPTY_ECHO_ARGS_TEXT,
                    null
            );
        else
            return answerMethodFactory.getEditMessageText(
                    callbackQuery,
                    String.join(" ", args),
                    null
            );
    }
}
