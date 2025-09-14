import React, { useEffect, useState, useContext } from "react";
import { ListGroup, Spinner, Card, Button, Form } from "react-bootstrap";
import { FaStar, FaEdit, FaTrash } from "react-icons/fa";
import Apis, { authApis } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";

const Review = ({ username }) => {
    const [myReview, setMyReview] = useState(null);
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [hasMore, setHasMore] = useState(true);
    const [page, setPage] = useState(1);

    const [showForm, setShowForm] = useState(false);
    const [reviewRating, setReviewRating] = useState(0);
    const [reviewComment, setReviewComment] = useState("");
    const [formLoading, setFormLoading] = useState(false);

    const [user] = useContext(MyUserContext);
    const isLoggedIn = !!user;

    const loadReviews = async (nextPage = 1, isFirst = false) => {
        if (!username) return;
        setLoading(true);
        try {
            const res = await authApis().get(`/profile/${username}/reviews?page=${nextPage}`);
            const reviewsData = res.data.reviews || [];

            if (isFirst) {
                setReviews(reviewsData);
                setMyReview(res.data.myReview);
            } else {
                setReviews(prev => [...prev, ...reviewsData]);
            }

            setHasMore(reviewsData.length > 0);
            setPage(nextPage);
        } catch (err) {
            console.error("Error loading reviews:", err);
            if (isFirst) {
                setReviews([]);
                setMyReview(null);
                setHasMore(false);
            }
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        setReviews([]); setMyReview(null); setPage(1); setHasMore(true);
        loadReviews(1, true);
    }, [username]);

    const handleDelete = async () => {
        if (!window.confirm("Bạn có chắc muốn xóa đánh giá của mình?")) return;
        setFormLoading(true);
        try {
            const reviewId = myReview.reviewId;
            await authApis().delete(`/secure/profile/review/${reviewId}`);

            loadReviews(1, true);
            setShowForm(false);
        } catch (err) {
            alert("Xóa đánh giá thất bại!");
        } finally {
            setFormLoading(false);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setFormLoading(true);
        try {
            if (myReview) {
                const reviewId = myReview.reviewId;
                await authApis().patch(`/secure/profile/${username}/review/${reviewId}`, {
                    rating: reviewRating,
                    comment: reviewComment
                });
            } else {
                await authApis().post(`/secure/profile/${username}/review`, {
                    rating: reviewRating,
                    comment: reviewComment
                });
            }

            loadReviews(1, true);
            setShowForm(false);
        } catch (err) {
            alert("Lưu đánh giá thất bại!");
        } finally {
            setFormLoading(false);
        }
    };

    const renderForm = () => (
        <Card className="mb-3 shadow-sm">
            <Card.Body>
                <Form onSubmit={handleSubmit}>
                    <div className="mb-2">
                        <span className="fw-bold me-2">Chọn số sao:</span>
                        {[...Array(5)].map((_, i) => (
                            <FaStar
                                key={i}
                                className={i < reviewRating ? "text-warning" : "text-secondary"}
                                style={{ fontSize: "1.6rem", cursor: "pointer", marginLeft: "3px" }}
                                onClick={() => setReviewRating(i + 1)}
                            />
                        ))}
                    </div>
                    <Form.Group className="mb-2">
                        <Form.Control
                            as="textarea"
                            rows={3}
                            placeholder="Nhận xét của bạn..."
                            value={reviewComment}
                            onChange={e => setReviewComment(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Button type="submit" variant="primary" disabled={formLoading || reviewRating === 0}>
                        {myReview ? "Cập nhật đánh giá" : "Gửi đánh giá"}
                    </Button>
                    {myReview && (
                        <Button
                            variant="outline-danger"
                            className="ms-2"
                            onClick={handleDelete}
                            disabled={formLoading}
                        >
                            <FaTrash /> Xóa đánh giá
                        </Button>
                    )}
                </Form>
            </Card.Body>
        </Card>
    );

    if (loading && page === 1)
        return <div className="text-center"><Spinner animation="border" /></div>;

    return (
        <>
            {myReview && (
                <Card className="mb-3 border-primary shadow-sm">
                    <Card.Body>
                        <div className="d-flex align-items-center mb-1">
                            <span className="fw-bold text-primary me-2" style={{ fontSize: "1.1rem" }}>
                                Đánh giá của bạn
                            </span>
                            <span>
                                {[...Array(5)].map((_, i) => (
                                    <FaStar
                                        key={i}
                                        className={i < (myReview.rating || 0) ? "text-warning" : "text-secondary"}
                                        style={{ fontSize: "1.4rem", marginLeft: "2px" }}
                                    />
                                ))}
                            </span>
                            <span className="ms-auto">
                                <Button
                                    variant="outline-primary"
                                    size="sm"
                                    className="me-2"
                                    onClick={() => {
                                        setReviewRating(myReview.rating);
                                        setReviewComment(myReview.comment);
                                        setShowForm(true);
                                    }}
                                >
                                    <FaEdit /> Sửa
                                </Button>
                                <Button
                                    variant="outline-danger"
                                    size="sm"
                                    onClick={handleDelete}
                                >
                                    <FaTrash /> Xóa
                                </Button>
                            </span>
                        </div>
                        <div className="fst-italic text-muted" style={{ fontSize: "1.08rem" }}>
                            {myReview.comment || <span className="text-secondary">Không có nhận xét</span>}
                        </div>
                        <div className="text-end small text-muted mt-1">
                            {myReview.createdAt
                                ? new Date(myReview.createdAt).toLocaleDateString("vi-VN")
                                : ""}
                        </div>
                    </Card.Body>
                </Card>
            )}

            {isLoggedIn && user.username !== username && (
                <>
                    {showForm && renderForm()}
                    {!myReview && !showForm && renderForm()}
                </>
            )}

            <ListGroup>
                {reviews.length > 0 ? (
                    reviews.map((rv, idx) => (
                        <ListGroup.Item key={rv.reviewId || idx} className="border-0 mb-2 p-3 bg-light rounded">
                            <div className="d-flex align-items-center mb-1">
                                <span className="fw-semibold text-primary">{rv.fromAccount?.username || "Người dùng ẩn danh"}</span>
                                <span className="ms-2">
                                    {[...Array(5)].map((_, i) => (
                                        <FaStar
                                            key={i}
                                            className={i < (rv.rating || 0) ? "text-warning" : "text-secondary"}
                                            style={{ fontSize: "1rem", marginLeft: "2px" }}
                                        />
                                    ))}
                                </span>
                            </div>
                            <div className="fst-italic text-muted" style={{ fontSize: "0.95rem" }}>
                                {rv.comment || <span className="text-secondary">Không có nhận xét</span>}
                            </div>
                            <div className="text-end small text-muted mt-1">
                                {rv.createdAt
                                    ? new Date(rv.createdAt).toLocaleDateString("vi-VN")
                                    : ""}
                            </div>
                        </ListGroup.Item>
                    ))
                ) : (
                    <div className="text-center text-muted">Chưa có đánh giá nào về người dùng này.</div>
                )}
            </ListGroup>

            {hasMore && (
                <div className="text-center my-3">
                    <Button
                        variant="outline-primary"
                        onClick={() => loadReviews(page + 1)}
                        disabled={loading}
                    >
                        {loading ? <Spinner animation="border" size="sm" /> : "Xem thêm"}
                    </Button>
                </div>
            )}
        </>
    );
};

export default Review;