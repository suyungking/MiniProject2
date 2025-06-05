import { ref } from "vue";

const userList = [
  { userid: "user1", passwd: "1234", age: 25, birth: "1998-03-12", regDate: "2024-01-10" },
  { userid: "user2", passwd: "2345", age: 30, birth: "1993-06-25", regDate: "2024-01-12" },
  { userid: "user3", passwd: "3456", age: 22, birth: "2001-11-03", regDate: "2024-01-15" },
  { userid: "user4", passwd: "4567", age: 28, birth: "1995-09-17", regDate: "2024-01-18" },
  { userid: "user5", passwd: "5678", age: 33, birth: "1990-12-01", regDate: "2024-01-20" },
  { userid: "user6", passwd: "6789", age: 27, birth: "1996-07-14", regDate: "2024-01-22" },
  { userid: "user7", passwd: "7890", age: 21, birth: "2002-05-06", regDate: "2024-01-25" },
  { userid: "user8", passwd: "8901", age: 29, birth: "1994-02-28", regDate: "2024-01-27" },
  { userid: "user9", passwd: "9012", age: 31, birth: "1992-10-09", regDate: "2024-01-30" },
  { userid: "user10", passwd: "0123", age: 24, birth: "1999-01-21", regDate: "2024-02-01" },
  { userid: "user11", passwd: "1111", age: 26, birth: "1997-04-11", regDate: "2024-02-03" },
  { userid: "user12", passwd: "2222", age: 23, birth: "2000-08-19", regDate: "2024-02-05" },
  { userid: "user13", passwd: "3333", age: 35, birth: "1988-06-02", regDate: "2024-02-07" },
  { userid: "user14", passwd: "4444", age: 32, birth: "1991-03-29", regDate: "2024-02-10" },
  { userid: "user15", passwd: "5555", age: 27, birth: "1996-11-15", regDate: "2024-02-12" },
  {userid: 'hong123',
    passwd: '1234',
    name: '홍길동',
    email: 'hong123@example.com',
    birthday: '1990-05-01',
    phone: '010-1234-5678',
    addressNumber: '06236',
    address: '서울특별시 강남구 테헤란로 123',
    detailAddress: '101호',
    gender: '남',
    habit: ['헬스', '게임']},
];



  const loginProcess = (userid, passwd, onSuccess, onFail) => {
    const user = userList.find(
      (u) => u.userid === userid && u.passwd === passwd
    );
  
    if (user) {
      localStorage.setItem("loginUser", JSON.stringify({ userid: user.userid }));
      if (onSuccess) onSuccess();
    } else {
      if (onFail) onFail();
    }
  };


 const loginState = ref(null);

 

  const form = {
    userid: '',
    passwd: '',
    passwd2: '',
    name: '',
    email: '',
    birthday: '',
    address: '',
    detailaddress: '',
    gender: '남',
    phonenumber: '',
    habit: []
  };
export{userList,loginProcess,loginState}