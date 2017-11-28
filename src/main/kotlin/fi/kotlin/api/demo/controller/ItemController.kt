package fi.kotlin.api.demo.controller

import fi.kotlin.api.demo.model.Item
import fi.kotlin.api.demo.repository.ItemRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ItemController(private val itemRepository: ItemRepository) {

    @GetMapping("/items")
    fun getAllItems(): List<Item> =
            itemRepository.findAll()


    @PostMapping("/items")
    fun createNewItem(@Valid @RequestBody item: Item): ResponseEntity<Item>? {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemRepository.save(item))
    }

    @DeleteMapping("/items/deleteAll")
    fun deleteAllCategories(): Unit =
            itemRepository.deleteAll();

    @GetMapping("/items/{id}")
    fun getItemById(@PathVariable(value = "id") itemId: Long): ResponseEntity<Item> {
        println("itemId: " + itemId);
        return itemRepository.findById(itemId).map { item ->
            ResponseEntity.ok(item)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/items/{id}")
    fun updateItemById(@PathVariable(value = "id") itemId: Long,
                          @Valid @RequestBody newItem: Item): ResponseEntity<Item> {

        return itemRepository.findById(itemId).map { existingItem ->
            val updatedItem: Item = existingItem
                    .copy(count = newItem.count, name = newItem.name, categoryId = newItem.categoryId)
            ResponseEntity.ok().body(itemRepository.save(updatedItem))
        }.orElse(ResponseEntity.notFound().build())

    }

    @DeleteMapping("/items/{id}")
    fun deleteItemById(@PathVariable(value = "id") itemId: Long): ResponseEntity<Void> {

        return itemRepository.findById(itemId).map { item  ->
            itemRepository.delete(item)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())

    }
}