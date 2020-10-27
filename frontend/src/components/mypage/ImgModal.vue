<template>
    <div>
        <div>
            <v-img
                :src="image"
                aspect-ratio="1"
                class="grey lighten-2"
            ></v-img>
        </div>
        <div class="file">
            <form enctype="multipart/form-data">
                <v-file-input
                    class="fileInput"
                    label="File"
                    @change="onFileChange"
                    accept="image/*"
                ></v-file-input>
            </form>
            <v-btn text color="595959" v-on:click="addItem">등록하기</v-btn>
        </div>
    </div>
</template>

<script>
import {save} from "@/api/user";

export default {
    props:['imageModal'],
    data() {
        return {
            selectedFile: "",
            file: "",
            image: "",
            user: {
                id: this.$store.state.user.id,
                name: "",
                email: ""
            },
        }
    },
    methods: {
        onFileChange(input) {
            this.file = input;
            console.log(this.file);
            this.image = URL.createObjectURL(this.file);
        },
        addItem() {
            const vm = this;
            const data = new FormData();
            const file = this.file;
            data.append("file", this.file);
            save(
                // 사진 등록
                data,
                vm.user.id,
                function (success) {
                    alert("이미지 등록 성공!");
                    vm.$emit('closeThis');
                },
                function (error) {
                    console.log(error);
                },
                function (final) {
                    console.log("안녕");
                }
            );
            console.log(vm.imageModal);
        },
    }
}
</script>

<style scoped>

</style>