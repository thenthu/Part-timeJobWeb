import { useEffect, useState } from "react";
import { Card, Spinner, Alert, Container, Button, Row, Col, Image } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import { useNavigate } from "react-router-dom";

const Follower = () => {
    const [followers, setFollowers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        const fetchFollowers = async () => {
            let url = `${endpoints['followers']}`;
            try {
                const res = await authApis().get(url);
                setFollowers(res.data || []);
            } catch (err) {
                setError("Không thể tải danh sách người theo dõi.");
            } finally {
                setLoading(false);
            }
        };
        fetchFollowers();
    }, []);

    const formatDate = (dt) => {
        if (!dt) return "-";
        const [date] = dt.split(" ");
        return `${date}`;
    };

    return (
        <Container className="my-4">
            <h3 className="mb-4">Danh sách người theo dõi công ty</h3>
            {loading ? (
                <div className="text-center my-5">
                    <Spinner animation="border" />
                </div>
            ) : error ? (
                <Alert variant="danger">{error}</Alert>
            ) : followers.length === 0 ? (
                <Alert variant="info">Chưa có ứng viên nào theo dõi công ty của bạn.</Alert>
            ) : (
                <Row xs={1} sm={2} md={3} lg={4} className="g-4">
                    {followers.map((f, idx) => {
                        return (
                            <Col key={f.followerId || idx}>
                                <Card className="h-100 text-center shadow-sm border-0">
                                    <Card.Body className="d-flex align-items-center justify-content-center flex-row gap-3">
                                        <Image
                                            src={f.candidate?.avatar}
                                            roundedCircle
                                            width={60}
                                            height={60}
                                            alt="avatar"
                                            style={{ objectFit: "cover", border: "2px solid #eee" }}
                                        />
                                        <div className="text-start">
                                            <Card.Title className="mb-1">{f.candidate?.username}</Card.Title>
                                            <Card.Text className="text-muted" style={{ fontSize: "0.95em" }}>
                                                Theo dõi ngày: {formatDate(f.followedAt)}
                                            </Card.Text>
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
        </Container>
    );
};

export default Follower;