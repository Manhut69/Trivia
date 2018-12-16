import os

from cs50 import SQL
from flask import Flask, request, Response, json
from werkzeug.exceptions import default_exceptions

from helpers import apology

# # Ensure environment variable is set
# if not os.environ.get("API_KEY"):
#     raise RuntimeError("API_KEY not set")

# Configure application
app = Flask(__name__)

# Configure CS50 Library to use SQLite database
db = SQL("sqlite:///highscores.db")

@app.route("/postscore", methods=["GET", "POST"])
def postscore():
    if request.method == "GET":
        # get arguments from url
        args = request.args.to_dict()
        username = request.args.get("username")
        score = request.args.get("score")

        # score "-1" is used for getting just the score
        if score != "-1":

            # check if user already in database
            checklist = []
            for i in db.execute("SELECT user FROM score"):
                checklist.append(i["user"])

            # create new entry and/or update score
            if username not in checklist:
                db.execute("INSERT INTO score (user,highscore) VALUES (:username, :score)", username=username, score=score)
            else:
                highscore = int(db.execute("SELECT highscore FROM score WHERE user = :username", username=username)[0]["highscore"])
                score = int(score) + highscore
                db.execute("UPDATE score SET highscore = :score WHERE user == :username", score=score, username=username)

        # create JSON to display
        json_object = json.dumps({"database" : db.execute("SELECT * FROM score ORDER BY highscore DESC")}, ensure_ascii = False)

    # make sure the JSON is UTF-8 compliant
    response = Response(json_object, content_type="application/json; charset=UTF-8" )
    return response


def errorhandler(e):
    """Handle error"""
    return apology(e.name, e.code)


# listen for errors
for code in default_exceptions:
    app.errorhandler(code)(errorhandler)
