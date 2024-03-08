import {createApp} from 'vue'
import App from './App.vue'
import store from "./store";
import router from "./router";
import "./assets/css/nucleo-icons.css";
import "./assets/css/nucleo-svg.css";
import SoftUIDashboard from "./soft-ui-dashboard";
import axios from "axios";
import idempotent from "@/js/idempotent";


const app = createApp(App)
axios.defaults.baseURL = "http://116.123.197.103:8080";
app.config.globalProperties.axios = axios;
app.config.globalProperties.$store = store;

// 요청 인터셉터 추가
axios.interceptors.request.use(
    function (config) {
        if (config.url !== "/jpos/user/login" && config.url !== "/jpos/user/signup") {
            const user = JSON.parse(localStorage.getItem("accessToken"));
            const token = user?.token;
            if (token) {
                config.headers["Authorization"] = 'Bearer ' + token;
            } else {
            }
        }
        // 요청이 전달되기 전에 작업 수행
        return config;
    },
    function (error) {
        // 요청 오류가 있는 경우 작업 수행
        return Promise.reject(error);
    },
);


axios.interceptors.response.use(
    function (response) {
        // 2xx 범위에 있는 상태 코드는 이 함수를 트리거
        // 응답 데이터가 있는 작업 수행
        return response;
    },
    function (error) {
        // 2xx 외의 범위에 있는 상태 코드는 이 함수를 트리거
        // 응답 오류가 있는 작업 수행
        if (error.response && error.response.status) {
            if (error.response.status >= 400) alert(error.response.data.message);
        }
        return Promise.reject(error);
    },
);


app.use(store)
    .use(router)
    .use(SoftUIDashboard)
    .use(idempotent)
    .mount('#app')
