import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'

Vue.use(Router);

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'home',
            component: Home
        },
        {
            path: '/about',
            name: 'about',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
        },
        {
            path: '/tasks/:theme_name?/:subtheme_name?/:difficulty_name?/:task_id?',
            name: 'tasks',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import(/* webpackChunkName: "about" */ './views/Tasks.vue'),
            beforeEnter (to, from, next) {
                if (localStorage.getItem("user"))
                    next()
                else next(false)
            }
        },
        {
            path: '/stats',
            name: 'stats',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import(/* webpackChunkName: "about" */ './views/Stats.vue'),
            beforeEnter (to, from, next) {
                if (localStorage.getItem("user"))
                    next()
                else next(false)
            }
        },
        {
            path: '/newtask',
            name: 'newtask',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import(/* webpackChunkName: "about" */ './views/NewTask.vue'),
            beforeEnter (to, from, next) {
                if (localStorage.getItem("user") && JSON.parse(localStorage.getItem("user")).role !== "student")
                    next()
                else next(false)
            }

        },
        {
            path: '/chat',
            name: 'chat',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import(/* webpackChunkName: "about" */ './views/Chat.vue'),
            beforeEnter (to, from, next) {
                if (localStorage.getItem("user"))
                    next()
                else next(false)
            }
        },
    ]
})
