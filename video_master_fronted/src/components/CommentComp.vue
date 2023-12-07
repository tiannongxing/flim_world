<script setup>

import dayjs from 'dayjs';
import {ref} from 'vue';
import relativeTime from 'dayjs/plugin/relativeTime';
import { LikeFilled, LikeOutlined, DislikeFilled, DislikeOutlined } from '@ant-design/icons-vue';
import CommentReply from "./CommentReply.vue";

dayjs.extend(relativeTime);
const likes = ref(0);
const dislikes = ref(0);
const action = ref();
const neededReply = ref(false)
const like = () => {
  likes.value = 1;
  dislikes.value = 0;
  action.value = 'liked';
};
const dislike = () => {
  likes.value = 0;
  dislikes.value = 1;
  action.value = 'disliked';
};

const commentReply = () =>{
  neededReply.value = true
}

const commentReplyClose = () =>{
  neededReply.value = false
}
</script>

<template>
  <!-- 评论区最多只有一层嵌套，该组件作为一个评论单元出现 -->
  <div class="container container_extend">
    <a-comment>
      <template #actions>
      <span key="comment-basic-like">
        <a-tooltip title="Like">
          <template v-if="action === 'liked'">
            <LikeFilled @click="like"/>
          </template>
          <template v-else>
            <LikeOutlined @click="like"/>
          </template>
        </a-tooltip>
        <span style="padding-left: 8px; cursor: auto">
          {{ likes }}
        </span>
      </span>
        <span key="comment-basic-dislike">
        <a-tooltip title="Dislike">
          <template v-if="action === 'disliked'">
            <DislikeFilled @click="dislike"/>
          </template>
          <template v-else>
            <DislikeOutlined @click="dislike"/>
          </template>
        </a-tooltip>
        <span style="padding-left: 8px; cursor: auto">
          {{ dislikes }}
        </span>
      </span>
        <span key="comment-basic-reply-to" @click="commentReply">回复</span>
      </template>
      <template #author><a>Han Solo</a></template>
      <template #avatar>
        <a-avatar src="https://joeschmoe.io/api/v1/random" alt="Han Solo"/>
      </template>
      <template #content>
        <p>
          We supply a series of design principles, practical patterns and high quality design
          resources (Sketch and Axure), to help people create their product prototypes beautifully and
          efficiently.
        </p>
      </template>
      <template #datetime>
        <a-tooltip :title="dayjs().format('YYYY-MM-DD HH:mm:ss')">
          <span>{{ dayjs().fromNow() }}</span>
        </a-tooltip>
      </template>
      <div v-if="neededReply">
        <span @click="commentReplyClose">关闭</span>
        <CommentReply></CommentReply>
      </div>
      <a-comment>
        <template #actions>
      <span key="comment-basic-like">
        <a-tooltip title="Like">
          <template v-if="action === 'liked'">
            <LikeFilled @click="like"/>
          </template>
          <template v-else>
            <LikeOutlined @click="like"/>
          </template>
        </a-tooltip>
        <span style="padding-left: 8px; cursor: auto">
          {{ likes }}
        </span>
      </span>
          <span key="comment-basic-dislike">
        <a-tooltip title="Dislike">
          <template v-if="action === 'disliked'">
            <DislikeFilled @click="dislike"/>
          </template>
          <template v-else>
            <DislikeOutlined @click="dislike"/>
          </template>
        </a-tooltip>
        <span style="padding-left: 8px; cursor: auto">
          {{ dislikes }}
        </span>
      </span>
        </template>
        <template #author><a>Han Solo</a></template>
        <template #avatar>
          <a-avatar src="https://joeschmoe.io/api/v1/random" alt="Han Solo"/>
        </template>
        <template #content>
          <p>
            We supply a series of design principles, practical patterns and high quality design
            resources (Sketch and Axure), to help people create their product prototypes beautifully and
            efficiently.
          </p>
        </template>
        <template #datetime>
          <a-tooltip :title="dayjs().format('YYYY-MM-DD HH:mm:ss')">
            <span>{{ dayjs().fromNow() }}</span>
          </a-tooltip>
        </template>
      </a-comment>
    </a-comment>


  </div>
</template>

<style scoped>

</style>