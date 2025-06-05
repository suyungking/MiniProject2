<template>
  <div class="container">
    <Header></Header>
    <router-view></router-view>
  </div>
</template>

<script setup>
import Header from '@/components/Header.vue';
import { loginState } from './loginData/LoginSample';
    const saved = localStorage.getItem("loginUser");
    if(saved){
      loginState.value =JSON.parse(saved);
      }
 
 
</script>

<style scoped>
.container { text-align: center; margin-top:10px; }
</style>로