<template>
  <div>
    <div class="container">
      <my-page-nav></my-page-nav>
      <div id="mywallet-info" class="row">
        <div class="col-md-12 mt-5">
          <div class="card">
            <table class="table table-bordered">
              <tr>
                <th>회원 정보</th>
                <td class="text-right">{{ user.name }}({{ user.email }})</td>
              </tr>
              <tr>
                <th>보유 ETH</th>
                <td class="text-right">{{ wallet["balance"] }} ETH</td>
                <td colspan="2" class="text-left">
                  <button
                    type="button"
                    class="btn btn-secondary"
                    v-on:click="chargeETH()"
                    v-bind:disabled="isCharging"
                  >
                    {{ isCharging ? "충전중" : "10ETH 충전하기" }}
                  </button>
                </td>
              </tr>
              <tr>
                <th>보유 캐시</th>
                <td class="text-right">{{ wallet["cash"] }} CASH</td>
                <td colspan="2" class="text-left">
                  <div class="input-group">
                    <input
                      v-model="cashChargeAmount"
                      type="number"
                      class="form-control"
                    />
                    <div class="input-group-append mr-3">
                      <span class="input-group-text">ETH</span>
                    </div>
                    <button
                      type="button"
                      class="btn btn-primary"
                      v-on:click="chargeCash()"
                      v-bind:disabled="!canCashCharge"
                    >
                      {{ isCashCharging ? "충전중" : "캐시 충전하기" }}
                    </button>
                  </div>

                  <p class="text-primary mb-0">* 1 ETH = 100000 CASH</p>
                  <p class="text-primary mb-0">* 최소 충전 금액: 0.1 ETH</p>
                </td>
              </tr>
              <!-- PJTⅡ 과제3 Req.1-1 보유 캐시 화면 구현 -->

              <tr>
                <th>내 지갑주소</th>
                <td class="text-right">{{ wallet["address"] }}</td>
                <th>ETH 충전 횟수</th>
                <td class="text-right">{{ wallet["receivingCount"] }}회</td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as walletService from "@/api/wallet.js";
import { findById } from "@/api/user.js";
import { createWeb3 } from "@/utils/itemInventory.js";
import { buyCash, getBalance } from "@/utils/cashContract.js";
import MyPageNav from "./MyPageNav.vue";
import { ethToWei } from "@/utils/ethereumUnitUtils.js";
import BN from "bn.js";

export default {
  name: "WalletInfo",
  components: {
    MyPageNav
  },
  data() {
    return {
      wallet: {
        id: 0,
        ownerId: null,
        address: "",
        balance: 0,
        cash: 0,
        receivingCount: 0
      },
      user: {
        name: "",
        email: ""
      },
      isCharging: false, // 현재 코인을 충전하고 있는 중인지 확인
      isCashCharging: false, // 현재 캐시을 충전하고 있는 중인지 확인
      cashChargeAmount: 0.1,
      userId: this.$store.state.user.id,
      walletAddress: this.$store.state.user.walletAddress
    };
  },
  computed: {
    canCashCharge() {
      return this.cashChargeAmount >= 0.1 && !this.isCashCharging;
    }
  },
  methods: {
    fetchWalletInfo() {
      /**
       * TODO: PJTⅡ 과제1 Req.1-2 [지갑 조회]
       * 사용자 지갑을 조회하여 잔액을 화면에 보여준다.
       */
      walletService.findByUserId(this.userId,
        (response)=> {
          this.wallet.id = response.data.id;
          this.wallet.ownerId = response.data.ownerId;
          this.wallet.address = response.data.address;
          this.wallet.balance = response.data.balance;
          this.wallet.cash    = response.data.cash;
          this.wallet.receivingCount = response.data.receivingCount;
        },
        (error)=> {
          alert("지갑 조회 중 에러 발생");
          console.dir(error);
        }
      )

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
      walletService.chargeEther(this.walletAddress,
        (response)=> {
          console.dir(response);
          this.wallet.balance = response.data.balance;
          this.wallet.receivingCount = response.data.receivingCount;
          alert("충전 되었습니다.");
          this.isCharging = false;
        },
        (error)=> {
          console.dir(error);
          alert("충전에 실패하였습니다.");
          this.isCharging = false;
        }
      )
    },
    chargeCash() {
      const vm = this;
      this.isCashCharging = true;
      const privateKey = prompt("캐시를 충전하시려면 개인키를 입력하세요.");
      let check = false;
      walletService.isValidPrivateKey(this.userId, privateKey, 
        (res) => {
          if (res) {
            /**
             * TODO: PJTⅡ 과제3 Req.1-1 [토큰 구매]
             * 이더를 지불하고 캐시를 충전
             */
            walletService.chargeCash(this.wallet.address, privateKey, this.cashChargeAmount,
              (response)=> {
                console.dir(response);
                this.wallet.balance = response.data.balance;
                this.wallet.cash    = response.data.cash;
                alert((this.cashChargeAmount*100000)+"Cash가 충전 되었습니다.");
                this.isCashCharging = false;
              },
              (error)=>{
                console.dir(error);
                alert("캐쉬 충전에 실패하였습니다.");
                this.isCashCharging = false;
              }
            )
    
          }else {
            alert("개인키 인증에 실패하였습니다.");
            this.isCashCharging = false;
          }
        }
      )
    },
    /**
     * TODO: PJTⅡ 과제3 Req.1-2 [토큰 잔액 조회]
     * 스마트 컨트랙트의 잔액 확인 함수 호출
     */
    fetchCashBalance() {
      
    },
    fetchEtherBalance() {
      const vm = this;
      walletService.findByUserId(this.userId, function(res) {
        const web3 = createWeb3();
        vm.wallet.balance = web3.utils.fromWei(
          res.data.balance.toString(),
          "ether"
        );
      });
    },
    // 회원 정보 가져온다.
    fetchUserInfo() {
      const vm = this;
      findById(this.userId, function(response) {
        const data = response.data;
        vm.user.name = data["name"];
        vm.user.email = data["email"];
      });
    },
  },
  mounted() {
    this.fetchWalletInfo();
    this.fetchUserInfo();
  }
};
</script>

<style>
#mywallet-info th {
  text-align: left;
}
</style>
