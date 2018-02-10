package org.jypj.zgcsx.course.config.export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.validator.constraints.NotBlank;
import org.jypj.zgcsx.course.constant.CommonConstant;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public abstract class CourseXlsView<T> extends AbstractXlsView {
    private static final String CONTENT_TYPE_EXCEL = "application/ms-excel; charset=UTF-8";
    private List<T> list;
    private String name;
    private String titleName;
    private String sheetName;
    private String excelName;

    CourseXlsView(List<T> list) {
        this.list = list;
    }

    abstract List<String> getTitles();

    abstract List<Object> getValues(T t);

    abstract void init();

    private CellStyle topStyle;
    private CellStyle titleFirstStyle;
    private CellStyle titleStyle;
    private CellStyle firstStyle;
    private CellStyle style;

    private void initStyle(Workbook workbook) {
        //设置顶部标题样式
        topStyle = workbook.createCellStyle();
        topStyle.setAlignment(CellStyle.ALIGN_CENTER);
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
        init();
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
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(8);
        sheet.setDefaultRowHeightInPoints((float) 13.5);

        //创建标题
        int rowIndex = 0;
        CellRangeAddress cra = new CellRangeAddress(rowIndex, rowIndex, 0, titles.size()-1); // 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(cra);

        Row titleRow = sheet.createRow(rowIndex++);
        titleRow.setHeightInPoints((float) 24.75);
        titleRow.createCell(0).setCellValue(titleName);
        titleRow.getCell(0).setCellStyle(topStyle);

        Row header = sheet.createRow(rowIndex++);
        header.setHeightInPoints((float) 15);
        setValues(header, titles, true);

        for (T t : list) {
            Row row = sheet.createRow(rowIndex++);
            row.setHeightInPoints((float) 15.75);
            List<Object> values = getValues(t);
            setValues(row, values, false);
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

    public String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        if (this.titleName == null) {
            this.titleName = name;
        }
        if (this.sheetName == null) {
            this.sheetName = name;
        }
        if (this.excelName == null) {
            this.excelName = name + ".xls";
        }
        this.name = name;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }
}