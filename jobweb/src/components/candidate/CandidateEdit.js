import React, { useState, useEffect } from "react";
import { Form, Button, Alert } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import { useNavigate, useLocation } from "react-router-dom";
import MySpinner from "../layout/MySpinner";

const CandidateEdit = () => {
    const location = useLocation();
    const profile = location.state?.profile;

    const [candidate, setCandidate] = useState({
        fullName: "",
        phone: "",
        birthDate: "",
        address: "",
        gender: "",
    });
    const [avatar, setAvatar] = useState(null);
    const [previewAvatar, setPreviewAvatar] = useState(null);
    const [msg, setMsg] = useState("");
    const [loading, setLoading] = useState(false);
    const nav = useNavigate();

    useEffect(() => {
        if (profile) {
            setCandidate({
                fullName: profile.fullName || "",
                phone: profile.phone || "",
                birthDate: profile.birthDate || "",
                address: profile.address || "",
                gender: profile.gender || "",
            });
            setPreviewAvatar(profile.avatar || null);
        }
    }, [profile]);

    useEffect(() => {
        return () => {
            if (previewAvatar && typeof previewAvatar === "object") {
                URL.revokeObjectURL(previewAvatar);
            }
        };
    }, [previewAvatar]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCandidate(prev => ({ ...prev, [name]: value }));
    };

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        setAvatar(file);
        if (file) {
            setPreviewAvatar(URL.createObjectURL(file));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setMsg("");
        try {
            const formData = new FormData();
            for (let key in candidate) {
                formData.append(key, candidate[key]);
            }
            if (avatar) formData.append("avatar", avatar);

            const res = await authApis().patch(endpoints["candidate-profile-edit"], formData, {
                headers: { "Content-Type": "multipart/form-data" }
            });

            if (res.status === 200) {
                setMsg("Cập nhật thành công!");
                setTimeout(() => nav("/profile"), 1000);
            } else {
                setMsg("Cập nhật thất bại!");
            }
        } catch (err) {
            setMsg(err.response?.data || "Có lỗi xảy ra khi cập nhật!");
        } finally {
            setLoading(false);
        }
    };

    if (!profile) {
        return (
            <div className="container mt-4">
                <Alert variant="danger" className="text-center">
                    Không có thông tin ứng viên để chỉnh sửa!
                    <div>
                        <Button variant="secondary" onClick={() => nav(-1)} className="mt-3">
                            Quay lại
                        </Button>
                    </div>
                </Alert>
            </div>
        );
    }

    return (
        <div className="container mt-4">
            <h2 className="text-success text-center mb-4">Chỉnh sửa thông tin ứng viên</h2>
            {msg && <Alert variant={msg.includes("thành công") ? "success" : "danger"}>{msg}</Alert>}
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3" controlId="fullName">
                    <Form.Label>Họ tên</Form.Label>
                    <Form.Control
                        name="fullName"
                        value={candidate.fullName}
                        onChange={handleChange}
                        required
                        placeholder="Nhập họ tên"
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="phone">
                    <Form.Label>Số điện thoại</Form.Label>
                    <Form.Control
                        name="phone"
                        value={candidate.phone}
                        onChange={handleChange}
                        required
                        placeholder="Nhập số điện thoại"
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="birthDate">
                    <Form.Label>Ngày sinh</Form.Label>
                    <Form.Control
                        name="birthDate"
                        type="date"
                        value={candidate.birthDate}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="address">
                    <Form.Label>Địa chỉ</Form.Label>
                    <Form.Control
                        name="address"
                        value={candidate.address}
                        onChange={handleChange}
                        required
                        placeholder="Nhập địa chỉ"
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="gender">
                    <Form.Label>Giới tính</Form.Label>
                    <div>
                        <Form.Check
                            inline
                            type="radio"
                            label="Nam"
                            name="gender"
                            value="Nam"
                            checked={candidate.gender === "Nam"}
                            onChange={handleChange}
                            id="gender-male"
                        />
                        <Form.Check
                            inline
                            type="radio"
                            label="Nữ"
                            name="gender"
                            value="Nữ"
                            checked={candidate.gender === "Nữ"}
                            onChange={handleChange}
                            id="gender-female"
                        />
                    </div>
                </Form.Group>
                <Form.Group className="mb-3" controlId="avatar">
                    <Form.Label>Ảnh đại diện</Form.Label>
                    <Form.Control
                        type="file"
                        accept="image/*"
                        onChange={handleFileChange}
                    />
                </Form.Group>
                {previewAvatar && (
                    <div className="mb-3 text-center">
                        <img
                            src={typeof previewAvatar === "string" ? previewAvatar : URL.createObjectURL(previewAvatar)}
                            alt="Avatar hiện tại"
                            style={{ maxWidth: "200px", maxHeight: "200px", borderRadius: "50%" }}
                        />
                    </div>
                )}
                {loading ? <MySpinner /> : (
                    <Button className="mb-3" type="submit" variant="success">Lưu thay đổi</Button>
                )}
            </Form>
        </div>
    );
};

export default CandidateEdit;