package pers.cxt.bms.client.controller;

import com.hundsun.jrescloud.rpc.annotation.CloudReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import pers.cxt.bms.api.constants.BookConstant;
import pers.cxt.bms.api.dto.BookDTO;
import pers.cxt.bms.api.dto.BookIdDTO;
import pers.cxt.bms.api.dto.RegionDTO;
import pers.cxt.bms.api.entity.ResponseEntity;
import pers.cxt.bms.api.enums.ErrorCodes;
import pers.cxt.bms.api.service.BookService;
import pers.cxt.bms.api.util.FileDownload;
import pers.cxt.bms.api.util.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@Api(tags = "图书管理")
@RestController
@RequestMapping(path = "/book")
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @CloudReference(springCloud = "book-manage-server")
    private BookService bookService;

    @ApiOperation(value = "获取图书列表")
    @PostMapping(value = "/getBooksList")
    public ResponseEntity getBooksList(@RequestBody BookDTO bookDTO) {
        try {
            return new ResponseEntity(true, bookService.getBooksList(bookDTO), null);
        } catch (Exception e) {
            LOGGER.error("获取图书列表失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "获取图书总数")
    @PostMapping(value = "/getBooksCount")
    public ResponseEntity getBooksCount(@RequestBody BookDTO bookDTO) {
        try {
            return new ResponseEntity(true, bookService.getBooksCount(bookDTO), null);
        } catch (Exception e) {
            LOGGER.error("获取图书总数失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "保存图书信息")
    @PostMapping(value = "/saveBook")
    public ResponseEntity saveBook(@RequestBody BookDTO bookDTO) {
        try {
            boolean isSuccess = bookService.saveBook(bookDTO);
            if (isSuccess) {
                return new ResponseEntity(true, null, BookConstant.INSERT_SUCCESS);
            } else {
                return new ResponseEntity(ErrorCodes.INSERT_ERROR);
            }
        } catch (Exception e) {
            LOGGER.error("保存图书信息失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "通过图书ID获取图书信息")
    @PostMapping(value = "/getBookByBookId")
    public ResponseEntity getBookByBookId(@RequestParam(value = "id") String id) {
        try {
            if (id == null) {
                return new ResponseEntity(ErrorCodes.PARAM_IS_NOT_COMPLETE);
            }
            return new ResponseEntity(true, bookService.getBookByBookId(id), null);
        } catch (Exception e) {
            LOGGER.error("通过图书ID获取图书信息失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "通过图书ID更新图书信息")
    @PostMapping(value = "/updateByBookId")
    public ResponseEntity updateByBookId(@RequestBody BookDTO bookDTO) {
        try {
            boolean isSuccess = bookService.updateByBookId(bookDTO);
            if (isSuccess) {
                return new ResponseEntity(true, null, BookConstant.UPDATE_SUCCESS);
            } else {
                return new ResponseEntity(ErrorCodes.UPDATE_ERROR);
            }
        } catch (Exception e) {
            LOGGER.error("通过图书ID更新图书信息失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "通过图书ID删除图书信息")
    @PostMapping(value = "/deleteByBookIds")
    public ResponseEntity deleteByBookIds(@RequestBody BookIdDTO bookIdDTO) {
        try {
            boolean isSuccess = bookService.deleteByBookIds(bookIdDTO);
            if (isSuccess) {
                return new ResponseEntity(true, null, BookConstant.DELETE_SUCCESS);
            } else {
                return new ResponseEntity(ErrorCodes.DELETE_ERROR);
            }
        } catch (Exception e) {
            LOGGER.error("通过图书ID删除图书信息失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "通过类别获取图书总数")
    @GetMapping(value = "/getBooksCountByClassify")
    public ResponseEntity getBooksCountByClassify(@RequestParam(value = "classify", required = false) String classify) {
        try {
            return new ResponseEntity(true, bookService.getBooksCountByClassify(classify), null);
        } catch (Exception e) {
            LOGGER.error("通过类别获取图书总数失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "获取图片访问地址")
    @GetMapping(value = "/getImageUrl")
    public ResponseEntity getImageUrl() {
        try {
            return new ResponseEntity(true, bookService.getImageUrl(), null);
        } catch (Exception e) {
            LOGGER.error("获取图片访问地址失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "导出SQL文件")
    @PostMapping(value = "/exportSql")
    public void exportSql(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = bookService.exportSql(System.getProperty("user.dir"));
            FileDownload.download(filePath, request, response);
        } catch (Exception e) {
            LOGGER.error("导出SQL文件失败", e);
        }
    }

    @ApiOperation(value = "上传图片")
    @PostMapping(value = "/uploadImage")
    public ResponseEntity uploadImage(HttpServletRequest request) {
        try {
            String thumbPath = null;
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            multipartResolver.setDefaultEncoding("UTF-8");
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                if (iter.hasNext()) {
                    String fileName = iter.next();
                    MultipartFile file = multiRequest.getFile(fileName);
                    if (file != null) {
                        InputStream in = file.getInputStream();
                        byte[] bytes = IOUtils.input2byte(in);
                        thumbPath = bookService.uploadImage(bytes);
                    }
                }
            }
            if (thumbPath == null) {
                return new ResponseEntity(ErrorCodes.UPLOAD_ERROR);
            }
            return new ResponseEntity(true, thumbPath, BookConstant.UPLOAD_SUCCESS);
        } catch (Exception e) {
            LOGGER.error("上传图片失败", e);
            return new ResponseEntity(ErrorCodes.UPLOAD_ERROR);
        }
    }

    @ApiOperation(value = "获取汇总总数")
    @PostMapping(value = "/getAllCount")
    public ResponseEntity getAllCount() {
        try {
            return new ResponseEntity(true, bookService.getAllCount(), null);
        } catch (Exception e) {
            LOGGER.error("获取汇总总数失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "获取汇总总数")
    @PostMapping(value = "/getAllSummaryCount")
    public ResponseEntity getAllSummaryCount(@RequestBody RegionDTO regionDTO) {
        try {
            return new ResponseEntity(true, bookService.getAllSummaryCount(regionDTO), null);
        } catch (Exception e) {
            LOGGER.error("获取汇总总数失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "获取图书总数")
    @PostMapping(value = "/getAllBookCount")
    public ResponseEntity getAllBookCount(@RequestBody RegionDTO regionDTO) {
        try {
            return new ResponseEntity(true, bookService.getAllBookCount(regionDTO), null);
        } catch (Exception e) {
            LOGGER.error("获取图书总数失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "获取图书分类总数")
    @PostMapping(value = "/getAllBookDetailCount")
    public ResponseEntity getAllBookDetailCount(@RequestBody RegionDTO regionDTO) {
        try {
            return new ResponseEntity(true, bookService.getAllBookDetailCount(regionDTO), null);
        } catch (Exception e) {
            LOGGER.error("获取图书分类总数失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "获取名人书画总数")
    @PostMapping(value = "/getAllPaintingCount")
    public ResponseEntity getAllPaintingCount(@RequestBody RegionDTO regionDTO) {
        try {
            return new ResponseEntity(true, bookService.getAllPaintingCount(regionDTO), null);
        } catch (Exception e) {
            LOGGER.error("获取名人书画总数失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "获取文物古迹总数")
    @PostMapping(value = "/getAllRelicCount")
    public ResponseEntity getAllRelicCount(@RequestBody RegionDTO regionDTO) {
        try {
            return new ResponseEntity(true, bookService.getAllRelicCount(regionDTO), null);
        } catch (Exception e) {
            LOGGER.error("获取文物古迹总数失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }
}
