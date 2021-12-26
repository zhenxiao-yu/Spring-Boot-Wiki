<template>
  <a-layout>
    <TheHeader></TheHeader>
    <router-view/>
    <TheFooter></TheFooter>
  </a-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import TheHeader from '@/components/the-header.vue'; // header
import TheFooter from '@/components/the-footer.vue'; // footer

export default defineComponent({
  //name of the component
  name: 'app',
  //using these components
  components: {
    TheHeader,
    TheFooter
  }
});
</script>

<style>
#components-layout-demo-top-side-2 .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}
</style>
