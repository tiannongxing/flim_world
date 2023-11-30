<script setup>
import {ref} from "vue";
import dayjs from "dayjs";
import MapContainer from "../components/MapContainer.vue";

const gender = ref("man")
const dateFormat = 'YYYY/MM/DD';
let birth = ref(dayjs('2023/11/18', dateFormat))
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
  open.value = true;
};
const handleOk = e => {
  open.value = false;
};
</script>

<template>
  <div class="message_container">
    <div class="small_tip small_tip_color"></div>
    <a-typography-text class="small_tip_font_color">我的信息</a-typography-text>
    <a-divider></a-divider>
    <div class="user_form">
      <a-row :gutter="[8,16]">

        <a-col :span="4">
          <a-typography-text>昵称</a-typography-text>
        </a-col>
        <a-col :span="20">
          <a-input size="small"></a-input>
        </a-col>

        <a-col :span="4">
          <a-typography-text>用户名</a-typography-text>
        </a-col>
        <a-col :span="20">
          <a-typography-text>展示用户名</a-typography-text>
        </a-col>

        <a-col :span="4">性别</a-col>
        <a-col :span="20">
          <a-radio-group v-model:value="gender" button-style="solid" @change="console.log(gender)" size="small">
            <a-radio-button value="man">男</a-radio-button>
            <a-radio-button value="male">女</a-radio-button>
            <a-radio-button value="other">其他</a-radio-button>
          </a-radio-group>
        </a-col>

        <a-col :span="4">出生日期</a-col>
        <a-col :span="20">
          <a-date-picker size="small" v-model:value="birth" :format="dateFormat"/>
        </a-col>


        <a-col :span="4">绑定手机</a-col>
        <a-col :span="20">
          <a-typography-text style="min-width: 16em">显示绑定手机</a-typography-text>
          <a-button size="small" style="margin-left: 3em">修改手机号</a-button>
        </a-col>

        <a-col :span="4">绑定邮箱</a-col>
        <a-col :span="20">
          <a-typography-text style="min-width: 16em">显示绑定邮箱</a-typography-text>
          <a-button size="small" style="margin-left: 3em">修改邮箱</a-button>
        </a-col>

        <a-col :span="4">位置:</a-col>
        <a-col :span="18">
          <a-cascader size="small" v-model:value="value" :options="options" style="width: 40%"
                      placeholder="请选择省市县"/>
          <a-input style="height: 24px;width: 60%;" placeholder="详细信息..."></a-input>
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
        <map-container></map-container>
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
</style>