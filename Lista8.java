
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class ArquivoTextoLeitura {

    private BufferedReader entrada;

    ArquivoTextoLeitura(String nomeArquivo) {
        try {
            entrada = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException excecao) {
            System.out.println("Arquivo não encontrado: " + nomeArquivo);
        }
    }

    public void fecharArquivo() {
        if (entrada != null) {
            try {
                entrada.close();
            } catch (IOException excecao) {
                System.out.println("Erro no fechamento do arquivo de leitura: " + excecao);
            }
        }
    }

    public String ler() {
        String textoEntrada = null;
        if (entrada != null) {
            try {
                textoEntrada = entrada.readLine();
            } catch (IOException excecao) {
                System.out.println("Erro de leitura: " + excecao);
            }
        }
        return textoEntrada;
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

}

class Celula {

    Livro livro;
    Celula prox;

    public Celula(Livro livro) {
        this.livro = livro;
        this.prox = null;
    }
}

class Fila {

    private Celula inicio;
    private Celula fim;
    private float somaNota;

    public Fila() {
        inicio = null;
        fim = null;
    }

    public void enfileirar(Livro livro) {
        Celula nova = new Celula(livro);

        if (inicio == null) {
            inicio = fim = nova;
        } else {
            fim.prox = nova;
            fim = nova;
        }

        somaNota += livro.getNota_avaliacao();

        MyIO.println((int)Math.round(somaNota));
    }

     Livro desenfileirar() {
        if (inicio == fim) {
            return null;
        }

        Livro livro = inicio.livro;
        inicio = inicio.prox;

        if (inicio == null) {
            fim = null;
        }

        somaNota -= livro.getNota_avaliacao();

        return livro;
    }

    void desenfileraTexto(Livro livro) {
        if (livro.getSegundo_autor().equals("")) {
            MyIO.println("(D) [" + livro.getCategoria() + "] [" + livro.getNota_avaliacao() + "] ["
                    + livro.getQuantidade_avaliacoes() + "] " + livro.getAutor_principal() + ". "
                    + livro.getTitulo() + ". " + livro.getAno_publicacao() + ". ISBN: " + livro.getISBN() + ".");
        } else {
            MyIO.println("(D) [" + livro.getCategoria() + "] [" + livro.getNota_avaliacao() + "] ["
                    + livro.getQuantidade_avaliacoes() + "] " + livro.getAutor_principal() + ", "
                    + livro.getSegundo_autor() + ". " + livro.getTitulo() + ". " + livro.getAno_publicacao() + ". ISBN: " + livro.getISBN() + ".");
        }
    }

    int obterSomaDaNotasMedia() {
        somaNota = 0; 
        for (Celula i = inicio; i != fim; i = i.prox) {
            somaNota += i.livro.getNota_avaliacao();
        }
        return Math.round(somaNota);
    }

    void acharLivro(String linha, Livro[] livros) {
        String[] info = linha.split(";");

        for (int j = 0; j < livros.length; j++) {
            if (livros[j].getTitulo().equals(info[0]) && livros[j].getAno_publicacao() == Integer.parseInt(info[1]) && livros[j].getAutor_principal().equals(info[2])) {
                enfileirar(livros[j]);
                MyIO.println(obterSomaDaNotasMedia());
                break;
            }
        }
    }

    void mostrar() {
        if (inicio == fim) {
            MyIO.println("Fila vazia");
            return;
        }
        int i = 0;
        for (Celula tmp = inicio.prox; tmp != null; tmp = tmp.prox) {

            if (tmp.livro.getSegundo_autor().equals(" ")) {
                MyIO.println("[" + i + "][" + tmp.livro.getCategoria() + "] [" + tmp.livro.getNota_avaliacao() + "] ["
                        + tmp.livro.getQuantidade_avaliacoes() + "] " + tmp.livro.getAutor_principal() + ". "
                        + tmp.livro.getSegundo_autor() + ". " + tmp.livro.getTitulo() + ". " + tmp.livro.getAno_publicacao()
                        + ". ISBN: " + tmp.livro.getISBN() + ".");
                        
            } else {
                MyIO.println("[" + i + "][" + tmp.livro.getCategoria() + "] [" + tmp.livro.getNota_avaliacao() + "] ["
                        + tmp.livro.getQuantidade_avaliacoes() + "] " + tmp.livro.getAutor_principal() + ". " + tmp.livro.getTitulo() + ". " + tmp.livro.getAno_publicacao()
                        + ". ISBN: " + tmp.livro.getISBN() + ".");
            }
            i++;
        }
    }

}

class Lista8 {

    public static void main(String[] args) {
        String linha;

        // contar
        ArquivoTextoLeitura txt_1 = new ArquivoTextoLeitura("/tmp/livros.txt");
        int qtdLivro = 0;
        do {
            linha = txt_1.ler();
            if (linha != null) {
                qtdLivro++;
            }
        } while (linha != null);

        ArquivoTextoLeitura txt_2 = new ArquivoTextoLeitura("/tmp/livros.txt");
        Livro livrosTxt[] = new Livro[qtdLivro];

        // preencher livrotxt
        linha = txt_2.ler();
        for (int j = 0; linha != null; j++) {
            Livro livro = new Livro();
            livro.ler(linha);
            livrosTxt[j] = livro;
            linha = txt_2.ler();
        }

        // fila
        Fila fila = new Fila();

        // prencher fila
        do {
            linha = MyIO.readLine();
            fila.acharLivro(linha, livrosTxt);
        } while (!linha.equals("FIM"));

        //D e E
        int operacoes = MyIO.readInt();

        for (int j = 0; j < operacoes; j++) {
            linha = MyIO.readLine();

            String[] dados = linha.split(" ", 2);

            if (dados[0].equals("D")) {
                Livro livro = fila.desenfileirar();
                fila.desenfileraTexto(livro);

            }

            if (dados[0].equals("E")) {
                fila.acharLivro(dados[1], livrosTxt);
            }
        }

        fila.mostrar();

    }
}
