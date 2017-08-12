import os
import sqlite3
import requests
import tweepy

os.path.abspath(__file__)
dir_path = os.path.abspath(os.path.dirname(__file__))
dbname = os.path.join(dir_path, 'database.sqlite3')
conn = sqlite3.connect(dbname)
c = conn.cursor()

create_table = '''CREATE TABLE timelines (
                    id integer, user_id integer, text text, name text,    screen_name text,
                    media_count integer, media_url1 text, media_url2 text, media_url3 text, media_url4 text,
                    face_change_url1 text, face_change_url2 text, face_change_url3 text, face_change_url4 text,
                    favorite_count integer, retweet_count integer,
                    favorited integer, retweeted integer
                )'''
c.execute(create_table)

# twitterの認証とツイートの取得
consumer_key = "UZ07BQX9FRvXK6f0pkRPkQr0D"
consumer_secret = "Dw7Dl7w0y0Je5YO07DwjukTliQMazE0sxKfZUnc5PHXovoZjXy"
access_token = "893758559548080128-4OsFtaNZAZ9UeXqctWeNpC4uuK9nrgL"
access_token_secret = "o8lS3n0D0qNJp8ij4ky4uA2AuRW3gKenhaz2zLjoGjF8Q"
auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
api = tweepy.API(auth)
tweets = api.home_timeline(count=100)

# apiで取得したツイートから必要な情報に整形しDBに格納していく
for tweet in tweets:
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

    sql = '''INSERT INTO timelines (id , user_id , text , name , screen_name ,
                                    media_count, media_url1, media_url2, media_url3, media_url4,
                                    favorite_count, retweet_count,favorited, retweeted)
                                    values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)'''
    tweet_data = (tweet._json["id"], tweet._json["user"]["id"], tweet._json["text"],
                  tweet._json["user"]["name"],tweet._json["user"]["screen_name"],
                  media_count, media_urls[0], media_urls[1], media_urls[2], media_urls[3],
                  tweet._json["favorite_count"], tweet._json["retweet_count"],
                  favorited_flag, retweeted_flag)
    c.execute(sql, tweet_data)

conn.commit()
conn.close()
