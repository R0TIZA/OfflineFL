package ru.rotiza.offlinefl.srvice.data;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.rotiza.offlinefl.srvice.factory.KeyboardFactory;

import java.util.List;

import static ru.rotiza.offlinefl.srvice.data.Commands.*;

public class InlineKeyboards {

    private static final KeyboardFactory keyboardFactory = KeyboardFactory.getInstance();

    public static final InlineKeyboardMarkup START_INLINE_KEYBOARD = keyboardFactory.getInlineKeyboard(
            List.of("Помощь", "Обратная связь", "Echo"),
            List.of(INFO, FEEDBACK, ECHO),
            List.of(2, 1)
    );

}
