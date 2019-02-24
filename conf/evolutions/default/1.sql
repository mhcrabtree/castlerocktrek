# --- !Ups

CREATE TABLE `ward`(
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64),
  PRIMARY KEY (id),
  UNIQUE KEY (`name`)
);

INSERT INTO `ward` (`name`) VALUES
  ('Canyon Ridge Ward'),
  ('Castle Pines Ward'),
  ('Castle Rock Ward'),
  ('Crystal Valley Ward'),
  ('Foothills Ward'),
  ('Lone Tree Ward'),
  ('Meadows Ward'),
  ('Park Meadows Ward'),
  ('Perry Park Ward'),
  ('Plum Creek Ward'),
  ('Terrain Ward');
  
# --- !Downs
DROP TABLE IF EXISTS `ward`;  
