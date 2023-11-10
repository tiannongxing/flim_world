<script setup>
import {register} from 'swiper/element/bundle';
import {onBeforeMount, onMounted} from "vue";
import Swiper from "swiper";

onBeforeMount(() => {
  register()
})

onMounted(() => {
  new Swiper('.swiper', {
    // 进行swiper的参数配置
    direction: 'horizontal',
    loop: true,
    centeredSlides: true,

    pagination: {
      el: '.swiper-pagination',
    },


    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },

    autoplay: {
      delay: 8000,
      stopOnLastSlide: false,
      disableOnInteraction: false,
    },
  });
})

const printlog = (msg) => {
  console.log(msg)
}

const clickEvent = (event) => {
  console.log(event.target.src)
}

</script>

<template>
  <div
      class="z-index-3 border-radius-10"
      :style="{
    width: 80+'%',
    marginTop: 1+'em',
    transform: 'translateX(10%)',
    textAlign: 'center',
  }">
    <!-- 使用第三方组件必须额外增加一个div包裹 -->
    <!-- 使用swiper必须要在swiper和swiper-wrapper的包裹下 -->
    <div class="swiper carousel_map_container">
      <div class="swiper-wrapper">
        <!-- todo 传输图片地址的集合，使用v-for展示图片 -->
        <div class="swiper-slide">
          <img class="border-radius-10 img_container" src="/static/loops/1.png" @click="clickEvent($event)" loading="lazy" alt="">
        </div>
        <div class="swiper-slide">
          <img class="border-radius-10 img_container" src="/static/loops/2.png" @click="clickEvent($event)" loading="lazy" alt="">
        </div>
        <div class="swiper-slide">
          <img class="border-radius-10 img_container" src="/static/loops/3.png" @click="clickEvent($event)" loading="lazy"  alt="">
        </div>
      </div>

      <div class="swiper-pagination"></div>

      <div class="swiper-button-prev"></div>
      <div class="swiper-button-next"></div>

    </div>
  </div>


</template>

<style scoped>
.carousel_map_container{
  display: inline-block;
  width: 80%;
  height: 30em;
}
.img_container{
  width: 100%;
  height: 100%;
}
</style>