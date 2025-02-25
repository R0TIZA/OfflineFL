package ru.rotiza.offlinefl.srvice.data;

public class Texts {

    public static final String INFO_TEXT = """
            Привет, это простой учебный бот и вот его возможности:\n
            /help, /h - комманды, благодаря котором можно вывести эту подсказку снова.\n
            /echo [Ваш текст] - бот напишет в ответ все что вы введете после этой команды или \"И в ответ тишина\", если отправить команду без текста.\n
            /feedback - получить ссылки на соц. сети для обратной связи.\n
            """;
    public static final String FEEDBACK_TEXT = """
            Ссылки для обратной связи:
            VK - https://vk.com/r0tiza
            TG - https://t.me/R0TIZA
            """;
    public static final String NO_COMMAND_TEXT = "У этого бота пока нет такой команды.";
    public static final String NO_CALLBACK_QUERY_TEXT = "Дурень, кнопку сделал, а поведение не задал. Иди доделывай!";
}
