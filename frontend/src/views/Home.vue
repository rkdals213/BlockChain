<template>
    <div>
        <h-nav></h-nav>
        <div class="hero-banner" style="margin-bottom:0px; height:500px; padding-top:40px;"> 
            <div class="container">
                <div class="row no-gutters align-items-center">
                <div class="col-5 d-none d-sm-block">
                    <div class="hero-banner__img">
                    <img class="img-fluid" style="height:388px;width:400px; margin-right:350px;" src="../../public/images/이미지.png" alt="">
                    </div>
                </div>
                <div class="col-sm-7 col-lg-5 offset-lg-1 pl-4 pl-md-5 pl-lg-0">
                    <div class="hero-banner__content" style="margin-top:38px; margin-left:50px;">
                        <h4>중코마켓</h4>
                        <h1 style="margin:0px;">중고마켓에서</h1>
                        <h1>코인으로 거래하자</h1>
                        <p style="margin-bottom:8px;">1이더 = 400,000원 상당</p>
                        <p style="margin-bottom:8px;">1이더 = 100,000 CASH</p>
                        <p style="margin-bottom:8px;">이더로 코인을 충전하여 거래하세요</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <v-card color="white">
        <v-card-title class="text-center justify-center py-6">
            <h1 class="font-weight-bold text-secondary">카테고리별 인기상품</h1>
        </v-card-title>
        <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
        <v-tabs v-model="tab" background-color="transparent" color="basil" grow>
            <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
            <v-tab v-for="item in items" :key="item" class="font-weight-bold" @click="getCategory(item)" style="font-family: 'Jua', sans-serif; font-size:18px;">{{ item }}</v-tab>
            <v-flex xl1 lg1 md1 sm0 xs0><span></span></v-flex>
        </v-tabs>
        <v-tabs-items v-model="tab">
            <v-tab-item v-for="item in items" :key="item">
                <v-card color="white" flat>
                    <v-card-text style="padding:16px 110px 16px 110px;">
                            <v-row>
                                <v-flex xs12 sm6 md4 lg4 xl6
                                        class="col-md-2 artwork"
                                        v-for="item in itemlist"
                                        v-bind:key="item.id">
                                    <item-card :item="item" @clicked="onClickItem(item.id)"></item-card>
                                </v-flex>
                            </v-row>
                    </v-card-text>
                </v-card>
            </v-tab-item>
        </v-tabs-items>
    </v-card>

        <!--================ Start footer Area  =================-->	
	<footer class="footer" style="background:#002347; padding-top:25px; height:350px;">
        <v-col style="color: white;"><h1>Our Team</h1></v-col><br>
        <v-row>
            <v-col cols="1">
            </v-col>
            <v-col cols="2">
                <img id="image" src="../../public/images/이정훈.jpg" alt=""><br><br>
                <p style="color:white;">이정훈</p>
            </v-col>
            <v-col cols="2">
                <img id="image" src="../../public/images/윤경준.jpg" alt=""><br><br>
                <p style="color:white;">윤경준</p>
            </v-col>
            <v-col cols="2">
                <img id="image" src="../../public/images/강민형.jpeg" alt=""><br><br>
                <p style="color:white;">강민형</p>
            </v-col>
            <v-col cols="2">
                <img id="image" src="../../public/images/김동현.jpg" alt=""><br><br>
                <p style="color:white;">김동현</p>
            </v-col>
            <v-col cols="2">
                <img id="image" src="../../public/images/최연희.jpg" alt=""><br><br>
                <p style="color:white;">최연희</p>
            </v-col>
            <v-col cols="1">
            </v-col>
        </v-row>
	</footer>
    </div>
</template>


<script>
import HNav from "../components/common/HNav";
import StepFlow from '@/components/common/StepFlow';
import Vue from 'vue';
import Vuetify from 'vuetify/lib';
import 'material-design-icons-iconfont/dist/material-design-icons.css';
import {mdiAccount} from '@mdi/js';
import {findAll} from "@/api/item";
import {getPrice} from "@/utils/itemInventory";
import ItemCard from "@/components/shop/ItemCard";
import {findByMainCategory} from "@/api/item.js";

export default {
    data() {
        return {
            itemlist: [],
            circle: false,
            disabled: false,
            length: 30,
            nextIcon: 'navigate_next',
            prevIcon: 'navigate_before',
            page: 1,
            totalVisible: 10,
            tab: null,
            items: [
                '패션/잡화',
                '뷰티/미용',
                '디지털/가전',
                '유아물품',
                '도서/티켓',
                '스포츠/레저',
                '생활/문구/가구',
                '기타',
            ],
            searchKeyword: "",
        }
    },
    components: {
        HNav,
        StepFlow,
        ItemCard
    },
    created() {
        console.dir(this.$store.state)
        console.log('처음시작 ');
        var default_category = "패션";
        console.log(default_category);
        const vm = this;
        findByMainCategory(
            default_category,
            function (success) { // 가져온 카테고리별 리스트 보여주기
                vm.itemlist = success.data;
            },
            function (fail) {
                console.log('카테고리 목록 가져오기 실패!');
            }
        )
        console.log(this.itemlist);
    },
    mounted: function () {


    },
    methods: {
        onClickItem(itemId) {
            this.$router.push("item/detail/" + itemId);
        },
        getCategory(cate) { // 카테고리 누르면 받아오기
            var category = cate.split("/");
            console.log(category[0]);
            const vm = this;
            findByMainCategory(
                category[0],
                function (success) { // 가져온 카테고리별 리스트 보여주기
                    vm.itemlist = success.data;
                },
                function (fail) {
                    console.log('카테고리 목록 가져오기 실패!');
                }
            )
        },
        search() { // shop으로 검색 키워드 넘기기
            var searchKeyword = this.searchKeyword;
            this.$router.push("/shop/" + searchKeyword);
        },
    },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

#image{
    width:120px;
    height: 120px;
}

.header_area{position:relative;width:100%;z-index:9999;transition:background 0.4s, all 0.3s linear;box-shadow:0px 10px 27px 3px rgba(221,221,221,0.3)}.header_area .navbar{background:transparent;padding:0px;border:0px;border-radius:0px}.header_area .navbar .nav .nav-item{margin-right:45px}.header_area .navbar .nav .nav-item .nav-link{text-transform:capitalize;color:#222;display:inline-block}@media (min-width: 991px){.header_area .navbar .nav .nav-item .nav-link{padding:32px 0px}}.header_area .navbar .nav .nav-item .nav-link:after{display:none}.header_area .navbar .nav .nav-item:hover .nav-link,.header_area .navbar .nav .nav-item.active .nav-link{color:#384aeb}.header_area .navbar .nav .nav-item.submenu{position:relative}.header_area .navbar .nav .nav-item.submenu ul{border:none;padding:0px;border-radius:0px;box-shadow:none;margin:0px;background:#fff}@media (min-width: 992px){.header_area .navbar .nav .nav-item.submenu ul{position:absolute;top:120%;left:0px;min-width:200px;text-align:left;opacity:0;transition:all 300ms ease-in;visibility:hidden;display:block;border:none;padding:0px;border-radius:0px}}.header_area .navbar .nav .nav-item.submenu ul:before{content:"";width:0;height:0;border-style:solid;border-width:10px 10px 0 10px;border-color:#eeeeee transparent transparent transparent;position:absolute;right:24px;top:45px;z-index:3;opacity:0;transition:all 400ms linear}.header_area .navbar .nav .nav-item.submenu ul .nav-item{display:block;float:none;margin-right:0px;border-bottom:1px solid #ededed;margin-left:0px;transition:all 0.4s linear}.header_area .navbar .nav .nav-item.submenu ul .nav-item .nav-link{line-height:45px;color:#222;padding:0px 30px;transition:all 150ms linear;display:block;margin-right:0px}.header_area .navbar .nav .nav-item.submenu ul .nav-item:last-child{border-bottom:none}.header_area .navbar .nav .nav-item.submenu ul .nav-item:hover .nav-link{background:#384aeb;color:#fff}@media (min-width: 992px){.header_area .navbar .nav .nav-item.submenu:hover ul{visibility:visible;opacity:1;top:100%}}.header_area .navbar .nav .nav-item.submenu:hover ul .nav-item{margin-top:0px}.header_area .navbar .nav .nav-item:last-child{margin-right:0px}.header_area .navbar .search{font-size:12px;line-height:60px;display:inline-block;color:#222}.header_area .navbar .search i{font-weight:600}.header_area.navbar_fixed .main_menu{position:fixed;width:100%;top:-70px;left:0;right:0;background:#ffffff;-webkit-transform:translateY(70px);-moz-transform:translateY(70px);-ms-transform:translateY(70px);-o-transform:translateY(70px);transform:translateY(70px);-webkit-transition:all 0.3s ease 0s;-moz-transition:all 0.3s ease 0s;-o-transition:all 0.3s ease 0s;transition:all 0.3s ease 0s;box-shadow:0px 3px 16px 0px rgba(0,0,0,0.1)}.header_area.navbar_fixed .main_menu .navbar .nav .nav-item .nav-link{line-height:70px}#search_input_box{text-align:center;padding:5px 20px}#search_input_box .form-control{padding-left:0;background:transparent;border:0;color:#ffffff;font-weight:600;font-size:18px}#search_input_box .form-control:focus{box-shadow:none;outline:none}#search_input_box input.placeholder{color:#ffffff;font-size:16px}#search_input_box input:-moz-placeholder{color:#ffffff;font-size:16px}#search_input_box input::-moz-placeholder{color:#ffffff;font-size:16px}#search_input_box input::-webkit-input-placeholder{color:#ffffff;font-size:16px}#search_input_box .btn{width:0;height:0;padding:0;border:0}#search_input_box .lnr-cross{color:#fff;font-weight:600;cursor:pointer;padding:10px 0}.header-top{background:#000000}@media (max-width: 991px){.header-top{display:none}}.header-top a{color:#fff}.header-top ul li{display:inline-block;margin-left:15px}.header-top .header-top-left a{margin-right:60px;font-weight:600}.header-top .header-top-left a .text{margin-top:-2px}.header-top .header-top-left a .text span{font-size:14px;color:#ffffff}.header-top .header-top-left a .lnr{color:#ffffff;margin-right:8px}@media (max-width: 767px){.header-top .header-top-left .text{display:none}}.header-top .header-top-right{text-align:right}.header-top .header-top-right a{padding:0 20px;height:40px;display:inline-block;line-height:40px;font-size:14px;font-weight:600;font-family:"Oswald",sans-serif;background:transparent;-webkit-transition:all 0.3s ease 0s;-moz-transition:all 0.3s ease 0s;-o-transition:all 0.3s ease 0s;transition:all 0.3s ease 0s}.nav-shop li{display:inline-block;margin-right:15px}.nav-shop li i,.nav-shop li span{font-size:16px;color:#222}.nav-shop li button{padding:0;border:0;background:transparent;position:relative}.nav-shop li button .nav-shop__circle{font-size:9px;display:inline-block;background:#384aeb;color:#fff;padding:0px 5px;border-radius:50%;position:absolute;top:-8px;right:-12px}.nav-shop li:last-child{margin-left:40px}@media (max-width: 1619px){.header_area .navbar .search{margin-left:40px}}@media (max-width: 1199px){.header_area .navbar .nav .nav-item{margin-right:28px}.home_banner_area .banner_inner{padding:100px 0px}.header_area .navbar .tickets_btn{margin-left:40px}}@media (max-width: 991px){.navbar-toggler{border:none;border-radius:0px;padding:0px;cursor:pointer;margin-top:27px;margin-bottom:23px}.header_area .navbar{background:#ffffff}.navbar-toggler[aria-expanded="false"] span:nth-child(2){opacity:1}.navbar-toggler[aria-expanded="true"] span:nth-child(2){opacity:0}.navbar-toggler[aria-expanded="true"] span:first-child{transform:rotate(-45deg);position:relative;top:7.5px}.navbar-toggler[aria-expanded="true"] span:last-child{transform:rotate(45deg);bottom:6px;position:relative}.navbar-toggler span{display:block;width:25px;height:3px;background:#384aeb;margin:auto;margin-bottom:4px;transition:all 400ms linear;cursor:pointer}.navbar .container{padding-left:15px;padding-right:15px}.nav{padding:0px 0px}.header_area+section,.header_area+row,.header_area+div{margin-top:117px}.header_top .nav{padding:0px}.header_area .navbar .nav .nav-item .nav-link{line-height:30px;margin-right:0px;display:block;border-bottom:1px solid #ededed33;border-radius:0px}.header_area .navbar .nav-shop{padding-bottom:40px}.header_area .navbar .search{margin-left:0px}.header_area .navbar-collapse{max-height:100px;overflow-y:scroll}.header_area .navbar .nav .nav-item.submenu ul .nav-item .nav-link{padding:0px 15px}.header_area .navbar .nav .nav-item{margin-right:0px}.header_area+section,.header_area+row,.header_area+div{margin-top:0px;padding-top:71px}.header_area.navbar_fixed .main_menu .navbar .nav .nav-item .nav-link{line-height:40px}.categories_post img{width:100%}.categories_post{max-width:360px;margin:0 auto}.blog_categorie_area .col-lg-4{margin-top:30px}.blog_area{padding-bottom:80px}.single-post-area .blog_right_sidebar{margin-top:30px}.contact_info{margin-bottom:50px}.home_banner_area .donation_inner{margin-bottom:-30px}.home_banner_area .dontation_item{max-width:350px;margin:auto}.footer-area .col-sm-6{margin-bottom:30px}.header_area .navbar .tickets_btn{margin-left:0px}.home_banner_area{min-height:700px}}@media (max-width: 767px){.home_banner_area{min-height:500px}.home_banner_area .banner_inner{min-height:500px}.home_banner_area .banner_inner .banner_content{margin-top:0px}.blog_banner .banner_inner .blog_b_text h2{font-size:40px;line-height:50px}.blog_info.text-right{text-align:left !important;margin-bottom:10px}.home_banner_area .banner_inner .banner_content h3{font-size:30px}.home_banner_area .banner_inner .banner_content p br{display:none}.home_banner_area .banner_inner .banner_content h3 span{line-height:45px;padding-bottom:0px;padding-top:0px}.footer-bottom{text-align:center}.footer-bottom .footer-social{text-align:center;margin-top:15px}}@media (max-width: 575px){.top_menu{display:none}.header_area+section,.header_area+row,.header_area+div{margin-top:0px;padding-top:71px}.home_banner_area .banner_inner .banner_content h2{font-size:40px;line-height:44px}.home_banner_area{min-height:500px}.home_banner_area .banner_inner{min-height:500px}.blog_banner .banner_inner .blog_b_text{margin-top:0px}.home_banner_area .banner_inner .banner_content img{display:none}.home_banner_area .banner_inner .banner_content h5{margin-top:0px}.sample-text-area{padding:70px 0 70px 0}.generic-blockquote{padding:30px 15px 30px 30px}.blog_details h2{font-size:20px;line-height:30px}.blog_banner .banner_inner .blog_b_text h2{font-size:28px;line-height:38px}.footer-area{padding:70px 0px}.single-footer-widget .sub-btn{right:0px}}@media (max-width: 480px){.header_area .navbar-collapse{max-height:250px}.home_banner_area .banner_inner .banner_content{margin-top:0px}.home_banner_area .banner_inner .banner_content h3{font-size:24px}.banner_area .banner_inner .banner_content h2{font-size:32px}.comments-area .thumb{margin-right:10px}}.section-intro p{margin-bottom:3px}.section-intro__style{border-bottom:2px solid #384aeb;padding-bottom:8px}.hero-banner{background:#F1F6F7;position:relative;margin-bottom:10px}@media (min-width: 991px){.hero-banner{z-index:111}}.hero-banner::before{content:"";display:block;width:5%;height:100%;background:#384aeb;position:absolute;top:0;left:0}@media (min-width: 575px){.hero-banner::before{width:20%}}@media (min-width: 991px){.hero-banner::before{width:15%}}.hero-banner__img{position:relative;top:1px}@media (max-width: 575px){.hero-banner__content{padding-bottom:50px}}@media (min-width: 1000px){.hero-banner__content{max-width:495px}}.hero-banner__content h4{font-size:30px;font-family:"Roboto",sans-serif;color:#555555;margin-bottom:12px}.hero-banner__content h1{font-family:"Roboto",sans-serif;font-size:25px;text-transform:uppercase;font-weight:700;margin-bottom:10px}@media (min-width: 767px){.hero-banner__content h1{font-size:30px;margin-bottom:25px}}@media (min-width: 991px){.hero-banner__content h1{font-size:50px}}.hero-banner__content p{margin-bottom:10px}@media (min-width: 767px){.hero-banner__content p{margin-bottom:40px}}
.section-margin{margin:45px 0}@media (min-width: 980px){.section-margin{margin:70px 0}}@media (min-width: 1100px){.section-margin{margin:120px 0}.section-margin.calc-60px{margin:120px 0 70px 0}}.section-margin--small{margin:35px 0}@media (min-width: 1000px){.section-margin--small{margin:98px 0}}.section-padding{padding:60px 0}@media (min-width: 1000px){.section-padding{padding:130px 0}}.pb-60px{padding-bottom:60px}.pt-60px{padding-top:60px}.ml-40{margin-left:40px}.header_area{position:relative;width:100%;z-index:9999;transition:background 0.4s, all 0.3s linear;box-shadow:0px 10px 27px 3px rgba(221,221,221,0.3)}.header_area .navbar{background:transparent;padding:0px;border:0px;border-radius:0px}.header_area .navbar .nav .nav-item{margin-right:45px}.header_area .navbar .nav .nav-item .nav-link{text-transform:capitalize;color:#222;display:inline-block}@media (min-width: 991px){.header_area .navbar .nav .nav-item .nav-link{padding:32px 0px}}.header_area .navbar .nav .nav-item .nav-link:after{display:none}.header_area .navbar .nav .nav-item:hover .nav-link,.header_area .navbar .nav .nav-item.active .nav-link{color:#384aeb}.header_area .navbar .nav .nav-item.submenu{position:relative}.header_area .navbar .nav .nav-item.submenu ul{border:none;padding:0px;border-radius:0px;box-shadow:none;margin:0px;background:#fff}@media (min-width: 992px){.header_area .navbar .nav .nav-item.submenu ul{position:absolute;top:120%;left:0px;min-width:200px;text-align:left;opacity:0;transition:all 300ms ease-in;visibility:hidden;display:block;border:none;padding:0px;border-radius:0px}}.header_area .navbar .nav .nav-item.submenu ul:before{content:"";width:0;height:0;border-style:solid;border-width:10px 10px 0 10px;border-color:#eeeeee transparent transparent transparent;position:absolute;right:24px;top:45px;z-index:3;opacity:0;transition:all 400ms linear}.header_area .navbar .nav .nav-item.submenu ul .nav-item{display:block;float:none;margin-right:0px;border-bottom:1px solid #ededed;margin-left:0px;transition:all 0.4s linear}.header_area .navbar .nav .nav-item.submenu ul .nav-item .nav-link{line-height:45px;color:#222;padding:0px 30px;transition:all 150ms linear;display:block;margin-right:0px}.header_area .navbar .nav .nav-item.submenu ul .nav-item:last-child{border-bottom:none}.header_area .navbar .nav .nav-item.submenu ul .nav-item:hover .nav-link{background:#384aeb;color:#fff}@media (min-width: 992px){.header_area .navbar .nav .nav-item.submenu:hover ul{visibility:visible;opacity:1;top:100%}}.header_area .navbar .nav .nav-item.submenu:hover ul .nav-item{margin-top:0px}.header_area .navbar .nav .nav-item:last-child{margin-right:0px}.header_area .navbar .search{font-size:12px;line-height:60px;display:inline-block;color:#222}.header_area .navbar .search i{font-weight:600}.header_area.navbar_fixed .main_menu{position:fixed;width:100%;top:-70px;left:0;right:0;background:#ffffff;-webkit-transform:translateY(70px);-moz-transform:translateY(70px);-ms-transform:translateY(70px);-o-transform:translateY(70px);transform:translateY(70px);-webkit-transition:all 0.3s ease 0s;-moz-transition:all 0.3s ease 0s;-o-transition:all 0.3s ease 0s;transition:all 0.3s ease 0s;box-shadow:0px 3px 16px 0px rgba(0,0,0,0.1)}.header_area.navbar_fixed .main_menu .navbar .nav .nav-item .nav-link{line-height:70px}#search_input_box{text-align:center;padding:5px 20px}#search_input_box .form-control{padding-left:0;background:transparent;border:0;color:#ffffff;font-weight:600;font-size:18px}#search_input_box .form-control:focus{box-shadow:none;outline:none}#search_input_box input.placeholder{color:#ffffff;font-size:16px}#search_input_box input:-moz-placeholder{color:#ffffff;font-size:16px}#search_input_box input::-moz-placeholder{color:#ffffff;font-size:16px}#search_input_box input::-webkit-input-placeholder{color:#ffffff;font-size:16px}#search_input_box .btn{width:0;height:0;padding:0;border:0}#search_input_box .lnr-cross{color:#fff;font-weight:600;cursor:pointer;padding:10px 0}.header-top{background:#000000}@media (max-width: 991px){.header-top{display:none}}.header-top a{color:#fff}.header-top ul li{display:inline-block;margin-left:15px}.header-top .header-top-left a{margin-right:60px;font-weight:600}.header-top .header-top-left a .text{margin-top:-2px}.header-top .header-top-left a .text span{font-size:14px;color:#ffffff}.header-top .header-top-left a .lnr{color:#ffffff;margin-right:8px}@media (max-width: 767px){.header-top .header-top-left .text{display:none}}.header-top .header-top-right{text-align:right}.header-top .header-top-right a{padding:0 20px;height:40px;display:inline-block;line-height:40px;font-size:14px;font-weight:600;font-family:"Oswald",sans-serif;background:transparent;-webkit-transition:all 0.3s ease 0s;-moz-transition:all 0.3s ease 0s;-o-transition:all 0.3s ease 0s;transition:all 0.3s ease 0s}.nav-shop li{display:inline-block;margin-right:15px}.nav-shop li i,.nav-shop li span{font-size:16px;color:#222}.nav-shop li button{padding:0;border:0;background:transparent;position:relative}.nav-shop li button .nav-shop__circle{font-size:9px;display:inline-block;background:#384aeb;color:#fff;padding:0px 5px;border-radius:50%;position:absolute;top:-8px;right:-12px}.nav-shop li:last-child{margin-left:40px}@media (max-width: 1619px){.header_area .navbar .search{margin-left:40px}}@media (max-width: 1199px){.header_area .navbar .nav .nav-item{margin-right:28px}.home_banner_area .banner_inner{padding:100px 0px}.header_area .navbar .tickets_btn{margin-left:40px}}@media (max-width: 991px){.navbar-toggler{border:none;border-radius:0px;padding:0px;cursor:pointer;margin-top:27px;margin-bottom:23px}.header_area .navbar{background:#ffffff}.navbar-toggler[aria-expanded="false"] span:nth-child(2){opacity:1}.navbar-toggler[aria-expanded="true"] span:nth-child(2){opacity:0}.navbar-toggler[aria-expanded="true"] span:first-child{transform:rotate(-45deg);position:relative;top:7.5px}.navbar-toggler[aria-expanded="true"] span:last-child{transform:rotate(45deg);bottom:6px;position:relative}.navbar-toggler span{display:block;width:25px;height:3px;background:#384aeb;margin:auto;margin-bottom:4px;transition:all 400ms linear;cursor:pointer}.navbar .container{padding-left:15px;padding-right:15px}.nav{padding:0px 0px}.header_area+section,.header_area+row,.header_area+div{margin-top:117px}.header_top .nav{padding:0px}.header_area .navbar .nav .nav-item .nav-link{line-height:30px;margin-right:0px;display:block;border-bottom:1px solid #ededed33;border-radius:0px}.header_area .navbar .nav-shop{padding-bottom:40px}.header_area .navbar .search{margin-left:0px}.header_area .navbar-collapse{max-height:340px;overflow-y:scroll}.header_area .navbar .nav .nav-item.submenu ul .nav-item .nav-link{padding:0px 15px}.header_area .navbar .nav .nav-item{margin-right:0px}.header_area+section,.header_area+row,.header_area+div{margin-top:0px;padding-top:71px}.header_area.navbar_fixed .main_menu .navbar .nav .nav-item .nav-link{line-height:40px}.categories_post img{width:100%}.categories_post{max-width:360px;margin:0 auto}.blog_categorie_area .col-lg-4{margin-top:30px}.blog_area{padding-bottom:80px}.single-post-area .blog_right_sidebar{margin-top:30px}.contact_info{margin-bottom:50px}.home_banner_area .donation_inner{margin-bottom:-30px}.home_banner_area .dontation_item{max-width:350px;margin:auto}.footer-area .col-sm-6{margin-bottom:30px}.header_area .navbar .tickets_btn{margin-left:0px}.home_banner_area{min-height:700px}}@media (max-width: 767px){.home_banner_area{min-height:500px}.home_banner_area .banner_inner{min-height:500px}.home_banner_area .banner_inner .banner_content{margin-top:0px}.blog_banner .banner_inner .blog_b_text h2{font-size:40px;line-height:50px}.blog_info.text-right{text-align:left !important;margin-bottom:10px}.home_banner_area .banner_inner .banner_content h3{font-size:30px}.home_banner_area .banner_inner .banner_content p br{display:none}.home_banner_area .banner_inner .banner_content h3 span{line-height:45px;padding-bottom:0px;padding-top:0px}.footer-bottom{text-align:center}.footer-bottom .footer-social{text-align:center;margin-top:15px}}@media (max-width: 575px){.top_menu{display:none}.header_area+section,.header_area+row,.header_area+div{margin-top:0px;padding-top:71px}.home_banner_area .banner_inner .banner_content h2{font-size:40px;line-height:44px}.home_banner_area{min-height:500px}.home_banner_area .banner_inner{min-height:500px}.blog_banner .banner_inner .blog_b_text{margin-top:0px}.home_banner_area .banner_inner .banner_content img{display:none}.home_banner_area .banner_inner .banner_content h5{margin-top:0px}.sample-text-area{padding:70px 0 70px 0}.generic-blockquote{padding:30px 15px 30px 30px}.blog_details h2{font-size:20px;line-height:30px}.blog_banner .banner_inner .blog_b_text h2{font-size:28px;line-height:38px}.footer-area{padding:70px 0px}.single-footer-widget .sub-btn{right:0px}}@media (max-width: 480px){.header_area .navbar-collapse{max-height:250px}.home_banner_area .banner_inner .banner_content{margin-top:0px}.home_banner_area .banner_inner .banner_content h3{font-size:24px}.banner_area .banner_inner .banner_content h2{font-size:32px}.comments-area .thumb{margin-right:10px}}.section-intro p{margin-bottom:3px}.section-intro__style{border-bottom:2px solid #384aeb;padding-bottom:8px}.hero-banner{background:#F1F6F7;position:relative;margin-bottom:10px}@media (min-width: 991px){.hero-banner{z-index:111}}.hero-banner::before{content:"";display:block;width:5%;height:100%;background:#384aeb;position:absolute;top:0;left:0}@media (min-width: 575px){.hero-banner::before{width:20%}}@media (min-width: 991px){.hero-banner::before{width:25%}}.hero-banner__img{position:relative;top:1px}@media (max-width: 575px){.hero-banner__content{padding-bottom:50px}}@media (min-width: 1000px){.hero-banner__content{max-width:495px}}.hero-banner__content h4{font-size:30px;font-family:"Roboto",sans-serif;color:#555555;margin-bottom:12px}.hero-banner__content h1{font-family:"Roboto",sans-serif;
font-size:25px;text-transform:uppercase;font-weight:700;margin-bottom:10px}@media (min-width: 767px){.hero-banner__content h1{font-size:30px;margin-bottom:25px}}@media (min-width: 991px){.hero-banner__content h1{font-size:50px}}.hero-banner__content p{margin-bottom:10px}@media (min-width: 767px){.hero-banner__content p{margin-bottom:40px}}.hero-carousel__slide{position:relative}.hero-carousel__slide::after{content:"";display:block;position:absolute;bottom:0;left:0;width:100%;height:100%;background:rgba(255,255,255,0.8);transform:translateY(50px);opacity:0;transition:all .4s ease}.hero-carousel__slideOverlay{position:absolute;bottom:50px;background:#384aeb;padding:23px 90px 23px 25px;transform:translateY(50px);opacity:0;z-index:-1;transition:all .4s ease-in-out}.hero-carousel__slideOverlay h3{font-size:20px;font-weight:400;color:#fff}.hero-carousel__slideOverlay p{color:#fff;margin:0}.hero-carousel__slide:hover::after{transform:translateY(0);opacity:1}.hero-carousel__slide:hover .hero-carousel__slideOverlay{transform:translateY(0);opacity:1;z-index:2}.card-product{border:0;margin-bottom:30px}.card-product p{margin-bottom:0}.card-product__img{position:relative}.card-product__imgOverlay{background:rgba(255,255,255,0.5);position:absolute;bottom:0;left:0;width:100%;padding:30px 5px;transform:translateY(30px);opacity:0;z-index:-1;transition:all .48s ease}.card-product__imgOverlay li{display:inline-block}.card-product__imgOverlay li button{border:0;border-radius:0;padding:7px 12px;background:#8894ff}.card-product__imgOverlay li button i,.card-product__imgOverlay li button span{font-size:15px;color:#fff;vertical-align:middle}.card-product__imgOverlay li button:hover{background:#384aeb}.card-product__imgOverlay li:not(:last-child){margin-right:5px}.card-product__title a{color:#222}.card-product__price{font-size:18px;font-weight:500}.card-product:hover .card-product__imgOverlay{opacity:1;z-index:1;transform:translateY(0)}.card-product:hover .card-product__title a{color:#384aeb}@media (min-width: 1200px){.offer{background-size:100% 100%}}.offer__content{padding:100px 0 110px 0}@media (min-width: 1000px){.offer__content{padding:122px 0 135px 0}}.offer__content h3{font-size:35px;font-weight:400;margin-bottom:20px}@media (min-width: 650px){.offer__content h3{font-size:50px;margin-bottom:25px}}.offer__content h4{font-size:30px}.offer__content p{color:#555555}#bestSellerCarousel .owl-prev,#bestSellerCarousel .owl-next{width:40px;height:40px;background:#f1f6f7}#bestSellerCarousel .owl-prev i,#bestSellerCarousel .owl-prev span,#bestSellerCarousel .owl-next i,#bestSellerCarousel .owl-next span{font-size:15px;color:#384aeb;line-height:40px}#bestSellerCarousel .owl-prev:hover,#bestSellerCarousel .owl-next:hover{background:#384aeb}#bestSellerCarousel .owl-prev:hover i,#bestSellerCarousel .owl-prev:hover span,#bestSellerCarousel .owl-next:hover i,#bestSellerCarousel .owl-next:hover span{color:#fff}#bestSellerCarousel .owl-prev{position:absolute;top:30%;transform:translateY(-30%);left:-20px}@media (min-width: 1340px){#bestSellerCarousel .owl-prev{left:-105px}}#bestSellerCarousel .owl-next{position:absolute;top:30%;transform:translateY(-30%);right:-20px}@media (min-width: 1340px){#bestSellerCarousel .owl-next{right:-105px}}.card-blog{border:0}.card-blog .card-body{padding:25px 25px 25px 0}

.searchform label,
.searchform input {
    color: #737373;
    vertical-align: baseline;
}

.searchform input[type=search] {
    font: 1em/1.618 Open Sans, Arial, Sans-serif;
    border: .125em solid #737373;
    border-width: 0 0 3px;
    background-color: transparent;
    padding: .1875em .375em;
    width: 80%;
}

.searchform input[type=search]:focus {
    border-color: #E18728;
    color: black;
    font-size: 22px;
    font-family: 'Jua', sans-serif;
}

@media only screen and (min-width: 48em) {
    .searchform input[type=search] {
        width: 40%;
    }
}

.text-secondary {
    color: #2c3e50 !important;
    font-size: 25px;
}

.box {
    margin-left: 250px;
    height: 40px;
    width: 440px;
    border: 1px solid #1b5ac2;
    background: #ffffff;
    float: left;
}

input::placeholder {
    color: black;
    font-size: 22px;
    font-family: 'Jua', sans-serif;
}

#search-box {
    height: 34.6px;
    padding: 3px 6px;
    font-family: 'Jua', sans-serif;
    font-size: 22px;
    text-align: center;
}


/* Helper classes */
.basil {
    background-color: #FFFBE6 !important;
}

.basil--text {
    color: #356859 !important;
}

.footer-area{background:#002347;padding:50px 0px 50px}@media (min-width: 800px){.footer-area{padding:195px 0px 120px}}.single-footer-widget p{margin-bottom:15px}.single-footer-widget p:last-child{margin-bottom:0}.single-footer-widget .instafeed{margin-left:-5px;margin-right:-5px}.single-footer-widget .instafeed li{margin:5px}@media (max-width: 1199px) and (min-width: 992px){.single-footer-widget .instafeed li:nth-child(4),.single-footer-widget .instafeed li:nth-child(5),.single-footer-widget .instafeed li:nth-child(6){display:none}}.single-footer-widget .sm-head{color:#fff;margin-bottom:0px}.single-footer-widget .sm-head span{position:relative;margin-left:-40px;margin-right:10px;display:inline-block;width:28px;height:28px;line-height:28px;text-align:center;background:#384aeb;color:#ffffff;border-radius:20px}.tp_widgets .list li{margin-bottom:12px}.tp_widgets .list li a{color:#7b838a;font-family:"Roboto",sans-serif}.tp_widgets .list li a:hover{color:#384aeb}.tp_widgets .list li:last-child{margin-bottom:0px}.tp_widgets p{color:#7b838a}.footer_title{font-size:18px;line-height:24px;font-weight:500;font-family:"Oswald",sans-serif;text-transform:capitalize;color:#fff;margin-bottom:28px}@media (max-width: 991px){.footer_title{margin-bottom:10px}}.footer_title.large_title{font-size:36px;font-weight:600}@media (max-width: 1199px){.footer_title.large_title{font-size:18px}}.footer-bottom{background:rgba(0,35,71,0.980392);padding:32px}.footer-bottom .footer-text{color:#fff;margin-bottom:0}.footer-bottom .footer-text a{color:#384aeb;opacity:1}.footer-only{padding-top:60px !important;padding-bottom:60px !important}@media (min-width: 1000px){.footer-only{padding-top:115px !important;padding-bottom:115px !important}}

</style>
