package com.mygdx.lildrak;

public class Constants {
    public static float VIEWPORT_WIDTH = 4f;
    public static float VIEWPORT_HEIGHT = 3f;
    public static float METER_TO_PIXEL = 1 / 320f;
    public static float DEG_TO_RAD = 0.0175f;
    public static float TIME_STEP = 1 / 60f;
    public static int VELOCITY_ITERATIONS = 6;
    public static int POSITION_ITERATIONS = 2;
    public static short BACKGROUND_INDEX = -2; // non-collide fixture index group

    public static int WINDOW_Z = -1000;
    public static int WHIP_Z = -100;
    public static int COLLECTIBLE_Z = -10;
    public static int BAT_Z = 0;
    public static int SKULL_Z = 1000;

    public static float WORLD_GRAVITY = -4f;
    public static float MOVE_FORCE = 100f;
    public static int PLAYER_HEALTH = 3;
    public static float LEVEL_TIME = 10f;
    public static int MAX_LEVEL = 10;
    public static float SPAWN_RATE = 1.5f;
    public static float SPAWN_RATE_MODIFIER = 0.05f; // all spawn rate decreases as difficulty increases to fit the speed increase
    public static float SPAWN_RATE_DIFFICULTY_INCREASE = 0.2f; // spawn obstacles faster at higher difficulties
    public static float SCROLL_SPEED = 0.7f;
    public static float SCROLL_SPEED_INCREASE = 0.05f; // scroll faster at higher levels

    public static float COLLECTIBLE_SPAWN_RATE = 0.5f;
    public static float COLLECTIBLE_CANDY_SPAWN_RATE = 0.5f;
    public static float COLLECTIBLE_LOLLIPOP_SPAWN_RATE = 0.3f;
    public static float COLLECTIBLE_MONEY_SPAWN_RATE = 0.1f;
    public static float COLLECTIBLE_FLAME_SPAWN_RATE = 0.1f;
}
