-- Database: rafael

-- DROP DATABASE IF EXISTS rafael;

-- Database: rafael

-- Tabela de pessoas
CREATE TABLE pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(15),
    data_nascimento DATE
);

-- Tabela de usuários
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    id_pessoa INT NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(50) NOT NULL,
    CONSTRAINT fk_pessoa FOREIGN KEY (id_pessoa) REFERENCES pessoa (id) ON DELETE CASCADE
);

-- Tabela de especialidades
CREATE TABLE especialidade (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

-- Tabela de médicos
CREATE TABLE medico (
    id SERIAL PRIMARY KEY,
    id_pessoa INT NOT NULL,
    id_usuario INT NOT NULL,
    crm VARCHAR(20) UNIQUE NOT NULL,
    CONSTRAINT fk_pessoa FOREIGN KEY (id_pessoa) REFERENCES pessoa (id) ON DELETE CASCADE,
    CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON DELETE CASCADE
);

-- Tabela que liga médicos e especialidades (muitos-para-muitos)
CREATE TABLE medico_especialidade (
    id_medico INT NOT NULL,
    id_especialidade INT NOT NULL,
    PRIMARY KEY (id_medico, id_especialidade),
    CONSTRAINT fk_medico FOREIGN KEY (id_medico) REFERENCES medico (id) ON DELETE CASCADE,
    CONSTRAINT fk_especialidade FOREIGN KEY (id_especialidade) REFERENCES especialidade (id) ON DELETE CASCADE
);

-- Tabela de clientes
CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    id_pessoa INT NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    CONSTRAINT fk_pessoa FOREIGN KEY (id_pessoa) REFERENCES pessoa (id) ON DELETE CASCADE
);

-- Tabela de consultas
CREATE TABLE consulta (
    id SERIAL PRIMARY KEY,
    id_medico INT NOT NULL,
    id_cliente INT NOT NULL,
    data_consulta TIMESTAMP NOT NULL,
    observacoes TEXT,
    CONSTRAINT fk_medico FOREIGN KEY (id_medico) REFERENCES medico (id) ON DELETE CASCADE,
    CONSTRAINT fk_cliente FOREIGN KEY (id_cliente) REFERENCES cliente (id) ON DELETE CASCADE
);

-- Inserir especialidades
INSERT INTO especialidade (nome)
VALUES 
('Alergologia'),
('Anestesiologia'),
('Angiologia'),
('Cardiologia'),
('Cirurgia Geral'),
('Dermatologia'),
('Endocrinologia'),
('Gastroenterologia'),
('Geriatria'),
('Ginecologia'),
('Neurologia'),
('Oftalmologia'),
('Ortopedia'),
('Pediatria'),
('Psiquiatria');

-- Inserir pessoas (médicos e clientes)
INSERT INTO pessoa (nome, telefone, data_nascimento)
VALUES 
('Carlos Silva', '11987654321', '1980-05-10'),
('Mariana Souza', '21987654321', '1990-08-15'),
('João Oliveira', '31987654321', '1985-12-20'),
('Ana Lima', '41987654321', '1995-03-25'),
('Paula Mendes', '51987654321', '1975-07-30'),
('Roberto Almeida', '61987654321', '1970-11-22'),
('Beatriz Santos', '71987654321', '1988-06-12'),
('Gabriel Ferreira', '81987654321', '1992-09-18'),
('Larissa Moreira', '91987654321', '1998-04-10'),
('Fernando Costa', '11987654322', '1983-01-05'),
('Juliana Ribeiro', '21987654322', '1979-07-14'),
('Lucas Barros', '31987654322', '1991-03-03'),
('Viviane Lopes', '41987654322', '1987-12-02'),
('André Carvalho', '51987654322', '1996-08-09'),
('Patrícia Nunes', '61987654322', '1981-11-30');

-- Inserir usuários (médicos)
INSERT INTO usuario (id_pessoa, email, senha)
VALUES 
(1, 'carlos@exemplo.com', 'senha123'),
(2, 'mariana@exemplo.com', 'senha456'),
(3, 'joao@exemplo.com', 'senha789'),
(4, 'ana@exemplo.com', 'senhaabc'),
(5, 'paula@exemplo.com', 'senha1234');

-- Inserir médicos
INSERT INTO medico (id_pessoa, id_usuario, crm)
VALUES 
(1, 1, 'CRM-SP-12345'),
(2, 2, 'CRM-RJ-54321'),
(3, 3, 'CRM-MG-67890'),
(4, 4, 'CRM-BA-24680'),
(5, 5, 'CRM-PE-13579');

-- Associar médicos às especialidades
INSERT INTO medico_especialidade (id_medico, id_especialidade)
VALUES 
(1, 4), -- Carlos é cardiologista
(2, 6), -- Mariana é dermatologista
(3, 1), -- João é alergologista
(4, 13), -- Ana é ortopedista
(5, 10); -- Paula é ginecologista

-- Inserir clientes
INSERT INTO cliente (id_pessoa, cpf)
VALUES 
(6, '123.456.789-01'),
(7, '987.654.321-09'),
(8, '456.789.123-67'),
(9, '321.654.987-45'),
(10, '654.321.987-89'),
(11, '789.456.123-12'),
(12, '987.123.456-78'),
(13, '123.987.654-32'),
(14, '321.987.654-76'),
(15, '654.123.987-54');

INSERT INTO consulta (id_medico, id_cliente, data_consulta, observacoes)
VALUES 
(1, 6, '2024-01-15 09:00:00', 'Consulta inicial para dor no peito.'),
(1, 7, '2024-01-16 10:30:00', 'Revisão de exames cardíacos.'),
(2, 8, '2024-01-17 14:00:00', 'Consulta para tratamento de acne.'),
(2, 9, '2024-01-18 11:00:00', 'Consulta para alergia na pele.'),
(3, 10, '2024-01-19 16:00:00', 'Consulta para tratamento de rinite.'),
(3, 7, '2024-01-20 09:30:00', 'Avaliação para alergias alimentares.'), -- Substituído id_cliente = 12 por 7
(4, 8, '2024-01-21 13:00:00', 'Avaliação de dor no joelho.'),           -- Substituído id_cliente = 13 por 8
(4, 9, '2024-01-22 10:00:00', 'Consulta para entorse no tornozelo.'), -- Substituído id_cliente = 14 por 9
(5, 10, '2024-01-23 15:30:00', 'Consulta ginecológica anual.'),       -- Substituído id_cliente = 15 por 10
(5, 6, '2024-01-24 14:00:00', 'Consulta pré-natal.');
