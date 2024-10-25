<script setup>
import OrdersBgController from '@/background/OrdersBgController.vue';
import utils from '@/utils';
import axios from 'axios';
import { ref } from 'vue';

const props = defineProps([
    "orderid",
    "isbackground"
])
const isdataready = ref(false)
const orderdata = ref({})
const itemdata = ref({})
const statusMap={
    0:"已取消",
    1:"已下单未付款",
    2:"已付款",
    3:"已发货",
    4:"已取消"
}
axios.get(utils.host + "/get_order?orderid=" + props.orderid).then(response => {
    orderdata.value = response.data
    orderdata.value.statusName="订单状态："+statusMap[orderdata.value.status]
    axios.get(utils.host + "/get_item?id=" + response.data.itemid).then(rresponse => {
        itemdata.value = rresponse.data.result
        isdataready.value = true
    })

})
</script>

<template>
    <div class="order_container" v-if="isdataready">
        <img :src="itemdata.imgurl">
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
        </div>
        <OrdersBgController :id="props.orderid" v-if="props.isbackground"/>
    </div>
</template>
<style>
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