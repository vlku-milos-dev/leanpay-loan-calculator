START TRANSACTION;

CREATE TABLE loan
(
    id                         SERIAL PRIMARY KEY,
    amount                     NUMERIC(15, 2) NOT NULL,
    annual_interest_percentage NUMERIC(5, 2)  NOT NULL,
    number_of_months           INT            NOT NULL,
    created_at                 TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE installment
(
    id               SERIAL PRIMARY KEY,
    loan_id          INT            NOT NULL,
    month_number     INT            NOT NULL,
    payment_amount   NUMERIC(15, 2) NOT NULL,
    principal_amount NUMERIC(15, 2) NOT NULL,
    interest_amount  NUMERIC(15, 2) NOT NULL,
    balance_owned    NUMERIC(15, 2) NOT NULL,
    FOREIGN KEY (loan_id) REFERENCES loan (id)
);

COMMIT;