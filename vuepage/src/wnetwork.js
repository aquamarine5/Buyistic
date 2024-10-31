
import { ElNotification } from 'element-plus';

const service = axios.create({
    baseURL: import.meta.env.VITE_APIHOST, // You can set your base URL here
    timeout: 5000 // Request timeout
});

service.interceptors.response.use(
    response => {
        // 检查响应状态码，如果是错误状态码则触发错误提醒
        if (response.data.status === 'err') {
            ElNotification({
                title: '错误',
                message: response.data.message || '发生未知错误',
                type: 'error',
            });
            return Promise.reject(new Error(response.data.message || '发生未知错误'));
        }
        if (response.data.status === 'success.Signal') {
            ElNotification({
                title: "成功",
                type: "success"
            })
        }
        return response;
    },
    error => {
        console.log(error); // for debug
        ElNotification({
            title: error.message,
            message: error.response.data.message || '发生未知错误',
            type: 'error',
        });
        return Promise.reject(error);
    }
);

export default class wnetwork {
    static get(url, params) {
        return service.get(url, { params });
    }

    static post(url, data) {
        return service.post(url, data);
    }

    static APIHOST = import.meta.env.VITE_APIHOST;
    static IMGHOST = import.meta.env.VITE_IMGHOST;
}