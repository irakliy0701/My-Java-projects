<template>
  <div>
    <br>
    <h2>Редактировать пост:</h2>
    <br><br>
    <div><strong>Название:</strong></div>
    <my-input
        v-focus
        :model-value="myPost.title"
        @update:model-value="setPostTitle"
        type="text"
        placeholder="Новое название..."/>
    <br><br>
    <div><strong>Описание:</strong></div>
    <my-input
        :model-value="myPost.body"
        @update:model-value="setPostBody"
        type="text"
        placeholder="Новое описание..."/>
    <my-button
        class="btn"
        style="align-self: flex-end; margin-top: 15px"
        @click="updatePost">Редактировать
    </my-button>
  </div>
</template>

<script>

import axios from "axios";
import {mapMutations, mapState} from "vuex";

export default {
  methods: {
    ...mapMutations({
      setPostTitle: 'setPostTitle',
      setPostBody: 'setPostBody'
    }),
    async updatePost(){
      await axios
          .patch(this.url+ "/updatePost", {
            id: this.$route.params.id, // this.post.id
            title: this.myPost.title,
            body: this.myPost.body
          }).catch(error => console.log(error));

      // this.$router.go(0);
      this.$router.push('/myRestApi')
    }
  },
  computed:{
    ...mapState({
      myPost: state => state.myPost,
      url: state => state.url
    })
  }
}
</script>

<style scoped>

</style>