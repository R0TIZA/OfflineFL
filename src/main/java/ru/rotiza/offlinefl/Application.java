package ru.rotiza.offlinefl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.rotiza.offlinefl.telegram.TelegramProperties;

@SpringBootApplication
@EnableConfigurationProperties({TelegramProperties.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
