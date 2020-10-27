<template>
    <nav class="navbar navbar-expand-lg fixed-top navbar-dark" style="height: 50px; background-color: rgb(36,37,38);">
        <div class="container">
            <router-link class="navbar-brand" to="/">중코마켓</router-link>
            <div
                class="navbar-collapse offcanvas-collapse"
                id="navbarsExampleDefault"
            >
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <router-link class="nav-link" to="/shop">상점</router-link>
                    </li>
                    <li class="nav-item">
                    <router-link class="nav-link" to="/sell">판매하기</router-link>
                    </li>
                    <li class="nav-item" v-if="$store.state.isSigned">
                        <router-link
                            class="nav-link"
                            to="/mypage/wallet_create"
                            v-if="!$store.state.user.walletAddress"
                        >마이페이지
                        </router-link
                        >
                        <router-link
                            class="nav-link"
                            to="/mypage/wallet_info"
                            v-if="$store.state.user.walletAddress"
                        >내상점
                        </router-link
                        >
                    </li>
                    <li class="nav-item" v-if="!$store.state.isSigned">
                        <!--            <router-link class="nav-link" to="/login">Sign In</router-link>-->
                        <v-btn
                            text
                            fab
                            slot="activator"
                            v-bind="attrs"
                            v-on="{ ...dialog, ...tooltip }"
                            min-width="100px"
                            @click.stop="signin = true"
                        >
                            <v-icon>power_settings_new</v-icon>
                            <span>Sign In</span>
                        </v-btn>
                        <v-dialog v-model="signin" max-width="500" min-width="300">
                            <Login></Login>
                        </v-dialog>
                    </li>
                    <li class="nav-item" v-if="!$store.state.isSigned">
                        <v-btn
                            text
                            fab
                            slot="activator"
                            v-bind="attrs"
                            v-on="{ ...dialog, ...tooltip }"
                            min-width="100px"
                            @click.stop="signup = true"
                        >
                            <v-icon>power_settings_new</v-icon>
                            <span>Sign Up</span>
                        </v-btn>
                        <v-dialog v-model="signup" max-width="500" min-width="300">
                            <Signup></Signup>
                        </v-dialog>
                    </li>
                    <li class="nav-item" v-if="$store.state.isSigned">
                        <router-link class="nav-link" to="/logout">로그아웃</router-link>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<script>
import Login from "@/views/Login"
import Signup from "@/views/Signup";

export default {
    data() {
        return {
            signin: false,
            signup: false,
        }
    },
    components: {
        Login,
        Signup
    },
};
</script>

<style>
#nav-icon {
    height: 30px;
    padding-right: 0.5rem;
}
</style>
