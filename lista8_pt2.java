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

}


class Celula {
    Livro livro;
    Celula prox;

    public Celula(Livro livro) {
        this.livro = livro;
        this.prox = null;
    }
}

class FilaDinamica {
    private Celula inicio;
    private Celula fim;
    private int totalElementos;
    private double somaNota;

    public FilaDinamica() {
        inicio = null;
        fim = null;
        totalElementos = 0;
        somaNota = 0.0;
    }

    public void enfileirar(Livro livro) {
        Celula nova = new Celula(livro);
        if (inicio == null) {
            inicio = fim = nova;
        } else {
            fim.prox = nova;
            fim = nova;
        }
        totalElementos++;
        somaNota += livro.getNota_avaliacao();

        MyIO.println((int) Math.round(somaNota));
    }

    public Livro desenfileirar() {
        if (inicio == null) {
            throw new RuntimeException("Fila vazia");
        }
        Livro livro = inicio.livro;
        inicio = inicio.prox;
        if (inicio == null) {
            fim = null;
        }
        totalElementos--;
        somaNota -= livro.getNota_avaliacao();
        return livro;
    }

    public void mostrar() {
        if (inicio == null) {
            MyIO.println("Fila vazia");
            return;
        }
        Celula tmp = inicio;
        int i = 0;
        while (tmp != null) {
            Livro livro = tmp.livro;
            if (livro.getSegundo_autor().equals(" ")) {
                MyIO.println("[" + i + "] [" + livro.getCategoria() + "] [" + livro.getNota_avaliacao() + "] ["
                        + livro.getQuantidade_avaliacoes() + "] " + livro.getAutor_principal() + ". "
                        + livro.getSegundo_autor() + ". " + livro.getTitulo() + ". " + livro.getAno_publicacao()
                        + ". ISBN:" + livro.getISBN());
            } else {
                MyIO.println("[" + i + "] [" + livro.getCategoria() + "] [" + livro.getNota_avaliacao() + "] ["
                        + livro.getQuantidade_avaliacoes() + "] " + livro.getAutor_principal() + ". " + livro.getTitulo() + ". " + livro.getAno_publicacao()
                        + ". ISBN:" + livro.getISBN());
            }
            tmp = tmp.prox;
            i++;
        }
    }

    public int getSomaMedia() {
        return (int) Math.round(somaNota);
    }

    public int getQtdElementos() {
        return totalElementos;
    }
}

class lista8_pt2 {
    static void acharLivro(String linha, Livro[] livros, FilaDinamica fila) {
        String[] info = linha.split(";");
        for (Livro livro : livros) {
            if (livro.getTitulo().equals(info[0]) &&
                livro.getAno_publicacao() == Integer.parseInt(info[1]) &&
                livro.getAutor_principal().equals(info[2])) {
                fila.enfileirar(livro);
            }
        }
    }

    public static void main(String[] args) {
        String linha;
        ArquivoTextoLeitura txt_1 = new ArquivoTextoLeitura("livros.txt");
        int qtdLivro = 0;
        while ((linha = txt_1.ler()) != null) {
            qtdLivro++;
        }
        txt_1.fecharArquivo();

        ArquivoTextoLeitura txt_2 = new ArquivoTextoLeitura("livros.txt");
        Livro livrosTxt[] = new Livro[qtdLivro];
        int i = 0;

        while ((linha = txt_2.ler()) != null && !linha.equals("FIM")) {
            Livro livro = new Livro();
            livro.ler(linha);
            livrosTxt[i++] = livro;
        }
        txt_2.fecharArquivo();

        FilaDinamica fila = new FilaDinamica();
        linha = MyIO.readLine();

        while (!linha.equals("FIM")) {
            acharLivro(linha, livrosTxt, fila);
            linha = MyIO.readLine();
        }

        int operacoes = MyIO.readInt();
        for (int j = 0; j < operacoes; j++) {
            linha = MyIO.readLine();
            String[] dados = linha.split(" ", 2);

            if (dados[0].equals("D")) {
                if (fila.getQtdElementos() == 0) {
                    MyIO.println("Fila vazia");
                } else {
                    Livro livro = fila.desenfileirar();
                    MyIO.println("(D) [" + livro.getCategoria() + "] [" + livro.getNota_avaliacao() + "] ["
                            + livro.getQuantidade_avaliacoes() + "] [" + livro.getAutor_principal() + "], ["
                            + livro.getSegundo_autor() + "]. [" + livro.getTitulo() + "]. [" + livro.getAno_publicacao()
                            + "]. ISBN:[" + livro.getISBN() + "].");
                }
            }

            if (dados[0].equals("E")) {
                acharLivro(dados[1], livrosTxt, fila);
            }
        }

        fila.mostrar();
    }
}
