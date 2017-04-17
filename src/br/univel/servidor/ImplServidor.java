package br.univel.servidor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.univel.cliente.Cliente;
import br.univel.comum.Arquivo;
import br.univel.comum.Md5Util;
import br.univel.comum.TipoFiltro;
import br.univel.view.TelaPrincipal;


public class ImplServidor  extends UnicastRemoteObject  implements IServer{

    private Map<Cliente, List<Arquivo>> mapArquivo = new HashMap<>();
    private List<Cliente> listaCliente = new ArrayList<>();
	public ImplServidor() throws RemoteException {
		super();
	}
	public  void publicarMinhaLista(final String dir) throws RemoteException {
		File diretorio = new File(dir);
		List<Arquivo> listaArquivo = new ArrayList<>();
		if (!diretorio.exists()) {
			diretorio.mkdir();
		}
		File arquivos[] = diretorio.listFiles();

		listaArquivo.clear();

		for (int i = 0; i < arquivos.length; i++) {
			File file = arquivos[i];
			Arquivo arquivo = new Arquivo();
			arquivo.setNome(file.getName().substring(0, file.getName().lastIndexOf(".")));
			arquivo.setExtensao(file.getName().substring((file.getName().lastIndexOf(".") + 1)));
			arquivo.setPath(file.getPath());
			arquivo.setDataHoraModificacao(new Date());
			arquivo.setTamanho(file.length());
			arquivo.setMd5(Md5Util.getMD5Checksum(file.getAbsolutePath()));
			arquivo.setId(i + 1);
			
			TelaPrincipal.getListaArquivos().add(arquivo);
		}
		
		publicarListaArquivos(TelaPrincipal.getMeuCliente(), TelaPrincipal.getListaArquivos());
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
        
        
        
        mapArquivo.entrySet().forEach(cliente -> {
        	List<Arquivo> listaArquivo = new ArrayList<>();
          cliente.getValue().forEach(valor -> {
        	
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
            }else if(query.isEmpty()){
            	listaArquivo.add(valor);
            }	
          });
          mapResultado.put(cliente.getKey(), listaArquivo);
        });
        return mapResultado;	
	}

	@Override
	public byte[] baixarArquivo(Cliente cli, Arquivo arq) throws RemoteException {
		byte[] dados;
        Path path = Paths.get(arq.getPath());

        try {
            dados = Files.readAllBytes(path);
            return dados;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}
	
	@Override
	public void desconectar(Cliente c) throws RemoteException {
		
        if (mapArquivo.containsKey(c)) {
            mapArquivo.remove(c);

        } 
        if (listaCliente.contains(c)) {
			listaCliente.remove(c);
		}
	}
	
}
