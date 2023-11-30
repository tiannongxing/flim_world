<script setup>
import {markRaw, onMounted, reactive, ref} from "vue";
import locale from "ant-design-vue/es/locale/zh_CN.js";
import {
  AppstoreAddOutlined,
  PoweroffOutlined,
  RightOutlined,
  SisternodeOutlined,
  UserOutlined
} from "@ant-design/icons-vue";
import router from "../routers/main.js";
import LoginPanelByMail from "./loginComp/LoginPanelByMail.vue";
import LoginPanelByPassword from "./loginComp/LoginPanelByPassword.vue";
import RegisterComp from "./RegisterComp.vue";

onMounted(() => {
  user_display_size.width = avatar.value.size * 9
  user_display_size.height = avatar.value.size * 12
})
const avatar = ref(null)
const user_display_size = reactive({
  width: 0,
  height: 0
})
const font_color_list = reactive({
  selected: '#1677FF',
  unselect: '#000000'
})
const title_img_click = () => {
  console.log('title_img_click')
}

const change_font_color = (event, color) => {
  event.target.style.color = color
}

let find = ref("")
let onSearch = () => {
  jumpToPage('/searchedMovie', find.value)
}
let avatar_animate = ref('')
const animate_list = ref(['avatar_magnify', 'avatar_shrink', 'user_display_show', 'user_display_hidden'])
let header_image = ref('/static/unlogin.png')
let is_login = ref(true)
let show_user_display = ref(false)
let display_user_panel = ref('')
let font_color = ref(font_color_list.unselect)
let display_sub_user_panel = ref('')
let selected = ref("index")
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
const handleOk = (modelName) => {
  console.log(modelName);
  if (1==1)
    modelOpen[modelName] = false
  // 输入的信息有误，不退出注册页面，弹出报错信息
};



let loginPanelShowList = reactive([
  markRaw(LoginPanelByPassword),
  markRaw(LoginPanelByMail),
])

let selectedLoginPanel = ref(loginPanelShowList[0])
let jumpToAdmin = ()=>{
  window.location.href = 'src/views/administrator/'
}

let jumpToIndividualCenter = ()=>{
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
                 @mouseenter="is_login===true?(display_user_panel=animate_list[2],avatar_animate=animate_list[0]):display_user_panel=animate_list[2]"
                 @mouseleave="is_login===true?(display_user_panel=animate_list[3],avatar_animate=animate_list[1]):display_user_panel=animate_list[3]"
            >
              <!-- 非登录状态下展示框-->
              <div
                  class="user_display"
                  :class="display_user_panel"
                  v-if="!is_login"
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
                   v-if="is_login"
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
                            class="align-left" type="text" size="large" block>
                    <span><PoweroffOutlined/> 退出登录</span>
                  </a-button>
                </div>
              </div>
              <!-- 头像展示框-->
              <div
                  :class="avatar_animate"
                  :style="{
                  margin: 30+'%',
                  transform: 'translateX(-30%)',
                    }">
                <a-avatar ref="avatar" :size="32">
                  <template #icon>
                    <img :src="header_image">
                  </template>
                </a-avatar>
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
      <a-modal v-model:open="modelOpen.loginModel" title="登录" ok-text="登录" @ok="handleOk('loginModel')">
        <div class="align-center">
          <a-button type="link" :disabled="selectedLoginPanel === loginPanelShowList[0]"
                    @click="selectedLoginPanel=loginPanelShowList[0]">密码登录</a-button>
          <a-typography-text>|</a-typography-text>
          <a-button type="link" :disabled="selectedLoginPanel === loginPanelShowList[1]"
                    @click="selectedLoginPanel=loginPanelShowList[1]">邮箱登录</a-button>
        </div>
        <component :is="selectedLoginPanel"></component>
      </a-modal>

      <!-- 注册模态窗口 -->
      <a-modal v-model:open="modelOpen.registerModel" title="注册" ok-text="注册" @ok="handleOk('registerModel')">
        <register-comp></register-comp>
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