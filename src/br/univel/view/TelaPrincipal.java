package br.univel.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;


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
import javax.swing.JTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import br.univel.comum.TipoFiltro;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField tf_Nome;
	private JTextField tf_Ip;
	private JTextField tf_Porta;
	private JTextField tf_Pesquisar;
	private JTextField tf_Pasta;

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
		setBounds(100, 100, 687, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 14, 0, 0, 0, 97, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNome = new JLabel("Nome:");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 0;
		panel.add(lblNome, gbc_lblNome);
		
		tf_Nome = new JTextField();
		GridBagConstraints gbc_tf_Nome = new GridBagConstraints();
		gbc_tf_Nome.gridwidth = 6;
		gbc_tf_Nome.insets = new Insets(0, 0, 5, 5);
		gbc_tf_Nome.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_Nome.gridx = 1;
		gbc_tf_Nome.gridy = 0;
		panel.add(tf_Nome, gbc_tf_Nome);
		tf_Nome.setColumns(10);
		
		JRadioButton rdbtnServidor = new JRadioButton("Cliente e Servidor");
		GridBagConstraints gbc_rdbtnServidor = new GridBagConstraints();
		gbc_rdbtnServidor.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnServidor.gridx = 7;
		gbc_rdbtnServidor.gridy = 0;
		panel.add(rdbtnServidor, gbc_rdbtnServidor);
		
		JRadioButton rdbtnCliente = new JRadioButton("Cliente");
		GridBagConstraints gbc_rdbtnCliente = new GridBagConstraints();
		gbc_rdbtnCliente.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnCliente.gridx = 8;
		gbc_rdbtnCliente.gridy = 0;
		panel.add(rdbtnCliente, gbc_rdbtnCliente);
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnServidor);
		bg.add(rdbtnCliente);

		JLabel lblIp = new JLabel("IP:");
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.anchor = GridBagConstraints.EAST;
		gbc_lblIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblIp.gridx = 0;
		gbc_lblIp.gridy = 1;
		panel.add(lblIp, gbc_lblIp);
		
		tf_Ip = new JTextField();
		GridBagConstraints gbc_tf_Ip = new GridBagConstraints();
		gbc_tf_Ip.gridwidth = 2;
		gbc_tf_Ip.insets = new Insets(0, 0, 5, 5);
		gbc_tf_Ip.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_Ip.gridx = 1;
		gbc_tf_Ip.gridy = 1;
		panel.add(tf_Ip, gbc_tf_Ip);
		tf_Ip.setColumns(10);
		
		JLabel lblPorta = new JLabel("Porta:");
		GridBagConstraints gbc_lblPorta = new GridBagConstraints();
		gbc_lblPorta.anchor = GridBagConstraints.EAST;
		gbc_lblPorta.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorta.gridx = 3;
		gbc_lblPorta.gridy = 1;
		panel.add(lblPorta, gbc_lblPorta);
		
		tf_Porta = new JTextField();
		GridBagConstraints gbc_tf_Porta = new GridBagConstraints();
		gbc_tf_Porta.insets = new Insets(0, 0, 5, 5);
		gbc_tf_Porta.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_Porta.gridx = 4;
		gbc_tf_Porta.gridy = 1;
		panel.add(tf_Porta, gbc_tf_Porta);
		tf_Porta.setColumns(10);
		
		JLabel lblPasta = new JLabel("Pasta:");
		GridBagConstraints gbc_lblPasta = new GridBagConstraints();
		gbc_lblPasta.anchor = GridBagConstraints.EAST;
		gbc_lblPasta.insets = new Insets(0, 0, 5, 5);
		gbc_lblPasta.gridx = 5;
		gbc_lblPasta.gridy = 1;
		panel.add(lblPasta, gbc_lblPasta);
		
		tf_Pasta = new JTextField();
		GridBagConstraints gbc_tf_Pasta = new GridBagConstraints();
		gbc_tf_Pasta.gridwidth = 3;
		gbc_tf_Pasta.insets = new Insets(0, 0, 5, 5);
		gbc_tf_Pasta.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_Pasta.gridx = 6;
		gbc_tf_Pasta.gridy = 1;
		panel.add(tf_Pasta, gbc_tf_Pasta);
		tf_Pasta.setColumns(10);
		
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
		gbc_btnConectar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConectar.insets = new Insets(0, 0, 0, 5);
		gbc_btnConectar.gridx = 2;
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
		gbc_btnDesconectar.gridx = 4;
		gbc_btnDesconectar.gridy = 2;
		panel.add(btnDesconectar, gbc_btnDesconectar);
		
		JButton btnIniciarServidor = new JButton("Iniciar Servidor");
		btnIniciarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IServer servico;
				try {
					servico = (IServer) UnicastRemoteObject.exportObject(servidor, 0);
					Registry registry = LocateRegistry.createRegistry(1818);

					registry.rebind(IServer.NOME_SERVICO, servico);
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				
			}
		});
		GridBagConstraints gbc_btnIniciarServidor = new GridBagConstraints();
		gbc_btnIniciarServidor.insets = new Insets(0, 0, 0, 5);
		gbc_btnIniciarServidor.gridx = 6;
		gbc_btnIniciarServidor.gridy = 2;
		panel.add(btnIniciarServidor, gbc_btnIniciarServidor);

		JLabel lblPesquisarPor = new JLabel("Pesquisar por:");
		GridBagConstraints gbc_lblPesquisarPor = new GridBagConstraints();
		gbc_lblPesquisarPor.insets = new Insets(0, 0, 5, 5);
		gbc_lblPesquisarPor.anchor = GridBagConstraints.EAST;
		gbc_lblPesquisarPor.gridx = 0;
		gbc_lblPesquisarPor.gridy = 2;
		contentPane.add(lblPesquisarPor, gbc_lblPesquisarPor);
				
		tf_Pesquisar = new JTextField();
		GridBagConstraints gbc_tf_Pesquisar = new GridBagConstraints();
		gbc_tf_Pesquisar.insets = new Insets(0, 0, 5, 5);
		gbc_tf_Pesquisar.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_Pesquisar.gridx = 1;
		gbc_tf_Pesquisar.gridy = 2;
		contentPane.add(tf_Pesquisar, gbc_tf_Pesquisar);
		tf_Pesquisar.setColumns(10);
		
		JComboBox cb_Filtro = new JComboBox();
		cb_Filtro.setModel(new DefaultComboBoxModel(TipoFiltro.values()));
		GridBagConstraints gbc_cb_Filtro = new GridBagConstraints();
		gbc_cb_Filtro.insets = new Insets(0, 0, 5, 5);
		gbc_cb_Filtro.fill = GridBagConstraints.HORIZONTAL;
		gbc_cb_Filtro.gridx = 2;
		gbc_cb_Filtro.gridy = 2;
		contentPane.add(cb_Filtro, gbc_cb_Filtro);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cb_Filtro.getSelectedItem();
				//servidor.procurarArquivo(tf_Pesquisar.getText(), TipoFiltro., cb_Filtro.getToolTipText());
			}
		});
		GridBagConstraints gbc_btnPesquisar = new GridBagConstraints();
		gbc_btnPesquisar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPesquisar.insets = new Insets(0, 0, 5, 5);
		gbc_btnPesquisar.gridx = 4;
		gbc_btnPesquisar.gridy = 2;
		contentPane.add(btnPesquisar, gbc_btnPesquisar);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf_Pesquisar.setText("");
			}
		});
		GridBagConstraints gbc_btnLimpar = new GridBagConstraints();
		gbc_btnLimpar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLimpar.insets = new Insets(0, 0, 5, 0);
		gbc_btnLimpar.gridx = 5;
		gbc_btnLimpar.gridy = 2;
		contentPane.add(btnLimpar, gbc_btnLimpar);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
			},
			new String[] {
				"Cliente", "Arquivo"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setRowHeaderView(table);
		
		JButton btnDownload = new JButton("Download");
		GridBagConstraints gbc_btnDownload = new GridBagConstraints();
		gbc_btnDownload.insets = new Insets(0, 0, 5, 0);
		gbc_btnDownload.gridx = 5;
		gbc_btnDownload.gridy = 4;
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
	public void TelaMostraArquivos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btnCarregar = new JButton("Carregar");
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				carregar();
			}
		});
		contentPane.add(btnCarregar, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					mostraSelecionadoTabela();
				}
			}
		});
	}

	protected void mostraSelecionadoTabela() {
		int linhaSelecionada = table.getSelectedRow();
		if (linhaSelecionada < 0) {
			System.out.println("Nenhuma selecionada");
		} else {
			int row = table.convertRowIndexToModel(linhaSelecionada);
			Arquivo arq = ((ResultadoModel)table.getModel()).getMeuItem(row);
			System.out.println(arq.getId());
			
		}
	}

	
	protected void carregar() {
		Map<Cliente, List<Arquivo>> dados = gerarDados();
		
		ResultadoModel modelo = new ResultadoModel(dados);
		
		table.setModel(modelo);
		
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
