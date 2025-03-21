package ru.rotiza.offlinefl.srvice.data;

public class Commands {

    //Simple commands
    public static final String START = "start";
    public static final String H = "h";
    public static final String HELP = "help";
    public static final String INFO = "info";
    public static final String FEEDBACK = "feedback";

    //Commands with arguments
    public static final String ECHO = "echo";

    //Group of Timetable commands
    public static final String TIMETABLE = "timetable";
    public static final String TIMETABLE_SHOW = "timetable_show";
    public static final String TIMETABLE_ADD = "timetable_add";
    public static final String TIMETABLE_REMOVE = "timetable_remove";

    //Group of Task commands
    public static final String TASK = "task";
    public static final String TASK_PIN = "task_pin";

    //Group of ProgressControl commands
    public static final String PROGRESS_CONTROL = "progress";
    public static final String PROGRESS_CONTROL_STAT = "progress_stat";

    //Group of Auth commands
    public static final String AUTH = "auth";
    public static final String AUTH_TEACHER = "auth_teacher";
    public static final String AUTH_STUDENT = "auth_student";

    //Group of Profile commands
    public static final String PROFILE = "profile";
}
