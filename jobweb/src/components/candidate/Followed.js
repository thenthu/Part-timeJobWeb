import React, { useEffect, useState } from "react";
import { Card, Row, Col, Button, Image, Alert, Spinner } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { FaRegBuilding } from "react-icons/fa";
import Apis, { authApis, endpoints } from "../../configs/Apis";

const Followed = () => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [unfollowing, setUnfollowing] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const loadFollowed = async () => {
            try {
                const res = await authApis().get(endpoints["followed"]);
                setData(res.data);
            } catch (err) {
                setError("Không thể tải danh sách công ty đã theo dõi.");
            } finally {
                setLoading(false);
            }
        };
        loadFollowed();
    }, []);

    const handleUnfollow = async (employerId) => {
        if (!window.confirm("Bạn có chắc muốn bỏ theo dõi công ty này?")) return;
        setUnfollowing(employerId);
        try {
            await authApis().delete(`${endpoints["followed"]}/${employerId}`);
            setData(prev => prev.filter(item => item.employer.employerId !== employerId));
        } catch (err) {
            alert("Có lỗi xảy ra khi bỏ theo dõi!");
        } finally {
            setUnfollowing(null);
        }
    };

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

    if (!data || data.length === 0)
        return (
            <Alert variant="info" className="text-center my-5">
                Bạn chưa theo dõi công ty nào.
            </Alert>
        );

    return (
        <div className="container py-4">
            <h3 className="mb-4 text-primary">Công ty đã theo dõi</h3>
            <Row>
                {data.map((item, idx) => {
                    const { employer, followedAt } = item;
                    return (
                        <Col md={6} lg={4} className="mb-4" key={employer.employerId || idx}>
                            <Card className="h-100 d-flex flex-column shadow-sm border-0">
                                <Card.Body className="d-flex flex-column">
                                    <div className="d-flex align-items-center mb-3">
                                        <Image
                                            src={employer.avatar}
                                            alt="Logo công ty"
                                            style={{
                                                width: 54,
                                                height: 54,
                                                borderRadius: 12,
                                                objectFit: "cover",
                                                marginRight: 16,
                                                background: "#f8f9fa"
                                            }}
                                        />
                                        <div>
                                            <div style={{ fontWeight: "bold", fontSize: 18 }}>
                                                {employer.companyName}
                                            </div>
                                            <div className="text-muted" style={{ fontSize: 13 }}>
                                                <FaRegBuilding className="me-1" />
                                                Mã số thuế: {employer.taxCode}
                                            </div>
                                            <div style={{ fontSize: 12, color: "#888" }}>
                                                Đại diện: {employer.representativeName} ({employer.representativeTitle})
                                            </div>
                                        </div>
                                    </div>

                                    <div className="mb-2 mt-auto">
                                        <span className="fw-semibold">Ngày theo dõi:</span>{" "}
                                        {followedAt ? new Date(followedAt).toLocaleDateString() : ""}
                                    </div>
                                    <div className="d-flex gap-2 mt-2">
                                        <Button
                                            variant="outline-primary"
                                            size="sm"
                                            onClick={() => navigate(`/employer/${employer.employerId}`)}
                                        >
                                            Xem trang công ty
                                        </Button>
                                        <Button
                                            variant="outline-danger"
                                            size="sm"
                                            onClick={() => handleUnfollow(employer.employerId)}
                                            disabled={unfollowing === employer.employerId}
                                        >
                                            {unfollowing === employer.employerId ? "Đang bỏ..." : "Bỏ theo dõi"}
                                        </Button>
                                    </div>
                                </Card.Body>
                            </Card>
                        </Col>
                    );
                })}
            </Row>
        </div>
    );
};

export default Followed;