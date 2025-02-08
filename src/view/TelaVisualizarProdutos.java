package src.view;

import model.Produto;
import src.dao.ProdutoDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

public class TelaVisualizarProdutos extends JDialog {
    private JPanel contentPane;
    private JTable table1;
    private JButton excluirButton;
    private JButton salvarButton;
    private JButton buttonOK;
    private ProdutoDao produtoDao;

    public TelaVisualizarProdutos() {
        produtoDao = new ProdutoDao();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        salvarButton.addActionListener(e -> {

        });

        excluirButton.addActionListener(e -> {
            int selectedRow = table1.getSelectedRow();

            // Verifica se uma linha foi selecionada
            if (selectedRow != -1) {
                // Remove a linha do modelo
                int produtoId = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());


                try {
                    produtoDao.removerProduto(produtoId);
                    ((DefaultTableModel) table1.getModel()).removeRow(selectedRow);
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }


            } else {
                JOptionPane.showMessageDialog(TelaVisualizarProdutos.this, "Nenhuma linha selecionada!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void createUIComponents() {
        ProdutoDao produtoDao = new ProdutoDao();
        table1 = new JTable();

        try {
            produtoDao.adicionarProduto(new Produto(1,"Arroz vencido", 15, LocalDate.now()));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Set<Produto> produtos;
        String titles[] = {"Codigo", "Descricao", "Preco", "Validade"};

        try {
            produtos = produtoDao.getProdutos();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (produtos != null) {
            // Inicializando corretamente a matriz dados
            String dados[][] = new String[produtos.size()][4]; // 4 colunas

            int linha = 0;
            for (Produto produto : produtos) {
                // Garantindo que cada linha da matriz seja inicializada
                dados[linha][0] = String.valueOf(produto.getId());
                dados[linha][1] = produto.getDescricao();
                dados[linha][2] = String.valueOf(produto.getPreco());
                dados[linha][3] = produto.getValidade().toString();
                linha++;
            }

            DefaultTableModel model = new DefaultTableModel(dados, titles);

            table1.setModel(model);// Criando JTable corretamente
        } else {

            table1.setModel(new DefaultTableModel(new String[][]{}, titles));
        }

        salvarButton = new JButton();
        ImageIcon icon2 = new ImageIcon("img/salvar.png");
        salvarButton.setIcon(icon2);

        excluirButton = new JButton();
        ImageIcon icon3 = new ImageIcon("img/lixeira.png");
        excluirButton.setIcon(icon3);

    }

}
