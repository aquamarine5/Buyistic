<script setup>

import wnetwork from '@/wnetwork';
import { ElButton, ElCollapse, ElCollapseItem, ElInput, ElOption, ElSelect } from 'element-plus';
import { ref } from 'vue';
const property = ref("")
const changedvalue = ref("")
const props = defineProps([
    "itemid"
])
const options = {
    "nowprice": "当前价格",
    "rawprice": "原价",
    "title": "标题",
    "detail": "描述",
    "type": "商品状态"
}
function edit() {
    wnetwork.get(
        "/background/items/change_property?property=" + property.value +
        "&value=" + changedvalue.value + "&id=" + props.itemid).then(() => {
            property.value = ""
            changedvalue.value = ""
            location.reload()
        })
}
</script>

<template>
    <ElCollapse>
        <ElCollapseItem title="更改信息" name="edit">
            <div style="display: flex; width: 200px;">
                <ElSelect v-model="property" placeholder="选择一项要更改的项" style="width: 200px;">
                    <ElOption v-for="(label, value) in options" :key="value" :label="label" :value="value" />
                </ElSelect>
                <ElInput v-model="changedvalue" />
                <ElButton v-wave type="primary" @click="edit">提交</ElButton>
            </div>

            <div v-if="property == 'type'">
                对于商品状态，0为隐藏商品，1为正常商品
            </div>
        </ElCollapseItem>
    </ElCollapse>
</template>