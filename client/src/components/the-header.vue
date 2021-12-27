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
        <!-- logout link -->
        <a class="login-menu" v-show="user.id">
          <span>Logout</span>
        </a>
      </a-popconfirm>
      <!-- show welcome message when a user is logged in -->
      <a class="login-menu" v-show="user.id">
        <span>Welcome, {{ user.name }}</span>
      </a>
      <!-- show login message when no one is logged in -->
      <a class="login-menu" v-show="!user.id" @click="showLoginModal">
        <span>Login</span>
      </a>
    </a-menu>

    <a-modal
        title="Login"
        v-model:visible="loginModalVisible"
        :confirm-loading="loginModalLoading"
        @ok="login"
    >
      <a-form :model="loginUser" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
        <a-form-item label="loginName">
          <a-input v-model:value="loginUser.loginName"/>
        </a-form-item>
        <a-form-item label="password">
          <a-input v-model:value="loginUser.password" type="password"/>
        </a-form-item>
      </a-form>
    </a-modal>
  </a-layout-header>
</template>

<script lang="ts">
import { defineComponent, ref, computed } from 'vue';
import axios from 'axios';
import { message } from 'ant-design-vue';
import store from "@/store";

declare let hexMd5: any;
declare let KEY: any;

//export header component
export default defineComponent({
  name: 'the-header',
  setup() {
    // save user state after login
    const user = computed(() => store.state.user);

    // used for login
    const loginUser = ref({
      loginName: "test",
      password: "test"
    });
    const loginModalVisible = ref(false);
    const loginModalLoading = ref(false);
    const showLoginModal = () => {
      loginModalVisible.value = true;
    };

    // login function
    const login = () => {
      console.log("login start");
      loginModalLoading.value = true;
      loginUser.value.password = hexMd5(loginUser.value.password + KEY);
      axios.post('/user/login', loginUser.value).then((response) => {
        loginModalLoading.value = false;
        const data = response.data;
        if (data.success) {
          loginModalVisible.value = false;
          message.success("Login Success！");

          store.commit("setUser", data.content);
        } else {
          message.error(data.message);
        }
      });
    };

    // logout function
    const logout = () => {
      console.log("logout start");
      axios.get('/user/logout/' + user.value.token).then((response) => {
        const data = response.data;
        if (data.success) {
          message.success("Logout Success！");
          store.commit("setUser", {});
        } else {
          message.error(data.message);
        }
      });
    };

    return {
      loginModalVisible,
      loginModalLoading,
      showLoginModal,
      loginUser,
      login,
      user,
      logout
    }
  }
});
</script>

<style>
.logo {
  width: 120px;
  height: 31px;
  /*background: rgba(255, 255, 255, 0.2);*/
  /*margin: 16px 28px 16px 0;*/
  float: left;
  color: white;
  font-size: 18px;
}

.login-menu {
  float: right;
  color: white;
  padding-left: 10px;
}
</style>