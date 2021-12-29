<template>
  <a-layout>
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">

      <!-- search bar + add function-->
      <div>
        <a-form layout="inline" :model="param">
          <a-form-item>
            <!-- search bar -->
            <a-input v-model:value="param.name" placeholder="Name">
            </a-input>
          </a-form-item>
          <a-form-item>
            <!-- find button -->
            <a-button type="primary" @click="handleQuery({page: 1, size: pagination.pageSize})">
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

      <!-- ebook table -->
      <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="ebooks"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
      >
        <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar"/>
        </template>
        <template v-slot:category="{ text, record }">
          <span>{{ getCategoryName(record.category1Id) }} / {{ getCategoryName(record.category2Id) }}</span>
        </template>
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <router-link :to="'/admin/doc?ebookId=' + record.id">
              <a-button type="primary">
                Doc Manager
              </a-button>
            </router-link>
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

  <!-- edit ebook popup up window -->
  <a-modal
      title="ebook list"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
    <a-form :model="ebook" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <!--   cover input box   -->
      <a-form-item label="Cover">
        <a-input v-model:value="ebook.cover"/>
      </a-form-item>
      <!--  ebook title input box    -->
      <a-form-item label="Name">
        <a-input v-model:value="ebook.name"/>
      </a-form-item>
      <!--   category input box   -->
      <a-form-item label="Category">
        <a-cascader
            v-model:value="categoryIds"
            :field-names="{ label: 'name', value: 'id', children: 'children' }"
            :options="level1"
        />
      </a-form-item>
      <!--   description input box   -->
      <a-form-item label="Description">
        <a-input v-model:value="ebook.description" type="textarea"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';
import {message} from 'ant-design-vue';
import {Tool} from "@/util/tool";

export default defineComponent({
  name: 'AdminEbook',
  setup() {
    const param = ref(); // reference to parameter for searching
    param.value = {};
    const ebooks = ref();
    const pagination = ref({
      current: 1,
      pageSize: 10,
      total: 0
    });
    const loading = ref(false);

    //define columns data
    const columns = [
      {
        title: 'Cover',
        dataIndex: 'cover',
        slots: {customRender: 'cover'}
      },
      {
        title: 'Name',
        dataIndex: 'name'
      },
      {
        title: 'Category',
        slots: {customRender: 'category'}
      },
      {
        title: 'Doc Count',
        dataIndex: 'docCount'
      },
      {
        title: 'View Count',
        dataIndex: 'viewCount'
      },
      {
        title: 'Vote Count',
        dataIndex: 'voteCount'
      },
      {
        title: 'Action',
        key: 'action',
        slots: {customRender: 'action'}
      }
    ];

    //handle query about ebook list
    const handleQuery = (params: any) => {
      //set leading to true initially
      loading.value = true;
      // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据
      ebooks.value = [];
      axios.get("/ebook/list", {
        params: {
          page: params.page,
          size: params.size,
          //if value isn't null, look for ebook by name
          name: param.value.name
        }
      }).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          ebooks.value = data.content.list;

          // reset pager buttons to current page
          pagination.value.current = params.page;
          pagination.value.total = data.content.total;
        } else {
          message.error(data.message);
        }
      });
    };

    //execute when page nav buttons has been clicked
    const handleTableChange = (pagination: any) => {
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      });
    };

    // save or insert ebook
    const categoryIds = ref();
    //reference to an instance of ebook
    const ebook = ref();
    //popup window states
    const modalVisible = ref(false);
    const modalLoading = ref(false);
    //execute when 'ok is pressed on popup window
    const handleModalOk = () => {
      modalLoading.value = true; //start loading
      ebook.value.category1Id = categoryIds.value[0]; //save category 1
      ebook.value.category2Id = categoryIds.value[1]; //save category 2
      //POST ebook value to backend
      axios.post("/ebook/save", ebook.value).then((response) => {
        modalLoading.value = false; // end loading
        const data = response.data; // data = commonResp
        // check if new data has been saved successfully
        if (data.success) {
          modalVisible.value = false;
          //rerender ebook list on current page
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize,
          });
        } else {
          message.error(data.message);
        }
      });
    };

    //edit ebook function
    const edit = (record: any) => {
      //show edit window
      modalVisible.value = true;
      //copy record so that unsaved values won't be be displayed on the ebook list
      ebook.value = Tool.copy(record);
      categoryIds.value = [ebook.value.category1Id, ebook.value.category2Id]
    };

    //add ebook function
    const add = () => {
      //show add window
      modalVisible.value = true;
      //clear input value when adding
      ebook.value = {};
    };

    //delete ebook function
    const handleDelete = (id: number) => {
      //delete ebook by id
      axios.delete("/ebook/delete/" + id).then((response) => {
        const data = response.data; // data = commonResp
        // check if new data has been deleted successfully
        if (data.success) {
          // rerender ebook list on current page
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize,
          });
        } else {
          message.error(data.message);
        }
      });
    };

    const level1 = ref();
    let categorys: any;

    //handle query about category list
    const handleQueryCategory = () => {
      loading.value = true;
      axios.get("/category/all").then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          categorys = data.content;
          console.log("原始数组：", categorys);

          level1.value = [];
          // level1.value = Tool.array2Tree(categories, 0);
          console.log("树形结构：", level1.value);

          // 加载完分类后，再加载电子书，否则如果分类树加载很慢，则电子书渲染会报错
          handleQuery({
            page: 1,
            size: pagination.value.pageSize,
          });
        } else {
          message.error(data.message);
        }
      });
    };


    //get the name of a category by it's id
    const getCategoryName = (cid: number) => {
      // console.log(cid)
      let result = "";
      categorys.forEach((item: any) => {
        if (item.id === cid) {
          // return item.name;
          result = item.name;
        }
      });
      return result;
    };


    //return category when component loads (on mounted)
    onMounted(() => {
      handleQueryCategory();
    });

    return {
      param,
      ebooks,
      pagination,
      columns,
      loading,
      handleTableChange,
      handleQuery,
      getCategoryName,
      edit,//edit ebook
      add, //add new ebook
      ebook,
      modalVisible,
      modalLoading,
      handleModalOk,
      categoryIds,
      level1,
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
