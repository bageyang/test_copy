package com.zj.auction.common.util;

import com.zj.auction.common.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.aspectj.util.FileUtil;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.rmi.ServerException;
import java.util.List;

/**
 * ************************************************
 * 公用的excel导出
 *
 * @author MengDaNai
 * @version 1.0
 * @date 2019年3月8日 创建文件
 * @See ************************************************
 */
@Slf4j
public class ExcelUtil extends BaseServiceImpl {

    /**
     * ************************************************
     * Excel表格导出
     * <p>
     * 例子<BR/>
     * <p>
     * List<List<String>> excelData = new ArrayList<>();<BR/>
     * <p>
     * List<String> head = new ArrayList<>();<BR/>
     * head.add("第一列");<BR/>
     * head.add("第二列");<BR/>
     * head.add("第三列");<BR/>
     * <p>
     * List<String> data1 = new ArrayList<>();<BR/>
     * data1.add("123");<BR/>
     * data1.add("234");<BR/>
     * data1.add("345");<BR/>
     * <p>
     * List<String> data2 = new ArrayList<>();<BR/>
     * data2.add("abc");<BR/>
     * data2.add("bcd");<BR/>
     * data2.add("cde");<BR/>
     * <p>
     * excelData.add(head);<BR/>
     * excelData.add(data1);<BR/>
     * excelData.add(data2);<BR/>
     * <p>
     * String sheetName = "测试";<BR/>
     * String fileName = "ExcelTest.xls";<BR/>
     * <p>
     * ExcelUtil.exportExcel(ThreadParameterUtil.getResponse(), excelData, sheetName, fileName, 15);<BR/>
     *
     * @param resp    HttpServletResponse对象
     * @param excelData   Excel表格的数据，封装为List<List<String>>
     * @param sheetName   sheet的名字
     * @param fileName    导出Excel的文件名
     * @param columnWidth Excel表格的宽度，建议为15
     * @author MengDaNai
     * @date 2019年3月8日 创建文件
     * ************************************************
     */
    public static void exportExcel(HttpServletResponse resp, List<List<String>> excelData, String sheetName, String fileName, int columnWidth) {
        try {
            //声明一个工作簿
            try (HSSFWorkbook workbook = new HSSFWorkbook()) {
                //生成一个表格，设置表格名称
                HSSFSheet sheet = workbook.createSheet(sheetName);

                //设置表格列宽度
                sheet.setDefaultColumnWidth(columnWidth);

                //写入List<List<String>>中的数据
                int rowIndex = 0;
                for (List<String> data : excelData) {
                    //创建一个row行，然后自增1
                    HSSFRow row = sheet.createRow(rowIndex++);

                    //遍历添加本行数据
                    for (int i = 0; i < data.size(); i++) {
                        //创建一个单元格
                        HSSFCell cell = row.createCell(i);
                        //创建一个内容对象
                        HSSFRichTextString text = new HSSFRichTextString(data.get(i));
                        //将内容对象的文字内容写入到单元格中
                        cell.setCellValue(text);
                    }
                }
                //准备将Excel的输出流通过response输出到页面下载
                //八进制输出流
                resp.reset();
                resp.setDateHeader("Expires", 0L);
                resp.addHeader("Pragma", "no-cache");
                resp.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
                resp.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                resp.addHeader("Access-Control-Allow-Origin", "*");

                //设置导出Excel的名称
                resp.setHeader("Content-disposition", "attachment;filename=\"" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1) + "\"");
                //workbook将Excel写入到response的输出流中，供页面下载该Excel文件
                workbook.write(resp.getOutputStream());
            }
        } catch (IOException e) {
            log.error("创建工作簿失败", e);
        }
    }

    public static void exportExcels(List<List<String>> excelData, String sheetName, String fileName, int columnWidth) throws IOException {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com = fsv.getHomeDirectory();
        String filePath = com.getPath() + File.separator + fileName + ".xls";
        File file = null;
        file = new File(filePath);
        if (!file.exists() && !file.createNewFile()) {
            throw new ServerException("生成文件失败");
        }
        //声明一个工作簿
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            //生成一个表格，设置表格名称
            HSSFSheet sheet = workbook.createSheet(sheetName);

            //设置表格列宽度
            sheet.setDefaultColumnWidth(columnWidth);

            //写入List<List<String>>中的数据
            int rowIndex = 0;
            for (List<String> data : excelData) {
                //创建一个row行，然后自增1
                HSSFRow row = sheet.createRow(rowIndex++);

                //遍历添加本行数据
                for (int i = 0; i < data.size(); i++) {
                    //创建一个单元格
                    HSSFCell cell = row.createCell(i);
                    //创建一个内容对象
                    HSSFRichTextString text = new HSSFRichTextString(data.get(i));
                    //将内容对象的文字内容写入到单元格中
                    cell.setCellValue(text);
                }
            }
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        }
    }

    public static void downloadExcel(HttpServletResponse response, String fileName) {
        // 获取电脑桌面路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com = fsv.getHomeDirectory();
        String filePath = com.getPath() + File.separator + fileName + ".xls";

        File file = null;
        FileInputStream in = null;
        OutputStream out = null;
        try {
            file = new File(filePath);
            // 判断文件是否存在
            if (file.exists()) {
                // 这样设置会自动判断文件类型
                response.setContentType("multipart/form-data");
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
                // 设置文件大小
                response.setHeader("Content-Length", file.length() + "");
                // 文件传输
                in = new FileInputStream(file);
                out = response.getOutputStream();
                // 把本地文件下载到客户端
                FileUtil.copyStream(in, out);
            }
        } catch (Exception e) {
            log.error("创建文件失败", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    // 删除本地文件
                    if (file.exists()) {
                        Files.deleteIfExists(file.toPath());
                    }
                    out.close();
                }
            } catch (Exception e) {
                log.error("关闭流失败", e);
            }
        }
    }
}
