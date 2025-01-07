CREATE TABLE IF NOT EXISTS FIAP.USERS (

    id SERIAL PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255),
    login VARCHAR(255),
    senha VARCHAR(255),
    data DATE,
    endereco_rua VARCHAR(255),
    endereco_cidade VARCHAR(255),
    endereco_estado VARCHAR(255),
    endereco_cep VARCHAR(20),
    tipo VARCHAR(50)
);