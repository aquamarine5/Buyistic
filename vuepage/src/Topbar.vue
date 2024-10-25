<script setup>
import { useRouter } from 'vue-router';
import AccountStatusDisplayer from './account/AccountStatusDisplayer.vue';
const router=useRouter()
function gotoHome(){
    router.push({
        name:"app"
    })
}
</script>

<template>
    <div class="app_topbar">
        <div class="app_title" @click="gotoHome">
            <slot></slot>
        </div>
        <AccountStatusDisplayer />
    </div>
</template>

<style>
.app_topbar {
    background-color: blueviolet;
    display: flex;
    align-items: center;
}

.app_title {
    cursor: pointer;
    white-space: nowrap;
    padding-top: 15px;
    padding-inline: 20px;
    height: 40px;
    font-size: larger;
    color: #f5f5f5;
    font-weight: 800;
}
</style>