<script setup>
import { computed,ref} from "vue";
import {Refresh} from "@element-plus/icons-vue";
import axios from "axios";
import {ElMessage} from "element-plus";


const tableData=ref([])

const user=JSON.parse(localStorage.getItem("Authorization"));

axios.get('/order/selectByUsername',{
  params:{
    userName:user.username
  },
  headers:{'token':user.token}
}).then(function (res){
  tableData.value=res.data.data
}).catch(function (err){
  console.log(err)
})

const receive=(index)=>{
  axios.put('/order/updateStatus',{
    id:index.id,
    status:"已收货"
  },{headers:{'token':user.token}}).then(function (res){
    if(res.data.code==='200'){
      ElMessage.success("收货成功")
      setTimeout(()=>{
        location.reload()
      },1000)
    }
  }).catch(function (err){
    console.log(err)
  })
}


</script>

<template>
  <div>
    <div  style="width: 80%; margin: 20px auto">
      <div style="font-size: 17px;margin: 20px 10px">
        订单管理
      </div>
      <div class="table">
        <el-table :data="tableData" style="width: 100%" >
          <el-table-column prop="id" label="订单编号" >
          </el-table-column>
          <el-table-column prop="goodName" label="产品名称" >
          </el-table-column>
          <el-table-column prop="date" label="下单时间" >
          </el-table-column>
          <el-table-column prop="price" label="价格" >
          </el-table-column>
          <el-table-column prop="status" label="状态">

          </el-table-column>
          <el-table-column label="操作">
            <template v-slot="scope">
              <el-button v-if="scope.row.status==='已发货'" @click="receive(scope.row)">确认收货</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>


    </div>
  </div>
</template>

<style scoped>

.table{
  width: 1200px;
  margin-top: 5px;
  padding: 15px 10px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}

</style>