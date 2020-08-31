/**
 * @Title: ImageUtils.java
 * @Package com.hundsun.fund.utils
 * @Description: TODO
 * @author yingxiong
 * @date 2013-7-17 下午01:29:23
 * @version V1.0
 */
package pers.cxt.bms.api.util;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.List;


/**
 * @Author cxt
 * @Date 2020/6/25
 */
@SuppressWarnings("restriction")
@Slf4j
public class ImageUtils {

    public static final int J2D_ROTATE_90 = 90;
    public static final int J2D_ROTATE_180 = 180;
    public static final int J2D_ROTATE_270 = 270;
    private static final int BUFFER = 2048;

    /**
     * 图片横向合并
     *
     * @param filePathList 待合并图片路径列表
     * @param targetPath   目标文件路径
     * @return void
     * @Title: mergeX
     * @Description: 横向合并
     */
    public static void mergeX(List<String> filePathList, String targetPath) {
        int targetWidth = 0;
        int targetHeight = 0;
        int xFocus = 0;
        try {
            for (String filePath : filePathList) {
                File file = new File(filePath);
                if (!file.exists()) {
                    log.info("待合并图片不存在，跳过处理。请求路径如下：" + file.getAbsolutePath());
                    continue;
                }
                BufferedImage image = ImageIO.read(file);
                int width = image.getWidth();// 图片宽度
                int height = image.getHeight();// 图片高度
                targetWidth += width;
                targetHeight = targetHeight > height ? targetHeight : height;
            }

            BufferedImage imageResult = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) imageResult.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, targetWidth, targetHeight);//填充整个屏幕
            //g.setColor(Color.BLACK);
            g.dispose();

            for (String filePath : filePathList) {
                File file = new File(filePath);
                if (!file.exists()) {
                    continue;
                }
                BufferedImage image = ImageIO.read(file);
                int width = image.getWidth();
                int height = image.getHeight();
                int[] imageArray = new int[width * height];
                imageArray = image.getRGB(0, 0, width, height, imageArray, 0, width);
                imageResult.setRGB(xFocus, 0, width, height, imageArray, 0, width);// 设置左半部分的RGB
                xFocus += width;
            }

            File outFile = new File(targetPath);
            ImageIO.write(imageResult, "jpg", outFile);// 写图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片纵向合并
     *
     * @param filePathList 待合并文件路径列表
     * @param targetPath   目标文件路径
     * @return void
     * @Title: mergeY
     * @Description: 纵向合并
     */
    public static void mergeY(List<String> filePathList, String targetPath) {
        int targetWidth = 0;
        int targetHeight = 0;
        int yFocus = 0;
        try {
            for (String filePath : filePathList) {
                File file = new File(filePath);
                if (!file.exists()) {
                    log.info("待合并图片不存在，跳过处理。请求路径如下：" + file.getAbsolutePath());
                    continue;
                }

                BufferedImage image = ImageIO.read(file);
                int width = image.getWidth();// 图片宽度
                int height = image.getHeight();// 图片高度
                targetWidth = targetWidth > width ? targetWidth : width;
                targetHeight += height;
            }

            BufferedImage imageResult = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) imageResult.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, targetWidth, targetHeight);//填充整个屏幕
            //g.setColor(Color.BLACK);
            g.dispose();

            for (String filePath : filePathList) {
                File file = new File(filePath);
                if (!file.exists()) {
                    continue;
                }
                BufferedImage image = ImageIO.read(file);
                int width = image.getWidth();// 图片宽度
                int height = image.getHeight();// 图片高度
                int[] imageArray = new int[width * height];// 从图片中读取RGB
                imageArray = image.getRGB(0, 0, width, height, imageArray, 0, width);
                imageResult.setRGB(0, yFocus, width, height, imageArray, 0, width);
                yFocus += height;
            }

            File outFile = new File(targetPath);
            ImageIO.write(imageResult, "jpg", outFile);// 写图片

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将Image对象转成可操作的BufferedImage
     *
     * @param img
     * @return
     */
    private static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }

    /**
     * 按略A4的尺寸（略小于A4，去除页眉页脚）计算压缩比例
     *
     * @param h
     * @param w
     * @return
     */
    private static int getPercentA4(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = 970 / h * 100;
        } else {
            p2 = 640 / w * 100;
        }
        p = Math.round(p2);
        return p;
    }

    /**
     * 第一种解决方案 在不改变图片形状的同时，判断，如果h>w，则按h压缩，
     * 否则在w>h或w=h的情况下，按宽度压缩
     *
     * @param h
     * @param w
     * @return
     */

    private static int getPercent(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = (2.5f * 297) / h * 100;
        } else {
            p2 = (2.5f * 210) / w * 100;
        }
        p = Math.round(p2);
        return p;
    }

    /**
     * 第二种解决方案，统一按照宽度压缩 这样来的效果是，所有图片的宽度是相等的，
     * 给客户最好的效果建议： 210*297
     *
     * @param
     */
    private static int getPercent2(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 100;
        p = Math.round(p2);
        return p;
    }

    /**
     * 旋转图片
     *
     * @param originalFilePath 原始图片路径
     * @param targetFilePath   目标图片路径
     * @param angle            旋转角度 90,180,270,360
     * @throws IOException
     */
    public static void rotateImage(String originalFilePath, String targetFilePath, int angle) throws IOException {
        File originalFile = new File(originalFilePath);
        if (!originalFile.exists()) {
            log.warn("图片旋转处理失败（文件系统不存在）：originalFilePath=" + originalFilePath);
            return;
        }
        BufferedImage bi = ImageIO.read(originalFile);
        BufferedImage t = ImageUtils.rotateJ2D(bi, angle, null);

        ImageWriter writer = null;
        ImageTypeSpecifier type = ImageTypeSpecifier
                .createFromRenderedImage(t);
        Iterator iter = ImageIO.getImageWriters(type, "jpg");
        if (iter.hasNext()) {
            writer = (ImageWriter) iter.next();
        }

        IIOImage iioImage = new IIOImage(t, null, null);
        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_DEFAULT);

//		param.setCompressionQuality((0.9f));
        ImageOutputStream outputStream = null;
        try {
            outputStream = ImageIO.createImageOutputStream(new File(targetFilePath));
            writer.setOutput(outputStream);
            writer.write(null, iioImage, param);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bi.flush();
            t.flush();
            outputStream.close();
        }
    }

    /**
     * 旋转 - 参数指定目标图旋转角度。
     *
     * @param bufferedImage BufferedImage
     * @param radian        int 90,180,270,360
     * @param hints         RenderingHints
     * @return BufferedImage
     */
    private static BufferedImage rotateJ2D(BufferedImage bufferedImage,
                                           int radian, RenderingHints hints) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        BufferedImage dstImage = null;
        AffineTransform affineTransform = new AffineTransform();

        if (radian == 180) {
            affineTransform.translate(width, height);
            dstImage = new BufferedImage(width, height, bufferedImage.getType());
        } else if (radian == 90) {
            affineTransform.translate(height, 0);
            dstImage = new BufferedImage(height, width, bufferedImage.getType());
        } else if (radian == 270) {
            affineTransform.translate(0, width);
            dstImage = new BufferedImage(height, width, bufferedImage.getType());
        }

        affineTransform.rotate(Math.toRadians(radian));
        AffineTransformOp affineTransformOp = new AffineTransformOp(
                affineTransform, hints);

        return affineTransformOp.filter(bufferedImage, dstImage);
    }

    public static BufferedImage resizeImage(byte[] imgContent, int thumbHeight) throws IOException {
        BufferedImage oriImage = ImageIO.read(IOUtils.byte2Input(imgContent));
        double ratio = (double) thumbHeight / oriImage.getHeight();
        int thumbWidth = getResize(oriImage.getWidth(), ratio);

        Image new_img = oriImage.getScaledInstance(thumbWidth, thumbHeight, Image.SCALE_SMOOTH);

        BufferedImage img2 = ImageUtils.toBufferedImage(new_img);

        return img2;
    }

    /**
     * @param oriSize
     * @param ratio
     * @return
     */
    public static int getResize(int oriSize, double ratio) {
        return (int) (oriSize * ratio);
    }

    public static byte[] getImageBufferBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", out);
        return out.toByteArray();
    }

    public static boolean saveImage(byte[] imgContent, String imgFileName, String imgSavePath) {
        if (imgContent != null) {
            byte[] imgContentBytes = imgContent;
            OutputStream outputStream = null;
            try {
                File file = new File(imgSavePath + imgFileName);
                if (file.createNewFile()) {
                    file.setReadable(true, false);

                    outputStream = new FileOutputStream(file);
                    outputStream.write(imgContentBytes);
                    outputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error("save file to path: {} occur error {}", imgSavePath, e.getMessage());
                return false;
            } finally {
                if (null != outputStream) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}
