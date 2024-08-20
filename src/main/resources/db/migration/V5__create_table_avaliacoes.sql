
CREATE TABLE Avaliacoes (
    ID_avaliacao SERIAL ,
    ID_cliente INTEGER NOT NULL,
    ID_restaurante INTEGER NOT NULL,
    avaliacao INTEGER NOT NULL,
    comentario CHARACTER(200) NOT NULL,
    data_avaliacao TIMESTAMP NOT NULL ,
    PRIMARY KEY (ID_cliente, ID_restaurante, data_avaliacao),
    FOREIGN KEY (ID_cliente) REFERENCES Clientes(ID_cliente),
    FOREIGN KEY (ID_restaurante) REFERENCES Restaurantes(ID_restaurante)
);


