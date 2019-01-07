import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class TesterHomeTest {
    @BeforeClass
    public static void beforeAll() {
        useRelaxedHTTPSValidation();
    }

    @Test
    public void topics() {
        useRelaxedHTTPSValidation();//实现对https网站的处理
        get("https://testerhome.com/api/v3/topics.json")
                .then()
                .body("topics[0].title", containsString("如何说服一个前端修改其 bug？"));
    }

    @Test
    public void getDemo() {
        given()
                .log().all()
                .proxy(8080)
                .param("wd", "mp3")
                .param("ie", "utf-8")
                //如果用cookie，就要写全，否则会导致丢失，后面的header会不生效
                .cookie("BAIDUID", "8CB54A9066C557126099344569246B69:FG=1")
                .cookie("PSTM", "1516266460")
                .cookie("BIDUPSID", "766B87CF9897D7AE508966E8E476D4B9")
                .cookie("BDUSS", "XgzMXZFbC04LXJwQnRpb0ZTNGVtWEs1T3Y0Q3JGWmhwWHJ0R1d6RTE3Q0dTZUpiQVFBQUFBJCQAAAAAAAAAAAEAAABlUikks8IxMjA1OTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIa8uluGvLpbU")
                .cookie("MCITY", "-131%3A")
                .cookie("BD_UPN", "12314753")
                .cookie("H_PS_645EC", "776d1czmoCjkPZqSSOfAYf%2BaRJFpEf2BdlWJo3I7BFM35p9XQUUyUc9GSHuW7gvBxp2k")
                .cookie("", "")
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
    public void postDemo() {
        given()
                .proxy(8080)
                .formParam("j_username", "abc")
                .formParam("j_password", "123")
                .formParam("from", "/")
                .formParam("Submint", "Sign in")
                //   .body("")
                .when().post("http://jenkins.testing-studio.com:8080/j_acegi_security_check")
                .then().statusCode(302);
    }

    @Test
    //作业一：编写测试用例，实现发起get请求，搜索“霍格沃兹 测试学院”，断言状态码是否正确
    public void zuoye1Get() {
        given()
                .log().all()
                //      .proxy(8080)
                .param("wd", "霍格沃兹 测试学院")
                .param("ie", "utf-8")
                .get("https://www.baidu.com/s")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    //作业二：编写post测试用例，登陆雪球，输入错误的用户名密码即可
    public void zuoye2() {
        given()
                //      .proxy(8080)
                .param("username", "CBr2")
                .param("password", "123")
                .when().post("https://xueqiu.com/snowman/login")
                .then()
                .log().all()
                .statusCode(302);
    }

    @Test
    public void HtmlPathDemo() {
        given()
                .log().all()
                .proxy(8080)
                .param("wd", "mp3")
                .param("ie", "utf-8")
                //如果用cookie，就要写全，否则会导致丢失，后面的header会不生效
                .cookie("BAIDUID", "8CB54A9066C557126099344569246B69:FG=1")
                .cookie("PSTM", "1516266460")
                .cookie("BIDUPSID", "766B87CF9897D7AE508966E8E476D4B9")
                .cookie("BDUSS", "XgzMXZFbC04LXJwQnRpb0ZTNGVtWEs1T3Y0Q3JGWmhwWHJ0R1d6RTE3Q0dTZUpiQVFBQUFBJCQAAAAAAAAAAAEAAABlUikks8IxMjA1OTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIa8uluGvLpbU")
                .cookie("MCITY", "-131%3A")
                .cookie("BD_UPN", "12314753")
                .cookie("H_PS_645EC", "776d1czmoCjkPZqSSOfAYf%2BaRJFpEf2BdlWJo3I7BFM35p9XQUUyUc9GSHuW7gvBxp2k")
                .cookie("", "")
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                .header("Cookie",//cookie属于header部分
                        "BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; delPer=0; BD_CK_SAM=1; PSINO=2; BDSVRTM=221; H_PS_PSSID=26524_1446_25809_21079_18559_20691_28206_28131_26350_27244; WWW_ST=1546413044067")
                .get("https://www.baidu.com/s")
                .then()
                .log().all()
                .statusCode(200)
                .body("html.head.title", equalTo("mp3_百度搜索"))
                .body("**.findAll{it.@class=='nums_text'}", equalTo("百度为您找到相关结果约55,500,000个"));
        //这里运行失败，老师说是百度的bug，百度更新了网页 有个代码他们写错了 浏览器解析存在容错机制没问题  但是xpath发现不是标准xml
        // .body(hasXPath("//*[@class='nums_text' and contains(text(), '百度为您找到相关结果约55,500,000个')]"));
    }

    @Test
    public void JsonPathDemo() {
        String title = "写下 2019 你的三个小目标";
        given()
                .when()
                .get("https://testerhome.com/api/v3/topics.json")
                .then().statusCode(200)
                .body("topics.title[0]", equalTo("如何说服一个前端修改其 bug？"))
                .body("topics.size()", equalTo(20))
                //curl 'https://testerhome.com/api/v3/topics.json' |grep -o title |wc-l  验证title个数
                //.body("topics.findAll{it.title.contains('写下 2019 你的三个小目标')}.title",equalTo("[写下 2019 你的三个小目标]"))
                //findAll返回数组
                .body("topics.find{it.title.contains('写下 2019 你的三个小目标')}.title", equalTo(title));

    }

    @Test
    //作业三：找出来testerhome帖子回复数大于等于6的所有帖子数是不是等于8
    public void ZuoYe3() {
        given()
                .when()
                .get("https://testerhome.com/api/v3/topics.json")
                .then().statusCode(200)
                .body("topics.findAll{it.replies_count>6}.size()", equalTo(8));
        //curl 'https://testerhome.com/api/v3/topics.json' |grep -oE "replies_count[^,]*"查看帖子回复数，[^,]*表示0个或多个不为,的字符
    }

    @Test
    public void xmlPathDemo() {
        given()
                .when().get("http://jenkins.testing-studio.com:8080/job/AllureDemo/api/xml")
                .then()
                .statusCode(200)
                .body("freeStyleProject.displayName", equalTo("AllureDemo"))
                .body("..lastBuild.number", equalTo("19"))//结果值默认是字符串类型
                .body("..lastBuild.number.toFloat()", equalTo(19f))
                //数字断言
                .body("**.find {it.name()=='lastSuccessfulBuild'}.number", equalTo("1"))
                .body("**.find {it.name()=='lastSuccessfulBuild'}.number.toInteger()", equalTo(1))
                .body("**.findAll{it.number=='1'}[-1].url", equalTo("http://jenkins.testing-studio.com:8080/job/AllureDemo/1/"));
    }

    @Test
    public void hamcrestDemo() {
        given()
                .when().get("http://jenkins.testing-studio.com:8080/job/AllureDemo/api/xml")
                .then()
                .statusCode(200)
                .body("..lastBuild.number", equalTo("19"))//结果值默认是字符串类型
                .body("..lastBuild.number.toFloat()", greaterThanOrEqualTo(19f))
                .body("..lastBuild.number.toInteger()", greaterThanOrEqualTo(19))
                .body("..lastBuild.number.toDouble()", closeTo(19, 2))
                //数字断言
                .body("**.find {it.name()=='lastSuccessfulBuild'}.number", equalTo("1"))
                .body("**.find {it.name()=='lastSuccessfulBuild'}.number.toInteger()", equalTo(1))
                .body("**.findAll{it.number=='1'}[-1].url", equalTo("http://jenkins.testing-studio.com:8080/job/AllureDemo/1/"));
    }

    @Test
    //作业四：http://jenkins.testing-studio.com:8080/job/AllureDemo/api/xml 判断构建号>16并且节点名字等于build的job的url地址
    public void ZuoYe4() {
        given()
                .when().get("http://jenkins.testing-studio.com:8080/job/AllureDemo/api/xml")
                .then()
                .statusCode(200)
                // .body("..lastBuild.number.toInteger()",greaterThanOrEqualTo(16))
                //这里再看看
                .body("freeStyleProject.build.findAll{it.number.toInteger()>16}.url", hasItem("http://jenkins.testing-studio.com:8080/job/AllureDemo/18/"));
    }

    @Test
    public void jsonPost() {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("name", "广州研发中心");
        data.put("parentid", 1);
        data.put("order", 1);
        data.put("id", 2);

       /* given().proxy(8080)
                .queryParam("access_token", "xxxxx")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"北京研发中心\",\"id\":2,\"parentid\":1,\"order\":1}")
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create");   */
        given().proxy(8080)
                .queryParam("access_token", "xxxxx")
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create");
    }

    @Test
    public void xmlPostDemo() {
        given().proxy(8080)
                .queryParam("access_token", "xxxxx")
                .contentType(ContentType.XML)
                .body(new DemoBean(), ObjectMapperType.JAXB)
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create");
    }

    @Test
    public void timeoutDemo() {
        given().log().all().when()
                .get("https://testerhome.com/api/v3/topics.json")
                .then().log().all()
                .body("topics[0].title", containsString("Appium Studio"))
                .time(lessThanOrEqualTo(2L), TimeUnit.SECONDS)
                .time(lessThanOrEqualTo(2000L), TimeUnit.MILLISECONDS);

    }

    @Test
    public void schema() {
        given().when().get("https://testerhome.com/api/v3/topics.json")
                .then()
                .body(matchesJsonSchema(new File("D:\\霍格沃兹课程\\RestAssuredDemo\\CBr2.json")));
    }

    @Test
    //作业五：http://jenkins.testing-studio.com:8080/job/AllureDemo/api/json 编写json schema校验用例
    public void ZuoYe5() {
        given().
                when()
                .get("https://testerhome.com/api/v3/topics.json")
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(new File("D:\\霍格沃兹课程\\RestAssuredDemo\\ZuoYe5.json")));
    }
    @Test
    //extract实例，指所有处于extract后面的内容，将返回的数据存储给相应的变量
    public void extractDemo1(){
        HashMap<String, Object> topic=given()
                //.log().all()
        .when()
        .get("https://testerhome.com/api/v3/topics.json").prettyPeek()
        .then()
                //.log().all()
                .statusCode(200)
                .extract().path("topics.find{it.title.contains(\"求教\")}");
        System.out.println(topic);

        String login=given()
                //.log().all()
                .when()
                .get("https://testerhome.com/api/v3/topics.json").prettyPeek()
                .then()
                //.log().all()
                .statusCode(200)
                .extract().path("topics.find{it.title.contains(\"求教\")}.user.login");
        System.out.println(login);
    }
    @Test
    //extract实例，指所有处于extract后面的内容，将返回的数据存储给相应的变量,extractDemo1优化版
    public void extractDemo2(){
       ValidatableResponse validatableResponse=given().log().all()
                .when()
                .get("https://testerhome.com/api/v3/topics.json").prettyPeek()
                .then().log().all()
                .statusCode(200);

       HashMap<String,Object>topic=validatableResponse.extract().path("topics.find{it.title.contains(\"求教\")}");
       String login=validatableResponse.extract().path("topics.find{it.title.contains(\"求教\")}.user.login");
        System.out.println(topic);
        System.out.println(login);
    }
    @Test
    //extract实例，指所有处于extract后面的内容，将返回的数据存储给相应的变量,extractDemo1最终版;
    //找到topic存储到topic变量，找到帖子的login存储到login变量
    public void extractDemo3(){
        Response response=given().log().all()
                .when().get("https://testerhome.com/api/v3/topics.json").prettyPeek()
                .then().log().all()
                .statusCode(200)
                .extract().response();

        HashMap<String,Object> topic=response.path("topics.find{it.title.contains(\"求教\")}");
        String login=response.path("topics.find{it.title.contains(\"求教\")}.user.login");
        System.out.println(topic);
        System.out.println(login);
    }

}

