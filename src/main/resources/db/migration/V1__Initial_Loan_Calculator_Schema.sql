START TRANSACTION;

CREATE TABLE loan
(
    id                         BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount                     DECIMAL(15, 2) NOT NULL,
    annual_interest_percentage DECIMAL(5, 2) NOT NULL,
    number_of_months           INT NOT NULL,
    created_at                 TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE installment
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_id          BIGINT NOT NULL,
    month_number     INT NOT NULL,
    payment_amount   DECIMAL(15, 2) NOT NULL,
    principal_amount DECIMAL(15, 2) NOT NULL,
    interest_amount  DECIMAL(15, 2) NOT NULL,
    balance_owned    DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (loan_id) REFERENCES loan (id)
);

COMMIT;
