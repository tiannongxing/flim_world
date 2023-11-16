<script setup>
import NavBar from "./components/NavBar.vue";
import Footer from "./components/Footer.vue";
import {useRoute} from "vue-router";
import locale from "ant-design-vue/es/vc-picker/locale/zh_CN.js";



</script>

<template>
  <a-config-provider :locale="locale">
    <nav-bar class="navbar_style"></nav-bar>
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
