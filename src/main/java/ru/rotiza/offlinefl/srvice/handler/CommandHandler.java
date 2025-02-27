package ru.rotiza.offlinefl.srvice.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rotiza.offlinefl.srvice.manager.echo.EchoManager;
import ru.rotiza.offlinefl.srvice.manager.feedback.FeedbackManager;
import ru.rotiza.offlinefl.srvice.manager.info.InfoManager;
import ru.rotiza.offlinefl.srvice.manager.start.StartManager;
import ru.rotiza.offlinefl.srvice.manager.unknown_command.UnknownCommandManager;
import ru.rotiza.offlinefl.telegram.Bot;

import static ru.rotiza.offlinefl.srvice.data.Commands.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandHandler {

    final InfoManager infoManager;
    final StartManager startManager;
    final EchoManager echoManager;
    final FeedbackManager feedbackManager;
    final UnknownCommandManager unknownCommandManager;

    @Autowired
    public CommandHandler(InfoManager infoManager, EchoManager echoManager, FeedbackManager feedbackManager, UnknownCommandManager unknownCommandManager, StartManager startManager) {
        this.infoManager = infoManager;
        this.echoManager = echoManager;
        this.feedbackManager = feedbackManager;
        this.unknownCommandManager = unknownCommandManager;
        this.startManager = startManager;
    }

    public BotApiMethod<?> answer(Message message, Bot bot) {
        String command = message.getText().split(" ")[0];
        command = command.substring(1);

        switch (command) {
            case HELP:
            case H:
                return infoManager.answerCommand(message, bot);
            case START:
                return startManager.answerCommand(message, bot);
            case ECHO:
                return echoManager.answerCommand(message, bot);
            case FEEDBACK:
                return feedbackManager.answerCommand(message, bot);
            default:
                return unknownCommandManager.answerCommand(message, bot);
        }
    }
}
