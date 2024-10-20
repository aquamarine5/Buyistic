<script setup>
import AccountStatusDisplayer from './account/AccountStatusDisplayer.vue';
</script>

<template>
    <div class="app_topbar">
        <div class="app_title">
            <slot></slot>
        </div>
        <AccountStatusDisplayer/>
    </div>
</template>

<style>

.app_topbar{
    background-color:blueviolet;
}
.app_title{
    padding-top: 15px;
    padding-inline: 20px;
    height: 40px;
    font-size: larger;
    color:#f5f5f5;
    font-weight: 800;
}</style>