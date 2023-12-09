<script setup>
import {defineEmits, onBeforeMount, reactive, ref, watch} from "vue";
import {getMessageSender} from "../utils/MessageSender.js";
import MessageObj from "../utils/messageObj.js";
import {VideosAttributes} from "../utils/VideosAttributes.js";
import router from "../routers/main.js";
import emitter from "../utils/EventBus.js";
import {onBeforeRouteUpdate} from "vue-router";

const targetType = defineProps({
  target_type: [String, Object],
  pagination: Number,
})
let type = ref(targetType.target_type)
let returnMsg = ref('')
let currentPagination = ref(targetType.pagination)
let selected = reactive({
  type: '',
  region: '',
  era: ''
})
let total = ref(0)
const emit = defineEmits(['total-change'])
onBeforeMount(() => {
  // 使用VideosAttributes来保证前端与后端接收的key相同
  console.log(typeof targetType.target_type)
  let promise = null
  if (typeof targetType.target_type === 'string') {
    promise = getMessageSender('/video-master/resource/getVideos',
        new MessageObj(VideosAttributes.VIDEOS_TYPE, type.value).getObject(),
    )
  } else if (typeof targetType.target_type === 'object') {
    let searchKey = targetType.target_type.param
    promise = getMessageSender('/video-master/resource/getMoviesLikeName',
        new MessageObj(VideosAttributes.SEARCHED_KEY_WORDS, searchKey).getObject(),
    )
  }
  if (promise !== null) {
    promise.then((res) => {
      returnMsg.value = res.data.videoList
      total.value = res.data.total
    }).catch((err) => {
      console.log(err)
    })
  }
})


emitter.on("currentSelectCondition", (selected) => {
  selected = selected
  let promise = null
  if (typeof targetType.target_type === 'string') {
    promise = getMessageSender('/video-master/resource/getVideos',
        new MessageObj(VideosAttributes.VIDEOS_TYPE, type.value).getObject(),
        new MessageObj(VideosAttributes.VIDEOS_SUB_TYPE, selected.type).getObject(),
        new MessageObj(VideosAttributes.VIDEOS_REGION, selected.region).getObject(),
        new MessageObj(VideosAttributes.VIDEOS_AGING, selected.era).getObject()
    )
  }else if (typeof targetType.target_type === 'object') {
    console.log(selected.opr == VideosAttributes.MOST_PLAYS_NUMBER ?
        new MessageObj(VideosAttributes.MOST_PLAYS_NUMBER, selected.opr).getObject() :
        new MessageObj(VideosAttributes.LEAST_PUBLISH_DATE, selected.opr))
    let searchKey = targetType.target_type.param
    promise = getMessageSender('/video-master/resource/getMoviesLikeName',
        new MessageObj(VideosAttributes.SEARCHED_KEY_WORDS, searchKey).getObject(),
        new MessageObj(VideosAttributes.VIDEOS_TYPE, selected.type).getObject(),
        selected.opr == VideosAttributes.MOST_PLAYS_NUMBER ?
            new MessageObj(VideosAttributes.MOST_PLAYS_NUMBER, selected.opr).getObject() :
            new MessageObj(VideosAttributes.LEAST_PUBLISH_DATE, selected.opr).getObject(),
    )
  }
  if (promise !== null) {
    promise.then(
        (res) => {
          console.log(res)
          returnMsg.value = res.data.videoList
          total.value = res.data.total
        }
    ).catch(
        (err) => {
          console.log(err)
        }
    )
  }
})
let movieDetail = (movieId) => {
  router.push({path: '/movieDetail', query: {movieIndex: movieId}})
}


watch(() => targetType.pagination, (pre, next) => {
  currentPagination = pre;
  let promise = getMessageSender('/video-master/resource/getVideos',
      new MessageObj(VideosAttributes.VIDEOS_TYPE, type.value).getObject(),
      new MessageObj(VideosAttributes.VIDEOS_SUB_TYPE, selected.type).getObject(),
      new MessageObj(VideosAttributes.VIDEOS_REGION, selected.region).getObject(),
      new MessageObj(VideosAttributes.VIDEOS_AGING, selected.era).getObject(),
      new MessageObj(VideosAttributes.TARGET_PAGE, currentPagination).getObject()
  ).then(
      (res) => {
        returnMsg.value = res.data.videoList
        total.value = res.data.total
      }
  ).catch(
      (err) => {
        console.log(err)
      }
  )
})

watch(total, (pre, next) => {
  emit('total-change', pre)
})
onBeforeRouteUpdate(() => {
  emitter.off("currentSelectCondition")
})
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