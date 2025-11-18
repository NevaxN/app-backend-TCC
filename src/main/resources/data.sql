INSERT INTO roles (name) VALUES ('ROLE_USUARIO') ON CONFLICT (name) DO NOTHING;

INSERT INTO roles (name) VALUES ('ROLE_ADM') ON CONFLICT (name) DO NOTHING;

INSERT INTO tipo_usuarios (id, name) VALUES 
(1, 'PESQUISADOR'), 
(2, 'EMPRESA')
ON CONFLICT (id) DO NOTHING;

-- A senha para todos os usuários abaixo é "123456"

INSERT INTO usuarios (id, login, password, email_verificado) VALUES
(10, 'pesquisador.ana@email.com', '$2a$10$N9Z.OM.Y.8.W.X.G.E.Z.Z.u5.1.O.u.B.m.S.S.I.Q.0.l.3.f.S.6.a', true),
(11, 'pesquisador.bruno@email.com', '$2a$10$N9Z.OM.Y.8.W.X.G.E.Z.Z.u5.1.O.u.B.m.S.S.I.Q.0.l.3.f.S.6.a', true),
(12, 'pesquisador.carla@email.com', '$2a$10$N9Z.OM.Y.8.W.X.G.E.Z.Z.u5.1.O.u.B.m.S.S.I.Q.0.l.3.f.S.6.a', true),
(13, 'pesquisador.diego@email.com', '$2a$10$N9Z.OM.Y.8.W.X.G.E.Z.Z.u5.1.O.u.B.m.S.S.I.Q.0.l.3.f.S.6.a', true),
(14, 'pesquisador.elisa@email.com', '$2a$10$N9Z.OM.Y.8.W.X.G.E.Z.Z.u5.1.O.u.B.m.S.S.I.Q.0.l.3.f.S.6.a', true),
(15, 'pesquisador.fabio@email.com', '$2a$10$N9Z.OM.Y.8.W.X.G.E.Z.Z.u5.1.O.u.B.m.S.S.I.Q.0.l.3.f.S.6.a', true),
(16, 'pesquisador.gabriela@email.com', '$2a$10$N9Z.OM.Y.8.W.X.G.E.Z.Z.u5.1.O.u.B.m.S.S.I.Q.0.l.3.f.S.6.a', true),
(17, 'pesquisador.heitor@email.com', '$2a$10$N9Z.OM.Y.8.W.X.G.E.Z.Z.u5.1.O.u.B.m.S.S.I.Q.0.l.3.f.S.6.a', true),
(18, 'pesquisador.isabela@email.com', '$2a$10$N9Z.OM.Y.8.W.X.G.E.Z.Z.u5.1.O.u.B.m.S.S.I.Q.0.l.3.f.S.6.a', true),
(19, 'pesquisador.julio@email.com', '$2a$10$N9Z.OM.Y.8.W.X.G.E.Z.Z.u5.1.O.u.B.m.S.S.I.Q.0.l.3.f.S.6.a', true),
(20, 'empresa.inovatech@email.com', '$2a$10$N9Z.OM.Y.8.W.X.G.E.Z.Z.u5.1.O.u.B.m.S.S.I.Q.0.l.3.f.S.6.a', true),
(21, 'empresa.buildright@email.com', '$2a$10$N9Z.OM.Y.8.W.X.G.E.Z.Z.u5.1.O.u.B.m.S.S.I.Q.0.l.3.f.S.6.a', true),
(22, 'empresa.saudemais@email.com', '$2a$10$N9Z.OM.Y.8.W.X.G.E.Z.Z.u5.1.O.u.B.m.S.S.I.Q.0.l.3.f.S.6.a', true),
(23, 'empresa.agroforte@email.com', '$2a$10$N9Z.OM.Y.8.W.X.G.E.Z.Z.u5.1.O.u.B.m.S.S.I.Q.0.l.3.f.S.6.a', true),
(24, 'empresa.focus@email.com', '$2a$10$N9Z.OM.Y.8.W.X.G.E.Z.Z.u5.1.O.u.B.m.S.S.I.Q.0.l.3.f.S.6.a', true)
ON CONFLICT (id) DO NOTHING;

INSERT INTO pesquisadores (id, usuario_id, nome_pesquisador, sobrenome) VALUES
(1, 10, 'Ana', 'Souza'),
(2, 11, 'Bruno', 'Lima'),
(3, 12, 'Carla', 'Costa'),
(4, 13, 'Diego', 'Martins'),
(5, 14, 'Elisa', 'Ferreira'),
(6, 15, 'Fábio', 'Almeida'),
(7, 16, 'Gabriela', 'Nunes'),
(8, 17, 'Heitor', 'Gomes'),
(9, 18, 'Isabela', 'Pinto'),
(10, 19, 'Júlio', 'Rocha')
ON CONFLICT (id) DO NOTHING;

INSERT INTO empresas (
    id, 
    usuario_id, 
    nome_comercial, 
    nome_registro, 
    cnpj, 
    logradouro, 
    numero_endereco, 
    bairro, 
    cidade, 
    estado, 
    cep, 
    telefone, 
    email, 
    setor
) VALUES
(1, 20, 'InovaTech Soluções', 'InovaTech LTDA', '11.111.111/0001-11', 'Av. Batel', '100', 'Batel', 'Curitiba', 'PR', '80420-000', '41999999999', 'contato@inovatech.com', 'Tecnologia'),
(2, 21, 'BuildRight Construtora', 'BuildRight S.A.', '22.222.222/0001-22', 'Rua XV de Novembro', '200', 'Centro', 'Curitiba', 'PR', '80020-000', '41888888888', 'contato@buildright.com', 'Construção Civil'),
(3, 22, 'SaúdeMais Labs', 'Laboratórios SaúdeMais', '33.333.333/0001-33', 'Av. Cândido de Abreu', '300', 'Centro Cívico', 'Curitiba', 'PR', '80530-000', '41777777777', 'contato@saudemais.com', 'Saúde'),
(4, 23, 'AgroForte S.A.', 'AgroForte S.A.', '44.444.444/0001-44', 'Rua Mateus Leme', '400', 'São Lourenço', 'Curitiba', 'PR', '82200-000', '41666666666', 'contato@agroforte.com', 'Agronegócio'),
(5, 24, 'Focus Consultoria', 'Focus Consultoria Empresarial', '55.555.555/0001-55', 'Rua Comendador Araújo', '500', 'Centro', 'Curitiba', 'PR', '80420-010', '41555555555', 'contato@focus.com', 'Consultoria')
ON CONFLICT (id) DO NOTHING;

INSERT INTO user_roles (user_id, role_id) VALUES
(10, 1), (11, 1), (12, 1), (13, 1), (14, 1), (15, 1), (16, 1), (17, 1), (18, 1), (19, 1),
(20, 1), (21, 1), (22, 1), (23, 1), (24, 1)
ON CONFLICT (user_id, role_id) DO NOTHING;

INSERT INTO user_type (user_id, type_id) VALUES 
(10, 1), (11, 1), (12, 1), (13, 1), (14, 1), 
(15, 1), (16, 1), (17, 1), (18, 1), (19, 1), 
(20, 2), (21, 2), (22, 2), (23, 2), (24, 2);

SELECT setval('usuarios_id_seq', 25, true);
SELECT setval('pesquisadores_id_seq', 11, true);
SELECT setval('empresas_id_seq', 6, true);
SELECT setval('roles_id_seq', 3, true);
SELECT setval('tipo_usuarios_id_seq', 3, true);