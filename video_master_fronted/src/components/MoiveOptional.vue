<script setup>
import {onBeforeMount, onBeforeUpdate, reactive, ref, watch} from "vue";
import axios from "axios";
import emitter from "../utils/EventBus.js";

let selectCondition = reactive({
  // 通过这些条件查询对应的影片
  type:"",
  region:"",
  era:"",
})

const targetType = defineProps({
  target_type : [String,Object],
})

let dataSet = reactive({
  dataType:"",
  dataRegion:"",
  dataEra:"",
})


onBeforeMount(()=>{
    //当传入的数据类型是数据类型，即代表根据类别搜索时，走这条路
    axios.get(`/video-master/resource/getAllOptionByName?targetType="${targetType.target_type}"`)
        .then(
            (res)=>{
              console.log(res)
              dataSet.dataEra = res.data[0];
              dataSet.dataRegion = res.data[1];
              dataSet.dataType = res.data[2];
            }
        ).catch()
})

watch( selectCondition,
    (pre,next)=>{
      emitter.emit("currentSelectCondition",next)
    })
</script>

<template>
  <div class="optional_container margin-top-giant">
    <div class="optional_sub_container">
      <div class="nav-font-huge vertical_center choice_unit user_select_forbidden">类型：</div>
      <div class="nav-font-big vertical_center choice_unit user_select_forbidden" @click='selectCondition.type=""'>全部</div>
      <div class="nav-font-big vertical_center choice_unit user_select_forbidden" @click="selectCondition.type=type"
           v-for="type in dataSet.dataType">{{ type }}</div>
    </div>
    <div class="optional_sub_container">
      <div class="nav-font-huge vertical_center choice_unit user_select_forbidden ">地区：</div>
      <div class="nav-font-big vertical_center choice_unit user_select_forbidden" @click='selectCondition.region=""'>全部</div>
      <div class="nav-font-big vertical_center choice_unit user_select_forbidden" @click="selectCondition.region=region"
           v-for="region in dataSet.dataRegion">{{ region }}</div>
    </div>
    <div class="optional_sub_container">
      <div class="nav-font-huge vertical_center choice_unit user_select_forbidden">年代：</div>
      <div class="nav-font-big vertical_center choice_unit user_select_forbidden" @click='selectCondition.era=""'>全部</div>
      <div class="nav-font-big vertical_center choice_unit user_select_forbidden" @click="selectCondition.era=era"
           v-for="era in dataSet.dataEra">{{ era }}</div>
    </div>
  </div>
</template>

<style scoped>
.optional_container{
  width: 80%;
  margin-left: 10%;
  height: 10em;
  background-color: #F9F9F9;
  border: 1px solid #F9F9F0;
  border-radius: 5px;
}

.optional_sub_container{
  width: 90%;
  height: 33%;
  margin-left: 5%;
}
.optional_sub_container:nth-child(-n+2){
  border-bottom: 2px solid #E9E9E9;
}

.choice_unit{
  display: inline-block;
  margin-left: 0.5em;
  margin-right: 0.5em;
  cursor:pointer;
}

.choice_unit:first-child{
  color: #B3B3B3;
  margin-left: 0em;
  margin-right: 1em;
  cursor: auto;
}

</style>