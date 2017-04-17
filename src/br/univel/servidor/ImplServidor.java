package br.univel.servidor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.univel.cliente.Cliente;
import br.univel.comum.Arquivo;
import br.univel.comum.Md5Util;
import br.univel.comum.TipoFiltro;


public class ImplServidor  extends UnicastRemoteObject  implements IServer, Runnable{

	private int PORTA_TCPIP;
    private Map<Cliente, List<Arquivo>> mapArquivo = new HashMap<>();
    private List<Cliente> listaCliente = new ArrayList<>();
    private Arquivo arq;
	public ImplServidor() throws RemoteException {
		super();
		new Thread(this).start();
	}
	public void iniciarServidor(){
		try {
			Registry registry = LocateRegistry.createRegistry(1818);
			registry.rebind(IServer.NOME_SERVICO, this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void registrarCliente(Cliente c) throws RemoteException {
		listaCliente.add(c);

	}

	@Override
	public void publicarListaArquivos(Cliente c , List<Arquivo> lista) throws RemoteException {
		mapArquivo.put(c, lista);
	}

	@Override
	public Map<Cliente, List<Arquivo>> procurarArquivo(final String query, final TipoFiltro tipoFiltro, final String filtro) throws RemoteException {
        Map<Cliente, List<Arquivo>> mapResultado = new HashMap<>();
        
        Pattern pat = Pattern.compile("." + query + ".");
        
        List<Arquivo> listaArquivo = new ArrayList<>();
        
        mapArquivo.forEach((cliente, arquivo) -> {
        	
          arquivo.forEach(valor -> {
        	Matcher matcher = pat.matcher(valor.getNome());
            if(matcher.matches()){	
            	if (TipoFiltro.EXTENSAO.equals(tipoFiltro)){
                    if (valor.getExtensao().toLowerCase().contains(filtro.toLowerCase())) {
                         listaArquivo.add(valor);
                    } else if (filtro.isEmpty()){
                    	listaArquivo.add(valor);
                    }
            	} else if (TipoFiltro.TAMANHO_MAX.equals(tipoFiltro)){
                    if (valor.getTamanho() >= Integer.valueOf(filtro)) {
                         listaArquivo.add(valor);
                    }else if (filtro.isEmpty()){
                    	listaArquivo.add(valor);
                    }
            	}  
            	else if (TipoFiltro.TAMANHO_MAX.equals(tipoFiltro)){
                    if (valor.getTamanho() <= Integer.valueOf(filtro)) {
                         listaArquivo.add(valor);
                    } else if(filtro.isEmpty()){
                    	listaArquivo.add(valor);
                    }
            	} 
            }	
          });
          mapResultado.put(cliente, listaArquivo);
        });
        return mapResultado;	
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

        if (mapArquivo.containsKey(c)) {
            mapArquivo.remove(c);

        } else {
            
        }
	}
	@Override
	public void run() {
		
	}

}
