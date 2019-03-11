# --- !Ups

CREATE TABLE `ward`(
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64),
  `active` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1,
  PRIMARY KEY `pk_ward_id` (`id`),
  UNIQUE KEY `uk_ward_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  ('Terrain Ward'),
  ('Founders Ward');
 
CREATE TABLE `organization`(
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64),
  `active` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1,
  PRIMARY KEY `pk_organization_id` (`id`),
  UNIQUE KEY `uk_organization_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `organization` (`name`) VALUES
  ('Youth Mia Maids'),
  ('Youth Laurels'),
  ('Youth Teachers'),
  ('Youth Priests'),
  ('Adult Participant');

CREATE TABLE `gender`(
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64),
  `active` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1,
  PRIMARY KEY `pk_gender_id` (`id`),
  UNIQUE KEY `uk_gender_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `gender` (`name`, `active`) VALUES
  ('Unknown', 0),
  ('Male', 1),
  ('Female', 1);

CREATE TABLE `registration` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ward_id` INT(10) UNSIGNED NOT NULL,
  `organization_id` INT(10) UNSIGNED NOT NULL,
  `child_name_first` VARCHAR(64) NOT NULL DEFAULT "",
  `child_name_middle` VARCHAR(64),
  `child_name_last` VARCHAR(64) NOT NULL DEFAULT "",
  `child_dob` VARCHAR(16) NOT NULL default "",
  `child_gender_id` INT(10) UNSIGNED NOT NULL DEFAULT 1,
  `parent_mother_name_first` VARCHAR(64) NOT NULL DEFAULT "",
  `parent_mother_name_last` VARCHAR(64) NOT NULL DEFAULT "",
  `parent_mother_contact_phone` VARCHAR(64) NOT NULL DEFAULT "",
  `parent_mother_contact_email` VARCHAR(64) NOT NULL DEFAULT "",
  `parent_father_name_first` VARCHAR(64) NOT NULL DEFAULT "",
  `parent_father_name_last` VARCHAR(64) NOT NULL DEFAULT "",
  `parent_father_contact_phone` VARCHAR(64) NOT NULL DEFAULT "",
  `parent_father_contact_email` VARCHAR(64) NOT NULL DEFAULT "",
  `emergency_name_first` VARCHAR(64) NOT NULL DEFAULT "",
  `emergency_name_last` VARCHAR(64) NOT NULL DEFAULT "",
  `emergency_contact_phone` VARCHAR(64) NOT NULL DEFAULT "",
  `medical_conditions_provider_details` TEXT,
  `medical_conditions_epilepsy` VARCHAR(3) NOT NULL DEFAULT "",
  `medical_conditions_highbloodpressure` VARCHAR(3) NOT NULL DEFAULT "",
  `medical_conditions_heartdisease` VARCHAR(3) NOT NULL DEFAULT "",
  `medical_conditions_lungdisease` VARCHAR(3) NOT NULL DEFAULT "",
  `medical_conditions_orthopedic` VARCHAR(3) NOT NULL DEFAULT "",
  `medical_medications_regular_details` TEXT,
  `medical_medications_asneeded_details` TEXT,
  `medical_alergies_medications` VARCHAR(3) NOT NULL DEFAULT "",
  `medical_alergies_pollen` VARCHAR(3) NOT NULL DEFAULT "",
  `medical_alergies_toxins` VARCHAR(3) NOT NULL DEFAULT "",
  `medical_alergies_foods` VARCHAR(3) NOT NULL DEFAULT "",
  `medical_alergies_orthopedic` VARCHAR(3) NOT NULL DEFAULT "",
  `medical_hospitalization_5years` VARCHAR(3) NOT NULL DEFAULT "",
  `medical_hospitalization_details` TEXT,
  `medical_mental_concerns` VARCHAR(3) NOT NULL DEFAULT "",
  `medical_mental_concerns_details` TEXT,
  `medical_fitness_level` VARCHAR(20) NOT NULL DEFAULT "",
  `medical_other_concerns_details` TEXT,
  `medical_consent_legal_given` VARCHAR(3) NOT NULL DEFAULT "",
  `ip` VARCHAR(128) NOT NULL DEFAULT "",  
  `ips` VARCHAR(256) NOT NULL DEFAULT "",  
  `user_agent` VARCHAR(256) NOT NULL DEFAULT "",  
  `context` TEXT,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `k_registration_ward_id` (`ward_id`),
  KEY `k_registration_organization_id` (`organization_id`),
  CONSTRAINT `fk_registration_ward_id` FOREIGN KEY (`ward_id`) REFERENCES `ward` (`id`),
  CONSTRAINT `fk_registration_organization_id` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`),
  CONSTRAINT `fk_registration_child_gender_id` FOREIGN KEY (`child_gender_id`) REFERENCES `gender` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# --- !Downs
DROP TABLE IF EXISTS `registration`;  
DROP TABLE IF EXISTS `organization`;  
DROP TABLE IF EXISTS `ward`;  
DROP TABLE IF EXISTS `gender`;  
