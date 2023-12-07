<script setup>
import {useRoute} from "vue-router";
import {onBeforeMount, ref} from "vue";
import {CaretRightOutlined} from "@ant-design/icons-vue";
import axios from "axios";
import router from "../routers/main.js";

let query = useRoute().query;
let displayMovieDetail = ref('')
onBeforeMount(() => {
  axios.get(`/video-master/resource/getMovieDetailById?mid=${query.movieIndex}`).then(
      (res) => {
        displayMovieDetail.value = res.data;
      }
  ).catch(
      (res) => {
        alert("产生了一个错误，错误原因：" + res)
      }
  )
})

const toPlayer = (clickEpisode) => {
  router.push({
    path: "/moviePlayer", query: {
      movieId: displayMovieDetail.id,
      selectEpisode: clickEpisode,
    }
  })
}
</script>

<template>
  <div class="container movie_detail_wrapper">
    <div style="background: #EAEDF1;" class="container_extend">
      <a-row :gutter="[16,16]">
        <a-col :span="20">
          <a-typography-title>{{ displayMovieDetail.name }}</a-typography-title>
          <a-tag color="#FDE6DD" style="color: #2f2f2f">{{ displayMovieDetail.type }}</a-tag>
          <a-tag v-for="(cate,index) in displayMovieDetail.category">
            {{ cate }}
          </a-tag>
          <a-typography-title :level="5" style="margin-top: 1em">出版年份：{{ displayMovieDetail.publishDate }}
          </a-typography-title>
          <a-typography-title :level="5" style="margin-top: 0.3em">出版地区：{{
              displayMovieDetail.location
            }}
          </a-typography-title>
          <a-typography-title :level="5" style="margin-top: 0.3em">主演：{{
              displayMovieDetail.starring
            }}
          </a-typography-title>
          <a-typography-title :level="5" style="margin-top: 0.3em">介绍：{{ displayMovieDetail.description }}
          </a-typography-title>

          <a-button type="primary" shape="round" size="large" style="margin-top: 2em">
            <template #icon>
              <CaretRightOutlined/>
            </template>
            立即观看
          </a-button>
        </a-col>
        <a-col :span="4">
          <!-- 会先找不到一次。目前的问题 -->
          <img :src="'/resources/'+displayMovieDetail.imgSrc" :alt="displayMovieDetail.imgSrc">
        </a-col>
      </a-row>
    </div>

    <div class="episode_selector">
      <a-typography-title :level="3">在线观看</a-typography-title>
      <a-typography-text>全{{ displayMovieDetail.totalEpisode }}集</a-typography-text>
      <div class="container_extend" style="background: #EAEDF1">
        <a-button v-for="i in displayMovieDetail.currentEpisode" type="default" class="fix_width button_unit"
                  @click="toPlayer(i)">{{ i }}
        </a-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.movie_detail_wrapper {
  margin-left: 10%;
  width: 80%;

}

.episode_selector {
  margin-top: 2em;
}

.fix_width {
  width: 5em;
  height: 3em;
}

.button_unit {
  margin-top: .1em;
  margin-left: .1em;
}
</style>