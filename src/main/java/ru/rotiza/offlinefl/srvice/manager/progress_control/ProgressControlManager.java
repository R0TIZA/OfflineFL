package ru.rotiza.offlinefl.srvice.manager.progress_control;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProgressControlManager extends AbstractManager {

    final UnknownCommandManager unknownCommandManager;

    @Autowired
    public ProgressControlManager(AnswerMethodFactory answerMethodFactory, UnknownCommandManager unknownCommandManager) {
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

        switch (callbackData) {
            case PROGRESS_CONTROL:
                return mainMenu(callbackQuery);
            case PROGRESS_CONTROL_STAT:
                return stat(callbackQuery);
            default:
                unknownCommandManager.answerCallbackQuery(callbackQuery, bot);
        }
        return null;
    }

    private BotApiMethod<?> mainMenu(Message message) {
        return answerMethodFactory.getSendMessage(
                message,
                PROGRESS_CONTROL_MENU_TEXT,
                PROGRESS_CONTROL_MENU_INLINE_KEYBOARD
                );
    }

    private BotApiMethod<?> mainMenu(CallbackQuery callbackQuery) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                PROGRESS_CONTROL_MENU_TEXT,
                PROGRESS_CONTROL_MENU_INLINE_KEYBOARD
        );
    }

    private BotApiMethod<?> stat(CallbackQuery callbackQuery) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                PROGRESS_CONTROL_SHOW_TEXT,
                PROGRESS_CONTROL_SHOW_INLINE_KEYBOARD
        );
    }
}
