import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import VWave from 'v-wave'
import BackgroundApp from './BackgroundApp.vue'

const app = createApp(BackgroundApp)


app.use(ElementPlus)
app.use(VWave)
app.mount('#app')

