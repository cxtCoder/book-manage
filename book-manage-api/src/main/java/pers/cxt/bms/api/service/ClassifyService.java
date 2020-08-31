package pers.cxt.bms.api.service;

import com.hundsun.jrescloud.rpc.annotation.CloudFunction;
import com.hundsun.jrescloud.rpc.annotation.CloudService;
import pers.cxt.bms.api.bo.NameIdBO;
import pers.cxt.bms.api.dto.ClassifyDTO;
import pers.cxt.bms.api.dto.SubClassifyDTO;

import java.util.List;
import java.util.Map;

/**
 * @Author cxt
 * @Date 2020/7/4
 */
@CloudService
public interface ClassifyService {
    /**
     * 通过大类别获取小类别
     *
     * @param classify 图书类别
     * @return
     */
    @CloudFunction("getSubClassifyByClassify")
    List<NameIdBO> getSubClassifyByClassify(String classify);

    /**
     * 获取小类别
     *
     * @param classify 图书类别
     * @return
     */
    @CloudFunction("getSubClassify")
    Map<String, String> getSubClassify(String classify);

    /**
     * 获取类别列表
     *
     * @param subClassifyDTO 类别
     * @return
     */
    @CloudFunction("getClassifyList")
    List<ClassifyDTO> getClassifyList(SubClassifyDTO subClassifyDTO);

    /**
     * 获取类别总数
     *
     * @param subClassifyDTO 类别
     * @return
     */
    @CloudFunction("getClassifyCount")
    long getClassifyCount(SubClassifyDTO subClassifyDTO);

    /**
     * 通过类别ID更新类别信息
     *
     * @param classifyDTO 类别
     * @return
     */
    @CloudFunction("updateClassifyById")
    boolean updateClassifyById(ClassifyDTO classifyDTO);

    /**
     * 通过类别ID删除类别信息
     *
     * @param id 类别ID
     * @return
     */
    @CloudFunction("deleteClassifyById")
    boolean deleteClassifyById(String id);

    /**
     * 校验类别是否使用
     *
     * @param id 类别ID
     * @return
     */
    @CloudFunction("checkClassify")
    boolean checkClassify(String id);

    /**
     * 校验二级类别是否存在
     *
     * @param classifyDTO 类别
     * @return
     */
    @CloudFunction("checkSubClassify")
    boolean checkSubClassify(ClassifyDTO classifyDTO);

    /**
     * 管理类别信息
     *
     * @param classifyDTO 类别
     * @return
     */
    @CloudFunction("manageSubClassify")
    boolean manageSubClassify(ClassifyDTO classifyDTO);
}
