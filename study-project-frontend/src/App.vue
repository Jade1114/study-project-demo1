<script setup>
import {get} from "@/net";
import {useStore} from "@/stores";
import router from "@/router/index.js";

const store = useStore()

if (store.auth.user == null) {
  get('/api/user/me', (user) => {
        store.setUser(user)
        router.push('/index')
      }, () => {
        store.auth.user = null
      }
  )
}
</script>

<template>
  <router-view/>
</template>

<style scoped>

</style>
