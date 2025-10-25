import { createRouter, createWebHistory} from "vue-router";
import { useStore } from "@/stores/index.js";
import { get } from "@/net";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes:[
        {
            path: '/',
            name: 'welcome',
            component: () => import('@/views/WelcomeView.vue'),
            children: [
                {
                    path: '',
                    name: 'welcome-login',
                    component: () => import('@/components/welcome/LoginPage.vue')
                },
                {
                    path: 'register',
                    name: 'welcome-register',
                    component: () => import('@/components/welcome/RegisterPage.vue')
                },
                {
                    path: 'forget',
                    name: 'welcome-forget',
                    component: () => import('@/components/welcome/ForgetPage.vue')
                }
            ]
        },
        {
            path: '/index',
            name: 'index',
            component: () => import('@/views/IndexView.vue')
        }
    ]
})

router.beforeEach(async (to, from, next) => {
    const store = useStore();
    const publicPages = ['welcome-login','welcome-register','welcome-forget'];
    const authRequired = !publicPages.includes(to.name);

    if (!authRequired) {
        // 已登录访问登录/注册页 → 重定向到首页
        if (store.auth.user) {
            return next('/index');
        }
        return next();
    }

    // 受保护页面
    if (store.auth.user) {
        return next(); // 已登录直接放行
    }

    // 尝试恢复登录
    try {
        await new Promise((resolve, reject) => {
            get('/api/user/me', (user) => {
                store.auth.user = user;
                resolve();
            }, () => {
                reject();
            });
        });
        next();
    } catch {
        next('/'); // 没登录 → 跳到欢迎页
    }
});

export default router;