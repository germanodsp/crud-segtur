# crud-segtur

Projeto Final - SEGTUR Turismo
Este projeto foi desenvolvido como avaliação final da disciplina de Programação Orientada a Objetos I.

Autores:
Germano da Silva Pinheiro 
Rodrigo Junior da Silva Cardoso 

1. Descrição do Sistema

O SEGTUR Turismo é um sistema de gerenciamento para uma agência de viagens focada no público sênior. O objetivo é oferecer uma interface simples e intuitiva para administrar as operações da empresa, incluindo o cadastro de usuários, pacotes de viagem, destinos, reservas e avaliações.
O sistema foi projetado para ser uma solução de média complexidade que resolve um problema do mundo real, permitindo a entrada, manipulação e visualização de dados de forma organizada.

2. Funcionalidades Principais

Gerenciamento de Usuários: Permite guardar informações como CPF, nome, email, telefone e senha para acesso ao sistema.
Gerenciamento de Pacotes de Viagem: Cadastra pacotes com nome, preço, datas de saída e chegada, itinerário completo e controle de vagas disponíveis.
Gestão de Destinos: Armazena informações sobre os lugares, suas descrições e os pacotes associados.
Sistema de Reservas: Permite que vagas sejam compradas ou reservadas mediante um sinal. O sistema controla quem reservou, qual pacote, a data da reserva e seu status (confirmada ou cancelada).
Política de Cancelamento: Define uma data limite para o cancelamento de reservas.
Avaliações e Feedback: Uma seção onde usuários podem deixar comentários, postar fotos e dar notas sobre os pacotes de viagem que utilizaram, ajudando outros clientes na escolha.

3. Conceitos e Tecnologias Aplicadas

O projeto foi desenvolvido em Java, aplicando os seguintes conceitos de Programação Orientada a Objetos e boas práticas, conforme os requisitos da disciplina:
Organização em Pacotes: As classes foram estruturadas seguindo a sugestão do padrão MVC, divididas em pacotes como modelo, view e dao.
Padrão DAO (Data Access Object): A persistência de dados é abstraída da lógica de negócio através do padrão DAO. Para cada entidade, uma classe DAO correspondente foi criada.
Persistência em Memória: Conforme solicitado, os dados são mantidos em memória durante a execução do programa, utilizando Coleções do Java (ArrayList) como repositório.
Interfaces: Pelo menos uma interface (OperacoesDAO) foi utilizada para definir o contrato de operações de persistência, promovendo o baixo acoplamento.
Enumerações: Pelo menos uma enum (StatusReserva) foi utilizada para representar um conjunto fixo de constantes, como o status de uma reserva.
Relações entre Classes: O projeto implementa a relação de Agregação/Composição, por exemplo, um Destino que contém uma lista de Pacotes.
Tratamento de Exceções: O sistema realiza o tratamento de erros e exceções para garantir que não aborte durante a utilização, como no caso de entradas inválidas do usuário.
Boas Práticas e Documentação: O código segue as convenções do Java e está documentado para explicar o funcionamento dos métodos.

4. Como Executar o Projeto

Certifique-se de ter o JDK (Java Development Kit) instalado em sua máquina.
Clone ou baixe o repositório do projeto.
Abra o projeto em sua IDE de preferência (Eclipse, IntelliJ, NetBeans, etc.).
Localize e execute o arquivo Segtur.java que está no pacote view. Este arquivo contém o método main que inicia o sistema.
O menu interativo será exibido no console, permitindo a utilização de todas as funcionalidades do sistema.