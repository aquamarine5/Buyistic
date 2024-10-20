<script setup>
import Topbar from '@/Topbar.vue';
import utils from '@/utils';
import axios from 'axios';
import { ElButton } from 'element-plus';
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const itemData=ref({})
const isLoaded=ref(false)
axios.get(utils.host+"/get_item?id="+useRoute().params.id).then(response=>{
    isLoaded=true
    itemData.value=response.data.result
})
function afterpay(){
    axios.get(utils.host+"/buyit?userid="+localStorage.getItem("userid")+"&itemid="+useRoute().params.id).then(response=>{
        useRouter().push('orders')
    })
}
</script>

<template>
    <Topbar>
        Buyistic: 付款
    </Topbar>
    <div class="payment_container" v-if="isLoaded">
        <div class="payment_price">
            <span>
                请扫码支付并备注用户名和商品名称 然后加好友确认付款状态
            </span>
            <div class="payment_item">
                <img class="payment_img" :src="itemData.imgurl">
                <div style="display: flex;">
                    <div class="payment_title" >
                        {{ itemData.title }}
                    </div>
                    <div class="payment_const">
                        {{ itemData.nowprice }}
                    </div>
                </div>
            </div>
        </div>
    </div>
    <img :src="utils.host+'/assets/img/pay_qrcode.png'">
    <img :src="utils.host+'/assets/img/contact_qrcode.png'">
    <div class="payment_afterpay">
        <ElButton @click="afterpay" type="primary" v-wave>
            付款完成
        </ElButton>
        <ElButton @click="useRouter().back()" type="error" v-wave>
            不想要了
        </ElButton>
    </div>
</template>