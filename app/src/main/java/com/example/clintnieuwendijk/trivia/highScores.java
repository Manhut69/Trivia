package com.example.clintnieuwendijk.trivia;

public class highScores {
    String username;
    String highscore;

    public highScores(String username, String highscore) {
        this.username = username;
        this.highscore = highscore;
    }

    public String getUsername() {
        return username;
    }

    public String getHighscore() {
        return highscore;
    }
}
