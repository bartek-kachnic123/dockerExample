from flask import Flask
from flask_cors import CORS
from chat import chat_bp


def create_app():
    flask_app = Flask(__name__)
    CORS(flask_app)

    flask_app.register_blueprint(chat_bp)

    return flask_app


if __name__ == '__main__':
    app = create_app()
    app.run(debug=True)
