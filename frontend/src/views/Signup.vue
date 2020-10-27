<template>
    <v-card>
        <v-container>
            <v-card-title class="headline">Sign Up</v-card-title>
            <!-- <div class="form-group">
                <label for="email">이메일</label>
                <input
                    type="text"
                    class="form-control"
                    id="email"
                    v-model="user.email"
                    placeholder="이메일"
                />
            </div>
            <div class="form-group">
                <label for="name">이름</label>
                <input
                    type="text"
                    class="form-control"
                    id="name"
                    v-model="user.name"
                    placeholder="이름"
                />
            </div>
            <div class="form-group">
                <label for="password">비밀번호</label>
                <input
                    type="password"
                    class="form-control"
                    id="password"
                    v-model="user.password"
                    placeholder="비밀번호"
                />
            </div>
            <div class="form-group">
                <label for="password-confirm">비밀번호 확인</label>
                <input
                    type="password"
                    class="form-control"
                    id="password-confirm"
                    v-model="user.passwordConfirm"
                    placeholder="비밀번호 확인"
                />
            </div> -->
            <v-text-field
                label="이메일"
                v-model="user.email"
            ></v-text-field>
            <v-text-field
                label="이름"
                v-model="user.name"
            ></v-text-field>
            <v-text-field
                label="비밀번호"
                v-model="user.password"
                type="password"
            ></v-text-field>
            <v-text-field
                label="비밀번호 확인"
                v-model="user.passwordConfirm"
                type="password"
            ></v-text-field>

            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn text @click="register" min-width="100px">
                    <v-icon>person_add</v-icon>
                    SignUp
                </v-btn>
            </v-card-actions>
        </v-container>
    </v-card>
</template>

<script>
import {signup} from "../api/user.js";

export default {
    data() {
        return {
            user: {
                email: "",
                name: "",
                password: "",
                passwordConfirm: ""
            }
        };
    },
    methods: {
        register() {
            var vm = this;

            if (this.user.password === this.user.passwordConfirm) {
                signup(
                    this.user.email,
                    this.user.name,
                    this.user.password,
                    function () {
                        alert("회원가입이 완료되었습니다.");
                        vm.$router.push("/");
                    },
                    function (error) {
                        console.error(error);
                    }
                );
            } else {
                alert("비밀번호가 일치하지 않습니다.");
            }
        }
    }
};
</script>

<style></style>
