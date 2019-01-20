package raas;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CsvToJson {
    public CsvToJson(Path pthTempDir) throws IOException {
        Files.walk(pthTempDir).filter(path -> path.toString()
                .endsWith(".txt") && !path.toString().contains("Definitie"))
                .forEach(path -> {
                        try {
                            csvToJsonFile(path.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                });
    }

    public void csvToJsonFile(String path) throws IOException {
        File input = new File(path);
        File output = new File(path.replaceAll("txt", "json"));
        String key = path.replaceAll(".txt", "").replaceAll(".*/", "");
        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
        CsvMapper csvMapper = new CsvMapper();
        HashMap<String, List> myMap = new HashMap<>();
        MappingIterator<Map> iterator = csvMapper.readerFor(Map.class).with(csvSchema).readValues(new InputStreamReader(new FileInputStream(input)));
        ArrayList<Map> values = new ArrayList<>();
        int amountOfInvalidRows = 0;
        while (iterator.hasNextValue()) {
            try {
                Map value = iterator.nextValue();
                addToValues(path, value, values);
            } catch (JsonParseException e) {
                amountOfInvalidRows++;
                continue;

            }

        }
        printInvalidRows(amountOfInvalidRows, key);
        input.delete();
        myMap.put(key, values);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(output, myMap);
    }

    private void addToValues(String path, Map value, ArrayList<Map> values) {
        if ((!path.contains("wegvakken") || !value.get("WEGNUMMER").equals(""))
                && ((!path.contains("/ongevallen") || !value.get("HECTOMETER").equals(""))
                && ((!path.contains("/puntlocaties") || value.get("FK_VELD5").toString().contains("HTT"))))
        ) {
            values.add(value);
        }
    }

    private void printInvalidRows(int amount, String file) {
        if (amount > 0) {
            System.out.println(String.format("Removed %d invalid rows in file %s", amount, file));
        }
    }
}