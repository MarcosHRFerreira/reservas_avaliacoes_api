
CREATE TABLE  IF NOT EXISTS reservas (
    idreserva SERIAL,
    idcliente INTEGER NOT NULL,
    idrestaurante INTEGER NOT NULL,
    datahora TIMESTAMP NOT NULL,
    numeropessoas INTEGER NOT NULL,
    numeromesas INTEGER NOT NULL,
    status CHARACTER(20),
    FOREIGN KEY (idcliente) REFERENCES clientes(idcliente),
    FOREIGN KEY (idrestaurante) REFERENCES restaurantes(idrestaurante)
);
