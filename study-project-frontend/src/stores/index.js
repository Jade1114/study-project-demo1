// src/stores/index.js
import { createPinia } from 'pinia'
import { defineStore } from "pinia";

export const useStore = defineStore('store', {
    state: () => ({
        auth: { user: null }
    }),

    actions: {
        setUser(user) {
            this.auth.user = user
        },
        clearUser() {
            this.auth.user = null
        },
    }
})

const pinia = createPinia()
export default pinia
