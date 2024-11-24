<!--
 * @Author: aquamarine5 && aquamarine5_@outlook.com
 * Copyright (c) 2024 by @aquamarine5, RC. All Rights Reversed.
-->
<script setup>
const props = defineProps([
    "imgpaths"
])
</script>

<template>
    <div class="splash_displayer">
        <div class="splash_img">
            <img :src="props.imgpaths[0]">
        </div>
    </div>

</template>