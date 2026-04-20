<script setup>
import { ref } from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";

const form = ref({});
form.value=JSON.parse(localStorage.getItem("Authorization"))

const successUpload=(res)=>{
  console.log(res)
  form.value.avatar=res.data
  console.log(form.avatar)
  localStorage.setItem("Authorization",JSON.stringify(form.value))
  location.reload()
}

const save=()=>{
  localStorage.setItem('Authorization',JSON.stringify(form))
  console.log(form)
  axios.put('/admin/updateAdminInformation',{
    username:form.value.username,
    name:form.value.name,
    phone:form.value.phone,
    email:form.value.email,
    avatar:form.value.avatar
  },{headers:{'token':form.value.token}}).then(function (res){
    if(res.data.code==='200'){
      ElMessage.success('修改成功')
      localStorage.setItem('Authorization',JSON.stringify(form.value))
    }
    else{
      ElMessage.error(res.data.msg)
    }
  }).catch(function (err){
    console.log(err)
  })
}

</script>

<template>
  <div>
    <el-card style="width: 50%">
      <el-form label-width="100px" style="padding-right: 50px">
        <div style="margin: 15px; text-align: center">
          <el-upload action="http://localhost:8080/api/file/upload"
          :on-success="successUpload" :show-file-list="false">
            <el-image :src="'http://localhost:8080/api/file/' + form.avatar" class="avatar"/>
          </el-upload>
        </div>
        <el-form-item label="账号">
          <el-input disabled v-model="form.username"> </el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.name" clearable></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input clearable v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" clearable></el-input>
        </el-form-item>
        <div style="text-align: center;margin-bottom: 20px">
          <el-button type="primary" @click="save">保存</el-button>
        </div>
      </el-form>
    </el-card>
  </div>

</template>

<style scoped>

.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%
}

</style>