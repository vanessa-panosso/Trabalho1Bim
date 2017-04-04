package br.univel.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import br.univel.comum.Arquivo;
import br.univel.comum.Cliente;
import br.univel.comum.IServer;
import br.univel.comum.ImplServidor;
import br.univel.comum.ResultadoModel;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import br.univel.comum.TipoFiltro;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public TelaPrincipal() throws RemoteException {
		setBackground(Color.LIGHT_GRAY);
		Cliente cliente = new Cliente();
		IServer servidor = new ImplServidor();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 351);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 14, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 6;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNome = new JLabel("Nome:");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 0;
		panel.add(lblNome, gbc_lblNome);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 5;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblIp = new JLabel("IP:");
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.anchor = GridBagConstraints.EAST;
		gbc_lblIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblIp.gridx = 0;
		gbc_lblIp.gridy = 1;
		panel.add(lblIp, gbc_lblIp);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 2;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		panel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblPorta = new JLabel("Porta:");
		GridBagConstraints gbc_lblPorta = new GridBagConstraints();
		gbc_lblPorta.anchor = GridBagConstraints.EAST;
		gbc_lblPorta.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorta.gridx = 4;
		gbc_lblPorta.gridy = 1;
		panel.add(lblPorta, gbc_lblPorta);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 5;
		gbc_textField_2.gridy = 1;
		panel.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					servidor.registrarCliente(cliente);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnConectar = new GridBagConstraints();
		gbc_btnConectar.anchor = GridBagConstraints.WEST;
		gbc_btnConectar.insets = new Insets(0, 0, 0, 5);
		gbc_btnConectar.gridx = 1;
		gbc_btnConectar.gridy = 2;
		panel.add(btnConectar, gbc_btnConectar);
		
		JButton btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					servidor.desconectar(cliente);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnDesconectar = new GridBagConstraints();
		gbc_btnDesconectar.insets = new Insets(0, 0, 0, 5);
		gbc_btnDesconectar.gridx = 2;
		gbc_btnDesconectar.gridy = 2;
		panel.add(btnDesconectar, gbc_btnDesconectar);
		
		JButton btnIniciarServidor = new JButton("Iniciar Servidor");
		GridBagConstraints gbc_btnIniciarServidor = new GridBagConstraints();
		gbc_btnIniciarServidor.gridx = 5;
		gbc_btnIniciarServidor.gridy = 2;
		panel.add(btnIniciarServidor, gbc_btnIniciarServidor);
		
		JLabel lblPesquisarPor = new JLabel("Pesquisar por:");
		GridBagConstraints gbc_lblPesquisarPor = new GridBagConstraints();
		gbc_lblPesquisarPor.insets = new Insets(0, 0, 5, 5);
		gbc_lblPesquisarPor.anchor = GridBagConstraints.EAST;
		gbc_lblPesquisarPor.gridx = 0;
		gbc_lblPesquisarPor.gridy = 2;
		contentPane.add(lblPesquisarPor, gbc_lblPesquisarPor);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(TipoFiltro.values()));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 2;
		contentPane.add(comboBox, gbc_comboBox);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.gridwidth = 3;
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 2;
		gbc_textField_3.gridy = 2;
		contentPane.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		GridBagConstraints gbc_btnPesquisar = new GridBagConstraints();
		gbc_btnPesquisar.insets = new Insets(0, 0, 5, 0);
		gbc_btnPesquisar.gridx = 5;
		gbc_btnPesquisar.gridy = 2;
		contentPane.add(btnPesquisar, gbc_btnPesquisar);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		scrollPane.setRowHeaderView(table);
		
		JButton btnDownload = new JButton("Download");
		GridBagConstraints gbc_btnDownload = new GridBagConstraints();
		gbc_btnDownload.insets = new Insets(0, 0, 5, 0);
		gbc_btnDownload.gridx = 5;
		gbc_btnDownload.gridy = 3;
		contentPane.add(btnDownload, gbc_btnDownload);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 6;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 6;
		contentPane.add(scrollPane_1, gbc_scrollPane_1);
		
		JList list = new JList();
		list.setForeground(new Color(0, 204, 51));
		list.setBackground(new Color(0, 0, 0));
		scrollPane_1.setColumnHeaderView(list);
	}
	protected void carregar() {
		Map<Cliente, List<Arquivo>> dados = gerarDados();
		
		ResultadoModel modelo = new ResultadoModel(dados);
		
		table.setModel((TableModel) modelo);
		
	}

	private Map<Cliente, List<Arquivo>> gerarDados() {

		Map<Cliente, List<Arquivo>> dados = new HashMap<>();
		
		for (int c = 1; c <= 100; c++) {
			
			Cliente cli = new Cliente();
			cli.setId(c);
			cli.setNome("Cliente " + c);
			
			List<Arquivo> lista = new ArrayList<>();
			for (int a = 1; a <= 100; a++) {
				Arquivo arq = new Arquivo();
				arq.setId(1);
				arq.setNome("Arquivo " + a);
				
				lista.add(arq);
			}
			
			dados.put(cli, lista);
		}
		
		return dados;
	}
}
