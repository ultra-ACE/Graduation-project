<script setup>
import { ref } from "vue";
import {useRouter} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";

const router = useRouter()
const admin=JSON.parse(localStorage.getItem("Authorization"))

const form = ref({
  oldPassword:'',
  newPassword:'',
  confirmPassword:''
});

const changePassword=()=>{
  if(form.value.oldPassword===null||form.value.newPassword===null||form.value.confirmPassword===null){
    ElMessage.warning("请填写全部信息")
  }
  else if(form.value.oldPassword!==admin.password){
    ElMessage.warning("原密码错误")
  }
  else if(form.value.newPassword!==form.value.confirmPassword){
    ElMessage.warning("两次新密码不一致")
  }
  else{
    axios.put('/admin/updatePassword',{
      username:admin.username,
      password:form.value.newPassword
    },{headers:{'token':admin.token}}).then(function (res){
      if(res.data.code==="200"){
        ElMessage.success("修改成功，请重新登录")
        localStorage.removeItem("Authorization")
        setTimeout(()=>{
          router.push('/')
        },1000)
      }
      else {
        ElMessage.error("修改失败")
      }
    }).catch(function (err){
      console.log(err)
    })
  }



}


</script>

<template>
  <div>
    <el-card style="width: 50%">
      <el-form label-width="100px" style="padding-right: 50px">
        <el-form-item label="旧密码">
          <el-input show-password v-model="form.oldPassword" clearable></el-input>
        </el-form-item>
        <el-form-item label="新密码">
          <el-input show-password v-model="form.newPassword" clearable></el-input>
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input type="password" v-model="form.confirmPassword" clearable></el-input>
        </el-form-item>
        <div style="text-align: center;margin-bottom: 20px">
          <el-button type="primary" @click="changePassword">保存</el-button>
        </div>
      </el-form>
    </el-card>
  </div>


</template>

<style scoped>

</style>