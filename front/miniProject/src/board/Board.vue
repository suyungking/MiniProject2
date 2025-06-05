<template>
  <div class="container d-flex justify-content-center" style="margin-top: 90px;">
    <div style="width: 100%; max-width: 1000px;">
      <div class="row mb-3">
        <div class="col-auto">
          <select class="form-select form-select-sm shadow-sm border-primary" v-model="selectedType" @change="loadBoard">
            <option :value="1">공지사항</option>
            <option :value="2">자유게시판</option>
          </select>
        </div>
      </div>

      
      <div class="row align-items-center g-2 mb-4">
        <div class="col-auto">
          <label class="me-1">건수:</label>
          <select class="form-select form-select-sm d-inline-block w-auto shadow-sm border-primary" v-model="size" @change="changeSize">
            <option v-for="s in [10, 30, 50, 100]" :key="s" :value="s">{{ s }}</option>
          </select>
        </div>

        <div class="col-auto">
          <span class="small text-muted">({{ pages.pageNo }} / {{ pages.totalPage }})</span>
        </div>

        <div class="col">
          <input v-model="searchValue" class="form-control form-control-sm shadow-sm" placeholder="제목 또는 작성자를 입력하세요" style="min-width: 220px;" />
        </div>

        <div class="col-auto">
          <button class="btn btn-primary btn-sm px-3 shadow-sm" @click="movePage(1)">검색</button>
        </div>
      </div>

      
<div style="max-height: 600px; overflow-y: auto;">
  <table class="table table-bordered table-hover table-striped text-center align-middle shadow-sm">
    <thead class="table-dark" style="position: sticky; top: 0; z-index: 1;">
      <tr>
        <th>번호</th>
        <th>작성자</th>
        <th>제목</th>
        <th>등록날짜</th>
        <th>조회수</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(item, idx) in boardList" :key="item.boardNumber">
        <td>{{ pages.totalCount - ((pages.pageNo - 1) * size + idx) }}</td>
        <td>{{ item.boardWriter }}</td>
        <td>
          <router-link :to="{ name: 'boardDetail', params: { id: item.boardNumber } }" class="text-decoration-none text-dark fw-semibold">
            {{ item.boardTitle }}
          </router-link>
        </td>
        <td>{{ item.boardRegisterTime }}</td>
        <td>{{ item.viewCount }}</td>
      </tr>
      <tr v-if="boardList.length === 0">
        <td colspan="5" class="text-muted">검색결과가 없습니다.</td>
      </tr>
    </tbody>
  </table>
</div>


      <div class="d-flex justify-content-center my-3">
  
      <div class="btn-group">


    <button class="btn btn-outline-secondary btn-sm px-3 rounded-1 shadow-sm" 
    :disabled="!pages.prev"@click="movePage(pages.startPage - 1)">이전
    </button>


    <button v-for="n in pageRange" :key="n" class="btn btn-outline-primary btn-sm px-3 rounded-1 shadow-sm" 
    :class="{ active: pages.pageNo === n }" @click="movePage(n)">
      {{ n }}
    </button>

    <button class="btn btn-outline-secondary btn-sm px-3 rounded-1 shadow-sm" :disabled="!pages.next"
      @click="movePage(pages.endPage + 1)">다음
    </button>
    
  </div>
</div>

      <div class="text-end">
        <router-link v-if="loginUser" :to="{ name: 'boardRegister', params: { id: loginUser.userid } }">
          <button class="btn btn-success px-4 py-2 shadow">등록하기</button>
        </router-link>
      </div>
    </div>
  </div>
</template>



<script setup>
import axios from 'axios';
import { computed, onMounted, reactive, ref } from 'vue';


const selectedType = ref(1)
const pages = ref({pageNo: 1, totalPage:1, totalCount:0, startPage: 1,    
                  endPage: 1,prev: false,next: false  });
const size =ref(10); 
const boardList = ref([]);
const searchValue= ref("");

onMounted( ()=>{
  loadBoard();
})

  const loginUser = JSON.parse(localStorage.getItem("loginUser"));

//   const loadBoard = async () =>{
//   try {
//     const response = await axios.get(`/api/board/list/${selectedType.value}?page=${pages.value.pageNo}&size=${size.value}&searchValue=${searchValue.value}`);
//     boardList.value = response.data.list;
//     pages.value = {
//       pageNo: response.data.pageNo,
//       totalPage: response.data.totalPage,
//       totalCount: response.data.totalCount,
//       startPage: response.data.startPage,
//       endPage: response.data.endPage,
//       prev: response.data.prev,
//       next: response.data.next
//     };
//   } catch (e) {
//     alert("리스트를 불러올 수 없습니다.")
//     console.error(e);
    
//   }

// };
const loadBoard = () =>{
    axios.get(`/api/board/list/${selectedType.value}?page=${pages.value.pageNo}&size=${size.value}&searchValue=${searchValue.value}`)
    .then(response => {
      boardList.value = response.data.list;
      pages.value = {
      pageNo: response.data.pageNo,
      totalPage: response.data.totalPage,
      totalCount: response.data.totalCount,
      startPage: response.data.startPage,
      endPage: response.data.endPage,
      prev: response.data.prev,
      next: response.data.next
    };
    })
    
   .catch (e =>{
    alert("리스트를 불러올 수 없습니다.")
    console.error(e);
   }) 
    
    
  

};

const changeSize = () => {
  pages.value.pageNo = 1;
  loadBoard();
}

const movePage = (page) => {
  pages.value.pageNo = page;
  loadBoard();
};

const pageRange = computed(() => {
  const range = [];
  for (let i = pages.value.startPage; i <= pages.value.endPage; i++) {
    range.push(i);
  }
  return range;
});





</script>
<style>

</style>