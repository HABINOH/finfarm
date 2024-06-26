package com.moneygang.finfarm.domain.member.controller;

import com.moneygang.finfarm.domain.member.dto.request.*;
import com.moneygang.finfarm.domain.member.dto.response.*;
import com.moneygang.finfarm.domain.member.entity.Member;
import com.moneygang.finfarm.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<Member>> selectAll() {
        return memberService.selcetAll();
    }

    @Operation(summary = "회원 로그인", description = "카카오 로그인에서 받은 인가코드를 통해 회원정보를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "(message : \"Success\", code : 200)",
            content = @Content(schema = @Schema(implementation = MemberLoginResponse.class)))
    @ApiResponse(responseCode = "400", description = "(message : \"잘못된 인가 코드입니다.\", code : 404)", content = @Content)
    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponse> kakaologin(@RequestBody MemberLoginRequest request) {
        return memberService.login(request);
    }

    @Operation(summary = "회원 가입", description = "회원 정보(이메일, 닉네임, 계좌번호, 이미지 주소)를 받아 회원가입을 진행합니다.")
    @ApiResponse(responseCode = "200", description = "(message : \"Success\", code : 200)",
            content = @Content(schema = @Schema(implementation = MemberJoinResponse.class)))
    @ApiResponse(responseCode = "400", description = "(message : \"회원가입 실패\", code : 400)", content = @Content)
    @PostMapping("/sign-up")
    public ResponseEntity<MemberJoinResponse> join(@RequestBody MemberJoinRequest request) {
        return memberService.join(request);
    }

    @Operation(summary = "자동 로그인", description = "access token 을 통해 로그인을 진행합니다.")
    @ApiResponse(responseCode = "200", description = "(message : \"Success\", code : 200)",
            content = @Content(schema = @Schema(implementation = MemberAutoLoginResponse.class)))
    @ApiResponse(responseCode = "401", description = """
            (message : "토큰이 만료되었습니다.", code : 401)
            
            (message : "유효하지 않은 토큰입니다.", code : 401)""", content = @Content)
    @PostMapping("/auto-login")
    public ResponseEntity<MemberAutoLoginResponse> autoLogin() {
        return memberService.autoLogin();
    }

    @Operation(summary = "토큰 재발급", description = "이메일과 refresh token 을 통해 access token, refresh token 을 재발급 받습니다.")
            @ApiResponse(responseCode = "200", description = "(message : \"Success\", code : 200)",
                    content = @Content(schema = @Schema(implementation = MemberReissueResponse.class)))
            @ApiResponse(responseCode = "400", description = "(message : \"refresh token 이 일치하지 않거나 존재하지 않습니다.\", code : 400)", content = @Content)
    @PostMapping("/reissue")
    public ResponseEntity<MemberReissueResponse> reissue(@RequestBody MemberReissueRequest request, @CookieValue(name = "refreshToken", defaultValue = "token") String refreshToken) {
        return memberService.reissue(request.getMemberEmail(), refreshToken);
    }

    @Operation(summary = "회원 탈퇴", description = "access token 에 해당하는 유저 이메일로 회원 탈퇴합니다.")
    @ApiResponse(responseCode = "200", description = "(message : \"Success\", code : 200)",
            content = @Content(schema = @Schema(implementation = MemberQuitResponse.class)))
    @ApiResponse(responseCode = "404", description = "(message : \"user not found\", code : 404)", content = @Content)
    @DeleteMapping("/quit")
    public ResponseEntity<MemberQuitResponse> quit() {
        return memberService.quit();
    }

    @Operation(summary = "마이페이지 조회", description = "access token 을 통해 유저 닉네임과 image 주소를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "(message : \"Success\", code : 200)",
            content = @Content(schema = @Schema(implementation = MemberMypageResponse.class)))
    @ApiResponse(responseCode = "404", description = "(message : \"user not found\", code : 404)", content = @Content)
    @GetMapping("/my-page")
    public ResponseEntity<MemberMypageResponse> getMyPage() {
        return memberService.getMypage();
    }

    @Operation(summary = "마이페이지 수정", description = "유저 닉네임과 프로필 사진을 변경합니다.")
    @ApiResponse(responseCode = "200", description = "(message : \"Success\", code : 200)",
            content = @Content(schema = @Schema(implementation = MemberUpdateResponse.class)))
    @ApiResponse(responseCode = "404", description = "(message : \"user not found\", code : 404)", content = @Content)
    @ApiResponse(responseCode = "404", description = "(message : \"파일이 존재하지 않습니다.\", code : 404)", content = @Content)
    @ApiResponse(responseCode = "500", description = "(message : \"AWS Server Error\", code : 500)", content = @Content)
    @PutMapping("/my-page")
    public ResponseEntity<MemberUpdateResponse> updateMyPage(MemberUpdateRequest request) {
        return memberService.updateMypage(request);
    }

    @Operation(summary = "프로필 사진 저장", description = "이미지를 서버에 저장한 후, 해당 url 을 제공합니다.")
    @ApiResponse(responseCode = "200", description = "(message : \"Success\", code : 200)",
            content = @Content(schema = @Schema(implementation = MemberProfileResponse.class)))
    @ApiResponse(responseCode = "400", description = "(message : \"Bad Request\", code : 400)", content = @Content)
    @PostMapping(value = "/profile", consumes = {"multipart/form-data"})
    public ResponseEntity<MemberProfileResponse> saveProfileImage(@ModelAttribute MemberProfileRequest request) {
        return memberService.saveProfileImage(request);
    }

    @Operation(summary = "닉네임 중복 검사", description = "닉네임의 중복 여부를 확인합니다.")
    @ApiResponse(responseCode = "200", description = "(message : \"Success\", code : 200)",
            content = @Content(schema = @Schema(implementation = MemberDuplicateNicknameResponse.class)))
    @ApiResponse(responseCode = "400", description = "(message : \"Bad Request\", code : 400)", content = @Content)
    @GetMapping("/nickname/is-exist/{nickname}")
    public ResponseEntity<MemberDuplicateNicknameResponse> checkNicknameDuplication(@PathVariable("nickname") String nickname) {
        return memberService.duplicateNickname(nickname);
    }

    @Operation(summary = "이메일 중복 검사", description = "이메일의 중복 여부를 확인합니다.")
    @ApiResponse(responseCode = "200", description = "(message : \"Success\", code : 200)",
            content = @Content(schema = @Schema(implementation = MemberDuplicateEmailResponse.class)))
    @ApiResponse(responseCode = "400", description = "(message : \"Bad Request\", code : 400)", content = @Content)
    @GetMapping("/email/is-exist/{email}")
    public ResponseEntity<MemberDuplicateEmailResponse> checkEmailDuplication(@PathVariable("email") String email) {
        return memberService.duplicateEmail(email);
    }

    @Operation(summary = "퀴즈 풀 수 있는지 검사", description = "하루에 한 문제만 풀 수 있습니다. 00시가 지나면 다시 풀 수 있습니다.")
    @ApiResponse(responseCode = "200", description = "(message : \"Success\", code : 200)",
            content = @Content(schema = @Schema(implementation = MemberQuizPossibleResponse.class)))
    @ApiResponse(responseCode = "404", description = "(message : \"user not found\", code : 404)", content = @Content)
    @GetMapping("/quiz-possible")
    public ResponseEntity<MemberQuizPossibleResponse> isQuizSolvePossible(){
        return memberService.isQuizSolvePossible();
    }

    @Operation(summary = "퀴즈 보상 획득", description = "5000 포인트를 지급합니다.")
    @ApiResponse(responseCode = "200", description = "(message : \"Success\", code : 200)",
            content = @Content(schema = @Schema(implementation = MemberGetQuizAwardResponse.class)))
    @ApiResponse(responseCode = "404", description = "(message : \"user not found\", code : 404)", content = @Content)
    @GetMapping("/quiz-award")
    public ResponseEntity<MemberGetQuizAwardResponse> getQuizAward(){
        return memberService.getQuizAward();
    }

}
