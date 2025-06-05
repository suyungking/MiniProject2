<template>
    <div class="container d-flex justify-content-center" style="margin-top: 80px;">
      <div class="card card-body shadow rounded p-5" style="width: 700px;">
  
        <p class="mb-3 d-flex justify-content-between">
          <strong>아이디</strong> <span>{{ paramId.userId }}</span>
        </p>
        <p class="mb-3 d-flex justify-content-between">
          <strong>비번</strong> <span>{{ paramId.userPasswd }}</span>
        </p>
        <p class="mb-3 d-flex justify-content-between">
          <strong>이름</strong> <span>{{ paramId.userName }}</span>
        </p>
        <p class="mb-3 d-flex justify-content-between">
          <strong>생일</strong> <span>{{ paramId.userBirthday }}</span>
        </p>
        <p class="mb-3 d-flex justify-content-between">
          <strong>전화번호</strong> <span>{{ paramId.userPhone }}</span>
        </p>
        <p class="mb-3 d-flex justify-content-between">
          <strong>우편번호</strong> <span>{{ paramId.addressNumber }}</span>
        </p>
        <p class="mb-3 d-flex justify-content-between">
          <strong>주소</strong> <span>{{ paramId.address }}</span>
        </p>
        <p class="mb-3 d-flex justify-content-between">
          <strong>상세주소</strong> <span>{{ paramId.addressDetail }}</span>
        </p>
        <p class="mb-3 d-flex justify-content-between">
          <strong>성별</strong> <span>{{ paramId.gender }}</span>
        </p>
        <p class="mb-3 d-flex justify-content-between">
          <strong>취미</strong> <span>{{ paramId.habit }}</span>
        </p>
        <p class="mb-3 d-flex justify-content-between">
          <strong>로그인시간</strong> <span>{{ paramId.loginTime }}</span>
        </p>
        <p class="mb-4 d-flex justify-content-between">
          <strong>등록날짜</strong> <span>{{ paramId.registerTime }}</span>
        </p>
  
        
        <div class="d-grid gap-3">
          <button class="btn btn-outline-danger" @click="logout">로그아웃</button>
  
          <router-link :to="{ name: 'VailedMember', params: { id: paramId.userId } }">
            <button class="btn btn-outline-primary w-100">수정하기</button>
          </router-link>
  
          <button class="btn btn-outline-secondary" @click="leave">탈퇴하기</button>
        </div>
  
      </div>
    </div>
  </template>
  
  

<script setup>
import { loginState } from '@/loginData/LoginSample';
import axios from 'axios';
import { onMounted, reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
    

    const paramId = reactive({});
    const route = useRoute()
    const router = useRouter()
    
    const logout = () => {
        localStorage.removeItem("loginUser");
        loginState.value = null;;
        alert("로그아웃 되었습니다.");
        router.push({ name: 'board' });

        }

    // onMounted(async () => {
    //     try {
    //         const userId = route.params.id;
    //         const response = await axios.get(`/api/member/${userId}`);
    //         Object.assign(paramId.value,response.data);

    //     }catch(e){
    //         alert("회원 정보를 불러올 수 없습니다.")
    //         console.error(e);
    //     }

    // })

      onMounted(() => {
        const userId = route.params.id;
        axios.get(`/api/member/${userId}`)
        .then(response =>{
          Object.assign(paramId,response.data);
        })
        .catch(error => {
          alert("회원 정보를 불러올 수 없습니다.")
          console.error(error);
        })

      })



    // const leave = async () => {
    //     if (!confirm("정말 탈퇴하시겠습니까?")) return;
    //     const userId = paramId.value.userId;
    //     try {
    //         const response = await axios.put(`/api/member/delete/${userId}`,paramId.value);
    //         if (response.data === true) {
    //             alert("회원 탈퇴가 완료되었습니다.");
    //             router.push({ name: 'login' });
    //         } else {
    //             alert("탈퇴 실패");
    //             }
    //     } catch (error) {
    //         console.error("탈퇴 오류:", error);
    //         alert("서버 오류로 탈퇴에 실패했습니다.");
    //     }


    // }

    const leave = () => {
      if (!confirm("정말 탈퇴하시겠습니까?")) return;
      const userId = paramId.userId;
      axios.put(`/api/member/delete/${userId}`,paramId)
      .then(response => {
        if(response.data===true){
          alert("회원 탈퇴가 완료되었습니다.");
          router.push({ name: 'login' });
        }
      })
      .catch(error =>{
        console.error("탈퇴 오류:", error);
        alert("서버 오류로 탈퇴에 실패했습니다.");
      })

    }
                

</script>

<style lang="scss" scoped>

</style>