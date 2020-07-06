# Roshambo
Este projeto tem como objetivo ser uma API Restful para uma versão online do clássico Pedra, Papel e Tesoura. 
Desenvolvido em Java com Spring Boot.

## Instalação
O projeto utiliza o Gradle como ferramenta para automação de builds e gerência de dependências. É necessário que você 
tenha-o instalado em seu ambiente para poder prosseguir com a instalação e execução do projeto.

Faça o _clone_ do projeto e em seguida, dentro da pasta do mesmo execute o seguinte comando pelo terminal:

``` 
gradle install
```

## Executando o projeto

Para executar o projeto, ainda na pasta raiz do mesmo, execute os comandos a seguir:

```
gradle clean build
```
```
gradle runTomcat
```
Este último comando, faz executar a instância embarcada do servidor do projeto. Ao executá-la, poderá utilizar os
endpoints do projeto em seu cliente.


## Modelagem do Projeto
A seguir é possível visualizar o Diagrama de Classes desenhado para essa solução. Você pode abrir a versão editável 
do arquivo **Class_Diagram.mdj** que se encontra no diretório raiz do projeto com o software: StarUML.

![ER_Diagram](https://i.ibb.co/m9X5Y5s/Screenshot-from-2020-07-06-00-06-36.png)


## Descrevendo a lógica para começar a jogar

1. É necessário criar, primeiramente, uma sessão onde serão adicionados os jogadores, jogadas e estratégias.
2. Quando uma sessão é criada, o sistema automaticamente adiciona as estratégias para aquela determinada sessão.
3. Deve-se ter, obrigatóriamente, 2 jogadores
4. Para cada jogador, deve ser cadastrada uma jogada (aqui neste projeto chamada de: Move)
5. Tendo preenchido esses pré-requisitos, será então possível, iniciar o jogo.

## Endpoints
Após executar o comando para subir o servidor Tomcat, você poderá utilizar  dos 30 endpoints desenvolvidos para o
projeto. Você pode também importar a coleção para o Postman, o arquivo **Roshambo.postman_collection.json** encontra-se
no diretório raíz do projeto.