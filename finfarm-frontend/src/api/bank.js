import { localAxios } from '@/utils/http-common';

const local = localAxios();

// 계좌 비밀번호 변경 함수
async function changePassword(passwordInfo) {
  try {
    const response = await local.put('/banking/account/password', {
      originPassword: passwordInfo.originPassword,
      changePassword: passwordInfo.changePassword,
      checkPassword: passwordInfo.checkPassword,
    });
    return response.data;
  } catch (error) {
    console.log(`계좌 비밀번호 변경 실패: ${error}`);
    throw error;
  }
}

// 출금 함수
async function withdrawCash(withdrawInfo) {
  try {
    const response = await local.post('/banking/account/withdraw', {
      amount: withdrawInfo.amount,
      accountPassword: withdrawInfo.password,
    });
    return response.data;
  } catch (error) {
    console.log(`출금 실패: ${error}`);
    throw error;
  }
}

// 입금 함수
async function depositCash(amount) {
  try {
    const response = await local.post('/banking/account/deposit', {
      amount: amount,
    });
    return response.data;
  } catch (error) {
    console.log(`입금 실패: ${error}`);
    throw error;
  }
}

// 계좌 내역 조회 함수
async function accountCheck(accountContent) {
  try {
    const response = await local.post('/banking/account', {
      startDate: accountContent.startDate,
      endDate: accountContent.endDate,
      accountType: accountContent.transitionType,
      accountNickname: accountContent.recordName,
      sortCriteria: accountContent.sortOrder,
    });

    const filterData = response.data;
    return filterData;
  } catch (error) {
    console.log(`계좌 내역 조회 실패: ${error}`);
    throw error;
  }
}

// 최근 이체 내역 확인 함수
async function recentTransferDetails() {
  try {
    const response = await local.get('/banking/account/remit/recent');
    return response.data;
  } catch (error) {
    console.log(`최근 이체 내역 확인 실패: ${error}`);
    throw error;
  }
}

// 송금 함수
async function accountTransfer(transferContent) {
  try {
    const response = await local.post('/banking/account/remit', {
      otherMemberPk: transferContent.recipient,
      amount: transferContent.amount,
      accountPassword: transferContent.password,
    });

    const remitData = response.data;
    return remitData;
  } catch (error) {
    console.log(`송금 실패: ${error}`);
    throw error;
  }
}

// 송금 유저 조회 함수
async function checkAnotherUser(anotherUser) {
  try {
    const response = await local.get('/banking/account/remit', {
      nickname: anotherUser,
    });
    return response.data;
  } catch (error) {
    console.log(`송금 유저 조회 실패: ${error}`);
    throw error;
  }
}

// 대출 받기
async function loan(loanInfo) {
  try {
    const response = await local.post('/banking/loan/take', {
      loanPk: loanInfo.loanPk,
      amount: loanInfo.amount,
      accountPassword: loanInfo.password,
    });
    return response.data;
  } catch (error) {
    console.log(`대출 실패: ${error}`);
    throw error;
  }
}

// 대출 상환
async function loanRepay(repayInfo) {
  try {
    const response = await local.post('/banking/loan/take', {
      loanHistoryPk: repayInfo.pk,
      repayAmount: repayInfo.repayAmount,
      accountPassword: repayInfo.password,
    });
    return response.data;
  } catch (error) {
    console.log(`대출 상환 실패: ${error}`);
    throw error;
  }
}

// 대출 내역 조회
async function loanHistory() {
  try {
    const response = await local.get('/banking/loan');
    return response.data;
  } catch (error) {
    console.log(`대출 내역 조회 실패: ${error}`);
    throw error;
  }
}

// 대출 심사
async function loanQualificate(loanPk) {
  try {
    const response = await local.get('/banking/loan/audit', {
      loanPk: loanPk,
    });
    return response.data;
  } catch (error) {
    console.log(`대출 심사 실패: ${error}`);
    throw error;
  }
}

export {
  changePassword,
  withdrawCash,
  depositCash,
  accountCheck,
  recentTransferDetails,
  accountTransfer,
  checkAnotherUser,
  loan,
  loanRepay,
  loanHistory,
  loanQualificate,
};