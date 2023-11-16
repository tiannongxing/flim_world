<script setup>
import {ref} from "vue";
import CarouselMap from "../components/CarouselMap.vue";
import RecommendUnit from "../components/RecommendUnit.vue";
const recommend_types = ref(["电影", "电视剧", "动漫", "综艺"])
</script>

<template>
  <div>
    <carousel-map></carousel-map>
    <div v-for="type in recommend_types">
      <recommend-unit :recommend_type="type"></recommend-unit>
    </div>
  </div>
</template>

<style scoped>

</style>