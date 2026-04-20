<script setup>
import {ref,onMounted} from "vue";
import {Delete,CreditCard,Plus,Minus} from "@element-plus/icons-vue";
import axios from "axios";
import {ElMessage} from "element-plus";

const shoppingCar=ref([])
const selection=ref([])
const good=ref({})

const buyDialog=ref(false)
const address=ref('')
const user=JSON.parse(localStorage.getItem("Authorization"))
shoppingCar.value=JSON.parse(localStorage.getItem(user.username))

const totalPrice=ref()

const deleteGood=(index)=>{
  axios.post('/shopping/deleteGoodInShoppingCar',{
    goods:shoppingCar.value,
    good:{
      goodId:index.goodId,
      goodName:index.goodName,
      price:index.price,
      num:index.num,
      img:index.img
    }
  }
  ,{headers:{'token':user.token}}).then(function (res){
    if(res.data.code==='200'){
      shoppingCar.value=res.data.data
      localStorage.setItem(user.username,JSON.stringify(shoppingCar.value))
    }
    console.log(res)
  }).catch(function (err){
    console.log(err)
  })
}

const handleSelectionChange=(val)=>{
  selection.value=val
}

const deleteGoods=()=>{
  if(selection.value.length===0){
    ElMessage.error("请选择要删除的商品")
  }
  else{
    axios.post('/shopping/deleteGoodsInShoppingCar',{
      goods:shoppingCar.value,
      deleteGoods:selection.value
    },{headers:{'token':user.token}}).then(function (res){
      if(res.data.code==='200'){
        shoppingCar.value=res.data.data
        localStorage.setItem(user.username,JSON.stringify(shoppingCar.value))
      }
      console.log(res)
    }).catch(function (err){
      console.log(err)
    })
  }
}

const plus=(index)=>{
  index.num++
  inputEvent(index)
  localStorage.setItem(user.username,JSON.stringify(shoppingCar.value))
}
const minus=(index)=>{
  if(index.num>1){
    index.num--
    inputEvent(index)
    localStorage.setItem(user.username,JSON.stringify(shoppingCar.value))
  }
}

const inputEvent=(index)=>{
  axios.get('/good/selectById',{
    params:{
      id:index.goodId
    },
    headers:{'token':user.token}
  }).then(function (res){
    good.value=res.data.data
    console.log(good.value.num)
    if(index.num > good.value.num){
      ElMessage.error("库存不足")
      index.num=good.value.num
    }
    localStorage.setItem(user.username,JSON.stringify(shoppingCar.value))
  }).catch(function (err){
    console.log(err)
  })
}





const buy=()=>{
  buyDialog.value=true
  axios.post('/shopping/calculateTotalPrice',selection.value,{headers:{'token':user.token}})
      .then(function (res){
        totalPrice.value=res.data.data
      }).catch(function (err){
    console.log(err)
  })
}


const afford=()=>{
  axios.post('/shopping/afford',{
    goods:shoppingCar.value,
    deleteGoods:selection.value,
    user:user,
    address:address.value
  },{headers:{'token':user.token}}).then(function (res){
    if(res.data.code==='200'){
      ElMessage.success("购买成功")
      shoppingCar.value=res.data.data
      if(shoppingCar.value.length===0){
        localStorage.removeItem(user.username)
      }
      else{
        localStorage.setItem(user.username,JSON.stringify(shoppingCar.value))
      }
      buyDialog.value=false
    }
    else{
      ElMessage.error(res.data.msg)
      setTimeout(()=>{
        location.reload()
      },1000)
    }
  }).catch(function (err){
    ElMessage.error(err)

  })
}



</script>

<template>
  <el-backtop :right="5"></el-backtop>

  <el-dialog v-model="buyDialog">
    <el-form>
      <el-form-item label="总价">
        {{totalPrice}} 元(¥)
      </el-form-item>
      <el-form-item label="收货地址">
        <el-input v-model="address" placeholder="请输入收货地址"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="afford">确认购买</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>






  <div >
    <el-card>
      <div>
        <el-button type="danger" size="default" :icon="Delete" style="font-size: large" @click="deleteGoods"></el-button>
        <el-button :icon="CreditCard" type="success" style="font-size: large" @click="buy"></el-button>
      </div>
      <el-table :data="shoppingCar" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" v-slot="scope"></el-table-column>
        <el-table-column label="商品编号" prop="goodId" ></el-table-column>
        <el-table-column label="图片" prop="img">
          <template #default="scope">
            <img :src="'http://localhost:8080/api/file/'+scope.row.img" alt="" style="width: 50px;height: 50px">
          </template>
        </el-table-column>
        <el-table-column label="商品名称" prop="goodName"></el-table-column>
        <el-table-column label="商品单价" prop="price"></el-table-column>
        <el-table-column label="商品数量" >
          <template #default="scope">
            <div style="align-items: center" >
              <el-button type="text" :icon="Minus" style="color: #606266" @click="minus(scope.row)"></el-button>
              <el-input v-model="scope.row.num"  size="default" style="width: 45px" @blur="inputEvent(scope.row)" @input="inputEvent(scope.row)"></el-input>
              <el-button type="text" :icon="Plus" style="color: #606266" @click="plus(scope.row)"></el-button>
            </div>

          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button type="danger" size="small" @click="deleteGood(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<style scoped>



</style>