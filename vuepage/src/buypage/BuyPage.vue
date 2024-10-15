<script setup>
import Topbar from '@/Topbar.vue';
import utils from '@/utils';
import axios from 'axios';
import { ref } from 'vue';

const props=defineProps([
    "id"
])
const data=ref({})

axios.get(utils.host+"/get_item?id="+props.id).then(response=>{
    data.value=response.data
})
</script>

<template>
    <Topbar>
        Buyistic: 详情
    </Topbar>
    <div class="buypage_container" v-if="data!={}">

    </div>
    <div class="buypage_wait" v-else>
        请等待网络相应。
    </div>
</template>