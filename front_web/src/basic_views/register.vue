<script setup>

import {ref} from "vue";
import {User,Lock} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
import axios from "axios";

name = 'register'

const router = useRouter()

const form = ref({
  username: '',
  password: '',
  password_again: '',
})

const register=()=>{
  if(form.value.password!==form.value.password_again){
    ElMessage.error('两次密码不一致')
  }
  else{
    axios.post("/RegisterUser",form.value).then(function (res){
      if(res.data.code==='200'){
        console.log('注册成功')
        console.log(res.data)
        ElMessage.success('注册成功')
        setTimeout(()=>{
          router.push('/')
        },1000)
      }
      else{
        ElMessage.error(res.data.msg)
      }
    }).catch(function (err){
      console.log(err)
    })

  }
}


</script>

<template>

  <div class="container">
    <el-form v-model="form" class="formlooks">
      <h3>注册账号</h3>
      <el-form-item >
        <el-input :prefix-icon="User" maxlength="9" v-model="form.username" clearable placeholder="请输入账号" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item >
        <el-input :prefix-icon="Lock" type="password" maxlength="9" v-model="form.password" clearable show-password placeholder="请输入密码"
                  style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item >
        <el-input :prefix-icon="Lock" type="password" maxlength="9" v-model="form.password_again" clearable placeholder="请确认密码"
                  style="width: 300px"></el-input>
      </el-form-item>


      <el-form-item>
        <el-button style="width: 300px" @click="register">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>


<style scoped>

.container {
  height: 100vh;
  overflow: hidden;
  background-size: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(45deg, #09b4e5, #5799b6, #ecfcff, #e6f4f1);
}


.formlooks {
  width: 380px;
  height: 380px;
  background: rgba(255, 250, 244, 0.5);
  border-radius: 10px;
  box-shadow: 0 0 10px #ccc;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}


</style>

