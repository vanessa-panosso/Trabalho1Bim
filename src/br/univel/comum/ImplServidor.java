package br.univel.comum;

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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImplServidor  extends UnicastRemoteObject  implements IServer{

	private int PORTA_TCPIP;

	public ImplServidor() throws RemoteException {
		super();
	}

	@Override
	public void registrarCliente(Cliente c) throws RemoteException {
		Registry registry;

		IServer servico;
		try {
			registry = LocateRegistry.getRegistry(c.getIp(), PORTA_TCPIP);
			servico = (IServer) registry.lookup(NOME_SERVICO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publicarListaArquivos(Cliente c , List<Arquivo> lista) throws RemoteException {
		
	}

	@Override
	public Map<Cliente, List<Arquivo>> procurarArquivo(String query, TipoFiltro tipoFiltro, String filtro) throws RemoteException {
		List<String> resultado = new ArrayList<>();
			
		Pattern pat = Pattern.compile(".*" + query + ".*");
		if(tipoFiltro.NOME.equals(tipoFiltro)){
		for (String nome : Lista.DADOS) {
			Matcher m = pat.matcher(nome.toLowerCase());
			if (m.matches()) {
				resultado.add(nome);
			}
		}
		
		for (String res : resultado) {
			System.out.println(res);
		}
		
		}else if (tipoFiltro.EXTENSAO.equals(tipoFiltro)){
			for (String extensao : Lista.DADOS) {
				Matcher m = pat.matcher(extensao.toLowerCase());
				if (m.matches()) {
					resultado.add(extensao);
				}
			}
			
			for (String res : resultado) {
				System.out.println(res);
			}
		}else if (tipoFiltro.TAMANHO_MIN.equals(tipoFiltro)){
			for (String tamanho_min : Lista.DADOS) {
				Matcher m = pat.matcher(tamanho_min.toLowerCase());
				if (m.matches()) {
					resultado.add(tamanho_min);
				}
			}
			
			for (String res : resultado) {
				System.out.println(res);
			}
		}else if(tipoFiltro.TAMANHO_MAX.equals(tipoFiltro)){
			for (String tamanho_max : Lista.DADOS) {
				Matcher m = pat.matcher(tamanho_max.toLowerCase());
				if (m.matches()) {
					resultado.add(tamanho_max);
				}
			}
			
			for (String res : resultado) {
				System.out.println(res);
			}
		} 
		return null;

	}

	@Override
	public byte[] baixarArquivo(Cliente cli, Arquivo arq) throws RemoteException {
		Path path = Paths.get(arq.getPath());
		try {
			byte[] dados = Files.readAllBytes(path);
			if (dados == null) {
				System.out.println("veio nulo");
			} else {

				System.out.println("2-->>" + dados);

				String bytesBaixado = Md5Util.getMD5Checksum(arq.getPath());
				if (arq.getMd5().equals(bytesBaixado)) {
					
					escreva(new File("cópia_de_" + arq.getNome()), dados);
				} else { 
					escreva(new File("cópia_de_" + arq.getNome()), dados);
				}
			}
			return dados;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public void escreva(File arq, byte[] dados) {
		String path = "." + File.separatorChar + "shared" + File.separatorChar + arq.getName();
		System.out.println(path + arq.getName());
		try {
			Files.write(Paths.get(path), dados, StandardOpenOption.CREATE);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	@Override
	public void desconectar(Cliente c) throws RemoteException {
		
	}

}
