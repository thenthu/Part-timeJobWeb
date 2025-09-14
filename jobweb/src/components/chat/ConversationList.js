import React, { useEffect, useState, useContext } from "react";
import { listenConversations } from "./ChatService";
import { authApis, endpoints } from "../../configs/Apis";
import { Form, InputGroup, ListGroup, Spinner, Image } from "react-bootstrap";
import { MyUserContext } from "../../configs/Contexts";

export default function ConversationList({ onSelect, selectedId }) {
    const [user] = useContext(MyUserContext);

    const [list, setList] = useState([]);
    const [profileMap, setProfileMap] = useState({});
    const [search, setSearch] = useState("");
    const [searchResults, setSearchResults] = useState([]);
    const [searchLoading, setSearchLoading] = useState(false);

    useEffect(() => {
        if (!user) return;
        const stop = listenConversations(user.username, setList);
        return stop;
    }, [user]);

    useEffect(() => {
        if (!user || list.length === 0) {
            setProfileMap({});
            return;
        }
        const usernames = list.map(conv => conv.otherUsername);
        if (usernames.length === 0) {
            setProfileMap({});
            return;
        }

        const fetchProfiles = async () => {
            try {
                let url;
                if (user.role?.roleName === "ROLE_EMPLOYER") {
                    url = endpoints["candidates"];
                } else {
                    url = endpoints["employers"];
                }
                url += url.includes("?")
                    ? `&usernames=${encodeURIComponent(usernames.join(","))}`
                    : `?usernames=${encodeURIComponent(usernames.join(","))}`;

                const res = await authApis().get(url);
                const map = {};
                (res.data || []).forEach(profile => {
                    map[profile.username] = profile;
                });
                setProfileMap(map);
            } catch {
                setProfileMap({});
            }
        };
        fetchProfiles();
    }, [user, list]);

    const handleSearch = async (e) => {
        const value = e.target.value;
        setSearch(value);
        if (value.length < 2) {
            setSearchResults([]);
            return;
        }
        setSearchLoading(true);
        try {
            let url;
            if (user.role?.roleName === "ROLE_EMPLOYER") {
                url = endpoints["candidates"];
            } else {
                url = endpoints["employers"];
            }
            url += url.includes("?") ? `&kw=${encodeURIComponent(value)}` : `?kw=${encodeURIComponent(value)}`;
            const res = await authApis().get(url);
            console.log("Search results:", res.data);
            console.log("Search URL:", url);
            setSearchResults(res.data || []);
        } catch(e) {
            console.error("Search error:", e);
            setSearchResults([]);
        } finally {
            setSearchLoading(false);
        }
    };

    const handleSelectContact = (contact) => {
        const otherUsername = contact.username;
        const id = [user.username, otherUsername].sort().join("--");
        onSelect({
            id,
            otherUsername,
            avatar: contact.avatar,
            name: contact.companyName || contact.fullName || contact.username,
        });
        setSearch("");
        setSearchResults([]);
    };

    return (
        <div>
            <h5 className="fw-bold px-3 pt-3 mb-3">Đoạn chat</h5>
            <div className="mb-3 px-3" style={{ position: "relative" }}>
                <InputGroup>
                    <Form.Control
                        value={search}
                        onChange={handleSearch}
                        placeholder={
                            user?.role?.roleName === "ROLE_EMPLOYER"
                                ? "Tìm ứng viên để chat..."
                                : "Tìm employer để chat..."
                        }
                        autoComplete="off"
                    />
                    {searchLoading && (
                        <InputGroup.Text>
                            <Spinner animation="border" size="sm" />
                        </InputGroup.Text>
                    )}
                </InputGroup>
                {search && (
                    <ListGroup
                        className="position-absolute w-100 mt-1 shadow"
                        style={{ zIndex: 10, maxHeight: 240, overflowY: "auto" }}
                    >
                        {searchResults.map(contact => (
                            <ListGroup.Item
                                action
                                key={contact.username}
                                onClick={() => handleSelectContact(contact)}
                                className="d-flex align-items-center"
                            >
                                <Image
                                    src={contact.avatar || `https://ui-avatars.com/api/?name=${encodeURIComponent(contact.companyName || contact.fullName || contact.name || contact.username)}`}
                                    roundedCircle
                                    width={36}
                                    height={36}
                                    className="me-3"
                                    alt="avatar"
                                />
                                <div>
                                    <div className="fw-bold">{contact.companyName || contact.fullName || contact.username}</div>
                                    <div className="text-muted small">{contact.username}</div>
                                </div>
                            </ListGroup.Item>
                        ))}
                        {!searchLoading && searchResults.length === 0 && (
                            <ListGroup.Item className="text-muted text-center">Không tìm thấy</ListGroup.Item>
                        )}
                    </ListGroup>
                )}
            </div>

            <ListGroup variant="flush">
                {list.length === 0 && (
                    <ListGroup.Item className="text-muted text-center py-4">
                        Chưa có đoạn chat nào
                    </ListGroup.Item>
                )}
                {list.map(conv => {
                    const contact = profileMap[conv.otherUsername];
                    return (
                        <ListGroup.Item
                            action
                            key={conv.id}
                            onClick={() => onSelect({
                                ...conv,
                                avatar: contact?.avatar,
                                name: contact?.companyName || contact?.fullName || conv.otherUsername,
                            })}
                            active={conv.id === selectedId}
                            className="d-flex align-items-center"
                        >
                            <Image
                                src={contact?.avatar || `https://ui-avatars.com/api/?name=${encodeURIComponent(contact?.companyName || contact?.fullName || contact?.name || conv.otherUsername)}`}
                                roundedCircle
                                width={36}
                                height={36}
                                className="me-3"
                                alt="avatar"
                            />
                            <div>
                                <div className="fw-bold">{contact?.companyName || contact?.fullName || contact?.name || conv.otherUsername}</div>
                                <div className="text-muted small">{conv.lastMsg}</div>
                            </div>
                        </ListGroup.Item>
                    );
                })}
            </ListGroup>
        </div>
    );
}