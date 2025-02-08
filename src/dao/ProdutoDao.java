package src.dao;

import model.Produto;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class ProdutoDao {

    //TODO: Fazer o método de atualizar e refatorar

    private File arquivo;

    public ProdutoDao() {
        arquivo = new File("Produtos");
        if(!arquivo.exists()){
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean adicionarProduto(Produto produto) throws IOException,
            ClassNotFoundException {
        Set<Produto> produtos = getProdutos();
        if(produtos.add(produto)){
            atualizarArquivo(produtos);
        }
        return false;
    }

    public boolean removerProduto(int produtoId) throws IOException,
            ClassNotFoundException {
        Set<Produto> produtos = getProdutos();
        Produto produtoEncontrado = getProdutoById(produtos, produtoId);
        if(produtoEncontrado != null){
            produtos.remove(produtoEncontrado);
            atualizarArquivo(produtos);
        }
        return false;
    }

    public Produto getProdutoById(Set<Produto> produtos, int produtoId){
        AtomicReference<Produto> produtoEncontrado = new AtomicReference<>(null);
        produtos.stream()
                .filter(produto -> produto.getId() == produtoId)
                .findFirst().ifPresent(produtoEncontrado::set);
        return produtoEncontrado.get();
    }

    public boolean atualizar(Produto produto) throws IOException,
            ClassNotFoundException {
        Set<Produto> produtos = getProdutos();
        if(produtos.contains(produto)){
            if(produtos.remove(produto) && produtos.add(produto)){
                atualizarArquivo(produtos);
                return true;
            }
        }
        return false;
    }

    private void atualizarArquivo(Set<Produto> produtos)
            throws IOException {
        try(ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(arquivo))){
            out.writeObject(produtos);
        }
    }

    public Set<Produto> getProdutos() throws IOException,
            ClassNotFoundException {
        if(arquivo.length()==0){
            return new HashSet<>();
        }
        try(ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(arquivo))){
            return (Set<Produto>) in.readObject();
        }
    }

}
