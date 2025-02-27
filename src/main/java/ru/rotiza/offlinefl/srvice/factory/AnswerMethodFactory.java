package ru.rotiza.offlinefl.srvice.factory;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class AnswerMethodFactory {

    public SendMessage getSendMessage(Message message, String text, ReplyKeyboard replyKeyboard) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(text)
                .replyMarkup(replyKeyboard)
                .disableWebPagePreview(true)
                .build();
    }

    public EditMessageText getEditMessageText(CallbackQuery callbackQuery, String text, InlineKeyboardMarkup replyKeyboard) {
        Message message = (Message) callbackQuery.getMessage();

        return EditMessageText.builder()
                .chatId(message.getChatId())
                .messageId(message.getMessageId())
                .text(text)
                .replyMarkup(replyKeyboard)
                .disableWebPagePreview(true)
                .build();
    }

    public DeleteMessage getDeleteMessage(Long chatId, Integer messageId) {
        return DeleteMessage.builder()
                .chatId(chatId)
                .messageId(messageId)
                .build();
    }
}
