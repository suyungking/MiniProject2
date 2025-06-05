<template>
    <div class="container d-flex justify-content-center" style="margin-top: 80px;">
      <div class="card p-5 shadow" style="width: 700px;">
        <h3 class="text-center mb-4">비밀번호 확인</h3>
  
        <div class="mb-3">
          <label class="form-label fw-semibold" >아이디 </label>
          <input type="text" class="form-control" v-model="info.userId" readonly  />
        </div>
  
        <div class="mb-4">
          <label class="form-label fw-semibold">비밀번호</label>
          <input type="password" class="form-control" v-model="info.userPasswd" />
        </div>
  
        <div class="d-grid gap-3">
          <button class="btn btn-primary" @click="memberUpdate">확인 </button>
  
        </div>
      </div>
    </div>
  </template>
  
<script setup>
import {  loginState } from "@/loginData/LoginSample";
import { onMounted, reactive } from "vue";
import { useRouter,useRoute } from "vue-router";
import axios from "axios";

    const router = useRouter();

    const route = useRoute();

    const info = reactive({ userId : "", userPasswd: "" });
    
    // onMounted(async () => {
    //     try {
    //         const userId = route.params.id;
    //         const response = await axios.get(`/api/member/user/${userId}`);
    //         Object.assign(info,response.data);


    //     }catch(e){
    //         alert("회원 정보를 불러올 수 없습니다.")
    //         console.error(e);
    //     }

    // })

    onMounted(() => {
        const userId = route.params.id;

        axios.get(`/api/member/user/${userId}`)
          .then(response => {
            Object.assign(info, response.data);
          })
          .catch(e => {
            alert("회원 정보를 불러올 수 없습니다.");
            console.error(e);
          });
      });

    

    // const memberUpdate = async ()=> {

    //     try {
    //       const userId = info.userId
    //       const response = await axios.post(`/api/member/checkUpdate/${userId}`, {
    //         pass: info.userPasswd
    //       })

    //       if (response.data === true) {
    //         router.push({ name: 'memberUpdate', params: { id: info.userId } })
    //       } else {
    //         alert("비밀번호가 일치하지 않습니다.")
    //       }
    //     } catch (e) {
    //       console.error("비밀번호 확인 오류:", e)
    //       alert("서버 오류로 확인에 실패했습니다.")
    //     }



        
    //       }


    const memberUpdate = () => {
      const userId = info.userId;

      axios.post(`/api/member/checkUpdate/${userId}`, {
        pass: info.userPasswd
      })
      .then(response => {
        if (response.data === true) {
          router.push({ name: 'memberUpdate', params: { id: info.userId } });
        } else {
          alert("비밀번호가 일치하지 않습니다.");
        }
      })
      .catch(e => {
        console.error("비밀번호 확인 오류:", e);
        alert("서버 오류로 확인에 실패했습니다.");
      });
    };
</script>

<style lang="scss" scoped>

</style>