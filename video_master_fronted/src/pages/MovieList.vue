<script setup>

import {onBeforeMount, onMounted, reactive, ref} from "vue";
import {useRoute} from "vue-router";
import axios from "axios";
import MoiveOptional from "../components/MoiveOptional.vue";
import MovieListPanel from "../components/MovieListPanel.vue";
import PaginationUnit from "../components/PaginationUnit.vue";
const route = useRoute()
let current_pagination = ref(1)
let totalMovie = ref(300)
let queryParams = ref("")
onBeforeMount(()=>{
  queryParams = route.query.params
  queryParams = JSON.parse(queryParams)
})

// 利用emit来传递数据，父组件接收子组件的数据并进行处理,将更新后的数据再传给子组件
let handlePaginationChange = (data) => {
  current_pagination.value = data
}

let handleTotalChange = (data) => {
  totalMovie.value = data
}
</script>

<template>
  <moive-optional :target_type="queryParams[0]"></moive-optional>
  <movie-list-panel :target_type="queryParams[0]" @total-change="handleTotalChange" :pagination="current_pagination"></movie-list-panel>
  <pagination-unit @pagination-change="handlePaginationChange" :total="totalMovie"></pagination-unit>
</template>

<style scoped>

</style>