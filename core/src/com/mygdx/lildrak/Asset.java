package com.mygdx.lildrak;

public interface Asset {

    enum Image implements Asset {

        BACKGROUND("data/splash.png"),
        FADE("data/fade.png"),
        FADE_BLACK("data/fadeblack.png"),
        BLACK("data/black.png"),
        HEART("data/heart.png"),
        BAT1("data/bat1.png"),
        BAT2("data/bat2.png"),
        BAT3("data/bat3.png"),
        BAT4("data/bat4.png"),
        SKULL("data/skelly.png"),
        WHIP("data/whip.png"),
        WINDOW("data/window.png"),
        LOLLIPOP("data/lollipop.png"),
        CANDY("data/candy.png"),
        MONEY("data/money.png"),
        FLAME1("data/flame1.gif"),
        FLAME2("data/flame2.gif");

        private String fileName;

        Image(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public String getFileName() {
            return fileName;
        }
    }

    enum Sound implements Asset {

        MUSIC("data/music.ogg"),
        MUSIC_GAME("data/music_game.ogg"),
        PICKUP("data/pickup.ogg"),
        HURT("data/hurt.ogg");

        private String fileName;

        Sound(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public String getFileName() {
            return fileName;
        }
    }

    enum Font implements Asset {

        DEFAULT("data/font.fnt");

        private String fileName;

        Font(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public String getFileName() {
            return fileName;
        }
    }

    String getFileName();
}