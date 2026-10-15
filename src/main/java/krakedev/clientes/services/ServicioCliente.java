package krakedev.clientes.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import krakedev.clientes.entidades.Cliente;

@Service
public class ServicioCliente {
	
	private ArrayList<Cliente> clientes=new ArrayList<Cliente>();
	
	//métdo buscar cliente
	
	public Cliente buscarPorCedula(String cedula) {
		//for each, me retorna la cedula
		for(Cliente buscaCedula: clientes) {
			if(buscaCedula.getCedula().equals(cedula)) {
				return buscaCedula;
			}
		}
		return null;
	}
	
	//Método agregar cliente
	
	public Cliente crear(Cliente cliente) {
		//buscar cliente usando el metodo:
		Cliente existe=buscarPorCedula(cliente.getCedula());
		if(existe !=null) {
			return null;
		}else {
			clientes.add(cliente);
			
			return cliente;
		}
	}
	
	//Método listar clientes
	
	public List<Cliente> listar(){
		return clientes;
	}
	
	//Método actualizar
	public Cliente actualizar(String cedula, Cliente clienteActualizado) {
		Cliente cliente = buscarPorCedula(cedula);
		
		if(cliente != null) {
			cliente.setNombre(clienteActualizado.getNombre());
			cliente.setApellido(clienteActualizado.getApellido());
			cliente.setEmail(clienteActualizado.getEmail());
			
		}
		
		return cliente;
	}
	
	public boolean eliminar(String cedula) {
		Cliente cliente = buscarPorCedula(cedula);
		if(cliente != null) {
			clientes.remove(cliente);
			return true;
		}else {
			return false;
		}
	}
}
