<script setup>

import wnetwork from '@/wnetwork';
import { ElButton, ElDropdown, ElDropdownItem, ElDropdownMenu, ElIcon } from 'element-plus';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
const uuid = ref(localStorage.getItem("userid"))
const userdata = ref({})
const router = useRouter()
if (uuid.value != null) {
    wnetwork.get("/get_user?userid=" + uuid.value).then(response => {
        userdata.value = response.data.data.result
    })
}
function login() {
    router.push({
        name:"login"
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
                <template #dropdown>
                    <ElDropdownMenu>
                        <ElDropdownItem>
                            <RouterLink :to="{'name':'orders'}">订单</RouterLink>
                        </ElDropdownItem>
                        <ElDropdownItem @click="logout">
                            登出
                        </ElDropdownItem>
                    </ElDropdownMenu>
                </template>
            </ElDropdown>
        </div>
        <div class="accdper_nologin" v-else>
            <ElButton type="primary" @click="login">登录</ElButton>
        </div>
    </div>
</template>
<style>
ElDropdownItem{
    cursor: pointer;
}
.accdper_username {
    color: white;
    cursor: pointer;
}

.accdper_container {
    display: flex;
    justify-content: flex-end;
    width: 100%;
    margin-right: 20px;

}
</style>