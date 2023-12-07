<script setup>
import router from "../routers/main.js";

const targetType = defineProps({
  target_type : String,
})

console.log(targetType.target_type)

let movieDetail = (movieMsg) => {
  router.push({path:'/movieDetail',query:{movieIndex:movieMsg}})
}
</script>

<template>
  <div class="margin-top-giant movie_list_container">
    <a-row :gutter="[8,16]">
      <a-col :span="4" v-for="i in 30">
          <a-card hoverable @click="movieDetail(i)">
            <template #cover>
              <img style="height: 10em" alt="影片名" src="/static/loops/3.png" />
            </template>
            <a-card-meta title="影片名">
              <template #description class="">影片主演</template>
            </a-card-meta>
          </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<style scoped>
.movie_list_container{
  width: 84%;
  margin-left: 8%;

}
</style>