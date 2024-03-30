# apartment

2022년 대우직업능력학교에서 자바 과정을 배우고 한참이 지났다.
당시 몇몇의 회사에서 면접도 보고 하였지만 마음이 내키지 않았고 그렇게 시간이 흘렀다.
그러다 보니 점점 나태해지고 게을러져 가는 와중에 전에 다니던 시설관리 회사에서 잠시 일해달라는 요청이 왔다.
회사를 다니면서 공부를 해야지 하다가 점점 국비졸업생들이 많아지고 취업시장도 얿어 붙으면서 점점 경쟁력이 사라져갔다. 어쩌면 아예 없었을지도..
내 나이 31살 이러다 제대로 무언가를 이뤄본적도 없고 정말 늦었다는 생각이 든다.

일단 개인프로젝트를 하나 만들어야겠다는 생각이 들었다. 뭔가 결과물이라도 보여줘야 비벼볼 수 있다는 생각이 들었다.
유튜브에서 공공데이터포털 사이트에서 데이터를 받아 포트폴리오를 만들어 보라는 영상을 보았다 조회수가 높은걸 봐선 나 이외에도 많을 것이다.
먼저 사이트를 접속해보니 국토교통부 아파트 실거래가가 가장 눈에 띄었다. 실제로 아파트 실거래가를 보여주는 사이트를 접속해보니 상당히 잘 만들었다.
내가 이런걸 만들 수 있을까라는 생각도 들긴 했지만 뒤가 없는 만큼 무작정 만들어보자는 생각이 들었다.
학원에서는 스프링에 타임리프를 사용해서 백과 프런트를 구현했지만 이번에는 색 다른걸 해보고 싶었다.
구글링 결과 여러 유명한 사이트들이 react로 프런트를 구현한다는 걸 알았고 무턱대고 유튜브로 react 기초 영상을 보았다.
react는 상당히 빨랐고 기초 영상을 보았을 뿐이지만 그리 어렵게 느껴지지는 않았다.
스프링으로는 국토교통부 API로 데이터를 받아오는 API를 구현하고 그 데이터를 react에 뿌려주는 역할을 하면 된다는 것과
react에서는 다른 실거래가 정보 제공 사이트 들과 같이 카카오 지도 API 등을 사용해서 받아온 데이터를 가공해 보여주면 될 것 같았다.
서버는 AWS EC2를 이용해 Ubuntu를 사용하고 탄력적 ip를 발급받아 가비아에서 예전에 구입한 도메인을 연결해 서비스를 하면 되겠다는 생각을 하였다.


문제가 생겼다. 나는 '국토교통부 아파트매매 실거래 상세자료' 라는 XML로 구성된 데이터를 신청하였는데 다른 사이트와 같이 데이터를 가공해 제공하려면 너무 많은 과정이 필요했다.
이 API는 지역별코드 (11110 = 중구) 그리고 년도월 (200601) 를 파라미터로 데이터를 요청하는데 한 아파트 단지에 대한 거래내역을 연도에따라 한번에 보여줄려면 12번의 호출이 필요했고
한 단지를 위해 수많은 데이터를 요청하는 것은 너무 비효율적이라는 생각이 들었다.
그래서 해결방법으로는 데이터베이스에 API호출로 받아온 데이터를 저장해서 쿼리를 통해 데이터를 제공하는 것이 더 효율적이라는 생각이 들었다.
그렇게 대한민국 행정구역별로 테이블을 만들었고 데이터를 반복문을 통해 집어넣었다.
하지만 데이터는 2006년 1월부터 12월까지 2024년 3월(현재 작성월) 까지 집어넣어야 했고 공공데이터포털의 일일 개발용 트래픽양은 1000에 불과했기에 데이터를 넣을려면 많을 일수가 필요했다.
너무 비효율적이라는 생각이 들 때 또 하나의 문제를 발견했다.

내가 위에서 말한 '다른 사이트'는 국토교통부 실거래가 공개시스템 (https://rt.molit.go.kr/) 사이트다. 이 사이트를 보니 데이터가 미묘하게 달랐다.
확인해보니 '국토교통부 아파트매매 실거래 상세자료' 가 아닌 그냥 '국토교통부 아파트매매 자료' (상세가 빠진...) 를 바탕으로 만들어진 거였다.
또 자료도 엑셀이나 CSV 파일로 제공해주고 있었다.
그래서 API사용 허가를 새로 요청하고 엑셀파일을 제공받아 sqldeveloper 를 통해 데이터베이스에 저장을 하는것으로 정했다.
엑셀파일은 1년 단위로 제공했기에 햊정구역 17개의 2006년 부터 2024년까지 19개년 총 323개의 엑셀파일을 다운받았다. 이것도 하루에 100번만 다운받을수있어서 4일이 꼬박 걸렸다.
그리고 햊정구역별 테이블에 sqldeveloper의 데이터 임포트를 통해 데이터를 집어넣었다.
데이터가 많으니 메모리 부족 오류 (java heap memory) 가 계속 발생했고 메모리를 늘려줘도 몇 번 집어넣으면 컴퓨터가 계속 느려졌다.
그래서 검색 결과 sqlloader라는 것을 알게 되었고 엑셀데이터를 txt파일로 변환해 데이터를 좀 더 쉽게 넣을 수 있었다.
경기도는 200만개 가깝게 데이터가 들어갔고 행정구역별로 테이블을 구성한것이 잘 했나라는 생각이 들기도 했다. 중복을 제거하여 테이블을 구성하는 것을 정규화라고 알고있는데 이는 나중에 다시한번 고민해봐야겠다.

서버는 AWS EC2를 활용해 우분투 서버를 하나 구성하고 데이터베이스를 서버에 깔지 아니면 AWS의 RDS서비스를 이용할 지는 현재 고민중이다.

여기까지가 현재날짜 2024.3.30일 까지의 과정이다. 처음 시작부터 작성을 했으면 좋았겠다는 아쉬움이 있지만 지금 부터라도 꾸준히 작성해 개발과정을 담아보려한다.

