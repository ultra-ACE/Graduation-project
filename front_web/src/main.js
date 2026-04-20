
import { createApp } from 'vue';
import App from './App.vue';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import 'font-awesome/css/font-awesome.min.css';
import router from '/src/router/index.js';
import axios from "axios";
import VueAxios from "vue-axios";




const app = createApp(App)
app.use(router)
app.use(ElementPlus)
app.use(VueAxios, axios)
app.mount('#app')
axios.defaults.baseURL = "http://localhost:8080/api"
app.config.globalProperties.$axios = axios


