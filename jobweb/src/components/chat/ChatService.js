import { db } from "../../configs/Firebase";
import { ref, push, serverTimestamp, onValue, off, get } from "firebase/database";

export async function sendMessage(conversationId, sender, text) {
    const messagesRef = ref(db, `conversations/${conversationId}/messages`);
    try {
        const result = await push(messagesRef, {
            sender,
            text,
            timestamp: serverTimestamp(),
        });
        return result;
    } catch (err) {
        console.error("Push error:", err);
        throw err;
    }
}

export function listenMessages(conversationId, callback) {
    const messagesRef = ref(db, `conversations/${conversationId}/messages`);
    const handle = onValue(messagesRef, (snapshot) => {
        const data = snapshot.val();
        const messages = data
            ? Object.entries(data)
                .map(([id, value]) => ({ id, ...value }))
                .sort((a, b) => (a.timestamp || 0) - (b.timestamp || 0))
            : [];
        callback(messages);
    });
    return () => off(messagesRef, "value", handle);
}

export function listenConversations(username, callback) {
    const convsRef = ref(db, `conversations`);
    const handle = onValue(convsRef, (snapshot) => {
        const data = snapshot.val();
        const conversations = [];
        if (data) {
            Object.entries(data).forEach(([convId, value]) => {
                const parts = convId.split("--");
                if (parts.includes(username)) {
                    let lastMsg = "";
                    if (value.messages) {
                        const msgs = Object.values(value.messages);
                        lastMsg = msgs.length > 0 ? msgs[msgs.length - 1].text : "";
                    }
                    const otherUsername = parts.find(u => u !== username);
                    conversations.push({
                        id: convId,
                        otherUsername,
                        lastMsg,
                    });
                }
            });
        }
        callback(conversations);
    });
    return () => off(convsRef, "value", handle);
}