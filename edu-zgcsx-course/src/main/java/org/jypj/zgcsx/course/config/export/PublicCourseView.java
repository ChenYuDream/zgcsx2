package org.jypj.zgcsx.course.config.export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.course.constant.CommonConstant;
import org.jypj.zgcsx.course.vo.PublicCourseVo;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 公用课表
 */
public class PublicCourseView extends AbstractXlsView {

    private static final String CONTENT_TYPE_EXCEL = "application/ms-excel; charset=UTF-8";
    private List<PublicCourseVo> list;
    private String excelName;

    private CellStyle topStyle;
    private CellStyle titleFirstStyle;
    private CellStyle titleStyle;
    private CellStyle firstStyle;
    private CellStyle style;

    public PublicCourseView(List<PublicCourseVo> list, String excelName) {
        this.excelName = excelName + ".xls";
        this.list = list;
    }

    List<String> getTitles() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("星期一");
        list.add("星期二");
        list.add("星期三");
        list.add("星期四");
        list.add("星期五");
        return list;
    }

    List<Object> getValues(PublicCourseVo.CourseVo courseVo) {
        List<Object> list = new ArrayList<>();
        list.add(courseVo.getIndex());
        list.add(courseVo.getCourseName()[0]);
        list.add(courseVo.getCourseName()[1]);
        list.add(courseVo.getCourseName()[2]);
        list.add(courseVo.getCourseName()[3]);
        list.add(courseVo.getCourseName()[4]);
        return list;
    }

    //写入空行分割
    List<Object> getNullValues(Integer index) {
        List<Object> list = new ArrayList<>();
        list.add(index == null ? "" : index);
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        return list;
    }

    /**
     * 验证数组的值是否全部为空
     *
     * @param strings
     * @return true:至少有一个值不为空; false 全部为空
     */
    public static boolean validate(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            if (StringUtil.isNotEmpty(strings[i])) {
                return true;
            }
        }
        return false;
    }

    private void initStyle(Workbook workbook) {
        //设置顶部标题样式
        topStyle = workbook.createCellStyle();
        topStyle.setAlignment(CellStyle.ALIGN_LEFT);
        topStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);

        Font titleFont = workbook.createFont();
        titleFont.setFontName("宋体");
        titleFont.setCharSet(134);

        Font font = workbook.createFont();
        font.setFontName("Verdana");
        font.setCharSet(134);

        //创建样式
        titleFirstStyle = workbook.createCellStyle();
        titleFirstStyle.setAlignment(CellStyle.ALIGN_LEFT);
        titleFirstStyle.setFont(titleFont);
        titleFirstStyle.setBorderBottom(CellStyle.BORDER_MEDIUM); //下边框
        titleFirstStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);//左边框
        titleFirstStyle.setBorderTop(CellStyle.BORDER_MEDIUM);//上边框
        titleFirstStyle.setBorderRight(CellStyle.BORDER_MEDIUM);//右边框

        //创建样式
        titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(CellStyle.ALIGN_LEFT);
        titleStyle.setFont(titleFont);
        titleStyle.setBorderBottom(CellStyle.BORDER_MEDIUM); //下边框
        titleStyle.setBorderTop(CellStyle.BORDER_MEDIUM);//上边框
        titleStyle.setBorderRight(CellStyle.BORDER_MEDIUM);//右边框

        //创建样式
        firstStyle = workbook.createCellStyle();
        firstStyle.setAlignment(CellStyle.ALIGN_LEFT);
        firstStyle.setFont(font);
        firstStyle.setBorderBottom(CellStyle.BORDER_MEDIUM); //下边框
        firstStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);//左边框
        firstStyle.setBorderRight(CellStyle.BORDER_MEDIUM);//右边框

        //创建样式
        style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font);
        style.setBorderBottom(CellStyle.BORDER_MEDIUM); //下边框
        style.setBorderRight(CellStyle.BORDER_MEDIUM);//右边框

    }


    @Override
    protected void buildExcelDocument(Map<String, Object> map,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        //火狐文件名乱码
        String agent = request.getHeader("User-Agent");
        if (agent != null) {
            agent = agent.toLowerCase();
            String headerName = URLEncoder.encode(excelName, "utf-8");
            if (agent.contains("firefox")) {
                response.setHeader("content-disposition", String.format("attachment;filename*=utf-8'zh_cn'%s", headerName));
            } else {
                response.setHeader("content-disposition", "attachment;filename=" + headerName);
            }
        }
        response.setContentType(CONTENT_TYPE_EXCEL);
        response.setCharacterEncoding(CommonConstant.DEFAULT_ENCODING);
        //初始化样式
        initStyle(workbook);
        //标题行
        List<String> titles = getTitles();

        //创建sheet
        Sheet sheet = null;
        for (PublicCourseVo publicCourseVo : list) {
            sheet = workbook.createSheet(publicCourseVo.getSheet() == null ? "  " : publicCourseVo.getSheet());
            sheet.setDefaultColumnWidth(8);
            sheet.setDefaultRowHeightInPoints((float) 13.5);

            //创建标题
            int rowIndex = 0;
            CellRangeAddress cra = new CellRangeAddress(rowIndex, rowIndex, 0, 2);  // 起始行, 终止行, 起始列, 终止列
            sheet.addMergedRegion(cra);
            CellRangeAddress cra2 = new CellRangeAddress(rowIndex, rowIndex, 3, 5); // 起始行, 终止行, 起始列, 终止列
            sheet.addMergedRegion(cra2);

            for (PublicCourseVo.GradeVo gradeVo : publicCourseVo.getGradeVoList()) {
                for (PublicCourseVo.ClazzVo clazzVo : gradeVo.getClazzVoList()) {
                    Row titleRow = sheet.createRow(rowIndex++);
                    titleRow.setHeightInPoints((float) 25);
                    titleRow.createCell(0, 3).setCellValue("学期周次：" + clazzVo.getXnxq());
                    titleRow.createCell(3, 5).setCellValue("班级：" + clazzVo.getClazz());
                    titleRow.getCell(0).setCellStyle(topStyle);


                    Row header = sheet.createRow(rowIndex++);
                    header.setHeightInPoints((float) 15);
                    setValues(header, titles, true);

                    try {
                        if(clazzVo.getCourseVoList() != null && clazzVo.getCourseVoList().size() > 0){
                            for (PublicCourseVo.CourseVo t : clazzVo.getCourseVoList()) {
                                Row row = sheet.createRow(rowIndex++);
                                row.setHeightInPoints((float) 15.75);
                                List<Object> values = getValues(t);
                                setValues(row, values, false);
                            }
                        }else{
                            for (int i = 0; i < 6; i++) {
                                Row row = sheet.createRow(rowIndex++);
                                row.setHeightInPoints((float) 15.75);
                                List<Object> values = getNullValues(i+1);
                                setValues(row, values, false);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void setValues(Row row, List<?> values, boolean isTitle) {
        for (int i = 0; i < values.size(); i++) {
            Object value = values.get(i);
            Cell cell = row.createCell(i);
            if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else {
                cell.setCellValue((String) value);
            }
            if (i == 0) {
                cell.setCellStyle(isTitle ? titleFirstStyle : firstStyle);
            } else {
                cell.setCellStyle(isTitle ? titleStyle : style);
            }
        }
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }
}