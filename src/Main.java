import java.io.*;
import java.util.*;

public class Main {
    private List<String[]> records;

    public Main(String filePath) throws IOException {
        records = new ArrayList<>();
        loadRecords(filePath);
    }

    private void loadRecords(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(";");
            int randomValue = new Random().nextInt(11) + 10; // 10 <= X <= 20
            String[] newRecord = Arrays.copyOf(fields, fields.length + 2);
            newRecord[newRecord.length - 2] = String.valueOf(randomValue);
            newRecord[newRecord.length - 1] = "false";
            records.add(newRecord);
        }
        reader.close();
    }

    public int countFields(String[] record) {
        return record.length;
    }

    public void calculateMaxLengths() {
        int maxLength = 0;
        int[] fieldLengths = new int[records.get(0).length];

        for (String[] record : records) {
            maxLength = Math.max(maxLength, Arrays.toString(record).length());
            for (int i = 0; i < record.length; i++) {
                fieldLengths[i] = Math.max(fieldLengths[i], record[i].length());
            }
        }
        System.out.println("Max Record Length: " + maxLength);
        System.out.println("Max Field Lengths: " + Arrays.toString(fieldLengths));
    }

    public void padRecords() {
        for (String[] record : records) {
            for (int i = 0; i < record.length; i++) {
                record[i] = String.format("%-20s", record[i]);
            }
        }
    }

    public void addRecord(String[] newRecord) {
        records.add(newRecord);
    }

    public void displaySignificantData(int field1, int field2, int field3) {
        for (String[] record : records) {
            System.out.println(record[field1] + " | " + record[field2] + " | " + record[field3]);
        }
    }

    public String[] searchRecord(int keyField, String keyValue) {
        for (String[] record : records) {
            if (record[keyField].equals(keyValue)) {
                return record;
            }
        }
        return null;
    }


    public void modifyRecord(int index, String[] newValues) {
        if (index >= 0 && index < records.size()) {
            records.set(index, newValues);
        }
    }

    public void logicalDelete(int index) {
        if (index >= 0 && index < records.size()) {
            records.get(index)[records.get(index).length - 1] = "true";
        }
    }

    public static void main(String[] args) {
        try {
            Main processor = new Main("MultipleFiles/Viapiana.csv");
            processor.calculateMaxLengths();
            processor.padRecords();
            processor.displaySignificantData(0, 1, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}