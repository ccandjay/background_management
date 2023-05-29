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
import VueVideoPlayer from 'vue-video-player'
import 'video.js/dist/video-js.css'
import 'vue-video-player/src/custom-theme.css'
// window.videojs = VueVideoPlayer.videojs
// require('video.js/dist/lang/zh-CN.js');
// use
Vue.use(VueVideoPlayer)
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
