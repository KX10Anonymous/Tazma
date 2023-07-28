SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema tazmadb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `tazmadb` ;

-- -----------------------------------------------------
-- Schema tazmadb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tazmadb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `tazmadb` ;

-- -----------------------------------------------------
-- Table `tazmadb`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`address` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`address` (
  `id` BIGINT NOT NULL,
  `area` VARCHAR(255) NULL DEFAULT NULL,
  `house_number` VARCHAR(255) NULL DEFAULT NULL,
  `province` VARCHAR(255) NULL DEFAULT NULL,
  `street_name` VARCHAR(255) NULL DEFAULT NULL,
  `suburb` VARCHAR(255) NULL DEFAULT NULL,
  `user_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK3qqtrufwq0apcofc9dd47xtl8`
    FOREIGN KEY (`user_id`)
    REFERENCES `tazmadb`.`_user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `UK_7rod8a71yep5vxasb0ms3osbg` ON `tazmadb`.`address` (`user_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `tazmadb`.`_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`_user` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`_user` (
  `id` BIGINT NOT NULL,
  `appointment_type` ENUM('CLIENT_VISIT', 'HOUSE_CALL') NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `firstname` VARCHAR(255) NULL DEFAULT NULL,
  `gender` ENUM('FEMALE', 'MALE', 'UNISEX') NULL DEFAULT NULL,
  `lastname` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `phone` VARCHAR(255) NULL DEFAULT NULL,
  `role` ENUM('ADMIN', 'CLIENT', 'STYLIST') NULL DEFAULT NULL,
  `status` ENUM('AVAILABLE', 'UNAVAILABLE') NULL DEFAULT NULL,
  `address_id` BIGINT NULL DEFAULT NULL,
  `role_name` ENUM('ADMIN', 'CLIENT', 'STYLIST') NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKc8ad8fpu3hyob6qpfrl2mpyip`
    FOREIGN KEY (`address_id`)
    REFERENCES `tazmadb`.`address` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `UK_k11y3pdtsrjgy8w9b6q4bjwrx` ON `tazmadb`.`_user` (`email` ASC) VISIBLE;

CREATE UNIQUE INDEX `UK_smv6iv0phvugvruh5upf10h08` ON `tazmadb`.`_user` (`phone` ASC) VISIBLE;

CREATE UNIQUE INDEX `UK_t4kkpv6fhqnav71v2bfqthybx` ON `tazmadb`.`_user` (`address_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `tazmadb`.`styles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`styles` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`styles` (
  `id` BIGINT NOT NULL,
  `gender` ENUM('FEMALE', 'MALE', 'UNISEX') NULL DEFAULT NULL,
  `style_name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tazmadb`.`locations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`locations` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`locations` (
  `id` BIGINT NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `location_name` VARCHAR(255) NULL DEFAULT NULL,
  `longitude` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tazmadb`.`appointments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`appointments` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`appointments` (
  `id` BIGINT NOT NULL,
  `agreed_amount` DOUBLE NULL DEFAULT NULL,
  `appointment_time` DATETIME(6) NULL DEFAULT NULL,
  `appointment_type` TINYINT NULL DEFAULT NULL,
  `client_offer` DOUBLE NULL DEFAULT NULL,
  `counter_offer` DOUBLE NULL DEFAULT NULL,
  `status` ENUM('ACCEPTED', 'CLOSED', 'OPEN') NULL DEFAULT NULL,
  `client_id` BIGINT NULL DEFAULT NULL,
  `location_id` BIGINT NULL DEFAULT NULL,
  `style_id` BIGINT NULL DEFAULT NULL,
  `stylist_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK11ikwei35n5trqloqxvo6nv5j`
    FOREIGN KEY (`style_id`)
    REFERENCES `tazmadb`.`styles` (`id`),
  CONSTRAINT `FK50ss2ewjoq9rmaet804kilacj`
    FOREIGN KEY (`stylist_id`)
    REFERENCES `tazmadb`.`_user` (`id`),
  CONSTRAINT `FKhhf3cpq8bsh8f2oywg342fbxf`
    FOREIGN KEY (`client_id`)
    REFERENCES `tazmadb`.`_user` (`id`),
  CONSTRAINT `FKsorylc1v099qpxex8nfwuvlog`
    FOREIGN KEY (`location_id`)
    REFERENCES `tazmadb`.`locations` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `FKhhf3cpq8bsh8f2oywg342fbxf` ON `tazmadb`.`appointments` (`client_id` ASC) VISIBLE;

CREATE INDEX `FKsorylc1v099qpxex8nfwuvlog` ON `tazmadb`.`appointments` (`location_id` ASC) VISIBLE;

CREATE INDEX `FK11ikwei35n5trqloqxvo6nv5j` ON `tazmadb`.`appointments` (`style_id` ASC) VISIBLE;

CREATE INDEX `FK50ss2ewjoq9rmaet804kilacj` ON `tazmadb`.`appointments` (`stylist_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `tazmadb`.`_user_appointments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`_user_appointments` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`_user_appointments` (
  `user_id` BIGINT NOT NULL,
  `appointments_id` BIGINT NOT NULL,
  CONSTRAINT `FK2nk0c8jo3pt0i88t9nyo572qb`
    FOREIGN KEY (`user_id`)
    REFERENCES `tazmadb`.`_user` (`id`),
  CONSTRAINT `FKamp1gwrsf4u6xorb7qwpjv5b1`
    FOREIGN KEY (`appointments_id`)
    REFERENCES `tazmadb`.`appointments` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `UK_8xs30chm6hqod7ok52u36b1bg` ON `tazmadb`.`_user_appointments` (`appointments_id` ASC) VISIBLE;

CREATE INDEX `FK2nk0c8jo3pt0i88t9nyo572qb` ON `tazmadb`.`_user_appointments` (`user_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `tazmadb`.`posts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`posts` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`posts` (
  `id` BIGINT NOT NULL,
  `created` DATETIME(6) NULL DEFAULT NULL,
  `url` VARCHAR(255) NULL DEFAULT NULL,
  `stylist_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKnrbcjjehna9n4bel40vx0bl82`
    FOREIGN KEY (`stylist_id`)
    REFERENCES `tazmadb`.`_user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `FKnrbcjjehna9n4bel40vx0bl82` ON `tazmadb`.`posts` (`stylist_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `tazmadb`.`_user_posts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`_user_posts` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`_user_posts` (
  `user_id` BIGINT NOT NULL,
  `posts_id` BIGINT NOT NULL,
  CONSTRAINT `FK61yi45y71eud1006d8db56fqd`
    FOREIGN KEY (`user_id`)
    REFERENCES `tazmadb`.`_user` (`id`),
  CONSTRAINT `FKql5m1hi9igd5mq7b3imfcmse4`
    FOREIGN KEY (`posts_id`)
    REFERENCES `tazmadb`.`posts` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `UK_7vycd6ijy8040df3j0bdvj1fb` ON `tazmadb`.`_user_posts` (`posts_id` ASC) VISIBLE;

CREATE INDEX `FK61yi45y71eud1006d8db56fqd` ON `tazmadb`.`_user_posts` (`user_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `tazmadb`.`_user_seq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`_user_seq` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`_user_seq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tazmadb`.`address_seq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`address_seq` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`address_seq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tazmadb`.`appointments_seq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`appointments_seq` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`appointments_seq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tazmadb`.`locations_seq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`locations_seq` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`locations_seq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tazmadb`.`reactions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`reactions` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`reactions` (
  `id` BIGINT NOT NULL,
  `created` DATETIME(6) NULL DEFAULT NULL,
  `react` TINYINT NULL DEFAULT NULL,
  `post_id` BIGINT NULL DEFAULT NULL,
  `user_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKh8b4h9wybhu8tc5w11e8t3krc`
    FOREIGN KEY (`post_id`)
    REFERENCES `tazmadb`.`posts` (`id`),
  CONSTRAINT `FKlecqlhpjrh1n46mw9l5sxlio`
    FOREIGN KEY (`user_id`)
    REFERENCES `tazmadb`.`_user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `FKh8b4h9wybhu8tc5w11e8t3krc` ON `tazmadb`.`reactions` (`post_id` ASC) VISIBLE;

CREATE INDEX `FKlecqlhpjrh1n46mw9l5sxlio` ON `tazmadb`.`reactions` (`user_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `tazmadb`.`posts_reactions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`posts_reactions` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`posts_reactions` (
  `post_id` BIGINT NOT NULL,
  `reactions_id` BIGINT NOT NULL,
  CONSTRAINT `FKb0992ehissidtjtfvsbvhdas7`
    FOREIGN KEY (`reactions_id`)
    REFERENCES `tazmadb`.`reactions` (`id`),
  CONSTRAINT `FKkdmghpci33leh2l8nmfey7phb`
    FOREIGN KEY (`post_id`)
    REFERENCES `tazmadb`.`posts` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `UK_jm5dcn7q22pcr8uimlavlvbpu` ON `tazmadb`.`posts_reactions` (`reactions_id` ASC) VISIBLE;

CREATE INDEX `FKkdmghpci33leh2l8nmfey7phb` ON `tazmadb`.`posts_reactions` (`post_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `tazmadb`.`posts_seq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`posts_seq` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`posts_seq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tazmadb`.`reactions_seq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`reactions_seq` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`reactions_seq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tazmadb`.`resources`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`resources` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`resources` (
  `id` BIGINT NOT NULL,
  `path` VARCHAR(255) NULL DEFAULT NULL,
  `style_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKsbye9rap7ioxkghpl70inth6f`
    FOREIGN KEY (`style_id`)
    REFERENCES `tazmadb`.`styles` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `FKsbye9rap7ioxkghpl70inth6f` ON `tazmadb`.`resources` (`style_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `tazmadb`.`resources_seq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`resources_seq` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`resources_seq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tazmadb`.`reviews`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`reviews` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`reviews` (
  `id` BIGINT NOT NULL,
  `created` DATETIME(6) NULL DEFAULT NULL,
  `rating` TINYINT NULL DEFAULT NULL,
  `appointment_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKfhaj6kqx2pjpn6eambt0pa1nm`
    FOREIGN KEY (`appointment_id`)
    REFERENCES `tazmadb`.`appointments` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `UK_vroos1rdslok15k6q2go3p15` ON `tazmadb`.`reviews` (`appointment_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `tazmadb`.`spring_session`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`spring_session` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`spring_session` (
  `PRIMARY_ID` CHAR(36) NOT NULL,
  `SESSION_ID` CHAR(36) NOT NULL,
  `CREATION_TIME` BIGINT NOT NULL,
  `LAST_ACCESS_TIME` BIGINT NOT NULL,
  `MAX_INACTIVE_INTERVAL` INT NOT NULL,
  `EXPIRY_TIME` BIGINT NOT NULL,
  `PRINCIPAL_NAME` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;

CREATE UNIQUE INDEX `SPRING_SESSION_IX1` ON `tazmadb`.`spring_session` (`SESSION_ID` ASC) VISIBLE;

CREATE INDEX `SPRING_SESSION_IX2` ON `tazmadb`.`spring_session` (`EXPIRY_TIME` ASC) VISIBLE;

CREATE INDEX `SPRING_SESSION_IX3` ON `tazmadb`.`spring_session` (`PRINCIPAL_NAME` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `tazmadb`.`spring_session_attributes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`spring_session_attributes` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`spring_session_attributes` (
  `SESSION_PRIMARY_ID` CHAR(36) NOT NULL,
  `ATTRIBUTE_NAME` VARCHAR(200) NOT NULL,
  `ATTRIBUTE_BYTES` BLOB NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`, `ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK`
    FOREIGN KEY (`SESSION_PRIMARY_ID`)
    REFERENCES `tazmadb`.`spring_session` (`PRIMARY_ID`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `tazmadb`.`styles_seq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`styles_seq` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`styles_seq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tazmadb`.`token`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`token` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`token` (
  `id` INT NOT NULL,
  `expired` BIT(1) NOT NULL,
  `revoked` BIT(1) NOT NULL,
  `token` VARCHAR(255) NULL DEFAULT NULL,
  `token_type` ENUM('BEARER') NULL DEFAULT NULL,
  `user_id` BIGINT NULL DEFAULT NULL,
  `stamp` DATETIME(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKiblu4cjwvyntq3ugo31klp1c6`
    FOREIGN KEY (`user_id`)
    REFERENCES `tazmadb`.`_user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `UK_pddrhgwxnms2aceeku9s2ewy5` ON `tazmadb`.`token` (`token` ASC) VISIBLE;

CREATE INDEX `FKiblu4cjwvyntq3ugo31klp1c6` ON `tazmadb`.`token` (`user_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `tazmadb`.`token_seq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tazmadb`.`token_seq` ;

CREATE TABLE IF NOT EXISTS `tazmadb`.`token_seq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
