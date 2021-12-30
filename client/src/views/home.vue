<template>
  <a-layout>
    <!--  SIDE MENU  -->
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
          @click="handleClick"
          :openKeys="openKeys"
      >
        <!--  link to show welcome component -->
        <a-menu-item key="welcome">
          <MailOutlined/>
          <span>Welcome</span>
        </a-menu-item>
        <!-- show parent categories -->
        <a-sub-menu v-for="item in level1" :key="item.id" :disabled="true">
          <template v-slot:title>
            <span><user-outlined/>{{ item.name }}</span>
          </template>
          <a-menu-item v-for="child in item.children" :key="child.id">
            <MailOutlined/>
            <span> {{ child.name }} </span>
          </a-menu-item>
        </a-sub-menu>

        <a-menu-item key="tip" :disabled="true">
          <span>Configure In Categories</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <div class="welcome" v-show="isShowWelcome">
        <the-welcome></the-welcome>
      </div>
      <a-list v-show="!isShowWelcome" item-layout="vertical" size="large" :grid="{ gutter: 20, column: 3 }"
              :data-source="ebooks">
        <template #renderItem="{ item }">
          <a-list-item key="item.name">
            <template #actions>
              <span>
                <component v-bind:is="'FileOutlined'" style="margin-right: 8px"/>
                {{ item.docCount }}
              </span>
              <span>
                <component v-bind:is="'UserOutlined'" style="margin-right: 8px"/>

                {{ item.viewCount }}
              </span>
              <span>
                <component v-bind:is="'LikeOutlined'" style="margin-right: 8px"/>
                {{ item.voteCount }}
              </span>
            </template>
            <a-list-item-meta :description="item.description">
              <template #title>
                <router-link :to="'/doc?ebookId=' + item.id">
                  {{ item.name }}
                </router-link>
              </template>
              <template #avatar>
                <a-avatar :src="item.cover"/>
              </template>
            </a-list-item-meta>
          </a-list-item>
        </template>
      </a-list>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref, reactive, toRef} from 'vue';
import axios from 'axios';
import {message} from 'ant-design-vue';
import {Tool} from "@/util/tool";
import TheWelcome from '@/components/the-welcome.vue';


export default defineComponent({
  name: 'Home',
  components: {
    TheWelcome
  },
  setup() {
    //use ref() to make ebooks a dynamic data
    const ebooks = ref();
    //using reactive
    // const ebooks1 = reactive({books: []});

    const openKeys = ref();

    const level1 = ref();

    let categorys: any;

    // return list of all categories
    const handleQueryCategory = () => {
      axios.get("/category/all").then((response) => {
        const data = response.data;
        if (data.success) {
          categorys = data.content;
          console.log("Original：", categorys);
          //open child categories when loading finishes
          openKeys.value = [];
          for (let i = 0; i < categorys.length; i++) {
            openKeys.value.push(categorys[i].id)
          }

          level1.value = [];
          level1.value = Tool.array2Tree(categorys, 0);
          console.log("Tree：", level1.value);
        } else {
          message.error(data.message);
        }
      });
    };

    const isShowWelcome = ref(true);
    let categoryId2 = 0;

    const handleQueryEbook = () => {
      axios.get("/ebook/list", {
        params: {
          page: 1, //1 page
          size: 1000, //max 1000 ebooks per page
          categoryId2: categoryId2
        }
      }).then((response) => {
        const data = response.data;
        ebooks.value = data.content.list;
        // ebooks1.books = data.content;
      });
    };

    const handleClick = (value: any) => {
      // console.log("menu click", value)
      if (value.key === 'welcome') {
        isShowWelcome.value = true;
      } else {
        categoryId2 = value.key;
        isShowWelcome.value = false;
        handleQueryEbook();
      }
      // isShowWelcome.value = value.key === 'welcome';
    };

    onMounted(() => {
      handleQueryCategory();
    });

    //return dynamic ebooks data
    return {
      ebooks,
      // ebooks2: toRef(ebooks1, "books")
      // listData,
      pagination: {
        onChange: (page: any) => {
          console.log(page);
        },
        pageSize: 3,
      },
      handleClick,
      level1,
      isShowWelcome,
      openKeys
    }
  }
});
</script>

<!--style for current component-->
<style scoped>
  .ant-avatar {
    width: 50px;
    height: 50px;
    line-height: 50px;
    border-radius: 8%;
    margin: 5px 0;
  }
</style>