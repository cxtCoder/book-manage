package pers.cxt.bms.client.controller;

import com.hundsun.jrescloud.rpc.annotation.CloudReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pers.cxt.bms.api.dto.ClassifyDTO;
import pers.cxt.bms.api.dto.SubClassifyDTO;
import pers.cxt.bms.api.entity.ResponseEntity;
import pers.cxt.bms.api.enums.ErrorCodes;
import pers.cxt.bms.api.service.ClassifyService;

/**
 * @Author cxt
 * @Date 2020/7/4
 */
@Api(tags = "分类管理")
@RestController
@RequestMapping(path = "/classify")
public class ClassifyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @CloudReference(springCloud = "book-manage-server")
    private ClassifyService classifyService;

    @ApiOperation(value = "通过大类别获取小类别")
    @GetMapping(value = "/getSubClassifyByClassify")
    public ResponseEntity getSubClassifyByClassify(@RequestParam(value = "classify") String classify) {
        try {
            if (classify == null) {
                return new ResponseEntity(ErrorCodes.PARAM_IS_NOT_COMPLETE);
            }
            return new ResponseEntity(true, classifyService.getSubClassifyByClassify(classify), null);
        } catch (Exception e) {
            LOGGER.error("通过大类别获取小类别失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "获取类别列表")
    @PostMapping(value = "/getClassifyList")
    public ResponseEntity getClassifyList(@RequestBody SubClassifyDTO subClassifyDTO) {
        try {
            return new ResponseEntity(true, classifyService.getClassifyList(subClassifyDTO), null);
        } catch (Exception e) {
            LOGGER.error("获取类别列表失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "获取类别总数")
    @PostMapping(value = "/getClassifyCount")
    public ResponseEntity getClassifyCount(@RequestBody SubClassifyDTO subClassifyDTO) {
        try {
            return new ResponseEntity(true, classifyService.getClassifyCount(subClassifyDTO), null);
        } catch (Exception e) {
            LOGGER.error("获取类别总数失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "通过类别ID更新类别信息")
    @PostMapping(value = "/updateClassifyById")
    public ResponseEntity updateClassifyById(@RequestBody ClassifyDTO classifyDTO) {
        try {
            return new ResponseEntity(true, classifyService.updateClassifyById(classifyDTO), null);
        } catch (Exception e) {
            LOGGER.error("通过类别ID更新类别信息失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "通过类别ID删除类别信息")
    @GetMapping(value = "/deleteClassifyById")
    public ResponseEntity deleteClassifyById(@RequestParam(value = "id") String id) {
        try {
            return new ResponseEntity(true, classifyService.deleteClassifyById(id), null);
        } catch (Exception e) {
            LOGGER.error("通过类别ID删除类别信息失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "校验类别是否使用")
    @GetMapping(value = "/checkClassify")
    public ResponseEntity checkClassify(@RequestParam(value = "id") String id) {
        try {
            return new ResponseEntity(true, classifyService.checkClassify(id), null);
        } catch (Exception e) {
            LOGGER.error("校验类别是否使用失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "校验二级类别是否存在")
    @PostMapping(value = "/checkSubClassify")
    public ResponseEntity checkSubClassify(@RequestBody ClassifyDTO classifyDTO) {
        try {
            return new ResponseEntity(true, classifyService.checkSubClassify(classifyDTO), null);
        } catch (Exception e) {
            LOGGER.error("校验二级类别是否存在失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    @ApiOperation(value = "管理类别信息")
    @PostMapping(value = "/manageSubClassify")
    public ResponseEntity manageSubClassify(@RequestBody ClassifyDTO classifyDTO) {
        try {
            return new ResponseEntity(true, classifyService.manageSubClassify(classifyDTO), null);
        } catch (Exception e) {
            LOGGER.error("管理类别信息失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }
}
