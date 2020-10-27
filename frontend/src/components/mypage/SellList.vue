<template>
  <div>
    <!--        <h-nav></h-nav>-->
    <!--        <div style="height:100px; background-color: rgb(255,212,85);">-->
    <!--            <h1 style="margin: 2rem 0.7rem 1rem 2rem; display: inline-block;">거래 결과</h1>-->
    <!--        </div>-->
    <v-card color="basil">
      <div>
        <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
        <v-card>
          <v-list three-line xl10 lg10 md10 sm12 xs12>
            <div v-for="(item, index) in items" :key="item.itemId">
              <v-divider v-if="index !== 0"></v-divider>
              <v-list-item>
                <v-list-item-avatar size="100px">
                  <v-img :src="imgPath(item.itemId)"></v-img>
                </v-list-item-avatar>
                <sell-list-card :item="item"></sell-list-card>
              </v-list-item>
              <v-row>
                <v-col cols="1"></v-col>
                <v-col cols="10" id="progress-container">
                  <v-stepper alt-labels v-model="item.state">
                    <v-stepper-header>
                      <v-stepper-step step="1" :complete="item.state === 'I'">
                        입금 대기
                      </v-stepper-step>
                      <v-divider></v-divider>
                      <v-stepper-step step="2" :complete="item.state === 'P'">
                        입금 완료
                      </v-stepper-step>
                      <v-divider></v-divider>
                      <v-stepper-step step="3" :complete="item.state === 'S'">
                        배송 시작
                      </v-stepper-step>
                      <v-divider></v-divider>
                      <v-stepper-step step="4" :complete="item.state === 'C'">
                        거래 완료
                      </v-stepper-step>
                    </v-stepper-header>
                  </v-stepper>
                </v-col>
                <v-col cols="1">
                  <v-row>
                    <v-btn
                      color="success"
                      :disabled="item.state !== 'P'"
                      @click="send(index)"
                      >배송시작</v-btn
                    >
                  </v-row>
                  <v-row>
                    <v-btn
                      color="primary"
                      :disabled="item.state !== 'C'"
                      @click="cancelPurchase(index)"
                      >거래 취소</v-btn
                    >
                  </v-row>
                  <v-spacer></v-spacer>
                </v-col>
              </v-row>
            </div>
          </v-list>
        </v-card>
        <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
      </div>
    </v-card>
  </div>
</template>

<script>
import HNav from "@/components/common/HNav copy";
import { findMySalePurchases } from "@/api/purchase";
import * as purchaseService from "@/api/purchase.js";
import * as walletService from "@/api/wallet.js";
import { findById } from "@/api/item";
import SellListCard from "@/components/mypage/SellListCard";
import PurchaseTxDetailVue from "../escrow/PurchaseTxDetail.vue";

export default {
  components: {
    SellListCard,
    HNav,
  },
  data() {
    return {
      items: [],
      userId: this.$store.state.user.id,
      name: [],
    };
  },
  computed: {
    function() {
      console.log(this.items.length);
      for (let i = 0; i < this.items.length; i++) {
        const temp = this.getItemName(this.items[i].itemId);
        this.name.push(temp);
        console.log(temp);
      }
    },
  },
  methods: {
    imgPath(id) {
      console.log(process.env.VUE_APP_BACKEND);
      return process.env.VUE_APP_BACKEND + "api/items/images/" + id + "_1";
    },
    getPurchaseHistory() {
      findMySalePurchases(this.userId, (res) => {
        if (res) {
          console.log(res);
          this.items = res.data;
        } else {
          alert("개인키 인증에 실패하였습니다.");
        }
      });
    },
    estimate() {
      alert("estimate");
      // 평가하기 버튼
    },
    cancelPurchase(index) {
      const privateKey = prompt("취소하시려면 개인키를 입력하세요.");
      console.log(this.userId);
      const vm = this;
      walletService.isValidPrivateKey(this.userId, privateKey, (res) => {
        if (res) {
          purchaseService.cancel(
            vm.items[index].purchaseId,
            privateKey,
            (res) => {
              alert("성공!");
            },
            (error) => {
              alert("실패!");
            }
          );
        } else {
          alert("개인키 인증에 실패하였습니다.");
          this.isCashCharging = false;
        }
      });
    },
    send(index) {
      const privateKey = prompt("배송하시려면 개인키를 입력하세요.");
      console.log(this.userId);
      const vm = this;
      walletService.isValidPrivateKey(this.userId, privateKey, (res) => {
        if (res) {
          purchaseService.send(
            vm.items[index].purchaseId,
            privateKey,
            (res) => {
              alert("성공!");
            },
            (error) => {
              alert("실패!");
            }
          );
        } else {
          alert("개인키 인증에 실패하였습니다.");
          this.isCashCharging = false;
        }
      });
    },
  },
  mounted: function() {},
  created() {
    this.getPurchaseHistory();
    for (let i = 0; i < this.items.length; i++) {
      const temp = this.getItemName(this.items[i].itemId);
      this.name.push(temp);
    }
  },
};
</script>

<style>
ul {
  display: flex;
  list-style: none;
}
</style>
