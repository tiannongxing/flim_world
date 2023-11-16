<script setup>
import { ref } from 'vue';
import locale from "ant-design-vue/es/locale/zh_CN.js";
const current1 = ref(1);
const current2 = ref(2);
const onChange = pageNumber => {
  console.log('Page: ', pageNumber);
};
</script>

<template>
  <div class="align-center margin-top-giant">
    <a-config-provider :locale="locale">
      <a-pagination v-model:current="current1" simple :total="300" default-page-size="30"/>
    </a-config-provider>
  </div>
</template>

<style scoped>

</style>