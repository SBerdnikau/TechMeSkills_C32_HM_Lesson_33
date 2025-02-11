-- Table: public.accounts

-- DROP TABLE IF EXISTS public.accounts;

CREATE TABLE IF NOT EXISTS public.accounts
(
    id bigint NOT NULL DEFAULT nextval('accounts_id_seq'::regclass),
    account_number character varying(45) COLLATE pg_catalog."default" NOT NULL,
    balance numeric(10,2) DEFAULT 0.00,
    username character varying(45) COLLATE pg_catalog."default" NOT NULL,
    created timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT accounts_pkey PRIMARY KEY (id),
    CONSTRAINT accounts_account_number_key UNIQUE (account_number)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.accounts
    OWNER to postgres;