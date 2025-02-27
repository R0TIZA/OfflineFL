package ru.rotiza.offlinefl.srvice.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.rotiza.offlinefl.srvice.factory.AnswerMethodFactory;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleMessageManager {

    final AnswerMethodFactory answerMethodFactory;

    @Autowired
    public SimpleMessageManager(AnswerMethodFactory answerMethodFactory) {
        this.answerMethodFactory = answerMethodFactory;
    }

    public BotApiMethod<?> sendMessage(Long chatId, String text, ReplyKeyboard replyKeyboard) {
        return answerMethodFactory.getSendMessage(chatId, text, replyKeyboard);
    }

    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        return answerMethodFactory.getEditMessageText(callbackQuery.getMessage().getChatId(),
                ((Message)callbackQuery.getMessage()).getMessageId(),
                text,
                inlineKeyboardMarkup);
    }

}
