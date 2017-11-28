package fi.kotlin.api.demo.controller

import fi.kotlin.api.demo.model.Category
import fi.kotlin.api.demo.repository.CategoryRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class CategoryController(private val categoryRepository: CategoryRepository) {

    @GetMapping("/categories")
    fun getAllCategories(): List<Category> =
            categoryRepository.findAll()

    @PostMapping("/categories")
    fun createNewCategory(@Valid @RequestBody category: Category): ResponseEntity<Category> {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.save(category))
    }

    @DeleteMapping("/categories/deleteAll")
    fun deleteAllCategories(): Unit =
            categoryRepository.deleteAll();

    @GetMapping("/categories/{id}")
    fun getCategoryById(@PathVariable(value = "id") categoryId: Long): ResponseEntity<Category> {
        return categoryRepository.findById(categoryId).map { category ->
            ResponseEntity.ok(category)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/categories/{id}")
    fun updateCategoryById(@PathVariable(value = "id") categoryId: Long,
                          @Valid @RequestBody newCategory: Category): ResponseEntity<Category> {

        return categoryRepository.findById(categoryId).map { existingCategory ->
            val updatedCategory: Category = existingCategory
                    .copy(name = newCategory.name)
            ResponseEntity.ok().body(categoryRepository.save(updatedCategory))
        }.orElse(ResponseEntity.notFound().build())

    }

    @DeleteMapping("/categories/{id}")
    fun deleteCategoryById(@PathVariable(value = "id") categoryId: Long): ResponseEntity<Void> {

        return categoryRepository.findById(categoryId).map { category  ->
            categoryRepository.delete(category)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())

    }
}