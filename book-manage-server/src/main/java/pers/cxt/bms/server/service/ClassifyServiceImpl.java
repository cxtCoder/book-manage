package pers.cxt.bms.server.service;

import com.hundsun.jrescloud.rpc.annotation.CloudComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import pers.cxt.bms.api.bo.NameIdBO;
import pers.cxt.bms.api.dto.ClassifyDTO;
import pers.cxt.bms.api.dto.SubClassifyDTO;
import pers.cxt.bms.api.service.ClassifyService;
import pers.cxt.bms.api.util.UUIDUtils;
import pers.cxt.bms.database.dao.BmBookMapper;
import pers.cxt.bms.database.dao.BmClassifyMapper;
import pers.cxt.bms.database.entity.BmBookCriteria;
import pers.cxt.bms.database.entity.BmClassify;
import pers.cxt.bms.database.entity.BmClassifyCriteria;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author cxt
 * @Date 2020/7/4
 */
@Slf4j
@CloudComponent
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    private BmClassifyMapper classifyMapper;

    @Autowired
    private BmBookMapper bookMapper;

    /**
     * 通过大类别获取小类别
     *
     * @param classify 图书类别
     * @return
     */
    @Override
    public List<NameIdBO> getSubClassifyByClassify(String classify) {
        List<NameIdBO> subClassifyList = new ArrayList<>();
        BmClassifyCriteria classifyCriteria = new BmClassifyCriteria();
        classifyCriteria.createCriteria().andClassifyEqualTo(classify);
        List<BmClassify> classifyList = classifyMapper.selectByExample(classifyCriteria);
        if (classifyList == null || classifyList.size() == 0) {
            return subClassifyList;
        }
        classifyList.forEach(item -> {
            NameIdBO nameIdBO = new NameIdBO();
            nameIdBO.setId(item.getClassifyId());
            nameIdBO.setName(item.getSubClassify());
            subClassifyList.add(nameIdBO);
        });
        return subClassifyList;
    }

    /**
     * 获取小类别
     *
     * @param classify 图书类别
     * @return
     */
    @Override
    public Map<String, String> getSubClassify(String classify) {
        Map<String, String> map = new HashMap<>();
        BmClassifyCriteria classifyCriteria = new BmClassifyCriteria();
        classifyCriteria.createCriteria().andClassifyEqualTo(classify);
        List<BmClassify> classifyList = classifyMapper.selectByExample(classifyCriteria);
        if (classifyList == null || classifyList.size() == 0) {
            return map;
        }
        classifyList.forEach(item -> {
            map.put(item.getClassifyId(), item.getSubClassify());
        });
        return map;
    }

    /**
     * 获取类别列表
     *
     * @param subClassifyDTO 类别
     * @return
     */
    @Override
    public List<ClassifyDTO> getClassifyList(SubClassifyDTO subClassifyDTO) {
        List<ClassifyDTO> classifyDTOList = new ArrayList<>();
        BmClassifyCriteria classifyCriteria = getClassifyCriteria(subClassifyDTO);
        RowBounds rowBounds = new RowBounds(subClassifyDTO.getPageSize() * (subClassifyDTO.getPageNum() - 1), subClassifyDTO.getPageSize());
        List<BmClassify> classifyList = classifyMapper.selectByExampleWithRowbounds(classifyCriteria, rowBounds);
        if (classifyList == null || classifyList.size() == 0) {
            return classifyDTOList;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        classifyList.forEach(classify -> {
            ClassifyDTO classifyDTO = new ClassifyDTO();
            classifyDTO.setId(classify.getClassifyId());
            classifyDTO.setClassify(classify.getClassify());
            classifyDTO.setSubClassify(classify.getSubClassify());
            classifyDTO.setCreateTime(sdf.format(classify.getCreateTime()));
            classifyDTO.setUpdateTime(sdf.format(classify.getUpdateTime()));
            classifyDTOList.add(classifyDTO);
        });
        return classifyDTOList;
    }

    /**
     * 获取类别总数
     *
     * @param subClassifyDTO 类别
     * @return
     */
    @Override
    public long getClassifyCount(SubClassifyDTO subClassifyDTO) {
        BmClassifyCriteria classifyCriteria = getClassifyCriteria(subClassifyDTO);
        return classifyMapper.countByExample(classifyCriteria);
    }

    /**
     * 设置条件
     *
     * @param subClassifyDTO 类别
     * @return
     */
    private BmClassifyCriteria getClassifyCriteria(SubClassifyDTO subClassifyDTO) {
        BmClassifyCriteria classifyCriteria = new BmClassifyCriteria();
        BmClassifyCriteria.Criteria criteria = classifyCriteria.createCriteria();
        if (StringUtils.isNotBlank(subClassifyDTO.getClassify())) {
            criteria.andClassifyEqualTo(subClassifyDTO.getClassify());
        }
        if (StringUtils.isNotBlank(subClassifyDTO.getSubClassify())) {
            criteria.andSubClassifyLike("%" + subClassifyDTO.getSubClassify() + "%");
        }
        return classifyCriteria;
    }

    /**
     * 通过类别ID更新类别信息
     *
     * @param classifyDTO 类别
     * @return
     */
    @Override
    public boolean updateClassifyById(ClassifyDTO classifyDTO) {
        try {
            BmClassify classify = new BmClassify();
            classify.setClassifyId(classifyDTO.getId());
            classify.setClassify(classifyDTO.getSubClassify());
            classify.setSubClassify(classifyDTO.getSubClassify());
            classify.setUpdateTime(new Date());
            classifyMapper.updateByPrimaryKey(classify);
        } catch (Exception e) {
            log.error("通过类别ID更新类别信息失败", e);
            return false;
        }
        return true;
    }

    /**
     * 通过类别ID删除类别信息
     *
     * @param id 类别ID
     * @return
     */
    @Override
    public boolean deleteClassifyById(String id) {
        try {
            classifyMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            log.error("通过类别ID删除类别信息失败", e);
            return false;
        }
        return true;
    }

    /**
     * 校验类别是否使用
     *
     * @param id 类别ID
     * @return
     */
    @Override
    public boolean checkClassify(String id) {
        BmBookCriteria bookCriteria = new BmBookCriteria();
        bookCriteria.createCriteria().andClassifyIdEqualTo(id);
        return bookMapper.countByExample(bookCriteria) == 0;
    }

    /**
     * 校验二级类别是否存在
     *
     * @param classifyDTO 类别
     * @return
     */
    @Override
    public boolean checkSubClassify(ClassifyDTO classifyDTO) {
        BmClassifyCriteria classifyCriteria = new BmClassifyCriteria();
        classifyCriteria.createCriteria()
                .andClassifyEqualTo(classifyDTO.getClassify())
                .andSubClassifyEqualTo(classifyDTO.getSubClassify());
        return classifyMapper.countByExample(classifyCriteria) == 0;
    }

    /**
     * 管理类别信息
     *
     * @param classifyDTO 类别
     * @return
     */
    @Override
    public boolean manageSubClassify(ClassifyDTO classifyDTO) {
        try {
            BmClassify classify = new BmClassify();
            classify.setClassify(classifyDTO.getClassify());
            classify.setSubClassify(classifyDTO.getSubClassify());
            classify.setUpdateTime(new Date());
            if (StringUtils.isNotBlank(classifyDTO.getId())) {
                // 编辑
                classify.setClassifyId(classifyDTO.getId());
                classifyMapper.updateByPrimaryKeySelective(classify);
            } else {
                // 新增
                classify.setClassifyId(UUIDUtils.uuid());
                classify.setCreateTime(new Date());
                classifyMapper.insert(classify);
            }
        } catch (Exception e) {
            log.error("管理类别信息失败", e);
            return false;
        }
        return true;
    }
}
