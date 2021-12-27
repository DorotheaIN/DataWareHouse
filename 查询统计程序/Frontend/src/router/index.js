import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    hidden: true,
    redirect: '/home',
    children: [{
      path: 'home',
      name: 'Home',
      component: () => import('@/views/home/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },

  {
    path: '/single-condition',
    component: Layout,
    redirect: '/single-condition/time',
    name: 'SingleCondition',
    meta: { title: '单条件查询', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'time',
        name: 'Time',
        component: () => import('@/views/single-condition/time/index'),
        meta: { title: '按照时间进行查询及统计', icon: 'table' }
      },
      {
        path: 'name',
        name: 'Name',
        component: () => import('@/views/single-condition/name/index'),
        meta: { title: '按照电影名称进行查询及统计', icon: 'table' }
      },
      {
        path: 'director',
        name: 'Director',
        component: () => import('@/views/single-condition/director/index'),
        meta: { title: '按照导演进行查询及统计', icon: 'table' }
      },
      {
        path: 'writer',
        name: 'Writer',
        component: () => import('@/views/single-condition/writer/index'),
        meta: { title: '按照编剧进行查询及统计', icon: 'table' }
      },
      {
        path: 'actor',
        name: 'Actor',
        component: () => import('@/views/single-condition/actor/index'),
        meta: { title: '按照演员进行查询及统计', icon: 'table' }
      },
      {
        path: 'relationship',
        name: 'Relationship',
        component: () => import('@/views/single-condition/relationship/index'),
        meta: { title: '按照演员和导演的关系进行查询及统计', icon: 'table' }
      },
      {
        path: 'genre',
        name: 'Genre',
        component: () => import('@/views/single-condition/genre/index'),
        meta: { title: '按照电影类别进行查询及统计', icon: 'table' }
      },
      {
        path: 'rating',
        name: 'Rating',
        component: () => import('@/views/single-condition/rating/index'),
        meta: { title: '按照用户评价进行查询及统计', icon: 'table' }
      },
      {
        path: 'review',
        name: 'Review',
        component: () => import('@/views/single-condition/review/index'),
        meta: { title: '按照电影名称进行评论的查询及统计', icon: 'table' }
      },
      {
        path: 'version',
        name: 'Version',
        component: () => import('@/views/single-condition/version/index'),
        meta: { title: '查询电影版本', icon: 'table' }
      },
    ]
  },

  {
    path: '/muti-condition',
    component: Layout,
    children: [{
      path: 'muti-condition',
      name: 'MutiCondition',
      component: () => import('@/views/muti-condition/index'),
      meta: { title: '多条件查询', icon: 'dashboard' }
    }]
  },
  
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
