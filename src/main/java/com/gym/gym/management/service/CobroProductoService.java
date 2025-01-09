package com.gym.gym.management.service;

import com.gym.gym.management.entity.CobroProducto;
import com.gym.gym.management.repository.CobroProductoRepository;
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

    public List<CobroProducto> getAllCobrosPagados() {
        return cobroProductoRepository.findAll().stream()
                .filter(cobroProducto -> "pagado".equals(cobroProducto.getEstado()))
                .toList();
    }

    public CobroProducto saveCobro(CobroProducto cobroProducto) {
        return cobroProductoRepository.save(cobroProducto);
    }


    public void deleteCobro(Long id) {
        cobroProductoRepository.deleteById(id);
    }

    public CobroProducto updateCobro(Long id, CobroProducto cobroProducto) {
        cobroProducto.setId(id);
        return cobroProductoRepository.save(cobroProducto);
    }
}
