package br.univel.exemplos.tabela;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JScrollPane;

import java.awt.GridBagConstraints;

import javax.swing.JTable;
import javax.swing.JButton;

import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TelaTabela extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaTabela frame = new TelaTabela();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaTabela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JButton btnNewButton = new JButton("Preenche");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preencheTabela();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		contentPane.add(btnNewButton, gbc_btnNewButton);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerSelecionado();
			}
		});
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemove.gridx = 0;
		gbc_btnRemove.gridy = 1;
		contentPane.add(btnRemove, gbc_btnRemove);

		JButton btnAdiciona = new JButton("Adiciona");
		btnAdiciona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarDoAlem();
			}
		});
		GridBagConstraints gbc_btnAdiciona = new GridBagConstraints();
		gbc_btnAdiciona.insets = new Insets(0, 0, 5, 0);
		gbc_btnAdiciona.gridx = 0;
		gbc_btnAdiciona.gridy = 2;
		contentPane.add(btnAdiciona, gbc_btnAdiciona);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		contentPane.add(scrollPane, gbc_scrollPane);

		table = new JTable() {

			@Override
			public String getToolTipText(MouseEvent e) {
				
				String tip = null;
                
				Point p = e.getPoint();
                
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);
                
                if (rowIndex == -1 || colIndex == -1) {
                	return null;
                }

                try {
                    tip = getValueAt(rowIndex, colIndex).toString();
                } catch (RuntimeException e1) {
                	
                }

                return tip;
				
			}
			
		};
		
		scrollPane.setViewportView(table);

		// final
		configuraTabela();
	}

	protected void adicionarDoAlem() {

		Cliente c = new Cliente(123, "Do além!");
		
		
		((ClienteModel)table.getModel()).adicionarCliente(c);
		
	}

	protected void removerSelecionado() {
		Cliente c = getClienteSelecionadoNaTabela();
		if (c != null) {
			((ClienteModel)table.getModel()).removerCliente(c);
		}

	}

	private void configuraTabela() {
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {
					Cliente c = getClienteSelecionadoNaTabela();
					if (c != null) {
						JOptionPane.showMessageDialog(null,
								"Cliente: " + c.toString());
					}

				}
			}

		});

	}

	protected void preencheTabela() {

		List<Cliente> lista = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			lista.add(new Cliente(i, "Cliente " + i));
		}

		ClienteModel model = new ClienteModel(lista);
		table.setModel(model);

	}

	private Cliente getClienteSelecionadoNaTabela() {
		Cliente c = null;
		int index = table.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!");
		} else {
			c = ((ClienteModel) table.getModel()).getClienteNaLinha(index);
		}
		return c;
	}

}
