package pers.cxt.bms.server.service;

import com.hundsun.jrescloud.rpc.annotation.CloudComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import pers.cxt.bms.api.bo.BookContentBO;
import pers.cxt.bms.api.bo.BookSummaryBO;
import pers.cxt.bms.api.bo.SummaryBO;
import pers.cxt.bms.api.dto.BookDTO;
import pers.cxt.bms.api.dto.BookIdDTO;
import pers.cxt.bms.api.dto.RegionDTO;
import pers.cxt.bms.api.enums.BookEnum;
import pers.cxt.bms.api.enums.PaintingEnum;
import pers.cxt.bms.api.enums.RelicEnum;
import pers.cxt.bms.api.service.BookService;
import pers.cxt.bms.api.service.ClassifyService;
import pers.cxt.bms.api.service.ContentService;
import pers.cxt.bms.api.util.ImageUtils;
import pers.cxt.bms.api.util.UUIDUtils;
import pers.cxt.bms.database.dao.BmBookMapper;
import pers.cxt.bms.database.dao.BmClassifyMapper;
import pers.cxt.bms.database.dao.BmUserMapper;
import pers.cxt.bms.database.entity.*;
import pers.cxt.bms.server.config.DataSourceConfig;
import pers.cxt.bms.server.config.ImgConfig;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@Slf4j
@CloudComponent
public class BookServiceImpl implements BookService {

    @Autowired
    private BmBookMapper bookMapper;

    @Autowired
    private BmClassifyMapper classifyMapper;

    @Autowired
    private BmUserMapper userMapper;

    @Autowired
    private ContentService contentService;

    @Autowired
    private ClassifyService classifyService;

    @Autowired
    private DataSourceConfig dataSourceConfig;

    @Autowired
    private ImgConfig imgConfig;

    @Value("${image.oriFileUrl}")
    private String imgUrl;

    /**
     * 获取图书列表
     *
     * @param bookDTO 图书信息
     * @return
     */
    @Override
    public List<BookDTO> getBooksList(BookDTO bookDTO) throws ParseException {
        List<BookDTO> bookDTOList = new ArrayList<>();
        BmBookCriteria bookCriteria = getBookCriteria(bookDTO);
        if (bookCriteria == null) {
            return bookDTOList;
        }
        RowBounds rowBounds = getRowBounds(bookDTO);
        List<BmBook> bookList = bookMapper.selectByExampleWithRowbounds(bookCriteria, rowBounds);
        if (bookList == null || bookList.size() == 0) {
            return bookDTOList;
        }
        for (BmBook book : bookList) {
            bookDTOList.add(transformBean(book));
        }
        return bookDTOList;
    }

    /**
     * 获取图书列表
     *
     * @param bookDTO 图书信息
     * @return
     */
    @Override
    public List<BmBook> getBookList(BookDTO bookDTO) throws ParseException {
        List<BmBook> bookList = new ArrayList<>();
        BmBookCriteria bookCriteria = getBookCriteria(bookDTO);
        if (bookCriteria == null) {
            return bookList;
        }
        return bookMapper.selectByExample(bookCriteria);
    }

    /**
     * 获取图书总数
     *
     * @param bookDTO 图书信息
     * @return
     */
    @Override
    public long getBooksCount(BookDTO bookDTO) throws ParseException {
        BmBookCriteria bookCriteria = getBookCriteria(bookDTO);
        if (bookCriteria == null) {
            return 0L;
        }
        return bookMapper.countByExample(bookCriteria);
    }

    /**
     * 保存图书信息
     *
     * @param bookDTO 图书信息
     * @return
     */
    @Override
    public Boolean saveBook(BookDTO bookDTO) {
        try {
            BmBook book = setBookInfo(bookDTO);
            if (StringUtils.isBlank(bookDTO.getBookId())) {
                book.setBookId(UUIDUtils.uuid());
                book.setCreateTime(new Date());
                book.setUpdateTime(new Date());
                bookMapper.insert(book);
            } else {
                book.setBookId(bookDTO.getBookId());
                book.setUpdateTime(new Date());
                bookMapper.updateByPrimaryKeySelective(book);
                contentService.deleteBookContent(book.getBookId());
            }
            contentService.saveBookContent(book.getBookId(), bookDTO.getContentData());
        } catch (Exception e) {
            log.error("保存图书信息失败", e);
            return false;
        }
        return true;
    }

    /**
     * 通过图书ID获取图书信息
     *
     * @param id 图书ID
     * @return
     */
    @Override
    public BookDTO getBookByBookId(String id) {
        BmBook book = bookMapper.selectByPrimaryKey(id);
        if (book == null) {
            return null;
        }
        return transformBean(book);
    }

    /**
     * 通过图书ID更新图书信息
     *
     * @param bookDTO 图书信息
     * @return
     */
    @Override
    public Boolean updateByBookId(BookDTO bookDTO) {
        try {
            BmBook book = setBookInfo(bookDTO);
            book.setBookId(bookDTO.getBookId());
            book.setUpdateTime(new Date());
            bookMapper.updateByPrimaryKeySelective(book);
        } catch (Exception e) {
            log.error("通过图书ID更新图书信息失败", e);
            return false;
        }
        return true;
    }

    /**
     * 通过图书ID删除图书信息
     *
     * @param bookIdDTO 图书ID
     * @return
     */
    @Override
    public Boolean deleteByBookIds(BookIdDTO bookIdDTO) {
        try {
            List<String> selectedIds = bookIdDTO.getSelectedIds();
            if (selectedIds == null || selectedIds.size() == 0) {
                return true;
            }
            selectedIds.forEach(id -> bookMapper.deleteByPrimaryKey(id));

            contentService.deleteBookContent(selectedIds);
        } catch (Exception e) {
            log.error("通过图书ID删除图书信息失败", e);
            return false;
        }
        return true;
    }

    /**
     * 获取图书总数
     *
     * @param classify 图书类别
     * @return
     */
    @Override
    public long getBooksCountByClassify(String classify) {
        BmBookCriteria bookCriteria = new BmBookCriteria();
        if (StringUtils.isNotBlank(classify)) {
            bookCriteria.createCriteria().andClassifyEqualTo(classify);
        } else {
            List<String> bookType = new ArrayList<>();
            for (BookEnum bookEnum : BookEnum.values()) {
                bookType.add(bookEnum.getName());
            }
            bookCriteria.createCriteria().andClassifyIn(bookType);
        }
        List<BmBook> bookList = bookMapper.selectByExample(bookCriteria);
        if (bookList == null || bookList.size() == 0) {
            return 0;
        }
        return bookList.stream().mapToInt(BmBook::getQuantity).sum();
    }

    /**
     * 获取图片访问地址
     */
    @Override
    public String getImageUrl() {
        return imgUrl;
    }

    /**
     * 导出SQL文件
     */
    @Override
    public String exportSql(String localPath) {
        StringBuffer command = new StringBuffer();
        String ip = dataSourceConfig.getUrl().split("//")[1].split(":")[0];
        String port = dataSourceConfig.getUrl().split(ip + ":")[1].split("/")[0];
        String database = dataSourceConfig.getUrl().split(port + "/")[1].split("\\?")[0];
        String filePath = localPath + System.getProperty("file.separator") + database + ".sql";

        command.append("mysqldump -u").append(dataSourceConfig.getUsername()).append(" -p").append(dataSourceConfig.getPassword())
                .append(" -h").append(ip).append(" -P").append(port)
                .append(" ").append(database).append(" -r ").append(filePath);
        log.info("sql: {}", command.toString());
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command.toString());
        } catch (IOException e) {
            log.error("导出SQL文件失败", e);
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * 上传图片
     *
     * @param bytes 请求
     * @return
     */
    @Override
    public String uploadImage(byte[] bytes) throws IOException {
        BmBook book = new BmBook();
        String id = UUIDUtils.uuid();
        book.setBookId(id);
        book.setThumbPath(id + imgConfig.getThumbPrefix() + "." + imgConfig.getThumbSuffix());
        boolean status = saveImageToServer(book, bytes);
        if (status) {
            return book.getThumbPath();
        } else {
            return null;
        }
    }

    /**
     * 获取汇总总数
     */
    @Override
    public SummaryBO getAllCount() {
        BmBookCriteria bookCriteria = new BmBookCriteria();
        List<BmBook> bookList = bookMapper.selectByExample(bookCriteria);
        return getSummaryBO(bookList);
    }

    /**
     * 获取汇总总数
     */
    @Override
    public SummaryBO getAllSummaryCount(RegionDTO regionDTO) {
        List<BmBook> bookList = getBookList(null, regionDTO);
        return getSummaryBO(bookList);
    }

    /**
     * 获取汇总数据
     *
     * @param bookList 图书数据
     * @return
     */
    private SummaryBO getSummaryBO(List<BmBook> bookList) {
        SummaryBO summaryBO = new SummaryBO();
        if (bookList == null || bookList.size() == 0) {
            summaryBO.setBookNumber(0);
            summaryBO.setPaintingNumber(0);
            summaryBO.setRelicNumber(0);
            return summaryBO;
        }
        List<String> bookType = new ArrayList<>();
        for (BookEnum bookEnum : BookEnum.values()) {
            bookType.add(bookEnum.getName());
        }
        bookList.forEach(book -> {
            if (bookType.contains(book.getClassify())) {
                summaryBO.setBookNumber(summaryBO.getBookNumber() + book.getQuantity());
            } else if (PaintingEnum.MRSH.getName().equals(book.getClassify())) {
                summaryBO.setPaintingNumber(summaryBO.getPaintingNumber() + book.getQuantity());
            } else {
                summaryBO.setRelicNumber(summaryBO.getRelicNumber() + book.getQuantity());
            }
        });
        return summaryBO;
    }

    /**
     * 获取图书总数
     */
    @Override
    public BookSummaryBO getAllBookCount(RegionDTO regionDTO) {
        List<BmBook> bookList = getBookList(null, regionDTO);
        BookSummaryBO bookSummaryBO = new BookSummaryBO();
        if (bookList == null || bookList.size() == 0) {
            bookSummaryBO.setWsNumber(0);
            bookSummaryBO.setZsNumber(0);
            bookSummaryBO.setWxNumber(0);
            bookSummaryBO.setLsNumber(0);
            bookSummaryBO.setGjNumber(0);
            bookSummaryBO.setQtNumber(0);
            return bookSummaryBO;
        }
        bookList.forEach(book -> {
            if (BookEnum.WS.getName().equals(book.getClassify())) {
                bookSummaryBO.setWsNumber(bookSummaryBO.getWsNumber() + book.getQuantity());
            } else if (BookEnum.ZS.getName().equals(book.getClassify())) {
                bookSummaryBO.setZsNumber(bookSummaryBO.getZsNumber() + book.getQuantity());
            } else if (BookEnum.WX.getName().equals(book.getClassify())) {
                bookSummaryBO.setWxNumber(bookSummaryBO.getWxNumber() + book.getQuantity());
            } else if (BookEnum.LS.getName().equals(book.getClassify())) {
                bookSummaryBO.setLsNumber(bookSummaryBO.getLsNumber() + book.getQuantity());
            } else if (BookEnum.GJ.getName().equals(book.getClassify())) {
                bookSummaryBO.setGjNumber(bookSummaryBO.getGjNumber() + book.getQuantity());
            } else if (BookEnum.QT.getName().equals(book.getClassify())) {
                bookSummaryBO.setQtNumber(bookSummaryBO.getQtNumber() + book.getQuantity());
            }
        });
        return bookSummaryBO;
    }

    /**
     * 获取图书分类总数
     */
    @Override
    public Map<String, Long> getAllBookDetailCount(RegionDTO regionDTO) {
        List<BmBook> bookList = getBookList(null, regionDTO);
        if (bookList == null || bookList.size() == 0) {
            return new HashMap<>();
        }
        return getCountMap(bookList, regionDTO.getCondition());
    }

    /**
     * 获取名人书画总数
     */
    @Override
    public Map<String, Long> getAllPaintingCount(RegionDTO regionDTO) {
        List<BmBook> bookList = getBookList(PaintingEnum.MRSH.getName(), regionDTO);
        if (bookList == null || bookList.size() == 0) {
            return new HashMap<>();
        }
        return getCountMap(bookList, PaintingEnum.MRSH.getName());
    }

    /**
     * 获取文物古迹总数
     */
    @Override
    public Map<String, Long> getAllRelicCount(RegionDTO regionDTO) {
        List<BmBook> bookList = getBookList(RelicEnum.WWGJ.getName(), regionDTO);
        if (bookList == null || bookList.size() == 0) {
            return new HashMap<>();
        }
        return getCountMap(bookList, RelicEnum.WWGJ.getName());
    }

    /**
     * 获取图书数据
     *
     * @param classify  分类
     * @param regionDTO 地区信息
     * @return
     */
    private List<BmBook> getBookList(String classify, RegionDTO regionDTO) {
        BmBookCriteria bookCriteria = new BmBookCriteria();
        BmBookCriteria.Criteria criteria = bookCriteria.createCriteria();
        if (StringUtils.isNotBlank(regionDTO.getProvince())) {
            criteria.andProvinceEqualTo(regionDTO.getProvince());
        }
        if (StringUtils.isNotBlank(regionDTO.getCity())) {
            criteria.andCityEqualTo(regionDTO.getCity());
        }
        if (StringUtils.isNotBlank(regionDTO.getCondition())) {
            criteria.andClassifyEqualTo(regionDTO.getCondition());
        }
        if (StringUtils.isNotBlank(regionDTO.getDistrict())) {
            criteria.andDistrictEqualTo(regionDTO.getDistrict());
        }
        if (StringUtils.isNotBlank(classify)) {
            criteria.andClassifyEqualTo(classify);
        }
        return bookMapper.selectByExample(bookCriteria);
    }

    /**
     * 获取总数集合
     *
     * @param bookList 图书信息
     * @param classify 分类
     * @return
     */
    private Map<String, Long> getCountMap(List<BmBook> bookList, String classify) {
        Map<String, Long> map = new HashMap<>();
        Map<String, String> classifyMap = classifyService.getSubClassify(classify);
        if (classifyMap == null || classifyMap.size() == 0) {
            return map;
        }
        bookList.forEach(book -> {
            String subClassify = classifyMap.get(book.getClassifyId());
            if (map.containsKey(subClassify)) {
                map.put(subClassify, map.get(subClassify) + book.getQuantity());
            } else {
                map.put(subClassify, Long.parseLong(book.getQuantity().toString()));
            }
        });
        return map;
    }

    /**
     * 获取查询条件
     *
     * @param bookDTO 图书信息
     * @return
     */
    private BmBookCriteria getBookCriteria(BookDTO bookDTO) throws ParseException {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BmBookCriteria bookCriteria = new BmBookCriteria();
        bookCriteria.setOrderByClause("province, city, district, name, series_order");
        BmBookCriteria.Criteria criteria = bookCriteria.createCriteria();
        if (StringUtils.isNotBlank(bookDTO.getClassify())) {
            criteria.andClassifyEqualTo(bookDTO.getClassify());
        } else {
            criteria.andClassifyIn(BookEnum.getBookEnumName());
        }
        if (StringUtils.isNotBlank(bookDTO.getClassifyId())) {
            criteria.andClassifyIdEqualTo(bookDTO.getClassifyId());
        }
        if (StringUtils.isNotBlank(bookDTO.getPublishedDate())) {
            String start = bookDTO.getPublishedDate().split("\\|")[0];
            String end = bookDTO.getPublishedDate().split("\\|")[1];
            criteria.andPublishedDateBetween(sdfDate.parse(start), sdfDate.parse(end));
        }
        if (StringUtils.isNotBlank(bookDTO.getCreateTime())) {
            String start = bookDTO.getCreateTime().split("\\|")[0];
            String end = bookDTO.getCreateTime().split("\\|")[1];
            criteria.andCreateTimeBetween(sdfTime.parse(start), sdfTime.parse(end));
        }
        if (StringUtils.isNotBlank(bookDTO.getUpdateTime())) {
            String start = bookDTO.getUpdateTime().split("\\|")[0];
            String end = bookDTO.getUpdateTime().split("\\|")[1];
            criteria.andUpdateTimeBetween(sdfTime.parse(start), sdfTime.parse(end));
        }
        int findMode = bookDTO.getFindMode();
        if (findMode == 1) {
            //精确匹配
            if (StringUtils.isNotBlank(bookDTO.getProvince())) {
                criteria.andProvinceEqualTo(bookDTO.getProvince());
            }
            if (StringUtils.isNotBlank(bookDTO.getCity())) {
                criteria.andCityEqualTo(bookDTO.getCity());
            }
            if (StringUtils.isNotBlank(bookDTO.getDistrict())) {
                criteria.andDistrictEqualTo(bookDTO.getDistrict());
            }
            if (StringUtils.isNotBlank(bookDTO.getBookName())) {
                criteria.andNameEqualTo(bookDTO.getBookName());
            }
            if (StringUtils.isNotBlank(bookDTO.getBookSubName())) {
                criteria.andSubNameEqualTo(bookDTO.getBookSubName());
            }
            if (StringUtils.isNotBlank(bookDTO.getAuthor())) {
                criteria.andAuthorEqualTo(bookDTO.getAuthor());
            }
            if (StringUtils.isNotBlank(bookDTO.getCreateUser())) {
                BmUserCriteria userCriteria = new BmUserCriteria();
                userCriteria.createCriteria().andUserNameEqualTo(bookDTO.getCreateUser());
                List<String> userIds = getUserIdList(userCriteria);
                if (userIds == null) {
                    return null;
                }
                criteria.andUserIdIn(userIds);
            }
            if (StringUtils.isNotBlank(bookDTO.getPublishing())) {
                criteria.andPublishingEqualTo(bookDTO.getPublishing());
            }
            if (StringUtils.isNotBlank(bookDTO.getPosition())) {
                criteria.andPositionEqualTo(bookDTO.getPosition());
            }
        } else {
            //模糊匹配
            if (StringUtils.isNotBlank(bookDTO.getProvince())) {
                criteria.andProvinceLike("%" + bookDTO.getProvince() + "%");
            }
            if (StringUtils.isNotBlank(bookDTO.getCity())) {
                criteria.andCityLike("%" + bookDTO.getCity() + "%");
            }
            if (StringUtils.isNotBlank(bookDTO.getDistrict())) {
                criteria.andDistrictLike("%" + bookDTO.getDistrict() + "%");
            }
            if (StringUtils.isNotBlank(bookDTO.getBookName())) {
                criteria.andNameLike("%" + bookDTO.getBookName() + "%");
            }
            if (StringUtils.isNotBlank(bookDTO.getBookSubName())) {
                criteria.andSubNameLike("%" + bookDTO.getBookSubName() + "%");
            }
            if (StringUtils.isNotBlank(bookDTO.getAuthor())) {
                criteria.andAuthorLike("%" + bookDTO.getAuthor() + "%");
            }
            if (StringUtils.isNotBlank(bookDTO.getCreateUser())) {
                BmUserCriteria userCriteria = new BmUserCriteria();
                userCriteria.createCriteria().andUserNameLike("%" + bookDTO.getCreateUser() + "%");
                List<String> userIds = getUserIdList(userCriteria);
                if (userIds == null) {
                    return null;
                }
                criteria.andUserIdIn(userIds);
            }
            if (StringUtils.isNotBlank(bookDTO.getPublishing())) {
                criteria.andPublishingLike("%" + bookDTO.getPublishing() + "%");
            }
            if (StringUtils.isNotBlank(bookDTO.getPosition())) {
                criteria.andPositionLike("%" + bookDTO.getPosition() + "%");
            }
        }
        return bookCriteria;
    }

    private List<String> getUserIdList(BmUserCriteria userCriteria) {
        List<BmUser> userList = userMapper.selectByExample(userCriteria);
        if (userList == null || userList.size() == 0) {
            return null;
        }
        return userList.stream().map(BmUser::getUserId).collect(Collectors.toList());
    }

    /**
     * 获取分页
     *
     * @param bookDTO 图书信息
     * @return
     */
    private RowBounds getRowBounds(BookDTO bookDTO) {
        RowBounds rowBounds;
        if (bookDTO.getPageNum() != -1) {
            rowBounds = new RowBounds(bookDTO.getPageSize() * (bookDTO.getPageNum() - 1), bookDTO.getPageSize());
        } else {
            rowBounds = new RowBounds();
        }
        return rowBounds;
    }

    private BookDTO transformBean(BmBook book) {
        BmClassify classify = classifyMapper.selectByPrimaryKey(book.getClassifyId());
        BmUser user = userMapper.selectByPrimaryKey(book.getUserId());
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(book.getBookId());
        bookDTO.setBookName(book.getName());
        bookDTO.setBookSubName(book.getSubName());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPublishing(book.getPublishing());
        bookDTO.setPublishedDate(sdfDate.format(book.getPublishedDate()));
        bookDTO.setSummary(book.getSummary());
        bookDTO.setRemarks(book.getRemarks());
        bookDTO.setClassifyId(book.getClassifyId());
        bookDTO.setClassify(classify.getClassify());
        bookDTO.setSubClassify(classify.getSubClassify());
        bookDTO.setThumbPath(book.getThumbPath());
        bookDTO.setSeries(book.getSeries());
        bookDTO.setSeriesOrder(book.getSeriesOrder());
        bookDTO.setQuantity(book.getQuantity());
        bookDTO.setProvince(book.getProvince());
        bookDTO.setCity(book.getCity());
        bookDTO.setDistrict(book.getDistrict());
        bookDTO.setPosition(book.getPosition());
        bookDTO.setUserId(book.getUserId());
        bookDTO.setCreateUser(user.getUserName());
        List<BookContentBO> contentData = contentService.getBookContentById(book.getBookId());
        bookDTO.setContentData(contentData == null ? new ArrayList<>() : contentData);
        bookDTO.setCreateTime(sdfTime.format(book.getCreateTime()));
        bookDTO.setUpdateTime(sdfTime.format(book.getUpdateTime()));
        return bookDTO;
    }

    /**
     * 设置图书信息
     *
     * @param bookDTO 图书信息
     */
    private BmBook setBookInfo(BookDTO bookDTO) throws ParseException {
        BmBook book = new BmBook();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        book.setName(bookDTO.getBookName());
        book.setSubName(bookDTO.getBookSubName());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublishing(bookDTO.getPublishing());
        book.setPublishedDate(sdfDate.parse(bookDTO.getPublishedDate()));
        book.setSummary(bookDTO.getSummary());
        book.setRemarks(bookDTO.getRemarks());
        book.setClassifyId(bookDTO.getClassifyId());
        BmClassify classify = classifyMapper.selectByPrimaryKey(bookDTO.getClassifyId());
        book.setClassify(classify.getClassify());
        book.setSeries(bookDTO.getSeries());
        book.setSeriesOrder(bookDTO.getSeriesOrder());
        book.setThumbPath(bookDTO.getThumbPath());
        book.setQuantity(bookDTO.getQuantity());
        book.setProvince(bookDTO.getProvince());
        book.setCity(bookDTO.getCity());
        book.setDistrict(bookDTO.getDistrict());
        book.setPosition(bookDTO.getPosition());
        book.setUserId(bookDTO.getUserId());
        return book;
    }

    /**
     * 保存图片到服务器
     *
     * @param book       图片信息
     * @param imgContent 图片内容
     * @throws IOException
     */
    private boolean saveImageToServer(BmBook book, byte[] imgContent) throws IOException {
        String fileName = book.getBookId() + "." + imgConfig.getThumbSuffix();
        String thumbFileName = book.getBookId() + imgConfig.getThumbPrefix() + "." + imgConfig.getThumbSuffix();
        BufferedImage bufferedImage = ImageUtils.resizeImage(imgContent, imgConfig.getThumbImageHeight());
        byte[] thumbImageBuffer = ImageUtils.getImageBufferBytes(bufferedImage);

        ImageUtils.saveImage(imgContent, fileName, imgConfig.getOriFileSavePath());
        return ImageUtils.saveImage(thumbImageBuffer, thumbFileName, imgConfig.getThumbFileSavePath());
    }
}
