<!--
 * @Author: aquamarine5 && aquamarine5_@outlook.com
 * Copyright (c) 2024 by @aquamarine5, RC. All Rights Reversed.
-->
<script setup>
import Topbar from '@/Topbar.vue';

import wnetwork from '@/wnetwork';

import { ElButton, ElNotification } from 'element-plus';
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const router = useRouter()
const route = useRoute()
const data = ref({})

wnetwork.get("/get_item?id=" + route.params.id).then(response => {
    data.value = response.data.data.result
})

function buyit() {
    if (localStorage.getItem("userid") == null) {
        ElNotification({
            title: "请先登录！",
            type: "warning"
        })
        router.push({
            name: "login"
        })
    }
    else {
        router.push({
            name: "pay",
            params: {
                id: route.params.id
            }
        })
    }
}
</script>

<template>
    <Topbar>
        Buyistic: 商品详情
    </Topbar>
    <div class="buypage_container" v-if="data != {}">
        <img class="buypage_img" :src="data.imgurl">
        <div class="buypage_content">
            <div class="buypage_title">
                {{ data.title }}
            </div>
            <div class="buypage_detail">
                {{ data.detail }}
            </div>
            <div class="buypage_prices">
                <div class="buypage_nowprice">
                    {{ data.nowprice }}
                </div>
                <div class="buypage_rawprice">
                    {{ data.rawprice }}
                </div>
            </div>
        </div>
        <ElButton v-wave type="primary" @click="buyit">
            购买
        </ElButton>
    </div>
    <div class="buypage_wait" v-else>
        请等待网络响应。
    </div>
</template>

<style>
.buypage_container {
    margin: 16px;
}

.buypage_img {
    max-width: 300px;
    max-height: 300px;
    border-radius: 20px;
}

.buypage_title {
    font-weight: 600;
    font-size: larger;
}

.buypage_detail {
    color: gray;
    font-size: small;
}

.buypage_prices {
    display: flex;
    margin-block: 8px;
    align-items: center;
    font-family: "Gilroy", sans-serif;
}

.buypage_rawprice {
    text-decoration: line-through;
    padding-left: 5px;
    padding-top: 5px;
    font-size: smaller;
    font-family: "GilroyRegular", sans-serif;
    color: gray;
}

.buypage_nowprice {
    color: red;
    font-weight: 600;
    font-size: larger;
}
</style>