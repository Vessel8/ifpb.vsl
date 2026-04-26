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
-- Inserting fictitious Course data
-- ------------------------------------------------
INSERT INTO COURSE (COURSE_NAME, DURATION_HOURS, PRICE, RELEASE_DATE) VALUES
                                                                          ('Python Fundamentals', 40, 299.99, '2024-03-15'),
                                                                          ('Web Development Bootcamp', 120, 599.99, '2024-04-01'),
                                                                          ('Data Science Mastery', 80, 799.99, '2024-03-20'),
                                                                          ('Digital Marketing Essentials', 35, 249.99, '2024-04-10'),
                                                                          ('Mobile App Development', 90, 649.99, '2024-05-05'),
                                                                          ('Cybersecurity Basics', 45, 349.99, '2024-03-25'),
                                                                          ('Cloud Computing with AWS', 60, 899.99, '2024-04-15'),
                                                                          ('UI/UX Design Principles', 50, 449.99, '2024-05-01'),
                                                                          ('Project Management Professional', 35, 399.99, '2024-03-30'),
                                                                          ('Artificial Intelligence Fundamentals', 70, 999.99, '2024-04-20');

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

insert TBL_ROLE (role) values ('ROLE_ADMIN');
insert TBL_ROLE (role) values ('ROLE_USER');

--     Senha: senhasegura
insert TBL_USER (user_name, user_password, user_fk_role)
       values ('admin', '$2a$12$9mctnda5IHgP3J7h3zeJ1.ETog0ecyVfjNym9Jlvy9EgCwNu4x5u.', 1 );

--  Senha: SenhaSegura
insert TBL_USER (user_name, user_password, user_fk_role)
       values ('arthur', '$2a$12$qUiN/j9lwwblQvyBm7BX5OIINdNEvqyQBH7c.WCjIUbPfUqjSq3cu', 2 );