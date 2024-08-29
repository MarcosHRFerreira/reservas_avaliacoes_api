
CREATE TABLE IF NOT EXISTS  restaurantes_cozinhas (
    idrestaurantecozinha SERIAL,
    idrestaurante INTEGER NOT NULL,
    idcozinha INTEGER NOT NULL,
    PRIMARY KEY (idrestaurante, idcozinha),
    FOREIGN KEY (idrestaurante) REFERENCES Restaurantes(idrestaurante),
    FOREIGN KEY (idcozinha) REFERENCES Cozinhas(idcozinha)
);


