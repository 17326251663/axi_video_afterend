/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.24 : Database - axi_video
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`axi_video` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `axi_video`;

/*Table structure for table `aaxi_video_visitor` */

DROP TABLE IF EXISTS `aaxi_video_visitor`;

CREATE TABLE `aaxi_video_visitor` (
  `vid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ip` varchar(50) NOT NULL COMMENT '访问ip',
  `url` varchar(200) NOT NULL COMMENT '访问的url',
  `visitortime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '访问时间',
  `address` varchar(50) DEFAULT NULL COMMENT '访问地',
  `visitorclass` varchar(100) DEFAULT NULL COMMENT '访问方法',
  `args` varchar(200) DEFAULT NULL COMMENT '携带参数',
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `aaxi_video_visitor` */

/*Table structure for table `axxi_type` */

DROP TABLE IF EXISTS `axxi_type`;

CREATE TABLE `axxi_type` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(20) DEFAULT NULL COMMENT '类型名称',
  `turl` varchar(40) DEFAULT NULL COMMENT '类型的链接关键字',
  `realurl` varchar(200) DEFAULT NULL COMMENT '真实路径',
  `type` varchar(10) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `axxi_type` */

insert  into `axxi_type`(`tid`,`typeName`,`turl`,`realurl`,`type`) values (1,'电视剧','intfname=hollywood_tv','https://list.video.qq.com/fcgi-bin/list_select_cgi?callback=jsonp22&otype=json&version=10000&platform=5&intfname=hollywood_tv','1'),(2,'电影','intfname=hollywood_movie','https://list.video.qq.com/fcgi-bin/list_select_cgi?callback=jsonp22&otype=json&version=10000&platform=5&intfname=hollywood_movie','2'),(3,'动漫','intfname=vip_cartoon_h5','https://list.video.qq.com/fcgi-bin/list_select_cgi?callback=jsonp22&otype=json&version=20304&platform=5&intfname=vip_cartoon_h5','3'),(4,'综艺','intfname=hollywood_variety','https://list.video.qq.com/fcgi-bin/list_select_cgi?callback=jsonp22&otype=json&version=10000&platform=5&intfname=hollywood_variety','10'),(5,'少儿','intfname=m_vip_children','https://list.video.qq.com/fcgi-bin/list_select_cgi?callback=jsonp22&otype=json&version=10000&platform=5&intfname=m_vip_children','106'),(6,'纪录片','intfname=hollywood_doco','https://list.video.qq.com/fcgi-bin/list_select_cgi?callback=jsonp22&otype=json&version=10000&intfname=hollywood_doco&platform=5','9'),(7,'音乐','intfname=vip_music_h5','https://list.video.qq.com/fcgi-bin/list_select_cgi?callback=jsonp22&otype=json&version=20304&intfname=vip_music_h5&platform=5','22'),(8,'知识','intfname=h5_pay_for_knowledge','https://list.video.qq.com/fcgi-bin/list_select_cgi?callback=jsonp22&otype=json&version=10000&intfname=h5_pay_for_knowledge&platform=5','27');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
