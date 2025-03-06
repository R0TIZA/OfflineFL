package ru.rotiza.offlinefl.srvice.manager.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.rotiza.offlinefl.entity.user.Action;
import ru.rotiza.offlinefl.entity.user.Role;
import ru.rotiza.offlinefl.entity.user.User;
import ru.rotiza.offlinefl.repository.UserRepo;
import ru.rotiza.offlinefl.srvice.factory.AnswerMethodFactory;
import ru.rotiza.offlinefl.srvice.manager.AbstractManager;
import ru.rotiza.offlinefl.telegram.Bot;

import static ru.rotiza.offlinefl.srvice.data.InlineKeyboards.AUTH_INLINE_KEYBOARD;
import static ru.rotiza.offlinefl.srvice.data.Texts.AUTH_TEXT;
import static ru.rotiza.offlinefl.srvice.data.Texts.AUTH_STUDENT_TEXT;
import static ru.rotiza.offlinefl.srvice.data.Texts.AUTH_TEACHER_TEXT;
import static ru.rotiza.offlinefl.srvice.data.Commands.AUTH_STUDENT;
import static ru.rotiza.offlinefl.srvice.data.Commands.AUTH_TEACHER;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthManager extends AbstractManager {

    final UserRepo userRepo;
    private final AnswerMethodFactory answerMethodFactory;

    @Autowired
    AuthManager(AnswerMethodFactory answerMethodFactory,
                UserRepo userRepo) {
        super(answerMethodFactory);
        this.userRepo = userRepo;
        this.answerMethodFactory = answerMethodFactory;
    }

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return null;
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        Long userId = message.getFrom().getId();

        User user = userRepo.findById(userId).orElseThrow();
        user.setAction(Action.AUTH);
        userRepo.save(user);

        return answerMethodFactory.getSendMessage(message, AUTH_TEXT, AUTH_INLINE_KEYBOARD);
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        Long userId = callbackQuery.getFrom().getId();
        Message message = (Message) callbackQuery.getMessage();
        String answerMessage = "";
        User user = userRepo.findById(userId).orElseThrow();

        switch (callbackQuery.getData()) {
            case AUTH_STUDENT:
                user.setRole(Role.STUDENT);
                user.setAction(Action.FREE);
                answerMessage = AUTH_STUDENT_TEXT;
                break;
            case AUTH_TEACHER:
                user.setRole(Role.TEACHER);
                user.setAction(Action.FREE);
                answerMessage = AUTH_TEACHER_TEXT;
                break;
        }
        userRepo.save(user);

        try{
            bot.execute(answerMethodFactory.getAnswerCallbackQuery(callbackQuery, answerMessage));
        }
        catch (TelegramApiException exception){
            log.error(exception.getMessage());
        }
        return answerMethodFactory.getDeleteMessage(message);
    }
}
