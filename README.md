# process-video
Projeto para finalizar última entrega - Pós-graduação FIAP

<img src="https://i.imgur.com/4nXuOFw.png" width="1000">


##
<p style='text-align: indent'> A ideia por de trás deste é a de receber um vídeo ou mais, realizar o processamento dos mesmos sem que nenhum se perca, persisti-los em base dados.
Como arquiteto, a primeira questão a se pensar é a de "custos". Quão será custoso para realizer a tarefa de processamento de tais arquivos, mante-los em base dedados e consumi-los.
A princípio, para as tarefas mais simples, pensando a baixo nível, seria uma aplicação poliglota na questão de persistência, será necessário um banco de dados que trabalhe com chave e valor como o MongoDB <img src="https://github.com/user-attachments/assets/9cd41ad2-cccd-4863-b5e2-cfed465dcc55" width="25"> para persistir nossos videos e também imagens, um outro banco como PostgreSql
<img src="https://github.com/user-attachments/assets/744c5564-978f-4e56-9c80-17709a30724c" width="20"> para persistir informaões referente ao tipo de arquivo enviado.</p>

##
Após criação dos itens para persistir nossos arquivos e informações, partimos para a parte de processamento, preciso que cada video encaminhado para o processamento não interrompido, vejo uma alternativa como um serviço reativo talvez, mensageria.

##
A arquitetura a ser adotada para este projeto seja clean architecture.

<img src="https://github.com/user-attachments/assets/48d89f4b-e2d8-4bf0-9fd9-c153b53639a0" width="500">

##
<p style='text-align: indent'>A arquitetura limpa coopera com o business, organização, design e manutenibilidade, sempre respeitando os adapters e gateways. Na medida em que a construção e implementação das features, venho analisando melhor a arquitetura e me pergunto se não seria melhor para esse cenário a arquiteura event-drive, como prentendo criar um serviço assíncrono, irei utilizar apache kafka para enfileirar meus objetos para que eu possa ter mais coesão do fluxo de request e esta arquitetura coopera bastante com esse cenário, possa ser que eu mude ao final do projeto.</p>

<img src="https://github.com/user-attachments/assets/12514a6b-0c2e-4459-b1b6-6ea1d3f49017" width="500">

##
<p style='text-align: indent'>Infelizmente, não pude adotar a arquitetura voltada a eventos <b>event-driven</b>. Esta arquitetura lida diretamente com filas, processos assíncronos, para este cenário, não pude utilzar o kafka, precisava deixar minhas requests não blockantes, pensei no kafka porém a fila não iria suportar o tamanho da stream.
Brinquei bastante com spring webflux <img src="https://gitlab.com/uploads/-/system/project/avatar/25570288/webflux.png" width="27">. Pode-se dizer que apanhei bastante para inserir uma api reativa ao meu cenário, até consegui, mas como já tinha meu usecase pronto para lidar com um certo tipo de entreda, removi o spring webflux.</p>






