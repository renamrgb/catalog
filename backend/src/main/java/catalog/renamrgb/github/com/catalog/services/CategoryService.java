package catalog.renamrgb.github.com.catalog.services;

import catalog.renamrgb.github.com.catalog.dto.CategoryDTO;
import catalog.renamrgb.github.com.catalog.entities.Category;
import catalog.renamrgb.github.com.catalog.repositories.CategoryRepository;
import catalog.renamrgb.github.com.catalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<CategoryDTO> findAll() {
        List<Category> listEntity = repository.findAll();
        return listEntity
                .stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        Category category = obj.orElseThrow(() -> new ResourceNotFoundException("Category não existe"));
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
}
