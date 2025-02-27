package ru.rotiza.offlinefl.srvice.data;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.rotiza.offlinefl.srvice.factory.KeyboardFactory;

import java.util.List;

import static ru.rotiza.offlinefl.srvice.data.Commands.*;

public class InlineKeyboards {

    private static final KeyboardFactory keyboardFactory = KeyboardFactory.getInstance();

    //Start keyboard
    public static final InlineKeyboardMarkup START_INLINE_KEYBOARD = keyboardFactory.getInlineKeyboard(
            List.of("Помощь", "Обратная связь", "Расписание занятий", "Назначить д/з"),
            List.of(INFO, FEEDBACK, TIMETABLE, TASK),
            List.of(2, 1, 1)
    );

    //Timetable keyboards
    public static final InlineKeyboardMarkup TIMETABLE_MENU_INLINE_KEYBOARD = keyboardFactory.getInlineKeyboard(
            List.of("Показать расписание", "Добавить занятие", "Удалить занятие"),
            List.of(TIMETABLE_SHOW, TIMETABLE_ADD, TIMETABLE_REMOVE),
            List.of(1, 2)
    );
    public static final InlineKeyboardMarkup TIMETABLE_SHOW_INLINE_KEYBOARD = keyboardFactory.getInlineKeyboard(
            List.of("<Назад"),
            List.of(TIMETABLE),
            List.of(1)
    );
    public static final InlineKeyboardMarkup TIMETABLE_ADD_INLINE_KEYBOARD = keyboardFactory.getInlineKeyboard(
            List.of("<Назад"),
            List.of(TIMETABLE),
            List.of(1)
    );
    public static final InlineKeyboardMarkup TIMETABLE_REMOVE_INLINE_KEYBOARD = keyboardFactory.getInlineKeyboard(
            List.of("<Назад"),
            List.of(TIMETABLE),
            List.of(1)
    );

    //Task keyboards
    public static final InlineKeyboardMarkup TASK_MENU_INLINE_KEYBOARD = keyboardFactory.getInlineKeyboard(
            List.of("Прикрепить домашнее задание"),
            List.of(TASK_PIN),
            List.of(1)
    );
    public static final InlineKeyboardMarkup TASK_PIN_INLINE_KEYBOARD = keyboardFactory.getInlineKeyboard(
            List.of("<Назад"),
            List.of(TASK),
            List.of(1)
    );
}
