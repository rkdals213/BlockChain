<template>
    <v-list-item-content xl10 lg10 md10 sm12 xs12>
        <v-col>
            <v-row>
                <v-col cols="3" style="text-align: left; font-size:30px">제목</v-col>
                <v-col style="float: left; text-align: left">
                    <div id="font" style="font-size:30px;">{{ name }}</div>
                </v-col>
            </v-row>
            <v-row>
                <v-col cols="3" style="text-align: left; font-size:20px">가격</v-col>
                <v-col style="float: right; text-align: left">
                    <div id="font" style="display: inline; font-weight: bold; font-size:20px;">{{ price }}</div>
                    CASH
                </v-col>
            </v-row>
        </v-col>
        <!--        <v-list-item-title style="font-size: xx-large">{{ name }}</v-list-item-title>-->
        <!--        <v-list-item-subtitle style="font-size: x-large">{{ price }}</v-list-item-subtitle>-->
        <v-spacer></v-spacer>
    </v-list-item-content>
</template>

<script>
import {findById, findLikedList} from "@/api/item";
import {deleteBookMark, bookMarkSave} from "@/api/bookmark";
import {getPrice} from "@/utils/itemInventory";

export default {
    props: ['item'],
    computed: {},
    data() {
        return {
            name: "",
            price: 0,
            state: this.item.state
        }
    },
    created() {
        console.log(this.item);
        this.getItemName(this.item.itemId)
    },
    methods: {
        getItemName(itemId) {
            const vm = this;
            findById(itemId, (res) => {
                if (res) {
                    vm.name = res.data.name;
                    vm.price = res.data.price;
                    console.log(vm.name)
                } else {
                    alert("개인키 인증에 실패하였습니다.");
                }
            });
        },
    }
};
</script>

<style>
.item-card img {
    height: 180px;
    width: 180px;
}

</style>
