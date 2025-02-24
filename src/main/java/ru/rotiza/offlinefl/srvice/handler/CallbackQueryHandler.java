package ru.rotiza.offlinefl.srvice.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.MaybeInaccessibleMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import ru.rotiza.offlinefl.srvice.factory.KeyboardFactory;
import ru.rotiza.offlinefl.srvice.manager.EchoManager;
import ru.rotiza.offlinefl.srvice.manager.FeedbackManager;
import ru.rotiza.offlinefl.srvice.manager.InfoManager;
import ru.rotiza.offlinefl.srvice.manager.SimpleMessageManager;
import ru.rotiza.offlinefl.telegram.Bot;

import java.util.Arrays;
import java.util.List;

import static ru.rotiza.offlinefl.srvice.data.Commands.*;

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
                return simpleMessageManager.answerCallbackQuery(callbackQuery, "Дурень, кнопку сделал, а поведение не задал. Иди доделывай!");
        }
    }
}
