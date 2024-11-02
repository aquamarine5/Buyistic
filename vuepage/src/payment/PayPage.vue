<script setup>
import Topbar from '@/Topbar.vue';

import wnetwork from '@/wnetwork';

import { ElButton, ElNotification } from 'element-plus';
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const itemData = ref({})
const isLoaded = ref(false)
const route = useRoute()
const router = useRouter()
wnetwork.get("/get_item?id=" + useRoute().params.id).then(response => {
    isLoaded.value = true
    itemData.value = response.data.data.result
})
function afterpay() {
    wnetwork.get("/buyit?userid="
        + localStorage.getItem("userid") + "&itemid=" + route.params.id + "&price=" + itemData.value.nowprice).then(() => {
            ElNotification({
                title: "购买成功！",
                type: "success"
            })
            router.push({
                name: "orders"
            })
        })
}
function back() {
    router.back()
}
</script>

<template>
    <Topbar>
        Buyistic: 付款
    </Topbar>
    <div class="payment_container" v-if="isLoaded">
        <div class="payment_price">
            <span style="font-weight: 700; background-color: yellow; padding: 6px; margin: 8px;">
                （！）请扫码支付并备注用户名和商品名称 然后加好友确认付款状态
            </span>
            <div class="payment_item">
                <img class="payment_img" :src="itemData.imgurl">
                <div class="payment_desc">
                    <div class="payment_title">
                        {{ itemData.title }}
                    </div>
                    <div class="payment_const">
                        {{ itemData.nowprice }}
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="payment_afterpay">
        <ElButton @click="afterpay" type="primary" v-wave size="large">
            付款完成
        </ElButton>
        <ElButton @click="back" type="danger" v-wave size="large">
            不想要了
        </ElButton>
    </div>
    <img class="payment_cimg" :src="wnetwork.IMGHOST + '/pay_qrcode.png'">
    <img class="payment_cimg" :src="wnetwork.IMGHOST + '/contact_qrcode.png'">
</template>

<style>
.payment_afterpay {
    display: flex;
    margin: 16px;
}

.payment_desc {
    padding-inline: 4px;
}

.payment_title {
    font-weight: 600;
}

.payment_const {
    color: red;
    font-weight: 600;
}

.payment_item {
    display: flex;
    margin-top: 6px;
    padding: 8px;
    border-radius: 10px;
    border-width: 2px;
    border-color: #666;
    border-style: solid;
}

.payment_container {
    margin: 16px;
}

.payment_img {
    max-width: 200px;
    max-height: 200px;
}
.payment_cimg {
    width: 50%;
    height: 50%;
}
</style>