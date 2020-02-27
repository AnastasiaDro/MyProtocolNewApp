package com.mymur.myprotocolnewapp;

public class Constants {

    //Коды активностей
    public static final int SPLASH_ACTIVITY_CONSTANT = 10;
    public static final int HOME_ACTIVITY_CONSTANT = 20;
    public static final int PRACTICE_ACTIVITY_CONSTANT = 30;

    //Коды item-ов контекстного меню

    public static final int ABOUT_STUDENT_CONTEXTMENU_ITEM = 111;
    public static final int ABOUT_TRIAL_CONTEXTMENU_ITEM = 222;

    public static final int HIDE_CONTEXTMENU_ITEM = 333;

    //подсказки
    public static final int NO_HINT = 0;
    //всего девять типов подсказок:
    //1. полная физическая
    public static final int FULL_PSYSICAL_HINT = 1;
    //2. частичная физическая
    public static final int PARTIAL_PHYSICAL_HINT = 2;
    //3. подсказка жестом
    public static final int GESTURE_HINT = 3;
    //4. полная вербальная подсказка
    public static final int FULL_VERBAL_HINT = 4;
    //5. частичная вербальная подсказка
    public static final int PARTIAL_VERBAL_HINT = 5;
    //6. текстуальная или письменная подсказка
    public static final int WRITTEN_HINT = 6;
    //7. визуальная подсказка
    public static final int VISUAL_HINT = 7;
    //8. аудиторная подсказка - подсказка звуком, например будильник
    public static final int AUDIO_HINT = 8;
    //9. позиционная подсказка
    public static final int POSITIONAL_HINT = 9;
}
