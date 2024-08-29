CREATE TABLE IF NOT EXISTS restaurantes (
    idrestaurante SERIAL NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email varchar(100) not null,
    telefone CHARACTER(50) NOT NULL,
    logradouro VARCHAR(100) ,
    bairro VARCHAR(100) NOT NULL,
    cep CHAR(9) NOT NULL,
    complemento VARCHAR(100),
    numero VARCHAR(20) NOT NULL,
    uf CHAR(2) NOT NULL,
    cidade VARCHAR(100) NOT NULL);
