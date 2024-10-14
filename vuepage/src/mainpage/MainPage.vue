<script setup>
import axios from 'axios';
import ShoppingItem from './ShoppingItem.vue';
import utils from '../utils';
import { ref } from 'vue';

var items_data=ref([])
axios.get(utils.host + "/get_items").then(
    response => {
        items_data.value=response.data.data
    }
)
</script>

<template>
    <div class="app_topbar">
        <div class="app_title">
            Buyistic
        </div>
    </div>
    <div class="items_container">
        <ShoppingItem v-for="item in items_data"
            :imgurl="item.imgurl" :detail="item.detail" :title="item.title"
            :nowprice="item.nowprice" :rawprice="item.rawprice" :itemid="item.id"/>
    </div>
</template>

<style>
.app_topbar{
    background-color:blueviolet;
}
.app_title{
    padding-top: 15px;
    padding-inline: 20px;
    height: 40px;
    font-size: larger;
    color:#f5f5f5;
    font-weight: 800;
}
</style>