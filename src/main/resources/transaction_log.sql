-- Table: public.transaction_log

-- DROP TABLE IF EXISTS public.transaction_log;

CREATE TABLE IF NOT EXISTS public.transaction_log
(
    id bigint NOT NULL DEFAULT nextval('transaction_log_id_seq'::regclass),
    transaction_id bigint NOT NULL,
    log_date timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    log_message character varying COLLATE pg_catalog."default",
    CONSTRAINT transaction_log_pkey PRIMARY KEY (id),
    CONSTRAINT transaction_id FOREIGN KEY (transaction_id)
        REFERENCES public.transactions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.transaction_log
    OWNER to postgres;