<script setup>
import { computed,ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";


const search = ref('')
const tableData=ref([])
const addForm=ref({})
const addDialog=ref(false)
const detailDialog=ref({})
const editDialog=ref({})
const selection=ref([])



const admin=JSON.parse(localStorage.getItem("Authorization"))



axios.get('/information/selectAll',{headers:{'token':admin.token}}).then(function (res) {
  tableData.value = res.data.data
}).catch(function (err) {
  console.log(err)
})

const searchName=()=>{
  axios.get('/information/selectByTitle',{
    params:{
      title:search.value
    },
    headers:{'token':admin.token}
  }).then(function (res) {
    tableData.value= res.data.data
  }).catch(function (err) {
    console.log(err)
  })
}

const resetting=()=> {
  axios.get('/information/selectAll', {headers: {'token': admin.token}}).then(function (res) {
    tableData.value = res.data.data
    search.value=''
  }).catch(function (err) {
    console.log(err)
  })
}

const addInformation=()=>{
  axios.post('/information/addInformation',{
    title:addForm.value.title,
    content:addForm.value.content,
    date:addForm.value.date.toString()
  },{headers:{'token':admin.token}}).then(function (res){
    if(res.data.code==='200'){
      ElMessage.success('添加成功')
      addDialog.value=false
      setTimeout(()=>{
        location.reload()
      },1000)
    }
    else{
      ElMessage.error(res.data.msg)
    }
  }).catch(function (err){
    console.log(err)
  })
}

const editInformation=(index)=>{
  axios.put('/information/updateInformation',{
    id:index.id,
    title:index.title,
    content:index.content,
    date:index.date.toString()
  },{headers:{'token':admin.token}}).then(function (res){
    if(res.data.code==='200'){
      ElMessage.success('修改成功')
      editDialog[index.id]=false
      location.reload()
    }
    else{
      ElMessage.error(res.data.msg)
    }
  }).catch(function (err){
    console.log(err)
  })
}

const deleteInformation=(index)=>{
  axios.delete('/information/deleteInformation'+index.toString(),{
    headers:{'token':admin.token}}).then(function (res){
    ElMessage.success("删除成功")
    setTimeout(()=>{
      location.reload()
    },1000)
  }).catch(function (err){
    console.log(err)
  })
}

const handSelection=(val)=>{
  selection.value=val
}

const deleteInformations=()=>{
  if(selection.value.length===0){
    ElMessage.error('请选择要删除的商品')
  }
  else{
    axios.put('/information/deleteInformations',selection.value,{headers:{'token':admin.token}}).then(function (res){
      if(res.data.code==='200'){
        selection.value=[]
        location.reload()
      }
      else{
        ElMessage.error(res.data.msg)
      }
    }).catch(function (err){
      console.log(err)
    })
  }
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
      <el-input placeholder="请输入标题查询" style="width: 200px;font-size: 13px;" v-model="search" clearable></el-input>
      <el-button type="info" plain style="margin-left: 10px;" @click="searchName">搜索</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="resetting">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="addDialog=true">新增</el-button>
      <el-button type="danger" plain style="margin-left: 10px" @click="deleteInformations">批量删除</el-button>
    </div>

    <el-dialog v-model="addDialog" title="增加公告">
      <el-form>
        <el-form-item label="资讯标题">
          <el-input v-model="addForm.title" clearable placeholder="请输入资讯标题"></el-input>
        </el-form-item>
        <el-form-item label="资讯内容">
          <el-input v-model="addForm.content" clearable placeholder="请输入资讯内容"></el-input>
        </el-form-item>
        <el-form-item label="发布时间">
          <el-date-picker
              v-model="addForm.date"
              type="date"
              placeholder="选择日期" value-format="YYYY-MM-DD">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addInformation">添加</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <div class="table">
      <el-table :data="pagination" stripe @selection-change="handSelection">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="title" label="资讯标题" show-overflow-tooltip></el-table-column>
        <el-table-column prop="content" label="资讯内容">
          <template v-slot="scope">
            <el-dialog v-model="detailDialog[scope.row.id]" :append-to-body="true">
              <span>{{scope.row.content}}</span>
            </el-dialog>
            <el-button type="primary" size="small" @click="detailDialog[scope.row.id]=true">点击查看</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="发布时间" sortable></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-dialog title="公告编辑" :append-to-body="true" v-model="editDialog[scope.row.id]">
              <el-form>
                <el-form-item label="资讯标题">
                  <el-input v-model="scope.row.title" clearable placeholder="请输入资讯标题"></el-input>
                </el-form-item>
                <el-form-item label="资讯内容">
                  <el-input v-model="scope.row.content" clearable placeholder="请输入资讯内容"></el-input>
                </el-form-item>
                <el-form-item label="发布时间">
                  <el-date-picker
                      v-model="scope.row.date"
                      type="date"
                      placeholder="选择日期" value-format="YYYY-MM-DD">
                  </el-date-picker>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="editInformation(scope.row)">确认修改</el-button>
                </el-form-item>
              </el-form>
            </el-dialog>


            <el-button plain type="primary" size="small" @click="editDialog[scope.row.id]=true">编辑</el-button>
            <el-button plain type="danger" size="small" @click="deleteInformation(scope.row.id)">删除</el-button>
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
  margin-bottom: 10px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgba(0,0,0,.1);
}
.operation{
  width: calc(100% - 20px);
  padding: 10px;
  background-color: #fff;
  border-radius: 5px;
  margin-bottom: 10px;
  box-shadow: 0 2px 5px 0 rgba(0,0,0,.1);
}
.table{
  width: calc(100% - 20px);
  padding: 15px 10px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}

</style>