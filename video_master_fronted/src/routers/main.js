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
    },
    {
        path: '/movieDetail',
        name: 'movieDetail',
        component: ()=> import("../pages/MovieDetail.vue")
    },
    {
        path: '/moviePlayer',
        name: 'moviePlayer',
        component: ()=>import("../pages/MoviePlayer.vue")
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

export default router