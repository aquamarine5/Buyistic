<!--
 * @Author: aquamarine5 && aquamarine5_@outlook.com
 * Copyright (c) 2024 by @aquamarine5, RC. All Rights Reversed.
-->
<script setup>
import Topbar from '@/Topbar.vue';
import { ref } from 'vue';
import OrderDisplayer from './OrderDisplayer.vue';
import wnetwork from '@/wnetwork';

const ordersData = ref({})
wnetwork.get("/orders?userid=" + localStorage.getItem("userid")).then(response => {
    ordersData.value = response.data.data.result
})
</script>

<template>
    <Topbar>
        Buyistic: 全部订单
    </Topbar>
    <div class="orders_container">
        <div class="orders_item" v-for="order in ordersData" :key="order.orderid">
            <OrderDisplayer :orderid="order.orderid" :isbackground="false" />
        </div>
    </div>
</template>

<style>
.orders_container {
    margin: 16px;
}
</style>