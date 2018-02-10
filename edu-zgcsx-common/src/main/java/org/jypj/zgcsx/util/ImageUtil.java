package org.jypj.zgcsx.util;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by jian_wu on 2018/1/30.
 */
public class ImageUtil {


    /**
     * 插入文字
     *
     * @param bufImage
     * @param showContent
     * @return
     */
    public  static BufferedImage insertText(BufferedImage bufImage, String showContent,int type,int times) {
        int width = bufImage.getWidth();
        int height = bufImage.getHeight();
        BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        //设置字体
        Graphics2D gs = outImage.createGraphics();
        gs.setColor(Color.WHITE);
        gs.drawImage(bufImage, 0, 0, bufImage.getWidth(), bufImage.getHeight(), null);
        if(1 == type){
            gs.setColor(new Color(68, 68, 68));
        }else{
            gs.setColor(Color.WHITE);
        }
        Font font = new Font("黑体", Font.BOLD, 13);
        /* 消除java.awt.Font字体的锯齿 */
        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics metrics = gs.getFontMetrics(font);
        String s = showContent;
        int strLen = metrics.stringWidth(s); //得到文本的长度
        gs.setFont(font); //设置字体
        int startY;
        if(times == 0 && !(s.contains("(")||s.contains("（"))){
            startY = height - 21;
        }else{
            startY = height - 12 - 14 * times;//设置绘画文字的起始Y坐标
        }
        //如果有括号换行显示
        if(s.contains("(")||s.contains("（")){
            int kuoHaonum = s.lastIndexOf("(") == -1?s.lastIndexOf("（"):s.lastIndexOf("(");
            String s1 = s.substring(0, kuoHaonum );
            String s2 = s.substring(kuoHaonum );
            int s1Len = metrics.stringWidth(s2);
            int startX = (width - s1Len) / 2;
            gs.drawString(s2, startX, height-12);//画字符串
            gs.dispose();
            outImage.flush();
            outImage = insertText(outImage, s1,type,times+1);//递归实现
        } else {
            int s1Len = metrics.stringWidth(s);
            int startX = (width - s1Len) / 2;
            gs.drawString(s, startX, startY);//画字符串
            gs.dispose();
            outImage.flush();
        }
        return outImage;
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\jian_wu\\Desktop\\聘任部门负责人（选中）.png");
        BufferedImage image =  ImageIO.read(file);
        image = insertText(image,"聘任部门负责人",2,1);
        ImageIO.write(image,"png",new FileImageOutputStream(new File("C:\\Users\\jian_wu\\Desktop\\1.png")));
        System.out.println("图片处理完成");
    }

}
