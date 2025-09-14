import React, { useState, useContext, useEffect } from "react";
import { Form, Button, Alert, Row, Col } from "react-bootstrap";
import Apis, { authApis, endpoints } from "../../configs/Apis";
import { useNavigate } from "react-router-dom";
import MySpinner from "../layout/MySpinner";
import { MyUserContext } from "../../configs/Contexts";

const EmployerRegister = () => {
    const [employer, setEmployer] = useState({
        companyName: "",
        representativeName: "",
        representativeTitle: "",
        taxCode: "",
    });

    const [avatar, setAvatar] = useState(null);
    const [workEnvImg1, setWorkEnvImg1] = useState(null);
    const [workEnvImg2, setWorkEnvImg2] = useState(null);
    const [workEnvImg3, setWorkEnvImg3] = useState(null);
    const [verifyDoc, setVerifyDoc] = useState(null);

    const [previewAvatar, setPreviewAvatar] = useState(null);
    const [previewWorkEnv1, setPreviewWorkEnv1] = useState(null);
    const [previewWorkEnv2, setPreviewWorkEnv2] = useState(null);
    const [previewWorkEnv3, setPreviewWorkEnv3] = useState(null);

    const [msg, setMsg] = useState("");
    const [loading, setLoading] = useState(false);

    const [user, dispatch] = useContext(MyUserContext);
    const nav = useNavigate();

    useEffect(() => {
        if (!user) {
            setMsg("Bạn cần đăng nhập để đăng ký doanh nghiệp!");
        }
    }, [user]);

    const handleFileChange = (setter, previewSetter) => (e) => {
        const file = e.target.files[0];
        setter(file);
        if (file) {
            previewSetter(URL.createObjectURL(file));
        } else {
            previewSetter(null);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEmployer(prev => ({ ...prev, [name]: value }));
    };

    const handleVerifyDocChange = (e) => {
        setVerifyDoc(e.target.files[0]);
    };

    useEffect(() => {
        return () => {
            if (previewAvatar) URL.revokeObjectURL(previewAvatar);
            if (previewWorkEnv1) URL.revokeObjectURL(previewWorkEnv1);
            if (previewWorkEnv2) URL.revokeObjectURL(previewWorkEnv2);
            if (previewWorkEnv3) URL.revokeObjectURL(previewWorkEnv3);
        };
    }, [previewAvatar, previewWorkEnv1, previewWorkEnv2, previewWorkEnv3]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            const formData = new FormData();
            formData.append(
                "employer",
                new Blob([JSON.stringify(employer)], { type: "application/json" })
            );
            console.log("Submitting employer registration:", formData);
            if (avatar) formData.append("avatar", avatar);
            if (workEnvImg1) formData.append("workEnvImg1", workEnvImg1);
            if (workEnvImg2) formData.append("workEnvImg2", workEnvImg2);
            if (workEnvImg3) formData.append("workEnvImg3", workEnvImg3);
            if (verifyDoc) formData.append("verifyDoc", verifyDoc);

            const res = await authApis().post(endpoints["employer-register"], formData);

            if (res.status === 201) {
                let u = await authApis().get(endpoints['profile']);
                dispatch({ type: "login", payload: u.data });
                nav("/");
            } else {
                setMsg("Đăng ký doanh nghiệp thất bại!");
            }
        } catch (ex) {
            setMsg("Có lỗi xảy ra khi đăng ký doanh nghiệp!");
            console.error(ex);
        } finally {
            setLoading(false);
        }
    };

    if (!user) {
        return (
            <div className="container mt-4">
                <Alert variant="danger" className="text-center">
                    {msg || "Bạn cần đăng nhập để đăng ký doanh nghiệp!"}
                </Alert>
            </div>
        );
    }

    return (
        <div className="container mt-4">
            <h2 className="text-success text-center mb-4">Đăng ký thông tin doanh nghiệp</h2>
            {msg && <Alert variant="danger">{msg}</Alert>}
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3" controlId="name">
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
                        <Form.Group className="mb-3" controlId="phone">
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
                        <Form.Group className="mb-3" controlId="address">
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
                <Form.Group className="mb-3" controlId="website">
                    <Form.Label>Mã số thuế</Form.Label>
                    <Form.Control
                        name="taxCode"
                        value={employer.taxCode}
                        onChange={handleChange}
                        placeholder="Nhập mã số thuế"
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
                                src={previewAvatar}
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
                                src={previewWorkEnv1}
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
                                src={previewWorkEnv2}
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
                                src={previewWorkEnv3}
                                alt="Xem trước môi trường 3"
                                style={{ maxWidth: "150px", maxHeight: "150px", borderRadius: "10px" }}
                            />
                        </div>
                    )}
                </Form.Group>
                <Form.Group className="mb-3" controlId="verifyDoc">
                    <Form.Label>Tài liệu xác thực (PDF/ảnh) tối đa 10MB</Form.Label>
                    <Form.Control
                        type="file"
                        accept="image/*,.pdf"
                        onChange={handleVerifyDocChange}
                    />
                    {verifyDoc && (
                        <div className="mb-2 text-center">
                            <span>{verifyDoc.name}</span>
                        </div>
                    )}
                </Form.Group>
                {loading ? <MySpinner /> : (
                    <Button className="mb-3" type="submit" variant="success">Đăng ký doanh nghiệp</Button>
                )}
            </Form>
        </div>
    );
};

export default EmployerRegister;