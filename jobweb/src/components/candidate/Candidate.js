import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState, useContext } from "react";
import { Card, Row, Col, ListGroup, Badge, Alert, Container, Image, Spinner, Button } from "react-bootstrap";
import { FaEnvelope, FaIdCard, FaUser, FaBirthdayCake, FaVenusMars, FaMapMarkerAlt, FaRegHeart, FaHeart } from "react-icons/fa";
import Apis, { authApis, endpoints } from "../../configs/Apis";
import { MyUserContext } from "../../configs/Contexts";
import Review from "../Review";

const Candidate = () => {
    const { candidateId } = useParams();
    const [candidate, setCandidate] = useState(null);
    const [loading, setLoading] = useState(true);
    const [followed, setFollowed] = useState(false);
    const [followLoading, setFollowLoading] = useState(false);
    const [followList, setFollowList] = useState([]);
    const navigate = useNavigate();

    const [user] = useContext(MyUserContext);
    const isLoggedIn = !!user;

    useEffect(() => {
        const loadCandidate = async () => {
            try {
                const res = await authApis().get(`/secure/candidate/${candidateId}`);
                setCandidate(res.data);
            } catch (err) {
                setCandidate(null);
            } finally {
                setLoading(false);
            }
        };
        loadCandidate();
    }, [candidateId]);

    if (loading)
        return (
            <div className="text-center mt-5">
                <Spinner animation="border" />
            </div>
        );

    if (!candidate)
        return (
            <Alert variant="danger" className="mt-5 text-center">
                Không tìm thấy thông tin ứng viên!
                <div>
                    <Button variant="secondary" onClick={() => navigate(-1)} className="mt-3">
                        Quay lại
                    </Button>
                </div>
            </Alert>
        );

    return (
        <Container className="py-4">
            <Row className="justify-content-center">
                <Col md={8} lg={7}>
                    <Card className="shadow-sm border-0 mb-4">
                        <Card.Body>
                            <div className="d-flex flex-column align-items-center">
                                <Image
                                    src={candidate.avatar}
                                    roundedCircle
                                    width={180}
                                    height={180}
                                    alt="avatar"
                                    className="mb-3 border border-2"
                                    style={{ objectFit: "cover" }}
                                />
                                <h4 className="fw-bold">{candidate.fullName}</h4>
                                <Badge bg="primary" className="mb-2">
                                    Ứng viên
                                </Badge>
                                <div className="mb-3 small text-muted">
                                    Tham gia từ:{" "}
                                    {candidate.createdAt
                                        ? new Date(candidate.createdAt).toLocaleDateString("vi-VN")
                                        : ""}
                                </div>
                            </div>
                            <hr />
                            <ListGroup variant="flush">
                                <ListGroup.Item className="border-0 px-0 py-2">
                                    <FaEnvelope className="me-2 text-primary" /> Email:{" "}
                                    <span className="fw-semibold">{candidate.email}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="border-0 px-0 py-2">
                                    <FaIdCard className="me-2 text-primary" /> Username:{" "}
                                    <span className="fw-semibold">{candidate.username}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="border-0 px-0 py-2">
                                    <FaUser className="me-2 text-primary" /> Giới tính:{" "}
                                    <span className="fw-semibold">{candidate.gender}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="border-0 px-0 py-2">
                                    <FaBirthdayCake className="me-2 text-primary" /> Ngày sinh:{" "}
                                    <span className="fw-semibold">
                                        {candidate.dob
                                            ? new Date(candidate.dob).toLocaleDateString("vi-VN")
                                            : ""}
                                    </span>
                                </ListGroup.Item>
                                <ListGroup.Item className="border-0 px-0 py-2">
                                    <FaMapMarkerAlt className="me-2 text-primary" /> Địa chỉ:{" "}
                                    <span className="fw-semibold">{candidate.address}</span>
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                    <Card className="shadow-sm border-0">
                        <Card.Header className="bg-white">
                            <h5 className="mb-0">Đánh giá về ứng viên</h5>
                        </Card.Header>
                        <Card.Body>
                            <Review username={candidate.username} />
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default Candidate;