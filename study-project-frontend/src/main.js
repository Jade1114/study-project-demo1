import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import 'element-plus/dist/index.css'

const app = createApp(App)
app.use(router) // ✅ 把 router 注册进 Vue 实例
app.mount('#app')
