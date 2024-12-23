package com.gym.gym.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CobroProductoService {
    @Autowired
    private CobroProductoRepository cobroProductoRepository;

    public List<CobroProducto> getAllCobros() {
        return cobroProductoRepository.findAll();
    }

    public CobroProducto saveCobro(CobroProducto cobroProducto) {
        if (cobroProducto.getSocioId() != null) {
            // Si hay un socioId, se guarda normalmente
            return cobroProductoRepository.save(cobroProducto);
        } else {
            // Si no hay socioId, asignamos un nombre de comprador manual
            cobroProducto.setNombreComprador(cobroProducto.getNombreComprador()); 
            return cobroProductoRepository.save(cobroProducto);
        }
    }


    public void deleteCobro(Long id) {
        cobroProductoRepository.deleteById(id);
    }

    public CobroProducto updateCobro(Long id, CobroProducto cobroProducto) {
        cobroProducto.setId(id);
        return cobroProductoRepository.save(cobroProducto);
    }
}
