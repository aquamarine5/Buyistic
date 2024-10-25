<script setup>
import utils from '@/utils';
import axios from 'axios';
import { ElButton, ElCollapse, ElCollapseItem, ElInput, ElNotification, ElOption, ElSelect } from 'element-plus';
const property = ref()
const props = defineProps([
    "id"
])
const statusMap={
    0:"已取消",
    1:"已下单未付款",
    2:"已付款",
    3:"已发货",
    4:"已取消"
}
function edit(){
    axios.get(utils.host+
        "/background/orders/change_status?orderid="+props.id+"&status="+property.value).then(response=>{
            property.value=""
            ElNotification({
                title:"成功",
                type:"success"
            })
    })
}
</script>

<template>
    <ElCollapse>
        <ElCollapseItem title="更改信息" name="edit">
            <div style="display: flex;">
                <ElSelect v-model="property" placeholder="选择一项要更改的项">
                    <ElOption v-for="(value, label) in statusMap" 
                        :key="value" :label="label" :value="value" />
                </ElSelect>
                <ElButton v-wave type="primary" @click="edit">提交</ElButton>
            </div>
        </ElCollapseItem>
    </ElCollapse>
</template>