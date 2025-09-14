import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { authApis, endpoints } from "../../configs/Apis";
import { Container, Spinner, Alert, Card, Row, Col, Button, Image, Badge, Modal } from "react-bootstrap";

const ResumeDetailModal = ({ show, onHide, resume }) => {
    if (!resume) return null;

    const candidate = resume.candidate || {};
    const avatar = candidate.avatar || "https://via.placeholder.com/80";
    const birthDate = candidate.birthDate
        ? new Date(candidate.birthDate).toLocaleDateString()
        : "-";

    return (
        <Modal show={show} onHide={onHide} size="lg" centered>
            <Modal.Header closeButton>
                <Modal.Title>Chi tiết hồ sơ: {resume.resumeName}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Row className="mb-4">
                    <Col xs={3} className="text-center">
                        <Image
                            src={avatar}
                            roundedCircle
                            width={90}
                            height={90}
                            alt="avatar"
                            style={{ objectFit: "cover", border: "2px solid #eee" }}
                        />
                        <h5 className="mt-2">{candidate.fullName}</h5>
                        <div className="text-muted" style={{ fontSize: "0.95em" }}>{candidate.email}</div>
                    </Col>
                    <Col xs={9}>
                        <Row>
                            <Col md={6}><strong>Username:</strong> {candidate.username}</Col>
                            <Col md={6}><strong>SĐT:</strong> {candidate.phone}</Col>
                        </Row>
                        <Row>
                            <Col md={6}><strong>Giới tính:</strong> {candidate.gender}</Col>
                            <Col md={6}><strong>Ngày sinh:</strong> {birthDate}</Col>
                        </Row>
                        <Row>
                            <Col md={12}><strong>Địa chỉ:</strong> {candidate.address}</Col>
                        </Row>
                    </Col>
                </Row>
                <Row>
                    <Col md={6}><strong>Chuyên ngành:</strong> {resume.major?.majorName}</Col>
                    <Col md={6}><strong>Vị trí mong muốn:</strong> {resume.level?.levelName}</Col>
                </Row>
                <hr />
                <Row className="mb-2"><Col><strong>Mục tiêu nghề nghiệp:</strong><br />{resume.careerObjective}</Col></Row>
                <Row className="mb-2"><Col><strong>Kinh nghiệm:</strong><br />{resume.experience}</Col></Row>
                <Row className="mb-2"><Col><strong>Kỹ năng:</strong><br />{resume.skills}</Col></Row>
                <Row className="mb-2"><Col><strong>Học vấn:</strong><br />{resume.education}</Col></Row>
                <Row className="mb-2"><Col><strong>Kỹ năng mềm:</strong><br />{resume.softSkills}</Col></Row>
                <Row className="mb-2"><Col><strong>Giải thưởng:</strong><br />{resume.awards}</Col></Row>
            </Modal.Body>
        </Modal>
    );
};

const JobApplication = () => {
    const { jobId } = useParams();
    const [applications, setApplications] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [selectedResume, setSelectedResume] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [processingId, setProcessingId] = useState(null);
    const navigate = useNavigate();

    const loadApplications = async () => {
        try {
            const url = `${endpoints["job"]}/${jobId}/applications`;
            const res = await authApis().get(url);
            setApplications(res.data || []);
        } catch (err) {
            setError("Không thể tải danh sách ứng tuyển.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadApplications();
    }, [jobId]);

    const formatDate = (dt) => {
        if (!dt) return "-";
        const [date, time] = dt.split(" ");
        return `${date} ${time?.slice(0, 5) || ""}`;
    };

    const handleShowResume = (resume) => {
        setSelectedResume(resume);
        setShowModal(true);
    };

    const handleStatus = async (candidateId, jobId, newStatus) => {
        setProcessingId(candidateId);
        console.log(candidateId, jobId, newStatus);
        try {
            await authApis().patch(
                `/secure/employer/job/application?candidateId=${candidateId}&jobId=${jobId}&status=${encodeURIComponent(newStatus)}`
            );
            loadApplications();
        } catch (err) {
            alert("Có lỗi xảy ra khi cập nhật trạng thái!");
        } finally {
            setProcessingId(null);
        }
    };

    return (
        <Container className="my-4">
            <h3 className="mb-4">Danh sách ứng viên ứng tuyển</h3>
            {loading ? (
                <div className="text-center my-5">
                    <Spinner animation="border" />
                </div>
            ) : error ? (
                <Alert variant="danger">{error}</Alert>
            ) : applications.length === 0 ? (
                <Alert variant="info">Chưa có ai ứng tuyển vị trí này.</Alert>
            ) : (
                <Row xs={1} sm={2} md={2} lg={3} className="g-4">
                    {applications.map((app, idx) => {
                        const candidate = app.candidate || {};
                        return (
                            <Col key={app.applicationId || idx}>
                                <Card className="h-100 shadow-sm border-0">
                                    <Card.Body className="d-flex align-items-center flex-row gap-3">
                                        <Image
                                            src={candidate.avatar}
                                            roundedCircle
                                            width={60}
                                            height={60}
                                            alt="avatar"
                                            style={{ objectFit: "cover", border: "2px solid #eee" }}
                                        />
                                        <div className="text-start flex-grow-1">
                                            <Card.Title className="mb-1 d-flex align-items-center gap-2">
                                                {candidate.fullName}
                                                <Button
                                                    variant="outline-info"
                                                    size="sm"
                                                    style={{ padding: "2px 8px", fontSize: "0.7em" }}
                                                    onClick={() => navigate(`/candidate/${candidate.candidateId}`)}
                                                >
                                                    Xem trang cá nhân
                                                </Button>
                                            </Card.Title>
                                            <Card.Text className="mb-1"><strong>Email:</strong> {candidate.email}</Card.Text>
                                            <Card.Text className="mb-1"><strong>SĐT:</strong> {candidate.phone}</Card.Text>
                                            <Card.Text className="mb-1 text-muted" style={{ fontSize: "0.95em" }}>
                                                <i className="bi bi-calendar-check me-1"></i>
                                                Nộp lúc: {formatDate(app.applyDate)}
                                            </Card.Text>
                                            <Card.Text className="mb-2">
                                                <Badge bg={app.status === "Đã duyệt" ? "success" : app.status === "Đã từ chối" ? "danger" : "secondary"}>
                                                    {app.status}
                                                </Badge>
                                            </Card.Text>
                                            <div className="mb-1 d-flex gap-2 justify-content-between" style={{ minWidth: 270 }}>
                                                <Button
                                                    variant="outline-primary"
                                                    size="sm"
                                                    className="flex-fill"
                                                    style={{ minWidth: 0 }}
                                                    onClick={() => handleShowResume(app.resume)}
                                                >
                                                    Xem CV
                                                </Button>
                                                <Button
                                                    variant="outline-success"
                                                    size="sm"
                                                    className="flex-fill"
                                                    style={{ minWidth: 0 }}
                                                    disabled={app.status === "Đã duyệt" || processingId === app.applicationId}
                                                    onClick={() => handleStatus(candidate.candidateId, app.jobPost.jobId, "Đã duyệt")}
                                                >
                                                    {processingId === app.applicationId && app.status !== "Đã duyệt" ? <Spinner size="sm" animation="border" /> : "Duyệt"}
                                                </Button>
                                                <Button
                                                    variant="outline-danger"
                                                    size="sm"
                                                    className="flex-fill"
                                                    style={{ minWidth: 0 }}
                                                    disabled={app.status === "Đã từ chối" || processingId === app.applicationId}
                                                    onClick={() => handleStatus(candidate.candidateId, app.jobPost.jobId, "Đã từ chối")}
                                                >
                                                    {processingId === app.applicationId && app.status !== "Đã từ chối" ? <Spinner size="sm" animation="border" /> : "Từ chối"}
                                                </Button>
                                            </div>
                                        </div>
                                    </Card.Body>
                                </Card>
                            </Col>
                        );
                    })}
                </Row>
            )}
            <div className="mt-4">
                <Button variant="secondary" onClick={() => navigate(-1)}>Quay lại</Button>
            </div>
            <ResumeDetailModal
                show={showModal}
                onHide={() => setShowModal(false)}
                resume={selectedResume}
            />
        </Container>
    );
};

export default JobApplication;