<script setup>
import {computed, ref} from "vue";
import {useRouter} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";

const router = useRouter()
const isDialogVisible =ref(false);

const form = ref({})
form.value= JSON.parse(localStorage.getItem("Authorization"));
console.log(form.value)
const NewPassword_form=ref({
  oldPassword:null,
  newPassword:null,
  confirmPassword:null
})

const setNewPassword=()=>{
  if(NewPassword_form.value.oldPassword===null||NewPassword_form.value.newPassword===null||NewPassword_form.value.confirmPassword===null){
    ElMessage.error('信息不全！')
  }
  else if(NewPassword_form.value.oldPassword!==form.value.password){
    ElMessage.error('旧密码错误！')
  }
  else if(NewPassword_form.value.newPassword!==NewPassword_form.value.confirmPassword){
    ElMessage.error('两次密码不一致！')
  }
  else{
    axios.put('/user/updateUserPassword',{
      username:form.value.username,
      password:NewPassword_form.value.newPassword
    },{headers:{'token':form.value.token}}).then(function (res){
      if(res.data.code==='200'){
        ElMessage.success('修改成功，请重新登录！')
        isDialogVisible.value=false
        localStorage.removeItem('Authorization')
        router.push('/')
      }
      else{
        ElMessage.error(res.data.msg)
      }
    }).catch(function (err){
      console.log(err)
    })
  }
}

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
  axios.put('/user/updateUserInformation',{
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
console.log(form.value.avatar)
</script>

<template>
  <div style="width: 100%;margin: 5px auto">
    <el-card style="width: 50%;margin: 30px auto">
      <div style="text-align: right;margin-bottom: 20px">
        <el-button @click="isDialogVisible=true" color="#7ab0e0" >修改密码</el-button>
      </div>
      <el-form label-width="80px" style="padding-right: 20px">
        <div style="margin: 15px;text-align: center">
          <el-upload action="http://localhost:8080/api/file/upload" :on-success="successUpload"
          :limit="1" :show-file-list="false">
            <el-image class="avatar" :src="'http://localhost:8080/api/file/' + form.avatar"></el-image>
          </el-upload>
        </div>
        <el-form-item label="账号" prop="username">
          <el-input disabled v-model="form.username" placeholder="账号"> </el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="name">
          <el-input v-model="form.name" clearable placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="电话" clearable></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="邮箱" clearable></el-input>
        </el-form-item>
        <div style="text-align: center;margin-bottom: 20px">
          <el-button  color="#417ca9" @click="save">保存</el-button>

        </div>
      </el-form>

      <el-dialog v-model="isDialogVisible">
        <el-form v-model="NewPassword_form">
          <el-form-item prop="oldPassword">
            <el-input type="password" v-model="NewPassword_form.oldPassword" show-password placeholder="旧密码"></el-input>
          </el-form-item>
          <el-form-item prop="newPassword">
            <el-input type="password" v-model="NewPassword_form.newPassword" show-password placeholder="新密码"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPassword">
            <el-input type="password" v-model="NewPassword_form.confirmPassword" placeholder="确认密码"></el-input>
          </el-form-item>
          <div style="text-align: center;margin-bottom: 20px">
            <el-button type="primary" @click="setNewPassword" color="#417ca9" >确定</el-button>
          </div>
        </el-form>
      </el-dialog>

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