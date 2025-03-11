package ru.rotiza.offlinefl.srvice.manager.profile;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rotiza.offlinefl.entity.user.User;
import ru.rotiza.offlinefl.entity.user.UserDetails;
import ru.rotiza.offlinefl.repository.UserRepo;
import ru.rotiza.offlinefl.srvice.factory.AnswerMethodFactory;
import ru.rotiza.offlinefl.srvice.manager.AbstractManager;
import ru.rotiza.offlinefl.telegram.Bot;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileManager extends AbstractManager {

    final UserRepo userRepo;

    @Autowired
    public ProfileManager(AnswerMethodFactory answerMethodFactory, UserRepo userRepo) {
        super(answerMethodFactory);
        this.userRepo = userRepo;
    }

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return showProfile(message);
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        return null;
    }

    private BotApiMethod<?> showProfile(Message message) {
        StringBuilder messageText = new StringBuilder("\uD83D\uDC64 Профиль \n");
        User currentUser = userRepo.findById(message.getFrom().getId()).orElseThrow();
        UserDetails currentUserDetails = currentUser.getDetails();

        if(currentUserDetails.getUsername() != null) {
            messageText.append("\uFE0FИмя пользователя - ").append(currentUserDetails.getUsername()).append("\n");
        }
        else {
            messageText.append("\uFE0F Имя пользователя - ").append(currentUserDetails.getFirstName()).append("\n");
        }
        messageText.append("\uFE0FРоль - ").append(currentUser.getRole().name()).append("\n");
        messageText.append("\uFE0FВаш уникальный токен:\n").append(currentUser.getToken().toString()).append("\n\n");
        messageText.append("* - токен нужен для того, чтобы ученик и преподаватель могли установить связь между собой.");

        return answerMethodFactory.getSendMessage(message, messageText.toString(), null);
    }
}
