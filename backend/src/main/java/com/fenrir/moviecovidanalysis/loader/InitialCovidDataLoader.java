package com.fenrir.moviecovidanalysis.loader;

import com.fenrir.moviecovidanalysis.model.Country;
import com.fenrir.moviecovidanalysis.model.CovidReport;
import com.fenrir.moviecovidanalysis.repository.CountryRepository;
import com.fenrir.moviecovidanalysis.repository.CovidReportRepository;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.ObjectRowProcessor;
import com.univocity.parsers.conversions.Conversions;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;

@AllArgsConstructor
@Component
public class InitialCovidDataLoader implements CommandLineRunner {
    private final static Logger logger = LoggerFactory.getLogger(InitialCovidDataLoader.class);

    private CountryRepository countryRepository;
    private CovidReportRepository covidReportRepository;

    private final CsvLoader csvLoader = new CsvLoader();

    private final List<CovidReport> reports = new ArrayList<>();
    private final Map<String, Country> countries = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        logger.info("Loading Covid dataset...");
        parseCovid();

        countryRepository.saveAll(countries.values());
        covidReportRepository.saveAll(reports);

        reports.clear();
        countries.clear();
        logger.info("Covid dataset loaded.");
    }

    public void parseCovid() throws FileNotFoundException {
        Path path = Path.of("data/who_covid_data.csv");
        ObjectRowProcessor rowProcessor = new ObjectRowProcessor() {
            @Override
            public void rowProcessed(Object[] objects, ParsingContext parsingContext) {
                CovidReport report = new CovidReport(
                        (Date) objects[0],
                        (Integer) objects[4],
                        (Integer) objects[5],
                        (Integer) objects[6],
                        (Integer) objects[7],
                        parseCountry((String) objects[2])
                );
                reports.add(report);
            }
        };
        rowProcessor.convertFields(Conversions.toDate("yyyy-MM-dd")).set("Date_reported");
        rowProcessor.convertFields(Conversions.toInteger()).set("New_cases", "Cumulative_cases", "New_deaths", "Cumulative_deaths");
        csvLoader.load(path.toString(), rowProcessor);
    }

    private Country parseCountry(String country) {
        countries.putIfAbsent(country, new Country(country));
        return countries.get(country);
    }
}
