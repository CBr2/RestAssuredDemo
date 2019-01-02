import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TesterHomeTest {
    @BeforeClass
    public static void beforeAll(){
        useRelaxedHTTPSValidation();
    }
    @Test
    public void topics(){
        useRelaxedHTTPSValidation();//实现对https网站的处理
        get("https://testerhome.com/api/v3/topics.json")
                .then()
                .body("topics[0].title",containsString("MixMonkey 基于 Android Monkey"));
    }
    @Test
    public void getDemo(){
        given()
                .log().all()
                .proxy(8080)
                .param("wd","mp3")
                .param("ie","utf-8")
                //如果用cookie，就要写全，否则会导致丢失，后面的header会不生效
                .cookie("BAIDUID","8CB54A9066C557126099344569246B69:FG=1")
                .cookie("PSTM","1516266460")
                .cookie("BIDUPSID","766B87CF9897D7AE508966E8E476D4B9")
                .cookie("BDUSS","XgzMXZFbC04LXJwQnRpb0ZTNGVtWEs1T3Y0Q3JGWmhwWHJ0R1d6RTE3Q0dTZUpiQVFBQUFBJCQAAAAAAAAAAAEAAABlUikks8IxMjA1OTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIa8uluGvLpbU")
                .cookie("MCITY","-131%3A")
                .cookie("BD_UPN","12314753")
                .cookie("H_PS_645EC","776d1czmoCjkPZqSSOfAYf%2BaRJFpEf2BdlWJo3I7BFM35p9XQUUyUc9GSHuW7gvBxp2k")
                .cookie("","")
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
               .header("Cookie",//cookie属于header部分
                        "BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; delPer=0; BD_CK_SAM=1; PSINO=2; BDSVRTM=221; H_PS_PSSID=26524_1446_25809_21079_18559_20691_28206_28131_26350_27244; WWW_ST=1546413044067")
        .get("https://www.baidu.com/s")
        .then()
                .log().all()
                .statusCode(200);
    }
    @Test
    public void postDemo(){
        given()
                .proxy(8080)
                .formParam("j_username","abc")
                .formParam("j_password","123")
                .formParam("from","/")
                .formParam("Submint","Sign in")
             //   .body("")
        .when().post("http://jenkins.testing-studio.com:8080/j_acegi_security_check")
        .then().statusCode(302);
    }
    @Test
    //作业一：编写测试用例，实现发起get请求，搜索“霍格沃兹 测试学院”，断言状态码是否正确
    public void zuoye1Get(){
        given()
                .log().all()
                .proxy(8080)
                .param("wd","霍格沃兹 测试学院")
                .param("ie","utf-8")
        .get("https://www.baidu.com/s")
        .then()
                .log().all()
                .statusCode(200);
    }
    @Test
    //作业二：编写post测试用例，登陆雪球，输入错误的用户名密码即可
    public void zuoye2(){
        given()
                .proxy(8080)
                .param("username","CBr2")
                .param("password","123")
        .when().post("https://xueqiu.com/snowman/login")
        .then()
                .log().all()
                .statusCode(302);
    }
}
