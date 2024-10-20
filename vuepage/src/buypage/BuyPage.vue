<script setup>
import Topbar from '@/Topbar.vue';
import utils from '@/utils';
import axios from 'axios';
import { ref } from 'vue';
import { useRoute } from 'vue-router';

const props=defineProps([
    "id"
])
const data=ref({})

axios.get(utils.host+"/get_item?id="+useRoute().params.id).then(response=>{
    data.value=response.data.result
})
</script>

<template>
    <Topbar>
        Buyistic: 详情
    </Topbar>
    <div class="buypage_container" v-if="data!={}">
        <img class="buypage_img" :src="data.imgurl">
        <div class="buypage_content">
            <div class="buypage_title">
                {{ data.title }}
            </div>
            <div class="buypage_detail">
                {{ data.detail }}
            </div>
            <div class="buypage_prices">
                <div class="buypage_nowprice">
                    {{ data.nowprice }}
                </div>
                <div class="buypage_rawprice">
                    {{ data.rawprice }}
                </div>
            </div>
        </div>
    </div>
    <div class="buypage_wait" v-else>
        请等待网络响应。
    </div>
</template>

<style>
.buypage_container{
    margin: 16px;
}
.buypage_img{
    width: 1vw;
}
.buypage_title{
    font-weight: 600;
    font-size: larger;
}
.buypage_detail{
    color:gray;
    font-size: small;
}
.buypage_prices{
    display: flex;
    margin-block: 8px;
    align-items: center;
}
.buypage_rawprice{
    text-decoration: line-through;
    padding-left: 5px;
    font-size: smaller;
    color:gray;
}
.buypage_nowprice{
    color:red;
    font-weight: 600;
    font-size: larger;
}
</style>