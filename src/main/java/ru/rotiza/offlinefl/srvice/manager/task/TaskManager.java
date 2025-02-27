package ru.rotiza.offlinefl.srvice.manager.task;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rotiza.offlinefl.srvice.factory.AnswerMethodFactory;
import ru.rotiza.offlinefl.srvice.manager.AbstractManager;
import ru.rotiza.offlinefl.srvice.manager.unknown_command.UnknownCommandManager;
import ru.rotiza.offlinefl.telegram.Bot;

import static ru.rotiza.offlinefl.srvice.data.Texts.*;
import static ru.rotiza.offlinefl.srvice.data.Commands.*;
import static ru.rotiza.offlinefl.srvice.data.InlineKeyboards.*;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskManager extends AbstractManager {

    final UnknownCommandManager unknownCommandManager;

    public TaskManager(AnswerMethodFactory answerMethodFactory, UnknownCommandManager unknownCommandManager) {
        super(answerMethodFactory);
        this.unknownCommandManager = unknownCommandManager;
    }

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return mainMenu(message);
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        String callbackData = callbackQuery.getData();

        switch (callbackData){
            case TASK:
                return mainMenu(callbackQuery);
            case TASK_PIN:
                return pin(callbackQuery);
            default:
                return unknownCommandManager.answerCallbackQuery(callbackQuery, bot);
        }
    }

    private BotApiMethod<?> mainMenu(Message message) {
        return answerMethodFactory.getSendMessage(
                message,
                TASK_MENU_TEXT,
                TASK_MENU_INLINE_KEYBOARD
        );
    }

    private BotApiMethod<?> mainMenu(CallbackQuery callbackQuery) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                TASK_MENU_TEXT,
                TASK_MENU_INLINE_KEYBOARD
        );
    }

    private BotApiMethod<?> pin(CallbackQuery callbackQuery) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                TASK_PIN_TEXT,
                TASK_PIN_INLINE_KEYBOARD
        );
    }
}
