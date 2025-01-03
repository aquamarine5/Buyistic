import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import VWave from 'v-wave'
import { createWebHistory, createRouter } from 'vue-router'
import MainPage from './mainpage/MainPage.vue'
import App from './App.vue'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)

const routes = [
    { path: '/', component: MainPage, name: "app" },
    { path: "/pay/:id", component: () => import("@/payment/PayPage.vue"), name: "pay" },
    { path: "/orders", component: () => import("@/orders/OrdersPage.vue"), name: "orders" },
    { path: "/login", component: () => import("@/account/LoginPage.vue"), name: "login" },
    { path: '/buy/:id', component: () => import('@/buypage/BuyPage.vue'), name: "buy" },
    
    { path: "/background/accounts", component: () => import("@/background/AccountsBgPage.vue"), name: "bg_accounts" },
    { path: "/background/items", component: () => import("@/background/ItemsBgPage.vue"), name: "bg_items" },
    { path: "/background/orders", component: () => import("@/background/OrdersBgPage.vue"), name: "bg_orders" },
    { path: '/background', component: () => import('@/background/BackgroundPage.vue'), name: "background" },
]
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: routes
})
app.use(router)
app.use(ElementPlus)
app.use(VWave)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.mount('#app')

