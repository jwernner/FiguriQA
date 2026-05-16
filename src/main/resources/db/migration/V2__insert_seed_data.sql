INSERT INTO users (id, name, username, password, role, city, nickname) VALUES
    (1, 'Ana Ribeiro', 'ana', '123456', 'COLECIONADOR', 'Curitiba', 'AnaGol'),
    (2, 'Bruno Lima', 'bruno', '123456', 'COLECIONADOR', 'Sao Paulo', 'B10'),
    (3, 'Carla Souza', 'carla', '123456', 'COLECIONADOR', 'Recife', 'Carla <b>Craque</b>'),
    (4, 'Admin FiguriQA', 'admin', 'admin123', 'ADMIN', 'Campinas', 'Admin');

INSERT INTO teams (id, name, group_name, fictional_confederation) VALUES
    (1, 'Aurora FC', 'A', 'Liga Boreal'),
    (2, 'Monte Azul', 'A', 'Liga Continental'),
    (3, 'Costa Coral', 'B', 'Liga Atlantica'),
    (4, 'Vale Verde', 'B', 'Liga Sulina'),
    (5, 'Estrela Norte', 'C', 'Liga Boreal'),
    (6, 'Rio Dourado', 'C', 'Liga Tropical'),
    (7, 'Serra Prata', 'D', 'Liga Continental'),
    (8, 'Ilha Violeta', 'D', 'Liga Atlantica');

INSERT INTO stickers (id, code, title, type, rarity, team_id)
SELECT
    ((team_data.team_id - 1) * 12) + sticker_number AS id,
    team_data.prefix || '-' || lpad(sticker_number::text, 2, '0') AS code,
    'Figurinha ' || sticker_number || ' de ' || team_data.name AS title,
    CASE sticker_number % 4
        WHEN 0 THEN 'Escudo ficticio'
        WHEN 1 THEN 'Atleta inventado'
        WHEN 2 THEN 'Mascote'
        ELSE 'Estadio imaginario'
    END AS type,
    CASE sticker_number % 3
        WHEN 0 THEN 'Comum'
        WHEN 1 THEN 'Especial'
        ELSE 'Brilhante'
    END AS rarity,
    team_data.team_id
FROM (
    VALUES
        (1, 'Aurora FC', 'AU'),
        (2, 'Monte Azul', 'MO'),
        (3, 'Costa Coral', 'CO'),
        (4, 'Vale Verde', 'VA'),
        (5, 'Estrela Norte', 'ES'),
        (6, 'Rio Dourado', 'RI'),
        (7, 'Serra Prata', 'SE'),
        (8, 'Ilha Violeta', 'IL')
) AS team_data(team_id, name, prefix)
CROSS JOIN generate_series(1, 12) AS sticker_series(sticker_number);

INSERT INTO user_stickers (id, user_id, sticker_id, quantity)
SELECT row_number() OVER (ORDER BY user_id, sticker_index) AS id, user_id, sticker_index + 1 AS sticker_id, quantity
FROM (
    SELECT 1 AS user_id, sticker_index,
           CASE WHEN (sticker_index + 0) % 2 = 0 THEN 3 ELSE 1 END AS quantity
    FROM generate_series(0, 95) AS sticker_series(sticker_index)
    WHERE (sticker_index + 0) % 5 <> 0

    UNION ALL

    SELECT 2 AS user_id, sticker_index,
           CASE WHEN (sticker_index + 1) % 3 = 0 THEN 3 ELSE 1 END AS quantity
    FROM generate_series(0, 95) AS sticker_series(sticker_index)
    WHERE (sticker_index + 1) % 5 <> 0

    UNION ALL

    SELECT 3 AS user_id, sticker_index,
           CASE WHEN (sticker_index + 2) % 4 = 0 THEN 3 ELSE 1 END AS quantity
    FROM generate_series(0, 95) AS sticker_series(sticker_index)
    WHERE (sticker_index + 2) % 5 <> 0
) seeded_collection;

INSERT INTO swap_points (id, name, city, address, active) VALUES
    (1, 'Centro Comunitario Bola Cheia', 'Curitiba', 'Rua das Copas, 101', TRUE),
    (2, 'Biblioteca Arena do Saber', 'Sao Paulo', 'Avenida Album Novo, 55', TRUE),
    (3, 'Praca Encontro dos Colecionadores', 'Recife', 'Travessa da Figurinha, 8', FALSE);

INSERT INTO trades (id, requester_id, receiver_id, status, confirmation_code, created_at, completed_at) VALUES
    (1, 1, 2, 'PENDENTE', 'TRD-SEED1', CURRENT_TIMESTAMP - INTERVAL '1 day', NULL),
    (2, 2, 3, 'ACEITA', 'TRD-SEED2', CURRENT_TIMESTAMP - INTERVAL '2 days', NULL),
    (3, 3, 1, 'CANCELADA', 'TRD-SEED3', CURRENT_TIMESTAMP - INTERVAL '3 days', NULL),
    (4, 1, 3, 'CONCLUIDA', 'TRD-SEED4', CURRENT_TIMESTAMP - INTERVAL '4 days', CURRENT_TIMESTAMP - INTERVAL '3 days');

INSERT INTO trade_items (id, trade_id, sticker_id, direction, quantity) VALUES
    (1, 1, 4, 'REQUESTER_GIVES', 1),
    (2, 1, 20, 'RECEIVER_GIVES', 1),
    (3, 2, 25, 'REQUESTER_GIVES', 1),
    (4, 2, 42, 'RECEIVER_GIVES', 1),
    (5, 3, 56, 'REQUESTER_GIVES', 1),
    (6, 3, 8, 'RECEIVER_GIVES', 1),
    (7, 4, 62, 'REQUESTER_GIVES', 1),
    (8, 4, 14, 'RECEIVER_GIVES', 1);

SELECT setval(pg_get_serial_sequence('users', 'id'), (SELECT MAX(id) FROM users));
SELECT setval(pg_get_serial_sequence('teams', 'id'), (SELECT MAX(id) FROM teams));
SELECT setval(pg_get_serial_sequence('stickers', 'id'), (SELECT MAX(id) FROM stickers));
SELECT setval(pg_get_serial_sequence('user_stickers', 'id'), (SELECT MAX(id) FROM user_stickers));
SELECT setval(pg_get_serial_sequence('swap_points', 'id'), (SELECT MAX(id) FROM swap_points));
SELECT setval(pg_get_serial_sequence('trades', 'id'), (SELECT MAX(id) FROM trades));
SELECT setval(pg_get_serial_sequence('trade_items', 'id'), (SELECT MAX(id) FROM trade_items));
