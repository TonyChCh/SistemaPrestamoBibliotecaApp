CREATE TABLE IF NOT EXISTS USER (
    id          BIGINT          AUTO_INCREMENT  PRIMARY KEY,
    user_name   VARCHAR(100)    NOT NULL,
    password    VARCHAR(1024)   NOT NULL,
    type        VARCHAR(20)     NOT NULL        DEFAULT 'REGULAR',
    UNIQUE KEY uq_users_username (user_name)
);

CREATE TABLE IF NOT EXISTS BOOK (
    id          BIGINT          AUTO_INCREMENT  PRIMARY KEY,
    title       VARCHAR(255)    NOT NULL,
    category    VARCHAR(20)     NOT NULL,
    available   BOOLEAN         NOT NULL        DEFAULT TRUE,
    status      VARCHAR(20)     NOT NULL        DEFAULT 'AVAILABLE',
    UNIQUE KEY uq_book_per_title_category (title, category),
    CONSTRAINT ck_status CHECK (status IN ('AVAILABLE', 'RESERVED', 'LOANED'))
);

CREATE TABLE IF NOT EXISTS LOAN (
    id          BIGINT          AUTO_INCREMENT  PRIMARY KEY,
    user_id     BIGINT          NOT NULL,
    loan_time   DATETIME        NOT NULL        DEFAULT CURRENT_TIMESTAMP,
    return_time DATETIME,
    active      BOOLEAN         NOT NULL        DEFAULT TRUE,
    CONSTRAINT fk_loans_user FOREIGN KEY (user_id) REFERENCES USER(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS LOAN_HISTORY (
    loan_id     BIGINT          NOT NULL,
    book_id     BIGINT          NOT NULL,
    CONSTRAINT pk_loan_history PRIMARY KEY (loan_id, book_id),
    CONSTRAINT fk_loan_history_loan FOREIGN KEY (loan_id) REFERENCES LOAN(id) ON DELETE CASCADE,
    CONSTRAINT fk_loan_history_book FOREIGN KEY (book_id) REFERENCES BOOK(id) ON DELETE CASCADE
);