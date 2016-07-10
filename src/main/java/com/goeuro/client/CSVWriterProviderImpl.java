package com.goeuro.client;

import au.com.bytecode.opencsv.CSVWriter;
import com.goeuro.config.Config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by phanindra on 7/10/16.
 */
public class CSVWriterProviderImpl implements CSVWriterProvider {

    public CSVWriter getCsvWriter(File file) throws IOException {
        return new CSVWriter(new FileWriter(new File(file, Config.CSV_FILE_NAME)));
    }
}
