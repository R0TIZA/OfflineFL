package ru.rotiza.offlinefl.srvice.data;

public class Texts {

    //Info text (for help and start commands)
    public static final String INFO_TEXT = """
            Привет, это простой учебный бот и вот его возможности:\n
            /help, /h - комманды, благодаря котором можно вывести эту подсказку снова.\n
            /echo [Ваш текст] - бот напишет в ответ все что вы введете после этой команды или \"И в ответ тишина\", если отправить команду без текста.\n
            /feedback - получить ссылки на соц. сети для обратной связи.\n
            /timetable - открыть меню настройки расписания занятий.\n
            /task - открыть меню назначения домашних заданий ученикам.\n
            """;
    //Feedback text
    public static final String FEEDBACK_TEXT = """
            Ссылки для обратной связи:
            VK - https://vk.com/r0tiza
            TG - https://t.me/R0TIZA
            """;
    //Error texts
    public static final String NO_COMMAND_TEXT = "У этого бота пока нет такой команды.";
    public static final String NO_CALLBACK_QUERY_TEXT = "Извините, наш разработчик забыл задать поведение этой кнопке, поэтому она не работает :(";
    public static final String EMPTY_ECHO_ARGS_TEXT = "А в ответ тишина.";

    //Timetable texts
    public static final String TIMETABLE_MENU_TEXT = "Здесь вы можете управлять своим расписанием.";
    public static final String TIMETABLE_SHOW_TEXT = "Выберете день недели.";
    public static final String TIMETABLE_ADD_TEXT = "Выберете день, когда хотите назначить занятие.";
    public static final String TIMETABLE_REMOVE_TEXT = "Выберете день, когда хотите отменить занятие.";

    //Task texts
    public static final String TASK_MENU_TEXT = "Вы можете добавить домашнее задание ученику.";
    public static final String TASK_PIN_TEXT = "Для назначения домашнего задания, выберите ученика.";
}
