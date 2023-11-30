<script setup>
import axios from "axios";
import {reactive} from "vue";
import {CALL_IP} from "/src/const_param.js";
import router from "../routers/main.js";
import emitter from "../../../utils/EventBus.js";

let userDataSet = reactive({})
let getUserDataSet = (token) => {
  //todo 使用token从后端的redis数据库中获取用户信息
  axios.post('/video-master/getUserDataByToken', {
    // 传输localStorage中存放的token
  }).then((res) => {

  }).catch((res) => {

  })
}

let jumpToSecurityDetailPage = ()=>{
  emitter.emit("changeSelected", {
    changeTo: 2
  })
  router.push("/security")
}

let jumpToChangeMsg = ()=>{
  emitter.emit("changeSelected", {
    changeTo: 1
  })
  router.push("/message")
}
</script>

<template>
  <div class="home_container">
    <div>
      <div class="avatar_container">
        <a-avatar :size="{ xs: 16, sm: 24, md: 32, lg: 52, xl: 64, xxl: 80 }">
          <img src="/users/avatar/noise.jpg" class="avatar_css">
        </a-avatar>
      </div>
      <div class="user_text">
        <a-typography-title :level="4">用户昵称</a-typography-title>
        <a-space :size="[0, 'small']" wrap>
          <a-tag :bordered="false">Tag 1</a-tag>
          <a-tag :bordered="false">Tag 2</a-tag>
          <a-tag :bordered="false">Tag 3</a-tag>
        </a-space>
      </div>
      <div class="user_opr_container">
        <a-button type="primary" size="small" style="margin-right: 1em" @click="jumpToChangeMsg">修改资料</a-button>
        <a-button type="default" size="small">个人空间</a-button>
      </div>
    </div>
    <a-divider></a-divider>
    <div class="security_container">
      <div class="sec_header">
        <a-typography-title :level="5" style="display: inline-block">账号安全</a-typography-title>
        <a-button style="display: inline-block;position: absolute;right: 5%" type="default" size="small" @click="jumpToSecurityDetailPage">
          更多账号安全
        </a-button>
      </div>
      <div class="sec_body">
        <a-row :gutter="[8,64]" style="margin-top: 1em">
          <a-col :span="12">
            <div class="text_container">
              <a-typography-text>我的邮箱</a-typography-text>
            </div>
            <div class="text_container">
              <a-typography-text style="font-size: 12px">绑定后即可使用邮箱登录</a-typography-text>
            </div>
            <a-button size="small">绑定邮箱</a-button>
          </a-col>
          <a-col :span="12">
            <div class="text_container">
              <a-typography-text>我的手机</a-typography-text>
            </div>
            <div class="text_container">
              <a-typography-text style="font-size: 12px">绑定后即可使用手机登录</a-typography-text>
            </div>
            <a-button size="small">绑定手机</a-button>
          </a-col>
          <a-col :span="12">
            <div class="text_container">
              <a-typography-text>我的密保</a-typography-text>
            </div>
            <div class="text_container">
              <a-typography-text style="font-size: 12px">设置密保，账号更安全</a-typography-text>
            </div>
            <a-button size="small">设置密保</a-button>
          </a-col>
          <a-col :span="12">
            <div class="text_container">
              <a-typography-text>修改密码</a-typography-text>
            </div>
            <div class="text_container">
              <a-typography-text style="font-size: 12px">定期修改密码，账号更安全</a-typography-text>
            </div>
            <a-button size="small">修改密码</a-button>
          </a-col>
        </a-row>
      </div>
    </div>
  </div>
</template>

<style scoped>
.home_container {
  padding: 2em;
}

.avatar_container {
  display: inline-block;
}

.avatar_css {
  width: 64px;
  height: 64px;
}

.user_text {
  display: inline-block;
  margin-left: 2em;
  transform: translateY(30%);
}

.user_opr_container {
  display: inline-block;
  position: absolute;
  right: 5%;
  margin-top: 4em;
}

.security_container {

}

.sec_header {

}
.text_container{
  margin-bottom: 5px;
}
</style>