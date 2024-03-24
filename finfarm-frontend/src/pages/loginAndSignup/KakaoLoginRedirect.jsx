import React, { useEffect } from 'react';
import axios from 'axios';

const RedirectPage = () => {
  useEffect(() => {
    // URL에서 인가 코드를 추출합니다.
    const code = new URLSearchParams(window.location.search).get('code');
    if (code) {
      sendCodeToBackend(code);
    }
  }, []);

  const sendCodeToBackend = async (code) => {
    try {
      const response = await axios.post('YOUR_BACKEND_ENDPOINT', {
        code,
      });
      console.log('Success:', response.data);
      // 여기서 추가적인 성공 로직을 처리할 수 있습니다.
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