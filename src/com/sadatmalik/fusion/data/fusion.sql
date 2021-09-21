-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema fusion
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema fusion
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fusion` DEFAULT CHARACTER SET utf8 ;
USE `fusion` ;

-- -----------------------------------------------------
-- Table `fusion`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `timestamp` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`income`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`income` (
  `income_id` INT NOT NULL AUTO_INCREMENT,
  `income_amount` INT NOT NULL,
  `income_source` VARCHAR(100) NOT NULL,
  `income_weekly_interval` INT NOT NULL COMMENT 'Zero for one off income',
  `timestamp` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`income_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`users_income`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`users_income` (
  `users_income_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `income_id` INT NOT NULL,
  `active` TINYINT NOT NULL,
  PRIMARY KEY (`users_income_id`),
  INDEX `fk_users_income_user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_users_income_income_id_idx` (`income_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_income_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `fusion`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_income_income_id`
    FOREIGN KEY (`income_id`)
    REFERENCES `fusion`.`income` (`income_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`monthly_income`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`monthly_income` (
  `monthly_income_id` INT NOT NULL AUTO_INCREMENT,
  `monthly_income_amount` INT NOT NULL,
  `monthly_income_source` INT NOT NULL,
  `day_of_month_recvd` INT NOT NULL,
  `timestamp` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`monthly_income_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`users_monthly_income`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`users_monthly_income` (
  `users_monthly_income_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `monthly_income_id` INT NOT NULL,
  `active` TINYINT NOT NULL,
  PRIMARY KEY (`users_monthly_income_id`),
  INDEX `fk_users_monthly_income_monthly_income_idx` (`monthly_income_id` ASC) VISIBLE,
  INDEX `fk_users_monthly_income_user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_monthly_income_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `fusion`.`users` (`user_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_monthly_income_monthly_income`
    FOREIGN KEY (`monthly_income_id`)
    REFERENCES `fusion`.`monthly_income` (`monthly_income_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`account_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`account_types` (
  `account_types_id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`account_types_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`accounts` (
  `account_id` INT NOT NULL AUTO_INCREMENT,
  `bank` VARCHAR(45) NOT NULL,
  `account_type` INT NOT NULL,
  `balance` INT NOT NULL,
  `timestamp` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`account_id`),
  INDEX `fk_accounts_account_type_idx` (`account_type` ASC) VISIBLE,
  CONSTRAINT `fk_accounts_account_type`
    FOREIGN KEY (`account_type`)
    REFERENCES `fusion`.`account_types` (`account_types_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`users_accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`users_accounts` (
  `users_accounts_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`users_accounts_id`),
  INDEX `fk_users_accounts_user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_users_accounts_account_id_idx` (`account_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_accounts_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `fusion`.`users` (`user_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_accounts_account_id`
    FOREIGN KEY (`account_id`)
    REFERENCES `fusion`.`accounts` (`account_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`debt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`debt` (
  `debt_id` INT NOT NULL AUTO_INCREMENT,
  `lender` VARCHAR(45) NOT NULL,
  `total_owed` DECIMAL(10,2) NOT NULL,
  `total_borrowed` DECIMAL(10,2) NOT NULL,
  `day_of_month_paid` INT NOT NULL,
  `interest_rate` DECIMAL(10,2) NOT NULL,
  `date_borrowed` DATETIME NOT NULL,
  `intial_term_months` INT NOT NULL,
  `timestamp` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`debt_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`users_debt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`users_debt` (
  `users_debt_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `debt_id` INT NOT NULL,
  PRIMARY KEY (`users_debt_id`),
  INDEX `fk_users_debt_user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_users_debt_debt_id_idx` (`debt_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_debt_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `fusion`.`users` (`user_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_debt_debt_id`
    FOREIGN KEY (`debt_id`)
    REFERENCES `fusion`.`debt` (`debt_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`debt_accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`debt_accounts` (
  `debt_account_id` INT NOT NULL AUTO_INCREMENT,
  `account_id` INT NOT NULL,
  `debt_id` INT NOT NULL,
  PRIMARY KEY (`debt_account_id`),
  INDEX `fk_debt_debt_id_idx` (`debt_id` ASC) VISIBLE,
  INDEX `fk_debt_accounts_account_id_idx` (`account_id` ASC) VISIBLE,
  CONSTRAINT `fk_debt_accounts_debt_id`
    FOREIGN KEY (`debt_id`)
    REFERENCES `fusion`.`debt` (`debt_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_debt_accounts_account_id`
    FOREIGN KEY (`account_id`)
    REFERENCES `fusion`.`accounts` (`account_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`weekly_expenses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`weekly_expenses` (
  `weekly_expense_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `amount` DECIMAL(10,2) NOT NULL,
  `times_per_week` INT NOT NULL,
  `weekly_interval` INT NOT NULL,
  PRIMARY KEY (`weekly_expense_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`expense_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`expense_category` (
  `expense_category_id` INT NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`expense_category_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`weekly_expenses_expense_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`weekly_expenses_expense_category` (
  `weekly_expenses_expense_category_id` INT NOT NULL AUTO_INCREMENT,
  `weekly_expense_id` INT NOT NULL,
  `expense_category_id` INT NOT NULL,
  PRIMARY KEY (`weekly_expenses_expense_category_id`),
  INDEX `fk_weekly_expenses_expense_category_weekly_expense_id_idx` (`weekly_expense_id` ASC) VISIBLE,
  INDEX `fk_weekly_expenses_expense_category_weekly_expense_category_idx` (`expense_category_id` ASC) VISIBLE,
  CONSTRAINT `fk_weekly_expenses_expense_category_weekly_expense_id`
    FOREIGN KEY (`weekly_expense_id`)
    REFERENCES `fusion`.`weekly_expenses` (`weekly_expense_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_weekly_expenses_expense_category_weekly_expense_category_id`
    FOREIGN KEY (`expense_category_id`)
    REFERENCES `fusion`.`expense_category` (`expense_category_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`monthly_expenses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`monthly_expenses` (
  `monthly_expense_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `amount` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`monthly_expense_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`monthly_expenses_expense_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`monthly_expenses_expense_category` (
  `monthly_expenses_expense_category_id` INT NOT NULL AUTO_INCREMENT,
  `monthly_expense_id` INT NOT NULL,
  `expense_category_id` INT NOT NULL,
  PRIMARY KEY (`monthly_expenses_expense_category_id`),
  INDEX `fk_monthly_expenses_expense_category_monthly_expense_id_idx` (`monthly_expense_id` ASC) VISIBLE,
  INDEX `fk_monthly_expenses_expense_category_expense_category_id_idx` (`expense_category_id` ASC) VISIBLE,
  CONSTRAINT `fk_monthly_expenses_expense_category_monthly_expense_id`
    FOREIGN KEY (`monthly_expense_id`)
    REFERENCES `fusion`.`monthly_expenses` (`monthly_expense_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_monthly_expenses_expense_category_expense_category_id`
    FOREIGN KEY (`expense_category_id`)
    REFERENCES `fusion`.`expense_category` (`expense_category_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`weekly_expenses_accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`weekly_expenses_accounts` (
  `weekly_expenses_accounts_id` INT NOT NULL AUTO_INCREMENT,
  `weekly_expense_id` INT NOT NULL,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`weekly_expenses_accounts_id`),
  INDEX `fk_weekly_expenses_accounts_weekly_expense_id_idx` (`weekly_expense_id` ASC) VISIBLE,
  INDEX `fkweekly_expenses_accounts_account_id_idx` (`account_id` ASC) VISIBLE,
  CONSTRAINT `fk_weekly_expenses_accounts_weekly_expense_id`
    FOREIGN KEY (`weekly_expense_id`)
    REFERENCES `fusion`.`weekly_expenses` (`weekly_expense_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fkweekly_expenses_accounts_account_id`
    FOREIGN KEY (`account_id`)
    REFERENCES `fusion`.`accounts` (`account_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`monthly_expenses_accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`monthly_expenses_accounts` (
  `monthly_expenses_accounts_id` INT NOT NULL AUTO_INCREMENT,
  `monthly_expense_id` INT NOT NULL,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`monthly_expenses_accounts_id`),
  INDEX `fk_monthly_expenses_accounts_monthly_expense_id_idx` (`monthly_expense_id` ASC) VISIBLE,
  INDEX `fkmonthly_expenses_accounts_account_id_idx` (`account_id` ASC) VISIBLE,
  CONSTRAINT `fk_monthly_expenses_accounts_monthly_expense_id`
    FOREIGN KEY (`monthly_expense_id`)
    REFERENCES `fusion`.`monthly_expenses` (`monthly_expense_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fkmonthly_expenses_accounts_account_id`
    FOREIGN KEY (`account_id`)
    REFERENCES `fusion`.`accounts` (`account_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`users_weekly_expenses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`users_weekly_expenses` (
  `users_weekly_expenses_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `weekly_expense_id` INT NOT NULL,
  PRIMARY KEY (`users_weekly_expenses_id`),
  INDEX `fk_users_weekly_expenses_user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_users_weekly_expenses_weekly_expense_id_idx` (`weekly_expense_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_weekly_expenses_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `fusion`.`users` (`user_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_weekly_expenses_weekly_expense_id`
    FOREIGN KEY (`weekly_expense_id`)
    REFERENCES `fusion`.`weekly_expenses` (`weekly_expense_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`users_monthly_expenses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`users_monthly_expenses` (
  `users_monthly_expenses_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `monthly_expense_id` INT NOT NULL,
  PRIMARY KEY (`users_monthly_expenses_id`),
  INDEX `fk_users_monthly_expenses_user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_users_monthly_expenses_monthly_expense_id_idx` (`monthly_expense_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_monthly_expenses_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `fusion`.`users` (`user_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_monthly_expenses_monthly_expense_id`
    FOREIGN KEY (`monthly_expense_id`)
    REFERENCES `fusion`.`monthly_expenses` (`monthly_expense_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`goals`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`goals` (
  `goal_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `target` DECIMAL(10,2) NOT NULL,
  `acheived` DECIMAL(10,2) NOT NULL,
  `current_contribution` DECIMAL(10,2) NOT NULL,
  `contribute_weekly_monthly` TINYINT NOT NULL,
  PRIMARY KEY (`goal_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`users_goals`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`users_goals` (
  `users_goals_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `goal_id` INT NOT NULL,
  PRIMARY KEY (`users_goals_id`),
  INDEX `fk_users_goals_user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_users_goals_goal_id_idx` (`goal_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_goals_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `fusion`.`users` (`user_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_goals_goal_id`
    FOREIGN KEY (`goal_id`)
    REFERENCES `fusion`.`goals` (`goal_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fusion`.`goals_accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fusion`.`goals_accounts` (
  `goals_accounts_id` INT NOT NULL AUTO_INCREMENT,
  `goal_id` INT NOT NULL,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`goals_accounts_id`),
  INDEX `fk_goals_accounts_goal_id_idx` (`goal_id` ASC) VISIBLE,
  INDEX `fk_goals_accounts_account_id_idx` (`account_id` ASC) VISIBLE,
  CONSTRAINT `fk_goals_accounts_goal_id`
    FOREIGN KEY (`goal_id`)
    REFERENCES `fusion`.`goals` (`goal_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_goals_accounts_account_id`
    FOREIGN KEY (`account_id`)
    REFERENCES `fusion`.`accounts` (`account_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
