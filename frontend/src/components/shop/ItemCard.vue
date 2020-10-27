<template>
<div><br>
        <v-card
            class="item-card mx-auto"
            width="263"
            height="280"
            :elevation="hover ? 16 : 2"
            @click.prevent="$emit('clicked')" href=""
        >
            <v-img class="white--text align-end" height="280" width="263" :src="imgPath">
                 <v-card-actions style="float:right; padding: 2px 0 0 0px;">
                <v-btn
                    color="red accent-4"
                    icon
                    v-if="checkLiked()"
                    @click.stop="changeLiked()"
                    style="padding: 0px;"
                >
                    <v-icon middle color="red accent-4" icon>mdi-heart</v-icon>
                </v-btn>
                <v-btn color="red accent-4" icon v-else @click.stop="!changeLiked()">
                    <v-icon middle color="red accent-4">mdi-heart-outline</v-icon>
                </v-btn>
            </v-card-actions>
            </v-img>
           
        </v-card>
        <div style="margin-top:20px; color:black;">
            <div style="font-weight:bold;">{{ item.name }}</div>
            <div style="font-size:15px;margin-top:3px;">{{ item.price }} CASH</div>
        </div>
</div>
</template>

<script>
import {findLikedList} from "@/api/item";
import {deleteBookMark, bookMarkSave} from "@/api/bookmark";
import {getPrice} from "@/utils/itemInventory";

export default {
    props: ['item'],
    computed: {
        imgPath() {
            return process.env.VUE_APP_BACKEND + 'api/items/images/' + this.item.id + "_1";
            // return "https://picsum.photos/id/11/100/60";
        }
    },
    data() {
        return {
            userLiked: [],
            isLiked: false
        }
    },
    created() {
        console.log(this.item);
        var time = this.item.registeredAt.split("T");
        this.item.registeredAt = time[0];
        this.getLikedList();
    },
    methods: {
        getLikedList() {
            const vm = this;
            vm.isLiked = false
            findLikedList(this.$store.state.user.id, function (response) {
                    vm.userLiked = response
                    if (response.data.length > 0) {
                        if(response.data.includes(vm.item.id*1)) {
                            vm.isLiked = true;
                        }
                    }
                },
                err => {
                    alert(err)
                }
            );
        },
        checkLiked() {
            if (this.$store.state.isSigned !== null) {
                if(this.isLiked == false)
                    return false
                else
                    return true;
            }
        },
        changeLiked() {
            const vm = this;
            // if (this.userLiked.data.includes(this.item.id)) {
            if(this.isLiked) {
                deleteBookMark(this.$store.state.user.id, this.item.id, function (response) {
                        vm.getLikedList()
                    },
                    err => {
                        alert(err)
                    }
                );
            } else {
                const body = {
                    userId: vm.$store.state.user.id,
                    itemId: vm.item.id
                };
                bookMarkSave(body, function (response) {
                        vm.getLikedList()
                    },
                    err => {
                        alert(err)
                    }
                );
            }

        }
    }
};
</script>

<style>

.card-product__imgOverlay li button i,.card-product__imgOverlay li button span{font-size:15px;color:#fff;vertical-align:middle}.card-product__imgOverlay li button:hover{background:#384aeb}.card-product__imgOverlay li:not(:last-child){margin-right:5px}.card-product__title a{color:#222}.card-product__price{font-size:18px;font-weight:500}.card-product:hover .card-product__imgOverlay{opacity:1;z-index:1;transform:translateY(0)}.card-product:hover .card-product__title a{color:#384aeb}@media (min-width: 1200px){.offer{background-size:100% 100%}}.offer__content{padding:100px 0 110px 0}@media (min-width: 1000px){.offer__content{padding:122px 0 135px 0}}.offer__content h3{font-size:35px;font-weight:400;margin-bottom:20px}@media (min-width: 650px){.offer__content h3{font-size:50px;margin-bottom:25px}}.offer__content h4{font-size:30px}.offer__content p{color:#555555}#bestSellerCarousel .owl-prev,#bestSellerCarousel .owl-next{width:40px;height:40px;background:#f1f6f7}#bestSellerCarousel .owl-prev i,#bestSellerCarousel .owl-prev span,#bestSellerCarousel .owl-next i,#bestSellerCarousel .owl-next span{font-size:15px;color:#384aeb;line-height:40px}#bestSellerCarousel .owl-prev:hover,#bestSellerCarousel .owl-next:hover{background:#384aeb}#bestSellerCarousel .owl-prev:hover i,#bestSellerCarousel .owl-prev:hover span,#bestSellerCarousel .owl-next:hover i,#bestSellerCarousel .owl-next:hover span{color:#fff}#bestSellerCarousel .owl-prev{position:absolute;top:30%;transform:translateY(-30%);left:-20px}@media (min-width: 1340px){#bestSellerCarousel .owl-prev{left:-105px}}#bestSellerCarousel .owl-next{position:absolute;top:30%;transform:translateY(-30%);right:-20px}@media (min-width: 1340px){#bestSellerCarousel .owl-next{right:-105px}}.card-blog{border:0}.card-blog .card-body{padding:25px 25px 25px 0}

#font {
  font-family: Helvetica, Arial, sans-serif;
}

.item-card img {
    height: 180px;
    width: 180px;
}

</style>
