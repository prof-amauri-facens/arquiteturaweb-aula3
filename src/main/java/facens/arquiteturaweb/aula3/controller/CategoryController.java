package facens.arquiteturaweb.aula3.controller;

import facens.arquiteturaweb.aula3.modelo.Categoria;
import facens.arquiteturaweb.aula3.modelo.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controller ´Privado
@RestController
@RequestMapping("/categorias")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Categoria> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoryById(@PathVariable Long id) {
        Categoria categoria = categoryRepository.findById(id).orElse(null);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public Categoria createCategory(@RequestBody Categoria categoria) {
        return categoryRepository.save(categoria);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeCategory(@PathVariable Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok("Categoria removida com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");
        }
    }
}
