package pers.cxt.bms.client.controller;

import com.hundsun.jrescloud.rpc.annotation.CloudReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pers.cxt.bms.api.dto.BookDTO;
import pers.cxt.bms.api.entity.ResponseEntity;
import pers.cxt.bms.api.enums.ErrorCodes;
import pers.cxt.bms.api.service.ContentService;
import pers.cxt.bms.api.util.FileDownload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@Api(tags = "目录管理")
@RestController
@RequestMapping(path = "/content")
public class ContentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @CloudReference(springCloud = "book-manage-server")
    private ContentService contentService;

    /**
     * 获取图书目录
     *
     * @param id 图书ID
     * @return
     */
    @ApiOperation(value = "获取图书目录")
    @GetMapping(value = "/getBookContentById")
    public ResponseEntity getBookContentById(@RequestParam("id") String id) {
        try {
            return new ResponseEntity(true, contentService.getBookContentById(id), null);
        } catch (Exception e) {
            LOGGER.error("获取图书目录失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    /**
     * 导出目录文件
     *
     * @param id   图书ID
     * @param type 文件类型
     * @return
     */
    @ApiOperation(value = "导出目录文件")
    @GetMapping(value = "/exportContent")
    public void exportContent(HttpServletRequest request, HttpServletResponse response, String id, String type) {
        try {
            String filePath = contentService.exportContent(id, type);
            if (filePath != null) {
                FileDownload.download(filePath, request, response);
            }
        } catch (Exception e) {
            LOGGER.error("导出目录文件失败", e);
        }
    }

    /**
     * 导出总目录文件
     *
     * @param bookDTO 图书信息
     * @return
     */
    @ApiOperation(value = "导出总目录文件")
    @PostMapping(value = "/exportTotalContent")
    public void exportTotalContent(HttpServletRequest request, HttpServletResponse response, @RequestBody BookDTO bookDTO) {
        try {
            String filePath = contentService.exportTotalContent(bookDTO);
            if (filePath != null) {
                FileDownload.download(filePath, request, response);
            }
        } catch (Exception e) {
            LOGGER.error("导出总目录文件失败", e);
        }
    }
}
