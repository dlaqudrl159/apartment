# 아파트 거래내역 조회 서비스

## 프로젝트 소개
국토교통부_아파트 매매 실거래가 상세 자료 Open Api를 이용해 각 시도별 아파트 거래내역을 확인할 수 있는 사이트 입니다.
<https://www.apttrade.info/> << 프로젝트 보러가기!!

## 프로젝트 목적
1. 외부 API나 라이브러리를 사용해보는 경험을 가져보는 것
2. 외부 API나 라이브러리의 문서를 읽고 활용하는 능력을 키워보는 것
3. 백엔드와 프론트엔드를 구분해 서버 - 클라이언트 환경을 꾸려보고 데이터 통신을 해보는 것
4. 공공데이터 포털 API를 활용해 데이터를 제공받고 내가 원하는 형태로 가공해 데이터베이스에 저장해 활용하는 것
5. 내가 만든 웹어플리케이션을 클라우드 서버에 올리고 도메인 구매 후 적용해보는 것

## 자바를 쓴 이유와 느낀점
1. 자바는 대한민국에서 굉장히 많이쓰이며 그에 걸맞게 정보도 많을 거라고 생각함
2. 아파트 거래내역은 굉장히 대량의 데이터이며 자바의 컬력센프레임워크와 stream객체를 활용해 보다 쉽게 다룰 수 있다고 생각함
3. 자바의 프레임워크중 하나인 Spring을 사용해 웹어플리케이션을 보다 쉽게 개발 가능하다는 생각함

## 스프링을 쓴 이유와 느낀점
1. 스프링은 Component Scan을 사용해 객체(빈)을 자동으로 등록해주고 의존관계도 설정해주어 편리했다.
2. 다양한 어노테이션이 있어서 많은 코드를 줄일 수 있다. 예를 들면 @Controller 어노테이션을 쓰면 Servlet을 따로 만들지 않아도 되며 @Autowired를 통해 알아서 객체간 관계를 맺어주며 lombok같은 라이브러리를 사용해 getter, setter같은 메소드를 적을 필요도 없다.
   또 스프링은 제어의 역전 즉 Ioc를 통해 흐름이 스프링에 있어 개발자는 스프링이 정해준 흐름대로 코드를 짜면 되어 개발에 집중 가능했다.
4. Mybatis-Spring 연동모듈 사용으로 자바객체와 데이터베이스간에 자동으로 맵핑을 해주며 conn.close()같은 메소드를 쓰지 않아도 리소스를 반납해준다.
5. 스프링부트는 스프링레거시에 비해 자동으로 설정해주는 것이 많았다. properties 파일에 Datasource 나 logLevel을 적어두면 자동으로 적용도 해주며 톰캣이 내장서버로 존재해 server.port 등 서버 설정을 쉽게 바꿀수 있었다.

## 주요 기능
## 스케줄을 이용한 매일 최신 데이터 업데이트
 - 공공데이터 API 와 지오코더 API로 아파트의 거래내역, 좌표 데이터를 매일 최신화
 - Mybatis 배치 처리로 10만건이상의 데이터를 빠른 속도로 처리
 - 예외 처리를 통한 API 재호출 시도로 외부 API 사용 안정성 강화
 - 각종 파싱/컨버터를 구현 사용자 입장에서 보기 편한 데이터로 가공

## 최적화를 통한 서버 부하 메모리 사용량 감소
 - 드래그 시 좌표 데이터 요청 캐싱처리로 빠른 사용감 경험
 - useRef를 활용 이전 요청과 현재의 요청을 비교해 중복되는 데이터는 요청에서 제외 응답 속도 개선
 - 좌표 클러스터링을 활용해 클라이언트의 브라우저 부담감 감소

Velog : https://velog.io/@dlaqudrl15/series 주요 코드 및 문제 해결 정리

## 개발 환경
Backend
- Java
- Spring Boot
- Spring Security
- JWT
- Lombok
- MyBatis
- Junit
- Gradle
  
Frontend
- React
- Axios
- Http-Proxy-Middleware
- MUI(Material-UI)
- Styled-Component
- npm

Database
- Oracle
  
External API
- Kakao Map API

Ide&Tools
- STS (Spring Tool Suite)
- VSCode

배포 환경
- aws ec2
- aws route53

## 데이터베이스 구조
이 데이터베이스는 각 시도 아파트의 실거래가 정보와 위치 정보를 저장합니다.

예시는 부산테이블이며 각시도별로 테이블이 존재합니다.

COORDINATES 테이블은 아파트의 좌표 정보를 저장합니다.
```mermaid
erDiagram
    BUSAN ||--o{ COORDINATES : "has location"
    BUSAN {
        varchar2(100) SIGUNGU "시군구"
        varchar2(100) BUNGI "법정동"
        varchar2(100) BONBUN "본번"
        varchar2(100) BUBUN "부번"
        varchar2(100) APARTMENTNAME "아파트명"
        varchar2(100) AREAFOREXCLUSIVEUSE "전용면적"
        varchar2(100) DEALYEARMONTH "거래년월"
        varchar2(100) DEALDAY "거래일"
        varchar2(100) DEALAMOUNT "거래금액"
        varchar2(100) APARTMENTDONG "동"
        varchar2(100) FLOOR "층"
        varchar2(100) BUYERGBN "매수자구분"
        varchar2(100) SELLERGBN "매도자구분"
        varchar2(100) BUILDYEAR "건축년도"
        varchar2(100) ROADNAME "도로명"
        varchar2(100) CANCLEDEALDAY "해제거래일"
        varchar2(100) REQGBN "요청구분"
        varchar2(100) RDEALERLAWDNM "중개사법정동명"
        varchar2(100) REGISTRATIONDATE "등기일자"
        varchar2(100) SGGCD "시군구코드"
    }
    COORDINATES {
        varchar2(100) SIGUNGU "시군구"
        varchar2(100) BUNGI "법정동"
        varchar2(100) LAT "위도"
        varchar2(100) LNG "경도"
        varchar2(100) ROADNAME "도로명"
        varchar2(100) APARTMENTNAME "아파트명"
    }
  ```

