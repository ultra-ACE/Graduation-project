<script setup>
import { computed,ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";


const user=ref({})
user.value=JSON.parse(localStorage.getItem("Authorization"))

const search = ref('')

const dialogVisible = ref({})
const addDialogVisible = ref(false)
const addForm=ref({})
const tableData=ref([])

axios.get('/admin/selectAll',{headers:{'token':user.value.token}}).then(function (res){
  tableData.value=res.data.data
}).catch(function (err){
  console.log(err)
})

const searchName=()=>{
  axios.get('/admin/selectByName',{
    params:{
      name:search.value
    },
    headers:{
      'token':user.value.token
    }
  }).then(function (res){
    console.log(res)
    tableData.value=res.data.data
  }).catch(function (err){
    console.log(err)
  })
}

const resetting=()=>{
  axios.get('/admin/selectAll',{headers:{'token':user.value.token}}).then(function (res){
    tableData.value=res.data.data
  }).catch(function (err){
    console.log(err)
  })
}

const editConfirm=(information)=>{
  axios.put('/admin/updateAdminInformation',{
    username:information.username,
    name:information.name,
    password:information.password
  },{
    headers:{'token':user.value.token}
  }).then(function (res){
    console.log(res)
    if(res.data.code==='200'){
      ElMessage.success("修改成功")
      dialogVisible.value=false
    }
    else{
      ElMessage.error(res.data.msg)
    }
  }).catch(function (err){
    console.log(err)
  })
}

const addAdmin=()=>{
  axios.post('/admin/addAdmin',{
    username:addForm.value.username,
    password:addForm.value.password
  },{headers:{'token':user.value.token}}).then(function (res){
    if (res.data.code==='200')
    {
      ElMessage.success("添加成功")
      location.reload()
    }
  }).catch(function (err){
    console.log(err)
  })
  addDialogVisible.value=false
}

const deleteAdmin=(index)=>{
  axios.delete('/admin/deleteByUserName'+index, {
    headers:{'token':user.value.token}
  }).then(function (res) {
    location.reload()
  }).catch(function (err) {
    console.log(err)
  })

}

const pageNumber = ref(1)
const pageSize = ref(5)

const handleCurrentChange = (val) => {
  pageNumber.value = val
}

const handleSizeChange = (val) => {
  pageSize.value = val
}
const pagination = computed(()=>{
  const start = (pageNumber.value-1)*pageSize.value
  const end = start+pageSize.value
  return  tableData.value.slice(start,end)
})


</script>

<template>
  <div>
    <div class="search">
      <el-input v-model="search" placeholder="请输入搜索内容" style="width: 300px"></el-input>
      <el-button type="primary" style="margin-left: 10px" @click="searchName">搜索</el-button>
      <el-button type="info" style="margin-left: 10px" @click="resetting">重置</el-button>
      <el-button type="success" style="margin-left: 10px" @click="addDialogVisible=true">增加</el-button>
    </div>

    <el-dialog v-model="addDialogVisible" :append-to-body="true" title="增加管理员">
      <el-form v-model="addForm">
        <el-form-item label="账号" >
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" >
          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
      </el-form>
      <div style="margin: auto;display: flex;justify-content: center">
        <el-button type="primary" @click="addAdmin">确定</el-button>
      </div>
    </el-dialog>


    <div class="table">
      <el-table :data="pagination">
        <el-table-column   label="序号" type="index" align="center"></el-table-column>
        <el-table-column  prop="username"  label="管理员账号" align="center"></el-table-column>
        <el-table-column prop="password"  label="管理员密码" align="center"></el-table-column>
        <el-table-column prop="name" label="管理员昵称" align="center"></el-table-column>
        <el-table-column align="center" label="操作">
          <template v-slot="scope">
            <el-dialog v-model="dialogVisible[scope.row.id]" :append-to-body="true" title="编辑">
              <div>
                <el-form label-width="80px">
                  <el-form-item label="昵称">
                    <el-input v-model="scope.row.name"></el-input>
                  </el-form-item>
                  <el-form-item label="密码">
                    <el-input v-model="scope.row.password"></el-input>
                  </el-form-item>
                </el-form>
                <div style="margin: auto;display: flex;justify-content: center">
                  <el-button type="primary" @click="editConfirm(scope.row)">确定</el-button>
                </div>
              </div>
            </el-dialog>
            <el-button type="primary" size="small" @click="dialogVisible[scope.row.id]=true">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteAdmin(scope.row.username)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>


      <div style="margin-top: 10px">
        <el-pagination
            :current-page="pageNumber"
            :page-size="pageSize"
            layout="total,prev, pager,next"
            :total="tableData.length"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange">
        </el-pagination>

      </div>
    </div>
  </div>
</template>

<style scoped>
.search{
  width: calc(100% - 20px);
  padding: 10px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgba(0,0,0,.1);
  margin:0
}

.table{
  width: calc(100% - 20px);
  margin-top: 10px;
  padding: 15px 10px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}
</style>