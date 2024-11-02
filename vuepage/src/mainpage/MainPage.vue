<script setup>

import ShoppingItem from './ShoppingItem.vue';
import { ref } from 'vue';
import Topbar from '@/Topbar.vue';
import wnetwork from '@/wnetwork';

var items_data = ref([])
wnetwork.get("/get_items").then(
    response => {
        items_data.value = response.data.data.data
    }
)
</script>

<template>
    <Topbar>
        Buyistic
    </Topbar>
    <div class="items_container">
        <ShoppingItem v-for="item in items_data" :imgurl="item.imgurl" :detail="item.detail" :title="item.title"
            :nowprice="item.nowprice" :rawprice="item.rawprice" :itemid="item.id" :type="item.type"
            :isbackground="false" :key="item.id"></ShoppingItem>
    </div>
</template>

<style></style>