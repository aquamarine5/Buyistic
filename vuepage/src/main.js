import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import VWave from 'v-wave'
import BuyPage from './buypage/BuyPage.vue'
import BackgroundPage from './background/BackgroundPage.vue'
import { createMemoryHistory, createRouter } from 'vue-router'

const app = createApp(App)

const routes=[
    {path:'/', component: App},
    {path:'/buy',component:BuyPage},
    {path:'/background',component:BackgroundPage}
]
const router=createRouter({
    history:createMemoryHistory(),
    routes
}
)
app.use(router)
app.use(ElementPlus)
app.use(VWave)
app.mount('#app')

