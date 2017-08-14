import os
import requests
import tweepy
import sqlite3
import json
import pandas as pd
from flask import Flask, jsonify, abort
from flask import g, request


consumer_key = "UZ07BQX9FRvXK6f0pkRPkQr0D"
consumer_secret = "Dw7Dl7w0y0Je5YO07DwjukTliQMazE0sxKfZUnc5PHXovoZjXy"
access_token = "893758559548080128-4OsFtaNZAZ9UeXqctWeNpC4uuK9nrgL"
access_token_secret = "o8lS3n0D0qNJp8ij4ky4uA2AuRW3gKenhaz2zLjoGjF8Q"

app = Flask(__name__)
dir_path = os.path.abspath(os.path.dirname(__file__))
DATABASE = os.path.join(dir_path, 'database.sqlite3')

def authorize(consumer_key, consumer_secret, access_token, access_token_secret):
    auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
    auth.set_access_token(access_token, access_token_secret)
    api = tweepy.API(auth)
    return api

def get_db():
    db = getattr(g, '_database', None)
    if db is None:
        db = g._database = sqlite3.connect(DATABASE)
    return db

@app.route("/vacatter/api/v1.0/home_timeline", methods=["GET"])
def get_timeline():
    conn = get_db()
    c = conn.cursor()
    sql = "SELECT * from timelines"
    df = pd.read_sql_query(sql, conn)
    conn.close()

    media_urls_set = []
    for urls in df["media_urls"]:
        media_urls_set.append([url.replace("'", "").replace(" ", "") for url in urls[1:-1].split(",")])
    df["media_urls"] = media_urls_set

    face_change_urls_set = []
    for urls in df["face_change_urls"]:
        face_change_urls_set.append([url.replace("'", "").replace(" ", "") for url in urls[1:-1].split(",")])
    df["face_change_urls"] = face_change_urls_set

    df_json_str = df.to_json(orient="records")
    df_json = json.loads(df_json_str)
    return jsonify(df_json)

@app.route("/vacatter/api/v1.0/token", methods=["POST"])
def authorize_token():
    access_token = request.json["access_token"]
    access_token_secret = request.json["access_token_secret"]
    api = authorize(consumer_key, consumer_secret, access_token, access_token_secret)
    return jsonify(api.me()._json)

@app.teardown_appcontext
def close_connection(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()

if __name__ == "__main__":
    app.run(debug=True)
