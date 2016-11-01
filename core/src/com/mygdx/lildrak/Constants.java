package com.mygdx.lildrak;

public class Constants
{
    public static int VIEWPORT_WIDTH = 4;
    public static int VIEWPORT_HEIGHT = 3;
    public static float METER_TO_PIXEL = 1/320f;
    public static float DEGTORAD = 0.0175f;
    public static float TIME_STEP = 1/60f;
    public static int VELOCITY_ITERATIONS = 6;
    public static int POSITION_ITERATIONS = 2;
    public static short BACKGROUND_INDEX = -2; // non-collide fixture index group

    public static int WINDOW_Z = -1000;
    public static int WHIP_Z = -100;
    public static int COLLECTIBLE_Z = -10;
    public static int BAT_Z = 0;
    public static int SKULL_Z = 1000;

    public static float WORLD_GRAVITY = -5f;
    public static float MOVE_FORCE = 170F;
    public static int PLAYER_HEALTH = 3;
    public static float LEVEL_TIME = 10f;
    public static int MAX_LEVEL = 4;
    public static float SPAWN_RATE = 2.5f;
    public static float SPAWN_RATE_MODIFIER = 0.15f; // all spawn rate decreases as difficulty increases to fit the speed increase
    public static float SPAWN_RATE_DIFFICULTY_INCREMENT = 0.5f; // spawn obstacles faster at higher difficulties
    public static float COLLECTIBLE_SPAWN_RATE = 0.5f;
    public static float SCROLL_SPEED = 0.7f;
    public static float SCROLL_SPEED_MODIFIER = 1.15f; // scroll faster at higher levels
    public static float COLLECTIBLE_CHANCE = 1/3f;
}
