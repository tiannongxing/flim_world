<script setup>
import {onBeforeUnmount, reactive, ref} from 'vue';
import router from "./routers/main.js";
import emitter from "../../utils/EventBus.js";

const onCollapse = (collapsed, type) => {
  console.log(collapsed, type);
};
const onBreakpoint = broken => {
  console.log(broken);
};
const selectedKeys = ref([0]);
let pushToTargetPage = (page,...msg)=>{
  if(msg.length === 0)
    router.push({path: page})
  else{
    let sets = JSON.stringify(msg)
    router.push({path: page,query: {params:sets}})
  }
}

let intViewportHeight = ref(document.documentElement.clientHeight);
let operationSet = reactive([
  {
    name: '首页',
    to : '/home'
  },
  {
    name: '我的信息',
    to : '/message'
  },
  {
    name: '账号安全',
    to: '/security'
  },
  {
    name: '我的记录',
    to:'/records'
  },
  {
    name: '用户注销',
    to: '/revoked'
  }])

emitter.on("changeSelected",(param)=>{
  // 通过mitt组件实现的全局事件总线，用于实现组件之间通信
  selectedKeys.value = [param.changeTo]
})

onBeforeUnmount(()=>{
  // 在页面跳转前，结束挂载事件
  emitter.off("changeSelected")
})
</script>

<template>
  <a-layout :style="{height : intViewportHeight+'px'}">
    <a-layout-sider
        breakpoint="lg"
        collapsed-width="0"
        @collapse="onCollapse"
        @breakpoint="onBreakpoint"
    >
      <div class="logo user_select_forbidden">
        <a-typography-title style="color: #5FB878" :level="3">个人中心</a-typography-title>
      </div>
      <a-menu v-model:selectedKeys="selectedKeys" theme="dark" mode="inline">
        <a-menu-item v-for="(operation,index) in operationSet" :key="index" @click="pushToTargetPage(operation.to)">
          <span class="nav-text">{{ operation.name }}</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-header :style="{ background: '#fff', padding: 0 }"/>
      <a-layout-content :style="{ margin: '24px 16px 0' }">
        <div :style="{ padding: '24px', background: '#fff', minHeight: `calc(${intViewportHeight}px - 10em)`,
         height: 'auto !important'}">
          <router-view></router-view>
        </div>
      </a-layout-content>
      <a-layout-footer style="text-align: center">
        Ant Design ©2018 Created by Ant UED
      </a-layout-footer>
    </a-layout>
  </a-layout>

</template>

<style scoped>
#components-layout-demo-responsive .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}

.site-layout-sub-header-background {
  background: #fff;
}

.site-layout-background {
  background: #fff;
}

[data-theme='dark'] .site-layout-sub-header-background {
  background: #141414;
}

.logo {
  display: inline-block;
  width: 100%;
  height: 5vh;
  line-height: 5vh;
  margin-left: 2em;
  margin-top: 1em;
}
</style>
