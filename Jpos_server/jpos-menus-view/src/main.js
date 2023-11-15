import './assets/main.css'


import router from './router'
import * as components from 'vue-onsenui/esm/components';

import { createApp } from 'vue'
import { createPinia } from 'pinia';

import App from './App.vue'

const pinia = createPinia();
const app = createApp(App)


app.use(pinia);
app.use(router);

app.mount('#app')
