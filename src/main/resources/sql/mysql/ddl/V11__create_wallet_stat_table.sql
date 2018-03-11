CREATE TABLE wallet_stat
(
  id        BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  balance   DECIMAL(19, 15) NOT NULL,
  date      DATETIME        NOT NULL,
  json      LONGTEXT        NOT NULL,
  wallet_id BIGINT          NULL,
  KEY FK_wallet_stat_wallet_id (wallet_id),
  CONSTRAINT FK_wallet_stat_wallet_id
  FOREIGN KEY (wallet_id) REFERENCES wallet (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


