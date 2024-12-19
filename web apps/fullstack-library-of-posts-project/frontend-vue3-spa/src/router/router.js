import Main from "@/pages/Main.vue";
import {createRouter, createWebHistory} from "vue-router";
import About from "@/pages/About.vue";
import PostIdPage from "@/pages/PostIdPage.vue";
import PostPageMyRestApi from "@/pages/PostPageMyRestApi.vue";

const routes = [
    {
        path: '/',
        component: Main
    },
    {
        path: '/about',
        component: About
    },
    {
        path: '/posts/:id',
        component: PostIdPage
    },
    {
        path: '/myRestApi',
        component: PostPageMyRestApi
    }
]

const router = createRouter({
    routes, // сокращено для routes: routes
    history: createWebHistory(process.env.BASE_URL) // тк создали history mode -> необходимо создать Web историю
})

export default router
