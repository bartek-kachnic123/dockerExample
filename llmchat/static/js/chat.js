const chatBox = document.getElementById('chatBox');

async function sendMessage() {
  const inputField = document.getElementById('userInput');
  const message = inputField.value.trim();
  if (!message) return;

  const startOption = document.querySelector('input[name="startOption"]:checked');
  const startText = startOption ? startOption.value + ' ' : '';

  const endOption = document.querySelector('input[name="endOption"]:checked');
  const endText = endOption ? ' ' + endOption.value : '';

  const fullPrompt = `${startText}${message}${endText}`;

  chatBox.textContent += `You: ${message}\nAI: `;

  const response = await fetch('/api/chat', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ message: fullPrompt })
  });

  if (!response.ok) {
    chatBox.textContent += `[Error: ${response.statusText}]\n\n`;
    return;
  }

  const reader = response.body.getReader();
  const decoder = new TextDecoder();

  while (true) {
    const { done, value } = await reader.read();
    if (done) break;

    const chunk = decoder.decode(value, { stream: true });
    const lines = chunk.split('\n');

    for (const line of lines) {
      if (line.startsWith('data: ')) {
        const text = line.replace('data: ', '');
        if (text.startsWith('[ERROR]')) {
          chatBox.textContent += `${text}\n\n`;
          return;
        }
        chatBox.textContent += text;
        chatBox.scrollTop = chatBox.scrollHeight;
      }
    }
  }

  chatBox.textContent += '\n\n';
  inputField.value = '';
}
