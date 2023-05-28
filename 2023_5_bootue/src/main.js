import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI, { Message } from 'element-ui'
import '../src/assets/gloabel.css'
import 'element-ui/lib/theme-chalk/index.css'
import request from './utils/request'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import { baseApi } from './config'
// use
Vue.use(mavonEditor)

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.prototype.request = request
Vue.prototype.$message = Message
Vue.prototype.$api = baseApi

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
