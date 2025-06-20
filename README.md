# teamproject_qna
은행 문의 답변 프로젝트


사용 문의 쓰면 - 예금
예금 담당 문의 매니저가 해당 문의를 작성

예금 매니저는 예금 담당 문의만 볼수 있음

===============

database
oracle db

우선 순위
web

인증, 인가
jwt 방식
패스워드 암호화 - bCrypt

후순위
문의 - pagination


매니저는 /admin으로 다르게 구성

라이브러리
jpa
spring web
oracle driver
lombok
devtool
thymeleaf
bcrypt - 암호화
jwt관련 라이브러리

깃


회원
uid
username
password
name
role


매니저
mid - pk - auto
uid - 외래키
job - (예금, 대출, 카드)

문의 테이블
bid
title
content
writer - uid - 외래키
category


답변 테이블
aid
content
writer - mid
bid - 외래키





화면 정의서

처음 페이지
    - 로그인 페이지
        - username, password 입력창
        - 로그인 하기 버튼
        - 회원가입 이동 버튼
        - 로그인 성공 시 문의 페이지로 이동
    회원가입 페이지
        - username
        - password
        - name
        - 회원가입 버튼
        - 로그인 이동 버튼
    문의 페이지
        - 폼 제공
            - title
            - content
            - writer 자동 제공
        - 카테고리(카드, 적금, 대출) 선택 하기
        - 등록하기 버튼(문의 등록)
        - 마이페이지 이동 버튼 제공
    마이 페이지
        - 회원이 등록한 문의 내역
        - 제목 누르면 상세 보기 제공
        - 문의 페이지 이동 버튼 제공
    상세 페이지
        - 회원이 작성한 문의 상세 정보
        - 매니저가 작성한 답변을 볼수 있음
        - 마이페이지 이동 버튼

    매니저 페이지
        - 전체 문의 내역 보기
        - 현재 매니저 직무 문의 내역 보기
        - 문의 제목 선택시 매니저용 상세 페이지 이동
    매니저 문의 상세 페이지
        - 문의 정보 상세 제공
        - 답변을 달수 있는 기능 제공
        


웹, 앱 둘다 개발이 되면 둘 다 개발
사용자가 로그인
회원 상담사를 제공함 - 회원만 가능인듯
상담에 대한 카테고리(예금, ) 등등
삼담사는 문의가 들어오면 처리를 함

매니저는 문의가 들어온 것을 처리해야 함

만약 여력이 된다면
로그인 할 떄 패스워드를 암호화

사용자 인가 인증을 jwt로 구현
