# Laudocs API
Implementação backend do sistema Laudocs, um sistema de gerenciamento e geração de laudos médicos. Esta API é responsável por gerenciar os laudos, consultas e pacientes, facilitando a criação, consulta, atualização e exclusão de laudos médicos, bem como o gerenciamento de informações relacionadas a pacientes e consultas.
---

## **Funcionalidades**
- Cadastro de Laudos: Criação e armazenamento de laudos médicos com metadados como tipo, tamanho e URL.
- Associação com Pacientes: Laudos podem ser associados a pacientes para facilitar o rastreamento e histórico médico.
- Associação com Consultas: Laudos também podem ser associados a consultas, possibilitando o vínculo entre os documentos e os atendimentos realizados.
= Consultas de Laudos: Recuperação dos laudos associados a um paciente ou consulta específica.
- Armazenamento de Arquivos: Armazenamento de laudos no formato .doc ou outros tipos de arquivo médico. (DESENVOLVIMENTO)

## **Tecnologias**
- Spring Boot: Framework para criação da API.
- Java 17/21: Linguagem de programação utilizada.
- JPA/Hibernate: Para gerenciamento de persistência e mapeamento de entidades.
- MySQL: Banco de dados relacional para armazenar os dados da aplicação.
- Lombok: Utilizado para gerar automaticamente getters, setters, construtores e outros métodos boilerplate.
- Spring Security: Para autenticação e autorização.
