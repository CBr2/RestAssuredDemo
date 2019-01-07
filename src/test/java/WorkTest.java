import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class WorkTest {
    public static String token=null;
    @BeforeClass
    public static void beforeClass(){
        RestAssured.useRelaxedHTTPSValidation();//https需要调用该api
    }
    @Test
    //https://work.weixin.qq.com/api/doc#
    //AgentId:1000002
    //自建应用Secret:J7k_Ks2l6erzFfrPWBa9dVgTkqDc8vdOOQF-OcnanVI
    //企业ID(corpid)：ww9e5a74238026fe8c
    public void getToken(){
        token=given()
                .param("corpid","ww9e5a74238026fe8c")
                .param("corpsecret","J7k_Ks2l6erzFfrPWBa9dVgTkqDc8vdOOQF-OcnanVI")
        .when()
        .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken" ).prettyPeek()
        .then()
                .statusCode(200)
                .extract().path("access_token");
        System.out.println(token);
    }
}
