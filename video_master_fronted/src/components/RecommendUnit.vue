<script setup>
import {onBeforeMount, reactive} from "vue";
import axios from "axios";

let page_variable = reactive({
  recommend_type : "",
  recommend_imgs_data:"",
})

const param = defineProps({
  recommend_type: String,
})


page_variable.recommend_type = param.recommend_type;
// 从后台获取影片图片和连接
axios.get(`/video-master/resource/getByName?name="${page_variable.recommend_type}"`)
    .then(
        //处理成功
        function (res){
          page_variable.recommend_imgs_data=res.data;
        }
    ).catch(
        //处理失败
)


</script>

<template>
  <div>
    <div class="movie_recommend margin-top-large movie_container">
      <a-row>
        <a-col :span="12">
          <a-typography-title :level="4" class=" left-margin-20 select_forbidden">推荐{{ page_variable.recommend_type }}</a-typography-title>
        </a-col>
        <a-col :span="12" class="align-right">
          <span class=" right-margin-20">
            <a-button size="middle" type="primary"
                      @click="">全部{{ page_variable.recommend_type }}</a-button>
          </span>
        </a-col>
      </a-row>
      <a-row class="left-margin-10 right-margin-10" :style="{
        height: 10+'%',
      }">
        <!-- todo 使用v-for批量展示图片-->
        <a-col :span="4" v-for="movie in page_variable.recommend_imgs_data" :key="movie.id">
          <a-card hoverable style="width: 90%; height: 30em; border-radius: 10px;"
                  @click="">
            <template #cover>
              <img style="height: 20em" :alt="movie.name" :src="'/resources/'+movie.imgSrc" />
            </template>
            <a-card-meta :title="movie.name"></a-card-meta>
          </a-card>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<style scoped>
.movie_container{
  width: 80%;
  margin-left: 10%;
}
</style>