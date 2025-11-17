/*
SQLyog Ultimate v13.1.1 (32 bit)
MySQL - 8.0.43 : Database - agrodata2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`agrodata2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `agrodata2`;

/*Table structure for table `catalogocultivo` */

DROP TABLE IF EXISTS `catalogocultivo`;

CREATE TABLE `catalogocultivo` (
  `idCultivo` int NOT NULL AUTO_INCREMENT,
  `nombreCultivo` varchar(100) NOT NULL,
  PRIMARY KEY (`idCultivo`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `catalogocultivo` */

insert  into `catalogocultivo`(`idCultivo`,`nombreCultivo`) values 
(1,'chayote'),
(2,'papa'),
(3,'calabaza');

/*Table structure for table `catalogoestado` */

DROP TABLE IF EXISTS `catalogoestado`;

CREATE TABLE `catalogoestado` (
  `idEstado` int NOT NULL AUTO_INCREMENT,
  `nombreEstado` varchar(100) NOT NULL,
  PRIMARY KEY (`idEstado`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `catalogoestado` */

insert  into `catalogoestado`(`idEstado`,`nombreEstado`) values 
(1,'Pendiente'),
(2,'Aceptada'),
(3,'Rechazada'),
(4,'completada');

/*Table structure for table `catalogoriego` */

DROP TABLE IF EXISTS `catalogoriego`;

CREATE TABLE `catalogoriego` (
  `idRiego` int NOT NULL AUTO_INCREMENT,
  `nombreRiego` varchar(100) NOT NULL,
  PRIMARY KEY (`idRiego`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `catalogoriego` */

insert  into `catalogoriego`(`idRiego`,`nombreRiego`) values 
(1,'Temporal'),
(2,'Goteo'),
(3,'Aspercion');

/*Table structure for table `catalogorol` */

DROP TABLE IF EXISTS `catalogorol`;

CREATE TABLE `catalogorol` (
  `idRol` int NOT NULL AUTO_INCREMENT,
  `nombreRol` varchar(100) NOT NULL,
  PRIMARY KEY (`idRol`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `catalogorol` */

insert  into `catalogorol`(`idRol`,`nombreRol`) values 
(1,'Agronomo'),
(2,'Agricultor');

/*Table structure for table `catalogotaller` */

DROP TABLE IF EXISTS `catalogotaller`;

CREATE TABLE `catalogotaller` (
  `idTaller` int NOT NULL AUTO_INCREMENT,
  `nombreTaller` varchar(100) NOT NULL,
  `descripcion` text NOT NULL,
  `idEstado` int DEFAULT '1',
  PRIMARY KEY (`idTaller`),
  KEY `fk_estado` (`idEstado`),
  CONSTRAINT `fk_estado` FOREIGN KEY (`idEstado`) REFERENCES `catalogoestado` (`idEstado`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `catalogotaller` */

/*Table structure for table `costotaller` */

DROP TABLE IF EXISTS `costotaller`;

CREATE TABLE `costotaller` (
  `idTaller` int NOT NULL,
  `costo` decimal(6,2) NOT NULL,
  PRIMARY KEY (`idTaller`),
  CONSTRAINT `costotaller_ibfk_1` FOREIGN KEY (`idTaller`) REFERENCES `catalogotaller` (`idTaller`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `costotaller` */

/*Table structure for table `cultivoporsolicitud` */

DROP TABLE IF EXISTS `cultivoporsolicitud`;

CREATE TABLE `cultivoporsolicitud` (
  `idSolicitud` int NOT NULL,
  `idCultivo` int NOT NULL,
  PRIMARY KEY (`idSolicitud`,`idCultivo`),
  KEY `idCultivo` (`idCultivo`),
  CONSTRAINT `cultivoporsolicitud_ibfk_1` FOREIGN KEY (`idSolicitud`) REFERENCES `solicitudasesoria` (`idSolicitud`) ON DELETE CASCADE,
  CONSTRAINT `cultivoporsolicitud_ibfk_2` FOREIGN KEY (`idCultivo`) REFERENCES `catalogocultivo` (`idCultivo`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `cultivoporsolicitud` */

insert  into `cultivoporsolicitud`(`idSolicitud`,`idCultivo`) values 
(21,1),
(21,2),
(21,3);

/*Table structure for table `plandecultivo` */

DROP TABLE IF EXISTS `plandecultivo`;

CREATE TABLE `plandecultivo` (
  `idPlan` int NOT NULL AUTO_INCREMENT,
  `idSolicitud` int NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date DEFAULT NULL,
  `observaciones` text NOT NULL,
  `idEstado` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`idPlan`),
  KEY `idSolicitud` (`idSolicitud`),
  CONSTRAINT `plandecultivo_ibfk_1` FOREIGN KEY (`idSolicitud`) REFERENCES `solicitudasesoria` (`idSolicitud`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `plandecultivo` */

insert  into `plandecultivo`(`idPlan`,`idSolicitud`,`fechaInicio`,`fechaFin`,`observaciones`,`idEstado`) values 
(10,21,'2025-11-16',NULL,'Plan generado automáticamente. Por favor, complete los detalles.',1);

/*Table structure for table `registrotarea` */

DROP TABLE IF EXISTS `registrotarea`;

CREATE TABLE `registrotarea` (
  `idRegistro` int NOT NULL AUTO_INCREMENT,
  `idTarea` int NOT NULL,
  `imagen` varchar(1000) NOT NULL,
  `comentario` text NOT NULL,
  PRIMARY KEY (`idRegistro`),
  KEY `idTarea` (`idTarea`),
  CONSTRAINT `registrotarea_ibfk_1` FOREIGN KEY (`idTarea`) REFERENCES `tarea` (`idTarea`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `registrotarea` */

/*Table structure for table `reportedesempeño` */

DROP TABLE IF EXISTS `reportedesempeño`;

CREATE TABLE `reportedesempeño` (
  `idReporte` int NOT NULL AUTO_INCREMENT,
  `idPlan` int NOT NULL,
  `fechaGeneracion` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `observaciones` text NOT NULL,
  PRIMARY KEY (`idReporte`),
  KEY `idPlan` (`idPlan`),
  CONSTRAINT `reportedesempeño_ibfk_1` FOREIGN KEY (`idPlan`) REFERENCES `plandecultivo` (`idPlan`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `reportedesempeño` */

/*Table structure for table `reporteplaga` */

DROP TABLE IF EXISTS `reporteplaga`;

CREATE TABLE `reporteplaga` (
  `idReportePlaga` int NOT NULL AUTO_INCREMENT,
  `idPlan` int NOT NULL,
  `fechaReporte` datetime DEFAULT NULL,
  `tipoPlaga` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `descripcion` text NOT NULL,
  `imagen` longtext,
  `idEstado` int NOT NULL,
  PRIMARY KEY (`idReportePlaga`),
  KEY `idPlan` (`idPlan`),
  KEY `idEstado` (`idEstado`),
  CONSTRAINT `reporteplaga_ibfk_1` FOREIGN KEY (`idPlan`) REFERENCES `plandecultivo` (`idPlan`) ON DELETE CASCADE,
  CONSTRAINT `reporteplaga_ibfk_2` FOREIGN KEY (`idEstado`) REFERENCES `catalogoestado` (`idEstado`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `reporteplaga` */

insert  into `reporteplaga`(`idReportePlaga`,`idPlan`,`fechaReporte`,`tipoPlaga`,`descripcion`,`imagen`,`idEstado`) values 
(34,10,'2025-11-17 00:00:00','Hongos en los chayotes','Mi chayote se de muy mal Color Y aparte están partidos y le salen gusanos por donde no crees',NULL,1),
(35,10,'2025-11-17 00:00:00','Hongos en los chayotes','Los hongos de los chayates están con gusanos',NULL,1);

/*Table structure for table `solicitudasesoria` */

DROP TABLE IF EXISTS `solicitudasesoria`;

CREATE TABLE `solicitudasesoria` (
  `idSolicitud` int NOT NULL AUTO_INCREMENT,
  `idAgricultor` int NOT NULL,
  `fechaSolicitud` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usoMaquinaria` tinyint(1) NOT NULL DEFAULT '0',
  `nombreMaquinaria` text,
  `tipoRiego` int NOT NULL,
  `tienePlaga` tinyint(1) NOT NULL DEFAULT '0',
  `descripcionPlaga` text,
  `superficieTotal` decimal(10,2) NOT NULL,
  `direccionTerreno` varchar(100) NOT NULL,
  `motivoAsesoria` text NOT NULL,
  `idEstado` int NOT NULL,
  PRIMARY KEY (`idSolicitud`),
  KEY `idAgricultor` (`idAgricultor`),
  KEY `tipoRiego` (`tipoRiego`),
  KEY `idEstado` (`idEstado`),
  CONSTRAINT `solicitudasesoria_ibfk_1` FOREIGN KEY (`idAgricultor`) REFERENCES `usuario` (`idUsuario`) ON DELETE CASCADE,
  CONSTRAINT `solicitudasesoria_ibfk_2` FOREIGN KEY (`tipoRiego`) REFERENCES `catalogoriego` (`idRiego`),
  CONSTRAINT `solicitudasesoria_ibfk_3` FOREIGN KEY (`idEstado`) REFERENCES `catalogoestado` (`idEstado`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `solicitudasesoria` */

insert  into `solicitudasesoria`(`idSolicitud`,`idAgricultor`,`fechaSolicitud`,`usoMaquinaria`,`nombreMaquinaria`,`tipoRiego`,`tienePlaga`,`descripcionPlaga`,`superficieTotal`,`direccionTerreno`,`motivoAsesoria`,`idEstado`) values 
(21,16,'2025-11-16 20:31:15',0,NULL,3,0,NULL,15.00,' chiapas','Cultivos: chayote, papa, calabaza. Motivo General: necesito sembrera mucho',2);

/*Table structure for table `solicitudtaller` */

DROP TABLE IF EXISTS `solicitudtaller`;

CREATE TABLE `solicitudtaller` (
  `idSolicitudTaller` int NOT NULL AUTO_INCREMENT,
  `idAgricultor` int NOT NULL,
  `idTaller` int NOT NULL,
  `fechaSolicitud` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fechaAplicarTaller` date NOT NULL,
  `fechaFin` date DEFAULT NULL,
  `direccion` varchar(200) NOT NULL,
  `comentario` text NOT NULL,
  `idEstado` int NOT NULL,
  `estadoPagoImagen` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`idSolicitudTaller`),
  KEY `idAgricultor` (`idAgricultor`),
  KEY `idTaller` (`idTaller`),
  KEY `idEstado` (`idEstado`),
  CONSTRAINT `solicitudtaller_ibfk_1` FOREIGN KEY (`idAgricultor`) REFERENCES `usuario` (`idUsuario`) ON DELETE CASCADE,
  CONSTRAINT `solicitudtaller_ibfk_2` FOREIGN KEY (`idTaller`) REFERENCES `catalogotaller` (`idTaller`),
  CONSTRAINT `solicitudtaller_ibfk_3` FOREIGN KEY (`idEstado`) REFERENCES `catalogoestado` (`idEstado`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `solicitudtaller` */

/*Table structure for table `tarea` */

DROP TABLE IF EXISTS `tarea`;

CREATE TABLE `tarea` (
  `idTarea` int NOT NULL AUTO_INCREMENT,
  `idPlan` int NOT NULL,
  `nombreTarea` varchar(100) NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaVencimiento` date NOT NULL,
  `idEstado` int NOT NULL,
  `fechaCompletado` date DEFAULT NULL,
  `idUsuario` int DEFAULT NULL,
  PRIMARY KEY (`idTarea`),
  KEY `idPlan` (`idPlan`),
  KEY `idEstado` (`idEstado`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `tarea_ibfk_1` FOREIGN KEY (`idPlan`) REFERENCES `plandecultivo` (`idPlan`) ON DELETE CASCADE,
  CONSTRAINT `tarea_ibfk_2` FOREIGN KEY (`idEstado`) REFERENCES `catalogoestado` (`idEstado`),
  CONSTRAINT `tarea_ibfk_3` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tarea` */

insert  into `tarea`(`idTarea`,`idPlan`,`nombreTarea`,`fechaInicio`,`fechaVencimiento`,`idEstado`,`fechaCompletado`,`idUsuario`) values 
(20,10,'fertilizacion','2025-11-17','2025-11-19',2,'2025-11-16',16),
(21,10,'se nesesita ensectesida rosearle ','2025-11-17','2025-11-19',1,NULL,16);

/*Table structure for table `tareareporteplaga` */

DROP TABLE IF EXISTS `tareareporteplaga`;

CREATE TABLE `tareareporteplaga` (
  `idTareaReportePlaga` int NOT NULL AUTO_INCREMENT,
  `idReportePlaga` int NOT NULL,
  `idTarea` int NOT NULL,
  PRIMARY KEY (`idTareaReportePlaga`),
  UNIQUE KEY `uk_reporte_tarea` (`idReportePlaga`,`idTarea`),
  KEY `idTareaReportePlaga` (`idTareaReportePlaga`),
  KEY `idTarea` (`idTarea`),
  CONSTRAINT `tareareporteplaga_ibfk_2` FOREIGN KEY (`idReportePlaga`) REFERENCES `reporteplaga` (`idReportePlaga`),
  CONSTRAINT `tareareporteplaga_ibfk_3` FOREIGN KEY (`idTarea`) REFERENCES `tarea` (`idTarea`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tareareporteplaga` */

insert  into `tareareporteplaga`(`idTareaReportePlaga`,`idReportePlaga`,`idTarea`) values 
(1,34,21);

/*Table structure for table `usuario` */

DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `imagenPerfil` varchar(1000) DEFAULT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellidoPaterno` varchar(100) NOT NULL,
  `apellidoMaterno` varchar(100) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `contraseña` varchar(255) NOT NULL,
  `rol` int NOT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `rol` (`rol`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`rol`) REFERENCES `catalogorol` (`idRol`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `usuario` */

insert  into `usuario`(`idUsuario`,`imagenPerfil`,`nombre`,`apellidoPaterno`,`apellidoMaterno`,`telefono`,`correo`,`contraseña`,`rol`) values 
(1,NULL,'Juan','Pérez','González','96130452749','juan.agricultor@email.com','hashed_password_789',2),
(2,NULL,'Roberto','Sánchez','Vázquez','9615554321','roberto.asesor@email.com','hashed_pass_roberto',2),
(3,NULL,'Sofía','Castro','Hernández','9615556789','sofia.admin@email.com','hashed_pass_sofia',2),
(4,NULL,'Miguel','Ramírez','Gómez','9615550000','miguel.jefe@email.com','hashed_pass_miguel',2),
(6,'hola.jpg','Vianey','Ruiz','Lopez','9532402594','ruiz@gmail.com','$2a$10$hgkeZMBGtS1u0CcorMmlKOmcpT8wFVezfOQL/4wxyjajzE8.a.Iui',2),
(7,'hola.jpg','Vianey','Ruiz','Lopez','9532402594','ru@gmail.com','$2a$10$J7MNaYKxiT2zMloGyUOtEON540NRk3DhmBCriuAE/30XL8ccmd8B2',2),
(8,'hola.jpg','Vianey','Ruiz','Lopez','9532402594','juanruiz.com','$2a$10$KrUswxaVfXiqKaFnlL5h1..y1ScsEM.jnRHhO/gcBJiCfNa0gb.6q',2),
(9,NULL,'Miguel','Ramírez','Gómez','9615550000','agronomo@email.com','$2a$10$LoRUa2MnelJWg/IX0LKMWeEduj6vBuy74J0jfp1MD4u7NL6izte5.',1),
(10,'hola.jpg','Vianey','Ruiz','Lopez','9532402594','juanrz.com','$2a$10$kxdG4Q5rSOF90S8ACe8.i.NGcKEZZQGvLIuCdWFTv8jJ14yrPacde',2),
(11,'hola.jpg','Vianey','mortines','Lopez','9532402594','juan.com','$2a$10$js0krSXQKNhRyUU7cRb07udWW1EOo//guiiUHWrvEEW8QsvoeiR1y',2),
(12,'hola.jpg','Vianey','hola','Lopez','9532402594','jua.com','$2a$10$me6sjheKW.KU5Ow12qDdzO6Zsufvqy9K/QeBeERizZRF2JFtaeGNe',2),
(13,'hola.jpg','Gisela','Ruiz','Lopez','9532402594','hola.com','$2a$10$9A9Gs/91a5COFGla5WCAZeSUebNtxpzcFLInQFdDmVslqcaK49fsa',2),
(14,'hola.jpg','Gisela','Ruiz','Lopez','9532402594','hola2.com','$2a$10$4A50RKA5efiXbK8XZu4fG.vJsuFwdfN.jhi2KCLtMT1z.rKooz9.S',2),
(15,'hola.jpg','Vianey','Ruiz','Lopez','1234567890','vianey251203.com','$2a$10$uHaaWCNShe8NxUfGtwrihuN8RAkysDQAIm7cIj/AHFPIOS9T2kolq',2),
(16,'hola.jpg','Vianey','Ruiz','Lopez','9532402594','vianey2203.com','$2a$10$xw1BntWgVcWPMiOVzjY5UedM7094i5pHMN04y/KLEhWvU3.SNZwdO',2),
(17,'hola.jpg','Vianey','Ruiz','Lopez','9532402594','vianey220.com','$2a$10$Grt/mUic6/vhvnaED2dxMuKzJ95GrpgRK/.Z5/e7QUOzV8gm5rYbK',2),
(18,'hola.jpg','Vianey','Ruiz','Lopez','1234567890','vianey251203.com','$2a$10$3HPTRNsk5sRCB.zpj9VRpuPfNLQhjlG6.cED.QlDVKmg0qfF8MTne',2),
(19,'default.jpg','vianey','ruiz','lopez','9565882525','giselavianeyruizlopez@gmail.com','$2a$10$A6TSYfzk4SJkdJ8gNFxgfObUgDZ68BrtxP2uLanH/A/a30G7T4hhG',2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
