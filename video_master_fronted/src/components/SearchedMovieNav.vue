<script setup>
import {nextTick, ref} from "vue";
import mitt from "mitt";
import emitter from "../utils/EventBus.js";
import {VideosAttributes} from "../utils/VideosAttributes.js";

const activeKey = ref([''])
const oprActiveKey = ref([''])
const typeItem = ref([
  {
    key: '',
    label: '全部',
    title: '全部',
  }, {
    key: '电影',
    label: '电影',
    title: '电影',
  }, {
    key: '动漫',
    label: '动漫',
    title: '动漫',
  }, {
    key: '电视剧',
    label: '电视剧',
    title: '电视剧',
  }, {
    key: '教育',
    label: '教育',
    title: '教育',
  },
])
const oprItem = ref([
  {
    key: '',
    label: '默认排序',
    title: '默认排序',
  }, {
    key: VideosAttributes.MOST_PLAYS_NUMBER,
    label: '最多播放',
    title: '最多播放',
  }, {
    key: VideosAttributes.LEAST_PUBLISH_DATE,
    label: '最新发布',
    title: '最新发布',
  },
])
const callPanel = ()=>{
  let types = {
    type: activeKey.value[0],
    opr: oprActiveKey.value[0]
  }
  emitter.emit("currentSelectCondition", types)
}
</script>

<template>
  <div class="searched_container margin-top-giant">
    <a-page-header
        style="border: 1px solid rgb(235, 237, 240)"
        title="搜索视频"
    />
    <a-menu v-model:selected-keys="activeKey" mode="horizontal" :items="typeItem"
            @select="callPanel()"></a-menu>
    <a-menu v-model:selected-keys="oprActiveKey" mode="horizontal" :items="oprItem"
            @select="callPanel()"></a-menu>
  </div>
</template>

<style scoped>
.searched_container {
  width: 80%;
  margin-left: 10%;
  border-radius: 5px;
}
</style>