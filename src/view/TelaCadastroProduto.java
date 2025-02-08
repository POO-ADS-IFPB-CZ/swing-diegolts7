package src.view;

import model.Produto;
import src.dao.ProdutoDao;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;

public class TelaCadastroProduto extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel label1;
    private JTextField campoCodigo;
    private JTextField campoDescricao;
    private JTextField campoPreco;
    private JTextField campoValidade;
    private JButton listarButton;
    private final ProdutoDao produtoDao;

    public TelaCadastroProduto() {
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cadastro de Produtos");
        setSize(600,600);
        setLocationRelativeTo(null);
        this.produtoDao = new ProdutoDao();

        ImageIcon icon = new ImageIcon("img/supermercado.png");
        setIconImage(icon.getImage());
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> {
            String codigoString = campoCodigo.getText();
            int codigo = Integer.parseInt(codigoString);
            String descricao = campoDescricao.getText();
            String precoString = campoPreco.getText();
            float preco = Float.parseFloat(precoString);
            String validadeString = campoValidade.getText();
            LocalDate validade = LocalDate.parse(validadeString);

            Produto produto = new Produto(codigo, descricao, preco,
                    validade);
            try {
                this.produtoDao.adicionarProduto(produto);
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        });
        listarButton.addActionListener(e-> {
            TelaVisualizarProdutos telaVisualizarProdutos = new TelaVisualizarProdutos();
            //dispose();
            telaVisualizarProdutos.pack();
            telaVisualizarProdutos.setLocationRelativeTo(null);
            telaVisualizarProdutos.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaCadastroProduto tela = new TelaCadastroProduto();
            tela.setVisible(true);
        });
    }

    private void createUIComponents() {
        ImageIcon icon = new ImageIcon("img/supermercado.png");
        label1 = new JLabel();
        label1.setIcon(icon);

        buttonOK = new JButton();
        ImageIcon icon2 = new ImageIcon("img/salvar.png");
        buttonOK.setIcon(icon2);

        ImageIcon iconListar = new ImageIcon("img/listaIcon.png");
        listarButton = new JButton();
        listarButton.setIcon(iconListar);
    }
}
