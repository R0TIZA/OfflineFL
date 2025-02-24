package ru.rotiza.offlinefl.srvice;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.rotiza.offlinefl.srvice.handler.CallbackQueryHandler;
import ru.rotiza.offlinefl.srvice.handler.CommandHandler;
import ru.rotiza.offlinefl.srvice.handler.MessageHandler;
import ru.rotiza.offlinefl.telegram.Bot;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UpdateDispatcher {

    CallbackQueryHandler callbackQueryHandler;
    CommandHandler commandHandler;
    MessageHandler messageHandler;

    @Autowired
    public UpdateDispatcher(CallbackQueryHandler callbackQueryHandler,CommandHandler commandHandler,MessageHandler messageHandler) {
        this.callbackQueryHandler = callbackQueryHandler;
        this.commandHandler = commandHandler;
        this.messageHandler = messageHandler;
    }

    public BotApiMethod<?> distribute(Update update, Bot bot) {
        if(update.hasCallbackQuery()) return callbackQueryHandler.answer(update.getCallbackQuery(), bot);
        else if(update.hasMessage()) {
            if(update.getMessage().hasText()) {
                Message message = update.getMessage();
                if(message.getText().charAt(0) == '/'||message.getText().charAt(0) == '\\') return commandHandler.answer(message, bot);
                else return messageHandler.answer(message, bot);
            }
        }
        log.info("Unsupported update: "+ update);
        return null;
    }
}
