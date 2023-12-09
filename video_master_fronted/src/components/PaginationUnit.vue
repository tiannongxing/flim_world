<script setup>
import {reactive, ref, watch,defineEmits} from 'vue';
import locale from "ant-design-vue/es/locale/zh_CN.js";
let current_pagination = ref(1)
let total = ref(300)
const onChange = pageNumber => {
  console.log('Page: ', pageNumber);
};
const emit = defineEmits(['pagination-change'])
watch(current_pagination, (pre, next) => {
  emit('pagination-change',pre)
})

let receivedParams = defineProps({
  total: Number
})
total.value = receivedParams.total
watch(()=> receivedParams.total, (pre, next) => {
  total.value=pre;
})
</script>

<template>
  <div class="align-center margin-top-giant" >
    <a-config-provider :locale="locale">
      <a-pagination v-model:current="current_pagination" show-quick-jumper :showSizeChanger="false" :total="total" default-page-size="30"/>
    </a-config-provider>
  </div>
</template>

<style scoped>

</style>