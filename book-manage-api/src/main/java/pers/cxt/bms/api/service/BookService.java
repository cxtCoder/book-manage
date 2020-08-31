package pers.cxt.bms.api.service;

import com.hundsun.jrescloud.rpc.annotation.CloudFunction;
import com.hundsun.jrescloud.rpc.annotation.CloudService;
import pers.cxt.bms.api.bo.BookSummaryBO;
import pers.cxt.bms.api.bo.SummaryBO;
import pers.cxt.bms.api.dto.BookDTO;
import pers.cxt.bms.api.dto.BookIdDTO;
import pers.cxt.bms.api.dto.RegionDTO;
import pers.cxt.bms.database.entity.BmBook;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@CloudService
public interface BookService {
    /**
     * 获取图书列表
     *
     * @param bookDTO 图书信息
     * @return
     */
    @CloudFunction("getBooksList")
    List<BookDTO> getBooksList(BookDTO bookDTO) throws ParseException;

    /**
     * 获取图书列表
     *
     * @param bookDTO 图书信息
     * @return
     */
    @CloudFunction("getBookList")
    List<BmBook> getBookList(BookDTO bookDTO) throws ParseException;

    /**
     * 获取图书总数
     *
     * @param bookDTO 图书信息
     * @return
     */
    @CloudFunction("getBooksCount")
    long getBooksCount(BookDTO bookDTO) throws ParseException;

    /**
     * 保存图书信息
     *
     * @param bookDTO 图书信息
     * @return
     */
    @CloudFunction("saveBook")
    Boolean saveBook(BookDTO bookDTO);

    /**
     * 通过图书ID获取图书信息
     *
     * @param id 图书ID
     * @return
     */
    @CloudFunction("getBookByBookId")
    BookDTO getBookByBookId(String id);

    /**
     * 通过图书ID更新图书信息
     *
     * @param bookDTO 图书信息
     * @return
     */
    @CloudFunction("updateByBookId")
    Boolean updateByBookId(BookDTO bookDTO);

    /**
     * 通过图书ID删除图书信息
     *
     * @param bookIdDTO 图书ID
     * @return
     */
    @CloudFunction("deleteByBookIds")
    Boolean deleteByBookIds(BookIdDTO bookIdDTO);

    /**
     * 获取图书总数
     *
     * @param classify 图书类别
     * @return
     */
    @CloudFunction("getBooksCountByClassify")
    long getBooksCountByClassify(String classify);

    /**
     * 获取图片访问地址
     */
    @CloudFunction("getImageUrl")
    String getImageUrl();

    /**
     * 导出SQL文件
     */
    @CloudFunction("exportSql")
    String exportSql(String localPath);

    /**
     * 上传图片
     *
     * @param bytes 请求
     * @return
     */
    @CloudFunction("uploadImage")
    String uploadImage(byte[] bytes) throws IOException;

    /**
     * 获取汇总总数
     */
    @CloudFunction("getAllCount")
    SummaryBO getAllCount();

    /**
     * 获取汇总总数
     */
    @CloudFunction("getAllSummaryCount")
    SummaryBO getAllSummaryCount(RegionDTO regionDTO);

    /**
     * 获取图书总数
     */
    @CloudFunction("getAllBookCount")
    BookSummaryBO getAllBookCount(RegionDTO regionDTO);

    /**
     * 获取图书分类总数
     */
    @CloudFunction("getAllBookDetailCount")
    Map<String, Long> getAllBookDetailCount(RegionDTO regionDTO);

    /**
     * 获取名人书画总数
     */
    @CloudFunction("getAllPaintingCount")
    Map<String, Long> getAllPaintingCount(RegionDTO regionDTO);

    /**
     * 获取文物古迹总数
     */
    @CloudFunction("getAllRelicCount")
    Map<String, Long> getAllRelicCount(RegionDTO regionDTO);
}
