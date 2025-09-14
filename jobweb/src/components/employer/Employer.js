import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState, useContext } from "react";
import { Card, Row, Col, ListGroup, Badge, Alert, Container, Image, Spinner, Button } from "react-bootstrap";
import { FaEnvelope, FaUserTie, FaIdBadge, FaFileInvoice, FaStar, FaRegHeart, FaHeart } from "react-icons/fa";
import Apis, { authApis, endpoints } from "../../configs/Apis";
import { MyUserContext } from "../../configs/Contexts";
import Review from "../Review";

const Employer = () => {
    const { employerId } = useParams();
    const [employer, setEmployer] = useState(null);
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [loadingReviews, setLoadingReviews] = useState(true);
    const [followed, setFollowed] = useState(false);
    const [followLoading, setFollowLoading] = useState(false);
    const [followList, setFollowList] = useState([]);
    const navigate = useNavigate();

    const [user] = useContext(MyUserContext);
    const isLoggedIn = !!user;

    useEffect(() => {
        const loadEmployer = async () => {
            try {
                const res = await Apis.get(`/employer/${employerId}`);
                setEmployer(res.data);
            } catch (err) {
                setEmployer(null);
                setReviews([]);
            } finally {
                setLoading(false);
                setLoadingReviews(false);
            }
        };
        loadEmployer();
    }, [employerId]);

    useEffect(() => {
        const loadFollowedEmployers = async () => {
            if (!isLoggedIn) {
                setFollowList([]);
                setFollowed(false);
                return;
            }
            try {
                const res = await authApis().get(endpoints["followed"]);
                setFollowList(res.data || []);

                const found = (res.data || []).some(item =>
                    item.employer && String(item.employer.employerId) === String(employerId)
                );
                setFollowed(found);
            } catch (err) {
                setFollowList([]);
                setFollowed(false);
            }
        };
        loadFollowedEmployers();
    }, [isLoggedIn, employerId]);

    const handleFollow = async () => {
        setFollowLoading(true);
        if (followed) {
            if (!window.confirm("Bạn có chắc muốn bỏ theo dõi công ty này?")) return;
            try {
                await authApis().delete(`${endpoints["followed"]}/${employerId}`);
                setFollowed(false);
            } catch (err) {
                alert("Có lỗi xảy ra khi bỏ theo dõi!");
            } finally {
                setFollowLoading(false);
            }
        }
        else {
            try {
                await authApis().post(`${endpoints["followed"]}/${employerId}`);
                setFollowed(true);
            } catch (err) {
                alert("Có lỗi khi theo dõi công ty!");
            } finally {
                setFollowLoading(false);
            }
        }
    };

    if (loading)
        return (
            <div className="text-center mt-5">
                <Spinner animation="border" />
            </div>
        );

    if (!employer)
        return (
            <Alert variant="danger" className="mt-5 text-center">
                Không tìm thấy thông tin công ty!
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
                                    src={employer.avatar}
                                    roundedCircle
                                    width={180}
                                    height={180}
                                    alt="avatar"
                                    className="mb-3 border border-2"
                                    style={{ objectFit: "cover" }}
                                />
                                <h4 className="fw-bold">{employer.companyName}</h4>
                                <Badge bg="success" className="mb-2">
                                    Nhà tuyển dụng
                                </Badge>
                                <div className="mb-3 small text-muted">
                                    Tham gia từ:{" "}
                                    {employer.createdAt
                                        ? new Date(employer.createdAt).toLocaleDateString("vi-VN")
                                        : ""}
                                </div>
                                {isLoggedIn && (
                                    <div>
                                        {followed ? (
                                            <Button
                                                variant="outline-danger"
                                                className="mt-2"
                                                onClick={handleFollow}
                                                disabled={followLoading}
                                            >
                                                <FaHeart className="me-1" /> Đã theo dõi
                                            </Button>
                                        ) : (
                                            <Button
                                                variant="outline-primary"
                                                className="mt-2"
                                                onClick={handleFollow}
                                                disabled={followLoading}
                                            >
                                                <FaRegHeart className="me-1" />
                                                {followLoading ? "Đang theo dõi..." : "Theo dõi công ty"}
                                            </Button>
                                        )}
                                    </div>
                                )}
                            </div>
                            <hr />
                            <ListGroup variant="flush">
                                <ListGroup.Item className="border-0 px-0 py-2">
                                    <FaEnvelope className="me-2 text-success" /> Email:{" "}
                                    <span className="fw-semibold">{employer.email}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="border-0 px-0 py-2">
                                    <FaUserTie className="me-2 text-success" /> Đại diện pháp luật:{" "}
                                    <span className="fw-semibold">{employer.representativeName}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="border-0 px-0 py-2">
                                    <FaIdBadge className="me-2 text-success" /> Chức vụ:{" "}
                                    <span className="fw-semibold">{employer.representativeTitle}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="border-0 px-0 py-2">
                                    <FaFileInvoice className="me-2 text-success" /> Mã số thuế:{" "}
                                    <span className="fw-semibold">{employer.taxCode}</span>
                                </ListGroup.Item>
                                {(employer.workEnvImg1 || employer.workEnvImg2 || employer.workEnvImg3) && (
                                    <ListGroup.Item className="border-0 px-0 py-2">
                                        <div className="d-flex gap-3 justify-content-center">
                                            {[employer.workEnvImg1, employer.workEnvImg2, employer.workEnvImg3]
                                                .filter(Boolean)
                                                .map((img, idx) => (
                                                    <Image
                                                        key={idx}
                                                        src={img}
                                                        alt={`work-env-${idx + 1}`}
                                                        width={140}
                                                        height={100}
                                                        rounded
                                                        className="border"
                                                        style={{ objectFit: "cover" }}
                                                    />
                                                ))}
                                        </div>
                                    </ListGroup.Item>
                                )}
                            </ListGroup>
                        </Card.Body>
                    </Card>

                    <Card className="shadow-sm border-0">
                        <Card.Header className="bg-white">
                            <h5 className="mb-0">Đánh giá về công ty</h5>
                        </Card.Header>
                        <Card.Body>
                            <Review username={employer.username} />
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default Employer;