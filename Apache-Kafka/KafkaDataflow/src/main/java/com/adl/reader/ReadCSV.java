package com.adl.reader;

import com.adl.model.student;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.util.List;

public class ReadCSV {
    public String csvFileName = "data/student1.csv";
    public List stdlist;
    public List ReadCSVFile()  {

        try {
           CSVReader csvReader = new CSVReader(new FileReader(csvFileName));

            CsvToBean csvToBean = new CsvToBeanBuilder(csvReader)
                    .withType(student.class)
                    .withIgnoreLeadingWhiteSpace(true).build();

            stdlist = csvToBean.parse();

            csvReader.close();
        }catch(Exception FileNotFoundException){
              System.out.println("File is not available...");
        }
 
        return stdlist;
    }
}
