import axios from "axios";

const url = "http://localhost:7070/posts"
export default {
    data(){
        return{
            posts: [],
            dialogVisible: false,
            isPostLoading: false,
            selectedSort: '',
            searchQuery: '',
            page: 1,
            limit: 10,
            totalPages: 0,
            sortedOptions: [
                {value: 'title', name: 'По названию'},
                {value: 'body', name: 'По содержимому'},
            ]
        }
    },
    methods:{
        async createPost(post){
            await axios
                .post(url + "/createPost", {
                    title: post.title,
                    body: post.body
                }).catch(error => console.log(error));
            this.dialogVisible = false;
        },
        async removePost(post){
            await axios.delete(url + "/delete/" + post.id)
                .catch(error => console.log(error));
        },
        showDialog(){
            this.dialogVisible = true
        },
        async fetchLimitPosts(){
            try {
                this.isPostLoading = true;
                // Через Fetch Api:
                const request = new Request(url + "/getLimitPosts?limit=" + this.limit + "&page=" + this.page)
                const resp = await fetch(request)
                if (!resp.ok){
                    throw new Error(`HTTP error: ${resp.status}`);
                }
                const data = await resp.json();
                console.log(data)
                this.posts = data

                await this.findTotalPages()
            } catch (e){
                alert("Ошибка при запросе на сервер")
            } finally {
                this.isPostLoading = false;
            }
        },
        async findTotalPages(){
            try{
                const request = new Request(url)
                const response = await fetch(request)
                if (!response.ok){
                    throw new Error(`HTTP error: ${response.status}`);
                }
                const data = await response.json();
                this.totalPages = Math.ceil(data.length / this.limit)
                console.log(this.totalPages)
            } catch (e){
                alert("Ошибка при запросе на сервер")
            }
        }
    },
    computed:{
        sortedPosts(){
            return [...this.posts].sort((post1, post2) =>
                post1[this.selectedSort]?.localeCompare(post2[this.selectedSort])
            )
        },
        sortedAndSearchedPosts(){
            return this.sortedPosts.filter(post => post.title.toLowerCase().includes(
                this.searchQuery.toLowerCase()
            ))
        }
    }
}