<template>
  <div>
    <h1>Страница с постами</h1>
    <my-input
        v-focus
        v-model="searchQuery"
        placeholder="Поиск по названию постов..."
    />
    <div class="app__btns">
      <my-button
          @click="showDialog"
      >
        Создать пост
      </my-button>
      <my-select
          v-model="selectedSort"
          :options="sortedOptions"
      />
    </div>

    <my-dialog v-model:show="dialogVisible">
      <post-form
          @create="createPost"
      />
    </my-dialog>

    <post-list
        :posts="sortedAndSearchedPosts"
        @remove="removePost"
        v-if="!isPostLoading"
    />
    <div v-else >Идет загрузка постов...</div>

    <my-page-changer :total-pages="totalPages" v-model:page="page">
    </my-page-changer>
  </div>
</template>

<script>
import PostForm from "@/components/PostForm.vue";
import PostList from "@/components/PostList.vue";
import MyDialog from "@/components/UI/MyDialog.vue";
import MyButton from "@/components/UI/MyButton.vue";
import axios from "axios";
import MySelect from "@/components/UI/MySelect.vue";
import MyInput from "@/components/UI/MyInput.vue";
import MyPageChanger from "@/components/UI/MyPageChanger.vue";
import posPageMixin from "@/mixins/postPageMixin"
export default {
  components:{
    MyPageChanger,
    MyInput,
    MySelect,
    MyButton,
    MyDialog,
    PostList, PostForm
  },
  mixins: [posPageMixin],
  mounted(){
    this.fetchLimitPosts();
  },
  watch:{
    page(){
      this.fetchLimitPosts()
    }
  },
}
</script>

<style scoped>
.app__btns{
  margin: 15px 0;
  display: flex;
  justify-content: space-between;
}
</style>