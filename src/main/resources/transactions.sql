-- Table: public.transactions

-- DROP TABLE IF EXISTS public.transactions;

CREATE TABLE IF NOT EXISTS public.transactions
(
    id bigint NOT NULL DEFAULT nextval('transactions_id_seq'::regclass),
    account_number character varying(45) COLLATE pg_catalog."default" NOT NULL,
    transaction_date timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    transaction_type character varying(10) COLLATE pg_catalog."default",
    amount numeric(10,2) NOT NULL,
    CONSTRAINT transactions_pkey PRIMARY KEY (id),
    CONSTRAINT transactions_account_number_fkey FOREIGN KEY (account_number)
        REFERENCES public.accounts (account_number) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT transactions_transaction_type_check CHECK (transaction_type::text = ANY (ARRAY['Deposit'::character varying::text, 'Withdrawal'::character varying::text, 'Transfer'::character varying::text]))
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.transactions
    OWNER to postgres;