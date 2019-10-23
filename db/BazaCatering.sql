use ProjektCatering

CREATE TABLE AccountType (
AccountTypeID INT IDENTITY(1,1),
Name NVARCHAR(50),
CONSTRAINT AccountTypePrimaryKey PRIMARY KEY(AccountTypeID)
)

CREATE TABLE Accounts (
AccountID INT IDENTITY(1,1),
AccountName NVARCHAR(50),
AccountTypeID INT,
Email NVARCHAR(50),
Password NVARCHAR(50),
CONSTRAINT AccountsPrimaryKey PRIMARY KEY(AccountID),
CONSTRAINT AccountsAccountTypeForeignKey FOREIGN KEY(AccountTypeID)
REFERENCES AccountType(AccountTypeID)
)

CREATE TABLE UserData (
UserDataID INT,
Name NVARCHAR(50),
LastName NVARCHAR(50),
CONSTRAINT UserDataPrimaryKey PRIMARY KEY(UserDataID),
CONSTRAINT UserDataAccountsForeignKey FOREIGN KEY(UserDataID)
REFERENCES Accounts(AccountID)
)

CREATE TABLE CompanyData (
CompanyDataID INT,
Name NVARCHAR(50),
NIP NVARCHAR(50),
REGON NVARCHAR(50),
WebsiteAddress NVARCHAR(50),
AverageRating REAL,
CONSTRAINT CompanyDataPrimaryKey PRIMARY KEY(CompanyDataID),
CONSTRAINT CompanyDataAccountsForeignKey FOREIGN KEY(CompanyDataID)
REFERENCES Accounts(AccountID)
)

CREATE TABLE Address (
AddressID INT IDENTITY(1,1),
AccountID INT,
City NVARCHAR(50),
Street NVARCHAR(50),
PostCode NVARCHAR(6),
LocalNumber NVARCHAR(10),
CONSTRAINT AddressPrimaryKey PRIMARY KEY(AddressID),
CONSTRAINT AddressAccountForeignKey FOREIGN KEY(AccountID)
REFERENCES Accounts(AccountID)
)

CREATE TABLE ServiceType (
ServiceTypeID INT IDENTITY(1,1),
ServiceTypeName NVARCHAR(50),
CONSTRAINT ServiceTypePrimaryKey PRIMARY KEY(ServiceTypeID)
)

CREATE TABLE Services (
ServiceID INT IDENTITY(1,1),
ServiceTypeID INT NOT NULL,
CompanyDataID INT NOT NULL,
ServiceName NVARCHAR(50),
Description TEXT,
CONSTRAINT ServicesPrimaryKey PRIMARY KEY(ServiceID),
CONSTRAINT ServicesServiceTypeForeignKey FOREIGN KEY(ServiceTypeID)
REFERENCES ServiceType(ServiceTypeID),
CONSTRAINT ServicesCompanyDataForeignKey FOREIGN KEY(CompanyDataID)
REFERENCES CompanyData(CompanyDataID)
)

CREATE TABLE Favorites(
UserDataID INT NOT NULL,
ServiceID INT NOT NULL,
CONSTRAINT FavoritesUserDataForeignKey FOREIGN KEY(UserDataID)
REFERENCES UserData(UserDataID),
CONSTRAINT FavoritesServicesForeignKey FOREIGN KEY(ServiceID)
REFERENCES Services(ServiceID)
)

CREATE TABLE ServiceVariant (
ServiceVariantID INT IDENTITY(1,1),
ServiceID INT NOT NULL,
Calories INT,
PricePerPerson REAL,
DayNumber INT,
DeliveryTime TIME,
CONSTRAINT ServiceVariantPrimaryKey PRIMARY KEY(ServiceVariantID),
CONSTRAINT ServiceVariantServicesForeignKey FOREIGN KEY(ServiceID)
REFERENCES Services(ServiceID)
)

CREATE TABLE Orders(
OrderID INT IDENTITY(1,1),
ServiceVariantID INT NOT NULL,
UserDataID INT NOT NULL,
AddressID INT NOT NULL,
OrderDate DATE,
DeliveryDate DATE,
CONSTRAINT OrderPrimaryKey PRIMARY KEY(OrderID),
CONSTRAINT OrderServiceVariantForeignKey FOREIGN KEY(ServiceVariantID)
REFERENCES ServiceVariant(ServiceVariantID),
CONSTRAINT OrderUserDataForeignKey FOREIGN KEY(UserDataID)
REFERENCES UserData(UserDataID),
CONSTRAINT OrderAddressForeignKey FOREIGN KEY(AddressID)
REFERENCES Address(AddressID)
)

CREATE TABLE Rates(
RateID INT IDENTITY(1,1),
CompanyDataID INT NOT NULL,
UserDataID INT NOT NULL,
Rate INT,
Description TEXT,
CONSTRAINT RatesPrimaryKey PRIMARY KEY(RateID),
CONSTRAINT RatesCompanyData FOREIGN KEY(CompanyDataID)
REFERENCES CompanyData(CompanyDataID),
CONSTRAINT RatesUserDataForeignKey FOREIGN KEY(UserDataID)
REFERENCES UserData(UserDataID)
)

CREATE TABLE Categories(
CategoryID INT IDENTITY(1,1),
CategoryName NVARCHAR(50),
CONSTRAINT CategoriesPrimaryKey PRIMARY KEY(CategoryID)
)

CREATE TABLE ServiceCategory(
ServiceID INT NOT NULL,
CategoryID INT NOT NULL,
CONSTRAINT ServiceCategoryCategoriesForeignKey FOREIGN KEY(CategoryID)
REFERENCES Categories(CategoryID),
CONSTRAINT ServiceCategoryServicesForeignKey FOREIGN KEY(ServiceID)
REFERENCES Services(ServiceID)
)


CREATE TABLE Messages(
MessageID INT IDENTITY(1,1),
CompanyDataID INT NOT NULL,
UserDataID INT NOT NULL,
Subject TEXT,
Description TEXT,
CONSTRAINT MessagesPrimaryKey PRIMARY KEY(MessageID),
CONSTRAINT MessagesCompanyData FOREIGN KEY(CompanyDataID)
REFERENCES CompanyData(CompanyDataID),
CONSTRAINT MessagesUserDataForeignKey FOREIGN KEY(UserDataID)
REFERENCES UserData(UserDataID)
)