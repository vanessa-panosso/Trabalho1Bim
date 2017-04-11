package br.univel.servidor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.univel.cliente.Cliente;
import br.univel.comum.Arquivo;
import br.univel.comum.Md5Util;
import br.univel.comum.TipoFiltro;


public class ImplServidor  extends UnicastRemoteObject  implements IServer{

	private int PORTA_TCPIP;

	public ImplServidor() throws RemoteException {
		super();
	}

	@Override
	public void registrarCliente(Cliente c) throws RemoteException {
		IServer servico;
		try {
			servico = (IServer) UnicastRemoteObject.exportObject(this, 0);
			Registry registry = LocateRegistry.createRegistry(1818);
			registry.rebind(IServer.NOME_SERVICO, servico);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void publicarListaArquivos(Cliente c , List<Arquivo> lista) throws RemoteException {
		
	}

	@Override
	public Map<Cliente, List<Arquivo>> procurarArquivo(final String query, final TipoFiltro tipoFiltro, final String filtro) throws RemoteException {
        Map<Cliente, List<Arquivo>> resultadoMap = new HashMap<>();
        Map<Cliente, List<Arquivo>> padraoMap = new HashMap<>();

        padraoMap.forEach((k, value) -> {
            List<Arquivo> list = new ArrayList<>();

            value.forEach(v -> {
            	if(TipoFiltro.NOME.equals(tipoFiltro)){
                  if (v.getNome().toLowerCase().contains(query.toLowerCase())) {
                    list.add(v);
                  }
            	} else if (TipoFiltro.EXTENSAO.equals(tipoFiltro)){
                    if (v.getExtensao().toLowerCase().contains(filtro.toLowerCase())) {
                      if (v.getNome().toLowerCase().contains(query.toLowerCase())) {
                         list.add(v);
                      }
                    }
            	} else if (TipoFiltro.TAMANHO_MAX.equals(tipoFiltro)){
                    if (v.getTamanho() >= Integer.valueOf(filtro)) {
                      if (v.getNome().toLowerCase().contains(query.toLowerCase())) {
                         list.add(v);
                      }
                    }
            	}  
            	else if (TipoFiltro.TAMANHO_MAX.equals(tipoFiltro)){
                    if (v.getTamanho() <= Integer.valueOf(filtro)) {
                       if (v.getNome().toLowerCase().contains(query.toLowerCase())) {
                         list.add(v);
                       }
                    }
            	}   
            });
            resultadoMap.put(k, list);
        });
        return resultadoMap;	
	}

	@Override
	public byte[] baixarArquivo(Cliente cli, Arquivo arq) throws RemoteException {
		Path path = Paths.get(arq.getPath());
		try {
			byte[] dados = Files.readAllBytes(path);
			if (dados == null) {
				System.out.println("veio nulo");
			} else {

				String bytesBaixado = Md5Util.getMD5Checksum(arq.getPath());
				if (arq.getMd5().equals(bytesBaixado)) {
					
					//escreva(new File("cópia_de_" + arq.getNome()), dados);
				} else { 
					//escreva(new File("cópia_de_" + arq.getNome()), dados);
				}
			}
			return dados;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void desconectar(Cliente c) throws RemoteException {
		
	}

}
