<script setup>
import ShoppingItem from '@/mainpage/ShoppingItem.vue';
import Topbar from '@/Topbar.vue';
import utils from '@/utils';
import axios from 'axios';
import ItemsBgController from './ItemsBgController.vue';
import { ref } from 'vue';
import { ElButton, ElCollapse, ElCollapseItem, ElInput, ElInputNumber, ElNotification } from 'element-plus';
import wnetwork from '@/wnetwork';

wnetwork.get("/background/items/get_all").then(response => {
    itemsdata.value = response.data.data
    isdataready.value = true
})


</script>

<template>
    <Topbar>
        Buyistic: 商品后台
    </Topbar>
    <ElCollapse>
        <ElCollapseItem title="新建商品" name="new">
            <div class="just_flex">
                上传封面图
                <ElButton type="primary" @click="uploadButtonClicked">
                    <input type="file" ref="uploadInput" style="display: none;" @change="uploadInputChanged">
                </ElButton>
            </div>
            <div class="just_flex">
                输入标题
                <ElInput v-model="reftitle" />
            </div>
            <div class="just_flex">
                输入描述
                <ElInput v-model="refdetail" />
            </div>
            <div class="just_flex">
                输入现在价格
                <ElInputNumber v-model="refnowprice" min="0" />
            </div>
            <div class="just_flex">
                输入原先价格
                <ElInputNumber v-model="refrawprice" min="0" />
            </div>
            <ElButton type="primary" @click="addItem">
                提交
            </ElButton>
        </ElCollapseItem>
    </ElCollapse>
    <div class="bgitems_container" v-if="isdataready">
        <ShoppingItem v-for="item in itemsdata" :imgurl="item.imgurl" :detail="item.detail" :title="item.title"
            :nowprice="item.nowprice" :rawprice="item.rawprice" :itemid="item.id" :type="item.type"
            :isbackground="true">
            <ItemsBgController :itemid="item.id" />
        </ShoppingItem>
    </div>
</template>

<style>
.just_flex {
    display: flex;
}
</style>

<script>
const isdataready = ref(false)
const itemsdata = ref({})
const reftitle = ref("")
const refdetail = ref("")
const refnowprice = ref(1)
const refrawprice = ref(1)
var imgurl = null
export default {
    data() {
        return {
            uploadButtonClicked: () => {
                this.$refs.uploadInput.click()
            },
            uploadInputChanged: () => {
                let formData = new FormData()
                formData.append("file", this.$refs.uploadInput.files[0])
                axios.post("/background/file/upload/", formData).then(response => {
                    imgurl = utils.imgHost + response.data.filename
                })
            },
            addItem: () => {
                if (reftitle.value == "" || imgurl == null) {
                    ElNotification({
                        title: "必填项不能为空！",
                        type: "error"
                    })
                    return
                }
                let formdata = new FormData()
                formdata.append("imgurl", imgurl)
                formdata.append("title", reftitle.value)
                formdata.append("detail", refdetail.value)
                formdata.append("nowprice", refnowprice.value)
                formdata.append("rawprice", refrawprice.value)
                formdata.append("type", 1)
                formdata.append("categories", "")
                formdata.append("introductions", "")
                axios.post("/background/items/add", formdata).then(response => {
                    if (response.data.result == "OK") {
                        location.reload()
                    }
                    else {
                        ElNotification({
                            title: response.data.error_msg,
                            type: "error"
                        })
                    }
                })
            }
        }
    }
}
</script>