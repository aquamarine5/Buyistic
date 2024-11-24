<!--
 * @Author: aquamarine5 && aquamarine5_@outlook.com
 * Copyright (c) 2024 by @aquamarine5, RC. All Rights Reversed.
-->
<!--
 * @Anthor: aquamarine5 && aquamarine5_@outlook.com
 * Copyright (c) 2024 by @aquamarine5, RC. All Rights Reversed.
-->
<script setup>
import ShoppingItem from '@/mainpage/ShoppingItem.vue';
import Topbar from '@/Topbar.vue';
import ItemsBgController from './ItemsBgController.vue';
import { ref } from 'vue';
import { ElButton, ElCollapse, ElCollapseItem, ElInput, ElInputNumber, ElNotification } from 'element-plus';
import wnetwork from '@/wnetwork';

wnetwork.get("/background/items/get_all").then(response => {
    itemsdata.value = response.data.data.data
})

</script>

<template>
    <Topbar :isbackground="true">
        Buyistic: 商品后台
    </Topbar>
    <ElCollapse>
        <ElCollapseItem title="新建商品" name="new" style="padding-inline: 8px;">
            <div class="just_flex">
                封面图：
                <span>{{ imgurl }}</span>
                <ElButton type="primary" @click="uploadButtonClicked">
                    上传
                    <input type="file" ref="uploadInput" style="display: none;" @change="uploadInputChanged">
                </ElButton>
            </div>
            <div class="just_flex">
                输入标题
                <ElInput v-model="reftitle" />
            </div>
            <div class="just_flex">
                输入描述
                <ElInput v-model="refdetail" />
            </div>
            <div class="just_flex">
                输入现在价格
                <ElInputNumber v-model="refnowprice" min="0" />
            </div>
            <div class="just_flex">
                输入原先价格
                <ElInputNumber v-model="refrawprice" min="0" />
            </div>
            <div class="just_flex">
                上传海报图（可多张）
                <ElButton type="primary" @click="uploadSplashButtonClicked">
                    上传
                    <input type="file" ref="uploadSplashInput" style="display: none;"
                        @change="uploadSplashInputChanged">
                </ElButton>
                <div class="bg_splash_img_shower">
                    <!-- eslint-disable-next-line vue/require-v-for-key -->
                    <div class="bg_splash_img" v-for="img in refsplashimgs">
                        {{ img }}
                    </div>
                </div>
            </div>
            <ElButton type="primary" @click="addItem">
                提交
            </ElButton>
        </ElCollapseItem>
    </ElCollapse>
    <div class="bgitems_container">
        <ShoppingItem v-for="item in itemsdata" :imgurl="item.imgurl" :detail="item.detail" :title="item.title"
            :nowprice="item.nowprice" :rawprice="item.rawprice" :itemid="item.id" :type="item.type" :isbackground="true"
            :key="item.id">
            <ItemsBgController :itemid="item.id" />
        </ShoppingItem>
    </div>
</template>

<style>
.just_flex {
    display: flex;
    gap: 12px;
    padding-inline: 10px;
}
</style>

<script>
const itemsdata = ref({})
const reftitle = ref("")
const refdetail = ref("")
const refnowprice = ref(1)
const refrawprice = ref(1)
const refsplashimgs = ref([])
export default {
    data() {
        return {
            imgurl: ref(""),
            uploadSplashButtonClicked: () => {
                this.$refs.uploadSplashInput.click()
            },
            uploadSplashInputChanged: (event) => {
                let formData = new FormData()
                console.log(event.target.files[0])
                formData.append("file", event.target.files[0])
                wnetwork.post("/background/file/upload", formData).then(response => {
                    refsplashimgs.value.push(wnetwork.IMGHOST + response.data.data.filename)
                })
            },
            uploadButtonClicked: () => {
                this.$refs.uploadInput.click()
            },
            uploadInputChanged: (event) => {
                let formData = new FormData()
                console.log(event.target.files[0])
                formData.append("file", event.target.files[0])
                wnetwork.post("/background/file/upload", formData).then(response => {
                    this.imgurl = wnetwork.IMGHOST + response.data.data.filename
                })
            },
            addItem: () => {
                if (reftitle.value == "" || this.imgurl == null || this.imgurl == undefined) {
                    ElNotification({
                        title: "必填项（标题和封面图）不能为空！",
                        type: "error"
                    })
                    return
                }
                let formdata = new FormData()
                formdata.append("imgurl", this.imgurl)
                formdata.append("title", reftitle.value)
                formdata.append("detail", refdetail.value)
                formdata.append("nowprice", refnowprice.value)
                formdata.append("rawprice", refrawprice.value)
                formdata.append("type", 1)
                formdata.append("categories", "")
                formdata.append("introductions", "")
                formdata.append("splashimgs", JSON.stringify(refsplashimgs.value.join))
                wnetwork.post("/background/items/add", formdata).then(() => {
                    location.reload()
                })
            }
        }
    }
}
</script>