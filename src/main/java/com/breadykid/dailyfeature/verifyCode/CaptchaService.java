package com.breadykid.dailyfeature.verifyCode;

import com.breadykid.dailyfeature.util.RedisService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @description: 图验
 * 1.用BufferedImage用于在存缓存中存储生成的验证码图片
 * 2.使用Graphics来进行验证码图片的绘制，验证码真值保存在缓存用于前端验证
 * 3.通过ImageIO将生成的图片进行输出
 * @author: Joyce Liu
 */
@Service
public class CaptchaService {
    private static final Logger log = LoggerFactory.getLogger(CaptchaService.class);
    //字体
    public static final String GEORGIA = "Georgia";
    //随机值
    public static final String RANDOM_CHAR_NUM = "0123456789";
    public static final String RANDOM_CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    public static final String RANDOM_CHAR_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String RANDOM_CHAR_NUM_LETTER = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //图片类型
    private static final String JPG = "jpg";
    private static final String BASE64_HEADER_TEMPLATE = "data:image/jpeg;base64,%s";

    //cache key
    private static final String CACHE_KEY_IMAGE_FORMAT = "verify_code:image:%s";
    private static final String CACHE_KEY_MAPPING_FORMAT = "verify_code:mapping:%s_%s";

    @Autowired
    private RedisService redisService;

    /**
     * 生成图片流并转base64
     *
     * @return base64字符串
     */
    public String imageToBase64(String randomId) {
        //生成随机字符
        String randomValue = randomStr(6, RANDOM_CHAR_LOWERCASE);

        //读缓存
        String base64Str = getImageCache(randomValue);
        if (Optional.ofNullable(base64Str).isPresent()) {
            return base64Str;
        }

        //无缓存生成新图
        String imageBase64Str = null;
        ByteArrayOutputStream bos = null;
        try {
            //画图
            bos = drawImage(randomValue, GEORGIA, 5, 100, 25);
            //转base64字符串
            imageBase64Str = String.format(BASE64_HEADER_TEMPLATE, Base64.encodeBase64String(bos.toByteArray()));
            saveVerifyMapping(randomValue, randomId);
        } catch (Exception e) {
            log.error("io exception{}", e);
        } finally {
            IOUtils.closeQuietly(bos);
        }

        //存缓存
        saveImageCache(randomValue, imageBase64Str);

        return imageBase64Str;
    }

    /**
     * 返回图片
     *
     * @param response
     */
    public void image(String randomId, HttpServletResponse response) {
        ServletOutputStream osResp = null;
        ByteArrayOutputStream os = null;
        //生成随机字符
        String randomValue = randomStr(6, RANDOM_CHAR_LOWERCASE);

        //读缓存
        String base64Str = getImageCache(randomValue);
        try {
            if (Optional.ofNullable(base64Str).isPresent()) {
                //base64转输出流
                os = new ByteArrayOutputStream();
                os.write(Base64.decodeBase64(base64Str));
                //输出流写入响应体
                osResp = response.getOutputStream();
                os.writeTo(osResp);
                IOUtils.closeQuietly(os);
                IOUtils.closeQuietly(osResp);
                return;
            }
        } catch (Exception e) {
            log.error("io exception{}", e);
        }

        //生成新图
        try {
            os = drawImage(randomValue, GEORGIA, 3, 100, 25);
            osResp = response.getOutputStream();
            os.writeTo(osResp);

            //转base64字符串
            String imageBase64Str = String.format(BASE64_HEADER_TEMPLATE, Base64.encodeBase64String(os.toByteArray()));
            //存图片缓存
            saveImageCache(randomValue, imageBase64Str);
            saveVerifyMapping(randomValue, randomId);
        } catch (IOException e) {
            log.error("io exception{}", e);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(osResp);
        }
    }

    /**
     * 请求与图验匹配缓存
     */
    private void saveVerifyMapping(String imageValue, String reqId) {
        redisService.set(String.format(CACHE_KEY_MAPPING_FORMAT, reqId, imageValue), imageValue, 100, TimeUnit.SECONDS);
    }

    /**
     * 读取请求与图验匹配缓存
     *
     * @param reqId
     * @return
     */
    public boolean existVerifyMapping(String imageValue, String reqId) {
        boolean exists = redisService.exists(String.format(CACHE_KEY_MAPPING_FORMAT, reqId, imageValue));
        //验证成功，删除本次请求匹配缓存
        if (exists) {
            redisService.delete(String.format(CACHE_KEY_MAPPING_FORMAT, reqId, imageValue));
        }
        return exists;
    }

    /**
     * 图片缓存
     */
    private void saveImageCache(String imageValue, String base64Str) {
        redisService.setNX(String.format(CACHE_KEY_IMAGE_FORMAT, imageValue), base64Str, 100, TimeUnit.DAYS);
    }

    /**
     * 读取图片缓存
     *
     * @param imageValue
     * @return
     */
    private String getImageCache(String imageValue) {
        return redisService.get(String.format(CACHE_KEY_IMAGE_FORMAT, imageValue));
    }

    private static final Random random = new Random();

    /**
     * 随机真值
     *
     * @param total
     * @param value
     * @return
     */
    private String randomStr(int total, String value) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < total; i++) {
            String str = String.valueOf(randomChar(value));
            stringBuilder.append(str);
        }
        log.info("验证码={}", stringBuilder);
        return stringBuilder.toString();
    }

    /**
     * 随机字符
     *
     * @param randomChar
     * @return 字符
     */
    private char randomChar(String randomChar) {
        String str = Optional.ofNullable(randomChar).isEmpty() ? RANDOM_CHAR_NUM : randomChar;
        return str.charAt(random.nextInt(str.length()));
    }

    /**
     * 生成图片
     *
     * @param randomValue 随机字符值
     * @param fontName    字体
     * @param line        干扰线条数
     * @param width       图宽
     * @param height      图长
     * @return 验证码图片流
     */
    private ByteArrayOutputStream drawImage(String randomValue, String fontName, int line, int width, int height) {
        //创建图片缓冲区
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();

        //设置背景颜色
        g.setBackground(getColor(false));
        //设置背景尺寸
        g.clearRect(0, 0, width, height);

        //画入字符
        char[] charArray = new char[randomValue.length()];
        randomValue.getChars(0, randomValue.length(), charArray, 0);
        for (int i = 0; i < charArray.length; i++) {
            float x = i * 1.0F * width / randomValue.length();
            g.setFont(randomFont(randomValue.length(), fontName, height));
            g.setColor(getColor(true));
            g.drawString(String.valueOf(charArray[i]), x, height - 5);
        }

        //定义干扰线
        Graphics2D graphics = (Graphics2D) bi.getGraphics();
        //随机干扰线的数量
        int max = line + 1;
        int min = line - 1;
        int randomLine = random.nextInt(max) % (max - min + 1) + min;
        for (int i = 0; i < randomLine; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            graphics.setColor(getColor(true));
            graphics.drawLine(x1, y1, x2, y2);
        }
        //释放图形上下文
        g.dispose();
        //图片转流
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, JPG, result);
        } catch (IOException e) {
            log.error("图片写流异常{}", e);
        }
        return result;
    }

    /**
     * 随机字体
     *
     * @param total    字符数
     * @param fontName 字体名
     * @param height   图片高度
     * @return
     */
    private Font randomFont(int total, String fontName, int height) {
        //随机字符字体
        int style = random.nextInt(total);
        //随机字符大小
        int size = random.nextInt(height - 5) % 6 + height;
        return new Font(fontName, style, size);
    }

    /**
     * 获取颜色
     *
     * @param isRandom 是否随机
     * @return 颜色
     */
    private Color getColor(boolean isRandom) {
        final int base = 255;
        if (!isRandom) {
            return new Color(base, base, base);
        }
        int r = random.nextInt(base);
        int g = random.nextInt(base);
        int b = random.nextInt(base);
        return new Color(r, g, b);
    }

}
