package com.example.kovengerss.domain.dao;

import com.example.kovengerss.domain.vo.UserVO;
import com.example.kovengerss.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDAO {
    private final UserMapper userMapper;

    //회원 정보 조회
    public void userSelect(Integer userNum){;}
    //회원가입
    public void userInsert(UserVO userVO){;}
    //회원 정보 수정
    public void userUpdate(Integer userNum){;}
    //회원 탈퇴
    public void userDelete(UserVO userVO){;}
    //아이디 중복검사
    public void CheckId(String userId){;}
    //아이디 가져오기
    public void getId(UserVO userVO){;}
    //현재 비밀번호 가져오기
    public void getPw(UserVO userVO){;}
    //이메일 가져오기
    public void getEmail(UserVO userVO){;}
    //핸드폰 번호 가져오기
    public void getPhoneNumber(UserVO userVO){;}
    //출생년도 가져오기
    public void getBirth(UserVO userVO){;}
    //성별 가져오기
    public void getGender(UserVO userVO){;}
    //해당 회원 이상형 번호(ideal_num)가져오기
    public void getIdealNum(UserVO userVO){;}
}
