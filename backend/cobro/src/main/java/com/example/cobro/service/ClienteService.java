package com.example.cobro.service;

import com.example.cobro.model.Cliente;
import com.example.cobro.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente crearCliente(Cliente cliente) {
        if (clienteRepository.existsByCedula(cliente.getCedula())) {
            throw new RuntimeException("Ya existe un cliente con la cédula: " + cliente.getCedula());
        }
        return clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> obtenerCliente(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> obtenerClientePorCedula(String cedula) {
        return clienteRepository.findByCedula(cedula);
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente actualizarCliente(Long id, Cliente datos) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

        if (!cliente.getCedula().equals(datos.getCedula()) &&
            clienteRepository.existsByCedula(datos.getCedula())) {
            throw new RuntimeException("Ya existe un cliente con la cédula: " + datos.getCedula());
        }

        cliente.setCedula(datos.getCedula());
        cliente.setNombre(datos.getNombre());
        cliente.setDireccion(datos.getDireccion());
        cliente.setTelefono(datos.getTelefono());

        return clienteRepository.save(cliente);
    }

    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}
