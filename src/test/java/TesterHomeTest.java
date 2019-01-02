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
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                .header("Cookie",//cookie属于header部分
                        "BDUSS=XgzMXZFbC04LXJwQnRpb0ZTNGVtWEs1T3Y0Q3JGWmhwWHJ0R1d6RTE3Q0dTZUpiQVFBQUFBJCQAAAAAAAAAAAEAAABlUikks8IxMjA1OTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIa8uluGvLpbU; MCITY=-131%3A; BD_UPN=12314753; H_PS_645EC=776d1czmoCjkPZqSSOfAYf%2BaRJFpEf2BdlWJo3I7BFM35p9XQUUyUc9GSHuW7gvBxp2k; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; delPer=0; BD_CK_SAM=1; PSINO=2; BDSVRTM=221; H_PS_PSSID=26524_1446_25809_21079_18559_20691_28206_28131_26350_27244; WWW_ST=1546413044067")
        .get("https://www.baidu.com/s")
        .then()
                .log().all()
                .statusCode(200);
    }
}
