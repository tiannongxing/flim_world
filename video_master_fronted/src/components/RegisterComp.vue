<script setup>

</script>

<template>
  <div class="register_container">
    <a-row :gutter="[0,16]">
      <a-col :span="5">
        <a-typography-text>昵称：</a-typography-text>
      </a-col>
      <a-col :span="19">
        <a-input placeholder="请输入昵称"></a-input>
      </a-col>
      <a-col :span="5">
        <a-typography-text>用户名：</a-typography-text>
      </a-col>
      <a-col :span="19">
        <a-input placeholder="请输入用户名"></a-input>
      </a-col>
      <a-col :span="5">
        <a-typography-text>密码：</a-typography-text>
      </a-col>
      <a-col :span="19">
        <a-input-password placeholder="请输入密码"></a-input-password>
      </a-col>
      <a-col :span="5">
        <a-typography-text>重复密码：</a-typography-text>
      </a-col>
      <a-col :span="19">
        <a-input-password placeholder="请重复输入的密码"></a-input-password>
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