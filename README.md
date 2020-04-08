# YouShoes
예담직업전문학교 프로젝트 수제화쇼핑몰
> 진행기간 : 2020.02.28~2020.04.01
>> JSP기반 웹 프로젝트

### 1. 팀원
1. 유승우 - 프로필, 결제, 통계, 즐겨찾기, 공지사항 
2. 구교동 - 주문, 후기, 결제내역, 가게정보
3. 권우성 - 예약, 배송 
4. 배광준 - 로그인, 판매회원 관리, 구매자 메인 홈


### 2. 개발 환경
1. HTML
2. CSS
3. JavaScript
4. BootStrap
5. jQuery
6. Ajax
7. Oracle -> MySQL
8. Tomcat 8.5


### 3. 사용 API
1. 달력 API - FullCalendar
2. 통계 API - Chart.js
3. 지도 API - 카카오 지도 API 
4. 결제 API - I'mport; (아임포트)  

### 4. 프로시저 및 함수
* getimage(함수) - 작성자 : 유승우   
이미지 갖고옴
<pre><code>
CREATE OR REPLACE FUNCTION GETIMAGE (
  P_SECTION IN VARCHAR2, 
  P_SECTION_NO IN NUMBER ) 
RETURN VARCHAR2
  IS
    v_getImage varchar2(50);
  BEGIN  
    select img_name 
    into v_getImage 
    from image img join image_detail imgd 
    on img.img_no = imgd.img_no 
    where section=P_SECTION and section_no = P_SECTION_NO and LIMIT 1;
  RETURN v_getImage;
END getimage;
</code></pre>

* pointupdate(프로시저) - 작성자 : 유승우   
포인트 업데이트 
<pre><code>
create or replace PROCEDURE POINTUPDATE (
  P_no IN NUMBER , 
  P_CHARGEPOINT IN NUMBER ) AS
BEGIN
    update purchase_member 
    set point_now = point_now + p_chargepoint
    where pm_no= p_no;     
END POINTUPDATE;
</code></pre>

* holiday_import 프로시저 - 작성자 : 권우성   
정기 휴일 등록 및 취소
<pre><code>
create or replace PROCEDURE holiday_import
(p_week  in number,
p_day  in number,
p_sm_id in varchar2,
del_id in varchar2 )

is

day DATE;
CURSOR c_holiday is select 
day
from
(select to_date('20200101', 'yyyymmdd') + level - 1 day
from
dual
connect by 
level <= (to_date('20201231', 'yyyymmdd') - to_date('20200101', 'yyyymmdd') +1 )
)
where
to_char(day,'d') = p_day
and
to_char(day, 'w') = p_week;

BEGIN
open c_holiday;

loop
FETCH c_holiday INTO day;
exit when c_holiday%NOTFOUND;

if del_id = '1' then 
merge into daily_work
using dual
on (rest_date = day)
when MATCHED then
update set sm_id = p_sm_id
when not matched then
insert (sm_id, rest_date) values (p_sm_id, day);

elsif del_id = '2' then
delete daily_work where rest_date = day;

end if;
end loop;

CLOSE c_holiday;

commit;

END holiday_import;
</code></pre>

### 5. 두 프로젝트 간에 세션 공유 방법   
server에 있는 context.xml파일 수정(crossContext="true" 추가하기)    
<pre><code>
Context  =====>>>>>  Context crossContext="true"
</code></pre>    

server.xml파일 수정(emptySessionPath="true" 추가하기)
<pre><code>
Connector connectionTimeout="20000" port="8090" protocol="HTTP/1.1" redirectPort="8443"      
=======>>>>>>>>>         
Connector connectionTimeout="20000" port="8090" protocol="HTTP/1.1" redirectPort="8443" emptySessionPath="true"           
</code></pre>     

세션 세팅
<pre><code>
request.getSession().getServletContext().setAttribute("nid", nid);	//loginOkCommand에 설정 해 뒀습니다.    
</code></pre>

세션 GET
<pre><code>
HttpSession httpsession = request.getSession();
String nid = (String) httpsession.getServletContext().getContext("/youshoes").getAttribute("nid");	
request.setAttribute("nid", nid);
</code></pre>

세션 사용하실 때 서버 폴더 안에있는 context.xml과 server.xml설정이 되어있는지 확인         
프로젝트 실행시 youshoes 프로젝트를 실행시키고 실행시킬 때 서버에서 youshoes프로젝트랑 admin프로젝트 두개가 들어있어야 합니다.


