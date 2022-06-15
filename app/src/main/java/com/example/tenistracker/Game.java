package com.example.tenistracker;

import android.graphics.Bitmap;

public class Game {
    private int score_1;
    private int score_2;
    private String streetName;
    private Bitmap photo;

    public Game(int score_1, int score_2, String streetName, Bitmap photo){
        this.score_1 = score_1;
        this.score_2 = score_2;
        this.streetName = streetName;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Game{" +
                "score_1=" + score_1 +
                ", score_2=" + score_2 +
                ", streetName='" + streetName + '\'' +
                ", photo=" + photo +
                '}';
    }
}
