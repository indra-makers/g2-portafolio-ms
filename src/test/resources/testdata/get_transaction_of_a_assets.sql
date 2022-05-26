INSERT INTO public.tbl_portafolios (id_portafolio,username, name_portafolio, balance_portafolio) VALUES(10,'carolina','portafolio1', 10);
INSERT INTO public.tbl_portafolios (id_portafolio,username, name_portafolio, balance_portafolio) VALUES(12,'carolina2','portafolio12', 10);
INSERT INTO public.tbl_assets (id_assets, id_portafolio, id_symbolCoin, quantity, balance, dollar_balance) VALUES(6, 10, 'XOA', 40, 40, 40);
INSERT INTO public.tbl_transactions (id_transaction, id_assets, type_transaction, date, actual_price, fee, notes,total_recived,quantity , amount) VALUES(7, 6, 'buy', '2022/03/19', 6000, 40,'nota2', 40000, 4,4);
INSERT INTO public.tbl_transactions (id_transaction, id_assets, type_transaction, date, actual_price, fee, notes,total_recived,quantity , amount) VALUES(3, 6, 'buy', '2022/04/20', 7000, 10,'nota3', 70000, 8,9);
