--
-- Table structure for table `T_ARAP_ITEM`
--

DROP TABLE IF EXISTS `T_ARAP_ITEM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `T_ARAP_ITEM` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `modificationTime` datetime DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_CARD_POLICY`
--

DROP TABLE IF EXISTS `T_CARD_POLICY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `T_CARD_POLICY` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `modificationTime` datetime DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `cardNumber` varchar(255) DEFAULT NULL,
  `channelCode` varchar(255) DEFAULT NULL,
  `content` longtext,
  `expiredDate` datetime DEFAULT NULL,
  `isActive` bit(1) DEFAULT NULL,
  `productCode` varchar(255) DEFAULT NULL,
  `sku` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_CUSTOMER`
--

DROP TABLE IF EXISTS `T_CUSTOMER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `T_CUSTOMER` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `modificationTime` datetime DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` longtext,
  `idNumber` varchar(255) DEFAULT NULL,
  `idType` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_DECISION_TABLE`
--

DROP TABLE IF EXISTS `T_DECISION_TABLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `T_DECISION_TABLE` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `modificationTime` datetime DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` longtext,
  `effectiveDate` datetime DEFAULT NULL,
  `expiredDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_ENDORSEMENT`
--

DROP TABLE IF EXISTS `T_ENDORSEMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `T_ENDORSEMENT` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `modificationTime` datetime DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `applicationDate` datetime DEFAULT NULL,
  `applicationName` varchar(255) DEFAULT NULL,
  `applyType` varchar(255) DEFAULT NULL,
  `channelCode` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` longtext,
  `effectiveDate` datetime DEFAULT NULL,
  `endorsementNumber` varchar(255) DEFAULT NULL,
  `issueDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `policyNumber` varchar(255) DEFAULT NULL,
  `premium` decimal(19,2) DEFAULT NULL,
  `productCode` varchar(255) DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  `statusCode` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `wording` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_FACTOR`
--

DROP TABLE IF EXISTS `T_FACTOR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `T_FACTOR` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `modificationTime` datetime DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` longtext,
  `dataType` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `validationExpress` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_NUMBERING_SEQ`
--

DROP TABLE IF EXISTS `T_NUMBERING_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
 SET character_set_client = utf8mb4 ;
CREATE TABLE `T_ORDER` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `modificationTime` datetime DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_POLICY`
--

DROP TABLE IF EXISTS `T_POLICY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `T_POLICY` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `modificationTime` datetime DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_POLICY_INDEX`
--

DROP TABLE IF EXISTS `T_POLICY_INDEX`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `T_POLICY_INDEX` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `modificationTime` datetime DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `app` decimal(19,2) DEFAULT NULL,
  `businessOrgan` varchar(255) DEFAULT NULL,
  `businessSource` varchar(255) DEFAULT NULL,
  `channelCode` varchar(255) DEFAULT NULL,
  `contractStatusCode` varchar(255) DEFAULT NULL,
  `effectiveDate` datetime DEFAULT NULL,
  `expiredDate` datetime DEFAULT NULL,
  `extReferenceNumber` varchar(255) DEFAULT NULL,
  `issueDate` datetime DEFAULT NULL,
  `limitAmount` decimal(19,2) DEFAULT NULL,
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
  `proposalDate` datetime DEFAULT NULL,
  `proposalNumber` varchar(255) DEFAULT NULL,
  `quotationNumber` varchar(255) DEFAULT NULL,
  `sgp` decimal(19,2) DEFAULT NULL,
  `snp` decimal(19,2) DEFAULT NULL,
  `terminalDate` datetime DEFAULT NULL,
  `terminalReasonCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_POLICY_LOG`
--

DROP TABLE IF EXISTS `T_POLICY_LOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `T_POLICY_LOG` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `modificationTime` datetime DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `content` longtext,
  `endoId` varchar(255) DEFAULT NULL,
  `logType` varchar(255) DEFAULT NULL,
  `policyNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_POLICY_MATERIALS`
--

DROP TABLE IF EXISTS `T_POLICY_MATERIALS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `T_POLICY_MATERIALS` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `modificationTime` datetime DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `format` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_PRODUCT`
--

DROP TABLE IF EXISTS `T_PRODUCT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `T_PRODUCT` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `modificationTime` datetime DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` longtext,
  `effectiveDate` datetime DEFAULT NULL,
  `expiredDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_QUOTATION`
--

DROP TABLE IF EXISTS `T_QUOTATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `T_QUOTATION` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `modificationTime` datetime DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `T_SKU`
--

DROP TABLE IF EXISTS `T_SKU`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `T_SKU` (
  `uuid` varchar(255) NOT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `creationTime` datetime DEFAULT NULL,
  `modificationTime` datetime DEFAULT NULL,
  `modifiedBy` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` longtext,
  `effectiveDate` datetime DEFAULT NULL,
  `expiredDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-29 19:38:26
