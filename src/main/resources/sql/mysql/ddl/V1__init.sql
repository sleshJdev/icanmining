CREATE TABLE `application_user` (
  `id`       BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(255) NOT NULL,
  `username` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_application_user_username` (`username`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `role` (
  `id`   BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `application_user_roles` (
  `application_user_id` BIGINT(20) NOT NULL,
  `roles_id`            BIGINT(20) NOT NULL,
  PRIMARY KEY (`application_user_id`, `roles_id`),
  KEY `FK_application_user_roles__role_id` (`roles_id`),
  KEY `FK_application_user_roles__application_user_id` (`application_user_id`),
  CONSTRAINT `FK_application_user_roles__application_user_id` FOREIGN KEY (`application_user_id`) REFERENCES `application_user` (`id`),
  CONSTRAINT `FK_application_user_roles__role_id` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE user_share
(
  last_contribution_date DATETIME        NOT NULL,
  share                  DECIMAL(19, 15) NOT NULL,
  user_id                BIGINT          NOT NULL
    PRIMARY KEY,
  CONSTRAINT `FK_user_share__application_user_id`
  FOREIGN KEY (user_id) REFERENCES application_user (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `exchange_rate` (
  `id`       BIGINT(20)      NOT NULL AUTO_INCREMENT,
  `coin`     VARCHAR(255)    NOT NULL,
  `currency` VARCHAR(255)    NOT NULL,
  `rate`     DECIMAL(19, 15) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `wallet` (
  `id`      BIGINT(20)      NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(34)     NOT NULL,
  `balance` DECIMAL(19, 15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_wallet_address` (`address`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;