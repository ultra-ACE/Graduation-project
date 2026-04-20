<script setup>
import { computed,ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";




const search = ref('');
const tableData=ref([])

const data=JSON.parse(localStorage.getItem("Authorization"))
console.log(data)
axios.get('/user/selectAll',{headers:{'token':data.token}}).then(function (res) {
  tableData.value = res.data.data
}).catch(function (err) {
  console.log(err)
})

const searchName=()=>{
  axios.get('/user/selectByName',{
    params:{
      name:search.value
    },
    headers:{'token':data.token}
  }).then(function (res) {
    tableData.value= res.data.data
  }).catch(function (err) {
    ElMessage.error('查询失败')
  })
}

const resetting=()=>{
  axios.get('/user/selectAll',{headers:{'token':data.token}}).then(function (res) {
    tableData.value = res.data.data
  }).catch(function (err) {
    console.log(err)
  })
}

const remove = (index) => {
  axios.delete('/user/deleteByUserName'+index, {
    headers:{'token':data.token}
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
      <el-input v-model="search" clearable placeholder="请输入搜索内容" style="width: 300px"></el-input>
      <el-button type="primary" style="margin-left: 10px" @click="searchName">搜索</el-button>
      <el-button type="info" style="margin-left: 10px" @click="resetting">重置</el-button>
    </div>

    <div class="table">
      <el-table :data="pagination">
        <el-table-column align="center"  label="序号"  type="index" width="100"></el-table-column>
        <el-table-column align="center" label="账号" prop="username"></el-table-column>
        <el-table-column align="center" label="昵称" prop="name"></el-table-column>
        <el-table-column align="center" label="电话" prop="phone"></el-table-column>
        <el-table-column align="center" label="邮箱" prop="email"></el-table-column>
        <el-table-column align="center" label="操作" >
          <template v-slot="scope">
            <el-button @click="remove(scope.row.username)" type="danger">删除</el-button>
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