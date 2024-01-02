/*
=========================================================
* Vite Soft UI Dashboard - v1.0.0
=========================================================
* Product Page: https://creative-tim.com/product/vite-soft-ui-dashboard
* Copyright 2022 Creative Tim (https://www.creative-tim.com)
Coded by www.creative-tim.com
* Licensed under MIT (https://github.com/creativetimofficial/vite-soft-ui-dashboard/blob/556f77210e261adc3ec12197dab1471a1295afd8/LICENSE.md)
=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*/ 

import { createApp } from 'vue'
import App from './App.vue'
import store from "./store";
import router from "./router";
import "./assets/css/nucleo-icons.css";
import "./assets/css/nucleo-svg.css";
import SoftUIDashboard from "./soft-ui-dashboard";
import axios from "axios";


const app = createApp(App)
axios.defaults.baseURL = "http://116.123.197.103:8080";
app.config.globalProperties.axios = axios;
app.config.globalProperties.$store = store;

// 요청 인터셉터 추가
axios.interceptors.request.use(
    function (config) {
        debugger
        if(config.url !== "/jpos/user/login" && config.url !== "/jpos/user/signup"){
            const user = JSON.parse(localStorage.getItem("accessToken"));
            const token = user?.token;
            if(token){
                config.headers["Authorization"] = 'Bearer ' + token;
            }else{
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
            switch (error.response.status) {
                case 401:
                    const user = JSON.parse(localStorage.getItem("accessToken"));
                    if(user){
                        router.push('/main').catch(() => {});
                    }else{
                        alert("잘못된 접근입니다.")
                        router.push('/sign-in').catch(() => {});
                    }
                    break;
                default:
                    return Promise.reject(error);
            }
        }
        return Promise.reject(error);
    },
);



app.use(store)
    .use(router)
    .use(SoftUIDashboard)
    .mount('#app')
