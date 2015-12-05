package sponsoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortMatrixColumns {
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            String [] parts = line.split("\\|");
            System.out.println(printSortedMatrix(parts));
        }
    }

    static String printSortedMatrix(String [] parts) {
        List<Column> list = new ArrayList<>(parts.length);
        int [][] auxArray = new int[parts.length][parts.length];

        for (int i = 0; i < parts.length; i++) {
            String [] row = parts[i].trim().split(" ");
            for (int j = 0; j < row.length; j++) {
                auxArray[j][i] = Integer.parseInt(row[j]);
            }
        }

        for (int[] arr : auxArray) {
            list.add(new Column(arr));
        }

        Collections.sort(list);
        return listOfColumnsToString(list);
    }

    static String listOfColumnsToString(List<Column> list) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            for (Column col : list) {
                result.append(col.column[i]).append(" ");
            }
            result.append("| ");
        }
        return result.delete(result.length() - 3, result.length()).toString();
    }
}

class Column implements Comparable<Column> {
    int [] column;

    Column(int [] col) {
        column = col;
    }

    @Override
    public int compareTo(Column c) {
        for (int i = 0; i < column.length; i++) {
            if (column[i] > c.column[i]) {
                return +1;
            } else if (column[i] < c.column[i]) {
                return -1;
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int col : column) {
            result.append(col).append(" ");
        }
        return result.deleteCharAt(result.length() - 1).toString();
    }
}
