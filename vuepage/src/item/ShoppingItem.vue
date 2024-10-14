<script setup lang="ts">
import { ElButton } from 'element-plus';

const props=defineProps([
    "imgurl",
    "title",
    "detail",
    "nowprice",
    "rawprice",
    "itemid"
])
</script>

<template>
    <div class="shopping_item">
        <img class="shopping_img">
        <div class="shopping_content">
            <div class="shopping_text">
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
                
                <div class="shopping_buttons">
                    <ElButton class="shopping_btn_buy"/>
                </div>
            </div>
        </div>
    </div>
</template>

<style>

</style>