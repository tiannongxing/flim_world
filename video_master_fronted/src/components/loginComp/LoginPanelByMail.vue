<script setup>
import {inject, reactive, ref, watch} from "vue";
import axios from "axios";

let loginDataSet = reactive({
    type: "email",
    mail_msg: {
      email: "",
      code: "",
    }
})

let isLoading = ref(false)
let CaptchaContent = ref('验证码')
let disableSendButton = ref(false)
let emits = defineEmits(['putUser'])
// 依赖注入传入的就是一个ref对象
let callLogin = inject("callLogin")
let clearSignal = inject("clear")
watch(()=>callLogin.value,()=>{
  emits('putUser',JSON.stringify(loginDataSet))
})

watch(()=>clearSignal.value,()=>{
  loginDataSet.email = ""
  loginDataSet.code = ""
})


let sendCheckCode = (mail) => {
  isLoading = true;
  axios.post("/video-master/users/getCaptcha", {
    mail: loginDataSet.mail_msg.email
  }).then((res) => {
    isLoading = false;
    // 设置发送验证码冷却时间
    disableSendButton.value = true
    let time = 60;
    let intervalId = setInterval(()=> {
      time--
      CaptchaContent.value = `${time}秒`
      if (time === 0){
        disableSendButton.value = false
        CaptchaContent.value = "发送验证码"
        clearInterval(intervalId)
      }
    }, 1000)
  }).catch(() => {
    // todo 发往错误提示页面
  })
}
</script>

<template>
  <div class="align-center">
    <div>
      <h3>普通登录</h3>
      <div>
        <a-typography-text>邮箱：</a-typography-text>
        <a-input v-model:value.lazy="loginDataSet.mail_msg.email" style="width: 70%" autofocus placeholder="邮箱"/>
      </div>
      <div class="margin-top-medium">
        <a-typography-text>验证码：</a-typography-text>
        <a-input v-model:value.lazy="loginDataSet.mail_msg.code" style="width: 40%" autofocus placeholder="验证码"/>
        <a-button type="primary" style="width: 20%;margin-left: 8%" :loading="isLoading" :disabled="disableSendButton"
                  @click="sendCheckCode(loginDataSet.mail_msg.email)">{{ CaptchaContent }}
        </a-button>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>