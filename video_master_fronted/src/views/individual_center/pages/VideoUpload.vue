<script setup>
import {reactive, ref} from "vue";
import {InboxOutlined, PlusOutlined} from '@ant-design/icons-vue'
import {message} from "ant-design-vue";
import axios from "axios";

const uploadData = reactive({
  video: [],
  video_data: {},
  image: []
})

const imgBeforeUpload = (image) => {
  uploadData.image = [...(uploadData.image|| []),image]
  return false
}

const videoBeforeUpload = (video) => {
  uploadData.video = [...(uploadData.video|| []),video]
  return false
}

// 图片上传页面原
function getBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });
}

const previewVisible = ref(false);
const previewImage = ref('');
const previewTitle = ref('');
const handleCancel = () => {
  previewVisible.value = false;
  previewTitle.value = '';
};
const handlePreview = async file => {
  if (!file.url && !file.preview) {
    file.preview = await getBase64(file.originFileObj);
  }
  previewImage.value = file.url || file.preview;
  previewVisible.value = true;
  previewTitle.value = file.name || file.url.substring(file.url.lastIndexOf('/') + 1);
};



const submit = () => {
  // 以表单格式发送数据
  let formData = new FormData();
  // formData.append('video',uploadData.video[0])


  uploadData.image.forEach( item => {
    const img = item.originFileObj
    const suffix = img.name.split('.').pop()
    const blob = new Blob([img], {type: img.type})
    formData.append('image', blob, `image_${Date.now()}.${suffix}`)
  })

  uploadData.video.forEach( item => {
    const video = item.originFileObj
    const suffix = video.name.split('.').pop()
    const blob = new Blob([video], {type: video.type})
    formData.append('video',blob,`video_${Date.now()}.${suffix}`)
  })

  // 使用axios请求发送
  axios.post("/video-master/resource/videoUpload", formData
      ).then(res => {
        if (res.data === true){
          message.success("上传成功")
        }else{
          message.warning('上传失败，请重试')
        }

  }).catch(err => {
    message.warning(err,2)
  })
}
</script>

<template>
  <div>
    <div>
      <a-typography-text>视频图片</a-typography-text>
      <a-upload
          v-model:file-list="uploadData.image"
          :max-count="1"
          action="/video-master/resource/imgTest"
          list-type="picture-card"
          @preview="handlePreview"
          name="image"
          :before-upload="imgBeforeUpload"
          :accept="'image/*'"
      >
        <div v-if="uploadData.image.length < 2">
          <plus-outlined/>
          <div style="margin-top: 8px">上传图片</div>
        </div>
      </a-upload>
      <a-modal :open="previewVisible" :title="previewTitle" :footer="null" @cancel="handleCancel">
        <img alt="example" style="width: 100%" :src="previewImage"/>
      </a-modal>
    </div>
    <div>
      <a-typography-text>上传视频</a-typography-text>
      <a-upload-dragger
          v-model:fileList="uploadData.video"
          name="video"
          :max-count="1"
          :multiple="false"
          action="/video-master/resource/videoTest"
          :before-upload="videoBeforeUpload"
          :accept="'video/*'"
      >
        <p class="ant-upload-drag-icon">
          <inbox-outlined></inbox-outlined>
        </p>
        <p class="ant-upload-text">单击或拖动文件到此区域进行上传
        </p>
      </a-upload-dragger>
    </div>
    <div>
      <a-button type="primary" @click="submit">提交</a-button>
    </div>
  </div>
</template>

<style scoped>
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>