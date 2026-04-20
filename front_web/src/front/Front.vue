<script setup>
import {computed, ref} from "vue";
import {useRouter} from "vue-router";
import axios from "axios";

const router = useRouter();

const user= JSON.parse(localStorage.getItem("Authorization"));



</script>

<template>
  <div>
    <div class="front-header">
      <div class="front-header-left">
        <span >助农扶贫网</span>
      </div>
      <div class="front-header-center">
      </div>
      <div class="front-header-right">
        <el-dropdown style="display: flex; align-items: center;justify-content: right" placement="bottom">
          <div class="avatar">
            <el-avatar  :src="'http://localhost:8080/api/file/' + user.avatar" />
            <div>{{ user.name }}</div>
          </div>
          <template #dropdown>
            <el-dropdown-menu slot="dropdown" style="min-width: 40px">
              <el-dropdown-item @click="router.push('/Front/PersonalIn')">个人信息</el-dropdown-item>
              <el-dropdown-item @click="router.push('/Front/Orders')">我的订单</el-dropdown-item>
              <el-dropdown-item @click="router.push('/Front/Apply')">我的申请</el-dropdown-item>
              <el-dropdown-item @click="router.push('/')">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <div>
      <router-view></router-view>
    </div>

  </div>


</template>

<style scoped>



.front-header {
  display: flex;
  height: 60px;
  line-height: 60px;
  border-bottom: 1px solid #eee;
  background: #fff;
}

.front-header-left{
  width: 350px;
  display: flex;
  align-items: center;
  padding-left: 30px;
}
.front-header-center{
  flex: 1;
}
.front-header-right{
  width: 200px;
  display: flex;
  align-items: center;
  justify-content: flex-end;

}


.front-header-right .avatar {
  display: flex;
  align-items: center;
  padding-right: 10px;
  font-size: 15px;
  color: #606266;
  cursor: default;
}
.front-header-right .avatar img{
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 5px;
}


</style>