<template>
  <div class="container mt-5" style="max-width: 1000px;">
    <div class="card shadow-sm p-4" style="min-width: 700px; text-align: left;">
      <form @submit.prevent="boardRegisterForm">
        <div class="mb-3">
          <label class="form-label fw-semibold">게시글 종류</label>
          <select class="form-select" v-model="form.boardTypeId" :disabled="!isAdmin && !updateMode">
            <option :value="1">공지사항</option>
            <option :value="2">자유게시판</option>
          </select>
        </div>

        <div class="mb-3">
          <label class="form-label fw-semibold">제목</label>
          <input type="text" class="form-control" v-model="form.boardTitle" placeholder="제목을 입력해주세요" />
        </div>

        <div class="mb-3">
          <label class="form-label fw-semibold">작성자</label>
          <input type="text" class="form-control" v-model="form.boardWriter" readonly />
        </div>

        <div class="mb-3">
          <label class="form-label fw-semibold">내용</label>
          <textarea class="form-control" rows="5" v-model="form.boardContent" placeholder="내용을 입력해주세요"></textarea>
        </div>

        <div class="mb-4">
          <label class="form-label fw-semibold">비밀번호</label>
          <input type="password" class="form-control" v-model="form.boardPasswd" placeholder="비밀번호를 입력해주세요" />
        </div>

        <div class="d-flex gap-2 justify-content-end">
          <button type="submit" class="btn btn-success">{{ updateMode ? '수정' : '등록' }}</button>
          <button type="button" class="btn btn-secondary"  @click="resetForm" >초기화 </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { onMounted, reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

    const route = useRoute()
    const router = useRouter()
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));
    
    const updateMode  = route.path.includes('/update');

    const isAdmin = loginUser?.admin ==="Y";

    const form = reactive({
    boardNumber : '',
    boardTitle: '',
    boardContent: '',
    boardPasswd :'',
    boardWriter: loginUser?.userid || '',
    boardTypeId: '2',
  })

    const originalForm = reactive({});

// onMounted(async ()=>{
//   if(updateMode){
//     try {
//         const num = route.params.id;
//         const response = await axios.get(`/api/board/${num}`);
//         Object.assign(form,response.data);
//         originalForm.value = JSON.parse(JSON.stringify(response.data));
//     } catch (error) {
//         alert("게시물 정보를 불러올 수 없습니다.");
//         console.error(error);
//     }
//   }
//   else {
//     form.boardTypeId = isAdmin ? 1 : 2;
//     originalForm.value = JSON.parse(JSON.stringify(form));
// }

//       })

onMounted(() => {
      if (updateMode) {
        const num = route.params.id;
        axios.get(`/api/board/${num}`)
          .then(response => {
            Object.assign(form, response.data);
            Object.assign(originalForm, JSON.parse(JSON.stringify(response.data)));
          })
          .catch(error => {
            alert("게시물 정보를 불러올 수 없습니다.");
            console.error(error);
          });
      } else {
        form.boardTypeId = isAdmin ? 1 : 2;
        Object.assign(originalForm, JSON.parse(JSON.stringify(form)));
      }
    });



  //   const boardRegisterForm = async () =>{

  //       if(form.boardTitle.trim().length===0){
  //           alert("제목을 입력해주세요");
  //           return;
  //       }
  //       if(form.boardContent.trim().length===0){
  //           alert("내용을 입력해주세요");
  //           return;
  //       }
  //       if(form.boardPasswd.trim().length===0  >=4){
  //           alert("비밀번호를 4자리 이상 입력해주세요");
  //           return;       
  //   }
  //   if (updateMode ? !confirm("수정 하시겠습니까?") : !confirm("등록 하시겠습니까?")) return;

  //   try {
  //     let response ;
  //     let num;

  //     if(updateMode){
  //       num = route.params.id;
  //       response = await axios.put(`/api/board/update/${num}`, form);
  //     }
  //     else{
  //       response = await axios.post('/api/board/insert',form);
  //       num = response.data.boardNumber;
  //     }
      
  //     if (response.data) {
  //       alert(updateMode ? "게시물이 수정되었습니다." : "게시물 등록이 완료되었습니다.");
  //       router.push( { name: 'boardDetail', params: { id: num } } );
  // }
  //     else{
  //       alert(updateMode ? "게시물이 수정되지 않았습니다." : "게시물등록에  실패 했습니다.");
    
  //     }  
  //   } catch (error) {
  //     alert("서버 오류가 발생했습니다.");
  //   }

  //   }

  const boardRegisterForm = () => {
  if (form.boardTitle.trim().length === 0) {
    alert("제목을 입력해주세요");
    return;
  }
  if (form.boardContent.trim().length === 0) {
    alert("내용을 입력해주세요");
    return;
  }
  if (form.boardPasswd.trim().length < 4) {
    alert("비밀번호를 4자리 이상 입력해주세요");
    return;
  }

  if (updateMode ? !confirm("수정 하시겠습니까?") : !confirm("등록 하시겠습니까?")) return;

  let request;
  let num;

  if (updateMode) {
    num = route.params.id;
    request = axios.put(`/api/board/update/${num}`, form);
  } else {
    request = axios.post('/api/board/insert', form);
  }

  request
    .then(response => {
      if (updateMode) {
        if (response.data) {
          alert("게시물이 수정되었습니다.");
          router.push({ name: 'boardDetail', params: { id: num } });
        } else {
          alert("게시물이 수정되지 않았습니다.");
        }
      } else {
        if (response.data) {
          num = response.data.boardNumber;
          alert("게시물 등록이 완료되었습니다.");
          router.push({ name: 'boardDetail', params: { id: num } });
        } else {
          alert("게시물 등록에 실패했습니다.");
        }
      }
    })
    .catch(error => {
      alert("서버 오류가 발생했습니다.");
      console.error("등록/수정 실패:", error);
    });
};


    const resetForm = () => {
      Object.assign(form, JSON.parse(JSON.stringify(originalForm)));
    }
</script>

<style lang="scss" scoped>

</style>