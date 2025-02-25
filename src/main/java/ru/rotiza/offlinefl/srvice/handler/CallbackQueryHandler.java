package ru.rotiza.offlinefl.srvice.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.rotiza.offlinefl.srvice.manager.*;
import ru.rotiza.offlinefl.telegram.Bot;

import java.util.Arrays;

import static ru.rotiza.offlinefl.srvice.data.Commands.*;
import static ru.rotiza.offlinefl.srvice.data.Texts.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallbackQueryHandler {

    final InfoManager infoManager;
    final EchoManager echoManager;
    final FeedbackManager feedbackManager;
    final SimpleMessageManager simpleMessageManager;

    @Autowired
    public CallbackQueryHandler(InfoManager infoManager, EchoManager echoManager, FeedbackManager feedbackManager, SimpleMessageManager simpleMessageManager) {
        this.infoManager = infoManager;
        this.echoManager = echoManager;
        this.feedbackManager = feedbackManager;
        this.simpleMessageManager = simpleMessageManager;
    }

    public BotApiMethod<?> answer(CallbackQuery callbackQuery, Bot bot) {

        String callbackCommand = callbackQuery.getData().split(" ")[0];
        String[] callbackArgs = Arrays.stream(callbackQuery.getData().split(" ")).skip(1).toArray(String[]::new);

        switch (callbackCommand){
            case INFO:
                return infoManager.answerCallbackQuery(callbackQuery);
            case FEEDBACK:
                return feedbackManager.answerCallbackQuery(callbackQuery);
            case ECHO:
                return echoManager.answerCallbackQuery(callbackQuery, callbackArgs);
            default:
                return simpleMessageManager.answerCallbackQuery(callbackQuery, NO_CALLBACK_QUERY_TEXT);
        }
    }
}
