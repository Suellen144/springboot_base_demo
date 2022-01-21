package com.example.demo;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.annotations.PdfRubberStampAnnotation;
import com.spire.pdf.annotations.appearance.PdfAppearance;
import com.spire.pdf.graphics.PdfImage;
import com.spire.pdf.graphics.PdfTemplate;

import java.awt.geom.Rectangle2D;

/**
 * @Description: pdf盖章
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo
 * @Author: Suellen
 * @CreateDate: 2022/1/4 15:35
 */
public class ImageStampDemo {

    public static void main(String[] args) {

        //创建PdfDocument对象，加载PDF测试文档
        PdfDocument doc = new PdfDocument();
        doc.loadFromFile("E:\\文件\\文档文件\\测试文件\\订单相关文档_20220104153927.pdf");

        //获取文档第3页
        PdfPageBase page = doc.getPages().get(2);

        //加载印章图片
        PdfImage image = PdfImage.fromFile("E:\\文件\\文档文件\\印章文件\\upload\\seal.png");
        //获取印章图片的宽度和高度
        int width = image.getWidth();
        int height = image.getHeight();

        //创建PdfTemplate对象
        PdfTemplate template = new PdfTemplate(width, height);
        //将图片绘制到模板
        template.getGraphics().drawImage(image, 0, 0, width, height);

        //创建PdfRubebrStampAnnotation对象，指定大小和位置
        Rectangle2D rect = new Rectangle2D.Float((float) (page.getActualSize().getWidth() - width - 10), (float) (page.getActualSize().getHeight() - height - 60), width, height);
        PdfRubberStampAnnotation stamp = new PdfRubberStampAnnotation(rect);

        //创建PdfAppearance对象
        PdfAppearance pdfAppearance = new PdfAppearance(stamp);
        //将模板应用为PdfAppearance的一般状态
        pdfAppearance.setNormal(template);
        //将PdfAppearance 应用为图章的样式
        stamp.setAppearance(pdfAppearance);

        //添加图章到PDF
        page.getAnnotationsWidget().add(stamp);

        //保存文档
        doc.saveToFile("E:\\文件\\文档文件\\测试文件\\改章测试1.pdf", FileFormat.PDF);
    }
}
