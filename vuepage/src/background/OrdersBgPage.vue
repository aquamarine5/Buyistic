<script setup>
import Topbar from '@/Topbar.vue';
import { ref } from 'vue';
import OrderDisplayer from '@/orders/OrderDisplayer.vue';
import wnetwork from '@/wnetwork';
const ordersData = ref([])
wnetwork.get("/background/orders/get_all").then(response => {
    ordersData.value = response.data.data.orders
})
</script>

<template>
    <Topbar :isbackground="true">
        Buyistic: 全部订单
    </Topbar>
    <div class="orders_container">
        <div class="orders_item" v-for="order in ordersData" :key="order.createTime">
            <OrderDisplayer :orderid="order.orderid" :isbackground="true" />
        </div>
    </div>
</template>

<style>
.orders_container {
    margin: 16px;
}
</style>