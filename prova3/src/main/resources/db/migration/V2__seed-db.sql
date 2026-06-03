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
                                                                          ('Project Management Professional', 45, 399.99, '2024-03-30'),
                                                                          ('Artificial Intelligence Fundamentals', 70, 999.99, '2024-04-20');

-- ------------------------------------------------
-- Inserting not so fictitious user data
-- ------------------------------------------------
insert TBL_ROLE (role) values ('ROLE_ADMIN');
insert TBL_ROLE (role) values ('ROLE_USER');

--     Senha: senhasegura
insert TBL_USER (user_name, user_password, user_fk_role)
       values ('Admin', '$2a$12$9mctnda5IHgP3J7h3zeJ1.ETog0ecyVfjNym9Jlvy9EgCwNu4x5u.', 1 );

--  Senha: SenhaSegura
insert TBL_USER (user_name, user_password, user_fk_role)
       values ('Arthur', '$2a$12$qUiN/j9lwwblQvyBm7BX5OIINdNEvqyQBH7c.WCjIUbPfUqjSq3cu', 2 );