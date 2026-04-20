<script setup>
import { ref } from 'vue';
import axios from "axios";
import {ElMessage} from "element-plus";

const form = ref({})
const isDialogShow = ref(false)


const tableData = ref([])
const user = JSON.parse(localStorage.getItem("Authorization"))

axios.get('/apply/selectByApplyUser', {
  params: {
    applyUser: user.username
  },
  headers: {
    'token': user.token
  }
}).then(function (res) {
  tableData.value = res.data.data
  console.log(res)
}).catch(function (err) {
  console.log(err)
})



const successUpload=(res)=>{
  form.value.img=res.data
}

const applyGood=()=>{
  axios.post('/apply/addApply',{
    applyName:form.value.applyName,
    num:form.value.num,
    img:form.value.img,
    applyUser:user.username,
    price:form.value.price
  },{headers:{'token':user.token}}).then(function (res){
    if(res.data.code==='200'){
      ElMessage.success("发送成功")
      console.log(res)
      isDialogShow.value=false
      setTimeout(()=>{
        location.reload()
      },1000)
    }
  }).catch(function (err){
    console.log(err)
  })
}

const applyAgain=(index)=>{
  axios.put('/apply/updateStatus',{
    id:index.id,
    applyName:index.applyName,
    num:index.num,
    img:index.img,
    applyUser:user.username,
    price:index.price,
    status:"待审批"
  },{headers:{'token':user.token}}).then(function (res){
    if(res.data.code==='200'){
      ElMessage.success("发送成功")
      console.log(res)
      isDialogShow.value=false
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
    <div class="search">
      <el-button @click="isDialogShow=true">产品申请</el-button>
    </div>
    <div class="table">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column label="编号" prop="id"></el-table-column>
        <el-table-column prop="img" label="图片" >
          <template v-slot="scope">
            <el-image style="width: 40px; height: 40px" :src="'http://localhost:8080/api/file/'+scope.row.img"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="applyName" label="申请产品" ></el-table-column>
        <el-table-column prop="date" label="日期" ></el-table-column>
        <el-table-column prop="num" label="数量"></el-table-column>
        <el-table-column label="状态" prop="status"></el-table-column>
        <el-table-column label="操作" >
          <template v-slot="scope">
            <el-button v-if="scope.row.status==='未通过'" @click="applyAgain(scope.row)">再次申请</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>

  <el-dialog v-model="isDialogShow" title="产品申请">
    <el-form>
      <el-form-item>
        <el-upload action="http://localhost:8080/api/file/upload" :on-success="successUpload"
                   :limit="1" :show-file-list="false">
          <el-image class="avatar" :src="'http://localhost:8080/api/file/'+ form.img"></el-image>
        </el-upload>
      </el-form-item>

      <el-form-item label="商品名称">
        <el-input v-model="form.applyName"></el-input>
      </el-form-item>
      <el-form-item label="商品数量">
        <el-input v-model="form.num"></el-input>
      </el-form-item>
      <el-form-item label="商品价格">
        <el-input v-model="form.price"></el-input>
      </el-form-item>
      <div style="margin: auto;display: flex;justify-content: center">
        <el-button type="primary" @click="applyGood">确认申请</el-button>
      </div>
    </el-form>
  </el-dialog>


</template>

<style scoped>
.search{
  width: 100%;
  padding: 10px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgba(0,0,0,.1);
  margin:0
}

.table{
  margin-top: 5px;
  padding: 15px 10px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
}
</style>