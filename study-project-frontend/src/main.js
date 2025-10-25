import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import 'element-plus/dist/index.css'
import axios from "axios";
import pinia from "@/stores/index.js";

const app = createApp(App)

axios.defaults.baseURL = 'http://localhost:8080'
axios.defaults.withCredentials = true   // 🔥 关键代码：允许跨域携带 Cookie

app.use(router)
app.use(pinia)
app.mount('#app')
