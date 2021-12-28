import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios';
import Antd from 'ant-design-vue';
import * as Icons from '@ant-design/icons-vue';
import 'ant-design-vue/dist/antd.css';

//set up base url for axios requests
axios.defaults.baseURL = process.env.VUE_APP_SERVER;

//use axios interceptor to print log
axios.interceptors.request.use(function (config) {
    //print request
    console.log('request：', config);
    return config;
}, error => {
    return Promise.reject(error);
});
axios.interceptors.response.use(function (response) {
    //print response
    console.log('response：', response);
    return response;
}, error => {
    //printing error
    console.log('error：', error);
    return Promise.reject(error);
});


//create application component
const app = createApp(App);
app.use(store).use(router).use(Antd).mount('#app');

//using all ant design icons globally
const icons: any = Icons;
for (const i in icons) {
    app.component(i, icons[i]);
}

console.log('Environment: ', process.env.NODE_ENV);
console.log('Server: ', process.env.VUE_APP_SERVER);