import { useState, useEffect } from "react";
import { Alert, Button, Form } from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const Register = () => {
    const [user, setUser] = useState({
        email: "",
        username: "",
        password: "",
        confirmPassword: "",
        role: null
    });
    const [msg, setMsg] = useState();
    const [loading, setLoading] = useState(false);
    const [roles, setRoles] = useState([]);
    const [isCompany, setIsCompany] = useState(false);
    const nav = useNavigate();

    useEffect(() => {
        const loadRoles = async () => {
            try {
                let res = await Apis.get(endpoints['roles']);
                setRoles(res.data);

                const candidateRole = res.data.find(r => r.roleName === "ROLE_CANDIDATE");
                setUser(u => ({ ...u, role: candidateRole }));
            } catch (ex) {
                setMsg("Không tải được danh sách vai trò!");
            }
        };
        loadRoles();
    }, []);

    const handleCompanyTick = (e) => {
        const checked = e.target.checked;
        setIsCompany(checked);

        if (roles.length > 0) {
            const roleId = checked ? 2 : 3;
            const selectedRole = roles.find(r => r.roleId === roleId);
            setUser(u => ({ ...u, role: selectedRole }));
        }
    };

    const validate = () => {
        if (!user.email || !user.username || !user.password || !user.role) {
            setMsg("Vui lòng nhập đầy đủ thông tin!");
            return false;
        }
        if (user.password !== user.confirmPassword) {
            setMsg("Mật khẩu xác nhận không khớp!");
            return false;
        }
        return true;
    };

    const register = async (event) => {
        event.preventDefault();

        if (!validate())
            return;

        try {
            setLoading(true);

            let payload = {
                email: user.email,
                username: user.username,
                password: user.password,
                role: {roleId: user.role.roleId}
            };

            let res = await Apis.post(endpoints['register'], payload);

            if (res.status === 201)
                nav("/login");
            else
                setMsg("Đăng ký thất bại!");
        } catch (ex) {
            setMsg("Có lỗi xảy ra khi đăng ký!");
            console.error(ex);
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <h1 className="text-center text-success mt-2">ĐĂNG KÝ</h1>

            {msg && <Alert variant="danger">{msg}</Alert>}

            <Form onSubmit={register}>
                <Form.Group className="mb-3" controlId="email">
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                        required
                        type="email"
                        value={user.email}
                        onChange={e => setUser({ ...user, email: e.target.value })}
                        placeholder="Nhập email"
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="username">
                    <Form.Label>Tên đăng nhập</Form.Label>
                    <Form.Control
                        required
                        type="text"
                        value={user.username}
                        onChange={e => setUser({ ...user, username: e.target.value })}
                        placeholder="Nhập tên đăng nhập"
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="password">
                    <Form.Label>Mật khẩu</Form.Label>
                    <Form.Control
                        required
                        type="password"
                        value={user.password}
                        onChange={e => setUser({ ...user, password: e.target.value })}
                        placeholder="Nhập mật khẩu"
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="confirmPassword">
                    <Form.Label>Xác nhận mật khẩu</Form.Label>
                    <Form.Control
                        required
                        type="password"
                        value={user.confirmPassword}
                        onChange={e => setUser({ ...user, confirmPassword: e.target.value })}
                        placeholder="Nhập lại mật khẩu"
                    />
                </Form.Group>

                <Form.Group className="mb-3" controlId="isCompany">
                    <Form.Check
                        type="checkbox"
                        label="Đăng ký tài khoản doanh nghiệp"
                        checked={isCompany}
                        onChange={handleCompanyTick}
                    />
                </Form.Group>

                {loading ? <MySpinner /> : (
                    <Form.Group className="mb-3" controlId="submit">
                        <Button type="submit" variant="success">Đăng ký</Button>
                    </Form.Group>
                )}
            </Form>
        </>
    );
}

export default Register;