<script setup>
import Topbar from '@/Topbar.vue';
import utils from '@/utils';
import axios from 'axios';
import { ElButton, ElCheckbox, ElIcon, ElInput } from 'element-plus';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const input_username = ref("")
const input_password = ref("")
const showpassword = ref(false)
const response = ref({})
const showresponse = ref(false)
const router=useRouter()
function login() {
    let formdata = new FormData()
    formdata.append("username", input_username.value)
    formdata.append("password", input_password.value)
    axios.post(utils.host + "/login", formdata).then(res => {
        response.value = res.data
        showresponse.value = true
        if (res.data.status == "login" || res.data.status == "newaccount") {
            localStorage.setItem("userid", res.data.result.userid)
        }
    })
}

function gotoapp(){
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

<style>
.login_container{
    margin: 16px;
}
</style>