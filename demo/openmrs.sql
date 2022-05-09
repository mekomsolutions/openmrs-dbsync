-- MySQL dump 10.13  Distrib 8.0.24, for macos11 (x86_64)
--
-- Host: 127.0.0.1    Database: openmrs
-- ------------------------------------------------------
-- Server version	5.7.31-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `allergy`
--

DROP TABLE IF EXISTS `allergy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `allergy` (
  `allergy_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) NOT NULL,
  `severity_concept_id` int(11) DEFAULT NULL,
  `coded_allergen` int(11) NOT NULL,
  `non_coded_allergen` varchar(255) DEFAULT NULL,
  `allergen_type` varchar(50) NOT NULL,
  `comments` varchar(1024) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '1',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) DEFAULT NULL,
  `form_namespace_and_path` varchar(255) DEFAULT NULL,
  `encounter_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`allergy_id`),
  UNIQUE KEY `allergy_id` (`allergy_id`),
  KEY `allergy_patient_id_fk` (`patient_id`),
  KEY `allergy_coded_allergen_fk` (`coded_allergen`),
  KEY `allergy_severity_concept_id_fk` (`severity_concept_id`),
  KEY `allergy_creator_fk` (`creator`),
  KEY `allergy_changed_by_fk` (`changed_by`),
  KEY `allergy_voided_by_fk` (`voided_by`),
  KEY `allergy_encounter_id_fk` (`encounter_id`),
  CONSTRAINT `allergy_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `allergy_coded_allergen_fk` FOREIGN KEY (`coded_allergen`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `allergy_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `allergy_encounter_id_fk` FOREIGN KEY (`encounter_id`) REFERENCES `encounter` (`encounter_id`),
  CONSTRAINT `allergy_patient_id_fk` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`),
  CONSTRAINT `allergy_severity_concept_id_fk` FOREIGN KEY (`severity_concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `allergy_voided_by_fk` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allergy`
--


--
-- Table structure for table `allergy_reaction`
--

DROP TABLE IF EXISTS `allergy_reaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `allergy_reaction` (
  `allergy_reaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `allergy_id` int(11) NOT NULL,
  `reaction_concept_id` int(11) NOT NULL,
  `reaction_non_coded` varchar(255) DEFAULT NULL,
  `uuid` char(38) DEFAULT NULL,
  PRIMARY KEY (`allergy_reaction_id`),
  UNIQUE KEY `allergy_reaction_id` (`allergy_reaction_id`),
  KEY `allergy_reaction_allergy_id_fk` (`allergy_id`),
  KEY `allergy_reaction_reaction_concept_id_fk` (`reaction_concept_id`),
  CONSTRAINT `allergy_reaction_allergy_id_fk` FOREIGN KEY (`allergy_id`) REFERENCES `allergy` (`allergy_id`),
  CONSTRAINT `allergy_reaction_reaction_concept_id_fk` FOREIGN KEY (`reaction_concept_id`) REFERENCES `concept` (`concept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allergy_reaction`
--


--
-- Table structure for table `care_setting`
--

DROP TABLE IF EXISTS `care_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `care_setting` (
  `care_setting_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `care_setting_type` varchar(50) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`care_setting_id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `care_setting_creator` (`creator`),
  KEY `care_setting_retired_by` (`retired_by`),
  KEY `care_setting_changed_by` (`changed_by`),
  CONSTRAINT `care_setting_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `care_setting_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `care_setting_retired_by` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `care_setting`
--

INSERT INTO `care_setting` VALUES (1,'Outpatient','Out-patient care setting','OUTPATIENT',1,'2013-12-27 00:00:00',0,NULL,NULL,NULL,NULL,NULL,'6f0c9a92-6f24-11e3-af88-005056821db0'),(2,'Inpatient','In-patient care setting','INPATIENT',1,'2013-12-27 00:00:00',0,NULL,NULL,NULL,NULL,NULL,'c365e560-c3ec-11e3-9c1a-0800200c9a66');

--
-- Table structure for table `clob_datatype_storage`
--

DROP TABLE IF EXISTS `clob_datatype_storage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clob_datatype_storage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` char(38) NOT NULL,
  `value` longtext NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `clob_datatype_storage_uuid_index` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clob_datatype_storage`
--


--
-- Table structure for table `cohort`
--

DROP TABLE IF EXISTS `cohort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cohort` (
  `cohort_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`cohort_id`),
  UNIQUE KEY `cohort_uuid_index` (`uuid`),
  KEY `user_who_changed_cohort` (`changed_by`),
  KEY `cohort_creator` (`creator`),
  KEY `user_who_voided_cohort` (`voided_by`),
  CONSTRAINT `cohort_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_changed_cohort` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_voided_cohort` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cohort`
--


--
-- Table structure for table `cohort_member`
--

DROP TABLE IF EXISTS `cohort_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cohort_member` (
  `cohort_id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `cohort_member_id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`cohort_member_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `cohort_member_creator` (`creator`),
  KEY `parent_cohort` (`cohort_id`),
  KEY `member_patient` (`patient_id`),
  CONSTRAINT `cohort_member_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `member_patient` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`),
  CONSTRAINT `parent_cohort` FOREIGN KEY (`cohort_id`) REFERENCES `cohort` (`cohort_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cohort_member`
--


--
-- Table structure for table `concept`
--

DROP TABLE IF EXISTS `concept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept` (
  `concept_id` int(11) NOT NULL AUTO_INCREMENT,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `short_name` varchar(255) DEFAULT NULL,
  `description` text,
  `form_text` text,
  `datatype_id` int(11) NOT NULL DEFAULT '0',
  `class_id` int(11) NOT NULL DEFAULT '0',
  `is_set` tinyint(1) NOT NULL DEFAULT '0',
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`concept_id`),
  UNIQUE KEY `concept_uuid_index` (`uuid`),
  KEY `user_who_changed_concept` (`changed_by`),
  KEY `concept_classes` (`class_id`),
  KEY `concept_creator` (`creator`),
  KEY `concept_datatypes` (`datatype_id`),
  KEY `user_who_retired_concept` (`retired_by`),
  CONSTRAINT `concept_classes` FOREIGN KEY (`class_id`) REFERENCES `concept_class` (`concept_class_id`),
  CONSTRAINT `concept_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `concept_datatypes` FOREIGN KEY (`datatype_id`) REFERENCES `concept_datatype` (`concept_datatype_id`),
  CONSTRAINT `user_who_changed_concept` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_retired_concept` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept`
--

INSERT INTO `concept` VALUES (1,0,'','',NULL,4,11,0,1,'2021-05-09 07:08:37',NULL,NULL,NULL,NULL,NULL,NULL,'81ad92a2-2e1b-4212-bc60-e434e465a905'),(2,0,'','',NULL,4,11,0,1,'2021-05-09 07:08:37',NULL,NULL,NULL,NULL,NULL,NULL,'b4c474e4-f9b7-4d4d-8e1e-d8eb0363cb16');

--
-- Table structure for table `concept_answer`
--

DROP TABLE IF EXISTS `concept_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_answer` (
  `concept_answer_id` int(11) NOT NULL AUTO_INCREMENT,
  `concept_id` int(11) NOT NULL DEFAULT '0',
  `answer_concept` int(11) DEFAULT NULL,
  `answer_drug` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `sort_weight` double DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`concept_answer_id`),
  UNIQUE KEY `concept_answer_uuid_index` (`uuid`),
  KEY `answer` (`answer_concept`),
  KEY `answers_for_concept` (`concept_id`),
  KEY `answer_creator` (`creator`),
  KEY `answer_answer_drug_fk` (`answer_drug`),
  CONSTRAINT `answer` FOREIGN KEY (`answer_concept`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `answer_answer_drug_fk` FOREIGN KEY (`answer_drug`) REFERENCES `drug` (`drug_id`),
  CONSTRAINT `answer_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `answers_for_concept` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_answer`
--


--
-- Table structure for table `concept_attribute`
--

DROP TABLE IF EXISTS `concept_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_attribute` (
  `concept_attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `concept_id` int(11) NOT NULL,
  `attribute_type_id` int(11) NOT NULL,
  `value_reference` text NOT NULL,
  `uuid` char(38) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`concept_attribute_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `concept_attribute_concept_fk` (`concept_id`),
  KEY `concept_attribute_attribute_type_id_fk` (`attribute_type_id`),
  KEY `concept_attribute_creator_fk` (`creator`),
  KEY `concept_attribute_changed_by_fk` (`changed_by`),
  KEY `concept_attribute_voided_by_fk` (`voided_by`),
  CONSTRAINT `concept_attribute_attribute_type_id_fk` FOREIGN KEY (`attribute_type_id`) REFERENCES `concept_attribute_type` (`concept_attribute_type_id`),
  CONSTRAINT `concept_attribute_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `concept_attribute_concept_fk` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `concept_attribute_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `concept_attribute_voided_by_fk` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_attribute`
--


--
-- Table structure for table `concept_attribute_type`
--

DROP TABLE IF EXISTS `concept_attribute_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_attribute_type` (
  `concept_attribute_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `datatype` varchar(255) DEFAULT NULL,
  `datatype_config` text,
  `preferred_handler` varchar(255) DEFAULT NULL,
  `handler_config` text,
  `min_occurs` int(11) NOT NULL,
  `max_occurs` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`concept_attribute_type_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `concept_attribute_type_creator_fk` (`creator`),
  KEY `concept_attribute_type_changed_by_fk` (`changed_by`),
  KEY `concept_attribute_type_retired_by_fk` (`retired_by`),
  CONSTRAINT `concept_attribute_type_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `concept_attribute_type_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `concept_attribute_type_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_attribute_type`
--


--
-- Table structure for table `concept_class`
--

DROP TABLE IF EXISTS `concept_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_class` (
  `concept_class_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `date_changed` datetime DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`concept_class_id`),
  UNIQUE KEY `concept_class_uuid_index` (`uuid`),
  KEY `concept_class_retired_status` (`retired`),
  KEY `concept_class_creator` (`creator`),
  KEY `user_who_retired_concept_class` (`retired_by`),
  KEY `concept_class_name_index` (`name`),
  KEY `concept_class_changed_by` (`changed_by`),
  CONSTRAINT `concept_class_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `concept_class_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_retired_concept_class` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_class`
--

INSERT INTO `concept_class` VALUES (1,'Test','Acq. during patient encounter (vitals, labs, etc.)',1,'2004-02-02 00:00:00',0,NULL,NULL,NULL,'8d4907b2-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(2,'Procedure','Describes a clinical procedure',1,'2004-03-02 00:00:00',0,NULL,NULL,NULL,'8d490bf4-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(3,'Drug','Drug',1,'2004-02-02 00:00:00',0,NULL,NULL,NULL,'8d490dfc-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(4,'Diagnosis','Conclusion drawn through findings',1,'2004-02-02 00:00:00',0,NULL,NULL,NULL,'8d4918b0-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(5,'Finding','Practitioner observation/finding',1,'2004-03-02 00:00:00',0,NULL,NULL,NULL,'8d491a9a-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(6,'Anatomy','Anatomic sites / descriptors',1,'2004-03-02 00:00:00',0,NULL,NULL,NULL,'8d491c7a-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(7,'Question','Question (eg, patient history, SF36 items)',1,'2004-03-02 00:00:00',0,NULL,NULL,NULL,'8d491e50-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(8,'LabSet','Term to describe laboratory sets',1,'2004-03-02 00:00:00',0,NULL,NULL,NULL,'8d492026-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(9,'MedSet','Term to describe medication sets',1,'2004-02-02 00:00:00',0,NULL,NULL,NULL,'8d4923b4-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(10,'ConvSet','Term to describe convenience sets',1,'2004-03-02 00:00:00',0,NULL,NULL,NULL,'8d492594-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(11,'Misc','Terms which don\'t fit other categories',1,'2004-03-02 00:00:00',0,NULL,NULL,NULL,'8d492774-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(12,'Symptom','Patient-reported observation',1,'2004-10-04 00:00:00',0,NULL,NULL,NULL,'8d492954-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(13,'Symptom/Finding','Observation that can be reported from patient or found on exam',1,'2004-10-04 00:00:00',0,NULL,NULL,NULL,'8d492b2a-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(14,'Specimen','Body or fluid specimen',1,'2004-12-02 00:00:00',0,NULL,NULL,NULL,'8d492d0a-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(15,'Misc Order','Orderable items which aren\'t tests or drugs',1,'2005-02-17 00:00:00',0,NULL,NULL,NULL,'8d492ee0-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(16,'Frequency','A class for order frequencies',1,'2014-03-06 00:00:00',0,NULL,NULL,NULL,'8e071bfe-520c-44c0-a89b-538e9129b42a',NULL,NULL);

--
-- Table structure for table `concept_complex`
--

DROP TABLE IF EXISTS `concept_complex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_complex` (
  `concept_id` int(11) NOT NULL,
  `handler` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`concept_id`),
  CONSTRAINT `concept_attributes` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_complex`
--


--
-- Table structure for table `concept_datatype`
--

DROP TABLE IF EXISTS `concept_datatype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_datatype` (
  `concept_datatype_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `hl7_abbreviation` varchar(3) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`concept_datatype_id`),
  UNIQUE KEY `concept_datatype_uuid_index` (`uuid`),
  KEY `concept_datatype_retired_status` (`retired`),
  KEY `concept_datatype_creator` (`creator`),
  KEY `user_who_retired_concept_datatype` (`retired_by`),
  KEY `concept_datatype_name_index` (`name`),
  CONSTRAINT `concept_datatype_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_retired_concept_datatype` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_datatype`
--

INSERT INTO `concept_datatype` VALUES (1,'Numeric','NM','Numeric value, including integer or float (e.g., creatinine, weight)',1,'2004-02-02 00:00:00',0,NULL,NULL,NULL,'8d4a4488-c2cc-11de-8d13-0010c6dffd0f'),(2,'Coded','CWE','Value determined by term dictionary lookup (i.e., term identifier)',1,'2004-02-02 00:00:00',0,NULL,NULL,NULL,'8d4a48b6-c2cc-11de-8d13-0010c6dffd0f'),(3,'Text','ST','Free text',1,'2004-02-02 00:00:00',0,NULL,NULL,NULL,'8d4a4ab4-c2cc-11de-8d13-0010c6dffd0f'),(4,'N/A','ZZ','Not associated with a datatype (e.g., term answers, sets)',1,'2004-02-02 00:00:00',0,NULL,NULL,NULL,'8d4a4c94-c2cc-11de-8d13-0010c6dffd0f'),(5,'Document','RP','Pointer to a binary or text-based document (e.g., clinical document, RTF, XML, EKG, image, etc.) stored in complex_obs table',1,'2004-04-15 00:00:00',0,NULL,NULL,NULL,'8d4a4e74-c2cc-11de-8d13-0010c6dffd0f'),(6,'Date','DT','Absolute date',1,'2004-07-22 00:00:00',0,NULL,NULL,NULL,'8d4a505e-c2cc-11de-8d13-0010c6dffd0f'),(7,'Time','TM','Absolute time of day',1,'2004-07-22 00:00:00',0,NULL,NULL,NULL,'8d4a591e-c2cc-11de-8d13-0010c6dffd0f'),(8,'Datetime','TS','Absolute date and time',1,'2004-07-22 00:00:00',0,NULL,NULL,NULL,'8d4a5af4-c2cc-11de-8d13-0010c6dffd0f'),(10,'Boolean','BIT','Boolean value (yes/no, true/false)',1,'2004-08-26 00:00:00',0,NULL,NULL,NULL,'8d4a5cca-c2cc-11de-8d13-0010c6dffd0f'),(11,'Rule','ZZ','Value derived from other data',1,'2006-09-11 00:00:00',0,NULL,NULL,NULL,'8d4a5e96-c2cc-11de-8d13-0010c6dffd0f'),(12,'Structured Numeric','SN','Complex numeric values possible (ie, <5, 1-10, etc.)',1,'2005-08-06 00:00:00',0,NULL,NULL,NULL,'8d4a606c-c2cc-11de-8d13-0010c6dffd0f'),(13,'Complex','ED','Complex value.  Analogous to HL7 Embedded Datatype',1,'2008-05-28 12:25:34',0,NULL,NULL,NULL,'8d4a6242-c2cc-11de-8d13-0010c6dffd0f');

--
-- Table structure for table `concept_description`
--

DROP TABLE IF EXISTS `concept_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_description` (
  `concept_description_id` int(11) NOT NULL AUTO_INCREMENT,
  `concept_id` int(11) NOT NULL DEFAULT '0',
  `description` text NOT NULL,
  `locale` varchar(50) NOT NULL DEFAULT '',
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`concept_description_id`),
  UNIQUE KEY `concept_description_uuid_index` (`uuid`),
  KEY `user_who_changed_description` (`changed_by`),
  KEY `description_for_concept` (`concept_id`),
  KEY `user_who_created_description` (`creator`),
  CONSTRAINT `description_for_concept` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `user_who_changed_description` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_created_description` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_description`
--


--
-- Table structure for table `concept_map_type`
--

DROP TABLE IF EXISTS `concept_map_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_map_type` (
  `concept_map_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `is_hidden` tinyint(1) NOT NULL DEFAULT '0',
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`concept_map_type_id`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `name` (`name`),
  KEY `mapped_user_creator_concept_map_type` (`creator`),
  KEY `mapped_user_changed_concept_map_type` (`changed_by`),
  KEY `mapped_user_retired_concept_map_type` (`retired_by`),
  CONSTRAINT `mapped_user_changed_concept_map_type` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `mapped_user_creator_concept_map_type` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `mapped_user_retired_concept_map_type` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_map_type`
--

INSERT INTO `concept_map_type` VALUES (1,'SAME-AS',NULL,1,'2021-05-09 00:00:00',NULL,NULL,0,0,NULL,NULL,NULL,'35543629-7d8c-11e1-909d-c80aa9edcf4e'),(2,'NARROWER-THAN',NULL,1,'2021-05-09 00:00:00',NULL,NULL,0,0,NULL,NULL,NULL,'43ac5109-7d8c-11e1-909d-c80aa9edcf4e'),(3,'BROADER-THAN',NULL,1,'2021-05-09 00:00:00',NULL,NULL,0,0,NULL,NULL,NULL,'4b9d9421-7d8c-11e1-909d-c80aa9edcf4e'),(4,'Associated finding',NULL,1,'2021-05-09 00:00:00',NULL,NULL,0,0,NULL,NULL,NULL,'55e02065-7d8c-11e1-909d-c80aa9edcf4e'),(5,'Associated morphology',NULL,1,'2021-05-09 00:00:00',NULL,NULL,0,0,NULL,NULL,NULL,'605f4a61-7d8c-11e1-909d-c80aa9edcf4e'),(6,'Associated procedure',NULL,1,'2021-05-09 00:00:00',NULL,NULL,0,0,NULL,NULL,NULL,'6eb1bfce-7d8c-11e1-909d-c80aa9edcf4e'),(7,'Associated with',NULL,1,'2021-05-09 00:00:00',NULL,NULL,0,0,NULL,NULL,NULL,'781bdc8f-7d8c-11e1-909d-c80aa9edcf4e'),(8,'Causative agent',NULL,1,'2021-05-09 00:00:00',NULL,NULL,0,0,NULL,NULL,NULL,'808f9e19-7d8c-11e1-909d-c80aa9edcf4e'),(9,'Finding site',NULL,1,'2021-05-09 00:00:00',NULL,NULL,0,0,NULL,NULL,NULL,'889c3013-7d8c-11e1-909d-c80aa9edcf4e'),(10,'Has specimen',NULL,1,'2021-05-09 00:00:00',NULL,NULL,0,0,NULL,NULL,NULL,'929600b9-7d8c-11e1-909d-c80aa9edcf4e'),(11,'Laterality',NULL,1,'2021-05-09 00:00:00',NULL,NULL,0,0,NULL,NULL,NULL,'999c6fc0-7d8c-11e1-909d-c80aa9edcf4e'),(12,'Severity',NULL,1,'2021-05-09 00:00:00',NULL,NULL,0,0,NULL,NULL,NULL,'a0e52281-7d8c-11e1-909d-c80aa9edcf4e'),(13,'Access',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'f9e90b29-7d8c-11e1-909d-c80aa9edcf4e'),(14,'After',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'01b60e29-7d8d-11e1-909d-c80aa9edcf4e'),(15,'Clinical course',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'5f7c3702-7d8d-11e1-909d-c80aa9edcf4e'),(16,'Component',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'67debecc-7d8d-11e1-909d-c80aa9edcf4e'),(17,'Direct device',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'718c00da-7d8d-11e1-909d-c80aa9edcf4e'),(18,'Direct morphology',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'7b9509cb-7d8d-11e1-909d-c80aa9edcf4e'),(19,'Direct substance',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'82bb495d-7d8d-11e1-909d-c80aa9edcf4e'),(20,'Due to',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'8b77f7d3-7d8d-11e1-909d-c80aa9edcf4e'),(21,'Episodicity',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'94a81179-7d8d-11e1-909d-c80aa9edcf4e'),(22,'Finding context',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'9d23c22e-7d8d-11e1-909d-c80aa9edcf4e'),(23,'Finding informer',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'a4524368-7d8d-11e1-909d-c80aa9edcf4e'),(24,'Finding method',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'af089254-7d8d-11e1-909d-c80aa9edcf4e'),(25,'Has active ingredient',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'b65aa605-7d8d-11e1-909d-c80aa9edcf4e'),(26,'Has definitional manifestation',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'c2b7b2fa-7d8d-11e1-909d-c80aa9edcf4'),(27,'Has dose form',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'cc3878e6-7d8d-11e1-909d-c80aa9edcf4e'),(28,'Has focus',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'d67c5840-7d8d-11e1-909d-c80aa9edcf4e'),(29,'Has intent',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'de2fb2c5-7d8d-11e1-909d-c80aa9edcf4e'),(30,'Has interpretation',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'e758838b-7d8d-11e1-909d-c80aa9edcf4e'),(31,'Indirect device',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'ee63c142-7d8d-11e1-909d-c80aa9edcf4e'),(32,'Indirect morphology',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'f4f36681-7d8d-11e1-909d-c80aa9edcf4e'),(33,'Interprets',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'fc7f5fed-7d8d-11e1-909d-c80aa9edcf4e'),(34,'Measurement method',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'06b11d79-7d8e-11e1-909d-c80aa9edcf4e'),(35,'Method',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'0efb4753-7d8e-11e1-909d-c80aa9edcf4e'),(36,'Occurrence',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'16e7b617-7d8e-11e1-909d-c80aa9edcf4e'),(37,'Part of',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'1e82007b-7d8e-11e1-909d-c80aa9edcf4e'),(38,'Pathological process',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'2969915e-7d8e-11e1-909d-c80aa9edcf4e'),(39,'Priority',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'32d57796-7d8e-11e1-909d-c80aa9edcf4e'),(40,'Procedure context',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'3f11904c-7d8e-11e1-909d-c80aa9edcf4e'),(41,'Procedure device',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'468c4aa3-7d8e-11e1-909d-c80aa9edcf4e'),(42,'Procedure morphology',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'5383e889-7d8e-11e1-909d-c80aa9edcf4e'),(43,'Procedure site',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'5ad2655d-7d8e-11e1-909d-c80aa9edcf4e'),(44,'Procedure site - Direct',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'66085196-7d8e-11e1-909d-c80aa9edcf4e'),(45,'Procedure site - Indirect',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'7080e843-7d8e-11e1-909d-c80aa9edcf4e'),(46,'Property',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'76bfb796-7d8e-11e1-909d-c80aa9edcf4e'),(47,'Recipient category',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'7e7d00e4-7d8e-11e1-909d-c80aa9edcf4e'),(48,'Revision status',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'851e14c1-7d8e-11e1-909d-c80aa9edcf4e'),(49,'Route of administration',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'8ee5b13d-7d8e-11e1-909d-c80aa9edcf4e'),(50,'Scale type',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'986acf48-7d8e-11e1-909d-c80aa9edcf4e'),(51,'Specimen procedure',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'a6937642-7d8e-11e1-909d-c80aa9edcf4e'),(52,'Specimen source identity',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'b1d6941e-7d8e-11e1-909d-c80aa9edcf4e'),(53,'Specimen source morphology',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'b7c793c1-7d8e-11e1-909d-c80aa9edcf4e'),(54,'Specimen source topography',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'be9f9eb8-7d8e-11e1-909d-c80aa9edcf4e'),(55,'Specimen substance',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'c8f2bacb-7d8e-11e1-909d-c80aa9edcf4e'),(56,'Subject of information',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'d0664c4f-7d8e-11e1-909d-c80aa9edcf4e'),(57,'Subject relationship context',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'dace9d13-7d8e-11e1-909d-c80aa9edcf4e'),(58,'Surgical approach',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'e3cd666d-7d8e-11e1-909d-c80aa9edcf4e'),(59,'Temporal context',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'ed96447d-7d8e-11e1-909d-c80aa9edcf4e'),(60,'Time aspect',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'f415bcce-7d8e-11e1-909d-c80aa9edcf4e'),(61,'Using access device',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'fa9538a9-7d8e-11e1-909d-c80aa9edcf4e'),(62,'Using device',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'06588655-7d8f-11e1-909d-c80aa9edcf4e'),(63,'Using energy',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'0c2ae0bc-7d8f-11e1-909d-c80aa9edcf4e'),(64,'Using substance',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'13d2c607-7d8f-11e1-909d-c80aa9edcf4e'),(65,'IS A',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'1ce7a784-7d8f-11e1-909d-c80aa9edcf4e'),(66,'MAY BE A',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'267812a3-7d8f-11e1-909d-c80aa9edcf4e'),(67,'MOVED FROM',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'2de3168e-7d8f-11e1-909d-c80aa9edcf4e'),(68,'MOVED TO',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'32f0fd99-7d8f-11e1-909d-c80aa9edcf4e'),(69,'REPLACED BY',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'3b3b9a7d-7d8f-11e1-909d-c80aa9edcf4e'),(70,'WAS A',NULL,1,'2021-05-09 00:00:00',NULL,NULL,1,0,NULL,NULL,NULL,'41a034da-7d8f-11e1-909d-c80aa9edcf4e');

--
-- Table structure for table `concept_name`
--

DROP TABLE IF EXISTS `concept_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_name` (
  `concept_name_id` int(11) NOT NULL AUTO_INCREMENT,
  `concept_id` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL DEFAULT '',
  `locale` varchar(50) NOT NULL DEFAULT '',
  `locale_preferred` tinyint(1) DEFAULT '0',
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `concept_name_type` varchar(50) DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `date_changed` datetime DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`concept_name_id`),
  UNIQUE KEY `concept_name_uuid_index` (`uuid`),
  KEY `name_of_concept` (`name`),
  KEY `name_for_concept` (`concept_id`),
  KEY `user_who_created_name` (`creator`),
  KEY `user_who_voided_this_name` (`voided_by`),
  KEY `concept_name_changed_by` (`changed_by`),
  CONSTRAINT `concept_name_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `name_for_concept` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `user_who_created_name` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_voided_this_name` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_name`
--

INSERT INTO `concept_name` VALUES (1,1,'Verdadeiro','pt',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'f907bd35-9630-4e80-98ed-b7de1bb0f9f7',NULL,NULL),(2,1,'Sim','pt',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'93c2c97e-cda7-4701-ad10-b2906f4dc965',NULL,NULL),(3,1,'True','en',1,1,'2021-05-09 07:08:37','FULLY_SPECIFIED',0,NULL,NULL,NULL,'d5bdedda-6c6d-403d-b11e-319ef7cea636',NULL,NULL),(4,1,'Yes','en',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'573218f0-034c-4a29-ab94-5a370ec6592f',NULL,NULL),(5,1,'Vero','it',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'50c95aa7-07d2-47d0-ab5c-95e268894168',NULL,NULL),(6,1,'Sì','it',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'2b6eb82b-ac8e-4d40-bba6-29bfc0c113b2',NULL,NULL),(7,1,'Vrai','fr',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'4b1b5d1c-e2e2-494f-aaf4-9b4cc5133c8f',NULL,NULL),(8,1,'Oui','fr',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'93eb2ed6-2313-4972-9baa-a4c1ad81a2ea',NULL,NULL),(9,1,'Verdadero','es',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'6b29d33a-f28f-4a8b-907b-177b758376f6',NULL,NULL),(10,1,'Sí','es',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'63cc37ca-77d1-4b79-acaf-8f532ee75e7b',NULL,NULL),(11,2,'Falso','pt',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'76f5efea-aa7b-4540-b325-10c4a502329e',NULL,NULL),(12,2,'Não','pt',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'5f944c3f-69e6-49b7-8904-6ddf17fb0e95',NULL,NULL),(13,2,'False','en',1,1,'2021-05-09 07:08:37','FULLY_SPECIFIED',0,NULL,NULL,NULL,'4fd15ff2-9fa6-41fe-b29d-d9cc2e8a5995',NULL,NULL),(14,2,'No','en',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'f8631744-5614-4962-8344-901ee0859195',NULL,NULL),(15,2,'Falso','it',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'9591f04a-302c-4f5e-bf16-9c92bee74bdd',NULL,NULL),(16,2,'No','it',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'79d548c8-a2f3-4a9b-95c7-234ee56c4696',NULL,NULL),(17,2,'Faux','fr',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'4da00645-e23b-44cd-ade5-aa9129a2ff04',NULL,NULL),(18,2,'Non','fr',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'35fe7cd5-35d6-43ec-8619-842d39dd8afe',NULL,NULL),(19,2,'Falso','es',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'59ff92df-7615-414a-a2ab-557289a4b734',NULL,NULL),(20,2,'No','es',0,1,'2021-05-09 07:08:37',NULL,0,NULL,NULL,NULL,'65954ee2-47f8-4d87-8458-41f796131b36',NULL,NULL);

--
-- Table structure for table `concept_name_tag`
--

DROP TABLE IF EXISTS `concept_name_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_name_tag` (
  `concept_name_tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  `description` text,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `date_changed` datetime DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`concept_name_tag_id`),
  UNIQUE KEY `concept_name_tag_unique_tags` (`tag`),
  UNIQUE KEY `concept_name_tag_uuid_index` (`uuid`),
  KEY `user_who_created_name_tag` (`creator`),
  KEY `user_who_voided_name_tag` (`voided_by`),
  KEY `concept_name_tag_changed_by` (`changed_by`),
  CONSTRAINT `concept_name_tag_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_name_tag`
--


--
-- Table structure for table `concept_name_tag_map`
--

DROP TABLE IF EXISTS `concept_name_tag_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_name_tag_map` (
  `concept_name_id` int(11) NOT NULL,
  `concept_name_tag_id` int(11) NOT NULL,
  KEY `mapped_concept_name` (`concept_name_id`),
  KEY `mapped_concept_name_tag` (`concept_name_tag_id`),
  CONSTRAINT `mapped_concept_name` FOREIGN KEY (`concept_name_id`) REFERENCES `concept_name` (`concept_name_id`),
  CONSTRAINT `mapped_concept_name_tag` FOREIGN KEY (`concept_name_tag_id`) REFERENCES `concept_name_tag` (`concept_name_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_name_tag_map`
--


--
-- Table structure for table `concept_numeric`
--

DROP TABLE IF EXISTS `concept_numeric`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_numeric` (
  `concept_id` int(11) NOT NULL DEFAULT '0',
  `hi_absolute` double DEFAULT NULL,
  `hi_critical` double DEFAULT NULL,
  `hi_normal` double DEFAULT NULL,
  `low_absolute` double DEFAULT NULL,
  `low_critical` double DEFAULT NULL,
  `low_normal` double DEFAULT NULL,
  `units` varchar(50) DEFAULT NULL,
  `allow_decimal` tinyint(1) DEFAULT NULL,
  `display_precision` int(11) DEFAULT NULL,
  PRIMARY KEY (`concept_id`),
  CONSTRAINT `numeric_attributes` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_numeric`
--


--
-- Table structure for table `concept_proposal`
--

DROP TABLE IF EXISTS `concept_proposal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_proposal` (
  `concept_proposal_id` int(11) NOT NULL AUTO_INCREMENT,
  `concept_id` int(11) DEFAULT NULL,
  `encounter_id` int(11) DEFAULT NULL,
  `original_text` varchar(255) NOT NULL DEFAULT '',
  `final_text` varchar(255) DEFAULT NULL,
  `obs_id` int(11) DEFAULT NULL,
  `obs_concept_id` int(11) DEFAULT NULL,
  `state` varchar(32) NOT NULL DEFAULT 'UNMAPPED',
  `comments` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `locale` varchar(50) NOT NULL DEFAULT '',
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`concept_proposal_id`),
  UNIQUE KEY `concept_proposal_uuid_index` (`uuid`),
  KEY `user_who_changed_proposal` (`changed_by`),
  KEY `concept_for_proposal` (`concept_id`),
  KEY `user_who_created_proposal` (`creator`),
  KEY `encounter_for_proposal` (`encounter_id`),
  KEY `proposal_obs_concept_id` (`obs_concept_id`),
  KEY `proposal_obs_id` (`obs_id`),
  CONSTRAINT `concept_for_proposal` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `encounter_for_proposal` FOREIGN KEY (`encounter_id`) REFERENCES `encounter` (`encounter_id`),
  CONSTRAINT `proposal_obs_concept_id` FOREIGN KEY (`obs_concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `proposal_obs_id` FOREIGN KEY (`obs_id`) REFERENCES `obs` (`obs_id`),
  CONSTRAINT `user_who_changed_proposal` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_created_proposal` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_proposal`
--


--
-- Table structure for table `concept_proposal_tag_map`
--

DROP TABLE IF EXISTS `concept_proposal_tag_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_proposal_tag_map` (
  `concept_proposal_id` int(11) NOT NULL,
  `concept_name_tag_id` int(11) NOT NULL,
  KEY `mapped_concept_proposal_tag` (`concept_name_tag_id`),
  KEY `mapped_concept_proposal` (`concept_proposal_id`),
  CONSTRAINT `mapped_concept_proposal` FOREIGN KEY (`concept_proposal_id`) REFERENCES `concept_proposal` (`concept_proposal_id`),
  CONSTRAINT `mapped_concept_proposal_tag` FOREIGN KEY (`concept_name_tag_id`) REFERENCES `concept_name_tag` (`concept_name_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_proposal_tag_map`
--


--
-- Table structure for table `concept_reference_map`
--

DROP TABLE IF EXISTS `concept_reference_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_reference_map` (
  `concept_map_id` int(11) NOT NULL AUTO_INCREMENT,
  `concept_reference_term_id` int(11) NOT NULL,
  `concept_map_type_id` int(11) NOT NULL DEFAULT '1',
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `concept_id` int(11) NOT NULL DEFAULT '0',
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`concept_map_id`),
  UNIQUE KEY `concept_reference_map_uuid_id` (`uuid`),
  KEY `map_for_concept` (`concept_id`),
  KEY `map_creator` (`creator`),
  KEY `mapped_concept_map_type` (`concept_map_type_id`),
  KEY `mapped_user_changed_ref_term` (`changed_by`),
  KEY `mapped_concept_reference_term` (`concept_reference_term_id`),
  CONSTRAINT `map_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `map_for_concept` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `mapped_concept_map_type` FOREIGN KEY (`concept_map_type_id`) REFERENCES `concept_map_type` (`concept_map_type_id`),
  CONSTRAINT `mapped_concept_reference_term` FOREIGN KEY (`concept_reference_term_id`) REFERENCES `concept_reference_term` (`concept_reference_term_id`),
  CONSTRAINT `mapped_user_changed_ref_term` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_reference_map`
--


--
-- Table structure for table `concept_reference_source`
--

DROP TABLE IF EXISTS `concept_reference_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_reference_source` (
  `concept_source_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  `description` text NOT NULL,
  `hl7_code` varchar(50) DEFAULT '',
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `unique_id` varchar(250) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`concept_source_id`),
  UNIQUE KEY `concept_reference_source_uuid_id` (`uuid`),
  UNIQUE KEY `concept_source_unique_hl7_codes` (`hl7_code`),
  UNIQUE KEY `concept_reference_source_unique_id_unique` (`unique_id`),
  KEY `unique_hl7_code` (`hl7_code`),
  KEY `concept_source_creator` (`creator`),
  KEY `user_who_retired_concept_source` (`retired_by`),
  KEY `concept_reference_source_changed_by` (`changed_by`),
  CONSTRAINT `concept_reference_source_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `concept_source_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_retired_concept_source` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_reference_source`
--


--
-- Table structure for table `concept_reference_term`
--

DROP TABLE IF EXISTS `concept_reference_term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_reference_term` (
  `concept_reference_term_id` int(11) NOT NULL AUTO_INCREMENT,
  `concept_source_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `version` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `date_changed` datetime DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`concept_reference_term_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `mapped_user_creator` (`creator`),
  KEY `mapped_user_changed` (`changed_by`),
  KEY `mapped_user_retired` (`retired_by`),
  KEY `mapped_concept_source` (`concept_source_id`),
  KEY `idx_code_concept_reference_term` (`code`),
  CONSTRAINT `mapped_concept_source` FOREIGN KEY (`concept_source_id`) REFERENCES `concept_reference_source` (`concept_source_id`),
  CONSTRAINT `mapped_user_changed` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `mapped_user_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `mapped_user_retired` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_reference_term`
--


--
-- Table structure for table `concept_reference_term_map`
--

DROP TABLE IF EXISTS `concept_reference_term_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_reference_term_map` (
  `concept_reference_term_map_id` int(11) NOT NULL AUTO_INCREMENT,
  `term_a_id` int(11) NOT NULL,
  `term_b_id` int(11) NOT NULL,
  `a_is_to_b_id` int(11) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`concept_reference_term_map_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `mapped_term_a` (`term_a_id`),
  KEY `mapped_term_b` (`term_b_id`),
  KEY `mapped_concept_map_type_ref_term_map` (`a_is_to_b_id`),
  KEY `mapped_user_creator_ref_term_map` (`creator`),
  KEY `mapped_user_changed_ref_term_map` (`changed_by`),
  CONSTRAINT `mapped_concept_map_type_ref_term_map` FOREIGN KEY (`a_is_to_b_id`) REFERENCES `concept_map_type` (`concept_map_type_id`),
  CONSTRAINT `mapped_term_a` FOREIGN KEY (`term_a_id`) REFERENCES `concept_reference_term` (`concept_reference_term_id`),
  CONSTRAINT `mapped_term_b` FOREIGN KEY (`term_b_id`) REFERENCES `concept_reference_term` (`concept_reference_term_id`),
  CONSTRAINT `mapped_user_changed_ref_term_map` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `mapped_user_creator_ref_term_map` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_reference_term_map`
--


--
-- Table structure for table `concept_set`
--

DROP TABLE IF EXISTS `concept_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_set` (
  `concept_set_id` int(11) NOT NULL AUTO_INCREMENT,
  `concept_id` int(11) NOT NULL DEFAULT '0',
  `concept_set` int(11) NOT NULL DEFAULT '0',
  `sort_weight` double DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`concept_set_id`),
  UNIQUE KEY `concept_set_uuid_index` (`uuid`),
  KEY `idx_concept_set_concept` (`concept_id`),
  KEY `has_a` (`concept_set`),
  KEY `user_who_created` (`creator`),
  CONSTRAINT `has_a` FOREIGN KEY (`concept_set`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `user_who_created` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_set`
--


--
-- Table structure for table `concept_state_conversion`
--

DROP TABLE IF EXISTS `concept_state_conversion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_state_conversion` (
  `concept_state_conversion_id` int(11) NOT NULL AUTO_INCREMENT,
  `concept_id` int(11) DEFAULT '0',
  `program_workflow_id` int(11) DEFAULT '0',
  `program_workflow_state_id` int(11) DEFAULT '0',
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`concept_state_conversion_id`),
  UNIQUE KEY `concept_state_conversion_uuid_index` (`uuid`),
  UNIQUE KEY `unique_workflow_concept_in_conversion` (`program_workflow_id`,`concept_id`),
  KEY `concept_triggers_conversion` (`concept_id`),
  KEY `conversion_to_state` (`program_workflow_state_id`),
  CONSTRAINT `concept_triggers_conversion` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `conversion_involves_workflow` FOREIGN KEY (`program_workflow_id`) REFERENCES `program_workflow` (`program_workflow_id`),
  CONSTRAINT `conversion_to_state` FOREIGN KEY (`program_workflow_state_id`) REFERENCES `program_workflow_state` (`program_workflow_state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_state_conversion`
--


--
-- Table structure for table `concept_stop_word`
--

DROP TABLE IF EXISTS `concept_stop_word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concept_stop_word` (
  `concept_stop_word_id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(50) NOT NULL,
  `locale` varchar(50) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`concept_stop_word_id`),
  UNIQUE KEY `Unique_StopWord_Key` (`word`,`locale`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concept_stop_word`
--

INSERT INTO `concept_stop_word` VALUES (1,'A','en','f5f45540-e2a7-11df-87ae-18a905e044dc'),(2,'AND','en','f5f469ae-e2a7-11df-87ae-18a905e044dc'),(3,'AT','en','f5f47070-e2a7-11df-87ae-18a905e044dc'),(4,'BUT','en','f5f476c4-e2a7-11df-87ae-18a905e044dc'),(5,'BY','en','f5f47d04-e2a7-11df-87ae-18a905e044dc'),(6,'FOR','en','f5f4834e-e2a7-11df-87ae-18a905e044dc'),(7,'HAS','en','f5f48a24-e2a7-11df-87ae-18a905e044dc'),(8,'OF','en','f5f49064-e2a7-11df-87ae-18a905e044dc'),(9,'THE','en','f5f496ae-e2a7-11df-87ae-18a905e044dc'),(10,'TO','en','f5f49cda-e2a7-11df-87ae-18a905e044dc');

--
-- Table structure for table `conditions`
--

DROP TABLE IF EXISTS `conditions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conditions` (
  `condition_id` int(11) NOT NULL AUTO_INCREMENT,
  `additional_detail` varchar(255) DEFAULT NULL,
  `previous_version` int(11) DEFAULT NULL,
  `condition_coded` int(11) DEFAULT NULL,
  `condition_non_coded` varchar(255) DEFAULT NULL,
  `condition_coded_name` int(11) DEFAULT NULL,
  `clinical_status` varchar(50) NOT NULL,
  `verification_status` varchar(50) DEFAULT NULL,
  `onset_date` datetime DEFAULT NULL,
  `date_created` datetime NOT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` varchar(38) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `voided_by` int(11) DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `patient_id` int(11) NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `encounter_id` int(11) DEFAULT NULL,
  `form_namespace_and_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`condition_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `condition_previous_version_fk` (`previous_version`),
  KEY `condition_condition_coded_fk` (`condition_coded`),
  KEY `condition_condition_coded_name_fk` (`condition_coded_name`),
  KEY `condition_creator_fk` (`creator`),
  KEY `condition_changed_by_fk` (`changed_by`),
  KEY `condition_voided_by_fk` (`voided_by`),
  KEY `condition_patient_fk` (`patient_id`),
  KEY `conditions_encounter_id_fk` (`encounter_id`),
  CONSTRAINT `condition_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `condition_condition_coded_fk` FOREIGN KEY (`condition_coded`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `condition_condition_coded_name_fk` FOREIGN KEY (`condition_coded_name`) REFERENCES `concept_name` (`concept_name_id`),
  CONSTRAINT `condition_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `condition_patient_fk` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`),
  CONSTRAINT `condition_previous_version_fk` FOREIGN KEY (`previous_version`) REFERENCES `conditions` (`condition_id`),
  CONSTRAINT `condition_voided_by_fk` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `conditions_encounter_id_fk` FOREIGN KEY (`encounter_id`) REFERENCES `encounter` (`encounter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conditions`
--


--
-- Table structure for table `diagnosis_attribute`
--

DROP TABLE IF EXISTS `diagnosis_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diagnosis_attribute` (
  `diagnosis_attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `diagnosis_id` int(11) NOT NULL,
  `attribute_type_id` int(11) NOT NULL,
  `value_reference` text NOT NULL,
  `uuid` char(38) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`diagnosis_attribute_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `diagnosis_attribute_diagnosis_fk` (`diagnosis_id`),
  KEY `diagnosis_attribute_attribute_type_id_fk` (`attribute_type_id`),
  KEY `diagnosis_attribute_creator_fk` (`creator`),
  KEY `diagnosis_attribute_changed_by_fk` (`changed_by`),
  KEY `diagnosis_attribute_voided_by_fk` (`voided_by`),
  CONSTRAINT `diagnosis_attribute_attribute_type_id_fk` FOREIGN KEY (`attribute_type_id`) REFERENCES `diagnosis_attribute_type` (`diagnosis_attribute_type_id`),
  CONSTRAINT `diagnosis_attribute_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `diagnosis_attribute_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `diagnosis_attribute_diagnosis_fk` FOREIGN KEY (`diagnosis_id`) REFERENCES `encounter_diagnosis` (`diagnosis_id`),
  CONSTRAINT `diagnosis_attribute_voided_by_fk` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diagnosis_attribute`
--


--
-- Table structure for table `diagnosis_attribute_type`
--

DROP TABLE IF EXISTS `diagnosis_attribute_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diagnosis_attribute_type` (
  `diagnosis_attribute_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `datatype` varchar(255) DEFAULT NULL,
  `datatype_config` text,
  `preferred_handler` varchar(255) DEFAULT NULL,
  `handler_config` text,
  `min_occurs` int(11) NOT NULL,
  `max_occurs` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`diagnosis_attribute_type_id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `diagnosis_attribute_type_creator_fk` (`creator`),
  KEY `diagnosis_attribute_type_changed_by_fk` (`changed_by`),
  KEY `diagnosis_attribute_type_retired_by_fk` (`retired_by`),
  CONSTRAINT `diagnosis_attribute_type_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `diagnosis_attribute_type_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `diagnosis_attribute_type_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diagnosis_attribute_type`
--


--
-- Table structure for table `drug`
--

DROP TABLE IF EXISTS `drug`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drug` (
  `drug_id` int(11) NOT NULL AUTO_INCREMENT,
  `concept_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `combination` tinyint(1) NOT NULL DEFAULT '0',
  `dosage_form` int(11) DEFAULT NULL,
  `maximum_daily_dose` double DEFAULT NULL,
  `minimum_daily_dose` double DEFAULT NULL,
  `route` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `strength` varchar(255) DEFAULT NULL,
  `dose_limit_units` int(11) DEFAULT NULL,
  PRIMARY KEY (`drug_id`),
  UNIQUE KEY `drug_uuid_index` (`uuid`),
  KEY `primary_drug_concept` (`concept_id`),
  KEY `drug_creator` (`creator`),
  KEY `drug_changed_by` (`changed_by`),
  KEY `dosage_form_concept` (`dosage_form`),
  KEY `drug_retired_by` (`retired_by`),
  KEY `route_concept` (`route`),
  KEY `drug_dose_limit_units_fk` (`dose_limit_units`),
  CONSTRAINT `dosage_form_concept` FOREIGN KEY (`dosage_form`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `drug_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `drug_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `drug_dose_limit_units_fk` FOREIGN KEY (`dose_limit_units`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `drug_retired_by` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `primary_drug_concept` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `route_concept` FOREIGN KEY (`route`) REFERENCES `concept` (`concept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drug`
--


--
-- Table structure for table `drug_ingredient`
--

DROP TABLE IF EXISTS `drug_ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drug_ingredient` (
  `drug_id` int(11) NOT NULL,
  `ingredient_id` int(11) NOT NULL,
  `uuid` char(38) NOT NULL,
  `strength` double DEFAULT NULL,
  `units` int(11) DEFAULT NULL,
  PRIMARY KEY (`drug_id`,`ingredient_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `drug_ingredient_units_fk` (`units`),
  KEY `drug_ingredient_ingredient_id_fk` (`ingredient_id`),
  CONSTRAINT `drug_ingredient_drug_id_fk` FOREIGN KEY (`drug_id`) REFERENCES `drug` (`drug_id`),
  CONSTRAINT `drug_ingredient_ingredient_id_fk` FOREIGN KEY (`ingredient_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `drug_ingredient_units_fk` FOREIGN KEY (`units`) REFERENCES `concept` (`concept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drug_ingredient`
--


--
-- Table structure for table `drug_order`
--

DROP TABLE IF EXISTS `drug_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drug_order` (
  `order_id` int(11) NOT NULL DEFAULT '0',
  `drug_inventory_id` int(11) DEFAULT NULL,
  `dose` double DEFAULT NULL,
  `as_needed` tinyint(1) DEFAULT NULL,
  `dosing_type` varchar(255) DEFAULT NULL,
  `quantity` double DEFAULT NULL,
  `as_needed_condition` varchar(255) DEFAULT NULL,
  `num_refills` int(11) DEFAULT NULL,
  `dosing_instructions` text,
  `duration` int(11) DEFAULT NULL,
  `duration_units` int(11) DEFAULT NULL,
  `quantity_units` int(11) DEFAULT NULL,
  `route` int(11) DEFAULT NULL,
  `dose_units` int(11) DEFAULT NULL,
  `frequency` int(11) DEFAULT NULL,
  `brand_name` varchar(255) DEFAULT NULL,
  `dispense_as_written` tinyint(1) NOT NULL DEFAULT '0',
  `drug_non_coded` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `inventory_item` (`drug_inventory_id`),
  KEY `drug_order_duration_units_fk` (`duration_units`),
  KEY `drug_order_quantity_units` (`quantity_units`),
  KEY `drug_order_route_fk` (`route`),
  KEY `drug_order_dose_units` (`dose_units`),
  KEY `drug_order_frequency_fk` (`frequency`),
  CONSTRAINT `drug_order_dose_units` FOREIGN KEY (`dose_units`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `drug_order_duration_units_fk` FOREIGN KEY (`duration_units`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `drug_order_frequency_fk` FOREIGN KEY (`frequency`) REFERENCES `order_frequency` (`order_frequency_id`),
  CONSTRAINT `drug_order_quantity_units` FOREIGN KEY (`quantity_units`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `drug_order_route_fk` FOREIGN KEY (`route`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `extends_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `inventory_item` FOREIGN KEY (`drug_inventory_id`) REFERENCES `drug` (`drug_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drug_order`
--


--
-- Table structure for table `drug_reference_map`
--

DROP TABLE IF EXISTS `drug_reference_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drug_reference_map` (
  `drug_reference_map_id` int(11) NOT NULL AUTO_INCREMENT,
  `drug_id` int(11) NOT NULL,
  `term_id` int(11) NOT NULL,
  `concept_map_type` int(11) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`drug_reference_map_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `drug_for_drug_reference_map` (`drug_id`),
  KEY `concept_reference_term_for_drug_reference_map` (`term_id`),
  KEY `concept_map_type_for_drug_reference_map` (`concept_map_type`),
  KEY `user_who_changed_drug_reference_map` (`changed_by`),
  KEY `drug_reference_map_creator` (`creator`),
  KEY `user_who_retired_drug_reference_map` (`retired_by`),
  CONSTRAINT `concept_map_type_for_drug_reference_map` FOREIGN KEY (`concept_map_type`) REFERENCES `concept_map_type` (`concept_map_type_id`),
  CONSTRAINT `concept_reference_term_for_drug_reference_map` FOREIGN KEY (`term_id`) REFERENCES `concept_reference_term` (`concept_reference_term_id`),
  CONSTRAINT `drug_for_drug_reference_map` FOREIGN KEY (`drug_id`) REFERENCES `drug` (`drug_id`),
  CONSTRAINT `drug_reference_map_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_changed_drug_reference_map` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_retired_drug_reference_map` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drug_reference_map`
--


--
-- Table structure for table `encounter`
--

DROP TABLE IF EXISTS `encounter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `encounter` (
  `encounter_id` int(11) NOT NULL AUTO_INCREMENT,
  `encounter_type` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL DEFAULT '0',
  `location_id` int(11) DEFAULT NULL,
  `form_id` int(11) DEFAULT NULL,
  `encounter_datetime` datetime NOT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `visit_id` int(11) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`encounter_id`),
  UNIQUE KEY `encounter_uuid_index` (`uuid`),
  KEY `encounter_datetime_idx` (`encounter_datetime`),
  KEY `encounter_ibfk_1` (`creator`),
  KEY `encounter_type_id` (`encounter_type`),
  KEY `encounter_form` (`form_id`),
  KEY `encounter_location` (`location_id`),
  KEY `encounter_patient` (`patient_id`),
  KEY `user_who_voided_encounter` (`voided_by`),
  KEY `encounter_changed_by` (`changed_by`),
  KEY `encounter_visit_id_fk` (`visit_id`),
  CONSTRAINT `encounter_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `encounter_form` FOREIGN KEY (`form_id`) REFERENCES `form` (`form_id`),
  CONSTRAINT `encounter_ibfk_1` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `encounter_location` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`),
  CONSTRAINT `encounter_patient` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON UPDATE CASCADE,
  CONSTRAINT `encounter_type_id` FOREIGN KEY (`encounter_type`) REFERENCES `encounter_type` (`encounter_type_id`),
  CONSTRAINT `encounter_visit_id_fk` FOREIGN KEY (`visit_id`) REFERENCES `visit` (`visit_id`),
  CONSTRAINT `user_who_voided_encounter` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encounter`
--


--
-- Table structure for table `encounter_diagnosis`
--

DROP TABLE IF EXISTS `encounter_diagnosis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `encounter_diagnosis` (
  `diagnosis_id` int(11) NOT NULL AUTO_INCREMENT,
  `diagnosis_coded` int(11) DEFAULT NULL,
  `diagnosis_non_coded` varchar(255) DEFAULT NULL,
  `diagnosis_coded_name` int(11) DEFAULT NULL,
  `encounter_id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `condition_id` int(11) DEFAULT NULL,
  `certainty` varchar(255) NOT NULL,
  `dx_rank` int(11) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `form_namespace_and_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`diagnosis_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `encounter_diagnosis_encounter_id_fk` (`encounter_id`),
  KEY `encounter_diagnosis_condition_id_fk` (`condition_id`),
  KEY `encounter_diagnosis_creator_fk` (`creator`),
  KEY `encounter_diagnosis_voided_by_fk` (`voided_by`),
  KEY `encounter_diagnosis_changed_by_fk` (`changed_by`),
  KEY `encounter_diagnosis_coded_fk` (`diagnosis_coded`),
  KEY `encounter_diagnosis_coded_name_fk` (`diagnosis_coded_name`),
  KEY `encounter_diagnosis_patient_fk` (`patient_id`),
  CONSTRAINT `encounter_diagnosis_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `encounter_diagnosis_coded_fk` FOREIGN KEY (`diagnosis_coded`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `encounter_diagnosis_coded_name_fk` FOREIGN KEY (`diagnosis_coded_name`) REFERENCES `concept_name` (`concept_name_id`),
  CONSTRAINT `encounter_diagnosis_condition_id_fk` FOREIGN KEY (`condition_id`) REFERENCES `conditions` (`condition_id`),
  CONSTRAINT `encounter_diagnosis_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `encounter_diagnosis_encounter_id_fk` FOREIGN KEY (`encounter_id`) REFERENCES `encounter` (`encounter_id`),
  CONSTRAINT `encounter_diagnosis_patient_fk` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`),
  CONSTRAINT `encounter_diagnosis_patient_id_fk` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`),
  CONSTRAINT `encounter_diagnosis_voided_by_fk` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encounter_diagnosis`
--


--
-- Table structure for table `encounter_provider`
--

DROP TABLE IF EXISTS `encounter_provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `encounter_provider` (
  `encounter_provider_id` int(11) NOT NULL AUTO_INCREMENT,
  `encounter_id` int(11) NOT NULL,
  `provider_id` int(11) NOT NULL,
  `encounter_role_id` int(11) NOT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `date_voided` datetime DEFAULT NULL,
  `voided_by` int(11) DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`encounter_provider_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `encounter_id_fk` (`encounter_id`),
  KEY `provider_id_fk` (`provider_id`),
  KEY `encounter_role_id_fk` (`encounter_role_id`),
  KEY `encounter_provider_creator` (`creator`),
  KEY `encounter_provider_changed_by` (`changed_by`),
  KEY `encounter_provider_voided_by` (`voided_by`),
  CONSTRAINT `encounter_id_fk` FOREIGN KEY (`encounter_id`) REFERENCES `encounter` (`encounter_id`),
  CONSTRAINT `encounter_provider_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `encounter_provider_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `encounter_provider_voided_by` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `encounter_role_id_fk` FOREIGN KEY (`encounter_role_id`) REFERENCES `encounter_role` (`encounter_role_id`),
  CONSTRAINT `provider_id_fk` FOREIGN KEY (`provider_id`) REFERENCES `provider` (`provider_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encounter_provider`
--


--
-- Table structure for table `encounter_role`
--

DROP TABLE IF EXISTS `encounter_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `encounter_role` (
  `encounter_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`encounter_role_id`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `encounter_role_unique_name` (`name`),
  KEY `encounter_role_creator_fk` (`creator`),
  KEY `encounter_role_changed_by_fk` (`changed_by`),
  KEY `encounter_role_retired_by_fk` (`retired_by`),
  CONSTRAINT `encounter_role_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `encounter_role_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `encounter_role_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encounter_role`
--

INSERT INTO `encounter_role` VALUES (1,'Unknown','Unknown encounter role for legacy providers with no encounter role set',1,'2011-08-18 14:00:00',NULL,NULL,0,NULL,NULL,NULL,'a0b03050-c99b-11e0-9572-0800200c9a66');

--
-- Table structure for table `encounter_type`
--

DROP TABLE IF EXISTS `encounter_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `encounter_type` (
  `encounter_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  `description` text,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `edit_privilege` varchar(255) DEFAULT NULL,
  `view_privilege` varchar(255) DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  PRIMARY KEY (`encounter_type_id`),
  UNIQUE KEY `encounter_type_unique_name` (`name`),
  UNIQUE KEY `encounter_type_uuid_index` (`uuid`),
  KEY `encounter_type_retired_status` (`retired`),
  KEY `user_who_created_type` (`creator`),
  KEY `user_who_retired_encounter_type` (`retired_by`),
  KEY `privilege_which_can_view_encounter_type` (`view_privilege`),
  KEY `privilege_which_can_edit_encounter_type` (`edit_privilege`),
  KEY `encounter_type_changed_by` (`changed_by`),
  CONSTRAINT `encounter_type_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `privilege_which_can_edit_encounter_type` FOREIGN KEY (`edit_privilege`) REFERENCES `privilege` (`privilege`),
  CONSTRAINT `privilege_which_can_view_encounter_type` FOREIGN KEY (`view_privilege`) REFERENCES `privilege` (`privilege`),
  CONSTRAINT `user_who_created_type` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_retired_encounter_type` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encounter_type`
--


--
-- Table structure for table `fhir_concept_source`
--

DROP TABLE IF EXISTS `fhir_concept_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fhir_concept_source` (
  `fhir_concept_source_id` int(11) NOT NULL AUTO_INCREMENT,
  `concept_source_id` int(11) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` varchar(38) NOT NULL,
  PRIMARY KEY (`fhir_concept_source_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `fhir_concept_source_concept_reference_source_fk` (`concept_source_id`),
  KEY `fhir_concept_source_creator_fk` (`creator`),
  KEY `fhir_concept_source_changed_by_fk` (`changed_by`),
  KEY `fhir_concept_source_retired_by_fk` (`retired_by`),
  CONSTRAINT `fhir_concept_source_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_concept_source_concept_reference_source_fk` FOREIGN KEY (`concept_source_id`) REFERENCES `concept_reference_source` (`concept_source_id`),
  CONSTRAINT `fhir_concept_source_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_concept_source_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fhir_concept_source`
--


--
-- Table structure for table `fhir_diagnostic_report`
--

DROP TABLE IF EXISTS `fhir_diagnostic_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fhir_diagnostic_report` (
  `diagnostic_report_id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(50) NOT NULL,
  `concept_id` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `encounter_id` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`diagnostic_report_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `fhir_diagnostic_report_creator` (`creator`),
  KEY `fhir_diagnostic_report_changed_by` (`changed_by`),
  KEY `fhir_diagnostic_report_voided_by` (`voided_by`),
  KEY `fhir_diagnostic_report_code` (`concept_id`),
  KEY `fhir_diagnostic_report_subject` (`subject_id`),
  KEY `fhir_diagnostic_report_encounter` (`encounter_id`),
  CONSTRAINT `fhir_diagnostic_report_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_diagnostic_report_code` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `fhir_diagnostic_report_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_diagnostic_report_encounter` FOREIGN KEY (`encounter_id`) REFERENCES `encounter` (`encounter_id`),
  CONSTRAINT `fhir_diagnostic_report_subject` FOREIGN KEY (`subject_id`) REFERENCES `patient` (`patient_id`),
  CONSTRAINT `fhir_diagnostic_report_voided_by` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fhir_diagnostic_report`
--


--
-- Table structure for table `fhir_diagnostic_report_performers`
--

DROP TABLE IF EXISTS `fhir_diagnostic_report_performers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fhir_diagnostic_report_performers` (
  `diagnostic_report_id` int(11) NOT NULL,
  `provider_id` int(11) NOT NULL,
  PRIMARY KEY (`diagnostic_report_id`,`provider_id`),
  KEY `fhir_diagnostic_report_performers_reference` (`provider_id`),
  CONSTRAINT `fhir_diagnostic_report_performers_diagnostic_report` FOREIGN KEY (`diagnostic_report_id`) REFERENCES `fhir_diagnostic_report` (`diagnostic_report_id`),
  CONSTRAINT `fhir_diagnostic_report_performers_reference` FOREIGN KEY (`provider_id`) REFERENCES `provider` (`provider_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fhir_diagnostic_report_performers`
--


--
-- Table structure for table `fhir_diagnostic_report_results`
--

DROP TABLE IF EXISTS `fhir_diagnostic_report_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fhir_diagnostic_report_results` (
  `diagnostic_report_id` int(11) NOT NULL,
  `obs_id` int(11) NOT NULL,
  PRIMARY KEY (`diagnostic_report_id`,`obs_id`),
  KEY `fhir_diagnostic_report_results_reference` (`obs_id`),
  CONSTRAINT `fhir_diagnostic_report_results_diagnostic_report` FOREIGN KEY (`diagnostic_report_id`) REFERENCES `fhir_diagnostic_report` (`diagnostic_report_id`),
  CONSTRAINT `fhir_diagnostic_report_results_reference` FOREIGN KEY (`obs_id`) REFERENCES `obs` (`obs_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fhir_diagnostic_report_results`
--


--
-- Table structure for table `fhir_duration_unit_map`
--

DROP TABLE IF EXISTS `fhir_duration_unit_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fhir_duration_unit_map` (
  `duration_unit_map_id` int(11) NOT NULL AUTO_INCREMENT,
  `concept_id` int(11) DEFAULT NULL,
  `unit_of_time` varchar(20) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retired_reason` varchar(255) DEFAULT NULL,
  `uuid` char(36) NOT NULL,
  PRIMARY KEY (`duration_unit_map_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `fhir_duration_unit_map_creator` (`creator`),
  KEY `fhir_duration_unit_map_changed_by` (`changed_by`),
  KEY `fhir_duration_unit_map_retired_by` (`retired_by`),
  KEY `fhir_duration_unit_map_concept` (`concept_id`),
  KEY `fhir_duration_unit_map_unit_of_time` (`unit_of_time`),
  CONSTRAINT `fhir_duration_unit_map_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_duration_unit_map_concept` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `fhir_duration_unit_map_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_duration_unit_map_retired_by` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fhir_duration_unit_map`
--


--
-- Table structure for table `fhir_encounter_class_map`
--

DROP TABLE IF EXISTS `fhir_encounter_class_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fhir_encounter_class_map` (
  `encounter_class_map_id` int(11) NOT NULL AUTO_INCREMENT,
  `location_id` int(11) DEFAULT NULL,
  `encounter_class` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retired_reason` varchar(255) DEFAULT NULL,
  `uuid` char(36) NOT NULL,
  PRIMARY KEY (`encounter_class_map_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `fhir_encounter_class_map_creator` (`creator`),
  KEY `fhir_encounter_class_map_changed_by` (`changed_by`),
  KEY `fhir_encounter_class_map_retired_by` (`retired_by`),
  KEY `fhir_encounter_class_map_location` (`location_id`),
  CONSTRAINT `fhir_encounter_class_map_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_encounter_class_map_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_encounter_class_map_location` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`),
  CONSTRAINT `fhir_encounter_class_map_retired_by` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fhir_encounter_class_map`
--


--
-- Table structure for table `fhir_observation_category_map`
--

DROP TABLE IF EXISTS `fhir_observation_category_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fhir_observation_category_map` (
  `observation_category_map_id` int(11) NOT NULL AUTO_INCREMENT,
  `concept_class_id` int(11) DEFAULT NULL,
  `observation_category` varchar(255) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retired_reason` varchar(255) DEFAULT NULL,
  `uuid` char(36) NOT NULL,
  PRIMARY KEY (`observation_category_map_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `fhir_observation_category_map_creator` (`creator`),
  KEY `fhir_observation_category_map_changed_by` (`changed_by`),
  KEY `fhir_observation_category_map_retired_by` (`retired_by`),
  KEY `fhir_observation_category_map_concept_class` (`concept_class_id`),
  KEY `fhir_observation_category_map_observation_category` (`observation_category`),
  CONSTRAINT `fhir_observation_category_map_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_observation_category_map_concept_class` FOREIGN KEY (`concept_class_id`) REFERENCES `concept_class` (`concept_class_id`),
  CONSTRAINT `fhir_observation_category_map_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_observation_category_map_retired_by` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fhir_observation_category_map`
--

INSERT INTO `fhir_observation_category_map` VALUES (1,1,'laboratory',1,'2022-05-09 13:53:43',NULL,NULL,0,NULL,NULL,NULL,'5993de46-cfc9-11ec-9587-0242c0a8f004'),(2,2,'procedure',1,'2022-05-09 13:53:43',NULL,NULL,0,NULL,NULL,NULL,'59954c0b-cfc9-11ec-9587-0242c0a8f004'),(3,5,'exam',1,'2022-05-09 13:53:44',NULL,NULL,0,NULL,NULL,NULL,'59970eb5-cfc9-11ec-9587-0242c0a8f004');

--
-- Table structure for table `fhir_patient_identifier_system`
--

DROP TABLE IF EXISTS `fhir_patient_identifier_system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fhir_patient_identifier_system` (
  `fhir_patient_identifier_system_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_identifier_type_id` int(11) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` varchar(38) NOT NULL,
  PRIMARY KEY (`fhir_patient_identifier_system_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `fhir_patient_identifier_system_patient_identifier_type_fk` (`patient_identifier_type_id`),
  KEY `fhir_patient_identifier_system_creator_fk` (`creator`),
  KEY `fhir_patient_identifier_system_changed_by_fk` (`changed_by`),
  KEY `fhir_patient_identifier_system_retired_by_fk` (`retired_by`),
  CONSTRAINT `fhir_patient_identifier_system_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_patient_identifier_system_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_patient_identifier_system_patient_identifier_type_fk` FOREIGN KEY (`patient_identifier_type_id`) REFERENCES `patient_identifier_type` (`patient_identifier_type_id`),
  CONSTRAINT `fhir_patient_identifier_system_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fhir_patient_identifier_system`
--


--
-- Table structure for table `fhir_reference`
--

DROP TABLE IF EXISTS `fhir_reference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fhir_reference` (
  `reference_id` int(11) NOT NULL AUTO_INCREMENT,
  `target_type` varchar(255) NOT NULL,
  `reference` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` varchar(38) NOT NULL,
  `target_uuid` char(32) DEFAULT NULL,
  PRIMARY KEY (`reference_id`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `target_uuid` (`target_uuid`),
  KEY `fhir_reference_creator_fk` (`creator`),
  KEY `fhir_reference_changed_by_fk` (`changed_by`),
  KEY `fhir_reference_retired_by_fk` (`retired_by`),
  CONSTRAINT `fhir_reference_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_reference_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_reference_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fhir_reference`
--


--
-- Table structure for table `fhir_task`
--

DROP TABLE IF EXISTS `fhir_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fhir_task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) NOT NULL DEFAULT 'UNKNOWN',
  `status_reason` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `intent` varchar(255) NOT NULL,
  `owner_reference_id` int(11) DEFAULT NULL,
  `encounter_reference_id` int(11) DEFAULT NULL,
  `for_reference_id` int(11) DEFAULT NULL,
  `based_on` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `task_creator` (`creator`),
  KEY `task_changed_by` (`changed_by`),
  KEY `fhir_task_retired_by_fk` (`retired_by`),
  KEY `task_owner_reference_fk` (`owner_reference_id`),
  KEY `task_for_reference_fk` (`for_reference_id`),
  KEY `task_encounter_reference_fk` (`encounter_reference_id`),
  CONSTRAINT `fhir_task_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `task_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `task_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `task_encounter_reference_fk` FOREIGN KEY (`encounter_reference_id`) REFERENCES `fhir_reference` (`reference_id`),
  CONSTRAINT `task_for_reference_fk` FOREIGN KEY (`for_reference_id`) REFERENCES `fhir_reference` (`reference_id`),
  CONSTRAINT `task_owner_reference_fk` FOREIGN KEY (`owner_reference_id`) REFERENCES `fhir_reference` (`reference_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fhir_task`
--


--
-- Table structure for table `fhir_task_based_on_reference`
--

DROP TABLE IF EXISTS `fhir_task_based_on_reference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fhir_task_based_on_reference` (
  `task_id` int(11) NOT NULL,
  `reference_id` int(11) NOT NULL,
  UNIQUE KEY `reference_id` (`reference_id`),
  KEY `task_based_on_fk` (`task_id`),
  CONSTRAINT `reference_based_on_fk` FOREIGN KEY (`reference_id`) REFERENCES `fhir_reference` (`reference_id`),
  CONSTRAINT `task_based_on_fk` FOREIGN KEY (`task_id`) REFERENCES `fhir_task` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fhir_task_based_on_reference`
--


--
-- Table structure for table `fhir_task_input`
--

DROP TABLE IF EXISTS `fhir_task_input`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fhir_task_input` (
  `task_input_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `value_datetime` datetime DEFAULT NULL,
  `value_numeric` double DEFAULT NULL,
  `value_text` varchar(255) DEFAULT NULL,
  `value_reference_id` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` varchar(38) NOT NULL,
  PRIMARY KEY (`task_input_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `fhir_task_input_creator_fk` (`creator`),
  KEY `fhir_task_input_changed_by_fk` (`changed_by`),
  KEY `fhir_task_input_retired_by_fk` (`retired_by`),
  KEY `input_type_fk` (`type_id`),
  KEY `input_reference_fk` (`value_reference_id`),
  KEY `input_task_fk` (`task_id`),
  CONSTRAINT `fhir_task_input_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_task_input_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_task_input_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `input_reference_fk` FOREIGN KEY (`value_reference_id`) REFERENCES `fhir_reference` (`reference_id`),
  CONSTRAINT `input_task_fk` FOREIGN KEY (`task_id`) REFERENCES `fhir_task` (`task_id`),
  CONSTRAINT `input_type_fk` FOREIGN KEY (`type_id`) REFERENCES `concept` (`concept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fhir_task_input`
--


--
-- Table structure for table `fhir_task_output`
--

DROP TABLE IF EXISTS `fhir_task_output`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fhir_task_output` (
  `task_output_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) DEFAULT NULL,
  `type_id` int(11) NOT NULL,
  `value_datetime` datetime DEFAULT NULL,
  `value_numeric` double DEFAULT NULL,
  `value_text` varchar(255) DEFAULT NULL,
  `value_reference_id` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` varchar(38) NOT NULL,
  PRIMARY KEY (`task_output_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `fhir_task_output_creator_fk` (`creator`),
  KEY `fhir_task_output_changed_by_fk` (`changed_by`),
  KEY `fhir_task_output_retired_by_fk` (`retired_by`),
  KEY `output_type_fk` (`type_id`),
  KEY `output_reference_fk` (`value_reference_id`),
  KEY `output_task_fk` (`task_id`),
  CONSTRAINT `fhir_task_output_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_task_output_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fhir_task_output_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `output_reference_fk` FOREIGN KEY (`value_reference_id`) REFERENCES `fhir_reference` (`reference_id`),
  CONSTRAINT `output_task_fk` FOREIGN KEY (`task_id`) REFERENCES `fhir_task` (`task_id`),
  CONSTRAINT `output_type_fk` FOREIGN KEY (`type_id`) REFERENCES `concept` (`concept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fhir_task_output`
--


--
-- Table structure for table `field`
--

DROP TABLE IF EXISTS `field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `field` (
  `field_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` text,
  `field_type` int(11) DEFAULT NULL,
  `concept_id` int(11) DEFAULT NULL,
  `table_name` varchar(50) DEFAULT NULL,
  `attribute_name` varchar(50) DEFAULT NULL,
  `default_value` text,
  `select_multiple` tinyint(1) NOT NULL DEFAULT '0',
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`field_id`),
  UNIQUE KEY `field_uuid_index` (`uuid`),
  KEY `field_retired_status` (`retired`),
  KEY `user_who_changed_field` (`changed_by`),
  KEY `concept_for_field` (`concept_id`),
  KEY `user_who_created_field` (`creator`),
  KEY `type_of_field` (`field_type`),
  KEY `user_who_retired_field` (`retired_by`),
  CONSTRAINT `concept_for_field` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `type_of_field` FOREIGN KEY (`field_type`) REFERENCES `field_type` (`field_type_id`),
  CONSTRAINT `user_who_changed_field` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_created_field` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_retired_field` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field`
--


--
-- Table structure for table `field_answer`
--

DROP TABLE IF EXISTS `field_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `field_answer` (
  `field_id` int(11) NOT NULL DEFAULT '0',
  `answer_id` int(11) NOT NULL DEFAULT '0',
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`field_id`,`answer_id`),
  UNIQUE KEY `field_answer_uuid_index` (`uuid`),
  KEY `field_answer_concept` (`answer_id`),
  KEY `user_who_created_field_answer` (`creator`),
  CONSTRAINT `answers_for_field` FOREIGN KEY (`field_id`) REFERENCES `field` (`field_id`),
  CONSTRAINT `field_answer_concept` FOREIGN KEY (`answer_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `user_who_created_field_answer` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field_answer`
--


--
-- Table structure for table `field_type`
--

DROP TABLE IF EXISTS `field_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `field_type` (
  `field_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` text,
  `is_set` tinyint(1) NOT NULL DEFAULT '0',
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`field_type_id`),
  UNIQUE KEY `field_type_uuid_index` (`uuid`),
  KEY `user_who_created_field_type` (`creator`),
  CONSTRAINT `user_who_created_field_type` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field_type`
--

INSERT INTO `field_type` VALUES (1,'Concept','',0,1,'2005-02-22 00:00:00','8d5e7d7c-c2cc-11de-8d13-0010c6dffd0f'),(2,'Database element','',0,1,'2005-02-22 00:00:00','8d5e8196-c2cc-11de-8d13-0010c6dffd0f'),(3,'Set of Concepts','',1,1,'2005-02-22 00:00:00','8d5e836c-c2cc-11de-8d13-0010c6dffd0f'),(4,'Miscellaneous Set','',1,1,'2005-02-22 00:00:00','8d5e852e-c2cc-11de-8d13-0010c6dffd0f'),(5,'Section','',1,1,'2005-02-22 00:00:00','8d5e86fa-c2cc-11de-8d13-0010c6dffd0f');

--
-- Table structure for table `form`
--

DROP TABLE IF EXISTS `form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `form` (
  `form_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `version` varchar(50) NOT NULL DEFAULT '',
  `build` int(11) DEFAULT NULL,
  `published` tinyint(1) NOT NULL DEFAULT '0',
  `xslt` text,
  `template` text,
  `description` text,
  `encounter_type` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retired_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`form_id`),
  UNIQUE KEY `form_uuid_index` (`uuid`),
  KEY `form_published_index` (`published`),
  KEY `form_retired_index` (`retired`),
  KEY `form_published_and_retired_index` (`published`,`retired`),
  KEY `user_who_last_changed_form` (`changed_by`),
  KEY `user_who_created_form` (`creator`),
  KEY `form_encounter_type` (`encounter_type`),
  KEY `user_who_retired_form` (`retired_by`),
  CONSTRAINT `form_encounter_type` FOREIGN KEY (`encounter_type`) REFERENCES `encounter_type` (`encounter_type_id`),
  CONSTRAINT `user_who_created_form` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_last_changed_form` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_retired_form` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form`
--


--
-- Table structure for table `form_field`
--

DROP TABLE IF EXISTS `form_field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `form_field` (
  `form_field_id` int(11) NOT NULL AUTO_INCREMENT,
  `form_id` int(11) NOT NULL DEFAULT '0',
  `field_id` int(11) NOT NULL DEFAULT '0',
  `field_number` int(11) DEFAULT NULL,
  `field_part` varchar(5) DEFAULT NULL,
  `page_number` int(11) DEFAULT NULL,
  `parent_form_field` int(11) DEFAULT NULL,
  `min_occurs` int(11) DEFAULT NULL,
  `max_occurs` int(11) DEFAULT NULL,
  `required` tinyint(1) NOT NULL DEFAULT '0',
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `sort_weight` double DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`form_field_id`),
  UNIQUE KEY `form_field_uuid_index` (`uuid`),
  KEY `user_who_last_changed_form_field` (`changed_by`),
  KEY `user_who_created_form_field` (`creator`),
  KEY `field_within_form` (`field_id`),
  KEY `form_containing_field` (`form_id`),
  KEY `form_field_hierarchy` (`parent_form_field`),
  CONSTRAINT `field_within_form` FOREIGN KEY (`field_id`) REFERENCES `field` (`field_id`),
  CONSTRAINT `form_containing_field` FOREIGN KEY (`form_id`) REFERENCES `form` (`form_id`),
  CONSTRAINT `form_field_hierarchy` FOREIGN KEY (`parent_form_field`) REFERENCES `form_field` (`form_field_id`),
  CONSTRAINT `user_who_created_form_field` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_last_changed_form_field` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_field`
--


--
-- Table structure for table `form_resource`
--

DROP TABLE IF EXISTS `form_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `form_resource` (
  `form_resource_id` int(11) NOT NULL AUTO_INCREMENT,
  `form_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `value_reference` text NOT NULL,
  `datatype` varchar(255) DEFAULT NULL,
  `datatype_config` text,
  `preferred_handler` varchar(255) DEFAULT NULL,
  `handler_config` text,
  `uuid` char(38) NOT NULL,
  `date_changed` datetime DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`form_resource_id`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `unique_form_and_name` (`form_id`,`name`),
  KEY `form_resource_changed_by` (`changed_by`),
  CONSTRAINT `form_resource_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `form_resource_form_fk` FOREIGN KEY (`form_id`) REFERENCES `form` (`form_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_resource`
--


--
-- Table structure for table `global_property`
--

DROP TABLE IF EXISTS `global_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `global_property` (
  `property` varchar(255) NOT NULL DEFAULT '',
  `property_value` text,
  `description` text,
  `uuid` char(38) NOT NULL,
  `datatype` varchar(255) DEFAULT NULL,
  `datatype_config` text,
  `preferred_handler` varchar(255) DEFAULT NULL,
  `handler_config` text,
  `date_changed` datetime DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`property`),
  UNIQUE KEY `global_property_uuid_index` (`uuid`),
  KEY `global_property_property_index` (`property`),
  KEY `global_property_changed_by` (`changed_by`),
  CONSTRAINT `global_property_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `global_property`
--

INSERT INTO `global_property` VALUES ('allergy.allergen.ConceptClasses','Drug,MedSet','A comma-separated list of the allowed concept classes for the allergen field of the allergy dialog','fce8f66c-98b8-4829-a5af-d063376cd5c3',NULL,NULL,NULL,NULL,NULL,NULL),('allergy.concept.allergen.drug','162552AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA','UUID for the drug allergens concept','2d1d37a4-3efb-40ec-9a17-8579cf2032df',NULL,NULL,NULL,NULL,NULL,NULL),('allergy.concept.allergen.environment','162554AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA','UUID for the environment allergens concept','8d6366e2-300d-454d-97ce-a48c5d8daa6f',NULL,NULL,NULL,NULL,NULL,NULL),('allergy.concept.allergen.food','162553AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA','UUID for the food allergens concept','b7bcbd2e-600a-447a-810f-8f67cdfabd67',NULL,NULL,NULL,NULL,NULL,NULL),('allergy.concept.otherNonCoded','5622AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA','UUID for the allergy other non coded concept','ca8e9a2e-938a-47aa-a089-0e89d66cf4d2',NULL,NULL,NULL,NULL,NULL,NULL),('allergy.concept.reactions','162555AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA','UUID for the allergy reactions concept','af3ded68-92b6-4a1d-a35a-ee2b77ccb05e',NULL,NULL,NULL,NULL,NULL,NULL),('allergy.concept.severity.mild','1498AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA','UUID for the MILD severity concept','f505b8de-d1b2-4534-9efd-1e8264f7ed32',NULL,NULL,NULL,NULL,NULL,NULL),('allergy.concept.severity.moderate','1499AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA','UUID for the MODERATE severity concept','3fadb92c-625a-430e-b37d-bca02a372eb7',NULL,NULL,NULL,NULL,NULL,NULL),('allergy.concept.severity.severe','1500AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA','UUID for the SEVERE severity concept','e341f68a-aff4-47b3-b4ba-b184d8421e6f',NULL,NULL,NULL,NULL,NULL,NULL),('allergy.concept.unknown','1067AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA','UUID for the allergy unknown concept','c64807c5-e56c-4b0f-8ccb-a3f56445758e',NULL,NULL,NULL,NULL,NULL,NULL),('allergy.reaction.ConceptClasses','Symptom','A comma-separated list of the allowed concept classes for the reaction field of the allergy dialog','96701d68-5ab0-4169-8b3b-a6fc274040ae',NULL,NULL,NULL,NULL,NULL,NULL),('application.name','OpenMRS','The name of this application, as presented to the user, for example on the login and welcome pages.','70a9ffe6-b81e-412c-971e-a9f4af4b2c78',NULL,NULL,NULL,NULL,NULL,NULL),('concept.defaultConceptMapType','NARROWER-THAN','Default concept map type which is used when no other is set','a30bba85-da4e-47a5-94cd-7b517f800728',NULL,NULL,NULL,NULL,NULL,NULL),('concept.false','2','Concept id of the concept defining the FALSE boolean concept','0c7e3e92-9b0d-4640-b85d-4636c03c0fc0',NULL,NULL,NULL,NULL,NULL,NULL),('concept.height','5090','Concept id of the concept defining the HEIGHT concept','e2e630b9-0b08-4326-bf80-a2332995e616',NULL,NULL,NULL,NULL,NULL,NULL),('concept.medicalRecordObservations','1238','The concept id of the MEDICAL_RECORD_OBSERVATIONS concept.  This concept_id is presumed to be the generic grouping (obr) concept in hl7 messages.  An obs_group row is not created for this concept.','d5d42bb5-adbc-4f48-ab82-5f005e8c3fa0',NULL,NULL,NULL,NULL,NULL,NULL),('concept.none','1107','Concept id of the concept defining the NONE concept','3672c9a1-e054-45b7-b5ff-6e2f50ef19ba',NULL,NULL,NULL,NULL,NULL,NULL),('concept.otherNonCoded','5622','Concept id of the concept defining the OTHER NON-CODED concept','f425c65c-7e19-4fcc-aad8-971e7a573ddc',NULL,NULL,NULL,NULL,NULL,NULL),('concept.problemList','1284','The concept id of the PROBLEM LIST concept.  This concept_id is presumed to be the generic grouping (obr) concept in hl7 messages.  An obs_group row is not created for this concept.','d4b71006-5cd1-4531-aa5a-19a81d36d453',NULL,NULL,NULL,NULL,NULL,NULL),('concept.true','1','Concept id of the concept defining the TRUE boolean concept','4848ba3c-000f-463d-8f0d-8126d1c3f3f4',NULL,NULL,NULL,NULL,NULL,NULL),('concept.weight','5089','Concept id of the concept defining the WEIGHT concept','2965b593-40d3-4cbc-b346-a41d1354d16d',NULL,NULL,NULL,NULL,NULL,NULL),('conceptDrug.dosageForm.conceptClasses',NULL,'A comma-separated list of the allowed concept classes for the dosage form field of the concept drug management form.','dd1b825c-f8ea-4f73-bb8e-fd52140fd0fd',NULL,NULL,NULL,NULL,NULL,NULL),('conceptDrug.route.conceptClasses',NULL,'A comma-separated list of the allowed concept classes for the route field of the concept drug management form.','87a266b8-389e-4ebe-be96-ebc08bb4fbd7',NULL,NULL,NULL,NULL,NULL,NULL),('concepts.locked','false','if true, do not allow editing concepts','d627eca5-be22-4f30-8f42-2740efd65f7b','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('concept_map_type_management.enable','false','Enables or disables management of concept map types','9970264d-ca40-4686-a5e9-f284f99dd774','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('dashboard.encounters.maximumNumberToShow','3','An integer which, if specified, would determine the maximum number of encounters to display on the encounter tab of the patient dashboard.','c73d7a2f-63d1-4f18-a7b8-fece02f3d0c7',NULL,NULL,NULL,NULL,NULL,NULL),('dashboard.encounters.providerDisplayRoles',NULL,'A comma-separated list of encounter roles (by name or id). Providers with these roles in an encounter will be displayed on the encounter tab of the patient dashboard.','54fe463c-fd14-45cd-b9ca-987750cf258b',NULL,NULL,NULL,NULL,NULL,NULL),('dashboard.encounters.showEditLink','true','true/false whether or not to show the \'Edit Encounter\' link on the patient dashboard','bccfc7fc-0e31-4d88-af4c-ad0ff4687767','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('dashboard.encounters.showEmptyFields','true','true/false whether or not to show empty fields on the \'View Encounter\' window','5e93316d-c3df-452a-871f-5d1d69a081fc','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('dashboard.encounters.showViewLink','true','true/false whether or not to show the \'View Encounter\' link on the patient dashboard','410703a1-1297-4439-9b44-0a3f70f931af','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('dashboard.encounters.usePages','smart','true/false/smart on how to show the pages on the \'View Encounter\' window.  \'smart\' means that if > 50% of the fields have page numbers defined, show data in pages','d8c12e3c-5f90-4463-93a2-a0e3bfdd5901',NULL,NULL,NULL,NULL,NULL,NULL),('dashboard.header.programs_to_show',NULL,'List of programs to show Enrollment details of in the patient header. (Should be an ordered comma-separated list of program_ids or names.)','20e6c98e-e17b-417d-822e-25a06d4e944a',NULL,NULL,NULL,NULL,NULL,NULL),('dashboard.header.showConcept','5497','Comma delimited list of concepts ids to show on the patient header overview','0f887680-d145-4375-92c4-3736a99e3500',NULL,NULL,NULL,NULL,NULL,NULL),('dashboard.header.workflows_to_show',NULL,'List of programs to show Enrollment details of in the patient header. List of workflows to show current status of in the patient header. These will only be displayed if they belong to a program listed above. (Should be a comma-separated list of program_workflow_ids.)','7aa0adba-0e82-46f1-b194-5eceea29de30',NULL,NULL,NULL,NULL,NULL,NULL),('dashboard.metadata.caseConversion',NULL,'Indicates which type automatic case conversion is applied to program/workflow/state in the patient dashboard. Valid values: lowercase, uppercase, capitalize. If empty no conversion is applied.','1bab6838-cff6-4698-9574-7e6f9cde81cd',NULL,NULL,NULL,NULL,NULL,NULL),('dashboard.overview.showConcepts',NULL,'Comma delimited list of concepts ids to show on the patient dashboard overview tab','60da15d2-7b9c-4f91-ac52-9851f894cd64',NULL,NULL,NULL,NULL,NULL,NULL),('dashboard.regimen.displayDrugSetIds','ANTIRETROVIRAL DRUGS,TUBERCULOSIS TREATMENT DRUGS','Drug sets that appear on the Patient Dashboard Regimen tab. Comma separated list of name of concepts that are defined as drug sets.','a9dcfd5f-91a3-4847-ad5d-842d4ad44bad',NULL,NULL,NULL,NULL,NULL,NULL),('dashboard.regimen.displayFrequencies','7 days/week,6 days/week,5 days/week,4 days/week,3 days/week,2 days/week,1 days/week','Frequency of a drug order that appear on the Patient Dashboard. Comma separated list of name of concepts that are defined as drug frequencies.','f248d510-1e70-4002-bad6-7d2b85d9ddbf',NULL,NULL,NULL,NULL,NULL,NULL),('dashboard.relationships.show_types',NULL,'Types of relationships separated by commas.  Doctor/Patient,Parent/Child','bbea35bd-1b2b-4a8b-90f5-af61c1eca1aa',NULL,NULL,NULL,NULL,NULL,NULL),('dashboard.showPatientName','false','Whether or not to display the patient name in the patient dashboard title. Note that enabling this could be security risk if multiple users operate on the same computer.','7f736ae8-b0ba-467d-83ee-16f96d104407','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('datePicker.weekStart','0','First day of the week in the date picker. Domingo/Dimanche/Sunday:0  Lunes/Lundi/Monday:1','f383dfba-9206-444e-bd2b-bd7ea8f08799',NULL,NULL,NULL,NULL,NULL,NULL),('default_locale','en_GB','Specifies the default locale. You can specify both the language code(ISO-639) and the country code(ISO-3166), e.g. \'en_GB\' or just country: e.g. \'en\'','65b978b5-f3a3-4bcb-9a00-d52a437bd8e3',NULL,NULL,NULL,NULL,NULL,NULL),('default_location','Unknown Location','The name of the location to use as a system default','10f16fb1-fea5-4e6d-8496-a514fee87e2e',NULL,NULL,NULL,NULL,NULL,NULL),('default_theme',NULL,'Default theme for users.  OpenMRS ships with themes of \'green\', \'orange\', \'purple\', and \'legacy\'','e04678fd-ad37-430e-b13a-3d3983cd0cea',NULL,NULL,NULL,NULL,NULL,NULL),('drugOrder.drugOther',NULL,'Specifies the uuid of the concept which represents drug other non coded','628b93cb-617b-463b-868f-8af35fc198e3',NULL,NULL,NULL,NULL,NULL,NULL),('drugOrder.requireDrug','false','Set to value true if you need to specify a formulation(Drug) when creating a drug order.','b92fe8fb-d0b5-48e5-98a2-64272d2e9fb0',NULL,NULL,NULL,NULL,NULL,NULL),('drugOrder.requireOutpatientQuantity','true','true/false whether to require quantity, quantityUnits, and numRefills for outpatient drug orders','9f187530-01d1-48e2-9e33-992bb1f69be2',NULL,NULL,NULL,NULL,NULL,NULL),('encounterForm.obsSortOrder','number','The sort order for the obs listed on the encounter edit form.  \'number\' sorts on the associated numbering from the form schema.  \'weight\' sorts on the order displayed in the form schema.','1aa693d0-5ede-45af-9d93-58bbb9fe917c',NULL,NULL,NULL,NULL,NULL,NULL),('EncounterType.encounterTypes.locked','false','saving, retiring or deleting an Encounter Type is not permitted, if true','8df2e616-004a-4f22-8256-f3596e25061f','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('fhir.allergy.strategy','DefaultAllergyStrategy','Set allergy strategy','45a5de56-8188-41a6-a5ee-676507fee379',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.allergy.strategy.concept.uuid','1234','Concept id for allergy obs','92b3f282-8096-48b9-a1bf-8708f72156ec',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.appointment.strategy','DefaultAppointmentStrategy','Set appointment strategy.','88767617-d113-4bcb-bfa2-9bd0dbfce380',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.baseUrl','http://localhost:8080/openmrs/ws/fhir','The URI prefix through which clients consuming FHIR services will connect to the web application, should\nbe of the form http://{ipAddress}:{port}/{contextPath}/ws/fhir','3b3afcb2-12cb-4010-8df5-693a8292ccf7',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.concept.codingSystem','OPENMRS','Concept Coding System','b926343e-7c61-4689-bb5d-24130853ceb7',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.concepts.conditions',NULL,'The set of IDs of the concepts that can be matched to conditions which is needed to be stored as\nObservations','9a2878b3-ee2b-4044-addf-a0ccd5864ab4',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.condition.strategy','DefaultConditionStrategy','Set condition strategy.','00a5e26d-de18-4e85-b782-e190cbd1b126',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.customNarrativePropertiesPath','classpath:/com/foo/customnarrative.properties','Set custom narratives property path','44edf8ff-23dd-4fea-81f7-e89b247c8f54',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.diagnosticreport.imagingstudy','0123456789','Concept Id for \'ImagingStudy\' field in http://www.hl7.org/FHIR/2015May/diagnosticreport.html','ad4601bb-d2f6-4966-a46e-149a75649ac1',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.diagnosticreport.name','0123456789','Concept Id for \'Name\' field in http://www.hl7.org/FHIR/2015May/diagnosticreport.html','3f7d3bbc-a172-4835-a4a5-1e33e561ccd3',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.diagnosticreport.presentedform','0123456789','Concept Id for \'PresentedForm\' field in http://www.hl7.org/FHIR/2015May/diagnosticreport.html','5a0807a0-ee6b-4e60-9741-93f24473dd94',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.diagnosticreport.radiology.server','http://fhir-dev.healthintersections.com.au/open','The URL of third party FHIR base server that use to retrieve Radiology Diagnostic Reports','583772ce-6ccd-4390-8ca4-b40d217d6223',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.diagnosticreport.result','0123456789','Concept Id for \'Result\' field in http://www.hl7.org/FHIR/2015May/diagnosticreport.html','bc69a6b2-c871-496a-b92c-3db22c36ec0e',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.diagnosticreport.status','0123456789','Concept Id for \'Status\' field in http://www.hl7.org/FHIR/2015May/diagnosticreport.html','b9313fd4-2d5d-4e71-a0dc-aad5dae55321',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.encounter.encounterRoleUuid','73bbb069-9781-4afc-a9d1-54b6b2270e03','Encounter Role uuid','d6da2795-cb4a-4f87-bf57-47911e1061c3',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.encounter.encounterType.DEFAULT','01234-abcde-56789','EncounterType uuid for Default (not available in http://hl7.org/implement/standards/fhir/v2/0074/index\n.html)','51e71b6a-d93f-4d10-9d5a-22209fdc3439',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.encounter.encounterType.LAB','01234-abcde-56789','EncounterType uuid for Laboratory in http://hl7.org/implement/standards/fhir/v2/0074/index.html','57147db9-de11-42a2-a6c7-d24d3dc99bdb',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.encounter.encounterType.RAD','01234-abcde-56789','EncounterType uuid for Radiology in http://hl7.org/implement/standards/fhir/v2/0074/index.html','fad37200-37f5-4599-a6cc-34b9f201d032',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.encounter.strategy','DefaultEncounterStrategy','Set encounter strategy.','43535329-29f6-4997-84db-55c1e492f4be',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.group.strategy','DefaultGroupStrategy','Set group strategy.','3cc43742-4eef-4071-b49d-76f4f2142f76',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.imagingstudy.series','0123456789','Concept Id for \'ImagingStudy.Series\' field in http://www.hl7.org/FHIR/2015May/diagnosticreport.html','f54d57c9-3781-47e5-b250-a3ba5215fa19',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.imagingstudy.series.instance','0123456789','Concept Id for \'ImagingStudy.Series.Instance\' field in\nhttp://www.hl7.org/FHIR/2015May/diagnosticreport.html','8d0f6f53-37cc-48e4-b121-9e50541a4e76',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.imagingstudy.series.instance.content','0123456789','Concept Id for \'ImagingStudy.Series.Content\' field in http://www.hl7.org/FHIR/2015May/imagingstudy.html','adad915c-ae97-463f-8d5e-945924fda329',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.isCustomerNarrativesEnabled','false','Set true if you need to enable custom narratives','ceb6f87c-fda1-41d0-a423-0e7adb096ccf',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.location.strategy','DefaultLocationStrategy','Set location strategy','c4916330-0703-4df1-8a58-056e9c245db8',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.mandatory','false','true/false whether or not the fhir module MUST start when openmrs starts.  This is used to make sure that mission critical modules are always running if openmrs is running.','608b72af-c8d1-4558-b57b-c8b9ded40494',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.medication.strategy','DefaultMedicationStrategy','Set medication strategy.','021111d3-c598-4667-8421-db840f892028',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.medicationRequest.strategy','DefaultMedicationRequestStrategy','Set medication request strategy.','ecf4235d-2228-4bca-afa3-ab092af9ffe6',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.observation.strategy','DefaultObservationStrategy','Set observation strategy.','5e558ffb-5d5b-4717-8470-ac77ca1d8fe1',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.patient.strategy','DefaultPatientStrategy','Set patient strategy.','7522a92c-b586-4f52-afe3-586035f5cb40',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.person.strategy','DefaultPersonStrategy','Set person strategy.','917f5eec-e13f-481d-bc47-46d15b411404',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.practitioner.strategy','DefaultPractitionerStrategy','Set practitioner strategy.','51d548a7-845c-41d2-9467-3393225edad4',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.procedureRequest.strategy','DefaultProcedureRequestStrategy','Set procedure request strategy.','b9c1774f-2b86-4545-88ef-bf7bd9670dc6',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.relatedPerson.strategy','DefaultRelatedPersonStrategy','Set related person strategy.','1e316f64-2462-47ee-96d7-00db5c3ac2aa',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.started','true','DO NOT MODIFY. true/false whether or not the fhir module has been started.  This is used to make sure modules that were running  prior to a restart are started again','9be784ab-3120-4ccf-adb4-d9884eea3781',NULL,NULL,NULL,NULL,NULL,NULL),('fhir.uriPrefix',NULL,'The URI prefix through which clients consuming web services will connect to the web application, should\nbe of the form http://{ipAddress}:{port}/{contextPath}','23cf0a7d-5a0d-42e2-9345-92515497d614',NULL,NULL,NULL,NULL,NULL,NULL),('fhir2.administeringEncounterRoleUuid','546cce2d-6d58-4097-ba92-206c1a2a0462','Set administering encounter role uuid','48ac5c69-108f-402e-b7f8-21c3498b56d8',NULL,NULL,NULL,NULL,NULL,NULL),('fhir2.immunizationsEncounterTypeUuid','29c02aff-9a93-46c9-bf6f-48b552fcb1fa','Set immunizations encounter type uuid','bf908796-45fc-4a18-abd4-1a90dd971617',NULL,NULL,NULL,NULL,NULL,NULL),('fhir2.locationContactPointAttributeTypeUuid','abcde432-1691-11df-97a5-7038c432abcd','Set location attribute type uuid','23497eb7-026c-48f9-a359-fa65a6af0359',NULL,NULL,NULL,NULL,NULL,NULL),('fhir2.mandatory','false','true/false whether or not the fhir2 module MUST start when openmrs starts.  This is used to make sure that mission critical modules are always running if openmrs is running.','57c985b4-0aef-42d4-b278-c0b8e48d3555',NULL,NULL,NULL,NULL,NULL,NULL),('fhir2.narrativesOverridePropertyFile',NULL,'Path of narrative override properties file','3178b63d-2c20-4fbc-830f-f355d67053f4',NULL,NULL,NULL,NULL,NULL,NULL),('fhir2.paging.default','10','Set default page size','9596844a-d42d-4028-85b3-54a7fc569f56',NULL,NULL,NULL,NULL,NULL,NULL),('fhir2.paging.maximum','100','Set maximum page size','f55b2f85-38c0-441b-b930-3509775e8da1',NULL,NULL,NULL,NULL,NULL,NULL),('fhir2.personAttributeTypeUuid','14d4f066-15f5-102d-96e4-000c29c2a5d7','Set person attribute type uuid','a4ca6081-f268-44ff-8a9f-72015cd16ffa',NULL,NULL,NULL,NULL,NULL,NULL),('fhir2.personContactPointAttributeTypeUuid','14d4f066-15f5-102d-96e4-000c29c2a5d7','Set person attribute type uuid','1faa9375-c498-4177-a46f-80c3af9a3aad',NULL,NULL,NULL,NULL,NULL,NULL),('fhir2.providerContactPointAttributeTypeUuid','5021b1a1-e7f6-44b4-ba02-da2f2bcf8718','Set provider attribute type uuid','fdc1f2ee-e431-429b-b3fa-e880299a6e62',NULL,NULL,NULL,NULL,NULL,NULL),('fhir2.started','true','DO NOT MODIFY. true/false whether or not the fhir2 module has been started.  This is used to make sure modules that were running  prior to a restart are started again','9f116b2b-3b06-41f4-93c1-d57c0455da94',NULL,NULL,NULL,NULL,NULL,NULL),('fhir2.uriPrefix',NULL,'Prefix for the FHIR server in case this cannot be automatically detected','3ad5be47-569e-44f2-941d-3bc513b868c1',NULL,NULL,NULL,NULL,NULL,NULL),('FormEntry.enableDashboardTab','true','true/false whether or not to show a Form Entry tab on the patient dashboard','3fde6d28-7f25-4942-a320-60b6db6e3317','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('FormEntry.enableOnEncounterTab','false','true/false whether or not to show a Enter Form button on the encounters tab of the patient dashboard','724c01d6-bf7f-47ac-8710-32f547da0323','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('forms.locked','false','Set to a value of true if you do not want any changes to be made on forms, else set to false.','c0e7261f-b3af-47f0-bff0-fee793d71a4e',NULL,NULL,NULL,NULL,NULL,NULL),('graph.color.absolute','rgb(20,20,20)','Color of the \'invalid\' section of numeric graphs on the patient dashboard.','8aa65e68-237f-48eb-92a6-f8f088702e74',NULL,NULL,NULL,NULL,NULL,NULL),('graph.color.critical','rgb(200,0,0)','Color of the \'critical\' section of numeric graphs on the patient dashboard.','f3764762-d554-45aa-bd36-ef67d4b6b195',NULL,NULL,NULL,NULL,NULL,NULL),('graph.color.normal','rgb(255,126,0)','Color of the \'normal\' section of numeric graphs on the patient dashboard.','6a081d2d-33fb-44c4-8111-f0229dd6223a',NULL,NULL,NULL,NULL,NULL,NULL),('gzip.enabled','false','Set to \'true\' to turn on OpenMRS\'s gzip filter, and have the webapp compress data before sending it to any client that supports it. Generally use this if you are running Tomcat standalone. If you are running Tomcat behind Apache, then you\'d want to use Apache to do gzip compression.','b8af470a-7b45-4049-b390-766fa7aee198','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('hl7_archive.dir','hl7_archives','The default name or absolute path for the folder where to write the hl7_in_archives.','cd1db831-d389-4ca9-b49c-11c139b09f7e',NULL,NULL,NULL,NULL,NULL,NULL),('hl7_processor.ignore_missing_patient_non_local','false','If true, hl7 messages for patients that are not found and are non-local will silently be dropped/ignored','fd4932ff-b66d-487a-a1a4-daeb51f5129a','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('host.url',NULL,'The URL to redirect to after requesting for a password reset. Always provide a place holder in this url with name {activationKey}, it will get substituted by the actual activation key.','832028f4-b589-4e94-9b4d-079a1dd9497a',NULL,NULL,NULL,NULL,NULL,NULL),('layout.address.format','<org.openmrs.layout.address.AddressTemplate>\n    <nameMappings class=\"properties\">\n      <property name=\"postalCode\" value=\"Location.postalCode\"/>\n      <property name=\"address2\" value=\"Location.address2\"/>\n      <property name=\"address1\" value=\"Location.address1\"/>\n      <property name=\"country\" value=\"Location.country\"/>\n      <property name=\"stateProvince\" value=\"Location.stateProvince\"/>\n      <property name=\"cityVillage\" value=\"Location.cityVillage\"/>\n    </nameMappings>\n    <sizeMappings class=\"properties\">\n      <property name=\"postalCode\" value=\"10\"/>\n      <property name=\"address2\" value=\"40\"/>\n      <property name=\"address1\" value=\"40\"/>\n      <property name=\"country\" value=\"10\"/>\n      <property name=\"stateProvince\" value=\"10\"/>\n      <property name=\"cityVillage\" value=\"10\"/>\n    </sizeMappings>\n    <lineByLineFormat>\n      <string>address1</string>\n      <string>address2</string>\n      <string>cityVillage stateProvince country postalCode</string>\n    </lineByLineFormat>\n   <requiredElements>\\n\" + \" </requiredElements>\\n\" + \" </org.openmrs.layout.address.AddressTemplate>','XML description of address formats','e3f75f1a-4b6d-45b0-8170-5d7bbd94ed5b',NULL,NULL,NULL,NULL,NULL,NULL),('layout.name.format','short','Format in which to display the person names.  Valid values are short, long','f52f97c7-afd1-4f6d-801c-7575a1972d2f',NULL,NULL,NULL,NULL,NULL,NULL),('legacyui.enableExitFromCare','false','true/false whether or not to show the Exit / Resume Care button on the patient dashboard','738c1535-eb9c-4de7-9d5f-6897997de7e0',NULL,NULL,NULL,NULL,NULL,NULL),('legacyui.mandatory','false','true/false whether or not the legacyui module MUST start when openmrs starts.  This is used to make sure that mission critical modules are always running if openmrs is running.','e8a9f4e5-ba5e-4824-9eb6-208b846f7297',NULL,NULL,NULL,NULL,NULL,NULL),('legacyui.started','true','DO NOT MODIFY. true/false whether or not the legacyui module has been started.  This is used to make sure modules that were running  prior to a restart are started again','168221af-3d23-4921-9017-fea69bec6bf9',NULL,NULL,NULL,NULL,NULL,NULL),('locale.allowed.list','en, en_GB, es, fr, it, pt','Comma delimited list of locales allowed for use on system','8e07e719-5063-4bae-a0aa-ce910204fb82',NULL,NULL,NULL,NULL,NULL,NULL),('location.field.style','default','Type of widget to use for location fields','97e94546-2ad0-48ed-ab89-764cd1e3498e',NULL,NULL,NULL,NULL,NULL,NULL),('log.layout','%p - %C{1}.%M(%L) |%d{ISO8601}| %m%n','A log layout pattern which is used by the OpenMRS file appender.','fb721763-e31c-427d-98d0-5deab4040cf2',NULL,NULL,NULL,NULL,NULL,NULL),('log.level','org.openmrs.api:info','Logging levels for log4j.xml. Valid format is class:level,class:level. If class not specified, \'org.openmrs.api\' presumed. Valid levels are trace, debug, info, warn, error or fatal','6a2d3079-9058-4cdc-b5de-eb0c0d138f6b',NULL,NULL,NULL,NULL,NULL,NULL),('log.location',NULL,'A directory where the OpenMRS log file appender is stored. The log file name is \'openmrs.log\'.','0f8bfee8-5fc2-4388-b94f-039efc8feeb3',NULL,NULL,NULL,NULL,NULL,NULL),('login.url','owa/addonmanager/index.html','Responsible for defining the Authentication URL','2778eb63-8654-4a40-8991-716dc7b442da',NULL,NULL,NULL,NULL,NULL,NULL),('mail.debug','false','true/false whether to print debugging information during mailing','1a860701-d9b3-46f7-9774-576fe790f01c',NULL,NULL,NULL,NULL,NULL,NULL),('mail.default_content_type','text/plain','Content type to append to the mail messages','69e9bcdd-0ec7-4d24-b6d9-1abb180ac7ad',NULL,NULL,NULL,NULL,NULL,NULL),('mail.from','info@openmrs.org','Email address to use as the default from address','84e88ce2-13af-4912-bd83-96a6b2bc28f1',NULL,NULL,NULL,NULL,NULL,NULL),('mail.password','test','Password for the SMTP user (if smtp_auth is enabled)','282356a3-7699-4dc7-a3de-47c3dd87a0c4',NULL,NULL,NULL,NULL,NULL,NULL),('mail.smtp.starttls.enable','false','Set to true to enable TLS encryption, else set to false','00f1624e-5274-40eb-9810-f201848d991c',NULL,NULL,NULL,NULL,NULL,NULL),('mail.smtp_auth','false','true/false whether the smtp host requires authentication','ff8c6629-0473-4a23-9dc3-1dabc980da93',NULL,NULL,NULL,NULL,NULL,NULL),('mail.smtp_host','localhost','SMTP host name','187d3901-4075-4e84-82df-2e3474390c0e',NULL,NULL,NULL,NULL,NULL,NULL),('mail.smtp_port','25','SMTP port','6917477e-a806-4dd8-9ab1-4f1afd69eda0',NULL,NULL,NULL,NULL,NULL,NULL),('mail.transport_protocol','smtp','Transport protocol for the messaging engine. Valid values: smtp','b47b2305-a708-4e9e-94f6-354af9657de7',NULL,NULL,NULL,NULL,NULL,NULL),('mail.user','test','Username of the SMTP user (if smtp_auth is enabled)','e0d51363-2f4f-49dd-bec6-e741b186005a',NULL,NULL,NULL,NULL,NULL,NULL),('minSearchCharacters','2','Number of characters user must input before searching is started.','6feda253-9bba-4439-b267-bb4d73939cd0',NULL,NULL,NULL,NULL,NULL,NULL),('module_repository_folder','modules','Name of the folder in which to store the modules','6d9a3b60-4242-4f6b-936d-aa7d872b7f50',NULL,NULL,NULL,NULL,NULL,NULL),('newPatientForm.relationships',NULL,'Comma separated list of the RelationshipTypes to show on the new/short patient form.  The list is defined like \'3a, 4b, 7a\'.  The number is the RelationshipTypeId and the \'a\' vs \'b\' part is which side of the relationship is filled in by the user.','dd2393df-1979-42f4-8af0-f8ed43445a15',NULL,NULL,NULL,NULL,NULL,NULL),('new_patient_form.showRelationships','false','true/false whether or not to show the relationship editor on the addPatient.htm screen','6a4cd7fa-7a7c-44bd-a664-8a0c9ee0920a','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('obs.complex_obs_dir','complex_obs','Default directory for storing complex obs.','533f8017-0cf4-4337-9a48-479389884a9a',NULL,NULL,NULL,NULL,NULL,NULL),('order.drugDispensingUnitsConceptUuid',NULL,'Specifies the uuid of the concept set where its members represent the possible drug dispensing units','ece911df-4091-4f62-9682-9bd1e20c9f77',NULL,NULL,NULL,NULL,NULL,NULL),('order.drugDosingUnitsConceptUuid',NULL,'Specifies the uuid of the concept set where its members represent the possible drug dosing units','0e2fedbb-05cb-4268-b989-398cd96ec7b6',NULL,NULL,NULL,NULL,NULL,NULL),('order.drugRoutesConceptUuid',NULL,'Specifies the uuid of the concept set where its members represent the possible drug routes','d8d79b8c-12bc-4706-a255-4ed4ddb93cfd',NULL,NULL,NULL,NULL,NULL,NULL),('order.durationUnitsConceptUuid',NULL,'Specifies the uuid of the concept set where its members represent the possible duration units','0be58463-2a5a-481a-99ab-eb0eeff58b91',NULL,NULL,NULL,NULL,NULL,NULL),('order.nextOrderNumberSeed','1','The next order number available for assignment','c27888b4-67cf-4a83-a52b-df391e6e6cc3',NULL,NULL,NULL,NULL,NULL,NULL),('order.orderNumberGeneratorBeanId',NULL,'Specifies spring bean id of the order generator to use when assigning order numbers','ef0370ce-1e26-4fbb-8b33-307fb1d55608',NULL,NULL,NULL,NULL,NULL,NULL),('order.testSpecimenSourcesConceptUuid',NULL,'Specifies the uuid of the concept set where its members represent the possible test specimen sources','d2c0276d-012e-4c28-8b2b-78a0f323e0f5',NULL,NULL,NULL,NULL,NULL,NULL),('owa.appBaseUrl',NULL,'The base URL from where the Open Web Apps are hosted','c4995fea-4d30-41c4-8448-f02d533b36f3',NULL,NULL,NULL,NULL,NULL,NULL),('owa.appFolderPath','/root/.OpenMRS/owa','The default location where the apps are stored on disk','539acee4-23a5-40a1-9b07-d2fec883fa56',NULL,NULL,NULL,NULL,NULL,NULL),('owa.appStoreUrl','http://modules.openmrs.org','The default URL for the OpenMRS appstore','22bb9988-b2dc-4239-94e6-d2574b5ed66d',NULL,NULL,NULL,NULL,NULL,NULL),('owa.mandatory','false','true/false whether or not the owa module MUST start when openmrs starts.  This is used to make sure that mission critical modules are always running if openmrs is running.','0bffd902-64ee-479a-93e1-aecc3b55757c',NULL,NULL,NULL,NULL,NULL,NULL),('owa.started','true','DO NOT MODIFY. true/false whether or not the owa module has been started.  This is used to make sure modules that were running  prior to a restart are started again','a3584e63-8b5c-40fc-be0e-d597b61ed41b',NULL,NULL,NULL,NULL,NULL,NULL),('patient.defaultPatientIdentifierValidator','org.openmrs.patient.impl.LuhnIdentifierValidator','This property sets the default patient identifier validator.  The default validator is only used in a handful of (mostly legacy) instances.  For example, it\'s used to generate the isValidCheckDigit calculated column and to append the string \"(default)\" to the name of the default validator on the editPatientIdentifierType form.','3793a035-e588-42a1-8166-8e71d82ba6a7',NULL,NULL,NULL,NULL,NULL,NULL),('patient.headerAttributeTypes',NULL,'A comma delimited list of PersonAttributeType names that will be shown on the patient dashboard','f156364d-c251-4db5-9363-d8d08dc27b35',NULL,NULL,NULL,NULL,NULL,NULL),('patient.identifierPrefix',NULL,'This property is only used if patient.identifierRegex is empty.  The string here is prepended to the sql indentifier search string.  The sql becomes \"... where identifier like \'<PREFIX><QUERY STRING><SUFFIX>\';\".  Typically this value is either a percent sign (%) or empty.','5bbb9985-8732-4504-91af-b64dcd0026d4',NULL,NULL,NULL,NULL,NULL,NULL),('patient.identifierRegex',NULL,'WARNING: Using this search property can cause a drop in mysql performance with large patient sets.  A MySQL regular expression for the patient identifier search strings.  The @SEARCH@ string is replaced at runtime with the user\'s search string.  An empty regex will cause a simply \'like\' sql search to be used. Example: ^0*@SEARCH@([A-Z]+-[0-9])?$','e0932acd-f673-425a-915c-80aca6c59157',NULL,NULL,NULL,NULL,NULL,NULL),('patient.identifierSearchPattern',NULL,'If this is empty, the regex or suffix/prefix search is used.  Comma separated list of identifiers to check.  Allows for faster searching of multiple options rather than the slow regex. e.g. @SEARCH@,0@SEARCH@,@SEARCH-1@-@CHECKDIGIT@,0@SEARCH-1@-@CHECKDIGIT@ would turn a request for \"4127\" into a search for \"in (\'4127\',\'04127\',\'412-7\',\'0412-7\')\"','cf40d7e5-8ad6-4220-b1aa-b2f472c5bdb6',NULL,NULL,NULL,NULL,NULL,NULL),('patient.identifierSuffix',NULL,'This property is only used if patient.identifierRegex is empty.  The string here is prepended to the sql indentifier search string.  The sql becomes \"... where identifier like \'<PREFIX><QUERY STRING><SUFFIX>\';\".  Typically this value is either a percent sign (%) or empty.','8dd597b7-bf64-4ee8-bd91-2c85fb36276a',NULL,NULL,NULL,NULL,NULL,NULL),('patient.listingAttributeTypes',NULL,'A comma delimited list of PersonAttributeType names that should be displayed for patients in _lists_','377067ed-56d8-463b-8203-d5e5a68b958c',NULL,NULL,NULL,NULL,NULL,NULL),('patient.nameValidationRegex',NULL,'Names of the patients must pass this regex. Eg : ^[a-zA-Z \\-]+$ contains only english alphabet letters, spaces, and hyphens. A value of .* or the empty string means no validation is done.','98467ec4-63e7-4c58-bf8b-b5a9b37686d4',NULL,NULL,NULL,NULL,NULL,NULL),('patient.viewingAttributeTypes',NULL,'A comma delimited list of PersonAttributeType names that should be displayed for patients when _viewing individually_','2f11c5f5-cb01-4659-8b86-e678916e6fcb',NULL,NULL,NULL,NULL,NULL,NULL),('patientIdentifierSearch.matchMode','EXACT','Specifies how patient identifiers are matched while searching for a patient. Valid values are \'EXACT, \'ANYWHERE\' or \'START\'. Defaults to \'EXACT\' if missing or invalid value is present.','edfa5c12-b773-493a-bcbd-37fa00e1d7c7',NULL,NULL,NULL,NULL,NULL,NULL),('patientIdentifierTypes.locked','false','Set to a value of true if you do not want allow editing patient identifier types, else set to false.','ac5ad00c-d34c-42a4-9fc0-7add2e6b9e1c',NULL,NULL,NULL,NULL,NULL,NULL),('patientSearch.matchMode','START','Specifies how patient names are matched while searching patient. Valid values are \'ANYWHERE\' or \'START\'. Defaults to start if missing or invalid value is present.','d27fd9f0-cf32-4ba9-ae96-9aef8d3c09e7',NULL,NULL,NULL,NULL,NULL,NULL),('patient_identifier.importantTypes',NULL,'A comma delimited list of PatientIdentifier names : PatientIdentifier locations that will be displayed on the patient dashboard.  E.g.: TRACnet ID:Rwanda,ELDID:Kenya','b154cd01-88ab-4d34-84d1-08aaa068bb44',NULL,NULL,NULL,NULL,NULL,NULL),('person.attributeSearchMatchMode','EXACT','Specifies how person attributes are matched while searching person. Valid values are \'ANYWHERE\' or \'EXACT\'. Defaults to exact if missing or invalid value is present.','fbab1410-c0a8-42f9-b6b9-fac2821ead5b',NULL,NULL,NULL,NULL,NULL,NULL),('person.searchMaxResults','1000','The maximum number of results returned by patient searches','8366e456-641d-417c-bed1-d1e39dfca984',NULL,NULL,NULL,NULL,NULL,NULL),('personAttributeTypes.locked','false','Set to a value of true if you do not want allow editing person attribute types, else set to false.','ea7214bb-e36e-465a-80b5-a56df4548f50',NULL,NULL,NULL,NULL,NULL,NULL),('provider.unknownProviderUuid',NULL,'Specifies the uuid of the Unknown Provider account','a3b38b11-972d-40c4-b695-a589251179c4',NULL,NULL,NULL,NULL,NULL,NULL),('providerSearch.matchMode','EXACT','Specifies how provider identifiers are matched while searching for providers. Valid values are START,EXACT, END or ANYWHERE','bfd6dace-da75-4a59-8839-2bdc387df9f8',NULL,NULL,NULL,NULL,NULL,NULL),('reportProblem.url','http://errors.openmrs.org/scrap','The openmrs url where to submit bug reports','b2b345e3-3ec4-49b7-aba4-cd8e16c45d18',NULL,NULL,NULL,NULL,NULL,NULL),('scheduler.password','test','Password for the OpenMRS user that will perform the scheduler activities','720609d8-d87d-47ab-9cca-77138577c5bc',NULL,NULL,NULL,NULL,NULL,NULL),('scheduler.username','admin','Username for the OpenMRS user that will perform the scheduler activities','e9ffced4-bcd0-4249-a3e0-ab968d8285a7',NULL,NULL,NULL,NULL,NULL,NULL),('search.caseSensitiveDatabaseStringComparison','false','Indicates whether database string comparison is case sensitive or not. Setting this to false for MySQL with a case insensitive collation improves search performance.','73744002-40d9-4f13-b608-54a100e5e616',NULL,NULL,NULL,NULL,NULL,NULL),('search.indexVersion','7','Indicates the index version. If it is blank, the index needs to be rebuilt.','e9ff8927-4704-48fd-b4d9-bd6f02a74ec7',NULL,NULL,NULL,NULL,NULL,NULL),('searchWidget.batchSize','200','The maximum number of search results that are returned by an ajax call','d8d57f6e-681c-43cd-b80d-afcb26581399',NULL,NULL,NULL,NULL,NULL,NULL),('searchWidget.dateDisplayFormat',NULL,'Date display format to be used to display the date somewhere in the UI i.e the search widgets and autocompletes','173b4091-838b-45a8-b5ea-c244750f143b',NULL,NULL,NULL,NULL,NULL,NULL),('searchWidget.maximumResults','2000','Specifies the maximum number of results to return from a single search in the search widgets','4fb9471e-2809-4847-927a-3ed302c1ec37',NULL,NULL,NULL,NULL,NULL,NULL),('searchWidget.runInSerialMode','false','Specifies whether the search widgets should make ajax requests in serial or parallel order, a value of true is appropriate for implementations running on a slow network connection and vice versa','5ce4b314-32f6-46e0-a82f-96357d878675','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('searchWidget.searchDelayInterval','300','Specifies time interval in milliseconds when searching, between keyboard keyup event and triggering the search off, should be higher if most users are slow when typing so as to minimise the load on the server','cd242cbd-adf6-4814-a51f-7d095fb51c85',NULL,NULL,NULL,NULL,NULL,NULL),('security.allowedFailedLoginsBeforeLockout','7','Maximum number of failed logins allowed after which username is locked out','0e9ec4bc-16ab-4178-9bfc-c009fa09da74',NULL,NULL,NULL,NULL,NULL,NULL),('security.passwordCannotMatchUsername','true','Configure whether passwords must not match user\'s username or system id','acfdc523-3752-4552-a523-727cbe697833','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('security.passwordCustomRegex',NULL,'Configure a custom regular expression that a password must match','41546c30-6b41-4990-8faf-fd2b949fb782',NULL,NULL,NULL,NULL,NULL,NULL),('security.passwordMinimumLength','8','Configure the minimum length required of all passwords','dd674912-34c4-4740-a769-5325890b9251',NULL,NULL,NULL,NULL,NULL,NULL),('security.passwordRequiresDigit','true','Configure whether passwords must contain at least one digit','51461066-4853-4d83-825e-cb3e5279347b','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('security.passwordRequiresNonDigit','true','Configure whether passwords must contain at least one non-digit','dd72ebb0-3b07-4fdd-b4fc-fc7e0061b8d3','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('security.passwordRequiresUpperAndLowerCase','true','Configure whether passwords must contain both upper and lower case characters','01b5166a-4c7e-4e18-b397-077e543b1647','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('security.validTime','600000','Specifies the duration of time in seconds for which a password reset token is valid, the default value is 10 minutes and the allowed values range from 1 minute to 12hrs','fa5f8b19-1d28-4fe7-bfb3-b7e5952d5483',NULL,NULL,NULL,NULL,NULL,NULL),('user.headerAttributeTypes',NULL,'A comma delimited list of PersonAttributeType names that will be shown on the user dashboard. (not used in v1.5)','5815b88a-8248-422a-b84c-bd3d9103d94c',NULL,NULL,NULL,NULL,NULL,NULL),('user.listingAttributeTypes',NULL,'A comma delimited list of PersonAttributeType names that should be displayed for users in _lists_','bc234f9b-b5d4-469d-96ed-710a88477b4d',NULL,NULL,NULL,NULL,NULL,NULL),('user.requireEmailAsUsername','false','Indicates whether a username must be a valid e-mail or not.','d0af30fb-8bf1-4146-88e8-373cda2e145a','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('user.viewingAttributeTypes',NULL,'A comma delimited list of PersonAttributeType names that should be displayed for users when _viewing individually_','db578d24-55e9-4fca-9d06-6140424941e4',NULL,NULL,NULL,NULL,NULL,NULL),('use_patient_attribute.healthCenter','false','Indicates whether or not the \'health center\' attribute is shown when viewing/searching for patients','3f8b28fb-ec8b-4911-947c-9d95d0e09a90','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('use_patient_attribute.mothersName','false','Indicates whether or not mother\'s name is able to be added/viewed for a patient','89754f50-d578-4538-92b0-d1faf35495f1','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('validation.disable','false','Disables validation of OpenMRS Objects. Only takes affect on next restart. Warning: only do this is you know what you are doing!','9521bf3b-deef-4b62-82b0-830cb662b901',NULL,NULL,NULL,NULL,NULL,NULL),('visits.allowOverlappingVisits','true','true/false whether or not to allow visits of a given patient to overlap','e7019d8f-80fc-4f48-b781-f74a121d81fe','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('visits.assignmentHandler','org.openmrs.api.handler.ExistingVisitAssignmentHandler','Set to the name of the class responsible for assigning encounters to visits.','68e35f99-81fd-44f5-9e3f-1fa596b7f548',NULL,NULL,NULL,NULL,NULL,NULL),('visits.autoCloseVisitType',NULL,'comma-separated list of the visit type(s) to automatically close','75fdeb65-32e7-4226-ac3c-bb87b5367be6',NULL,NULL,NULL,NULL,NULL,NULL),('visits.enabled','true','Set to true to enable the Visits feature. This will replace the \'Encounters\' tab with a \'Visits\' tab on the dashboard.','da8fc3cc-999d-4899-ac55-de2a3134c839','org.openmrs.customdatatype.datatype.BooleanDatatype',NULL,NULL,NULL,NULL,NULL),('visits.encounterTypeToVisitTypeMapping',NULL,'Specifies how encounter types are mapped to visit types when automatically assigning encounters to visits. e.g 1:1, 2:1, 3:2 in the format encounterTypeId:visitTypeId or encounterTypeUuid:visitTypeUuid or a combination of encounter/visit type uuids and ids e.g 1:759799ab-c9a5-435e-b671-77773ada74e4','cff91e5d-715b-42af-87f3-1665dc2b0fac',NULL,NULL,NULL,NULL,NULL,NULL),('webservices.rest.allowedips',NULL,'A comma-separate list of IP addresses that are allowed to access the web services. An empty string allows everyone to access all ws. \n        IPs can be declared with bit masks e.g. 10.0.0.0/30 matches 10.0.0.0 - 10.0.0.3 and 10.0.0.0/24 matches 10.0.0.0 - 10.0.0.255.','df38a223-5700-420d-bc7e-9b8fd64e19cc',NULL,NULL,NULL,NULL,NULL,NULL),('webservices.rest.mandatory','false','true/false whether or not the webservices.rest module MUST start when openmrs starts.  This is used to make sure that mission critical modules are always running if openmrs is running.','d37fada5-700c-4ce8-99c1-66bdba01786d',NULL,NULL,NULL,NULL,NULL,NULL),('webservices.rest.maxResultsAbsolute','100','The absolute max results limit. If the client requests a larger number of results, then will get an error','407399a4-adf8-444c-95e7-b1df8839a88f',NULL,NULL,NULL,NULL,NULL,NULL),('webservices.rest.maxResultsDefault','50','The default max results limit if the user does not provide a maximum when making the web service call.','5ff2af15-47f4-4111-bb0a-18d12707a048',NULL,NULL,NULL,NULL,NULL,NULL),('webservices.rest.quietDocs','true','If the value of this setting is \"true\", then nothing is logged while the Swagger specification is being generated.','efa8d529-1da7-42c0-a0a6-25dbf4b74154',NULL,NULL,NULL,NULL,NULL,NULL),('webservices.rest.started','true','DO NOT MODIFY. true/false whether or not the webservices.rest module has been started.  This is used to make sure modules that were running  prior to a restart are started again','7a4c2054-0ecf-43e7-ba03-ca18b89ce4a2',NULL,NULL,NULL,NULL,NULL,NULL),('webservices.rest.uriPrefix',NULL,'The URI prefix through which clients consuming web services will connect to the web application, should be of the form http://{ipAddress}:{port}/{contextPath}','7d9e1cab-ff03-4af3-b9b0-15a85618008d',NULL,NULL,NULL,NULL,NULL,NULL);

--
-- Table structure for table `hl7_in_archive`
--

DROP TABLE IF EXISTS `hl7_in_archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hl7_in_archive` (
  `hl7_in_archive_id` int(11) NOT NULL AUTO_INCREMENT,
  `hl7_source` int(11) NOT NULL DEFAULT '0',
  `hl7_source_key` varchar(255) DEFAULT NULL,
  `hl7_data` text NOT NULL,
  `date_created` datetime NOT NULL,
  `message_state` int(11) DEFAULT '2',
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`hl7_in_archive_id`),
  UNIQUE KEY `hl7_in_archive_uuid_index` (`uuid`),
  KEY `hl7_in_archive_message_state_idx` (`message_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hl7_in_archive`
--


--
-- Table structure for table `hl7_in_error`
--

DROP TABLE IF EXISTS `hl7_in_error`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hl7_in_error` (
  `hl7_in_error_id` int(11) NOT NULL AUTO_INCREMENT,
  `hl7_source` int(11) NOT NULL DEFAULT '0',
  `hl7_source_key` text,
  `hl7_data` text NOT NULL,
  `error` varchar(255) NOT NULL DEFAULT '',
  `error_details` mediumtext,
  `date_created` datetime NOT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`hl7_in_error_id`),
  UNIQUE KEY `hl7_in_error_uuid_index` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hl7_in_error`
--


--
-- Table structure for table `hl7_in_queue`
--

DROP TABLE IF EXISTS `hl7_in_queue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hl7_in_queue` (
  `hl7_in_queue_id` int(11) NOT NULL AUTO_INCREMENT,
  `hl7_source` int(11) NOT NULL DEFAULT '0',
  `hl7_source_key` text,
  `hl7_data` text NOT NULL,
  `message_state` int(11) NOT NULL DEFAULT '0',
  `date_processed` datetime DEFAULT NULL,
  `error_msg` text,
  `date_created` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`hl7_in_queue_id`),
  UNIQUE KEY `hl7_in_queue_uuid_index` (`uuid`),
  KEY `hl7_source_with_queue` (`hl7_source`),
  CONSTRAINT `hl7_source_with_queue` FOREIGN KEY (`hl7_source`) REFERENCES `hl7_source` (`hl7_source_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hl7_in_queue`
--


--
-- Table structure for table `hl7_source`
--

DROP TABLE IF EXISTS `hl7_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hl7_source` (
  `hl7_source_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` text,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`hl7_source_id`),
  UNIQUE KEY `hl7_source_uuid_index` (`uuid`),
  KEY `user_who_created_hl7_source` (`creator`),
  CONSTRAINT `user_who_created_hl7_source` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hl7_source`
--

INSERT INTO `hl7_source` VALUES (1,'LOCAL','',1,'2006-09-01 00:00:00','8d6b8bb6-c2cc-11de-8d13-0010c6dffd0f');

--
-- Table structure for table `liquibasechangelog`
--

DROP TABLE IF EXISTS `liquibasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `liquibasechangelog` (
  `ID` varchar(63) NOT NULL,
  `AUTHOR` varchar(63) NOT NULL,
  `FILENAME` varchar(200) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID`,`AUTHOR`,`FILENAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `liquibasechangelog`
--

INSERT INTO `liquibasechangelog` VALUES ('0','bwolfe','liquibase-update-to-latest.xml','2011-09-20 00:00:00',10016,'MARK_RAN','8:12a6014284bbf4978e29e2f37d967125',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('02232009-1141','nribeka','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10061,'EXECUTED','8:f8a821a8e041ad4f07406e8a7c1a3968','Modify Column','Modify the password column to fit the output of SHA-512 function',NULL,'2.0.5',NULL,NULL,NULL),('1','upul','liquibase-update-to-latest.xml','2021-05-09 07:08:08',10042,'MARK_RAN','8:991e6075bd349bc17d8762417c4f270d','Add Column','Add the column to person_attribute type to connect each type to a privilege',NULL,'2.0.5',NULL,NULL,NULL),('1-grant-new-dashboard-overview-tab-app-privileges','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:47',10482,'EXECUTED','8:5f69fffb89d102e1eb536301e57e29f8','Custom SQL','Granting the new patient overview tab application privileges',NULL,'2.0.5',NULL,NULL,NULL),('1-increase-privilege-col-size-privilege','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:47',10484,'EXECUTED','8:41f2d0fb85aba597736481b9c965fd67','Drop Foreign Key Constraint (x2), Modify Column, Add Foreign Key Constraint (x2)','Increasing the size of the privilege column in the privilege table',NULL,'2.0.5',NULL,NULL,NULL),('10-insert-new-app-privileges','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:47',10480,'EXECUTED','8:0ef73a3413ede0e2a7481292685fb711','Custom SQL','Inserting the new application privileges',NULL,'2.0.5',NULL,NULL,NULL),('10000000-TRUNK-6015','dkayiwa','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:02',10747,'EXECUTED','8:10276a470199bd3ce0addf3f49f68f17','addColumn tableName=encounter_diagnosis','Adding \"form_namespace_and_path\" column to encounter_diagnosis table',NULL,'4.4.1',NULL,NULL,NULL),('10000000-TRUNK-6016','dkayiwa','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:02',10748,'EXECUTED','8:f0ce0a60bdab2256858d966eabb0bc5e','addColumn tableName=allergy','Adding \"form_namespace_and_path\" column to the allergy table',NULL,'4.4.1',NULL,NULL,NULL),('10000000-TRUNK-6017','dkayiwa','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:02',10750,'EXECUTED','8:ec16bf7b6f70059ad300110b68192dc0','addColumn tableName=orders','Adding \"form_namespace_and_path\" column to the orders table',NULL,'4.4.1',NULL,NULL,NULL),('10000000-TRUNK-6018','aojwang','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:02',10751,'EXECUTED','8:ebf094385dad9b9cf36fbe62aa22b4b7','addColumn tableName=patient_state','Adding \"form_namespace_and_path\" column to the patient_state table',NULL,'4.4.1',NULL,NULL,NULL),('10000001-TRUNK-6016','dkayiwa','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:02',10749,'EXECUTED','8:76d5f880ebc99cb1739172b4a52d013f','addColumn tableName=allergy; addForeignKeyConstraint baseTableName=allergy, constraintName=allergy_encounter_id_fk, referencedTableName=encounter','Adding \'encounter_id\' column to the allergy table',NULL,'4.4.1',NULL,NULL,NULL),('10000001-TRUNK-6018','aojwang','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:02',10752,'EXECUTED','8:b4a0ccd8c23e64e4f3fd702fe943456e','addColumn tableName=patient_state; addForeignKeyConstraint baseTableName=patient_state, constraintName=patient_state_encounter_id_fk, referencedTableName=encounter','Adding \'encounter_id\' column to the patient_state table',NULL,'4.4.1',NULL,NULL,NULL),('11-insert-new-api-privileges','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:47',10481,'EXECUTED','8:8d108267a32a8414e6a7820628de2854','Custom SQL','Inserting the new API privileges',NULL,'2.0.5',NULL,NULL,NULL),('1226348923233-12','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10022,'EXECUTED','8:bb24363bbbc4fced0df16bb7f8f16f14','Insert Row (x12)','',NULL,'2.0.5',NULL,NULL,NULL),('1226348923233-13','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10023,'EXECUTED','8:31983765854eb7c5b5a17e4719e33a36','Insert Row (x2)','',NULL,'2.0.5',NULL,NULL,NULL),('1226348923233-14','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10027,'EXECUTED','8:1dd132e55df2925c4279946e865480d7','Insert Row (x4)','',NULL,'2.0.5',NULL,NULL,NULL),('1226348923233-15','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10028,'EXECUTED','8:d1033daf42ce797eec3172fb416970ac','Insert Row (x15)','',NULL,'2.0.5',NULL,NULL,NULL),('1226348923233-16','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10019,'EXECUTED','8:602bd18cfd62b1620c9409215ea87301','Insert Row','',NULL,'2.0.5',NULL,NULL,NULL),('1226348923233-17','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10030,'EXECUTED','8:1e4df341bb32471b7979789a85321705','Insert Row (x2)','',NULL,'2.0.5',NULL,NULL,NULL),('1226348923233-18','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10031,'EXECUTED','8:c34ac1c3709196543dc191ce2f0b93f2','Insert Row (x2)','',NULL,'2.0.5',NULL,NULL,NULL),('1226348923233-2','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10020,'EXECUTED','8:5cbb44a28b0ef311d891fd24efa57943','Insert Row (x5)','',NULL,'2.0.5',NULL,NULL,NULL),('1226348923233-20','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10032,'EXECUTED','8:33e813ecb9ecc9779b454b8e23169029','Insert Row','',NULL,'2.0.5',NULL,NULL,NULL),('1226348923233-21','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10033,'EXECUTED','8:d0a7367e2fa776a490a8803b845db9ec','Insert Row','',NULL,'2.0.5',NULL,NULL,NULL),('1226348923233-22','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10018,'EXECUTED','8:6f142e9cb7f38398d1cb23e72b852545','Insert Row','',NULL,'2.0.5',NULL,NULL,NULL),('1226348923233-23','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10034,'EXECUTED','8:b1f30cc0f5470c7c83572d41f5d1250a','Insert Row','',NULL,'2.0.5',NULL,NULL,NULL),('1226348923233-6','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10026,'EXECUTED','8:d4f3f0ae5bff1c976c6d6ac30e77c9d7','Insert Row (x13)','',NULL,'2.0.5',NULL,NULL,NULL),('1226348923233-8','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10021,'EXECUTED','8:efb51ea5811efd6ead825f1d72a1063b','Insert Row (x7)','',NULL,'2.0.5',NULL,NULL,NULL),('1226412230538-24','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10024,'EXECUTED','8:d8e61a7fd06bb9fe652c25af8769a23b','Insert Row (x2)','',NULL,'2.0.5',NULL,NULL,NULL),('1226412230538-7','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10025,'EXECUTED','8:85ea71083e5c1a7c2f851212278aadee','Insert Row (x106)','',NULL,'2.0.5',NULL,NULL,NULL),('1226412230538-9a','ben (generated)','liquibase-core-data.xml','2021-05-09 07:08:07',10035,'EXECUTED','8:8384d4acc1f80f90eca0f2daf46d3aa2','Insert Row (x4)','',NULL,'2.0.5',NULL,NULL,NULL),('1227123456789-100','dkayiwa','liquibase-schema-only.xml','2021-05-09 07:08:02',178,'EXECUTED','8:94df37ec6e1d989969b931fe8c1b4a10','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-1','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',1,'EXECUTED','8:b9ee4c0e8008ec77d884bc1b20d3e4af','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-10','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',8,'EXECUTED','8:5e473876e7fe6743de5d12b73a92ce67','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-100','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',114,'EXECUTED','8:b82fee3c37b72cc3cbe07eb7f884f392','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-101','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',115,'EXECUTED','8:312a5a8c66b7882abc7a29a6ea0bd7c3','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-102','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',116,'EXECUTED','8:5136a87232a949301939f363154f9d13','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-103','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',117,'EXECUTED','8:a4e06c893bc3abbba045c45ab85853a8','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-104','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',118,'EXECUTED','8:8b0af28d7870f07c6d747ea1f4f84427','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-105','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',119,'EXECUTED','8:b2a50e78f1c72368f0fe189966e0e4a4','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-106','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',120,'EXECUTED','8:9a11d3faff3bc49fd5f178c62ff6f1e8','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-107','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',121,'EXECUTED','8:5bb2d869cd940420add9767f582d3340','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-108','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',135,'EXECUTED','8:4d0fd8dfa3132aeda7fd95fce186feca','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-109','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',136,'EXECUTED','8:898a7623617966cd510cee8696044919','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-11','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',9,'EXECUTED','8:246eecf888b6e516f7c5111ca182a300','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-110','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',137,'EXECUTED','8:d9477a162e1fbc21a0d3c1b7eacfd1e1','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-111','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',138,'EXECUTED','8:0bcc6c622cb1340d70bf01da7461aef9','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-112','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',139,'EXECUTED','8:ff5d37e7cb4d30650d7e13056dc33dc5','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-115','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',140,'EXECUTED','8:8fe43a46974794123e7befe6a8b9ed28','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-116','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',141,'EXECUTED','8:907bb8476d14d066857bb9b66dd0524f','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-117','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',142,'EXECUTED','8:8711faa15c279d4c7c4b6a560e07eaa2','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-118','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',143,'EXECUTED','8:bfb62d5788118190ee02400aa6aefbba','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-119','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',144,'EXECUTED','8:7e8377c775d527df2615e4cb50df702d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-12','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',10,'EXECUTED','8:ba84f58e4897d27213c7d149dd403d7b','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-120','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',145,'EXECUTED','8:f7413bef07383112602b74b12b825e61','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-121','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',146,'EXECUTED','8:a17fdca397bd485507fc28b78ddf0939','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-122','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',147,'EXECUTED','8:8f0d4dad5f4097fe33370512cfef177e','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-123','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',148,'EXECUTED','8:33220c0b589a0b3d5e98997606293ade','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-124','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',149,'EXECUTED','8:a47bda8885b98a1354d9d5b35492665d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-125','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',150,'EXECUTED','8:08eb1f8ee0e4ea531ccfda38a33c412a','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-126','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',151,'EXECUTED','8:6acecf1ef9667b482fec5b6f5daafa20','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-128','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',152,'EXECUTED','8:9529c1e321fe781628ed212a926502d9','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-129','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',153,'EXECUTED','8:cc50810c3979bb56f574cbf6b9aa35ca','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-13','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',11,'EXECUTED','8:6471aa402a63c659dfe087491712a159','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-130','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',154,'EXECUTED','8:b9e6fd520b69b2e0cf8378a599f5af96','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-131','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',155,'EXECUTED','8:a3590505dd4113518513b471228fd9ce','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-132','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',156,'EXECUTED','8:c1f3769598fea95a686f33ea1e81450d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-134','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',157,'EXECUTED','8:2d2cf512d0f3a8ac703922f4eaa134f8','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-135','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',158,'EXECUTED','8:538229aca1977a64b71417de6f9b2203','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-136','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',159,'EXECUTED','8:42264fcc9ed9e7f0b79b3c4286883e42','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-137','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',160,'EXECUTED','8:afb2d71f356b81b14ddba3354cb16d3e','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-139','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',161,'EXECUTED','8:e522bfbd461773a55bd5cd2e302ddfa9','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-14','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',12,'EXECUTED','8:47c333878a63bb0829dbd70be5d6fbde','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-140','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',162,'EXECUTED','8:0a92c6aa00468c1138659c133b47f689','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-141','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',163,'EXECUTED','8:e7006b0ba18224ba3e65034727d920e6','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-142','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',164,'EXECUTED','8:dbe91f626277b7755414156f8e822ddf','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-143','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',165,'EXECUTED','8:0fbb640bf726e1725e1e16a9e7d9d660','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-144','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',166,'EXECUTED','8:af117aa9c1fc98e5647e06188e67897d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-145','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',167,'EXECUTED','8:a3ea4580bec548e44c13a5e4ba48291f','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-146','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',168,'EXECUTED','8:624a7378df141559081274e3b9490409','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-147','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',169,'EXECUTED','8:7fc4fc3cb040fabc59a3d896fc6db14d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-149','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',170,'EXECUTED','8:dc4fc39a9b92d489247c3ae7c39502c0','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-15','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',13,'EXECUTED','8:cf72b976506c68977da03bf631e4976a','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-150','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',171,'EXECUTED','8:82673a3ca4ce3dc22d91f7fb943238b4','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-151','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',172,'EXECUTED','8:ca8e406ac7c35a8bed355a297b9b727d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-153','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',173,'EXECUTED','8:b6aa43fc174a02cbdf41d9345ecafeef','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-154','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',174,'EXECUTED','8:fe5fcb763d71ae04ea1236c18112fb78','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-155','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',175,'EXECUTED','8:c939abf513c92137e189b9b3f0696346','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-158','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',176,'EXECUTED','8:a041ccd99fd8e3372f3a7e939e5cfa8f','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-159','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',177,'EXECUTED','8:f4a15d22f90d940a6ebb98282b104a50','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-16','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',14,'EXECUTED','8:d6e4e68dcaf2230046cdf81040580be0','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-160','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',179,'EXECUTED','8:87a27fdfa8a122a26521476f95e5b9cc','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-161','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',180,'EXECUTED','8:f442267aa1c5ceeff29e7e067dc23a39','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-162','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',181,'EXECUTED','8:a922ecb26bcd3dce8f958e0a698f7a8f','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-163','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',182,'EXECUTED','8:8c1b126ec623a54fc58e7117b2c69442','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-164','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',183,'EXECUTED','8:e036a8611996a334d4b7c1a64d373967','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-165','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',184,'EXECUTED','8:97e31f702f3477b2e6c4adf7ff6ce788','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-166','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',185,'EXECUTED','8:616a9bba053513635fd9cb319b9d2148','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-167','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',186,'EXECUTED','8:adc24af188e55646d2de3f132186b110','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-168','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',187,'EXECUTED','8:a926a454309c7f1dfb6dd9cdc3d78f56','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-169','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',188,'EXECUTED','8:3219fbfc0e1e9e2bb9647d04661a5e95','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-17','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',15,'EXECUTED','8:11e45d5067a89a3d021b79ce4ea72c98','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-170','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:02',189,'EXECUTED','8:d55b0889f9237b6f48c07be443185b50','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-171','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',190,'EXECUTED','8:8129c4e809429228a78a121492cf75ae','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-173','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',191,'EXECUTED','8:0d0490bb229e0c93937b97bd1a1da3da','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-174','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',192,'EXECUTED','8:288d97e78fe4711e2c824add03593934','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-175','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',193,'EXECUTED','8:5c81b788b71d72347efc04e2ea14e86e','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-176','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',194,'EXECUTED','8:2d2cc0fff3d1b1b87c2b8408ab9dcec8','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-177','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',195,'EXECUTED','8:fb5444c537926cc5ddb7a4d7685572c0','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-178','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',196,'EXECUTED','8:080331e4e521909eeff49029db253ac7','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-179','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',197,'EXECUTED','8:13eaab806af2e6ef373269df3a75f50c','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-18','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',16,'EXECUTED','8:0985338631bc69e03ad28cec443b4ef9','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-180','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',198,'EXECUTED','8:6f02254260667a609aa7b7bed418a054','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-181','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',199,'EXECUTED','8:f831b9d0614a2f1288cf590d4f42ff5e','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-182','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',200,'EXECUTED','8:69cf5f8af39868715a7212e3982bd6c3','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-183','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',201,'EXECUTED','8:dac4b647aac57b2521193b9d51c36875','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-184','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',202,'EXECUTED','8:3a05d0f8472d2f9616f282208ccbfb24','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-185','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',203,'EXECUTED','8:e4d0dcac58c6a439b1fd3bb29bd250bb','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-186','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',204,'EXECUTED','8:bc40972a9ca894ca341596a0e238cb20','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-187','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',205,'EXECUTED','8:fef3ce1af33eafc48c01c6a641cdc6aa','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-188','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',206,'EXECUTED','8:7bfd83a0c064489b3be2f3d3d724329a','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-189','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',207,'EXECUTED','8:d03299e4b005c3f57e21adffe84fe9e9','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-19','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',17,'EXECUTED','8:e23e36321c19a2a2d0c6de601b7399f5','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-190','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',208,'EXECUTED','8:3451a0cef626a0ea0e6880caaa07710b','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-191','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',209,'EXECUTED','8:b407ad923b164d71e62c1a54cba7f8e6','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-192','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',210,'EXECUTED','8:17f0abb8aeaea10ebaa261c07fe89819','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-193','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',211,'EXECUTED','8:1af26804285d3781e8f20f64fca2b801','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-194','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',212,'EXECUTED','8:2b2723d27a393d2a08355f2c0d690969','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-195','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',213,'EXECUTED','8:d20285f254bae676e4cf34dedb8ad204','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-196','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',214,'EXECUTED','8:df2cb7ab8e7bf606efaac0b60b09828d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-197','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',215,'EXECUTED','8:7c8f7cbb185d4b569744060f1d959dfe','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-198','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',216,'EXECUTED','8:c8a1b99634259d0853a28308b25376f0','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-199','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',217,'EXECUTED','8:122b1b781b9647cef5c75188856b2fb1','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-2','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',2,'EXECUTED','8:9fabbaa2a7e0fe524eee1752b89f46e6','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-20','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',18,'EXECUTED','8:824f4a936297d786e76ef2599a331b07','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-200','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',218,'EXECUTED','8:fac409fa9dc54f7da65eb2a91a909662','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-201','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',219,'EXECUTED','8:d88d1c8dbf1716b5594de5d966bd4c7a','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-202','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',220,'EXECUTED','8:931354889d25441e582e4a6906a8cf39','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-203','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',221,'EXECUTED','8:6b88af8eeada64d06620e37c9160eed8','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-204','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',222,'EXECUTED','8:5ea0a5d44207cd1136e379c85c1f4ede','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-205','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',223,'EXECUTED','8:816a75aad04786a9ff5c6b1654754834','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-207','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',224,'EXECUTED','8:04c787d381d35902a52e5fc1ab835e62','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-208','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',225,'EXECUTED','8:ee57b25859180f18b59202de8eaf1a50','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-209','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',226,'EXECUTED','8:a45ba0b58b30b0d9a8039c325ff0ec86','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-21','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',19,'EXECUTED','8:967d30ff8a8c9090fce2394beb060346','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-210','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',227,'EXECUTED','8:57fc8b055c19714b325d4acc72ee8cb2','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-211','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',228,'EXECUTED','8:79b38fb2671d546d8b3aff323b5f5db7','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-212','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',229,'EXECUTED','8:1264f1181b621ea81a5fbfa4a3a1ba10','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-213','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',230,'EXECUTED','8:eae032da1de620c893e5f4097ab21ea4','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-214','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',231,'EXECUTED','8:df8e5ca1f2dfe75caea42ee631b8d945','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-215','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',232,'EXECUTED','8:52b0b69fa958558fbc793b0bb8c48c0b','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-216','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',233,'EXECUTED','8:a84ca3ad5332805b9ac730db2ecd7751','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-217','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',234,'EXECUTED','8:df378a8c646bfe7120206639191e4379','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-218','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',235,'EXECUTED','8:443c8c622ed11c4ed8ea591ed2f46a57','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-219','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:03',236,'EXECUTED','8:bfa49ce65ce129e897b193915ae9254d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-22','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',20,'EXECUTED','8:b82f19178202b32c24c35113b96f6f48','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-220','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',237,'EXECUTED','8:294cab06123ffaa612d862bb1770f507','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-221','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',238,'EXECUTED','8:b97adb659df32747d9d11f1f27861cc0','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-222','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',239,'EXECUTED','8:01a41e1499eccbceb2ee1e85f885fc6b','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-223','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',240,'EXECUTED','8:784f11544fa4fb78337d0d4726001a20','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-224','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',241,'EXECUTED','8:d708bfc19b3f72dd244e92af44a48a6f','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-225','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',242,'EXECUTED','8:b562f4552de1ee1d313ba400ad0abdcf','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-226','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',243,'EXECUTED','8:bd137d46a4117614f13149d9921a6a0c','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-227','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',244,'EXECUTED','8:ae5f09dfc18ed781f6a40eabf99b2080','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-228','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',245,'EXECUTED','8:e13f40ea2b81bd3c8f88114afea8b98b','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-229','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',246,'EXECUTED','8:a8cd37a93164fff7878cf490079e4f52','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-23','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',21,'EXECUTED','8:243fb268f2145683b0f144d818159951','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-230','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',247,'EXECUTED','8:406498a13805528d6fddc0e6c7341cf5','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-231','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',248,'EXECUTED','8:54b7296fa90a459d7c973b0dad40d4eb','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-232','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',249,'EXECUTED','8:02752f99428dde99a49b18fd526a1ff2','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-234','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',250,'EXECUTED','8:09761974f703e0f0873a2ee5fbd6010b','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-235','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',251,'EXECUTED','8:7325424371f3459d9d5e8671c6ce352d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-236','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',252,'EXECUTED','8:ddec2c62eb77d94538330c3fd4b2f74e','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-237','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',253,'EXECUTED','8:325fb31ac58f83967d393c93bc0a1caa','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-239','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',254,'EXECUTED','8:8e489eac5e3fda67030eaf19ba4fb336','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-24','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',22,'EXECUTED','8:93a08be66270fc86033d43a29d5d0fb0','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-240','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',255,'EXECUTED','8:4852e6011efe34354313a6dee16245ec','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-241','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',256,'EXECUTED','8:870085d83f6db3788fda9bbb0a5eced5','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-242','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',257,'EXECUTED','8:32c6d7fe9dd7edf50913872f5f580d36','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-243','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',258,'EXECUTED','8:234a3f86d408a26037246e51d157e978','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-244','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',259,'EXECUTED','8:877e65e37a23488650cf8da0924e397a','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-245','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',260,'EXECUTED','8:34292be5f3d5f642a205e090f42ec1d0','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-246','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',261,'EXECUTED','8:7e8a2f630b93499da5bfa14be0272128','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-247','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',262,'EXECUTED','8:512574bd869f69658c919d03dd82efaf','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-248','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',263,'EXECUTED','8:819dd227d3fa0a87c4caa787d63b5f9a','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-249','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',264,'EXECUTED','8:d139c4ee68c48efe2f15c650485c2128','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-25','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',23,'EXECUTED','8:1eefe029f3db6b92c0e8635907050b03','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-250','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',265,'EXECUTED','8:8ac3a86eebf76fe9707ac804781caf25','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-251','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',266,'EXECUTED','8:b93837b949b74ee2615d75b02466780e','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-252','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',267,'EXECUTED','8:428ddd5a90b6a2b2c64f1bc330ea1dd1','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-253','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',268,'EXECUTED','8:93d8209c11d0889d9f08da91f6f55d00','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-254','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',269,'EXECUTED','8:49980361202a7431a9eebe87fdbb163a','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-255','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',270,'EXECUTED','8:21b93409799d20c5ffe9e4b7ae772b56','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-256','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',271,'EXECUTED','8:a95ff360f7b50aaec60fd15a2177a3bc','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-257','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',272,'EXECUTED','8:177befaf326dbda388563a43bbceaf3c','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-258','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',273,'EXECUTED','8:2fc819a4127debbc5c0e3677ac3a84c2','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-259','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',274,'EXECUTED','8:8a68843cbb9ee511c31c7ade79f91f90','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-26','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',24,'EXECUTED','8:b53c6fee26203bc468bc6c8ed3c26b82','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-260','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',275,'EXECUTED','8:7a051e2c6ed52bf7095ba7a7010321a8','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-261','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',276,'EXECUTED','8:ec1887f2092d0488a2817d4c081e67b7','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-262','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',277,'EXECUTED','8:47af392b01fc954bba483d9ce08e3796','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-263','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',278,'EXECUTED','8:4de5603f0df218cdd681b0f61d8b1f49','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-264','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',279,'EXECUTED','8:2f8b67450bbe1a9855a70f119df13161','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-265','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',280,'EXECUTED','8:ce6a2b55ad00d751500690708db7b2c0','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-266','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:04',281,'EXECUTED','8:405d8706e1b750c15eef45a95a576a1d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-267','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',282,'EXECUTED','8:580d0e47dc42a73fa97dfd47ba35d082','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-268','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',283,'EXECUTED','8:69d65b39a0eed70cea696899be4dc274','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-269','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',284,'EXECUTED','8:862dbda4b3adda29d7bbd252b6a870f6','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-27','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',25,'EXECUTED','8:34d1368d781c4c2c09cd34f44d66f87d','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-270','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',285,'EXECUTED','8:d4dc63350a170a64eb9660d7946b257b','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-271','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',286,'EXECUTED','8:f10ff78209c3e95a93d2bf546e1c7be2','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-272','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',287,'EXECUTED','8:68e403d624f8c440f04eba12e5e4ca40','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-273','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',288,'EXECUTED','8:8731eef11c093e33844595b6248a8430','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-274','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',289,'EXECUTED','8:342d5a86c1f4448e5acf445db148a914','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-275','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',290,'EXECUTED','8:ec8da449622fb757d68944b3911f7686','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-276','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',291,'EXECUTED','8:cd33bac67f462bae9f7d861662d9ca55','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-277','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',292,'EXECUTED','8:f577fc315cfd4a65ac6efbda2788e190','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-278','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',293,'EXECUTED','8:a69193512a21ced5e137287a1f2e4b22','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-279','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',294,'EXECUTED','8:474f284368f5574fdf4a6787d848aa8b','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-28','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',26,'EXECUTED','8:3674baa9b7bef0377b86f968d4005c15','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-280','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',295,'EXECUTED','8:6fddf45f8f23d6b526a0c6380fdf8e75','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-281','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',296,'EXECUTED','8:2f42ecf0e297deff9ecf9fa036d1f7eb','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-282','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',297,'EXECUTED','8:8afe37c7611647b84db798745124c71a','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-283','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',298,'EXECUTED','8:1ebff8cb70f8a959fa679d866c2e4d0d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-284','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',299,'EXECUTED','8:3118ece63b5e3f681eb8ceb916b9f4c3','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-285','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',300,'EXECUTED','8:59046a70052c608b9da12ce385700208','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-286','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',301,'EXECUTED','8:9601ccbb14fdd0886df63abc9602c09f','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-287','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',302,'EXECUTED','8:ea6f5d796fc93953678e921b5c461556','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-288','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',303,'EXECUTED','8:7756dad654857babe7d182dc9e732273','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-289','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',304,'EXECUTED','8:9bdac9cd60d44aae3ecba3ca94470cd6','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-29','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',27,'EXECUTED','8:796cc934f4657168e452ddd4b446b669','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-290','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',305,'EXECUTED','8:0f723ef290712e01df3e2dc5529b83bb','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-291','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',306,'EXECUTED','8:91c3fd2eb43b91b68d18984692f6e2ae','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-292','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',307,'EXECUTED','8:9abfbd0a3bfa317acb6d2010d8b59a1d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-293','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',308,'EXECUTED','8:02b0c86ca6937b19028b3d65f2428e5f','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-294','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',309,'EXECUTED','8:d304d70729a9c588fbe374302dcbe5b0','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-295','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',310,'EXECUTED','8:7e3a3bfd45501ae6376fb0a7994ead05','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-296','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',311,'EXECUTED','8:754851b11bb6bcbd75ba8a0ca5b44cfc','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-297','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',312,'EXECUTED','8:7c42397818d1b5204b86553de075c03b','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-298','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',313,'EXECUTED','8:b0bf22ea31dc4681ea18e7160b78447e','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-299','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',314,'EXECUTED','8:486a85a30bb34f06bb8494eb66c3946f','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-30','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',28,'EXECUTED','8:5dc1d10d359dfafcd8165c2fdcdcf4f6','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-300','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',315,'EXECUTED','8:8d25ef0f1b1e47704bb555a0b9b8a0fa','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-301','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:05',316,'EXECUTED','8:db4d8e41589809f08836eb9c51c7f1f2','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-31','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',29,'EXECUTED','8:a47f9ea643e9137c2661b7cc1c739d3b','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-32','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',30,'EXECUTED','8:1ce8870ad88a2afa289c943262a799d3','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-33','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',31,'EXECUTED','8:382517f2c1d0ef153ea7a4c955bb2c98','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-34','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',32,'EXECUTED','8:b70d719a673788f00b25e6be9470fd68','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-35','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',33,'EXECUTED','8:f4812355bb8df61c9b4804edc3506a28','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-36','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',34,'EXECUTED','8:69ee3da4e5afaa90ec1bf4524b4933d4','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-37','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',35,'EXECUTED','8:93adb423282bcb7a6abc9a3461de848e','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-39','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',36,'EXECUTED','8:87eacef2b6f347c8dfb473fec966bd7d','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-4','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',3,'EXECUTED','8:f132cf2bc2b4de846dfebdcb71b880bc','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-40','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',37,'EXECUTED','8:a7619ad8304326287c2d3224c730130c','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-41','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',38,'EXECUTED','8:d08dbffd822602fdd51c912cf5139edd','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-42','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',39,'EXECUTED','8:81e833b1773e3061e1d8a99d8b509883','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-43','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',40,'EXECUTED','8:8e277b197cc5aa4adff2e12c9e8ce9b4','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-44','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',41,'EXECUTED','8:42e54dd8810de987d52880b8ca0331ee','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-45','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',42,'EXECUTED','8:c48976cd5b95055c8cee5ba93dcf706d','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-46','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',43,'EXECUTED','8:5acacd7d7891e4a59c921213408bf78a','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-47','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',44,'EXECUTED','8:91bd9e03fafe0c54c70af94cd09a397a','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-48','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',45,'EXECUTED','8:89045b42da7bd45a2b7d9f64511e60c8','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-49','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',46,'EXECUTED','8:da5a1c063e17a2170d2507d734027cf2','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-5','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',4,'EXECUTED','8:4f815b5ade565c4181842a06a914b33e','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-50','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',47,'EXECUTED','8:7d8d52ca80f9251977b942a41a01684f','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-51','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',48,'EXECUTED','8:c0e340f833fc8b0a897f766f8d28559e','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-52','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',49,'EXECUTED','8:c78aef8f5bed877f209e2929d5601632','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-53','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',50,'EXECUTED','8:21a81e0aabb467978dba93aa6ff49e56','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-54','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',51,'EXECUTED','8:ab6e0235fb941f142e9f77ab3818cecf','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-55','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',52,'EXECUTED','8:d2c349708efd64bff948c9f3628f69c7','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-56','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',53,'EXECUTED','8:055daf3aebd4c75a5222bdb5753bab1f','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-57','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',54,'EXECUTED','8:15c666579adca65ac59e50b4363f1d67','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-58','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',55,'EXECUTED','8:3c8fa58916d0f07aa5945477991420c0','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-59','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',56,'EXECUTED','8:be6bb86fa85ae84ef0eb4c1442935f7c','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-6','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',5,'EXECUTED','8:6d194162ae41c1eb56bcceb5b98fb2dd','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-60','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',57,'EXECUTED','8:7aef9bd685e05e00fd3dba5b707e6dfc','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-61','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',58,'EXECUTED','8:c97901d3ccd8a3370af9a3615d29aed7','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-62','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',59,'EXECUTED','8:31b1443b16aa921032024a6fbaa682ef','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-63','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',60,'EXECUTED','8:60f184e73c38311cfc21e3d56cb21a58','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-64','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',61,'EXECUTED','8:a730a0cf22f6948f9b236a2623ce361a','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-65','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',62,'EXECUTED','8:ea6b9e7bcd2f4a293226d2e85b0869d8','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-66','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',63,'EXECUTED','8:c135beb12cbc32b9bba0f4c46b9984a1','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-67','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',64,'EXECUTED','8:9565c50e09deaee956108205f7f0692c','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-68','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',65,'EXECUTED','8:db3c9804aec65a634360b9dae1c438ac','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-7','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',6,'EXECUTED','8:c3a514e9b9955e251475b494d9c0f37c','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-70','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',66,'EXECUTED','8:e08024edda0d46e9f3791350c20870c1','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-71','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',67,'EXECUTED','8:eb4242ca3f767f4f38d06424226b786a','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-72','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',68,'EXECUTED','8:cbf073110abbb6705f74f1edac6d0d1f','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-73','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',89,'EXECUTED','8:93bc8e07ef54744c6936f86e9bc0bce0','Add Primary Key','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-75','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',90,'EXECUTED','8:1a3ef38f92cb196a32c5bb853f0bf01a','Add Primary Key','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-77','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',91,'EXECUTED','8:220af5ccd81c66e73b1681055c6dd081','Add Primary Key','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-78','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',92,'EXECUTED','8:7cc81659ee8d69d510500dca90ccbc18','Add Primary Key','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-79','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',93,'EXECUTED','8:fc879e209ad095057724d196e38d8d0f','Add Primary Key','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-81','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',94,'EXECUTED','8:57db34a9aad33e9c3926881608d96673','Add Primary Key','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-82','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',95,'EXECUTED','8:f3a497e17b6748747270b5cffa52ad89','Add Primary Key','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-83','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',96,'EXECUTED','8:83041d405e6a4607d17e4c3bdb470f89','Add Primary Key','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-84','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',97,'EXECUTED','8:ce76d2b396e4862b93b268e0d2b38102','Add Primary Key','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-85','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',99,'EXECUTED','8:10dd421af13171c93b9a4b0c9493d2d2','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-86','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',100,'EXECUTED','8:3a1855a57c069c0e95b1c5a670b41cd1','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-87','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',101,'EXECUTED','8:fc75042ff21053a52c51cd24ccd728cb','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-88','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',102,'EXECUTED','8:4fa55c37743c667db20646e00c983055','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-89','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',103,'EXECUTED','8:6d73a8d8fb8ca5a16cb0dcff557f1529','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-9','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:00',7,'EXECUTED','8:6e038709bc309eef7574cf8f35496720','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-90','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',104,'EXECUTED','8:17ffcb676dfe120cf24afacc3c0b3a65','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-91','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',105,'EXECUTED','8:bfca481d55ccff730dd230ae65d087a9','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-92','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',106,'EXECUTED','8:03be1266ccc05cfad7efa40747d4d2cc','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-93','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',107,'EXECUTED','8:10b2cacd0dd97eae418593a4e5f5de22','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-94','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',108,'EXECUTED','8:d45b0842a9a1434cf5e06cee5d8e9ea0','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-95','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',109,'EXECUTED','8:9399e3fbfb0b7d9c67ded1c0bfd7e784','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-96','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',110,'EXECUTED','8:88961931654d72cab82a81cfaee1cde9','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-97','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',111,'EXECUTED','8:a09ae7a5d3935120e4598069c0f6a2c0','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-98','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',112,'EXECUTED','8:a89855d60cf822ff4f93457a5d9ec428','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('1227303685425-99','ben (generated)','liquibase-schema-only.xml','2021-05-09 07:08:01',113,'EXECUTED','8:69190b5ab06806605078796b6ee32294','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('2','upul','liquibase-update-to-latest.xml','2021-05-09 07:08:08',10043,'MARK_RAN','8:994eef110601ec00914894c4b6e94651','Add Foreign Key Constraint','Create the foreign key from the privilege required for to edit\n			a person attribute type and the privilege.privilege column',NULL,'2.0.5',NULL,NULL,NULL),('2-increase-privilege-col-size-rol-privilege','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10485,'EXECUTED','8:e8b24e34e6c6dc2c8170a9e30bcebc47','Drop Foreign Key Constraint, Modify Column, Add Foreign Key Constraint','Increasing the size of the privilege column in the role_privilege table',NULL,'2.0.5',NULL,NULL,NULL),('2-role-assign-new-api-privileges-to-renamed-ones','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:47',10483,'EXECUTED','8:a9864f3710971db50826543ab2e79224','Custom SQL','Assigning the new API-level privileges to roles that used to have the renamed privileges',NULL,'2.0.5',NULL,NULL,NULL),('200805281223','bmckown','liquibase-update-to-latest.xml','2021-05-09 07:08:08',10045,'MARK_RAN','8:074b13df89bf0362fb9785ae893ba013','Create Table, Add Foreign Key Constraint','Create the concept_complex table',NULL,'2.0.5',NULL,NULL,NULL),('200805281224','bmckown','liquibase-update-to-latest.xml','2021-05-09 07:08:08',10046,'MARK_RAN','8:6986fd4e1043cedec48390ee3f6306af','Add Column','Adding the value_complex column to obs.  This may take a long time if you have a large number of observations.',NULL,'2.0.5',NULL,NULL,NULL),('200805281225','bmckown','liquibase-update-to-latest.xml','2021-05-09 07:08:08',10047,'MARK_RAN','8:c031e591b89ee9e0545337e3e35db77c','Insert Row','Adding a \'complex\' Concept Datatype',NULL,'2.0.5',NULL,NULL,NULL),('200805281226','bmckown','liquibase-update-to-latest.xml','2021-05-09 07:08:08',10048,'MARK_RAN','8:684bdc9c06dbb995cb7cd1dc25f36da3','Drop Table (x2)','Dropping the mimetype and complex_obs tables as they aren\'t needed in the new complex obs setup',NULL,'2.0.5',NULL,NULL,NULL),('200809191226','smbugua','liquibase-update-to-latest.xml','2021-05-09 07:08:08',10049,'MARK_RAN','8:05957b3b575084e61560b0f982ac49b4','Add Column','Adding the hl7 archive message_state column so that archives can be tracked\n			(preCondition database_version check in place because this change was in the old format in trunk for a while)',NULL,'2.0.5',NULL,NULL,NULL),('200809191927','smbugua','liquibase-update-to-latest.xml','2021-05-09 07:08:08',10050,'MARK_RAN','8:2ae91750b725ef3a76c0a67082e948e5','Rename Column, Modify Column','Adding the hl7 archive message_state column so that archives can be tracked',NULL,'2.0.5',NULL,NULL,NULL),('200811261102','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:08',10044,'EXECUTED','8:702d63c1f4df83c47e1db3cbe5cab2b1','Update Data','Fix field property for new Tribe person attribute',NULL,'2.0.5',NULL,NULL,NULL),('200901101524','Knoll_Frank','liquibase-update-to-latest.xml','2021-05-09 07:08:08',10051,'EXECUTED','8:a9443329a76435cfd0826394e4c95c32','Modify Column','Changing datatype of drug.retire_reason from DATETIME to varchar(255)',NULL,'2.0.5',NULL,NULL,NULL),('200901130950','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:08',10052,'EXECUTED','8:4f76ab65377acf1eddfed038749ded86','Delete Data (x2)','Remove Manage Tribes and View Tribes privileges from all roles',NULL,'2.0.5',NULL,NULL,NULL),('200901130951','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10053,'EXECUTED','8:bafff0cb91a4f0d3c294e783fe2888d4','Delete Data (x2)','Remove Manage Mime Types, View Mime Types, and Purge Mime Types privilege',NULL,'2.0.5',NULL,NULL,NULL),('200901161126','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10054,'EXECUTED','8:3f0f5495529bf79177080416637013f5','Delete Data','Removed the database_version global property',NULL,'2.0.5',NULL,NULL,NULL),('20090121-0949','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10055,'EXECUTED','8:7753c9deef5448a2dc7e583f4b5ac747','Custom SQL','Switched the default xslt to use PV1-19 instead of PV1-1',NULL,'2.0.5',NULL,NULL,NULL),('20090122-0853','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10056,'EXECUTED','8:d287ee58845e22aba7f671f7c2450bae','Custom SQL, Add Lookup Table, Drop Foreign Key Constraint, Delete Data (x2), Drop Table','Remove duplicate concept name tags',NULL,'2.0.5',NULL,NULL,NULL),('20090123-0305','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10057,'MARK_RAN','8:733624adb629b21a72d150f5400acbf2','Add Unique Constraint','Add unique constraint to the tags table',NULL,'2.0.5',NULL,NULL,NULL),('20090214-2246','isherman','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10063,'EXECUTED','8:690b856ba1a294eea0ffa33129cb716a','Custom SQL','Add weight and cd4 to patientGraphConcepts user property (mysql specific)',NULL,'2.0.5',NULL,NULL,NULL),('20090214-2247','isherman','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10064,'MARK_RAN','8:210e9534dd7f6f5c1439050673176abb','Custom SQL','Add weight and cd4 to patientGraphConcepts user property (using standard sql)',NULL,'2.0.5',NULL,NULL,NULL),('200902142212','ewolodzko','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10242,'MARK_RAN','8:f44564fc206096a026c494f2b67c39a1','Add Column','Add a sortWeight field to PersonAttributeType',NULL,'2.0.5',NULL,NULL,NULL),('200902142213','ewolodzko','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10243,'EXECUTED','8:88a5dbdc582edb314cc5e9f037faf867','Custom SQL','Add default sortWeights to all current PersonAttributeTypes',NULL,'2.0.5',NULL,NULL,NULL),('20090224-1002-create-visit_type','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10384,'MARK_RAN','8:a43352c3e672ede733bee8451b5a3752','Create Table, Add Foreign Key Constraint (x3)','Create visit type table',NULL,'2.0.5',NULL,NULL,NULL),('20090224-1229','Keelhaul+bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10058,'MARK_RAN','8:6f100ecab372c2e84bd8005542fc473f','Create Table, Add Foreign Key Constraint (x2)','Add location tags table',NULL,'2.0.5',NULL,NULL,NULL),('20090224-1250','Keelhaul+bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10059,'MARK_RAN','8:61bd0a6375d678a2a488e09fffc7f5f8','Create Table, Add Foreign Key Constraint (x2), Add Primary Key','Add location tag map table',NULL,'2.0.5',NULL,NULL,NULL),('20090224-1256','Keelhaul+bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10060,'MARK_RAN','8:bace0255bf77eec2f4d6a9093b3e4f54','Add Column, Add Foreign Key Constraint','Add parent_location column to location table',NULL,'2.0.5',NULL,NULL,NULL),('20090225-1551','dthomas','liquibase-update-to-latest.xml','2011-09-15 00:00:00',10001,'MARK_RAN','8:22e7c547946a33f8fd2a5d45b13c0f13',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('20090301-1259','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10062,'EXECUTED','8:0c675d0ef999f9ae994db9b12a5c3bbb','Update Data (x2)','Fixes the description for name layout global property',NULL,'2.0.5',NULL,NULL,NULL),('20090316-1008','vanand','liquibase-update-to-latest.xml','2011-09-15 00:00:00',10000,'MARK_RAN','8:3ed8c39a3e9a9dcec6d90d853fa25d70',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('20090316-1008-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:44',10432,'EXECUTED','8:7ab0cc9af397882d5c3c8440af60763d','Modify Column (x36)','(Fixed)Changing from smallint to BOOLEAN type on BOOLEAN properties',NULL,'2.0.5',NULL,NULL,NULL),('200903210905','mseaton','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10065,'MARK_RAN','8:3253e07995aa73558d050cd9ab3871b2','Create Table, Add Foreign Key Constraint (x3)','Add a table to enable generic storage of serialized objects',NULL,'2.0.5',NULL,NULL,NULL),('200903210905-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10066,'EXECUTED','8:c98c63dc236a0d05589fec21ad4f75b5','Modify Column','(Fixed)Add a table to enable generic storage of serialized objects',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-cohort','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10071,'MARK_RAN','8:dfd349213f4e4420b7434be443f177a7','Add Column','Adding \"uuid\" column to cohort table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-concept','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10072,'MARK_RAN','8:49b29ff9e018411ef0a8bb9e6d1e6e63','Add Column','Adding \"uuid\" column to concept table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-concept_answer','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10073,'MARK_RAN','8:116ffcd364fe305a9c4b45076087f2eb','Add Column','Adding \"uuid\" column to concept_answer table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-concept_class','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10074,'MARK_RAN','8:eb32830247a967891fa82b60549a4733','Add Column','Adding \"uuid\" column to concept_class table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-concept_datatype','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10075,'MARK_RAN','8:bda3bbd0db73de9a70e398f3c2c26894','Add Column','Adding \"uuid\" column to concept_datatype table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-concept_description','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10076,'MARK_RAN','8:a0c894858dc298285e114f6408914ce9','Add Column','Adding \"uuid\" column to concept_description table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-concept_map','bwolfe','liquibase-update-to-latest.xml','2011-09-15 00:00:00',10002,'MARK_RAN','8:41f48f7496d0018bd569d4619a72ccab',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('20090402-1515-38-concept_name','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10077,'MARK_RAN','8:df3db343dd510d0d857a72fafb1ba30c','Add Column','Adding \"uuid\" column to concept_name table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-concept_name_tag','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10078,'MARK_RAN','8:07c52ba900d6f818338d1b0589dd2a8a','Add Column','Adding \"uuid\" column to concept_name_tag table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-concept_proposal','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10079,'MARK_RAN','8:e0de2b5735e30548ecbc9fab2f1c2211','Add Column','Adding \"uuid\" column to concept_proposal table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-concept_set','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10080,'MARK_RAN','8:7e8b8983a8ee1596310fa7058251e1fc','Add Column','Adding \"uuid\" column to concept_set table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-concept_source','bwolfe','liquibase-update-to-latest.xml','2011-09-15 00:00:00',10003,'MARK_RAN','8:1a338a4f1677a54a286d436e7fc7b4f5',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('20090402-1515-38-concept_state_conversion','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10081,'MARK_RAN','8:21bec53f218e6bd24cccae667e5dc85a','Add Column','Adding \"uuid\" column to concept_state_conversion table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-drug','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10082,'MARK_RAN','8:1decd3f89ba2aca40e061ea160a1b9ef','Add Column','Adding \"uuid\" column to drug table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-encounter','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10083,'MARK_RAN','8:a741c222423621abdf68535359f32898','Add Column','Adding \"uuid\" column to encounter table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-encounter_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10084,'MARK_RAN','8:139c94b0944467f861b94743fedb83a1','Add Column','Adding \"uuid\" column to encounter_type table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-field','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10085,'MARK_RAN','8:cea6c5bceb8a63aeb705aeabc9eda074','Add Column','Adding \"uuid\" column to field table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-field_answer','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10086,'MARK_RAN','8:257a42ef6e65628b2328cceddb9d79af','Add Column','Adding \"uuid\" column to field_answer table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-field_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10087,'MARK_RAN','8:3fc87b41cb19a1a936a0843b588406b8','Add Column','Adding \"uuid\" column to field_type table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-form','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10088,'MARK_RAN','8:9df9e8b138a66ee813ba3e6563dbcd28','Add Column','Adding \"uuid\" column to form table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-form_field','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10089,'MARK_RAN','8:f3fa05a70b238ad39fdc4b5f5775a170','Add Column','Adding \"uuid\" column to form_field table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-global_property','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10090,'MARK_RAN','8:3191a35266bb80aacd7dbfbcab62c881','Add Column','Adding \"uuid\" column to global_property table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-hl7_in_archive','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10091,'MARK_RAN','8:299ee90d4b77c2f8644295016756f26d','Add Column','Adding \"uuid\" column to hl7_in_archive table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-hl7_in_error','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10092,'MARK_RAN','8:c2d494cf4849455cf98b28d5787bcff8','Add Column','Adding \"uuid\" column to hl7_in_error table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-hl7_in_queue','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10093,'MARK_RAN','8:761e2adde052d212fae3ddf795305726','Add Column','Adding \"uuid\" column to hl7_in_queue table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-hl7_source','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10094,'MARK_RAN','8:03f5882475857194746c9cae19fb033d','Add Column','Adding \"uuid\" column to hl7_source table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-location','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10095,'MARK_RAN','8:fb5852c147c6552683742bb36cab712a','Add Column','Adding \"uuid\" column to location table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-location_tag','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10096,'MARK_RAN','8:9d2d045aa80786a9a3258df6332d5683','Add Column','Adding \"uuid\" column to location_tag table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-note','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10097,'MARK_RAN','8:6cddec65df48e21d60213067fa3b17b1','Add Column','Adding \"uuid\" column to note table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-notification_alert','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10098,'MARK_RAN','8:dc0b48ef8527b4051b21f288853edcd1','Add Column','Adding \"uuid\" column to notification_alert table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-notification_template','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10099,'MARK_RAN','8:282775b441053b08fe0c52a264d4129a','Add Column','Adding \"uuid\" column to notification_template table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-obs','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10100,'MARK_RAN','8:53c3e6402015533b339c62e16719b405','Add Column','Adding \"uuid\" column to obs table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-orders','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10102,'MARK_RAN','8:63d4fa7c72867778d83f761f62d1d8f0','Add Column','Adding \"uuid\" column to orders table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-order_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10101,'MARK_RAN','8:7a330740dbef60e686d1ea8b288e2c25','Add Column','Adding \"uuid\" column to order_type table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-patient_identifier','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10103,'MARK_RAN','8:9146eb4b70d16a42a4f22ec512b1ebb3','Add Column','Adding \"uuid\" column to patient_identifier table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-patient_identifier_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10104,'MARK_RAN','8:ad452810a6368848d35038295d34adf8','Add Column','Adding \"uuid\" column to patient_identifier_type table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-patient_program','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10105,'MARK_RAN','8:3b3828a5869d8534b3e9005e76864760','Add Column','Adding \"uuid\" column to patient_program table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-patient_state','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10106,'MARK_RAN','8:8adb1f93dd431670c14974af4eb9a136','Add Column','Adding \"uuid\" column to patient_state table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-person','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10107,'MARK_RAN','8:fc958fe4b7b798e07fb298193dac004a','Add Column','Adding \"uuid\" column to person table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-person_address','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10108,'MARK_RAN','8:1c4706c30f63e4ac562dda6064c3320d','Add Column','Adding \"uuid\" column to person_address table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-person_attribute','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10109,'MARK_RAN','8:8df07962118ce4256c3663a2cac5fa6d','Add Column','Adding \"uuid\" column to person_attribute table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-person_attribute_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10110,'MARK_RAN','8:eb63583f3dc09efe8110de22caa78152','Add Column','Adding \"uuid\" column to person_attribute_type table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-person_name','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10111,'MARK_RAN','8:2dac685163826c58b24bb5651634062e','Add Column','Adding \"uuid\" column to person_name table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-privilege','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10112,'MARK_RAN','8:c268f25e6813449d2eadf0d68396b2cc','Add Column','Adding \"uuid\" column to privilege table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-program','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10113,'MARK_RAN','8:8e8f9accf78030c69ee3215ca9fba00d','Add Column','Adding \"uuid\" column to program table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-program_workflow','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10114,'MARK_RAN','8:5d32167cb42d7cbdd125d72fd61ccf62','Add Column','Adding \"uuid\" column to program_workflow table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-program_workflow_state','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10115,'MARK_RAN','8:d3db0773befa2bb3f5067273b3651e08','Add Column','Adding \"uuid\" column to program_workflow_state table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-relationship','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10116,'MARK_RAN','8:fbc333ba5255a1a2ae18c161415e165e','Add Column','Adding \"uuid\" column to relationship table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-relationship_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10117,'MARK_RAN','8:00e478ff9774218dbcae69353e39d36e','Add Column','Adding \"uuid\" column to relationship_type table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-report_object','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10118,'MARK_RAN','8:e813e7bbf9200ba577a6b7de2c9f2d3e','Add Column','Adding \"uuid\" column to report_object table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-report_schema_xml','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10119,'MARK_RAN','8:9e001c357521a148a920c2c18de2c92d','Add Column','Adding \"uuid\" column to report_schema_xml table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-role','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10120,'MARK_RAN','8:7af04cddec8d25686faea5823b062300','Add Column','Adding \"uuid\" column to role table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1515-38-serialized_object','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10121,'MARK_RAN','8:7f544fd844f6fad7ba68767d01a47c21','Add Column','Adding \"uuid\" column to serialized_object table',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-cohort','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10122,'EXECUTED','8:2aec641cb1c4a52c7a3e3a9aec8ecde2','Update Data','Generating UUIDs for all rows in cohort table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-concept','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10123,'EXECUTED','8:95207da0528a9be3b9bd25ba74412b5e','Update Data','Generating UUIDs for all rows in concept table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-concept_answer','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10124,'EXECUTED','8:d93c561107bbfdaf221b962a8faadef2','Update Data','Generating UUIDs for all rows in concept_answer table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-concept_class','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10125,'EXECUTED','8:3c669720a030112860368418505f7ff6','Update Data','Generating UUIDs for all rows in concept_class table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-concept_datatype','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10126,'EXECUTED','8:416d783ab7a7afcaae7de90804bfef76','Update Data','Generating UUIDs for all rows in concept_datatype table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-concept_description','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10127,'EXECUTED','8:c5e949674cfc1ab44c3b14f2a2243eb0','Update Data','Generating UUIDs for all rows in concept_description table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-concept_map','bwolfe','liquibase-update-to-latest.xml','2011-09-15 00:00:00',10004,'MARK_RAN','8:473811d69495503c4779f69e1e39cc96',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('20090402-1516-concept_name','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10128,'EXECUTED','8:cbb0c68546f2f3cf6c054e2bfb5d35ed','Update Data','Generating UUIDs for all rows in concept_name table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-concept_name_tag','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10129,'EXECUTED','8:bdf262d25be8a474ecd082cf912c7a46','Update Data','Generating UUIDs for all rows in concept_name_tag table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-concept_proposal','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10130,'EXECUTED','8:c35661adc7de633f5a09341aa66e4464','Update Data','Generating UUIDs for all rows in concept_proposal table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-concept_set','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10131,'EXECUTED','8:c1ae0a21355b727580ce5fd27f038f9a','Update Data','Generating UUIDs for all rows in concept_set table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-concept_source','bwolfe','liquibase-update-to-latest.xml','2011-09-15 00:00:00',10005,'MARK_RAN','8:8b55f0633e4cfac3b4cafa1457414587',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('20090402-1516-concept_state_conversion','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10132,'EXECUTED','8:9e6d6c9c6aae84fe66b62bfd2f7449f4','Update Data','Generating UUIDs for all rows in concept_state_conversion table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-drug','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10133,'EXECUTED','8:ca660f1210e771cd5fe9adf129fa4fe7','Update Data','Generating UUIDs for all rows in drug table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-encounter','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10134,'EXECUTED','8:fcad09479545ddbd610c1fbe80a99a98','Update Data','Generating UUIDs for all rows in encounter table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-encounter_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10135,'EXECUTED','8:28cf79decfb6733b9fded57570ae70aa','Update Data','Generating UUIDs for all rows in encounter_type table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-field','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10136,'EXECUTED','8:4fff4e8fa2e3baa014447181e93a2179','Update Data','Generating UUIDs for all rows in field table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-field_answer','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10137,'EXECUTED','8:bdf11bfe68d3cd5e72cb9bde6f9db72a','Update Data','Generating UUIDs for all rows in field_answer table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-field_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10138,'EXECUTED','8:9c0ae756b4216bfe210ee3bc0010ca59','Update Data','Generating UUIDs for all rows in field_type table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-form','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10139,'EXECUTED','8:378d9ed17da79cce5c21f6c6bf5b8d7a','Update Data','Generating UUIDs for all rows in form table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-form_field','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10140,'EXECUTED','8:859f8df70d99983b7f5be179ee2c0156','Update Data','Generating UUIDs for all rows in form_field table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-global_property','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10141,'EXECUTED','8:0a772a7a774bae5725bd6759eaf2cb23','Update Data','Generating UUIDs for all rows in global_property table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-hl7_in_archive','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10142,'EXECUTED','8:54a322e0f3b5cf7ed897e2249ca17d76','Update Data','Generating UUIDs for all rows in hl7_in_archive table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-hl7_in_error','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10143,'EXECUTED','8:4bbb1e57dca851e4110d41d4dc0ec463','Update Data','Generating UUIDs for all rows in hl7_in_error table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-hl7_in_queue','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10144,'EXECUTED','8:01b916c4d68fd62fd0ab23531d9860f0','Update Data','Generating UUIDs for all rows in hl7_in_queue table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-hl7_source','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10145,'EXECUTED','8:13e38d86ad39fca09b4e9e82a63705c7','Update Data','Generating UUIDs for all rows in hl7_source table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-location','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10146,'EXECUTED','8:32f5adaaf261baad31e70e79643c2c2b','Update Data','Generating UUIDs for all rows in location table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-location_tag','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10147,'EXECUTED','8:3042d164255542158295b00512ed45f2','Update Data','Generating UUIDs for all rows in location_tag table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-note','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10148,'EXECUTED','8:670ed9708c27860f462b54c068196a69','Update Data','Generating UUIDs for all rows in note table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-notification_alert','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10149,'EXECUTED','8:383fec14d77658f1211f1165c240d255','Update Data','Generating UUIDs for all rows in notification_alert table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-notification_template','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10150,'EXECUTED','8:f1c0ccfe7a0aaf2d233d0822d3c213b0','Update Data','Generating UUIDs for all rows in notification_template table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-obs','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10151,'EXECUTED','8:1d9b0b160fefef7205f4a424d03cdaf3','Update Data','Generating UUIDs for all rows in obs table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-orders','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10153,'EXECUTED','8:38902fa2f185db604bf719baf5fedd0f','Update Data','Generating UUIDs for all rows in orders table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-order_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10152,'EXECUTED','8:9ec2968e7184834a13f32af289826480','Update Data','Generating UUIDs for all rows in order_type table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-patient_identifier','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10154,'EXECUTED','8:8f868c560c9b44b629e6a1d0eff550dd','Update Data','Generating UUIDs for all rows in patient_identifier table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-patient_identifier_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10155,'EXECUTED','8:5d1400c2dc3c6d6c5ffeb02b72942fa5','Update Data','Generating UUIDs for all rows in patient_identifier_type table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-patient_program','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10156,'EXECUTED','8:12451028127f6d7536989af69ec8bdc9','Update Data','Generating UUIDs for all rows in patient_program table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-patient_state','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10157,'EXECUTED','8:0b81277a703b38d0ebe5cf65e7ecc313','Update Data','Generating UUIDs for all rows in patient_state table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-person','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10158,'EXECUTED','8:357977a0ad46e8e166a51fd516820898','Update Data','Generating UUIDs for all rows in person table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-person_address','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10159,'EXECUTED','8:5f4b16332d1645b1a3a2c3671e680cc9','Update Data','Generating UUIDs for all rows in person_address table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-person_attribute','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10160,'EXECUTED','8:51c79b0cdd021644e011b4fb57376436','Update Data','Generating UUIDs for all rows in person_attribute table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-person_attribute_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10161,'EXECUTED','8:2a7050fcd87693ae2f8cb7d6d3f6ed23','Update Data','Generating UUIDs for all rows in person_attribute_type table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-person_name','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10162,'EXECUTED','8:d04cb9640a4462cc376b7497d045fb2b','Update Data','Generating UUIDs for all rows in person_name table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-privilege','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10163,'EXECUTED','8:8661fe630022fa2f248a313f578dd30b','Update Data','Generating UUIDs for all rows in privilege table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-program','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10164,'EXECUTED','8:048acecb6308a96c34b3fc6db419d25f','Update Data','Generating UUIDs for all rows in program table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-program_workflow','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10165,'EXECUTED','8:27bb44cb60d3e895512cb9022aa586b0','Update Data','Generating UUIDs for all rows in program_workflow table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-program_workflow_state','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10166,'EXECUTED','8:ffd6c9969bbf9ea41aef80d5c0d493a6','Update Data','Generating UUIDs for all rows in program_workflow_state table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-relationship','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10167,'EXECUTED','8:3439981909918c7932414c77c17072cc','Update Data','Generating UUIDs for all rows in relationship table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-relationship_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10168,'EXECUTED','8:0cca043249122b9f4f639e56f272491c','Update Data','Generating UUIDs for all rows in relationship_type table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-report_object','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10169,'EXECUTED','8:9c954f74614ab79ae4f0fed58f8a6f88','Update Data','Generating UUIDs for all rows in report_object table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-report_schema_xml','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10170,'EXECUTED','8:3440f488fa2cab5432b80a40c883f580','Update Data','Generating UUIDs for all rows in report_schema_xml table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-role','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10171,'EXECUTED','8:b9cca9504ef0caaebb12f4208a7dc428','Update Data','Generating UUIDs for all rows in role table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1516-serialized_object','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10172,'EXECUTED','8:2607cf633f0300f09c6a339bdde6529b','Update Data','Generating UUIDs for all rows in serialized_object table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1517','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:12',10181,'MARK_RAN','8:c21f941e118c0d47576e1641d185ccd5','Custom Change','Adding UUIDs to all rows in all columns via a java class. (This will take a long time on large databases)',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1518','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:12',10182,'MARK_RAN','8:76910e4c3815ec85281ce213f54d987f','Add Not-Null Constraint (x52)','Now that UUID generation is done, set the uuid columns to not \"NOT NULL\"',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-cohort','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:12',10183,'EXECUTED','8:77b494d38aabc6081271790f1564dab1','Create Index','Creating unique index on cohort.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-concept','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:12',10184,'EXECUTED','8:500f06c0ccd14c3ad38ad346625bce42','Create Index','Creating unique index on concept.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-concept_answer','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:13',10185,'EXECUTED','8:c69cbfc6d2a895f923c44cef77c58c57','Create Index','Creating unique index on concept_answer.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-concept_class','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:13',10186,'EXECUTED','8:777953414468627a2f501bd7e125db49','Create Index','Creating unique index on concept_class.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-concept_datatype','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:14',10187,'EXECUTED','8:6c8e4a3a894750ce9bc678e9d807518a','Create Index','Creating unique index on concept_datatype.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-concept_description','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:14',10188,'EXECUTED','8:317a7702fa74a21e03cfb9e3a52de422','Create Index','Creating unique index on concept_description.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-concept_map','bwolfe','liquibase-update-to-latest.xml','2011-09-15 00:00:00',10006,'MARK_RAN','8:bb2d240e64ac0fe71b690d29198df955',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('20090402-1519-concept_name','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:14',10189,'EXECUTED','8:8bbbbd22558e249981bf4d3f6c7371a0','Create Index','Creating unique index on concept_name.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-concept_name_tag','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:15',10190,'EXECUTED','8:3bd2e9880786acbece7c5e6a32df1a6e','Create Index','Creating unique index on concept_name_tag.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-concept_proposal','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:15',10191,'EXECUTED','8:5c89b47e4723db1092ff4cd1edf9789d','Create Index','Creating unique index on concept_proposal.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-concept_set','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:16',10192,'EXECUTED','8:726cacc6a41d6085f83ebd2ded0b0508','Create Index','Creating unique index on concept_set.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-concept_source','bwolfe','liquibase-update-to-latest.xml','2011-09-15 00:00:00',10007,'MARK_RAN','8:326340af265daddbe08cfa9318455f75',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('20090402-1519-concept_state_conversion','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:16',10193,'EXECUTED','8:34f2585ef6ec5a1406ba4cb500d797f2','Create Index','Creating unique index on concept_state_conversion.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-drug','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:17',10194,'EXECUTED','8:90a5c1c07fc60cf606f2e5287420eb8a','Create Index','Creating unique index on drug.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-encounter','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:17',10195,'EXECUTED','8:e1469cea326c33a9a7b2037e0f3fd950','Create Index','Creating unique index on encounter.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-encounter_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:17',10196,'EXECUTED','8:6b6184d6dfa1cb5244846f86905834c2','Create Index','Creating unique index on encounter_type.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-field','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:18',10197,'EXECUTED','8:9c28f982a3f44a0ff474bcf2e5c3fbe0','Create Index','Creating unique index on field.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-field_answer','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:18',10198,'EXECUTED','8:4e30b5bd7fb49b67689d8e9d7ce098cb','Create Index','Creating unique index on field_answer.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-field_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:19',10199,'EXECUTED','8:76fba4dbde21b10af578e7a234f36e79','Create Index','Creating unique index on field_type.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-form','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:19',10200,'EXECUTED','8:6bd03d28591c39c417bebedb4d07a2d9','Create Index','Creating unique index on form.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-form_field','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:19',10201,'EXECUTED','8:9f5f7a115a63a18bff06a79ab57e3b79','Create Index','Creating unique index on form_field.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-global_property','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:20',10202,'EXECUTED','8:6b83da608638370c70772fbda4306f80','Create Index','Creating unique index on global_property.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-hl7_in_archive','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:20',10203,'EXECUTED','8:8bf1e300e8d59d339d074ee1d9c06a3e','Create Index','Creating unique index on hl7_in_archive.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-hl7_in_error','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:21',10204,'EXECUTED','8:96007e74c2ab4db39707f441a0e405c5','Create Index','Creating unique index on hl7_in_error.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-hl7_in_queue','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:21',10205,'EXECUTED','8:7026ded64129d5ee13983e0139863cef','Create Index','Creating unique index on hl7_in_queue.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-hl7_source','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:21',10206,'EXECUTED','8:9aefe928785ebc93038120d8f1198b09','Create Index','Creating unique index on hl7_source.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-location','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:22',10207,'EXECUTED','8:a8bd3a1fe414a06a075c50bb23bf7e96','Create Index','Creating unique index on location.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-location_tag','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:22',10208,'EXECUTED','8:25dbec36375b2d464c85efa5d7d45ece','Create Index','Creating unique index on location_tag.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-note','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:23',10209,'EXECUTED','8:78990bcbb4708019e152c1cff21b94b7','Create Index','Creating unique index on note.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-notification_alert','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:23',10210,'EXECUTED','8:fe7cb1bda832204e534c4b3af6a39605','Create Index','Creating unique index on notification_alert.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-notification_template','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:23',10211,'EXECUTED','8:b209aecc9baa12965e731bb464fd3f5c','Create Index','Creating unique index on notification_template.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-obs','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:24',10212,'EXECUTED','8:4395352eee53db567d0d66e3e8bf7d2d','Create Index','Creating unique index on obs.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-orders','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:25',10214,'EXECUTED','8:8e03d3bdee85f503eaf3a836f7e80b43','Create Index','Creating unique index on orders.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-order_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:24',10213,'EXECUTED','8:a4c3cb136a54680371d860c89d13c90f','Create Index','Creating unique index on order_type.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-patient_identifier','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:25',10215,'EXECUTED','8:102efeb63bce03581ef711e92fbf4269','Create Index','Creating unique index on patient_identifier.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-patient_identifier_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:25',10216,'EXECUTED','8:09db86cc2664cd4e1fdf6b4c7b7c83f0','Create Index','Creating unique index on patient_identifier_type.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-patient_program','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:26',10217,'EXECUTED','8:9701be1bbedaacd361eda124e80cc0fd','Create Index','Creating unique index on patient_program.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-patient_state','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:26',10218,'EXECUTED','8:53318c39e879f77a7b6d8ed569969258','Create Index','Creating unique index on patient_state.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-person','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:27',10219,'EXECUTED','8:767cf71beed653993ad3f2c80d01f839','Create Index','Creating unique index on person.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-person_address','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:27',10220,'EXECUTED','8:a2e00f3d9e790b0645fc59d3c5867f90','Create Index','Creating unique index on person_address.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-person_attribute','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:27',10221,'EXECUTED','8:0526457c6e72d5d0fc510a8df3c5ed82','Create Index','Creating unique index on person_attribute.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-person_attribute_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:28',10222,'EXECUTED','8:c61cabd046206a46a734a908e4a69636','Create Index','Creating unique index on person_attribute_type.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-person_name','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:28',10223,'EXECUTED','8:5369e0fa305852af136d2db5656d65f3','Create Index','Creating unique index on person_name.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-privilege','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:29',10224,'EXECUTED','8:06caf65888135bb5004b72befdabb9e3','Create Index','Creating unique index on privilege.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-program','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:29',10225,'EXECUTED','8:9b6166282bfd83be4c7d17503ec8c77e','Create Index','Creating unique index on program.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-program_workflow','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:30',10226,'EXECUTED','8:e2a4933e4b5a58e7c9b0db0fe8a3a080','Create Index','Creating unique index on program_workflow.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-program_workflow_state','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:30',10227,'EXECUTED','8:855a4d592fda084790c160bda14e60fd','Create Index','Creating unique index on program_workflow_state.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-relationship','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:30',10228,'EXECUTED','8:353b87e4d3d7cf8941a59ec946e1deb6','Create Index','Creating unique index on relationship.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-relationship_type','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:31',10229,'EXECUTED','8:894a9d4a6491ab41ce500852f4ba9f06','Create Index','Creating unique index on relationship_type.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-report_object','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:31',10230,'EXECUTED','8:d270ccf458d341419b7ffd672f381602','Create Index','Creating unique index on report_object.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-report_schema_xml','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:32',10231,'EXECUTED','8:85fde849e083b6d73d9f77bfe8b348da','Create Index','Creating unique index on report_schema_xml.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-role','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:32',10232,'EXECUTED','8:6e59e26f177e1690d53eaee678369e91','Create Index','Creating unique index on role.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090402-1519-serialized_object','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:32',10233,'EXECUTED','8:56201769190e59008c7e5cfb1d1cf2c9','Create Index','Creating unique index on serialized_object.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090408-1298','Cory McCarthy','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10068,'EXECUTED','8:c461291f0024f205ab1a0d8085500649','Modify Column','Changed the datatype for encounter_type to \'text\' instead of just 50 chars',NULL,'2.0.5',NULL,NULL,NULL),('200904091023','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10067,'EXECUTED','8:edd3c2248371a8f0f2b66efd137d672c','Delete Data (x4)','Remove Manage Tribes and View Tribes privileges from the privilege table and role_privilege table.\n			The privileges will be recreated by the Tribe module if it is installed.',NULL,'2.0.5',NULL,NULL,NULL),('20090414-0804','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10173,'EXECUTED','8:c7ae9cc69665a7cac874796a15ac75f9','Drop Foreign Key Constraint','Dropping foreign key on concept_set.concept_id table',NULL,'2.0.5',NULL,NULL,NULL),('20090414-0805','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10174,'MARK_RAN','8:31195ba116232c3c239eb99a74f3be9a','Drop Primary Key','Dropping primary key on concept set table',NULL,'2.0.5',NULL,NULL,NULL),('20090414-0806','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10175,'MARK_RAN','8:2fec2a5cbe145044e4ddda6e528b95e8','Add Column','Adding new integer primary key to concept set table',NULL,'2.0.5',NULL,NULL,NULL),('20090414-0807','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:11',10176,'MARK_RAN','8:ebc6c156c3492c8c2d16f12297ae3e43','Create Index, Add Foreign Key Constraint','Adding index and foreign key to concept_set.concept_id column',NULL,'2.0.5',NULL,NULL,NULL),('20090414-0808a','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:11',10177,'EXECUTED','8:b26ae51a72c53522c54fc2e1d9f83ebc','Drop Foreign Key Constraint','Dropping foreign key on patient_identifier.patient_id column',NULL,'2.0.5',NULL,NULL,NULL),('20090414-0808b','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:11',10178,'MARK_RAN','8:5ef5bcb5f83f538fe32a3e1b0ac0d014','Drop Primary Key','Dropping non-integer primary key on patient identifier table before adding a new integer primary key',NULL,'2.0.5',NULL,NULL,NULL),('20090414-0809','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:11',10179,'MARK_RAN','8:d830ba80f78504482eaa8dfe4d93be3d','Add Column','Adding new integer primary key to patient identifier table',NULL,'2.0.5',NULL,NULL,NULL),('20090414-0810','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:12',10180,'MARK_RAN','8:31c7d78247503004563dcec3b22c97fe','Create Index, Add Foreign Key Constraint','Adding index and foreign key on patient_identifier.patient_id column',NULL,'2.0.5',NULL,NULL,NULL),('20090414-0811a','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:33',10234,'EXECUTED','8:a996aae13913281567000b04221e17b4','Drop Foreign Key Constraint','Dropping foreign key on concept_word.concept_id column',NULL,'2.0.5',NULL,NULL,NULL),('20090414-0811b','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:33',10236,'MARK_RAN','8:68201f30b43cec5ad62a2c0ad1ef8cf3','Drop Primary Key','Dropping non-integer primary key on concept word table before adding new integer one',NULL,'2.0.5',NULL,NULL,NULL),('20090414-0812','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:33',10237,'MARK_RAN','8:be261ff12facaf51c32cbd2c442149ce','Add Column','Adding integer primary key to concept word table',NULL,'2.0.5',NULL,NULL,NULL),('20090414-0812b','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:33',10238,'MARK_RAN','8:6a9f6449f288592ea5e47bae65986340','Add Foreign Key Constraint','Re-adding foreign key for concept_word.concept_name_id',NULL,'2.0.5',NULL,NULL,NULL),('200904271042','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10241,'MARK_RAN','8:63e5f7ca7868c45cda0fcb1250323e00','Drop Column','Remove the now unused synonym column',NULL,'2.0.5',NULL,NULL,NULL),('20090428-0811aa','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:33',10235,'MARK_RAN','8:210185827394b8982208a79333b4c33f','Drop Foreign Key Constraint','Removing concept_word.concept_name_id foreign key so that primary key can be changed to concept_word_id',NULL,'2.0.5',NULL,NULL,NULL),('20090428-0854','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10239,'EXECUTED','8:1ad9516c3ccff7c5d0f2c4d0ccb4d820','Add Foreign Key Constraint','Adding foreign key for concept_word.concept_id column',NULL,'2.0.5',NULL,NULL,NULL),('200905071626','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:10',10070,'MARK_RAN','8:56e2d2bb15a004bd5ac5bf362e7e3f82','Create Index','Add an index to the concept_word.concept_id column (This update may fail if it already exists)',NULL,'2.0.5',NULL,NULL,NULL),('200905150814','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:09',10069,'EXECUTED','8:d4325318f852b3ede2798d3a08f42088','Delete Data','Deleting invalid concept words',NULL,'2.0.5',NULL,NULL,NULL),('200905150821','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10240,'EXECUTED','8:096845d15f847ebefb86fec52322ee8f','Custom SQL','Deleting duplicate concept word keys',NULL,'2.0.5',NULL,NULL,NULL),('200906301606','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10244,'EXECUTED','8:044fc2a5c368ef46ca54e94f5283ba23','Modify Column','Change person_attribute_type.sort_weight from an integer to a float',NULL,'2.0.5',NULL,NULL,NULL),('200907161638-1','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10245,'EXECUTED','8:3a4f40729f5f01ef36ee1c178a987138','Modify Column','Change obs.value_numeric from a double(22,0) to a double',NULL,'2.0.5',NULL,NULL,NULL),('200907161638-2','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10246,'EXECUTED','8:450c05cda87828eb6f47ffe0cdf401ce','Modify Column','Change concept_numeric columns from a double(22,0) type to a double',NULL,'2.0.5',NULL,NULL,NULL),('200907161638-3','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10247,'EXECUTED','8:3ec90a07b19979d3f1a2832381ef162d','Modify Column','Change concept_set.sort_weight from a double(22,0) to a double',NULL,'2.0.5',NULL,NULL,NULL),('200907161638-4','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10248,'EXECUTED','8:e9cf31e6d6c61bd1fe037f91a615a8bc','Modify Column','Change concept_set_derived.sort_weight from a double(22,0) to a double',NULL,'2.0.5',NULL,NULL,NULL),('200907161638-5','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10249,'EXECUTED','8:60ce0bb585b5279a887cb56a7e26e945','Modify Column','Change drug table columns from a double(22,0) to a double',NULL,'2.0.5',NULL,NULL,NULL),('200907161638-6','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10250,'EXECUTED','8:4e57bc42bd5470361e6cda531795c115','Modify Column','Change drug_order.dose from a double(22,0) to a double',NULL,'2.0.5',NULL,NULL,NULL),('200908291938-1','dthomas','liquibase-update-to-latest.xml','2011-09-15 00:00:00',10008,'MARK_RAN','8:e5090f4822b86509bb56596dceab99a1',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('200908291938-2a','dthomas','liquibase-update-to-latest.xml','2011-09-15 00:00:00',10009,'MARK_RAN','8:d634a0e082718f7366d632aa5ed24593',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('20090831-1039-38-scheduler_task_config','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10254,'MARK_RAN','8:d946e9fef0aca80b94cb3d4409289d38','Add Column','Adding \"uuid\" column to scheduler_task_config table',NULL,'2.0.5',NULL,NULL,NULL),('20090831-1040-scheduler_task_config','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10255,'EXECUTED','8:0ff0cf59b9ea0198eb9aa57efdc52202','Update Data','Generating UUIDs for all rows in scheduler_task_config table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20090831-1041-scheduler_task_config','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10256,'MARK_RAN','8:a738d122f05898d4f7817e280e5d7dd1','Custom Change','Adding UUIDs to all rows in scheduler_task_config table via a java class for non mysql/oracle/mssql databases.',NULL,'2.0.5',NULL,NULL,NULL),('20090831-1042-scheduler_task_config','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10257,'EXECUTED','8:980b925052886c640c72550654dec469','Add Not-Null Constraint','Now that UUID generation is done for scheduler_task_config, set the uuid column to not \"NOT NULL\"',NULL,'2.0.5',NULL,NULL,NULL),('20090831-1043-scheduler_task_config','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10258,'EXECUTED','8:e65d51a694f6ab7dc4507b049f2ea611','Create Index','Creating unique index on scheduler_task_config.uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20090907-1','Knoll_Frank','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10259,'MARK_RAN','8:d62c4cf899e32b8abfb9a48ac4e02b88','Rename Column','Rename the concept_source.date_voided column to date_retired',NULL,'2.0.5',NULL,NULL,NULL),('20090907-2a','Knoll_Frank','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10260,'MARK_RAN','8:4cbe5f1c7fcb781e1c286f7b52b05123','Drop Foreign Key Constraint','Remove the concept_source.voided_by foreign key constraint',NULL,'2.0.5',NULL,NULL,NULL),('20090907-2b','Knoll_Frank','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10261,'MARK_RAN','8:c539308417bd8549b90356addfbc9b85','Rename Column, Add Foreign Key Constraint','Rename the concept_source.voided_by column to retired_by',NULL,'2.0.5',NULL,NULL,NULL),('20090907-3','Knoll_Frank','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10262,'MARK_RAN','8:23b6001e89959129ba05e21077cbc427','Rename Column','Rename the concept_source.voided column to retired',NULL,'2.0.5',NULL,NULL,NULL),('20090907-4','Knoll_Frank','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10263,'MARK_RAN','8:80eb04b2bb5c0e714b29dba6ebdfd502','Rename Column','Rename the concept_source.void_reason column to retire_reason',NULL,'2.0.5',NULL,NULL,NULL),('20091001-1023','rcrichton','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10291,'MARK_RAN','8:1e7ac300f296e9eadf8bc121ddd053da','Add Column','add retired column to relationship_type table',NULL,'2.0.5',NULL,NULL,NULL),('20091001-1024','rcrichton','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10292,'MARK_RAN','8:4d7a047e2438ab41b60e3cbb81405863','Add Column','add retired_by column to relationship_type table',NULL,'2.0.5',NULL,NULL,NULL),('20091001-1025','rcrichton','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10293,'MARK_RAN','8:67813c79367264837a9aedbc8cda45dc','Add Foreign Key Constraint','Create the foreign key from the relationship.retired_by to users.user_id.',NULL,'2.0.5',NULL,NULL,NULL),('20091001-1026','rcrichton','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10294,'MARK_RAN','8:ee3a0de9371dad837a6c57cb5488378d','Add Column','add date_retired column to relationship_type table',NULL,'2.0.5',NULL,NULL,NULL),('20091001-1027','rcrichton','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10295,'MARK_RAN','8:5d943e10f541dae0d795443c3986948f','Add Column','add retire_reason column to relationship_type table',NULL,'2.0.5',NULL,NULL,NULL),('200910271049-1','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10264,'EXECUTED','8:23d6932d2fbff1776cb07c241fb96520','Update Data (x5)','Setting core field types to have standard UUIDs',NULL,'2.0.5',NULL,NULL,NULL),('200910271049-10','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10273,'EXECUTED','8:21cdf21fa5da029385241ae0dd09ac97','Update Data (x4)','Setting core roles to have standard UUIDs',NULL,'2.0.5',NULL,NULL,NULL),('200910271049-2','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10265,'EXECUTED','8:f446e8df5eaf852168c54402101ca1a3','Update Data (x7)','Setting core person attribute types to have standard UUIDs',NULL,'2.0.5',NULL,NULL,NULL),('200910271049-3','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10266,'EXECUTED','8:9430aaffa164ed6be0e42efd1aba2112','Update Data (x4)','Setting core encounter types to have standard UUIDs',NULL,'2.0.5',NULL,NULL,NULL),('200910271049-4','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10267,'EXECUTED','8:c8fbf8c706341ec7404fdf974d2577c2','Update Data (x12)','Setting core concept datatypes to have standard UUIDs',NULL,'2.0.5',NULL,NULL,NULL),('200910271049-5','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10268,'EXECUTED','8:98daa6de2bd8ba45273bd4b69715ea5e','Update Data (x4)','Setting core relationship types to have standard UUIDs',NULL,'2.0.5',NULL,NULL,NULL),('200910271049-6','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10269,'EXECUTED','8:a080a9b0abaf6a4c71e1836d1db9e70e','Update Data (x15)','Setting core concept classes to have standard UUIDs',NULL,'2.0.5',NULL,NULL,NULL),('200910271049-7','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10270,'EXECUTED','8:a595ef7a02fcb5da4072a0a7bab0ff10','Update Data (x2)','Setting core patient identifier types to have standard UUIDs',NULL,'2.0.5',NULL,NULL,NULL),('200910271049-8','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10271,'EXECUTED','8:243233f07faeed0ac0df5cee4e5b9bbc','Update Data','Setting core location to have standard UUIDs',NULL,'2.0.5',NULL,NULL,NULL),('200910271049-9','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10272,'EXECUTED','8:89a5030d339ad14f4d9f43b9ccd31e8a','Update Data','Setting core hl7 source to have standard UUIDs',NULL,'2.0.5',NULL,NULL,NULL),('200912031842','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10277,'EXECUTED','8:82ac70cd14fed7009266943f155ffee7','Drop Foreign Key Constraint, Add Foreign Key Constraint','Changing encounter.provider_id to reference person instead of users',NULL,'2.0.5',NULL,NULL,NULL),('200912031846-1','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10279,'MARK_RAN','8:77ff621f2e656826b8608f19478aaddf','Add Column, Update Data','Adding person_id column to users table (if needed)',NULL,'2.0.5',NULL,NULL,NULL),('200912031846-2','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10280,'MARK_RAN','8:b0748b0c468f23f2fa58c8ed1d081839','Update Data, Add Not-Null Constraint','Populating users.person_id',NULL,'2.0.5',NULL,NULL,NULL),('200912031846-3','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10281,'EXECUTED','8:6b05d0fd1d75a6095123ea18f108b56a','Add Foreign Key Constraint, Set Column as Auto-Increment','Restoring foreign key constraint on users.person_id',NULL,'2.0.5',NULL,NULL,NULL),('200912071501-1','arthurs','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10274,'EXECUTED','8:8764502f80067d318dfd2faf3731bbed','Update Data','Change name for patient.searchMaxResults global property to person.searchMaxResults',NULL,'2.0.5',NULL,NULL,NULL),('200912091819','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10282,'MARK_RAN','8:226f16d4a250f1ccf1af5fdaa7d48761','Add Column, Add Foreign Key Constraint','Adding retired metadata columns to users table',NULL,'2.0.5',NULL,NULL,NULL),('200912091819-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10283,'EXECUTED','8:2764ddfca1b043b732dad9881e3d49f7','Modify Column','(Fixed)users.retired to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('200912091820','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10284,'MARK_RAN','8:80d1f6e3d1adedd6ef1b6247e058af21','Update Data','Migrating voided metadata to retired metadata for users table',NULL,'2.0.5',NULL,NULL,NULL),('200912091821','djazayeri','liquibase-update-to-latest.xml','2011-09-15 00:00:00',10012,'MARK_RAN','8:71e37f0c4349070be32aac4af3374fa0',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('200912140038','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10285,'MARK_RAN','8:e2240aba6ac7f26f9802be8411084fd8','Add Column','Adding \"uuid\" column to users table',NULL,'2.0.5',NULL,NULL,NULL),('200912140039','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10286,'EXECUTED','8:8027b71175d4202a72bda26f7e107cdf','Update Data','Generating UUIDs for all rows in users table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('200912140040','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10287,'MARK_RAN','8:c5bd581226dfb2f5c7762b205513bcb9','Custom Change','Adding UUIDs to users table via a java class. (This will take a long time on large databases)',NULL,'2.0.5',NULL,NULL,NULL),('200912141000-drug-add-date-changed','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:47',10478,'MARK_RAN','8:a11b32c878736f7e6cc5fbb3626f2434','Add Column','Add date_changed column to drug table',NULL,'2.0.5',NULL,NULL,NULL),('200912141001-drug-add-changed-by','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:47',10479,'MARK_RAN','8:a267bc3e9979e861b15ceea91818cdd1','Add Column, Add Foreign Key Constraint','Add changed_by column to drug table',NULL,'2.0.5',NULL,NULL,NULL),('200912141552','madanmohan','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10275,'MARK_RAN','8:55ee44a866dff1e5dcb69fc194290632','Add Column, Add Foreign Key Constraint','Add changed_by column to encounter table',NULL,'2.0.5',NULL,NULL,NULL),('200912141553','madanmohan','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10276,'MARK_RAN','8:f74808b8d0e70703c836cade7f4977a2','Add Column','Add date_changed column to encounter table',NULL,'2.0.5',NULL,NULL,NULL),('20091215-0208','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10296,'EXECUTED','8:19e9a8690cdb14b0a786d1601e91b1de','Custom SQL','Prune concepts rows orphaned in concept_numeric tables',NULL,'2.0.5',NULL,NULL,NULL),('20091215-0209','jmiranda','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10297,'EXECUTED','8:e5265abc655a7ec6195a36faf971ebc2','Custom SQL','Prune concepts rows orphaned in concept_complex tables',NULL,'2.0.5',NULL,NULL,NULL),('20091215-0210','jmiranda','liquibase-update-to-latest.xml','2011-09-15 00:00:00',10011,'MARK_RAN','8:187f8a0f34e454b5adddfcded840f610',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('200912151032','n.nehete','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10289,'EXECUTED','8:5587ff3f964f6c0ef863fd2679a7af1b','Add Not-Null Constraint','Encounter Type should not be null when saving an Encounter',NULL,'2.0.5',NULL,NULL,NULL),('200912211118','nribeka','liquibase-update-to-latest.xml','2011-09-15 00:00:00',10010,'MARK_RAN','8:a0be212b17f9a3be33b6e6d0f97d1a5c',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('201001072007','upul','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10290,'MARK_RAN','8:a8d4f3d196b4585531d89adc4d9bfaa3','Add Column','Add last execution time column to scheduler_task_config table',NULL,'2.0.5',NULL,NULL,NULL),('20100111-0111-associating-daemon-user-with-person','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10462,'MARK_RAN','8:c72be5ab36e9c2c7a36528e8c1c66478','Custom SQL','Associating daemon user with a person',NULL,'2.0.5',NULL,NULL,NULL),('20100128-1','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10251,'MARK_RAN','8:9a5dbd5c16787ee4670a889c0b3563a0','Insert Row','Adding \'System Developer\' role again (see ticket #1499)',NULL,'2.0.5',NULL,NULL,NULL),('20100128-2','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10252,'MARK_RAN','8:146b8eb501aae3a9d23f62a8fc89a53a','Update Data','Switching users back from \'Administrator\' to \'System Developer\' (see ticket #1499)',NULL,'2.0.5',NULL,NULL,NULL),('20100128-3','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:34',10253,'MARK_RAN','8:33e7b5581d96e6ef6c1d2c585eeef324','Delete Data','Deleting \'Administrator\' role (see ticket #1499)',NULL,'2.0.5',NULL,NULL,NULL),('20100306-095513a','thilini.hg','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10298,'MARK_RAN','8:cc3b542d171452e4684a4f15742bc592','Drop Foreign Key Constraint','Dropping unused foreign key from notification alert table',NULL,'2.0.5',NULL,NULL,NULL),('20100306-095513b','thilini.hg','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10299,'MARK_RAN','8:277cc468e6aa9258fbac3ce37c88b6e6','Drop Column','Dropping unused user_id column from notification alert table',NULL,'2.0.5',NULL,NULL,NULL),('20100322-0908','syhaas','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10300,'MARK_RAN','8:fae60ed0a1fd3f64484e1ab3dff39c7f','Add Column, Update Data','Adding sort_weight column to concept_answers table and initially sets the sort_weight to the concept_answer_id',NULL,'2.0.5',NULL,NULL,NULL),('20100323-192043','ricardosbarbosa','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10317,'EXECUTED','8:22fc806be045ee61b18dc2c15c9e454d','Update Data, Delete Data (x2)','Removing the duplicate privilege \'Add Concept Proposal\' in favor of \'Add Concept Proposals\'',NULL,'2.0.5',NULL,NULL,NULL),('20100330-190413','ricardosbarbosa','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10318,'EXECUTED','8:fd57944f31d29f10fd984a364556e6ef','Update Data, Delete Data (x2)','Removing the duplicate privilege \'Edit Concept Proposal\' in favor of \'Edit Concept Proposals\'',NULL,'2.0.5',NULL,NULL,NULL),('20100412-2217','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10301,'MARK_RAN','8:2a2eccd27a0ccfa3640864fdab32edb0','Add Column','Adding \"uuid\" column to notification_alert_recipient table',NULL,'2.0.5',NULL,NULL,NULL),('20100412-2218','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10302,'EXECUTED','8:b81d6cac8db510e4ca1449202e41fd45','Update Data','Generating UUIDs for all rows in notification_alert_recipient table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('20100412-2219','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10303,'MARK_RAN','8:9d7e78fcb1a5ebe7e34f05fc94a1116f','Custom Change','Adding UUIDs to notification_alert_recipient table via a java class (if needed).',NULL,'2.0.5',NULL,NULL,NULL),('20100412-2220','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10304,'EXECUTED','8:c208411e9c01b29b520b2d15bcf9f55f','Add Not-Null Constraint','Now that UUID generation is done, set the notification_alert_recipient.uuid column to not \"NOT NULL\"',NULL,'2.0.5',NULL,NULL,NULL),('20100413-1509','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10305,'MARK_RAN','8:7e44eccfd68046f392168bce56a35163','Rename Column','Change location_tag.tag to location_tag.name',NULL,'2.0.5',NULL,NULL,NULL),('20100415-forgotten-from-before','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:36',10288,'EXECUTED','8:6574c7948db9e686d5010bc833a0579c','Add Not-Null Constraint','Adding not null constraint to users.uuid',NULL,'2.0.5',NULL,NULL,NULL),('20100419-1209','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10385,'MARK_RAN','8:597a1c408d8ddbd964370a1de61814a5','Create Table, Add Foreign Key Constraint (x7), Create Index','Create the visit table and add the foreign key for visit_type',NULL,'2.0.5',NULL,NULL,NULL),('20100419-1209-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10386,'EXECUTED','8:81fcc92ec6a41bf454f8d0832d1dc1a1','Modify Column','(Fixed)Changed visit.voided to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('20100423-1402','slorenz','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10307,'MARK_RAN','8:4afd4079efd3ff663172a71a656cb0af','Create Index','Add an index to the encounter.encounter_datetime column to speed up statistical\n			analysis.',NULL,'2.0.5',NULL,NULL,NULL),('20100423-1406','slorenz','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10308,'MARK_RAN','8:722b0dba4b61dcab30b52c887cf9e0b8','Create Index','Add an index to the obs.obs_datetime column to speed up statistical analysis.',NULL,'2.0.5',NULL,NULL,NULL),('20100426-1111-add-not-null-personid-contraint','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10463,'EXECUTED','8:8e301734688653a461f8f70769939106','Add Not-Null Constraint','Add the not null person id contraint',NULL,'2.0.5',NULL,NULL,NULL),('20100426-1111-remove-not-null-personid-contraint','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10309,'EXECUTED','8:949a17577887a067e61a70e2a07776bc','Drop Not-Null Constraint','Drop the not null person id contraint',NULL,'2.0.5',NULL,NULL,NULL),('20100426-1947','syhaas','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10310,'MARK_RAN','8:6a5d4dffc66d17649534cf5bab3711c5','Insert Row','Adding daemon user to users table',NULL,'2.0.5',NULL,NULL,NULL),('20100512-1400','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10312,'MARK_RAN','8:ebc342e31168b104b1a855d17eb1a145','Insert Row','Create core order_type for drug orders',NULL,'2.0.5',NULL,NULL,NULL),('20100513-1947','syhaas','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10311,'EXECUTED','8:f1e35335210be5047614b7f7a4b4e182','Delete Data (x2)','Removing scheduler.username and scheduler.password global properties',NULL,'2.0.5',NULL,NULL,NULL),('20100517-1545','wyclif and djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10313,'EXECUTED','8:ae87f981f04fae8f03c3859ef84ce632','Custom Change','Switch boolean concepts/observations to be stored as coded',NULL,'2.0.5',NULL,NULL,NULL),('20100525-818-1','syhaas','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10319,'MARK_RAN','8:3d48d9996ee36dcbb39edfc649d499de','Create Table, Add Foreign Key Constraint (x2)','Create active list type table.',NULL,'2.0.5',NULL,NULL,NULL),('20100525-818-1-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10320,'EXECUTED','8:bdee9ea46cd4ae75e1fbfc2257d8c7fa','Modify Column','(Fixed)Change active_list_type.retired to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('20100525-818-2','syhaas','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10321,'MARK_RAN','8:0e4feb9bc6ebf0989f3d52f653987dc8','Create Table, Add Foreign Key Constraint (x7)','Create active list table',NULL,'2.0.5',NULL,NULL,NULL),('20100525-818-2-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10322,'EXECUTED','8:0681e8628edfbf79903f4a282e964517','Modify Column','(Fixed)Change active_list_type.retired to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('20100525-818-3','syhaas','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10323,'MARK_RAN','8:6a70737e46b4c9da140e334889543639','Create Table, Add Foreign Key Constraint','Create allergen table',NULL,'2.0.5',NULL,NULL,NULL),('20100525-818-4','syhaas','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10324,'MARK_RAN','8:9412cbd4f4330ea59c04cd314caf6e6e','Create Table','Create problem table',NULL,'2.0.5',NULL,NULL,NULL),('20100525-818-5','syhaas','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10325,'MARK_RAN','8:b44486b46d789020feece4100f07226a','Insert Row (x2)','Inserting default active list types',NULL,'2.0.5',NULL,NULL,NULL),('20100526-1025','Harsha.cse','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10314,'EXECUTED','8:e81895e7335e642b219d9520e5d051c0','Drop Not-Null Constraint (x2)','Drop Not-Null constraint from location column in Encounter and Obs table',NULL,'2.0.5',NULL,NULL,NULL),('20100603-1625-1-person_address','sapna','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10366,'MARK_RAN','8:afcf5d54e869e06ddee319fb5b8e2ea5','Add Column','Adding \"date_changed\" column to person_address table',NULL,'2.0.5',NULL,NULL,NULL),('20100603-1625-2-person_address','sapna','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10367,'MARK_RAN','8:f185ffac5a215382b7de2018aad8cf9b','Add Column, Add Foreign Key Constraint','Adding \"changed_by\" column to person_address table',NULL,'2.0.5',NULL,NULL,NULL),('20100604-0933a','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10315,'EXECUTED','8:5c31dfc2bf4cf407431c2f74c8619b1c','Add Default Value','Changing the default value to 2 for \'message_state\' column in \'hl7_in_archive\' table',NULL,'2.0.5',NULL,NULL,NULL),('20100604-0933b','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10316,'EXECUTED','8:7f6ffd19b615403d5b61c53ca8f099b5','Update Data','Converting 0 and 1 to 2 for \'message_state\' column in \'hl7_in_archive\' table',NULL,'2.0.5',NULL,NULL,NULL),('20100607-1550a','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10326,'MARK_RAN','8:49317fce52bc13b927643a5061818733','Add Column','Adding \'concept_name_type\' column to concept_name table',NULL,'2.0.5',NULL,NULL,NULL),('20100607-1550b','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:38',10327,'MARK_RAN','8:231fd1d938aa540dc20a66a40b2c65b7','Add Column','Adding \'locale_preferred\' column to concept_name table',NULL,'2.0.5',NULL,NULL,NULL),('20100607-1550b-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:38',10328,'EXECUTED','8:23279fd3d8ec462f445ad6ea2c5a25bd','Modify Column','(Fixed)Change concept_name.locale_preferred to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('20100607-1550c','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:38',10329,'EXECUTED','8:a85db3130823a0fa3303be1c3f20ca40','Drop Foreign Key Constraint','Dropping foreign key constraint on concept_name_tag_map.concept_name_tag_id',NULL,'2.0.5',NULL,NULL,NULL),('20100607-1550d','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:38',10330,'EXECUTED','8:4482ac0fbdef7ca9dd3dbe4dd5950ac7','Update Data, Delete Data (x2)','Setting the concept name type for short names',NULL,'2.0.5',NULL,NULL,NULL),('20100607-1550f','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:38',10331,'EXECUTED','8:852131421995919f7635e39a5938aa74','Update Data, Delete Data (x2)','Converting concept names with \'preferred\' and \'preferred_XX\' concept name tags to preferred names',NULL,'2.0.5',NULL,NULL,NULL),('20100607-1550g','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:38',10334,'EXECUTED','8:ebd05571a805ac48c744d51797a73926','Delete Data (x2)','Deleting \'default\' and \'synonym\' concept name tags',NULL,'2.0.5',NULL,NULL,NULL),('20100607-1550h','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:38',10335,'EXECUTED','8:49690601b77333c239a98a0dd87b48ea','Custom Change','Validating and attempting to fix invalid concepts and ConceptNames',NULL,'2.0.5',NULL,NULL,NULL),('20100607-1550i','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:38',10336,'EXECUTED','8:c5f743c8e5d86321e9faad7714a034f0','Add Foreign Key Constraint','Restoring foreign key constraint on concept_name_tag_map.concept_name_tag_id',NULL,'2.0.5',NULL,NULL,NULL),('20100621-1443','jkeiper','liquibase-update-to-latest.xml','2021-05-09 07:08:38',10337,'EXECUTED','8:3efe573dc4aeb0f4602f4858f48031f7','Modify Column','Modify the error_details column of hl7_in_error to hold\n			stacktraces',NULL,'2.0.5',NULL,NULL,NULL),('201008021047','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:39',10338,'MARK_RAN','8:d37207a166aaca60d194ee966bc7e120','Create Index','Add an index to the person_name.family_name2 to speed up patient and person searches',NULL,'2.0.5',NULL,NULL,NULL),('201008201345','mseaton','liquibase-update-to-latest.xml','2021-05-09 07:08:39',10339,'EXECUTED','8:8773db0676f99091aa4c93783e84c973','Custom Change','Validates Program Workflow States for possible configuration problems and reports warnings',NULL,'2.0.5',NULL,NULL,NULL),('201008242121','misha680','liquibase-update-to-latest.xml','2021-05-09 07:08:39',10340,'EXECUTED','8:5f75b90f75b1e3b9563197e0c478e2cd','Modify Column','Make person_name.person_id not NULLable',NULL,'2.0.5',NULL,NULL,NULL),('20100924-1110','mseaton','liquibase-update-to-latest.xml','2021-05-09 07:08:39',10341,'MARK_RAN','8:ce0f46a7551b0141913d8d0879184c70','Add Column, Add Foreign Key Constraint','Add location_id column to patient_program table',NULL,'2.0.5',NULL,NULL,NULL),('201009281047','misha680','liquibase-update-to-latest.xml','2021-05-09 07:08:39',10342,'MARK_RAN','8:ff2e6e0cfaf8ec11aa9abe24486ab1be','Drop Column','Remove the now unused default_charge column',NULL,'2.0.5',NULL,NULL,NULL),('201010051745','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:39',10343,'EXECUTED','8:beb060b6240890967b8ce28b9bf42fb4','Update Data','Setting the global property \'patient.identifierRegex\' to an empty string',NULL,'2.0.5',NULL,NULL,NULL),('201010051746','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:39',10344,'EXECUTED','8:40f4b376001d7e8275f3ec38c4b2673f','Update Data','Setting the global property \'patient.identifierSuffix\' to an empty string',NULL,'2.0.5',NULL,NULL,NULL),('201010151054','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:39',10345,'MARK_RAN','8:abe3a64258b8fa35d18382132e201e4f','Create Index','Adding index to form.published column',NULL,'2.0.5',NULL,NULL,NULL),('201010151055','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:40',10346,'MARK_RAN','8:ede27f7bbe00e7d92488436c40e95cb6','Create Index','Adding index to form.retired column',NULL,'2.0.5',NULL,NULL,NULL),('201010151056','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:40',10347,'MARK_RAN','8:95a17d8e94c1c037f484ce2778456583','Create Index','Adding multi column index on form.published and form.retired columns',NULL,'2.0.5',NULL,NULL,NULL),('201010261143','crecabarren','liquibase-update-to-latest.xml','2021-05-09 07:08:40',10348,'MARK_RAN','8:db57d4a9ffbef9fa7bd6fd11f13e4862','Rename Column','Rename neighborhood_cell column to address3 and increase the size to 255 characters',NULL,'2.0.5',NULL,NULL,NULL),('201010261145','crecabarren','liquibase-update-to-latest.xml','2021-05-09 07:08:40',10349,'MARK_RAN','8:861c1dddc95f793a90d8882346962b6f','Rename Column','Rename township_division column to address4 and increase the size to 255 characters',NULL,'2.0.5',NULL,NULL,NULL),('201010261147','crecabarren','liquibase-update-to-latest.xml','2021-05-09 07:08:40',10350,'MARK_RAN','8:e0da5f97d74ee12b59f083cd525e3a89','Rename Column','Rename subregion column to address5 and increase the size to 255 characters',NULL,'2.0.5',NULL,NULL,NULL),('201010261149','crecabarren','liquibase-update-to-latest.xml','2021-05-09 07:08:40',10351,'MARK_RAN','8:4ff46473e5a408f908c24f546ade64da','Rename Column','Rename region column to address6 and increase the size to 255 characters',NULL,'2.0.5',NULL,NULL,NULL),('201010261151','crecabarren','liquibase-update-to-latest.xml','2021-05-09 07:08:40',10352,'MARK_RAN','8:27d4a904b2ff1d2dc5a95f89953dda7e','Rename Column','Rename neighborhood_cell column to address3 and increase the size to 255 characters',NULL,'2.0.5',NULL,NULL,NULL),('201010261153','crecabarren','liquibase-update-to-latest.xml','2021-05-09 07:08:40',10353,'MARK_RAN','8:4384f3a168c246179310ddb83ea84cdb','Rename Column','Rename township_division column to address4 and increase the size to 255 characters',NULL,'2.0.5',NULL,NULL,NULL),('201010261156','crecabarren','liquibase-update-to-latest.xml','2021-05-09 07:08:40',10354,'MARK_RAN','8:f468002c0f4230aaf64643caff634b5e','Rename Column','Rename subregion column to address5 and increase the size to 255 characters',NULL,'2.0.5',NULL,NULL,NULL),('201010261159','crecabarren','liquibase-update-to-latest.xml','2021-05-09 07:08:40',10355,'MARK_RAN','8:dacb6a5db8698edfc5a185a20d213186','Rename Column','Rename region column to address6 and increase the size to 255 characters',NULL,'2.0.5',NULL,NULL,NULL),('20101029-1016','gobi/prasann','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10368,'MARK_RAN','8:6104cefe5f6860cbd88263d0f174d7e5','Create Table, Add Unique Constraint','Create table to store concept stop words to avoid in search key indexing',NULL,'2.0.5',NULL,NULL,NULL),('20101029-1026','gobi/prasann','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10369,'MARK_RAN','8:53e135b8d5a4764106323cbb8af6c096','Insert Row (x10)','Inserting the initial concept stop words',NULL,'2.0.5',NULL,NULL,NULL),('201011011600','jkeiper','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10357,'MARK_RAN','8:c33417b0d7b79c1047e80a570938e7de','Create Index','Adding index to message_state column in HL7 archive table',NULL,'2.0.5',NULL,NULL,NULL),('201011011605','jkeiper','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10358,'EXECUTED','8:d99d2c232a3c1e4621a8d138893b459a','Custom Change','Moving \"deleted\" HL7s from HL7 archive table to queue table',NULL,'2.0.5',NULL,NULL,NULL),('201011051300','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10365,'MARK_RAN','8:8147fa6e97c11507e1f592303259720e','Create Index','Adding index on notification_alert.date_to_expire column',NULL,'2.0.5',NULL,NULL,NULL),('201012081716','nribeka','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10363,'MARK_RAN','8:383dd3fb35a2368b1fbc27bf59118f15','Delete Data','Removing concept that are concept derived and the datatype',NULL,'2.0.5',NULL,NULL,NULL),('201012081717','nribeka','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10364,'MARK_RAN','8:3f230e64be983208e486949e2489377d','Drop Table','Removing concept derived tables',NULL,'2.0.5',NULL,NULL,NULL),('20101209-10000-encounter-add-visit-id-column','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10387,'MARK_RAN','8:2df1b03acdf5a6b0d54b4a25077891df','Add Column, Add Foreign Key Constraint','Adding visit_id column to encounter table',NULL,'2.0.5',NULL,NULL,NULL),('20101209-1353','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10490,'MARK_RAN','8:a2f1f517eb417f7efde2eed25cd78b2c','Add Not-Null Constraint','Adding not-null constraint to orders.as_needed',NULL,'2.0.5',NULL,NULL,NULL),('20101209-1721','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10359,'MARK_RAN','8:9e9d018e3f308f58fa80bc8068d3795e','Add Column','Add \'weight\' column to concept_word table',NULL,'2.0.5',NULL,NULL,NULL),('20101209-1722','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10360,'MARK_RAN','8:0be623f667ba4826338d27cd0aa96b9a','Create Index','Adding index to concept_word.weight column',NULL,'2.0.5',NULL,NULL,NULL),('20101209-1723','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10361,'MARK_RAN','8:e6eefd14cf271f73906b95631d1a6890','Insert Row','Insert a row into the schedule_task_config table for the ConceptIndexUpdateTask',NULL,'2.0.5',NULL,NULL,NULL),('20101209-1731','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10362,'MARK_RAN','8:6b7cd4a7763b5cd3eef94bf9c86a8a1e','Update Data','Setting the value of \'start_on_startup\' to trigger off conceptIndexUpdateTask on startup',NULL,'2.0.5',NULL,NULL,NULL),('201012092009','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:40',10356,'EXECUTED','8:5a6ee12caac3b6ab0999a6bba72bcaf7','Modify Column (x10)','Increasing length of address fields in person_address and location to 255',NULL,'2.0.5',NULL,NULL,NULL),('2011-07-12-1947-add-outcomesConcept-to-program','grwarren','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10398,'MARK_RAN','8:4ab8c5595a1b1f1ce1e31a7e2db04a29','Add Column, Add Foreign Key Constraint','Adding the outcomesConcept property to Program',NULL,'2.0.5',NULL,NULL,NULL),('2011-07-12-2005-add-outcome-to-patientprogram','grwarren','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10399,'MARK_RAN','8:92988d39364b8fabd34281f906e7f510','Add Column, Add Foreign Key Constraint','Adding the outcome property to PatientProgram',NULL,'2.0.5',NULL,NULL,NULL),('201101121434','gbalaji,gobi','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10378,'MARK_RAN','8:54c5f947d09d807f9a6a40e0bc44bdd7','Drop Column','Dropping unused date_started column from obs table',NULL,'2.0.5',NULL,NULL,NULL),('201101221453','suho','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10377,'EXECUTED','8:539410b84b415f17d3108ac726bf5ae8','Modify Column','Increasing the serialized_data column of serialized_object to hold mediumtext',NULL,'2.0.5',NULL,NULL,NULL),('20110124-1030','surangak','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10380,'MARK_RAN','8:41690fc231a4f9057758a2fb39134251','Add Foreign Key Constraint','Adding correct foreign key for concept_answer.answer_drug',NULL,'2.0.5',NULL,NULL,NULL),('20110125-1435','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10371,'MARK_RAN','8:373cbc561e5c827cfa62f38d0e74de98','Add Column','Adding \'start_date\' column to person_address table',NULL,'2.0.5',NULL,NULL,NULL),('20110125-1436','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10372,'MARK_RAN','8:92b60f0b0d6b32865634e8ea0ae014a5','Add Column','Adding \'end_date\' column to person_address table',NULL,'2.0.5',NULL,NULL,NULL),('201101271456-add-enddate-to-relationship','misha680','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10389,'MARK_RAN','8:90dbc7c097acfc01a98815b526cda2a9','Add Column','Adding the end_date column to relationship.',NULL,'2.0.5',NULL,NULL,NULL),('201101271456-add-startdate-to-relationship','misha680','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10388,'MARK_RAN','8:5b24ed14dd68a5736074bae7b5e2121a','Add Column','Adding the start_date column to relationship.',NULL,'2.0.5',NULL,NULL,NULL),('20110201-1625-1','arahulkmit','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10373,'MARK_RAN','8:2e6d682c843b4b5de444fd932b5c63a6','Add Column','Adding \"date_changed\" column to patient_identifier table',NULL,'2.0.5',NULL,NULL,NULL),('20110201-1625-2','arahulkmit','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10374,'MARK_RAN','8:6e112a8f10060fafcde892317204b236','Add Column, Add Foreign Key Constraint','Adding \"changed_by\" column to patient_identifier table',NULL,'2.0.5',NULL,NULL,NULL),('20110201-1626-1','arahulkmit','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10375,'MARK_RAN','8:ce68acff64cd26068b239e698fbcd630','Add Column','Adding \"date_changed\" column to relationship table',NULL,'2.0.5',NULL,NULL,NULL),('20110201-1626-2','arahulkmit','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10376,'MARK_RAN','8:70a24f63744571b1ccbe4fbce1bf47ea','Add Column, Add Foreign Key Constraint','Adding \"changed_by\" column to relationship table',NULL,'2.0.5',NULL,NULL,NULL),('201102081800','gbalaji,gobi','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10379,'MARK_RAN','8:ae7478c0a07c75a3b1bf7c6db2b019e8','Drop Column','Dropping unused date_stopped column from obs table',NULL,'2.0.5',NULL,NULL,NULL),('20110218-1206','rubailly','liquibase-update-to-latest.xml','2011-09-15 00:00:00',10013,'MARK_RAN','8:52f364b1a36198c2024048401494f5a3',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('20110218-1210','rubailly','liquibase-update-to-latest.xml','2011-09-15 00:00:00',10013,'MARK_RAN','8:14d6f807ae4d2f334aa9b8d93845e0bd',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('201102280948','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:35',10278,'EXECUTED','8:ba3abc4baa4d798f52d09937afe469bb','Drop Foreign Key Constraint','Removing the foreign key from users.user_id to person.person_id if it still exists',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030a','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10413,'MARK_RAN','8:cbf1fb1dea4114240ba27eb3903b380f','Rename Table','Renaming the concept_source table to concept_reference_source',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030b','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10414,'MARK_RAN','8:7f53907bf6494ed14dfd7e4bba32a399','Create Table, Add Foreign Key Constraint (x4)','Adding concept_reference_term table',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030b-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10415,'EXECUTED','8:a9335dc5ca9f2ea27acf67021788d3a1','Modify Column','(Fixed)Change concept_reference_term.retired to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030c','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10416,'MARK_RAN','8:7885f4a3923325ebbf9b20c2d76e710e','Create Table, Add Foreign Key Constraint (x3)','Adding concept_map_type table',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030c-fix','sunbiz','liquibase-update-to-latest.xml','2011-09-19 00:00:00',10014,'MARK_RAN','8:7548e6ce94df3068683687e7a7f99337',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('20110301-1030d','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10417,'MARK_RAN','8:0802aea258b9f9655ac9e21095f1008c','Rename Table','Renaming the concept_map table to concept_reference_map',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030e','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10418,'MARK_RAN','8:ca8f281b5735e257a0b912184a5c9063','Add Column','Adding concept_reference_term_id column to concept_reference_map table',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030f','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10419,'MARK_RAN','8:4c4c13f192b0cd7da7d7de1227497aae','Custom Change','Inserting core concept map types',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030g','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10420,'MARK_RAN','8:d6365858db30a5d92ddc31dba97f2311','Add Column, Add Foreign Key Constraint','Adding concept_map_type_id column and a foreign key constraint to concept_reference_map table',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030h','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10421,'MARK_RAN','8:b439329c677473ade4a6eadf707784b9','Add Column, Add Foreign Key Constraint','Adding changed_by column and a foreign key constraint to concept_reference_map table',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030i','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10422,'MARK_RAN','8:7f51b6bec534a039b83b24ab9881efba','Add Column','Adding date_changed column and a foreign key constraint to concept_reference_map table',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030j','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10423,'MARK_RAN','8:2c564409b4763443025e5dfd61daa67e','Create Table, Add Foreign Key Constraint (x5)','Adding concept_reference_term_map table',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030m','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10424,'MARK_RAN','8:f15295af2ea3c533fa8626e9f113474c','Custom Change','Creating concept reference terms from existing rows in the concept_reference_map table',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030n','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10425,'MARK_RAN','8:17a88a578acd46c70251707660ca065c','Add Foreign Key Constraint','Adding foreign key constraint to concept_reference_map.concept_reference_term_id column',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030o','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10426,'MARK_RAN','8:0d25bb6c779222fa15dd5a4d55b05e57','Drop Foreign Key Constraint','Dropping foreign key constraint on concept_reference_map.source column',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030p','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10427,'MARK_RAN','8:9b6815bb916832686f48897cad437505','Drop Column','Dropping concept_reference_map.source column',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030q','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10428,'MARK_RAN','8:bc25b8f0965a382c1038d496779e24d3','Drop Column','Dropping concept_reference_map.source_code column',NULL,'2.0.5',NULL,NULL,NULL),('20110301-1030r','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10429,'MARK_RAN','8:fa861adc577c7fd6b4d8a95b3df82133','Drop Column','Dropping concept_reference_map.comment column',NULL,'2.0.5',NULL,NULL,NULL),('201103011751','abbas','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10381,'EXECUTED','8:ab3253aaf0761754c23390525905b01b','Create Table, Add Foreign Key Constraint (x3)','Create the person_merge_log table',NULL,'2.0.5',NULL,NULL,NULL),('20110326-1','Knoll_Frank','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10456,'EXECUTED','8:bb69a04248c7d2129a5da8ebf4c2d0bc','Add Column, Add Foreign Key Constraint','Add obs.previous_version column (TRUNK-420)',NULL,'2.0.5',NULL,NULL,NULL),('20110326-2','Knoll_Frank','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10459,'EXECUTED','8:d6d191acfac83ee1ecbc831088011217','Custom SQL','Fix all the old void_reason content and add in the new previous_version to the matching obs row (POTENTIALLY VERY SLOW FOR LARGE OBS TABLES)',NULL,'2.0.5',NULL,NULL,NULL),('20110329-2317','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10382,'EXECUTED','8:85017b1c7309955389a91e900ddbbfa4','Delete Data','Removing \'View Encounters\' privilege from Anonymous user',NULL,'2.0.5',NULL,NULL,NULL),('20110329-2318','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10383,'EXECUTED','8:811fb7534042dac50b03315d19f72af6','Delete Data','Removing \'View Observations\' privilege from Anonymous user',NULL,'2.0.5',NULL,NULL,NULL),('20110425-1600-create-visit-attribute-type-table','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10390,'MARK_RAN','8:8b80760cc4cd291ec4245823458241f5','Create Table, Add Foreign Key Constraint (x3)','Creating visit_attribute_type table',NULL,'2.0.5',NULL,NULL,NULL),('20110425-1600-create-visit-attribute-type-table-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10391,'EXECUTED','8:b543b2800a3f431a2058295a2276df75','Modify Column','(Fixed)Change visit_attribute_type.retired to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('20110425-1700-create-visit-attribute-table','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10393,'MARK_RAN','8:73a51fd457452975f971319b52e07e92','Create Table, Add Foreign Key Constraint (x5)','Creating visit_attribute table',NULL,'2.0.5',NULL,NULL,NULL),('20110425-1700-create-visit-attribute-table-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10394,'EXECUTED','8:6423f08e572239efc6791424b9d6ace9','Modify Column','(Fixed)Change visit_attribute.voided to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('20110426-11701','zabil','liquibase-update-to-latest.xml','2021-05-09 07:08:44',10435,'MARK_RAN','8:6e02976998c4465924a64766b04557f2','Create Table, Add Foreign Key Constraint (x4)','Create provider table',NULL,'2.0.5',NULL,NULL,NULL),('20110426-11701-create-provider-table','dkayiwa','liquibase-schema-only.xml','2021-05-09 07:08:01',87,'EXECUTED','8:6e02976998c4465924a64766b04557f2','Create Table, Add Foreign Key Constraint (x4)','Create provider table',NULL,'2.0.5',NULL,NULL,NULL),('20110426-11701-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:44',10436,'EXECUTED','8:21f99433a430ef61cae45aaa8697d9a1','Modify Column','(Fixed)Change provider.retired to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('20110510-11702-create-provider-attribute-type-table','zabil','liquibase-update-to-latest.xml','2021-05-09 07:08:44',10437,'EXECUTED','8:4c4b1eef4d1daf3ca04e8d2bda0ba03f','Create Table, Add Foreign Key Constraint (x3)','Creating provider_attribute_type table',NULL,'2.0.5',NULL,NULL,NULL),('20110510-11702-create-provider-attribute-type-table-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:44',10438,'EXECUTED','8:98a5e5f4fd9945dbebbc71476f06f4f6','Modify Column','(Fixed)Change provider_attribute_type.retired to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('20110628-1400-create-provider-attribute-table','kishoreyekkanti','liquibase-update-to-latest.xml','2021-05-09 07:08:44',10440,'EXECUTED','8:30487ce7bf6e01e597d5bdcd2c37651f','Create Table, Add Foreign Key Constraint (x5)','Creating provider_attribute table',NULL,'2.0.5',NULL,NULL,NULL),('20110628-1400-create-provider-attribute-table-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:44',10441,'EXECUTED','8:2d3c0e46fb801f22ace3289190fcb94d','Modify Column','(Fixed)Change provider_attribute.voided to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('20110705-2300-create-encounter-role-table','kishoreyekkanti','liquibase-update-to-latest.xml','2021-05-09 07:08:44',10442,'MARK_RAN','8:a781c79a82d42eb0c640f7a1634bfe12','Create Table, Add Foreign Key Constraint (x3)','Creating encounter_role table',NULL,'2.0.5',NULL,NULL,NULL),('20110705-2300-create-encounter-role-table-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:44',10443,'EXECUTED','8:537f4b2d1e10580fc498a3ccf3c93434','Modify Column','(Fixed)Change encounter_role.retired to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('20110705-2311-create-encounter-role-table','dkayiwa','liquibase-schema-only.xml','2021-05-09 07:08:01',88,'EXECUTED','8:a781c79a82d42eb0c640f7a1634bfe12','Create Table, Add Foreign Key Constraint (x3)','Creating encounter_role table',NULL,'2.0.5',NULL,NULL,NULL),('20110708-2105','cta','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10397,'MARK_RAN','8:499ad47fee416ccf8275d3ec4a615ede','Add Unique Constraint','Add unique constraint to the concept_source table',NULL,'2.0.5',NULL,NULL,NULL),('201107192313-change-length-of-regex-column','jtellez','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10395,'EXECUTED','8:e2acac9088095b0d6686001470124f73','Modify Column','Increasing maximum length of patient identifier type regex format',NULL,'2.0.5',NULL,NULL,NULL),('20110811-1205-create-encounter-provider-table','sree/vishnu','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10444,'EXECUTED','8:42bd6a51dd55ecda516d60ec0f3695a6','Create Table, Add Foreign Key Constraint (x3)','Creating encounter_provider table',NULL,'2.0.5',NULL,NULL,NULL),('20110811-1205-create-encounter-provider-table-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10445,'EXECUTED','8:0c8f4336d4c1a2c21ee0533187e405df','Modify Column','(Fixed)Change encounter_provider.voided to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('20110817-1544-create-location-attribute-type-table','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10400,'MARK_RAN','8:b67d6fbe077d5709320fa3dc6ec6e0ba','Create Table, Add Foreign Key Constraint (x3)','Creating location_attribute_type table',NULL,'2.0.5',NULL,NULL,NULL),('20110817-1544-create-location-attribute-type-table-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10401,'EXECUTED','8:54f69c863e384b40aca4d55a0f1008be','Modify Column','(Fixed)Change visit_attribute.retired to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('20110817-1601-create-location-attribute-table','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10403,'MARK_RAN','8:c43c78ded673255ae489de00afb98efa','Create Table, Add Foreign Key Constraint (x5)','Creating location_attribute table',NULL,'2.0.5',NULL,NULL,NULL),('20110817-1601-create-location-attribute-table-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10404,'EXECUTED','8:1e33b44a92ddb0ab701fdee6a79c144c','Modify Column','(Fixed)Change visit_attribute.retired to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('20110819-1455-insert-unknown-encounter-role','raff','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10446,'EXECUTED','8:8debfac6b7fa992660ff624c2477d58d','Insert Row','Inserting the unknown encounter role into the encounter_role table',NULL,'2.0.5',NULL,NULL,NULL),('20110825-1000-creating-providers-for-persons-from-encounter','raff','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10447,'EXECUTED','8:d09eb76f79ceb485389b4e868101075d','Custom SQL','Creating providers for persons from the encounter table',NULL,'2.0.5',NULL,NULL,NULL),('20110825-1000-drop-provider-id-column-from-encounter-table','raff','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10449,'EXECUTED','8:7dcdc5b75b8b7c0bca5051be6cf3675c','Drop Foreign Key Constraint, Drop Column','Dropping the provider_id column from the encounter table',NULL,'2.0.5',NULL,NULL,NULL),('20110825-1000-migrating-providers-to-encounter-provider','raff','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10448,'EXECUTED','8:71426149e5750b40093427adff0eca07','Custom SQL','Migrating providers from the encounter table to the encounter_provider table',NULL,'2.0.5',NULL,NULL,NULL),('2011091-0749','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',125,'EXECUTED','8:4afd4079efd3ff663172a71a656cb0af','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('2011091-0750','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',126,'EXECUTED','8:722b0dba4b61dcab30b52c887cf9e0b8','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('20110913-0300','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:44',10430,'MARK_RAN','8:cc8823c1d921823036ac3ed9f2bb5505','Drop Foreign Key Constraint, Add Foreign Key Constraint','Remove ON DELETE CASCADE from relationship table for person_a',NULL,'2.0.5',NULL,NULL,NULL),('20110913-0300b','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:44',10431,'MARK_RAN','8:21d8366ed023110a7ce75934e6ec5106','Drop Foreign Key Constraint, Add Foreign Key Constraint','Remove ON DELETE CASCADE from relationship table for person_b',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0104','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:05',317,'EXECUTED','8:994eef110601ec00914894c4b6e94651','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0114','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:00',69,'EXECUTED','8:3d4ecc1aca24cfccba63760fff22b965','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0117','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:05',318,'EXECUTED','8:102ab5b11c58ef42f225a6d7e0166dc9','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0245','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:05',319,'EXECUTED','8:733624adb629b21a72d150f5400acbf2','Add Unique Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0306','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:00',70,'EXECUTED','8:d1577a646538485feb8450fedeff773b','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0308','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:05',320,'EXECUTED','8:85942a6e70ed22d099815b6e72f396a8','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0310','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:05',321,'EXECUTED','8:8eb1140d1d7d244f103d73ddfeb66a83','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0312','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:00',71,'EXECUTED','8:3342f138915efd12b9eef259b790f806','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0314','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:05',322,'EXECUTED','8:388b084e9b2e9db99a6e6e50acac1d54','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0315','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:05',323,'EXECUTED','8:3409c75179bc800b59bc7b00eb031376','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0317','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',98,'EXECUTED','8:40a24cc34eb0e336d3e9547d0112065f','Add Primary Key','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0321','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:05',324,'EXECUTED','8:566cede85bd9be926a0bb71a0d79277c','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0434','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:05',326,'EXECUTED','8:dd23007aa851d93be1865482135927b4','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0435','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:05',327,'EXECUTED','8:2cb7f73620a4888bfa9cd900a8933855','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0448','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:00',72,'EXECUTED','8:af1c6d38c0a1512361ea93fdb6b6edbd','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0453','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:05',325,'EXECUTED','8:cf7ca65534550a56abe71344b886a6ab','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0509','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',122,'EXECUTED','8:56e2d2bb15a004bd5ac5bf362e7e3f82','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0943','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',123,'EXECUTED','8:e2599444ff422fb5697c582cfa7a78e9','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0945','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:05',328,'EXECUTED','8:dc59ece89c55d66d0906899b6d2f4e7a','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0956','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',124,'EXECUTED','8:25e26185db2276fdaa06fb8e7003dc72','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('20110914-0958','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:05',329,'EXECUTED','8:a7a644bd479b8815bba03ab3b5fd792d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0258','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:05',330,'EXECUTED','8:6a9f6449f288592ea5e47bae65986340','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0259','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',331,'EXECUTED','8:1ad9516c3ccff7c5d0f2c4d0ccb4d820','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0357','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',332,'EXECUTED','8:9857d30e4da66f8ec3def347bd917bef','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0547','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',333,'EXECUTED','8:40387996134eb4915291bf634e37aa2a','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0552','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',334,'EXECUTED','8:78a3e8e747854495f3afc6131ae38ea4','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0603','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',335,'EXECUTED','8:879ec37f7216d61d3d7dd43c0ffc2e37','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0610','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',336,'EXECUTED','8:71b1c44a62d9c2fa3ac4a99ae12970bf','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0634','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',337,'EXECUTED','8:67813c79367264837a9aedbc8cda45dc','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0751','sunbiz','liquibase-core-data.xml','2021-05-09 07:08:07',10029,'EXECUTED','8:c20bfa3cacd99a49bf2e4394a3924a5c','Insert Row','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0803','sunbiz','liquibase-core-data.xml','2021-05-09 07:08:07',10036,'EXECUTED','8:1cb881162fff62bdff9cd8de70216838','Insert Row','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0823','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',338,'EXECUTED','8:5c7873a4c9e718facc72ff713b187821','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0824','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',339,'EXECUTED','8:3b16e2439602fd5d0d7a471e1d31418c','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0825','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',74,'EXECUTED','8:2b7f97660cbf73b850bb567f82a965fa','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0836','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',340,'EXECUTED','8:c25a8391f65339803f7de1c189a6622c','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0837','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',341,'EXECUTED','8:e5ae5343711b1667a433e85d67e4a7c2','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0838','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',342,'EXECUTED','8:579f7479fca5058f113ece4f5940eba8','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0839','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',343,'EXECUTED','8:a40880a17eac8d7c2befe4269796c712','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0840','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',344,'EXECUTED','8:0ff0418c876585fbb78b9d8033b38154','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0841','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',345,'EXECUTED','8:44887a73d028466598f12c11d425584b','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0842','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',346,'EXECUTED','8:a61c3fa2ca3199228f0c97b57f5f0c6e','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0845','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',75,'EXECUTED','8:614827d67d9b8f4f940a3b718c9a868f','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0846','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',347,'EXECUTED','8:459986f989751047b879f2ab5484b555','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0847','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',76,'EXECUTED','8:624de527b9b6a618c6bd31f65089866d','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0848','sunbiz','liquibase-core-data.xml','2021-05-09 07:08:07',10037,'EXECUTED','8:de76d856ac158405e8a828b4d5473c51','Insert Row (x2)','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0848','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',77,'EXECUTED','8:811391518f0aaf7387666454816bb7e7','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-0903','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',348,'EXECUTED','8:c5f743c8e5d86321e9faad7714a034f0','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1045','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',127,'EXECUTED','8:d37207a166aaca60d194ee966bc7e120','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1049','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',349,'EXECUTED','8:67dca3df78ad8f45f5345da7cac35f38','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1051','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',128,'EXECUTED','8:abe3a64258b8fa35d18382132e201e4f','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1052','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',129,'EXECUTED','8:ede27f7bbe00e7d92488436c40e95cb6','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1053','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',130,'EXECUTED','8:95a17d8e94c1c037f484ce2778456583','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1103','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',131,'EXECUTED','8:c33417b0d7b79c1047e80a570938e7de','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1104','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',132,'EXECUTED','8:0be623f667ba4826338d27cd0aa96b9a','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1107','sunbiz','liquibase-core-data.xml','2021-05-09 07:08:07',10038,'EXECUTED','8:3fbd85d611e663f73689e46b1022a6e2','Insert Row','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1133','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',133,'EXECUTED','8:8147fa6e97c11507e1f592303259720e','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1135','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',350,'EXECUTED','8:b307c0f5349edbd181e3cfb64ddb003d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1148','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',351,'EXECUTED','8:c3c65251490400697cc18e3891b9dca1','Add Unique Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1149','sunbiz','liquibase-core-data.xml','2021-05-09 07:08:07',10039,'EXECUTED','8:53e135b8d5a4764106323cbb8af6c096','Insert Row (x10)','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1202','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',352,'EXECUTED','8:0e0a981ccbcce3a4a4dfaa7262670332','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1203','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',353,'EXECUTED','8:25cfe24f72d094570dba3cd507f4ac8a','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1210','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',354,'EXECUTED','8:41690fc231a4f9057758a2fb39134251','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1215','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',73,'EXECUTED','8:4269116d244bf6d2a3c13205f56426ca','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1222','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',78,'EXECUTED','8:a73115d32f5484096004d72dc0b57a76','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1225','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',355,'EXECUTED','8:40d0d2fc674d312a29162f0da9a14b46','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1226','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',356,'EXECUTED','8:d50d8a771c5a981799c16bda2a382529','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1227','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',357,'EXECUTED','8:8da6c886b62b32eac3f5b7a3d67e10c8','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1231','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',79,'EXECUTED','8:c48324984a84aa2ac8ae9862a9424e2b','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1240','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',358,'EXECUTED','8:1fec4b72b749d0d81755f930bacb47c1','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1241','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',359,'EXECUTED','8:1db06dd675d9a7d321aa5d5c710d2e31','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1242','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',360,'EXECUTED','8:a032157868f4714b709ca145ff3695e5','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1243','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',361,'EXECUTED','8:387e39d049631288ee2cf8133ab343ee','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1244','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',362,'EXECUTED','8:e24fea75579c2e94b2cda9ba126a590a','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1245','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',363,'EXECUTED','8:1072d841664d4d1bc7bcea3adaea5b16','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1246','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',364,'EXECUTED','8:9556c1cfa8795950a3b3d454f73cab1b','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1247','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',134,'EXECUTED','8:fe8454bcd5df128318c029e22ad9e9ea','Create Index','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1248','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',365,'EXECUTED','8:a7717355d58a5aa09c9b63d9c379b76f','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1258','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',80,'EXECUTED','8:8a94dc5f641f6fc222d15008da5a12a2','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1301','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',366,'EXECUTED','8:c9105c36d10d4b7910df721357dee582','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1302','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',367,'EXECUTED','8:d1e2064772cf01d87303c1e72296fade','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1303','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',368,'EXECUTED','8:24d89b37a504b649ab28f328a1f5c597','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1307','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',81,'EXECUTED','8:f2cf6b336a586b20420582bd982fbc6c','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1311','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',392,'EXECUTED','8:ead67eaad39389c77abe64eb15bf10ca','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1312','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',369,'EXECUTED','8:bda9eb22045c9d58fc263810cfed7bd5','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1313','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',370,'EXECUTED','8:c3c71ec2ca69aaed775247e914bfa733','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1314','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',371,'EXECUTED','8:27bfbfc163bc36ff7a9ea59fd84578d0','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1315','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',372,'EXECUTED','8:20bddf00dab5e9ad44b8b48e9e799b7c','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1316','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',373,'EXECUTED','8:24baa4b064618a638e619677b06ced54','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1317','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:06',374,'EXECUTED','8:593dacdc206815bb33788bc9e26b9170','Add Unique Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1320','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',375,'EXECUTED','8:2deb6e9613d4166064f769af173919ff','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1323','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',376,'EXECUTED','8:780ff19cd2bb02f1ff17c2e849d5ac74','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1325','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',82,'EXECUTED','8:269eb6c8a2b5708da7896ec4932728f0','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1327','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',377,'EXECUTED','8:b6ed1c67d900499bad7b2e8915684400','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1328','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',378,'EXECUTED','8:a742b4cd576b24716d211b570e1397bd','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1329','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',379,'EXECUTED','8:1d2d42a3e8298efb36d82dcdb7706bbd','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1337','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',83,'EXECUTED','8:0f670f4d0e094548f7ba5c31e3ba2b97','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1342','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',380,'EXECUTED','8:119692e9cb663b78878edbf24ab91bd3','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1343','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',381,'EXECUTED','8:46ac285c7f3e4b567bdd84f39c30dc73','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1344','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',382,'EXECUTED','8:3dab20a653fb83532e2ccf2dba0524d0','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1345','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',383,'EXECUTED','8:6613eaaa4a89a59e09c3920999e73e48','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1346','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',384,'EXECUTED','8:1847ad162d3f62091f97e52f0dd8c1dd','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1435','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',84,'EXECUTED','8:c0f7820a36a74f0a5820501050ec9185','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1440','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',385,'EXECUTED','8:d1f6227c33788dc5bed023dc637e4cd2','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1441','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',386,'EXECUTED','8:be081836e24d509f3a387071a6bc5c22','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1442','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',387,'EXECUTED','8:704c167289fdb193640d5b07a2e417df','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1443','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',388,'EXECUTED','8:df91c3b6faca6f3406b29a8cab00c63d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1450','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',85,'EXECUTED','8:dd9120d3823c30cdfac401df92bca5eb','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1451','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',389,'EXECUTED','8:d3b488259594544b59347e376b27673f','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1452','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',390,'EXECUTED','8:5e0881515fd493523bafdbe301e0b358','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1453','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',391,'EXECUTED','8:76bc43ace6336c00c20eec560827f418','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1459','sunbiz','liquibase-core-data.xml','2021-05-09 07:08:07',10040,'EXECUTED','8:4c4c13f192b0cd7da7d7de1227497aae','Custom Change','Inserting core concept map types',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1524','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',393,'EXECUTED','8:c558a844985e8c07a8daafa8940321eb','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1528','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:01',86,'EXECUTED','8:9c429ec0e80e5bd159c92a3d6208058b','Create Table','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1530','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',394,'EXECUTED','8:274bece0d1bffce6525b8dd9b2c81692','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1531','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',395,'EXECUTED','8:ce869ae90333b8196248dd09233dbccb','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1532','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',396,'EXECUTED','8:b8ed3edfff7329c42196616310aa025d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1533','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',397,'EXECUTED','8:485c2fa3a8444ac0fad21c573c6da07d','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1534','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',398,'EXECUTED','8:a376e8581e22f17b014dc63b435d9a37','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1536','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',399,'EXECUTED','8:17a88a578acd46c70251707660ca065c','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20110915-1700','sunbiz','liquibase-schema-only.xml','2021-05-09 07:08:07',402,'EXECUTED','8:fe6398f690dcce73e63f2358a35bec9c','Insert Row (x18)','',NULL,'2.0.5',NULL,NULL,NULL),('201109152336','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:44',10433,'MARK_RAN','8:c451aced1df1dc71785fa829c18c37df','Update Data','Updating logging level global property',NULL,'2.0.5',NULL,NULL,NULL),('20110919-0638','sunbiz','liquibase-update-to-latest.xml','2011-09-19 00:00:00',10015,'MARK_RAN','8:c2e715a956abd582cdfc0d2a3687ca6c',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('20110919-0639-void_empty_attributes','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:44',10434,'EXECUTED','8:fcec772d0288922a7b5b47a607afa56f','Custom SQL','Void all attributes that have empty string values.',NULL,'2.0.5',NULL,NULL,NULL),('20110922-0551','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:37',10306,'MARK_RAN','8:39508d681f7516cf9b5a26327be4ecff','Modify Column','Changing global_property.property from varbinary to varchar',NULL,'2.0.5',NULL,NULL,NULL),('20110926-1200','raff','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10396,'MARK_RAN','8:3ef8d1055940a0771db494f7af553ae6','Custom SQL','Change all empty concept_source.hl7_code to NULL',NULL,'2.0.5',NULL,NULL,NULL),('201109301703','suho','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10405,'MARK_RAN','8:9757fada669433daa071408fdaf195f3','Update Data','Converting general address format (if applicable)',NULL,'2.0.5',NULL,NULL,NULL),('201109301704','suho','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10406,'MARK_RAN','8:aca928b9b33626159f2f3286e89db814','Update Data','Converting Spain address format (if applicable)',NULL,'2.0.5',NULL,NULL,NULL),('201109301705','suho','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10407,'MARK_RAN','8:91e51392803ac675854e0b6d8a00036c','Update Data','Converting Rwanda address format (if applicable)',NULL,'2.0.5',NULL,NULL,NULL),('201109301706','suho','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10408,'MARK_RAN','8:dc90d947a50a2a7181c9f6a781f67484','Update Data','Converting USA address format (if applicable)',NULL,'2.0.5',NULL,NULL,NULL),('201109301707','suho','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10409,'MARK_RAN','8:29c983a2ad6a23fb97b3022d3d26e004','Update Data','Converting Kenya address format (if applicable)',NULL,'2.0.5',NULL,NULL,NULL),('201109301708','suho','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10410,'MARK_RAN','8:71493b9ab4fa4e7dbb52a373eebf4fb5','Update Data','Converting Lesotho address format (if applicable)',NULL,'2.0.5',NULL,NULL,NULL),('201109301709','suho','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10411,'MARK_RAN','8:e9fead577033768e28cf27f88ac1b601','Update Data','Converting Malawi address format (if applicable)',NULL,'2.0.5',NULL,NULL,NULL),('201109301710','suho','liquibase-update-to-latest.xml','2021-05-09 07:08:43',10412,'MARK_RAN','8:fcdd0dc9413b3ba8779c057e0381b4f3','Update Data','Converting Tanzania address format (if applicable)',NULL,'2.0.5',NULL,NULL,NULL),('201110051353-fix-visit-attribute-type-columns','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10392,'MARK_RAN','8:0c4e69ce9f53ced8b3e3499a1d13ea7b','Add Column (x2)','Refactoring visit_attribute_type table (devs only)',NULL,'2.0.5',NULL,NULL,NULL),('201110072042-fix-location-attribute-type-columns','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:42',10402,'MARK_RAN','8:a989035dca24723de87a0e320fa874b4','Add Column (x2)','Refactoring location_attribute_type table (devs only)',NULL,'2.0.5',NULL,NULL,NULL),('201110072043-fix-provider-attribute-type-columns','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:44',10439,'MARK_RAN','8:6ca4d48a0730805c14401d369b7c9a1c','Add Column (x2)','Refactoring provider_attribute_type table (devs only)',NULL,'2.0.5',NULL,NULL,NULL),('20111008-0938-1','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10450,'EXECUTED','8:41703ff87604623af1acde531f8a847f','Add Column','Allow Global Properties to be typed',NULL,'2.0.5',NULL,NULL,NULL),('20111008-0938-2','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10451,'EXECUTED','8:da67fa2b6f1df1ae3d3101996e095fae','Add Column','Allow Global Properties to be typed',NULL,'2.0.5',NULL,NULL,NULL),('20111008-0938-3','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10452,'EXECUTED','8:e08d2fce7a991f8ad8b572a29edfe8c0','Add Column','Allow Global Properties to be typed',NULL,'2.0.5',NULL,NULL,NULL),('20111008-0938-4','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10453,'EXECUTED','8:b40ce1995b3973edb44159fde30cf10c','Add Column','Allow Global Properties to be typed',NULL,'2.0.5',NULL,NULL,NULL),('201110091820-a','jkeiper','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10454,'MARK_RAN','8:f91a6a26fa2bbe2f2aa773adc00b2e43','Add Column','Add xslt column back to the form table',NULL,'2.0.5',NULL,NULL,NULL),('201110091820-b','jkeiper','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10455,'MARK_RAN','8:8b1655d1fa210ceab38f715c58085791','Add Column','Add template column back to the form table',NULL,'2.0.5',NULL,NULL,NULL),('201110091820-c','jkeiper','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10457,'MARK_RAN','8:c24f73198c50e3ea4d15d7dd9077995f','Rename Table','Rename form_resource table to preserve data; 20111010-1515 reference is for bleeding-edge developers and can be generally ignored',NULL,'2.0.5',NULL,NULL,NULL),('20111010-1515','jkeiper','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10458,'EXECUTED','8:d1af328501320d2630042d9ff59f7bbc','Create Table, Add Foreign Key Constraint, Add Unique Constraint','Creating form_resource table',NULL,'2.0.5',NULL,NULL,NULL),('20111128-1601','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10460,'EXECUTED','8:aa22584cc3e9f06299826c53da91c5c7','Insert Row','Inserting Auto Close Visits Task into \'schedule_task_config\' table',NULL,'2.0.5',NULL,NULL,NULL),('20111209-1400-deleting-non-existing-roles-from-role-role-table','raff','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10461,'EXECUTED','8:5487278421f32da2d31b46327187223d','Custom SQL','Deleting non-existing roles from the role_role table',NULL,'2.0.5',NULL,NULL,NULL),('20111214-1500-setting-super-user-gender','raff','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10464,'EXECUTED','8:fc183f43699ef3c7f980fd439f1367d9','Custom SQL','Setting super user gender',NULL,'2.0.5',NULL,NULL,NULL),('20111218-1830','abbas','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10465,'EXECUTED','8:5990d972bed8ac44270d934de5699349','Add Unique Constraint, Add Column (x6), Add Foreign Key Constraint (x2)','Add unique uuid constraint and attributes inherited from BaseOpenmrsData to the person_merge_log table',NULL,'2.0.5',NULL,NULL,NULL),('20111218-1830-fix','sunbiz','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10466,'EXECUTED','8:3cb13fd64b7b3d1b9c1e685d2eef422b','Modify Column','(Fixed)Change person_merge_log.voided to BOOLEAN',NULL,'2.0.5',NULL,NULL,NULL),('20111219-1404','bwolfe','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10467,'EXECUTED','8:7fadabff0a1e627ad8f39835c8e09996','Update Data','Fix empty descriptions on relationship types',NULL,'2.0.5',NULL,NULL,NULL),('20111222-1659','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10468,'EXECUTED','8:c567cc29efdfa8755e68371df80c0608','Create Table, Create Index','Create clob_datatype_storage table',NULL,'2.0.5',NULL,NULL,NULL),('201118012301','lkellett','liquibase-update-to-latest.xml','2021-05-09 07:08:41',10370,'MARK_RAN','8:56d2e999aa23ae8a6fb10f1e1d6e9b47','Add Column','Adding the discontinued_reason_non_coded column to orders.',NULL,'2.0.5',NULL,NULL,NULL),('201202020847','abbas','liquibase-update-to-latest.xml','2021-05-09 07:08:45',10469,'EXECUTED','8:d8991008694bd642a82d66e0eab76495','Modify data type, Add Not-Null Constraint','Change merged_data column type to CLOB in person_merge_log table',NULL,'2.0.5',NULL,NULL,NULL),('20120322-1510','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:46',10470,'EXECUTED','8:830dbe85a0fd56b5c5ece5b7225f2e09','Add Column','Adding uniqueness_behavior column to patient_identifier_type table',NULL,'2.0.5',NULL,NULL,NULL),('20120330-0954','jkeiper','liquibase-update-to-latest.xml','2021-05-09 07:08:46',10471,'EXECUTED','8:fc35b420fb6f4f4846f5e017a68a3ea5','Modify data type','Increase size of drug name column to 255 characters',NULL,'2.0.5',NULL,NULL,NULL),('20120503-djmod','dkayiwa and djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10491,'EXECUTED','8:b6dba7388bcd4d41dd7a5c4259baff40','Create Table, Add Foreign Key Constraint (x2)','Create test_order table',NULL,'2.0.5',NULL,NULL,NULL),('20120504-1000','raff','liquibase-update-to-latest.xml','2021-05-09 07:08:46',10472,'EXECUTED','8:cc6cc689db5924e3cfacc0ff8768e25f','Drop Table','Dropping the drug_ingredient table',NULL,'2.0.5',NULL,NULL,NULL),('20120504-1010','raff','liquibase-update-to-latest.xml','2021-05-09 07:08:46',10473,'EXECUTED','8:03c6138ff06bcdf1e6d14fc7bd8f004a','Create Table','Creating the drug_ingredient table',NULL,'2.0.5',NULL,NULL,NULL),('20120504-1020','raff','liquibase-update-to-latest.xml','2021-05-09 07:08:46',10474,'EXECUTED','8:0ab99e16e664357c4b075e45900f4407','Add Primary Key','Adding a primary key to the drug_ingredient table',NULL,'2.0.5',NULL,NULL,NULL),('20120504-1030','raff','liquibase-update-to-latest.xml','2021-05-09 07:08:46',10475,'EXECUTED','8:2bf0fad4eaa11d1bc644a07b147f2514','Add Foreign Key Constraint','Adding a new foreign key from drug_ingredient.units to concept.concept_id',NULL,'2.0.5',NULL,NULL,NULL),('20120504-1040','raff','liquibase-update-to-latest.xml','2021-05-09 07:08:47',10476,'EXECUTED','8:51a1211a4eb0bf76018851fbe6a8ad48','Add Foreign Key Constraint','Adding a new foreign key from drug_ingredient.drug_id to drug.drug_id',NULL,'2.0.5',NULL,NULL,NULL),('20120504-1050','raff','liquibase-update-to-latest.xml','2021-05-09 07:08:47',10477,'EXECUTED','8:42f5ddc295fca4e609698624e9122bb8','Add Foreign Key Constraint','Adding a new foreign key from drug_ingredient.ingredient_id to concept.concept_id',NULL,'2.0.5',NULL,NULL,NULL),('201205241728-1','mvorobey','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10486,'MARK_RAN','8:8e5564b918f5ed37a5e4e9f591e5b27b','Add Column, Add Foreign Key Constraint','Add optional property view_privilege to encounter_type table',NULL,'2.0.5',NULL,NULL,NULL),('201205241728-2','mvorobey','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10487,'MARK_RAN','8:a1fb897c3a8cae31813ac90302a817c1','Add Column, Add Foreign Key Constraint','Add optional property edit_privilege to encounter_type table',NULL,'2.0.5',NULL,NULL,NULL),('20120529-2230','mvorobey','liquibase-schema-only.xml','2021-05-09 07:08:07',400,'EXECUTED','8:83886060f9bcb3f8c743b39b7bde4aa0','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20120529-2231','mvorobey','liquibase-schema-only.xml','2021-05-09 07:08:07',401,'EXECUTED','8:b28ab1097a92cdea10ef1b6f2456cc97','Add Foreign Key Constraint','',NULL,'2.0.5',NULL,NULL,NULL),('20120613-0930','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10489,'EXECUTED','8:1176a3533ab6e628829e1ca068aaadb1','Drop Not-Null Constraint','Dropping not null constraint from provider.identifier column',NULL,'2.0.5',NULL,NULL,NULL),('20121007-orders_urgency','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10492,'EXECUTED','8:315a2373649cb932bb2a96bcef36ed43','Add Column','Adding urgency column to orders table',NULL,'2.0.5',NULL,NULL,NULL),('20121007-test_order_laterality','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10493,'EXECUTED','8:791574955077ba15a21655c3cd971b95','Modify data type','Changing test_order.laterality to be a varchar',NULL,'2.0.5',NULL,NULL,NULL),('20121008-order_specimen_source_fk','djazayeri','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10494,'MARK_RAN','8:52b643b1891c542f45df4a6d362f13f9','Add Foreign Key Constraint','Adding FK constraint for test_order.specimen_source if necessary',NULL,'2.0.5',NULL,NULL,NULL),('20121016-1504','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10495,'EXECUTED','8:780a14eb75c1692c4989a8169ab2f708','Drop Foreign Key Constraint, Modify Column, Add Foreign Key Constraint','Removing auto increment from test_order.order_id column',NULL,'2.0.5',NULL,NULL,NULL),('20121020-TRUNK-3610','lluismf','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10496,'EXECUTED','8:602805280d880932a94fcfd534d5a107','Update Data (x2)','Rename global property autoCloseVisits.visitType to visits.autoCloseVisitType',NULL,'2.0.5',NULL,NULL,NULL),('20121021-TRUNK-333','lluismf','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10498,'EXECUTED','8:0d5e1d8fc96ed73b9a76b333c1c4010d','Drop Table','Removing concept set derived table',NULL,'2.0.5',NULL,NULL,NULL),('20121025-TRUNK-213','lluismf','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10497,'EXECUTED','8:6f303bd1747a6924d29c43ee3b593817','Modify Column (x2)','Normalize varchar length of locale columns',NULL,'2.0.5',NULL,NULL,NULL),('20121109-TRUNK-3474','patandre','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10499,'EXECUTED','8:92f9c5a99209f5f71ccb4cb4fc392ff3','Drop Not-Null Constraint','Dropping not null constraint from concept_class.description column',NULL,'2.0.5',NULL,NULL,NULL),('20121112-TRUNK-3474','patandre','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10500,'EXECUTED','8:7df1af16bab05233408b4461fdf14471','Drop Not-Null Constraint','Dropping not null constraint from concept_datatype.description column',NULL,'2.0.5',NULL,NULL,NULL),('20121113-TRUNK-3474','patandre','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10501,'EXECUTED','8:ae49ac026a5e0dc548a6f468039af43c','Drop Not-Null Constraint','Dropping not null constraint from patient_identifier_type.description column',NULL,'2.0.5',NULL,NULL,NULL),('20121113-TRUNK-3474-person-attribute-type','patandre','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10502,'EXECUTED','8:badc91a102c3e59ad3272fb24366a58f','Drop Not-Null Constraint','Dropping not null constraint from person_attribute_type.description column',NULL,'2.0.5',NULL,NULL,NULL),('20121113-TRUNK-3474-privilege','patandre','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10503,'EXECUTED','8:498a6bb6292a64e260820c75ac6c6622','Drop Not-Null Constraint','Dropping not null constraint from privilege.description column',NULL,'2.0.5',NULL,NULL,NULL),('20121114-TRUNK-3474-encounter_type','patandre','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10506,'EXECUTED','8:9c0e7e651d3c7c910aa56658c3129be4','Drop Not-Null Constraint','Dropping not null constraint from encounter_type.description column',NULL,'2.0.5',NULL,NULL,NULL),('20121114-TRUNK-3474-relationship_type','patandre','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10505,'EXECUTED','8:26d74cb76761bac56d6746a9b06a27a5','Drop Not-Null Constraint','Dropping not null constraint from relationship_type.description column',NULL,'2.0.5',NULL,NULL,NULL),('20121114-TRUNK-3474-role','patandre','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10504,'EXECUTED','8:7127e753b29990afafb6587d0bbbd25f','Drop Not-Null Constraint','Dropping not null constraint from role.description column',NULL,'2.0.5',NULL,NULL,NULL),('20121212-TRUNK-2768','patandre','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10507,'EXECUTED','8:9187db1be97c4d91d35a1cc35f4ab748','Add Column','Adding deathdate_estimated column to person.',NULL,'2.0.5',NULL,NULL,NULL),('201301031440-TRUNK-4135','rkorytkowski','liquibase-update-to-latest.xml','2021-05-09 07:08:53',10572,'EXECUTED','8:feadce20f10427194d62fdddec7a5bab','Custom Change','Creating coded order frequencies for drug order frequencies',NULL,'2.0.5',NULL,NULL,NULL),('201301031448-TRUNK-4135','rkorytkowski','liquibase-update-to-latest.xml','2021-05-09 07:08:53',10573,'EXECUTED','8:65ba189466c8817db26bf9da7292e90a','Custom Change','Migrating drug order frequencies to coded order frequencies',NULL,'2.0.5',NULL,NULL,NULL),('201301031455-TRUNK-4135','rkorytkowski','liquibase-update-to-latest.xml','2021-05-09 07:08:53',10574,'EXECUTED','8:0b00899fe3d6b9f81f9811deefd49046','Drop Column','Dropping temporary column drug_order.frequency_text',NULL,'2.0.5',NULL,NULL,NULL),('201306141103-TRUNK-3884','susantan','liquibase-update-to-latest.xml','2021-05-09 07:08:49',10509,'EXECUTED','8:75076e2450e410fc30b8d9198b01d512','Add Foreign Key Constraint (x3)','Adding 3 foreign key relationships (creator,created_by,voided_by) to encounter_provider table',NULL,'2.0.5',NULL,NULL,NULL),('20130626-TRUNK-439','jthoenes','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10508,'EXECUTED','8:57cf0e3f590b2e8a954f87c4d6464510','Update Data','Adding configurability to Patient Header on Dashboard. Therefore the cd4_count property is dropped and\n            replaced with a header.showConcept property.',NULL,'2.0.5',NULL,NULL,NULL),('20130809-TRUNK-4044-duplicateEncounterRoleChangeSet','surangak','liquibase-update-to-latest.xml','2021-05-09 07:08:49',10512,'EXECUTED','8:854400531f71e88e351bb748f9724ed6','Custom Change','Custom changesets to identify and resolve duplicate EncounterRole names',NULL,'2.0.5',NULL,NULL,NULL),('20130809-TRUNK-4044-duplicateEncounterTypeChangeSet','surangak','liquibase-update-to-latest.xml','2021-05-09 07:08:49',10513,'MARK_RAN','8:730273b18b7a29d8aa6ef77bf69e2693','Custom Change','Custom changesets to identify and resolve duplicate EncounterType names',NULL,'2.0.5',NULL,NULL,NULL),('20130809-TRUNK-4044-encounter_role_unique_name_constraint','surangak','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10515,'EXECUTED','8:2549dbe31c7d2ef5c74105fb8b907d5f','Add Unique Constraint','Adding the unique constraint to the encounter_role.name column',NULL,'2.0.5',NULL,NULL,NULL),('20130809-TRUNK-4044-encounter_type_unique_name_constraint','surangak','liquibase-update-to-latest.xml','2021-05-09 07:08:49',10514,'EXECUTED','8:d33c85414c1f1592aeaacfe5568783c4','Add Unique Constraint','Adding the unique constraint to the encounter_type.name column',NULL,'2.0.5',NULL,NULL,NULL),('20130925-TRUNK-4105','hannes','liquibase-update-to-latest.xml','2021-05-09 07:08:49',10510,'EXECUTED','8:d2ad7f39efadf4ae6e8cc21e028d3ae2','Create Index','Adding index on concept_reference_term.code column',NULL,'2.0.5',NULL,NULL,NULL),('20131023-TRUNK-3903','k-joseph','liquibase-update-to-latest.xml','2021-05-09 07:08:49',10511,'MARK_RAN','8:cf91ab0553cbb9ec7cac072849cd326f','Add Column','Adding \"display_precision\" column to concept_numeric table',NULL,'2.0.5',NULL,NULL,NULL),('201310281153-TRUNK-4123','mujir,sushmitharaos','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10542,'EXECUTED','8:be70be300b910a582310a204e754a7fa','Add Column, Add Foreign Key Constraint','Adding previous_order_id to orders',NULL,'2.0.5',NULL,NULL,NULL),('201310281153-TRUNK-4124','mujir,sushmitharaos','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10543,'EXECUTED','8:a493352b87f8552e7595dcd80120969e','Add Column, Update Data, Add Not-Null Constraint','Adding order_action to orders and setting order_actions as NEW for existing orders',NULL,'2.0.5',NULL,NULL,NULL),('201311041510','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10537,'EXECUTED','8:26be1a4e020906ea74e1cde7e44e4ef0','Rename Column','Renaming drug_order.prn column to drug_order.as_needed',NULL,'2.0.5',NULL,NULL,NULL),('201311041511','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10538,'EXECUTED','8:6e51cb7454670c8dac8e059756b3bab5','Add Column','Adding as_needed_condition column to drug_order table',NULL,'2.0.5',NULL,NULL,NULL),('201311041512','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10539,'EXECUTED','8:bfb1543529f3e5559b7ab7a60cc81ee5','Add Column','Adding order_number column to orders table',NULL,'2.0.5',NULL,NULL,NULL),('201311041513','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10540,'MARK_RAN','8:38715220feef4c61b1cd9dcb477f1161','Update Data','Setting order numbers for existing orders',NULL,'2.0.5',NULL,NULL,NULL),('201311041515-TRUNK-4122','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10541,'EXECUTED','8:5f36fc8fa2eaaf81683dae32d11734ee','Add Not-Null Constraint','Making orders.order_number not nullable',NULL,'2.0.5',NULL,NULL,NULL),('20131210-TRUNK-4130','k-joseph','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10547,'EXECUTED','8:01f153d08375fe18d7eaecfb60247b64','Add Column','Adding num_refills column to drug_order table',NULL,'2.0.5',NULL,NULL,NULL),('201312141400-TRUNK-4126','arathy','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10544,'EXECUTED','8:55586a39affe5750b8cfcf783f35ce8d','Modify data type, Rename Column','Renaming drug_order.complex to dosing_type',NULL,'2.0.5',NULL,NULL,NULL),('201312141400-TRUNK-4127','arathy','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10546,'MARK_RAN','8:7c437a28fe021d08c0db024a79f6d9ef','Update Data (x2)','Converting values in drug_order.dosing_type column',NULL,'2.0.5',NULL,NULL,NULL),('201312141401-TRUNK-4126','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10545,'EXECUTED','8:9b14ba27d4610cc3652270925b8ee305','Drop Not-Null Constraint','Making drug_order.dosing_type nullable',NULL,'2.0.5',NULL,NULL,NULL),('20131216-1637','gitahi','liquibase-update-to-latest.xml','2021-05-09 07:08:54',10582,'EXECUTED','8:93db608425b739536413a9643f34e791','Create Table, Add Foreign Key Constraint (x6)','Add drug_reference_map table',NULL,'2.0.5',NULL,NULL,NULL),('201312161618-TRUNK-4129','rkorytkowski','liquibase-update-to-latest.xml','2021-05-09 07:08:51',10553,'EXECUTED','8:8f39307fb685d25a5fcb31a619037d87','Add Column, Add Foreign Key Constraint','Adding quantity_units column to drug_order table',NULL,'2.0.5',NULL,NULL,NULL),('201312161713-TRUNK-4129','rkorytkowski','liquibase-update-to-latest.xml','2021-05-09 07:08:51',10554,'EXECUTED','8:3375513e4491388717db57d88191ab7c','Modify data type','Changing quantity column of drug_order to double',NULL,'2.0.5',NULL,NULL,NULL),('201312162044-TRUNK-4126','k-joseph','liquibase-update-to-latest.xml','2021-05-09 07:08:51',10551,'EXECUTED','8:d3e848a205a746bf4e9740f40c5a7282','Add Column','Adding duration column to drug_order table',NULL,'2.0.5',NULL,NULL,NULL),('201312162059-TRUNK-4126','k-joseph','liquibase-update-to-latest.xml','2021-05-09 07:08:51',10552,'EXECUTED','8:d1005650e5202567e6204bf6b6574689','Add Column, Add Foreign Key Constraint','Adding duration_units column to drug_order table',NULL,'2.0.5',NULL,NULL,NULL),('20131217-TRUNK-4142','banka','liquibase-update-to-latest.xml','2021-05-09 07:08:51',10550,'EXECUTED','8:6c30e47f0cf1ebbefaab6dcc16770e50','Add Column','Adding comment_to_fulfiller column to orders table',NULL,'2.0.5',NULL,NULL,NULL),('20131217-TRUNK-4157','banka','liquibase-update-to-latest.xml','2021-05-09 07:08:51',10549,'EXECUTED','8:3201330e5aa8f7a5e2dc75bd4e95a6fe','Add Column','Adding dosing_instructions column to drug_order table',NULL,'2.0.5',NULL,NULL,NULL),('201312171559-TRUNK-4159','k-joseph','liquibase-update-to-latest.xml','2021-05-09 07:08:51',10548,'EXECUTED','8:a1356a33a2f1f3ee34126bbf3a8e4d94','Create Table, Add Foreign Key Constraint (x4)','Create the order_frequency table',NULL,'2.0.5',NULL,NULL,NULL),('201312181649-TRUNK-4137','k-joseph','liquibase-update-to-latest.xml','2021-05-09 07:08:52',10561,'EXECUTED','8:3bfcd0b9f492c8b516c0800e1452b937','Add Column, Add Foreign Key Constraint','Adding frequency column to test_order table',NULL,'2.0.5',NULL,NULL,NULL),('201312181650-TRUNK-4137','k-joseph','liquibase-update-to-latest.xml','2021-05-09 07:08:52',10562,'EXECUTED','8:0ba86fc85458a0e4df7b8d4742136cd9','Add Column','Adding number_of_repeats column to test_order table',NULL,'2.0.5',NULL,NULL,NULL),('201312182214-TRUNK-4136','k-joseph','liquibase-update-to-latest.xml','2021-05-09 07:08:51',10555,'EXECUTED','8:acb3dcb3c474c68ba94802c675021723','Add Column, Add Foreign Key Constraint','Adding route column to drug_order table',NULL,'2.0.5',NULL,NULL,NULL),('201312182223-TRUNK-4136','k-joseph','liquibase-update-to-latest.xml','2021-05-09 07:08:51',10556,'EXECUTED','8:cb3c2c150182d226e9bd3c435c036d67','Drop Column','Dropping equivalent_daily_dose column from drug_order table',NULL,'2.0.5',NULL,NULL,NULL),('201312191200-TRUNK-4167','banka','liquibase-update-to-latest.xml','2021-05-09 07:08:51',10557,'EXECUTED','8:eec66ebb21605b80079a99a7bf437aa0','Add Column','Adding dose_units column to drug_order table',NULL,'2.0.5',NULL,NULL,NULL),('201312191300-TRUNK-4167','banka','liquibase-update-to-latest.xml','2021-05-09 07:08:52',10558,'EXECUTED','8:324df324d6431af1946d01624b1c64fa','Add Foreign Key Constraint','Adding foreignKey constraint on dose_units',NULL,'2.0.5',NULL,NULL,NULL),('201312201200-TRUNK-4167','banka','liquibase-update-to-latest.xml','2021-05-09 07:08:52',10559,'MARK_RAN','8:ae0a91ef02548a3580eb168c9f3f7317','Custom Change','Migrating old text units to coded dose_units in drug_order',NULL,'2.0.5',NULL,NULL,NULL),('201312201425-TRUNK-4138','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:52',10565,'MARK_RAN','8:7ae5071d34fa145acac884a61df18179','Update Data','Setting order.discontinued_reason to null for stopped orders',NULL,'2.0.5',NULL,NULL,NULL),('201312201523-TRUNK-4138','banka','liquibase-update-to-latest.xml','2021-05-09 07:08:52',10564,'EXECUTED','8:9ca149b68f2df37166fde53eea920fe1','Custom Change','Creating Discontinue Order for discontinued orders',NULL,'2.0.5',NULL,NULL,NULL),('201312201525-TRUNK-4138','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:52',10566,'MARK_RAN','8:f338be595f4d1b489c5a0d7cabfef17c','Update Data','Setting orders.discontinued_reason_non_coded to null for stopped orders',NULL,'2.0.5',NULL,NULL,NULL),('201312201601-TRUNK-4138','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:52',10568,'EXECUTED','8:5ccea84adf3b7dfeabc7b0a3a6c5a6b4','Drop Foreign Key Constraint','Dropping fk constraint on orders.discontinued_by column to users.user_id column',NULL,'2.0.5',NULL,NULL,NULL),('201312201640-TRUNK-4138','banka','liquibase-update-to-latest.xml','2021-05-09 07:08:52',10563,'EXECUTED','8:12ba0eeb2ddebe7c77996b0651669b94','Rename Column','Rename orders.discontinued_date to date_stopped',NULL,'2.0.5',NULL,NULL,NULL),('201312201651-TRUNK-4138','banka','liquibase-update-to-latest.xml','2021-05-09 07:08:52',10567,'EXECUTED','8:900c1a849be3ca5104c1addbf8c90971','Drop Column','Removing discontinued from orders',NULL,'2.0.5',NULL,NULL,NULL),('201312201700-TRUNK-4138','banka','liquibase-update-to-latest.xml','2021-05-09 07:08:52',10569,'EXECUTED','8:9d8f08ab296afa73d3dcaaaa6199d9bf','Drop Column','Removing discontinued_by from orders',NULL,'2.0.5',NULL,NULL,NULL),('201312201800-TRUNK-4167','banka','liquibase-update-to-latest.xml','2021-05-09 07:08:52',10560,'EXECUTED','8:f1e3dd59ccfe7395af57333fe7a0be77','Drop Column','Deleting units column',NULL,'2.0.5',NULL,NULL,NULL),('201312271822-TRUNK-4156','vinay','liquibase-update-to-latest.xml','2021-05-09 07:08:53',10575,'EXECUTED','8:4e4555c7ef47460b32994178eecafa5f','Create Table, Add Foreign Key Constraint (x3)','Adding care_setting table',NULL,'2.0.5',NULL,NULL,NULL),('201312271823-TRUNK-4156','vinay','liquibase-update-to-latest.xml','2021-05-09 07:08:53',10576,'EXECUTED','8:2c0254863d118f52cb87047ddfb9c11c','Insert Row','Adding OUTPATIENT care setting',NULL,'2.0.5',NULL,NULL,NULL),('201312271824-TRUNK-4156','vinay','liquibase-update-to-latest.xml','2021-05-09 07:08:53',10577,'EXECUTED','8:34935ebf282def5963db2270ecc61c74','Insert Row','Adding INPATIENT care setting',NULL,'2.0.5',NULL,NULL,NULL),('201312271826-TRUNK-4156','vinay','liquibase-update-to-latest.xml','2021-05-09 07:08:53',10578,'EXECUTED','8:0c789d70e7602127ae8e5d88fb58df4e','Add Column','Add care_setting column to orders table',NULL,'2.0.5',NULL,NULL,NULL),('201312271827-TRUNK-4156','vinay','liquibase-update-to-latest.xml','2021-05-09 07:08:53',10579,'MARK_RAN','8:254146872f9e6ac0429e414c01a4369d','Custom SQL','Set default value for orders.care_setting column for existing rows',NULL,'2.0.5',NULL,NULL,NULL),('201312271828-TRUNK-4156','vinay','liquibase-update-to-latest.xml','2021-05-09 07:08:53',10580,'EXECUTED','8:658c1a309a30a8880ec77cf3954ca8ea','Add Not-Null Constraint','Make care_setting column non-nullable',NULL,'2.0.5',NULL,NULL,NULL),('201312271829-TRUNK-4156','vinay','liquibase-update-to-latest.xml','2021-05-09 07:08:53',10581,'EXECUTED','8:797ed8855071aa2e47f920e31ff33e54','Add Foreign Key Constraint','Add foreign key constraint',NULL,'2.0.5',NULL,NULL,NULL),('201401031433-TRUNK-4135','rkorytkowski','liquibase-update-to-latest.xml','2021-05-09 07:08:52',10570,'EXECUTED','8:b8892aab8ea4fde9acf3204eac4e20dd','Rename Column','Temporarily renaming drug_order.frequency column to frequency_text',NULL,'2.0.5',NULL,NULL,NULL),('201401031434-TRUNK-4135','rkorytkowski','liquibase-update-to-latest.xml','2021-05-09 07:08:53',10571,'EXECUTED','8:b8a47a7ea5ef704760bc0cd4c167e181','Add Column, Add Foreign Key Constraint','Adding the frequency column to the drug_order table',NULL,'2.0.5',NULL,NULL,NULL),('201401040436-TRUNK-3919','dkithmal','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10516,'EXECUTED','8:64885a85f461f51fdc86f199982e6845','Add Column, Add Foreign Key Constraint','Add changed_by column to location_tag table',NULL,'2.0.5',NULL,NULL,NULL),('201401040438-TRUNK-3919','dkithmal','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10517,'EXECUTED','8:7ae930c289721170583ffc6ca1be8c2c','Add Column','Add date_changed column to location_tag table',NULL,'2.0.5',NULL,NULL,NULL),('201401040440-TRUNK-3919','dkithmal','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10518,'EXECUTED','8:3fd1822464548afa4134b32a042e709d','Add Column, Add Foreign Key Constraint','Add changed_by column to location table',NULL,'2.0.5',NULL,NULL,NULL),('201401040442-TRUNK-3919','dkithmal','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10519,'EXECUTED','8:0d59525585bdbc1c2145fc9c917a6272','Add Column','Add date_changed column to location table',NULL,'2.0.5',NULL,NULL,NULL),('201401101647-TRUNK-4187','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10520,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','Empty','Checks that all existing free text drug order dose units and frequencies have been mapped to\n            concepts, this will fail the upgrade process if any unmapped text is found',NULL,'2.0.5',NULL,NULL,NULL),('201402041600-TRUNK-4138','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:54',10583,'EXECUTED','8:10ddf0c27a96e7fda9d6c4b0e0963653','Drop Foreign Key Constraint','Temporary dropping foreign key on orders.discontinued_reason column',NULL,'2.0.5',NULL,NULL,NULL),('201402041601-TRUNK-4138','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:54',10584,'EXECUTED','8:d4be61b39216cd34a90a8aded6f49b04','Rename Column','Renaming orders.discontinued_reason column to order_reason',NULL,'2.0.5',NULL,NULL,NULL),('201402041602-TRUNK-4138','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:55',10585,'EXECUTED','8:e223c0933b53a0c7e4b4353211d96b2d','Add Foreign Key Constraint','Adding back foreign key on orders.discontinued_reason column',NULL,'2.0.5',NULL,NULL,NULL),('201402041604-TRUNK-4138','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:55',10586,'EXECUTED','8:2c3af06ae1e1e5c7a22007747fc7efba','Rename Column','Renaming orders.discontinued_reason_non_coded column to order_reason_non_coded',NULL,'2.0.5',NULL,NULL,NULL),('201402042238-TRUNK-4202','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:55',10588,'MARK_RAN','8:7929683b57dbbd7be48a2e698c868f55','Custom Change','Converting orders.orderer to reference provider.provider_id',NULL,'2.0.5',NULL,NULL,NULL),('201402051638-TRUNK-4202','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:55',10587,'EXECUTED','8:4c73656c9bcb89e03487349ce1be62b3','Drop Foreign Key Constraint','Temporarily removing foreign key constraint from orders.orderer column',NULL,'2.0.5',NULL,NULL,NULL),('201402051639-TRUNK-4202','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:55',10589,'EXECUTED','8:3b2feb440d7b27d40befa121b23657d4','Add Foreign Key Constraint','Adding foreign key constraint to orders.orderer column',NULL,'2.0.5',NULL,NULL,NULL),('201402120720-TRUNK-3902','k-joseph','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10599,'EXECUTED','8:4ca5cb3aa17f56718f3c56ecaaf7b128','Rename Column','Rename concept_numeric.precise to concept_numeric.allow_decimal',NULL,'2.0.5',NULL,NULL,NULL),('201402241055','Akshika','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10535,'EXECUTED','8:613168bf075ac75360d11f573feefabf','Modify Column','Making orders.start_date not nullable',NULL,'2.0.5',NULL,NULL,NULL),('201402281648-TRUNK-4274','k-joseph','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10536,'EXECUTED','8:fb1c0544bf2516c18a9f90f686d27db6','Modify Column','Making order.encounter required',NULL,'2.0.5',NULL,NULL,NULL),('201403011348','alexisduque','liquibase-update-to-latest.xml','2021-05-09 07:08:55',10590,'EXECUTED','8:fe0c10d32f9b817a295bec10ed17a97e','Modify Column','Make orders.orderer not NULLable',NULL,'2.0.5',NULL,NULL,NULL),('20140304-TRUNK-4170-duplicateLocationAttributeTypeNameChangeSet','harsz89','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10600,'MARK_RAN','8:bcc61948650059bb491e961971af50b5','Custom Change','Custom changeset to identify and resolve duplicate Location Attribute Type names',NULL,'2.0.5',NULL,NULL,NULL),('20140304-TRUNK-4170-location_attribute_type_unique_name','harsz89','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10601,'EXECUTED','8:8840ef4f61f4f2c05e3d41e322de7fa5','Add Unique Constraint','Adding the unique constraint to the location_attribute_type.name column',NULL,'2.0.5',NULL,NULL,NULL),('20140304816-TRUNK-4139','Akshika','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10592,'EXECUTED','8:2e1712a31d04490070783fc9db61c52d','Add Column','Adding scheduled_date column to orders table',NULL,'2.0.5',NULL,NULL,NULL),('201403061758-TRUNK-4284','Banka, Vinay','liquibase-update-to-latest.xml','2021-05-09 07:08:55',10591,'EXECUTED','8:4bf8b412e24209f462942bfc981af0e9','Insert Row','Inserting Frequency concept class',NULL,'2.0.5',NULL,NULL,NULL),('201403070132-TRUNK-4286','andras-szell','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10534,'EXECUTED','8:6dd03bbcf2de9d2645b2cfff5fb0a2aa','Insert Row','Insert order type for test orders',NULL,'2.0.5',NULL,NULL,NULL),('20140313-TRUNK-4288','dszafranek','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10593,'EXECUTED','8:308822b1f80ada31873aa1729c151d5b','Create Table, Add Foreign Key Constraint (x2), Add Primary Key','Add order_type_class_map table',NULL,'2.0.5',NULL,NULL,NULL),('20140314-TRUNK-4283','dszafranek, wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10521,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','Empty','Checking that all orders have start_date column set',NULL,'2.0.5',NULL,NULL,NULL),('20140316-TRUNK-4283','dszafranek, wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10523,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','Empty','Checking that all orders have encounter_id column set',NULL,'2.0.5',NULL,NULL,NULL),('20140318-TRUNK-4265','jkondrat','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10594,'EXECUTED','8:4f65d700426c7aa94fe7838ce8b1dc62','Merge Column, Update Data','Concatenate dose_strength and units to form the value for the new strength field',NULL,'2.0.5',NULL,NULL,NULL),('201403262140-TRUNK-4265','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10524,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','Empty','Checking if there are any drugs with the dose_strength specified but no units',NULL,'2.0.5',NULL,NULL,NULL),('201404091110','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10525,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','Empty','Checking if order_type table is already up to date or can be updated automatically',NULL,'2.0.5',NULL,NULL,NULL),('201404091112','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10529,'EXECUTED','8:853f2329810a15bf5ef36f3130e70eb5','Add Unique Constraint','Adding unique key constraint to order_type.name column',NULL,'2.0.5',NULL,NULL,NULL),('201404091128','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10530,'EXECUTED','8:39511fb040092c43e8f5f3ec58118cdb','Add Column','Adding java_class_name column to order_type table',NULL,'2.0.5',NULL,NULL,NULL),('201404091129','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10531,'EXECUTED','8:f07c00661d9c0245a17d4e32eef42220','Add Column','Adding parent column to order_type table',NULL,'2.0.5',NULL,NULL,NULL),('201404091131','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10533,'EXECUTED','8:b7f8f3c60aebc38f27ea8a81568a9adc','Add Not-Null Constraint','Add not-null constraint on order_type.java_class_name column',NULL,'2.0.5',NULL,NULL,NULL),('201404091516','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10595,'EXECUTED','8:678a7d1063d398f828d0efeff02dc2ec','Add Column, Add Foreign Key Constraint','Add changed_by column to order_type table',NULL,'2.0.5',NULL,NULL,NULL),('201404091517','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10596,'EXECUTED','8:2e448249bde370177f9904a22609c8a7','Add Column','Add date_changed column to order_type table',NULL,'2.0.5',NULL,NULL,NULL),('201404101130','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10532,'EXECUTED','8:acc2139dbc25f461a6f65f371f01c805','Update Data','Setting java_class_name column for drug order type row',NULL,'2.0.5',NULL,NULL,NULL),('201406201443','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10597,'EXECUTED','8:40e2b8be45cba42d7b37e41f1e10e634','Add Column','Add brand_name column to drug_order table',NULL,'2.0.5',NULL,NULL,NULL),('201406201444','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10598,'EXECUTED','8:85dfe5492c7b3a49f07bc90086842844','Add Column','Add dispense_as_written column to drug_order table',NULL,'2.0.5',NULL,NULL,NULL),('201406211643-TRUNK-4401','harsz89','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10527,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','Empty','Checking that all discontinued orders have the discontinued_date column set',NULL,'2.0.5',NULL,NULL,NULL),('201406211703-TRUNK-4401','harsz89','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10528,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','Empty','Checking that all discontinued orders have the discontinued_by column set',NULL,'2.0.5',NULL,NULL,NULL),('201406262016','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10526,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','Empty','Checking that all users that created orders have provider accounts',NULL,'2.0.5',NULL,NULL,NULL),('20140635-TRUNK-4283','dszafranek, wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:50',10522,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','Empty','Checking that all orders have orderer column set',NULL,'2.0.5',NULL,NULL,NULL),('20140715-TRUNK-2999-remove_concept_word','rkorytkowski','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10602,'EXECUTED','8:823f79c3bd5624f810ed81cb2f8b01ad','Drop Table','Removing the concept_word table (replaced by Lucene)',NULL,'2.0.5',NULL,NULL,NULL),('20140718-TRUNK-2999-remove_update_concept_index_task','rkorytkowski','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10603,'EXECUTED','8:d8f65c211d04d19312075861f4995a5d','Delete Data','Deleting the update concept index task',NULL,'2.0.5',NULL,NULL,NULL),('20140719-TRUNK-4445-update_dosing_type_to_varchar_255','mihir','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10606,'EXECUTED','8:d71b286da1e68819d005c837254e84bf','Modify data type','Increase size of dosing type column to 255 characters',NULL,'2.0.5',NULL,NULL,NULL),('20140724-1528','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10604,'EXECUTED','8:e67f765e838502e837d4ac0651073035','Drop Default Value','Dropping default value for drug_order.drug_inventory_id',NULL,'2.0.5',NULL,NULL,NULL),('20140801-TRUNK-4443-rename_order_start_date_to_date_activated','bharti','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10605,'EXECUTED','8:64a97f174a667e831045af5a93bb95b1','Rename Column','Renaming the start_date in order table to date_activated',NULL,'2.0.5',NULL,NULL,NULL),('201408200733-TRUNK-4446','Deepak','liquibase-update-to-latest.xml','2021-05-09 07:08:56',10607,'EXECUTED','8:dba80a93fd9105b84082a603882354e7','Modify data type','Changing duration column of drug_order to int',NULL,'2.0.5',NULL,NULL,NULL),('201409230113-TRUNK-3484','k-joseph','liquibase-update-to-latest.xml','2021-05-09 07:08:57',10610,'MARK_RAN','8:58d6f207f90b799e675c7cb89fff6a56','Update Data','Updating description for visits.encounterTypeToVisitTypeMapping GP to the value set in OpenmrsContants',NULL,'2.0.5',NULL,NULL,NULL),('20141010-trunk-4492','alec','liquibase-update-to-latest.xml','2021-05-09 07:08:58',10612,'MARK_RAN','8:27fd49cfedc85505c11797f46d5c9a72','Drop Column','Dropping the tribe field from patient table because it has been moved to person_attribute.',NULL,'2.0.5',NULL,NULL,NULL),('201410291606-TRUNK-3474','jbuczynski','liquibase-update-to-latest.xml','2021-05-09 07:08:58',10613,'EXECUTED','8:3dda1c6a8738461777bfdb1de7e1b66b','Drop Not-Null Constraint','Dropping not null constraint from program.description column',NULL,'2.0.5',NULL,NULL,NULL),('201410291613-TRUNK-3474','jbuczynski','liquibase-update-to-latest.xml','2021-05-09 07:08:58',10614,'EXECUTED','8:609dd8b60d1360b7e24dcbf12287e987','Drop Not-Null Constraint','Dropping not null constraint from order_type.description column',NULL,'2.0.5',NULL,NULL,NULL),('201410291614-TRUNK-3474','jbuczynski','liquibase-update-to-latest.xml','2021-05-09 07:08:58',10615,'EXECUTED','8:d2f5710af3a8f8f58937508d321426ce','Drop Not-Null Constraint','Dropping not null constraint from concept_name_tag.description column',NULL,'2.0.5',NULL,NULL,NULL),('201410291616-TRUNK-3474','jbuczynski','liquibase-update-to-latest.xml','2021-05-09 07:08:58',10616,'EXECUTED','8:1074e6b56ed6777fb9269d187c26bce1','Drop Not-Null Constraint','Dropping not null constraint from active_list_type.description column',NULL,'2.0.5',NULL,NULL,NULL),('20141103-1030','wyclif','liquibase-update-to-latest.xml','2021-05-09 07:08:58',10617,'EXECUTED','8:98b6390ae26c56e8ec5c2c1a3a3d31bd','Add Column','Adding form_namespace_and_path column to obs table',NULL,'2.0.5',NULL,NULL,NULL),('201411101055-TRUNK-3386','pmuchowski','liquibase-update-to-latest.xml','2021-05-09 07:08:59',10622,'EXECUTED','8:2de993e213dfec1e8b358a4164c8af03','Drop Foreign Key Constraint','Temporarily removing foreign key constraint from person_attribute_type.edit_privilege column',NULL,'2.0.5',NULL,NULL,NULL),('201411101056-TRUNK-3386','pmuchowski','liquibase-update-to-latest.xml','2021-05-09 07:08:59',10623,'EXECUTED','8:4443516e0b36928b1a58f040b8791d68','Drop Foreign Key Constraint','Temporarily removing foreign key constraint from role_privilege.privilege column',NULL,'2.0.5',NULL,NULL,NULL),('201411101057-TRUNK-3386','pmuchowski','liquibase-update-to-latest.xml','2021-05-09 07:08:59',10624,'EXECUTED','8:3f49e3dc4aa458623dfe11c10d6e6c1b','Modify Column','Increasing the size of the privilege column in the privilege table',NULL,'2.0.5',NULL,NULL,NULL),('201411101058-TRUNK-3386','pmuchowski','liquibase-update-to-latest.xml','2021-05-09 07:09:00',10625,'EXECUTED','8:994eef110601ec00914894c4b6e94651','Add Foreign Key Constraint','Adding foreign key constraint to person_attribute_type.edit_privilege column',NULL,'2.0.5',NULL,NULL,NULL),('201411101106-TRUNK-3386','pmuchowski','liquibase-update-to-latest.xml','2021-05-09 07:09:00',10626,'EXECUTED','8:cfc610064cf20217eb1f853fadd52b92','Modify Column','Increasing the size of the privilege column in the role_privilege table',NULL,'2.0.5',NULL,NULL,NULL),('201411101107-TRUNK-3386','pmuchowski','liquibase-update-to-latest.xml','2021-05-09 07:09:00',10627,'EXECUTED','8:1f30c7e5e81967c3e1a1196ccf2f2710','Add Foreign Key Constraint','Adding foreign key constraint to role_privilege.privilege column',NULL,'2.0.5',NULL,NULL,NULL),('20141121-TRUNK-2193','raff','liquibase-update-to-latest.xml','2021-05-09 07:08:58',10618,'EXECUTED','8:90856496e5c98091c58930f9eb5e19ef','Rename Column','Renaming drug_ingredient.quantity to strength',NULL,'2.0.5',NULL,NULL,NULL),('20150108-TRUNK-14','rpuzdrowski','liquibase-update-to-latest.xml','2021-05-09 07:08:58',10620,'EXECUTED','8:1dfd11ff1e6b71c1a7327a0c59d5f931','Delete Data','Removing dashboard.regimen.standardRegimens global property',NULL,'2.0.5',NULL,NULL,NULL),('20150108-TRUNK-3849','rpuzdrowski','liquibase-update-to-latest.xml','2021-05-09 07:08:58',10619,'MARK_RAN','8:1d204100dff76eaa6977d108540e800f','Custom Change','Updating layout.address.format global property',NULL,'2.0.5',NULL,NULL,NULL),('20150122-1414','rkorytkowski','liquibase-update-to-latest.xml','2021-05-09 07:08:38',10332,'EXECUTED','8:616062e6acd8d1dac50b8353ca1d459f','Update Data','Reverting concept name type to NULL for concepts having names tagged as default',NULL,'2.0.5',NULL,NULL,NULL),('20150122-1420','rkorytkowski','liquibase-update-to-latest.xml','2021-05-09 07:08:38',10333,'EXECUTED','8:a1a2b59662d50c016bc8968b794d968d','Update Data, Delete Data (x2)','Setting concept name type to fully specified for names tagged as default',NULL,'2.0.5',NULL,NULL,NULL),('20150211-TRUNK-3709','jkondrat','liquibase-update-to-latest.xml','2021-05-09 07:08:58',10621,'EXECUTED','8:7247188ac237d4d2adfe1029c92bf5c2','Custom Change','Encrypting the users.secret_answer column',NULL,'2.0.5',NULL,NULL,NULL),('20150221-1644','sandeepraparthi','liquibase-update-to-latest.xml','2021-05-09 07:09:01',10628,'EXECUTED','8:45e485ce6f9b8785b930c0374a8d891f','Add Foreign Key Constraint','Adding foreign key on patient_identifier.patient_id column',NULL,'2.0.5',NULL,NULL,NULL),('20150428-TRUNK-4693-1','mseaton','liquibase-update-to-latest.xml','2021-05-09 07:08:57',10608,'MARK_RAN','8:010954269c2f406ff0e1d9085ee07381','Drop Foreign Key Constraint','Removing invalid foreign key constraint from order_type.parent column to order.order_id column',NULL,'2.0.5',NULL,NULL,NULL),('20150428-TRUNK-4693-2','mseaton','liquibase-update-to-latest.xml','2021-05-09 07:08:57',10609,'EXECUTED','8:75e6d21123e6c08353cad016c466452a','Add Foreign Key Constraint','Adding foreign key constraint from order_type.parent column to order_type.order_type_id column',NULL,'2.0.5',NULL,NULL,NULL),('201506051103-TRUNK-4727','Chethan, Preethi','liquibase-update-to-latest.xml','2021-05-09 07:09:01',10629,'EXECUTED','8:124825ed3eb7b8d328f206a02738981d','Add Column','Adding birthtime column to person',NULL,'2.0.5',NULL,NULL,NULL),('201506192000-TRUNK-4729','thomasvandoren','liquibase-update-to-latest.xml','2021-05-09 07:09:01',10630,'EXECUTED','8:1d4b84f18070d55cebd66c603675ef58','Add Column, Add Foreign Key Constraint','Add changed_by column to encounter_type table',NULL,'2.0.5',NULL,NULL,NULL),('201506192001-TRUNK-4729','thomasvandoren','liquibase-update-to-latest.xml','2021-05-09 07:09:01',10631,'EXECUTED','8:134c1bbb31b0bc1be1d05cb004278105','Add Column','Add date_changed column to encounter_type table',NULL,'2.0.5',NULL,NULL,NULL),('201508111304','sns.recommind','liquibase-update-to-latest.xml','2021-05-09 07:09:01',10632,'EXECUTED','8:9672f72dfc57e0a6bb5bd0df42e90040','Create Index','Add an index to the global_property.property column',NULL,'2.0.5',NULL,NULL,NULL),('201508111412','sns.recommind','liquibase-update-to-latest.xml','2021-05-09 07:09:02',10633,'EXECUTED','8:6e676415380fef7f1ac0d9f9033aca69','Create Index','Add an index to the concept_class.name column',NULL,'2.0.5',NULL,NULL,NULL),('201508111415','sns.recommind','liquibase-update-to-latest.xml','2021-05-09 07:09:02',10634,'EXECUTED','8:0eb97161e6a7cea441a2ed63e804feaa','Create Index','Add an index to the concept_datatype.name column',NULL,'2.0.5',NULL,NULL,NULL),('201509281653','Sravanthi','liquibase-update-to-latest.xml','2021-05-09 07:09:02',10643,'EXECUTED','8:6b0b62e1eb0e2be03a7efb772cdc3d77','Add Column','Add drug_non_coded column to drug_order table',NULL,'2.0.5',NULL,NULL,NULL),('20151006-1530','bahmni','liquibase-update-to-latest.xml','2021-05-09 07:09:04',10648,'EXECUTED','8:53759a5bad5df99a0cdae9f89ecd1f52','Create Table, Add Foreign Key Constraint (x3)','Create order_set table',NULL,'2.0.5',NULL,NULL,NULL),('20151006-1537','bahmni','liquibase-update-to-latest.xml','2021-05-09 07:09:04',10649,'EXECUTED','8:021e1756be58ad3d219d72a1253d3918','Create Table, Add Foreign Key Constraint (x6)','Create order_set_member table',NULL,'2.0.5',NULL,NULL,NULL),('20151007-TRUNK-4747-remove_active_list','jdegraft','liquibase-update-to-latest.xml','2021-05-09 07:09:02',10641,'EXECUTED','8:805d64f01b1308d9a8bf77fc7e589690','Drop Table','Removing the active_list table (active_list feature removed)',NULL,'2.0.5',NULL,NULL,NULL),('20151007-TRUNK-4747-remove_active_list_allergy','jdegraft','liquibase-update-to-latest.xml','2021-05-09 07:09:02',10640,'EXECUTED','8:d065b749b4efbd4f1fe5ebb652002c06','Drop Table','Removing the active_list_allergy table (active_list feature removed)',NULL,'2.0.5',NULL,NULL,NULL),('20151007-TRUNK-4747-remove_active_list_problem','jdegraft','liquibase-update-to-latest.xml','2021-05-09 07:09:02',10639,'EXECUTED','8:47a86f44747e4df00fe9f45adbc0f487','Drop Table','Removing the active_list_problem table (active_list feature removed)',NULL,'2.0.5',NULL,NULL,NULL),('20151007-TRUNK-4747-remove_active_list_type','jdegraft','liquibase-update-to-latest.xml','2021-05-09 07:09:02',10642,'EXECUTED','8:693196ad558eda718e394a7c914971d1','Drop Table','Removing the active_list_type table (active_list feature removed)',NULL,'2.0.5',NULL,NULL,NULL),('20151022-TRUNK-4750','gwasilwa','liquibase-update-to-latest.xml','2021-05-09 07:09:03',10644,'EXECUTED','8:036050f0ba60f45596591e876d558ffd','Add Column (x2)','Adding address columns (7-15) to person_address and location',NULL,'2.0.5',NULL,NULL,NULL),('20151118-1630','bahmni','liquibase-update-to-latest.xml','2021-05-09 07:09:04',10650,'EXECUTED','8:d7449cf960dbec1d5b9765c2fe4f0ac5','Create Table, Add Foreign Key Constraint (x6)','Create order_group table',NULL,'2.0.5',NULL,NULL,NULL),('20151118-1640','bahmni','liquibase-update-to-latest.xml','2021-05-09 07:09:04',10651,'EXECUTED','8:a120b265a54af40422854ac577880d6e','Add Column, Add Foreign Key Constraint','Adding \'order_group_id\' column to orders table',NULL,'2.0.5',NULL,NULL,NULL),('20151218-1729','Rahul,Swathi','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10714,'EXECUTED','8:663120bc2e414e0ef0dc8c53ebcb3136','Create Table, Add Foreign Key Constraint (x5)','Creating patient_program_attribute table',NULL,'2.0.5',NULL,NULL,NULL),('20160201-TRUNK-1505','mnagasowmya','liquibase-update-to-latest.xml','2021-05-09 07:09:03',10645,'EXECUTED','8:7825e4e23a06261e731818c1060c7d51','Drop Column','Removing a column value_boolean from obs table',NULL,'2.0.5',NULL,NULL,NULL),('20160202-1743','rkorytkowski','liquibase-update-to-latest.xml','2021-05-09 07:09:04',10647,'EXECUTED','8:484954fb75c20307a88b0ae4171dda02','Add Not-Null Constraint (x53)','Set uuid columns to \"NOT NULL\", if not set already for 1.9.x tables',NULL,'2.0.5',NULL,NULL,NULL),('20160212-1020','bahmni','liquibase-update-to-latest.xml','2021-05-09 07:09:04',10652,'EXECUTED','8:088bfc1ebb05ceafd3237b844541f0aa','Add Column','Adding \'sort_weight\' column to orders table',NULL,'2.0.5',NULL,NULL,NULL),('20160216-1700','bahmni','liquibase-update-to-latest.xml','2021-05-09 07:09:03',10646,'EXECUTED','8:83647104df9b6914cb009179f10e9dc9','Custom Change','Set uuid for columns in all tables which has uuid as null. This is required for successful run of next changeSet.',NULL,'2.0.5',NULL,NULL,NULL),('20160427-0950-create-concept-attribute-type-table','bahmni','liquibase-update-to-latest.xml','2021-05-09 07:09:04',10655,'EXECUTED','8:0e3bb5143dc3562a4106f5f010c950e8','Create Table, Add Foreign Key Constraint (x3)','Creating concept_attribute_type table',NULL,'2.0.5',NULL,NULL,NULL),('20160427-0954-create-concept-attribute-table','bahmni','liquibase-update-to-latest.xml','2021-05-09 07:09:05',10656,'EXECUTED','8:f1aff3972d637493a6adc91be193348c','Create Table, Add Foreign Key Constraint (x5)','Creating concept_attribute table',NULL,'2.0.5',NULL,NULL,NULL),('201604281645','vishnuraom','liquibase-update-to-latest.xml','2021-05-09 07:09:04',10653,'MARK_RAN','8:41c185d784b1e6f5a717f94ed4c444dd','Update Data','Converting values in drug_order.dosing_type column from SIMPLE to org.openmrs.SimpleDosingInstructions (TRUNK-4845)',NULL,'2.0.5',NULL,NULL,NULL),('201604281646','vishnuraom','liquibase-update-to-latest.xml','2021-05-09 07:09:04',10654,'MARK_RAN','8:7dd6cc5720b5bcb82c811bac7c2baf57','Update Data','Converting values in drug_order.dosing_type column from FREE_TEXT to org.openmrs.FreeTextDosingInstructions(TRUNK-4845)',NULL,'2.0.5',NULL,NULL,NULL),('201609171146-1','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:06',10667,'EXECUTED','8:a1ffc1cc8db22ad700b3d5789b4ca80e','Drop Foreign Key Constraint','Dropping foreign key constraint member_patient',NULL,'2.0.5',NULL,NULL,NULL),('201609171146-1.1','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10668,'EXECUTED','8:c1e8d888a52cd6830f1c7245534c931e','Drop Foreign Key Constraint','Dropping foreign key constraint parent_cohort',NULL,'2.0.5',NULL,NULL,NULL),('201609171146-1.2','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10669,'EXECUTED','8:1eb5a9ee7233979adbee989a91140325','Drop Primary Key','Dropping primary key for cohort_member',NULL,'2.0.5',NULL,NULL,NULL),('201609171146-2','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10670,'EXECUTED','8:2b6770c6367227bccaec91119037bb92','Add Column','Adding column cohort_member.cohort_member_id',NULL,'2.0.5',NULL,NULL,NULL),('201609171146-2.1','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10671,'MARK_RAN','8:0958768d360dcd6a6f07dec01138c55d','Custom Change','Updating cohort member ids',NULL,'2.0.5',NULL,NULL,NULL),('201609171146-2.2','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10672,'EXECUTED','8:efac82e8f62fe999ae8cdd851256a571','Add Primary Key','Adding primary key to cohort_member.cohort_member_id',NULL,'2.0.5',NULL,NULL,NULL),('201609171146-2.3','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10673,'EXECUTED','8:7c93380a4682620b5c0881d6b02bfbdf','Set Column as Auto-Increment','Adding auto increment property to cohort_member.cohort_member_id',NULL,'2.0.5',NULL,NULL,NULL),('201609171146-3','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10674,'EXECUTED','8:abfcac2b2f7c09993a274548c146fa04','Add Column','Adding column cohort_member.start_date',NULL,'2.0.5',NULL,NULL,NULL),('201609171146-4','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10676,'EXECUTED','8:04b0ef23418e3ea2a00eacbbcfbc00cc','Add Column','Adding column cohort_member.end_date',NULL,'2.0.5',NULL,NULL,NULL),('201609171146-5','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10677,'EXECUTED','8:4ea126f0da0ddd2e03644de5cc91edb0','Add Column','Adding column cohort_member.creator',NULL,'2.0.5',NULL,NULL,NULL),('201609171146-5.1','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10678,'MARK_RAN','8:f23677031ae31460d2748c50b71d5eb4','Update Data','Updating cohort_member.creator value',NULL,'2.0.5',NULL,NULL,NULL),('201609171146-5.2','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10679,'EXECUTED','8:4612418089a691bf2a01b68dd9423390','Add Foreign Key Constraint','Adding foreign key constraint to cohort_member.creator',NULL,'2.0.5',NULL,NULL,NULL),('201610042145-1','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10680,'EXECUTED','8:433d5a023c2a97ae8e1447eb83163d30','Add Column','Adding column cohort_member.date_created',NULL,'2.0.5',NULL,NULL,NULL),('201610042145-1.1','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10681,'MARK_RAN','8:ec3182a6d6c5353fa647098aabb665b3','Update Data','Updating cohort_member.date_created with value cohort.date_created',NULL,'2.0.5',NULL,NULL,NULL),('201610042145-2','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10687,'EXECUTED','8:6158704b588ee00c5e328b2bab9d171e','Add Column','Adding column cohort_member.uuid',NULL,'2.0.5',NULL,NULL,NULL),('201610042145-2.1','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10688,'EXECUTED','8:a80e35af13b110ae2544b75cf3d07243','Update Data','Generating UUIDs for all rows in cohort_member table via built in uuid function.',NULL,'2.0.5',NULL,NULL,NULL),('201610042145-2.2','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10689,'EXECUTED','8:e21714083cfc6eaca753218bdbaebff4','Add Unique Constraint','Adding unique constraint to cohort_member.uuid',NULL,'2.0.5',NULL,NULL,NULL),('201610131530-1','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10682,'EXECUTED','8:2c34109121c89941136d4d148f52d0a4','Add Column','Adding column cohort_member.voided',NULL,'2.0.5',NULL,NULL,NULL),('201610131530-1.1','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10683,'EXECUTED','8:7771c874bb3fe2a9102c8ddf8b2b137d','Add Default Value','Adding defaultValue for cohort_member.voided',NULL,'2.0.5',NULL,NULL,NULL),('201610131530-2','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10684,'EXECUTED','8:c89d07098df9f58356d3284d53b30ee5','Add Column','Adding column cohort_member.voided_by',NULL,'2.0.5',NULL,NULL,NULL),('201610131530-3','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10685,'EXECUTED','8:b33935a613e8e7308ee15b300bc94270','Add Column','Adding column cohort_member.date_voided',NULL,'2.0.5',NULL,NULL,NULL),('201610131530-4','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10686,'EXECUTED','8:3a18b5e8d5f23f27051b0d8dd144a0cc','Add Column','Adding column cohort_member.void_reason',NULL,'2.0.5',NULL,NULL,NULL),('201610242135-1','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10690,'EXECUTED','8:fa5a33f95d868d1052807395813e37d1','Add Foreign Key Constraint','Adding foreign key constraint to cohort_member.cohort_id',NULL,'2.0.5',NULL,NULL,NULL),('201610242135-2','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10691,'EXECUTED','8:2cae31214498cbfd57211a94afbeaa7e','Add Foreign Key Constraint','Adding foreign key constraint to cohort_member.patient_id',NULL,'2.0.5',NULL,NULL,NULL),('201610242135-3','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:07',10675,'MARK_RAN','8:65e8273dbaf897b4e34769d92363e9f5','Update Data','Updating cohort_start_date with value cohort.date_created',NULL,'2.0.5',NULL,NULL,NULL),('201611121945-1','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10692,'EXECUTED','8:00f89c8fc8d81fd3f662b33b10b22415','Drop Default Value','Dropping defaultValue for cohort_member.cohort_id',NULL,'2.0.5',NULL,NULL,NULL),('201611121945-2','vshankar','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10693,'EXECUTED','8:c8cc20aa06d6626210c3db2053a81b3e','Drop Default Value','Dropping defaultValue for cohort_member.patient_id',NULL,'2.0.5',NULL,NULL,NULL),('20170707-TRUNK-5185-1','mogoodrich','liquibase-update-to-latest.xml','2021-05-09 07:09:05',10664,'EXECUTED','8:735b6bfe17b5c70118e1bef0ec2e3b9b','Create Index','Adding unique index on concept_reference_source uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20170707-TRUNK-5185-2','mogoodrich','liquibase-update-to-latest.xml','2021-05-09 07:09:06',10665,'EXECUTED','8:f8b2a9472948917c4824525c825f13a9','Create Index','Adding unique index on concept_reference_map uuid column',NULL,'2.0.5',NULL,NULL,NULL),('20180405131015-TRUNK-5333','alicerowan','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10719,'MARK_RAN','8:4ca5cb3aa17f56718f3c56ecaaf7b128','Rename Column','Rename concept_numeric.precise to concept_numeric.allow_decimal',NULL,'2.0.5',NULL,NULL,NULL),('20180508-1000','Kelechi+iheanyi','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10715,'MARK_RAN','8:845dfe877a088206a28fcbd0c04dd2fd','Rename Table','Rename conditions table to emrapi_conditions',NULL,'2.0.5',NULL,NULL,NULL),('20180508-1001','Kelechi+iheanyi','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10716,'EXECUTED','8:88d01340cbf5c037e124d0599522bb13','Create Table, Add Foreign Key Constraint (x7)','Add conditions table',NULL,'2.0.5',NULL,NULL,NULL),('20180508-1002','Kelechi+iheanyi','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10717,'MARK_RAN','8:69842fc8b8dda5fbaa7b4755d2c58f02','Custom SQL','Migrate data from the emrapi_conditions table to the new conditions table',NULL,'2.0.5',NULL,NULL,NULL),('20180706-passwordreset','harisu+fanyui','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10725,'EXECUTED','8:441a4efcc2181b70efd7cb0384ec5563','Add Column','Adding column users.activation_key',NULL,'2.0.5',NULL,NULL,NULL),('20180808-passwordreset','harisu+fanyui','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10726,'EXECUTED','8:52aab8137a1343c58822432e90d8e530','Add Column','Adding column users.email',NULL,'2.0.5',NULL,NULL,NULL),('20181402-TRUNK-5339','esirkings','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10718,'EXECUTED','8:daa25283474716219ec1e857668a3ff8','Create Table, Add Foreign Key Constraint (x9)','Creating encounter_diagnosis table',NULL,'2.0.5',NULL,NULL,NULL),('20190625-Trunk-5411','gitacliff','liquibase-update-to-latest.xml','2021-05-09 07:09:10',10729,'EXECUTED','8:54df679826e34a04b8b80942e94a7b7a','Add Column, Add Foreign Key Constraint','Adding \"dose_limit_units\" column to drug table',NULL,'2.0.5',NULL,NULL,NULL),('20190815-Trunk-5412','gitacliff','liquibase-update-to-latest.xml','2022-05-09 13:53:00',10736,'EXECUTED','8:097971ad4b683c624ef7b5d9afb8faea','addColumn tableName=order_group; addForeignKeyConstraint baseTableName=order_group, constraintName=order_group_order_group_reason_fk, referencedTableName=concept','Adding \"order_group_reason\" column to order_group table',NULL,'4.4.1',NULL,NULL,NULL),('20190815-Trunk-5650','gitacliff','liquibase-update-to-latest.xml','2022-05-09 13:53:00',10737,'EXECUTED','8:683558c2e7716af357494252a94aee20','addColumn tableName=order_group; addForeignKeyConstraint baseTableName=order_group, constraintName=order_group_parent_order_group_fk, referencedTableName=order_group','Adding \"parent_order_group\" column to order_group table',NULL,'4.4.1',NULL,NULL,NULL),('20190815-Trunk-5651','gitacliff','liquibase-update-to-latest.xml','2022-05-09 13:53:00',10739,'EXECUTED','8:f57b630542abaf86eb5f11cb4c3e9ad5','addColumn tableName=order_group; addForeignKeyConstraint baseTableName=order_group, constraintName=order_group_previous_order_group_fk, referencedTableName=order_group','Adding \"previous_order_group\" column to order_group table',NULL,'4.4.1',NULL,NULL,NULL),('2020-08-29-2200-TRUNK-5821','miirochristopher','liquibase-update-to-latest.xml','2021-05-09 07:09:10',10734,'EXECUTED','8:b19d78a89af7cbbc1146acfdc3bc9ee5','Create Index','Adding index to order_number column in Orders table',NULL,'2.0.5',NULL,NULL,NULL),('2020-08-30-100-TRUNK-5821','miirochristopher','liquibase-update-to-latest.xml','2021-05-09 07:09:11',10735,'EXECUTED','8:ebab8d61ca65d04b73dbed42acebbe6e','Create Index','Adding index to accession_number column in Orders table',NULL,'2.0.5',NULL,NULL,NULL),('2020-08-31-2200-TRUNK-5821','miirochristopher','liquibase-update-to-latest.xml','2022-05-09 13:53:01',10744,'MARK_RAN','8:b19d78a89af7cbbc1146acfdc3bc9ee5','createIndex indexName=orders_order_number, tableName=orders','Adding index to order_number column in Orders table',NULL,'4.4.1',NULL,NULL,NULL),('2020-08-32-100-TRUNK-5821','miirochristopher','liquibase-update-to-latest.xml','2022-05-09 13:53:01',10745,'MARK_RAN','8:ebab8d61ca65d04b73dbed42acebbe6e','createIndex indexName=orders_accession_number, tableName=orders','Adding index to accession_number column in Orders table',NULL,'4.4.1',NULL,NULL,NULL),('2020-09-16-1700-TRUNK-5736','miirochristopher','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:05',10762,'EXECUTED','8:40649e743e95f164311fbe7a4d9db53c','dropForeignKeyConstraint baseTableName=users, constraintName=user_who_changed_user; addForeignKeyConstraint baseTableName=users, constraintName=user_who_changed_user, referencedTableName=users','Updating foreign key user_who_changed_user to add delete CASCADE',NULL,'4.4.1',NULL,NULL,NULL),('20200714-Trunk-5862','loliveira','liquibase-update-to-latest.xml','2021-05-09 07:09:10',10733,'EXECUTED','8:c2d4c311dcab0c2c9507b75bfdd78a3c','Add Column','Adding \"form_namespace_and_path\" column to conditions table',NULL,'2.0.5',NULL,NULL,NULL),('20200723-TRUNK-5410','tendomart','liquibase-update-to-latest.xml','2022-05-09 13:53:01',10743,'EXECUTED','8:47b18620e5dd10a8c0f7a13281af80ce','createTable tableName=order_group_attribute; addForeignKeyConstraint baseTableName=order_group_attribute, constraintName=order_group_attribute_order_group_fk, referencedTableName=order_group; addForeignKeyConstraint baseTableName=order_group_attri...','Creating order_group_attribute table',NULL,'4.4.1',NULL,NULL,NULL),('2021-09-02-TRUNK-6020-a','tendomart','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:02',10753,'EXECUTED','8:aadbb027ec7a7517476c91d72adfeb81','modifyDataType columnName=property, tableName=user_property','Increasing user_property.property from VARCHAR(100) to VARCHAR(255)',NULL,'4.4.1',NULL,NULL,NULL),('2021-09-02-TRUNK-6020-b','tendomart','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:02',10754,'EXECUTED','8:85b6045f5edd6bb0e8196d34ce45af93','modifyDataType columnName=property_value, tableName=user_property','Changing user_property.property_value from VARCHAR(255) to LONGTEXT',NULL,'4.4.1',NULL,NULL,NULL),('2021-17-11-0222-TRUNK-6044','miirochristopher','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:05',10763,'EXECUTED','8:891345f735354d676708be6a6901e6d6','renameColumn newColumnName=dx_rank, oldColumnName=rank, tableName=encounter_diagnosis','Renaming column rank to dx_rank because rank is a reserved word in MySQL 8.0.2 and later',NULL,'4.4.1',NULL,NULL,NULL),('2021-24-10-1000-TRUNK-6038','miirochristopher','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:04',10760,'EXECUTED','8:64667c33ce83ef617adcec76bb0d8d07','createTable tableName=diagnosis_attribute_type; addForeignKeyConstraint baseTableName=diagnosis_attribute_type, constraintName=diagnosis_attribute_type_creator_fk, referencedTableName=users; addForeignKeyConstraint baseTableName=diagnosis_attribut...','Creating diagnosis_attribute_type table',NULL,'4.4.1',NULL,NULL,NULL),('2021-24-10-1145-TRUNK-6038','miirochristopher','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:04',10761,'EXECUTED','8:f8ab3e462613d00033a3c048dd1dfd20','createTable tableName=diagnosis_attribute; addForeignKeyConstraint baseTableName=diagnosis_attribute, constraintName=diagnosis_attribute_diagnosis_fk, referencedTableName=encounter_diagnosis; addForeignKeyConstraint baseTableName=diagnosis_attribu...','Creating diagnosis_attribute table',NULL,'4.4.1',NULL,NULL,NULL),('2021-27-09-0200-TRUNK-6027','miirochristopher','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:03',10755,'EXECUTED','8:06f763bdcac48ebd7f3a3c27b1659968','createTable tableName=order_attribute_type; addForeignKeyConstraint baseTableName=order_attribute_type, constraintName=order_attribute_type_creator_fk, referencedTableName=users; addForeignKeyConstraint baseTableName=order_attribute_type, constrai...','Creating order_attribute_type table',NULL,'4.4.1',NULL,NULL,NULL),('2021-27-09-0300-TRUNK-6027','miirochristopher','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:03',10756,'EXECUTED','8:61f02d74b284d8b7ce979fa00a81a4a7','createTable tableName=order_attribute; addForeignKeyConstraint baseTableName=order_attribute, constraintName=order_attribute_order_fk, referencedTableName=orders; addForeignKeyConstraint baseTableName=order_attribute, constraintName=order_attribut...','Creating order_attribute table',NULL,'4.4.1',NULL,NULL,NULL),('3-increase-privilege-col-size-person_attribute_type','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:48',10488,'EXECUTED','8:8c799f623a82031f88eede87cc3f37e9','Drop Foreign Key Constraint, Modify Column, Add Foreign Key Constraint','Increasing the size of the edit_privilege column in the person_attribute_type table',NULL,'2.0.5',NULL,NULL,NULL),('add_based_on_reference_join_table_20200311','pmanko','liquibase.xml','2022-05-09 13:53:39',10770,'EXECUTED','8:9fed57d573d504ea890f9f13132f55fe','createTable tableName=fhir_task_based_on_reference; addForeignKeyConstraint baseTableName=fhir_task_based_on_reference, constraintName=task_based_on_fk, referencedTableName=fhir_task; addForeignKeyConstraint baseTableName=fhir_task_based_on_refere...','',NULL,'4.4.1',NULL,NULL,NULL),('add_ciel_fhir_concept_source_20200221','ibacher','liquibase.xml','2022-05-09 13:53:38',10766,'EXECUTED','8:f2e2cb0a28fe3b6a94a95e49661a5bb7','sql','',NULL,'4.4.1',NULL,NULL,NULL),('add_default_duration_unit_20200930','ibacher','liquibase.xml','2022-05-09 13:53:44',10781,'EXECUTED','8:1f653cca60adfae5c8c91994cb23beea','sql; sql; sql; sql; sql; sql; sql','',NULL,'4.4.1',NULL,NULL,NULL),('add_default_observation_categories_20200930','ibacher','liquibase.xml','2022-05-09 13:53:44',10779,'EXECUTED','8:73affe6c6768796f73b415e5ce59f160','sql; sql; sql','',NULL,'4.4.1',NULL,NULL,NULL),('add_fhir_concept_source_20200105','ibacher','liquibase.xml','2022-05-09 13:53:37',10764,'EXECUTED','8:32021b3c1947c434aa9b2c00d550a0d4','createTable tableName=fhir_concept_source; addForeignKeyConstraint baseTableName=fhir_concept_source, constraintName=fhir_concept_source_concept_reference_source_fk, referencedTableName=concept_reference_source; addForeignKeyConstraint baseTableNa...','',NULL,'4.4.1',NULL,NULL,NULL),('add_fhir_diagnostic_report_20200917','ibacher','liquibase.xml','2022-05-09 13:53:41',10774,'EXECUTED','8:8e027c92045d9cdc7817bcdcf72079ac','createTable tableName=fhir_diagnostic_report; addForeignKeyConstraint baseTableName=fhir_diagnostic_report, constraintName=fhir_diagnostic_report_creator, referencedTableName=users; addForeignKeyConstraint baseTableName=fhir_diagnostic_report, con...','Create the table for storing Diagnostic Reports',NULL,'4.4.1',NULL,NULL,NULL),('add_fhir_diagnostic_report_performers_20200917','ibacher','liquibase.xml','2022-05-09 13:53:42',10775,'EXECUTED','8:82f5bbdcf8b2bd705a2c20c61a27d977','createTable tableName=fhir_diagnostic_report_performers; addForeignKeyConstraint baseTableName=fhir_diagnostic_report_performers, constraintName=fhir_diagnostic_report_performers_diagnostic_report, referencedTableName=fhir_diagnostic_report; addFo...','',NULL,'4.4.1',NULL,NULL,NULL),('add_fhir_diagnostic_report_results_20201020','ibacher','liquibase.xml','2022-05-09 13:53:43',10776,'EXECUTED','8:0e92072a259d4762a0f1b3bb2749f444','createTable tableName=fhir_diagnostic_report_results; addForeignKeyConstraint baseTableName=fhir_diagnostic_report_results, constraintName=fhir_diagnostic_report_results_diagnostic_report, referencedTableName=fhir_diagnostic_report; addForeignKeyC...','',NULL,'4.4.1',NULL,NULL,NULL),('add_fhir_duration_unit_map_20200930','ibacher','liquibase.xml','2022-05-09 13:53:44',10780,'EXECUTED','8:837653ceb2c37b0f16dd861364ed03c0','createTable tableName=fhir_duration_unit_map; addForeignKeyConstraint baseTableName=fhir_duration_unit_map, constraintName=fhir_duration_unit_map_creator, referencedTableName=users; addForeignKeyConstraint baseTableName=fhir_duration_unit_map, con...','',NULL,'4.4.1',NULL,NULL,NULL),('add_fhir_encounter_class_map_20200930','ibacher','liquibase.xml','2022-05-09 13:53:43',10777,'EXECUTED','8:c07b71cc89518e9c89054145d3d3851c','createTable tableName=fhir_encounter_class_map; addForeignKeyConstraint baseTableName=fhir_encounter_class_map, constraintName=fhir_encounter_class_map_creator, referencedTableName=users; addForeignKeyConstraint baseTableName=fhir_encounter_class_...','',NULL,'4.4.1',NULL,NULL,NULL),('add_fhir_observation_category_map_20200930','ibacher','liquibase.xml','2022-05-09 13:53:43',10778,'EXECUTED','8:8406cad7f81cab2dfa52a98702256f17','createTable tableName=fhir_observation_category_map; addForeignKeyConstraint baseTableName=fhir_observation_category_map, constraintName=fhir_observation_category_map_creator, referencedTableName=users; addForeignKeyConstraint baseTableName=fhir_o...','',NULL,'4.4.1',NULL,NULL,NULL),('add_fhir_patient_identifier_system_20210507','Medhavi','liquibase.xml','2022-05-09 13:53:45',10782,'EXECUTED','8:3e54e358f177bc7d2b968ce42cc55739','createTable tableName=fhir_patient_identifier_system; addForeignKeyConstraint baseTableName=fhir_patient_identifier_system, constraintName=fhir_patient_identifier_system_patient_identifier_type_fk, referencedTableName=patient_identifier_type; addF...','',NULL,'4.4.1',NULL,NULL,NULL),('add_fhir_reference_20200311','pmanko','liquibase.xml','2022-05-09 13:53:39',10769,'EXECUTED','8:4af2620a8401719ce59a4b4ba75b7eb4','createTable tableName=fhir_reference; addForeignKeyConstraint baseTableName=fhir_reference, constraintName=fhir_reference_creator_fk, referencedTableName=users; addForeignKeyConstraint baseTableName=fhir_reference, constraintName=fhir_reference_ch...','',NULL,'4.4.1',NULL,NULL,NULL),('add_fhir_task_20200311','pmanko','liquibase.xml','2022-05-09 13:53:38',10768,'EXECUTED','8:e1ec059d84b15074664e277fe1d2d2dd','createTable tableName=fhir_task; addForeignKeyConstraint baseTableName=fhir_task, constraintName=task_creator, referencedTableName=users; addForeignKeyConstraint baseTableName=fhir_task, constraintName=task_changed_by, referencedTableName=users; a...','Create Task table for the Task FHIR resource',NULL,'4.4.1',NULL,NULL,NULL),('add_fhir_task_input_20200308','pmanko','liquibase.xml','2022-05-09 13:53:41',10772,'EXECUTED','8:347812d29adfbbaf3b757f8e9832738e','createTable tableName=fhir_task_input; addForeignKeyConstraint baseTableName=fhir_task_input, constraintName=fhir_task_input_creator_fk, referencedTableName=users; addForeignKeyConstraint baseTableName=fhir_task_input, constraintName=fhir_task_inp...','',NULL,'4.4.1',NULL,NULL,NULL),('add_fhir_task_output_20200311','pmanko','liquibase.xml','2022-05-09 13:53:40',10771,'EXECUTED','8:591e2822f354ee07598a8f13a8de6307','createTable tableName=fhir_task_output; addForeignKeyConstraint baseTableName=fhir_task_output, constraintName=fhir_task_output_creator_fk, referencedTableName=users; addForeignKeyConstraint baseTableName=fhir_task_output, constraintName=fhir_task...','',NULL,'4.4.1',NULL,NULL,NULL),('add_loinc_fhir_concept_source_20200221','ibacher','liquibase.xml','2022-05-09 13:53:37',10765,'EXECUTED','8:a338427d100eb59b39015c6773b85dba','sql','',NULL,'4.4.1',NULL,NULL,NULL),('add_target_uuid_to_fhir_reference_202020917','ibacher','liquibase.xml','2022-05-09 13:53:41',10773,'EXECUTED','8:34e6228e38f3e8434003dd0e71cbf682','addColumn tableName=fhir_reference; sql','',NULL,'4.4.1',NULL,NULL,NULL),('change_ciel_url_fhir_concept_source_20200820','ibacher','liquibase.xml','2022-05-09 13:53:38',10767,'EXECUTED','8:e21abac1fac251c4d8dfd0f8f732c765','sql','',NULL,'4.4.1',NULL,NULL,NULL),('disable-foreign-key-checks','ben','liquibase-core-data.xml','2021-05-09 07:08:07',10017,'EXECUTED','8:fa07746edc31d6235f8a7c1e63dc7c95','Custom SQL','',NULL,'2.0.5',NULL,NULL,NULL),('drop-tribe-foreign-key-TRUNK-4492','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:08:58',10611,'MARK_RAN','8:c4e617969fb8916681fd50b86a153ec9','Drop Foreign Key Constraint','Dropping foreign key on patient.tribe',NULL,'2.0.5',NULL,NULL,NULL),('enable-foreign-key-checks','ben','liquibase-core-data.xml','2021-05-09 07:08:07',10041,'EXECUTED','8:82a4e40bbc30eaf5423a361c1b56ad02','Custom SQL','',NULL,'2.0.5',NULL,NULL,NULL),('RA-354-create-allergy-table-rev1','fbiedrzycki','liquibase-update-to-latest.xml','2021-05-09 07:09:02',10635,'EXECUTED','8:c3bbe348bf50ae51795514ba336ae6a0','Create Table, Add Foreign Key Constraint (x6)','Create allergy table',NULL,'2.0.5',NULL,NULL,NULL),('RA-355-create-allergy-reaction-table','fbiedrzycki','liquibase-update-to-latest.xml','2021-05-09 07:09:02',10636,'EXECUTED','8:e660411099e3c253756902442c7f0589','Create Table, Add Foreign Key Constraint (x2)','Create allergy_reaction table',NULL,'2.0.5',NULL,NULL,NULL),('RA-360-Add-allergy-status-to-patient-2','rpuzdrowski','liquibase-update-to-latest.xml','2021-05-09 07:09:02',10637,'EXECUTED','8:24ff421cbe357d83006cdc236785deae','Add Column','Add allergy_status field to the patient table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-3422-20160216-1700','Wyclif','liquibase-update-to-latest.xml','2021-05-09 07:09:05',10657,'MARK_RAN','8:46e6a005763154c52b42815a8be21919','Insert Row','Add \"Get Visits\" privilege',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-3422-20160216-1701','Wyclif','liquibase-update-to-latest.xml','2021-05-09 07:09:05',10658,'MARK_RAN','8:fb13a3b10d6183ecdf6a6031330e1553','Insert Row','Add \"Get Providers\" privilege',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-3422-20160216-1702','Wyclif','liquibase-update-to-latest.xml','2021-05-09 07:09:05',10659,'EXECUTED','8:d94bb114f6cb1a6cd93d596509e23ddf','Insert Row','Add \"Add Visits\" privilege',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-3422-20160216-1703','PralayRamteke','liquibase-update-to-latest.xml','2021-05-09 07:09:05',10660,'MARK_RAN','8:ed13450b7bf16eb62dad1717e9226634','Custom SQL','Add \"Get Visits\" privilege to the roles having \"Get Encounter\"',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-3422-20160216-1704','PralayRamteke','liquibase-update-to-latest.xml','2021-05-09 07:09:05',10661,'MARK_RAN','8:944a87096eeefe837d2756ca5521054a','Custom SQL','Add \"Get Providers\" privilege to the roles having \"Get Encounter\"',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-3422-20160216-1705','Wyclif','liquibase-update-to-latest.xml','2021-05-09 07:09:05',10662,'MARK_RAN','8:1f26253e28e048edc6050ff1153c79b4','Custom SQL','Add \"Add Visits\" privilege to the roles having \"Add Encounters\"',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-3422-20160216-1706','Wyclif','liquibase-update-to-latest.xml','2021-05-09 07:09:05',10663,'MARK_RAN','8:d0529400c5e3c985f25f404619b695bc','Custom SQL','Add \"Add Visits\" privilege to the roles having \"Edit Encounters\"',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4505-20180804','fruether','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10722,'EXECUTED','8:07c7f60638902f4d8d8c49c970512d80','Add Column','Adding cause_of_death_non_coded to the table person',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4548-MigrateAllergiesChangeSet1','dkayiwa','liquibase-update-to-latest.xml','2021-05-09 07:09:02',10638,'MARK_RAN','8:2c8deede314598dfd0f83338b03a2102','Custom Change','Custom changeset to migrate allergies from old to new tables',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1000','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10694,'EXECUTED','8:0dc0121eeda9c2f3f251dbceea361ffc','Add Column','Adding \"date_changed\" column to concept_class table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1001','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10695,'EXECUTED','8:eca6440f4ea914d1c31337deff78411f','Add Column, Add Foreign Key Constraint','Adding \"changed_by\" column to concept_class table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1002','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10696,'EXECUTED','8:3a584b6fd05e960a6437771d325fe5c7','Add Column','Adding \"date_changed\" column to concept_reference_source table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1003','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10697,'EXECUTED','8:96bdffec834950246574c2c97d3f2faf','Add Column, Add Foreign Key Constraint','Adding \"changed_by\" column to concept_reference_source table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1004','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10698,'EXECUTED','8:7024c608b8df8c3417272527a783b601','Add Column','Adding \"date_changed\" column to concept_name table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1005','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10699,'EXECUTED','8:3122f9c1679cc8a1a2948a42d88abf81','Add Column, Add Foreign Key Constraint','Adding \"changed_by\" column to concept_name table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1006','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10700,'EXECUTED','8:f8b1698369e70c263c7d256232fa9853','Add Column','Adding \"date_changed\" column to concept_name_tag table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1007','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10701,'EXECUTED','8:557c94271264ae94470f10a79887ba7a','Add Column, Add Foreign Key Constraint','Adding \"changed_by\" column to concept_name_tag table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1008','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10702,'EXECUTED','8:a7181bbfa73fc7837958e522fb88d913','Add Column','Adding \"date_changed\" column to form_resource table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1009','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10703,'EXECUTED','8:363a7f4db98813fd511c551d327555a4','Add Column, Add Foreign Key Constraint','Adding \"changed_by\" column to form_resource table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1010','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10704,'EXECUTED','8:f90dd5ade7da1bf408a0d06c4129d78f','Add Column','Adding \"date_changed\" column to global_property table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1011','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10705,'EXECUTED','8:7f9643696d99fe37e4155df39ef0c12d','Add Column, Add Foreign Key Constraint','Adding \"changed_by\" column to global_property table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1012','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10706,'EXECUTED','8:34bb5636497c57f69cf59c27912115fc','Add Column','Adding \"date_changed\" column to patient_identifier_type table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1013','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10707,'EXECUTED','8:d5d773f7421b9d52597fe84d872d3d90','Add Column, Add Foreign Key Constraint','Adding \"changed_by\" column to patient_identifier_type table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1014','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10708,'EXECUTED','8:74c92214e8d24e34eead91a6465bdece','Add Column','Adding \"date_changed\" column to relationship_type table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4730-20161114-1015','manuelagrindei','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10709,'EXECUTED','8:d4bb129db6652e093ce4f4f85a404926','Add Column, Add Foreign Key Constraint','Adding \"changed_by\" column to relationship_type table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4755','Rahul,Swathi','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10713,'EXECUTED','8:7323cb6bea011952036ba65c8ee6af56','Create Table, Add Foreign Key Constraint (x3)','Creating program_attribute_type table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4791-20180215','patrick','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10720,'EXECUTED','8:c2355838b0dab9af7eba58f6777cb9a5','Add Column, Add Foreign Key Constraint','Adding role_id column to provider table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4791-20180216','patrick','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10721,'EXECUTED','8:38f4f039ed68dca355240837cd365768','Add Column, Add Foreign Key Constraint','Adding speciality_id column to provider table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4936-20160930-1000','teleivo','liquibase-update-to-latest.xml','2021-05-09 07:09:06',10666,'EXECUTED','8:ae7e0e8e7e90d5c1bbed4bd6c8eb9504','Add Column, Add Unique Constraint','Add unique_id column to concept_reference_source',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4976-20170403-1','darius','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10710,'EXECUTED','8:498a038469d6fe8e15046517a5d6724d','Add Column','Adding \"status\" column to obs table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-4976-20170403-2','darius','liquibase-update-to-latest.xml','2021-05-09 07:09:08',10711,'EXECUTED','8:d765d031f1ddb8e475bcd1ea370823dd','Add Column','Adding \"interpretation\" column to obs table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-5140-20170404-1000','Shruthi,Salauddin','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10712,'EXECUTED','8:0e83bac43d511d8e8c1738f886c1b69d','Modify data type','Modify column length to 1000 from 255',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-5334-rename-allergy-comment-to-comments','madhavkauntia','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10727,'EXECUTED','8:f50bf8c0e8303f0d084de461e464e742','Rename Column','Renaming allerygy.comment to allergy.comments',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-5400-20180731-1','fruether','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10723,'EXECUTED','8:0cc9bac4ead83049dc518d2f1d31411c','Add Column','Adding fulfill_status and  fulfiller_comment column to provider table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-5400-201807311-2','fruether','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10724,'EXECUTED','8:8d3de8285a02614ab2706c1ffd5f6303','Add Column','Adding fulfill_status and  fulfiller_comment column to provider table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-5410','tendomart','liquibase-update-to-latest.xml','2022-05-09 13:53:01',10742,'EXECUTED','8:7a314d2f15c425b8fc40545505441ce3','createTable tableName=order_group_attribute_type; addForeignKeyConstraint baseTableName=order_group_attribute_type, constraintName=order_group_attribute_type_creator_fk, referencedTableName=users; addForeignKeyConstraint baseTableName=order_group_...','Creating order_group_attribute_type table',NULL,'4.4.1',NULL,NULL,NULL),('TRUNK-5413','odorajonathan','liquibase-update-to-latest.xml','2021-05-09 07:09:09',10728,'EXECUTED','8:adfdcedd3b6451067f29f86e734076c1','Add Column, Add Foreign Key Constraint','Add a category field to the order_set table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-5663-20191002','gitacliff','liquibase-update-to-latest.xml','2021-05-09 07:09:10',10730,'EXECUTED','8:ee20300a74e8d4c069824f7c3328a294','Add Column','Adding \"date_changed\" column to conditions table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-5672-201910211105','Mritunjay','liquibase-update-to-latest.xml','2021-05-09 07:09:10',10731,'EXECUTED','8:b1be0d7a6267503335e635e79132b5b7','Update Data','Populate username for admin user',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-5728-2020-04-15','samuel34','liquibase-update-to-latest.xml','2021-05-09 07:09:10',10732,'EXECUTED','8:2a90bedc8b02e4c89e1a37dc1110014b','Add Column, Add Foreign Key Constraint','Adding \'encounter_id\' column to \'conditions\' table',NULL,'2.0.5',NULL,NULL,NULL),('TRUNK-5728-2020-05-20','samuel34','liquibase-update-to-latest.xml','2022-05-09 13:53:00',10738,'MARK_RAN','8:2a90bedc8b02e4c89e1a37dc1110014b','addColumn tableName=conditions; addForeignKeyConstraint baseTableName=conditions, constraintName=conditions_encounter_id_fk, referencedTableName=encounter','Adding \'encounter_id\' column to \'conditions\' table',NULL,'4.4.1',NULL,NULL,NULL),('TRUNK-5835-2020-07-09-1600','gitacliff','liquibase-update-to-latest.xml','2022-05-09 13:53:00',10740,'EXECUTED','8:b744e5bfec82a26f3ed1e8a71fc8d5e5','createTable tableName=order_set_attribute_type; addForeignKeyConstraint baseTableName=order_set_attribute_type, constraintName=order_set_attribute_type_creator_fk, referencedTableName=users; addForeignKeyConstraint baseTableName=order_set_attribut...','Creating order_set_attribute_type table',NULL,'4.4.1',NULL,NULL,NULL),('TRUNK-5835-2020-07-09-1700','gitacliff','liquibase-update-to-latest.xml','2022-05-09 13:53:00',10741,'EXECUTED','8:8877d8978506c3107937fb42e6c6634b','createTable tableName=order_set_attribute; addForeignKeyConstraint baseTableName=order_set_attribute, constraintName=order_set_attribute_order_set_fk, referencedTableName=order_set; addForeignKeyConstraint baseTableName=order_set_attribute, constr...','Creating order_set_attribute table',NULL,'4.4.1',NULL,NULL,NULL),('TRUNK-6001','rasztabigab','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:02',10746,'EXECUTED','8:00bd333022b8d08c676749c2e68f3190','dropNotNullConstraint columnName=start_date, tableName=cohort_member','Delete non-null constraint from column cohort_member.start_date',NULL,'4.4.1',NULL,NULL,NULL),('TRUNK-6035','dkayiwa','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:04',10758,'EXECUTED','8:a83b3c51647a7b4cd149c317b0305a1c','addColumn tableName=test_order; addForeignKeyConstraint baseTableName=test_order, constraintName=test_order_location_fk, referencedTableName=concept','Adding location column to the test_order table',NULL,'4.4.1',NULL,NULL,NULL),('TRUNK-6036','dkayiwa','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:03',10757,'EXECUTED','8:a03e4dfd1b62637612a7de0eb6b8f16c','createTable tableName=referral_order; addForeignKeyConstraint baseTableName=referral_order, constraintName=referral_order_order_id_fk, referencedTableName=orders; addForeignKeyConstraint baseTableName=referral_order, constraintName=referral_order_...','Create referral_order table',NULL,'4.4.1',NULL,NULL,NULL),('TRUNK-6045','pmanko','org/openmrs/liquibase/updates/liquibase-update-to-latest-2.5.x.xml','2022-05-09 13:53:04',10759,'EXECUTED','8:8d3cf8206c717b64458982d991f40555','addColumn tableName=location; addForeignKeyConstraint baseTableName=location, constraintName=location_type_fk, referencedTableName=concept','Adding type field to the Location table',NULL,'4.4.1',NULL,NULL,NULL);

--
-- Table structure for table `liquibasechangeloglock`
--

DROP TABLE IF EXISTS `liquibasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `liquibasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` tinyint(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `liquibasechangeloglock`
--

INSERT INTO `liquibasechangeloglock` VALUES (1,0,NULL,NULL);

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `location_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `city_village` varchar(255) DEFAULT NULL,
  `state_province` varchar(255) DEFAULT NULL,
  `postal_code` varchar(50) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `latitude` varchar(50) DEFAULT NULL,
  `longitude` varchar(50) DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `county_district` varchar(255) DEFAULT NULL,
  `address3` varchar(255) DEFAULT NULL,
  `address4` varchar(255) DEFAULT NULL,
  `address5` varchar(255) DEFAULT NULL,
  `address6` varchar(255) DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `parent_location` int(11) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `address7` varchar(255) DEFAULT NULL,
  `address8` varchar(255) DEFAULT NULL,
  `address9` varchar(255) DEFAULT NULL,
  `address10` varchar(255) DEFAULT NULL,
  `address11` varchar(255) DEFAULT NULL,
  `address12` varchar(255) DEFAULT NULL,
  `address13` varchar(255) DEFAULT NULL,
  `address14` varchar(255) DEFAULT NULL,
  `address15` varchar(255) DEFAULT NULL,
  `location_type_concept_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`location_id`),
  UNIQUE KEY `location_uuid_index` (`uuid`),
  KEY `name_of_location` (`name`),
  KEY `location_retired_status` (`retired`),
  KEY `user_who_created_location` (`creator`),
  KEY `user_who_retired_location` (`retired_by`),
  KEY `parent_location` (`parent_location`),
  KEY `location_changed_by` (`changed_by`),
  KEY `location_type_fk` (`location_type_concept_id`),
  CONSTRAINT `location_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `location_type_fk` FOREIGN KEY (`location_type_concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `parent_location` FOREIGN KEY (`parent_location`) REFERENCES `location` (`location_id`),
  CONSTRAINT `user_who_created_location` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_retired_location` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

INSERT INTO `location` VALUES (1,'Unknown Location',NULL,'','','','','','',NULL,NULL,1,'2005-09-22 00:00:00',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,'8d6c993e-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

--
-- Table structure for table `location_attribute`
--

DROP TABLE IF EXISTS `location_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location_attribute` (
  `location_attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `location_id` int(11) NOT NULL,
  `attribute_type_id` int(11) NOT NULL,
  `value_reference` text NOT NULL,
  `uuid` char(38) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`location_attribute_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `location_attribute_location_fk` (`location_id`),
  KEY `location_attribute_attribute_type_id_fk` (`attribute_type_id`),
  KEY `location_attribute_creator_fk` (`creator`),
  KEY `location_attribute_changed_by_fk` (`changed_by`),
  KEY `location_attribute_voided_by_fk` (`voided_by`),
  CONSTRAINT `location_attribute_attribute_type_id_fk` FOREIGN KEY (`attribute_type_id`) REFERENCES `location_attribute_type` (`location_attribute_type_id`),
  CONSTRAINT `location_attribute_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `location_attribute_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `location_attribute_location_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`),
  CONSTRAINT `location_attribute_voided_by_fk` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_attribute`
--


--
-- Table structure for table `location_attribute_type`
--

DROP TABLE IF EXISTS `location_attribute_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location_attribute_type` (
  `location_attribute_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `datatype` varchar(255) DEFAULT NULL,
  `datatype_config` text,
  `preferred_handler` varchar(255) DEFAULT NULL,
  `handler_config` text,
  `min_occurs` int(11) NOT NULL,
  `max_occurs` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`location_attribute_type_id`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `location_attribute_type_unique_name` (`name`),
  KEY `location_attribute_type_creator_fk` (`creator`),
  KEY `location_attribute_type_changed_by_fk` (`changed_by`),
  KEY `location_attribute_type_retired_by_fk` (`retired_by`),
  CONSTRAINT `location_attribute_type_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `location_attribute_type_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `location_attribute_type_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_attribute_type`
--


--
-- Table structure for table `location_tag`
--

DROP TABLE IF EXISTS `location_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location_tag` (
  `location_tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  PRIMARY KEY (`location_tag_id`),
  UNIQUE KEY `location_tag_uuid_index` (`uuid`),
  KEY `location_tag_creator` (`creator`),
  KEY `location_tag_retired_by` (`retired_by`),
  KEY `location_tag_changed_by` (`changed_by`),
  CONSTRAINT `location_tag_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `location_tag_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `location_tag_retired_by` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_tag`
--


--
-- Table structure for table `location_tag_map`
--

DROP TABLE IF EXISTS `location_tag_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location_tag_map` (
  `location_id` int(11) NOT NULL,
  `location_tag_id` int(11) NOT NULL,
  PRIMARY KEY (`location_id`,`location_tag_id`),
  KEY `location_tag_map_tag` (`location_tag_id`),
  CONSTRAINT `location_tag_map_location` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`),
  CONSTRAINT `location_tag_map_tag` FOREIGN KEY (`location_tag_id`) REFERENCES `location_tag` (`location_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_tag_map`
--


--
-- Table structure for table `note`
--

DROP TABLE IF EXISTS `note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `note` (
  `note_id` int(11) NOT NULL DEFAULT '0',
  `note_type` varchar(50) DEFAULT NULL,
  `patient_id` int(11) DEFAULT NULL,
  `obs_id` int(11) DEFAULT NULL,
  `encounter_id` int(11) DEFAULT NULL,
  `text` text NOT NULL,
  `priority` int(11) DEFAULT NULL,
  `parent` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`note_id`),
  UNIQUE KEY `note_uuid_index` (`uuid`),
  KEY `user_who_changed_note` (`changed_by`),
  KEY `user_who_created_note` (`creator`),
  KEY `encounter_note` (`encounter_id`),
  KEY `obs_note` (`obs_id`),
  KEY `note_hierarchy` (`parent`),
  KEY `patient_note` (`patient_id`),
  CONSTRAINT `encounter_note` FOREIGN KEY (`encounter_id`) REFERENCES `encounter` (`encounter_id`),
  CONSTRAINT `note_hierarchy` FOREIGN KEY (`parent`) REFERENCES `note` (`note_id`),
  CONSTRAINT `obs_note` FOREIGN KEY (`obs_id`) REFERENCES `obs` (`obs_id`),
  CONSTRAINT `patient_note` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON UPDATE CASCADE,
  CONSTRAINT `user_who_changed_note` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_created_note` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note`
--


--
-- Table structure for table `notification_alert`
--

DROP TABLE IF EXISTS `notification_alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification_alert` (
  `alert_id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(512) NOT NULL,
  `satisfied_by_any` tinyint(1) NOT NULL DEFAULT '0',
  `alert_read` tinyint(1) NOT NULL DEFAULT '0',
  `date_to_expire` datetime DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`alert_id`),
  UNIQUE KEY `notification_alert_uuid_index` (`uuid`),
  KEY `alert_date_to_expire_idx` (`date_to_expire`),
  KEY `user_who_changed_alert` (`changed_by`),
  KEY `alert_creator` (`creator`),
  CONSTRAINT `alert_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_changed_alert` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_alert`
--


--
-- Table structure for table `notification_alert_recipient`
--

DROP TABLE IF EXISTS `notification_alert_recipient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification_alert_recipient` (
  `alert_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `alert_read` tinyint(1) NOT NULL DEFAULT '0',
  `date_changed` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`alert_id`,`user_id`),
  KEY `alert_read_by_user` (`user_id`),
  CONSTRAINT `alert_read_by_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `id_of_alert` FOREIGN KEY (`alert_id`) REFERENCES `notification_alert` (`alert_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_alert_recipient`
--


--
-- Table structure for table `notification_template`
--

DROP TABLE IF EXISTS `notification_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification_template` (
  `template_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `template` text,
  `subject` varchar(100) DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `recipients` varchar(512) DEFAULT NULL,
  `ordinal` int(11) DEFAULT '0',
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`template_id`),
  UNIQUE KEY `notification_template_uuid_index` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_template`
--


--
-- Table structure for table `obs`
--

DROP TABLE IF EXISTS `obs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `obs` (
  `obs_id` int(11) NOT NULL AUTO_INCREMENT,
  `person_id` int(11) NOT NULL,
  `concept_id` int(11) NOT NULL DEFAULT '0',
  `encounter_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `obs_datetime` datetime NOT NULL,
  `location_id` int(11) DEFAULT NULL,
  `obs_group_id` int(11) DEFAULT NULL,
  `accession_number` varchar(255) DEFAULT NULL,
  `value_group_id` int(11) DEFAULT NULL,
  `value_coded` int(11) DEFAULT NULL,
  `value_coded_name_id` int(11) DEFAULT NULL,
  `value_drug` int(11) DEFAULT NULL,
  `value_datetime` datetime DEFAULT NULL,
  `value_numeric` double DEFAULT NULL,
  `value_modifier` varchar(2) DEFAULT NULL,
  `value_text` text,
  `value_complex` varchar(1000) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `previous_version` int(11) DEFAULT NULL,
  `form_namespace_and_path` varchar(255) DEFAULT NULL,
  `status` varchar(16) NOT NULL DEFAULT 'FINAL',
  `interpretation` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`obs_id`),
  UNIQUE KEY `obs_uuid_index` (`uuid`),
  KEY `obs_datetime_idx` (`obs_datetime`),
  KEY `obs_concept` (`concept_id`),
  KEY `obs_enterer` (`creator`),
  KEY `encounter_observations` (`encounter_id`),
  KEY `obs_location` (`location_id`),
  KEY `obs_grouping_id` (`obs_group_id`),
  KEY `obs_order` (`order_id`),
  KEY `person_obs` (`person_id`),
  KEY `answer_concept` (`value_coded`),
  KEY `obs_name_of_coded_value` (`value_coded_name_id`),
  KEY `answer_concept_drug` (`value_drug`),
  KEY `user_who_voided_obs` (`voided_by`),
  KEY `previous_version` (`previous_version`),
  CONSTRAINT `answer_concept` FOREIGN KEY (`value_coded`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `answer_concept_drug` FOREIGN KEY (`value_drug`) REFERENCES `drug` (`drug_id`),
  CONSTRAINT `encounter_observations` FOREIGN KEY (`encounter_id`) REFERENCES `encounter` (`encounter_id`),
  CONSTRAINT `obs_concept` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `obs_enterer` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `obs_grouping_id` FOREIGN KEY (`obs_group_id`) REFERENCES `obs` (`obs_id`),
  CONSTRAINT `obs_location` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`),
  CONSTRAINT `obs_name_of_coded_value` FOREIGN KEY (`value_coded_name_id`) REFERENCES `concept_name` (`concept_name_id`),
  CONSTRAINT `obs_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `person_obs` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`) ON UPDATE CASCADE,
  CONSTRAINT `previous_version` FOREIGN KEY (`previous_version`) REFERENCES `obs` (`obs_id`),
  CONSTRAINT `user_who_voided_obs` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `obs`
--


--
-- Table structure for table `order_attribute`
--

DROP TABLE IF EXISTS `order_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_attribute` (
  `order_attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `attribute_type_id` int(11) NOT NULL,
  `value_reference` text NOT NULL,
  `uuid` char(38) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_attribute_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `order_attribute_order_fk` (`order_id`),
  KEY `order_attribute_attribute_type_id_fk` (`attribute_type_id`),
  KEY `order_attribute_creator_fk` (`creator`),
  KEY `order_attribute_changed_by_fk` (`changed_by`),
  KEY `order_attribute_voided_by_fk` (`voided_by`),
  CONSTRAINT `order_attribute_attribute_type_id_fk` FOREIGN KEY (`attribute_type_id`) REFERENCES `order_attribute_type` (`order_attribute_type_id`),
  CONSTRAINT `order_attribute_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_attribute_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_attribute_order_fk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `order_attribute_voided_by_fk` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_attribute`
--


--
-- Table structure for table `order_attribute_type`
--

DROP TABLE IF EXISTS `order_attribute_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_attribute_type` (
  `order_attribute_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `datatype` varchar(255) DEFAULT NULL,
  `datatype_config` text,
  `preferred_handler` varchar(255) DEFAULT NULL,
  `handler_config` text,
  `min_occurs` int(11) NOT NULL,
  `max_occurs` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`order_attribute_type_id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `order_attribute_type_creator_fk` (`creator`),
  KEY `order_attribute_type_changed_by_fk` (`changed_by`),
  KEY `order_attribute_type_retired_by_fk` (`retired_by`),
  CONSTRAINT `order_attribute_type_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_attribute_type_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_attribute_type_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_attribute_type`
--


--
-- Table structure for table `order_frequency`
--

DROP TABLE IF EXISTS `order_frequency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_frequency` (
  `order_frequency_id` int(11) NOT NULL AUTO_INCREMENT,
  `concept_id` int(11) NOT NULL,
  `frequency_per_day` double DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`order_frequency_id`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `concept_id` (`concept_id`),
  KEY `order_frequency_creator_fk` (`creator`),
  KEY `order_frequency_retired_by_fk` (`retired_by`),
  KEY `order_frequency_changed_by_fk` (`changed_by`),
  CONSTRAINT `order_frequency_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_frequency_concept_id_fk` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `order_frequency_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_frequency_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_frequency`
--


--
-- Table structure for table `order_group`
--

DROP TABLE IF EXISTS `order_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_group` (
  `order_group_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_set_id` int(11) DEFAULT NULL,
  `patient_id` int(11) NOT NULL,
  `encounter_id` int(11) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `order_group_reason` int(11) DEFAULT NULL,
  `parent_order_group` int(11) DEFAULT NULL,
  `previous_order_group` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_group_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `order_group_patient_id_fk` (`patient_id`),
  KEY `order_group_encounter_id_fk` (`encounter_id`),
  KEY `order_group_creator_fk` (`creator`),
  KEY `order_group_set_id_fk` (`order_set_id`),
  KEY `order_group_voided_by_fk` (`voided_by`),
  KEY `order_group_changed_by_fk` (`changed_by`),
  KEY `order_group_order_group_reason_fk` (`order_group_reason`),
  KEY `order_group_parent_order_group_fk` (`parent_order_group`),
  KEY `order_group_previous_order_group_fk` (`previous_order_group`),
  CONSTRAINT `order_group_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_group_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_group_encounter_id_fk` FOREIGN KEY (`encounter_id`) REFERENCES `encounter` (`encounter_id`),
  CONSTRAINT `order_group_order_group_reason_fk` FOREIGN KEY (`order_group_reason`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `order_group_parent_order_group_fk` FOREIGN KEY (`parent_order_group`) REFERENCES `order_group` (`order_group_id`),
  CONSTRAINT `order_group_patient_id_fk` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`),
  CONSTRAINT `order_group_previous_order_group_fk` FOREIGN KEY (`previous_order_group`) REFERENCES `order_group` (`order_group_id`),
  CONSTRAINT `order_group_set_id_fk` FOREIGN KEY (`order_set_id`) REFERENCES `order_set` (`order_set_id`),
  CONSTRAINT `order_group_voided_by_fk` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_group`
--


--
-- Table structure for table `order_group_attribute`
--

DROP TABLE IF EXISTS `order_group_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_group_attribute` (
  `order_group_attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_group_id` int(11) NOT NULL,
  `attribute_type_id` int(11) NOT NULL,
  `value_reference` text NOT NULL,
  `uuid` char(38) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_group_attribute_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `order_group_attribute_order_group_fk` (`order_group_id`),
  KEY `order_group_attribute_attribute_type_id_fk` (`attribute_type_id`),
  KEY `order_group_attribute_creator_fk` (`creator`),
  KEY `order_group_attribute_changed_by_fk` (`changed_by`),
  KEY `order_group_attribute_voided_by_fk` (`voided_by`),
  CONSTRAINT `order_group_attribute_attribute_type_id_fk` FOREIGN KEY (`attribute_type_id`) REFERENCES `order_group_attribute_type` (`order_group_attribute_type_id`),
  CONSTRAINT `order_group_attribute_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_group_attribute_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_group_attribute_order_group_fk` FOREIGN KEY (`order_group_id`) REFERENCES `order_group` (`order_group_id`),
  CONSTRAINT `order_group_attribute_voided_by_fk` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_group_attribute`
--


--
-- Table structure for table `order_group_attribute_type`
--

DROP TABLE IF EXISTS `order_group_attribute_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_group_attribute_type` (
  `order_group_attribute_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `datatype` varchar(255) DEFAULT NULL,
  `datatype_config` text,
  `preferred_handler` varchar(255) DEFAULT NULL,
  `handler_config` text,
  `min_occurs` int(11) NOT NULL,
  `max_occurs` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`order_group_attribute_type_id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `order_group_attribute_type_creator_fk` (`creator`),
  KEY `order_group_attribute_type_changed_by_fk` (`changed_by`),
  KEY `order_group_attribute_type_retired_by_fk` (`retired_by`),
  CONSTRAINT `order_group_attribute_type_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_group_attribute_type_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_group_attribute_type_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_group_attribute_type`
--


--
-- Table structure for table `order_set`
--

DROP TABLE IF EXISTS `order_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_set` (
  `order_set_id` int(11) NOT NULL AUTO_INCREMENT,
  `operator` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `category` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_set_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `order_set_creator_fk` (`creator`),
  KEY `order_set_retired_by_fk` (`retired_by`),
  KEY `order_set_changed_by_fk` (`changed_by`),
  KEY `category_order_set_fk` (`category`),
  CONSTRAINT `category_order_set_fk` FOREIGN KEY (`category`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `order_set_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_set_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_set_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_set`
--


--
-- Table structure for table `order_set_attribute`
--

DROP TABLE IF EXISTS `order_set_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_set_attribute` (
  `order_set_attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_set_id` int(11) NOT NULL,
  `attribute_type_id` int(11) NOT NULL,
  `value_reference` text NOT NULL,
  `uuid` char(38) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_set_attribute_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `order_set_attribute_order_set_fk` (`order_set_id`),
  KEY `order_set_attribute_attribute_type_id_fk` (`attribute_type_id`),
  KEY `order_set_attribute_creator_fk` (`creator`),
  KEY `order_set_attribute_changed_by_fk` (`changed_by`),
  KEY `order_set_attribute_voided_by_fk` (`voided_by`),
  CONSTRAINT `order_set_attribute_attribute_type_id_fk` FOREIGN KEY (`attribute_type_id`) REFERENCES `order_set_attribute_type` (`order_set_attribute_type_id`),
  CONSTRAINT `order_set_attribute_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_set_attribute_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_set_attribute_order_set_fk` FOREIGN KEY (`order_set_id`) REFERENCES `order_set` (`order_set_id`),
  CONSTRAINT `order_set_attribute_voided_by_fk` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_set_attribute`
--


--
-- Table structure for table `order_set_attribute_type`
--

DROP TABLE IF EXISTS `order_set_attribute_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_set_attribute_type` (
  `order_set_attribute_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `datatype` varchar(255) DEFAULT NULL,
  `datatype_config` text,
  `preferred_handler` varchar(255) DEFAULT NULL,
  `handler_config` text,
  `min_occurs` int(11) NOT NULL,
  `max_occurs` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`order_set_attribute_type_id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `order_set_attribute_type_creator_fk` (`creator`),
  KEY `order_set_attribute_type_changed_by_fk` (`changed_by`),
  KEY `order_set_attribute_type_retired_by_fk` (`retired_by`),
  CONSTRAINT `order_set_attribute_type_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_set_attribute_type_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_set_attribute_type_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_set_attribute_type`
--


--
-- Table structure for table `order_set_member`
--

DROP TABLE IF EXISTS `order_set_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_set_member` (
  `order_set_member_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_type` int(11) NOT NULL,
  `order_template` text,
  `order_template_type` varchar(1024) DEFAULT NULL,
  `order_set_id` int(11) NOT NULL,
  `sequence_number` int(11) NOT NULL,
  `concept_id` int(11) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`order_set_member_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `order_set_member_creator_fk` (`creator`),
  KEY `order_set_member_order_set_id_fk` (`order_set_id`),
  KEY `order_set_member_concept_id_fk` (`concept_id`),
  KEY `order_set_member_order_type_fk` (`order_type`),
  KEY `order_set_member_retired_by_fk` (`retired_by`),
  KEY `order_set_member_changed_by_fk` (`changed_by`),
  CONSTRAINT `order_set_member_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_set_member_concept_id_fk` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `order_set_member_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_set_member_order_set_id_fk` FOREIGN KEY (`order_set_id`) REFERENCES `order_set` (`order_set_id`),
  CONSTRAINT `order_set_member_order_type_fk` FOREIGN KEY (`order_type`) REFERENCES `order_type` (`order_type_id`),
  CONSTRAINT `order_set_member_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_set_member`
--


--
-- Table structure for table `order_type`
--

DROP TABLE IF EXISTS `order_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_type` (
  `order_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` text,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `java_class_name` varchar(255) NOT NULL,
  `parent` int(11) DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  PRIMARY KEY (`order_type_id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `order_type_uuid_index` (`uuid`),
  KEY `order_type_retired_status` (`retired`),
  KEY `type_created_by` (`creator`),
  KEY `user_who_retired_order_type` (`retired_by`),
  KEY `order_type_changed_by` (`changed_by`),
  KEY `order_type_parent_order_type` (`parent`),
  CONSTRAINT `order_type_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_type_parent_order_type` FOREIGN KEY (`parent`) REFERENCES `order_type` (`order_type_id`),
  CONSTRAINT `type_created_by` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_retired_order_type` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_type`
--

INSERT INTO `order_type` VALUES (2,'Drug Order','An order for a medication to be given to the patient',1,'2010-05-12 00:00:00',0,NULL,NULL,NULL,'131168f4-15f5-102d-96e4-000c29c2a5d7','org.openmrs.DrugOrder',NULL,NULL,NULL),(3,'Test Order','Order type for test orders',1,'2014-03-09 00:00:00',0,NULL,NULL,NULL,'52a447d3-a64a-11e3-9aeb-50e549534c5e','org.openmrs.TestOrder',NULL,NULL,NULL);

--
-- Table structure for table `order_type_class_map`
--

DROP TABLE IF EXISTS `order_type_class_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_type_class_map` (
  `order_type_id` int(11) NOT NULL,
  `concept_class_id` int(11) NOT NULL,
  PRIMARY KEY (`order_type_id`,`concept_class_id`),
  UNIQUE KEY `concept_class_id` (`concept_class_id`),
  CONSTRAINT `fk_order_type_class_map_concept_class_concept_class_id` FOREIGN KEY (`concept_class_id`) REFERENCES `concept_class` (`concept_class_id`),
  CONSTRAINT `fk_order_type_order_type_id` FOREIGN KEY (`order_type_id`) REFERENCES `order_type` (`order_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_type_class_map`
--


--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_type_id` int(11) NOT NULL DEFAULT '0',
  `concept_id` int(11) NOT NULL DEFAULT '0',
  `orderer` int(11) NOT NULL,
  `encounter_id` int(11) NOT NULL,
  `instructions` text,
  `date_activated` datetime DEFAULT NULL,
  `auto_expire_date` datetime DEFAULT NULL,
  `date_stopped` datetime DEFAULT NULL,
  `order_reason` int(11) DEFAULT NULL,
  `order_reason_non_coded` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `patient_id` int(11) NOT NULL,
  `accession_number` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `urgency` varchar(50) NOT NULL DEFAULT 'ROUTINE',
  `order_number` varchar(50) NOT NULL,
  `previous_order_id` int(11) DEFAULT NULL,
  `order_action` varchar(50) NOT NULL,
  `comment_to_fulfiller` varchar(1024) DEFAULT NULL,
  `care_setting` int(11) NOT NULL,
  `scheduled_date` datetime DEFAULT NULL,
  `order_group_id` int(11) DEFAULT NULL,
  `sort_weight` double DEFAULT NULL,
  `fulfiller_comment` varchar(1024) DEFAULT NULL,
  `fulfiller_status` varchar(50) DEFAULT NULL,
  `form_namespace_and_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `orders_uuid_index` (`uuid`),
  KEY `order_creator` (`creator`),
  KEY `orders_in_encounter` (`encounter_id`),
  KEY `type_of_order` (`order_type_id`),
  KEY `order_for_patient` (`patient_id`),
  KEY `user_who_voided_order` (`voided_by`),
  KEY `previous_order_id_order_id` (`previous_order_id`),
  KEY `orders_care_setting` (`care_setting`),
  KEY `discontinued_because` (`order_reason`),
  KEY `fk_orderer_provider` (`orderer`),
  KEY `orders_order_group_id_fk` (`order_group_id`),
  KEY `orders_order_number` (`order_number`),
  KEY `orders_accession_number` (`accession_number`),
  CONSTRAINT `discontinued_because` FOREIGN KEY (`order_reason`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `fk_orderer_provider` FOREIGN KEY (`orderer`) REFERENCES `provider` (`provider_id`),
  CONSTRAINT `order_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `order_for_patient` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON UPDATE CASCADE,
  CONSTRAINT `orders_care_setting` FOREIGN KEY (`care_setting`) REFERENCES `care_setting` (`care_setting_id`),
  CONSTRAINT `orders_in_encounter` FOREIGN KEY (`encounter_id`) REFERENCES `encounter` (`encounter_id`),
  CONSTRAINT `orders_order_group_id_fk` FOREIGN KEY (`order_group_id`) REFERENCES `order_group` (`order_group_id`),
  CONSTRAINT `previous_order_id_order_id` FOREIGN KEY (`previous_order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `type_of_order` FOREIGN KEY (`order_type_id`) REFERENCES `order_type` (`order_type_id`),
  CONSTRAINT `user_who_voided_order` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--


--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `patient_id` int(11) NOT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `allergy_status` varchar(50) NOT NULL DEFAULT 'Unknown',
  PRIMARY KEY (`patient_id`),
  KEY `user_who_changed_pat` (`changed_by`),
  KEY `user_who_created_patient` (`creator`),
  KEY `user_who_voided_patient` (`voided_by`),
  CONSTRAINT `person_id_for_patient` FOREIGN KEY (`patient_id`) REFERENCES `person` (`person_id`) ON UPDATE CASCADE,
  CONSTRAINT `user_who_changed_pat` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_created_patient` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_voided_patient` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--


--
-- Table structure for table `patient_identifier`
--

DROP TABLE IF EXISTS `patient_identifier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_identifier` (
  `patient_identifier_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) NOT NULL DEFAULT '0',
  `identifier` varchar(50) NOT NULL DEFAULT '',
  `identifier_type` int(11) NOT NULL DEFAULT '0',
  `preferred` tinyint(1) NOT NULL DEFAULT '0',
  `location_id` int(11) DEFAULT '0',
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `date_changed` datetime DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`patient_identifier_id`),
  UNIQUE KEY `patient_identifier_uuid_index` (`uuid`),
  KEY `identifier_name` (`identifier`),
  KEY `idx_patient_identifier_patient` (`patient_id`),
  KEY `identifier_creator` (`creator`),
  KEY `defines_identifier_type` (`identifier_type`),
  KEY `patient_identifier_ibfk_2` (`location_id`),
  KEY `identifier_voider` (`voided_by`),
  KEY `patient_identifier_changed_by` (`changed_by`),
  CONSTRAINT `defines_identifier_type` FOREIGN KEY (`identifier_type`) REFERENCES `patient_identifier_type` (`patient_identifier_type_id`),
  CONSTRAINT `fk_patient_id_patient_identifier` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`),
  CONSTRAINT `identifier_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `identifier_voider` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `patient_identifier_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `patient_identifier_ibfk_2` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_identifier`
--


--
-- Table structure for table `patient_identifier_type`
--

DROP TABLE IF EXISTS `patient_identifier_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_identifier_type` (
  `patient_identifier_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  `description` text,
  `format` varchar(255) DEFAULT NULL,
  `check_digit` tinyint(1) NOT NULL DEFAULT '0',
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `required` tinyint(1) NOT NULL DEFAULT '0',
  `format_description` varchar(255) DEFAULT NULL,
  `validator` varchar(200) DEFAULT NULL,
  `location_behavior` varchar(50) DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `uniqueness_behavior` varchar(50) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`patient_identifier_type_id`),
  UNIQUE KEY `patient_identifier_type_uuid_index` (`uuid`),
  KEY `patient_identifier_type_retired_status` (`retired`),
  KEY `type_creator` (`creator`),
  KEY `user_who_retired_patient_identifier_type` (`retired_by`),
  KEY `patient_identifier_type_changed_by` (`changed_by`),
  CONSTRAINT `patient_identifier_type_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `type_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_retired_patient_identifier_type` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_identifier_type`
--

INSERT INTO `patient_identifier_type` VALUES (1,'OpenMRS Identification Number','Unique number used in OpenMRS','',1,1,'2005-09-22 00:00:00',0,NULL,'org.openmrs.patient.impl.LuhnIdentifierValidator',NULL,0,NULL,NULL,NULL,'8d793bee-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL,NULL),(2,'Old Identification Number','Number given out prior to the OpenMRS system (No check digit)','',0,1,'2005-09-22 00:00:00',0,NULL,NULL,NULL,0,NULL,NULL,NULL,'8d79403a-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL,NULL);

--
-- Table structure for table `patient_program`
--

DROP TABLE IF EXISTS `patient_program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_program` (
  `patient_program_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) NOT NULL DEFAULT '0',
  `program_id` int(11) NOT NULL DEFAULT '0',
  `date_enrolled` datetime DEFAULT NULL,
  `date_completed` datetime DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  `outcome_concept_id` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`patient_program_id`),
  UNIQUE KEY `patient_program_uuid_index` (`uuid`),
  KEY `user_who_changed` (`changed_by`),
  KEY `patient_program_creator` (`creator`),
  KEY `patient_in_program` (`patient_id`),
  KEY `program_for_patient` (`program_id`),
  KEY `user_who_voided_patient_program` (`voided_by`),
  KEY `patient_program_location_id` (`location_id`),
  KEY `patient_program_outcome_concept_id_fk` (`outcome_concept_id`),
  CONSTRAINT `patient_in_program` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON UPDATE CASCADE,
  CONSTRAINT `patient_program_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `patient_program_location_id` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`),
  CONSTRAINT `patient_program_outcome_concept_id_fk` FOREIGN KEY (`outcome_concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `program_for_patient` FOREIGN KEY (`program_id`) REFERENCES `program` (`program_id`),
  CONSTRAINT `user_who_changed` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_voided_patient_program` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_program`
--


--
-- Table structure for table `patient_program_attribute`
--

DROP TABLE IF EXISTS `patient_program_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_program_attribute` (
  `patient_program_attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_program_id` int(11) NOT NULL,
  `attribute_type_id` int(11) NOT NULL,
  `value_reference` text NOT NULL,
  `uuid` char(38) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`patient_program_attribute_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `patient_program_attribute_programid_fk` (`patient_program_id`),
  KEY `patient_program_attribute_attributetype_fk` (`attribute_type_id`),
  KEY `patient_program_attribute_creator_fk` (`creator`),
  KEY `patient_program_attribute_changed_by_fk` (`changed_by`),
  KEY `patient_program_attribute_voided_by_fk` (`voided_by`),
  CONSTRAINT `patient_program_attribute_attributetype_fk` FOREIGN KEY (`attribute_type_id`) REFERENCES `program_attribute_type` (`program_attribute_type_id`),
  CONSTRAINT `patient_program_attribute_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `patient_program_attribute_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `patient_program_attribute_programid_fk` FOREIGN KEY (`patient_program_id`) REFERENCES `patient_program` (`patient_program_id`),
  CONSTRAINT `patient_program_attribute_voided_by_fk` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_program_attribute`
--


--
-- Table structure for table `patient_state`
--

DROP TABLE IF EXISTS `patient_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_state` (
  `patient_state_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_program_id` int(11) NOT NULL DEFAULT '0',
  `state` int(11) NOT NULL DEFAULT '0',
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `form_namespace_and_path` varchar(255) DEFAULT NULL,
  `encounter_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`patient_state_id`),
  UNIQUE KEY `patient_state_uuid_index` (`uuid`),
  KEY `patient_state_changer` (`changed_by`),
  KEY `patient_state_creator` (`creator`),
  KEY `patient_program_for_state` (`patient_program_id`),
  KEY `state_for_patient` (`state`),
  KEY `patient_state_voider` (`voided_by`),
  KEY `patient_state_encounter_id_fk` (`encounter_id`),
  CONSTRAINT `patient_program_for_state` FOREIGN KEY (`patient_program_id`) REFERENCES `patient_program` (`patient_program_id`),
  CONSTRAINT `patient_state_changer` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `patient_state_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `patient_state_encounter_id_fk` FOREIGN KEY (`encounter_id`) REFERENCES `encounter` (`encounter_id`),
  CONSTRAINT `patient_state_voider` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `state_for_patient` FOREIGN KEY (`state`) REFERENCES `program_workflow_state` (`program_workflow_state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_state`
--


--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `person_id` int(11) NOT NULL AUTO_INCREMENT,
  `gender` varchar(50) DEFAULT '',
  `birthdate` date DEFAULT NULL,
  `birthdate_estimated` tinyint(1) NOT NULL DEFAULT '0',
  `dead` tinyint(1) NOT NULL DEFAULT '0',
  `death_date` datetime DEFAULT NULL,
  `cause_of_death` int(11) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `deathdate_estimated` tinyint(1) NOT NULL DEFAULT '0',
  `birthtime` time DEFAULT NULL,
  `cause_of_death_non_coded` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `person_uuid_index` (`uuid`),
  KEY `person_birthdate` (`birthdate`),
  KEY `person_death_date` (`death_date`),
  KEY `person_died_because` (`cause_of_death`),
  KEY `user_who_changed_person` (`changed_by`),
  KEY `user_who_created_person` (`creator`),
  KEY `user_who_voided_person` (`voided_by`),
  CONSTRAINT `person_died_because` FOREIGN KEY (`cause_of_death`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `user_who_changed_person` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_created_person` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_voided_person` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

INSERT INTO `person` VALUES (1,'M',NULL,0,0,NULL,NULL,NULL,'2005-01-01 00:00:00',NULL,NULL,0,NULL,NULL,NULL,'50045790-b095-11eb-9be5-0242c0a82002',0,NULL,NULL);

--
-- Table structure for table `person_address`
--

DROP TABLE IF EXISTS `person_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person_address` (
  `person_address_id` int(11) NOT NULL AUTO_INCREMENT,
  `person_id` int(11) DEFAULT NULL,
  `preferred` tinyint(1) NOT NULL DEFAULT '0',
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `city_village` varchar(255) DEFAULT NULL,
  `state_province` varchar(255) DEFAULT NULL,
  `postal_code` varchar(50) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `latitude` varchar(50) DEFAULT NULL,
  `longitude` varchar(50) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `county_district` varchar(255) DEFAULT NULL,
  `address3` varchar(255) DEFAULT NULL,
  `address4` varchar(255) DEFAULT NULL,
  `address5` varchar(255) DEFAULT NULL,
  `address6` varchar(255) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `address7` varchar(255) DEFAULT NULL,
  `address8` varchar(255) DEFAULT NULL,
  `address9` varchar(255) DEFAULT NULL,
  `address10` varchar(255) DEFAULT NULL,
  `address11` varchar(255) DEFAULT NULL,
  `address12` varchar(255) DEFAULT NULL,
  `address13` varchar(255) DEFAULT NULL,
  `address14` varchar(255) DEFAULT NULL,
  `address15` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`person_address_id`),
  UNIQUE KEY `person_address_uuid_index` (`uuid`),
  KEY `patient_address_creator` (`creator`),
  KEY `address_for_person` (`person_id`),
  KEY `patient_address_void` (`voided_by`),
  KEY `person_address_changed_by` (`changed_by`),
  CONSTRAINT `address_for_person` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`) ON UPDATE CASCADE,
  CONSTRAINT `patient_address_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `patient_address_void` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `person_address_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_address`
--


--
-- Table structure for table `person_attribute`
--

DROP TABLE IF EXISTS `person_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person_attribute` (
  `person_attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `person_id` int(11) NOT NULL DEFAULT '0',
  `value` varchar(50) NOT NULL DEFAULT '',
  `person_attribute_type_id` int(11) NOT NULL DEFAULT '0',
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`person_attribute_id`),
  UNIQUE KEY `person_attribute_uuid_index` (`uuid`),
  KEY `attribute_changer` (`changed_by`),
  KEY `attribute_creator` (`creator`),
  KEY `defines_attribute_type` (`person_attribute_type_id`),
  KEY `identifies_person` (`person_id`),
  KEY `attribute_voider` (`voided_by`),
  CONSTRAINT `attribute_changer` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `attribute_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `attribute_voider` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `defines_attribute_type` FOREIGN KEY (`person_attribute_type_id`) REFERENCES `person_attribute_type` (`person_attribute_type_id`),
  CONSTRAINT `identifies_person` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_attribute`
--


--
-- Table structure for table `person_attribute_type`
--

DROP TABLE IF EXISTS `person_attribute_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person_attribute_type` (
  `person_attribute_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  `description` text,
  `format` varchar(50) DEFAULT NULL,
  `foreign_key` int(11) DEFAULT NULL,
  `searchable` tinyint(1) NOT NULL DEFAULT '0',
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `edit_privilege` varchar(255) DEFAULT NULL,
  `sort_weight` double DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`person_attribute_type_id`),
  UNIQUE KEY `person_attribute_type_uuid_index` (`uuid`),
  KEY `attribute_is_searchable` (`searchable`),
  KEY `name_of_attribute` (`name`),
  KEY `person_attribute_type_retired_status` (`retired`),
  KEY `attribute_type_changer` (`changed_by`),
  KEY `attribute_type_creator` (`creator`),
  KEY `user_who_retired_person_attribute_type` (`retired_by`),
  KEY `privilege_which_can_edit` (`edit_privilege`),
  CONSTRAINT `attribute_type_changer` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `attribute_type_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `privilege_which_can_edit` FOREIGN KEY (`edit_privilege`) REFERENCES `privilege` (`privilege`),
  CONSTRAINT `user_who_retired_person_attribute_type` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_attribute_type`
--

INSERT INTO `person_attribute_type` VALUES (1,'Race','Group of persons related by common descent or heredity','java.lang.String',0,0,1,'2007-05-04 00:00:00',NULL,NULL,0,NULL,NULL,NULL,NULL,6,'8d871386-c2cc-11de-8d13-0010c6dffd0f'),(2,'Birthplace','Location of persons birth','java.lang.String',0,0,1,'2007-05-04 00:00:00',NULL,NULL,0,NULL,NULL,NULL,NULL,0,'8d8718c2-c2cc-11de-8d13-0010c6dffd0f'),(3,'Citizenship','Country of which this person is a member','java.lang.String',0,0,1,'2007-05-04 00:00:00',NULL,NULL,0,NULL,NULL,NULL,NULL,1,'8d871afc-c2cc-11de-8d13-0010c6dffd0f'),(4,'Mother\'s Name','First or last name of this person\'s mother','java.lang.String',0,0,1,'2007-05-04 00:00:00',NULL,NULL,0,NULL,NULL,NULL,NULL,5,'8d871d18-c2cc-11de-8d13-0010c6dffd0f'),(5,'Civil Status','Marriage status of this person','org.openmrs.Concept',1054,0,1,'2007-05-04 00:00:00',NULL,NULL,0,NULL,NULL,NULL,NULL,2,'8d871f2a-c2cc-11de-8d13-0010c6dffd0f'),(6,'Health District','District/region in which this patient\' home health center resides','java.lang.String',0,0,1,'2007-05-04 00:00:00',NULL,NULL,0,NULL,NULL,NULL,NULL,4,'8d872150-c2cc-11de-8d13-0010c6dffd0f'),(7,'Health Center','Specific Location of this person\'s home health center.','org.openmrs.Location',0,0,1,'2007-05-04 00:00:00',NULL,NULL,0,NULL,NULL,NULL,NULL,3,'8d87236c-c2cc-11de-8d13-0010c6dffd0f');

--
-- Table structure for table `person_merge_log`
--

DROP TABLE IF EXISTS `person_merge_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person_merge_log` (
  `person_merge_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `winner_person_id` int(11) NOT NULL,
  `loser_person_id` int(11) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `merged_data` longtext NOT NULL,
  `uuid` char(38) NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`person_merge_log_id`),
  UNIQUE KEY `person_merge_log_unique_uuid` (`uuid`),
  KEY `person_merge_log_winner` (`winner_person_id`),
  KEY `person_merge_log_loser` (`loser_person_id`),
  KEY `person_merge_log_creator` (`creator`),
  KEY `person_merge_log_changed_by_fk` (`changed_by`),
  KEY `person_merge_log_voided_by_fk` (`voided_by`),
  CONSTRAINT `person_merge_log_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `person_merge_log_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `person_merge_log_loser` FOREIGN KEY (`loser_person_id`) REFERENCES `person` (`person_id`),
  CONSTRAINT `person_merge_log_voided_by_fk` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `person_merge_log_winner` FOREIGN KEY (`winner_person_id`) REFERENCES `person` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_merge_log`
--


--
-- Table structure for table `person_name`
--

DROP TABLE IF EXISTS `person_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person_name` (
  `person_name_id` int(11) NOT NULL AUTO_INCREMENT,
  `preferred` tinyint(1) NOT NULL DEFAULT '0',
  `person_id` int(11) NOT NULL,
  `prefix` varchar(50) DEFAULT NULL,
  `given_name` varchar(50) DEFAULT NULL,
  `middle_name` varchar(50) DEFAULT NULL,
  `family_name_prefix` varchar(50) DEFAULT NULL,
  `family_name` varchar(50) DEFAULT NULL,
  `family_name2` varchar(50) DEFAULT NULL,
  `family_name_suffix` varchar(50) DEFAULT NULL,
  `degree` varchar(50) DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`person_name_id`),
  UNIQUE KEY `person_name_uuid_index` (`uuid`),
  KEY `first_name` (`given_name`),
  KEY `last_name` (`family_name`),
  KEY `middle_name` (`middle_name`),
  KEY `family_name2` (`family_name2`),
  KEY `user_who_made_name` (`creator`),
  KEY `name_for_person` (`person_id`),
  KEY `user_who_voided_name` (`voided_by`),
  CONSTRAINT `name_for_person` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`) ON UPDATE CASCADE,
  CONSTRAINT `user_who_made_name` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_voided_name` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_name`
--

INSERT INTO `person_name` VALUES (1,1,1,NULL,'Super','',NULL,'User',NULL,NULL,NULL,1,'2005-01-01 00:00:00',0,NULL,NULL,NULL,NULL,NULL,'5006c9c0-b095-11eb-9be5-0242c0a82002');

--
-- Table structure for table `privilege`
--

DROP TABLE IF EXISTS `privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `privilege` (
  `privilege` varchar(255) NOT NULL,
  `description` text,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`privilege`),
  UNIQUE KEY `privilege_uuid_index` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privilege`
--

INSERT INTO `privilege` VALUES ('Add Allergies','Add allergies','390077d5-e515-4590-975c-8d8406aef0fe'),('Add Cohorts','Able to add a cohort to the system','50077952-b095-11eb-9be5-0242c0a82002'),('Add Concept Proposals','Able to add concept proposals to the system','50077ac8-b095-11eb-9be5-0242c0a82002'),('Add Encounters','Able to add patient encounters','50077b8b-b095-11eb-9be5-0242c0a82002'),('Add HL7 Inbound Archive','Able to add an HL7 archive item','c89b4fb5-d7d1-4d06-8ece-31a9903e7fe0'),('Add HL7 Inbound Exception','Able to add an HL7 error item','2c3c8e21-cf5e-4275-862d-d0d792dccbef'),('Add HL7 Inbound Queue','Able to add an HL7 Queue item','a9957e81-9d38-4a7f-aa5a-e8161c9bbebe'),('Add HL7 Source','Able to add an HL7 Source','ddf818f5-b852-48cf-b1bf-e16007e9560a'),('Add Observations','Able to add patient observations','50077c32-b095-11eb-9be5-0242c0a82002'),('Add Orders','Able to add orders','50077cd3-b095-11eb-9be5-0242c0a82002'),('Add Patient Identifiers','Able to add patient identifiers','50077d7b-b095-11eb-9be5-0242c0a82002'),('Add Patient Programs','Able to add patients to programs','50077e1c-b095-11eb-9be5-0242c0a82002'),('Add Patients','Able to add patients','50077ebd-b095-11eb-9be5-0242c0a82002'),('Add People','Able to add person objects','50077f59-b095-11eb-9be5-0242c0a82002'),('Add Problems','Add problems','ae664e60-c48f-478a-a8c3-3b1875cca564'),('Add Relationships','Able to add relationships','50077fed-b095-11eb-9be5-0242c0a82002'),('Add Report Objects','Able to add report objects','5007807c-b095-11eb-9be5-0242c0a82002'),('Add Reports','Able to add reports','5007810b-b095-11eb-9be5-0242c0a82002'),('Add Users','Able to add users to OpenMRS','50078197-b095-11eb-9be5-0242c0a82002'),('Add Visits','Able to add visits','65d14b28-3989-11e6-899a-a4d646d86a8a'),('Assign System Developer Role','Able to assign System Developer role','871137d6-0336-4834-a57f-2b0c124d6cef'),('Configure Visits','Able to choose encounter visit handler and enable/disable encounter visits','1c3b08a7-cdcd-48c8-a3cc-196557d0a249'),('Delete Cohorts','Able to add a cohort to the system','50078224-b095-11eb-9be5-0242c0a82002'),('Delete Concept Proposals','Able to delete concept proposals from the system','500782b0-b095-11eb-9be5-0242c0a82002'),('Delete Conditions','Able to delete conditions','5b9caeb4-4594-4ad9-8f30-642c88edffe4'),('Delete Diagnoses','Able to delete diagnoses','7df7c563-2650-412c-9ef3-10252a0427d0'),('Delete Encounters','Able to delete patient encounters','5007833d-b095-11eb-9be5-0242c0a82002'),('Delete HL7 Inbound Archive','Able to delete/retire an HL7 archive item','7ab146ee-95ec-45e9-b1ef-807aa7143efa'),('Delete HL7 Inbound Exception','Able to delete an HL7 archive item','b9497ae6-d401-4027-83b6-60538ee8de68'),('Delete HL7 Inbound Queue','Able to delete an HL7 Queue item','c10f42da-67b8-440c-8b02-df9bc33f7f06'),('Delete Notes','Able to delete patient notes','bfc55681-1ffd-4d68-bc31-c7fd2f3ab92d'),('Delete Observations','Able to delete patient observations','500783ca-b095-11eb-9be5-0242c0a82002'),('Delete Orders','Able to delete orders','50078454-b095-11eb-9be5-0242c0a82002'),('Delete Patient Identifiers','Able to delete patient identifiers','5007866d-b095-11eb-9be5-0242c0a82002'),('Delete Patient Programs','Able to delete patients from programs','50078714-b095-11eb-9be5-0242c0a82002'),('Delete Patients','Able to delete patients','500787a5-b095-11eb-9be5-0242c0a82002'),('Delete People','Able to delete objects','50078835-b095-11eb-9be5-0242c0a82002'),('Delete Relationships','Able to delete relationships','500788c3-b095-11eb-9be5-0242c0a82002'),('Delete Report Objects','Able to delete report objects','50078950-b095-11eb-9be5-0242c0a82002'),('Delete Reports','Able to delete reports','500789dc-b095-11eb-9be5-0242c0a82002'),('Delete Users','Able to delete users in OpenMRS','50078a69-b095-11eb-9be5-0242c0a82002'),('Delete Visits','Able to delete visits','c7571b9f-bce8-41dd-bbd8-8b929fc193b2'),('Edit Allergies','Able to edit allergies','7f8266a7-3aac-4b94-ab42-0c4683253003'),('Edit Cohorts','Able to add a cohort to the system','50078af7-b095-11eb-9be5-0242c0a82002'),('Edit Concept Proposals','Able to edit concept proposals in the system','50078b84-b095-11eb-9be5-0242c0a82002'),('Edit Conditions','Able to edit conditions','5c4ce04b-abdf-47eb-b01e-6a99c69b38d6'),('Edit Diagnoses','Able to edit diagnoses','5b4c8f61-6dfa-4777-bfa8-1506b250754a'),('Edit Encounters','Able to edit patient encounters','50078c12-b095-11eb-9be5-0242c0a82002'),('Edit Notes','Able to edit patient notes','ace26d69-3194-4f3d-bb35-a2440adb26ca'),('Edit Observations','Able to edit patient observations','50078ca1-b095-11eb-9be5-0242c0a82002'),('Edit Orders','Able to edit orders','50078d31-b095-11eb-9be5-0242c0a82002'),('Edit Patient Identifiers','Able to edit patient identifiers','50078dbf-b095-11eb-9be5-0242c0a82002'),('Edit Patient Programs','Able to edit patients in programs','50078e4c-b095-11eb-9be5-0242c0a82002'),('Edit Patients','Able to edit patients','50078edc-b095-11eb-9be5-0242c0a82002'),('Edit People','Able to edit person objects','50078f6b-b095-11eb-9be5-0242c0a82002'),('Edit Problems','Able to edit problems','823f0e86-c280-4cc0-86f7-b13196698f85'),('Edit Relationships','Able to edit relationships','50078ff9-b095-11eb-9be5-0242c0a82002'),('Edit Report Objects','Able to edit report objects','50079086-b095-11eb-9be5-0242c0a82002'),('Edit Reports','Able to edit reports','50079114-b095-11eb-9be5-0242c0a82002'),('Edit User Passwords','Able to change the passwords of users in OpenMRS','500791a0-b095-11eb-9be5-0242c0a82002'),('Edit Users','Able to edit users in OpenMRS','5007922f-b095-11eb-9be5-0242c0a82002'),('Edit Visits','Able to edit visits','affd2acc-fa2b-4e24-b53d-ba17c5d8cefb'),('Form Entry','Allows user to access Form Entry pages/functions','500792bb-b095-11eb-9be5-0242c0a82002'),('Get Allergies','Able to get allergies','d05118c6-2490-4d78-a41a-390e3596a220'),('Get Care Settings','Able to get Care Settings','2a9b0a12-5a95-4018-b6c9-cc9aa0d598f6'),('Get Concept Attribute Types','Able to get concept attribute types','49175d1c-35ca-4871-9c5c-8a904a9b3299'),('Get Concept Classes','Able to get concept classes','d05118c6-2490-4d78-a41a-390e3596a238'),('Get Concept Datatypes','Able to get concept datatypes','d05118c6-2490-4d78-a41a-390e3596a237'),('Get Concept Map Types','Able to get concept map types','d05118c6-2490-4d78-a41a-390e3596a230'),('Get Concept Proposals','Able to get concept proposals to the system','d05118c6-2490-4d78-a41a-390e3596a250'),('Get Concept Reference Terms','Able to get concept reference terms','d05118c6-2490-4d78-a41a-390e3596a229'),('Get Concept Sources','Able to get concept sources','d05118c6-2490-4d78-a41a-390e3596a231'),('Get Concepts','Able to get concept entries','d05118c6-2490-4d78-a41a-390e3596a251'),('Get Conditions','Able to get conditions','6818db18-28b6-45e4-996a-e8bf4598d848'),('Get Database Changes','Able to get database changes from the admin screen','d05118c6-2490-4d78-a41a-390e3596a222'),('Get Diagnoses','Able to get diagnoses','bd07070c-611d-495c-912f-8a8d93052669'),('Get Diagnoses Attribute Types','Able to get diagnoses attribute types','7705f2d7-ce34-43b6-a5af-7ebdda8364e4'),('Get Encounter Roles','Able to get encounter roles','d05118c6-2490-4d78-a41a-390e3596a210'),('Get Encounter Types','Able to get encounter types','d05118c6-2490-4d78-a41a-390e3596a247'),('Get Encounters','Able to get patient encounters','d05118c6-2490-4d78-a41a-390e3596a248'),('Get Field Types','Able to get field types','d05118c6-2490-4d78-a41a-390e3596a234'),('Get Forms','Able to get forms','d05118c6-2490-4d78-a41a-390e3596a240'),('Get Global Properties','Able to get global properties on the administration screen','d05118c6-2490-4d78-a41a-390e3596a226'),('Get HL7 Inbound Archive','Able to get an HL7 archive item','d05118c6-2490-4d78-a41a-390e3596a217'),('Get HL7 Inbound Exception','Able to get an HL7 error item','d05118c6-2490-4d78-a41a-390e3596a216'),('Get HL7 Inbound Queue','Able to get an HL7 Queue item','d05118c6-2490-4d78-a41a-390e3596a218'),('Get HL7 Source','Able to get an HL7 Source','d05118c6-2490-4d78-a41a-390e3596a219'),('Get Identifier Types','Able to get patient identifier types','d05118c6-2490-4d78-a41a-390e3596a239'),('Get Location Attribute Types','Able to get location attribute types','d05118c6-2490-4d78-a41a-390e3596a212'),('Get Locations','Able to get locations','d05118c6-2490-4d78-a41a-390e3596a246'),('Get Notes','Able to get patient notes','a5b589cf-0dee-4775-8098-1e625be21adf'),('Get Observations','Able to get patient observations','d05118c6-2490-4d78-a41a-390e3596a245'),('Get Order Frequencies','Able to get Order Frequencies','96446f47-cd18-48cb-9409-e6d8729e8dc3'),('Get Order Set Attribute Types','Able to get order set attribute types','6ee096cf-636a-4f04-a2d6-00857fc90461'),('Get Order Sets','Able to get order sets','f78da660-5fd8-4234-ab51-f33a010430ba'),('Get Order Types','Able to get order types','d05118c6-2490-4d78-a41a-390e3596a233'),('Get Orders','Able to get orders','d05118c6-2490-4d78-a41a-390e3596a241'),('Get Patient Cohorts','Able to get patient cohorts','d05118c6-2490-4d78-a41a-390e3596a242'),('Get Patient Identifiers','Able to get patient identifiers','d05118c6-2490-4d78-a41a-390e3596a243'),('Get Patient Programs','Able to get which programs that patients are in','d05118c6-2490-4d78-a41a-390e3596a227'),('Get Patients','Able to get patients','d05118c6-2490-4d78-a41a-390e3596a244'),('Get People','Able to get person objects','d05118c6-2490-4d78-a41a-390e3596a224'),('Get Person Attribute Types','Able to get person attribute types','d05118c6-2490-4d78-a41a-390e3596a225'),('Get Privileges','Able to get user privileges','d05118c6-2490-4d78-a41a-390e3596a236'),('Get Problems','Able to get problems','d05118c6-2490-4d78-a41a-390e3596a221'),('Get Programs','Able to get patient programs','d05118c6-2490-4d78-a41a-390e3596a228'),('Get Providers','Able to get Providers','d05118c6-2490-4d78-a41a-390e3596a211'),('Get Relationship Types','Able to get relationship types','d05118c6-2490-4d78-a41a-390e3596a232'),('Get Relationships','Able to get relationships','d05118c6-2490-4d78-a41a-390e3596a223'),('Get Roles','Able to get user roles','d05118c6-2490-4d78-a41a-390e3596a235'),('Get Users','Able to get users in OpenMRS','d05118c6-2490-4d78-a41a-390e3596a249'),('Get Visit Attribute Types','Able to get visit attribute types','d05118c6-2490-4d78-a41a-390e3596a213'),('Get Visit Types','Able to get visit types','d05118c6-2490-4d78-a41a-390e3596a215'),('Get Visits','Able to get visits','d05118c6-2490-4d78-a41a-390e3596a214'),('Manage Address Templates','Able to add/edit/delete address templates','051a532a-b69a-42ea-87f2-8aae5366016f'),('Manage Alerts','Able to add/edit/delete user alerts','50079349-b095-11eb-9be5-0242c0a82002'),('Manage Concept Attribute Types','Able to add/edit/retire concept attribute types','f08708e2-dc46-4f9a-ad65-207973287f65'),('Manage Concept Classes','Able to add/edit/retire concept classes','500793d5-b095-11eb-9be5-0242c0a82002'),('Manage Concept Datatypes','Able to add/edit/retire concept datatypes','50079463-b095-11eb-9be5-0242c0a82002'),('Manage Concept Map Types','Able to add/edit/retire concept map types','5f77f75a-3c17-4e13-82fc-c69b1dc39fbe'),('Manage Concept Name tags','Able to add/edit/delete concept name tags','bf46bd9a-02ba-4981-953c-c8bc6cc1ff66'),('Manage Concept Reference Terms','Able to add/edit/retire reference terms','622c00ed-0c32-4e2d-9030-11ce9d63894a'),('Manage Concept Sources','Able to add/edit/delete concept sources','500794f0-b095-11eb-9be5-0242c0a82002'),('Manage Concept Stop Words','Able to view/add/remove the concept stop words','91117476-04c0-4a5f-bfd8-b69f5ffac5ca'),('Manage Concepts','Able to add/edit/delete concept entries','50079581-b095-11eb-9be5-0242c0a82002'),('Manage Encounter Roles','Able to add/edit/retire encounter roles','0b8a6c12-0e5f-4809-89a7-55c16ca98448'),('Manage Encounter Types','Able to add/edit/delete encounter types','50079615-b095-11eb-9be5-0242c0a82002'),('Manage Field Types','Able to add/edit/retire field types','500796a3-b095-11eb-9be5-0242c0a82002'),('Manage FormEntry XSN','Allows user to upload and edit the xsns stored on the server','50079730-b095-11eb-9be5-0242c0a82002'),('Manage Forms','Able to add/edit/delete forms','500797bd-b095-11eb-9be5-0242c0a82002'),('Manage Global Properties','Able to add/edit global properties','5007984c-b095-11eb-9be5-0242c0a82002'),('Manage HL7 Messages','Able to add/edit/delete HL7 messages','c56ef5b4-99f4-4dfe-bad4-ef7801471642'),('Manage Identifier Types','Able to add/edit/delete patient identifier types','500798d8-b095-11eb-9be5-0242c0a82002'),('Manage Implementation Id','Able to view/add/edit the implementation id for the system','3841e100-4082-418a-84d2-73980ae6089a'),('Manage Location Attribute Types','Able to add/edit/retire location attribute types','36f4ec97-919e-417b-92ab-73f29c17cb1b'),('Manage Location Tags','Able to add/edit/delete location tags','fa2e5bdd-4931-44e1-b2b9-3d5f34a29abb'),('Manage Locations','Able to add/edit/delete locations','50079966-b095-11eb-9be5-0242c0a82002'),('Manage Modules','Able to add/remove modules to the system','500799f5-b095-11eb-9be5-0242c0a82002'),('Manage Order Frequencies','Able to add/edit/retire Order Frequencies','6900c3de-3be2-4654-8b5b-a047631d9a50'),('Manage Order Set Attribute Types','Able to add/edit/retire order set attribute types','8edf2208-ee24-41ee-bb50-489cee0e2feb'),('Manage Order Sets','Able to manage order sets','29c817f1-a31f-49fc-a912-751fbe66e004'),('Manage Order Types','Able to add/edit/retire order types','50079a81-b095-11eb-9be5-0242c0a82002'),('Manage OWA','Allows to configure OWA module, upload modules','3e216af8-79e6-4a71-8fdf-5f311105f1cb'),('Manage Person Attribute Types','Able to add/edit/delete person attribute types','50079b0d-b095-11eb-9be5-0242c0a82002'),('Manage Privileges','Able to add/edit/delete privileges','50079b9d-b095-11eb-9be5-0242c0a82002'),('Manage Programs','Able to add/view/delete patient programs','50079c2b-b095-11eb-9be5-0242c0a82002'),('Manage Providers','Able to edit Provider','14d614df-b9a2-41c2-bdad-703714e2be4d'),('Manage Relationship Types','Able to add/edit/retire relationship types','50079cb8-b095-11eb-9be5-0242c0a82002'),('Manage Relationships','Able to add/edit/delete relationships','50079d46-b095-11eb-9be5-0242c0a82002'),('Manage RESTWS','Allows to configure RESTWS module','ede8b7f5-28f9-44c2-867d-fe35a06a0b40'),('Manage Roles','Able to add/edit/delete user roles','50079dd7-b095-11eb-9be5-0242c0a82002'),('Manage Scheduler','Able to add/edit/remove scheduled tasks','50079e64-b095-11eb-9be5-0242c0a82002'),('Manage Search Index','Able to manage the search index','eae0c789-6fbc-47f2-8f48-40a1d9611b0a'),('Manage Visit Attribute Types','Able to add/edit/retire visit attribute types','a283bbe8-ee60-4ebd-a89b-5ba3875b7cec'),('Manage Visit Types','Able to add/edit/delete visit types','8e8b6cd0-c256-4406-a9f4-6351e6c4fd71'),('Patient Dashboard - View Demographics Section','Able to view the \'Demographics\' tab on the patient dashboard','50079ef2-b095-11eb-9be5-0242c0a82002'),('Patient Dashboard - View Encounters Section','Able to view the \'Encounters\' tab on the patient dashboard','50079f81-b095-11eb-9be5-0242c0a82002'),('Patient Dashboard - View Forms Section','Allows user to view the Forms tab on the patient dashboard','5007a013-b095-11eb-9be5-0242c0a82002'),('Patient Dashboard - View Graphs Section','Able to view the \'Graphs\' tab on the patient dashboard','5007a0a3-b095-11eb-9be5-0242c0a82002'),('Patient Dashboard - View Overview Section','Able to view the \'Overview\' tab on the patient dashboard','5007a132-b095-11eb-9be5-0242c0a82002'),('Patient Dashboard - View Patient Summary','Able to view the \'Summary\' tab on the patient dashboard','5007a1bf-b095-11eb-9be5-0242c0a82002'),('Patient Dashboard - View Regimen Section','Able to view the \'Regimen\' tab on the patient dashboard','5007a24f-b095-11eb-9be5-0242c0a82002'),('Patient Overview - View Allergies','Able to view the Allergies portlet on the patient overview tab','d05118c6-2490-4d78-a41a-390e3596a261'),('Patient Overview - View Patient Actions','Able to view the Patient Actions portlet on the patient overview tab','d05118c6-2490-4d78-a41a-390e3596a264'),('Patient Overview - View Problem List','Able to view the Problem List portlet on the patient overview tab','d05118c6-2490-4d78-a41a-390e3596a260'),('Patient Overview - View Programs','Able to view the Programs portlet on the patient overview tab','d05118c6-2490-4d78-a41a-390e3596a263'),('Patient Overview - View Relationships','Able to view the Relationships portlet on the patient overview tab','d05118c6-2490-4d78-a41a-390e3596a262'),('Purge Field Types','Able to purge field types','5007a2e8-b095-11eb-9be5-0242c0a82002'),('Remove Allergies','Remove allergies','4caee8d1-80a5-403e-b02d-967862ae27a1'),('Remove Problems','Remove problems','f95ccf60-0065-4181-9036-307226047390'),('Task: Modify Allergies','Able to add, edit, delete allergies','79e6eef0-1f82-407d-babb-58f8cf750ef5'),('Update HL7 Inbound Archive','Able to update an HL7 archive item','43b4c8b1-f254-4408-940f-1e12315b16df'),('Update HL7 Inbound Exception','Able to update an HL7 archive item','48aa6ec7-f4a0-4a68-b22f-c141e66d7ac0'),('Update HL7 Inbound Queue','Able to update an HL7 Queue item','4f433da9-3097-43b4-b8ad-81d2a90a83b3'),('Update HL7 Source','Able to update an HL7 Source','1e7fcdba-27f3-4bfb-842f-7c8837c83338'),('Upload XSN','Allows user to upload/overwrite the XSNs defined for forms','5007a37c-b095-11eb-9be5-0242c0a82002'),('View Administration Functions','Able to view the \'Administration\' link in the navigation bar','5007a40c-b095-11eb-9be5-0242c0a82002'),('View Allergies','Able to view allergies in OpenMRS','5007a49c-b095-11eb-9be5-0242c0a82002'),('View Concept Classes','Able to view concept classes','5007a5e3-b095-11eb-9be5-0242c0a82002'),('View Concept Datatypes','Able to view concept datatypes','5007a680-b095-11eb-9be5-0242c0a82002'),('View Concept Proposals','Able to view concept proposals to the system','5007a70f-b095-11eb-9be5-0242c0a82002'),('View Concept Sources','Able to view concept sources','5007a79f-b095-11eb-9be5-0242c0a82002'),('View Concepts','Able to view concept entries','5007a82d-b095-11eb-9be5-0242c0a82002'),('View Data Entry Statistics','Able to view data entry statistics from the admin screen','5007a8ba-b095-11eb-9be5-0242c0a82002'),('View Encounter Types','Able to view encounter types','5007a94a-b095-11eb-9be5-0242c0a82002'),('View Encounters','Able to view patient encounters','5007a9d9-b095-11eb-9be5-0242c0a82002'),('View FHIR Client','Gives access to FHIR rest client','dd773d89-1e03-462a-bb52-f89903c6931f'),('View Field Types','Able to view field types','5007aa69-b095-11eb-9be5-0242c0a82002'),('View Forms','Able to view forms','5007aaf6-b095-11eb-9be5-0242c0a82002'),('View Global Properties','Able to view global properties on the administration screen','5007ab82-b095-11eb-9be5-0242c0a82002'),('View Identifier Types','Able to view patient identifier types','5007ac12-b095-11eb-9be5-0242c0a82002'),('View Locations','Able to view locations','5007aca0-b095-11eb-9be5-0242c0a82002'),('View Navigation Menu','Ability to see the navigation menu','5007ad33-b095-11eb-9be5-0242c0a82002'),('View Observations','Able to view patient observations','5007adc4-b095-11eb-9be5-0242c0a82002'),('View Order Types','Able to view order types','5007ae52-b095-11eb-9be5-0242c0a82002'),('View Orders','Able to view orders','5007aee3-b095-11eb-9be5-0242c0a82002'),('View Patient Cohorts','Able to view patient cohorts','5007af73-b095-11eb-9be5-0242c0a82002'),('View Patient Identifiers','Able to view patient identifiers','5007b003-b095-11eb-9be5-0242c0a82002'),('View Patient Programs','Able to see which programs that patients are in','5007b090-b095-11eb-9be5-0242c0a82002'),('View Patients','Able to view patients','5007b11d-b095-11eb-9be5-0242c0a82002'),('View People','Able to view person objects','5007b364-b095-11eb-9be5-0242c0a82002'),('View Person Attribute Types','Able to view person attribute types','5007b400-b095-11eb-9be5-0242c0a82002'),('View Privileges','Able to view user privileges','5007b491-b095-11eb-9be5-0242c0a82002'),('View Problems','Able to view problems in OpenMRS','5007b51f-b095-11eb-9be5-0242c0a82002'),('View Programs','Able to view patient programs','5007b5ad-b095-11eb-9be5-0242c0a82002'),('View Relationship Types','Able to view relationship types','5007b63b-b095-11eb-9be5-0242c0a82002'),('View Relationships','Able to view relationships','5007b6c8-b095-11eb-9be5-0242c0a82002'),('View Report Objects','Able to view report objects','5007b758-b095-11eb-9be5-0242c0a82002'),('View Reports','Able to view reports','5007b7e6-b095-11eb-9be5-0242c0a82002'),('View RESTWS','Gives access to RESTWS in administration','bd2e4952-4c7f-41c5-8970-8a82fd0d61d3'),('View Roles','Able to view user roles','5007b874-b095-11eb-9be5-0242c0a82002'),('View Unpublished Forms','Able to view and fill out unpublished forms','5007b905-b095-11eb-9be5-0242c0a82002'),('View Users','Able to view users in OpenMRS','5007b998-b095-11eb-9be5-0242c0a82002');

--
-- Table structure for table `program`
--

DROP TABLE IF EXISTS `program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program` (
  `program_id` int(11) NOT NULL AUTO_INCREMENT,
  `concept_id` int(11) NOT NULL DEFAULT '0',
  `outcomes_concept_id` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `name` varchar(50) NOT NULL,
  `description` text,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`program_id`),
  UNIQUE KEY `program_uuid_index` (`uuid`),
  KEY `user_who_changed_program` (`changed_by`),
  KEY `program_concept` (`concept_id`),
  KEY `program_creator` (`creator`),
  KEY `program_outcomes_concept_id_fk` (`outcomes_concept_id`),
  CONSTRAINT `program_concept` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `program_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `program_outcomes_concept_id_fk` FOREIGN KEY (`outcomes_concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `user_who_changed_program` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program`
--


--
-- Table structure for table `program_attribute_type`
--

DROP TABLE IF EXISTS `program_attribute_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program_attribute_type` (
  `program_attribute_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `datatype` varchar(255) DEFAULT NULL,
  `datatype_config` text,
  `preferred_handler` varchar(255) DEFAULT NULL,
  `handler_config` text,
  `min_occurs` int(11) NOT NULL,
  `max_occurs` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`program_attribute_type_id`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `name` (`name`),
  KEY `program_attribute_type_creator_fk` (`creator`),
  KEY `program_attribute_type_changed_by_fk` (`changed_by`),
  KEY `program_attribute_type_retired_by_fk` (`retired_by`),
  CONSTRAINT `program_attribute_type_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `program_attribute_type_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `program_attribute_type_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program_attribute_type`
--


--
-- Table structure for table `program_workflow`
--

DROP TABLE IF EXISTS `program_workflow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program_workflow` (
  `program_workflow_id` int(11) NOT NULL AUTO_INCREMENT,
  `program_id` int(11) NOT NULL DEFAULT '0',
  `concept_id` int(11) NOT NULL DEFAULT '0',
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`program_workflow_id`),
  UNIQUE KEY `program_workflow_uuid_index` (`uuid`),
  KEY `workflow_changed_by` (`changed_by`),
  KEY `workflow_concept` (`concept_id`),
  KEY `workflow_creator` (`creator`),
  KEY `program_for_workflow` (`program_id`),
  CONSTRAINT `program_for_workflow` FOREIGN KEY (`program_id`) REFERENCES `program` (`program_id`),
  CONSTRAINT `workflow_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `workflow_concept` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `workflow_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program_workflow`
--


--
-- Table structure for table `program_workflow_state`
--

DROP TABLE IF EXISTS `program_workflow_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program_workflow_state` (
  `program_workflow_state_id` int(11) NOT NULL AUTO_INCREMENT,
  `program_workflow_id` int(11) NOT NULL DEFAULT '0',
  `concept_id` int(11) NOT NULL DEFAULT '0',
  `initial` tinyint(1) NOT NULL DEFAULT '0',
  `terminal` tinyint(1) NOT NULL DEFAULT '0',
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`program_workflow_state_id`),
  UNIQUE KEY `program_workflow_state_uuid_index` (`uuid`),
  KEY `state_changed_by` (`changed_by`),
  KEY `state_concept` (`concept_id`),
  KEY `state_creator` (`creator`),
  KEY `workflow_for_state` (`program_workflow_id`),
  CONSTRAINT `state_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `state_concept` FOREIGN KEY (`concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `state_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `workflow_for_state` FOREIGN KEY (`program_workflow_id`) REFERENCES `program_workflow` (`program_workflow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program_workflow_state`
--


--
-- Table structure for table `provider`
--

DROP TABLE IF EXISTS `provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provider` (
  `provider_id` int(11) NOT NULL AUTO_INCREMENT,
  `person_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `speciality_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`provider_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `provider_changed_by_fk` (`changed_by`),
  KEY `provider_person_id_fk` (`person_id`),
  KEY `provider_retired_by_fk` (`retired_by`),
  KEY `provider_creator_fk` (`creator`),
  KEY `provider_role_id_fk` (`role_id`),
  KEY `provider_speciality_id_fk` (`speciality_id`),
  CONSTRAINT `provider_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `provider_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `provider_person_id_fk` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`),
  CONSTRAINT `provider_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `provider_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `provider_speciality_id_fk` FOREIGN KEY (`speciality_id`) REFERENCES `concept` (`concept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provider`
--


--
-- Table structure for table `provider_attribute`
--

DROP TABLE IF EXISTS `provider_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provider_attribute` (
  `provider_attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `provider_id` int(11) NOT NULL,
  `attribute_type_id` int(11) NOT NULL,
  `value_reference` text NOT NULL,
  `uuid` char(38) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`provider_attribute_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `provider_attribute_provider_fk` (`provider_id`),
  KEY `provider_attribute_attribute_type_id_fk` (`attribute_type_id`),
  KEY `provider_attribute_creator_fk` (`creator`),
  KEY `provider_attribute_changed_by_fk` (`changed_by`),
  KEY `provider_attribute_voided_by_fk` (`voided_by`),
  CONSTRAINT `provider_attribute_attribute_type_id_fk` FOREIGN KEY (`attribute_type_id`) REFERENCES `provider_attribute_type` (`provider_attribute_type_id`),
  CONSTRAINT `provider_attribute_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `provider_attribute_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `provider_attribute_provider_fk` FOREIGN KEY (`provider_id`) REFERENCES `provider` (`provider_id`),
  CONSTRAINT `provider_attribute_voided_by_fk` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provider_attribute`
--


--
-- Table structure for table `provider_attribute_type`
--

DROP TABLE IF EXISTS `provider_attribute_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provider_attribute_type` (
  `provider_attribute_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `datatype` varchar(255) DEFAULT NULL,
  `datatype_config` text,
  `preferred_handler` varchar(255) DEFAULT NULL,
  `handler_config` text,
  `min_occurs` int(11) NOT NULL,
  `max_occurs` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`provider_attribute_type_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `provider_attribute_type_creator_fk` (`creator`),
  KEY `provider_attribute_type_changed_by_fk` (`changed_by`),
  KEY `provider_attribute_type_retired_by_fk` (`retired_by`),
  CONSTRAINT `provider_attribute_type_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `provider_attribute_type_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `provider_attribute_type_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provider_attribute_type`
--


--
-- Table structure for table `referral_order`
--

DROP TABLE IF EXISTS `referral_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `referral_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `specimen_source` int(11) DEFAULT NULL,
  `laterality` varchar(20) DEFAULT NULL,
  `clinical_history` text,
  `frequency` int(11) DEFAULT NULL,
  `number_of_repeats` int(11) DEFAULT NULL,
  `location` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `referral_order_location_fk` (`location`),
  KEY `referral_order_frequency_index` (`frequency`),
  KEY `referral_order_specimen_source_index` (`specimen_source`),
  CONSTRAINT `referral_order_frequency_fk` FOREIGN KEY (`frequency`) REFERENCES `order_frequency` (`order_frequency_id`),
  CONSTRAINT `referral_order_location_fk` FOREIGN KEY (`location`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `referral_order_order_id_fk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `referral_order_specimen_source_fk` FOREIGN KEY (`specimen_source`) REFERENCES `concept` (`concept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `referral_order`
--


--
-- Table structure for table `relationship`
--

DROP TABLE IF EXISTS `relationship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relationship` (
  `relationship_id` int(11) NOT NULL AUTO_INCREMENT,
  `person_a` int(11) NOT NULL,
  `relationship` int(11) NOT NULL DEFAULT '0',
  `person_b` int(11) NOT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `date_changed` datetime DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`relationship_id`),
  UNIQUE KEY `relationship_uuid_index` (`uuid`),
  KEY `relation_creator` (`creator`),
  KEY `person_a_is_person` (`person_a`),
  KEY `person_b_is_person` (`person_b`),
  KEY `relationship_type_id` (`relationship`),
  KEY `relation_voider` (`voided_by`),
  KEY `relationship_changed_by` (`changed_by`),
  CONSTRAINT `person_a_is_person` FOREIGN KEY (`person_a`) REFERENCES `person` (`person_id`),
  CONSTRAINT `person_b_is_person` FOREIGN KEY (`person_b`) REFERENCES `person` (`person_id`),
  CONSTRAINT `relation_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `relation_voider` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `relationship_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `relationship_type_id` FOREIGN KEY (`relationship`) REFERENCES `relationship_type` (`relationship_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relationship`
--


--
-- Table structure for table `relationship_type`
--

DROP TABLE IF EXISTS `relationship_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relationship_type` (
  `relationship_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `a_is_to_b` varchar(50) NOT NULL,
  `b_is_to_a` varchar(50) NOT NULL,
  `preferred` tinyint(1) NOT NULL DEFAULT '0',
  `weight` int(11) NOT NULL DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `date_changed` datetime DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`relationship_type_id`),
  UNIQUE KEY `relationship_type_uuid_index` (`uuid`),
  KEY `user_who_created_rel` (`creator`),
  KEY `user_who_retired_relationship_type` (`retired_by`),
  KEY `relationship_type_changed_by` (`changed_by`),
  CONSTRAINT `relationship_type_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_created_rel` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_retired_relationship_type` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relationship_type`
--

INSERT INTO `relationship_type` VALUES (1,'Doctor','Patient',0,0,'Relationship from a primary care provider to the patient',1,'2007-05-04 00:00:00',0,NULL,NULL,NULL,'8d919b58-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(2,'Sibling','Sibling',0,0,'Relationship between brother/sister, brother/brother, and sister/sister',1,'2007-05-04 00:00:00',0,NULL,NULL,NULL,'8d91a01c-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(3,'Parent','Child',0,0,'Relationship from a mother/father to the child',1,'2007-05-04 00:00:00',0,NULL,NULL,NULL,'8d91a210-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL),(4,'Aunt/Uncle','Niece/Nephew',0,0,'Relationship from a parent\'s sibling to a child of that parent',1,'2007-05-04 00:00:00',0,NULL,NULL,NULL,'8d91a3dc-c2cc-11de-8d13-0010c6dffd0f',NULL,NULL);

--
-- Table structure for table `report_object`
--

DROP TABLE IF EXISTS `report_object`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report_object` (
  `report_object_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `report_object_type` varchar(255) NOT NULL,
  `report_object_sub_type` varchar(255) NOT NULL,
  `xml_data` text,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`report_object_id`),
  UNIQUE KEY `report_object_uuid_index` (`uuid`),
  KEY `user_who_changed_report_object` (`changed_by`),
  KEY `report_object_creator` (`creator`),
  KEY `user_who_voided_report_object` (`voided_by`),
  CONSTRAINT `report_object_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_changed_report_object` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_voided_report_object` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_object`
--


--
-- Table structure for table `report_schema_xml`
--

DROP TABLE IF EXISTS `report_schema_xml`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report_schema_xml` (
  `report_schema_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `xml_data` text NOT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`report_schema_id`),
  UNIQUE KEY `report_schema_xml_uuid_index` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_schema_xml`
--


--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role` varchar(50) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`role`),
  UNIQUE KEY `role_uuid_index` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

INSERT INTO `role` VALUES ('Anonymous','Privileges for non-authenticated users.','774b2af3-6437-4e5a-a310-547554c7c65c'),('Authenticated','Privileges gained once authentication has been established.','f7fd42ef-880e-40c5-972d-e4ae7c990de2'),('Provider','All users with the \'Provider\' role will appear as options in the default Infopath ','8d94f280-c2cc-11de-8d13-0010c6dffd0f'),('System Developer','Developers of the OpenMRS .. have additional access to change fundamental structure of the database model.','8d94f852-c2cc-11de-8d13-0010c6dffd0f');

--
-- Table structure for table `role_privilege`
--

DROP TABLE IF EXISTS `role_privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_privilege` (
  `role` varchar(50) NOT NULL DEFAULT '',
  `privilege` varchar(255) NOT NULL,
  PRIMARY KEY (`privilege`,`role`),
  KEY `role_privilege_to_role` (`role`),
  CONSTRAINT `privilege_definitions` FOREIGN KEY (`privilege`) REFERENCES `privilege` (`privilege`),
  CONSTRAINT `role_privilege_to_role` FOREIGN KEY (`role`) REFERENCES `role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_privilege`
--

INSERT INTO `role_privilege` VALUES ('Authenticated','Get Concept Classes'),('Authenticated','Get Concept Datatypes'),('Authenticated','Get Encounter Types'),('Authenticated','Get Field Types'),('Authenticated','Get Global Properties'),('Authenticated','Get Identifier Types'),('Authenticated','Get Locations'),('Authenticated','Get Order Types'),('Authenticated','Get Person Attribute Types'),('Authenticated','Get Privileges'),('Authenticated','Get Relationship Types'),('Authenticated','Get Relationships'),('Authenticated','Get Roles'),('Authenticated','Patient Overview - View Relationships'),('Authenticated','View Concept Classes'),('Authenticated','View Concept Datatypes'),('Authenticated','View Encounter Types'),('Authenticated','View Field Types'),('Authenticated','View Global Properties'),('Authenticated','View Identifier Types'),('Authenticated','View Locations'),('Authenticated','View Order Types'),('Authenticated','View Person Attribute Types'),('Authenticated','View Privileges'),('Authenticated','View Relationship Types'),('Authenticated','View Relationships'),('Authenticated','View Roles');

--
-- Table structure for table `role_role`
--

DROP TABLE IF EXISTS `role_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_role` (
  `parent_role` varchar(50) NOT NULL DEFAULT '',
  `child_role` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`parent_role`,`child_role`),
  KEY `inherited_role` (`child_role`),
  CONSTRAINT `inherited_role` FOREIGN KEY (`child_role`) REFERENCES `role` (`role`),
  CONSTRAINT `parent_role` FOREIGN KEY (`parent_role`) REFERENCES `role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_role`
--


--
-- Table structure for table `scheduler_task_config`
--

DROP TABLE IF EXISTS `scheduler_task_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scheduler_task_config` (
  `task_config_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `schedulable_class` text,
  `start_time` datetime DEFAULT NULL,
  `start_time_pattern` varchar(50) DEFAULT NULL,
  `repeat_interval` int(11) NOT NULL DEFAULT '0',
  `start_on_startup` tinyint(1) NOT NULL DEFAULT '0',
  `started` tinyint(1) NOT NULL DEFAULT '0',
  `created_by` int(11) DEFAULT '0',
  `date_created` datetime DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `last_execution_time` datetime DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`task_config_id`),
  UNIQUE KEY `scheduler_task_config_uuid_index` (`uuid`),
  KEY `scheduler_changer` (`changed_by`),
  KEY `scheduler_creator` (`created_by`),
  CONSTRAINT `scheduler_changer` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `scheduler_creator` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduler_task_config`
--

INSERT INTO `scheduler_task_config` VALUES (2,'Auto Close Visits Task','Stops all active visits that match the visit type(s) specified by the value of the global property \'visits.autoCloseVisitType\'','org.openmrs.scheduler.tasks.AutoCloseVisitsTask','2011-11-28 23:59:59','MM/dd/yyyy HH:mm:ss',86400,0,0,1,'2021-05-09 07:08:45',NULL,NULL,NULL,'8c17b376-1a2b-11e1-a51a-00248140a5eb');

--
-- Table structure for table `scheduler_task_config_property`
--

DROP TABLE IF EXISTS `scheduler_task_config_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scheduler_task_config_property` (
  `task_config_property_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` text,
  `task_config_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`task_config_property_id`),
  KEY `task_config_for_property` (`task_config_id`),
  CONSTRAINT `task_config_for_property` FOREIGN KEY (`task_config_id`) REFERENCES `scheduler_task_config` (`task_config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduler_task_config_property`
--


--
-- Table structure for table `serialized_object`
--

DROP TABLE IF EXISTS `serialized_object`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serialized_object` (
  `serialized_object_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  `subtype` varchar(255) NOT NULL,
  `serialization_class` varchar(255) NOT NULL,
  `serialized_data` mediumtext NOT NULL,
  `date_created` datetime NOT NULL,
  `creator` int(11) NOT NULL,
  `date_changed` datetime DEFAULT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `date_retired` datetime DEFAULT NULL,
  `retired_by` int(11) DEFAULT NULL,
  `retire_reason` varchar(1000) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`serialized_object_id`),
  UNIQUE KEY `serialized_object_uuid_index` (`uuid`),
  KEY `serialized_object_creator` (`creator`),
  KEY `serialized_object_changed_by` (`changed_by`),
  KEY `serialized_object_retired_by` (`retired_by`),
  CONSTRAINT `serialized_object_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `serialized_object_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `serialized_object_retired_by` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serialized_object`
--


--
-- Table structure for table `test_order`
--

DROP TABLE IF EXISTS `test_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_order` (
  `order_id` int(11) NOT NULL DEFAULT '0',
  `specimen_source` int(11) DEFAULT NULL,
  `laterality` varchar(20) DEFAULT NULL,
  `clinical_history` text,
  `frequency` int(11) DEFAULT NULL,
  `number_of_repeats` int(11) DEFAULT NULL,
  `location` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `test_order_specimen_source_fk` (`specimen_source`),
  KEY `test_order_frequency_fk` (`frequency`),
  KEY `test_order_location_fk` (`location`),
  CONSTRAINT `test_order_frequency_fk` FOREIGN KEY (`frequency`) REFERENCES `order_frequency` (`order_frequency_id`),
  CONSTRAINT `test_order_location_fk` FOREIGN KEY (`location`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `test_order_order_id_fk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `test_order_specimen_source_fk` FOREIGN KEY (`specimen_source`) REFERENCES `concept` (`concept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_order`
--


--
-- Table structure for table `user_property`
--

DROP TABLE IF EXISTS `user_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_property` (
  `user_id` int(11) NOT NULL DEFAULT '0',
  `property` varchar(255) NOT NULL,
  `property_value` longtext,
  PRIMARY KEY (`user_id`,`property`),
  CONSTRAINT `user_property_to_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_property`
--


--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL DEFAULT '0',
  `role` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`role`,`user_id`),
  KEY `user_role_to_users` (`user_id`),
  CONSTRAINT `role_definitions` FOREIGN KEY (`role`) REFERENCES `role` (`role`),
  CONSTRAINT `user_role_to_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` VALUES (1,'Provider'),(1,'System Developer');

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `system_id` varchar(50) NOT NULL DEFAULT '',
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `salt` varchar(128) DEFAULT NULL,
  `secret_question` varchar(255) DEFAULT NULL,
  `secret_answer` varchar(255) DEFAULT NULL,
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `person_id` int(11) NOT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  `activation_key` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  KEY `user_who_changed_user` (`changed_by`),
  KEY `user_creator` (`creator`),
  KEY `user_who_retired_this_user` (`retired_by`),
  KEY `person_id_for_user` (`person_id`),
  CONSTRAINT `person_id_for_user` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`),
  CONSTRAINT `user_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_changed_user` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `user_who_retired_this_user` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

INSERT INTO `users` VALUES (1,'admin','admin','6f0be51d599f59dd1269e12e17949f8ecb9ac963e467ac1400cf0a02eb9f8861ce3cca8f6d34d93c0ca34029497542cbadda20c949affb4cb59269ef4912087b','c788c6ad82a157b712392ca695dfcf2eed193d7f',NULL,NULL,1,'2005-01-01 00:00:00',1,'2021-05-09 07:09:29',1,0,NULL,NULL,NULL,'5f5c8cb5-b095-11eb-9be5-0242c0a82002',NULL,NULL),(2,'daemon','daemon',NULL,NULL,NULL,NULL,1,'2010-04-26 13:25:00',NULL,NULL,1,0,NULL,NULL,NULL,'A4F30A1B-5EB9-11DF-A648-37A07F9C90FB',NULL,NULL);

--
-- Table structure for table `visit`
--

DROP TABLE IF EXISTS `visit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visit` (
  `visit_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) NOT NULL,
  `visit_type_id` int(11) NOT NULL,
  `date_started` datetime NOT NULL,
  `date_stopped` datetime DEFAULT NULL,
  `indication_concept_id` int(11) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`visit_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `visit_patient_index` (`patient_id`),
  KEY `visit_type_fk` (`visit_type_id`),
  KEY `visit_location_fk` (`location_id`),
  KEY `visit_creator_fk` (`creator`),
  KEY `visit_voided_by_fk` (`voided_by`),
  KEY `visit_changed_by_fk` (`changed_by`),
  KEY `visit_indication_concept_fk` (`indication_concept_id`),
  CONSTRAINT `visit_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `visit_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `visit_indication_concept_fk` FOREIGN KEY (`indication_concept_id`) REFERENCES `concept` (`concept_id`),
  CONSTRAINT `visit_location_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`),
  CONSTRAINT `visit_patient_fk` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`),
  CONSTRAINT `visit_type_fk` FOREIGN KEY (`visit_type_id`) REFERENCES `visit_type` (`visit_type_id`),
  CONSTRAINT `visit_voided_by_fk` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visit`
--


--
-- Table structure for table `visit_attribute`
--

DROP TABLE IF EXISTS `visit_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visit_attribute` (
  `visit_attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `visit_id` int(11) NOT NULL,
  `attribute_type_id` int(11) NOT NULL,
  `value_reference` text NOT NULL,
  `uuid` char(38) NOT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `voided` tinyint(1) NOT NULL DEFAULT '0',
  `voided_by` int(11) DEFAULT NULL,
  `date_voided` datetime DEFAULT NULL,
  `void_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`visit_attribute_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `visit_attribute_visit_fk` (`visit_id`),
  KEY `visit_attribute_attribute_type_id_fk` (`attribute_type_id`),
  KEY `visit_attribute_creator_fk` (`creator`),
  KEY `visit_attribute_changed_by_fk` (`changed_by`),
  KEY `visit_attribute_voided_by_fk` (`voided_by`),
  CONSTRAINT `visit_attribute_attribute_type_id_fk` FOREIGN KEY (`attribute_type_id`) REFERENCES `visit_attribute_type` (`visit_attribute_type_id`),
  CONSTRAINT `visit_attribute_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `visit_attribute_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `visit_attribute_visit_fk` FOREIGN KEY (`visit_id`) REFERENCES `visit` (`visit_id`),
  CONSTRAINT `visit_attribute_voided_by_fk` FOREIGN KEY (`voided_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visit_attribute`
--


--
-- Table structure for table `visit_attribute_type`
--

DROP TABLE IF EXISTS `visit_attribute_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visit_attribute_type` (
  `visit_attribute_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `datatype` varchar(255) DEFAULT NULL,
  `datatype_config` text,
  `preferred_handler` varchar(255) DEFAULT NULL,
  `handler_config` text,
  `min_occurs` int(11) NOT NULL,
  `max_occurs` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`visit_attribute_type_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `visit_attribute_type_creator_fk` (`creator`),
  KEY `visit_attribute_type_changed_by_fk` (`changed_by`),
  KEY `visit_attribute_type_retired_by_fk` (`retired_by`),
  CONSTRAINT `visit_attribute_type_changed_by_fk` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `visit_attribute_type_creator_fk` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `visit_attribute_type_retired_by_fk` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visit_attribute_type`
--


--
-- Table structure for table `visit_type`
--

DROP TABLE IF EXISTS `visit_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visit_type` (
  `visit_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `changed_by` int(11) DEFAULT NULL,
  `date_changed` datetime DEFAULT NULL,
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (`visit_type_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `visit_type_creator` (`creator`),
  KEY `visit_type_changed_by` (`changed_by`),
  KEY `visit_type_retired_by` (`retired_by`),
  CONSTRAINT `visit_type_changed_by` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `visit_type_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `visit_type_retired_by` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visit_type`
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
