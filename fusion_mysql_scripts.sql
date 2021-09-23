
SELECT * FROM accounts;

SELECT sum(balance) FROM accounts
WHERE account_type = 2;

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

# Get user's total weekly and monthly expenses combined
SELECT mex.name, mex.amount
FROM monthly_expenses mex
JOIN users_monthly_expenses umex
ON mex.monthly_expense_id = umex.monthly_expense_id
JOIN users u
UNION
SELECT wex.name, wex.amount
FROM weekly_expenses wex
JOIN users_weekly_expenses uwex
ON wex.weekly_expense_id = uwex.weekly_expense_id
JOIN users u
ON uwex.user_id = u.user_id
WHERE u.user_id = 1;

# Get all accounts with account type desriptions from account_types
SELECT a.*, at.*
FROM accounts a
JOIN account_types at
ON a.account_type = at.account_types_id;

