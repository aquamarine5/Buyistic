import axios from 'axios';
import { ElNotification } from 'element-plus';

const service = axios.create({
    baseURL: process.env.APIHOST, // You can set your base URL here
    timeout: 5000 // Request timeout
});

service.interceptors.response.use(
    response => response,
    error => {
        ElNotification({
            title: '网络错误',
            message: error.message,
            type: 'error',
        });
        return Promise.reject(error);
    }
);
export default class wnetwork {
    static get(url, params) {
        return service.get(this.APIHOST + url, { params });
    }

    static post(url, data) {
        return service.post(this.APIHOST + url, data);
    }

    static APIHOST = import.meta.env.VITE_APIHOST
    static IMGHOST = import.meta.env.VITE_IMGHOST
}