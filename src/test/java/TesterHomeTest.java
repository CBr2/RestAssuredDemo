import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TesterHomeTest {
    @Test
    public void topics(){
        useRelaxedHTTPSValidation();//实现对https网站的处理
        get("https://testerhome.com/api/v3/topics.json")
                .then()
                .body("topics[0].title",containsString("对比两大 push 服务商个推&极光"));
    }
}
