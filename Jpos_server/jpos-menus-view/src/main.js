import './assets/main.css'


import router from './router'
import axios from "axios";

import { createApp } from 'vue'
import { createPinia } from 'pinia';

import App from './App.vue'

axios.defaults.baseURL = "http://61.101.89.253:8080";

const pinia = createPinia();
const app = createApp(App)

app.config.globalProperties.axios = axios;

app.use(pinia);
app.use(router);

app.mount('#app')
