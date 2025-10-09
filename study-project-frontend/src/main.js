import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import 'element-plus/dist/index.css'
import axios from "axios";

const app = createApp(App)

axios.defaults.baseURL = 'http://localhost:8080'

app.use(router) // ✅ 把 router 注册进 Vue 实例
app.mount('#app')
