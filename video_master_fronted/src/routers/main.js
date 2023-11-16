import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";

const routes = [
    // 导入已经写好的路由 或者 简单的可以直接在这里写
    {
        path :'/',
        redirect: '/index',
        component: ()=> import("../pages/IndexPage.vue")
    },
    {
        path: '/index',
        name: 'index',
        component: ()=> import("../pages/IndexPage.vue")
    },
    {
        path: '/movieList',
        name: 'movieList',
        component: ()=> import("../pages/MovieList.vue")
    },
    {
        path: '/searchedMovie',
        name: 'searchedMovie',
        component: ()=> import("../pages/SearchedMovie.vue")
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

export default router