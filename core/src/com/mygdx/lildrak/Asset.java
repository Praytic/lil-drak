package com.mygdx.lildrak;

public interface Asset {

    enum Image implements Asset {

        BACKGROUND("splash.png"),
        INTRO("intro.png"),
        FADE("fade.png"),
        FADE_BLACK("fadeblack.png"),
        BLACK("black.png"),
        HEART("heart.png"),
        BAT1("bat1.png"),
        BAT2("bat2.png"),
        BAT3("bat3.png"),
        BAT4("bat4.png"),
        SKULL("skelly.png"),
        WHIP("whip.png"),
        WINDOW("window.png"),
        LOLLIPOP("lollipop.png"),
        CANDY("candy.png"),
        MONEY("money.png"),
        FLAME1("flame1.gif"),
        FLAME2("flame2.gif");

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

        MUSIC("music.ogg"),
        MUSIC_GAME("music_game.ogg"),
        PICKUP("pickup.ogg"),
        HURT("hurt.ogg");

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

        DEFAULT("font.fnt");

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