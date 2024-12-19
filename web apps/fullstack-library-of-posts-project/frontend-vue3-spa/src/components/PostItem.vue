<template>
  <div class="post">
    <div>
      <div>{{post.id}}</div>
      <div><strong>Название:</strong> {{post.title}}</div>
      <div><strong>Описание:</strong> {{post.body}}</div>
    </div>
    <div class="post__btns">
      <my-button
          @click="gotoPostPage"
      >Редактировать
      </my-button>
      <my-button
          @click="removePost"
      >Удалить
      </my-button>
    </div>
  </div>
</template>

<script>
import {mapMutations} from "vuex";

export default {
  props:{
    post:{
      type: Object,
      required: true,
    }
  },
  methods:{
    ...mapMutations({
      setPost: 'setPost'
    }),
    removePost(){
      this.$emit('remove', this.post);
      this.$router.go(0);
    },
    gotoPostPage(){
      this.setPost(this.post)
      this.$router.push(`/posts/${this.post.id}`)
    }
  }
}
</script>

<style scoped>
.post{
  padding: 15px;
  border: 2px solid teal;
  margin-top: 15px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.post__btns {
  display: flex;
}
</style>