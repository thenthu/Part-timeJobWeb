import { useContext, useState } from "react";
import {
  Navbar,
  Nav,
  Container,
  Button,
  Image,
  NavDropdown,
  Dropdown,
  Badge,
} from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { MyUserContext } from "../../configs/Contexts";
import { FaBell, FaComments } from "react-icons/fa";
import { authApis, endpoints } from "../../configs/Apis";

const Header = () => {
  const [user, dispatch] = useContext(MyUserContext);
  const navigate = useNavigate();

  const [notifications, setNotifications] = useState([]);
  const [loadingNoti, setLoadingNoti] = useState(false);
  const [notiLoaded, setNotiLoaded] = useState(false);
  const [markingRead, setMarkingRead] = useState(false);

  const unreadCount = notifications.filter((n) => !n.read).length;

  const handleLoadNotifications = async (isOpen) => {
    if (isOpen && user) {
      setLoadingNoti(true);
      try {
        const res = await authApis().get(endpoints["notifications"]);
        setNotifications(res.data || []);
        setNotiLoaded(true);
      } catch (err) {
        setNotifications([]);
      } finally {
        setLoadingNoti(false);
      }
    } else if (!isOpen) {
      setNotiLoaded(false);
    }
  };

  const handleMarkAllAsRead = async () => {
    setMarkingRead(true);
    try {
      const unread = notifications.filter((n) => !n.read);
      await Promise.all(
        unread.map((noti) =>
          authApis().patch(`/secure/notification/${noti.notifId}`)
        )
      );
      setLoadingNoti(true);
      const res = await authApis().get(endpoints["notifications"]);
      setNotifications(res.data || []);
    } catch (err) {
      console.error("Error marking notifications as read:", err);
    } finally {
      setMarkingRead(false);
      setLoadingNoti(false);
    }
  };

  const handleLogout = () => {
    dispatch({ type: "logout" });
    navigate("/");
  };

  return (
    <Navbar bg="white" expand="lg" className="shadow-sm mb-3">
      <Container>
        <Navbar.Brand as={Link} to="/" className="fw-bold text-success">
          Việc Làm 24h
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="main-navbar-nav" />
        <Navbar.Collapse id="main-navbar-nav">
          <Nav className="ms-auto align-items-center">

            {user && user.role?.roleName === "ROLE_CANDIDATE" && (
              <Button
                onClick={() => navigate(`/candidate/resume/`)}
                variant="success"
                className="me-4 fw-semibold"
              >
                Tạo hồ sơ
              </Button>
            )}
            {user && user.role?.roleName === "ROLE_EMPLOYER" && (
              <Button
                as={Link}
                to="/employer/job"
                variant="success"
                className="me-4 fw-semibold"
              >
                Đăng tuyển
              </Button>
            )}

            {user && (
              <>
                <Button
                  variant="link"
                  className="position-relative p-0 border-0 me-2"
                  style={{ boxShadow: "none" }}
                  onClick={() => navigate("/chat")}
                  aria-label="Chat"
                >
                  <FaComments size={22} className="text-secondary" />
                </Button>

                <Dropdown
                  align="end"
                  className="me-2"
                  onToggle={handleLoadNotifications}
                >
                  <Dropdown.Toggle
                    variant="link"
                    id="dropdown-notifications"
                    className="position-relative p-0 border-0"
                    style={{ boxShadow: "none" }}
                    aria-label="Notifications"
                  >
                    <FaBell size={22} className="text-secondary" />
                    {unreadCount > 0 && (
                      <Badge
                        bg="danger"
                        pill
                        className="position-absolute top-0 start-100 translate-middle"
                        style={{ fontSize: "0.7em" }}
                      >
                        {unreadCount}
                      </Badge>
                    )}
                  </Dropdown.Toggle>
                  <Dropdown.Menu
                    style={{
                      minWidth: 330,
                      maxWidth: 400,
                      maxHeight: 360,
                      overflowY: "auto",
                    }}
                  >
                    <Dropdown.Header>Thông báo</Dropdown.Header>
                    {loadingNoti || markingRead ? (
                      <Dropdown.ItemText className="text-muted text-center">
                        Đang tải...
                      </Dropdown.ItemText>
                    ) : notifications.length === 0 ? (
                      <Dropdown.ItemText className="text-muted text-center">
                        Không có thông báo
                      </Dropdown.ItemText>
                    ) : (
                      notifications.map((n) => (
                        <Dropdown.Item
                          key={n.id}
                          className={n.read ? "" : "fw-bold"}
                          style={{ whiteSpace: "normal" }}
                        >
                          {n.content}
                        </Dropdown.Item>
                      ))
                    )}
                    <Dropdown.Divider />
                    <Dropdown.Item
                      onClick={handleMarkAllAsRead}
                      className="text-center text-success"
                      disabled={markingRead || unreadCount === 0}
                      style={{
                        cursor:
                          markingRead || unreadCount === 0
                            ? "not-allowed"
                            : "pointer",
                      }}
                    >
                      Đánh dấu là đã đọc
                    </Dropdown.Item>
                  </Dropdown.Menu>
                </Dropdown>
              </>
            )}

            {user ? (
              <NavDropdown
                title={
                  <span>
                    <Image
                      src={user.avatar}
                      roundedCircle
                      width={32}
                      height={32}
                      className="me-2"
                      alt="avatar"
                    />
                    {user.username}
                  </span>
                }
                align="end"
                id="user-nav-dropdown"
              >
                <NavDropdown.Item as={Link} to="/profile">
                  Trang cá nhân
                </NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item
                  onClick={handleLogout}
                  className="text-danger"
                >
                  Đăng xuất
                </NavDropdown.Item>
              </NavDropdown>
            ) : (
              <>
                <Nav.Link
                  as={Link}
                  to="/register"
                  className="text-success fw-semibold"
                >
                  Đăng ký
                </Nav.Link>
                <Nav.Link
                  as={Link}
                  to="/login"
                  className="text-success fw-semibold"
                >
                  Đăng nhập
                </Nav.Link>
              </>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;