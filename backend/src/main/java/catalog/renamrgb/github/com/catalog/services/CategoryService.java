package catalog.renamrgb.github.com.catalog.services;

import catalog.renamrgb.github.com.catalog.dto.CategoryDTO;
import catalog.renamrgb.github.com.catalog.entities.Category;
import catalog.renamrgb.github.com.catalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
}
