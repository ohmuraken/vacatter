import os
import sqlite3


def initialize_user_table():
    os.path.abspath(__file__)
    dir_path = os.path.abspath(os.path.dirname(__file__))
    dbname = os.path.join(dir_path, 'database.sqlite3')
    conn = sqlite3.connect(dbname)
    c = conn.cursor()
    create_table = "CREATE TABLE users (user_id integer, access_token text, access_token_secret text, face_url text)"
    c.execute(create_table)
    conn.commit()
    conn.close()


if __name__ == "__main__":
    initialize_user_table()
