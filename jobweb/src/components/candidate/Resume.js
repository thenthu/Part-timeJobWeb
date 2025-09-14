import { useEffect, useState } from "react";
import { Card, Row, Col, ListGroup, Badge, Alert, Container, Image, Spinner, Button, ButtonGroup } from "react-bootstrap";
import { FaEnvelope, FaPhone, FaTransgender, FaCalendarAlt, FaMapMarkerAlt, FaAward, FaUserGraduate, FaLightbulb, FaTrophy, FaChartLine, FaCogs, FaTasks, FaUserFriends, FaEdit, FaTrash, FaPlus } from "react-icons/fa";
import Apis, { authApis, endpoints } from "../../configs/Apis";
import { useNavigate } from "react-router-dom";

const Resume = () => {
    const [resumes, setResumes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [deleting, setDeleting] = useState(null);
    const navigate = useNavigate();

    const loadResumes = async () => {
        try {
            let res = await authApis().get(endpoints['resumes']);
            setResumes(res.data || []);
        } catch {
            setResumes([]);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadResumes();
    }, []);

    const handleEdit = (resumeId) => {
        navigate(`/candidate/resume/${resumeId}`);
    };

    const handleDelete = async (resumeId) => {
        if (!window.confirm("Bạn có chắc muốn xóa hồ sơ này?")) return;
        setDeleting(resumeId);
        try {
            await authApis().delete(`/candidate/resume/${resumeId}`);
            setResumes(resumes.filter(r => r.resumeId !== resumeId));
        } catch {
            alert("Xóa thất bại. Vui lòng thử lại!");
        } finally {
            setDeleting(null);
        }
    };

    const handleAdd = () => {
        navigate(`/candidate/resume/`);
    };

    if (loading)
        return (
            <div className="text-center mt-5">
                <Spinner animation="border" />
            </div>
        );

    if (!resumes || resumes.length === 0)
        return (
            <Container className="py-4">
                <div className="d-flex justify-content-between align-items-center mb-3">
                    <h3 className="mb-0">Danh sách hồ sơ</h3>
                    <Button variant="success" onClick={handleAdd}>
                        <FaPlus className="me-2" /> Thêm hồ sơ
                    </Button>
                </div>
                <Alert variant="info" className="mt-5 text-center">
                    Không tìm thấy hồ sơ nào!
                </Alert>
            </Container>
        );

    return (
        <Container className="py-4">
            <div className="d-flex justify-content-between align-items-center mb-3">
                <h3 className="mb-0">Danh sách hồ sơ</h3>
                <Button variant="success" onClick={handleAdd}>
                    <FaPlus className="me-2" /> Thêm hồ sơ
                </Button>
            </div>
            <Row className="justify-content-center">
                <Col md={10} lg={9}>
                    {resumes.map((resume, idx) => {
                        const { candidate } = resume;
                        return (
                            <Card className="shadow-sm border-0 mb-4" key={resume.resumeId || idx}>
                                <Card.Body>
                                    <div className="d-flex align-items-center justify-content-between mb-3">
                                        <Card.Title as="h5" className="mb-0">{resume.resumeName}</Card.Title>
                                        <ButtonGroup>
                                            <Button
                                                variant="outline-primary"
                                                size="sm"
                                                onClick={() => handleEdit(resume.resumeId)}
                                            >
                                                <FaEdit /> Sửa
                                            </Button>
                                            <Button
                                                variant="outline-danger"
                                                size="sm"
                                                onClick={() => handleDelete(resume.resumeId)}
                                                disabled={deleting === resume.resumeId}
                                            >
                                                <FaTrash /> {deleting === resume.resumeId ? "Đang xóa..." : "Xóa"}
                                            </Button>
                                        </ButtonGroup>
                                    </div>
                                    <Row>
                                        <Col md={6}>
                                            <ListGroup variant="flush">
                                                <ListGroup.Item className="border-0 px-0 py-2">
                                                    <FaUserGraduate className="me-2 text-primary" /> Chuyên ngành: <span className="fw-semibold">{resume.major?.majorName}</span>
                                                </ListGroup.Item>
                                                <ListGroup.Item className="border-0 px-0 py-2">
                                                    <FaChartLine className="me-2 text-primary" /> Vị trí mong muốn: <span className="fw-semibold">{resume.level?.levelName}</span>
                                                </ListGroup.Item>
                                                <ListGroup.Item className="border-0 px-0 py-2">
                                                    <FaLightbulb className="me-2 text-primary" /> Mục tiêu nghề nghiệp: <span className="fw-semibold">{resume.careerObjective}</span>
                                                </ListGroup.Item>
                                                <ListGroup.Item className="border-0 px-0 py-2">
                                                    <FaTasks className="me-2 text-primary" /> Kinh nghiệm: <span className="fw-semibold">{resume.experience}</span>
                                                </ListGroup.Item>
                                                <ListGroup.Item className="border-0 px-0 py-2">
                                                    <FaCogs className="me-2 text-primary" /> Kỹ năng chuyên môn: <span className="fw-semibold">{resume.skills}</span>
                                                </ListGroup.Item>
                                            </ListGroup>
                                        </Col>
                                        <Col md={6}>
                                            <ListGroup variant="flush">
                                                <ListGroup.Item className="border-0 px-0 py-2">
                                                    <FaUserGraduate className="me-2 text-info" /> Học vấn: <span className="fw-semibold">{resume.education}</span>
                                                </ListGroup.Item>
                                                <ListGroup.Item className="border-0 px-0 py-2">
                                                    <FaUserFriends className="me-2 text-info" /> Kỹ năng mềm: <span className="fw-semibold">{resume.softSkills}</span>
                                                </ListGroup.Item>
                                                <ListGroup.Item className="border-0 px-0 py-2">
                                                    <FaTrophy className="me-2 text-info" /> Giải thưởng: <span className="fw-semibold">{resume.awards}</span>
                                                </ListGroup.Item>
                                            </ListGroup>
                                        </Col>
                                    </Row>
                                </Card.Body>
                            </Card>
                        );
                    })}
                </Col>
            </Row>
        </Container>
    );
};

export default Resume;