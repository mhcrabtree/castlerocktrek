# --- !Ups

CREATE TABLE `ward`(
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64),
  PRIMARY KEY `pk_ward_id` (`id`),
  UNIQUE KEY `uk_ward_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
 
CREATE TABLE `organization`(
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64),
  PRIMARY KEY `pk_organization_id` (`id`),
  UNIQUE KEY `uk_organization_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `organization` (`name`) VALUES
  ('Mia Maids'),
  ('Laurels'),
  ('Teachers'),
  ('Priests');

CREATE TABLE `registration` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ward_id` INT(10) UNSIGNED NOT NULL,
  `organization_id` INT(10) UNSIGNED NOT NULL,
  `child_name_first` VARCHAR(64) NOT NULL default "",
  `child_name_middle` VARCHAR(64),
  `child_name_last` VARCHAR(64) NOT NULL default "",
  `child_dob` VARCHAR(16) NOT NULL default "",
  `child_gender` VARCHAR(16) NOT NULL default "",
  PRIMARY KEY (`id`),
  KEY `k_registration_ward_id` (`ward_id`),
  KEY `k_registration_organization_id` (`organization_id`),
  CONSTRAINT `fk_registration_ward_id` FOREIGN KEY (`ward_id`) REFERENCES `ward` (`id`),
  CONSTRAINT `fk_registration_organization_id` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# --- !Downs
DROP TABLE IF EXISTS `registration`;  
DROP TABLE IF EXISTS `organization`;  
DROP TABLE IF EXISTS `ward`;  
