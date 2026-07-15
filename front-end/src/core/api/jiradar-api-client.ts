import axios from 'axios';
import { mapKeysToCamel } from '../utils/case-mapper';
import { getCookie } from '../utils/cookies';

const BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

export const apiClient = axios.create({
    baseURL: BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

apiClient.interceptors.request.use(
    (config) => {
        const token = getCookie('jiradar_token');
        if (token) {
            config.headers.Authorization = `Basic ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

apiClient.interceptors.response.use(
    (response) => {
        if (response.data) {
            response.data = mapKeysToCamel(response.data);
        }
        return response;
    },
    (error) => {
        console.error('API Error Intercepted:', error.response?.data || error.message);
        return Promise.reject(error);
    }
);