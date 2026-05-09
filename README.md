# TjnCompany - E-commerce Backend API

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-DarkGreen?style=flat-square)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Blue?style=flat-square)
![MapStruct](https://img.shields.io/badge/MapStruct-1.5.5-yellow?style=flat-square)

API RESTful desenvolvida em Java para o motor de um sistema de e-commerce. O projeto foca em alta consistência de dados, utilizando princípios de Engenharia de Software e Domain-Driven Design (DDD) para garantir que as regras de negócio sejam estritamente validadas na raiz do domínio.

## 🎯 Objetivo do Projeto
O épico inicial deste projeto **[EP001] – Digitalização do Controle de Inventário** visa substituir controles manuais por um banco de dados estruturado, garantindo padronização, auditoria e segurança na manipulação de produtos e categorias.

## 🚀 Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Framework:** Spring Boot (Web, Data JPA, Validation)
* **Banco de Dados:** PostgreSQL
* **Mapeamento de Objetos:** MapStruct + Lombok
* **Documentação:** Springdoc OpenAPI (Swagger)

## 🏗️ Arquitetura e Decisões de Design

Este projeto foi desenhado para ser escalável e de fácil manutenção, adotando os seguintes padrões:

* **Arquitetura *Feature-Based*:** Código organizado por domínios de negócio (ex: `/product`, `/category`) em vez de camadas técnicas, facilitando a navegação e o isolamento de funcionalidades.
* **Modelo de Domínio Rico (*Rich Domain Model*):** Entidades blindadas com construtores privados e fábricas estáticas (`create()`). Nenhuma entidade pode ser instanciada em um estado inválido (ex: preço de venda menor que o custo).
* **Mapeamento Híbrido com MapStruct:** Uso de *Default Methods* nas interfaces do MapStruct para unir a automação da conversão de DTOs com o controle rigoroso de instanciação das entidades.
* **Padronização Automática de Dados:** Implementação de utilitários como `NameNormalizer` para higienização silenciosa de inputs (remoção de acentos e caracteres especiais), garantindo integridade no banco de dados sem prejudicar a experiência do usuário.
* **Hierarquia de Categorias:** Suporte nativo a categorias raiz (parent `null`) e subcategorias, utilizando relacionamentos autorreferenciais.

## 📦 Funcionalidades (Sprint Atual)

- [x] **Gestão de Categorias:** Criação de categorias hierárquicas e validação de nomes duplicados.
- [x] **Gestão de Produtos:** Cadastro de itens com controle de estoque integrado (Flat Model), atrelados a categorias.
- [x] **Consultas Otimizadas:** Busca de produtos por nome normalizado seguindo estritamente o padrão REST (`GET /products/name?name=...`).
- [x] **Tratamento de Exceções:** Retornos HTTP semânticos (ex: `404 Not Found`, `400 Bad Request`) utilizando `@ResponseStatus` e validações do Spring.

## 🗺️ Roadmap (Próximos Passos)

- [ ] **Carrinho de Compras:** Endpoints para adição, remoção e alteração de quantidades de produtos.
- [ ] **Motor de Pedidos (Orders):** Checkout seguro com controle de concorrência.
- [ ] **Transações de Estoque:** Baixa automática e segura de estoque na consolidação de vendas.

## 🛠️ Como Executar o Projeto

1. **Pré-requisitos:** Java 21+, Maven e PostgreSQL instalados (ou via Docker).
2. **Configuração do Banco:** Crie um banco de dados no PostgreSQL chamado `tjncompany` (ou ajuste as credenciais no `application.properties`).
3. **Clone o repositório:**
   ```bash
   git clone [https://github.com/ThgShikifujin/TjnCompany.git](https://github.com/ThgShikifujin/TjnCompany.git)
   cd TjnCompany