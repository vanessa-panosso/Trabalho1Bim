package br.univel.comum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImplServidor  extends UnicastRemoteObject implements IServer{

	private int PORTA_TCPIP;

	public ImplServidor() throws RemoteException {
		super();
	}

	@Override
	public void registrarCliente(Cliente c) throws RemoteException {
		ImplServidor servidor = new ImplServidor();
		
		IServer servico;
		try {
			servico = (IServer) UnicastRemoteObject
					.exportObject(servidor, 0);
			Registry registry = LocateRegistry
					.createRegistry(1818);
			registry
				.rebind(servico.NOME_SERVICO, servico);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publicarListaArquivos(Cliente c, List<Arquivo> lista) throws RemoteException {
		
		int tempCli = 0;
		for (Entry<Cliente, List<Arquivo>> e : lista.entrySet()) {
			if (e.getValue() != null) {
				tempCli += e.getValue().size();
			}
		}

		Object[][] matrix = new Object[tempCli][4];
		
		List<Cliente> list = new ArrayList<>(lista.keySet());
		
		list.sort((o1, o2) -> o1.getNome().compareTo(o2.getNome()));
		
		int cont = 0;
		for (Cliente cli : list) {
			for (Arquivo arq : lista.get(cli)) {
				matrix[cont][0] = cli.getId();
				matrix[cont][1] = cli.getNome();
				matrix[cont][2] = arq.getId();
				matrix[cont][3] = arq.getNome();
				cont++;
			}
		}
	}

	@Override
	public Map<Cliente, List<Arquivo>> procurarArquivo(String query, TipoFiltro tipoFiltro, String filtro) throws RemoteException {
		List<String> resultado = new ArrayList<>();
			
		Pattern pat = Pattern.compile(".*" + query + ".*");
		
		for (String nome : Lista.DADOS) {
			Matcher m = pat.matcher(nome.toLowerCase());
			if (m.matches()) {
				resultado.add(nome);
			}
		}
		
		for (String res : resultado) {
			System.out.println(res);
		}
		return null;
	}

	@Override
	public byte[] baixarArquivo(Cliente cli, Arquivo arq) throws RemoteException {
		Path path = Paths.get(arq.getPath());
		try {
			byte[] dados = Files.readAllBytes(path);
			return dados;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void desconectar(Cliente c) throws RemoteException {
		
	}

}
