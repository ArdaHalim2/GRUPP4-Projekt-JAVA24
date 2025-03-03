DROP TABLE city;
DROP TABLE lake;
DROP TABLE test;
DROP TABLE student;
DROP TABLE country;

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
    city_countryId INT NOT NULL, -- Vi kommer att uppdatera detta senare
    FOREIGN KEY (city_countryId) REFERENCES country (countryId)
);

-- Skapa tabell för lake (sjöar)
CREATE TABLE lake
(
    lakeId         INT AUTO_INCREMENT PRIMARY KEY,
    lakeName       VARCHAR(255) NOT NULL UNIQUE,
    lake_countryId INT NOT NULL, -- Foreign key som kopplar till country
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
    test_studentId   INT NOT NULL, -- Foreign key som kopplar till student
    FOREIGN KEY (test_studentId) REFERENCES student (studentId)
);

-- Skapa index för att förbättra sökningar på viktiga kolumner
CREATE INDEX idx_country_name ON country (countryName);
CREATE INDEX idx_city_name ON city (cityName);
CREATE INDEX idx_city_name ON lake (lakeName);

-- Lägg till 15 exempeldata för länder (countryCapitaL kommer att uppdateras senare)
INSERT INTO country (countryName, countryCapital)
VALUES ('Frankrike', 'Paris'),          -- 1
       ('Storbritannien', 'London'),    -- 2
       ('Spanien', 'Madrid'),           -- 3
       ('Italien', 'Rom'),              -- 4
       ('Grekland', 'Aten'),            -- 5
       ('Nederländerna', 'Amsterdam'),  -- 6
       ('Belgien', 'Bryssel'),          -- 7
       ('Schweiz', 'Bern'),             -- 8
       ('Polen', 'Warszawa'),           -- 9
       ('Norge', 'Oslo'),               -- 10
       ('Portugal', 'Lisabon'),         -- 11
       ('Tjeckien', 'Prag'),            -- 12
       ('Sverige', 'Stockholm'),        -- 13
       ('Ungern', 'Budapest'),          -- 14
       ('Finland', 'Helsingfors');      -- 15

-- Lägg till 15 exempeldata för städer (endast huvudstäder)
INSERT INTO city (cityName, city_countryId)
VALUES ('Nice', 1),
       ('Manchester', 2),
       ('Malaga', 3),
       ('Milano', 4),
       ('Korinth', 5),
       ('Rotterdam', 6),
       ('Brygge', 7),
       ('Luzern', 8),
       ('Krakow', 9),
       ('Bergen', 10),
       ('Porto', 11),
       ('Brno', 12),
       ('Malmö', 13),
       ('Debrecen', 14),
       ('Tammerfors', 15);

-- Lägg till 15 sjöar med referens till land via lake_countryId
INSERT INTO lake (lakeName, lake_countryId)
VALUES ('Genèvesjön', 1),           -- Frankrike
       ('Loch Ness', 2),            -- Storbritannien
       ('Sjöarna i Galicien', 3),   -- Spanien
       ('Lago di Garda', 4),        -- Italien
       ('Sjöerna i Makedonien', 5), -- Grekland
       ('IJsselmeer', 6),           -- Nederländerna
       ('Lac de la Plate Taille', 7), -- Belgien
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
       ('David'),
       ('Erik');


INSERT INTO test(testCategory, testMaxScore, testStudentScore, testDate, test_studentId)
VALUES('capitals', 5,5,'20241220', 1),
      ('capitals', 5,4,'20241220',2),
      ('capitals', 5,3,'20241220',3),
      ('capitals', 5,2,'20241220',4),
      ('capitals', 5,1,'20241220',5),
      ('lakes', 5,5,'20241220', 1),
      ('lakes', 5,4,'20241220',2),
      ('lakes', 5,3,'20241220',3),
      ('lakes', 5,2,'20241220',4),
      ('lakes', 5,1,'20241220',5),
      ('cities', 5,5,'20241220', 1),
      ('cities', 5,4,'20241220',2),
      ('cities', 5,3,'20241220',3),
      ('cities', 5,2,'20241220',4),
      ('cities', 5,1,'20241220',5);
