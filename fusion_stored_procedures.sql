DELIMITER $$
CREATE PROCEDURE CreateIncome(
    src VARCHAR(100), 
    amount DECIMAL(10,2),
    weekly_interval INT,
    account_id INT
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
    
    -- insert income/account data
    IF l_income_id > 0 THEN
		INSERT INTO income_accounts
		(income_id, account_id)
		VALUES(l_income_id, account_id);
        -- commit
        COMMIT;
     ELSE
	ROLLBACK;
    END IF;
END$$
DELIMITER ;