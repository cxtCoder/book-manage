package pers.cxt.bms.api.util;

import org.apache.poi.util.LocaleUtil;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
public class WordUtils {

    private static final String FONT = "宋体";

    private static final Integer FONT_SIZE = 8;

    /**
     * 创建页眉
     *
     * @param document 文档对象
     * @param text     页眉文本
     */
    public static void createDefaultHeader(XWPFDocument document, String text) {
        CTP ctp = CTP.Factory.newInstance();
        XWPFParagraph paragraph = new XWPFParagraph(ctp, document);
        ctp.addNewR().addNewT().setStringValue(text);
        ctp.addNewR().addNewT().setSpace(SpaceAttribute.Space.PRESERVE);
        CTSectPr sectPr = document.getDocument().getBody().isSetSectPr() ? document.getDocument().getBody().getSectPr() : document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);
        XWPFHeader header = policy.createHeader(STHdrFtr.DEFAULT, new XWPFParagraph[]{paragraph});
        header.setXWPFDocument(document);
    }

    /**
     * 创建页脚
     *
     * @param document 文档
     */
    public static void createDefaultFooter1(XWPFDocument document) {
        CTP pageNo = CTP.Factory.newInstance();
        XWPFParagraph footer = new XWPFParagraph(pageNo, document);
        CTPPr begin = pageNo.addNewPPr();
        begin.addNewPStyle().setVal("footer");
        begin.addNewJc().setVal(STJc.CENTER);
        pageNo.addNewR().addNewFldChar().setFldCharType(STFldCharType.BEGIN);
        pageNo.addNewR().addNewInstrText().setStringValue("PAGE   \\* MERGEFORMAT");
        pageNo.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
        CTR end = pageNo.addNewR();
        CTRPr endRPr = end.addNewRPr();
        endRPr.addNewNoProof();
        endRPr.addNewLang().setVal("zh-CN");
        end.addNewFldChar().setFldCharType(STFldCharType.END);
        CTSectPr sectPr = document.getDocument().getBody().isSetSectPr() ? document.getDocument().getBody().getSectPr() : document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);
        policy.createFooter(STHdrFtr.DEFAULT, new XWPFParagraph[]{footer});
    }

    /**
     * 创建页脚
     *
     * @param document 文档
     */
    public static void createDefaultFooter2(XWPFDocument document) {
        CTP pageNo = CTP.Factory.newInstance();
        XWPFParagraph paragraph = new XWPFParagraph(pageNo, document);

        XWPFRun run = paragraph.createRun();
        setXWPFRunStyle(run, FONT, FONT_SIZE);
        run.addTab();

        run = paragraph.createRun();
        run.setText("第");
        setXWPFRunStyle(run, FONT, FONT_SIZE);

        run = paragraph.createRun();
        CTFldChar fldChar = run.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));
        setXWPFRunStyle(run, FONT, FONT_SIZE);

        run = paragraph.createRun();
        CTText ctText = run.getCTR().addNewInstrText();
        ctText.setStringValue("PAGE  \\* MERGEFORMAT");
        ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
        setXWPFRunStyle(run, FONT, FONT_SIZE);

        fldChar = run.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

        run = paragraph.createRun();
        run.setText("页/共");
        setXWPFRunStyle(run, FONT, FONT_SIZE);

        run = paragraph.createRun();
        fldChar = run.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));

        run = paragraph.createRun();
        ctText = run.getCTR().addNewInstrText();
        ctText.setStringValue("NUMPAGES  \\* MERGEFORMAT ");
        ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
        setXWPFRunStyle(run, FONT, FONT_SIZE);

        fldChar = run.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

        run = paragraph.createRun();
        run.setText("页");
        setXWPFRunStyle(run, FONT, FONT_SIZE);

        CTPPr begin = pageNo.addNewPPr();
        begin.addNewPStyle().setVal("footer");
        begin.addNewJc().setVal(STJc.CENTER);

        CTSectPr sectPr = document.getDocument().getBody().isSetSectPr() ? document.getDocument().getBody().getSectPr() : document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);
        policy.createFooter(STHdrFtr.DEFAULT, new XWPFParagraph[]{paragraph});
    }

    /**
     * 设置页脚的字体样式
     *
     * @param run      段落元素
     * @param font     字体
     * @param fontSize 字体大小
     */
    private static void setXWPFRunStyle(XWPFRun run, String font, int fontSize) {
        run.setFontSize(fontSize);
        CTRPr rpr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
        CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii(font);
        fonts.setEastAsia(font);
        fonts.setHAnsi(font);
    }

    /**
     * 增加目录记录
     *
     * @param paragraph 段落
     * @param titleText 标题文字
     * @param author    作者
     * @param pageNo    页码
     */
    public static void addContentRow(XWPFParagraph paragraph, String titleText, String author, String pageNo) {
        CTP ctp = paragraph.getCTP();
        ctp.setRsidR("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
        ctp.setRsidRDefault("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
        CTPPr pPr = ctp.addNewPPr();
        CTTabs tabs = pPr.addNewTabs();//Set of Custom Tab Stops自定义制表符集合
        CTTabStop tab = tabs.addNewTab();//Custom Tab Stop自定义制表符
        tab.setVal(STTabJc.RIGHT);
        tab.setLeader(STTabTlc.DOT);
        tab.setPos(new BigInteger("8290"));//默认为8290，若调整页边距，则需要调整
        pPr.addNewRPr().addNewNoProof();//不检查语法
        CTR run = ctp.addNewR();
        run.addNewRPr().addNewNoProof();
        run.addNewT().setStringValue(titleText);//添加标题文字
        //设置标题字体
        CTRPr pRpr = run.getRPr();
        CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr.addNewRFonts();
        fonts.setAscii("Times New Roman");
        fonts.setEastAsia("楷体");
        fonts.setHAnsi("楷体");
        // 设置标题字体大小
        CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
        sz.setVal(new BigInteger("28"));
        CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();
        szCs.setVal(new BigInteger("28"));
        //添加制表符
        run = ctp.addNewR();
        run.addNewRPr().addNewNoProof();
        run.addNewTab();
        //添加页码左括号
        ctp.addNewR().addNewT().setStringValue(author);
        ctp.addNewR().addNewT().setStringValue("（");
        //STFldCharType.BEGIN标识与结尾处STFldCharType.END相对应
        run = ctp.addNewR();
        run.addNewRPr().addNewNoProof();
        run.addNewFldChar().setFldCharType(STFldCharType.BEGIN);//Field Character Type
        // pageref run
        run = ctp.addNewR();
        run.addNewRPr().addNewNoProof();
        CTText text = run.addNewInstrText();//Field Code 添加域代码文本控件
        text.setSpace(SpaceAttribute.Space.PRESERVE);
        // bookmark reference
        //源码的域名为" PAGEREF _Toc","\h"含义为在目录内建立目录项与页码的超链接
        text.setStringValue(" PAGEREF " + 1 + " \\h ");
        ctp.addNewR().addNewRPr().addNewNoProof();
        run = ctp.addNewR();
        run.addNewRPr().addNewNoProof();
        run.addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
        // page number run
        run = ctp.addNewR();
        run.addNewRPr().addNewNoProof();
        run.addNewT().setStringValue(pageNo);
        run = ctp.addNewR();
        run.addNewRPr().addNewNoProof();
        //STFldCharType.END标识与上面STFldCharType.BEGIN相对应
        run.addNewFldChar().setFldCharType(STFldCharType.END);
        //添加页码右括号
        ctp.addNewR().addNewT().setStringValue("）");
        //设置行间距
        CTSpacing pSpacing = pPr.getSpacing() != null ? pPr.getSpacing() : pPr.addNewSpacing();
        pSpacing.setLineRule(STLineSpacingRule.AUTO);//行间距类型：多倍
        pSpacing.setLine(new BigInteger("360"));//此处1.5倍行间距
    }
}
