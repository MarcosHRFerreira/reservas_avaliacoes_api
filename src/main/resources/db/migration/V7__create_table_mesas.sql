
CREATE TABLE  IF NOT EXISTS  Mesas (
    idmesa SERIAL,
    idrestaurante INTEGER NOT NULL,
    numero CHARACTER(3) NOT NULL,
    status CHARACTER(20) NOT NULL,
    FOREIGN KEY (idrestaurante) REFERENCES restaurantes(idrestaurante)
);