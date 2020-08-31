package pers.cxt.bms.api.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.*;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
public class PdfUtils {

    // 页眉
    public String header;

    // 基础字体对象
    private BaseFont baseFont;

    // 字体对象
    private Font fontDetail;

    // 字体大小
    private int fontSize = 8;

    // 模板
    private PdfTemplate total;

    /**
     * pdf页面事件监听
     */
    public class PdfPageEvent extends PdfPageEventHelper {

        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            try {
                total = writer.getDirectContent().createTemplate(100, 100);// 共 页 的矩形的长宽高
                baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
                fontDetail = new Font(baseFont, fontSize, Font.NORMAL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            // 增加页码
            createDefaultFooter2(writer, document);
        }

        @Override
        public void onCloseDocument(PdfWriter writer, Document document) {
            // 增加总页码
            setDefaultTotalPage(writer);
        }
    }

    /**
     * 增加页码
     *
     * @param writer   写入
     * @param document 文档
     */
    private void createDefaultFooter1(PdfWriter writer, Document document) {
        try {
            // PDF文档内容
            PdfContentByte pdfContent = writer.getDirectContent();

            pdfContent.saveState();
            pdfContent.beginText();
            pdfContent.setFontAndSize(baseFont, fontSize);

            // 页脚的页码 展示
            String footerNum = String.format("第%d页", writer.getPageNumber());
            Phrase phrase = new Phrase(footerNum, fontDetail);

            // 页码的 横轴 坐标 居中
            float x = (document.left() + document.right()) / 2;
            // 页码的 纵轴 坐标
            float y = document.bottom(-10);
            // 添加文本内容，进行展示页码
            ColumnText.showTextAligned(pdfContent, Element.ALIGN_CENTER, phrase, x, y, 0);

            pdfContent.endText();
            pdfContent.restoreState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加页码
     *
     * @param writer   写入
     * @param document 文档
     */
    private void createDefaultFooter2(PdfWriter writer, Document document) {
        try {
            // 1.写入页眉
//            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(header, fontDetail), document.left(), document.top() + 20, 0);

            // 2.写入前半部分的 第 X页/共
            String foot = "第" + writer.getPageNumber() + "页/共";
            Phrase footer = new Phrase(foot, fontDetail);

            // 3.计算前半部分的foot1的长度，后面好定位最后一部分的'Y页'这俩字的x轴坐标，字体长度也要计算进去 = len
            float len = baseFont.getWidthPoint(foot, fontSize);

            // 4.拿到当前的PdfContentByte
            PdfContentByte pdfContent = writer.getDirectContent();

            // 5.写入页脚1，x轴就是(右margin+左margin + right() -left()- len)/2.0F 再给偏移20F适合人类视觉感受，否则肉眼看上去就太偏左了 ,y轴就是底边界-20,否则就贴边重叠到数据体里了就不是页脚了；注意Y轴是从下往上累加的，最上方的Top值是大于Bottom好几百开外的。
            ColumnText.showTextAligned(pdfContent, Element.ALIGN_CENTER, footer, (document.rightMargin() + document.right() + document.leftMargin() - document.left() - len) / 2.0F + 10F, document.bottom() - 19, 0);

            // 6.写入页脚2的模板（就是页脚的Y页这俩字）添加到文档中，计算模板的和Y轴,X=(右边界-左边界 - 前半部分的len值)/2.0F + len ， y 轴和之前的保持一致，底边界-20
            pdfContent.addTemplate(total, (document.rightMargin() + document.right() + document.leftMargin() - document.left()) / 2.0F + 10F, document.bottom() - 20); // 调节模版显示的位置
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置总页数
     *
     * @param writer 写入
     */
    private void setDefaultTotalPage(PdfWriter writer) {
        // 关闭文档的时候，将模板替换成实际的 Y 值
//        total.beginText();
//        total.setFontAndSize(baseFont, fontSize);
//        total.showText(writer.getPageNumber() + "页");
//        total.endText();
//        total.closePath();
        ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.format("%d页", writer.getPageNumber()), fontDetail), 0, 1, 0);
    }
}
