# Trivia

A trivia game using the openTriviaDatabase (OTDB) API and flask sqlite database interaction

Sources: https://opentdb.com/

# requirements

pip install flask

# functionality

The program requests all the categories from the OTDB and when a new game is started, prompts the user for a category, difficulty and number of questions.

Then, it will request questions from the OTDB, launching the actual game. Afterwards, the game posts the high score to an online database if a username was specified.

The other button on the main activity will go to the high score list, requesting the online database for the scores, and displays them in ascending order.

# screenshots

![Main activity](/data/screenshot0.jpg)
![prompt](/data/screenshot1.jpg)
![trivia game](/data/screenshot2.jpg)
![high score list](/data/screenshot3.jpg)
