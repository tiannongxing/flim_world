<script setup>

</script>

<template>
  <script setup>
    import {useRoute} from "vue-router";
    import {onBeforeMount, onBeforeUnmount, onMounted, reactive, ref} from "vue";
    import {EnterOutlined} from "@ant-design/icons-vue";
    import "video.js/dist/video-js.css"
    import videojs from "video.js";
    import CommentComp from "../components/CommentComp.vue";
    import CommentReply from "../components/CommentReply.vue";

    const videoPlayer = ref(null)
    const myPlayer = ref(null);

    let query = useRoute().query;
    let moviePlayer = ref('')
    onBeforeMount(() => {
      // 通过页面传输消息获得影片的地址
    })

    onMounted(() => {
      // 配置播放器
      myPlayer.value = videojs(videoPlayer.value, {
        poster: "/resources/gulite.jpg",
        controls: true,
        sources: [
          {
            src: "/resources/test_movie.mp4",
            type: 'video/mp4',
          }
        ],
        controlBar: {
          remainingTimeDisplay: {
            displayNegative: false
          }
        },
        playbackRates: [0.5, 1, 1.5, 2]
      }, () => {
        myPlayer.value.log("play.....")
      })
    })
    onBeforeUnmount(() => {
      // 组件销毁之前注销播放器组件
      if (myPlayer.value) {
        myPlayer.value.dispose()
      }
    })

    // 存放聊天消息
    let chatSet = reactive({chats: []})

    let addChat = (chat) => {

    }
  </script>

  <template>
    <div class="container">

      <div class="movie_player_container">
        <a-typography-title :level="4">影片名</a-typography-title>
        <video width="860" height="480" ref="videoPlayer" class="video-js player_location"
               style="margin: auto auto;">
        </video>
        <div class="chat_panel scrollbar">
          <a-typography-text strong style="font-size: 16px;">聊天室<br/></a-typography-text>
          <!-- todo 通过websocket 实现实时聊天 -->
          <div class="display_chat">

          </div>
          <a-input placeholder="请输入聊天内容">
            <template #suffix>
              <a-button size="small" type="primary" @click="">
                <template #icon>
                  <EnterOutlined/>
                </template>
              </a-button>
            </template>
          </a-input>
        </div>
      </div>


      <div class="episode_selector">
        <a-typography-text>全1集</a-typography-text>
        <div class="container_extend" style="background: #EAEDF1">
          <a-button v-for="i in 13" type="default" class="fix_width button_unit"
                    @click="">{{ i }}
          </a-button>
        </div>
      </div>
      <div class="episode_selector">
        <a-typography-title :level="5">评论</a-typography-title>
        <comment-comp></comment-comp>
        <comment-reply></comment-reply>
      </div>

    </div>
  </template>

  <style scoped>
    .movie_player_container {
      margin-left: 18%;
      margin-top: 4em;
    }

    .player_location {
      display: inline-block;
    }

    .chat_panel {
      display: inline-block;
      width: 15em;
      height: 480px;
      border: #B3B3B3 solid 2px;
      border-radius: 0 5px 5px 0;
      padding: 0.5rem;
      position: relative;
    }

    .display_chat {
      display: inline-block;
      width: 100%;
      height: 83%;
      margin: 0.5em 0;
      border: 1px solid #D9D9D9;
      border-radius: 1%;

    }

    .episode_selector {
      margin-top: 4em;
      width: 80%;
      margin-left: 10%;
    }

    .fix_width {
      width: 5em;
      height: 3em;
    }

    .button_unit {
      margin-top: .3em;
      margin-left: .5em;
    }

  </style>
</template>

<style scoped>

</style>