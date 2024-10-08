import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExtraiPapelSeriesHistoricasB3 {
    
    public static void main(String[] args) {
        System.out.println("Uso: java ExtraiPapelSeriesHistoricasB3 <ARQUIVO-COTAHIST_AAAA.TXT> <PAPEIS> [NOMEARQUIVOSAIDA]");
        System.out.println("Extrai de um arquivo no formato B3 de séries históricas de cotações, uma lista com alguns papéis.");
        System.out.println("<PAPEIS> pode ser um ticker específico, ou pode ser uma lista de papéis separados por ; Ex: BBAS3;KNRI11;BBDC4");
        System.out.println("Gera um arquivo texto separado por ; com o nome do papel, ou com o nome passado no parâmetro NOMEARQUIVOSAIDA.");
        
        String arqB3 = null, arqOut = null, argPapel = null, papeis[] = null;
        try {
            arqB3 = args[0];
            argPapel = args[1].trim();
            if (args.length > 2) {
                arqOut = args[2];
            }
            
            String[] aux = argPapel.split(";");
            int count = 0;
            for (String pap: aux) {
                if (pap != null && !pap.equals("")) {
                    count++;
                }
            }
            papeis = new String[count];
            count = 0;
            for (String pap: aux) {
                if (pap != null && !pap.equals("")) {
                    papeis[count] = pap;
                    count++;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Argumentos inválidos.");
            return;
        }
        
        FileWriter writer = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(arqB3)))) {            
            String cabecalho = reader.readLine();
            if (cabecalho.startsWith("00COTAHIST")) {
                String ano = cabecalho.substring(11, 15);
                if (arqOut == null) {
                    arqOut = argPapel.replace(";", "") + ano + ".csv";
                }
                    
                writer = new FileWriter(arqOut);
                //cabecalho de saida
                writer.write("Papel;Data;PrecoAbertura;PrecoFechamento;PrecoMaximo;PrecoMinimo;PrecoMedio;PrecoMelhorOfertaCompra;PrecoMelhorOfertaVenda;NrNegocios;QtdeTotalPapeisNegociados;VolumeNegociado;\n");
                    
                    String linha = reader.readLine();
                    while (linha != null && !linha.startsWith("99COTAHIST.")) {
                        String codigo = linha.substring(12, 24).trim();
                        boolean selecionado = false;
                        for (String p: papeis) {
                            if (p.equalsIgnoreCase(codigo)) {
                                selecionado = true;
                                break;
                            }
                        }
                        if (selecionado) {
                            String data = linha.substring(2, 10);
                            data =  data.substring(6, 8) + "/" + data.substring(4, 6) + "/" + data.substring(0, 4); 
                        
                        String abertura = linha.substring(56, 67) + "," + linha.substring(67, 69);
                        String maximo = linha.substring(69, 80) + "," + linha.substring(80, 82);
                        String minimo = linha.substring(82, 93) + "," + linha.substring(93, 95);
                        String medio = linha.substring(95, 106) + "," + linha.substring(106, 108);
                        String fechamento = linha.substring(108, 119) + "," + linha.substring(119, 121);
                        String melhorOfertaCompra = linha.substring(121, 132) + "," + linha.substring(132, 134);
                        String melhorOfertaVenda = linha.substring(134, 145) + "," + linha.substring(145, 147);
                        String negocios = linha.substring(147, 152);
                        String papeisNegociados = linha.substring(152, 170);
                        String volume = linha.substring(170, 186) + "," + linha.substring(186, 188);
                        
                        String toWrite = codigo + ";" + data + ";" + abertura + ";" + fechamento + ";" + maximo + ";" + minimo + ";" + medio + ";" + 
                        melhorOfertaCompra + ";" + melhorOfertaVenda + ";" + negocios + ";" + papeisNegociados + ";" + volume + ";\n";
                        writer.write(toWrite);
                    }                                
                    linha = reader.readLine();
                }
                
                reader.close();
                writer.close();
            }
            else {
                System.out.println("Formato arquivo inválido.");
                return;
            }            
        }
        catch (Exception e) {
            try { if (writer != null) writer.close(); } catch (IOException e1) { e1.printStackTrace(); }
            e.printStackTrace();
        }
        
        System.out.println("Terminado.");
    }

}
