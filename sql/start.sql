-- database

CREATE SCHEMA `horse_go` DEFAULT CHARACTER SET utf8;

--user table
CREATE TABLE `horse_go`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `wallet` DOUBLE NOT NULL DEFAULT 0,
  `role` ENUM('USER', 'ADMIN', 'BOOKMAKER') NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



--
INSERT INTO users (name, surname, login, password, role) VALUES ('Admin', 'Admin', 'Admin', MD5('AdminPassword'), 'ADMIN');
INSERT INTO users (name, surname, login, password, role) VALUES ('Bookmaker', 'Bookmaker', 'Bookmaker', MD5('BookmakerPassword'), 'BOOKMAKER');



-- horses table
CREATE TABLE `horse_go`.`horses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45),
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- horses
INSERT INTO horses (name) VALUES ('Jasper');
INSERT INTO horses (name) VALUES ('Jasmine');
INSERT INTO horses (name) VALUES ('Hurricane');
INSERT INTO horses (name) VALUES ('Eastern wind');
INSERT INTO horses (name) VALUES ('Lightning');
INSERT INTO horses (name) VALUES ('Fire');
INSERT INTO horses (name) VALUES ('Jumper');

--bets table
CREATE TABLE `horse_go`.`bets` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `game_id` INT NOT NULL,
  `horse_id` INT NOT NULL,
  `bet_amount` DOUBLE NOT NULL,
  `bet_type` ENUM('VICTORY', 'FIRST_THREE', 'OUTSIDER', 'EXACT_PLACE') NOT NULL,
  `bet_coefficient` DOUBLE NOT NULL,
  `user_win` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- games table
CREATE TABLE `horse_go`.`games` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `game_played` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- game bets table
CREATE TABLE `horse_go`.`game_bets` (
  `game_id` INT NOT NULL,
  `horse_id` INT NOT NULL,
  `bet_type` ENUM('VICTORY', 'FIRST_THREE', 'OUTSIDER', 'LAST_THREE') NOT NULL DEFAULT 'VICTORY',
  `bet_coefficient` DOUBLE NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- game horse table
CREATE TABLE `horse_go`.`game_horses` (
  `game_id` INT NOT NULL,
  `horse_id` INT NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

