import { useEffect, useState, useContext } from "react";
import { Card, Row, Col, ListGroup, Badge, Alert, Container, Image, Spinner, Button, ButtonGroup, Dropdown, DropdownButton } from "react-bootstrap";
import { FaEnvelope, FaUserTie, FaIdBadge, FaFileInvoice, FaMapMarkerAlt, FaPhone, FaTransgender, FaCalendarAlt, FaStar, FaBriefcase, FaEye, FaUserFriends, FaBuilding, FaFileAlt, FaPaperPlane, FaEdit } from "react-icons/fa";
import Apis, { authApis, endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";
import { MyUserContext } from "../configs/Contexts";
import Review from "./Review";

const Profile = () => {
    const [user, dispatch] = useContext(MyUserContext);
    const [profile, setProfile] = useState(null);
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [loadingReviews, setLoadingReviews] = useState(true);
    const navigate = useNavigate();

    const loadProfile = async () => {
        let url = `${endpoints['profile']}`;
        try {
            let res = await authApis().get(url);
            setProfile(res.data);

            if (res.data?.username) {
                try {
                    const reviewRes = await Apis.get(`/profile/${res.data.username}/review`);
                    setReviews(reviewRes.data || []);
                } catch {
                    setReviews([]);
                }
            }
        } catch (err) {
            setProfile(null);
            setReviews([]);
        } finally {
            setLoading(false);
            setLoadingReviews(false);
        }
    };

    useEffect(() => {
        loadProfile();
    }, []);

    if (loading)
        return (
            <div className="text-center mt-5">
                <Spinner animation="border" />
            </div>
        );

    if (!profile)
        return (
            <Alert variant="danger" className="mt-5 text-center">
                Không tìm thấy thông tin cá nhân!
                <div>
                    <Button variant="secondary" onClick={() => navigate(-1)} className="mt-3">
                        Quay lại
                    </Button>
                </div>
            </Alert>
        );

    const isEmployer = profile.role?.roleName === "ROLE_EMPLOYER";
    const isCandidate = profile.role?.roleName === "ROLE_CANDIDATE";

    const renderActionButtons = () => {
        if (isEmployer) {
            return (
                <ButtonGroup className="mb-3">
                    <Button variant="outline-success" onClick={() => navigate("/employer/jobs")}>
                        <FaBriefcase className="me-1" /> Tin đã đăng
                    </Button>
                    <Button variant="outline-primary" onClick={() => navigate("/employer/followers")}>
                        <FaUserFriends className="me-1" /> Người theo dõi
                    </Button>
                </ButtonGroup>
            );
        } else if (isCandidate) {
            return (
                <ButtonGroup className="mb-3">
                    <Button variant="outline-success" onClick={() => navigate("/candidate/resumes")}>
                        <FaFileAlt className="me-1" /> Xem hồ sơ xin việc
                    </Button>
                    <Button variant="outline-primary" onClick={() => navigate("/candidate/applications")}>
                        <FaPaperPlane className="me-1" /> Đơn đã nộp
                    </Button>
                    <Button variant="outline-info" onClick={() => navigate("/candidate/followed")}>
                        <FaBuilding className="me-1" /> Công ty đã theo dõi
                    </Button>
                </ButtonGroup>
            );
        }
        return null;
    };

    const renderTopRightMenu = () => {
        if (isEmployer || isCandidate) {
            return (
                <div
                    style={{
                        position: "absolute",
                        top: "20px",
                        right: "20px",
                        zIndex: 2,
                    }}
                >
                    <>
                        <style>
                            {`
                                #dropdown-ellipsis.no-caret::after {
                                    display: none !important;
                                }
                            `}
                        </style>
                        <Dropdown align="end">
                            <Dropdown.Toggle
                                as="span"
                                id="dropdown-ellipsis"
                                className="no-caret"
                                style={{
                                    background: "none",
                                    border: "none",
                                    padding: 0,
                                    cursor: "pointer",
                                    boxShadow: "none",
                                    display: "flex",
                                    alignItems: "center"
                                }}
                            >
                                <span style={{ fontSize: '1.5rem', fontWeight: 'bold' }}>⋮</span>
                            </Dropdown.Toggle>

                            <Dropdown.Menu>
                                <Dropdown.Item onClick={() => navigate("/change-password")}>
                                    Đổi mật khẩu
                                </Dropdown.Item>
                                <Dropdown.Item onClick={() =>
                                    navigate(isCandidate ? "/candidate/edit" : "/employer/edit", { state: { profile } })
                                }>
                                    Chỉnh sửa tài khoản
                                </Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>
                    </>
                </div>
            );
        }
        return null;
    };

    return (
        <Container className="py-4">
            <Row className="justify-content-center">
                <Col md={7} lg={6}>
                    <Card className="shadow-sm border-0 mb-4" style={{ position: "relative" }}>
                        {renderTopRightMenu()}
                        <Card.Body>
                            <div className="d-flex flex-column align-items-center">
                                <Image
                                    src={profile.avatar}
                                    roundedCircle
                                    width={110}
                                    height={110}
                                    alt="avatar"
                                    className="mb-3 border border-2"
                                    style={{ objectFit: "cover" }}
                                />
                                <h4 className="fw-bold">
                                    {isEmployer
                                        ? profile.companyName
                                        : profile.fullName}
                                </h4>
                                <Badge bg="success" className="mb-2">
                                    {isCandidate
                                        ? "Ứng viên"
                                        : isEmployer
                                            ? "Nhà tuyển dụng"
                                            : profile.role?.roleName}
                                </Badge>
                                <div className="text-muted mb-2">
                                    Tài khoản: <strong>{profile.username}</strong>
                                </div>
                                <div className="mb-3 small text-muted">
                                    Tham gia từ:{" "}
                                    {profile.createdAt
                                        ? new Date(profile.createdAt).toLocaleDateString("vi-VN")
                                        : ""}
                                </div>
                                {renderActionButtons()}
                            </div>
                            <hr />
                            <ListGroup variant="flush">
                                {isEmployer ? (
                                    <>
                                        <ListGroup.Item className="border-0 px-0 py-2">
                                            <FaEnvelope className="me-2 text-success" /> Email:{" "}
                                            <span className="fw-semibold">{profile.email}</span>
                                        </ListGroup.Item>
                                        <ListGroup.Item className="border-0 px-0 py-2">
                                            <FaUserTie className="me-2 text-success" /> Đại diện pháp luật:{" "}
                                            <span className="fw-semibold">{profile.representativeName}</span>
                                        </ListGroup.Item>
                                        <ListGroup.Item className="border-0 px-0 py-2">
                                            <FaIdBadge className="me-2 text-success" /> Chức vụ:{" "}
                                            <span className="fw-semibold">{profile.representativeTitle}</span>
                                        </ListGroup.Item>
                                        <ListGroup.Item className="border-0 px-0 py-2">
                                            <FaFileInvoice className="me-2 text-success" /> Mã số thuế:{" "}
                                            <span className="fw-semibold">{profile.taxCode}</span>
                                        </ListGroup.Item>
                                        {(profile.workEnvImg1 || profile.workEnvImg2 || profile.workEnvImg3) && (
                                            <ListGroup.Item className="border-0 px-0 py-2">
                                                <div className="d-flex gap-2">
                                                    {[profile.workEnvImg1, profile.workEnvImg2, profile.workEnvImg3]
                                                        .filter(Boolean)
                                                        .map((img, idx) => (
                                                            <Image
                                                                key={idx}
                                                                src={img}
                                                                alt={`work-env-${idx + 1}`}
                                                                width={80}
                                                                height={60}
                                                                rounded
                                                                className="border"
                                                            />
                                                        ))}
                                                </div>
                                            </ListGroup.Item>
                                        )}
                                    </>
                                ) : (
                                    <>
                                        <ListGroup.Item className="border-0 px-0 py-2">
                                            <FaEnvelope className="me-2 text-success" /> Email:{" "}
                                            <span className="fw-semibold">{profile.email}</span>
                                        </ListGroup.Item>
                                        <ListGroup.Item className="border-0 px-0 py-2">
                                            <FaPhone className="me-2 text-success" /> Số điện thoại:{" "}
                                            <span className="fw-semibold">{profile.phone}</span>
                                        </ListGroup.Item>
                                        <ListGroup.Item className="border-0 px-0 py-2">
                                            <FaTransgender className="me-2 text-success" /> Giới tính:{" "}
                                            <span className="fw-semibold">{profile.gender}</span>
                                        </ListGroup.Item>
                                        <ListGroup.Item className="border-0 px-0 py-2">
                                            <FaCalendarAlt className="me-2 text-success" /> Ngày sinh:{" "}
                                            <span className="fw-semibold">
                                                {profile.birthDate
                                                    ? new Date(profile.birthDate).toLocaleDateString("vi-VN")
                                                    : ""}
                                            </span>
                                        </ListGroup.Item>
                                        <ListGroup.Item className="border-0 px-0 py-2">
                                            <FaMapMarkerAlt className="me-2 text-success" /> Địa chỉ:{" "}
                                            <span className="fw-semibold">{profile.address}</span>
                                        </ListGroup.Item>
                                    </>
                                )}
                            </ListGroup>
                        </Card.Body>
                    </Card>

                    <Card className="shadow-sm border-0">
                        <Card.Header className="bg-white">
                            <h5 className="mb-0">Đánh giá</h5>
                        </Card.Header>
                        <Card.Body>
                            <Review username={profile.username} />
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default Profile;