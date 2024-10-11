import os.path
from datetime import datetime

def extrair_cotacoes(nome_arquivo, ativos, nome_arquivo_saida):
    file1 = open(nome_arquivo, 'r')
    file2 = open(nome_arquivo_saida, 'a')
    
    cabecalho = file1.readline()
    if cabecalho.startswith('00COTAHIST'): 
        while True:
            linha = file1.readline()
            if not linha:
                break

            #print(linha)

            #fim de arquivo
            if linha.startswith('99COTAHIST'): 
                break;

            codigo = linha[12:24].strip();
            if codigo in ativos:
                linha_a_escrever = parse_linha(linha)
                file2.write(linha_a_escrever)
                #print('WRITING: ' + linha_a_escrever)
    else:
        print('Formato de arquivo inválido.')

    file1.close()
    file2.close()


def extrair_cotacoes_arquivo(nome_arquivo, ativos, nome_arquivo_saida):
    #cria arquivo novo de saida e escreve cabecalho
    file2 = open(nome_arquivo_saida, 'w')
    file2.write('Papel;Data;PrecoAbertura;PrecoFechamento;PrecoMaximo;PrecoMinimo;PrecoMedio;PrecoMelhorOfertaCompra;PrecoMelhorOfertaVenda;NrNegocios;QtdeTotalPapeisNegociados;VolumeNegociado;\n');
    file2.close()
    extrair_cotacoes(nome_arquivo, ativos, nome_arquivo_saida)
    print('Finished')


def extrair_cotacoes_varios_arquivos(nome_base, ano_inicial, ativos, nome_arquivo_saida):
    #cria arquivo novo de saida e escreve cabecalho
    file2 = open(nome_arquivo_saida, 'w')
    file2.write('Papel;Data;PrecoAbertura;PrecoFechamento;PrecoMaximo;PrecoMinimo;PrecoMedio;PrecoMelhorOfertaCompra;PrecoMelhorOfertaVenda;NrNegocios;QtdeTotalPapeisNegociados;VolumeNegociado;\n');
    file2.close()

    ano = ano_inicial
    ano_atual = datetime.now().year
    while ano <= ano_atual:
        print('Processando ano ' + str(ano))
        nome_arquivo = nome_base + str(ano) + '.txt'
        extrair_cotacoes(nome_arquivo, ativos, nome_arquivo_saida)
        ano = ano + 1

    print('Finished')


def parse_linha(linha):
    data = linha[2:10]
    data =  data[6:8] + '/' + data[4:6] + '/' + data[0:4] 
    codigo = linha[12:24].strip();
    
    abertura = linha[56:67] + ',' + linha[67:69]
    maximo = linha[69:80] + ',' + linha[80:82]
    minimo = linha[82:93] + ',' + linha[93:95]
    medio = linha[95:106] + ',' + linha[106:108]
    fechamento = linha[108:119] + ',' + linha[119:121]
    melhor_oferta_compra = linha[121:132] + ',' + linha[132:134]
    melhor_oferta_venda = linha[134:145] + ',' + linha[145:147]
    negocios = linha[147:152]
    papeis_negociados = linha[152:170]
    volume = linha[170:186] + ',' + linha[186:188]
    
    to_write = codigo + ';' + data + ';' + abertura + ';' + fechamento + ';' + maximo + ';' + minimo + ';' + medio + ';' 
    to_write = to_write + melhor_oferta_compra + ';' + melhor_oferta_venda + ';' + negocios + ';' + papeis_negociados + ';' + volume + ';\n';
    return to_write;


#Exemplos de chamadas:
#extrair_cotacoes_arquivo('D:\\COTAHIST_A2012.TXT', ['ABCP11', 'KNRI11'], 'D:\\resultados3.csv')
extrair_cotacoes_varios_arquivos('D:\\COTAHIST_A', 2019, 'KNRI11', 'D:\\resultados4.csv')
#
#Para executar, basta modificar alguma dessas chamadas de função em uma nova linha sem # 
#Salvar o arquivo e executar o comando py ExtraiPapelSeriesHistoricasB3.py
#
