CREATE TABLE `payout` (
  `id`            BIGINT(20)      NOT NULL AUTO_INCREMENT,
  `amount`        DECIMAL(19, 15) NOT NULL,
  `approval_date` DATETIME                 DEFAULT NULL,
  `issue_date`    DATETIME        NOT NULL,
  `user_id`       BIGINT(20)      NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_payout__user_id` (`user_id`),
  CONSTRAINT `FK_payout__user_id` FOREIGN KEY (`user_id`) REFERENCES `application_user` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;