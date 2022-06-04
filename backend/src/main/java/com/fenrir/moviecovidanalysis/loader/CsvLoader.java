package com.fenrir.moviecovidanalysis.loader;

import com.univocity.parsers.common.processor.ObjectRowProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class CsvLoader {
    public void load(String file, ObjectRowProcessor rowProcessor) throws FileNotFoundException {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setLineSeparatorDetectionEnabled(true);
        settings.setHeaderExtractionEnabled(true);
        settings.setProcessor(rowProcessor);
        CsvParser parser = new CsvParser(settings);
        parser.parse(new FileReader(file));
    }
}
