create table public.tbl_portafolios (
    id_portafolio serial primary key,
    mail_user varchar(255) NOT NULL,
    name_portafolio varchar(255) NOT NULL,
    balance_portafolio numeric NOT NULL
)