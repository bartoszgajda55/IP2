package com.quizapp.ip2.Helper;

/**
 * Created by Aaron on 13/04/2018.
 */

public enum QuizColor {

    RED("f44336"),
    ORANGE("ff9f43"),
    GREEN("2ecc71"),
    TURQUOISE("079992"),
    BLUE("2e86de"),
    PURPLE("6c5ce7"),
    PINK("fd79a8"),
    GRAY("535c68");

    private final String color;

    QuizColor(final String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }
}
