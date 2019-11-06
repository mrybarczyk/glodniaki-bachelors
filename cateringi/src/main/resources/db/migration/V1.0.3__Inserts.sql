--typy kont
INSERT INTO AccountType (Name) VALUES ('admin');
INSERT INTO AccountType (Name) VALUES ('company');
INSERT INTO AccountType (Name) VALUES ('user');

--konta
--1
INSERT INTO Accounts (AccountName, AccountTypeID, Email, Password)
VALUES ('admin', 1, 'admin@glodniaki.pl', 'password456');
--2
INSERT INTO Accounts (AccountName, AccountTypeID, Email, Password)
VALUES ('kuchnianowaka', 2, 'jannowak@oned.pl', 'dobrzegotuje120');
--3
INSERT INTO Accounts (AccountName, AccountTypeID, Email, Password)
VALUES ('wloskiespecjaly', 2, 'glodniaki@wloskiespecjaly.pl', 'kalamarnicawsosie');
--4
INSERT INTO Accounts (AccountName, AccountTypeID, Email, Password)
VALUES ('dietacud12', 2, 'janinakowalska@onteria.pl', 'kotek10');
--5
INSERT INTO Accounts (AccountName, AccountTypeID, Email, Password)
VALUES ('kasia_d', 3, 'kasiad@hmail.com', 'drzewo');
--6
INSERT INTO Accounts (AccountName, AccountTypeID, Email, Password)
VALUES ('moniczka19283', 3, 'monia19283@oned.pl', 'hasloo');
--7
INSERT INTO Accounts (AccountName, AccountTypeID, Email, Password)
VALUES ('pkrawczyk', 3, 'piotr.krawczyk@hmail.com', 'V23_@rfaa91JK!');
--8
INSERT INTO Accounts (AccountName, AccountTypeID, Email, Password)
VALUES ('dkj', 3, 'dkj@onteria.pl', 'powazneitrudnedozlamaniahaslo');
--9
INSERT INTO Accounts (AccountName, AccountTypeID, Email, Password)
VALUES ('panibasia', 3, 'panibasia@pieklo.pl', 'slawekchcialpizze');

--dane użytkownikow
INSERT INTO UserData VALUES (5, 'Katarzyna', 'Drzewo');
INSERT INTO UserData VALUES (6, 'Monika', 'Kisiel');
INSERT INTO UserData VALUES (7, 'Piotr', 'Krawczyk');
INSERT INTO UserData VALUES (8, 'Dominik', 'Kula-Jarosz');
INSERT INTO UserData VALUES (9, 'Basia', 'Pani');

--dane firm
INSERT INTO CompanyData (CompanyDataID, Name, NIP, REGON, WebsiteAddress, AverageRating)
VALUES (2, 'Kuchnia Nowaka', '986-812-12-12', '12-894283-5', 'www.kuchnianowaka.pl', 0);

INSERT INTO CompanyData (CompanyDataID, Name, NIP, REGON, WebsiteAddress, AverageRating)
VALUES (3, 'Włoskie specjały', '446-123-54-65', '13-666999-2', 'www.wloskiespecjaly.pl', 0);

INSERT INTO CompanyData (CompanyDataID, Name, NIP, REGON, WebsiteAddress, AverageRating)
VALUES (4, 'Dieta-cud!', '123-789-24-14', '14-566788-4', 'www.dietacud.gda.pl', 0);

--adresy
--1
INSERT INTO Address (AccountID, City, Street, PostCode)
VALUES (2, 'Gdynia', 'ul. Hetmańska 40', '81-412');
--2
INSERT INTO Address (AccountID, City, Street, PostCode)
VALUES (3, 'Sopot', 'ul. Władysława Broniewskiego 2', '81-841');
--3
INSERT INTO Address (AccountID, City, Street, PostCode, LocalNumber)
VALUES (4, 'Gdańsk', 'ul. Jana Kasprowicza 4', '80-313', 'C');
--4
INSERT INTO Address (AccountID, City, Street, PostCode, LocalNumber)
VALUES (5, 'Gdańsk', 'ul. Toruńska 14A', '80-747', '2');
--5
INSERT INTO Address (AccountID, City, Street, PostCode)
VALUES (6, 'Gdynia', 'ul. Kazimierza Gorskiego 2', '81-304');
--6
INSERT INTO Address (AccountID, City, Street, PostCode, LocalNumber)
VALUES (6, 'Gdynia', 'ul. Jerzego Waszyngtona 18', '81-342', '4');
--7
INSERT INTO Address (AccountID, City, Street, PostCode, LocalNumber)
VALUES (7, 'Gdańsk', 'ul. Platynowa 26C', '80-041', '2');
--8
INSERT INTO Address (AccountID, City, Street, PostCode, LocalNumber)
VALUES (8, 'Sopot', 'ul. Generała Władysława Andersa 2', '81-831', '5');
--9
INSERT INTO Address (AccountID, City, Street, PostCode, LocalNumber)
VALUES (9, 'Piekło 2.0', 'ul. Biuro', '00-666', '43.21');
--10
INSERT INTO Address (AccountID, City, Street, PostCode, LocalNumber)
VALUES (9, 'Stalowa Wola', 'ul. Głowackiego', '37-450', '3');

--typy usług
--1
INSERT INTO ServiceType (ServiceTypeName) VALUES ('Catering');
--2
INSERT INTO ServiceType (ServiceTypeName) VALUES ('Oferta okazjonalna');

--usługi
--1
INSERT INTO Services (ServiceTypeID, CompanyDataID, ServiceName, Description)
VALUES (2, 2, 'Domowy obiad', 'Firmowy obiad? Po co komu sushi, skoro można zjeść pysznego schabowego!');
--2
INSERT INTO Services (ServiceTypeID, CompanyDataID, ServiceName, Description)
VALUES (2, 3, 'Spaghetti dla firm', 'Pyszne spaghetti dla twojej firmy.');
--3
INSERT INTO Services (ServiceTypeID, CompanyDataID, ServiceName, Description)
VALUES (1, 4, 'Dieta bezglutenowa', 'Pyszne posiłki bez glutenu.');
--4
INSERT INTO Services (ServiceTypeID, CompanyDataID, ServiceName, Description)
VALUES (1, 4, 'Dieta wegetariańska', 'Pyszne poisłki bez mięsa.');
--5
INSERT INTO Services (ServiceTypeID, CompanyDataID, ServiceName, Description)
VALUES (1, 4, 'Dieta wegańska', 'Pyszne posiłki bez mięsa oraz produktów odzwierzęcych.');

--ulubione
INSERT INTO Favorites VALUES (9, 2);
INSERT INTO Favorites VALUES (9, 4);
INSERT INTO Favorites VALUES (7, 1);
INSERT INTO Favorites VALUES (6, 3);
INSERT INTO Favorites VALUES (5, 1);
INSERT INTO Favorites VALUES (8, 5);

--warianty
--1
INSERT INTO ServiceVariant (ServiceID, PricePerPerson, DayNumber, DeliveryTime)
VALUES (1, 15.90, 0, '13:00');

--2
INSERT INTO ServiceVariant (ServiceID, PricePerPerson, DayNumber, DeliveryTime)
VALUES (2, 29.90, 0, '12:00');

--3
INSERT INTO ServiceVariant (ServiceID, Calories, PricePerPerson, DayNumber, DeliveryTime)
VALUES (3, 2000, 34.90, 5, '8:00');
--4
INSERT INTO ServiceVariant (ServiceID, Calories, PricePerPerson, DayNumber, DeliveryTime)
VALUES (3, 1500, 30.90, 5, '8:00');
--5
INSERT INTO ServiceVariant (ServiceID, Calories, PricePerPerson, DayNumber, DeliveryTime)
VALUES (3, 2500, 38.90, 5, '8:00');

--6
INSERT INTO ServiceVariant (ServiceID, Calories, PricePerPerson, DayNumber, DeliveryTime)
VALUES (4, 2000, 39.90, 5, '8:30');
--7
INSERT INTO ServiceVariant (ServiceID, Calories, PricePerPerson, DayNumber, DeliveryTime)
VALUES (4, 2500, 44.90, 5, '8:30');

--8
INSERT INTO ServiceVariant (ServiceID, Calories, PricePerPerson, DayNumber, DeliveryTime)
VALUES (5, 2000, 39.90, 5, '8:30');
--9
INSERT INTO ServiceVariant (ServiceID, Calories, PricePerPerson, DayNumber, DeliveryTime)
VALUES (5, 2500, 44.90, 5, '8:30');

--zamowienia
INSERT INTO Orders (ServiceVariantID, UserDataID, AddressID, OrderDate, DeliveryDate)
VALUES (2, 9, 9, '2019-10-29', '2019-11-03');

INSERT INTO Orders (ServiceVariantID, UserDataID, AddressID, OrderDate, DeliveryDate)
VALUES (9, 8, 8, '2019-09-13', '2019-09-14');

INSERT INTO Orders (ServiceVariantID, UserDataID, AddressID, OrderDate, DeliveryDate)
VALUES (1, 5, 4, '2019-02-02', '2019-02-03');

--oceny
INSERT INTO Rates (CompanyDataID, UserDataID, Rate, Description)
VALUES (3, 9, 4, 'Wprowadzcie pizze do oferty! Poza tym super');

INSERT INTO Rates (CompanyDataID, UserDataID, Rate, Description)
VALUES (4, 8, 3, 'Miod nie jest weganski!!! Ale po tej jednej wpadce ok');

INSERT INTO Rates (CompanyDataID, UserDataID, Rate, Description)
VALUES (2, 5, 5, 'Mniam');

--kategorie
--1
INSERT INTO Categories (CategoryName) VALUES ('Wegetariańskie');
--2
INSERT INTO Categories (CategoryName) VALUES ('Wegańskie');
--3
INSERT INTO Categories (CategoryName) VALUES ('Bez glutenu');
--4
INSERT INTO Categories (CategoryName) VALUES ('Okazjonalne');

INSERT INTO ServiceCategory VALUES (1, 4);
INSERT INTO ServiceCategory VALUES (2, 4);
INSERT INTO ServiceCategory VALUES (3, 3);
INSERT INTO ServiceCategory VALUES (4, 1);
INSERT INTO ServiceCategory VALUES (5, 2);

--wiadommości
--1
INSERT INTO Messages (Sender, Receiver, Subject, Description, DateSent)
VALUES (8, 4, 'Numer dostepowy', 'Moga panstwo wejsc do budynku kodem 98#1212, pozdrawiam dkj', '2019-10-31 12:00:12');

--2
INSERT INTO Messages (Sender, Receiver, Subject, Description, DateSent)
VALUES (7, 2, 'Zapytanie', 'Czy maja panstwo w ofercie dania bezglutenowe?', '2019-11-09 8:41:29');
