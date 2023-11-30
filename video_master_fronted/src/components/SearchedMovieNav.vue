<script setup>
import {nextTick, ref} from "vue";
import mitt from "mitt";
import emitter from "../utils/EventBus.js";

const activeKey = ref(['all'])
const oprActiveKey = ref(['default'])
const typeItem = ref([
  {
    key: 'all',
    label: '全部',
    title: '全部',
  }, {
    key: 'film',
    label: '电影',
    title: '电影',
  }, {
    key: 'comic',
    label: '动漫',
    title: '动漫',
  }, {
    key: 'drama',
    label: '电视剧',
    title: '电视剧',
  }, {
    key: 'education',
    label: '教育',
    title: '教育',
  },
])
const oprItem = ref([
  {
    key: 'default',
    label: '默认排序',
    title: '默认排序',
  }, {
    key: 'play_num',
    label: '最多播放',
    title: '最多播放',
  }, {
    key: 'new',
    label: '最新发布',
    title: '最新发布',
  },
])
const callPanel = ()=>{
  console.log(activeKey.value[0] + '/' + oprActiveKey.value[0])
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