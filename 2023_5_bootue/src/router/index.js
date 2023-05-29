import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

Vue.use(VueRouter)

// 提供一个重置路由的方法
export const resetRouter = () => {
  router.matcher = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
}

// 解决编程式路由往同一地址跳转时会报错的情况
const originalPush = VueRouter.prototype.push
const originalReplace = VueRouter.prototype.replace
// push
VueRouter.prototype.push = function push (location, onResolve, onReject) {
  if (onResolve || onReject) { return originalPush.call(this, location, onResolve, onReject) }
  return originalPush.call(this, location).catch(err => err)
}
// replace
VueRouter.prototype.replace = function push (location, onResolve, onReject) {
  if (onResolve || onReject) { return originalReplace.call(this, location, onResolve, onReject) }
  return originalReplace.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '*',
    name: '404',
    component: () => import(/* webpackChunkName: "about" */ '../views/404.vue')
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/front/Front'),
    children: [
      {
        path: 'home',
        name: 'FrontHome',
        component: () => import('../views/front/Home.vue')
      },
      {
        path: 'item1',
        name: 'Item1',
        component: () => import('../views/front/Item1.vue')
      },
      {
        path: 'person',
        name: 'FrontPerson',
        component: () => import('../views/front/Person')
      },
      {
        path: 'password',
        name: 'FrontPassword',
        component: () => import('../views/front/Password')
      },
      {
        path: 'video',
        name: 'Video',
        component: () => import('../views/front/Video')
      },
      {
        path: 'videoDetail',
        name: 'VideoDetail',
        component: () => import('../views/front/VideoDetail')
      },
      {
        path: 'article',
        name: 'FrontArticle',
        component: () => import('../views/front/Article')
      },
      {
        path: 'articleDetail',
        name: 'ArticleDetail',
        component: () => import('../views/front/ArticleDetail')
      }
    ]
  }
]
const router = new VueRouter({
  mode: 'history',
  routes
})
// 刷新页面会重置路由
export const setRouter = () => {
  const menus = localStorage.getItem('menus')
  let manageRouter = {}
  const menusRouter = JSON.parse(menus)
  if (menusRouter == null) {
    return
  }
  if (menusRouter.length > 0) {
    // 动态拼接路由
    manageRouter = {
      path: '/',
      component: () => import('../views/Manage.vue'),
      redirect: '/home',
      meta: {
        title: '框架'
      },
      children: []
    }
    menusRouter.forEach(item => {
      // 当菜单的 path 不为空时才设置路由
      if (item.path) {
        const itemRouter = { path: item.path.replace('/', ''), name: item.name, component: () => import('../views/' + item.pagePath + '.vue') }
        manageRouter.children.push(itemRouter)
      } else if (item.children.length > 0) {
        item.children.forEach(e => {
          const itemRouter = { path: e.path.replace('/', ''), name: e.name, component: () => import('../views/' + e.pagePath + '.vue') }
          manageRouter.children.push(itemRouter)
        })
      }
    })
  }
  // 获取当前路由的 name数组
  const nameArr = router.getRoutes().map(e => e.name)
  if (!nameArr.includes('Manage')) {
    return router.addRoute(manageRouter)
  }
}

// 刷新页面重置,就让路由重载一下
setRouter()

router.beforeEach((to, from, next) => {
  localStorage.setItem('currentPathName', to.name) // 设置当前的路由名称
  store.commit('setPath')

  // 未找到路由的情况
  if (!to.matched.length) {
    const storeMenus = localStorage.getItem('menus')
    if (storeMenus) {
      next('/404')
    } else {
      // 跳回登录页面
      next('/login')
    }
  }
  // 其他的情况都放行
  next()
})
export default router
