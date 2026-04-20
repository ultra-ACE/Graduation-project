<script setup>
import {computed, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";

const orderID = ref('')

const admin= JSON.parse(localStorage.getItem("Authorization"))
const tableData = ref([])

const selection = ref([])




axios.get('/order/selectAll',{headers:{'token':admin.token}}).then(function (res){
  tableData.value = res.data.data
}).catch(function (err){
  console.log(err)
})

const search=()=>{
  axios.get('/order/selectById',{
    params:{
      id:orderID.value
    },
    headers:{'token':admin.token}
  }).then(function (res){
    tableData.value = res.data.data
  }).catch(function (err){
    console.log(err)
  })
}

const resetting=()=>{
  axios.get('/order/selectAll',{headers:{'token':admin.token}}).then(function (res){
    tableData.value = res.data.data
    orderID.value = ''
  }).catch(function (err){
    console.log(err)
  })
}

const changeStatus=(index)=> {
  axios.put('/order/updateStatus',{
    id:index.id,
    status:"已发货"
  },{headers:{'token':admin.token}}).then(function (res){
    if(res.data.code==='200'){
      ElMessage.success("发货成功")
      setTimeout(()=>{
        location.reload()
      },1000)
    }
  }).catch(function (err){
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
      <el-input placeholder="请输入订单编号" style="width: 200px;font-size: 13px;" v-model="orderID" clearable></el-input>
      <el-button type="info" plain style="margin-left: 10px;" @click="search">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="resetting">重置</el-button>
    </div>


    <div class="table">
      <el-table :data="pagination" stripe >
        <el-table-column prop="id" label="订单编号"></el-table-column>
        <el-table-column prop="img" label="商品图片" width="100">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px;" v-if="'http://localhost:8080/api/file/'+scope.row.img" :preview-teleported="true"
                        :src="'http://localhost:8080/api/file/'+scope.row.img" :preview-src-list="['http://localhost:8080/api/file/'+scope.row.img]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="goodName" label="商品名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="price" label="订单价格"></el-table-column>
        <el-table-column prop="userName" label="下单用户"></el-table-column>
        <el-table-column prop="date" label="下单时间"></el-table-column>
        <el-table-column prop="status" label="订单状态" width="100"></el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="primary" size="small" v-if="scope.row.status === '待发货'"
                       @click="changeStatus(scope.row)">发货
            </el-button>
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
.main-content {
  width: 100%;
  margin: 5px auto;
}

.search{
  width: calc(100% - 20px);
  padding: 10px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgba(0,0,0,.1);
  margin:0
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



</style>