create table public.tbl_assets (
    id_assets serial primary key,
    id_portafolio int NOT NULL,
    id_symbolCoin varchar(255) NOT NULL,
    quantity int NOT NULL,
    balance numeric NOT NULL,
    dollar_balance numeric NOT NULL,
    CONSTRAINT idPortafolio_fk FOREIGN KEY (id_portafolio) REFERENCES tbl_portafolios(id_portafolio)
)