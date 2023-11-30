import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import {resolve} from "url";

// https://vitejs.dev/config/
export default defineConfig({
    base: '/',
    plugins: [vue(
    )],
    // 配置代理服务器
    server: {
        proxy: {
            // 静态资源访问
            '/static': {
                target: 'http://192.168.32.137/static',
                changeOrigin: true,
                rewrite: path => path.replace(/^\/static/, '')
            },

            // 影视资源等访问
            '/resources': {
                target: 'http://192.168.32.137/resources',
                changeOrigin: true,
                rewrite: path => path.replace(/^\/resources/, '')
            },

            //后台程序
            '/video-master': {
                target: "http://127.0.0.1:8089/video-master",
                changeOrigin: true,
                rewrite: path => path.replace(/^\/video-master/, '')
            },

            // 用户访问
            '/users':{
                target: "http://192.168.32.137/users",
                changeOrigin: true,
                rewrite: path => path.replace(/^\/users/, '')
            }
        }
    },
    build:{
        rollupOptions: {
            input: {
                main: 'index.html',
                administrator: 'src/views/administrator/index.html',
                individual: 'src/views/individual_center/index.html',
                account_security: 'src/views/account_security_detail/index.html',
            }
        }
    }

})
