# 아파트 거래내역 조회 서비스

## 프로젝트 소개
국토교통부_아파트 매매 실거래가 상세 자료 Open Api를 이용해 각 시도별 아파트 거래내역을 확인할 수 있는 사이트 입니다.
<http://www.apttrade.info/> << 프로젝트 보러가기!!

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

