#!flask/bin/python
from flask import Flask, jsonify

app = Flask(__name__)

tweets = [
    {
        'id': '894607123866296320',
        'media_urls': [
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg'
        ],
        'favorite_count': 40,
        'retweet_count': 34,
        'user_id': '471707277',
        'name': '真奈',
        'retweeted': False,
        'favorited': True,
        'text': 'だあああああ',
        'face_change_urls': [
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg'
        ],
    },
    {
        'id': '894607123866296322',
        'media_urls': [
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg'
        ],
        'favorite_count': 43,
        'retweet_count': 34,
        'user_id': '471707277',
        'name': '真奈',
        'retweeted': True,
        'favorited': False,
        'text': 'なああああああああ',
        'face_change_urls': [
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',
            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg'
        ]
    }
]

@app.route('/api/v1/tweets', methods=['GET'])
def get_tweets():
    return jsonify(tweets)

if __name__ == '__main__':
    app.run(debug=True)
