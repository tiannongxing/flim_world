<script setup>

import {onBeforeMount, onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import axios from "axios";
import MoiveOptional from "../components/MoiveOptional.vue";
import MovieListPanel from "../components/MovieListPanel.vue";
import PaginationUnit from "../components/PaginationUnit.vue";
const route = useRoute()
let queryParams = ref("")
onBeforeMount(()=>{
  queryParams = route.query.params
  queryParams = JSON.parse(queryParams)
})
</script>

<template>
  <moive-optional :target_type="queryParams[0]"></moive-optional>
  <movie-list-panel :target_type="queryParams[0]"></movie-list-panel>
  <pagination-unit></pagination-unit>
</template>

<style scoped>

</style>