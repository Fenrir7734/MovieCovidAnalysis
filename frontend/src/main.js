import { createApp } from 'vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import App from './App.vue'
import router from "@/router";
import store from "@/store";
import jwt_decode from "jwt-decode";


createApp(App)
    .use(router)
    .use(store)
    .mixin({
        computed: {
            currentUser() {
                return this.$store.state.auth.user
            },
            currentUserRole() {
                return this.currentUser
                    ? jwt_decode(this.currentUser.data.accessToken).roles
                    : ""
            },
            currentUserUsername() {
                return this.currentUser
                    ? jwt_decode(this.currentUser.data.accessToken).sub
                    : ""
            },
            loggedIn() {
                return this.$store.state.auth.status.loggedIn;
            },
            isUser() {
                return this.currentUser
                    ? this.currentUserRole === "ROLE_USER" || this.currentUserRole === "ROLE_ADMIN"
                    : false
            },
            isAdmin() {
                return this.currentUser
                    ? this.currentUserRole === "ROLE_ADMIN"
                    : false
            }
        }
    })
    .mount('#app')
