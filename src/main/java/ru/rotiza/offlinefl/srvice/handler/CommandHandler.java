package ru.rotiza.offlinefl.srvice.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rotiza.offlinefl.srvice.factory.KeyboardFactory;
import ru.rotiza.offlinefl.telegram.Bot;

import java.util.List;

import static ru.rotiza.offlinefl.srvice.data.Command.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandHandler {

    final String INFO_TEXT = """
            Привет, это простой учебный бот и вот его возможности:\n
            /help, /h - комманды, благодаря котором можно вывести эту подсказку снова.\n
            /echo [Ваш текст] - бот напишет в ответ все что вы введете после этой команды.\n
            /feedback - получить ссылки на соц. сети для обратной связи.\n
            """;

    final String FEEDBACK_TEXT = """
            Ссылки для обратной связи:
            VK - https://vk.com/r0tiza
            TG - https://t.me/R0TIZA
            """;

    final KeyboardFactory keyboardFactory;

    @Autowired
    public CommandHandler(KeyboardFactory keyboardFactory) {
        this.keyboardFactory = keyboardFactory;
    }

    public BotApiMethod<?> answer(Message message, Bot bot) {
        String chatId = String.valueOf(message.getChatId());
        String command = message.getText().split(" ")[0];
        String[] args = message.getText().replaceFirst(command, "").split(" ");
        command = command.substring(1);
        switch (command) {
            case HELP:
            case H:
                return simpleAnswer(chatId, INFO_TEXT);
            case START:
                return start(chatId, INFO_TEXT);
            case ECHO:
                return simpleAnswer(chatId, String.join(" ", args));
            case FEEDBACK:
                return simpleAnswer(chatId, FEEDBACK_TEXT);
            default:
                return simpleAnswer(chatId, "У этого бота пока нет такой команды.");
        }
    }

    private BotApiMethod<?> simpleAnswer(String chatId, String message) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .disableWebPagePreview(true)
                .build();
    }

    private BotApiMethod<?> start(String chatId, String message) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .disableWebPagePreview(true)
                .replyMarkup(keyboardFactory.getInlineKeyboard(
                        List.of("Помощь", "Обратная связь", "Зеркало"),
                        List.of("help", "feedback", "echo Зеркало"),
                        List.of(2, 1)
                ))
                .build();
    }
}
