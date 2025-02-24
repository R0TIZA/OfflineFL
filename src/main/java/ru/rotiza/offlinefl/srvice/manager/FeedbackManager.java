package ru.rotiza.offlinefl.srvice.manager;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class FeedbackManager {

    private final String FEEDBACK_TEXT = """
                                        Ссылки для обратной связи:
                                        VK - https://vk.com/r0tiza
                                        TG - https://t.me/R0TIZA
                                        """;

    public BotApiMethod<?> sendFeedbackText(String chatId){
        return SendMessage.builder()
                .chatId(chatId)
                .text(FEEDBACK_TEXT)
                .disableWebPagePreview(true)
                .build();
    }

    public BotApiMethod<?> sendFeedbackText(long chatId){
        return SendMessage.builder()
                .chatId(chatId)
                .text(FEEDBACK_TEXT)
                .disableWebPagePreview(true)
                .build();
    }

    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery) {
        return EditMessageText.builder()
                .chatId(callbackQuery.getMessage().getChatId())
                .messageId(((Message)callbackQuery.getMessage()).getMessageId())
                .text(FEEDBACK_TEXT)
                .disableWebPagePreview(true)
                .build();
    }

}
