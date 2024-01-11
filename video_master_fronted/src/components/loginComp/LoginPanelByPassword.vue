<script setup>
import {inject, reactive, watch} from "vue";

let loginDataSet = reactive({
  type: "pass",
  pass_msg:{
    username: "",
    password: "",
  }


})

let emits = defineEmits(['putUser'])
let callLogin = inject("callLogin")
let clearSignal = inject("clear")
watch(() => callLogin.value, () => {
  emits('putUser', JSON.stringify(loginDataSet))
})

watch(() => clearSignal.value, () => {
  loginDataSet.username = ""
  loginDataSet.password = ""
})
</script>

<template>
  <div class="align-center">
    <div>
      <h3>普通登录</h3>
      <div>
        <a-typography-text>用户：</a-typography-text>
        <a-input v-model:value.lazy="loginDataSet.pass_msg.username" style="width: 70%" autofocus placeholder="用户名"/>
      </div>
      <div class="margin-top-medium">
        <a-typography-text>密码：</a-typography-text>
        <a-input v-model:value.lazy="loginDataSet.pass_msg.password" style="width: 70%" autofocus placeholder="密码"/>
      </div>
    </div>
  </div>
</template>

<style scoped>


</style>