package ru.rotiza.offlinefl.srvice.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rotiza.offlinefl.srvice.manager.echo.EchoManager;
import ru.rotiza.offlinefl.srvice.manager.feedback.FeedbackManager;
import ru.rotiza.offlinefl.srvice.manager.info.InfoManager;
import ru.rotiza.offlinefl.srvice.manager.unknown_command.UnknownCommandManager;
import ru.rotiza.offlinefl.telegram.Bot;

import java.util.Arrays;

import static ru.rotiza.offlinefl.srvice.data.Commands.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallbackQueryHandler {

    final InfoManager infoManager;
    final EchoManager echoManager;
    final FeedbackManager feedbackManager;
    final UnknownCommandManager unknownCommandManager;

    @Autowired
    public CallbackQueryHandler(InfoManager infoManager, EchoManager echoManager, FeedbackManager feedbackManager, UnknownCommandManager unknownCommandManager) {
        this.infoManager = infoManager;
        this.echoManager = echoManager;
        this.feedbackManager = feedbackManager;
        this.unknownCommandManager = unknownCommandManager;
    }

    public BotApiMethod<?> answer(CallbackQuery callbackQuery, Bot bot) {
        String callbackCommand = callbackQuery.getData().split(" ")[0];

        switch (callbackCommand){
            case INFO:
                return infoManager.answerCallbackQuery(callbackQuery, bot);
            case FEEDBACK:
                return feedbackManager.answerCallbackQuery(callbackQuery, bot);
            case ECHO:
                System.out.println("echo");
                return echoManager.answerCallbackQuery(callbackQuery, bot);
            default:
                return unknownCommandManager.answerCallbackQuery(callbackQuery, bot);
        }
    }
}
