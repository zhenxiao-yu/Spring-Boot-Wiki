<template>
  <a-layout-header class="header">
    <!--  logo  -->
    <div class="logo">Wiki</div>
    <a-menu
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <!--   NAVIGATION MENU   -->
      <!--   home page  -->
      <a-menu-item key="/">
        <router-link to="/">Home</router-link>
      </a-menu-item>
      <!--   user management   -->
      <a-menu-item key="/admin/user" :style="user.id? {} : {display:'none'}">
        <router-link to="/admin/user">Users</router-link>
      </a-menu-item>
      <!--   ebook management   -->
      <a-menu-item key="/admin/ebook" :style="user.id? {} : {display:'none'}">
        <router-link to="/admin/ebook">Ebooks</router-link>
      </a-menu-item>
      <!--   category management   -->
      <a-menu-item key="/admin/category" :style="user.id? {} : {display:'none'}">
        <router-link to="/admin/category">Categories</router-link>
      </a-menu-item>
      <!--   about page  -->
      <a-menu-item key="/about">
        <router-link to="/about">About</router-link>
      </a-menu-item>

      <!--   Logout confirmation   -->
      <a-popconfirm
          title="Confirm Logout?"
          ok-text="Yes"
          cancel-text="No"
          @confirm="logout()"
      >
        <a class="login-menu" v-show="user.id">
          <span>Logout</span>
        </a>
      </a-popconfirm>
      <!--   show welcome message when a user is logged in   -->
      <a class="login-menu" v-show="user.id">
        <span>Welcome, {{ user.name }}</span>
      </a>
      <!--   show login message when no one is logged in   -->
      <a class="login-menu" v-show="!user.id" @click="showLoginModal">
        <span>Login</span>
      </a>
    </a-menu>

    <a-modal
        title="登录"
        v-model:visible="loginModalVisible"
        :confirm-loading="loginModalLoading"
        @ok="login"
    >
      <a-form :model="loginUser" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
        <a-form-item label="登录名">
          <a-input v-model:value="loginUser.loginName" />
        </a-form-item>
        <a-form-item label="密码">
          <a-input v-model:value="loginUser.password" type="password" />
        </a-form-item>
      </a-form>
    </a-modal>

  </a-layout-header>
</template>

<script lang="ts">
import {defineComponent} from 'vue';

//export header component
export default defineComponent({
  name: 'the-header',
});
</script>