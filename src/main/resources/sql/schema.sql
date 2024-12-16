-- Skapa tabell för student
CREATE TABLE student
(
    studentId          INT AUTO_INCREMENT PRIMARY KEY,
    studentName        VARCHAR(255) NOT NULL,
    studentDateOfBirth DATE
);

-- Skapa tabell för test
CREATE TABLE test
(
    testId           INT AUTO_INCREMENT PRIMARY KEY,
    testName         VARCHAR(255) NOT NULL,
    testCategory     VARCHAR(100),
    testMaxScore     INT,
    testStudentScore INT,
    testDate         DATE,
    test_studentId   INT, -- Foreign key som kopplar till student
    FOREIGN KEY (test_studentId) REFERENCES student (studentId)
);

-- Skapa tabell för country
CREATE TABLE country
(
    countryId             INT AUTO_INCREMENT PRIMARY KEY,
    countryName           VARCHAR(255) UNIQUE NOT NULL,
    countryCapital_cityID INT, -- Foreign key som kopplar till city
    countryPopulation     BIGINT
);

-- Skapa tabell för lake
CREATE TABLE lake
(
    lakeId         INT AUTO_INCREMENT PRIMARY KEY,
    lakeName       VARCHAR(255) NOT NULL,
    lake_countryId INT, -- Foreign key som kopplar till country
    FOREIGN KEY (lake_countryId) REFERENCES country (countryId)
);

-- Skapa tabell för city
CREATE TABLE city
(
    cityId         INT AUTO_INCREMENT PRIMARY KEY,
    cityName       VARCHAR(255) NOT NULL,
    city_countryId INT, -- Foreign key som kopplar till country
    FOREIGN KEY (city_countryId) REFERENCES country (countryId)
);

-- Lägga till en relation mellan country och city för huvudstad
ALTER TABLE country
    ADD CONSTRAINT fk_country_capital
        FOREIGN KEY (countryCapital_cityID) REFERENCES city (cityId);

-- Skapa index för att förbättra sökningar på viktiga kolumner
CREATE INDEX idx_studentId ON test(test_studentId);
CREATE INDEX idx_countryId ON lake(lake_countryId);
CREATE INDEX idx_city_countryId ON city(city_countryId);
