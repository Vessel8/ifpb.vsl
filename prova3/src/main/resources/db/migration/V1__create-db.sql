-- ------------------------------------------------
-- Create table Course
-- ------------------------------------------------
CREATE TABLE COURSE (
                        COURSE_ID   BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                        COURSE_NAME VARCHAR(250) NOT NULL,
                        DURATION_HOURS INT NOT NULL,
                        PRICE DECIMAL(10,2) NOT NULL,
                        RELEASE_DATE DATE NOT NULL
);

-- ------------------------------------------------
-- Constraints FK & Unique
-- ------------------------------------------------
ALTER TABLE COURSE
    ADD CONSTRAINT UNIQ_COURSE_NAME
        UNIQUE (COURSE_NAME);


-- ------------------------------------------------
-- User and role tables
-- ------------------------------------------------

CREATE TABLE TBL_USER
(
    user_id         BIGINT                NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_name       VARCHAR(250)          NOT NULL,
    user_password   VARCHAR(250)          NOT NULL,
    user_fk_role    BIGINT                NOT NULL
);

CREATE TABLE TBL_ROLE
(
    role_id         BIGINT                NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role            VARCHAR(250)          NOT NULL
);

ALTER TABLE TBL_ROLE
    ADD CONSTRAINT UNIQ_ROLE
        UNIQUE (role);

ALTER TABLE TBL_USER
    ADD CONSTRAINT FK_TO_ROLE_ID
        FOREIGN KEY (user_fk_role) REFERENCES TBL_ROLE (role_id);