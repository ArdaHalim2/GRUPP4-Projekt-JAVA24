-- Skapa tabell för country först (utan foreign key till city)
CREATE TABLE country
(
    countryId      INT AUTO_INCREMENT PRIMARY KEY,
    countryName    VARCHAR(255) UNIQUE NOT NULL,
    countryCapital INT -- Vi kommer att uppdatera detta senare
);

-- Skapa tabell för city (städer) utan foreign key till country
CREATE TABLE city
(
    cityId         INT AUTO_INCREMENT PRIMARY KEY,
    cityName       VARCHAR(255) NOT NULL,
    city_countryId INT, -- Vi kommer att uppdatera detta senare
    FOREIGN KEY (city_countryId) REFERENCES country (countryId)
);

-- Skapa tabell för lake (sjöar)
CREATE TABLE lake
(
    lakeId         INT AUTO_INCREMENT PRIMARY KEY,
    lakeName       VARCHAR(255) NOT NULL,
    lake_countryId INT, -- Foreign key som kopplar till country
    FOREIGN KEY (lake_countryId) REFERENCES country (countryId)
);

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
    testStudentScore INT DEFAULT 0,
    testDate         DATE,
    test_studentId   INT, -- Foreign key som kopplar till student
    FOREIGN KEY (test_studentId) REFERENCES student (studentId)
);

-- Lägg till 15 exempeldata för städer (endast huvudstäder)
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

-- Lägg till 15 exempeldata för länder (countryCapitaL kommer att uppdateras senare)
INSERT INTO country (countryName)
VALUES ('Frankrike'),
       ('Storbritannien'),
       ('Spanien'),
       ('Italien'),
       ('Grekland'),
       ('Nederländerna'),
       ('Belgien'),
       ('Schweiz'),
       ('Polen'),
       ('Norge'),
       ('Portugal'),
       ('Tjeckien'),
       ('Sverige'),
       ('Ungern'),
       ('Finland');

-- Lägg till 15 sjöar med referens till land via lake_countryId
INSERT INTO lake (lakeName, lake_countryId)
VALUES ('Genèvesjön', 1),           -- Frankrike
       ('Loch Ness', 2),            -- Storbritannien
       ('Sjöarna i Galicien', 3),   -- Spanien
       ('Lago di Garda', 4),        -- Italien
       ('Sjöerna i Makedonien', 5), -- Grekland
       ('IJsselmeer', 6),           -- Nederländerna
       ('Lago di Neuchâtel', 8),    -- Schweiz
       ('Mazuriska sjöarna', 9),    -- Polen
       ('Oslofjorden', 10),         -- Norge
       ('Sjöarna i Algarve', 11),   -- Portugal
       ('Sjöarna i Bohemien', 12),  -- Tjeckien
       ('Vättern', 13),             -- Sverige
       ('Balatonsjön', 14),         -- Ungern
       ('Saimaa', 15);              -- Finland


-- Uppdatera länder med huvudstäder
UPDATE country SET countryCapitaL = 1 WHERE countryName = 'Frankrike';  -- Paris
UPDATE country SET countryCapitaL = 2 WHERE countryName = 'Storbritannien';  -- London
UPDATE country SET countryCapitaL = 3 WHERE countryName = 'Spanien';  -- Madrid
UPDATE country SET countryCapitaL = 4 WHERE countryName = 'Italien';  -- Rom
UPDATE country SET countryCapitaL = 5 WHERE countryName = 'Grekland';  -- Aten
UPDATE country SET countryCapitaL = 6 WHERE countryName = 'Nederländerna';  -- Amsterdam
UPDATE country SET countryCapitaL = 7 WHERE countryName = 'Belgien';  -- Brussels
UPDATE country SET countryCapitaL = 8 WHERE countryName = 'Schweiz';  -- Bern
UPDATE country SET countryCapitaL = 9 WHERE countryName = 'Polen';  -- Warszawa
UPDATE country SET countryCapitaL = 10 WHERE countryName = 'Norge';  -- Oslo
UPDATE country SET countryCapitaL = 11 WHERE countryName = 'Portugal';  -- Lisabon
UPDATE country SET countryCapitaL = 12 WHERE countryName = 'Tjeckien';  -- Prag
UPDATE country SET countryCapitaL = 13 WHERE countryName = 'Sverige';  -- Stockholm
UPDATE country SET countryCapitaL = 14 WHERE countryName = 'Ungern';  -- Budapest
UPDATE country SET countryCapitaL = 15 WHERE countryName = 'Finland';  -- Helsingfors



-- Skapa index för att förbättra sökningar på viktiga kolumner
CREATE INDEX idx_studentId ON test (test_studentId);
CREATE INDEX idx_countryId ON lake (lake_countryId);
CREATE INDEX idx_city_countryId ON city (city_countryId);

CREATE INDEX idx_country_name ON country (countryName);
CREATE INDEX idx_city_name ON city (cityName);
CREATE INDEX idx_city_name ON lake (lakeName);

SELECT *
FROM city;
