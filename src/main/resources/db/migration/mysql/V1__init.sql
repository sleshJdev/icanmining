CREATE TABLE `application_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_application_user_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `application_user_roles` (
  `application_user_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  KEY `FK_application_user_roles__role_id` (`roles_id`),
  KEY `FK_aapplication_user_roles__application_user_id` (`application_user_id`),
  CONSTRAINT `FK_aapplication_user_roles__application_user_id` FOREIGN KEY (`application_user_id`) REFERENCES `application_user` (`id`),
  CONSTRAINT `FK_application_user_roles__role_id` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `user_profit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `issue_date` datetime NOT NULL,
  `mining_interval` decimal(19,0) NOT NULL,
  `profit` decimal(19,15) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_profit__user_id` (`user_id`),
  CONSTRAINT `FK_user_profit__user_id` FOREIGN KEY (`user_id`) REFERENCES `application_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `exchange_rate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coin` varchar(255) NOT NULL,
  `currency` varchar(255) NOT NULL,
  `rate` decimal(19,15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `wallet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(34) NOT NULL,
  `balance` decimal(19,15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_wallet_address` (`address`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO wallet (id, address, balance) VALUES (1, '3J85FLJrjyqz8Dsj5F9pJ9kkQkqJsByt15', 0);
INSERT INTO role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (2, 'ROLE_USER');
INSERT INTO application_user (id, password, username) VALUES (1, '$2a$16$c8m4JZESnrkb/laxILUjJOQ6SexRA//ltknKvL4C8rFpaOgwhkCsK', 'admin');
INSERT INTO application_user_roles (application_user_id, roles_id) VALUES (1, 1);