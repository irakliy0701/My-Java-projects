import {createStore} from "vuex";
export default createStore({
    state:{
        myPost: {
            title: '',
            body: ''
        },
        url: "http://localhost:7070/posts"
    },
    mutations: {
        setPost(state, post){
            state.myPost = post
        },
        setPostTitle(state, title){
            state.myPost.title = title
        },
        setPostBody(state, body){
            state.myPost.body = body
        }
    }
})
