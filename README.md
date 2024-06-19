# EventMaster: API de Gestão Completa de Eventos, Participantes e Check-ins

Esta documentação fornece uma visão geral da API para o cadastro de eventos, gerenciamento de participantes e check-ins. Ela descreve as principais funcionalidades, endpoints, e exemplos de uso.

---

## Sumário
1. Descrição do Projeto
2. Tecnologias Utilizadas
3. Estrutura do Projeto
4. Funcionalidades Principais
5. Endpoints Disponíveis
6. Exemplos de Uso da API
7. Configuração do Ambiente de Desenvolvimento
8. Contribuição

---

## Descrição do Projeto

Esta API permite a gestão de eventos, incluindo o cadastro de eventos, registro de participantes e gerenciamento de check-ins. Ela facilita a organização e controle de eventos através de endpoints RESTful.

## Tecnologias Utilizadas

| Linguagens de Programação | Ferramentas e Tecnologias |
| :-----------------: | :-----------------------: |
| <img height="40" src="https://github.com/vieira-fabricio/icons/blob/main/java.svg"> | <img height="40" src="https://github.com/vieira-fabricio/icons/blob/main/postgresql.svg"> |
| <img height="40" src="https://github.com/vieira-fabricio/icons/blob/main/spring.svg"> | <img height="40" src="https://github.com/vieira-fabricio/icons/blob/main/intellij-ide.svg"> |
|   | <img height="40" src="https://github.com/vieira-fabricio/icons/blob/main/postman-icon-svgrepo-com.svg"> |
  
## Estrutura do Projeto

domain: Contém as classes de modelo que representam os dados e o controle de suas exceções.
services: Contém a lógica de negócio da aplicação.
repositories: Responsável pela interação com o banco de dados.
controllers: Define os endpoints da API.
config: Gerencia as configurações do projeto.
dto: Contém os objetos de transferência de dados (Data Transfer Objects).

## Funcionalidades Principais

* Cadastro de Eventos
* Registro de Participantes em Eventos
* Gerenciamento de Check-ins de Participantes
* Consulta de Detalhes de Eventos
* Listagem de Participantes de um Evento

## Endpoints Disponíveis

### Eventos

* **GET /events/{id}**: Retorna os detalhes de um evento.
* **GET /events**: Retorna a lista de todos os eventos.
* **POST /events**: Cria um novo evento.
* **POST /events/{eventId}/attendees**: Registra um participante em um evento.
* **GET /events/{id}/attendees**: Lista os participantes de um evento.

### Participantes

* **GET /attendees/{attendeeId}/badge**: Retorna o crachá de um participante.
* **POST /attendees/{attendeeId}/check-in**: Registra o check-in de um participante.

## Exemplos de Uso da API

### 1. Criar um Novo Evento

#### Requisição:
`POST /events
Content-Type: application/json

{
  "title": "Workshop de Java",
  "details": "Um workshop aprofundado sobre Java.",
  "maximumAttendees": 50
}
`

#### Resposta:

`201 Created
Location: /events/{eventId}
{
  "eventId": "abc123"
}
`

### 2. Registrar um Participante em um Evento

#### Requisição:
`POST /events/{eventId}/attendees
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao.silva@example.com"
}
`

#### Resposta:
`201 Created
Location: /attendees/{attendeeId}/badge
{
  "attendeeId": "xyz789"
}
`

### 3. Registrar Check-in de um Participante

#### Requisição:
`POST /attendees/{attendeeId}/check-in
`

#### Resposta:
`201 Created
Location: /attendees/{attendeeId}/badge
`

### 4. Obter Detalhes de um Evento

#### Requisição:
`GET /events/{id}
`

#### Resposta:
`{
  "id": "abc123",
  "title": "Workshop de Java",
  "details": "Um workshop aprofundado sobre Java.",
  "slug": "workshop-de-java",
  "maximumAttendees": 50,
  "attendeesCount": 20
}
`

### 5. Listar Todos os Eventos

#### Requisição:
`GET /events
`

#### Resposta:
`[
  {
    "id": "abc123",
    "title": "Workshop de Java",
    "details": "Um workshop aprofundado sobre Java.",
    "slug": "workshop-de-java",
    "maximumAttendees": 50
  },
  {
    "id": "def456",
    "title": "Introdução ao Spring Boot",
    "details": "Aprenda Spring Boot do zero.",
    "slug": "introducao-ao-spring-boot",
    "maximumAttendees": 30
  }
]
`

## Instalação

Se você deseja baixar e executar este projeto em sua máquina local, siga estas etapas:

- Clone o repositório:

Abra seu terminal e execute o seguinte comando para clonar o repositório:
```git clone git@github.com:vieira-fabricio/java-pass-in.git```

- Acesse o diretório:
Navegue até o diretório do projeto usando o seguinte comando:
```cd seurepositorio```

- Configuração do Banco de Dados:
Certifique-se de ter um servidor `PostgreSQL` em execução. Você pode configurar as credenciais do banco de dados no arquivo `application.properties` ou `application.yml`.

- Build e Execução:
Use o `Maven` para construir o projeto e executá-lo localmente:
```mvn spring-boot:run```
Ou execute o código direto da sua `IDE` de preferência.

- Testando a API com Postman:
Você pode usar o `Postman` ou outro programa de sua preferência para testar e consumir os endpoints da API. Siga estas etapas:

- Configuração do ambiente:
Antes de enviar solicitações, defina o ambiente do Postman para o seu projeto. Você pode criar um novo ambiente com variáveis como base_url, que aponta para http://localhost:8080, ou ajustar conforme necessário para o ambiente em que a API está hospedada.

- Enviar solicitações:
Utilize ferramentas como Postman ou curl para testar os endpoints definidos.

## Contribuição

Contribuições são bem-vindas! Se desejar contribuir com melhorias, correções de bugs ou novas funcionalidades, siga estes passos:

1. Faça um fork do projeto.
2. Crie uma branch com uma descrição clara do que você está implementando.
3. Desenvolva e teste suas alterações.
4. Certifique-se de que seu código segue as diretrizes de estilo do projeto.
5. Abra um pull request descrevendo suas alterações.

## Autor
<table>
  <tr>
    <td align="left">
      <a href="https://github.com/vieira-fabricio">
        <span><b>Fabricio Vieira</b></span>
      </a>
      <br>
      <span>Desenvolvedor Backend Java</span>
    </td>
  </tr>
</table>

## Histórico de Mudanças

- 1.0.0 (03/04/2024): Primeira versão lançada.
