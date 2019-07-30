package com.example.clintnieuwendijk.trivia;

class highScores {
    /*
        a simple highscore display class for the high score adapter
        it has getters and a constructor to work with the highScoreAdapter class
     */

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
