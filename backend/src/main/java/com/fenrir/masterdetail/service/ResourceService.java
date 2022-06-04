package com.fenrir.masterdetail.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fenrir.masterdetail.dto.statistics.CovidDayReport;
import com.fenrir.masterdetail.dto.statistics.MoviePremierCountMToCovidD;
import com.fenrir.masterdetail.model.Country;
import com.fenrir.masterdetail.model.CovidReport;
import com.fenrir.masterdetail.repository.CountryRepository;
import com.fenrir.masterdetail.repository.CovidReportRepository;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@AllArgsConstructor
@Service
public class ResourceService {
    private StatisticService statisticService;
    private CountryRepository countryRepository;
    private CovidReportRepository covidReportRepository;

    public File exportAsJSON() {
        try {
            List<MoviePremierCountMToCovidD> list = statisticService.getMoviePremiereCountMToCovidD();

            ObjectMapper mapper = new ObjectMapper();
            ArrayNode moviePremierCountJSONArray = mapper.createArrayNode();

            for (MoviePremierCountMToCovidD moviePremierCountMToCovidD : list) {
                ArrayNode cases = mapper.createArrayNode();
                List<CovidDayReport> covidStats = moviePremierCountMToCovidD.getCovidStats();
                for (CovidDayReport covidStat : covidStats) {
                    ObjectNode dayCases = mapper.createObjectNode();
                    dayCases.put("day", covidStat.day() + 1);
                    dayCases.put("cases", covidStat.cases());
                    dayCases.put("death", covidStat.death());
                    cases.add(dayCases);
                }

                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("year", moviePremierCountMToCovidD.getYear());
                objectNode.put("month", moviePremierCountMToCovidD.getMonth());
                objectNode.put("movieCount", moviePremierCountMToCovidD.getMovieCount());
                objectNode.put("cases", cases);
                moviePremierCountJSONArray.add(objectNode);
            }

            String filename = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".json";
            Path path = Path.of("tmp", filename);
            ObjectWriter writer = mapper.writer();
            writer.writeValue(new File(path.toString()), moviePremierCountJSONArray);

            return path.toFile();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    public File exportAsXML() {
        try {
            List<MoviePremierCountMToCovidD> list = statisticService.getMoviePremiereCountMToCovidD();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("statistics");
            document.appendChild(root);

            for (MoviePremierCountMToCovidD moviePremierCountMToCovidD : list) {
                Element monthStats = document.createElement("monthCases");

                Element yearElem = document.createElement("year");
                yearElem.setTextContent(String.valueOf(moviePremierCountMToCovidD.getYear()));
                monthStats.appendChild(yearElem);

                Element monthElem = document.createElement("month");
                monthElem.setTextContent(String.valueOf(moviePremierCountMToCovidD.getMonth()));
                monthStats.appendChild(monthElem);

                Element movieCountElem = document.createElement("movieCount");
                movieCountElem.setTextContent(String.valueOf(moviePremierCountMToCovidD.getMovieCount()));
                monthStats.appendChild(movieCountElem);

                List<CovidDayReport> covidStats = moviePremierCountMToCovidD.getCovidStats();
                Element covidStatsElem = document.createElement("covid");
                for (CovidDayReport covidStat : covidStats) {
                    Element dayCases = document.createElement("dayCases");

                    Element dayElem = document.createElement("day");
                    dayElem.setTextContent(String.valueOf(covidStat.day() + 1));
                    dayCases.appendChild(dayElem);

                    Element casesElem = document.createElement("monthStats");
                    casesElem.setTextContent(String.valueOf(covidStat.cases()));
                    dayCases.appendChild(casesElem);

                    Element deathElem = document.createElement("deaths");
                    deathElem.setTextContent(String.valueOf(covidStat.death()));
                    dayCases.appendChild(deathElem);

                    covidStatsElem.appendChild(dayCases);
                }
                monthStats.appendChild(covidStatsElem);
                root.appendChild(monthStats);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            String filename = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".json";
            Path path = Path.of("tmp", filename);

            StreamResult result = new StreamResult(new File(path.toString()));
            transformer.transform(source, result);

            return path.toFile();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void importJSON(MultipartFile file) {
        Path path = Path.of("upload", file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            String content = readFile(path);
            JSONArray root = new JSONArray(content);
            parseJSON(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void parseJSON(JSONArray rootArray) throws ParseException {
        List<CovidReport> covidReports = new ArrayList<>();

        for (int i = 0; i < rootArray.length(); i++) {
            JSONObject rootObject = rootArray.getJSONObject(i);
            String date = rootObject.getString("date");
            int newCases = rootObject.getInt("newCases");
            int cumulativeCases = rootObject.getInt("cumulativeCases");
            int newDeaths = rootObject.getInt("newDeaths");
            int cumulativeDeaths = rootObject.getInt("cumulativeDeaths");

            JSONObject countryObject = rootObject.getJSONObject("country");
            long id = countryObject.getLong("id");
            String name = countryObject.getString("name");
            Country country = countryRepository.getCountryByName(name);

            CovidReport covidReport = new CovidReport(
                    parseDate(date),
                    newCases,
                    cumulativeCases,
                    newDeaths,
                    cumulativeDeaths,
                    country
            );

            covidReports.add(covidReport);
        }
        covidReportRepository.saveAll(covidReports);
    }

    public void importXML(MultipartFile file) {
        Path path = Path.of("upload", file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(path.toFile());
            parseXML(document);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void parseXML(Document document) throws ParseException {
        List<CovidReport> covidReports = new ArrayList<>();

        NodeList list = document.getElementsByTagName("report");
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            Element element = (Element) node;
            String date = element.getElementsByTagName("date").item(0).getTextContent();
            int newCases = Integer.parseInt(element.getElementsByTagName("newCases").item(0).getTextContent());
            int cumulativeCases = Integer.parseInt(element.getElementsByTagName("cumulativeCases").item(0).getTextContent());
            int newDeaths = Integer.parseInt(element.getElementsByTagName("newDeaths").item(0).getTextContent());
            int cumulativeDeaths = Integer.parseInt(element.getElementsByTagName("cumulativeDeaths").item(0).getTextContent());

            Element countryElem = (Element) element.getElementsByTagName("country").item(0);
            Long id = Long.parseLong(countryElem.getElementsByTagName("id").item(0).getTextContent());
            String name = countryElem.getElementsByTagName("name").item(0).getTextContent();
            Country country = countryRepository.getCountryByName(name);

            CovidReport covidReport = new CovidReport(
                    parseDate(date),
                    newCases,
                    cumulativeCases,
                    newDeaths,
                    cumulativeDeaths,
                    country
            );

            covidReports.add(covidReport);
        }

        covidReportRepository.saveAll(covidReports);
    }

    private String readFile(Path path) throws Exception {
        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toString()))) {
            String curr;
            while ((curr = reader.readLine()) != null) {
                builder.append(curr);
            }
        }
        return builder.toString();
    }

    private Date parseDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy");
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.parse(date);
    }
}
