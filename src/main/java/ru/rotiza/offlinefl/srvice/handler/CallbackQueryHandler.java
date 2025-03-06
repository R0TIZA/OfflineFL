package ru.rotiza.offlinefl.srvice.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.rotiza.offlinefl.srvice.manager.auth.AuthManager;
import ru.rotiza.offlinefl.srvice.manager.echo.EchoManager;
import ru.rotiza.offlinefl.srvice.manager.feedback.FeedbackManager;
import ru.rotiza.offlinefl.srvice.manager.info.InfoManager;
import ru.rotiza.offlinefl.srvice.manager.progress_control.ProgressControlManager;
import ru.rotiza.offlinefl.srvice.manager.task.TaskManager;
import ru.rotiza.offlinefl.srvice.manager.timetable.TimetableManager;
import ru.rotiza.offlinefl.srvice.manager.unknown_command.UnknownCommandManager;
import ru.rotiza.offlinefl.telegram.Bot;

import static ru.rotiza.offlinefl.srvice.data.Commands.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallbackQueryHandler {

    final InfoManager infoManager;
    final EchoManager echoManager;
    final FeedbackManager feedbackManager;
    final UnknownCommandManager unknownCommandManager;
    final TimetableManager timetableManager;
    final TaskManager taskManager;
    final ProgressControlManager progressControlManager;
    final AuthManager authManager;

    @Autowired
    public CallbackQueryHandler(InfoManager infoManager,
                                EchoManager echoManager,
                                FeedbackManager feedbackManager,
                                UnknownCommandManager unknownCommandManager,
                                TimetableManager timetableManager,
                                TaskManager taskManager,
                                ProgressControlManager progressControlManager,
                                AuthManager authManager) {
        this.infoManager = infoManager;
        this.echoManager = echoManager;
        this.feedbackManager = feedbackManager;
        this.unknownCommandManager = unknownCommandManager;
        this.timetableManager = timetableManager;
        this.taskManager = taskManager;
        this.progressControlManager = progressControlManager;
        this.authManager = authManager;
    }

    public BotApiMethod<?> answer(CallbackQuery callbackQuery, Bot bot) {
        String[] callbackCommand = callbackQuery.getData().split(" ")[0].split("_");

        switch (callbackCommand[0]){
            case INFO:
                return infoManager.answerCallbackQuery(callbackQuery, bot);
            case FEEDBACK:
                return feedbackManager.answerCallbackQuery(callbackQuery, bot);
            case ECHO:
                return echoManager.answerCallbackQuery(callbackQuery, bot);
            case TIMETABLE:
                return timetableManager.answerCallbackQuery(callbackQuery, bot);
            case TASK:
                return taskManager.answerCallbackQuery(callbackQuery, bot);
            case PROGRESS_CONTROL:
                return progressControlManager.answerCallbackQuery(callbackQuery, bot);
            case AUTH:
                return authManager.answerCallbackQuery(callbackQuery, bot);
            default:
                return unknownCommandManager.answerCallbackQuery(callbackQuery, bot);
        }
    }
}
