import React, { useEffect, useState } from "react";
import { Card, Row, Col, Button, Spinner, Alert, Container, Badge } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { FaPlus, FaEye, FaEdit, FaUsers, FaClock } from "react-icons/fa";
import Apis, { authApis, endpoints } from "../../configs/Apis";

const MyJob = () => {
    const [jobs, setJobs] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const loadJobs = async () => {
            setLoading(true);
            setError(null);
            let url = `${endpoints['myjob']}`;
            try {
                const res = await authApis().get(url);
                setJobs(res.data || []);
            } catch (err) {
                setError("Không thể tải danh sách tin tuyển dụng!");
            } finally {
                setLoading(false);
            }
        };
        loadJobs();
    }, []);

    function isActive(job) {
        if (!job.deadline) return true;
        const deadline = new Date(job.deadline);
        const today = new Date();

        deadline.setHours(0, 0, 0, 0);
        today.setHours(0, 0, 0, 0);
        return deadline >= today;
    }

    return (
        <Container className="py-4">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h3 className="fw-bold mb-0">Tin tuyển dụng của bạn</h3>
                <Button variant="success" onClick={() => navigate("/employer/job")}>
                    <FaPlus className="me-2" /> Đăng tin mới
                </Button>
            </div>
            {loading ? (
                <div className="text-center my-5">
                    <Spinner animation="border" />
                </div>
            ) : error ? (
                <Alert variant="danger" className="text-center">
                    {error}
                </Alert>
            ) : jobs.length === 0 ? (
                <Alert variant="info" className="text-center">
                    Bạn chưa có tin tuyển dụng nào!
                </Alert>
            ) : (
                <Row xs={1} md={2} lg={2} className="g-4">
                    {jobs.map((job) => (
                        <Col key={job.jobId}>
                            <Card className="shadow-sm border-0 h-100">
                                <Card.Body>
                                    <div className="d-flex align-items-center mb-2">
                                        <Card.Title as="h5" className="mb-0 fw-bold flex-grow-1">
                                            {job.jobTitle}
                                        </Card.Title>
                                        <Badge bg={isActive(job) ? "success" : "secondary"}>
                                            {isActive(job) ? "Đang tuyển" : "Đã đóng"}
                                        </Badge>
                                    </div>

                                    <div className="mb-2 text-muted small">
                                        <FaClock className="me-1" />
                                        Hạn nộp:{" "}
                                        {job.deadline
                                            ? new Date(job.deadline).toLocaleDateString("vi-VN")
                                            : "Không có"}
                                    </div>

                                    <div className="mb-2 small">
                                        <FaUsers className="me-1" />
                                        Số lượng cần tuyển:{" "}
                                        <span className="fw-semibold">{job.quantity || "Không có"}</span>
                                    </div>
                                </Card.Body>
                                <Card.Footer className="bg-white d-flex justify-content-end gap-2 border-0">
                                    <Button
                                        variant="outline-primary"
                                        size="sm"
                                        onClick={() => navigate(`/job/${job.jobId}`)}
                                    >
                                        <FaEye className="me-1" /> Xem chi tiết
                                    </Button>
                                    <Button
                                        variant="outline-success"
                                        size="sm"
                                        onClick={() => navigate(`/employer/job/${job.jobId}`)}
                                    >
                                        <FaEdit className="me-1" /> Sửa
                                    </Button>
                                    <Button
                                        variant="outline-secondary"
                                        size="sm"
                                        onClick={() => navigate(`/job/${job.jobId}/applications`)}
                                    >
                                        <FaUsers className="me-1" /> Ứng viên
                                    </Button>
                                </Card.Footer>
                            </Card>
                        </Col>
                    ))}
                </Row>
            )}
        </Container>
    );
};

export default MyJob;