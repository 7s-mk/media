package cn.edu.wtu.wtr.media.util.education.login;

import cn.edu.wtu.wtr.media.util.education.model.Course;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Scanner;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-15-21:41
 * @since 2021-03-15-21:41
 */
class JWClientTest {

    public static void main(String[] args) throws Exception {
        JWClient jwClient = new JWClient();
        // 1. 初始化
        System.out.println(jwClient.open());
        // 2. 验证码
        byte[] bytes = jwClient.checkImage();
        FileOutputStream fos = new FileOutputStream("C:\\Users\\lpc\\Desktop\\test\\check.jpg");
        fos.write(bytes);
        fos.close();
        // 3. 登录
        Scanner scanner = new Scanner(System.in);
        String id = scanner.nextLine();
        String pwd = scanner.nextLine();
        String code = scanner.nextLine();
        System.out.println(jwClient.login(id, pwd, code));

        // 4. 获取课表
        String xn = scanner.nextLine();
        String xq = scanner.nextLine();
        List<Course> courses = jwClient.getCourses(xn, xq);
        System.out.println(courses);
        System.out.println(courses.size());
    }

}