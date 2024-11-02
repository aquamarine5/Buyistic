<script setup>

import wnetwork from '@/wnetwork';

import { ElButton, ElCollapse, ElCollapseItem, ElOption, ElSelect } from 'element-plus';
import { ref } from 'vue';

const property = ref()
const username = ref("")
const props = defineProps([
    "orderid",
    "userid"
])
wnetwork.get("/get_user?userid=" + props.userid).then(response => {
    username.value = response.data.data.result.name
})
const statusMap = {
    0: "已取消",
    1: "已下单未付款",
    2: "已付款",
    3: "已发货",
    4: "已完成"
}
function edit() {
    wnetwork.get(
        "/background/orders/change_status?orderid=" + props.orderid + "&status=" + property.value).then(() => {
            property.value = ""
            location.reload()
        })
}
</script>

<template>
    <div class="orderbg_userinfo">
        用户: {{ username }}
    </div>
    <ElCollapse>
        <ElCollapseItem title="更改信息" name="edit">
            <div style="display: flex;">
                <ElSelect v-model="property" placeholder="更改状态" style="width: 200px;">
                    <ElOption v-for="(label, value) in statusMap" :key="value" :label="label" :value="value" />
                </ElSelect>
                <ElButton v-wave type="primary" @click="edit">提交</ElButton>
            </div>
        </ElCollapseItem>
    </ElCollapse>
</template>