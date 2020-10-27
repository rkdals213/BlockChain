<template>
    <div>
        <!-- <my-page-nav></my-page-nav> -->
        <v-row>
            <v-flex md2></v-flex>
            <v-col cols="12" md="8">
                <v-row id="profile-container">
                    <v-col cols="4" id="profile-image-container">
                        <div>
                            <v-avatar size="130px">
                                <!--                                <v-img-->
                                <!--                                    :src="`https://picsum.photos/500/300?random`"-->
                                <!--                                    :lazy-src="`https://picsum.photos/10/6?random`"-->
                                <!--                                    aspect-ratio="1"-->
                                <!--                                    class="grey lighten-2">-->
                                <!--                                </v-img>-->
                                <v-img
                                    :src="imgPath(true)"
                                    :lazy-src="imgPath(true)"
                                    aspect-ratio="1"
                                    class="grey lighten-2"
                                ></v-img>
                            </v-avatar>
                            <p
                                class="font-weight-bold"
                                style="margin: 10px 0 0 0; font-size: 18px"
                            >
                                {{ user.name }}
                            </p>
                            <p class="font-weight-bold">{{ user.email }}</p>
                            <router-link
                                :to="{ name: 'mypage.password' }"
                                style="color: green"
                            >비밀번호 변경
                            </router-link>
                            <a id="link-to-login" @click.stop="imageModal = true">이미지등록</a>
                        </div>
                    </v-col>
                    <!-- 지갑정보가 있을때 -->
                    <v-col cols="8" id="wallet-info-container" v-if="$store.state.user.walletAddress">
                        <v-row>
                            <v-col cols="6" md="3">
                                <p>보유 ETH :</p>
                            </v-col>
                            <v-col cols="6" md="3">
                                <p>{{ wallet.balance }} ETH</p>
                            </v-col>
                            <v-col cols="12" md="6">
                                <v-btn
                                    v-on:click="chargeETH()"
                                    v-bind:disabled="isCharging"
                                    color="success"
                                    width="100%"
                                >
                                    {{ isCharging ? "충전중" : "10ETH 충전하기" }}
                                </v-btn>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col cols="6" md="3">
                                <p>보유 캐시 :</p>
                            </v-col>
                            <v-col cols="6" md="3">
                                <p>{{ wallet.cash }} CASH</p>
                            </v-col>
                            <v-col cols="6" md="3">
                                <input
                                    v-model="cashChargeAmount"
                                    type="number"
                                    class="form-control"
                                />
                            </v-col>
                            <v-col cols="6" md="3">
                                <v-btn
                                    color="primary"
                                    v-on:click="chargeCash()"
                                    v-bind:disabled="!canCashCharge"
                                    width="100%"
                                >
                                    {{ isCashCharging ? "충전중" : "캐시 충전하기" }}
                                </v-btn>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col cols="12" md="3">
                                <p>내 지갑주소 :</p>
                            </v-col>
                            <v-col cols="12" md="9">
                                <p>{{ wallet.address }}</p>
                            </v-col>
                        </v-row>
                    </v-col>
                    <!-- 지갑정보가 없을때 -->
                    <v-col cols="8" v-if="!$store.state.user.walletAddress">
                        <v-row>
                            <v-col cols="12" style="font-size: 30px">
                                지갑이 없습니다!
                                지갑을 생성해주세요
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col cols="12">
                                <v-btn @click="">
                                    <a @click.stop="walletModal = true">지갑 생성</a>
                                </v-btn>
                            </v-col>
                        </v-row>
                    </v-col>
                </v-row>

                <v-row>
                    <v-tabs color="#356859" grow background-color="#FFFBE6">
                        <v-tab class="tab-menu" @click="showRegistedItemList">
                            등록 상품 목록
                        </v-tab>
                        <v-tab class="tab-menu" @click="showBuyHistory">
                            구매 내역 목록
                        </v-tab>
                        <v-tab class="tab-menu" @click="showRecentlyViewHistory">
                            최근 본 목록
                        </v-tab>
                        <v-tab class="tab-menu" @click="showBookMarkList">
                            찜한 목록
                        </v-tab>
                    </v-tabs>
                </v-row>
                <v-row v-if="tabStatus > 2">
                    <v-col v-for="item in items" :key="item.id" cols="6" sm="4" lg="3">
                        <item-card :item="item" @clicked="onClickItem(item.id)"></item-card>
                    </v-col>
                </v-row>
                <v-row v-if="tabStatus == 1">
                  <v-container
                    v-for="item in items"
                    :key="item.itemId"
                  >
                    <sell-item
                      :item="item"
                    ></sell-item>
                  </v-container>
                </v-row>
                <v-row v-if="tabStatus == 2">
                    <v-container
                        v-for="buyPurchase in buyPurchases"
                        :key="buyPurchase.id"
                    >
                        <buy-history
                            :buyPurchase="buyPurchase"
                            :ratings="ratings"
                        ></buy-history>
                    </v-container>
                </v-row>
            </v-col>
        </v-row>
        <v-dialog v-model="imageModal" max-width="500" min-width="300">
            <div style="background-color: white">
                <ImgModal :imageModal="imageModal" v-on:closeThis="reload"></ImgModal>
            </div>
        </v-dialog>
        <v-dialog v-model="walletModal" max-width="500" min-width="300">
            <div style="background-color: white">
                <walletModal :walletModal="walletModal" v-on:closeThis="reload"></walletModal>
            </div>
        </v-dialog>
    </div>
</template>

<script>
import MyPageNav from "./MyPageNav.vue";
import {findById, save} from "@/api/user.js";
import {loadBookMark} from "@/api/bookmark.js";
import * as itemService from "../../api/item.js";
import * as purchaseService from "@/api/purchase.js";

import ItemCard from "../shop/ItemCard.vue";
import BuyHistory from "./BuyHistory.vue";
import SellItem from "./SellItem.vue";
import SellHitsory from "./SellList.vue";
import ImgModal from "./ImgModal";
import walletModal from "./WalletCreate"

//이더리움 통신
import * as walletService from "@/api/wallet.js";
import {createWeb3} from "@/utils/itemInventory.js";
import {buyCash, getBalance} from "@/utils/cashContract.js";
import {ethToWei} from "@/utils/ethereumUnitUtils.js";
import * as ratingService from "@/api/rating.js";
import BN from "bn.js";
import {save as savaImage} from "../../api/item.js";

export default {
  components: {
    MyPageNav,
    ItemCard,
    BuyHistory,
    SellItem,
    SellHitsory,
    ImgModal,
  },
  data() {
    return {
      //   userId: this.$store.state.user.id,
      user: {
        id: this.$store.state.user.id,
        name: "",
        email: "",
      },
      size: 0,
      wallet: {
        id: 0,
        ownerId: null,
        address: "",
        balance: 0,
        cash: 0,
        receivingCount: 0,
      },
      //선택된 탭
      tabStatus: 1,
      items: [],
      buyPurchases: [],
      isCharging: false, // 현재 코인을 충전하고 있는 중인지 확인
      isCashCharging: false, // 현재 캐시을 충전하고 있는 중인지 확인
      cashChargeAmount: 0.1,
      walletAddress: this.$store.state.user.walletAddress,
      ratings: [],

      imageModal: false,
      walletModal: false,
    };
  },
  created() {
    this.fetchUserInfo();
    this.showRegistedItemList();
    this.fetchWalletInfo();
    this.fetchBuyHistory();
  },
  computed: {
    canCashCharge() {
      return this.cashChargeAmount >= 0.1 && !this.isCashCharging;
      },
    },
    methods: {
        changePassword() {
        },
        onClickItem(itemId) {
            this.$router.push("../item/detail/" + itemId);
        },
        showRegistedItemList() {
          console.log("시작")
          console.dir(this.user)
            this.tabStatus = 1;
            this.items = [];
            itemService.findMySaleItems(
                this.user.id,
                (res) => {
                    this.items = res.data;
                    console.log(res)
                },
                (err) => {
                    alert(err);
                }
            );
        },
        showBuyHistory() {
            this.tabStatus = 2;
            this.items = [];
            this.fetchBuyHistory();
        },
        showRecentlyViewHistory() {
            this.tabStatus = 3;
            this.items = [];
            const bookMark = JSON.parse(sessionStorage.getItem("bookmark"));

            if (bookMark != undefined) this.items = bookMark;
            else this.items = [];
        },
        showBookMarkList() {
            this.tabStatus = 4;
            this.items = [];
            loadBookMark(
                this.user.id,
                (res) => {
                    this.items = res.data;
                },
                (err) => {
                    alert(err);
                }
            );
        },
        fetchWalletInfo() {
            /**
             * TODO: PJTⅡ 과제1 Req.1-2 [지갑 조회]
             * 사용자 지갑을 조회하여 잔액을 화면에 보여준다.
             */
            walletService.findByUserId(
                this.user.id,
                (response) => {
                    this.wallet.id = response.data.id;
                    this.wallet.ownerId = response.data.ownerId;
                    this.wallet.address = response.data.address;
                    this.wallet.balance = response.data.balance;
                    this.wallet.cash = response.data.cash;
                    this.wallet.receivingCount = response.data.receivingCount;
                },
                (error) => {
                    alert("지갑 조회 중 에러 발생");
                }
            );

            /**
             * TODO: PJTⅡ 과제3 Req.1-3 [지갑 조회 확장]
             * 캐시 잔액 정보를 포함하여 화면에 보여준다.
             */
        },
        /**
         * TODO: PJTⅡ 과제1 Req.1-3 [코인 충전]
         * 이더 충전을 요청
         */
        chargeETH() {
            this.isCharging = true;
            walletService.chargeEther(
                this.walletAddress,
                (response) => {
                    this.wallet.balance = response.data.balance;
                    this.wallet.receivingCount = response.data.receivingCount;
                    alert("충전 되었습니다.");
                    this.isCharging = false;
                },
                (error) => {
                    alert("충전에 실패하였습니다.");
                    this.isCharging = false;
                }
            );
        },
        chargeCash() {
            const vm = this;
            this.isCashCharging = true;
            const privateKey = prompt("캐시를 충전하시려면 개인키를 입력하세요.");
            let check = false;
            walletService.isValidPrivateKey(this.user.id, privateKey, (res) => {
                if (res) {
                    /**
                     * TODO: PJTⅡ 과제3 Req.1-1 [토큰 구매]
                     * 이더를 지불하고 캐시를 충전
                     */
                    walletService.chargeCash(
                        this.wallet.address,
                        privateKey,
                        this.cashChargeAmount,
                        (response) => {
                            this.wallet.balance = response.data.balance;
                            this.wallet.cash = response.data.cash;
                            alert(this.cashChargeAmount * 100000 + "Cash가 충전 되었습니다.");
                            this.isCashCharging = false;
                        },
                        (error) => {
                            alert("캐쉬 충전에 실패하였습니다.");
                            this.isCashCharging = false;
                        }
                    );
                } else {
                    alert("개인키 인증에 실패하였습니다.");
                    this.isCashCharging = false;
                }
            });
        },
        /**
         * TODO: PJTⅡ 과제3 Req.1-2 [토큰 잔액 조회]
         * 스마트 컨트랙트의 잔액 확인 함수 호출
         */
        fetchCashBalance() {
        },
        fetchEtherBalance() {
            const vm = this;
            walletService.findByUserId(this.user.id, function (res) {
                const web3 = createWeb3();
                vm.wallet.balance = web3.utils.fromWei(
                    res.data.balance.toString(),
                    "ether"
                );
            });
        },
        // 회원 정보 가져온다.
        fetchUserInfo() {
            findById(this.user.id, (response) => {
                const data = response.data;
                this.user.name = data["name"];
                this.user.email = data["email"];
            });
        },
        fetchBuyHistory() {
            purchaseService.findMyPurchases(
                this.user.id,
                (res) => {
                    this.buyPurchases = res.data;
                },
                (err) => {
                    alert(err);
                }
            );
        },
        imgPath(flag) {
            // console.log(process.env.VUE_APP_BACKEND);
            // console.log(this.user.id);
            return flag
                ? process.env.VUE_APP_BACKEND + "api/users/downloadFile/" + this.user.id
                : "https://picsum.photos/500/300?random";
        },
        reload() {
            this.imageModal = false;
            this.$router.go(this.$router.currentRoute);
        },
    },
    mounted: function () {
    },
};
</script>

<style>
#profile-container {
    /* border: 1px solid #263238; */
}

#profile-image-container {
    background-color: #ffd455;
    padding: 30px 0 18px 0;
}

#shop-info-container {
    margin-left: 20px;
    margin-right: 20px;
}

#wallet-info-container {
    align-self: center;
}

#wallet-info-container p {
    font-size: 17px;
}

#item-container {
    min-height: 100px;
}

.tab-menu {
    font-size: 16px;
    font-weight: bold;
}
</style>
