import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { Form, Button, Row, Col, Spinner, Alert } from "react-bootstrap";
import Apis, { authApis, endpoints } from "../../configs/Apis";
import LevelSelect from "../select/LevelSelect";
import LocationSelect from "../select/LocationSelect";
import MajorSelect from "../select/MajorSelect";

function formatNumber(num) {
    if (num === null || num === undefined || num === "") return "";
    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function parseNumber(str) {
    if (!str) return "";
    return str.toString().replaceAll(",", "");
}

const defaultJob = {
    jobTitle: "",
    location: null,
    major: null,
    workAddress: "",
    jobLevel: null,
    minSalary: "",
    maxSalary: "",
    quantity: "",
    deadline: "",
    workTime: "",
    description: "",
    candidateRequirement: "",
    relatedSkills: "",
    benefits: ""
};

const JobAddOrEdit = () => {
    const { jobId } = useParams();
    const navigate = useNavigate();
    const [job, setJob] = useState(defaultJob);
    const [loading, setLoading] = useState(!!jobId);
    const [error, setError] = useState("");

    useEffect(() => {
        if (jobId) {
            const loadJob = async () => {
                try {
                    const res = await Apis.get(`/job/${jobId}`);
                    setJob({
                        ...defaultJob,
                        ...res.data,
                        location: res.data.location || null,
                        major: res.data.major || null,
                        jobLevel: res.data.jobLevel || null,
                        minSalary: res.data.minSalary ? formatNumber(res.data.minSalary) : "",
                        maxSalary: res.data.maxSalary ? formatNumber(res.data.maxSalary) : "",
                        quantity: res.data.quantity ? formatNumber(res.data.quantity) : "",
                        deadline: res.data.deadline || "",
                    });
                } catch (error) {
                    setError("Không thể tải dữ liệu công việc.");
                } finally {
                    setLoading(false);
                }
            };
            loadJob();
        }
    }, [jobId]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === "minSalary" || name === "maxSalary" || name === "quantity") {
            let cleaned = value.replace(/[^0-9,]/g, "");

            cleaned = cleaned.replace(/^0+/, '');
            setJob(prev => ({
                ...prev,
                [name]: formatNumber(parseNumber(cleaned))
            }));
        } else {
            setJob({ ...job, [name]: value });
        }
    };

    const handleLocationChange = (option) => {
        setJob({ ...job, location: option });
    };

    const handleMajorChange = (option) => {
        setJob({ ...job, major: option });
    };

    const handleLevelChange = (option) => {
        setJob({ ...job, jobLevel: option });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        try {
            if (
                !job.jobTitle ||
                !job.location ||
                !job.location.locationId ||
                !job.major ||
                !job.major.majorId ||
                !job.jobLevel ||
                !job.jobLevel.levelId
            ) {
                setError("Vui lòng nhập đầy đủ tên công việc, địa điểm và cấp bậc.");
                return;
            }
            let dataToSend = {
                ...job,
                location: { locationId: job.location.locationId },
                major: { majorId: job.major.majorId },
                jobLevel: { levelId: job.jobLevel.levelId },
                deadline: job.deadline ? job.deadline : null,
                minSalary: job.minSalary ? Number(parseNumber(job.minSalary)) : null,
                maxSalary: job.maxSalary ? Number(parseNumber(job.maxSalary)) : null,
                quantity: job.quantity ? Number(parseNumber(job.quantity)) : null,
            };
            if (jobId) {
                await authApis().patch(`${endpoints['job']}/${jobId}`, dataToSend);
            } else {
                await authApis().post(endpoints['job'], dataToSend);
            }
            navigate("/employer/jobs");
        } catch (error) {
            setError("Có lỗi xảy ra. Vui lòng thử lại.");
        }
    };

    if (loading)
        return (
            <div className="text-center mt-5">
                <Spinner animation="border" />
            </div>
        );

    return (
        <Form onSubmit={handleSubmit} className="p-3 p-md-4 bg-white shadow-sm rounded" style={{ maxWidth: 750, margin: "0 auto" }}>
            <h4 className="mb-4">{jobId ? "Cập nhật tin tuyển dụng" : "Tạo mới tin tuyển dụng"}</h4>
            {error && <Alert variant="danger">{error}</Alert>}
            <Form.Group className="mb-3">
                <Form.Label>Tên công việc <span className="text-danger">*</span></Form.Label>
                <Form.Control
                    name="jobTitle"
                    value={job.jobTitle}
                    onChange={handleChange}
                    placeholder="VD: Lập trình viên Frontend"
                    required
                />
            </Form.Group>
            <Row>
                <Col md={6}>
                    <Form.Group className="mb-3">
                        <Form.Label>Địa điểm <span className="text-danger">*</span></Form.Label>
                        <LocationSelect
                            value={job.location}
                            onChange={handleLocationChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group className="mb-3">
                        <Form.Label>Chuyên ngành <span className="text-danger">*</span></Form.Label>
                        <MajorSelect
                            value={job.major}
                            onChange={handleMajorChange}
                        />
                    </Form.Group>
                </Col>
            </Row>
            <Row>
                <Col md={6}>
                    <Form.Group className="mb-3">
                        <Form.Label>Cấp bậc <span className="text-danger">*</span></Form.Label>
                        <LevelSelect
                            value={job.jobLevel}
                            onChange={handleLevelChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group className="mb-3">
                        <Form.Label>Hình thức làm việc</Form.Label>
                        <Form.Control
                            name="workTime"
                            value={job.workTime}
                            onChange={handleChange}
                            placeholder="VD: Toàn thời gian, Bán thời gian, Remote..."
                        />
                    </Form.Group>
                </Col>
            </Row>
            <Row>
                <Col md={6}>
                    <Form.Group className="mb-3">
                        <Form.Label>Lương tối thiểu (VNĐ)</Form.Label>
                        <Form.Control
                            name="minSalary"
                            type="text"
                            inputMode="numeric"
                            value={job.minSalary}
                            onChange={handleChange}
                            placeholder="Nhập mức lương tối thiểu"
                            autoComplete="off"
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group className="mb-3">
                        <Form.Label>Lương tối đa (VNĐ)</Form.Label>
                        <Form.Control
                            name="maxSalary"
                            type="text"
                            inputMode="numeric"
                            value={job.maxSalary}
                            onChange={handleChange}
                            placeholder="Nhập mức lương tối đa"
                            autoComplete="off"
                        />
                    </Form.Group>
                </Col>
            </Row>
            <Row>
                <Col md={6}>
                    <Form.Group className="mb-3">
                        <Form.Label>Số lượng tuyển</Form.Label>
                        <Form.Control
                            name="quantity"
                            type="text"
                            inputMode="numeric"
                            value={job.quantity}
                            onChange={handleChange}
                            placeholder="Nhập số lượng cần tuyển"
                            autoComplete="off"
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group className="mb-3">
                        <Form.Label>Hạn nộp hồ sơ</Form.Label>
                        <Form.Control
                            name="deadline"
                            type="date"
                            value={job.deadline}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>
            <Form.Group className="mb-3">
                <Form.Label>Địa chỉ chi tiết</Form.Label>
                <Form.Control
                    name="workAddress"
                    value={job.workAddress}
                    onChange={handleChange}
                    placeholder="VD: Tầng 10, tòa nhà ABC, Quận 1"
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Mô tả công việc</Form.Label>
                <Form.Control
                    as="textarea"
                    rows={3}
                    name="description"
                    value={job.description}
                    onChange={handleChange}
                    placeholder="Mô tả chi tiết về công việc"
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Yêu cầu ứng viên</Form.Label>
                <Form.Control
                    as="textarea"
                    rows={3}
                    name="candidateRequirement"
                    value={job.candidateRequirement}
                    onChange={handleChange}
                    placeholder="VD: Có kinh nghiệm ReactJS, tốt nghiệp CNTT,..."
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Kỹ năng liên quan</Form.Label>
                <Form.Control
                    name="relatedSkills"
                    value={job.relatedSkills}
                    onChange={handleChange}
                    placeholder="VD: JavaScript, React, SQL..."
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Quyền lợi</Form.Label>
                <Form.Control
                    as="textarea"
                    rows={2}
                    name="benefits"
                    value={job.benefits}
                    onChange={handleChange}
                    placeholder="VD: Thưởng, bảo hiểm, du lịch..."
                />
            </Form.Group>
            <div className="d-flex gap-2">
                <Button type="submit" variant="success">{jobId ? "Cập nhật tin" : "Tạo mới tin"}</Button>
                <Button variant="secondary" onClick={() => navigate("/employer/jobs")}>Quay lại</Button>
            </div>
        </Form>
    );
};

export default JobAddOrEdit;