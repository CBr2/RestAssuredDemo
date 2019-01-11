import io.restassured.builder.ResponseBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;
import java.lang.String;
import java.util.Base64;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
//作业2：利用filter完成对 http://jenkins.testing-studio.com:9001/base64base64.json 的解密
//Filter的机制given + when -> filter -> alter request -> server -> origin response -> filter -> alter response -> then
public class homework_filter{
    public static Filter decodeFilter=new Filter() {
        @Override
        public Response filter(FilterableRequestSpecification req, FilterableResponseSpecification res, FilterContext filterContext) {
            Response response=filterContext.next(req, res);
            // String resp = response.body().asString().trim().replace("\n","");
            //String decode1 = new String(Base64.getDecoder().decode(resp)).trim();
            String decode1 = new String(Base64.getDecoder().decode(response.body().asString().trim().replace("\n",""))).trim();
            String decode2 = new String(Base64.getDecoder().decode(decode1)).trim();
            Response responseNew=new ResponseBuilder().clone(response)
                    .setBody(decode2)
                    .setContentType(ContentType.JSON)
                    .build();
            return responseNew;
        }
    };
    @Test
    public void decodeByFilter(){
        given().log().all()
                //.proxy(8080)
                .filter(decodeFilter)
                .auth().basic("hogwarts", "123456")
                .when().log().all().header("USER", "CBr2")
                .get("http://jenkins.testing-studio.com:9001/base64base64.json")
                .then().statusCode(200).body("data.items.quote.name[0]", equalTo("上证指数"));
    }
}


