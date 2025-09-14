import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import Home from "./components/Home";
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container } from "react-bootstrap";
import { MyUserContext } from "./configs/Contexts";
import { useReducer } from "react";
import Register from "./components/Register";
import Login from "./components/Login";
import MyUserReducer from "./reducers/MyUserReducer";
import Job from "./components/Job";
import Profile from "./components/Profile";
import Employer from "./components/employer/Employer";
import Resume from "./components/candidate/Resume";
import ResumeAddOrEdit from "./components/candidate/ResumeAddOrEdit";
import Application from "./components/candidate/Application";
import Followed from "./components/candidate/Followed";
import MyJob from "./components/employer/MyJob";
import JobAddOrEdit from "./components/employer/JobAddOrEdit";
import Follower from "./components/employer/Follower";
import JobApplication from "./components/employer/JobApplication";
import EmployerRegister from "./components/employer/EmployerRegister";
import CandidateRegister from "./components/candidate/CandidateRegister";
import Candidate from "./components/candidate/Candidate";
import CandidateEdit from "./components/candidate/CandidateEdit";
import EmployerEdit from "./components/employer/EmployerEdit";
import ChangePassword from "./components/ChangePassword";
import ChatPage from "./components/chat/ChatPage";

const App = () => {
  let [user, dispatch] = useReducer(MyUserReducer, null);

  return (
    <MyUserContext.Provider value={[user, dispatch]}>
      <BrowserRouter>
        <div className="d-flex flex-column min-vh-100">
          <Header />

          <main className="flex-grow-1">
            <Container>
              <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/job/:jobId" element={<Job />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/employer/:employerId" element={<Employer />} />
                <Route path="/candidate/resumes" element={<Resume />} />
                <Route path="/candidate/resume" element={<ResumeAddOrEdit />} />
                <Route path="/candidate/resume/:resumeId" element={<ResumeAddOrEdit />} />
                <Route path="/candidate/applications" element={<Application />} />
                <Route path="/candidate/followed" element={<Followed />} />
                <Route path="/candidate/:candidateId" element={<Candidate />} />
                <Route path="/candidate/edit" element={<CandidateEdit />} />
                <Route path="/employer/jobs" element={<MyJob />} />
                <Route path="/employer/job" element={<JobAddOrEdit />} />
                <Route path="/employer/job/:jobId" element={<JobAddOrEdit />} />
                <Route path="/employer/edit" element={<EmployerEdit />} />
                <Route path="/employer/followers" element={<Follower />} />
                <Route path="/job/:jobId/applications" element={<JobApplication />} />
                <Route path="/register" element={<Register />} />
                <Route path="/register-employer" element={<EmployerRegister />} />
                <Route path="/register-candidate" element={<CandidateRegister />} />
                <Route path="/login" element={<Login />} />
                <Route path="/change-password" element={<ChangePassword />} />
                <Route path="/chat" element={<ChatPage />} />
              </Routes>
            </Container>
          </main>

          <Footer />
        </div>
      </BrowserRouter>
    </MyUserContext.Provider>
  );
}

export default App;