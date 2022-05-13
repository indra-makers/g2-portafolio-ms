create table public.tbl_transactions (
    id_transaction serial primary key,
    id_assets int NOT NULL,
    type_transaction varchar(255) NOT NULL,
    date timestamp NOT NULL,
    actual_price numeric NOT NULL,
    fee numeric NOT NULL,
    notes varchar(255) NOT NULL,
    total_recived numeric NOT NULL,
    amount int NOT NULL,
    CONSTRAINT idAsset_fk FOREIGN KEY (id_assets) REFERENCES tbl_assets(id_assets)

)