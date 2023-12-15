<script setup>
import {useRoute} from "vue-router";
import {nextTick, onBeforeMount, onBeforeUnmount, onMounted, reactive, ref, watch} from "vue";
import {EnterOutlined} from "@ant-design/icons-vue";
import "video.js/dist/video-js.css"
import videojs from "video.js";
import CommentComp from "../components/CommentComp.vue";
import CommentReply from "../components/CommentReply.vue";
import {SERVER_IP} from "../const_param.js";
import {getMessageSender} from "../utils/MessageSender.js";
import messageObj from "../utils/messageObj.js";


const videoPlayer = ref(null)
const myPlayer = ref(null);
let ws = ref(null)
let query = useRoute().query;
let moviePlayer = ref('')
let sendMsg = ref('')
let callIp = ref(SERVER_IP)
// 匿名用户以当前时间戳作为用户id
let uid = ref(new Date().getTime())
let movieId = ref(0)

let messageSet = reactive([])
let messageContent = ref({
  senderId: '',
  senderName: '',
  roomId: '',
  time: '',
  content: ''
})
onBeforeMount(() => {
  // 通过页面传输消息获得影片播放相关信息
  let messageSender = getMessageSender("/video-master/resource/getPlayingCorrect",
      new messageObj("movieId", query.movieId).getObject(),
      new messageObj("selectEpisode", query.selectEpisode).getObject());

  messageSender.then((res) => {
    moviePlayer.value = res.data;

    // 配置播放器 没有解决直接在uri上的修改导致的一些问题
    myPlayer.value = videojs(videoPlayer.value, {
      poster: `/resources/${moviePlayer.value.imgSrc}`,
      controls: true,
      sources: [
        {
          src: `/resources/${moviePlayer.value.uri}`,
          type: `video/${moviePlayer.value.fileFormat}`,
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
  }).catch((err) => {
    //todo 向日志中写入错误信息

  })

})
onMounted(() => {
  //配置websocket 后端要注意跨域问题
  ws.value = new WebSocket(`ws://${callIp.value}/video-master/websocket?uid=${encodeURIComponent(uid.value)}&roomId=${movieId.value}`) //服务器地址


  // 使用事件监听器，监听ws的状态变化
  ws.value.addEventListener("open", () => {
    // ws与后端建立连接成功会调用这个回调函数
  })

  ws.value.addEventListener("message", (message) => {
    //收到服务器发送的消息，就把它显示在聊天框中 分辨服务器发送的是什么类型的消息
    if (typeof message.data === "string") {
      let msg = JSON.parse(message.data)
      messageContent.value.senderId = msg.senderId
      messageContent.value.senderName = msg.senderName
      messageContent.value.time = msg.time
      messageContent.value.content = msg.content
      // 深拷贝对象，解决引用问题
      let copy = JSON.parse(JSON.stringify(messageContent.value));
      messageSet.push(copy)
    }
    if (message.data instanceof ArrayBuffer) {
      let buffer = message.data
      console.log("Received arraybuffer");
    }
  })

  ws.value.addEventListener("close", () => {
    console.log("连接关闭")
  })

  ws.value.addEventListener("error", (err) => {
    console.log(err)
  })


})
onBeforeUnmount(() => {
  // 组件销毁之前注销播放器组件|websocket连接
  ws.value.close()
  if (myPlayer.value) {
    myPlayer.value.dispose()
  }
})

let socketSender = () => {
  messageContent.value.senderId = uid
  messageContent.value.senderName = '测试用户'
  messageContent.value.content = sendMsg.value
  messageContent.value.roomId = movieId.value.toString()
  messageContent.value.time = new Date().toLocaleTimeString()
  ws.value.send(JSON.stringify(messageContent.value))
  sendMsg.value = ''
}
</script>


<template>
  <div class="container">

    <div class="movie_player_container">
      <a-typography-title :level="4">{{ moviePlayer.title }}</a-typography-title>
      <video width="860" height="480" ref="videoPlayer" class="video-js player_location"
             style="margin: auto auto;">
      </video>
      <div class="chat_panel">
        <!-- todo 通过websocket 实现实时聊天 -->
        <a-typography-text strong style="font-size: 16px;">聊天室<br/></a-typography-text>
        <div class="display_chat scrollbar">
          <!--     显示聊天内容     -->
          <a-row :gutter="[4,4]">
            <a-col v-for="item in messageSet" :key="item.time">
              <a-typography-text :style="{color: '#1890ff',marginRight:'1em'}">{{ item.senderName }}</a-typography-text>
              <a-typography-text strong>{{ item.time }}</a-typography-text>
              <br>
              <a-typography-text>{{ item.content }}</a-typography-text>
            </a-col>
          </a-row>
        </div>
        <a-input placeholder="请输入聊天内容" v-model:value="sendMsg">
          <template #suffix>
            <a-button size="small" type="primary" @click="socketSender">
              <template #icon>
                <EnterOutlined/>
              </template>
            </a-button>
          </template>
        </a-input>
      </div>
    </div>


    <div class="episode_selector">
      <a-typography-text>全 {{ moviePlayer.totalEpisode }} 集</a-typography-text>
      <div class="container_extend" style="background: #EAEDF1">
        <a-button v-for="i in moviePlayer.totalEpisode" type="default" class="fix_width button_unit"
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
  padding: 0.5em;
  overflow-y: scroll;

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
