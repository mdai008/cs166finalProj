--entities, 6 total
DROP TABLE IF EXISTS Store CASCADE;
DROP TABLE IF EXISTS Product CASCADE;
DROP TABLE IF EXISTS User CASCADE;
DROP TABLE IF EXISTS Customer CASCADE;
DROP TABLE IF EXISTS Manager CASCADE;
DROP TABLE IF EXISTS Warehouse CASCADE;

--relationships, 6 total
--4 many to many: orders, update, requests, supplies
--2 one to many: has(aggregate), manages

DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS updates CASCADE;
DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS supplies CASCADE;

DROP TABLE IF EXISTS has CASCADE; --aggregate
DROP TABLE IF EXISTS manages CASCADE;



--entities
CREATE TABLE Store (
    StoreID INTEGER NOT NULL,
    Name CHAR(30) NOT NULL,
    Latitude DECIMAL(8,6) NOT NULL,
    Longitude DECIMAL(9,6) NOT NULL,
    DateEstablished DATE,
    PRIMARY KEY(StoreID),
    UNIQUE(StoreID)
);

CREATE TABLE Product (
    ProductName CHAR(30) NOT NULL,
    NumberOfUnits INTEGER NOT NULL,
    PricePerUnit INTEGER NOT NULL,
    Description CHAR(100),
    ImageURL CHAR(30)
);

CREATE TABLE User (
    UserID INTEGER NOT NULL,
    Password CHAR(11) NOT NULL,
    Name CHAR(50) NOT NULL,
    Email CHAR(50),
    PRIMARY KEY(UserID),
    UNIQUE(UserID)
);

CREATE TABLE Customer (
    UserID INTEGER NOT NULL,
    CreditScore INTEGER,
    Latitude DECIMAL(8,6) NOT NULL,
    Longitude DECIMAL(9,6) NOT NULL,
    PRIMARY KEY(UserID),
    UNIQUE(UserID),
    FOREIGN KEY(UserID) REFERENCES User(UserID)
);

CREATE TABLE Manager (
    UserID INTEGER NOT NULL,
    Degree CHAR(20),
    Salary INTEGER NOT NULL,
    PRIMARY KEY(UserID),
    UNIQUE(UserID),
    FOREIGN KEY(UserID) REFERENCES User(UserID)
);

CREATE TABLE Warehouse (
    WarehouseID INTEGER NOT NULL,
    Area DECIMAL(8,6) NOT NULL,
    Latitude DECIMAL(8,6) NOT NULL,
    Longitude DECIMAL(9,6) NOT NULL,
    PRIMARY KEY(WarehouseID),
    UNIQUE(WarehouseID)
);

--relationships
--orders, update, requests, supplies
CREATE TABLE orders (
    UserID INTEGER NOT NULL,
    StoreID INTEGER NOT NULL,
    ProductName CHAR(30) NOT NULL,
    unitsOrdered INTEGER,
    orderDate DATE,
    PRIMARY KEY(UserID, StoreID, ProductName),
    FOREIGN KEY(UserID) REFERENCES Customer(UserID),
    FOREIGN KEY(StoreID) REFERENCES Store(StoreID),
    FOREIGN KEY(ProductName) REFERENCES Product(ProductName)
);

CREATE TABLE updates (
    UserID INTEGER NOT NULL,
    StoreID INTEGER NOT NULL,
    ProductName CHAR(30) NOT NULL,
    PRIMARY KEY(UserID, StoreID, ProductName),
    FOREIGN KEY(UserID) REFERENCES Manager(UserID),
    FOREIGN KEY(StoreID) REFERENCES Store(StoreID),
    FOREIGN KEY(ProductName) REFERENCES Product(ProductName)
);

CREATE TABLE requests (
    UserID INTEGER NOT NULL,
    StoreID INTEGER NOT NULL,
    WarehouseID INTEGER NOT NULL,
    ProductName CHAR(30) NOT NULL,
    unitsReq INTEGER,
    PRIMARY KEY(UserID, StoreID, WarehouseID, ProductName),
    FOREIGN KEY(UserID) REFERENCES Manager(UserID),
    FOREIGN KEY(StoreID) REFERENCES Store(StoreID),
    FOREIGN KEY(WarehouseID) REFERENCES Warehouse(WarehouseID),
    FOREIGN KEY(ProductName) REFERENCES Product(ProductName)
);

CREATE TABLE supplies (
    WarehouseID INTEGER NOT NULL,
    ProductName CHAR(30) NOT NULL,
    StoreID INTEGER NOT NULL,
    PRIMARY KEY(WarehouseID, StoreID, ProductName),
    FOREIGN KEY(WarehouseID) REFERENCES Warehouse(WarehouseID),
    FOREIGN KEY(StoreID) REFERENCES Store(StoreID),
    FOREIGN KEY(ProductName) REFERENCES Product(ProductName)
);

--has, manages
CREATE TABLE has (
    StoreID INTEGER NOT NULL,
    ProductName CHAR(30) NOT NULL,
    PRIMARY KEY(StoreID),
    FOREIGN KEY(StoreID) REFERENCES Store(StoreID),
    FOREIGN KEY(ProductName) REFERENCES Product(ProductName)
);

CREATE TABLE manages (
    UserID INTEGER NOT NULL,
    StoreID INTEGER NOT NULL,
    PRIMARY KEY(UserID),
    FOREIGN KEY(UserID) REFERENCES Manager(UserID),
    FOREIGN KEY(StoreID) REFERENCES Store(StoreID)
);