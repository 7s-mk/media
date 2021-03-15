package cn.edu.wtu.wtr.media;

import cn.edu.wtu.wtr.media.util.education.login.JWClient;
import cn.edu.wtu.wtr.media.util.education.model.Course;
import cn.edu.wtu.wtr.media.util.education.model.CourseJSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-15-20:58
 * @since 2021-03-15-20:58
 */
public class JWTest {

    public static void main(String[] args) throws Exception {
        new JWTest().test();
    }

    @Test
    public void test() throws Exception {
        // httpClient
        HttpClient httpClient = new JWClient().getHttpClient();

        HttpResponse response = httpClient.execute(new HttpGet("https://auth.wtu.edu.cn/authserver/login?service=http%3A%2F%2Fjwglxt.wtu.edu.cn%2Fsso%2Fjziotlogin"));

        //获取响应内容
        HttpEntity entity = response.getEntity();
        //将内容编码为字符串
        String vlue = EntityUtils.toString(entity, "UTF-8");

        Document document = Jsoup.parseBodyFragment(vlue);//转换为文档树
        Element body = document.body();//获取主体
        //找到lt
        String lt = body.select("[name=lt]").attr("value");
        //得到公钥
        String pwdkey = body.select("[id=pwdDefaultEncryptSalt]").attr("value");
        //得到Cookies
        Header[] headers = response.getHeaders("Set-Cookie");
        String ROUTE = headers[0].getValue().split(";")[0].split("=")[1];
        System.out.println();
        String JSESSION = headers[1].getValue().split(";")[0].split("=")[1];

        System.out.println("Cookies GET1 Jsession:" + JSESSION);
        System.out.println("JWXTEFF lt: " + lt);
        System.out.println("JWXTEFF pwdkey: " + pwdkey);
        System.out.println("JWXTEFF Cookies GET1 Route:" + ROUTE);

        // 验证码
        HttpResponse image = httpClient.execute(new HttpGet("https://auth.wtu.edu.cn/authserver/captcha.html"));
        HttpEntity imageEntity = image.getEntity();
        InputStream content = imageEntity.getContent();
        FileOutputStream fos = new FileOutputStream("C:\\Users\\lpc\\Desktop\\test\\check.jpg");
        int len;
        byte[] bytes = new byte[1024];
        while ((len = content.read(bytes)) != -1) {
            fos.write(bytes, 0, len);
        }
        fos.close();
        content.close();

        // AES 加密
        HttpResponse aes = httpClient.execute(new HttpGet("https://auth.wtu.edu.cn/authserver/custom/js/encrypt.js"));
        HttpEntity aesEntity = aes.getEntity();
        String aseJs = EntityUtils.toString(aesEntity);
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        //写入函数
        engine.eval(aseJs);
        //实例化引用
        Invocable invoke = (Invocable) engine;
        //调用函数
        String password = (String) invoke.invokeFunction("encryptAES", "hll520...", pwdkey);
        System.out.println("passwordAes:\n" + password);

        Scanner scanner = new Scanner(System.in);
        String check = scanner.nextLine();
        // 登录
        String login = "https://auth.wtu.edu.cn/authserver/login?service=http%3A%2F%2Fjwglxt.wtu.edu.cn%2Fsso%2Fjziotlogin";
        HttpPost httpPost = new HttpPost(login);
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("username", "1704270128"));
        pairs.add(new BasicNameValuePair("password", password));
        pairs.add(new BasicNameValuePair("captchaResponse", check));
        pairs.add(new BasicNameValuePair("lt", lt));
        pairs.add(new BasicNameValuePair("dllt", "userNamePasswordLogin"));
        pairs.add(new BasicNameValuePair("execution", "e1s1"));
        pairs.add(new BasicNameValuePair("_eventId", "submit"));
        pairs.add(new BasicNameValuePair("rmShown", "1"));
        httpPost.setEntity(new UrlEncodedFormEntity(pairs));

        System.out.println("___________________________");
        HttpResponse loginOk = httpClient.execute(httpPost);
        HttpEntity entity1 = loginOk.getEntity();
        String value2 = EntityUtils.toString(entity1);
        System.out.println(value2);
        Document document2 = Jsoup.parseBodyFragment(value2);//转换为文档树
        Element body2 = document2.body();//获取主体
        String jwurl = body2.selectFirst("a[href]").after("href").attr("href");
        System.out.println(jwurl);

        // 登录jw
        HttpGet httpGet = new HttpGet(jwurl);
        HttpResponse jwR = httpClient.execute(httpGet);
        System.out.println(EntityUtils.toString(jwR.getEntity()));


        // 获取课表
        String kcbUrl = "http://jwglxt.wtu.edu.cn/kbcx/xskbcx_cxXsKb.html?gnmkdm=N253508&su=1704270128";
        HttpPost kcbPost = new HttpPost(kcbUrl);
        pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("xnm", "2019"));
        pairs.add(new BasicNameValuePair("xqm", "3"));
        kcbPost.setEntity(new UrlEncodedFormEntity(pairs));
        HttpResponse kcbResponse = httpClient.execute(kcbPost);
        HttpEntity kcbEntity = kcbResponse.getEntity();
        System.out.println("________________________________________");
        String kcb = EntityUtils.toString(kcbEntity);
        System.out.println(kcb);

        // 解析课程表
        List<CourseJSON> sjkList = JSONObject.parseObject(kcb).getJSONArray("kbList").toJavaList(CourseJSON.class);
        System.out.println(sjkList);
        System.out.println(sjkList.size());
        System.out.println("______________________");
        List<Course> build = Course.build(sjkList);
        System.out.println(build);
        System.out.println(build.size());


    }
}
