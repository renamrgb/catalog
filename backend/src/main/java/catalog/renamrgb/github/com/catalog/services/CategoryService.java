package catalog.renamrgb.github.com.catalog.services;

import catalog.renamrgb.github.com.catalog.entities.Category;
import catalog.renamrgb.github.com.catalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private  CategoryRepository repository;

    public List<Category> findAll() {
        return repository.findAll();
    }
}