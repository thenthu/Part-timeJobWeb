import React, { useEffect, useRef, useState } from "react";
import { listenMessages, sendMessage } from "./ChatService";
import { Card, Form, Button, Image, InputGroup } from "react-bootstrap";

function getConversationId(user1, user2) {
    return [user1, user2].sort().join("--");
}

export default function ChatBox({ username, otherUsername, avatar, companyName, name }) {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState("");
    const bottomRef = useRef();

    const conversationId = getConversationId(username, otherUsername);

    useEffect(() => {
        if (!username || !otherUsername) return;
        const stop = listenMessages(conversationId, setMessages);
        return stop;
    }, [conversationId, username, otherUsername]);

    useEffect(() => {
        bottomRef.current?.scrollIntoView({ behavior: "smooth" });
    }, [messages]);

    const handleSend = async () => {
        if (input.trim()) {
            try {
                await sendMessage(conversationId, username, input);
                setInput("");
            } catch (err) {
                alert("Lỗi gửi tin nhắn: " + err.message);
                console.error(err);
            }
        }
    };

    return (
        <Card className="h-100 d-flex flex-column border-0 rounded-0 bg-white">
            <Card.Header className="d-flex align-items-center bg-white border-bottom" style={{ minHeight: 70 }}>
                <Image
                    src={avatar || `https://ui-avatars.com/api/?name=${encodeURIComponent(companyName || name || otherUsername)}`}
                    roundedCircle
                    width={48}
                    height={48}
                    className="me-3 border"
                    alt="avatar"
                    style={{ objectFit: "cover", background: "#fafafa" }}
                />
                <div>
                    {companyName && <div className="fw-bold">{companyName}</div>}
                    {name && !companyName && <div className="fw-bold">{name}</div>}
                    <div className="text-muted">{otherUsername}</div>
                </div>
            </Card.Header>
            <Card.Body className="flex-grow-1 px-4 py-3 bg-light" style={{ overflowY: "auto" }}>
                {messages.map(msg => (
                    <div
                        key={msg.id}
                        className={`d-flex mb-2 ${msg.sender === username ? "justify-content-end" : "justify-content-start"}`}
                    >
                        <div
                            className={
                                "px-3 py-2 rounded-3 shadow-sm " +
                                (msg.sender === username
                                    ? "bg-primary text-white"
                                    : "bg-white text-dark border"
                                )
                            }
                            style={{
                                maxWidth: "70%",
                                wordBreak: "break-word",
                                borderTopRightRadius: msg.sender === username ? 0 : undefined,
                                borderTopLeftRadius: msg.sender === username ? undefined : 0,
                            }}
                        >
                            {msg.text}
                        </div>
                    </div>
                ))}
                <div ref={bottomRef} />
            </Card.Body>
            <Card.Footer className="bg-white border-top py-3">
                <Form
                    onSubmit={e => {
                        e.preventDefault();
                        handleSend();
                    }}
                >
                    <InputGroup>
                        <Form.Control
                            value={input}
                            onChange={e => setInput(e.target.value)}
                            placeholder="Nhập tin nhắn..."
                            className="me-2 rounded-pill bg-light"
                            autoComplete="off"
                            style={{ minHeight: 40 }}
                            onKeyDown={e => {
                                if (e.key === "Enter" && !e.shiftKey) {
                                    e.preventDefault();
                                    handleSend();
                                }
                            }}
                        />
                        <Button
                            variant="primary"
                            type="submit"
                            className="rounded-pill px-4"
                            disabled={!input.trim()}
                        >
                            Gửi
                        </Button>
                    </InputGroup>
                </Form>
            </Card.Footer>
        </Card>
    );
}