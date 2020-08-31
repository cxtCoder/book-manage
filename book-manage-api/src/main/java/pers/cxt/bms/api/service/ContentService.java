package pers.cxt.bms.api.service;

import com.hundsun.jrescloud.rpc.annotation.CloudFunction;
import com.hundsun.jrescloud.rpc.annotation.CloudService;
import pers.cxt.bms.api.bo.BookContentBO;
import pers.cxt.bms.api.dto.BookDTO;

import java.util.List;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@CloudService
public interface ContentService {
    /**
     * 保存图书目录信息
     *
     * @param bookId            图书ID
     * @param bookContentBOList 目录信息
     */
    void saveBookContent(String bookId, List<BookContentBO> bookContentBOList);

    /**
     * 删除图书目录信息
     *
     * @param bookId 图书ID
     */
    void deleteBookContent(String bookId);

    /**
     * 批量删除图书目录信息
     *
     * @param bookIds 图书ID
     */
    void deleteBookContent(List<String> bookIds);

    /**
     * 获取图书目录
     *
     * @param id 图书ID
     * @return
     */
    @CloudFunction("getBookContentById")
    List<BookContentBO> getBookContentById(String id);

    /**
     * 导出目录文件
     *
     * @param id   图书ID
     * @param type 文件类型
     * @return
     */
    @CloudFunction("exportContent")
    String exportContent(String id, String type);

    /**
     * 导出总目录文件
     *
     * @param bookDTO 图书信息
     * @return
     */
    @CloudFunction("exportTotalContent")
    String exportTotalContent(BookDTO bookDTO);
}
