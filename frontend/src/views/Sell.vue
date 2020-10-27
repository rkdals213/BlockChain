<template>
  <div>
    <h-nav></h-nav>
    <h-breadcrumb title="판매 등록"></h-breadcrumb>
    <v-card color="white">
      <v-container>
        <v-layout row>
          <v-flex xl5 lg5 md5 sm12 xs12>
            <v-card>
              <v-container fluid xl10>
                <v-row>
                  <v-col class="d-flex child-flex" cols="4">
                    <v-hover v-slot:default="{ hover }">
                      <div class="form-group">
                        <v-card
                          flat
                          tile
                          class="d-flex"
                          :elevation="hover ? 16 : 2"
                        >
                          <v-img
                            src="https://t1.daumcdn.net/cfile/tistory/260D7C4651FA0F400D?download"
                            aspect-ratio="1"
                            class="grey lighten-2"
                            @click="onImageClick"
                          >
                            <input
                              ref="uploader"
                              type="file"
                              multiple
                              accept="image/*"
                              @change="onFileChange"
                              hidden
                            />
                          </v-img>
                        </v-card>
                      </div>
                    </v-hover>
                  </v-col>
                  <v-col
                    v-for="n in image.length"
                    :key="n"
                    class="d-flex child-flex"
                    cols="4"
                  >
                    <v-hover v-slot:default="{ hover }">
                      <v-card
                        flat
                        tile
                        class="d-flex"
                        :elevation="hover ? 16 : 2"
                      >
                        <v-img
                          :src="image[n - 1]"
                          aspect-ratio="1"
                          class="grey lighten-2"
                          @click="removeImage(n - 1)"
                        ></v-img>
                      </v-card>
                    </v-hover>
                  </v-col>
                </v-row>
              </v-container>
            </v-card>
          </v-flex>
          <v-flex xl2 lg2 md2 sm0 xs0>
            <span></span>
          </v-flex>
          <v-flex xl5 lg5 md5 sm0 xs0>
            <v-form ref="form" v-model="valid" lazy-validation>
              <v-text-field
                v-model="item.name"
                :counter="10"
                label="상품 이름"
                required
              ></v-text-field>
              <v-select
                v-model="item.category"
                :items="items"
                :rules="[(v) => !!v || 'Item is required']"
                label="카테고리"
                required
              ></v-select>
              <v-text-field
                type="number"
                v-model="item.price"
                label="가격"
                required
              ></v-text-field>
              <v-text-field
                v-model="item.explanation"
                label="설명"
                required
              ></v-text-field>
              <v-checkbox
                v-model="item.directDeal"
                label="직거래 여부"
                required
              ></v-checkbox>
              <v-text-field
                v-if="item.directDeal == true"
                v-model="item.dealRegion"
                label="장소"
                required
              ></v-text-field>
              <v-btn color="error" class="mr-4" @click="cancel">취소</v-btn>
              <v-btn color="success" class="mr-4" @click="save">등록</v-btn>
              <br /><br />
            </v-form>
          </v-flex>
        </v-layout>
      </v-container>
    </v-card>
  </div>
</template>

<script>
import { create as createItem } from "@/api/item.js";
import { save as savaImage } from "@/api/item.js";
import { registerItem } from "@/utils/itemInventory.js";
import * as walletService from "@/api/wallet.js";
import HNav from "../components/common/HNav";
import HBreadcrumb from "@/components/common/HBreadcrumb";

export default {
  name: "ItemCreate",
  components: {
    HNav,
    HBreadcrumb
  },
  data() {
    return {
      files: [],
      item: {
        id: "",
        name: "",
        category: "",
        explanation: "",
        available: true,
        seller: this.$store.state.user.id,
        directDeal: false,
        dealRegion: "",
        price: null,
        address: this.$store.state.user.walletAddress,
        pk: "",
      },
      image: [],
      id: null,
      imageURL: "",
      privateKey: "",
      userId: this.$store.state.user.id,
      isCreating: false,
      defaultImage: require("../../public/images/no_image.png"),
      isImageUpload: false,
      items: [
        "패션/잡화",
        "뷰티/미용",
        "디지털/가전",
        "유아물품",
        "도서/티켓",
        "스포츠/레저",
        "생활/문구/가구",
        "기타",
      ],
    };
  },
  computed: {
    /**
     * public/images에 있는 로컬 이미지를 첨부해서 사용합니다.
     * DB에는 이미지 파일 이름만 저장되고
     * 화면에 보여줄 땐 'public/images/{파일이름}' 경로를 사용합니다.
     */
    imgLocalPath() {
      if (this.item.imgName) {
        return process.env.BASE_URL + "images/" + this.item.imgName;
      }

      return null;
    },
  },
  methods: {
    // 상품을 등록한다.
    save() {
      const vm = this;
      const privateKey = prompt("캐시를 충전하시려면 개인키를 입력하세요.");
      this.item.pk = privateKey;
      this.isCreating = true; // 아이템 등록 중임을 화면에 표시, 등록이 끝나면 false로 변경
      walletService.isValidPrivateKey(this.userId, privateKey, (res) => {
        if (res) {
          if (
            this.item.name.length <= 0 ||
            this.item.category.length <= 0 ||
            this.item.price === null ||
            this.item.price <= 0 ||
            this.image === null ||
            this.image.length <= 0
          ) {
            alert("입력폼을 모두 입력해주세요.");
            this.isCreating = false;
            return;
          }
          const data = new FormData();
          const file = this.files;
          for (let i = 0; i < this.files.length; i++) {
            data.append("file", this.files[i]);
          }
          for (var pair of data.entries()) {
            console.log(pair[0] + "," + pair[1]);
          }

          createItem(
            // 상품 등록 - 백앤드
            this.item,
            function(success) {
              alert("상품 등록 성공!");
              console.log(success);
              vm.item.id = success.data.id;
              console.log("아이디:" + success.data.id);
              savaImage(
                // 사진 등록
                data,
                vm.item.id,
                function(success) {
                  alert("이미지 등록 성공!");
                },
                function(error) {
                  console.log(error);
                },
                function(final) {
                  console.log("안녕");
                }
              );
            },
            function(error) {
              console.log(error);
            },
            function(final) {
              console.log("안녕");
            }
          );
        } else {
          alert("개인키 인증에 실패하였습니다.");
          this.isCreating = false;
        }
      });

      /**
       * TODO: PJTⅢ 과제3 Req.1-1 [상품 등록]
       * DB에 상품 등록 후 반환 받은 id를 이용해서 이더리움에 상품을 등록
       */
    },

    onImageClick() {
      this.$refs.uploader.click();
    },

    cancel() {
      this.$router.push("/");
    },

    onFileChange(input) {
      let length = input.target.files.length;
      if (length === 0) {
        return;
      }

      if (length > 9) length = 9;
      for (let i = 1; i <= length; i++) {
        if (this.image.length + i >= 9) {
          alert("더이상 이미지를 추가할수 없습니다");
          break;
        }
        const file = input.target.files[i - 1];
        this.image.push(URL.createObjectURL(file));
        this.files.push(file);
      }
      console.log(this.files);
      this.$refs.uploader.value = null;
    },

    removeImage(n) {
      alert(n);
      // this.item.image[n].removeImage();
      this.image.splice(n, 1);
      this.files.splice(n, 1);
    },
  },
};
</script>
