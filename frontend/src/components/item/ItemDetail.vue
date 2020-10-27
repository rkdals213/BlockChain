<template>
    <div>
        <v-container>
            <v-row>
                <v-flex col xl5 lg5 md5 sm12 xs12>
                    <v-carousel
                        cycle
                        hide-delimiter-background
                        show-arrows-on-hover
                        style="width: 300px; margin-left: 90px"
                    >
                        <v-carousel-item v-for="n in item.image" :key="n">
                            <v-row class="fill-height" align="center" justify="center">
                                <v-img
                                    :src="imgPath(n)"
                                    aspect-ratio="1"
                                    class="grey lighten-2"
                                ></v-img>
                            </v-row>
                        </v-carousel-item>
                    </v-carousel>
                </v-flex>
                <v-flex
                    col
                    xl5
                    lg5
                    md5
                    sm12
                    xs12
                    style="margin: 40px 0 0 30px"
                    id="font"
                >
                    <v-col>
                        <v-row>
                            <v-col style="float: left; text-align: left">
                                <div id="font" style="font-size: 19px">{{ item.name }}</div>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col style="float: right; text-align: left">
                                <div
                                    id="font"
                                    style="display: inline; font-weight: bold; font-size: 21px"
                                >
                                    {{ item.price }}
                                </div>
                                CASH
                            </v-col>
                        </v-row>
                        <v-divider style="margin-top: 0px"/>
                        <v-row>
                            <v-col cols="3" style="text-align: left">판매자</v-col>
                            <v-col
                                cols="8"
                                style="text-align: left; padding-bottom: 0px; height: 48px"
                            >
                                <p style="float: left">{{ item.seller.name }} (</p>
                                <v-img
                                    style="
                    text-align: left;
                    width: 15px;
                    float: left;
                    margin: 3px 4px 0 4px;
                  "
                                    :src="star"
                                ></v-img>
                                {{ rating }} )
                            </v-col>
                            <v-col cols="3" style="text-align: left">상품 등록일</v-col>
                            <v-col cols="8" style="text-align: left">{{ time }}</v-col>
                            <v-col cols="3" style="text-align: left">거래방법</v-col>

                            <v-col
                                v-if="item.directDeal == true"
                                cols="8"
                                style="text-align: left"
                            >직거래
                            </v-col
                            >
                            <v-col
                                v-if="item.directDeal == true"
                                cols="3"
                                style="text-align: left"
                            >직거래 장소
                            </v-col
                            >
                            <v-col
                                v-if="item.directDeal == true"
                                cols="8"
                                style="text-align: left"
                            >{{ item.dealRegion }}
                            </v-col
                            >
                            <v-col v-else cols="8" style="text-align: left">택배</v-col>
                            <v-col cols="3" style="text-align: left">상태</v-col>
                            <v-col cols="8" style="text-align: left">{{
                                    item.available ? "판매중" : "판매 종료"
                                }}
                            </v-col
                            >
                            <br/><br/><br/>

                            <v-row>
                                <v-col cols="2.1">
                                    <v-btn
                                        large
                                        color="primary"
                                        @click="Chatting()"
                                        style="width: 100%"
                                    >문의톡
                                    </v-btn
                                    >
                                </v-col>
                                <v-col cols="2.1">
                                    <v-btn large color="warning" style="width: 100%" @click="nego"
                                    >네고요청
                                    </v-btn
                                    >
                                </v-col>
                                <v-col cols="2.1">
                                    <div class="example-modal-window">
                                        <v-btn
                                            large
                                            color="error"
                                            style="width: 100%"
                                            @click="openModal"
                                        >바로구매
                                        </v-btn
                                        >
                                        <MyModal @close="closeModal" v-if="modal">
                                            <div
                                                v-if="postState == false"
                                                style="text-align: left"
                                                @click="getAddress"
                                            >
                                                <p
                                                    style="
                            margin-top: 5px;
                            font-weight: bold;
                            float: left;
                          "
                                                >
                                                    택배거래
                                                </p>
                                                <div style="float: right; margin-left: 200px">
                                                    <img
                                                        src="../../../public/images/arrow.png"
                                                        style="width: 12px"
                                                    />
                                                </div>
                                                <div style="clear: both">
                                                    <div style="float: left">
                                                        <div style="font-size: 14px">
                                                            안전하게 상품을 받을때까지,
                                                        </div>
                                                        <div style="font-size: 14px">
                                                            중코마켓이 결제금액을 보관해요
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div v-if="postState == true">
                                                <div v-if="Addressresult == false">
                                                    <p style="margin-top: 5px">배송지를 입력해주세요.</p>
                                                    <hr/>
                                                    <vue-daum-postcode
                                                        @complete="handleAddress($event)"
                                                    />
                                                </div>
                                                <div v-else>
                                                    <p style="margin-top: 5px">
                                                        상세주소를 입력해주세요.
                                                    </p>
                                                    <hr/>
                                                    <div style="text-align: left">
                                                        {{ address }}<br/>
                                                        <input
                                                            type="text"
                                                            class="input"
                                                            size="10"
                                                            v-model="detailAddress"
                                                        />
                                                    </div>
                                                    <v-btn
                                                        color="error"
                                                        primary
                                                        style="margin-top: 8px"
                                                        @click="movePage"
                                                        v-bind:disabled="isCharging"
                                                    >{{ isCharging ? "구매중입니다" : "구매하기" }}
                                                    </v-btn
                                                    >
                                                </div>
                                            </div>
                                            <hr v-if="directState == true"/>
                                            <div style="text-align: left" v-if="directState == true">
                                                <p style="margin-top: 5px; font-weight: bold">직거래</p>
                                                <p style="font-size: 14px; margin: 5px 0px 5px 0px">
                                                    직거래도 현금없이 간편하게 결제할 수 있어요
                                                </p>
                                            </div>
                                        </MyModal>
                                    </div>
                                </v-col>
                            </v-row>
                        </v-row>
                    </v-col>
                </v-flex>
                <v-divider/>
            </v-row>
        </v-container>
    </div>
</template>

<script>
// import * as itemService from "../../api/item.js";
// import * as auctionService from "../../api/auction.js";
// import * as userService from "../../api/user.js";
import {findById as findUserById} from "@/api/user.js";
import * as purchaseService from "@/api/purchase.js";
import {weiToEth} from "@/utils/ethereumUnitUtils.js";
// import { ITEM_STATUS } from "../../config/constants.js";
import {getLocalImg} from "@/utils/imgLoader.js";
import {getPrice} from "@/utils/itemInventory.js";
import {findById} from "@/api/item.js";
import {CATEGORY} from "@/utils/category.js";
import {bookMarkSave} from "@/api/bookmark.js";
import * as walletService from "@/api/wallet.js";
import * as itemService from "@/api/item.js";
import HNav from "../../components/common/HNav copy";
import Vue from "vue";
import VueDaumPostcode from "vue-daum-postcode";
import MyModal from "./Modal.vue";
import img from "../../../public/images/arrow.png";
import * as ratingService from "@/api/rating.js";

Vue.use(VueDaumPostcode);

export default {
    name: "ItemDetail",
    components: {
        HNav,
        MyModal,
    },
    data() {
        return {
            isCharging: false,
            item: {
                id: null,
                name: "",
                category: null,
                explanation: "",
                available: null,
                state: "",
                seller: {
                    id: null,
                    name: "",
                    email: "",
                },
                directDeal: false, // 직거래 여부
                dealRegion: "",
                image: 0,
                price: null,
                registeredAt: null,
            },
            bookMarkList: {
                userId: "",
                itemId: "",
            },
            userId: this.$store.state.user.id,
            userName: "",
            star: require("../../../public/images/star.png"),
            time: "",
            modal: false,
            message: "",
            directState: false,
            postState: false,
            Addressresult: false,
            address: "",
            detailAddress: "",
            allAddress: "",
            cash: 0,
            walletAddress: "",
            name: "10 Best Things to Do in Seattle",
            category: "Travel",
            rating: 0,
            image:
                "https://s3-us-west-2.amazonaws.com/s.cdpn.io/1159990/pike-place.jpg",
            author: "Katherine Kato",
            desc: `Seattle is a seaport city on the west coast of the United States...`,
        };
    },
    methods: {
        goBack: function () {
            // 이전 페이지로 이동한다.
            this.$router.go(-1);
        },
        nego() {
            var price = prompt("원하는 가격을 입력하세요.");
            var address = "";
            var vm = this;
            const privateKey = prompt("상품을 구매하시려면 개인키를 입력하세요.");
            let check = false;
            var id = this.item.id;
            walletService.isValidPrivateKey(this.userId, privateKey, (res) => {
                if (res) {
                    itemService.update(
                        id,
                        price,
                        privateKey,
                        (res) => {
                            purchaseService.create(
                                id,
                                privateKey,
                                vm.walletAddress,
                                (response) => {
                                    alert("네고요청을 전달하였습니다");
                                    vm.$router.push("../../mypage/profile");
                                },
                                (error) => {
                                    alert("에러가 발생하였습니다.");
                                }
                            );
                        },
                        (err) => {
                            alert("네고요청 실패!!");
                        }
                    );
                } else {
                    alert("개인키 인증에 실패하였습니다.");
                    this.isCashCharging = false;
                }
            });
        },
        convertWeiToEth(value) {
            if (value) {
                return weiToEth(value.toString()) + " ETH";
            } else {
                return "-";
            }
        },
        getImg(name) {
            if (name) {
                console.log(name);
                return getLocalImg(name);
            }
            return null;
        },
        imgPath(n) {
            console.log(process.env.VUE_APP_BACKEND);
            return (
                process.env.VUE_APP_BACKEND +
                "api/items/images/" +
                this.item.id +
                "_" +
                n
            );
        },

        Chatting() {
            var name = this.userName;
            var id = this.item.id;
            var win = window.open(
                "../../chat/" + name + "/" + id,
                "PopupWin",
                "width=400,height=600"
            );
        },

        purchase() {
            var address = "";
            var vm = this;
            const privateKey = prompt("상품을 구매하시려면 개인키를 입력하세요.");
            let check = false;
            var id = this.item.id;
            vm.isCharging = true;
            walletService.isValidPrivateKey(this.userId, privateKey, (res) => {
                if (res) {
                    purchaseService.create(
                        id,
                        privateKey,
                        vm.allAddress,
                        (response) => {
                            alert("구매 의사를 전달하였습니다");
                            vm.$router.push("../../mypage/profile");
                            vm.isCharging = false;
                        },
                        (error) => {
                            alert("에러가 발생하였습니다.");
                            vm.isCharging = false;
                        }
                    );
                } else {
                    alert("개인키 인증에 실패하였습니다.");
                    this.isCashCharging = false;
                }
            });
        },
        BookMark() {
            this.bookMarkList.userId = this.userId;
            this.bookMarkList.itemId = this.item.id;

            bookMarkSave(
                this.bookMarkList,
                function (success) {
                    console.log("찜 성공");
                },
                function (fail) {
                    console.dir(fail);
                }
            );
        },
        getAddress() {
            // 주소 입력 창 띄우기
            this.postState = true;
        },
        movePage() {
            this.allAddress += this.address;
            this.allAddress += " ";
            this.allAddress += this.detailAddress;
            // 주소 디비에 저장
            this.purchase();
        },
        handleAddress(input) {
            this.Addressresult = true;
            this.address = input.address;
            console.log(input);
        },
        openModal() {
            if (this.cash < this.item.price) {
                alert("캐시가 부족합니다!");
                return;
            }
            this.directState = this.item.directDeal;
            this.modal = true;
        },
        closeModal() {
            this.modal = false;
            this.postState = false;
        },
        doSend() {
            if (this.message.length > 0) {
                alert(this.message);
                this.message = "";
                this.closeModal();
            } else {
                alert("메시지를 입력해주세요.");
            }
        },

        getImg(name) {
            if (name) {
                console.log(name);
                return getLocalImg(name);
            }
            return null;
        },
        saveBookMark() {
            var bookMark = JSON.parse(sessionStorage.getItem("bookmark"));
            if (bookMark == undefined) {
                bookMark = new Set();
                bookMark.add(this.item);
            } else {
                var duplicate = false;
                for (var item of bookMark) {
                    if (this.item.id == item.id) {
                        duplicate = true;
                        break;
                    }
                }
                if (!duplicate) {
                    bookMark = new Set([...bookMark, this.item]);
                }
            }
            sessionStorage.setItem("bookmark", JSON.stringify(bookMark));
        },
        BookMark() {
            this.bookMarkList.userId = this.userId;
            this.bookMarkList.itemId = this.item.id;

            bookMarkSave(
                this.bookMarkList,
                function (success) {
                    console.log("찜 성공");
                },
                function (fail) {
                    console.dir(fail);
                }
            );
        },
    },
    filters: {
        symbolToFullName(symbol) {
            return CATEGORY[symbol];
        },
    },
    created() {
        this.item.id = this.$route.params.id;
        var vm = this;
        findUserById(this.userId, function (res) {
            const result = res.data;
            console.log(result.name);
            vm.userName = result.name;
        });
    },
    mounted: function () {
        const vm = this;

        // [DB] 상품 상세 정보 조회
        findById(
            this.item.id,
            function (res) {
                const result = res.data;
                vm.item.name = result.name;
                vm.item.category = result.category;
                vm.item.explanation = result.explanation ? result.explanation : "";
                vm.item.available = result.available;
                vm.item.seller.id = result.seller;
                vm.item.image = result.image;
                vm.item.price = result.price;
                vm.item.directDeal = result.directDeal;
                vm.item.dealRegion = result.dealRegion;
                vm.item.registeredAt = result.registeredAt;
                console.log(vm.item);

                var date = "";
                date = vm.item.registeredAt.split("T");
                vm.time += date[0];
                vm.time += " ";
                vm.time += date[1];

                // 판매자 정보
                findUserById(result.seller, function (res) {
                    const result = res.data;
                    vm.item.seller.name = result.name;
                    vm.item.seller.email = result.email;
                    vm.saveBookMark();
                });

                ratingService.get(
                    vm.item.seller.id,
                    (res) => {
                        if (res) {
                            vm.rating = res.data;
                            vm.rating = vm.rating.toFixed(1);
                        }
                    },

                    (err) => {
                        vm.rating = "0.0";
                    }
                );
            },

            function (error) {
                console.error(error);
                alert("DB에서 상품 상세 정보 조회를 가져올 수 없습니다.");
            }
        );
        // [Smart Contract] 가격 조회
        getPrice(
            this.item.id,
            function (price) {
                vm.item.price = price;
                console.log(price);
            },
            function (err) {
                console.error("가격 조회 실패:", err);
            }
        );

        walletService.findByUserId(this.userId, (res) => {
            if (res) {
                vm.cash = res.data.cash;
                vm.walletAddress = res.data.address;
                console.log(vm.cash);
            }
        });
    },
};
</script>

<style>
#font {
    font-family: Helvetica, Arial, sans-serif;
}

.input {
    width: 300px;
    margin: 3px 0;
    text-align: left;
    border: 1px ridge gray;
}

img.center {
    display: block;
    margin: 2rem auto;
}

.modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: table;
    transition: opacity 0.3s ease;
}

.modal-wrapper {
    display: table-cell;
    vertical-align: middle;
}

.modal-container {
    width: 300px;
    margin: 0px auto;
    padding: 20px 30px;
    background-color: #fff;
    border-radius: 2px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
    transition: all 0.3s ease;
    font-family: Helvetica, Arial, sans-serif;
}

.modal-header h3 {
    margin-top: 0;
    color: #42b983;
}

.modal-body {
    margin: 20px 0;
}

.modal-default-button {
    float: right;
}

.modal-enter,
.modal-leave {
    opacity: 0;
}

.modal-enter .modal-container,
.modal-leave .modal-container {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
}
</style>
