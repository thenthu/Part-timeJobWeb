import React, { useEffect, useState } from "react";
import { Card, Row, Col, Badge, Spinner, Alert, Button, Image } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { FaMapMarkerAlt } from "react-icons/fa";
import Apis, { authApis, endpoints } from "../../configs/Apis";

const STATUS_COLOR = {
    "Đã nộp": "secondary",
    "Đã duyệt": "success",
    "Đã từ chối": "danger"
};

const Application = () => {
    const [applications, setApplications] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        const loadApplications = async () => {
            try {
                const res = await authApis().get(endpoints["applications"]);
                setApplications(res.data);
            } catch (err) {
                setError("Không thể tải danh sách đơn ứng tuyển.", err);
            } finally {
                setLoading(false);
            }
        };
        loadApplications();
    }, []);

    if (loading)
        return (
            <div className="text-center py-5">
                <Spinner animation="border" />
            </div>
        );

    if (error)
        return (
            <Alert variant="danger" className="text-center my-5">
                {error}
            </Alert>
        );

    if (!applications || applications.length === 0)
        return (
            <Alert variant="info" className="text-center my-5">
                Bạn chưa ứng tuyển công việc nào.
            </Alert>
        );

    return (
        <div className="container py-4">
            <h3 className="mb-4 text-primary-flex justify-content-between align-items-center mb-3">Đơn ứng tuyển của bạn</h3>
            <Row>
                {applications.map((app, idx) => {
                    const { jobPost, candidate, resume, applyDate, status } = app;
                    const employer = jobPost.employer;
                    return (
                        <Col md={6} lg={4} className="mb-4" key={idx}>
                            <Card className="h-100 shadow-sm border-0">
                                <Card.Body>
                                    <div className="d-flex align-items-center mb-3">
                                        <Image
                                            src={employer.avatar}
                                            alt="Logo công ty"
                                            style={{
                                                width: 48,
                                                height: 48,
                                                borderRadius: 12,
                                                objectFit: "cover",
                                                marginRight: 14
                                            }}
                                        />
                                        <div>
                                            <div style={{ fontWeight: "bold", fontSize: 18 }}>
                                                {jobPost.jobTitle}
                                            </div>
                                            <div className="text-muted mb-1">{employer.companyName}</div>
                                            <div className="d-flex align-items-center" style={{ fontSize: 13 }}>
                                                <FaMapMarkerAlt className="me-1" />
                                                <span>{jobPost.location?.locationName || jobPost.workAddress}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="mb-2">
                                        <span className="fw-semibold">Mức lương:</span>{" "}
                                        <span>
                                            {jobPost.minSalary && jobPost.maxSalary
                                                ? `${(jobPost.minSalary / 1e6).toLocaleString()} - ${(jobPost.maxSalary / 1e6).toLocaleString()} triệu`
                                                : "Thoả thuận"}
                                        </span>
                                    </div>
                                    <div className="mb-2">
                                        <span className="fw-semibold">Chuyên ngành:</span> {jobPost.major?.majorName}
                                    </div>
                                    <div className="mb-2">
                                        <span className="fw-semibold">Vị trí:</span> {jobPost.jobLevel?.levelName}
                                    </div>
                                    <div className="mb-2">
                                        <span className="fw-semibold">Ngày ứng tuyển:</span>{" "}
                                        {applyDate ? new Date(applyDate).toLocaleDateString() : ""}
                                    </div>
                                    <div className="mb-2">
                                        <span className="fw-semibold">Trạng thái:</span>{" "}
                                        <Badge bg={STATUS_COLOR[status] || "secondary"}>{status}</Badge>
                                    </div>
                                    <Button
                                        variant="outline-primary"
                                        size="sm"
                                        className="mt-2"
                                        onClick={() => navigate(`/job/${jobPost.jobId}`)}
                                    >
                                        Xem chi tiết công việc
                                    </Button>
                                </Card.Body>
                                <Card.Footer className="bg-white border-0">
                                    <small className="text-muted">
                                        Đã gửi bằng hồ sơ:{" "}
                                        <Button
                                            variant="link"
                                            className="p-0 fw-semibold"
                                            onClick={() => navigate(`/candidate/resume/${resume?.resumeId}`)}
                                        >
                                            {resume?.resumeName}
                                        </Button>
                                    </small>
                                </Card.Footer>
                            </Card>
                        </Col>
                    );
                })}
            </Row>
        </div>
    );
};

export default Application;