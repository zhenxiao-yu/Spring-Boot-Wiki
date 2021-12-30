<template>
  <a-layout>
    <a-layout-content
      :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <!-- search bar + add function-->
      <div>
        <a-form layout="inline" :model="param">
          <a-form-item>
            <!-- find button -->
            <a-button type="primary" @click="handleQuery()">
              Find
            </a-button>
          </a-form-item>
          <a-form-item>
            <!-- add button -->
            <a-button type="primary" @click="add()">
              Add
            </a-button>
          </a-form-item>
        </a-form>
      </div>
      <!-- tip -->
      <p>
        <a-alert
                class="tip"
                message="Tip：Categories shown here will be shown on home page"
                type="info"
                closable
        />
      </p>

      <!-- categories table -->
      <a-table
        v-if="level1.length > 0"
        :columns="columns"
        :row-key="record => record.id"
        :data-source="level1"
        :loading="loading"
        :pagination="false"
        :defaultExpandAllRows="true"
      >
        <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar" />
        </template>
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <!-- Change Button -->
            <a-button type="primary" @click="edit(record)">
              Change
            </a-button>
            <!-- popup confirm box-->
            <a-popconfirm
              title="Confirm Delete?"
              ok-text="Yes"
              cancel-text="No"
              @confirm="handleDelete(record.id)"
            >
              <!-- delete button -->
              <a-button type="danger">
                Delete
              </a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>

  <!-- edit category popup up window -->
  <a-modal
    title="Category List"
    v-model:visible="modalVisible"
    :confirm-loading="modalLoading"
    @ok="handleModalOk"
  >
    <a-form :model="category" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <!--  category title input box    -->
      <a-form-item label="Name">
        <a-input v-model:value="category.name" />
      </a-form-item>
      <a-form-item label="Parent">
        <a-select
          v-model:value="category.parent"
          ref="select"
        >
          <a-select-option :value="0">
            无
          </a-select-option>
          <a-select-option v-for="c in level1" :key="c.id" :value="c.id" :disabled="category.id === c.id">
            {{c.name}}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="Order">
        <a-input v-model:value="category.sort" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
  import { defineComponent, onMounted, ref } from 'vue';
  import axios from 'axios';
  import { message } from 'ant-design-vue';
  import {Tool} from "@/util/tool";

  export default defineComponent({
    name: 'AdminCategory',
    setup() {
      const param = ref();
      param.value = {};
      const categorys = ref();
      const loading = ref(false);

      const columns = [
        {
          title: 'Name',
          dataIndex: 'name'
        },
        // {
        //   title: 'Parent',
        //   key: 'parent',
        //   dataIndex: 'parent'
        // },
        {
          title: 'Order',
          dataIndex: 'sort'
        },
        {
          title: 'Action',
          key: 'action',
          slots: { customRender: 'action' }
        }
      ];

      /**
       * 一级分类树，children属性就是二级分类
       * [{
       *   id: "",
       *   name: "",
       *   children: [{
       *     id: "",
       *     name: "",
       *   }]
       * }]
       */
      const level1 = ref(); // 一级分类树，children属性就是二级分类
      level1.value = [];

      //handle query about category list
      const handleQuery = () => {
        //set loading to true initially
        loading.value = true;
        // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据
        level1.value = [];
        axios.get("/category/all").then((response) => {
          loading.value = false;
          const data = response.data;
          if (data.success) {
            categorys.value = data.content;
            console.log("Original：", categorys.value);

            level1.value = [];
            level1.value = Tool.array2Tree(categorys.value, 0);
            console.log("Tree：", level1);
          } else {
            message.error(data.message);
          }
        });
      };

      // save or insert category
      const category = ref({});
      const modalVisible = ref(false);
      const modalLoading = ref(false);
      const handleModalOk = () => {
        modalLoading.value = true;
        //POST category value to backend
        axios.post("/category/save", category.value).then((response) => {
          modalLoading.value = false; // end loading
          const data = response.data; // data = commonResp
          // check if new data has been saved suc
          if (data.success) {
            modalVisible.value = false;
            //rerender category list
            handleQuery();
          } else {
            message.error(data.message);
          }
        });
      };

      //edit category function
      const edit = (record: any) => {
        modalVisible.value = true;
        category.value = Tool.copy(record);
      };

      //add category function
      const add = () => {
        //show add window
        modalVisible.value = true;
        //clear input value when adding
        category.value = {};
      };

      //delete category function
      const handleDelete = (id: number) => {
        axios.delete("/category/delete/" + id).then((response) => {
          const data = response.data; // data = commonResp
          // check if data has been deleted successfully
          if (data.success) {
            //rerender category list
            handleQuery();
          } else {
            message.error(data.message);
          }
        });
      };

      //return category when component loads (on mounted)
      onMounted(() => {
        handleQuery();
      });

      return {
        param,
        // categorys,
        level1,
        columns,
        loading,
        handleQuery,
        edit,
        add,
        category,
        modalVisible,
        modalLoading,
        handleModalOk,
        handleDelete
      }
    }
  });
</script>

<style scoped>
  img {
    width: 50px;
    height: 50px;
  }
</style>
