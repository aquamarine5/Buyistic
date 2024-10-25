<script setup>
import Topbar from '@/Topbar.vue';
import utils from '@/utils';
import axios from 'axios';
import { ref } from 'vue';
import OrderDisplayer from '@/orders/OrderDisplayer.vue';
const ordersData = ref([])
axios.get(utils.host + "/background/orders/get_all").then(response => {
    ordersData.value = response.data.orders
})
</script>

<template>
    <Topbar>
        Buyistic: 全部订单
    </Topbar>
    <div class="orders_container">
        <div class="orders_item" v-for="order in ordersData">
            <OrderDisplayer :orderid="order.orderid" :isbackground="true"/>
        </div>
    </div>
</template>

<style>
.orders_container {
    margin: 16px;
}
</style>