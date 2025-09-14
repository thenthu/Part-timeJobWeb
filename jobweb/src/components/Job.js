import { useEffect, useState, useContext } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Apis, { authApis, endpoints } from "../configs/Apis";
import { Container, Row, Col, Card, Button, ListGroup, Alert, Modal, Form } from "react-bootstrap";
import MySpinner from "./layout/MySpinner";
import { FaMoneyBillWave, FaMapMarkerAlt, FaUserTie, FaClock, FaUsers, FaGraduationCap } from "react-icons/fa";
import React from "react";
import { MyUserContext } from "../configs/Contexts";

const Job = () => {
    const { jobId } = useParams();
    const [job, setJob] = useState(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    const [user] = useContext(MyUserContext);

    const [resumeList, setResumeList] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [selectedResumeId, setSelectedResumeId] = useState(null);
    const [applying, setApplying] = useState(false);

    const [applyAlert, setApplyAlert] = useState({ type: "", message: "" });

    useEffect(() => {
        const loadJob = async () => {
            try {
                const res = await Apis.get(`/job/${jobId}`);
                setJob(res.data);
            } catch (error) {
                setJob(null);
            } finally {
                setLoading(false);
            }
        };
        loadJob();
    }, [jobId]);

    function getSalaryText(min, max) {
        const formatMillion = (value) =>
            (value / 1_000_000).toLocaleString(undefined, {
                minimumFractionDigits: 0,
                maximumFractionDigits: 1,
            });
        if (!min && !max) return "Thoả thuận";
        if (min && max) return `${formatMillion(min)} - ${formatMillion(max)} triệu`;
        if (min && !max) return `Từ ${formatMillion(min)} triệu`;
        if (!min && max) return `Đến ${formatMillion(max)} triệu`;
        return "Thoả thuận";
    }

    const handleApplyClick = async () => {
        setApplyAlert({ type: "", message: "" });
        if (!user) {
            alert("Vui lòng đăng nhập hoặc đăng ký để tiếp tục ứng tuyển!");
            return;
        }
        try {
            const res = await authApis().get(endpoints['resumes']);
            setResumeList(res.data || []);
            setShowModal(true);
        } catch (err) {
            alert("Không thể tải danh sách hồ sơ. Vui lòng thử lại!");
        }
    };

    const handleSubmitApply = async () => {
        if (!selectedResumeId) {
            setApplyAlert({ type: "danger", message: "Vui lòng chọn hồ sơ để ứng tuyển!" });
            return;
        }
        setApplying(true);
        setApplyAlert({ type: "", message: "" });
        try {
            const selectedResume = resumeList.find(resume => resume.resumeId === selectedResumeId);
            const application = {
                resume: { resumeId: selectedResume.resumeId },
            };
            await authApis().post(`/secure/job/${jobId}/apply`, application);
            setApplyAlert({ type: "success", message: "Ứng tuyển thành công!" });
        } catch (err) {
            if (err.response && err.response.status === 409) {
                setApplyAlert({ type: "danger", message: "Bạn đã ứng tuyển công việc này trước đó!" });
            } else {
                setApplyAlert({ type: "danger", message: "Có lỗi xảy ra khi ứng tuyển. Vui lòng thử lại!" });
            }
        } finally {
            setApplying(false);
        }
    };

    if (loading) return <div className="text-center mt-5"><MySpinner /></div>;

    if (!job)
        return (
            <Alert variant="danger" className="mt-5 text-center">
                Không tìm thấy công việc này!
            </Alert>
        );

    return (
        <Container className="py-3">
            <Row className="gy-4">
                <Col lg={8}>
                    <Card className="mb-3 border-0 shadow-sm">
                        <Card.Body>
                            <div className="d-flex flex-column flex-md-row justify-content-between">
                                <div>
                                    <Card.Title as="h4" className="fw-bold mb-2" style={{ fontSize: "1.5rem" }}>
                                        {job.jobTitle}
                                    </Card.Title>
                                </div>
                            </div>
                            <Row className="mt-4 g-3 text-center">
                                <Col xs={12} sm={4}>
                                    <div className="d-flex flex-column align-items-center">
                                        <FaMoneyBillWave size={28} className="text-success mb-1" />
                                        <div className="fw-medium">Thu nhập</div>
                                        <div className="text-success fw-bold">{getSalaryText(job.minSalary, job.maxSalary)}</div>
                                    </div>
                                </Col>
                                <Col xs={12} sm={4}>
                                    <div className="d-flex flex-column align-items-center">
                                        <FaMapMarkerAlt size={28} className="text-success mb-1" />
                                        <div className="fw-medium">Địa điểm</div>
                                        <div>{job.location?.locationName || job.workAddress}</div>
                                    </div>
                                </Col>
                                <Col xs={12} sm={4}>
                                    <div className="d-flex flex-column align-items-center">
                                        <FaUserTie size={28} className="text-success mb-1" />
                                        <div className="fw-medium">Kinh nghiệm</div>
                                        <div>{job.jobLevel?.levelName || "Không yêu cầu"}</div>
                                    </div>
                                </Col>
                            </Row>
                            <div className="d-flex align-items-center mt-4 gap-3 flex-wrap">
                                <div className="d-flex align-items-center gap-2">
                                    <FaClock className="text-secondary" />
                                    <span>
                                        Hạn nộp hồ sơ:&nbsp;
                                        <strong>
                                            {job.deadline ? new Date(job.deadline).toLocaleDateString("vi-VN") : "Không có"}
                                        </strong>
                                    </span>
                                </div>
                            </div>
                            <Row className="mt-4">
                                <Col sm={12} className="mb-2 mb-sm-0">
                                    <Button
                                        size="lg"
                                        className="w-100"
                                        variant="success"
                                        onClick={handleApplyClick}
                                    >
                                        <FaUserTie className="mb-1 me-2" /> Ứng tuyển ngay
                                    </Button>
                                </Col>
                            </Row>
                        </Card.Body>
                    </Card>

                    <Card className="mb-3 border-0 shadow-sm">
                        <Card.Body>
                            <div className="d-flex align-items-center mb-3">
                                <div style={{
                                    width: 4,
                                    height: 28,
                                    background: "#22ba59",
                                    borderRadius: 2,
                                    marginRight: 10
                                }} />
                                <h5 className="mb-0">Chi tiết tin tuyển dụng</h5>
                            </div>

                            <div className="mb-3">
                                <div className="fw-semibold mb-1">Mô tả công việc</div>
                                <div style={{ whiteSpace: "pre-line" }}>
                                    {job.description || "Không có mô tả"}
                                </div>
                            </div>

                            {job.candidateRequirement && (
                                <div className="mb-3">
                                    <div className="fw-semibold mb-1">Yêu cầu ứng viên</div>
                                    <div style={{ whiteSpace: "pre-line" }}>
                                        {job.candidateRequirement}
                                    </div>
                                </div>
                            )}

                            {getSalaryText(job.minSalary, job.maxSalary) && (
                                <div className="mb-3">
                                    <div className="fw-semibold mb-1">Thu nhập</div>
                                    <div style={{ whiteSpace: "pre-line" }}>
                                        Thu nhập: {getSalaryText(job.minSalary, job.maxSalary)}
                                    </div>
                                </div>
                            )}

                            {job.relatedSkills && (
                                <div className="mb-3">
                                    <div className="fw-semibold mb-1">Kỹ năng liên quan</div>
                                    {job.relatedSkills}
                                </div>
                            )}

                            {job.benefits && (
                                <div className="mb-3">
                                    <div className="fw-semibold mb-1">Quyền lợi</div>
                                    <div style={{ whiteSpace: "pre-line" }}>
                                        {job.benefits}
                                    </div>
                                </div>
                            )}

                            {job.workAddress && (
                                <div className="mb-3">
                                    <div className="fw-semibold mb-1">Địa điểm làm việc</div>
                                    <div style={{ whiteSpace: "pre-line" }}>
                                        {job.workAddress}
                                    </div>
                                </div>
                            )}
                        </Card.Body>
                    </Card>
                </Col>

                <Col lg={4}>
                    <Card className="mb-3 border-0 shadow-sm">
                        <Card.Body>
                            <div className="d-flex align-items-center mb-3">
                                <img
                                    src={job.employer.avatar}
                                    alt="logo"
                                    style={{
                                        width: 56,
                                        height: 56,
                                        objectFit: "cover",
                                        borderRadius: 12,
                                        marginRight: 14,
                                        border: "1.5px solid #e0e5ed"
                                    }}
                                />
                                <div>
                                    <div className="fw-bold">{job.employer.companyName}</div>
                                </div>
                            </div>
                            <ListGroup variant="flush" className="mb-2">
                                <ListGroup.Item className="px-0 py-1 border-0 d-flex align-items-center gap-2 small text-muted">
                                    <FaUsers className="text-secondary" /> Mã số thuế:&nbsp;
                                    <span>{job.employer.taxCode}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="px-0 py-1 border-0 d-flex align-items-center gap-2 small text-muted">
                                    <FaMapMarkerAlt className="text-secondary" /> Vị trí:&nbsp;
                                    <span>
                                        {job.location?.locationName}
                                    </span>
                                </ListGroup.Item>
                            </ListGroup>
                            <Button variant="link" className="ps-0 text-success fw-semibold" style={{ textDecoration: "none" }} onClick={() => navigate(`/employer/${job.employer.employerId}`)}>
                                Xem trang công ty <i className="bi bi-box-arrow-up-right" />
                            </Button>
                        </Card.Body>
                    </Card>

                    <Card className="mb-3 border-0 shadow-sm">
                        <Card.Body>
                            <div className="fw-bold fs-5 mb-3">Thông tin chung</div>
                            <ListGroup variant="flush">
                                <ListGroup.Item className="px-0 py-2 border-0 d-flex align-items-center gap-2">
                                    <FaUserTie className="text-success" /> Cấp bậc:&nbsp;
                                    <span className="fw-semibold">{job.jobLevel?.levelName || "Không yêu cầu"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="px-0 py-2 border-0 d-flex align-items-center gap-2">
                                    <FaGraduationCap className="text-success" /> Học vấn:&nbsp;
                                    <span>Trung cấp trở lên</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="px-0 py-2 border-0 d-flex align-items-center gap-2">
                                    <FaUsers className="text-success" /> Số lượng tuyển:&nbsp;
                                    <span>{job.quantity || "Không có"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="px-0 py-2 border-0 d-flex align-items-center gap-2">
                                    <FaClock className="text-success" /> Hình thức làm việc:&nbsp;
                                    <span>{job.workTime || "Toàn thời gian"}</span>
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            <Modal show={showModal} onHide={() => setShowModal(false)} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Chọn hồ sơ để ứng tuyển</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {applyAlert.message && (
                        <Alert variant={applyAlert.type}>{applyAlert.message}</Alert>
                    )}
                    {resumeList.length === 0 ? (
                        <div>Bạn chưa có hồ sơ nào. <Button variant="link" onClick={() => { setShowModal(false); navigate("/candidate/resume"); }}>Tạo ngay</Button></div>
                    ) : (
                        <Form>
                            {resumeList.map(resume => (
                                <Form.Check
                                    key={resume.resumeId}
                                    type="radio"
                                    label={resume.resumeName || `Resume #${resume.resumeId}`}
                                    name="resume"
                                    value={resume.resumeId}
                                    checked={selectedResumeId === resume.resumeId}
                                    onChange={() => setSelectedResumeId(resume.resumeId)}
                                    className="mb-2"
                                />
                            ))}
                        </Form>
                    )}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowModal(false)}>Hủy</Button>
                    <Button
                        variant="success"
                        onClick={handleSubmitApply}
                        disabled={applying || resumeList.length === 0}
                    >
                        {applying ? "Đang ứng tuyển..." : "Nộp hồ sơ"}
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
};

export default Job;