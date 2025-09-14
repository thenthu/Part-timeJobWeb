import React, { useState, useEffect } from "react";
import { Form, Button, Alert, Row, Col } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import { useNavigate, useLocation } from "react-router-dom";
import MySpinner from "../layout/MySpinner";

const EmployerEdit = () => {
    const location = useLocation();
    const nav = useNavigate();
    const profile = location.state?.profile;

    const [employer, setEmployer] = useState({
        companyName: "",
        representativeName: "",
        representativeTitle: "",
        taxCode: "",
        address: "",
        website: "",
    });

    const [avatar, setAvatar] = useState(null);
    const [workEnvImg1, setWorkEnvImg1] = useState(null);
    const [workEnvImg2, setWorkEnvImg2] = useState(null);
    const [workEnvImg3, setWorkEnvImg3] = useState(null);

    const [previewAvatar, setPreviewAvatar] = useState(null);
    const [previewWorkEnv1, setPreviewWorkEnv1] = useState(null);
    const [previewWorkEnv2, setPreviewWorkEnv2] = useState(null);
    const [previewWorkEnv3, setPreviewWorkEnv3] = useState(null);

    const [msg, setMsg] = useState("");
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (profile) {
            setEmployer({
                companyName: profile.companyName || "",
                representativeName: profile.representativeName || "",
                representativeTitle: profile.representativeTitle || "",
                taxCode: profile.taxCode || "",
                address: profile.address || "",
                website: profile.website || "",
            });
            setPreviewAvatar(profile.avatar || null);
            setPreviewWorkEnv1(profile.workEnvImg1 || null);
            setPreviewWorkEnv2(profile.workEnvImg2 || null);
            setPreviewWorkEnv3(profile.workEnvImg3 || null);
        }
    }, [profile]);

    useEffect(() => {
        return () => {
            if (previewAvatar && typeof previewAvatar === "object") URL.revokeObjectURL(previewAvatar);
            if (previewWorkEnv1 && typeof previewWorkEnv1 === "object") URL.revokeObjectURL(previewWorkEnv1);
            if (previewWorkEnv2 && typeof previewWorkEnv2 === "object") URL.revokeObjectURL(previewWorkEnv2);
            if (previewWorkEnv3 && typeof previewWorkEnv3 === "object") URL.revokeObjectURL(previewWorkEnv3);
        };
    }, [previewAvatar, previewWorkEnv1, previewWorkEnv2, previewWorkEnv3]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEmployer(prev => ({ ...prev, [name]: value }));
    };

    const handleFileChange = (setter, previewSetter) => (e) => {
        const file = e.target.files[0];
        setter(file);
        if (file) {
            previewSetter(URL.createObjectURL(file));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setMsg("");
        try {
            const formData = new FormData();
            for (let key in employer) {
                formData.append(key, employer[key]);
            }
            if (avatar) formData.append("avatar", avatar);
            if (workEnvImg1) formData.append("workEnvImg1", workEnvImg1);
            if (workEnvImg2) formData.append("workEnvImg2", workEnvImg2);
            if (workEnvImg3) formData.append("workEnvImg3", workEnvImg3);

            const res = await authApis().patch(endpoints["employer-profile-edit"], formData, {
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
                    Không có thông tin doanh nghiệp để chỉnh sửa!
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
            <h2 className="text-success text-center mb-4">Chỉnh sửa thông tin doanh nghiệp</h2>
            {msg && <Alert variant={msg.includes("thành công") ? "success" : "danger"}>{msg}</Alert>}
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3" controlId="companyName">
                    <Form.Label>Tên công ty</Form.Label>
                    <Form.Control
                        name="companyName"
                        value={employer.companyName}
                        onChange={handleChange}
                        required
                        placeholder="Nhập tên công ty"
                    />
                </Form.Group>
                <Row>
                    <Col md={6}>
                        <Form.Group className="mb-3" controlId="representativeName">
                            <Form.Label>Tên người đại diện</Form.Label>
                            <Form.Control
                                name="representativeName"
                                value={employer.representativeName}
                                onChange={handleChange}
                                required
                                placeholder="Nhập tên người đại diện"
                            />
                        </Form.Group>
                    </Col>
                    <Col md={6}>
                        <Form.Group className="mb-3" controlId="representativeTitle">
                            <Form.Label>Chức vụ</Form.Label>
                            <Form.Control
                                name="representativeTitle"
                                value={employer.representativeTitle}
                                onChange={handleChange}
                                required
                                placeholder="Nhập chức vụ"
                            />
                        </Form.Group>
                    </Col>
                </Row>
                <Form.Group className="mb-3" controlId="taxCode">
                    <Form.Label>Mã số thuế</Form.Label>
                    <Form.Control
                        name="taxCode"
                        value={employer.taxCode}
                        onChange={handleChange}
                        placeholder="Nhập mã số thuế"
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="address">
                    <Form.Label>Địa chỉ</Form.Label>
                    <Form.Control
                        name="address"
                        value={employer.address}
                        onChange={handleChange}
                        placeholder="Nhập địa chỉ"
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="website">
                    <Form.Label>Website</Form.Label>
                    <Form.Control
                        name="website"
                        value={employer.website}
                        onChange={handleChange}
                        placeholder="Nhập website"
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="avatar">
                    <Form.Label>Logo/Ảnh đại diện công ty</Form.Label>
                    <Form.Control
                        type="file"
                        accept="image/*"
                        onChange={handleFileChange(setAvatar, setPreviewAvatar)}
                    />
                    {previewAvatar && (
                        <div className="mb-2 text-center">
                            <img
                                src={typeof previewAvatar === "string" ? previewAvatar : URL.createObjectURL(previewAvatar)}
                                alt="Xem trước avatar"
                                style={{ maxWidth: "150px", maxHeight: "150px", borderRadius: "10px" }}
                            />
                        </div>
                    )}
                </Form.Group>
                <Form.Group className="mb-3" controlId="workEnvImg1">
                    <Form.Label>Ảnh môi trường làm việc 1</Form.Label>
                    <Form.Control
                        type="file"
                        accept="image/*"
                        onChange={handleFileChange(setWorkEnvImg1, setPreviewWorkEnv1)}
                    />
                    {previewWorkEnv1 && (
                        <div className="mb-2 text-center">
                            <img
                                src={typeof previewWorkEnv1 === "string" ? previewWorkEnv1 : URL.createObjectURL(previewWorkEnv1)}
                                alt="Xem trước môi trường 1"
                                style={{ maxWidth: "150px", maxHeight: "150px", borderRadius: "10px" }}
                            />
                        </div>
                    )}
                </Form.Group>
                <Form.Group className="mb-3" controlId="workEnvImg2">
                    <Form.Label>Ảnh môi trường làm việc 2</Form.Label>
                    <Form.Control
                        type="file"
                        accept="image/*"
                        onChange={handleFileChange(setWorkEnvImg2, setPreviewWorkEnv2)}
                    />
                    {previewWorkEnv2 && (
                        <div className="mb-2 text-center">
                            <img
                                src={typeof previewWorkEnv2 === "string" ? previewWorkEnv2 : URL.createObjectURL(previewWorkEnv2)}
                                alt="Xem trước môi trường 2"
                                style={{ maxWidth: "150px", maxHeight: "150px", borderRadius: "10px" }}
                            />
                        </div>
                    )}
                </Form.Group>
                <Form.Group className="mb-3" controlId="workEnvImg3">
                    <Form.Label>Ảnh môi trường làm việc 3</Form.Label>
                    <Form.Control
                        type="file"
                        accept="image/*"
                        onChange={handleFileChange(setWorkEnvImg3, setPreviewWorkEnv3)}
                    />
                    {previewWorkEnv3 && (
                        <div className="mb-2 text-center">
                            <img
                                src={typeof previewWorkEnv3 === "string" ? previewWorkEnv3 : URL.createObjectURL(previewWorkEnv3)}
                                alt="Xem trước môi trường 3"
                                style={{ maxWidth: "150px", maxHeight: "150px", borderRadius: "10px" }}
                            />
                        </div>
                    )}
                </Form.Group>
                {loading ? <MySpinner /> : (
                    <Button className="mb-3" type="submit" variant="success">Lưu thay đổi</Button>
                )}
            </Form>
        </div>
    );
};

export default EmployerEdit;