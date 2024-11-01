import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class ArquivoTextoLeitura {

   private BufferedReader entrada;

   ArquivoTextoLeitura(String nomeArquivo) {

      try {
         entrada = new BufferedReader(new FileReader(nomeArquivo));
      } catch (FileNotFoundException excecao) {
         System.out.println("Arquivo nao encontrado");
      }
   }

   public void fecharArquivo() {

      try {
         entrada.close();
      } catch (IOException excecao) {
         System.out.println("Erro no fechamento do arquivo de leitura: " + excecao);
      }
   }

   @SuppressWarnings("finally")
   public String ler() {

      String textoEntrada = null;

      try {
         textoEntrada = entrada.readLine();
      } catch (EOFException excecao) { // Excecao de final de arquivo.
         textoEntrada = null;
      } catch (IOException excecao) {
         System.out.println("Erro de leitura: " + excecao);
         textoEntrada = null;
      } finally {
         return textoEntrada;
      }
   }
}

class Livro {

   private long ISBN;
   private String titulo;
   private String autor_principal;
   private String segundo_autor;
   private String categoria;
   private String descricao;
   private int ano_publicacao;
   private int quantidade_paginas;
   private float nota_avaliacao;
   private int quantidade_avaliacoes;

   void setISBN(long x) {
      this.ISBN = x;
   }

   void setTitulo(String x) {
      this.titulo = x;
   }

   void setAutor_principal(String x) {
      this.autor_principal = x;
   }

   void setSegundo_autor(String x) {
      this.segundo_autor = x;
   }

   void setCategoria(String x) {
      this.categoria = x;
   }

   void setDescricao(String x) {
      this.descricao = x;
   }

   void setAno_publicacao(int x) {
      this.ano_publicacao = x;
   }

   void setQuantidade_paginas(int x) {
      this.quantidade_paginas = x;
   }

   void setNota_avaliacao(float x) {
      this.nota_avaliacao = x;
   }

   void setQuantidade_avaliacoes(int x) {
      this.quantidade_avaliacoes = x;
   }

   long getISBN() {
      return this.ISBN;
   }

   String getTitulo() {
      return this.titulo;
   }

   String getAutor_principal() {
      return this.autor_principal;
   }

   String getSegundo_autor() {
      return this.segundo_autor;
   }

   String getCategoria() {
      return this.categoria;
   }

   String getDescricao() {
      return this.descricao;
   }

   int getAno_publicacao() {
      return this.ano_publicacao;
   }

   int getQuantidade_paginas() {
      return this.quantidade_paginas;
   }

   float getNota_avaliacao() {
      return this.nota_avaliacao;
   }

   int getQuantidade_avaliacoes() {
      return this.quantidade_avaliacoes;
   }

   @Override
   public String toString() {
      if (this.getSegundo_autor().equals("")) {
         return "[" + this.categoria + this.quantidade_avaliacoes + "] "
               + this.autor_principal + ". " + this.titulo + ". " + this.ano_publicacao + ". ISBN: " + this.ISBN + ".";
      } else {
         return "[" + this.categoria + "] [" + "] [" + this.quantidade_avaliacoes + "] "
               + this.autor_principal + ". " + this.titulo + ". " + this.ano_publicacao + ". ISBN: " + this.ISBN + ".";
      }
   }

   public void imprimir() {
      MyIO.println(toString());
   }

   public Livro clone() {
      return new Livro(this.ISBN, this.titulo, this.autor_principal, this.segundo_autor, this.categoria,
            this.descricao, this.ano_publicacao,
            this.quantidade_paginas, this.nota_avaliacao, this.quantidade_avaliacoes);
   }

   Livro() {
   }

   Livro(long isbn, String titulo, String autor_principal, String segundo_autor, String categoria,
         String descricao, int ano_publicacao, int quantidade_paginas, float nota_avaliacao,
         int quantidade_avaliacoes) {
      this.ISBN = isbn;
      this.titulo = titulo;
      this.autor_principal = autor_principal;
      this.segundo_autor = segundo_autor;
      this.categoria = categoria;
      this.descricao = descricao;
      this.ano_publicacao = ano_publicacao;
      this.quantidade_paginas = quantidade_paginas;
      this.nota_avaliacao = nota_avaliacao;
      this.quantidade_avaliacoes = quantidade_avaliacoes;
   }

   void ler(String linha) {
      String[] info = linha.split("\\|");
      this.setISBN(Long.parseLong(info[0]));
      this.setTitulo(info[1]);
      this.setAutor_principal(info[2]);
      this.setSegundo_autor(info[3]);
      this.setCategoria(info[4]);
      this.setDescricao(info[5]);
      this.setAno_publicacao(Integer.parseInt(info[6]));
      this.setQuantidade_paginas(Integer.parseInt(info[7]));
      this.setNota_avaliacao(Float.parseFloat(info[8]));
      this.setQuantidade_avaliacoes(Integer.parseInt(info[9]));
   }

   void ler2(String linha) {
      String[] info = linha.split(";");
      this.setTitulo(info[0]);
      this.setAno_publicacao(Integer.parseInt(info[1]));
      this.setNota_avaliacao(Float.parseFloat(info[2]));
   }

}



class Celula{
   Livro livro;
   Celula prox;

   Celula(Livro livro){
      this.livro = livro;
      prox = null;
   }

   Celula(){
      prox = null;
   }

}


class Fila {
   private Celula primeiro, ultimo;
   private int totalElementos;
   private double somaNota;

    Fila() {
      primeiro = ultimo = new Celula();
      totalElementos = 0;
      somaNota = 0.0;
   }

   public void enfileirar(Livro livro) {

      ultimo.prox = new Celula(livro);
      ultimo = ultimo.prox;
      

      totalElementos++;
      somaNota += livro.getNota_avaliacao();
      MyIO.println(obterSomaDaNotaMedia());
      

   }

   public Livro desenfileirar() {
      if (primeiro == ultimo) {
         throw new RuntimeException("Fila vazia");
      }

      Livro livro = primeiro.prox.livro;

      totalElementos--;
      somaNota -= livro.getNota_avaliacao();

      return livro;
   }

   public void mostrar() {
      if (primeiro == ultimo) {
         System.out.println("Fila vazia");
         return;
      }

      int i =0;
      for (Celula tmp = primeiro; tmp != ultimo ; tmp = (tmp.prox) % totalElementos, i.pox) {
         MyIO.print("[" + i + "] [" + fila[tmp].getCategoria() + "] [" + fila[tmp].getNota_avaliacao() + "] [" +
            fila[tmp].getQuantidade_avaliacoes() + "] [" + fila[tmp].getAutor_principal() + "], [" + 
            fila[tmp].getSegundo_autor() + "]. [" + fila[tmp].getTitulo() + "[" + fila[tmp].getAno_publicacao() +
            "]. ISBN:[" + fila[tmp].getISBN() + "]");
      }
      

      }
   

   public int getSomaMedia() {
      return (int) Math.round(somaNota);
   }

   public int getQtdElementos() {
      return totalElementos;
   }

   double obterSomaDaNotaMedia (){
      return somaNota/totalElementos;
   }

}




class lista8 {

   static void acharLivro(Livro livro, Livro[] livros, Fila fila) {
      for (int i = 0; i < livros.length; i++) {
         if (livros[i].getTitulo().equals(livro.getTitulo()) &&
               livros[i].getAno_publicacao() == livro.getAno_publicacao() &&
               livros[i].getAutor_principal().equals(livro.getAutor_principal())) {

            fila.enfileirar(livros[i]);
         }
      }

   }

   public static void main(String[] args) {
      String linha;

      // contar
      ArquivoTextoLeitura txt_1 = new ArquivoTextoLeitura("/tmp/livros.txt");
      int qtdLivro = 0;
      do {
         linha = txt_1.ler();
         qtdLivro++;
      } while (linha != null);

      
      ArquivoTextoLeitura txt_2 = new ArquivoTextoLeitura("/tmp/livros.txt");

      // preencher
      Livro livrosTxt[] = new Livro[qtdLivro];

      for (int i = 0; i < qtdLivro; i++) {
         linha = txt_2.ler();
         Livro livro = new Livro();
         livro.ler(linha);
         livrosTxt[i] = livro;
      }


      // fila
      Fila fila = new Fila();

      // prencher pubin
      linha = MyIO.readLine();

      while(linha.equals("FIM")){

         Livro livro = new Livro();
         livro.ler2(linha);

         acharLivro(livro, livrosTxt, fila);

         linha = MyIO.readLine();
      }

      int operacoes = MyIO.readInt();

      for (int i = 0; i < operacoes; i++) {
         linha = MyIO.readLine();

         String[] dados = linha.split(" ");

         // desenfileirar
         if (dados[0].equals("D")) {
            Livro livro = fila.desenfileirar();

            MyIO.println("(D) [" + livro.getCategoria() + "] [" + livro.getNota_avaliacao() + "] [" +
                  livro.getQuantidade_avaliacoes() + "] [" + livro.getAutor_principal() + "], ["
                  + livro.getSegundo_autor() + "]. ["
                  + livro.getTitulo() + "]. [" + livro.getAno_publicacao() + " ]. ISBN:[" + livro.getISBN() + "].");

         }

         // enfileirar
         else if (dados[0].equals("E")) {

            Livro livro = new Livro();
            livro.ler2(dados[1]);

            acharLivro(livro, livrosTxt, fila);

         }
      }

   }

}
