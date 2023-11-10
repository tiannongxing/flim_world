<script setup>
import NavBar from "./components/NavBar.vue";
import Footer from "./components/Footer.vue";
import CarouselMap from "./components/CarouselMap.vue";
import RecommendUnit from "./components/RecommendUnit.vue";
import {reactive, ref} from "vue";

const recommend_types = ref(["电影", "电视剧", "动漫", "综艺"])
</script>

<template>
  <nav-bar class="navbar_style"></nav-bar>
  <carousel-map></carousel-map>
  <recommend-unit :recommend_type="recommend_types[0]"></recommend-unit>
<!--  <router-view>-->
<!--    -->
<!--  </router-view>-->
  <Footer></Footer>
</template>

<style scoped>
.navbar_style{
  background: black;
  padding: 1em;
  border-radius: 5px;
}
</style>
