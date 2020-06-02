package biz.middleware.util;

import net.coobird.thumbnailator.Thumbnails;

/**
 * @author hornYun
 * @date 2020-06-01 16:04
 */
public class CompressionUtil {


    public static void main(String[] args) throws Exception {
        String input = "D:\\backup\\bad1.png";
        Thumbnails.of(input).scale(0.5F).outputQuality(1F).toFile("D:\\backup\\test");

    }
}
