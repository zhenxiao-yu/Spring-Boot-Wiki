<template>
  <a-layout id="components-layout-demo-top-side-2">
    <TheHeader></TheHeader>
    <router-view/>
    <TheFooter></TheFooter>
  </a-layout>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
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

