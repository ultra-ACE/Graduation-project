<template>
<div class="box_top" >
  <div class="center">
    <img src="/src/assets/img.png">
    <h3>欢迎来到助农服务平台</h3>
    <el-form v-model="data.form">
      <el-form-item prop="username">
        <el-input  :prefix-icon="User" maxlength="9" v-model="data.form.username" placeholder="请输入账号" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input :prefix-icon="Lock" maxlength="9" type="password" show-password v-model="data.form.password" placeholder="请输入密码"
                  style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="data.form.role" placeholder="选择身份" style="width: 300px;">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-space :size="70">
          <router-link to="/retrieve_password">忘记密码</router-link>
          <router-link to="/register" >还没有账号？去注册！</router-link>
        </el-space>
      </el-form-item>
      <el-form-item>
        <el-button @click="Login" class="button_looks" >登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</div>
</template>


<script setup>

import {ref,reactive} from "vue";
import {User,Lock} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus"
import {useRouter} from "vue-router";
import axios from "axios";

name='login'

const router=useRouter()
const options=[
  {
    value:'Admin',
    label:'管理员'
  },
  {
    value:'User',
    label:'用户'
  }
]

const data=reactive({
  form:{}
})




const Login=()=>{
  if(data.form.role==='User'){
    axios.post("/LoginUser",data.form).then(function (res){
      if(res.data.code==='200'){
        ElMessage.success('登录成功')
        localStorage.setItem('Authorization',JSON.stringify(res.data.data))
        setTimeout(()=>{
          router.push('/Front')
        },1000)
      }
      else{
        ElMessage.error(res.data.msg)
      }
      console.log(res.data)
    }).catch(function (err){
      console.log('登录失败')
      console.log(err)
    })
  }
  else if(data.form.role==='Admin'){
    axios.post("/LoginAdmin",data.form).then(function (res){
      if(res.data.code==='200'){
        ElMessage.success('登录成功')
        localStorage.setItem('Authorization',JSON.stringify(res.data.data))
        setTimeout(()=>{
          router.push('/manage')
        },1000)
      }
      else{
        ElMessage.error(res.data.msg)
      }
      console.log(res.data)
    }).catch(function (err){
      console.log('登录失败')
      console.log(err)
    })
  }
}




</script>

<style scoped>

.center {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 10px;
  width: 400px;
  height: 400px;
  margin: auto;
  background: rgb(255,255,255,0.7);
  border:2px solid lightgray;
  border-radius: 10px;
  //box-shadow: white 0 0 0 1px, rgba(0, 0, 0, 0.1) 0 2px 4px;
  box-shadow: 0px 0px 5px 2px rgba(248, 248, 255, 0.5);
}

.box_top{
  display: flex;
  align-items: center;
  justify-content: center;
  height: calc(100vh);
  width: 100%;
  //background: linear-gradient(45deg, #00FF00, #FFEA00, #DE6F00, #DED2C5);
  background-image: url(../assets/img_1.png);
}

.button_looks{
  width: 300px;
}

.el-form-item{
  margin-bottom: 10px;
}

</style>


