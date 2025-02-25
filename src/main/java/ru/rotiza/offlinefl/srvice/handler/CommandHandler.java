package ru.rotiza.offlinefl.srvice.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rotiza.offlinefl.srvice.manager.*;
import ru.rotiza.offlinefl.telegram.Bot;

import java.util.Arrays;

import static ru.rotiza.offlinefl.srvice.data.Commands.*;
import static ru.rotiza.offlinefl.srvice.data.Texts.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandHandler {

    final InfoManager infoManager;
    final StartManager startManager;
    final EchoManager echoManager;
    final FeedbackManager feedbackManager;
    final SimpleMessageManager simpleMessageManager;

    @Autowired
    public CommandHandler(InfoManager infoManager, EchoManager echoManager, FeedbackManager feedbackManager, SimpleMessageManager simpleMessageManager, StartManager startManager) {
        this.infoManager = infoManager;
        this.echoManager = echoManager;
        this.feedbackManager = feedbackManager;
        this.simpleMessageManager = simpleMessageManager;
        this.startManager = startManager;
    }

    public BotApiMethod<?> answer(Message message, Bot bot) {

        Long chatId = message.getChatId();
        String command = message.getText().split(" ")[0];
        command = command.substring(1);
        String[] args = Arrays.stream(message.getText().split(" ")).skip(1).toArray(String[]::new);

        switch (command) {
            case HELP:
            case H:
                return infoManager.sendInfo(chatId);
            case START:
                return startManager.sendMessageStart(chatId);
            case ECHO:
                return echoManager.echo(chatId, args);
            case FEEDBACK:
                return feedbackManager.sendFeedbackText(chatId);
            default:
                return simpleMessageManager.sendMessage(chatId, NO_COMMAND_TEXT);
        }
    }
}
