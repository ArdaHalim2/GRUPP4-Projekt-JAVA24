-- DROP TABLE city;
-- DROP TABLE lake;
-- DROP TABLE test;
-- DROP TABLE student;
-- DROP TABLE country;

-- Skapa tabell för country först (utan foreign key till city)
CREATE TABLE country
(
    countryId      INT AUTO_INCREMENT PRIMARY KEY,
    countryName    VARCHAR(255) UNIQUE NOT NULL,
    countryCapital VARCHAR(255) NOT NULL -- Vi kommer att uppdatera detta senare
);

-- Skapa tabell för city (städer) utan foreign key till country
CREATE TABLE city
(
    cityId         INT AUTO_INCREMENT PRIMARY KEY,
    cityName       VARCHAR(255) NOT NULL UNIQUE,
    city_countryId INT, -- Vi kommer att uppdatera detta senare
    FOREIGN KEY (city_countryId) REFERENCES country (countryId)
);

-- Skapa tabell för lake (sjöar)
CREATE TABLE lake
(
    lakeId         INT AUTO_INCREMENT PRIMARY KEY,
    lakeName       VARCHAR(255) NOT NULL UNIQUE,
    lake_countryId INT, -- Foreign key som kopplar till country
    FOREIGN KEY (lake_countryId) REFERENCES country (countryId)
);

-- Skapa tabell för student
CREATE TABLE student
(
    studentId          INT AUTO_INCREMENT PRIMARY KEY,
    studentName        VARCHAR(255) NOT NULL
);

-- Skapa tabell för test
CREATE TABLE test
(
    testId           INT AUTO_INCREMENT PRIMARY KEY,
    testCategory     VARCHAR(255) NOT NULL,
    testMaxScore     INT DEFAULT 0,
    testStudentScore INT DEFAULT 0,
    testDate         DATE NOT NULL,
    test_studentId   INT, -- Foreign key som kopplar till student
    FOREIGN KEY (test_studentId) REFERENCES student (studentId)
);



-- Lägg till 15 exempeldata för länder (countryCapitaL kommer att uppdateras senare)
INSERT INTO country (countryName, countryCapital)
VALUES ('Frankrike', 'Paris'),
       ('Storbritannien', 'London'),
       ('Spanien', 'Madrid'),
       ('Italien', 'Rom'),
       ('Grekland', 'Aten'),
       ('Nederländerna', 'Amsterdam'),
       ('Belgien', 'Bryssel'),
       ('Schweiz', 'Bern'),
       ('Polen', 'Warszawa'),
       ('Norge', 'Oslo'),
       ('Portugal', 'Lisabon'),
       ('Tjeckien', 'Prag'),
       ('Sverige', 'Stockholm'),
       ('Ungern', 'Budapest'),
       ('Finland', 'Helsingfors');

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


INSERT INTO student(studentName)
VALUES ('Anna'),
       ('Bertil'),
       ('Caroline'),
       ('David');

-- Skapa index för att förbättra sökningar på viktiga kolumner
CREATE INDEX idx_country_name ON country (countryName);
CREATE INDEX idx_city_name ON city (cityName);
CREATE INDEX idx_city_name ON lake (lakeName);
