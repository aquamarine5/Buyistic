import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import VWave from 'v-wave'
import BuyPage from './BuyPage.vue'

const app = createApp(BuyPage)


app.use(ElementPlus)
app.use(VWave)
app.mount('#app')

