package ru.rotiza.offlinefl.srvice.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.rotiza.offlinefl.srvice.factory.AnswerMethodFactory;
import ru.rotiza.offlinefl.srvice.factory.KeyboardFactory;

import java.util.List;

import static ru.rotiza.offlinefl.srvice.data.Commands.*;
import static ru.rotiza.offlinefl.srvice.data.Texts.*;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StartManager {

    final AnswerMethodFactory answerMethodFactory;
    final KeyboardFactory keyboardFactory;

    final InlineKeyboardMarkup INFO_KEYBOARD;

    @Autowired
    public StartManager(AnswerMethodFactory answerMethodFactory, KeyboardFactory keyboardFactory) {
        this.answerMethodFactory = answerMethodFactory;
        this.keyboardFactory = keyboardFactory;
        this.INFO_KEYBOARD = this.keyboardFactory.getInlineKeyboard(
                List.of("Помощь", "Обратная связь", "Echo", "Test"),
                List.of(INFO, FEEDBACK, ECHO, "test"),
                List.of(2, 2)
        );
    }

    public BotApiMethod<?> sendMessageStart(Long chatId) {
        return answerMethodFactory.getSendMessage(chatId, INFO_TEXT, INFO_KEYBOARD);
    }

    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery.getMessage().getChatId(),
                ((Message)callbackQuery.getMessage()).getMessageId(),
                INFO_TEXT,
                INFO_KEYBOARD
        );
    }
}
