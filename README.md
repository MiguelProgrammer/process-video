# process-video
Projeto para finalizar última entrega - Pós-graduação FIAP

<img src="https://i.imgur.com/NlV37va.png" width="1000">

## Miro - DDD
<p style='text-align: indent'>link para leitura: <a href="https://miro.com/app/board/uXjVLi5-7EY=/">Doc Domain Driven Design</a></p>

##
<p style='text-align: indent'> A ideia por de trás deste conceito é a de receber um vídeo ou mais, realizar o processamento dos mesmos, tirar prints de cada frame sem que nenhum se perca, 
persistir os prints em base dados. Como arquiteto, a primeira questão a se pensar é a de "custos". Quão será custoso para realizar tal tarefa de processamento de tais arquivos, 
mante-los em base dedados e consumi-los. A princípio, para as tarefas mais simples, pensando a baixo nível, seria uma aplicação com persistência poliglota, 
será necessário um banco de dados que trabalhe com chave e valor como o MongoDB <img src="https://github.com/user-attachments/assets/9cd41ad2-cccd-4863-b5e2-cfed465dcc55" width="25">
para persistir nossos prints e um outro banco como PostgreSql
<img src="https://github.com/user-attachments/assets/744c5564-978f-4e56-9c80-17709a30724c" width="20"> para persistir informações referente ao tipo de arquivo enviado.</p>

##
Após criação dos itens para persistir nossos arquivos e informações, partimos para a parte de processamento, preciso que cada video encaminhado para o processamento não interrompido,
vejo uma alternativa como um serviço reativo talvez, mensageria.

##
A arquitetura a ser adotada para este projeto sera clean architecture.

<img src="https://github.com/user-attachments/assets/48d89f4b-e2d8-4bf0-9fd9-c153b53639a0" width="500">

##
<p style='text-align: indent'>A arquitetura limpa coopera com o business, organização, design e manutenibilidade, sempre respeitando os adapters e gateways. 
Na medida em que a construção e implementação das features, venho analisando melhor a arquitetura e me pergunto se não seria melhor para esse cenário a arquiteura event-drive, 
como prentendo criar um serviço assíncrono, irei utilizar apache kafka para enfileirar meus objetos para que eu possa ter mais organização de fluxo de request e esta arquitetura coopera 
bastante com esse cenário, possa ser que eu mude ao final do projeto.</p>

<img src="https://github.com/user-attachments/assets/12514a6b-0c2e-4459-b1b6-6ea1d3f49017" width="500">

##
<p style='text-align: indent'>Infelizmente, não pude adotar a arquitetura voltada a eventos <b>event-driven</b>. Esta arquitetura lida diretamente com filas, processos assíncronos, e para este cenário, 
não pude utilza-la, a um certo limite de bytes para usar filas e o kafka não iria suportar o tamanho da stream. Então parti para serviços não blockantes.
Brinquei bastante com spring webflux <img src="https://gitlab.com/uploads/-/system/project/avatar/25570288/webflux.png" width="27">. 
Pode-se dizer que apanhei bastante para inserir uma api reativa ao meu cenário, até consegui, mas como já tinha meu usecase pronto para lidar com um certo tipo de entreda, 
removi o spring webflux, tente lidar com arquivo e processa-los, persisti-los e recupera-los, é uma insana codifiação.

Ao final, fui para o simples, subi o tento do Java na aplicação, sai do Java17 e fui para Java21, assim eu posso trabalhar com threads virtuais, nos meus testes, as requisições foram bem sucedidas, 
porém, minha rest-api atua blockante, é um trade-off que minha arquitetura adotada me atribuiu, o famoso <b>Over-engineering</b>.

Tudo isso é muito interessante, houve momentos em que parti para o que realmente interessa, yagne, mas após todo esse processo de refactoring, percebi que é necessário se pensar com clareza 
na adoção das coisas simples, na medida em que você constroi a aplicação, você precisa analisar se está pensando em atender o problema ou usar tal arquitetura ou padrão de projeto.
Encontri diversas falhas durante o processo de desenvolvimento, realizaei diversas alterações, depuração, debugs, logs e outros meios mais de me esgotar ao ponto de volta ao início do problema e
 partir para a adoção do simples. Este projeto cooperou bo quesito visão arquitetural.</p>

## Tests - Unit, Integration and System
<p style='text-align: indent'> Cobertura baixa de testes, mas existe teste.</p>

<img src="https://i.imgur.com/y09qrex.png" width="500"> 

## Docker Hub
<p style='text-align: indent'>
 A imagem da aplicação está disponibilizada no <a href="https://hub.docker.com/r/migprogrammer/process-videos">DockerHub</a>
ou baixe a imagem agora mesmo 

```
  docker push migprogrammer/process-videos:tagname 
```

 Observações e considerações: Ao abaixar a imagem, será necessário ter o banco NoSql MongoDb instalado, também, deixe os seus vídeos nao seguinte
diretório 'C:\videos'.

Considere também, usar o mongoDb local, configurações básicas de conexão, somente o nome do banco como videos.
</p>

## Observabilidade - Dynatrace 
<p style='text-align: indent'>Usei o Dynatrace para ver como a aplicação lidá com o processamento de videos e geração de imagens</p>

<img src="https://i.imgur.com/JiD0vgV.png" width="500"> 

<img src="https://i.imgur.com/VsQszJk.png" width="500"> 

<img src="https://i.imgur.com/qzinB4E.png" width="500"> 