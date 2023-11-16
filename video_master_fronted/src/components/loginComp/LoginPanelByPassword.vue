<script setup>
import {reactive} from "vue";

let loginDataSet = reactive({
  loginMethod: "password",
  loginData: {
    username: "",
    password: "",
    email: "",
    code: "",
  },
})
</script>

<template>
  <div class="align-center">
    <div>
      <h3>普通登录</h3>
      <div>
        <a-typography-text>用户：</a-typography-text>
        <a-input v-model:value.lazy="loginDataSet.loginData.username" style="width: 70%" autofocus placeholder="用户名"/>
      </div>
      <div class="margin-top-medium">
        <a-typography-text>密码：</a-typography-text>
        <a-input v-model:value.lazy="loginDataSet.loginData.username" style="width: 70%" autofocus placeholder="密码"/>
      </div>
    </div>
  </div>
</template>

<style scoped>


</style>