<template>
  <div class="container d-flex justify-content-center" style="margin-top: 80px;">
    <div class="card card-body shadow rounded p-5" style="width: 100%; max-width: 1200px;">
      <form @submit.prevent="registerForm">

        
        <div class="mb-3 row align-items-center">
          <label class="col-sm-3 col-form-label fw-semibold">아이디</label>
          <div class="col-sm-6">
            <input type="text" class="form-control" v-model="form.userId" placeholder="아이디를 입력해주세요" :readonly="updateMode" @input="resetValidUserid" />
          </div>
          <div class="col-sm-3" v-if="!updateMode">
            <button type="button" class="btn btn-outline-secondary w-100" @click="checkUserId">중복확인</button>
          </div>
        </div>

        
        <div class="mb-3 row">
          <label class="col-sm-3 col-form-label">비밀번호</label>
          <div class="col-sm-9">
            <input type="password" class="form-control" v-model="form.userPasswd" placeholder="비밀번호를 입력해주세요" />
          </div>
        </div>

        
        <div class="mb-3 row">
          <label class="col-sm-3 col-form-label">비밀번호 확인</label>
          <div class="col-sm-9">
            <input type="password" class="form-control" v-model="form.userPasswd2" placeholder="비밀번호를 입력해주세요" />
          </div>
        </div>

        
        <div class="mb-3 row">
          <label class="col-sm-3 col-form-label">이름</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" v-model="form.userName" placeholder="이름을 입력해주세요" />
          </div>
        </div>

        
        <div class="mb-3 row">
          <label class="col-sm-3 col-form-label">이메일</label>
          <div class="col-sm-9">
            <input type="email" class="form-control" v-model="form.userEmail" placeholder="이메일을 입력해주세요" />
          </div>
        </div>

        
        <div class="mb-3 row">
          <label class="col-sm-3 col-form-label">생년월일</label>
          <div class="col-sm-9">
            <input type="date" class="form-control" v-model="form.userBirthday" />
          </div>
        </div>


        <div class="mb-3 row">
          <label class="col-sm-3 col-form-label">휴대전화</label>
          <div class="col-sm-9">
            <input type="tel" class="form-control" v-model="form.userPhone" placeholder="휴대폰 번호를 입력해주세요" />
          </div>
        </div>

        
        <div class="mb-3 row">
          <label class="col-sm-3 col-form-label">주소</label>
          <div class="col-sm-9">
            <div class="d-flex gap-2 mb-2 align-items-center">
              <input class="form-control w-50" v-model="form.addressNumber" placeholder="우편번호" readonly />
              <button type="button" class="btn btn-outline-primary" @click="KakoPost">주소 검색</button>
            </div>
            <input class="form-control mb-2" v-model="form.address" placeholder="주소" readonly />
            <input class="form-control" v-model="form.addressDetail" placeholder="상세 주소" />
          </div>
        </div>


        <div class="mb-3 row">
          <label class="col-sm-3 col-form-label">성별</label>
          <div class="col-sm-9 d-flex align-items-center">
            <div class="form-check me-4">
              <input class="form-check-input" type="radio" value="남" v-model="form.gender" />
              <label class="form-check-label">남</label>
            </div>
            <div class="form-check">
              <input class="form-check-input" type="radio" value="여" v-model="form.gender" />
              <label class="form-check-label">여</label>
            </div>
          </div>
        </div>

        <div class="mb-4 row">
      <label class="col-sm-3 col-form-label">취미</label>
      <div class="col-sm-9 d-flex flex-wrap gap-3">
        <div class="form-check form-check-inline" v-for="hobby in ['수영', '등산', '헬스', '런닝', '게임']" :key="hobby">
      <input class="form-check-input" type="checkbox" :value="hobby" v-model="form.habit" />
      <label class="form-check-label">{{ hobby }}</label>
    </div>
  </div>
</div>

        
        <div class="d-flex justify-content-end gap-3 mt-4">
          <button type="submit" class="btn btn-success px-5">{{ updateMode ? '수정' : '등록' }}</button>
          <button type="button" class="btn btn-secondary"  @click="resetForm" >초기화 </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ErrorCodes, onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
    const route = useRoute()
    const router = useRouter()
    
    const originalForm = ref({});
    const validUseridClicked = ref(false);
    
    const updateMode  = route.path.includes('/update');
    const form = reactive({
      userId: '',
      userPasswd: '',
      userPasswd2: '',
      userEmail: '',
      userName: '',
      userBirthday: '',
      userPhone: '',
      addressNumber: '',
      address:'',
      addressDetail: '',
      gender: '남',
      habit: []
    })

    onMounted(async ()=>{
      if(updateMode){        
      //   try {
      //       const userId = route.params.id;
      //       const response = await axios.get(`/api/member/${userId}`);
      //       Object.assign(form,response.data);
      //       form.userPasswd2 = form.userPasswd;
      //       form.habit = response.data.habit?.split(',') || [];

      //       originalForm.value = JSON.parse(JSON.stringify({...response.data, userPasswd2: response.data.userPasswd,
      //       habit: response.data.habit?.split(',') || []
      // }));
      //   }catch(e){
      //       alert("회원 정보를 불러올 수 없습니다.")
      //       console.error(e);
      //   }
      const userId = route.params.id;
      axios.get(`/api/member/${userId}`)
        .then(response => {
          Object.assign(form, response.data);
          form.userPasswd2 = form.userPasswd;
          form.habit = response.data.habit?.split(',') || [];

          originalForm.value = JSON.parse(JSON.stringify({
            ...response.data,
            userPasswd2: response.data.userPasswd,
            habit: response.data.habit?.split(',') || []
          }));
        })
        .catch(e => {
          alert("회원 정보를 불러올 수 없습니다.");
          console.error(e);
        });
  }else{
      originalForm.value = JSON.parse(JSON.stringify(form));
    }
  
  }
    
  
  )

    const KakoPost = () => {
      new daum.Postcode({
        oncomplete :(data) =>{
          form.addressNumber = data.zonecode;
          form.address= data.roadAddress;
        }

      }).open()
    }

    const registerForm = async () => {
      if (!updateMode&&!validUseridClicked.value) {
					alert("아이디 중복을 검사해주세요");
					return ;
				}

        if(form.userPasswd !== form.userPasswd2){
          alert("비밀번호가 맞지 않습니다.")
            form.userPasswd = ''
            form.userPasswd2 = ''
         
          return;
        }

    const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#$%^&*()_+\[\]{};':"\\|,.<>\/?]).{8,}$/;
    if (!passwordPattern.test(form.userPasswd)) {
				alert("비밀번호는 영문자, 숫자, 특수문자를 포함한 8자 이상이어야 합니다.");
				 return;
				}
      
    if(form.userName.trim().length===0){
      alert("이름을 입력해주세요");
      
      return;
    }
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if(form.userEmail.trim().length > 0 && !emailPattern.test(form.userEmail)){
      alert("이메일을 입력해주세요");
      return;
    }
    if (form.userBirthday.length ===0){
					alert("생년월일을 입력해주세요 ");
					
					return ;
		}
    const phonePattern = /^010-\d{4}-\d{4}$/;
    if ( form.userPhone.trim().length === 0 || !phonePattern.test(form.userPhone)) { 
      alert("전화번호를 정확히 입력해주세요. 예) 010-1234-5678");
    return;
}
    if (form.address.length ==0 ){
					alert("주소를 입력해주세요 ");
					
					return ;
		}

    if (form.addressDetail.length ==0 ){
					alert("상세주소를 입력해주세요 ");
					return ;
		}
    if (updateMode ? !confirm("수정 하시겠습니까?") : !confirm("회원 가입하시겠습니까?")) return;
    // try {
      const submitData = { ...form ,habit : form.habit.join(','),};
      delete submitData.userPasswd2;

      // let response ;
      let request;

      if(updateMode){
        const userId = route.params.id;
        request = axios.put(`/api/member/update/${userId}`, submitData);
        // response = await axios.put(`/api/member/update/${userId}`, submitData);
      }
      else{
        request = axios.post('api/member/insert', submitData);
        // response = await axios.post('api/member/insert',submitData);
      }
      request
      .then(response => {
      if (response.data === true) {
        alert(updateMode ? "회원정보가 수정되었습니다." : "회원가입이 완료되었습니다.");
        router.push(
          updateMode ? { name: 'memberDetail', params: { id: form.userId } }
            : { name: 'login' }
        );
      } else {
        alert(updateMode ? "회원정보가 수정되지 않았습니다." : "회원가입 실패 했습니다.");
      }
    })
    .catch(error => {
      alert("서버 오류가 발생했습니다.");
      console.error("회원 등록/수정 중 오류:", error);
    });
      
    //   if (response.data === true) {
    //     alert(updateMode ? "회원정보가 수정되었습니다." : "회원가입이 완료되었습니다.");
    //     router.push( updateMode ? { name: 'memberDetail', params: { id: form.userId } } : { name: 'login' }
    //     );}
    //   else{
    //     alert(updateMode ? "회원정보가 수정되지 않았습니다." : "회원가입 실패 했습니다.");
        

    //   }  
    // } catch (error) {
    //   alert("서버 오류가 발생했습니다.");
    // }


  
}



  const checkUserId = async () =>{
    if(form.userId.length < 8) {
      alert("아이디를 8자 이상 입력해주세요 ");
      return;
    }
    axios.post(`api/member/check`, {userId : form.userId})
    .then(response =>{
      if(response.data.exists){
        alert("이미 존재하는 아이디입니다.");
      validUseridClicked.value = false;
      }
      else {
      alert("사용 가능한 아이디입니다.");
      validUseridClicked.value = true;
      }
    })
    .catch(error => {
      console.error("중복확인 실패:", error);
      alert("중복확인 중 오류 발생");
      validUseridClicked.value = false;
    })


    // try {
    //   const response = await axios.post(`api/member/check`, {userId : form.userId});

    //   if(response.data.exists){
    //     alert("이미 존재하는 아이디입니다.");
    //   validUseridClicked.value = false;
    //   }
    //   else {
    //   alert("사용 가능한 아이디입니다.");
    //   validUseridClicked.value = true;
    // }
    // } catch (error) {
    //   console.error("중복확인 실패:", error);
    //   alert("중복확인 중 오류 발생");
    //   validUseridClicked.value = false;
      
    // }
  }

  const resetValidUserid = () =>{
    validUseridClicked.value=false;
  }


  const resetForm = () => {
  Object.assign(form, JSON.parse(JSON.stringify(originalForm.value)));
};
</script>

<style scoped>

</style> 