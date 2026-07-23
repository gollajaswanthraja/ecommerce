/*async function askAI() {

    const question = document.getElementById("question").value;

    const response = await fetch("/api/ai/chat", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            question: question
        })

    });

    const data = await response.json();

    document.getElementById("answer").innerHTML = data.answer;

}*/

async function askAI() {

    const question = document.getElementById("question").value.trim();

    if (question === "") {
        return;
    }

    const chatBox = document.getElementById("chatBox");

    chatBox.innerHTML += `
        <div class="user-message">
            👤 ${question}
        </div>
    `;

    document.getElementById("question").value = "";

    const response = await fetch("/api/ai/chat", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            question: question
        })

    });

    const data = await response.json();

    chatBox.innerHTML += `
        <div class="bot-message">
            🤖 ${data.answer}
        </div>
    `;

    chatBox.scrollTop = chatBox.scrollHeight;
}