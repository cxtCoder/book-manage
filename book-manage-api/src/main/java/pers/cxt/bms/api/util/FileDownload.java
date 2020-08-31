package pers.cxt.bms.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
public class FileDownload {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileDownload.class);

    public static void download(String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (filePath == null) {
            LOGGER.error("文件路径为空");
            return;
        }

        File file = new File(filePath);

        if (!file.exists()) {
            LOGGER.error("文件不存在,filePath = 【{}】,", filePath);
            return;
        }

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));

        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            LOGGER.error("Download the file failed! {}", e.getMessage());
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
