
INSERT INTO USUARIO(nome, senha) VALUES('user', '123');
INSERT INTO USUARIO(nome, senha) VALUES('user2', '$2a$10$p5xWFuR1sw1/uF4WqFMFvu5uBtlmgQaWLB/kXNixBv3qdsJyzjL1W');

INSERT INTO CATEGORIAS(nome, status) VALUES('Categoria A', 'ATIVA');
INSERT INTO CATEGORIAS(nome, status) VALUES('Categoria F', 'ATIVA');
INSERT INTO CATEGORIAS(nome, status) VALUES('Categoria J', 'ATIVA');
INSERT INTO CATEGORIAS(nome, status) VALUES('Categoria G', 'ATIVA');
INSERT INTO CATEGORIAS(nome, status) VALUES('Categoria H', 'ATIVA');
INSERT INTO CATEGORIAS(nome, status) VALUES('Categoria I', 'ATIVA');
INSERT INTO CATEGORIAS(nome, status) VALUES('Categoria Z', 'ATIVA');
INSERT INTO CATEGORIAS(nome, status) VALUES('Categoria D', 'ATIVA');
INSERT INTO CATEGORIAS(nome, status) VALUES('Categoria B', 'ATIVA');

INSERT INTO PRODUTOS(nome, categoria_id, preco_unitario, quantidade_estoque) VALUES('Produto A', 1, 1000, 10);
INSERT INTO PRODUTOS(nome, categoria_id, preco_unitario, quantidade_estoque) VALUES('Produto B', 2, 1000, 10);

INSERT INTO CLIENTES(nome, cpf, rua, numero, bairro, cidade, estado) VALUES ('A', 'cpf', 'rua', 'numero', 'bairro', 'cidade', 'estado');

INSERT INTO PEDIDOS (data, desconto, tipo_desconto, cliente_id) VALUES ('2022-02-02', 20, 'NENHUM', 1);

INSERT INTO ITENS_PEDIDO(desconto, preco_unitario, quantidade, tipo_desconto, pedido_id, produto_id) VALUES (10, 40, 20, 'NENHUM', 1, 1);