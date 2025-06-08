from flask import request, render_template, Response
from chat.ollama_service import generate_response_stream
from chat import chat_bp
import json


@chat_bp.route('/')
async def index():
    return render_template('index.html')


@chat_bp.route('/api/chat', methods=['POST'])
async def chat():
    user_input = request.json.get('message', '')

    def event_stream():
        try:
            for chunk in generate_response_stream(user_input):
                data = json.loads(chunk)
                text = data.get('response', '')
                yield f"data: {text}\n\n"
        except Exception as e:
            yield f"data: [ERROR] {str(e)}\n\n"

    return Response(event_stream(), content_type='text/event-stream')
