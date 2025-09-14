import axios from "axios";
import cookie from 'react-cookies'

const BASE_URL = 'http://localhost:8080/PartTimeJob/api/';

export const endpoints = {
    'jobs': '/jobs',
    'locations': '/locations',
    'majors': '/majors',
    'levels': '/job-levels',
    'roles': '/roles',
    'register': '/register',
    'login': '/login',
    'profile': '/secure/profile',
    'notifications': '/secure/notifications',
    'resumes': '/secure/candidate/resumes',
    'resume': '/secure/candidate/resume',
    'applications': '/secure/candidate/applications',
    'followed': '/secure/candidate/follow-employer',
    'myjob': '/secure/employer/jobs',
    'job': '/secure/employer/job',
    'followers': '/secure/employer/followers',
    'candidate-register': '/secure/register/candidate',
    'employer-register': '/secure/register/employer',
    'candidate-profile-edit': '/secure/profile/candidate',
    'employer-profile-edit': '/secure/profile/employer',
    'change-password': '/secure/profile/change-password',
    'employers': '/secure/employers',
    'candidates': '/secure/candidates',
}

export const authApis = () => axios.create({
    baseURL: BASE_URL,
    headers: {
        'Authorization': `Bearer ${cookie.load('token')}`
    }
})

export default axios.create({
    baseURL: BASE_URL
})