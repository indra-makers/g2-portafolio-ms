create table public.tb_portafolio (
    idPortafolio serial primary key,
    username varchar(255) NOT NULL,
    name_portafolio varchar(255) NOT NULL,
    balancePortafolio numeric NOT NULL
)