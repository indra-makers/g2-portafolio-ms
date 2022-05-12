create table public.tb_transaction (
    idTransaction serial primary key,
    idAsset int NOT NULL,
    typeTransaction varchar(255) NOT NULL,
    date timestamp NOT NULL,
    actualPrice numeric NOT NULL,
    fee numeric NOT NULL,
    notes varchar(255) NOT NULL,
    totalRecived numeric NOT NULL,
    amount int NOT NULL,
    CONSTRAINT idAsset_fk FOREIGN KEY (idAsset) REFERENCES tb_assets(idAsset)

)