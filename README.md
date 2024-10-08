# public-utils-b3
Pequenos programas utilitários em Java para filtrar dados da B3

Os programas foram compilados usando a versão 9 do Java, uma versão antiga que deve garantir a compatibilidade com a maioria das instalações. Como o código-fonte é compatível com versões ainda mais antigas, caso se queira, é possível compilar o código-fonte. 

Só atentar para usar a opção de encoding para o padrão brasileiro (ISO-8859-1), caso não esteja já como padrão. O comando para compilar os programas (abrindo um Prompt):

javac -encoding ISO-8859-1 -source 9 ExtraiPapelSeriesHistoricasB3VariosArquivos.java
javac -encoding ISO-8859-1 -source 9 ExtraiPapelSeriesHistoricasB3.java

Pode-se ver as instruções de uso ao chamar os programas, com o comando (no Prompt):

D:\>java ExtraiPapelSeriesHistoricasB3

Uso: java ExtraiPapelSeriesHistoricasB3 <ARQUIVO-COTAHIST_AAAA.TXT> <PAPEIS> [NOMEARQUIVOSAIDA]
Extrai de um arquivo no formato B3 de séries históricas de cotações, uma lista com alguns papéis.
<PAPEIS> pode ser um ticker específico, ou pode ser uma lista de papéis separados por ; Ex: BBAS3;KNRI11;BBDC4
Gera um arquivo texto separado por ; com o nome do papel, ou com o nome passado no parâmetro NOMEARQUIVOSAIDA.
Argumentos inválidos.

D:\>java ExtraiPapelSeriesHistoricasB3VariosArquivos

Uso: java ExtraiPapelSeriesHistoricasB3VariosArquivos <ARQUIVO-COTAHIST>AAAA.txt <PAPEIS> [NOMEARQUIVOSAIDA]
Extrai de vários arquivos (onde AAAA é o ano) no formato B3 de séries históricas de cotações, uma lista com alguns papéis.
<PAPEIS> pode ser um ticker específico, ou pode ser uma lista de papéis separados por ; Ex: BBAS3;KNRI11;BBDC4
Gera um arquivo texto separado por ; com o nome do papel, ou com o nome passado no parâmetro NOMEARQUIVOSAIDA.
Argumentos inválidos.

Exemplo 1:

Para extrair os dados do Itaú (ITUB4 e ITUB3) do arquivo baixado da B3 do ano de 2023, pode-se usar o comando:

java ExtraiPapelSeriesHistoricasB3 COTAHIST_A2012.TXT ITUB3;ITUB4 Cotacoes.csv

E é gerado um arquivo de nome Cotacoes.csv, com a estrutura abaixo:

Papel;Data;PrecoAbertura;PrecoFechamento;PrecoMaximo;PrecoMinimo;PrecoMedio;PrecoMelhorOfertaCompra;PrecoMelhorOfertaVenda;NrNegocios;QtdeTotalPapeisNegociados;VolumeNegociado;
ITUB3;02/01/2012;00000000027,63;00000000028,21;00000000028,70;00000000027,63;00000000027,95;00000000028,21;00000000028,30;00634;000000000000217000;0000000006065600,00;
ITUB4;02/01/2012;00000000034,22;00000000034,03;00000000034,25;00000000033,50;00000000033,91;00000000034,03;00000000034,20;07192;000000000003403800;0000000115443466,00;
ITUB3;03/01/2012;00000000027,99;00000000028,71;00000000028,81;00000000027,99;00000000028,44;00000000028,71;00000000028,78;00946;000000000000229000;0000000006514790,00;
ITUB4;03/01/2012;00000000034,08;00000000034,88;00000000034,88;00000000034,08;00000000034,60;00000000034,88;00000000034,89;16083;000000000006431300;0000000222581487,00;
(...)

Exemplo 2:

Para extrair os dados de cotações do fundo imobiliário KNRI11 dos últimos cinco anos, pode-se usar o comando:

java ExtraiPapelSeriesHistoricasB3VariosArquivos COTAHIST_A KNRI11 CotacoesFII.csv

Sendo que você já deveria ter baixado e disponibilizado os arquivos dos anos seguintes abaixo, na mesma pasta:
COTAHIST_A2019.TXT
COTAHIST_A2020.TXT
COTAHIST_A2021.TXT
COTAHIST_A2022.TXT
COTAHIST_A2023.TXT
COTAHIST_A2024.TXT

Atualmente os dados da B3 podem ser baixados neste link, nas séries anuais: https://www.b3.com.br/pt_br/market-data-e-indices/servicos-de-dados/market-data/historico/mercado-a-vista/series-historicas/
