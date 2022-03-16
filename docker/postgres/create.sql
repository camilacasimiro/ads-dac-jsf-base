CREATE TABLE pessoa(
    id serial PRIMARY KEY,
    nome VARCHAR(50),
    CPF VARCHAR(15)
);

CREATE TABLE dependente(
    id serial PRIMARY KEY,
    nome VARCHAR(50),
    dataDeNascimento DATE
);

CREATE TABLE pessoa_dependente(
   id_pessoa INT,
   id_dependente INT,
   FOREIGN KEY (id_pessoa) REFERENCES pessoa(id) ON DELETE CASCADE,
   FOREIGN KEY (id_dependente) REFERENCES dependente(id) ON DELETE CASCADE,
   PRIMARY KEY(id_pessoa,id_dependente)
);

