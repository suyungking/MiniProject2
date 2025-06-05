<template>
    <div class="container d-flex justify-content-center" style="margin-top: 80px;">
      <div class="card p-5 shadow" style="width: 700px;">
        <h3 class="text-center mb-4">로그인</h3>
  
        <div class="mb-3">
          <label class="form-label fw-semibold">아이디</label>
          <input type="text" class="form-control" v-model="info.userId" />
        </div>
  
        <div class="mb-4">
          <label class="form-label fw-semibold">비밀번호</label>
          <input type="password" class="form-control" v-model="info.userPasswd" />
        </div>
  
        <div class="d-grid gap-3">
          <button class="btn btn-primary" @click="loginFetch">로그인</button>
  
          <router-link :to="{ name: 'memberRegister'}">
            <button class="btn btn-outline-secondary w-100">회원가입</button>
          </router-link>
        </div>
      </div>
    </div>
  </template>
  
<script setup>
import {  loginState } from "@/loginData/LoginSample";
import { reactive } from "vue";
import { useRouter,useRoute } from "vue-router";
import axios from "axios";

    const router = useRouter();

    const info = reactive({ userId: "", userPasswd: "" });
    const url = "/api/member/login";
    // const loginFetch = async() =>{
    //     try{
    //         const response = await axios.post(url ,info)
    //         if(response.status===200){
    //             const user = response.data;

    //             if (user.locked === "Y") {
    //               alert("해당 계정은 잠겨 있습니다. 관리자에게 문의하세요.");
    //               return; 
    //             }
    //             if (user.leave === "Y") {
    //               alert("탈퇴한 회원입니다. 관리자에게 문의하세요.");
    //               return; 
    //             }
    //             loginState.value= user;

    //             localStorage.setItem("loginUser", JSON.stringify(user));
    //             alert("로그인성공했습니다.");
    //             router.push({ name: 'board' });
    //         }
    //     }
    //     catch(error){
    //         console.error("로그인 실패",error)
    //         alert("아이디 또는 비밀번호가 틀렸습니다.");
    //     }  
    // };

    const loginFetch = ()  => {
      axios.post(url,info)
      .then(response => { 
        if(response.status===200){
          const user = response.data;
        
        if (user.locked === "Y") {
        alert("해당 계정은 잠겨 있습니다. 관리자에게 문의하세요.");
        return;
      }
      if (user.leave === "Y") {
        alert("탈퇴한 회원입니다. 관리자에게 문의하세요.");
        return;
      }
      loginState.value = user;
      localStorage.setItem("loginUser", JSON.stringify(user));
      alert("로그인 성공했습니다.");
      router.push({ name: 'board' });
    }
    })
    .catch(error => {
      console.error("로그인 실패", error);
    alert("아이디 또는 비밀번호가 틀렸습니다.");
    })
  }
            

    
</script>

<style lang="scss" scoped>

</style>