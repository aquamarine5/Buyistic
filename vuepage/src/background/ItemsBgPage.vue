<script setup>
import ShoppingItem from '@/mainpage/ShoppingItem.vue';
import Topbar from '@/Topbar.vue';
import utils from '@/utils';
import axios from 'axios';
import ItemsBgController from './ItemsBgController.vue';
import { ref } from 'vue';
import { ElCollapse, ElCollapseItem } from 'element-plus';
const isdataready=ref(false)
const itemsdata=ref({})
axios.get(utils.host+"/background/items/get_all").then(response=>{
    itemsdata.value=response.data.data
    isdataready.value=true
})
</script>

<template>
    <Topbar>
        Buyistic: 商品后台
    </Topbar>
    <ElCollapse>
        <ElCollapseItem title="新建商品" name="new">
            
        </ElCollapseItem>
    </ElCollapse>
    <div class="bgitems_container" v-if="isdataready">
        <ShoppingItem v-for="item in itemsdata" :imgurl="item.imgurl" :detail="item.detail" :title="item.title"
            :nowprice="item.nowprice" :rawprice="item.rawprice" :itemid="item.id" :type="item.type" :isbackground="true">
            <ItemsBgController :itemid="item.id"/>
        </ShoppingItem>
    </div>
</template>