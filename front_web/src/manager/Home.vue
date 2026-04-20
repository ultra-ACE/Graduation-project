<script setup>
import { ref } from 'vue'
import axios from "axios";
name='Home'

const notices=ref([])

const admin=JSON.parse(localStorage.getItem("Authorization"))

axios.get('/information/selectAll',{headers:{'token':admin.token}}).then(function (res){
  notices.value=res.data.data
}).catch(function (err){
  console.log(err)
})


</script>

<template>
  <div>
    <div class="card" style="padding: 15px">
      你好，欢迎来到后台管理系统
    </div>
    <div style="display: flex;margin: 10px 0">
      <div style="width: 50%" class="card">
        <div style="margin-bottom: 30px; width: 1000px; font-size: 20px;font-weight: bold">公告列表</div>
        <div>
          <el-timeline slot="reference" reverse>
            <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.date">
              <span>{{item.content}}</span>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

.card {
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.1);
}

</style>