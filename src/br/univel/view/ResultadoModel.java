package br.univel.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.table.AbstractTableModel;

import br.univel.cliente.Cliente;
import br.univel.comum.Arquivo;


public class ResultadoModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private Object[][] matrix;

	public ResultadoModel(Map<Cliente, List<Arquivo>> dados) {

		int tempCli = 0;
		for (Entry<Cliente, List<Arquivo>> e : dados.entrySet()) {
			if (e.getValue() != null) {
				tempCli += e.getValue().size();
			}
		}

		matrix = new Object[tempCli][6];

		List<Cliente> list = new ArrayList<>(dados.keySet());

		list.sort((o1, o2) -> o1.getNome().compareTo(o2.getNome()));

		int cont = 0;
		for (Cliente cli : list) {
			for (Arquivo arq : dados.get(cli)) {
				matrix[cont][0] = cli.getNome();
				matrix[cont][1] = cli.getIp();
				matrix[cont][2] = arq.getNome();
				matrix[cont][3] = arq.getExtensao();
				matrix[cont][4] = arq;
				matrix[cont][5] = cli;
				cont++;
			}
		}
	}

	@Override
	public int getColumnCount() {
		return 4;
	}
	@Override
	public String getColumnName(int arg0) {
		switch(arg0){
			case 0:
				return "Nome";
			case 1:
				return "IP";
			case 2:
				return "Nome do Arquivo";
			case 3:
				return "Extensao";
			
		}
		return null;
	}
	@Override
	public int getRowCount() {
		return matrix.length;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return matrix[arg0][arg1];
	}

	public Arquivo getMeuItem(int row) {
		Arquivo arq = new Arquivo();
		arq.setId(row);
		return arq;
	}

}