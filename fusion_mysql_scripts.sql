
SELECT * FROM accounts;

SELECT sum(balance) FROM accounts
WHERE account_type = 2;

# Get accounts for user
SELECT a.* , a_t.description
FROM accounts a
JOIN account_types a_t
ON a.account_type = a_t.account_types_id
JOIN users_accounts ua
ON a.account_id = ua.account_id
JOIN users u
ON ua.user_id = u.user_id
WHERE u.user_id = 1;

# Query a user’s total monthly income?
SELECT sum(mi.monthly_income_amount)
FROM monthly_income mi
JOIN users_monthly_income umi
ON mi.monthly_income_id = umi.monthly_income_id
JOIN users u
ON umi.user_id = u.user_id
WHERE u.user_id = 2;

# Get users
SELECT * FROM users;

# Get user's total monthly expenses
SELECT mex.*
FROM monthly_expenses mex
JOIN users_monthly_expenses umex
ON mex.monthly_expense_id = umex.monthly_expense_id
JOIN users u
ON umex.user_id = u.user_id
WHERE u.user_id = 1;

# Get user's total weekly expenses
SELECT wex.*
FROM weekly_expenses wex
JOIN users_weekly_expenses uwex
ON wex.weekly_expense_id = uwex.weekly_expense_id
JOIN users u
ON uwex.user_id = u.user_id
WHERE u.user_id = 1;

# Get debts for a user
SELECT d.*, (d.total_borrowed * d.interest_rate / 100 / 12) AS monthly
FROM debt d
JOIN users_debt ud
ON d.debt_id = ud.debt_id
JOIN users u
ON ud.user_id = u.user_id
WHERE u.user_id = 1;

# Get debts with monthly payment calculated
SELECT *, (total_borrowed * interest_rate / 100 / 12) AS monthly
FROM fusion.debt;

# Get debts for user with monthly payment calculated
SELECT d.*, (d.total_borrowed * d.interest_rate / 100 / 12) AS monthly
FROM debt d
JOIN users_debt ud
ON d.debt_id = ud.debt_id
JOIN users u
ON ud.user_id = u.user_id
WHERE u.user_id = '1';

# Get user's total monthly expenses and debt payments combined
SELECT mex.name, mex.amount
FROM monthly_expenses mex
JOIN users_monthly_expenses umex
ON mex.monthly_expense_id = umex.monthly_expense_id
JOIN users u
UNION
SELECT d.lender, (d.total_borrowed * d.interest_rate / 100 / 12) AS monthly
FROM debt d
JOIN users_debt ud
ON d.debt_id = ud.debt_id
JOIN users u
ON ud.user_id = u.user_id
WHERE u.user_id = '1';

# Get all accounts with account type desriptions from account_types
SELECT a.*, at.*
FROM accounts a
JOIN account_types at
ON a.account_type = at.account_types_id;

# Get total debt for a user
SELECT sum(d.total_borrowed) AS total_debt
FROM debt d
JOIN users_debt ud
ON d.debt_id = ud.debt_id
JOIN users u
ON ud.user_id = u.user_id
WHERE u.user_id = '1';

# Get total monthly debt payment for a user
SELECT sum(d.total_borrowed * d.interest_rate / 100 / 12) AS total_monthly_payment
FROM debt d
JOIN users_debt ud
ON d.debt_id = ud.debt_id
JOIN users u
ON ud.user_id = u.user_id
WHERE u.user_id = '1';

