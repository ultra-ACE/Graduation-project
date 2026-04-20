<script setup>
import {computed, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";

const name = ref("");
const admin=ref()
const tableData=ref([])
const addDialog=ref(false)
const editDialog=ref(false)
const addForm=ref({})
const dialogVisible=ref({})
const selection=ref([])
admin.value=JSON.parse(localStorage.getItem("Authorization"))



axios.get('/good/selectAll',{headers:{'token':admin.value.token}}).then(function (res){
  tableData.value=res.data.data
}).catch(function (err){
  console.log(err)
})
const searchName=()=>{
  axios.get('/good/selectByGoodName',{
    params:{
      goodname:name.value
    },
    headers:{'token':admin.value.token}
  }).then(function (res){
    tableData.value=res.data.data
  }).catch(function (err){
    console.log(err)
  })
}
const resetting=()=>{
  axios.get('/good/selectAll',{headers:{'token':admin.value.token}}).then(function (res){
    tableData.value=res.data.data
    name.value=""
  }).catch(function (err){
    console.log(err)
  })
}
const successUpload=(res)=>{
  console.log(res)
  addForm.value.img=res.data
  console.log(addForm.avatar)
}
const successUploadEdit=(res,index)=>{
  index.img=res.data
  console.log(res)
}

const addGood=()=>{
  axios.post('/good/addGood',{
    goodName:addForm.value.name,
    price:addForm.value.price,
    num:addForm.value.num,
    img:addForm.value.img
  },{headers:{'token':admin.value.token}}).then(function (res){
    if(res.data.code==='200'){
      addDialog.value=false
      location.reload()
    }
    else{
      ElMessage.error(res.data.msg)
    }
  }).catch(function (err){
    console.log(err)
  })
}



const deleteGood=(index)=>{
  axios.delete('/good/deleteGood'+index.toString(),{
    headers:{'token':admin.value.token}}).then(function (res){
      location.reload()
  }).catch(function (err){
    console.log(err)
  })
}

const editGood=(index)=>{
  axios.put('/good/updateGood',{
    id:index.id,
    goodName:index.goodName,
    price:index.price,
    num:index.num,
    img:index.img
  },{headers:{'token':admin.value.token}}).then(function (res){
    if(res.data.code==='200'){
      editDialog.value=false
      location.reload()
    }
    else{
      console.log(admin.value.token)
      ElMessage.error(res.data.msg)
    }
  }).catch(function (err){
    console.log(err)
  })
}

const handSelection=(val)=>{
  selection.value=val
}

const deleteGoods=()=>{
  if(selection.value.length===0){
    ElMessage.error('请选择要删除的商品')
  }
  else{
    axios.put('/good/deleteGoods',selection.value,{headers:{'token':admin.value.token}}).then(function (res){
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
  <div class="search">
    <el-input placeholder="请输入名称查询" style="width: 200px" v-model="name"></el-input>
    <el-button type="info" plain style="margin-left: 10px" @click="searchName">查询</el-button>
    <el-button type="warning" plain style="margin-left: 10px" @click="resetting">重置</el-button>
  </div>
  <div class="operation">
    <el-button type="primary" plain @click="addDialog=true">新增</el-button>
    <el-button type="danger" plain @click="deleteGoods">批量删除</el-button>
  </div>

  <el-dialog v-model="addDialog" title="增加商品">
    <el-form>
      <el-form-item>
        <el-upload action="http://localhost:8080/api/file/upload" :on-success="successUpload"
        :show-file-list="false">
          <el-image class="avatar" :src="'http://localhost:8080/api/file/'+addForm.img"></el-image>
        </el-upload>
      </el-form-item>
      <el-form-item label="商品名称">
        <el-input v-model="addForm.name"></el-input>
      </el-form-item>
      <el-form-item label="商品价格">
        <el-input v-model="addForm.price"></el-input>
      </el-form-item>
      <el-form-item label="商品数量">
        <el-input v-model="addForm.num"></el-input>
      </el-form-item>
      <div style="margin: auto;display: flex;justify-content: center">
        <el-button type="primary" @click="addGood">确认添加</el-button>
      </div>
    </el-form>
  </el-dialog>






  <div class="table">
    <el-table :data="pagination" stripe @selection-change="handSelection">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="img" label="商品图片">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image  style="width: 40px; height: 40px" v-if="'http://localhost:8080/api/file/'+scope.row.img"
                         :src="'http://localhost:8080/api/file/'+scope.row.img"  :preview-src-list="['http://localhost:8080/api/file/'+scope.row.img]" :preview-teleported="true"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="goodName" label="商品名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="price" label="商品价格"></el-table-column>
        <el-table-column prop="num" label="商品库存"></el-table-column>
        <el-table-column prop="sales" label="商品销量"></el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-dialog v-model="dialogVisible[scope.row.id]" :append-to-body="true" title="修改商品">
              <el-form>
                <el-form-item>
                  <el-upload action="http://localhost:8080/api/file/upload" :on-success="(res)=>successUploadEdit(res,scope.row)"
                             :show-file-list="false">
                    <el-image class="avatar" :src="'http://localhost:8080/api/file/'+scope.row.img"></el-image>
                  </el-upload>
                </el-form-item>
                <el-form-item label="商品名称">
                  <el-input v-model="scope.row.goodName"></el-input>
                </el-form-item>
                <el-form-item label="商品价格">
                  <el-input v-model="scope.row.price"></el-input>
                </el-form-item>
                <el-form-item label="商品数量">
                  <el-input v-model="scope.row.num"></el-input>
                </el-form-item>
                <div style="margin: auto;display: flex;justify-content: center">
                  <el-button type="primary" @click="editGood(scope.row)">确认修改</el-button>
                </div>
              </el-form>
            </el-dialog>
            <el-button plain type="primary" @click="dialogVisible[scope.row.id]=true" size="small">编辑</el-button>
            <el-button plain type="danger" size="small" @click=deleteGood(scope.row.id)>删除</el-button>
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
  padding: 10px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgba(0,0,0,.1);
}

.table{
  width: calc(100% - 20px);
  margin-top: 10px;
  padding: 15px 10px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%
}

</style>