<script setup>
import {onBeforeMount, reactive, ref} from "vue";
import {getMessageSender} from "../utils/MessageSender.js";
import MessageObj from "../utils/messageObj.js";
import {VideosAttributes} from "../utils/VideosAttributes.js";
import router from "../routers/main.js";

const targetType = defineProps({
  target_type: String,
})

let type = ref(targetType.target_type)
let returnMsg = ref('')
onBeforeMount(() => {
  // 使用VideosAttributes来保证前端与后端接收的key相同
  let promise = getMessageSender('/video-master/resource/getVideos',
      new MessageObj(VideosAttributes.VIDEOS_TYPE, type).getObject(),
  )
  promise.then((res) => {
    returnMsg.value = res.data
  }).catch((err) => {
    console.log(err)
  })

  console.log(returnMsg.value)
})
let movieDetail = (movieMsg) => {
  router.push({path:'/'})
}
</script>

<template>
  <div class="margin-top-giant movie_list_container">
    <a-row :gutter="[8,16]">
      <a-col :span="4" v-for="(movie,index) in returnMsg" :key="movie.id">
        <a-card hoverable @click="movieDetail(movie.id)">
          <template #cover>
            <img :alt="movie.name" :src="`/resources/${movie.imgSrc}`"/>
          </template>
          <a-card-meta :title="movie.name">
            <template #description class="">{{ movie.starring }}</template>
          </a-card-meta>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<style scoped>
.movie_list_container {
  width: 84%;
  margin-left: 8%;

}
</style>