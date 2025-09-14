import React, { useState, useContext, useEffect } from "react";
import { Form, Button, Alert } from "react-bootstrap";
import Apis, { authApis, endpoints } from "../../configs/Apis";
import { useNavigate } from "react-router-dom";
import MySpinner from "../layout/MySpinner";
import { MyUserContext } from "../../configs/Contexts";

const CandidateRegister = () => {
    const [candidate, setCandidate] = useState({
        fullName: "",
        phone: "",
        birthDay: "",
        address: "",
        gender: "",
    });
    const [avatar, setAvatar] = useState(null);
    const [previewAvatar, setPreviewAvatar] = useState(null);
    const [msg, setMsg] = useState("");
    const [loading, setLoading] = useState(false);

    const [user, dispatch] = useContext(MyUserContext);
    const nav = useNavigate();

    useEffect(() => {
        if (!user) {
            setMsg("Bạn cần đăng nhập để đăng ký ứng viên!");
        }
    }, [user]);

    useEffect(() => {
        return () => {
            if (previewAvatar) {
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
        } else {
            setPreviewAvatar(null);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        alert("Đã bấm");

        if (!avatar) {
            setMsg("Vui lòng chọn ảnh đại diện!");
            return;
        }

        setLoading(true);

        try {
            const formData = new FormData();
            formData.append(
                "candidate",
                new Blob([JSON.stringify(candidate)], { type: "application/json" })
            );
            formData.append("avatar", avatar);

            const res = await authApis().post(endpoints["candidate-register"], formData, {
                headers: {
                    "Content-Type": "multipart/form-data"
                }
            });

            if (res.status === 201) {
                let u = await authApis().get(endpoints['profile']);
                dispatch({
                    type: "login",
                    payload: u.data
                });
                nav("/");
            } else {
                setMsg("Đăng ký ứng viên thất bại!");
            }
        } catch (ex) {
            setMsg("Có lỗi xảy ra khi đăng ký ứng viên!");
            console.error(ex);
        } finally {
            setLoading(false);
        }
    };

    if (!user) {
        return (
            <div className="container mt-4">
                <Alert variant="danger" className="text-center">
                    {msg || "Bạn cần đăng nhập để đăng ký ứng viên!"}
                </Alert>
            </div>
        );
    }

    return (
        <div className="container mt-4">
            <h2 className="text-success text-center mb-4">Đăng ký thông tin ứng viên</h2>
            {msg && <Alert variant="danger">{msg}</Alert>}
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
                <Form.Group className="mb-3" controlId="birthDay">
                    <Form.Label>Ngày sinh</Form.Label>
                    <Form.Control
                        name="birthDay"
                        type="date"
                        value={candidate.birthDay}
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
                            value="male"
                            checked={candidate.gender === "male"}
                            onChange={handleChange}
                            id="gender-male"
                        />
                        <Form.Check
                            inline
                            type="radio"
                            label="Nữ"
                            name="gender"
                            value="female"
                            checked={candidate.gender === "female"}
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
                        required
                    />
                </Form.Group>

                {previewAvatar && (
                    <div className="mb-3">
                        <img
                            src={previewAvatar}
                            alt="Xem trước avatar"
                            style={{ maxWidth: "200px", maxHeight: "200px", borderRadius: "50%" }}
                        />
                    </div>
                )}
                {loading ? <MySpinner /> : (
                    <Button className="mb-3" type="submit" variant="success">Đăng ký ứng viên</Button>
                )}
            </Form>
        </div>
    );
};

export default CandidateRegister;