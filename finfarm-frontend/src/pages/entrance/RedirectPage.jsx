import { useEffect } from 'react';
import axios from 'axios';
const { VITE_REACT_API_URL } = import.meta.env;

const RedirectPage = () => {
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
      accessToken: code, // 여기서 'code'를 'accessToken' 키의 값으로 설정
    };
  
    try {
      const response = await axios.post(`${VITE_REACT_API_URL}member/login`,
        JSON.stringify(dataToSend), // 수정된 데이터 객체를 JSON으로 변환
      { withCredentials: true, headers });
      console.log('Success:', response.data);
      
    } catch (error) {
      console.error('Error:', error.response ? error.response.data : error.message);
    }
  };
  

  return (
    <div>
      {/* 로딩 스피너나 메시지를 표시할 수 있습니다. */}
      로그인 처리 중입니다...
    </div>
  );
};

export default RedirectPage;
