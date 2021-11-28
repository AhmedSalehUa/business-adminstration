package assets.classes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.commons.lang.exception.NestableException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.util.CellUtil;

/**
 *
 * @author Guy Bashan, modified by Khosiawan
 */
public class TableToExcel {

    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private HSSFFont boldFont;
    private HSSFDataFormat format;
    private ResultSet resultSet;
    private FormatType[] formatTypes;
    private List<String> colTitles;
    private JTable tbl;
    private TableModel tm;

    /**
     * Prepare an excel writer with a ResultSet data-source.
     *
     * @param rs
     * @param formatTypes - to auto detect the formatTypes, set it to null
     * @param sheetName
     */
    public TableToExcel(ResultSet rs, FormatType[] formatTypes, String sheetName) {
        initPart();
        this.resultSet = rs;
        this.formatTypes = formatTypes;
        sheet = workbook.createSheet(sheetName);
    }

    /**
     * Prepare an excel writer with a JTable data-source.
     *
     * @param tbl
     * @param formatTypes - to auto detect the formatTypes, set it to null
     * @param sheetName
     */
    public TableToExcel(JTable tbl, FormatType[] formatTypes, String sheetName) {
        initPart();
        this.tbl = tbl;
        this.formatTypes = formatTypes;
        sheet = workbook.createSheet(sheetName);
    }

    /**
     * Prepare an excel writer with a TableModel data-source.
     *
     * @param tm
     * @param formatTypes - to auto detect the formatTypes, set it to null
     * @param sheetName
     */
    public TableToExcel(TableModel tm, FormatType[] formatTypes, String sheetName) {
        initPart();
        this.tm = tm;
        this.formatTypes = formatTypes;
        sheet = workbook.createSheet(sheetName);
    }

    private void initPart() {
        workbook = new HSSFWorkbook();
        boldFont = workbook.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        format = workbook.createDataFormat();
    }

    /**
     * Defining custom column titles (headers), rather than using the default
     * column name from the database.
     *
     * @param headers
     */
    public void setCustomTitles(List<String> headers) {
        this.colTitles = headers;
    }

    private FormatType getFormatType(Class _class) {
        if (_class == Integer.class || _class == Long.class) {
            return FormatType.INTEGER;
        } else if (_class == Float.class || _class == Double.class) {
            return FormatType.FLOAT;
        } else if (_class == Timestamp.class || _class == java.sql.Date.class) {
            return FormatType.DATE;
        } else {
            return FormatType.TEXT;
        }
    }

    private boolean generateFromResultSet(OutputStream outputStream) throws Exception {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            if (formatTypes != null && formatTypes.length != resultSetMetaData.getColumnCount()) {
                throw new IllegalStateException("Number of types is not identical to number of resultset columns. "
                        + "Number of types: " + formatTypes.length + ". Number of columns: " + resultSetMetaData.getColumnCount());
            }
            int currentRow = 0;
            HSSFRow row = sheet.createRow(currentRow);
            int numCols = resultSetMetaData.getColumnCount();
            boolean isAutoDecideFormatTypes;
            if (isAutoDecideFormatTypes = (formatTypes == null)) {
                formatTypes = new FormatType[numCols];
            }
            for (int i = 0; i < numCols; i++) {
                String title;
                if (colTitles != null && i < colTitles.size()) {
                    title = colTitles.get(i);
                } else {
                    title = tbl != null ? tbl.getColumnName(i) : tm.getColumnName(i);
                }

                writeCell(row, i, title, FormatType.TEXT, boldFont);
                if (isAutoDecideFormatTypes) {
                    Class _class = Class.forName(resultSetMetaData.getColumnClassName(i + 1));
                    formatTypes[i] = getFormatType(_class);
                }
            }
            currentRow++;
            // Write report rows
            while (resultSet.next()) {
                row = sheet.createRow(currentRow++);
                for (int i = 0; i < numCols; i++) {
                    Object value = resultSet.getObject(i + 1);
                    writeCell(row, i, value, formatTypes[i]);
                }
            }
            // Autosize columns
            for (int i = 0; i < numCols; i++) {
                sheet.autoSizeColumn((short) i);
            }
            workbook.write(outputStream);
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            outputStream.close();
        }
    }

    private boolean generateFromTable(OutputStream outputStream) throws Exception {
        try {
            int numCols = tbl != null ? tbl.getColumnCount() : tm.getColumnCount();
            if (formatTypes != null && formatTypes.length != numCols) {
                throw new IllegalStateException("Number of types is not identical to number of resultset columns. "
                        + "Number of types: " + formatTypes.length + ". Number of columns: " + numCols);
            }
            int currentRow = 0;
            HSSFRow row = sheet.createRow(currentRow);

            boolean isAutoDecideFormatTypes;
            if (isAutoDecideFormatTypes = (formatTypes == null)) {
                formatTypes = new FormatType[numCols];
            }
            for (int i = 0; i < numCols; i++) {
                String title;
                if (colTitles != null && i < colTitles.size()) {
                    title = colTitles.get(i);
                } else {
                    title = tbl != null ? tbl.getColumnName(i) : tm.getColumnName(i);
                }

                writeCell(row, i, title, FormatType.TEXT, boldFont);
                if (isAutoDecideFormatTypes) {
                    Class _class = tbl != null ? tbl.getColumnClass(i) : tm.getColumnClass(i);
                    formatTypes[i] = getFormatType(_class);
                }
            }
            currentRow++;
            // Write report rows
            int len = tbl != null ? tbl.getRowCount() : tm.getRowCount();
            for (int j = 0; j < len; j++) {
                row = sheet.createRow(currentRow++);
                for (int i = 0; i < numCols; i++) {
                    Object value = tbl != null ? tbl.getValueAt(j, i) : tm.getValueAt(j, i);
                    writeCell(row, i, value, formatTypes[i]);
                }
            }
            // Autosize columns
            for (int i = 0; i < numCols; i++) {
                sheet.autoSizeColumn((short) i);
            }
            workbook.write(outputStream);
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            outputStream.close();
        }
    }

    /**
     * Generate file excel from the data-source.
     *
     * @param file - output file
     * @throws Exception
     */
    public boolean generate(File file) throws Exception {
        boolean result=false;
        if (resultSet != null) {
          result=  generateFromResultSet(new FileOutputStream(file));
        } else if (tbl != null || tm != null) {
          result=  generateFromTable(new FileOutputStream(file));
        } else {
            JOptionPane.showMessageDialog(null, "Data source is null!");
        }
        return result;
    }

    private void writeCell(HSSFRow row, int col, Object value, FormatType formatType) throws NestableException {
        writeCell(row, col, value, formatType, null, null);
    }

    private void writeCell(HSSFRow row, int col, Object value, FormatType formatType, HSSFFont font) throws NestableException {
        writeCell(row, col, value, formatType, null, font);
    }

    private void writeCell(HSSFRow row, int col, Object value, FormatType formatType,
            Short bgColor, HSSFFont font) throws NestableException {
        HSSFCell cell = HSSFCellUtil.createCell(row, col, null);
        if (value == null) {
            return;
        }
        if (font != null) {
            HSSFCellStyle style = workbook.createCellStyle();
            style.setFont(font);
            cell.setCellStyle(style);
        }
        switch (formatType) {
            case TEXT:
                cell.setCellValue(value.toString());
                break;
            case INTEGER:
                cell.setCellValue(((Number) value).intValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat(("#,##0")));
                break;
            case FLOAT:
                cell.setCellValue(((Number) value).doubleValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat(("#,##0.00")));
                break;
            case DATE:
                cell.setCellValue((Timestamp) value);
                HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat(("m/d/yy")));
                break;
            case MONEY:
                cell.setCellValue(((Number) value).intValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT, format.getFormat("($#,##0.00);($#,##0.00)"));
                break;
            case PERCENTAGE:
                cell.setCellValue(((Number) value).doubleValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT, HSSFDataFormat.getBuiltinFormat("0.00%"));
        }
        if (bgColor != null) {
            HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.FILL_FOREGROUND_COLOR, bgColor);
            HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.FILL_PATTERN, HSSFCellStyle.SOLID_FOREGROUND);
        }
    }

    public enum FormatType {

        TEXT,
        INTEGER,
        FLOAT,
        DATE,
        MONEY,
        PERCENTAGE
    }
}
