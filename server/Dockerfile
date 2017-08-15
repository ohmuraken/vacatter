FROM tiangolo/uwsgi-nginx-flask:flask-python3.5

COPY ./app /app
WORKDIR /app
RUN pip install -r requirements.txt
RUN python setup_db.py
