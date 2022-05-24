INSERT INTO public.tbl_portafolios (id_portafolio,username, name_portafolio, balance_portafolio) VALUES(7, 'carolina','portafolio1', 10);
INSERT INTO public.tbl_assets (id_assets, id_portafolio, id_symbolCoin, quantity, balance, dollar_balance) VALUES(5, 7, 'XSA', 30, 30, 30);
INSERT INTO public.tbl_assets (id_assets, id_portafolio, id_symbolCoin, quantity, balance, dollar_balance) VALUES(6, 7, 'XOA', 40, 40, 40);
INSERT INTO public.tbl_transactions (id_transaction, id_assets, type_transaction, date, actual_price, fee, notes,total_recived,quantity, amount) VALUES(3, 5, 'buy', '2022/05/19', 3000, 30,'nota1', 20000, 2,2);
INSERT INTO public.tbl_transactions (id_transaction, id_assets, type_transaction, date, actual_price, fee, notes,total_recived,quantity , amount) VALUES(4, 6, 'buy', '2022/03/19', 4000, 40,'nota2', 40000, 4,4);
