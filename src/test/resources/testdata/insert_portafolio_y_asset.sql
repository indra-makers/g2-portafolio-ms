INSERT INTO public.tbl_portafolios(
id_portafolio, username, name_portafolio, balance_portafolio)
VALUES (111, 'user test', 'portafolio test', 111);

INSERT INTO public.tbl_assets(
id_assets, id_portafolio, id_symbolcoin, quantity, balance, dollar_balance)
VALUES (111, 111, 'CRT', 111, 111, 111);