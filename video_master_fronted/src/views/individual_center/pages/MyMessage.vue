<script setup>
import {computed, ref} from "vue";
import dayjs from "dayjs";
import MapContainer from "../components/MapContainer.vue";
import store from "../store/store.js";
import {message} from "ant-design-vue";
import {LoadingOutlined, PlusOutlined} from "@ant-design/icons-vue";
import myUpload from 'vue-image-crop-upload';

let userObj = ref({})
let ping = ref(new Date().toString())
let loginUser = computed(() => {
  if (store.state.userState.user) {
    userObj.value = JSON.parse(JSON.stringify(store.state.userState.user))
    return userObj.value
  } else {
    message.error("你没登录就进来了呢，真厉害！！")
  }
})

let avatarPath = computed(() => {
  if (loginUser) {
    return '/users/' + loginUser.value.img
  } else {

  }
})

const gender = ref("man")
const dateFormat = 'YYYY-MM-DD';
let birth = ref(dayjs(Date.toLocaleString(), dateFormat))
const options = [
  {
    value: 'zhejiang',
    label: 'Zhejiang',
    children: [
      {
        value: 'hangzhou',
        label: 'Hangzhou',
        children: [
          {
            value: 'xihu',
            label: 'West Lake',
          },
        ],
      },
    ],
  },
  {
    value: 'jiangsu',
    label: 'Jiangsu',
    children: [
      {
        value: 'nanjing',
        label: 'Nanjing',
        children: [
          {
            value: 'zhonghuamen',
            label: 'Zhong Hua Men',
          },
        ],
      },
    ],
  },
];
const value = ref([]);

const open = ref(false);
const showModal = () => {
  open.value = true
};
const handleOk = e => {
  ping.value = new Date().toString()
};

const putLocationHandler = (e) => {
  userObj.value.location = e.selectedAddress
  open.value = false
}

// 上传图片相关
let show = ref(false)
let toggleShow = ()=>{
  show.value = !show.value
}
let params = ref({
  token: localStorage.getItem("token"),
  name: 'avatar'
})

let headers= ref({
  smail: '*_~'
})
let imgDataUrl= ref('') // 创建的base64格式的图片
/**
 * crop success
 *
 * [param] imgDataUrl
 * [param] field
 */
let cropSuccess = (imgDataUrl, field)=>{
  console.log('-------- crop success --------');
  this.imgDataUrl = imgDataUrl;
}
/**
 * upload success
 *
 * [param] jsonData   服务器返回数据，已进行json转码
 * [param] field
 */
let cropUploadSuccess = (jsonData, field)=>{
  console.log('-------- upload success --------');
  console.log(jsonData);
  console.log('field: ' + field);
}
/**
 * upload fail
 *
 * [param] status    server api return error status, like 500
 * [param] field
 */
let cropUploadFail = (status, field)=>{
  console.log('-------- upload fail --------');
  console.log(status);
  console.log('field: ' + field);
}
</script>

<template>
  <div class="message_container">
    <div class="small_tip small_tip_color"></div>
    <a-typography-text class="small_tip_font_color">我的信息</a-typography-text>
    <a-divider></a-divider>
    <div class="user_form">
      <a-row :gutter="[8,16]">
        <a-col :span="4">
          <a-typography-text>头像</a-typography-text>
        </a-col>
        <a-col :span="20" style="position:relative;">
          <a-avatar :size="{ xs: 16, sm: 24, md: 32, lg: 52, xl: 64, xxl: 80 }" :src="avatarPath">
          </a-avatar>
          <!-- 图片上传部分 -->
            <a class="btn" style="margin-left: 2em" @click="toggleShow">设置头像</a>
            <my-upload field="img"
                       @crop-success="cropSuccess"
                       @crop-upload-success="cropUploadSuccess"
                       @crop-upload-fail="cropUploadFail"
                       v-model="show"
                       :width="300"
                       :height="300"
                       url="/upload"
                       :params="params"
                       :headers="headers"
                       img-format="png"></my-upload>
            <img :src="imgDataUrl">
        </a-col>
        <a-col :span="4">
          <a-typography-text>昵称</a-typography-text>
        </a-col>
        <a-col :span="20">
          <a-input size="small" v-model:value="userObj.nickname"></a-input>
        </a-col>

        <a-col :span="4">
          <a-typography-text>用户名</a-typography-text>
        </a-col>
        <a-col :span="20">
          <a-typography-text>{{ userObj.username }}</a-typography-text>
        </a-col>

        <a-col :span="4">性别</a-col>
        <a-col :span="20">
          <a-radio-group v-model:value="userObj.sex" button-style="solid" @change="console.log(gender)" size="small">
            <a-radio-button value="man">男</a-radio-button>
            <a-radio-button value="male">女</a-radio-button>
            <a-radio-button value="other">其他</a-radio-button>
          </a-radio-group>
        </a-col>

        <a-col :span="4">出生日期</a-col>
        <a-col :span="20">
          <a-date-picker size="small" v-model:value="userObj.birthday" :value-format="dateFormat" :format="dateFormat"
                         placeholder="选择日期"/>
        </a-col>


        <a-col :span="4">绑定手机</a-col>
        <a-col :span="20">
          <a-typography-text style="min-width: 16em">{{
              userObj.phone === null ? "未绑定手机" : userObj.phone
            }}
          </a-typography-text>
          <a-button size="small" style="margin-left: 3em">{{
              userObj.phone === null ? "添加手机" : "修改手机"
            }}
          </a-button>
        </a-col>

        <a-col :span="4">绑定邮箱</a-col>
        <a-col :span="20">
          <a-typography-text style="min-width: 16em">{{
              userObj.email === null ? "未绑定邮箱" : userObj.phone
            }}
          </a-typography-text>
          <a-button size="small" style="margin-left: 3em">{{
              userObj.phone === null ? "添加邮箱" : "修改邮箱"
            }}
          </a-button>
        </a-col>

        <a-col :span="4">位置:</a-col>
        <a-col :span="18">
          <!-- todo 省市县联动，需要从后台获取数据或者调用接口 -->
          <!--          <a-cascader size="small" v-model:value="value" :options="options" style="width: 40%"-->
          <!--                      placeholder="请选择省市县"/>-->
          <a-input style="height: 24px;width: 100%;" placeholder="填写位置信息"
                   v-model:value="userObj.location"></a-input>
        </a-col>
        <a-col :span="2">
          <a-button type="link" size="small" @click="showModal">定位</a-button>
        </a-col>
      </a-row>
      <a-modal
          v-model:open="open"
          title="定位"
          width="100%"
          wrap-class-name="full-modal"
          @ok="handleOk"
          ok-text="确认"
          cancel-text="取消"
      >
        <map-container :ping="ping" @putLocation="putLocationHandler"></map-container>
      </a-modal>
    </div>

    <a-button type="primary" style="margin-top: 1em">
      修改资料
    </a-button>
  </div>


</template>

<style scoped>
.message_container {
  padding: 2em;
}

.user_form {
  width: 33%;
}

.upload-wrapper{
  display: inline-block;
  position: absolute;
  left: 10em;
  top: 50%;
  transform: translateY(-50%);
}
</style>
<style lang="less">
.full-modal {
  .ant-modal {
    max-width: 100%;
    top: 0;
    padding-bottom: 0;
    margin: 0;
  }

  .ant-modal-content {
    display: flex;
    flex-direction: column;
    height: calc(100vh);
  }

  .ant-modal-body {
    flex: 1;
  }
}
.avatar-uploader > .ant-upload {
  width: inherit;
  height: inherit;
}
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>