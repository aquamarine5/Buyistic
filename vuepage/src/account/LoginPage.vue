<!--
 * @Author: aquamarine5 && aquamarine5_@outlook.com
 * Copyright (c) 2024 by @aquamarine5, RC. All Rights Reversed.
-->
<!--
 * @Anthor: aquamarine5 && aquamarine5_@outlook.com
 * Copyright (c) 2024 by @aquamarine5, RC. All Rights Reversed.
-->
<script setup>
import Topbar from '@/Topbar.vue';
import wnetwork from '@/wnetwork';
import { ElButton, ElCheckbox, ElIcon, ElInput, ElNotification } from 'element-plus';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const input_username = ref("")
const input_password = ref("")
const showpassword = ref(false)
const response = ref({})
const showresponse = ref(false)
const router = useRouter()
function login() {
    let formdata = new FormData()
    if (input_username.value == null || input_username.value == "") {
        ElNotification({
            title: "用户名不能为空！",
            type: "warning"
        })
        return
    }
    if (input_password.value == null || input_password.value == "") {
        ElNotification({
            title: "密码不能为空！",
            type: "warning"
        })
        return
    }
    formdata.append("username", input_username.value)
    formdata.append("password", input_password.value)
    wnetwork.post("/login", formdata).then(res => {
        response.value = res.data.data
        showresponse.value = true
        if (res.data.data.status == "login" || res.data.data.status == "newaccount") {
            localStorage.setItem("userid", res.data.data.result.userid)
        }
    })
}

function gotoapp() {
    router.back()
}
</script>

<template>

    <Topbar>
        Buyistic: 登录
    </Topbar>
    <div class="login_container">
        <ElInput v-model="input_username" class="login_input" placeholder="输入用户名" />
        <ElInput v-model="input_password" :type="showpassword ? 'text' : 'password'" placeholder="输入密码"
            class="login_input" />
        <div class="login_btns">

            <ElCheckbox v-model="showpassword">
                显示密码
            </ElCheckbox>
            <ElButton type="primary" @click="login">
                登录
            </ElButton>
        </div>
        <div class="login_tips">
            <ElIcon>
                <InfoFilled />
            </ElIcon>
            若您没有账号，点击登录时系统会为你自动注册账号。
        </div>
        <div class="login_response" v-if="showresponse"
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
            <ElButton type="primary" @click="gotoapp" v-wave>
                跳转至主页
            </ElButton>
        </div>
    </div>

</template>

<style scoped>
.login_response_text {
    padding-inline: 4px;
}

.login_response {
    display: flex;
    align-items: center;
    font-weight: 800;
    padding-top: 8px;
}

.login_btns {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    padding-inline: 10px;
}

.login_container {
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    padding: 16px;
    z-index: 1;
}

.login_container::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-image: url('http://aquamarine5.fun/img/login_splash.jpg');
    background-size: cover;
    background-position: center;
    opacity: 0.3;
    z-index: -1;
}

.login_input {
    margin-bottom: 10px;
}

.login_tips {
    margin-top: 20px;
    gap: 10px;
    font-weight: 800;
    display: flex;
    align-items: center;
}
</style>