import React, { useState } from "react";
import { Form, Button, Alert, Container, InputGroup } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";

const ChangePassword = () => {
    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [msg, setMsg] = useState("");
    const [variant, setVariant] = useState("danger");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMsg("");

        if (newPassword !== confirmPassword) {
            setMsg("Xác nhận mật khẩu mới không khớp!");
            setVariant("danger");
            return;
        }

        setLoading(true);
        try {
            await authApis().patch(endpoints["change-password"], null, {
                params: {
                    oldPassword,
                    newPassword
                }
            });
            setMsg("Đổi mật khẩu thành công!");
            setVariant("success");
            setOldPassword("");
            setNewPassword("");
            setConfirmPassword("");
        } catch (err) {
            setMsg(err.response?.data || "Có lỗi xảy ra!");
            setVariant("danger");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container className="py-4" style={{ maxWidth: 500 }}>
            <h3 className="mb-4 text-center text-primary">Đổi mật khẩu</h3>
            {msg && <Alert variant={variant}>{msg}</Alert>}
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                    <Form.Label>Mật khẩu hiện tại</Form.Label>
                    <Form.Control
                        type="password"
                        value={oldPassword}
                        onChange={e => setOldPassword(e.target.value)}
                        required
                        placeholder="Nhập mật khẩu cũ"
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Mật khẩu mới</Form.Label>
                    <Form.Control
                        type="password"
                        value={newPassword}
                        onChange={e => setNewPassword(e.target.value)}
                        required
                        placeholder="Nhập mật khẩu mới"
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Xác nhận mật khẩu mới</Form.Label>
                    <Form.Control
                        type="password"
                        value={confirmPassword}
                        onChange={e => setConfirmPassword(e.target.value)}
                        required
                        placeholder="Nhập lại mật khẩu mới"
                    />
                </Form.Group>
                <div className="d-grid">
                    <Button type="submit" variant="success" disabled={loading}>
                        {loading ? "Đang xử lý..." : "Đổi mật khẩu"}
                    </Button>
                </div>
            </Form>
        </Container>
    );
};

export default ChangePassword;