create table public.tb_assets (
    idAsset serial primary key,
    idPortafolio int NOT NULL,
    idSymbolCoin int NOT NULL,
    balanceAsset numeric NOT NULL,
    dollarBalance numeric NOT NULL,
    CONSTRAINT idPortafolio_fk FOREIGN KEY (idPortafolio) REFERENCES tb_portafolio(idPortafolio)
)