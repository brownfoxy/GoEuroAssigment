package com.goeuro.client;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.IOException;

/**
 * Created by phanindra on 7/10/16.
 */
public interface CSVWriterProvider {
    CSVWriter getCsvWriter(File file) throws IOException;
}
