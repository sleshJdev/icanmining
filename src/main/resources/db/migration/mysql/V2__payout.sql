CREATE TABLE `payout` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,15) NOT NULL,
  `approval_date` datetime DEFAULT NULL,
  `issue_date` datetime NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_payout__user_id` (`user_id`),
  CONSTRAINT `FK_payout__user_id` FOREIGN KEY (`user_id`) REFERENCES `application_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;