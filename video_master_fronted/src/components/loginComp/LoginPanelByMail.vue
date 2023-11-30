<script setup>
import {reactive, ref} from "vue";
import axios from "axios";

let loginDataSet = reactive({
  loginMethod: "mail",
  loginData: {
    username: "",
    password: "",
    email: "",
    code: "",
  },
})

let isLoading = ref(false)
let CaptchaContent = ref('验证码')
let disableSendButton = ref(false)
let intervalId = ref(null)
let sendCheckCode = (mail) => {
  isLoading = true;
  axios.post("/certification/getCaptcha", {
    mail: mail
  }).then((res) => {
    isLoading = false;
    // 设置发送验证码冷却时间
    disableSendButton = true
    let time = 60;
    setInterval(id=>{
      intervalId.value = id
      time--
      CaptchaContent.value = `${time}秒后可重发`
      if (time === 0){
        disableSendButton.value = false
        CaptchaContent.value = "发送验证码"
        clearInterval(id)
      }
    }, 1000)
  }).catch(() => {
    // todo 发往错误提示页面
  })
}


//todo 尝试：当emitter调用该模块时使用回调函数返回该模块中填写的数据。
</script>

<template>
  <div class="align-center">
    <div>
      <h3>普通登录</h3>
      <div>
        <a-typography-text>邮箱：</a-typography-text>
        <a-input v-model:value.lazy="loginDataSet.loginData.email" style="width: 70%" autofocus placeholder="邮箱"/>
      </div>
      <div class="margin-top-medium">
        <a-typography-text>验证码：</a-typography-text>
        <a-input v-model:value.lazy="loginDataSet.loginData.code" style="width: 40%" autofocus placeholder="验证码"/>
        <a-button type="primary" style="width: 20%;margin-left: 8%" :loading="isLoading" :disabled="disableSendButton"
                  @click="sendCheckCode(loginDataSet.loginData.email)">{{ CaptchaContent }}
        </a-button>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>