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
1. 카카오 Map Api를 활용해 지도를 제공합니다.
2. 지도에서 드래그 이벤트 발동시 맵의 중앙과 북동,북서,남동,남서 를 기준으로 해당 좌표의 시군구에 존재하는 아파트의 좌표를 지도에 마커로 찍습니다.
3. 마커는 아파트의 위치를 나타내며 해당 마커를 클릭시 클릭한 아파트의 거래내역을 모달창으로 제공합니다.
4. 좌표가 동일한 아파트가 여러개라면 인포윈도우로 해당 좌표의 아파트 목록을 보여줍니다.
5. 지도를 드래그아웃해 일정 크기 이상이 되면 마커 클러스터 기능이 작동해 좀더 깔끔하게 지도를 볼 수 있습니다.
6. 지도의 범위가 더 커지면 마커가 사라지고 범위가 좁아지면 다시 마커가 생성됩니다.
7. 거래내역은 년도별로 제공하며 월단위로 깔끔하게 확인할 수 있습니다.
8. 지번검색, 도로명검색을 제공하며 아파트의 이름을 검색할수 있고 검색된 아파트 내역을 클릭하면 해당 아파트의 좌표로 이동하며 거래내역이 모달창이 오픈됩니다.
9. 아파트 거래내역 데이터는 매일 02시에 자동으로 갱신됩니다.

## 추가 예정 기능
로그인 기능
- 현재는 관리자 아이디만 환경변수로 설정해 관리자 대시보드로 들어갈 수 있습니다. 추후 사용자 로그인 구현 예정입니다.
  
도로명주소 검색 기능

- 현재 도로명 검색은 거래내역이 있는 도로명주소만 나옵니다. 추후 행정안전부 도로명 주소 API 를 사용해 최신화된 도로명 주소 검색을 제공할 예정입니다.

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

