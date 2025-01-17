-- Criar o banco de dados
CREATE DATABASE cinema;

-- Usar o banco de dados criado
USE cinema;

-- Criar a tabela Cinema
CREATE TABLE Cinema (
    id INT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    local VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- Criar a tabela Filme com id AUTO_INCREMENT
CREATE TABLE Filme (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    duracao_s BIGINT NOT NULL,
    PRIMARY KEY (id)
);

-- Criar a tabela Sala
CREATE TABLE Sala (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    capacidade INT NOT NULL,
    cinema_id INT DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cinema_id) REFERENCES Cinema(id)
);

-- Criar a tabela Sessao
CREATE TABLE Sessao (
    id INT NOT NULL AUTO_INCREMENT,
    sala_id INT DEFAULT NULL,
    filme_id INT DEFAULT NULL,
    dataHora DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (sala_id) REFERENCES Sala(id),
    FOREIGN KEY (filme_id) REFERENCES Filme(id)
);

-- Criar a tabela VendaIngresso
CREATE TABLE VendaIngresso (
    id INT NOT NULL AUTO_INCREMENT,
    sessao_id INT NOT NULL,
    quantidade INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (sessao_id) REFERENCES Sessao(id)
);

-- Inserindo cinemas
INSERT INTO Cinema (id, nome, local)
VALUES
    (1, 'Anapolis', 'Rua Principal, 123'),
    (2, 'Bom Filme', 'Avenida Central, 456');

-- Inserindo filmes (id gerado automaticamente)
INSERT INTO Filme (nome, duracao_s)
VALUES
    ('Aventura Espacial', 7200), -- 2 horas
    ('Terror na Noite', 5400),  -- 1 hora e 30 minutos
    ('Comédia em Família', 6000); -- 1 hora e 40 minutos

-- Inserindo salas
INSERT INTO Sala (id, nome, capacidade, cinema_id)
VALUES
    (1, 'Sala 1', 100, 1), -- Sala no cinema Anapolis
    (2, 'Sala 2', 120, 1),
    (3, 'Sala 1', 80, 2), -- Sala no cinema Bom Filme
    (4, 'Sala 2', 150, 2);

-- Inserindo sessões (id gerado automaticamente)
INSERT INTO Sessao (sala_id, filme_id, dataHora)
VALUES
    (1, 1, '2024-11-24 14:00:00'), -- Filme na Sala 1 do cinema Anapolis
    (2, 2, '2024-11-24 16:30:00'), -- Filme na Sala 2 do cinema Anapolis
    (3, 3, '2024-11-24 18:00:00'), -- Filme na Sala 1 do cinema Bom Filme
    (4, 1, '2024-11-24 20:00:00'); -- Filme na Sala 2 do cinema Bom Filme
