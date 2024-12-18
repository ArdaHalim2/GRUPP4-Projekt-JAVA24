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
    testCategory     VARCHAR(100),
    testMaxScore     INT,
    testStudentScore INT DEFAULT 0,
    testDate         DATE,
    test_studentId   INT, -- Foreign key som kopplar till student
    FOREIGN KEY (test_studentId) REFERENCES student (studentId)
);

-- Skapa tabell för city (städer)
CREATE TABLE city
(
    cityId         INT AUTO_INCREMENT PRIMARY KEY,
    cityName       VARCHAR(255) NOT NULL,
    city_countryId INT, -- Foreign key som kopplar till country
    FOREIGN KEY (city_countryId) REFERENCES country (countryId)
);

-- Skapa tabell för country (länder)
CREATE TABLE country
(
    countryId      INT AUTO_INCREMENT PRIMARY KEY,
    countryName    VARCHAR(255) UNIQUE NOT NULL,
    countryCapitaL INT, -- Foreign key som kopplar till city (städer)
    FOREIGN KEY (countryCapitaL) REFERENCES city (cityId)
);

-- Skapa tabell för lake (sjöar)
CREATE TABLE lake
(
    lakeId         INT AUTO_INCREMENT PRIMARY KEY,
    lakeName       VARCHAR(255) NOT NULL,
    lake_countryId INT, -- Foreign key som kopplar till country
    FOREIGN KEY (lake_countryId) REFERENCES country (countryId)
);

INSERT INTO city (cityName, city_countryId)
VALUES ('Paris', 1),
       ('London', 2),
       ('Madrid', 3),
       ('Rom', 4),
       ('Aten', 5),
       ('Amsterdam', 6),
       ('Brussels', 7),
       ('Bern', 8),
       ('Warszawa', 9),
       ('Oslo', 10),
       ('Lisabon', 11),
       ('Prag', 12),
       ('Stockholm', 13),
       ('Budapest', 14),
       ('Helsingfors', 15);


INSERT INTO country (countryName, countryCapitaL)
VALUES ('Frankrike', 1),
       ('Storbritannien', 2),
       ('Spanien', 3),
       ('Italien', 4),
       ('Grekland', 5),
       ('Nederländerna', 6),
       ('Belgien', 7),
       ('Schweiz', 8),
       ('Polen', 9),
       ('Norge', 10),
       ('Portugal', 11),
       ('Tjeckien', 12),
       ('Sverige', 13),
       ('Ungern', 14),
       ('Finland', 15);

INSERT INTO country (countryName, countryCapitaL)
VALUES ('Frankrike', 1),
       ('Storbritannien', 2),
       ('Spanien', 3),
       ('Italien', 4),
       ('Grekland', 5),
       ('Nederländerna', 6),
       ('Belgien', 7),
       ('Schweiz', 8),
       ('Polen', 9),
       ('Norge', 10),
       ('Portugal', 11),
       ('Tjeckien', 12),
       ('Sverige', 13),
       ('Ungern', 14),
       ('Finland', 15);


ALTER TABLE country
    ADD CONSTRAINT fk_country_capital
        FOREIGN KEY (countryCapital_cityID) REFERENCES city (cityId);

-- Skapa index för att förbättra sökningar på viktiga kolumner
CREATE INDEX idx_studentId ON test (test_studentId);
CREATE INDEX idx_countryId ON lake (lake_countryId);
CREATE INDEX idx_city_countryId ON city (city_countryId);

CREATE INDEX idx_country_name ON country (countryName)
CREATE INDEX idx_city_name ON city (cityName)
CREATE INDEX idx_city_name ON lake (lakeName)
