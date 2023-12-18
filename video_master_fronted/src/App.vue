<script setup>
import NavBar from "./components/NavBar.vue";
import Footer from "./components/Footer.vue";
import {useRoute} from "vue-router";
import locale from "ant-design-vue/es/vc-picker/locale/zh_CN.js";
import {onBeforeMount, onMounted, provide, reactive, ref} from "vue";
import {getMessageSender, postMessageSender} from "./utils/MessageSender.js";
import MessageObj from "./utils/messageObj.js";
import {message} from "ant-design-vue";
import store from "./store/store.js";


let is_user_login = reactive({

})
let putUserHandler = (user)=>{
  is_user_login = user
}
onMounted(()=>{
  // localStorage.removeItem("token")
  let token = localStorage.getItem("token")
  if(token){
    // 如果token存在，则向后端发送请求，获取数据
    let promise = getMessageSender("/video-master/users/login/token",new MessageObj("token",localStorage.getItem("token")).getObject())
    promise.then(
        (res)=>{
          // 对于环境比较复杂的需要用于全局的信息，使用vuex，依赖注入无法处理这种状况
          store.dispatch("updateUserState",{
            action: "input",
            data: res.data
          })
        }
    ).catch(
        (err)=>{
          message.error(`发生错误： ${err}`)
        }
    )
  }
})


</script>

<template>
  <a-config-provider :locale="locale">
    <nav-bar class="navbar_style" @putUser="putUserHandler"></nav-bar>
    <router-view :key="useRoute().fullPath">
    </router-view>
    <Footer></Footer>
  </a-config-provider>
</template>

<style scoped>
.navbar_style {
  background: black;
  padding: 1em;
  border-radius: 5px;
}
</style>
