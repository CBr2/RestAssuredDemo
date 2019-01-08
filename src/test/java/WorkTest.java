import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class WorkTest {
    public static String token=null;
    @BeforeClass
    public static void beforeClass(){
        RestAssured.useRelaxedHTTPSValidation();//https需要调用该api
    }
    @Before
    //https://work.weixin.qq.com/api/doc#
    //AgentId:1000002
    //自建应用Secret:J7k_Ks2l6erzFfrPWBa9dVgTkqDc8vdOOQF-OcnanVI
    //企业ID(corpid)：ww9e5a74238026fe8c
    public void getToken() {
        token = given()
                .param("corpid", "ww9e5a74238026fe8c")
                .param("corpsecret", "J7k_Ks2l6erzFfrPWBa9dVgTkqDc8vdOOQF-OcnanVI")
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken").prettyPeek()
                .then()
                .statusCode(200)
                .extract().path("access_token");
        System.out.println(token);
    }
    @Test
    //消息示例https://work.weixin.qq.com/api/doc#90000/90135/90236
    /*
    {
   "touser" : "UserID1|UserID2|UserID3",
   "toparty" : "PartyID1|PartyID2",
   "totag" : "TagID1 | TagID2",
   "msgtype" : "text",
   "agentid" : 1,
   "text" : {
       "content" : "你的快递已到，请携带工卡前往邮件中心领取。\n出发前可查看<a href=\"http://work.weixin.qq.com\">邮件中心视频实况</a>，聪明避开排队。"
   },
   "safe":0
}
    */
        public void sendMsg(){
            HashMap<String, String> content = new HashMap<String, String>();
            content.put("content", "你的快递已到，请携带工卡前往邮件中心领取。\n出发前可查看<a href=\"http://work.weixin.qq.com\">邮件中心视频实况</a>，聪明避开排队。");
            HashMap<String, Object> msg = new HashMap<String, Object>();
            msg.put("touser", "CBr2");
            msg.put("msgtype", "text");
            msg.put("agentid", "1000002");
            msg.put("text", content);
            given()
                    .queryParam("access_token", token)
                    .contentType(ContentType.JSON)
                    .body(msg)
                    .when().post("https://qyapi.weixin.qq.com/cgi-bin/message/send").prettyPeek()
                    .then().statusCode(200);
        }



}
