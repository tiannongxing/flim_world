<script setup>
import {inject, reactive, watch} from "vue";

let register_message = reactive({
  nickname: "",
  username: "",
  password: "",
  p_repeat: "",
})

let emits = defineEmits(["putRegister"])
let props = defineProps({
  callRegister: String
})

let clearSignal = inject("clear");

watch(()=>[props.callRegister,register_message],([pre1,next1],[pre2,next2])=>{
  emits("putRegister",JSON.stringify(next2))
})

watch(()=>clearSignal,(pre,next)=>{
  register_message.p_repeat = ""
  register_message.password = ""
  register_message.username = ""
  register_message.nickname = ""
})
</script>

<template>
  <div class="register_container">
    <a-row :gutter="[0,16]">
      <a-col :span="5">
        <a-typography-text>昵称：</a-typography-text>
      </a-col>
      <a-col :span="19">
        <a-input placeholder="请输入昵称"  v-model:value.lazy="register_message.nickname"></a-input>
      </a-col>
      <a-col :span="5">
        <a-typography-text>用户名：</a-typography-text>
      </a-col>
      <a-col :span="19">
        <a-input placeholder="请输入用户名"  v-model:value.lazy="register_message.username"></a-input>
      </a-col>
      <a-col :span="5">
        <a-typography-text>密码：</a-typography-text>
      </a-col>
      <a-col :span="19">
        <a-input-password placeholder="请输入密码"  v-model:value.lazy="register_message.password"></a-input-password>
      </a-col>
      <a-col :span="5">
        <a-typography-text>重复密码：</a-typography-text>
      </a-col>
      <a-col :span="19">
        <a-input-password placeholder="请重复输入的密码"  v-model:value.lazy="register_message.p_repeat"></a-input-password>
      </a-col>
    </a-row>
  </div>
</template>

<style scoped>
.register_container {
  width: 90%;
  margin-left: 5%;
  text-align: center;
}
</style>