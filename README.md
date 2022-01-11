# Executar a aplicação:

No diretóro raiz da aplicação, executar uma das seguintes opções:<br>

1ª opção.<br>
Para executar somente o projeto, executar:<br>
<b>mvn spring-boot:run</b>

2ª opção.<br>
Para executar somente o projeto pela IDE do eclipse:<br>
Importar o projeto como um projeto maven existente. Na classe MoviesApplication.java, abrir o menu de contexto e executar o comando Runs As->Java Application.

3ª opção.<br>
Para executar o projeto e os testes pelo Maven. Executar:<br>
<b>mvn -U clean dependency:tree dependency:resolve dependency:resolve-plugins package && java -jar ./target/movies-0.0.1-SNAPSHOT.jar</b>

# Executar os testes:

1ª opção.<br>
<b>mvn test</b>

2ª opção.
Para executar o projeto e os testes pelo Maven. Executar:<br>
<b>mvn -U clean dependency:tree dependency:resolve dependency:resolve-plugins package && java -jar ./target/movies-0.0.1-SNAPSHOT.jar</b>

3ª opção.<br>
Para executar somente os testes do projeto pela IDE do eclipse:<br>
Importar o projeto como um projeto maven existente. Na pasta do projeto abrir o menu do contexto com o botão direito e executar o comado Run As->JUnit Test

# Executar o teste do menor e maior intervalo entre os prêmios:

1ª opção.<br>
Acessar o swagger e ir no controller Producers e ir no path <b>/producers/producer</b>. Clicar no botão "Try it out", colocar no campo filter "winnerminmax" e clicar no botão "Execute".

2ª opção.<br>
Executar viar curl no terminal:<br>
<b>curl -X GET "http://localhost:8895/movies/producers/producer?filter=winnerminmax" -H "accept: */*"</b>

3ª opção.<br>
Executar via Postman:<br>
http://localhost:8895/movies/producers/producer?filter=winnerminmax

# Acessar documentação da API (Swagger):

Após subir a aplicação, uma documentação da API (fornecida pelo Swagger) pode ser acessada através do endereço:<br>
http://localhost:8895/movies/swagger-ui.html
