
CREATE TABLE Reservas (
    id_reserva SERIAL,
    id_cliente INTEGER NOT NULL,
    id_restaurante INTEGER NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    numero_pessoas INTEGER NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente),
    FOREIGN KEY (id_restaurante) REFERENCES Restaurantes(id_restaurante)
);
