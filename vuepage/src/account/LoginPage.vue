<script setup>
import Topbar from '@/Topbar.vue';
import { UserFilled } from '@element-plus/icons-vue/dist/types';
import { CircleCloseFilled } from '@element-plus/icons-vue/dist/types';
import { SuccessFilled } from '@element-plus/icons-vue/dist/types';
import { InfoFilled } from '@element-plus/icons-vue/dist/types';
import { ElButton, ElCheckbox, ElIcon, ElInput } from 'element-plus';
import { useRouter } from 'vue-router';

const input_username = ref("")
const input_password = ref("")
const showpassword = ref(false)
const response = ref({})
function login() {

}
</script>

<template>
    <Topbar>
        Buyistic: Login or Register an account
    </Topbar>
    <ElInput v-model="input_username" class="login_input" placeholder="输入用户名" />
    <ElInput v-model="input_password" :type="showpassword ? 'text' : 'password'" placeholder="输入密码" class="login_input" />
    <ElCheckbox v-model="showpassword">
        显示密码
    </ElCheckbox>
    <ElButton type="primary" @click="login">
        登录
    </ElButton>
    <div class="login_tips">
        <ElIcon>
            <InfoFilled />
        </ElIcon>
        若您没有账号，点击登录时系统会为你自动注册账号。
    </div>
    <div class="login_response" v-if="response != {}"
        :style="'background-color: ' + response.status == 'login' ? 'green' : (response.status == 'newaccount' ? 'yellow' : 'red') + ';'">
        <ElIcon>
            <SuccessFilled v-if="response.status == 'login'" />
            <UserFilled v-else-if="response.status == 'newaccount'" />
            <CircleCloseFilled v-else />
        </ElIcon>
        <div class="login_response_text">
            <span v-if="response.status == 'login'">
                登录成功！
            </span>
            <span v-else-if="response.status == 'newaccount'">
                检测到是新账号，已成功注册！
            </span>
            <span v-else>
                密码错误！
            </span>
        </div>
        <ElButton type="primary" @click="useRouter().push('app')">
            跳转至主页
        </ElButton>
    </div>
</template>