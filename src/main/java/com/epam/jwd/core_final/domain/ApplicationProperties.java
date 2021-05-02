package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.util.PropertyReaderUtil;

import java.time.format.DateTimeFormatter;

/**
 * This class should be IMMUTABLE!
 * <p>
 * Expected fields:
 * <p>
 * inputRootDir {@link String} - base dir for all input files
 * outputRootDir {@link String} - base dir for all output files
 * crewFileName {@link String}
 * missionsFileName {@link String}
 * spaceshipsFileName {@link String}
 * <p>
 * fileRefreshRate {@link Integer}
 * dateTimeFormat {@link String} - date/time format for {@link java.time.format.DateTimeFormatter} pattern
 */
public final class ApplicationProperties {
    //todo

    private static ApplicationProperties INSTANCE;

    public static ApplicationProperties getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApplicationProperties();
        }
        return INSTANCE;
    }

    private final String inputRootDir;
    private final String outputRootDir;
    private final String crewFileName;
    private final String missionsFileName;
    private final String spacemap;
    private final String spaceshipsFileName;
    private final Integer fileRefreshRate;
    private final DateTimeFormatter dateTimeFormatter;


    private ApplicationProperties() {
        PropertyReaderUtil propertyReaderUtil = PropertyReaderUtil.getInstance();

        inputRootDir = propertyReaderUtil.getProperty("inputRootDir")+"\\";
        outputRootDir = propertyReaderUtil.getProperty("outputRootDir")+"\\";
        crewFileName = propertyReaderUtil.getProperty("crewFileName");
        missionsFileName = propertyReaderUtil.getProperty("missionsFileName")+".json";
        spacemap = propertyReaderUtil.getProperty("spacemap");
        spaceshipsFileName = propertyReaderUtil.getProperty("spaceshipsFileName");
        fileRefreshRate = Integer.valueOf(propertyReaderUtil.getProperty("fileRefreshRate"));
        dateTimeFormatter = DateTimeFormatter.ofPattern(propertyReaderUtil.getProperty("dateTimeFormat"));

    }

    public String getInputRootDir() {
        return inputRootDir;
    }

    public String getOutputRootDir() {
        return outputRootDir;
    }

    public String getCrewFileName() {
        return crewFileName;
    }

    public String getMissionsFileName() {
        return missionsFileName;
    }

    public String getSpacemapFileName() {
        return spacemap;
    }

    public String getSpaceshipsFileName() {
        return spaceshipsFileName;
    }

    public Integer getFileRefreshRate() {
        return fileRefreshRate;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }
}
