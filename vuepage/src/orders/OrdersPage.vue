<script setup>
import Topbar from '@/Topbar.vue';
import utils from '@/utils';
import axios from 'axios';
import { ref } from 'vue';
import OrderDisplayer from './OrderDisplayer.vue';
import wnetwork from '@/wnetwork';
const ordersData = ref({})
wnetwork.get("/orders?userid=" + localStorage.getItem("userid")).then(response => {
    ordersData.value = response.data.result
})
</script>

<template>
    <Topbar>
        Buyistic: 全部订单
    </Topbar>
    <div class="orders_container">
        <div class="orders_item" v-for="order in ordersData">
            <OrderDisplayer :orderid="order.orderid" :isbackground="false"/>
        </div>
    </div>
</template>

<style>
.orders_container {
    margin: 16px;
}
</style>