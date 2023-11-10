<script setup>
import {onBeforeMount, reactive} from "vue";
import axios from "axios";

let page_variable = reactive({
  recommend_type : "",
  recommend_imgs_data:[

  ],
})

const param = defineProps({
  recommend_type: String,
})


page_variable.recommend_type = param.recommend_type;
// 从后台获取影片图片和连接
axios.get()


</script>

<template>
  <div>
    <div class="movie_recommend margin-top-large">
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
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 100%; border-radius: 10px;"
                  @click="">
            <template #cover>
              <img alt="skirt" />
            </template>
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<style scoped>

</style>