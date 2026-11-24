# Barber API

## PostgreSQL local

Crie um banco chamado `barber` no PostgreSQL antes de iniciar a aplicação. O Hibernate cria/atualiza as tabelas a partir das entidades.

Configure as variáveis de ambiente:

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/barber"
$env:DB_USERNAME = "postgres"
$env:DB_PASSWORD = "sua_senha"
.\mvnw.cmd spring-boot:run
```

`DB_URL` e `DB_USERNAME` são opcionais quando usados os valores padrão acima. `DB_PASSWORD` deve ser definida conforme a configuração do seu PostgreSQL. Os testes usam um banco H2 isolado e não precisam de um servidor PostgreSQL.
