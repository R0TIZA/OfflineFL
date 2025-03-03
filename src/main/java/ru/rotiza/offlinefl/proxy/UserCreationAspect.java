package ru.rotiza.offlinefl.proxy;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.rotiza.offlinefl.entity.user.Action;
import ru.rotiza.offlinefl.entity.user.Role;
import ru.rotiza.offlinefl.entity.user.UserDetails;
import ru.rotiza.offlinefl.repository.DetailsRepo;
import ru.rotiza.offlinefl.repository.UserRepo;

import java.time.LocalDateTime;

@Component
@Aspect
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationAspect {

    final UserRepo userRepo;
    final DetailsRepo detailsRepo;

    @Autowired
    public UserCreationAspect(UserRepo userRepo,
                              DetailsRepo detailsRepo) {
        this.userRepo = userRepo;
        this.detailsRepo = detailsRepo;
    }

    @Pointcut("execution(* ru.rotiza.offlinefl.srvice.UpdateDispatcher.distribute(..))")
    public void distributeMethodPointcut() {}

    @Around("distributeMethodPointcut()")
    public Object distributeMethodAdvice(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Update update = (Update) args[0];
        User tgUser = null;

        if(update.hasMessage()) tgUser = update.getMessage().getFrom();
        else if (update.hasCallbackQuery()) update.getCallbackQuery().getFrom();
        else return joinPoint.proceed();

        if(userRepo.existsById(tgUser.getId())) return joinPoint.proceed();

        UserDetails details = UserDetails.builder()
                .firstName(tgUser.getFirstName())
                .lastName(tgUser.getLastName())
                .username(tgUser.getUserName())
                .registeredAt(LocalDateTime.now())
                .build();
        detailsRepo.save(details);

        ru.rotiza.offlinefl.entity.user.User newUser = ru.rotiza.offlinefl.entity.user.User.builder()
                .userId(tgUser.getId())
                .action(Action.FREE)
                .role(Role.USER)
                .details(details)
                .build();
        userRepo.save(newUser);

        return joinPoint.proceed();
    }
}
