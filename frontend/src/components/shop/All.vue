<template>
    <div>
        <h-breadcrumb title="중코 상점"></h-breadcrumb>

        <v-card color="white">
            <v-layout row wrap justify-center style="padding: 25px 0px 0 0">
                <v-col cols="1" style="padding: 0px">
                    <v-select :items="types" label="제목" item-text="name" item-value="value" solo style="margin-left: 5px" @change="changeSearchBy"></v-select>
                </v-col>
                <v-col cols="6" style="padding: 0px">
                    <v-text-field
                        id="search"
                        name="search"
                        type="text"
                        color="black"
                        label="검색어를 입력하세요"
                        v-model="searchKeyword"
                        @keyup.enter="
                            setPage(1);
                            search();
                        "
                        style="margin-left: 5px"
                        solo
                        clearable
                        single-line
                    ></v-text-field>
                </v-col>
            </v-layout>
            <div style="clear: both"></div>
            <v-tabs v-model="tab" background-color="transparent" color="basil" grow>
                <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
                <v-tab
                    v-for="item in categories"
                    :key="item"
                    class="font-weight-bold"
                    @click="
                        categoryNow = item;
                        setPage(1);
                        search();
                    "
                    style="font-family: 'Jua', sans-serif; font-size: 18px"
                    >{{ item }}</v-tab
                >
                <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
            </v-tabs>
            <v-container>
                <v-row>
                    <v-flex xs12 sm6 md4 lg3 xl3 class="itemCard" v-for="item in items" v-bind:key="item.id">
                        <item-card :item="item" @clicked="onClickItem(item.id)"></item-card>
                    </v-flex>
                </v-row>
            </v-container>
            <div class="text-center">
                <v-pagination
                    v-model="page"
                    :circle="circle"
                    :disabled="disabled"
                    :length="length"
                    :next-icon="nextIcon"
                    :prev-icon="prevIcon"
                    :page="page"
                    :total-visible="totalVisible"
                    @input="
                        setPage;
                        search();
                    "
                ></v-pagination
                ><br />
            </div>
        </v-card>
    </div>
</template>

<script>
import { getListNoKeyword, getList } from "@/api/item.js";
import HShopCategories from "./HShopCategories.vue";
import ItemCard from "./ItemCard.vue";
import { getPrice } from "@/utils/itemInventory.js";
import HNav from "@/components/common/HNav";
import HBreadcrumb from "@/components/common/HBreadcrumb";

export default {
    components: {
        HShopCategories,
        ItemCard,
        HNav,
        HBreadcrumb,
    },
    data() {
        return {
            items: [],
            newItem: {
                id: 999,
                name: "dummy",
                price: "1",
                registeredAt: "2020-09-29",
            },
            categories: ["전체보기", "패션/잡화", "뷰티/미용", "디지털/가전", "유아물품", "도서/티켓", "스포츠/레저", "생활/문구/가구", "기타"],

            circle: false,
            disabled: false,
            length: 30,
            nextIcon: "navigate_next",
            prevIcon: "navigate_before",
            page: 1,
            totalVisible: 10,
            searchKeyword: "",
            searchBy: 1,
            categoryNow: "전체보기",
            types: [
                { name: "제목", value: 1 },
                { name: "판매자", value: 2 },
            ],
            route: 0,
        };
    },
    methods: {
        changeSearchBy(searchBy) {
            this.searchBy = searchBy;
            console.log(this.searchBy);
        },
        setPage(page) {
            this.page = page;
            console.log(this.page + " : " + this.searchBy);
        },
        setSearchBy(searchBy) {
            this.searchBy = searchBy;
            console.log(this.searchBy);
            this.page = 1;
            this.search();
        },
        setKeyword(searchKeyword) {
            this.searchKeyword = searchKeyword;
            console.log(this.searchKeyword);
        },
        search() {
            let temp = 0;

            if (this.categoryNow === "전체보기") temp = 0;
            else temp = 1;

            console.log("searchby : " + this.searchBy);
            console.log("keyword : " + this.searchKeyword);
            console.log("category : " + this.categoryNow);
            console.log("temp : " + temp);

            console.log(temp);
            if (!this.searchKeyword || this.searchKeyword === "") {
                if (this.searchBy === 1 && temp === 0) this.getListNoKeyword(0);
                else if (this.searchBy === 1 && temp === 1) this.getListNoKeyword(1);
                else if (this.searchBy === 2 && temp === 0) this.getListNoKeyword(2);
                else if (this.searchBy === 2 && temp === 1) this.getListNoKeyword(3);
            } else {
                if (this.searchBy === 1 && temp === 0) this.getList(0);
                else if (this.searchBy === 1 && temp === 1) this.getList(1);
                else if (this.searchBy === 2 && temp === 0) this.getList(2);
                else if (this.searchBy === 2 && temp === 1) this.getList(3);
            }
        },
        onClickItem(itemId) {
            this.$router.push("item/detail/" + itemId);
        },

        getList(kind) {
            const vm = this;
            getList(
                vm.page - 1,
                vm.categoryNow.split("/")[0],
                vm.searchKeyword,
                kind,
                function (response) {
                    console.log(response.data.totalPages);
                    if (response.data.length > 0) {
                        vm.items = response.data.content;
                        vm.items.forEach((i) => {
                            // [스마트 컨트랙트] 가격 조회
                            getPrice(
                                i.id,
                                function (price) {
                                    vm.$set(i, "price", price);
                                },
                                function (err) {
                                    console.error("가격 조회 실패:", err);
                                    // alert("상품 가격 조회를 실패했습니다.");
                                }
                            );
                        });
                    } else {
                        vm.items = response.data.content;
                    }
                    vm.length = response.data.totalPages;
                },
                (err) => {
                    alert(err);
                }
            );
        },
        getListNoKeyword(kind) {
            const vm = this;
            getListNoKeyword(
                vm.page - 1,
                vm.categoryNow.split("/")[0],
                kind,
                function (response) {
                    console.log(response.data.totalPages);
                    if (response.data.length > 0) {
                        vm.items = response.data.content;

                        vm.items.forEach((i) => {
                            // [스마트 컨트랙트] 가격 조회
                            getPrice(
                                i.id,
                                function (price) {
                                    vm.$set(i, "price", price);
                                },
                                function (err) {
                                    console.error("가격 조회 실패:", err);
                                    // alert("상품 가격 조회를 실패했습니다.");
                                }
                            );
                        });
                    } else {
                        vm.items = response.data.content;
                    }
                    vm.length = response.data.totalPages;
                },
                (err) => {
                    alert(err);
                }
            );
        },
    },
    mounted: function () {
        if (this.route != 1) this.search(); // 상점 눌러서 들어왔을 때
    },
    created() {
        this.searchKeyword = this.$route.params.searchKeyword;
        if (this.searchKeyword.length != 0) {
            this.searchBy = 1; // 제목으로 검색
            this.search();
            this.route = 1;
        }
        this.searchBy = 1;
    },
};
</script>

<style scoped>
.blog-banner-area {
    height: 180px;
    position: relative;
    z-index: 1;
}
@media (min-width: 1000px) {
    .blog-banner-area {
        height: 270px;
    }
}
.blog-banner-area .blog-banner {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 100%;
    transform: translate(-50%, -50%);
}
.blog-banner-area::after {
    content: "";
    display: block;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: #f1f6f7;
    z-index: -1;
}
.blog-banner-btn {
    border: 2px solid #384aeb;
    padding: 10px 45px;
}
.blog-banner-btn:hover {
    background: #384aeb;
    color: #fff;
}
.banner-breadcrumb {
    display: inline-block;
}
.banner-breadcrumb .breadcrumb {
    background: transparent;
    padding: 0;
}
.banner-breadcrumb .breadcrumb-item {
    padding: 0.1rem;
}
.banner-breadcrumb .breadcrumb-item a {
    text-transform: capitalize;
    color: #777777;
}
.banner-breadcrumb .breadcrumb-item + .breadcrumb-item::before {
    color: #777777;
    padding-right: 0.1rem;
    content: "-";
}
.banner-breadcrumb .breadcrumb-item.active {
    color: #777777;
}

ul {
    display: flex;
    list-style: none;
}

.itemCard {
    padding: 12px;
}

.hero-banner {
    background: #f1f6f7;
    position: relative;
    margin-bottom: 10px;
}

@media (min-width: 991px) {
    .hero-banner {
        z-index: 111;
    }
}

.hero-banner::before {
    content: "";
    display: block;
    width: 5%;
    height: 100%;
    background: #384aeb;
    position: absolute;
    top: 0;
    left: 0;
}

@media (min-width: 575px) {
    .hero-banner::before {
        width: 20%;
    }
}

@media (min-width: 991px) {
    .hero-banner::before {
        width: 35%;
    }
}

.hero-banner__img {
    position: relative;
    top: 1px;
}

@media (max-width: 575px) {
    .hero-banner__content {
        padding-bottom: 50px;
    }
}

@media (min-width: 1000px) {
    .hero-banner__content {
        max-width: 495px;
    }
}

.hero-banner__content h4 {
    font-size: 30px;
    font-family: "Roboto", sans-serif;
    color: #555555;
    margin-bottom: 12px;
}

.hero-banner__content h1 {
    font-family: "Roboto", sans-serif;
    font-size: 25px;
    text-transform: uppercase;
    font-weight: 700;
    margin-bottom: 10px;
}

@media (min-width: 767px) {
    .hero-banner__content h1 {
        font-size: 30px;
        margin-bottom: 25px;
    }
}

@media (min-width: 991px) {
    .hero-banner__content h1 {
        font-size: 50px;
    }
}

.hero-banner__content p {
    margin-bottom: 10px;
}

@media (min-width: 767px) {
    .hero-banner__content p {
        margin-bottom: 40px;
    }
}
</style>
