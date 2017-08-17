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
import numpy as np
import cv2
import urllib


UPLOAD_FOLDER = os.path.join(os.path.dirname(os.path.realpath(__file__)), 'uploads/')
FACE_UPLOAD_FOLDER = os.path.join(os.path.dirname(os.path.realpath(__file__)), 'your_face/')
FACE_CHANGE_FOLDER = os.path.join(os.path.dirname(os.path.realpath(__file__)), 'face_change/')
FACE_CASCADE_DATA = os.path.join(os.path.dirname(os.path.realpath(__file__)), 'data/haarcascade_frontalface_default.xml')
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
app.config['FACE_CASCADE_DATA'] = FACE_CASCADE_DATA


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


# def url_to_image(url):
#     f = io.BytesIO(urllib.request.urlopen(url).read())
#     img = Image.open(f)
#     return img
def url_to_image(url):
    resp = urllib.request.urlopen(url)
    image = np.asarray(bytearray(resp.read()), dtype="uint8")
    image = cv2.imdecode(image, cv2.IMREAD_COLOR)
    return image

def make_face_change(user_id, media_urls, face_url, face_cascade):
    print("=============")
    face_change_urls = []
    print("face_url: {}".format(face_url))
    face_path = os.path.join(app.config["FACE_UPLOAD_FOLDER"], face_url.rsplit("/")[-1])
    face_img = cv2.imread(face_path)
    print("try to get face")
    for media_url in media_urls:
        if media_url != "none":
            base_img = url_to_image(media_url)
            base_img_gray = cv2.cvtColor(base_img, cv2.COLOR_BGR2GRAY)
            base_faces = face_cascade.detectMultiScale(base_img_gray, 1.3, 5)
            print("media_url: {}".format(media_url))
            print("base_face size: {}".format(len(base_faces)))
            if len(base_faces): # 顔が検出できた
                for (x, y, w, h) in base_faces:
                    # 顔交換
                    base_img[y:y+h, x:x+w] = cv2.resize(face_img, (h, w))
                    # 保存
                    filename = str(user_id) + "_" + media_url.rsplit("/", 1)[-1]
                    # file.save(os.path.join(app.config['FACE_CHANGE_FOLDER'], filename))
                    cv2.imwrite(os.path.join(app.config['FACE_CHANGE_FOLDER'], filename), base_img)
                    face_change_url = os.path.join("http://", request.host, "face_change", filename)
                    face_change_urls.append(face_change_url)
                return face_change_urls
            else: # 顔が検出できない
                face_change_urls.append("none")
                # face_change_urls.append(media_url) # こっちでもいいかも
        else:
            face_change_urls.append("none")
    return face_change_urls


def remake_json(tweet, face_url):
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

    if media_urls.count("none") != 4:
        print("check face")
        face_cascade =  cv2.CascadeClassifier(app.config['FACE_CASCADE_DATA'])
        print(type(face_cascade))
        face_change_urls = make_face_change(tweet._json["user"]["id"], media_urls, face_url, face_cascade) # TODO
    else:
        print("NO check face")
        face_change_urls = ["none", "none", "none", "none"]


    print("media_urls: {}".format(media_urls))
    print("face_change_urls: {}".format(face_change_urls))

    remaked_json = {
        "tweet_id": tweet._json["id"],
        "user_id": tweet._json["user"]["id"],
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
        user_id, access_token, acceess_token_secret, face_url = result[0]
        print("face_url: {}".format(face_url))
        conn.commit()
        conn.close()
        api = authorize(consumer_key, consumer_secret, access_token, access_token_secret)
        tweets = api.home_timeline(count=100)
        # res = [remake_json(tweet, face_url) for tweet in tweets if "extended_entities" in tweet._json.keys()]
        res = []
        for tweet in tweets:
            if "extended_entities" in tweet._json.keys():
                remaked_json = remake_json(tweet, face_url)
                if remaked_json["face_change_urls"].count("none") != 4:
                    res.append(remaked_json)
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
def uploaded_face(filename):
    return send_from_directory(app.config['FACE_UPLOAD_FOLDER'], filename)


@app.route('/face_change/<filename>', methods=['GET'])
def uploaded_face_change(filename):
    return send_from_directory(app.config['FACE_CHANGE_FOLDER'], filename)


@app.teardown_appcontext
def close_connection(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()

if __name__ == "__main__":
    app.run(debug=True)
