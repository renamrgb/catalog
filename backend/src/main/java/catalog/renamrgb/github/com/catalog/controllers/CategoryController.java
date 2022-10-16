package catalog.renamrgb.github.com.catalog.controllers;

import catalog.renamrgb.github.com.catalog.entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Category>> findAll() {
        List<Category> list = new ArrayList<>();
        list.add(new Category(1L, "livros"));
        list.add(new Category(2L, "Eletronicos"));

        return ResponseEntity.ok(list);
    }
}
