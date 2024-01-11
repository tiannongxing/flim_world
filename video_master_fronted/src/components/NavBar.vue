<script setup>
import {markRaw, nextTick, onMounted, provide, reactive, ref, watch} from "vue";
import locale from "ant-design-vue/es/locale/zh_CN.js";
import {PoweroffOutlined, UserOutlined} from "@ant-design/icons-vue";
import router from "../routers/main.js";
import LoginPanelByMail from "./loginComp/LoginPanelByMail.vue";
import LoginPanelByPassword from "./loginComp/LoginPanelByPassword.vue";
import RegisterComp from "./RegisterComp.vue";
import {message} from "ant-design-vue";
import {getMessageSender, postMessageSender} from "../utils/MessageSender.js";
import messageObj from "../utils/messageObj.js";
import TokenMapHandler from "../utils/TokenMapHandler.js";
import store from "../store/store.js";


const avatar = ref(null)
const user_display_size = reactive({
  width: 0,
  height: 0
})
const font_color_list = reactive({
  selected: '#1677FF',
  unselect: '#000000'
})

const model_corresponds_path = reactive({
  "registerModel": "/video-master/users/register",
  "loginModel": "/video-master/users/login"
})

let clear_signal = ref('')
let avatar_animate = ref('')
const animate_list = ref(['avatar_magnify', 'avatar_shrink', 'user_display_show', 'user_display_hidden'])
let header_image = ref('/static/unlogin.png')
let is_login = reactive({
  isLogin: false,
  user: {}
})
let put_user = defineEmits(['putUser'])
let show_user_display = ref(false)
let display_user_panel = ref('')
let font_color = ref(font_color_list.unselect)
let display_sub_user_panel = ref('')
let selected = ref("index")
let callRegister = ref("")
let callLogin = ref("")
let find = ref("")
let received = ref("")

onMounted(() => {
  user_display_size.width = avatar.value.size * 9
  user_display_size.height = avatar.value.size * 12
})
let registerHandler = (e) => {
  received.value = e
}

let loginHandler = (e) => {
  received.value = e
}

const title_img_click = () => {
  console.log('title_img_click')
}

const change_font_color = (event, color) => {
  event.target.style.color = color
}


let onSearch = () => {
  jumpToPage('/searchedMovie', find.value)
}

let jumpToPage = (path, ...param) => {
  let params = "";
  if (param.length != 0) {
    params = JSON.stringify(param)
  }
  router.push({path: path, query: {params: params}})
}
const modelOpen = reactive({
  loginModel: false,
  registerModel: false,
})
const showModel = (modelName) => {
  modelOpen[modelName] = true
}

let userLogout = () => {
  getMessageSender("/video-master/users/logout", new messageObj("id", is_login.user.id).getObject())
      .then((res) => {
        // 确认注销成功后，清除localstorage中的token信息
        avatar_animate=animate_list[1]
        store.dispatch("updateUserState", {isLogin: false})
        localStorage.removeItem("token")
        message.success("注销成功")

      })
      .catch((err) => {
        message.error(`注销失败,错误信息${err.message}`)
      })
}
provide("callLogin", callLogin)
provide("clear", clear_signal)
const handleRegisterOk = (modelName, successText, errorText) => {
  let errAppend = "" // 为空表示没有错误信息
  callRegister.value = Date.now().toString()
  nextTick(() => {
    let rec = JSON.parse(received.value)
    let params = []
    // 通过遍历对象生成传输的消息对象数组，并通过ES6展开语法发送到自定义的postMessage方法的不定参数中
    for (let key in rec) {
      params.push(new messageObj(key, rec[key]).getObject())
    }
    let postMessage = postMessageSender(
        model_corresponds_path[modelName]
        , ...params
    );

    postMessage.then((res) => {
      modelOpen[modelName] = false
      message.success(successText)
      clear_signal.value = Date.now().toString()
    }).catch((err) => {
      // 后台会验证用户输入是否合法，如不通过，会通过err返回错误信息
      let output = ""
      err.response.data.forEach(item => {
        // 无法换行
        output += `${errorText}: ${item}`;
      })
      message.error(`${output}`, 3)
    })
  })
  received.value = ""
};

watch(() => is_login.user, (pre, next) => {
  // 当当前登录用户的状态改变时，更新store中的状态信息
  store.dispatch("updateUserState", is_login)
})

watch(() => store.state.userState, (oldValue, newValue) => {
  let recObj = JSON.parse(JSON.stringify(oldValue))
  is_login.isLogin = recObj.isLogin
  is_login.user = recObj.user

}, {
  deep: true // 启动深度观察,如果需要观察对象下的多个属性
})

const handleLoginOk = (modelName, successText, errorText) => {
  // 父组件向间隔超过一层的子组件传值应该使用依赖注入 provide,后代可以直接使用inject调用
  callLogin.value = Date.now().toString()
  nextTick(() => { // 保证接收到的数据刷新
    let rec = JSON.parse(received.value)
    let params = []
    let tar = ""
    if (rec.type === "pass") {
      for (let key in rec.pass_msg) {
        params.push(new messageObj(key, rec.pass_msg[key]).getObject())
      }
      tar = "/password"

    } else if (rec.type === "email") {
      console.log(rec.mail_msg)
      for (let key in rec.mail_msg) {
        console.log(key)
        params.push(new messageObj(key, rec.mail_msg[key]).getObject())
      }
      tar = "/mail"
    }
    console.log(params)
    let postMessage = postMessageSender(
        model_corresponds_path[modelName] + tar
        , ...params
    );

    postMessage.then((res) => {
      let tokenMap = res.data
      //获得后台的map对象，并将token存入本地存储
      let userObj = TokenMapHandler.parsingMap(tokenMap)
      is_login.isLogin = true
      is_login.user = userObj
      //将登录对象放到store中
      store.dispatch("updateUserState", is_login)
      modelOpen[modelName] = false
      message.success(successText)
      clear_signal.value = Date.now().toString()
    }).catch((err) => {
      // 输入的信息有误，不退出注册页面，弹出报错信息
      message.error(`${errorText}: ${err.response.data}`)
    })
  })
};

let loginPanelShowList = reactive([
  markRaw(LoginPanelByPassword),
  markRaw(LoginPanelByMail),
])

let selectedLoginPanel = ref(loginPanelShowList[0])
let jumpToAdmin = () => {
  window.location.href = 'src/views/administrator/'
}

let jumpToIndividualCenter = () => {
  window.location.href = 'src/views/individual_center/'
}

</script>

<template>
  <div class="z-index-4">
    <a-row>
      <!--首图-->
      <a-col :span="3">
        <img width="200" src="/static/logo.png" @click="title_img_click">
      </a-col>
      <!--类别-->
      <a-col :span="7" style="margin-left: 3em">
        <a-row class="vertical_center margin-top-tiny">
          <a-col :span="4" class="nav-font-huge">
            <div class="select_button user_select_forbidden" @click="jumpToPage('/index')">
              首页
            </div>
          </a-col>
          <a-col :span="4" class="nav-font-huge">
            <div class="select_button user_select_forbidden" @click="jumpToPage('/movieList','电影')">
              电影
            </div>
          </a-col>
          <a-col :span="4" class="nav-font-huge">
            <div class="select_button user_select_forbidden" @click="jumpToPage('/movieList','动漫')">
              动漫
            </div>
          </a-col>
          <a-col :span="5" class="nav-font-huge">
            <div class="select_button user_select_forbidden" @click="jumpToPage('/movieList','电视剧')">
              电视剧
            </div>
          </a-col>
          <a-col :span="4" class="nav-font-huge">
            <div class="select_button user_select_forbidden" @click="jumpToPage('/movieList','教育')">
              教育
            </div>
          </a-col>
        </a-row>
      </a-col>
      <!--搜索框-->
      <a-col :span="7">
        <div class="vertical_center">
          <a-input v-model:value="find" placeholder="请输入影片名">
            <template #suffix>
              <a-button size="middle" type="primary" @click="onSearch">搜索</a-button>
            </template>
          </a-input>
        </div>
      </a-col>
      <!--用户-->
      <a-col :span="3">
        <a-row class="vertical_center align-center">
          <a-col :span="24">
            <div class="inline_block"
                 @mouseenter="is_login.isLogin===true?(display_user_panel=animate_list[2],avatar_animate=animate_list[0]):display_user_panel=animate_list[2]"
                 @mouseleave="is_login.isLogin===true?(display_user_panel=animate_list[3],avatar_animate=animate_list[1]):display_user_panel=animate_list[3]"
            >
              <!-- 非登录状态下展示框-->
              <div
                  class="user_display"
                  :class="display_user_panel"
                  v-if="!is_login.isLogin"
                  :style="
                {
                'width':user_display_size.width+'px',
                'height':user_display_size.height/1.6+'px',
                'left':-user_display_size.width/4+'px',
                }">
                <div class="content">
                  <p class="nav-font-middle select_forbidden"><span>登陆后你可以:</span></p>
                  <a-row class="align-center nav-font-middle select_forbidden" style="margin: 10% 0">
                    <a-col :span="12"><span>待定内容1</span></a-col>
                    <a-col :span="12"><span>待定内容2</span></a-col>
                  </a-row>
                  <a-row class="align-center nav-font-middle select_forbidden" style="margin: 10% 0">
                    <a-col :span="12"><span>待定内容3</span></a-col>
                    <a-col :span="12"><span>待定内容4</span></a-col>
                  </a-row>
                  <a-button type="primary" size="large" block @click="showModel('loginModel')">立即登录</a-button>
                  <a-row class="align-center nav-font-middle" style="margin: 5% 0">
                    <a-col :span="24">
                      <span class="select_forbidden">首次使用？</span>
                      <span style="color: #4096FF" @click="showModel('registerModel')"
                            class="user_select_forbidden cursor_pointer">点击注册</span>
                    </a-col>
                  </a-row>
                </div>
              </div>

              <!-- 登录状态下展示框-->
              <div class="user_display"
                   :class="display_user_panel"
                   v-if="is_login.isLogin"
                   :style="
               {
                'width':user_display_size.width+'px',
                'height':user_display_size.height/1.8+'px',
                'left':-user_display_size.width/7.5+'px',
                'margin-top':25+'%'
                }">
                <div class="content" style="padding-top: 10%">
                  <a-divider/>
                  <a-button @mouseenter=""
                            @mouseleave=""
                            class="align-left" type="text" size="large" @click="jumpToIndividualCenter" block>
                    <span><UserOutlined/>个人中心</span>

                  </a-button>
                  <a-button @mouseenter=""
                            @mouseleave=""
                            class="align-left" type="text" size="large" @click="userLogout" block>
                    <span><PoweroffOutlined/> 退出登录</span>
                  </a-button>
                </div>
              </div>
              <!-- 头像展示框-->
              <div style="width: 32px;height: 32px">
                <div
                    :class="avatar_animate"
                    :style="{
                  margin: 30+'%',
                  transform: 'translateX(-30%)',
                    }">
                  <a-avatar ref="avatar" :size="32">
                    <template #icon>
                      <img :src="is_login.isLogin == false?header_image:`/users/${is_login.user.img}`">
                    </template>
                  </a-avatar>
                </div>
              </div>
            </div>
          </a-col>
        </a-row>
      </a-col>
      <a-col :span="3" style="transform:translateY(25%)">
        <a-typography-text class="backend nav-font-huge user_select_forbidden" @click="jumpToAdmin">后台
        </a-typography-text>
      </a-col>
    </a-row>
    <a-config-provider :locale="locale"> <!-- 语言设置 -->
      <!-- 登录模态窗口 后端返回数据后再判断窗口是否需要退出 -->
      <a-modal v-model:open="modelOpen.loginModel" title="登录" ok-text="登录"
               @ok="handleLoginOk('loginModel','登录成功','登录失败')">
        <div class="align-center">
          <a-button type="link" :disabled="selectedLoginPanel === loginPanelShowList[0]"
                    @click="selectedLoginPanel=loginPanelShowList[0]">密码登录
          </a-button>
          <a-typography-text>|</a-typography-text>
          <a-button type="link" :disabled="selectedLoginPanel === loginPanelShowList[1]"
                    @click="selectedLoginPanel=loginPanelShowList[1]">邮箱登录
          </a-button>
        </div>
        <component :is="selectedLoginPanel" @putUser="loginHandler"></component>
      </a-modal>

      <!-- 注册模态窗口 -->
      <a-modal v-model:open="modelOpen.registerModel" title="注册" ok-text="注册"
               @ok="handleRegisterOk('registerModel','注册成功','注册失败')">
        <register-comp :callRegister="callRegister" @putRegister="registerHandler"></register-comp>
      </a-modal>
    </a-config-provider>
  </div>

</template>

<style scoped>
.inline_block {
  display: inline-block;
}

@keyframes avatar_animate_magnify {
  from {
    transform: scale(1);
  }
  to {
    transform: scale(4) translate(-50%, 40%);
    margin-bottom: 1px;

  }
}

@keyframes avatar_animate_shrink {
  from {
    transform: scale(4) translate(-50%, 40%);
    margin-bottom: 1px;
  }
  to {
    transform: scale(1);
  }
}

@keyframes icon_animate_bounce {
  50% {
    transform: translateY(-50%);
  }
}

.avatar_magnify {
  animation: avatar_animate_magnify 0.5s;
  animation-fill-mode: forwards;
}

.avatar_shrink {
  animation: avatar_animate_shrink 0.5s;
  animation-fill-mode: forwards;
}

.user_display_show {
  animation: fadeIn 0.5s;
  animation-fill-mode: forwards;
  visibility: visible !important;
  opacity: 1;
}

.user_display_hidden {
  animation: fadeOut 0.5s;
  animation-fill-mode: forwards;
  visibility: hidden !important;
  opacity: 0;
}

.icon_bounce_animate {
  animation: icon_animate_bounce 0.3s;

}

.user_display {
  display: inline-block;
  position: absolute;
  visibility: hidden;
  opacity: 0;
  filter: Alpha(opacity=0);
  border: #f2f2f2 solid 2px;
  background: white;
  border-radius: 5%;
  top: 100%;
}

.user_display .content {
  margin: 10%;
}

.cursor_pointer {
  cursor: pointer;
}

.select_button {
  display: inline-block;
  height: 2em;
  color: #B3B3B3;
}

.select_button:hover {
  color: white;
  border-bottom: 3px solid #5FB878;
}

vertical_align_center {
  height: calc(100%);
  line-height: calc(100%);
}

.backend {
  color: #B3B3B3;
}

.backend:hover {
  color: white;
}


</style>