/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companydbmanagerant.model.TableModel;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import companydbmanagerant.model.Employee.Employee;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import raven.toast.Notifications;

/**
 *
 * @author PHC
 */
public class SortByTableModel extends AbstractTableModel {

    private List<Column> columns;

    private List<String> activeColumns = new ArrayList<>();  // 활성화된 열들의 목록

    public SortByTableModel(List<Column> columns) {
        this.columns = columns;
        fireTableStructureChanged();
        this.activeColumns = new ArrayList<>();
        this.activeColumns.addAll(Arrays.asList(
                "정렬 순서", "Column 명", "정렬 방식"
        ));
        // 모델이 변경되었음을 JTable에 알림
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return super.getColumnClass(columnIndex);
    }

    @Override
    public int getColumnCount() {
        return activeColumns.size();
    }

    @Override
    public int getRowCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(int column) {
        return activeColumns.get(column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Column column = columns.get(rowIndex);

        String columnName = getColumnName(columnIndex);
        switch (columnName) {
            case "정렬 순서":
                return column.getOrder();
            case "Column 명":
                return column.getColumnName();
            case "정렬 방식":
                return column.getSortby();
            default:
                return null;  // 알 수 없는 열 이름에 대해서는 null 반환
        }
    }

    // 사용자가 열 선택을 변경할 때 호출될 메서드
    public void setActiveColumns(List<String> newActiveColumns) {
        this.activeColumns = newActiveColumns;
        fireTableStructureChanged(); // 테이블 구조 변경을 알림
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Column column = columns.get(rowIndex);

        String columnName = getColumnName(columnIndex);
        switch (columnName) {
            case "정렬 순서":
                if (!column.getOrder().equals(aValue)) {
                    column.setOrder((String) aValue);

                }
            case "Column 명":
                if (!column.getColumnName().equals(aValue)) {
                    column.setColumnName((String) aValue);

                }
            case "정렬 방식":
                if (!column.getSortby().equals(aValue)) {
                    column.setSortby((String) aValue);

                }

        }

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

//    public void getSelectedColumn(int selectedRow) {
//        if (selectedRow >= 0 && selectedRow < columns.size()) {
//            return columns.get(selectedRow);
//        }
//        return null;  // 선택된 행이 없거나 범위를 벗어난 경우 null을 반환
//    }
    public boolean upper(int row) {

        if (row <= 0 || row >= columns.size()) {
            return false;
        }

        if (columns.get(row).getSortby().equalsIgnoreCase("NONE")) {
            return false;
        }
        Column temp = columns.get(row - 1);
        columns.set(row - 1, columns.get(row));
        columns.set(row, temp);
        return true;
    }

    public boolean downner(int row) {

        if (row >= columns.size() - 1) {
            return false;
        }

        if (columns.get(row + 1).getSortby().equalsIgnoreCase("NONE")) {
            return false;
        }

        Column temp = columns.get(row + 1);
        columns.set(row + 1, columns.get(row));
        columns.set(row, temp);
        return true;
    }

    public int findNone() {

        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).getSortby().equalsIgnoreCase("NONE")) {
                return i;
            }
        }
        return -1;
    }

    public void refreshOrder() {
        List<Column> newcolumns = new ArrayList<>();
        List<Column> ascdesc = new ArrayList<>();
        List<Column> none = new ArrayList<>();
        for (Column col : columns) {
            if (col.getSortby().equalsIgnoreCase("NONE")) {
                none.add(col);
            } else {
                ascdesc.add(col);
            }
        }
        newcolumns.addAll(ascdesc);
        newcolumns.addAll(none);

        for (int i = 0; i < newcolumns.size(); i++) {
            if (newcolumns.get(i).getSortby().equalsIgnoreCase("NONE")) {
                newcolumns.get(i).setOrder("-");
            } else {
                newcolumns.get(i).setOrder("" + (i + 1));
            }
        }
        columns = newcolumns;
    }

    public String getOrderList() {

        StringBuilder sql = new StringBuilder(" ORDER BY ");
        boolean isExist = false;

        for (Column col : columns) {
            if (col.getSortby().equalsIgnoreCase("NONE")) {
                break;
            } else {
                isExist = true;
                sql.append(col.getColumnName()).append(" ").append(col.getSortby()).append(", ");
            }
        }
        if (!isExist) {
            return "";
        } else {
            // 마지막 쉼표를 제거
            int sqlLength = sql.length();
            if (sqlLength > 0) {
                sql.delete(sqlLength - 2, sqlLength);
            }
            return sql.toString();
        }
    }

}
