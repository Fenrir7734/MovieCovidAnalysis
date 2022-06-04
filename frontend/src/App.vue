<template>
  <NavBar/>
  <router-view :key="$route.fullPath"></router-view>
</template>

<script>

import NavBar from "@/components/NavBar";
export default {
  name: 'App',
  components: {NavBar}
}
</script>

<style>

</style>
