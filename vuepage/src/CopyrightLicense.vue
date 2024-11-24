<!--
 * @Author: aquamarine5 && aquamarine5_@outlook.com
 * Copyright (c) 2024 by @aquamarine5, RC. All Rights Reversed.
-->
<!--
 * @Anthor: aquamarine5 aquamarine5_@outlook.com
 * Copyright (c) 2024 by @aquamarine5, RC. All Rights Reversed.
-->
<script setup>
import wnetwork from './wnetwork';

function goto() {
    window.open("https://beian.mps.gov.cn/#/query/webSearch")
}
</script>

<template>
    <div class="copyright_container">
        <div class="copyright_text">
            Copyright © 2024 AQ,RC., All Rights Reserved.
        </div>
        <div class="copyright_text">
            Author: @海蓝色的咕咕鸽, @aquamarine5, Github#Buyistic
        </div>
        <div class="netsafe_container" @click="goto">
            <img :src="wnetwork.IMGHOST + '/netsafe.png'" class="netsafe_logo" alt="Netsafe Logo">
            冀ICP备2024089369号
        </div>
    </div>
</template>

<style scoped>
.netsafe_logo {
    width: 20px;
    height: 20px;
    margin-right: 4px;
}

.copyright_container {
    display: flex;
    width: 100%;
    justify-content: center;
    flex-direction: column;
    align-items: center;
    padding-bottom: 15px;
}

.copyright_text {
    color: gray;
    font-size: small;
}

.netsafe_container {
    display: flex;
    align-items: center;
    justify-content: center;
    color: gray;
    font-size: small;
}
</style>
