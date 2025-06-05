<template>
  <div class="container mt-5" style="max-width: 1000px;">
    <div class="card shadow-sm" style="min-width: 700px; text-align: left;">
      <div class="card-header bg-primary text-white">
        <h5 class="mb-0">{{ board.boardNum.boardTitle }}</h5>
      </div>
      <div class="card-body">
        <p><strong>게시글 종류:</strong> {{ boardTypeName }}</p>
        <p><strong>게시글 번호:</strong> {{ board.boardNum.boardNumber }}</p>
        <p><strong>작성자:</strong> {{ board.boardNum.boardWriter }}</p>
        <p><strong>등록날짜:</strong> {{ board.boardNum.boardRegisterTime }}</p>
        <p><strong>조회수:</strong> {{ board.boardNum.viewCount }}</p>

        <hr />

        <div class="p-3 border rounded bg-light text-start mb-3" style="white-space: pre-line; min-height: 150px;">
          {{board.boardNum.boardContent }}
        </div>

        <div v-if="board.Author" class="d-flex justify-content-end gap-2">
          <button class="btn btn-danger" @click="leave">삭제하기</button>
          <button class="btn btn-secondary" @click="checkUpdate">수정하기</button>
          
        </div>
      </div>
    </div>
  </div>
</template>


<script setup>
    import { useRoute, useRouter } from 'vue-router'
    import { computed, onMounted, reactive, ref } from 'vue';
    import axios from 'axios';

    const route = useRoute()
    const router = useRouter()
    
    const loginUser = JSON.parse(localStorage.getItem("loginUser"))



    const board = reactive({
      boardNum : {},
      Author : false
    })

    const boadType = {
      1 : "공지사항",
      2:" 자유게시판",
    };

    const boardTypeName = computed(() => {
      return boadType[board.boardNum.boardTypeId] || "";
    })
    onMounted(async () => {
        // try {
        //     const num = route.params.id;
            
        //     if (loginUser) {
        //       const response = await axios.get(`/api/board/${num}`);    
        //       const writer = response.data.boardWriter;
        //       board.Author = loginUser.userid === writer;

        //     if(loginUser.userid !== writer && loginUser.admin !=='Y'){
        //       await axios.post(`/api/board/view/${num}`);
              
        //     }
        //   }
        //   const response = await axios.get(`/api/board/${num}`);
        //   Object.assign(board.boardNum, response.data);

        // }catch(e){
        //     alert("게시물 정보를 불러올 수 없습니다.");
        //     console.error(e);
        // }

        const num = route.params.id;

          if (loginUser) {
            axios.get(`/api/board/${num}`)
              .then(response => {
                const writer = response.data.boardWriter;
                board.Author= loginUser.userid === writer;
                if (loginUser.userid !== writer && loginUser.admin !== 'Y') {
                  return axios.post(`/api/board/view/${num}`);
                }
              })
              .then(() => {
                return axios.get(`/api/board/${num}`);
              })
              .then(response => {
                Object.assign(board.boardNum, response.data);
              })
              .catch(e => {
                alert("게시물 정보를 불러올 수 없습니다.");
                console.error(e);
              });
          } else {
            axios.get(`/api/board/${num}`)
              .then(response => {
                Object.assign(board.boardNum, response.data);
              })
              .catch(e => {
                alert("게시물 정보를 불러올 수 없습니다.");
                console.error(e);
              });
  }

          

      })


      const leave = async () => {
          const input = prompt("게시글 비밀번호를 입력하세요:")
          
          if (!input) return

          const num = route.params.id;
          // try {
          //     const response = await axios.post(`/api/board/delete/${num}`,{pass: input});
          //     if (response.data === true) {
          //         alert("게시물 삭제가 완료되었습니다.");
          //         router.push({ name: 'board' });
          //     } else {
          //         alert("비밀번호가 일치하지 않습니다.");
          //         }
          // } catch (error) {
          //     console.error("삭제 오류:", error);
          //     alert("서버 오류로 삭제에 실패했습니다.");
          // }
          axios.post(`/api/board/delete/${num}`,{pass: input})
          .then(r => {
            if(r.data===true){
              alert("게시물 삭제가 완료되었습니다.");
              router.push({ name: 'board' });
            }
            else{
              alert("비밀번호가 일치하지 않습니다.");
            }
          })
          .catch(e => {
            console.error("삭제 오류:", e);
            alert("서버 오류로 삭제에 실패했습니다.");
          })

        }

        const checkUpdate = async () => {
          const input = prompt("게시글 비밀번호를 입력하세요:")
          if (!input) return
          // try {
          //   const num = boardNum.value.boardNumber
          //   const response = await axios.post(`/api/board/checkUpdate/${num}`, {
          //     pass: input
          //   })

          //   if (response.data === true) {
          //     router.push({ name: 'boardUpdate', params: { id: num } })
          //   } else {
          //     alert("비밀번호가 일치하지 않습니다.")
          //   }

          // } catch (e) {
          //   console.error("비밀번호 확인 오류:", e)
          //   alert("서버 오류로 확인에 실패했습니다.")
          // }
          const num = board.boardNum.boardNumber
          axios.post(`/api/board/checkUpdate/${num}`, {pass: input})
          .then(r => {
            if( r.data ===true){
              router.push({ name: 'boardUpdate', params: { id: num } })
            }
            else{
              alert("비밀번호가 일치하지 않습니다.");
            }
          })
          .catch(e => {
            console.error("비밀번호 확인 오류:", e)
            alert("서버 오류로 확인에 실패했습니다.");
          })

        }


  </script>

  <style lang="scss" scoped>

  </style>
