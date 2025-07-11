ALTER TABLE transactionals
RENAME TO transactions;

ALTER TABLE transactions
ADD COLUMN description VARCHAR(255);