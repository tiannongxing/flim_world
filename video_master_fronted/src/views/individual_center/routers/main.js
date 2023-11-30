import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";
let auth = false
const routes = [
    {
        path: '/',
        redirect: '/home'
    },
    {
        path: '/home',
        name: 'home',
        component: ()=>import("../pages/HomePage.vue")
    },
    {
        path:'/message',
        name:'message',
        component: ()=>import("../pages/MyMessage.vue")
    },
    {
        path:'/security',
        name:'security',
        component: ()=>import("../pages/AccountSecurity.vue")
    },
    {
        path:'/records',
        name:'records',
        component: ()=>import("../pages/MyRecords.vue")
    },
    {
        path:'/revoked',
        name:'revoked',
        component: ()=>import("../pages/AccountRevoked.vue")
    },
    {
        path:'/security_check',
        name:'security_check',
        component: ()=>import("../pages/SecurityCheck.vue"),

    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

export default router