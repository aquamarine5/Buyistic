<script setup lang="ts">
import Topbar from '@/Topbar.vue';
import utils from '@/utils';
import axios from 'axios';
import { ref } from 'vue';
const ordersData=ref({})
axios.get(utils.host+"/orders?userid="+localStorage.getItem("userid")).then(response=>{
    ordersData.value=response.data.result
})
</script>

<template>
    <Topbar>
        Buyistic: 全部订单
    </Topbar>
    <div class="orders_container">

    </div>
</template>