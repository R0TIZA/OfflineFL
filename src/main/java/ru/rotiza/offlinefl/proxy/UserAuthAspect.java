package ru.rotiza.offlinefl.proxy;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.rotiza.offlinefl.entity.user.Action;
import ru.rotiza.offlinefl.entity.user.Role;
import ru.rotiza.offlinefl.entity.user.User;
import ru.rotiza.offlinefl.repository.UserRepo;
import ru.rotiza.offlinefl.srvice.manager.auth.AuthManager;
import ru.rotiza.offlinefl.telegram.Bot;

@Aspect
@Order(100)
@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAuthAspect {
    final UserRepo userRepo;
    final AuthManager authManager;

    @Autowired
    public UserAuthAspect(UserRepo userRepo, AuthManager authManager) {
        this.userRepo = userRepo;
        this.authManager = authManager;
    }

    @Pointcut("execution(* ru.rotiza.offlinefl.srvice.UpdateDispatcher.distribute(..))")
    public void distributeMethodPointcut() {}

    @Around("distributeMethodPointcut()")
    public Object authMethodAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Update update = (Update) joinPoint.getArgs()[0];
        Bot bot = (Bot) joinPoint.getArgs()[1];
        User user;
        if(update.hasCallbackQuery()) user = userRepo.findById(update.getCallbackQuery().getFrom().getId()).orElseThrow();
        else if (update.hasMessage()) user = userRepo.findById(update.getMessage().getFrom().getId()).orElseThrow();
        else return joinPoint.proceed();

        if(user.getRole() != Role.EMPTY) return joinPoint.proceed();
        if(user.getAction() == Action.AUTH) return joinPoint.proceed();

        try{
            bot.execute(authManager.answerMessage(update.getMessage(), bot));
        }
        catch (TelegramApiException e){
            log.error(e.getMessage());
        }

        return joinPoint.proceed();
    }

}
