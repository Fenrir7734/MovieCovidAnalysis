import { createWebHistory, createRouter } from 'vue-router'
import SignIn from "@/components/SignIn";
import SignUp from "@/components/SignUp";
import store from "@/store";
import AuthenticationService from "@/services/AuthenticationService";
import ResponseCard from "@/components/ResponseCard";
import DashBoard from "@/components/DashBoard";

const routes = [
    {
        path: "/signin",
        name: "signin",
        component: SignIn
    },
    {
        path: "/signup",
        name: "signup",
        component: SignUp
    },
    {
        path: "/success",
        name: "success",
        component: ResponseCard,
        props: true
    },
    {
        path: "/",
        alias: "/dashboard",
        name: "dashboard",
        component: DashBoard
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

router.beforeEach((to, from, next) => {
    const publicPages = ['signin', 'signup', 'success', 'logout']
    const authRequired = !publicPages.includes(to.name)
    const loggedIn = store.state.auth.status.loggedIn

    if (authRequired && !loggedIn) {
        return next('/signin')
    } else if (authRequired) {
        AuthenticationService.validate()
            .then(() => {
                next()
            })
            .catch(() => {
                store.dispatch('auth/logout')
                    .then(() => {
                        next("/signin")
                    })
                    .catch(error => {
                        console.log(error)
                    })
            })
    } else {
      next()
    }
})

export default router;
