SET NAMES 'utf8mb4';

CREATE TABLE IF NOT EXISTS filmes (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      titulo VARCHAR(255) NOT NULL,
    data_lancamento DATE NOT NULL
    ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

DELETE FROM filmes;

INSERT INTO filmes (titulo, data_lancamento) VALUES
                                                 ('O Poderoso Chefão', '1972-03-24'),
                                                 ('O Senhor dos Anéis: O Retorno do Rei', '2003-12-17'),
                                                 ('Batman: O Cavaleiro das Trevas', '2008-07-18'),
                                                 ('Parasita', '2019-05-30'),
                                                 ('Interestelar', '2014-11-07'),
                                                 ('Clube da Luta', '1999-10-15'),
                                                 ('A Origem', '2010-07-16'),
                                                 ('Pulp Fiction: Tempo de Violência', '1994-10-14'),
                                                 ('Cidade de Deus', '2002-08-30'),
                                                 ('Toy Story', '1995-11-22');

