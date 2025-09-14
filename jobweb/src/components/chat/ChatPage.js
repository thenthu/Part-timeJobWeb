import React, { useContext, useState } from "react";
import { Container, Row, Col } from "react-bootstrap";
import { MyUserContext } from "../../configs/Contexts";
import ConversationList from "./ConversationList";
import ChatBox from "./ChatBox";

export default function ChatPage() {
  const [user] = useContext(MyUserContext);
  const [selectedConversation, setSelectedConversation] = useState(null);

  if (!user) return <div className="text-center mt-5">Bạn cần đăng nhập để chat!</div>;
  return (
    <div style={{height: "590px"}}>
      <Container fluid className="h-100" style={{ maxWidth: 1200 }}>
        <Row className="h-100 shadow rounded mx-auto" style={{background: "#fff", overflow: "hidden"}}>
          <Col
            xs={12}
            md={4}
            lg={3}
            className="border-end bg-light p-0 h-100"
            style={{overflowY: "auto"}}
          >
            <ConversationList
              onSelect={setSelectedConversation}
              selectedId={selectedConversation?.id}
            />
          </Col>
          <Col
            xs={12}
            md={8}
            lg={9}
            className="bg-white p-0 h-100 d-flex flex-column"
          >
            {selectedConversation ? (
              <ChatBox
                username={user.username}
                otherUsername={selectedConversation.otherUsername}
                avatar={selectedConversation.avatar}
                companyName={selectedConversation.companyName}
                name={selectedConversation.name}
              />
            ) : (
              <div className="text-secondary d-flex align-items-center justify-content-center flex-grow-1" style={{ fontSize: 20 }}>
                Chọn một cuộc trò chuyện bên trái
              </div>
            )}
          </Col>
        </Row>
      </Container>
    </div>
  );
}