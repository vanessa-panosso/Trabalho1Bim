package br.univel.comum;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.univel.jshare.gui.Arquivo;
import br.univel.jshare.gui.Cliente;


public class ImplServidor  extends UnicastRemoteObject implements IServer{

	private int PORTA_TCPIP;

	protected ImplServidor() throws RemoteException {
		super();
	}

	@Override
	public void registrarCliente(Cliente c) throws RemoteException {
		try {
			Registry registry = LocateRegistry.createRegistry(PORTA_TCPIP);
			registry.rebind(IServer.NOME_SERVICO, this);

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
	public Map<Cliente, List<Arquivo>> procurarArquivo(String query, TipoFiltro tipoFiltro, String filtro)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] baixarArquivo(Cliente cli, Arquivo arq) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void desconectar(Cliente c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
