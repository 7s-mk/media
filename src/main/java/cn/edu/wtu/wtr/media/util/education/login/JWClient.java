package cn.edu.wtu.wtr.media.util.education.login;

import cn.edu.wtu.wtr.media.util.education.exception.JWException;
import cn.edu.wtu.wtr.media.util.education.model.Course;
import cn.edu.wtu.wtr.media.util.education.model.CourseJSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-15-19:35
 * @since 2021-03-15-19:35
 */
@Slf4j
public class JWClient {

    /**
     * 配置
     */
    private static RequestConfig config;
    /**
     * 请求客户端
     */
    private final HttpClient httpClient;
    /**
     * 学号
     */
    private String id;
    /**
     * 验证字符串
     */
    private String lt;
    /**
     * 密钥
     */
    private String pwdKey;
    /*        private String route;
        private String session;*/
    private boolean open = false;
    private boolean login = false;


    public JWClient() {
        httpClient = getHttpClient();
    }

    /**
     * 初始化登录
     *
     * @return 是否成功
     */
    public boolean open() {
        String path = "https://auth.wtu.edu.cn/authserver/login?service=http%3A%2F%2Fjwglxt.wtu.edu.cn%2Fsso%2Fjziotlogin";
        try {
            HttpResponse response = httpClient.execute(new HttpGet(path));
            HttpEntity entity = response.getEntity();
            Element body = Jsoup.parseBodyFragment(EntityUtils.toString(entity));
            lt = body.select("[name=lt]").attr("value");
            pwdKey = body.select("[id=pwdDefaultEncryptSalt]").attr("value");
//            Header[] headers = response.getHeaders("Set-Cookie");
//            route = headers[0].getValue().split(";")[0].split("=")[1];
//            session = headers[1].getValue().split(";")[0].split("=")[1];
            open = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new JWException("初始化登录失败", path);
        }
    }

    /**
     * 获取验证码
     *
     * @return 验证码byte[]
     */
    public byte[] checkImage() {
        String path = "https://auth.wtu.edu.cn/authserver/captcha.html";
        try {
            HttpResponse response = httpClient.execute(new HttpGet(path));
            InputStream inputStream = response.getEntity().getContent();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] buffered = new byte[1024];
            while ((len = inputStream.read(buffered)) != -1)
                bos.write(buffered, 0, len);
            inputStream.close();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new JWException("解析验证码失败" + e.getMessage(), path);
        }
    }

    /**
     * 登录
     *
     * @param id   学号
     * @param pwd  密码
     * @param code 验证码
     * @return 是否登录成功
     */
    public boolean login(String id, String pwd, String code) {
        if (!open)
            throw new JWException("需要先初始化！");
        this.id = id;
        String path = getToken(pwd, code);
        try {
            // 登录JW
            HttpResponse response = httpClient.execute(new HttpGet(path));
            // 不是null登陆成功
            login = EntityUtils.toString(response.getEntity()) != null;
            return login;
        } catch (IOException e) {
            e.printStackTrace();
            throw new JWException("登录教务系统失败！" + e.getMessage(), path);
        }
    }

    /**
     * 获取课表
     *
     * @param xn 学年 2020-2021 是 2020
     * @param xq 下学期 3  上学期 12
     * @return 课表
     */
    public List<Course> getCourses(String xn, String xq) {
        String path = "http://jwglxt.wtu.edu.cn/kbcx/xskbcx_cxXsKb.html?gnmkdm=N253508&su=" + id;
        if (!login)
            throw new JWException("必须先登录");
        try {
            // 获取课表
            HttpPost coursePost = new HttpPost(path);
            List<NameValuePair> pairs = new ArrayList<>();
            pairs.add(new BasicNameValuePair("xnm", xn));
            pairs.add(new BasicNameValuePair("xqm", xq));
            coursePost.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = httpClient.execute(coursePost);
            String json = EntityUtils.toString(response.getEntity());
            if (json == null)
                return null;

            // 解析课程表
            List<CourseJSON> courseJSONSs = JSONObject.parseObject(json).getJSONArray("kbList").toJavaList(CourseJSON.class);
            // 转换成普通课表
            return Course.build(courseJSONSs);
        } catch (IOException e) {
            login = false;
            e.printStackTrace();
            throw new JWException("获取课表失败!" + e.getMessage(), path);
        }
    }

    /**
     * 获取教务系统Token
     *
     * @param pwd  密码
     * @param code 验证码
     * @return token
     */
    private String getToken(String pwd, String code) {
        String path = "https://auth.wtu.edu.cn/authserver/login?service=http%3A%2F%2Fjwglxt.wtu.edu.cn%2Fsso%2Fjziotlogin";
        try {
            HttpPost login = new HttpPost(path);
            List<NameValuePair> pairs = new ArrayList<>();
            pairs.add(new BasicNameValuePair("username", id));
            pairs.add(new BasicNameValuePair("password", ase(pwd)));
            pairs.add(new BasicNameValuePair("captchaResponse", code));
            pairs.add(new BasicNameValuePair("lt", lt));
            pairs.add(new BasicNameValuePair("dllt", "userNamePasswordLogin"));
            pairs.add(new BasicNameValuePair("execution", "e1s1"));
            pairs.add(new BasicNameValuePair("_eventId", "submit"));
            pairs.add(new BasicNameValuePair("rmShown", "1"));
            login.setEntity(new UrlEncodedFormEntity(pairs));
            // 登录
            HttpResponse response = httpClient.execute(login);
            //转换为文档树
            Document document = Jsoup.parseBodyFragment(EntityUtils.toString(response.getEntity()));
            //获取教务系统KEY
            return document.body().selectFirst("a[href]").after("href").attr("href");
        } catch (IOException e) {
            e.printStackTrace();
            throw new JWException("登录获取教务系统TOKEN失败！" + e.getMessage(), path);
        }
    }


    /**
     * AES 密码加密
     *
     * @param pwd 密码
     * @return 加密
     */
    private String ase(String pwd) {
        String path = "https://auth.wtu.edu.cn/authserver/custom/js/encrypt.js";
        try {
            HttpResponse response = httpClient.execute(new HttpGet(path));
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
            // 载入脚本
            engine.eval(EntityUtils.toString(response.getEntity()));
            //实例化引用
            Invocable invoke = (Invocable) engine;
            //调用函数  函数名  密码  密钥
            return (String) invoke.invokeFunction("encryptAES", pwd, pwdKey);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JWException("AES密码加密失败：" + e.getMessage(), path);
        }
    }


    @Deprecated
    public RequestConfig getRequestConfig() {
        if (config != null) {
            return config;
        }
        return RequestConfig.custom()
                // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(5000)
                // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)
                // socket读写超时时间(单位毫秒)
                .setSocketTimeout(5000)
                // 设置是否允许重定向(默认为true)
                .setRedirectsEnabled(true).build();
    }

    /**
     * 获取HTTP 客户端
     *
     * @return http
     */
    public HttpClient getHttpClient() {
        BasicCookieStore basicCookieStore = new BasicCookieStore();
//        basicCookieStore.addCookie(new BasicClientCookie());
        return HttpClientBuilder.create().setDefaultCookieStore(basicCookieStore).build();

    }
}
