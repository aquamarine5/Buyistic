<script setup>
import ShoppingItem from './item/ShoppingItem.vue';
import utils from './utils';
var items_data=[]
axios.get(utils.host + "/get_items").then(
    response => {
        items_data=response.data.data
    }
)
</script>

<template>
    <div class="app_topbar">
        <div class="app_title">
            Shopping
        </div>
    </div>
    <div class="items_container">
        <ShoppingItem v-for="item in items_data"
            :imgurl="item.imgurl" :detail="item.detail" :title="item.title"
            :nowprice="item.nowprice" :rawprice="item.rawprice" :itemid="item.itemid"/>
    </div>
</template>