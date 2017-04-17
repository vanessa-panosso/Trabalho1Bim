package br.univel.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import br.univel.cliente.Cliente;
import br.univel.cliente.ClienteCon;
import br.univel.comum.Arquivo;
import br.univel.comum.Md5Util;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.Insets;
import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import br.univel.comum.TipoFiltro;
import br.univel.servidor.IServer;
import br.univel.servidor.ImplServidor;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTextField tf_Nome;
	private JTextField tf_Ip;
	private JTextField tf_Porta;
	private JTextField tf_Pesquisar;
	private JTextField tf_Pasta;
	private JTable table;
	private ImplServidor servidor = new ImplServidor();
	private Cliente c = new Cliente();
	private Arquivo arq = new Arquivo();
	private static  List<Arquivo> listaArquivos = new ArrayList<Arquivo>();
	private static  Cliente meuCliente;
	private static String meuIp;

	private Map<Cliente, List<Arquivo>> mapaArquivos = new HashMap<Cliente, List<Arquivo>>();
	private static JTextArea log;

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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 14, 0, 0, 0, 97, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
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
		tf_Nome.setText("Vanessa");
		ButtonGroup bg = new ButtonGroup();

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

		InetAddress IP;
		try {
			IP = InetAddress.getLocalHost();
			meuIp = IP.getHostAddress();
		} catch (UnknownHostException e2) {
			e2.printStackTrace();
		}
		tf_Ip.setText(meuIp);
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
		tf_Porta.setText("1818");
		
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
		tf_Pasta.setText("C:\\jshare\\upload\\");
		
		
		meuCliente = new Cliente();
		meuCliente.setIp(tf_Ip.getText());
		meuCliente.setNome(tf_Nome.getText());
		meuCliente.setPorta(Integer.parseInt(tf_Porta.getText()));
		JButton btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Registry registry;
				try {
						registry = LocateRegistry.getRegistry(tf_Ip.getText(), Integer.parseInt(tf_Porta.getText()));
						IServer servico = (IServer) registry.lookup(IServer.NOME_SERVICO);
						servidor.meusArquivos(tf_Pasta.getText());
						servico.registrarCliente(meuCliente);
				} catch (RemoteException | NotBoundException e1) {
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
					servidor.desconectar(c);
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
					((ImplServidor) servidor).iniciarServidor();
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
				ResultadoModel modelo;
						try {
							mapaArquivos = servidor.procurarArquivo(tf_Pesquisar.getText(),(TipoFiltro) cb_Filtro.getSelectedItem(), tf_Pesquisar.getText());
							modelo = new ResultadoModel(mapaArquivos);
							table.removeAll();

							table.setModel(modelo);
							table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
				
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
		ResultadoModel modelo = new ResultadoModel(mapaArquivos);
		table = new JTable();
		table.setModel(modelo);
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					mostraSelecionadoTabela();
				}
			}
		});		
		JButton btnDownload = new JButton("Download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					Integer linha = table.getSelectedRow();
					Cliente cliente = (Cliente) table.getModel().getValueAt(linha, 5);
					Arquivo arquivo = (Arquivo) table.getModel().getValueAt(linha, 4);

					Registry registry;
					try {
						registry = LocateRegistry.getRegistry(cliente.getIp(), cliente.getPorta());
						IServer server = (IServer) registry.lookup(IServer.NOME_SERVICO);

						ler(arquivo, server.baixarArquivo(cliente, arquivo));
						servidor.meusArquivos(tf_Pasta.getText());

					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (NotBoundException e) {
						e.printStackTrace();
					}
				
			}
		});
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
		
		log = new JTextArea();
		log.setForeground(new Color(0, 204, 51));
		log.setBackground(new Color(0, 0, 0));
		scrollPane_1.setColumnHeaderView(log);
	}
	

	protected void mostraSelecionadoTabela() {
		int linhaSelecionada = table.getSelectedRow();
		if (linhaSelecionada < 0) {
		} else {
			int row = table.convertRowIndexToModel(linhaSelecionada);
			Arquivo arq = ((ResultadoModel)table.getModel()).getMeuItem(row);
		}
	}


	public void ler(Arquivo arq, byte[] dados) {
		try {
			File arquivo = new File(tf_Pasta.getText() + " " + arq.getNome() + "teste" + "." + arq.getExtensao());
			Files.write(Paths.get(arquivo.getPath()), dados, StandardOpenOption.CREATE);

			String md5Novo = Md5Util.getMD5Checksum(arquivo.getAbsolutePath());
			if (!md5Novo.equals(arq.getMd5())) {
				JOptionPane.showMessageDialog(null, String.format(
						"Arquivo corrompido!", arq.getNome()));
			} else {
				JOptionPane.showMessageDialog(null,
						String.format("O arquivo foi baixado com sucesso!", arq.getNome()));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public synchronized static List<Arquivo> getListaArquivos() {
		return listaArquivos;
	};
	public static JTextArea getTextArea(){
		return log;
	};
	public void setTextArea(JTextArea log){
		this.log = log;
	}
	
	public static Cliente getMeuCliente() {
		return meuCliente;
	}
	public synchronized  void setListaArquivos(List<Arquivo> listaArquivos) {
		listaArquivos = listaArquivos;
	}
}