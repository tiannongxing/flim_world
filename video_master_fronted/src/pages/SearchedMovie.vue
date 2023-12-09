<script setup>
import MovieListPanel from "../components/MovieListPanel.vue";
import PaginationUnit from "../components/PaginationUnit.vue";
import SearchedMovieNav from "../components/SearchedMovieNav.vue";
import {ref} from "vue";
import {useRoute} from "vue-router";

let selectedParams = ref([])
let current_pagination = ref(1)
let totalMovie = ref(300)
let queryParams = ref({param:''})
queryParams.value.param = JSON.parse(useRoute().query.params)[0]
let paginationChangeHandler = (data) => {
  current_pagination.value = data
}

let totalMovieChangeHandler = (data) => {
  totalMovie.value = data
}
</script>

<template>
  <searched-movie-nav></searched-movie-nav>
  <movie-list-panel :target_type="queryParams" @total-change="totalMovieChangeHandler" :pagination="current_pagination"></movie-list-panel>
  <pagination-unit @pagination-change="paginationChangeHandler" :total="totalMovie"></pagination-unit>
</template>

<style scoped>

</style>