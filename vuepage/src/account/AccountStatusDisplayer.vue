<script setup>
import utils from '@/utils';
import { ArrowDown } from '@element-plus/icons-vue/dist/types';
import axios from 'axios';
import { ElDropdown, ElDropdownItem, ElDropdownMenu, ElIcon } from 'element-plus';

const uuid = ref(localStorage.getItem("uuid"))
const userdata = ref({})
if (uuid != null) {
    axios.get(utils.host + "/get_user?uuid=" + uuid.value).then(response => {
        userdata.value = response.data.result
    })
}

function logout() {
    localStorage.removeItem("uuid")
    uuid.value = null
}
</script>
<template>
    <div class="accdper_container">
        <div class="accdper_login" v-if="uuid != null">
            <ElDropdown>
                <span class="accdper_username">
                    {{ userdata.name }}
                </span>
                <ElIcon>
                    <ArrowDown />
                </ElIcon>
                <ElDropdownMenu>
                    <ElDropdownItem>
                        <RouterLink to="orders">订单</RouterLink>
                    </ElDropdownItem>
                    <ElDropdownItem @click="logout">
                        登出
                    </ElDropdownItem>
                </ElDropdownMenu>
            </ElDropdown>
        </div>
        <div class="accdper_nologin" v-else>
            <RouterLink to="login">登录</RouterLink>
        </div>
    </div>
</template>