-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: jobdb
-- ------------------------------------------------------
-- Server version	9.1.0

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `iAccountId` int NOT NULL AUTO_INCREMENT,
  `sEmail` varchar(100) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `sPassword` varchar(255) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `iRoleId` int DEFAULT NULL,
  `dCreatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `bStatus` tinyint(1) DEFAULT '1',
  `sUsername` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  PRIMARY KEY (`iAccountId`),
  UNIQUE KEY `sEmail` (`sEmail`),
  UNIQUE KEY `sUsername` (`sUsername`),
  KEY `iRoleId` (`iRoleId`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`iRoleId`) REFERENCES `role` (`iRoleId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'admin@example.com','$2a$10$rXYvmqX0SYCHuP9s7HdH9..mO2fMPDBsvQU959IIkvhqKK6a7X.5u',1,'2025-07-29 23:12:00',1,'admin'),(2,'mikhaco.jsc@gmail.com','$2a$10$UiBY5BJ/3I52e76onrLB/.bs7FEGWen.Y4qzFdzY8BfQprmeHxjRi',2,'2025-07-29 23:12:00',1,'minhkhangco'),(3,'nguyenvanan@gmail.com','$2a$10$jdKmEY9TEgx7.TcvVB/7wuZ8UpD6lqiXcb/hy0z2QFkK7scA9K2nm',3,'2025-07-29 00:00:00',1,'vanan'),(4,'tranthibinh@gmail.com','$2a$10$8Tj00qtBKDAkY3wmkpRhGuN2./mJRJssaeCnTwHEtD3UWl63R7rAC',3,'2025-07-31 17:18:32',1,'thibinh'),(5,'lequangminh@gmail.com','$2a$10$1MEJu/1tnDrn6NCcstCRWuzeir5/zwlRt1nxuw42VDzTKWaZK8jzG',3,'2025-07-31 17:18:32',1,'quangminh'),(6,'fptfsoft@gmail.com','$2a$10$I9UlVcfS9xWPX6iKmO481OcFPRyI6oBNiigeU78yf0Y1/m83BFwqK',2,'2025-07-31 17:18:32',1,'fpt'),(9,'thenthuvu@gmail.com','$2a$10$OOK1WqLJUstxWXQmucBjJeu5pt5HShhDS1UzJ2iQ7uA.8h85SdW/6',3,'2025-08-06 20:16:35',1,'thanhthu'),(10,'ames@gmail.com','$2a$10$fxNNc9EUY70IDXzSBPbG8O1QXoOb8yaa.fCAUPWhpDtnOPUVuqmgG',2,'2025-08-07 00:00:00',1,'ames'),(11,'hyperi@gmail.com','$2a$10$soB.hqiNoftdM4us9r/kBuG.u.J.KeCwTGyk3/T4R/asqAg9E5VXu',2,'2025-08-07 00:00:00',1,'hyperi');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application` (
  `iCandidateId` int NOT NULL,
  `iJobPostId` int NOT NULL,
  `dApplyDate` date DEFAULT NULL,
  `sStatus` varchar(50) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `iResumeId` int DEFAULT NULL,
  PRIMARY KEY (`iCandidateId`,`iJobPostId`),
  KEY `iJobPostId` (`iJobPostId`),
  KEY `iResumeId` (`iResumeId`),
  CONSTRAINT `application_ibfk_1` FOREIGN KEY (`iCandidateId`) REFERENCES `candidate` (`iCandidateId`),
  CONSTRAINT `application_ibfk_2` FOREIGN KEY (`iJobPostId`) REFERENCES `job` (`iJobPostId`),
  CONSTRAINT `application_ibfk_3` FOREIGN KEY (`iResumeId`) REFERENCES `resume` (`iResumeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (1,1,'2025-07-29','Đã nộp',1),(1,2,'2025-07-29','Đã duyệt',1),(4,2,'2025-08-07','Đã duyệt',3);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidate`
--

DROP TABLE IF EXISTS `candidate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidate` (
  `iCandidateId` int NOT NULL AUTO_INCREMENT,
  `sFullName` varchar(100) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `sPhone` varchar(20) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `sGender` varchar(10) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `dBirthDate` date DEFAULT NULL,
  `sAvatar` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `sAddress` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `iAccountId` int DEFAULT NULL,
  PRIMARY KEY (`iCandidateId`),
  KEY `iAccountId` (`iAccountId`),
  CONSTRAINT `candidate_ibfk_1` FOREIGN KEY (`iAccountId`) REFERENCES `account` (`iAccountId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidate`
--

LOCK TABLES `candidate` WRITE;
/*!40000 ALTER TABLE `candidate` DISABLE KEYS */;
INSERT INTO `candidate` VALUES (1,'Nguyễn Văn An','0912345678','Nam','1996-04-15','https://res.cloudinary.com/depjn7fdk/image/upload/v1753721283/springweb/zrqiemlw5vxjt4bpflym.png','Quận Cầu Giấy, Hà Nội',3),(2,'Trần Thị Bình','0987654321','Nữ','1998-08-20','https://res.cloudinary.com/depjn7fdk/image/upload/v1753721276/springweb/jqhsnb0en0nim7ykz1c8.png','Quận Gò Vấp, TP. Hồ Chí Minh',4),(3,'Lê Quang Minh','0909123456','Nam','1995-12-01','https://res.cloudinary.com/depjn7fdk/image/upload/v1753721263/springweb/rhqey9l6qc2dlot4o9sq.png','Quận Hải Châu, Đà Nẵng',5),(4,'Vũ Trần Thanh Thư','0372368820',NULL,'2004-04-15','https://res.cloudinary.com/depjn7fdk/image/upload/v1754487808/springweb/tgm1nwg0zlcs0tmrohfj.png','Nhà Bè, TP. Hồ Chí Minh',9);
/*!40000 ALTER TABLE `candidate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employer`
--

DROP TABLE IF EXISTS `employer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employer` (
  `iEmployerId` int NOT NULL AUTO_INCREMENT,
  `sCompanyName` varchar(100) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `sRepresentativeName` varchar(100) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `sRepresentativeTitle` varchar(50) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `sAvatar` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `sTaxCode` varchar(20) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `sWorkEnvImg1` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `sWorkEnvImg2` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `sWorkEnvImg3` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `bVerified` tinyint(1) DEFAULT '0',
  `sVerifyDoc` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `iAccountId` int DEFAULT NULL,
  PRIMARY KEY (`iEmployerId`),
  KEY `iAccountId` (`iAccountId`),
  CONSTRAINT `employer_ibfk_1` FOREIGN KEY (`iAccountId`) REFERENCES `account` (`iAccountId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employer`
--

LOCK TABLES `employer` WRITE;
/*!40000 ALTER TABLE `employer` DISABLE KEYS */;
INSERT INTO `employer` VALUES (1,'Công ty Cổ Phần Cơ Điện Tử Minh Khang','Hoàng Minh Dũng','Giám đốc','https://res.cloudinary.com/depjn7fdk/image/upload/v1753624727/springweb/d0zi0lzcmsjfibw7sdkr.png','0106066000','https://res.cloudinary.com/depjn7fdk/image/upload/v1753624730/springweb/idnpkc6mzz7cqqhzh7yw.jpg','https://res.cloudinary.com/depjn7fdk/image/upload/v1753624733/springweb/bxskvve0vncqdc9mb04f.jpg','https://res.cloudinary.com/depjn7fdk/image/upload/v1753624735/springweb/iylqkox2qv9ccqoyvaho.jpg',1,'https://res.cloudinary.com/depjn7fdk/image/upload/v1754484658/springweb/hx7tyw6bbquqxhjrmfs7.pdf',2),(3,'CÔNG TY TNHH PHẦN MỀM FPT','Nguyễn Văn A','Tổng giám đốc','https://res.cloudinary.com/depjn7fdk/image/upload/v1752664309/fptsoftware_mvzank.png','0101601092','https://res.cloudinary.com/depjn7fdk/image/upload/v1753602872/springweb/ugrw4dgpmevc1doxsntz.jpg','https://res.cloudinary.com/depjn7fdk/image/upload/v1753602874/springweb/gqhoz5en7k9iexb3nxts.jpg','https://res.cloudinary.com/depjn7fdk/image/upload/v1753602878/springweb/ix37mzjokulisjqrqegt.jpg',1,'https://res.cloudinary.com/depjn7fdk/image/upload/v1753602880/springweb/rdit7kyuee5yt84p2tel.png',6),(4,' CÔNG TY CỔ PHẦN ĐÀO TẠO AMES SÀI GÒN','Hoàng Thị Thu Hà','Giám đốc','https://res.cloudinary.com/depjn7fdk/image/upload/v1754582718/springweb/qmjp7ocswqpgzlsgxnqo.png','0317595860','https://res.cloudinary.com/depjn7fdk/image/upload/v1754582720/springweb/srhrwxfkrdfehvvqadhz.avif','https://res.cloudinary.com/depjn7fdk/image/upload/v1754582722/springweb/gppldx6bnrpamlgagjtk.jpg','https://res.cloudinary.com/depjn7fdk/image/upload/v1754582724/springweb/lgap0d0gpufp8rgscui7.jpg',1,'https://res.cloudinary.com/depjn7fdk/image/upload/v1754582729/springweb/mlnapis2xavmjsccdc4o.png',10),(5,'CÔNG TY CỔ PHẦN THƯƠNG MẠI VÀ CÔNG NGHỆ HYPERI','Phùng Thị Phương Anh','Giám đốc','https://res.cloudinary.com/depjn7fdk/image/upload/v1754583680/springweb/g1w7qg3j9wvfdjxyftrd.jpg','0109762013','https://res.cloudinary.com/depjn7fdk/image/upload/v1754583682/springweb/ffl6nixxry4mypkno4mt.jpg','https://res.cloudinary.com/depjn7fdk/image/upload/v1754583691/springweb/wwstqgcdbp04ws0drlll.jpg','https://res.cloudinary.com/depjn7fdk/image/upload/v1754583692/springweb/qqeje752oxjxvc2n9khw.jpg',1,'https://res.cloudinary.com/depjn7fdk/image/upload/v1754583694/springweb/a7vehaqdtazzken9z97y.png',1);
/*!40000 ALTER TABLE `employer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `followcompany`
--

DROP TABLE IF EXISTS `followcompany`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `followcompany` (
  `iCandidateId` int NOT NULL,
  `iEmployerId` int NOT NULL,
  `dFollowedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`iCandidateId`,`iEmployerId`),
  KEY `iEmployerId` (`iEmployerId`),
  CONSTRAINT `followcompany_ibfk_1` FOREIGN KEY (`iCandidateId`) REFERENCES `candidate` (`iCandidateId`),
  CONSTRAINT `followcompany_ibfk_2` FOREIGN KEY (`iEmployerId`) REFERENCES `employer` (`iEmployerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `followcompany`
--

LOCK TABLES `followcompany` WRITE;
/*!40000 ALTER TABLE `followcompany` DISABLE KEYS */;
INSERT INTO `followcompany` VALUES (1,1,'2025-07-29 23:17:20'),(1,3,'2025-08-05 16:56:41');
/*!40000 ALTER TABLE `followcompany` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job` (
  `iJobPostId` int NOT NULL AUTO_INCREMENT,
  `iEmployerId` int DEFAULT NULL,
  `iMajorId` int DEFAULT NULL,
  `iLevelId` int DEFAULT NULL,
  `iLocationId` int DEFAULT NULL,
  `sJobTitle` varchar(100) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `sWorkAddress` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `iMinSalary` int DEFAULT NULL,
  `iMaxSalary` int DEFAULT NULL,
  `iQuantity` int DEFAULT NULL,
  `sDescription` text COLLATE utf8mb4_vietnamese_ci,
  `sCandidateRequirement` text COLLATE utf8mb4_vietnamese_ci,
  `sRelatedSkills` text COLLATE utf8mb4_vietnamese_ci,
  `sBenefits` text COLLATE utf8mb4_vietnamese_ci,
  `sWorkTime` varchar(100) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `dPostedDate` date DEFAULT NULL,
  `dDeadline` date DEFAULT NULL,
  `bStatus` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`iJobPostId`),
  KEY `iEmployerId` (`iEmployerId`),
  KEY `iMajorId` (`iMajorId`),
  KEY `iLevelId` (`iLevelId`),
  KEY `iLocationId` (`iLocationId`),
  CONSTRAINT `job_ibfk_1` FOREIGN KEY (`iEmployerId`) REFERENCES `employer` (`iEmployerId`),
  CONSTRAINT `job_ibfk_2` FOREIGN KEY (`iMajorId`) REFERENCES `major` (`iMajorId`),
  CONSTRAINT `job_ibfk_3` FOREIGN KEY (`iLevelId`) REFERENCES `joblevel` (`iLevelId`),
  CONSTRAINT `job_ibfk_4` FOREIGN KEY (`iLocationId`) REFERENCES `location` (`iLocationId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (1,3,1,2,1,'Nhân viên hỗ trợ kỹ thuật part-time','123 Lê Lợi, Q.1, TP.HCM',2000000,10000000,3,'Hỗ trợ xử lý sự cố kỹ thuật văn phòng','Biết cài đặt phần mềm, giao tiếp tốt','Tin học văn phòng, teamwork','Thưởng năng suất, môi trường năng động','Sáng T2-T6 (8h - 12h)',NULL,'2025-08-13',1),(2,1,4,3,2,'Nhân Viên Kế Toán Kho','BT2-20, Khu nhà ở cho CBCS Cục B42, B57, Tổng cục V, Bộ công an, Tân Triều, Thanh Trì, Hà Nội',12000000,15000000,2,'- Nhận hàng , kiểm tra hàng theo biên bản, cất hàng vào kho theo vị trí quy định để tiện cho lấy hàng bán đi\r\n- Nhập hàng về vào phần mềm kế toán Fast\r\n- Tiếp nhận đơn hàng xuất kho, nhặt hàng theo yêu cầu xuất hàng\r\n- Cập nhật tồn kho + seri xuất hàng lên data\r\n- Sắp xếp giao hàng: xe công ty giao, gọi xe ngoài, 247, ....\r\n- Theo dõi quá trình lấy hàng và giao hàng\r\n- Lập báo cáo nhập kho hàng tuần\r\n- Lập báo cáo công việc trong tuần\r\n- Báo cáo hàng tồn kho hàng tháng','- Giới tính: Nam\r\n- Tốt nghiệp Cao đẳng trở lên chuyên ngành Kế toán, Kinh tế, Tài chính hoặc liên quan.\r\n- Đã có kinh nghiệm làm kế toán kho từ 1 năm trở lên.\r\n- Ưu tiên ứng viên có kinh nghiệm làm kho\r\n- Hiểu biết về quy trình xuất – nhập kho, kiểm kê, lập báo cáo kho.','- Thành thạo tin học văn phòng: Word, Excel.\r\n- Sử dụng thành thạo phần mềm kế toán Fast.','- Thu nhập: 12- 15 triệu tùy theo năng lực; đánh giá lương 1 năm/lần; Thưởng Tết theo KPI năm, kết quả kinh doanh của công ty từ 2 tháng lương +++\r\n- Phụ cấp: Xăng xe, Ăn trưa, Cước điện thoại\r\n- Thiết bị làm việc: Được cấp Điện thoại, Máy tính\r\n- Quyền lợi: Bảo hiểm xã hội, Bảo hiểm sức khỏe, Khám sức khỏe định kỳ, Team building, Du lịch hàng năm, Thưởng tháng 13, Thưởng hiệu quả làm việc',' Từ Thứ Hai đến sáng Thứ Bảy, từ 08h00 - 17h00 (nghỉ trưa 1 tiếng)','2025-07-31','2025-08-13',1),(3,4,13,2,1,'Trợ Giảng Tiếng Anh Part-time','38/6 Tân Cảng, Phường 25, Quận Bình Thạnh, TP.HCM.',NULL,NULL,19,'Môn giảng dạy: Stem, Kỹ Năng Sống, Tư duy toán, Anh Văn.\r\n\r\nCấp: từ Mầm non đến Trung học cơ sở.\r\n\r\nHỗ trợ giáo viên giảng dạy các bạn học sinh\r\n\r\n\r\n\r\nĐăng ký để nhận lịch phỏng vấn:\r\n\r\n- 38/6 Tân Cảng, P.Thạnh Mỹ Tây, TP.HCM\r\n\r\n- D5, Lê Thị Riêng Khu Nhà ở Thới An, phường Thới An, TPHCM\r\n\r\n- 11 đường 21, KDC Gia Hòa, p. Phước Long, Tp Thủ Đức\r\n\r\n- 60 Đường 4A, Bình Trị Đông B, Bình Tân','- Yêu trẻ và ngành giáo dục.\r\n\r\n- Có bằng sư phạm là lợi thế.\r\n\r\n- Trung thực, nhiệt tình, năng động, ham học hỏi.\r\n\r\n- Có tinh thần hợp tác, tích cực.\r\n\r\n- Kỹ năng giao tiếp, thuyết trình tốt.','Tiếng Anh, Sư phạm','Được làm việc trong môi trường năng động, chuyên nghiệp; có cơ hội phát triển bản thân và thăng tiến lên vị trí cấp quản lý.\r\n\r\n- Có cơ hội học hỏi và nâng cao chuyên môn giảng dạy.\r\n- Lương tính theo tiết học ( mỗi tiết 30 phút): 40.000VND','Tự do',NULL,'2025-08-13',1),(4,5,18,2,2,'Etsy Graphic Designer (Remote Part-Time)','Tại nhà',4000000,5000000,1,'- Thiết kế sản phẩm in ấn và bộ hình ảnh listing phục vụ cho thị trường thương mại điện tử quốc tế Etsy,\nđặc biệt là các sản phẩm personalized.\n- Nhận brief từ bộ phận nghiên cứu sản phẩm và đưa ra định hướng hình ảnh cho ấn phẩm.\n- Thực hiện các công việc khác khi được yêu cầu.','- Có tối thiểu 1 năm kinh nghiệm thiết kế trở lên. Ưu tiên ứng viên đã từng thiết kế sản phẩm phục vụ thị trường quốc tế hoặc thương mại điện tử (đặc biệt là các sản phẩm liên quan đến gỗ và mica).\n- Tốt nghiệp Đại học, chuyên ngành thiết kế đồ họa hoặc các ngành liên quan\n- Sử dụng thành thạo các phần mềm thiết kế: Photoshop, Illustrator, Adobe Premier,...\n- Ưu tiên ứng viên có cách tiếp cận sáng tạo, có năng lực ứng dụng Al vào trong sản phẩm thiết kế\n- Biết lắng nghe và teamwork tốt\n- Có gu thẩm mỹ, tỉ mỉ trong hoàn thiện sản phẩm và yêu nghề\n- Ham học hỏi, chủ động trong công việc và có khả năng làm việc độc lập\n- Sẵn lòng làm việc trong môi trường startup trẻ, cởi mở, thân thiện, thẳng thắn và chính trực','kỹ năng giao tiếp tốt Khả Năng Làm Việc Độc Lập Và Nhóm Kiến Thức Về Thương Mại Điện Tử Kỹ Năng Phân Tích Dữ Liệu Kỹ Năng Quản Lý Sản Phẩm','- Lương: 4.000.000 VNĐ/tháng + thưởng theo hiệu quả công việc\n- Làm việc linh hoạt, môi trường cởi mở, sáng tạo, trẻ trung, có định hướng rõ ràng.\n- Hoạt động teambuilding định kỳ, du lịch thường niên\n- Chăm lo đời sống tinh thần: Sinh nhật, Lễ Tết,...\n- Cơ hội tham gia các khóa học nâng cao chuyên môn và kỹ năng','','2025-08-07','2025-08-15',1);
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `joblevel`
--

DROP TABLE IF EXISTS `joblevel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `joblevel` (
  `iLevelId` int NOT NULL AUTO_INCREMENT,
  `sLevelName` varchar(100) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  PRIMARY KEY (`iLevelId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `joblevel`
--

LOCK TABLES `joblevel` WRITE;
/*!40000 ALTER TABLE `joblevel` DISABLE KEYS */;
INSERT INTO `joblevel` VALUES (1,'Thực tập'),(2,'Nhân viên'),(3,'Trưởng nhóm'),(4,'Quản lý');
/*!40000 ALTER TABLE `joblevel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `iLocationId` int NOT NULL AUTO_INCREMENT,
  `sLocationName` varchar(100) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  PRIMARY KEY (`iLocationId`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'TP. Hồ Chí Minh'),(2,'Hà Nội'),(3,'Đà Nẵng'),(4,'An Giang'),(5,'Bà Rịa - Vũng Tàu'),(6,'Bắc Giang'),(7,'Bắc Kạn'),(8,'Bạc Liêu'),(9,'Bắc Ninh'),(10,'Bến Tre'),(11,'Bình Dương'),(12,'Bình Định'),(13,'Bình Phước'),(14,'Bình Thuận'),(15,'Cà Mau'),(16,'Cần Thơ'),(17,'Cao Bằng'),(18,'Đắk Lắk'),(19,'Đắk Nông'),(20,'Điện Biên'),(21,'Đồng Nai'),(22,'Đồng Tháp'),(23,'Gia Lai'),(24,'Hà Giang'),(25,'Hà Nam'),(26,'Hà Tĩnh'),(27,'Hải Dương'),(28,'Hải Phòng'),(29,'Hậu Giang'),(30,'Hòa Bình'),(31,'Hưng Yên'),(32,'Khánh Hòa'),(33,'Kiên Giang'),(34,'Kon Tum'),(35,'Lai Châu'),(36,'Lâm Đồng'),(37,'Lạng Sơn'),(38,'Lào Cai'),(39,'Long An'),(40,'Nam Định'),(41,'Nghệ An'),(42,'Ninh Bình'),(43,'Ninh Thuận'),(44,'Phú Thọ'),(45,'Phú Yên'),(46,'Quảng Bình'),(47,'Quảng Nam'),(48,'Quảng Ngãi'),(49,'Quảng Ninh'),(50,'Quảng Trị'),(51,'Sóc Trăng'),(52,'Sơn La'),(53,'Tây Ninh'),(54,'Thái Bình'),(55,'Thái Nguyên'),(56,'Thanh Hóa'),(57,'Thừa Thiên Huế'),(58,'Tiền Giang'),(59,'Trà Vinh'),(60,'Tuyên Quang'),(61,'Vĩnh Long'),(62,'Vĩnh Phúc'),(63,'Yên Bái');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major`
--

DROP TABLE IF EXISTS `major`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `major` (
  `iMajorId` int NOT NULL AUTO_INCREMENT,
  `sMajorName` varchar(100) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  PRIMARY KEY (`iMajorId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major`
--

LOCK TABLES `major` WRITE;
/*!40000 ALTER TABLE `major` DISABLE KEYS */;
INSERT INTO `major` VALUES (1,'Công nghệ thông tin'),(2,'Marketing'),(3,'Thiết kế'),(4,'Kế toán'),(5,'Quản trị kinh doanh'),(6,'Luật'),(7,'Y đa khoa'),(8,'Dược học'),(9,'Điện - Điện tử'),(10,'Xây dựng'),(11,'Cơ khí'),(12,'Sư phạm Toán'),(13,'Sư phạm Tiếng Anh'),(14,'Ngôn ngữ Anh'),(15,'Quản trị khách sạn'),(16,'Thú y'),(17,'Kỹ thuật phần mềm'),(18,'Thiết kế đồ họa'),(19,'Quản trị nhân sự'),(20,'Kinh doanh quốc tế');
/*!40000 ALTER TABLE `major` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `iNotifId` int NOT NULL AUTO_INCREMENT,
  `iAccountId` int DEFAULT NULL,
  `sContent` text COLLATE utf8mb4_vietnamese_ci,
  `bRead` tinyint(1) DEFAULT '0',
  `dCreatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`iNotifId`),
  KEY `iAccountId` (`iAccountId`),
  CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`iAccountId`) REFERENCES `account` (`iAccountId`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,1,'Chào mừng bạn đến với hệ thống!',0,'2025-07-29 23:17:37'),(22,2,'Chào mừng đến với hệ thống!',1,'2025-08-04 14:43:52'),(23,6,'Chào mừng đến với hệ thống!',1,'2025-08-04 14:43:52'),(24,3,'Chào mừng đến với hệ thống!',1,'2025-08-04 14:43:52'),(25,4,'Chào mừng đến với hệ thống!',0,'2025-08-04 14:43:52'),(26,5,'Chào mừng đến với hệ thống!',0,'2025-08-04 14:43:52'),(27,1,'test',0,'2025-08-04 21:41:08');
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resume`
--

DROP TABLE IF EXISTS `resume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resume` (
  `iResumeId` int NOT NULL AUTO_INCREMENT,
  `sResumeName` varchar(100) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `iCandidateId` int DEFAULT NULL,
  `iMajorId` int DEFAULT NULL,
  `iLevelId` int DEFAULT NULL,
  `sCareerObjective` text COLLATE utf8mb4_vietnamese_ci,
  `sExperience` text COLLATE utf8mb4_vietnamese_ci,
  `sSkills` text COLLATE utf8mb4_vietnamese_ci,
  `sEducation` text COLLATE utf8mb4_vietnamese_ci,
  `sSoftSkills` text COLLATE utf8mb4_vietnamese_ci,
  `sAwards` text COLLATE utf8mb4_vietnamese_ci,
  PRIMARY KEY (`iResumeId`),
  KEY `iCandidateId` (`iCandidateId`),
  KEY `fk_resume_major` (`iMajorId`),
  KEY `fk_resume_joblevel` (`iLevelId`),
  CONSTRAINT `fk_resume_joblevel` FOREIGN KEY (`iLevelId`) REFERENCES `joblevel` (`iLevelId`),
  CONSTRAINT `fk_resume_major` FOREIGN KEY (`iMajorId`) REFERENCES `major` (`iMajorId`),
  CONSTRAINT `resume_ibfk_1` FOREIGN KEY (`iCandidateId`) REFERENCES `candidate` (`iCandidateId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resume`
--

LOCK TABLES `resume` WRITE;
/*!40000 ALTER TABLE `resume` DISABLE KEYS */;
INSERT INTO `resume` VALUES (1,'CV Nguyễn Văn An',1,1,1,'Phát triển bản thân trong lĩnh vực IT','1 năm thực tập tại ABC','PHP, MySQL, JavaScript','ĐH CNTT TP.HCM','Teamwork, Communication','Học bổng 2020'),(2,'CV Tester',1,1,2,'Trở thành chuyên viên kiểm thử phần mềm chuyên nghiệp, phát triển kỹ năng kiểm thử tự động và đảm bảo chất lượng sản phẩm.','1 năm làm việc tại Công ty ABC: Thực hiện kiểm thử chức năng, kiểm thử hồi quy cho hệ thống quản lý bán hàng.\n6 tháng thực tập tại Công ty XYZ: Viết testcase, báo cáo bug, phối hợp với team phát triển.','Manual Testing, Automation Testing với Selenium, viết Test Case, Test Plan, kỹ năng báo cáo bug với Jira, SQL cơ bản.','Đại học Công nghệ thông tin, chuyên ngành Công nghệ phần mềm, tốt nghiệp năm 2024.','Tư duy logic, làm việc nhóm, quản lý thời gian, giao tiếp tốt.','Sinh viên 5 tốt cấp trường 2023, Top 5 cuộc thi IT Test Challenge 2024.'),(3,'CV 1',4,4,2,'abc','abc','abc','abc','abc','abc');
/*!40000 ALTER TABLE `resume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `iReviewId` int NOT NULL AUTO_INCREMENT,
  `iFromAccountId` int DEFAULT NULL,
  `iToAccountId` int DEFAULT NULL,
  `iRating` int DEFAULT NULL,
  `sComment` text COLLATE utf8mb4_vietnamese_ci,
  `dCreatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`iReviewId`),
  KEY `iFromAccountId` (`iFromAccountId`),
  KEY `iToAccountId` (`iToAccountId`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`iFromAccountId`) REFERENCES `account` (`iAccountId`),
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`iToAccountId`) REFERENCES `account` (`iAccountId`),
  CONSTRAINT `review_chk_1` CHECK ((`iRating` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,3,2,5,'Công ty rất chuyên nghiệp, nhiệt tình.','2025-07-29 00:00:00'),(6,4,2,4,'Công ty tốt','2025-08-07 00:00:00'),(7,5,2,5,'Công ty có đãi ngộ tốt','2025-08-07 00:00:00'),(8,2,3,5,'Chăm chỉ','2025-08-07 00:00:00');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `iRoleId` int NOT NULL AUTO_INCREMENT,
  `sRoleName` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  PRIMARY KEY (`iRoleId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_EMPLOYER'),(3,'ROLE_CANDIDATE');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-07 23:38:20
