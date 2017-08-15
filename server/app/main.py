import os
import requests
import tweepy
import sqlite3
import json
import pandas as pd
from flask import Flask, jsonify, abort, redirect
from flask import g, request, Response, flash,  url_for
from werkzeug.utils import secure_filename
from flask import send_from_directory


UPLOAD_FOLDER = os.path.join(os.path.dirname(os.path.realpath(__file__)), 'uploads/')
FACE_UPLOAD_FOLDER = os.path.join(os.path.dirname(os.path.realpath(__file__)), 'your_face/')
FACE_CHANGE_FOLDER = os.path.join(os.path.dirname(os.path.realpath(__file__)), 'face_change/')
ALLOWED_EXTENSIONS = set(['txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif'])

consumer_key = "UZ07BQX9FRvXK6f0pkRPkQr0D"
consumer_secret = "Dw7Dl7w0y0Je5YO07DwjukTliQMazE0sxKfZUnc5PHXovoZjXy"
access_token = "893758559548080128-4OsFtaNZAZ9UeXqctWeNpC4uuK9nrgL"
access_token_secret = "o8lS3n0D0qNJp8ij4ky4uA2AuRW3gKenhaz2zLjoGjF8Q"

app = Flask(__name__)
dir_path = os.path.abspath(os.path.dirname(__file__))
DATABASE = os.path.join(dir_path, 'database.sqlite3')
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
app.config['FACE_UPLOAD_FOLDER'] = FACE_UPLOAD_FOLDER
app.config['FACE_CHANGE_FOLDER'] = FACE_CHANGE_FOLDER


def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


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


def make_face_change(media_urls):
    pass # TODO


def remake_json(tweet):
    # tweet内の画像のURLを取得する.
    if "extended_entities" in tweet._json.keys(): # 画像がある(最大4枚)
        media_urls = [media["media_url_https"] for media in tweet._json["extended_entities"]["media"]]
        media_count = len(media_urls)
        media_urls += ["none"] * (4-media_count)
    else: # 画像を持たない
        media_count = 0
        media_urls = ["none"] * 4

    # いいねのフラグ
    if tweet._json["favorited"]: favorited_flag = 1
    else: favorited_flag = 0

    # リツイートのフラグ
    if tweet._json["retweeted"]: retweeted_flag = 1
    else: retweeted_flag = 0

    # リツイートで出現したコンテンツ
    if "retweeted_status" in tweet._json.keys():
        retweeted_status = 1
        mention_name = tweet._json["entities"]["user_mentions"][0]["name"]
        mention_id = tweet._json["entities"]["user_mentions"][0]["id"]
        mention_screen_name = tweet._json["entities"]["user_mentions"][0]["screen_name"]
    else:
        retweeted_status = 0
        mention_id = "none"
        mention_name = "none"
        mention_screen_name = "none"

    # 顔変換 TODO
    face_change_count = 0
    face_change_urls = ["none", "none", "none", "none"]
    # face_change_urls = make_face_change(media_urls) # TODO

    remaked_json = {
        "name": tweet._json["user"]["name"],
        "screen_name": tweet._json["user"]["screen_name"],
        "text": tweet._json["text"],
        "media_count": media_count,
        "media_urls": media_urls,
        "face_change_count": face_change_count,
        "face_change_urls": face_change_urls,
        "retweeted_status": retweeted_status,
        "mention_name": mention_name,
        "mention_screen_name": mention_screen_name,
        "mention_id": mention_id,
        "favorite_count": tweet._json["favorite_count"],
        "retweet_count": tweet._json["retweet_count"],
        "favorited": favorited_flag,
        "retweeted": retweeted_flag
    }
    return remaked_json


@app.route("/vacatter/api/v1.0/token", methods=["POST"])
def authorize_token():
    access_token = request.json["access_token"]
    access_token_secret = request.json["access_token_secret"]
    user_id = request.json["user_id"]

    conn = get_db()
    c = conn.cursor()
    sql = "SELECT * FROM users WHERE user_id={}".format(user_id)
    df = pd.read_sql_query(sql, conn)
    if len(df): # 既に過去にログイン
        sql = "UPDATE users SET user_id=?, access_token=?, access_token_secret=? WHERE user_id=?"
        c.execute(sql, (user_id, access_token, access_token_secret, user_id))
        conn.commit()
        conn.close()
        return "update token"
    else: # 初回ログイン
        sql = "INSERT INTO users (user_id, access_token, access_token_secret) values (?, ?, ?)"
        c.execute(sql, (user_id, access_token, access_token_secret))
        conn.commit()
        conn.close()
        return "fist login"


@app.route("/vacatter/api/v1.0/timeline", methods=["GET"])
def user_timeline():
    user_id = request.args.get("user_id")
    conn = get_db()
    c = conn.cursor()
    sql = "SELECT * FROM users WHERE user_id=?"
    c.execute(sql, (user_id,))
    result = c.fetchall()
    if len(result):
        user_id, access_token, acceess_token_secret = result[0][:-1]
        conn.commit()
        conn.close()
        api = authorize(consumer_key, consumer_secret, access_token, access_token_secret)
        tweets = api.home_timeline(count=100)
        res = [remake_json(tweet) for tweet in tweets if "extended_entities" in tweet._json.keys()]
        return jsonify(res)
    else:
        return "no such user"


@app.route('/vacatter/api/v1.0/face', methods=['GET', 'POST'])
def upload_face():
    if request.method == 'POST':
        user_id = request.form["user_id"]
        # check if the post request has the file part
        if 'image' not in request.files:
            return redirect(request.url)

        file = request.files['image']
        if file.filename == '':
            return 'No selected file'
        if file and allowed_file(file.filename):
            filename = user_id + "_" + secure_filename(file.filename)
            file.save(os.path.join(app.config['FACE_UPLOAD_FOLDER'], filename))
            face_url = os.path.join("http://", request.host, "your_face", filename)

            conn = get_db()
            c = conn.cursor()
            sql = "UPDATE users SET face_url=? WHERE user_id=?"
            c.execute(sql, (face_url, user_id))
            conn.commit()
            conn.close()
            return json.dumps({"face_url": face_url})

    return '''
        <!doctype html>
        <title>Upload new File</title>
        <h1>Upload new File</h1>
        <form method=post enctype=multipart/form-data>
            <input type=file name=image>
            <input type=submit value=Upload>
        </form>
    '''

@app.route('/vacatter/api/v1.0/face_url', methods=['GET'])
def face_url():
    user_id = request.args.get("user_id")
    conn = get_db()
    c = conn.cursor()
    sql = "SELECT * FROM users WHERE user_id=?"
    c.execute(sql, (user_id,))
    result = c.fetchall()
    if len(result):
        face_url = result[0][-1]
        filename = face_url.rsplit("/")[-1]
        conn.commit()
        conn.close()
        return json.dumps({"face_url": face_url})
    else:
        conn.commit()
        conn.close()
        return "no face"


@app.route('/your_face/<filename>', methods=['GET'])
def uploaded_file(filename):
    return send_from_directory(app.config['FACE_UPLOAD_FOLDER'], filename)


@app.teardown_appcontext
def close_connection(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()

if __name__ == "__main__":
    app.run(debug=True)
