package RegisterDemo;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.Random;

@Controller
public class UserController extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";  //长短网址转换所需字符串
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/add") // Map ONLY GET REQUESTs.
    public @ResponseBody
    String addNewUser(@RequestParam String url, User user) {
        // @ResponseBody means the returned String is a response, not a view name.
        user.setUrl("exam");
        user.setSt("exam");
        userRepository.save(user);
        if(url.contains("https://www.")||url.contains("http://www.")) {    //自动在输入网址上加上前缀
            user.setUrl(url);
            String str=Short(user);
            user.setSt(str);
            userRepository.save(user);
            log.info(user.toString() + " saved to the repo");
            return "xyallthebest.xyz/" + str;
        }
        else
        {
            url="https://www."+url;
            user.setUrl(url);
            String str=Short(user);
            user.setSt(str);
            userRepository.save(user);
            log.info(user.toString() + " saved to the repo");
            return "xyallthebest.xyz/" + str;
        }
    }
    /**
     * 查看长短网址，按照Spring Boot的设定，以Json的形式输送给用户端。
     *
     * @return
     */
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 域名的根目录，然后返回的“index”会映射到
     * java/resources/templates/index.html文件。
     *
     * @param name
     * @return
     */
        @GetMapping(path = "/")
    public String welcomePage(@RequestParam(name = "name", required = false, defaultValue = "World")
                                      String name) {
        return "suowo";
    }
    @GetMapping(path="/error")
    public String error()
    {
        return "error";
    }

    @GetMapping(path = "/users/{username}")    //短网址转换为长网址
    @ResponseBody
    public ModelAndView userProfile1(@PathVariable String username) throws IOException {
            log.info(username);
        User user;
        String str;
        str = "users" + "/" + username;
        System.out.println(username);
        if(userRepository.findBySt(str)!=null) {
            user = userRepository.findBySt(str).get(0);  //发生异常 找不到user index 0 out of size 0
            String url = user.getUrl();
            return new ModelAndView(new RedirectView(url));
        }
        else
        {
            return new ModelAndView((new RedirectView("xyallthebest.xyz/error")));
        }
    }

    public String Short(User user)     //得到短网址算法
    {
        int id = user.getId();
        int scale = 62;
        int length = 4;
        StringBuilder sb = new StringBuilder();
        int remainder = 0;
        while (id > scale - 1) {
            /** * 对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转（reverse）字符串 */
            remainder = Long.valueOf(id % scale).intValue();
            sb.append(chars.charAt(remainder));

            id = id / scale;
        }
        sb.append(chars.charAt(Long.valueOf(id).intValue()));
        String value = sb.reverse().toString();
        //return StringUtils.leftPad(value, length, '0');
        String str = StringUtils.leftPad(value, length, '0');
        String st=getRandomString(length);
        str = "users" + "/" + st+ str;
        return str;
    }
    //length用户要求产生字符串的长度
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}