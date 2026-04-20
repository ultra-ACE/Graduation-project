<script setup>

import {ref} from "vue";
import {User,Lock} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import axios from "axios";
import {useRouter} from "vue-router";

const router = useRouter()

name = 'retrieve_password'

const form=ref({
  username:'',
  password:'',
  password_again:''
})

const retrieve_password=()=>{
  if(form.value.password!==form.value.password_again){
    ElMessage.error('两次密码不一致')
  }
  else{
    axios.put("/RetrievePassword",form.value).then(function (res){
      if(res.data.code==='200') {
        ElMessage.success('修改成功')
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
      <h3>找回密码</h3>
      <el-form-item >
        <el-input :prefix-icon="User" v-model="form.username" clearable placeholder="请输入账号" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item >
        <el-input :prefix-icon="Lock" v-model="form.password" type="password" clearable show-password placeholder="请输入新密码"
                  style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input :prefix-icon="Lock" type="password" v-model="form.password_again" clearable placeholder="请再次输入新密码"
                  style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="retrieve_password" style="width: 300px">确认</el-button>
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
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 400px;
  height: 400px;
  margin: auto;
  background: rgb(255,255,255,0.7);
  border:2px solid lightgray;
  border-radius: 10px;
  box-shadow: 0px 0px 5px 2px rgba(248, 248, 255, 0.5);
}

</style>