package br.univel.cliente;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.Spring;

import br.univel.servidor.IServer;

public class ClienteCon {
	public ClienteCon() throws RemoteException, NotBoundException {
			super();
		};
		public String conectar(Cliente cliente) {
			Registry registry;
			String retorno;
			try {
				registry = LocateRegistry.getRegistry(cliente.getIp(), cliente.getPorta());
				IServer servico = (IServer) registry.lookup(IServer.NOME_SERVICO);
				servico.registrarCliente(cliente);
				retorno = cliente.getNome() + "se registrou";
			} catch (RemoteException | NotBoundException e) {
				retorno = "Erro ao se conectar";
				e.printStackTrace();
			}
			return retorno;			
		};
			
}	
