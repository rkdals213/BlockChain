<template>
    <v-card tile v-resize="onResize" style="z-index:99999">
        <v-app-bar color="transparent" app hide-on-scroll flat>
            <v-toolbar-title>
                <router-link class="navbar-brand font" style="font-size:18px; color:black; font-weight:bold;" to="/" id="title" >중코마켓</router-link>
            </v-toolbar-title>

            <v-spacer></v-spacer>
            <v-toolbar-title v-if="windowSize.x > 1000">
                <router-link id="link-to-shop" style="font-size:18px; color:black; font-weight:bold;" to="/shop">상점</router-link>
                <router-link id="link-to-sell" style="font-size:18px; color:black; font-weight:bold;" to="/sell">판매하기</router-link>
<!--                <router-link id="link-to-mypage" style="font-size:18px; color:black; font-weight:bold;" to="/mypage/wallet_create" v-if="!$store.state.user.walletAddress">마이페이지</router-link>-->
                <!-- <router-link id="link-to-myshop" to="/mypage/wallet_info" v-if="$store.state.user.walletAddress">내상점</router-link> -->
                <router-link id="link-to-myshop" style="font-size:18px; color:black; font-weight:bold;" to="/mypage/profile">프로필</router-link>
                <router-link id="link-to-logout" style="font-size:18px; color:black; font-weight:bold;" to="/logout" v-if="$store.state.isSigned">로그아웃</router-link>
                <a id="link-to-signup"
                   v-if="!$store.state.isSigned"
                   @click.stop="signup = true">회원가입</a>
                <a id="link-to-login"
                   v-if="!$store.state.isSigned"
                   @click.stop="signin = true">로그인</a>
            </v-toolbar-title>
            <v-flex v-if="windowSize.x <= 1000">
                <v-menu transition="scroll-x-transition">
                    <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            color="black"
                            dark
                            icon
                            v-bind="attrs"
                            v-on="on"
                        >
                            <v-icon>mdi-format-list-bulleted-square</v-icon>
                        </v-btn>
                    </template>
                    <v-list>
                        <v-list-item>
                            <v-list-item-title>
                                <router-link id="link-to-shop-2" to="/shop">상점</router-link>
                            </v-list-item-title>
                        </v-list-item>
                        <v-list-item>
                            <v-list-item-title>
                                <router-link id="link-to-sell-2" to="/sell">판매하기</router-link>
                            </v-list-item-title>
                        </v-list-item>
<!--                        <v-list-item v-if="!$store.state.user.walletAddress">-->
<!--                            <v-list-item-title>-->
<!--                                <router-link id="link-to-mypage-2" to="/mypage/wallet_create">마이페이지</router-link>-->
<!--                            </v-list-item-title>-->
<!--                        </v-list-item>-->
                        <!-- <v-list-item v-if="$store.state.user.walletAddress">
                            <v-list-item-title>
                                <router-link id="link-to-myshop-2" to="/mypage/wallet_info">내상점</router-link>
                            </v-list-item-title>
                        </v-list-item> -->
                        <v-list-item>
                            <v-list-item-title>
                                <router-link id="link-to-myshop-2" to="/mypage/profile">프로필</router-link>
                            </v-list-item-title>
                        </v-list-item>
                        <v-list-item v-if="$store.state.isSigned">
                            <v-list-item-title>
                                <router-link id="link-to-logout-2" to="/logout">로그아웃</router-link>
                            </v-list-item-title>
                        </v-list-item>
                        <v-list-item  v-if="!$store.state.isSigned">
                            <v-list-item-title>
                                <a id="link-to-signup-2"
                                   @click.stop="signup = true">회원가입</a>
                            </v-list-item-title>
                        </v-list-item>
                        <v-list-item v-if="!$store.state.isSigned">
                            <v-list-item-title>
                                <a id="link-to-login-2"
                                   @click.stop="signin = true">로그인</a>
                            </v-list-item-title>
                        </v-list-item>
                    </v-list>
                </v-menu>
            </v-flex>


            <v-dialog v-model="signup" max-width="500" min-width="300">
                <Signup></Signup>
            </v-dialog>
            <v-dialog v-model="signin" max-width="500" min-width="300">
                <Login></Login>
            </v-dialog>

        </v-app-bar>
    </v-card>
</template>

<script>
import Login from "@/views/Login"
import Signup from "@/views/Signup";

export default {
    data() {
        return {
            signin: false,
            signup: false,
            windowSize: {
                x: 0,
                y: 0
            }
        }
    },
    components: {
        Login,
        Signup
    },
    created() {
        console.dir("로그인?");
        console.dir(this.$store.state);
        this.onResize();
    },
    methods: {
        onResize() {
            this.windowSize = {x: window.innerWidth, y: window.innerHeight}
        }
    }
};
</script>

<style>

.font{
    font-size:18px;
    color:black; 
    font-weight:bold;
}

#title {
    color: black;
    margin-left: 60px;
}

#link-to-shop, #link-to-sell, #link-to-mypage, #link-to-myshop, #link-to-logout, #link-to-signup, #link-to-login {
    color: black;
    font-weight: bold;
    font-size:18px;
    margin: 0 10px;
}

#link-to-shop-2, #link-to-sell-2, #link-to-mypage-2, #link-to-myshop-2, #link-to-logout-2, #link-to-signup-2, #link-to-login-2 {
    color: black;
    margin: 0 10px;
}
</style>
