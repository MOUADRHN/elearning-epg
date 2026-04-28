<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
#chatbot-btn {
    position: fixed; bottom: 24px; right: 24px;
    width: 56px; height: 56px;
    background: #3498db; color: white;
    border: none; border-radius: 50%;
    font-size: 24px; cursor: pointer;
    box-shadow: 0 4px 16px rgba(0,0,0,0.2);
    z-index: 9999;
    display: flex; align-items: center; justify-content: center;
    transition: background 0.2s;
}
#chatbot-btn:hover { background: #2980b9; }

#chatbot-box {
    position: fixed; bottom: 90px; right: 24px;
    width: 360px; height: 480px;
    background: white; border-radius: 16px;
    box-shadow: 0 8px 32px rgba(0,0,0,0.18);
    z-index: 9998;
    display: none;
    flex-direction: column;
    overflow: hidden;
}

#chatbot-header {
    background: #2c3e50; color: white;
    padding: 14px 18px;
    display: flex; justify-content: space-between; align-items: center;
    font-weight: bold;
}
#chatbot-close {
    background: none; border: none; color: white;
    font-size: 20px; cursor: pointer; line-height: 1;
}

#chatbot-messages {
    flex: 1; overflow-y: auto;
    padding: 14px; display: flex;
    flex-direction: column; gap: 10px;
    background: #f8f9fa;
}

.msg-bot, .msg-user {
    max-width: 80%; padding: 10px 14px;
    border-radius: 12px; font-size: 13px;
    line-height: 1.5; word-break: break-word;
}
.msg-bot {
    background: white; color: #2c3e50;
    border: 1px solid #e2e8f0;
    align-self: flex-start;
    border-bottom-left-radius: 4px;
}
.msg-user {
    background: #3498db; color: white;
    align-self: flex-end;
    border-bottom-right-radius: 4px;
}
.msg-typing {
    background: white; color: #aaa;
    border: 1px solid #e2e8f0;
    align-self: flex-start;
    border-radius: 12px; border-bottom-left-radius: 4px;
    padding: 10px 14px; font-size: 13px;
}

#chatbot-input-area {
    padding: 12px; background: white;
    border-top: 1px solid #e2e8f0;
    display: flex; gap: 8px;
}
#chatbot-input {
    flex: 1; border: 1px solid #e2e8f0;
    border-radius: 8px; padding: 8px 12px;
    font-size: 13px; outline: none;
}
#chatbot-input:focus { border-color: #3498db; }
#chatbot-send {
    background: #3498db; color: white;
    border: none; border-radius: 8px;
    padding: 8px 14px; cursor: pointer;
    font-size: 16px;
}
#chatbot-send:hover { background: #2980b9; }
#chatbot-send:disabled { background: #bdc3c7; cursor: not-allowed; }
</style>

<!-- Bouton flottant -->
<button id="chatbot-btn" onclick="toggleChatbot()" title="Assistant EPG">
    <i class="bi bi-robot"></i>
</button>

<!-- Fenêtre chatbot -->
<div id="chatbot-box">
    <div id="chatbot-header">
        <span><i class="bi bi-robot me-2"></i>Assistant EPG</span>
        <button id="chatbot-close" onclick="toggleChatbot()">×</button>
    </div>
    <div id="chatbot-messages">
        <div class="msg-bot">
            Bonjour ! Je suis l'assistant EPG E-Learning.<br>
            Comment puis-je vous aider ?
        </div>
    </div>
    <div id="chatbot-input-area">
        <input type="text" id="chatbot-input"
               placeholder="Posez votre question..."
               onkeypress="if(event.key==='Enter') sendMessage()">
        <button id="chatbot-send" onclick="sendMessage()">
            <i class="bi bi-send"></i>
        </button>
    </div>
</div>

<script>
function toggleChatbot() {
    const box = document.getElementById('chatbot-box');
    box.style.display = box.style.display === 'flex' ? 'none' : 'flex';
    if (box.style.display === 'flex') {
        document.getElementById('chatbot-input').focus();
    }
}

async function sendMessage() {
    const input   = document.getElementById('chatbot-input');
    const msgs    = document.getElementById('chatbot-messages');
    const sendBtn = document.getElementById('chatbot-send');
    const text    = input.value.trim();
    if (!text) return;

    // Message utilisateur
    msgs.innerHTML += '<div class="msg-user">' + escapeHtml(text) + '</div>';
    input.value = '';
    sendBtn.disabled = true;

    // Indicateur de frappe
    const typing = document.createElement('div');
    typing.className = 'msg-typing';
    typing.id = 'typing-indicator';
    typing.textContent = '...';
    msgs.appendChild(typing);
    msgs.scrollTop = msgs.scrollHeight;

    try {
        const response = await fetch('/chatbot/message', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                '${_csrf.headerName}': '${_csrf.token}'
            },
            body: JSON.stringify({ message: text })
        });
        const data = await response.json();
        document.getElementById('typing-indicator').remove();
        msgs.innerHTML += '<div class="msg-bot">' + escapeHtml(data.reply) + '</div>';
    } catch (e) {
        document.getElementById('typing-indicator').remove();
        msgs.innerHTML += '<div class="msg-bot">Erreur de connexion.</div>';
    }

    sendBtn.disabled = false;
    msgs.scrollTop = msgs.scrollHeight;
    input.focus();
}

function escapeHtml(text) {
    return text.replace(/&/g,'&amp;')
               .replace(/</g,'&lt;')
               .replace(/>/g,'&gt;')
               .replace(/\n/g,'<br>');
}
</script>