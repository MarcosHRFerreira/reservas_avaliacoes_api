
CREATE TABLE Mesas (
    id_mesa SERIAL,
    id_restaurante INTEGER NOT NULL,
    numero CHARACTER(3) NOT NULL,
    status CHARACTER(20) NOT NULL,
    FOREIGN KEY (id_restaurante) REFERENCES Restaurantes(id_restaurante)
);