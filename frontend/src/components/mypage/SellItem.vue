<template>
    <v-card>
        <v-row id="container2">
            <v-col cols="2" id="item-info-container">
                <!-- <v-img
                  :src="imgPath(item.id)"
                  :lazy-src="`https://picsum.photos/10/6?random`"
                  aspect-ratio="1"
                  @click="onClickItem()"
                >
                </v-img>
                <p style="margin: 0">{{ item.name }}</p> -->
                <v-list-item>
                    <v-list-item-avatar size="100px">
                        <v-img :src="imgPath(item.itemId)"
                               :lazy-src="`https://picsum.photos/10/6?random`"></v-img>
                    </v-list-item-avatar>
                </v-list-item>
                <p style="margin:0; font-weight: bold;">{{ item.name }}</p>
                <p style="margin:0; font-weight: bold;">{{ item.price }} cash</p>
            </v-col>
            <v-col cols="10" id="progress-container">
                <p id="cancel-message" v-if="state == 5">취소된 거래입니다.</p>
                <p id="waiting-message" v-if="state == -1">거래 대기 중</p>
                <v-stepper alt-labels v-model="state">
                    <v-stepper-header>
                        <v-stepper-step step="1" :complete="state >= 1">
                            입금 대기
                        </v-stepper-step>
                        <v-divider></v-divider>
                        <v-stepper-step step="2" :complete="state >= 2">
                            입금 완료
                        </v-stepper-step>
                        <v-divider></v-divider>
                        <v-stepper-step step="3" :complete="state >= 3">
                            배송 시작
                        </v-stepper-step>
                        <v-divider></v-divider>
                        <v-stepper-step step="4" :complete="state >= 4">
                            거래 완료
                        </v-stepper-step>
                    </v-stepper-header>
                </v-stepper>
                <div id="btn-container">
                    <p>{{ purchaseInfo.address }}</p>
                    <v-btn color="success" :disabled="state != 2 && !isCharging" @click="send(index)"
                    >{{ isCharging ? "처리중입니다" : "배송시작" }}
                    </v-btn>
                </div>
            </v-col>
        </v-row>
    </v-card>
</template>
<script>
import * as purchaseService from "@/api/purchase.js";
import * as ratingService from "@/api/rating.js";
import * as walletService from "@/api/wallet";

export default {
    props: ["item"],
    data() {
        return {
            userId: this.$store.state.user.id,
            isCharging: false,
            state: -1,
            purchaseInfo: {},
            rating: {
                id: 0,
                purchasesId: 0,
                evaluator: 0,
                getter: 0,
                score: 0,
                privateKey: 0,
            },
        };
    },
    created() {
        // this.updateState();
        console.log(this.item)
        this.fetchPurchaseInfo();
    },
    mounted: function () {
    },
    methods: {
        updateState() {
            switch (this.purchaseInfo.state) {
                case "I":
                    this.state = 1;
                    break;
                case "P":
                    this.state = 2;
                    break;
                case "S":
                    this.state = 3;
                    break;
                case "C":
                    this.state = 4;
                    break;
                case "X":
                    this.state = 5;
                    break;
            }
        },
        imgPath(id) {
            return process.env.VUE_APP_BACKEND + "api/items/images/" + this.item.id + "_1";
        },
        fetchPurchaseInfo() {
            purchaseService.getPurchaseByItemId(
                this.item.id,
                res => {
                    for (var data of res.data) {
                        if (data.state != "X") {
                            this.purchaseInfo = data
                            console.dir(this.purchaseInfo)
                            this.updateState()
                            break;
                        }
                    }
                    // this.purchaseInfo = res.data[0]
                },
                err => {
                    alert(err)
                }
            )
        },
        onClickItem() {
            this.$router.push("../item/detail/" + this.item.id);
        },
        reload() {
            this.imageModal = false;
            this.$router.go(this.$router.currentRoute);
        },
        send(index) {
            const privateKey = prompt("배송하시려면 개인키를 입력하세요.");
            console.log(this.userId);
            const vm = this;
            vm.isCharging = true;
            walletService.isValidPrivateKey(this.userId, privateKey, (res) => {
                if (res) {
                    purchaseService.send(
                        vm.purchaseInfo.purchaseId,
                        privateKey,
                        (res) => {
                            alert("성공!");
                            vm.isCharging = false;
                            vm.reload();
                        },
                        (error) => {
                            alert("실패!");
                            vm.isCharging = false;
                        }
                    );
                } else {
                    alert("개인키 인증에 실패하였습니다.");
                    this.isCashCharging = false;
                }
            });
        },
    },
};
</script>

<style>
#container2 {
    padding: 0 10px;
}

#item-info-container {
    align-self: center;
}

#progress-container {
    align-self: center;
}

#cancel-message {
    color: red;
    font-size: 17px;
    text-align-last: left;
}

#waiting-message {
    color: blue;
    font-size: 17px;
    text-align-last: left;
}

#btn-container {
    float: right;
}
</style>
