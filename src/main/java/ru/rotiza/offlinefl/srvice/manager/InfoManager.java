package ru.rotiza.offlinefl.srvice.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.rotiza.offlinefl.srvice.factory.KeyboardFactory;

import java.util.List;

import static ru.rotiza.offlinefl.srvice.data.Commands.*;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfoManager {

    final KeyboardFactory KEYBOARD_FACTORY;

    final String INFO_TEXT = """
            Привет, это простой учебный бот и вот его возможности:\n
            /help, /h - комманды, благодаря котором можно вывести эту подсказку снова.\n
            /echo [Ваш текст] - бот напишет в ответ все что вы введете после этой команды или \"И в ответ тишина\", если отправить команду без текста.\n
            /feedback - получить ссылки на соц. сети для обратной связи.\n
            """;

    final InlineKeyboardMarkup INFO_KEYBOARD;

    @Autowired
    public InfoManager(KeyboardFactory keyboardFactory) {
        this.KEYBOARD_FACTORY = keyboardFactory;
        this.INFO_KEYBOARD = this.KEYBOARD_FACTORY.getInlineKeyboard(
                List.of("Помощь", "Обратная связь", "Echo", "Test"),
                List.of(INFO, FEEDBACK, ECHO, "test"),
                List.of(2, 2)
        );
    }

    public BotApiMethod<?> sendInfo(String chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(INFO_TEXT)
                .build();
    }

    public BotApiMethod<?> sendInfo(long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(INFO_TEXT)
                .build();
    }

    public BotApiMethod<?> sendInfo(String chatId, boolean isCommandStart) {
        if(isCommandStart) {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(INFO_TEXT)
                    .replyMarkup(INFO_KEYBOARD)
                    .build();
        }else return sendInfo(chatId);
    }

    public BotApiMethod<?> sendInfo(long chatId, boolean isCommandStart) {
        if (isCommandStart) {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(INFO_TEXT)
                    .replyMarkup(INFO_KEYBOARD)
                    .build();
        } else return sendInfo(chatId);
    }

    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery) {
        return EditMessageText.builder()
                .chatId(callbackQuery.getMessage().getChatId())
                .messageId(((Message)callbackQuery.getMessage()).getMessageId())
                .text(INFO_TEXT)
                .build();
    }

    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, boolean isCommandStart) {
        int messageId = ((Message)callbackQuery.getMessage()).getMessageId();
        long chatId = callbackQuery.getMessage().getChatId();

        if (isCommandStart) {
            return EditMessageText.builder()
                    .chatId(chatId)
                    .messageId(messageId)
                    .text(INFO_TEXT)
                    .replyMarkup(INFO_KEYBOARD)
                    .build();
        } else return sendInfo(messageId);
    }

}
