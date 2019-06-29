-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: stargate
-- ------------------------------------------------------
-- Server version	5.7.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `T_ARAP_ITEM`
--

DROP TABLE IF EXISTS `T_ARAP_ITEM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_ARAP_ITEM` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `amount` decimal(19,2) DEFAULT NULL,
  `balance` decimal(19,2) DEFAULT NULL,
  `content` longtext,
  `currency` varchar(255) DEFAULT NULL,
  `dueDate` datetime DEFAULT NULL,
  `feeCode` varchar(255) DEFAULT NULL,
  `refBizNumber` varchar(255) DEFAULT NULL,
  `refExtNumber` varchar(255) DEFAULT NULL,
  `transDate` datetime DEFAULT NULL,
  `transType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_CARD_POLICY`
--

DROP TABLE IF EXISTS `T_CARD_POLICY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_CARD_POLICY` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `cardNumber` varchar(255) DEFAULT NULL,
  `channelCode` varchar(255) DEFAULT NULL,
  `content` longtext,
  `expiredDate` datetime DEFAULT NULL,
  `isActive` bit(1) DEFAULT NULL,
  `productCode` varchar(255) DEFAULT NULL,
  `sku` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_CLAIM`
--

DROP TABLE IF EXISTS `T_CLAIM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_CLAIM` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `accidentTime` datetime DEFAULT NULL,
  `claimNumber` varchar(255) DEFAULT NULL,
  `claimTime` datetime DEFAULT NULL,
  `content` longtext,
  `policyNumber` varchar(255) DEFAULT NULL,
  `productCode` varchar(255) DEFAULT NULL,
  `statusCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_CLAIM_INDEX`
--

DROP TABLE IF EXISTS `T_CLAIM_INDEX`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_CLAIM_INDEX` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `accidentTime` datetime DEFAULT NULL,
  `claimAmount` decimal(19,2) DEFAULT NULL,
  `claimNumber` varchar(255) DEFAULT NULL,
  `claimTime` datetime DEFAULT NULL,
  `claimant` varchar(255) DEFAULT NULL,
  `paymentAmount` decimal(19,2) DEFAULT NULL,
  `policyNumber` varchar(255) DEFAULT NULL,
  `productCode` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `closeDate` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `T_CODE_TABLE`
--

DROP TABLE IF EXISTS `T_CODE_TABLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_CODE_TABLE` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` longtext,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_CUSTOMER`
--

DROP TABLE IF EXISTS `T_CUSTOMER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_CUSTOMER` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` longtext,
  `idNumber` varchar(255) DEFAULT NULL,
  `idType` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_DECISION_TABLE`
--

DROP TABLE IF EXISTS `T_DECISION_TABLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_DECISION_TABLE` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` longtext,
  `effectiveDate` datetime DEFAULT NULL,
  `expiredDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_ENDORSEMENT`
--

DROP TABLE IF EXISTS `T_ENDORSEMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_ENDORSEMENT` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `applicationDate` datetime DEFAULT NULL,
  `applyType` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` longtext,
  `effectiveDate` datetime DEFAULT NULL,
  `endorsementNumber` varchar(255) DEFAULT NULL,
  `issueDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `policyNumber` varchar(255) DEFAULT NULL,
  `productCode` varchar(255) DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  `statusCode` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `wording` varchar(255) DEFAULT NULL,
  `premium` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_ENDORSEMENTREGIST_LOG`
--

DROP TABLE IF EXISTS `T_ENDORSEMENTREGIST_LOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_ENDORSEMENTREGIST_LOG` (
  `transactionNo` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `content` longtext,
  `endorsementNumber` varchar(255) DEFAULT NULL,
  `makeDate` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`transactionNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_FACTOR`
--

DROP TABLE IF EXISTS `T_FACTOR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_FACTOR` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` longtext,
  `dataType` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `validationExpress` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `T_NOTICE_OF_LOSS`
--

DROP TABLE IF EXISTS `T_NOTICE_OF_LOSS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_NOTICE_OF_LOSS` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `accidentCause` varchar(255) DEFAULT NULL,
  `accidentDescription` varchar(255) DEFAULT NULL,
  `accidentTime` datetime DEFAULT NULL,
  `content` longtext,
  `noticeNumber` varchar(255) DEFAULT NULL,
  `noticeTime` datetime DEFAULT NULL,
  `policyNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_NUMBERING_SEQ`
--

DROP TABLE IF EXISTS `T_NUMBERING_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_NUMBERING_SEQ` (
  `NUMBERING_TYPE` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `NUMBERING` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `SEQUENCE` bigint(20) NOT NULL,
  PRIMARY KEY (`NUMBERING_TYPE`,`NUMBERING`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `T_ORDER`
--

DROP TABLE IF EXISTS `T_ORDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_ORDER` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `amount` decimal(19,2) DEFAULT NULL,
  `businessOrgan` varchar(255) DEFAULT NULL,
  `channelCode` varchar(255) DEFAULT NULL,
  `content` longtext,
  `customerName` varchar(255) DEFAULT NULL,
  `deliveryDate` datetime DEFAULT NULL,
  `orderNumber` varchar(255) DEFAULT NULL,
  `orderingTime` datetime DEFAULT NULL,
  `statusCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_ORGANIZATION`
--

DROP TABLE IF EXISTS `T_ORGANIZATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_ORGANIZATION` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `abbName` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `contactPersonName` varchar(255) DEFAULT NULL,
  `content` longtext,
  `fax` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parentOrg` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `registrationDate` datetime DEFAULT NULL,
  `registrationNo` varchar(255) DEFAULT NULL,
  `valid` bit(1) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_PERMISSION`
--

DROP TABLE IF EXISTS `T_PERMISSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_PERMISSION` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `available` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_POLICY`
--

DROP TABLE IF EXISTS `T_POLICY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_POLICY` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `businessOrgan` varchar(255) DEFAULT NULL,
  `businessSource` varchar(255) DEFAULT NULL,
  `channelCode` varchar(255) DEFAULT NULL,
  `content` longtext,
  `contractStatusCode` varchar(255) DEFAULT NULL,
  `effectiveDate` datetime DEFAULT NULL,
  `expiredDate` datetime DEFAULT NULL,
  `extReferenceNumber` varchar(255) DEFAULT NULL,
  `issueDate` datetime DEFAULT NULL,
  `policyNumber` varchar(255) DEFAULT NULL,
  `productCode` varchar(255) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `proposalDate` datetime DEFAULT NULL,
  `proposalNumber` varchar(255) DEFAULT NULL,
  `quotationDate` datetime DEFAULT NULL,
  `quotationNumber` varchar(255) DEFAULT NULL,
  `terminalReasonCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_POLICY_INDEX`
--

DROP TABLE IF EXISTS `T_POLICY_INDEX`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_POLICY_INDEX` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `app` decimal(19,2) DEFAULT NULL,
  `sgp` decimal(19,2) DEFAULT NULL,
  `snp` decimal(19,2) DEFAULT NULL,
  `limitAmount` decimal(19,2) DEFAULT NULL,
  `businessOrgan` varchar(255) DEFAULT NULL,
  `businessSource` varchar(255) DEFAULT NULL,
  `contractStatusCode` varchar(255) DEFAULT NULL,
  `effectiveDate` datetime DEFAULT NULL,
  `expiredDate` datetime DEFAULT NULL,
  `extReferenceNumber` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `policyHolderIdNumber` varchar(255) DEFAULT NULL,
  `policyHolderIdType` varchar(255) DEFAULT NULL,
  `policyHolderName` varchar(255) DEFAULT NULL,
  `policyInsuredIdNumber` varchar(255) DEFAULT NULL,
  `policyInsuredIdType` varchar(255) DEFAULT NULL,
  `policyInsuredName` varchar(255) DEFAULT NULL,
  `policyNumber` varchar(255) DEFAULT NULL,
  `productCode` varchar(255) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `proposalNumber` varchar(255) DEFAULT NULL,
  `quotationNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_POLICY_LOG`
--

DROP TABLE IF EXISTS `T_POLICY_LOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_POLICY_LOG` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `content` longtext,
  `endoId` varchar(255) DEFAULT NULL,
  `logType` varchar(255) DEFAULT NULL,
  `policyNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_PRODUCT`
--

DROP TABLE IF EXISTS `T_PRODUCT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_PRODUCT` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` longtext,
  `effectiveDate` datetime DEFAULT NULL,
  `expiredDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_QUOTATION`
--

DROP TABLE IF EXISTS `T_QUOTATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_QUOTATION` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `businessOrgan` varchar(255) DEFAULT NULL,
  `businessSource` varchar(255) DEFAULT NULL,
  `channelCode` varchar(255) DEFAULT NULL,
  `content` longtext,
  `effectiveDate` datetime DEFAULT NULL,
  `expiredDate` datetime DEFAULT NULL,
  `extReferenceNumber` varchar(255) DEFAULT NULL,
  `productCode` varchar(255) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `proposalDate` datetime DEFAULT NULL,
  `quotationDate` datetime DEFAULT NULL,
  `quotationNumber` varchar(255) DEFAULT NULL,
  `quotationStatusCode` varchar(255) DEFAULT NULL,
  `rejectReason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `T_ROLE`
--

DROP TABLE IF EXISTS `T_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_ROLE` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `available` bit(1) DEFAULT NULL,
  `content` longtext,
  `description` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_SKU`
--

DROP TABLE IF EXISTS `T_SKU`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_SKU` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` longtext,
  `effectiveDate` datetime DEFAULT NULL,
  `expiredDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_USER`
--

DROP TABLE IF EXISTS `T_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_USER` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `content` longtext,
  `email` varchar(255) DEFAULT NULL,
  `mobilePhone` varchar(255) DEFAULT NULL,
  `nickName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `status` VARCHAR(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_USER_AUTHENTICATION`
--

DROP TABLE IF EXISTS `T_USER_AUTHENTICATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_USER_AUTHENTICATION` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `expiredDate` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_USER_OAUTH`
--

DROP TABLE IF EXISTS `T_USER_OAUTH`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_USER_OAUTH` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime(3) DEFAULT NULL,
  `modificationTime` datetime(3) DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `expiredDate` datetime DEFAULT NULL,
  `oauthId` varchar(255) DEFAULT NULL,
  `oauthName` varchar(255) DEFAULT NULL,
  `oauthToken` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-05 11:14:17
