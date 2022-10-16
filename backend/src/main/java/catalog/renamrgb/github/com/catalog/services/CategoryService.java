package catalog.renamrgb.github.com.catalog.services;

import catalog.renamrgb.github.com.catalog.dto.CategoryDTO;
import catalog.renamrgb.github.com.catalog.entities.Category;
import catalog.renamrgb.github.com.catalog.repositories.CategoryRepository;
import catalog.renamrgb.github.com.catalog.services.exceptions.DatabaseException;
import catalog.renamrgb.github.com.catalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        Page<Category> listEntity = repository.findAll(pageable);
        return listEntity
                .map(CategoryDTO::new);
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        Category category = obj.orElseThrow(() -> new ResourceNotFoundException("Category com id " + id + " não existe"));
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category category = copyDtoToEntity(dto);
        category = repository.save(category);
        return new CategoryDTO(category);
    }

    private Category copyDtoToEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());

        return category;
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category category = repository.getOne(id);
            category.setName(dto.getName());
            category = repository.save(category);
            return new CategoryDTO(category);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Category com id " + id + " não existe");
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Category com id " + id + " não existe");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integridade do banco de dados violada");
        }
    }
}
