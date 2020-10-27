<template>
  <div>
    <h-breadcrumb title="상품 상세 보기" description="등록된 상품의 상세 내역을 볼 수 있습니다."></h-breadcrumb>
    <v-container fluid>
      <v-row justify="space-around" style="margin-top:50px">
        <v-col cols="5" style="margin-right:30px">
          <v-img
            :src="item.image"
            aspect-ratio="1"
            max-width="386"
            max-height="386"
          ></v-img>
        </v-col>
        <v-col cols="6">
          <v-row>
            <h2 style="float:left">{{item.name}}</h2>
          </v-row>
          <v-row>
            <v-col cols="9">
              <h1 style="float:left">{{item.price}}원</h1>
            </v-col>
            <v-col cols="3">
              <v-btn style="width:100%">네고요청</v-btn>
            </v-col>
          </v-row>
          <v-divider />
          <v-row>
            <v-col cols="3" style="text-align:left">· 판매자</v-col>
            <v-col cols="8" style="text-align:left">{{item.seller}}</v-col>

            <v-col cols="3" style="text-align:left">· 신용등급</v-col>
            <v-col cols="8" style="text-align:left">3.7</v-col>

            <v-col cols="3" style="text-align:left">· 거래방법</v-col>
            <v-col cols="8" style="text-align:left">직거래</v-col>

            <v-col cols="3" style="text-align:left">· 거래지역</v-col>
            <v-col cols="8" style="text-align:left">{{item.dealRegion}}</v-col>

            <v-row>
              <v-col cols="3">
                <v-btn large style="width:100%">♡ 찜</v-btn>
              </v-col>
              <v-col cols="3">
                <v-btn large color="primary" style="width:100%">문의하기(채팅)</v-btn>
              </v-col>
              <v-col cols="3">
                <v-btn large color="error" style="width:100%">바로구매</v-btn>
              </v-col>
              <v-col cols="3"></v-col>
            </v-row>
          </v-row>
        </v-col>
      </v-row>
      <v-divider />
      <h2 align="left">상품정보</h2>
      <v-divider />
      <p align="left">
        {{item.explanation}}
      </p>
    </v-container>
  </div>
</template>

<script>
import * as itemService from "../../api/item.js";
// import * as auctionService from "../../api/auction.js";
// import * as userService from "../../api/user.js";
import { findById as findUserById } from "@/api/user.js";
import { weiToEth } from "@/utils/ethereumUnitUtils.js";
// import { ITEM_STATUS } from "../../config/constants.js";
import { getLocalImg } from "@/utils/imgLoader.js";
import { getPrice } from "@/utils/itemInventory.js";
import { findById } from "@/api/item.js";
import { CATEGORY } from "@/utils/category.js";

export default {
  name: "ItemDetail",
  data() {
    return {
      item: {
        id: null,
        name: "",
        category: null,
        explanation: "",
        available: null,
        seller: {},
        image: null,
        idDirect: {},
        dealRegion: {},
        price: null,
        registeredAt: null,
      },
      user : {

      },
      userId: this.$store.state.user.id,
    };
  },
  methods: {
    goBack: function () {
      // 이전 페이지로 이동한다.
      this.$router.go(-1);
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
        return getLocalImg(name);
      }
      return null;
    },
  },
  filters: {
    symbolToFullName(symbol) {
      return CATEGORY[symbol];
    },
  },
  created() {
    this.item.id = this.$route.params.id;

    itemService.findById(this.item.id,
      res => {
        console.dir(res)
        this.item = res.data

        findById(this.item.seller, 
        res => {
            alert(res.data)
            const data = res.data;
            this.user.name = data["name"];
            this.user.email = data["email"];
            this.item.seller = this.user.email
        },
        err => {
          alert(err)
        }
        );
      },
      err => {
        alert(err)
      }
    )
  },
  // 일단 주석처리
  //   mounted: function() {
  //     const vm = this;

  //     // [DB] 상품 상세 정보 조회
  //     findById(
  //       this.item.id,
  //       function(res) {
  //         const result = res.data;
  //         vm.item.name = result.name;
  //         vm.item.category = result.category;
  //         vm.item.explanation = result.explanation ? result.explanation : "";
  //         vm.item.available = result.available;
  //         vm.item.seller.id = result.seller;
  //         vm.item.image = result.image;
  //         vm.item.registeredAt = result.registeredAt;

  //         // 판매자 정보
  //         findUserById(result.seller, function(res) {
  //           const result = res.data;
  //           vm.item.seller.name = result.name;
  //           vm.item.seller.email = result.email;
  //         });
  //       },
  //       function(error) {
  //         console.error(error);
  //         alert("DB에서 상품 상세 정보 조회를 가져올 수 없습니다.");
  //       }
  //     );
  //     // [Smart Contract] 가격 조회
  //     getPrice(
  //       this.item.id,
  //       function(price) {
  //         vm.item.price = price;
  //       },
  //       function(err) {
  //         alert("상품 가격 조회에 실패했습니다.");
  //         console.error("가격 조회 실패:", err);
  //       }
  //     );
  //   },
};
</script>

<style>
img.center {
  display: block;
  margin: 2rem auto;
}
</style>
