DELIMITER $$
CREATE PROCEDURE CreateIncome(
    src VARCHAR(100), 
    amount DECIMAL(10,2),
    weekly_interval INT,
    account_id INT,
    user_id INT
)
BEGIN
    DECLARE l_income_id INT DEFAULT 0;
    
    START TRANSACTION;
    -- Insert income data
    INSERT INTO income
	(income_source, income_amount, income_weekly_interval)
	VALUES (src, amount, weekly_interval);
        
    -- get income id
    SET l_income_id = LAST_INSERT_ID();
    
    -- insert income/account and user/income data
    IF l_income_id > 0 THEN
		INSERT INTO income_accounts
		(income_id, account_id)
		VALUES(l_income_id, account_id);
        
        INSERT INTO users_income
		(user_id, income_id)
		VALUES(user_id, l_income_id);
        -- commit
        COMMIT;
     ELSE
	ROLLBACK;
    END IF;
END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE CreateMonthlyIncome(
    src VARCHAR(100), 
    amount DECIMAL(10,2),
    day_of_month_recvd INT,
    account_id INT,
    user_id INT
)
BEGIN
    DECLARE l_monthly_income_id INT DEFAULT 0;
    
    START TRANSACTION;
    -- Insert monthly income data
    INSERT INTO monthly_income
	(monthly_income_source, monthly_income_amount, day_of_month_recvd)
	VALUES (src, amount, day_of_month_recvd);
        
    -- get income id
    SET l_monthly_income_id = LAST_INSERT_ID();
    
    -- insert income/account data
    IF l_monthly_income_id > 0 THEN
		INSERT INTO monthly_income_accounts
		(monthly_income_id, account_id)
		VALUES(l_monthly_income_id, account_id);
        
		INSERT INTO users_monthly_income
		(user_id, monthly_income_id)
		VALUES(user_id, l_monthly_income_id);

        -- commit
        COMMIT;
     ELSE
	ROLLBACK;
    END IF;
END$$
DELIMITER ;