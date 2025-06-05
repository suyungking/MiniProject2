<template>
    <nav class="navbar navbar-expand-md bg-dark navbar-dark fixed-top shadow py-3 px-4" style="min-height: 60px;">
  
      <div class="d-flex align-items-center w-100">
  
        
        <span class="navbar-brand fs-3 fw-bold text-light me-4">게시판</span>
  
        
        <ul class="navbar-nav d-flex flex-row align-items-center gap-4">
  
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'board' }">홈</router-link>
          </li>
  
          <li class="nav-item" v-if="!isLogin">
            <router-link class="nav-link" :to="{ name: 'login' }">로그인</router-link>
          </li>
  
          <li class="nav-item" v-else>
            <router-link :to="{ name: 'memberDetail', params: { id: userid } }">
              <span class="nav-link text-white fw-semibold">{{ userid }}님</span>
            </router-link>
          </li>
  
          <li class="nav-item" v-if="isAdmin">
            <router-link class="nav-link text-white fw-semibold" :to="{ name: 'memberList' }">회원 목록</router-link>
          </li>
  
        </ul>
  
      </div>
  
    </nav>
  </template>

<script setup>
import { loginState } from '@/loginData/LoginSample';
import { reactive, computed, ref, onMounted } from 'vue';
        
        const isLogin = computed(() => !!loginState.value);
        const isAdmin = computed(() => loginState.value?.admin === "Y");
        const userid = computed(() => loginState.value?.userid || "");
        
        
        
        onMounted(() => {
            const user = JSON.parse(localStorage.getItem("loginUser"));
            if(user){
                loginState.value= user
            }
        });

</script>

<style lang="scss" scoped>

</style>