<!--
 * @Author: aquamarine5 && aquamarine5_@outlook.com
 * Copyright (c) 2024 by @aquamarine5, RC. All Rights Reversed.
-->
<script setup>
import OrdersBgController from '@/background/OrdersBgController.vue';

import wnetwork from '@/wnetwork';
import { ElButton } from 'element-plus';
import { ref } from 'vue';

const props = defineProps([
    "orderid",
    "isbackground"
])
const isdataready = ref(false)
const orderdata = ref({})
const itemdata = ref({})
const statusMap = {
    0: "已取消",
    1: "已下单未付款",
    2: "已付款",
    3: "已发货",
    4: "已完成"
}
wnetwork.get("/get_order?orderid=" + props.orderid).then(response => {
    orderdata.value = response.data.data
    orderdata.value.statusName = "订单状态：" + statusMap[orderdata.value.status]
    wnetwork.get("/get_item?id=" + response.data.data.itemid).then(rresponse => {
        itemdata.value = rresponse.data.data.result
        isdataready.value = true
    })

})

function cancelOrder() {
    wnetwork.get("/cancel_order?orderid=" + props.orderid).then(() => {
        location.reload()
    })
}
</script>

<template>
    <div class="order_container" v-if="isdataready">
        <img class="order_img" :src="itemdata.imgurl">
        <div class="order_column">
            <div class="order_title">
                {{ itemdata.title }}
            </div>
            <div class="order_price">
                {{ orderdata.price }}
            </div>
            <div class="order_time">
                {{ new Date(orderdata.createTime).toLocaleString("zh") }}
            </div>
            <div class="order_status">
                {{ orderdata.statusName }}
            </div>
            <OrdersBgController :orderid="props.orderid" v-if="props.isbackground" :userid="orderdata.userid" />
            <ElButton type="primary" v-else @click="cancelOrder">
                取消订单
            </ElButton>
        </div>
    </div>
</template>
<style>
.order_img {
    max-width: 200px;
    max-height: 200px;
    border-radius: 20px;
}

.order_container {
    display: flex;
    margin: 6px;
    padding: 6px;
    border-radius: 10px;
    border-width: 2px;
    border-color: #666;
    border-style: solid;
}

.order_title {
    font-weight: 600;
}

.order_time {
    color: gray;
    font-size: small;
}

.order_price {
    color: red;
    font-weight: 600;
}
</style>