import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { Form, Button, Row, Col, Spinner, Alert } from "react-bootstrap";
import LevelSelect from "../select/LevelSelect";
import MajorSelect from "../select/MajorSelect";
import Apis, { authApis, endpoints } from "../../configs/Apis";

const defaultResume = {
    resumeName: "",
    major: null,
    level: null,
    careerObjective: "",
    experience: "",
    skills: "",
    education: "",
    softSkills: "",
    awards: "",
};

const ResumeAddOrEdit = () => {
    const { resumeId } = useParams();
    const navigate = useNavigate();
    const [resume, setResume] = useState(defaultResume);
    const [loading, setLoading] = useState(!!resumeId);
    const [error, setError] = useState("");

    useEffect(() => {
        if (resumeId) {
            const loadResume = async () => {
                try {
                    const res = await authApis().get(`${endpoints['resume']}/${resumeId}`);
                    setResume({
                        ...defaultResume,
                        ...res.data,
                        major: res.data.major || null,
                        level: res.data.level || null,
                    });
                } catch (error) {
                    setError("Không thể tải dữ liệu hồ sơ.");
                } finally {
                    setLoading(false);
                }
            };
            loadResume();
        }
    }, [resumeId]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setResume({ ...resume, [name]: value });
    };

    const handleMajorChange = (option) => {
        setResume({ ...resume, major: option });
    };

    const handleLevelChange = (option) => {
        setResume({ ...resume, level: option });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        try {
            if (
                !resume.resumeName ||
                !resume.major ||
                !resume.major.majorId ||
                !resume.level ||
                !resume.level.levelId
            ) {
                setError("Vui lòng điền đầy đủ tên hồ sơ, chuyên ngành và vị trí mong muốn.");
                return;
            }
            let dataToSend = {
                ...resume,
                major: { majorId: resume.major.majorId },
                level: { levelId: resume.level.levelId },
            };
            if (resumeId) {
                await authApis().patch(`${endpoints['resume']}/${resumeId}`, dataToSend);
            } else {
                await authApis().post(endpoints['resume'], dataToSend);
            }
            navigate("/candidate/resumes");
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
        <Form onSubmit={handleSubmit} className="p-3 p-md-4 bg-white shadow-sm rounded" style={{ maxWidth: 700, margin: "0 auto" }}>
            <h4 className="mb-4">{resumeId ? "Cập nhật hồ sơ" : "Tạo mới hồ sơ"}</h4>
            {error && <Alert variant="danger">{error}</Alert>}
            <Form.Group className="mb-3">
                <Form.Label>Tên hồ sơ <span className="text-danger">*</span></Form.Label>
                <Form.Control
                    name="resumeName"
                    value={resume.resumeName}
                    onChange={handleChange}
                    placeholder="VD: CV..."
                    required
                />
            </Form.Group>
            <Row>
                <Col md={6}>
                    <Form.Group className="mb-3">
                        <Form.Label>Chuyên ngành <span className="text-danger">*</span></Form.Label>
                        <MajorSelect
                            value={resume.major}
                            onChange={handleMajorChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group className="mb-3">
                        <Form.Label>Vị trí mong muốn <span className="text-danger">*</span></Form.Label>
                        <LevelSelect
                            value={resume.level}
                            onChange={handleLevelChange}
                        />
                    </Form.Group>
                </Col>
            </Row>
            <Form.Group className="mb-3">
                <Form.Label>Mục tiêu nghề nghiệp</Form.Label>
                <Form.Control
                    as="textarea"
                    rows={2}
                    name="careerObjective"
                    value={resume.careerObjective}
                    onChange={handleChange}
                    placeholder="Nêu mục tiêu nghề nghiệp của bạn"
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Kinh nghiệm</Form.Label>
                <Form.Control
                    as="textarea"
                    rows={2}
                    name="experience"
                    value={resume.experience}
                    onChange={handleChange}
                    placeholder="Kinh nghiệm làm việc, thực tập, dự án..."
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Kỹ năng chuyên môn</Form.Label>
                <Form.Control
                    as="textarea"
                    rows={2}
                    name="skills"
                    value={resume.skills}
                    onChange={handleChange}
                    placeholder="Liệt kê kỹ năng chuyên môn của bạn"
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Học vấn</Form.Label>
                <Form.Control
                    as="textarea"
                    rows={2}
                    name="education"
                    value={resume.education}
                    onChange={handleChange}
                    placeholder="Trường, ngành học, thành tích học tập..."
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Kỹ năng mềm</Form.Label>
                <Form.Control
                    as="textarea"
                    rows={2}
                    name="softSkills"
                    value={resume.softSkills}
                    onChange={handleChange}
                    placeholder="VD: Teamwork, Communication, Leadership..."
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Giải thưởng</Form.Label>
                <Form.Control
                    as="textarea"
                    rows={2}
                    name="awards"
                    value={resume.awards}
                    onChange={handleChange}
                    placeholder="VD: Học bổng, giải thưởng, danh hiệu..."
                />
            </Form.Group>
            <div className="d-flex gap-2">
                <Button type="submit" variant="success">{resumeId ? "Cập nhật hồ sơ" : "Tạo mới hồ sơ"}</Button>
                <Button variant="secondary" onClick={() => navigate("/candidate/resumes")}>Quay lại</Button>
            </div>
        </Form>
    );
};

export default ResumeAddOrEdit;