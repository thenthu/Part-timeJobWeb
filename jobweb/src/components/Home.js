import { useEffect, useState } from "react";
import { Alert, Button, Card, Col, Form, InputGroup, Row } from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import { useSearchParams } from "react-router-dom";
import MySpinner from "./layout/MySpinner";
import { FaSearch, FaChevronLeft, FaChevronRight } from "react-icons/fa";
import MajorSelect from "./select/MajorSelect";
import LocationSelect from "./select/LocationSelect";
import LevelSelect from "./select/LevelSelect";
import { useNavigate } from "react-router-dom";
import React from "react";

const Home = () => {
    const [jobs, setJobs] = useState([]);
    const [loading, setLoading] = useState(true);
    const [q, setQ] = useState("");
    const [page, setPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [params] = useSearchParams();

    const [selectedMajor, setSelectedMajor] = useState("");
    const [selectedLocation, setSelectedLocation] = useState("");
    const [selectedLevel, setSelectedLevel] = useState("");
    const [selectedSalary, setSelectedSalary] = useState("");
    const navigate = useNavigate();

    const loadJobs = async () => {
        let url = `${endpoints['jobs']}?page=${page}`;
        if (q) url += `&kw=${q}`;
        if (selectedMajor) url += `&majorId=${selectedMajor}`;
        if (selectedLocation) url += `&locationId=${selectedLocation}`;
        if (selectedLevel) url += `&levelId=${selectedLevel}`;
        if (selectedSalary) url += `&salaryRange=${selectedSalary}`;

        try {
            let res = await Apis.get(url);
            const data = res.data;
            setTotalPages(data.totalPages || 1);

            setJobs(data.jobs);
        } catch (ex) {
            console.error(ex);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        setLoading(true);
        const timer = setTimeout(() => {
            loadJobs();
        }, 350);
        return () => clearTimeout(timer);
    }, [q, selectedMajor, selectedLocation, selectedLevel, selectedSalary, page]);

    useEffect(() => {
        setPage(1);
    }, [q, selectedMajor, selectedLocation, selectedLevel, selectedSalary]);

    function getSalaryText(min, max) {
        const formatMillion = (value) => (value / 1_000_000).toLocaleString(undefined, {
            minimumFractionDigits: 0,
            maximumFractionDigits: 1,
        });

        if (!min && !max) return "Thoả thuận";
        if (min && max) return `${formatMillion(min)} - ${formatMillion(max)} triệu`;
        if (min && !max) return `Từ ${formatMillion(min)} triệu`;
        if (!min && max) return `Đến ${formatMillion(max)} triệu`;
        return "Thoả thuận";
    }

    return (
        <div className="container py-4">
            <InputGroup>
                <InputGroup.Text className="bg-white border-end-0 rounded-start-4 mb-4" style={{ fontSize: 18 }}>
                    <FaSearch />
                </InputGroup.Text>
                <Form.Control
                    placeholder="Tìm kiếm công việc..."
                    value={q}
                    onChange={e => setQ(e.target.value)}
                    className="border-start-0 rounded-end-4 mb-4"
                    style={{
                        boxShadow: "none",
                        fontSize: 16,
                        paddingLeft: 0,
                        background: "#fff"
                    }}
                />
            </InputGroup>

            <Row className="mb-4">
                <Col md={4}>
                    <MajorSelect onChange={option => setSelectedMajor(option ? option.majorId : "")} />
                </Col>
                <Col md={4}>
                    <LocationSelect onChange={option => setSelectedLocation(option ? option.locationId : "")} />
                </Col>
                <Col md={4}>
                    <LevelSelect onChange={option => setSelectedLevel(option ? option.levelId : "")} />
                </Col>
            </Row>
            <Row className="mb-4">
                <div className="d-flex gap-2 flex-wrap">
                    {[
                        { label: "Tất cả", value: "" },
                        { label: "Dưới 5 triệu", value: "1" },
                        { label: "Từ 5–10 triệu", value: "2" },
                        { label: "Từ 10–20 triệu", value: "3" },
                        { label: "Từ 20–30 triệu", value: "4" },
                        { label: "Trên 30 triệu", value: "5" }
                    ].map(item => (
                        <Button
                            key={item.value}
                            variant={selectedSalary === item.value ? "success" : "light"}
                            onClick={() => setSelectedSalary(item.value)}
                            className="rounded-pill"
                        >
                            {item.label}
                        </Button>
                    ))}
                </div>
            </Row>

            {(!jobs || jobs.length === 0) && !loading && (
                <Alert variant="info" className="text-center">Không có công việc nào!</Alert>
            )}

            <Row>
                {jobs.map(j => (
                    <Col key={j.jobId} md={6} lg={4} className="mb-4">
                        <Card
                            className="shadow-sm h-100 border-0 job-card-hover"
                            onClick={() => navigate(`/job/${j.jobId}`)}
                            style={{ cursor: "pointer" }}
                        >
                            <Card.Body className="d-flex">
                                <img
                                    src={j.employer.avatar}
                                    alt="Logo"
                                    style={{
                                        width: 64,
                                        height: 64,
                                        objectFit: "cover",
                                        borderRadius: 12,
                                        marginRight: 16,
                                        flexShrink: 0
                                    }}
                                />
                                <div className="flex-grow-1">
                                    <Card.Title className="text-primary mb-1" style={{ fontSize: "1.05rem" }}>
                                        {j.jobTitle}
                                    </Card.Title>
                                    <div className="text-muted mb-2">{j.employer.companyName}</div>
                                    <div className="d-flex gap-2 flex-wrap">
                                        <span className="badge bg-light text-dark border">
                                            {getSalaryText(j.minSalary, j.maxSalary)}
                                        </span>
                                        <span className="badge bg-light text-dark border">
                                            {j.location?.locationName || "Chưa rõ địa điểm"}
                                        </span>
                                    </div>
                                </div>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>

            {loading && <div className="text-center"><MySpinner /></div>}

            {totalPages > 1 && !loading && (
                <div className="d-flex justify-content-center align-items-center mt-4" style={{ gap: "18px" }}>
                    <Button
                        variant="outline-success"
                        className="d-flex align-items-center justify-content-center"
                        style={{
                            borderRadius: "50%",
                            width: 48,
                            height: 48,
                            fontSize: 16,
                            borderWidth: 2,
                        }}
                        disabled={page <= 1}
                        onClick={() => setPage(page - 1)}
                    >
                        <FaChevronLeft />
                    </Button>
                    <span className="fw-bold" style={{ fontSize: 16, color: "#23a455" }}>{page}</span>
                    <span style={{ fontSize: 16, color: "#888" }}>/ {totalPages} trang</span>
                    <Button
                        variant="outline-success"
                        className="d-flex align-items-center justify-content-center"
                        style={{
                            borderRadius: "50%",
                            width: 48,
                            height: 48,
                            fontSize: 16,
                            borderWidth: 2,
                        }}
                        disabled={page >= totalPages}
                        onClick={() => setPage(page + 1)}
                    >
                        <FaChevronRight />
                    </Button>
                </div>
            )}
        </div>
    );
};

export default Home;