# Spring-boot-coin
JPA & UnitTest, base on SpringBoot & H2.

## Environments
- JDK: 1.8
- H2
- SpringBoot

## sql
table schema:
```
CREATE TABLE Currency (
currencyId INTEGER PRIMARY KEY AUTO_INCREMENT,
currencyName VARCHAR(255) NOT NULL,
currencyCode VARCHAR(255) NOT NULL,
symbol VARCHAR(255) NOT NULL,
description VARCHAR(255) NOT NULL
);
```
```
CREATE TABLE CurrencyRate (
  currencyRateId BIGINT NOT NULL AUTO_INCREMENT,
  currencyId BIGINT NOT NULL,
  rate DOUBLE NOT NULL,
  updateTime VARCHAR(255) NOT NULL,
  PRIMARY KEY (currencyRateId),
  UNIQUE KEY UKGKPXJUF8XC3XD517K7XX4V9ME_INDEX_F (updateTime, currencyId)
);
```