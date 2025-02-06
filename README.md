# 아파트 거래내역 조회 서비스

## 프로젝트 소개
국토교통부_아파트 매매 실거래가 상세 자료 Open Api를 이용해 각 시도별 아파트 거래내역을 확인할 수 있는 사이트 입니다.


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

## 개발 환경
- java
- spring
- spring boot
- spring security
- jwt
- lombok
- gradle
- oracle
- mybatis
- junit
- react
- axios
- http-proxy-middleware
- kakao map api
- mui
- styled-component
- npm
- sts
- vscode
- aws ec2
- aws route53
  
[![Top Langs](https://github-readme-stats.vercel.app/api/top-langs/?username=dlaqudrl159)](https://github.com/anuraghazra/github-readme-stats)

