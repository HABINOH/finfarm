import { useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
const { VITE_REACT_API_URL } = import.meta.env;
const finfarm_URL = 'https://j10d203.p.ssafy.io/';

const RedirectPage = () => {
  const navigate = useNavigate();
  useEffect(() => {
    // URL에서 인가 코드를 추출합니다.
    const auth_code0 = new URLSearchParams(window.location.search).get('code');

    if (auth_code0) {
      console.log('인가코드가 존재하므로, 이것을 백엔드로 보냅니다.')
      console.log(auth_code0)
      sendCodeToBackend(auth_code0);
      console.log('추출한 인가코드를 백엔드에 보냈습니다. 백에서 잘 받았을까요?')
    } else {
      console.log('주소창에 추출할 인가코드가 없습니다. 힘내세요! ㅠㅠ');
    }
  }, []);

  const sendCodeToBackend = async (code) => {

    const headers = {
      "Content-Type": "application/json",
    };
  
    const dataToSend = {
      accessToken : code, // 여기서 'code'를 'authCode' 키의 값으로 설정
    };
  
    try {
      const res = await axios.post(`${VITE_REACT_API_URL}member/login`, JSON.stringify(dataToSend), { withCredentials: true, headers });
    
      console.log('백엔드에서 인가코드를 잘 받았고, 응답을 줬습니다.', res.data);
      localStorage.clear
      localStorage.setItem('accessToken', res.data.accessToken)

      if (res.data.member) { // member: True 인 경우, 로그인 처리
        console.log(`안녕하세요, ${res.data.memberNickname}님! 환영합니다.`);
        // 받은 모든 정보를 로컬 스토리지에 저장하고, 메인홈으로 이동.
        localStorage.setItem('memberCurPoint', res.data.memberCurPoint)
        localStorage.setItem('memberImageUrl', res.data.memberImageUrl)
        localStorage.setItem('memberNickname', res.data.memberNickname)
        localStorage.setItem('memberSolveQuiz', res.data.memberSolveQuiz)
        console.log('로그인완료. 메인화면으로 곧 이동합니다.')
        navigate('/home');

      } else { // member: False 인 경우, 회원가입 진행
        console.log('회원이 아니십니다. 회원가입 페이지로 이동하여 진행해주세요.');
        // 회원이 아닐 경우에도 백엔드로 이동하도록 수정할 수 있습니다.
        localStorage.setItem('memberEmail', res.data.memberNickname)
        console.log('회원가입 준비 중. 이메일 저장완료. 회원가입 페이지로 곧 이동합니다.')
        navigate('/entrance/signup');
      }
    } catch (error) {
      console.error('Error:', error.response ? error.response.data : error.message);
      // Todo.[에러핸들링] 어떤 에러인가에 따라서 사용자에게 더 명확한 피드백을 주는 것이 나아 보임. 특정 에러 별로 각각 다른 페이지로 리다이렉션 필요. 
    }
    
  };

  return (
    <div>
      {/* 로딩 스피너나 메시지를 표시할 수 있습니다. */}
      <p>조금만 기다려주세요.</p>
      <br />
      <p>회원이실 경우, 메인화면으로 이동합니다.</p>
      <br />
      <p>회원이 아닐 경우, 회원가입이 진행됩니다.</p>
    </div>
  );
};

export default RedirectPage;
