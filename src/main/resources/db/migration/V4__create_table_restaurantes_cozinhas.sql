
CREATE TABLE Restaurantes_Cozinhas (
    ID_restaurante_cozinha SERIAL,
    ID_restaurante INTEGER NOT NULL,
    ID_cozinha INTEGER NOT NULL,
    PRIMARY KEY (ID_restaurante, ID_cozinha),
    FOREIGN KEY (ID_restaurante) REFERENCES Restaurantes(ID_restaurante),
    FOREIGN KEY (ID_cozinha) REFERENCES Cozinhas(ID_cozinha)
);


