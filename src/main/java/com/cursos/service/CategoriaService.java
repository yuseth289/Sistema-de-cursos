package com.cursos.service;

import com.cursos.dto.categoria.CategoriaRequestDTO;
import com.cursos.dto.categoria.CategoriaResponseDTO;
import com.cursos.entity.Categoria;
import com.cursos.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaResponseDTO crear(CategoriaRequestDTO dto) {

        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());

        Categoria guardada = categoriaRepository.save(categoria);

        return convertirAResponse(guardada);
    }

    public List<CategoriaResponseDTO> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La categoría no existe"));

        return convertirAResponse(categoria);
    }

    public CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO dto) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La categoría no existe"));

        categoria.setNombre(dto.getNombre());

        Categoria actualizada = categoriaRepository.save(categoria);

        return convertirAResponse(actualizada);
    }

    public void eliminar(Long id) {

        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("La categoría no existe");
        }

        categoriaRepository.deleteById(id);
    }

    private CategoriaResponseDTO convertirAResponse(Categoria categoria) {

        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());

        return dto;
    }
}