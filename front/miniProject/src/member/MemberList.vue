<template>
  <div class="container mt-4" style="max-width: 1400px;">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <div class="d-flex align-items-center gap-3">
        <label>건수:</label>
        <select class="form-select form-select-sm w-auto" v-model="state.size" @change="changeSize">
          <option v-for="s in [10, 20, 50, 100]" :key="s" :value="s">{{ s }}</option>
        </select>
        <span class="text-muted">({{ state.pages.pageNo }} / {{ state.pages.totalPage }})</span>

        <input v-model="state.searchValue" class="form-control form-control-sm ms-3" placeholder="아이디 검색" style="width: 200px;" />
        <button class="btn btn-primary btn-sm" @click="movePage(1)">검색</button>
      </div>
    </div>

    <div class="table-responsive" style="max-height: 600px; overflow-y: auto;">
  <table class="table table-bordered table-hover table-striped align-middle text-center shadow-sm">
    <thead class="table-dark" style="position: sticky; top: 0; z-index: 1;">
      <tr>
        <th style="white-space: nowrap;">번호</th>
        <th style="white-space: nowrap;">아이디</th>
        <th style="white-space: nowrap;">이름</th>
        <th style="white-space: nowrap;">생일</th>
        <th style="white-space: nowrap;">이메일</th>
        <th style="white-space: nowrap;">전화번호</th>
        <th style="white-space: nowrap;">우편번호</th>
        <th style="white-space: nowrap;">주소</th>
        <th style="white-space: nowrap;">상세 주소</th>
        <th style="white-space: nowrap;">성별</th>
        <th style="white-space: nowrap;">취미</th>
        <th style="white-space: nowrap;">등록일자</th>
        <th style="white-space: nowrap;">로그인 시간</th>
        <th style="white-space: nowrap;">로그인 실패 횟수</th>
        <th style="white-space: nowrap;">잠김 여부</th>
        <th style="white-space: nowrap;">회원 탈퇴 여부</th>
      </tr>
    </thead>

    <tbody>
      <tr v-for="(item, idx) in state.memberList" :key="item.userNumber">
        <td style="white-space: nowrap;">{{ state.pages.totalCount - ((state.pages.pageNo - 1) * state.size + idx) }}</td>
        <td style="white-space: nowrap;">{{ item.userId }}</td>
        <td style="white-space: nowrap;">{{ item.userName }}</td>
        <td style="white-space: nowrap;">{{ item.userBirthday }}</td>
        <td style="white-space: nowrap;">{{ item.userEmail }}</td>
        <td style="white-space: nowrap;">{{ item.userPhone }}</td>
        <td style="white-space: nowrap;">{{ item.addressNumber }}</td>
        <td>{{ item.address }}</td>
        <td>{{ item.addressDetail }}</td>
        <td style="white-space: nowrap;">{{ item.gender }}</td>
        <td style="white-space: nowrap;">{{ item.habit.replaceAll(',', ', ') }}</td>
        <td style="white-space: nowrap;">{{ item.registerTime }}</td>
        <td style="white-space: nowrap;">{{ item.loginTime }}</td>
        <td style="white-space: nowrap;">{{ item.failCount }}</td>
        <td style="white-space: nowrap;">{{ item.userLocked }}</td>
        <td style="white-space: nowrap;">{{ item.userAdmin }}</td>
      </tr>

      <tr v-if="state.memberList.length === 0">
        <td colspan="16" class="text-muted">검색결과가 없습니다.</td>
      </tr>
    </tbody>
  </table>
</div>


    <div class="d-flex justify-content-center my-3">
  <div class="btn-group">
      <button class="btn btn-outline-secondary btn-sm px-3 rounded-1 shadow-sm" :disabled="!state.pages.prev"
        @click="movePage(state.pages.startPage - 1)"> 이전
      </button>

      
      <button v-for="n in pageRange" :key="n" class="btn btn-outline-primary btn-sm px-3 rounded-1 shadow-sm"
        :class="{ active: state.pages.pageNo === n }"
        @click="movePage(n)"> {{ n }}
      </button>

      
      <button class="btn btn-outline-secondary btn-sm px-3 rounded-1 shadow-sm" :disabled="!state.pages.next"
      @click="movePage(state.pages.endPage + 1)"> 다음
      </button>

</div>
</div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { computed, onMounted, reactive, ref } from 'vue';

  const state = reactive({
    memberList: [],
    size: 10,
    searchValue: "",
    pages: {
      pageNo: 1,
      totalPage: 1,
      totalCount: 0,
      startPage: 1,
      endPage: 1,
      prev: false,
      next: false
  }
});


  // const loadMembers = async () => {
  // try {
  //   const response = await axios.get(
  //     `/api/member/list?page=${pages.pageNo}&size=${size}&searchValue=${searchValue.value}`
  //   );
  //   memberList.value = response.data.list;
  //   pages.value = {
  //     pageNo: response.data.pageNo,
  //     totalPage: response.data.totalPage,
  //     totalCount: response.data.totalCount,
  //     startPage: response.data.startPage,
  //     endPage: response.data.endPage,
  //     prev: response.data.prev,
  //     next: response.data.next
  //   };
  // } catch (e) {
  //   alert("리스트를 불러올 수 없습니다.");
  //   console.error(e);
  // }

// };
const loadMembers =  () => {
axios.get(`/api/member/list?page=${state.pages.pageNo}&size=${state.size}&searchValue=${state.searchValue}`)
  .then(response => {
    state.memberList = response.data.list;
    Object.assign(state.pages, {
        pageNo: response.data.pageNo,
        totalPage: response.data.totalPage,
        totalCount: response.data.totalCount,
        startPage: response.data.startPage,
        endPage: response.data.endPage,
        prev: response.data.prev,
        next: response.data.next
      });
    })
  .catch(e => {
    alert("리스트를 불러올 수 없습니다.");
    console.error(e);
  })
};

onMounted(() => {
  loadMembers(); 
});


   const movePage = (page) => {
    state.pages.pageNo = page;
  loadMembers();
};

const changeSize = () => {
  state.pages.pageNo = 1;
  loadMembers();
};
const pageRange = computed(() => {
  const range = [];
  for (let i = state.pages.startPage; i <= state.pages.endPage; i++) {
    range.push(i);
  }
  return range;
});

</script>

<style lang="scss" scoped>

</style>