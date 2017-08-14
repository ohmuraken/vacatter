import os
import requests
import tweepy
import sqlite3
import json
import pandas as pd
from flask import Flask, jsonify, abort
from flask import g


app = Flask(__name__)
dir_path = os.path.abspath(os.path.dirname(__file__))
DATABASE = os.path.join(dir_path, 'database.sqlite3')

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

@app.teardown_appcontext
def close_connection(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()

if __name__ == "__main__":
    app.run(debug=True)
