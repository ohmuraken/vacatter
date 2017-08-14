#!flask/bin/python
import json
import os

import tweepy
from flask import Flask, jsonify, request, Response

app = Flask(__name__)

consumer_key = "UZ07BQX9FRvXK6f0pkRPkQr0D"
consumer_secret = "Dw7Dl7w0y0Je5YO07DwjukTliQMazE0sxKfZUnc5PHXovoZjXy"
access_token = "893758559548080128-4OsFtaNZAZ9UeXqctWeNpC4uuK9nrgL"
access_token_secret = "o8lS3n0D0qNJp8ij4ky4uA2AuRW3gKenhaz2zLjoGjF8Q"

dir_path = os.path.abspath(os.path.dirname(__file__))


def authorize(consumer_key, consumer_secret, access_token, access_token_secret):
    auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
    auth.set_access_token(access_token, access_token_secret)
    api = tweepy.API(auth)
    return api


# Response用のJsonを作る
def gen_tweet(id, media_urls, favorite_count, retweet_count, user_id, name, retweeted, favorited, text):
    return {
        "id": id,
        "media_urls": media_urls,
        "favorite_count": favorite_count,
        "retweet_count": retweet_count,
        "user_id": user_id,
        "name": name,
        "retweeted": retweeted,
        "favorited": favorited,
        "text": text,
        "face_change_urls": media_urls
    }


# media URLの抜き出し
def extract_media_url(media):
    res = []
    for m in media:
        res.append(m["media_url"])
    return res


@app.route('/vacatter/demo/api/v1/token', methods=['POST'])
def authorize_token():
    access_token = request.form["access_token"]
    access_token_secret = request.form["access_token_secret"]
    api = authorize(consumer_key, consumer_secret, access_token, access_token_secret)
    return jsonify(api.me()._json)


@app.route("/vacatter/demo/api/v1/timeline", methods=["GET"])
def get_timeline():
    res = []
    print(request.args["user_id"])
    api = authorize(consumer_key, consumer_secret, access_token, access_token_secret)
    # for status in api.home_timeline(count=20):
    for tweet_res in api.home_timeline(count=100, exclude_replies=True, include_entities=False):
        tweet = tweet_res._json

        if "extended_entities" not in tweet:
            continue
        res.append(gen_tweet(
            id=tweet['id_str'],
            media_urls=extract_media_url(tweet["extended_entities"]["media"]),
            favorite_count=tweet["favorite_count"],
            retweet_count=tweet["retweet_count"],
            user_id=tweet["user"]["id_str"],
            name=tweet["user"]["name"],
            retweeted=tweet["retweeted"],
            favorited=tweet["favorited"],
            text=tweet["text"]
        ))
    return Response(json.dumps(res, ensure_ascii=False), mimetype='application/json')


@app.route("/vacatter/demo/api/v1/face", methods=["POST"])
def post_face():
    print(request.form["user_id"])
    print(request.files["image"])
    return request.method


if __name__ == '__main__':
    app.run(debug=True)
