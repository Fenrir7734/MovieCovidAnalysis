package com.fenrir.moviecovidanalysis.loader;

import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.ObjectRowProcessor;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

public class TsvLoader {
    public void load(String file, ObjectRowProcessor rowProcessor) throws FileNotFoundException {
        TsvParserSettings settings = new TsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setRowProcessor(rowProcessor);
        TsvParser parser = new TsvParser(settings);
        parser.parse(new FileReader(file));
    }
}
