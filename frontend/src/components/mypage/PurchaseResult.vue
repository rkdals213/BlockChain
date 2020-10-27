<template>
    <div>
        <h-nav></h-nav>
        <div style="height:100px; background-color: rgb(255,212,85);">
            <h1 style="margin: 2rem 0.7rem 1rem 2rem; display: inline-block;">거래 결과</h1>
        </div>
        <v-card color="basil">
            asdasddgg
        </v-card>
    </div>

</template>

<script>
import HNav from "@/components/common/HNav";
import {getDetail} from "@/api/purchase";

export default {
    components: {
        HNav,
    },
    data() {
        return {

        };
    },
    methods: {
        getPurchaseHistory(){
            getDetail(this.purchaseId, (res) => {
                if (res) {

                } else {
                    alert("개인키 인증에 실패하였습니다.");
                }
            });
        }
    },
    mounted: function () {

    },
    created() {

    },

};
</script>

<style>
ul {
    display: flex;
    list-style: none;
}
</style>
