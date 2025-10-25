INSERT INTO usuarios (login, password, email_verificado) VALUES ('admin', '1234', true) ON CONFLICT DO NOTHING;

INSERT INTO roles (name) VALUES ('ROLE_USUARIO') ON CONFLICT (name) DO NOTHING;

INSERT INTO roles (name) VALUES ('ROLE_ADM') ON CONFLICT (name) DO NOTHING;

INSERT INTO tipo_usuarios (name) VALUES ('PESQUISADOR') ON CONFLICT (name) DO NOTHING;

INSERT INTO tipo_usuarios (name) VALUES ('EMPRESA') ON CONFLICT (name) DO NOTHING;