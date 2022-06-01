create table public.tbl_history_assets (
       id_assets serial,
       id_portafolio int NOT NULL,
       id_symbolCoin varchar(3) NOT NULL,
       quantity int NOT NULL,
       balance numeric NOT NULL,
       dollar_balance numeric NOT NULL,
       history_date TIMESTAMP NOT NULL default NOW()
)