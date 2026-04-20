<script setup>
import {ref} from "vue";
import {ShoppingCartFull} from "@element-plus/icons-vue";
import {useRouter} from "vue-router";
import axios from "axios";
const router = useRouter();

const goods=ref([])
const shoppingcar=ref([])

const user=JSON.parse(localStorage.getItem("Authorization"))


axios.get("/good/selectAll",{headers:{'token':user.token}}).then(function (res){
  goods.value=res.data.data
  console.log(res)
}).catch(function (err){
  console.log(err)
})


const buy=(index)=>{
  if(localStorage.getItem(user.username)===null){
    localStorage.setItem(user.username,JSON.stringify(shoppingcar.value))
  }
  shoppingcar.value=JSON.parse(localStorage.getItem(user.username))
  axios.post('/shopping/addGoodInShoppingCar',{
    goods:shoppingcar.value,
    good:{
      goodId:index.id,
      goodName:index.goodName,
      price:index.price,
      num:1,
      img:index.img
    }
  },{headers:{'token':user.token}}).then(function (res){
    if(res.data.code==='200'){
      shoppingcar.value=res.data.data
      localStorage.setItem(user.username,JSON.stringify(shoppingcar.value))
    }
  }).catch(function (err){
    console.log(err)
  })
}







</script>

<template>
  <el-affix class="el-affix">
    <el-button @click="router.push('/Front/ShoppingCar')" :icon="ShoppingCartFull"
               style="font-size: 35px;width: 60px; height: 60px" circle></el-button>
  </el-affix>
  <el-backtop :right="5" style="color: #07c796"></el-backtop>
  <div>
    <div>
      <el-carousel type="card" :interval="4000" >
        <el-carousel-item v-for="item in goods" :key="item">
          <img :src="'http://localhost:8080/api/file/'+item.img" style="height: auto;border-radius: 5px">
        </el-carousel-item>
      </el-carousel>
    </div>
    <div style="text-align: center; color: #19a14b; font-size: 25px; font-weight: 550">优质商品</div>

    <div>
      <div style="height: 1000px">
        <el-row :gutter="20">
          <el-col :span="4" style="margin-top: 30px" v-for="item in goods">
            <img :src="'http://localhost:8080/api/file/'+item.img" alt=""
                 style="width: 100%; height: 180px; border-radius: 10px; border: 1px solid #cccccc">
            <div style="text-align: left; font-weight: 530; font-size: 16px; margin-top: 10px" class="overflowShow">
              {{ item.goodName }}
            </div>
            <div style="margin-top: 10px">
              <span style="color: red; margin-right: 30px">￥ {{ item.price }}</span>
              <span style="color: #666666; margin-right: 30px">库存：{{ item.num }}</span>
            </div>
            <div style="margin-top: 10px ;align-content: center;display: flex">
              <el-button type="warning" size="default" @click="buy(item)" :disabled="item.num === 0">购 买</el-button>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<style scoped>

.el-affix {
  position: fixed;
  right: 5px;
  bottom: 50%;
  z-index: 100;
}


.custom-divider {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px 20px;
  background-color: #f5f5f5;
  border-radius: 4px;
  font-size: 16px;
  font-weight: bold;
  color: #333;
}

</style>