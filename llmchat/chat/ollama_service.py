import requests

OLLAMA_URL = "http://localhost:11434/api/generate"
MODEL_NAME = "llama2"


def generate_response_stream(prompt):
    response = requests.post(
        OLLAMA_URL,
        json={
            "model": MODEL_NAME,
            "prompt": prompt,
            "stream": True
        },
        stream=True
    )

    if response.status_code != 200:
        raise Exception("Ollama API request failed")

    for line in response.iter_lines():
        if line:
            yield line.decode('utf-8')
