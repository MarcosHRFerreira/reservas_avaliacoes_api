
CREATE TABLE IF NOT EXISTS avaliacoes (
    idavaliacao SERIAL ,
    idcliente INTEGER NOT NULL,
    idrestaurante INTEGER NOT NULL,
    avaliacao INTEGER NOT NULL,
    comentario CHARACTER(200) NOT NULL,
    dataavaliacao TIMESTAMP NOT NULL ,
    PRIMARY KEY (idcliente, idrestaurante, dataavaliacao),
    FOREIGN KEY (idcliente) REFERENCES Clientes(idcliente),
    FOREIGN KEY (idrestaurante) REFERENCES Restaurantes(idrestaurante)
);


