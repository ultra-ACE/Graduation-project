<script setup>
import {ref} from "vue";
import {HomeFilled, Menu, Histogram} from "@element-plus/icons-vue";
import {useRouter} from "vue-router";

name = 'manage'


const isRoot = ref()
const router = useRouter()
const admin =ref({})
admin.value= JSON.parse(localStorage.getItem("Authorization"))
if (admin.value.root) {
  isRoot.value = true
} else {
  isRoot.value = false
}

const logout = () => {
  localStorage.removeItem("Authorization")
  router.push('/')
}

</script>

<template>
  <div class="container">
    <!--    头部-->
    <div class="header">
      <div class="header_left">
        <img src="/src/assets/logo.png" style="width: 30px;height: 30px;border-radius: 5px">
        <div style="flex: 1;font-family: Kai ;margin-left: 5px;font-size: 20px;font-weight:700;color: white">
          助农扶贫系统
        </div>
      </div>
      <div class="header_right">
        <el-dropdown style="display: inline-block;position: relative;color: #606266;font-size: 14px;"
                     placement="bottom">
          <div class="avatar">
            <el-avatar :src="'http://localhost:8080/api/file/'+admin.avatar"></el-avatar>
            <div>{{ admin.name }}</div>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="router.push('/manage/PersonalIn')">个人信息</el-dropdown-item>
              <el-dropdown-item @click="router.push('/manage/PasswordChange')">修改密码</el-dropdown-item>
              <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    <div style="display: flex">
      <div class="main_left">
        <el-menu router :default-active="$route.path" style="border: none">
          <el-menu-item index="/manage/home">
            <HomeFilled class="icon"></HomeFilled>
            <span slot="title">首页</span>
          </el-menu-item>
          <el-sub-menu index="1">
            <template #title>
              <Menu class="icon"></Menu>
              <span slot="title">信息管理</span>
            </template>
            <el-menu-item index="/manage/Information">公告信息</el-menu-item>
            <el-menu-item index="/manage/Orders">订单信息</el-menu-item>
            <el-menu-item index="/manage/Goods">商品信息</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              <Menu class="icon"></Menu>
              <span slot="title">申请管理</span>
            </template>
            <el-menu-item index="/manage/ProductApply">产品申请</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="3">
            <template #title>
              <Menu class="icon"></Menu>
              <span slot="title">用户管理</span>
            </template>
            <el-menu-item v-if="isRoot" index="/manage/ManagerIn">管理员信息</el-menu-item>
            <el-menu-item index="/manage/UserIn">用户信息</el-menu-item>
          </el-sub-menu>


        </el-menu>
      </div>
      <div class="main_right">
        <router-view ></router-view>
      </div>
    </div>

  </div>
</template>

<style scoped>

.container {
  height: 100%;
  background: #f8f8ff;
}
.header{
  display: flex;
  justify-content: space-between;
  background: #00a5f7;
}
.header_left{
  width:200px;
  height: 60px;
  margin-left: 20px;
  display: flex;
  align-items: center;
}
.header_center{
  flex: 1;
  display: flex;
  width: 0;
  align-items: center;
}
.header_right{
  width: 200px;
  display: flex;
  margin-right: 20px;
  align-items: center;
  justify-content: flex-end;
}
.header_right .avatar {
  display: flex;
  align-items: center;
  padding-right: 10px;
  color: #fff;
  cursor: default;
}
.header_right .avatar img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 5px;
}
.main_left{
  width: 200px;
  min-height: calc(100vh - 60px);
  box-shadow: 0 2px 6px rgba(0,0,0,.1);
}
.main_right{
  flex: 1;
  width: calc(100% - 200px);
  padding: 10px;
}
.icon{
  margin-right: 5px;
  width: 24px;
  text-align: center;
  font-size: 18px;
  vertical-align: middle;
}
el-menu{
  width: 200px;
  height: 100%;
  background-color: #fff;
}
el-menu-item {
  color: #666;
  height: 50px;
  line-height: 50px;
  //background-color: #fff !important; /* 必须加上这个背景色，否则鼠标离开会出现白色 */
}
el-menu-item .is-active {
  background-color: #cdf4ea !important;
  color: #07c796;
  border-right: 3px solid #07c796;
}
el-submenu__title {
  color: #666;
  height: 50px;
  line-height: 50px;
}





.el-menu--inline el-menu-item {
  padding-left: 50px !important;
}

.el-menu is-active {
  color: #00a5f7 !important;
}

el-submenu__title:hover {
  background-color: #e6f9f5 !important;
}

el-menu-item:not(.is-active):hover {
  /*background-color: #0376bf !important;*/
  /*color: #fff;*/
}

el-menu-item:hover {
  background-color: #e6efff !important;
  color: #00a5f7;
}

.el-submenu__icon-arrow {
  margin-top: -5px;
}

.el-breadcrumb__inner {
  color: #fff !important;
}
.el-breadcrumb__separator {
  color: #fff;
}
.el-breadcrumb {
  margin-left: 10px;
}
el-dropdown-menu {
  margin-top: 20px !important;
}

th.el-table__cell {
  background-color: #f8f8f8 !important;
  color: #666;
}



</style>