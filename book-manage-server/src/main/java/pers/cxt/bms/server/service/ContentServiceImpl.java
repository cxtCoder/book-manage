package pers.cxt.bms.server.service;

import com.alibaba.fastjson.JSONObject;
import com.hundsun.jrescloud.rpc.annotation.CloudComponent;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import pers.cxt.bms.api.bo.BookContentBO;
import pers.cxt.bms.api.dto.BookDTO;
import pers.cxt.bms.api.enums.BookEnum;
import pers.cxt.bms.api.enums.FileTypeEnum;
import pers.cxt.bms.api.service.BookService;
import pers.cxt.bms.api.service.ContentService;
import pers.cxt.bms.api.util.PdfUtils;
import pers.cxt.bms.api.util.WordUtils;
import pers.cxt.bms.database.dao.BmBookMapper;
import pers.cxt.bms.database.dao.BmClassifyMapper;
import pers.cxt.bms.database.dao.BmContentMapper;
import pers.cxt.bms.database.entity.BmBook;
import pers.cxt.bms.database.entity.BmClassify;
import pers.cxt.bms.database.entity.BmContent;
import pers.cxt.bms.database.entity.BmContentCriteria;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@Slf4j
@CloudComponent
public class ContentServiceImpl implements ContentService {

    @Autowired
    private BmContentMapper contentMapper;

    @Autowired
    private BmBookMapper bookMapper;

    @Autowired
    private BmClassifyMapper classifyMapper;

    @Autowired
    private BookService bookService;

    /**
     * 保存图书目录信息
     *
     * @param bookId            图书ID
     * @param bookContentBOList 目录信息
     */
    @Override
    public void saveBookContent(String bookId, List<BookContentBO> bookContentBOList) {
        for (BookContentBO bookContentBO : bookContentBOList) {
            insertBookContent(bookId, bookContentBO);
            getBookContent(bookId, bookContentBO);
        }
    }

    /**
     * 删除图书目录信息
     *
     * @param bookId 图书ID
     */
    @Override
    public void deleteBookContent(String bookId) {
        BmContentCriteria contentCriteria = new BmContentCriteria();
        contentCriteria.createCriteria().andBookIdEqualTo(bookId);
        contentMapper.deleteByExample(contentCriteria);
    }

    /**
     * 批量删除图书目录信息
     *
     * @param bookIds 图书ID
     */
    @Override
    public void deleteBookContent(List<String> bookIds) {
        BmContentCriteria contentCriteria = new BmContentCriteria();
        contentCriteria.createCriteria().andBookIdIn(bookIds);
        contentMapper.deleteByExample(contentCriteria);
    }

    /**
     * 递归保存数据
     *
     * @param bookId        图书ID
     * @param bookContentBO 目录信息
     */
    private void getBookContent(String bookId, BookContentBO bookContentBO) {
        if (bookContentBO.getChildren() != null) {
            for (BookContentBO bookContent : bookContentBO.getChildren()) {
                insertBookContent(bookId, bookContent);
                getBookContent(bookId, bookContent);
            }
        }
    }

    /**
     * 插入图书目录
     *
     * @param bookId      图书ID
     * @param bookContent 目录信息
     */
    private void insertBookContent(String bookId, BookContentBO bookContent) {
        BmContent content = new BmContent();
        content.setContentId(bookContent.getId());
        content.setName(bookContent.getLabel());
        content.setUpContentId(bookContent.getUpperId());
        content.setLevel(bookContent.getLevel());
        content.setAuthor(bookContent.getAuthor());
        content.setStartPage(bookContent.getStartPage());
        content.setDescription(bookContent.getDesc());
        content.setBookId(bookId);
        contentMapper.insert(content);
    }

    /**
     * 获取图书目录
     *
     * @param id 图书ID
     * @return
     */
    @Override
    public List<BookContentBO> getBookContentById(String id) {
        BmContentCriteria contentCriteria = new BmContentCriteria();
        contentCriteria.setOrderByClause("start_page");
        contentCriteria.createCriteria().andBookIdEqualTo(id);
        List<BmContent> contentList = contentMapper.selectByExample(contentCriteria);
        BookContentBO bookContentBO = new BookContentBO();
        bookContentBO.setId("-1");
        buildChildren(bookContentBO, contentList);
        return bookContentBO.getChildren();
    }

    /**
     * 递归构建目录树
     *
     * @param bookContentBO 目录树信息
     * @param contentList   目录信息
     */
    private void buildChildren(BookContentBO bookContentBO, List<BmContent> contentList) {
        if (contentList == null || contentList.size() == 0) {
            return;
        }
        final List<BookContentBO> children = contentList.stream()
                .filter(content -> Objects.equals(content.getUpContentId(), bookContentBO.getId()))
                .map(content -> {
                    BookContentBO contentBO = new BookContentBO();
                    contentBO.setId(content.getContentId());
                    contentBO.setLabel(content.getName());
                    contentBO.setUpperId(content.getUpContentId());
                    contentBO.setLevel(content.getLevel());
                    contentBO.setAuthor(content.getAuthor());
                    contentBO.setStartPage(content.getStartPage());
                    contentBO.setDesc(content.getDescription());
                    return contentBO;
                }).collect(Collectors.toList());
        if (children.isEmpty()) {
            return;
        }
        bookContentBO.setChildren(children);
        children.forEach(contentBO -> buildChildren(contentBO, contentList));
    }

    /**
     * 导出目录文件
     *
     * @param id   图书ID
     * @param type 文件类型
     * @return
     */
    @Override
    public String exportContent(String id, String type) {
        String filePath;
        try {
            BmBook book = bookMapper.selectByPrimaryKey(id);
            if (book == null) {
                log.error("id = 【{}】,图书表中该ID暂无数据", id);
                return null;
            }
            List<BookContentBO> bookContentBOList = getBookContentById(id);
            if (CollectionUtils.isEmpty(bookContentBOList)) {
                log.error("id = 【{}】,图书目录表中该ID暂无数据", id);
                return null;
            }
            if (FileTypeEnum.WORD.getId().equals(type)) {
                filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "book-content.docx";
                XWPFDocument document = new XWPFDocument();
                exportContentWord(book, bookContentBOList, document);
                WordUtils.createDefaultFooter2(document);
                FileOutputStream fos = new FileOutputStream(new File(filePath));
                document.write(fos);
                fos.close();
            } else if (FileTypeEnum.PDF.getId().equals(type)) {
                filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "book-content.pdf";
                Document document = new Document();
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
                // 设置页面监听事件，必须在open方法前
                writer.setPageEvent(new PdfUtils().new PdfPageEvent());
                document.open();
                exportContentPdf(book, bookContentBOList, document);
                document.close();
            } else {
                log.error("type = 【{}】,该类型文件暂不支持", type);
                return null;
            }
        } catch (Exception e) {
            log.error("导出目录文件失败", e);
            return null;
        }
        return filePath;
    }

    /**
     * 导出总目录文件
     *
     * @param bookDTO 图书信息
     * @return
     */
    @Override
    public String exportTotalContent(BookDTO bookDTO) {
        String filePath;
        try {
            String type = bookDTO.getType();
            List<BmBook> bookList = bookService.getBookList(bookDTO);
            if (CollectionUtils.isEmpty(bookList)) {
                log.info("查询条件 = 【{}】,暂无图书数据", JSONObject.toJSONString(bookList));
                return null;
            }

            XWPFDocument wordDocument = new XWPFDocument();
            Document pdfDocument = new Document();
            if (FileTypeEnum.WORD.getId().equals(type)) {
                filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "book-content.docx";
            } else if (FileTypeEnum.PDF.getId().equals(type)) {
                filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "book-content.pdf";
                PdfWriter writer = PdfWriter.getInstance(pdfDocument, new FileOutputStream(filePath));
                // 设置页面监听事件，必须在open方法前
                writer.setPageEvent(new PdfUtils().new PdfPageEvent());
                pdfDocument.open();
            } else {
                log.error("type = 【{}】,该类型文件暂不支持", type);
                return null;
            }

            for (BookEnum bookEnum : BookEnum.values()) {
                List<BmBook> books = bookList.stream().filter(book -> bookEnum.getName().equals(book.getClassify())).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(books)) {
                    continue;
                }
                for (BmBook book : books) {
                    List<BookContentBO> bookContentBOList = getBookContentById(book.getBookId());
                    if (CollectionUtils.isEmpty(bookContentBOList)) {
                        log.info("id = 【{}】,图书目录表中该ID暂无数据", book.getBookId());
                        continue;
                    }
                    if (FileTypeEnum.WORD.getId().equals(type)) {
                        exportContentWord(book, bookContentBOList, wordDocument);
                    } else if (FileTypeEnum.PDF.getId().equals(type)) {
                        exportContentPdf(book, bookContentBOList, pdfDocument);
                    }
                }
            }

            if (FileTypeEnum.WORD.getId().equals(type)) {
                WordUtils.createDefaultFooter2(wordDocument);
                FileOutputStream fos = new FileOutputStream(new File(filePath));
                wordDocument.write(fos);
                fos.close();
            } else if (FileTypeEnum.PDF.getId().equals(type)) {
                pdfDocument.close();
            }
        } catch (Exception e) {
            log.error("导出目录文件失败", e);
            return null;
        }
        return filePath;
    }

    /**
     * 导出目录word
     *
     * @param book              图书信息
     * @param bookContentBOList 图书目录数据
     * @param document          文档
     * @return
     */
    private void exportContentWord(BmBook book, List<BookContentBO> bookContentBOList, XWPFDocument document) {
        try {
            setInfoXWPFParagraph(book, document);
            setTitleXWPFParagraph(document);
            for (BookContentBO bookContentBO : bookContentBOList) {
                XWPFParagraph paragraph = document.createParagraph();
                WordUtils.addContentRow(paragraph, bookContentBO.getLabel(), bookContentBO.getAuthor(), bookContentBO.getStartPage().toString());
                buildWordDocument(bookContentBO, document);
            }
        } catch (Exception e) {
            log.error("导出目录文件失败", e);
            e.printStackTrace();
        }
    }

    /**
     * 生成信息段落
     *
     * @param book     图书信息
     * @param document 文档
     */
    private void setInfoXWPFParagraph(BmBook book, XWPFDocument document) {
        XWPFParagraph name = document.createParagraph();
        // 分页
        name.setPageBreak(true);
        XWPFRun infoRun = name.createRun();
        infoRun.setText("书名：");
        infoRun.setText(book.getName());
        infoRun.setFontSize(15);

        XWPFParagraph subName = document.createParagraph();
        XWPFRun subNameRun = subName.createRun();
        subNameRun.setText("副书名：");
        subNameRun.setText(book.getSubName());
        subNameRun.setFontSize(15);

        XWPFParagraph series = document.createParagraph();
        XWPFRun seriesRun = series.createRun();
        seriesRun.setText("辑：");
        seriesRun.setText(book.getSeries());
        seriesRun.setFontSize(15);

        XWPFParagraph quantity = document.createParagraph();
        XWPFRun quantityRun = quantity.createRun();
        quantityRun.setText("数量：");
        quantityRun.setText(book.getQuantity().toString() + "（本）");
        quantityRun.setFontSize(15);

        XWPFParagraph classify = document.createParagraph();
        XWPFRun classifyRun = classify.createRun();
        classifyRun.setText("分类：");
        BmClassify bmClassify = classifyMapper.selectByPrimaryKey(book.getClassifyId());
        classifyRun.setText(book.getClassify() + "/" + bmClassify.getSubClassify());
        classifyRun.setFontSize(15);

        XWPFParagraph area = document.createParagraph();
        XWPFRun areaRun = area.createRun();
        areaRun.setText("地区：");
        areaRun.setText(book.getProvince() + "/" + book.getCity() + "/" + book.getDistrict());
        areaRun.setFontSize(15);

        XWPFParagraph author = document.createParagraph();
        XWPFRun authorRun = author.createRun();
        authorRun.setText("作者：");
        authorRun.setText(book.getAuthor());
        authorRun.setFontSize(15);

        XWPFParagraph publishing = document.createParagraph();
        XWPFRun publishingRun = publishing.createRun();
        publishingRun.setText("出版社：");
        publishingRun.setText(book.getPublishing());
        publishingRun.setFontSize(15);

        XWPFParagraph publishedDate = document.createParagraph();
        XWPFRun publishedDateRun = publishedDate.createRun();
        publishedDateRun.setText("出版日期：");
        publishedDateRun.setText(new SimpleDateFormat("yyyy-MM-dd").format(book.getPublishedDate()));
        publishedDateRun.setFontSize(15);

        XWPFParagraph position = document.createParagraph();
        position.setSpacingAfter(1500);
        XWPFRun positionRun = position.createRun();
        positionRun.setText("存放位置：");
        positionRun.setText(book.getPosition());
        positionRun.setFontSize(15);
    }

    /**
     * 生成标题段落
     *
     * @param document 文档
     */
    private void setTitleXWPFParagraph(XWPFDocument document) {
        XWPFParagraph titleParagraph = document.createParagraph();
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        titleParagraph.setSpacingAfter(400);

        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText("目录");
        titleRun.setColor("000000");
        titleRun.setBold(true);
        titleRun.setFontSize(30);
    }

    /**
     * 递归生成word文档
     *
     * @param bookContentBO 图书目录
     * @param document      文档
     */
    private void buildWordDocument(BookContentBO bookContentBO, XWPFDocument document) {
        if (bookContentBO.getChildren() != null) {
            for (BookContentBO bookContent : bookContentBO.getChildren()) {
                XWPFParagraph paragraph = document.createParagraph();
                paragraph.setIndentationFirstLine(150 * bookContent.getLevel());
                WordUtils.addContentRow(paragraph, bookContent.getLabel(), bookContent.getAuthor(), bookContent.getStartPage().toString());
                buildWordDocument(bookContent, document);
            }
        }
    }

    /**
     * 导出目录PDF
     *
     * @param book              图书信息
     * @param bookContentBOList 图书目录数据
     * @param document          文档
     * @return
     */
    private void exportContentPdf(BmBook book, List<BookContentBO> bookContentBOList, Document document) throws Exception {
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font topFont = new Font(bfChinese, 25, Font.BOLD);
        setInfoParagraph(book, document, bfChinese);
        Paragraph top = new Paragraph("目录", topFont);//设置字体样式
        top.setAlignment(1);//设置文字居中 0靠左 1，居中2，靠右
        top.setLeading(20f); //行间距
        top.setSpacingBefore(5f); //设置段落上空白
        top.setSpacingAfter(25f); //设置段落下空白
        document.add(top);

        Font font = new Font(bfChinese, 13, Font.NORMAL);
        for (BookContentBO bookContentBO : bookContentBOList) {
            Paragraph paragraph = new Paragraph(bookContentBO.getLabel(), font);
            paragraph.setLeading(22f);
            paragraph.setSpacingBefore(5f);
            if (!CollectionUtils.isEmpty(bookContentBO.getChildren())) {
                paragraph.setSpacingAfter(5f);
            }
            paragraph.add(new Chunk(new DottedLineSeparator()));
            paragraph.add(bookContentBO.getAuthor());
            paragraph.add("（" + bookContentBO.getStartPage() + "）");
            document.add(paragraph);
            buildPdfDocument(bookContentBO, document, font);
        }
    }

    /**
     * 生成信息段落
     *
     * @param book      图书
     * @param document  文档
     * @param bfChinese 字体
     */
    private void setInfoParagraph(BmBook book, Document document, BaseFont bfChinese) throws Exception {
        // 分页
        document.newPage();
        Font infoFont = new Font(bfChinese, 13, Font.NORMAL);

        Paragraph name = new Paragraph("书名：", infoFont);
        name.add(book.getName());
        document.add(name);

        Paragraph subName = new Paragraph("副书名：", infoFont);
        subName.add(book.getSubName());
        document.add(subName);

        Paragraph series = new Paragraph("辑：", infoFont);
        series.add(book.getSeries());
        document.add(series);

        Paragraph quantity = new Paragraph("数量：", infoFont);
        quantity.add(book.getQuantity().toString() + "（本）");
        document.add(quantity);

        Paragraph classify = new Paragraph("分类：", infoFont);
        BmClassify bmClassify = classifyMapper.selectByPrimaryKey(book.getClassifyId());
        classify.add(book.getClassify() + "/" + bmClassify.getSubClassify());
        document.add(classify);

        Paragraph area = new Paragraph("地区：", infoFont);
        area.add(book.getProvince() + "/" + book.getCity() + "/" + book.getDistrict());
        document.add(area);

        Paragraph author = new Paragraph("作者：", infoFont);
        author.add(book.getAuthor());
        document.add(author);

        Paragraph publishing = new Paragraph("出版社：", infoFont);
        publishing.add(book.getPublishing());
        document.add(publishing);

        Paragraph publishedDate = new Paragraph("出版日期：", infoFont);
        publishedDate.add(new SimpleDateFormat("yyyy-MM-dd").format(book.getPublishedDate()));
        document.add(publishedDate);

        Paragraph position = new Paragraph("存放位置：", infoFont);
        position.setSpacingAfter(90f);
        position.add(book.getPosition());
        document.add(position);
    }

    /**
     * 递归生成pdf文档
     *
     * @param bookContentBO 图书目录
     * @param document      文档
     */
    private void buildPdfDocument(BookContentBO bookContentBO, Document document, Font font) throws Exception {
        if (bookContentBO.getChildren() != null) {
            for (BookContentBO bookContent : bookContentBO.getChildren()) {
                Paragraph paragraph = new Paragraph(bookContent.getLabel(), font);
                paragraph.setLeading(22f);
                paragraph.setFirstLineIndent(5 * bookContent.getLevel());// 首行缩进
                paragraph.add(new Chunk(new DottedLineSeparator()));
                paragraph.add(bookContent.getAuthor());
                paragraph.add("（" + bookContent.getStartPage() + "）");
                document.add(paragraph);
                buildPdfDocument(bookContent, document, font);
            }
        }
    }
}
