ALTER TABLE user_share
  DROP FOREIGN KEY FK_user_share__application_user_id;
ALTER TABLE user_share
  DROP PRIMARY KEY;
ALTER TABLE user_share
  ADD CONSTRAINT `FK_user_share__application_user_id`
FOREIGN KEY (user_id) REFERENCES application_user (id);
ALTER TABLE user_share
  ADD COLUMN id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY;
