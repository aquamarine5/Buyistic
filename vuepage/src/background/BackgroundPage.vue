<script setup>
import Topbar from '@/Topbar.vue';
</script>

<template>
    <Topbar>
        Buyistic: 后台
    </Topbar>
    <RouterLink :to="{ 'name': 'bg_accounts' }">
        跳转至用户后台
    </RouterLink>
    <RouterLink :to="{ 'name': 'bg_items' }">
        跳转至商品后台
    </RouterLink>
    <RouterLink :to="{ 'name': 'bg_orders' }">
        跳转至订单后台
    </RouterLink>
</template>