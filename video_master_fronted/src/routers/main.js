import {createRouter, createWebHistory} from "vue-router";

const routes = [
    // 导入已经写好的路由
]

const router = createRouter({
    history: createWebHistory(),
    routes, // `routes: routes` 的缩写
})

export default router