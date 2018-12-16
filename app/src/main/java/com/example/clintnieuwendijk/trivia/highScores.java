package com.example.clintnieuwendijk.trivia;

class highScores {
    private String username;
    private String highscore;

    highScores(String username, String highscore) {
        this.username = username;
        this.highscore = highscore;
    }

    String getUsername() {
        return username;
    }

    String getHighscore() {
        return highscore;
    }
}
