package com.example.kovengerss.domain.service;

import com.example.kovengerss.domain.dao.UserDAO;
import com.example.kovengerss.domain.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Qualifier("user")
@Primary
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final SmsService smsService;

    @Override
    public void userSelect(Integer userNum) {

    }

    @Override
    public void userInsert(UserVO userVO) {
        userDAO.userInsert(userVO);
    }

    @Override
    public void userUpdate(Integer userNum) {

    }

    @Override
    public boolean userDelete(int userNum) {
        return userDAO.userDelete(userNum);
    }

    @Override
    public boolean checkId(String userId) {
        if (userDAO.CheckId(userId)) {
            return true;
        }
        return false;
    }

    @Override
    public UserVO login(String userId, String userPw) {
        return userDAO.login(userId, userPw);
    }

    @Override
    public void userGetId(UserVO userVO) {

    }

    @Override
    public void userGetPw(UserVO userVO) {

    }

    @Override
    public void userGetEmail(UserVO userVO) {

    }

    @Override
    public void userGetPhoneNumber(UserVO userVO) {

    }

    @Override
    public void userGetBirth(UserVO userVO) {

    }

    @Override
    public void userGetGender(UserVO userVO) {

    }

    @Override
    public void userGetIdealNum(UserVO userVO) {

    }

    // 회원탈퇴
    @Override
    public boolean userDeleteByIdAndPw(String userId, String userPw) {
        if (userDAO.checkUserPw(userId, userPw)) {
            userDAO.userDeleteWithPw(userPw);
            log.info("userId {} 삭제되었습니다.", userId);
            return true;
        } else {
            log.info("userPw 불일치");
            return false;
        }
    }

    // 아이디 찾기 핸드폰 인증 번호
    @Override
    public void sendSms(Map<String, Object> map) {
        String userPhoneNum = (String) map.get("userPhoneNum");

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            stringBuilder.append(new Random().nextInt(10));
        }

        // 4자리 인증코드
        // 인증코드로 smsService호출
        smsService.sendMessage(String.valueOf(stringBuilder), userPhoneNum);

        map.put("authCode", stringBuilder);
        map.put("res", true);
    }

    // 아이디 찾기
    @Override
    public void findIdSuccess(Map<String, Object> map) {
        String userPhoneNum = (String) map.get("userPhoneNum");

        UserVO userVO = userDAO.findUserByPhoneNum(userPhoneNum);

        map.put("res", true);
        map.put("id", userVO.getUserId());

    }
    // 전체 이용자 수 메인에 뿌려주기
    @Override
    public int getUserTotalCount() {
        return userDAO.countUser();
    }


    // 입력한 이메일이 가입했는지 검사
    @Override
    public void sendEmail(Map<String, Object> map) {
        String userEmail = (String) map.get("email");
        boolean isFindEmail = false;


        UserVO findUserVO = userDAO.findUserByEmail(userEmail);

        if (findUserVO != null) {
            isFindEmail = true;

            String host = "smtp.naver.com";     //네이버 사용. 구글도 가능 대신 포트 번호 알맞게 변경 해야 함.
            int port = 465;

            // account Id, Password
            String username = "";            // 실제 네이버 이메일
            String password = "";              // 실제 네이버 비밀번호  #commit 시 항상 지워주고 보내주세요.

            java.util.Properties props = System.getProperties();                                   // SMTP 서버 정보 설정
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.trust", host);
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");

            //Session 생성
            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(username, password);
                }
            });

            session.setDebug(true);

            String recipient = userEmail;                         //받는 사람 메일주소
            String subject = "썸썸 비밀번호 찾기 인증 메일";         //메일 제목


            javax.mail.Message mimeMessage = new MimeMessage(session);
            try {
                mimeMessage.setFrom(new InternetAddress("dnrwhddla300@naver.com"));       // 발신 메일 주소
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                mimeMessage.setSubject(subject); //제목
                mimeMessage.setContent("안녕하세요 SUMSUM을 이용해주셔서 감사합니다. 회원님이 요청하신 비밀번호 발송해드렸습니다."
                        + "<br>" +
                        "회원님의 비밀번호 : " + findUserVO.getUserPw()
                        + "<br>" + "바로 접속하기 : <a>http://localhost:10030/login</a>", "text/html; charset=UTF-8"); //내용
                Transport.send(mimeMessage);
            } catch (javax.mail.MessagingException e) {
                e.printStackTrace();
            }
        }

        map.put("result", isFindEmail);
    }

    // 유저 포인트 가져오기
    @Override
    public Integer getUserPoint(Integer userNum) {
        Integer userPoint = userDAO.findUserPoint(userNum);
        return userPoint;
    }


}
