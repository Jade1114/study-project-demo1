<script setup>
import { useStore } from "@/stores/index.js";
import { ElMessage } from "element-plus";
import router from "@/router";

const store = useStore();

const logout = () => {
  import('@/net').then(({ get }) => {
    get('/api/auth/logout', (message) => {
      ElMessage.success(message);
      store.clearUser()
      router.push('/');
    });
  });
};
</script>

<template>
  <div v-if="store.auth.user">
    欢迎 {{ store.auth.user.message.username }} 进入到学习平台
  </div>
  <div v-else>
    正在加载用户信息...
  </div>

  <div>
    <el-button @click="logout" type="danger" plain>退出登录</el-button>
  </div>
</template>

<style scoped>
</style>
