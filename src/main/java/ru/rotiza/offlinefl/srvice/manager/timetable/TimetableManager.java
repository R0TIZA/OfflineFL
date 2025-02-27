package ru.rotiza.offlinefl.srvice.manager.timetable;


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
public class TimetableManager extends AbstractManager {

    final UnknownCommandManager unknownCommandManager;

    public TimetableManager(AnswerMethodFactory answerMethodFactory, UnknownCommandManager unknownCommandManager) {
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
            case TIMETABLE:
                return mainMenu(callbackQuery);
            case TIMETABLE_SHOW:
                return show(callbackQuery);
            case TIMETABLE_ADD:
                return add(callbackQuery);
            case TIMETABLE_REMOVE:
                return remove(callbackQuery);
            default:
                return unknownCommandManager.answerCallbackQuery(callbackQuery, bot);
        }
    }

    private BotApiMethod<?> mainMenu(Message message) {
        return answerMethodFactory.getSendMessage(
                message,
                TIMETABLE_MENU_TEXT,
                TIMETABLE_MENU_INLINE_KEYBOARD
        );
    }

    private BotApiMethod<?> mainMenu(CallbackQuery callbackQuery) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                TIMETABLE_MENU_TEXT,
                TIMETABLE_MENU_INLINE_KEYBOARD
        );
    }

    private BotApiMethod<?> show(CallbackQuery callbackQuery) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                TIMETABLE_SHOW_TEXT,
                TIMETABLE_SHOW_INLINE_KEYBOARD
        );
    }

    private BotApiMethod<?> add(CallbackQuery callbackQuery) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                TIMETABLE_ADD_TEXT,
                TIMETABLE_ADD_INLINE_KEYBOARD
        );
    }

    private BotApiMethod<?> remove(CallbackQuery callbackQuery) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                TIMETABLE_REMOVE_TEXT,
                TIMETABLE_REMOVE_INLINE_KEYBOARD
        );
    }
}
