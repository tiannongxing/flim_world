<script setup>
import {inject, reactive, ref, watch} from "vue";
import axios from "axios";

let loginDataSet = reactive({
    type: "email",
    data:{
    email: "",
    code: "",
    }
})

let isLoading = ref(false)
let CaptchaContent = ref('验证码')
let disableSendButton = ref(false)
let intervalId = ref(null)
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
</script>

<template>
  <div class="align-center">
    <div>
      <h3>普通登录</h3>
      <div>
        <a-typography-text>邮箱：</a-typography-text>
        <a-input v-model:value.lazy="loginDataSet.email" style="width: 70%" autofocus placeholder="邮箱"/>
      </div>
      <div class="margin-top-medium">
        <a-typography-text>验证码：</a-typography-text>
        <a-input v-model:value.lazy="loginDataSet.code" style="width: 40%" autofocus placeholder="验证码"/>
        <a-button type="primary" style="width: 20%;margin-left: 8%" :loading="isLoading" :disabled="disableSendButton"
                  @click="sendCheckCode(loginDataSet.email)">{{ CaptchaContent }}
        </a-button>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>