package ru.rotiza.offlinefl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.rotiza.offlinefl.telegram.TelegramProperties;

@SpringBootApplication
@EnableConfigurationProperties({TelegramProperties.class})
@EnableAspectJAutoProxy
public class Application {

    public static void main(String[] args) { SpringApplication.run(Application.class, args); }

}
