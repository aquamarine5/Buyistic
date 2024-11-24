<!--
 * @Author: aquamarine5 && aquamarine5_@outlook.com
 * Copyright (c) 2024 by @aquamarine5, RC. All Rights Reversed.
-->
<script setup>
import { ElButton } from 'element-plus';
import { useRouter } from 'vue-router';
const router = useRouter()
const props = defineProps([
    "imgurl",
    "title",
    "detail",
    "nowprice",
    "rawprice",
    "itemid",
    "type",
    "isbackground"
])

function jumpToBuyPage() {
    router.push({
        name: "buy",
        params: {
            id: props.itemid
        }
    })
}
</script>

<template>
    <div class="shopping_item">
        <img class="shopping_img" :src="props.imgurl">
        <div class="shopping_content">
            <div class="shopping_text" @click="jumpToBuyPage">
                <div class="shopping_title">
                    {{ props.title }}
                </div>
                <div class="shopping_detail">
                    {{ props.detail }}
                </div>
            </div>
            <div class="shopping_controller">
                <div class="shopping_price">
                    <div class="shopping_nowprice">
                        {{ props.nowprice }}
                    </div>
                    <div class="shopping_rawprice">
                        {{ props.rawprice }}
                    </div>
                </div>

                <div class="shopping_buttons" v-if="!props.isbackground">
                    <ElButton v-wave class="shopping_btn_buy" type="primary" @click="jumpToBuyPage">
                        购买
                    </ElButton>
                </div>
            </div>
            <div class="background_slot" v-if="props.isbackground">
                <slot></slot>
            </div>
        </div>

    </div>
</template>

<style>
.shopping_item {
    display: flex;
    margin: 6px;
    padding: 6px;
    border-radius: 10px;
    border-width: 2px;
    border-color: #666;
    border-style: solid;
}

.shopping_img {
    padding: 6px;
    max-width: 150px;
    max-height: 150px;
    border-radius: 20px;
}

.shopping_controller {
    display: flex;
}

.shopping_buttons {
    margin-left: auto;
    justify-self: flex-end;
}

.shopping_content {
    padding-left: 6px;
    width: 100%;
}

.shopping_title {
    font-weight: 600;
    padding-top: 5px;
}

.shopping_detail {
    padding-top: 3px;
    font-weight: 400;
    color: gray;
    font-size: small;
}

.shopping_price {
    display: flex;
    align-items: center;
    font-family: "Gilroy", sans-serif;
}

.shopping_text {
    cursor: pointer;
}

.shopping_nowprice {
    font-weight: 800;
    color: red;
    font-size: larger;
}

.shopping_rawprice {
    font-weight: 400;
    padding-top: 5px;
    color: gray;
    font-size: smaller;
    text-decoration: line-through;
    font-family: "GilroyRegular", sans-serif;
    padding-left: 6px;
}
</style>