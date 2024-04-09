CREATE TABLE clients(
	id serial primary key,
	nome text not null,
	email text not null,
	senha text not null
);

CREATE TABLE logistics(
	id serial primary key,
	nome text not null,
	email text not null,
	senha text not null
);

CREATE TABLE products(
	id int primary key,
	preco int not null,
	nome text  not null,
	descricao text not null,
	estoque int not null
);


insert into clients (nome, email, senha) values 
('João Pedro',' jp2017@uol.com.br', '12345jaum'),
('Amara Silva', 'amarasil@bol.com.br'  ,'amara82'),
('Maria Pereira ','mariape@terra.com.b', '145aektm');

insert into logistics (nome, email, senha) values 
('Taniro Rodrigues','jtanirocr@gmail.com', '123456abc'),
('Lorena  Silva', 'lore_sil@yahoo.com.br'  ,'12uhuuu@'),
