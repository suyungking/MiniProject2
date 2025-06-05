import { createRouter, createWebHistory } from "vue-router";

// import Home from '@/member/Home.vue'
import LoginForm from '@/login/LoginForm.vue'
import RegisterForm from '@/login/RegisterForm.vue'
import Board from '@/board/Board.vue'
import BoardDetail from '@/board/BoardDetail.vue'
import MemberDetail from "@/member/MemberDetail.vue";
import BoardRegister from "@/board/BoardRegister.vue";
import MemberList from "@/member/MemberList.vue";
import NotFound from "@/components/NotFound.vue";
import VailedMember from "@/member/VailedMember.vue";

// import Home from '@/member/Home.vue'

const loginGuard = (to, from, next) => {
    const user = JSON.parse(localStorage.getItem("loginUser"))
    if (!user|| !user.userid) {
      alert('로그인이 필요합니다.')
      next('/login')
    } else {
      next()
    }
  }
  
  const adminGuard = (to, from, next) => {
    const user = JSON.parse(localStorage.getItem("loginUser"))
  
    if (!user||!user.userid) {
      alert('로그인이 필요합니다.')
      return next('/login')
    }
  
    if (user.admin !== 'Y') {
      alert('관리자만 접근 가능합니다.')
      return next('/')
    }
  
    next()
  }

const router = createRouter({
    history : createWebHistory(),
    routes : [
        {path : '/:paths(.*)*', name : 'NotFound', component : NotFound},



        {path : '/member/vailed/:id', name : 'VailedMember', component : VailedMember,beforeEnter: loginGuard},


        {path : '/',name : 'board' , component : Board},
        {path : '/board/:id',name : 'boardDetail' , component : BoardDetail},
        
        {path : '/login',name : 'login' , component : LoginForm},
        {path : '/register',name : 'memberRegister' , component : RegisterForm},

        {path : '/member/update/:id',name : 'memberUpdate' , component : RegisterForm,beforeEnter: loginGuard},

        {path : '/member/list',name : 'memberList' , component : MemberList,beforeEnter: adminGuard },

        {path : '/member/:id',name : 'memberDetail' , component : MemberDetail,beforeEnter: loginGuard},
        

        {path : '/board/register/:id',name : 'boardRegister' , component : BoardRegister,beforeEnter: loginGuard},
        {path : '/board/update/:id',name : 'boardUpdate' , component : BoardRegister,beforeEnter: loginGuard},
        
    ]

})


  
export default router;













