INSERT INTO public.tbl_portafolios (id_portafolio, username, name_portafolio, balance_portafolio) VALUES(100, 'user1','portafolio1', 100);
INSERT INTO public.tbl_assets (id_assets, id_portafolio, id_symbolCoin, quantity, balance, dollar_balance) VALUES(100, 100, 'XSA', 30, 30, 30);
INSERT INTO public.tbl_transactions
(id_transaction, id_assets, type_transaction, "date", actual_price, fee, notes, total_recived, amount, quantity)
VALUES(1 ,100, 'buy', '2022-05-17', 5000, 3200, 'cualquier cosa', 5000, 1, 2);
