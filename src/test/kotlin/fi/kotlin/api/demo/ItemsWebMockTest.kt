package fi.kotlin.api.demo

import com.fasterxml.jackson.databind.ObjectMapper
import fi.kotlin.api.demo.model.Category
import fi.kotlin.api.demo.model.Item
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

import java.util.Date

import org.hamcrest.Matchers.containsString
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ItemsWebMockTest {

    @Autowired
    private val mockMvc: MockMvc? = null

    @Autowired
    private val objectMapper: ObjectMapper? = null

    private val category = Category(name = "Testing")

    private val item = Item(
            categoryId =  category,
            name = "Item",
            count =  123)

    private val items_api = "/api/items"

    @After
    @Throws(Exception::class)
    fun clear() {
        this.mockMvc!!.perform(delete(items_api + "/deleteAll")).andExpect(status().isOk)
    }

    @Test
    @Throws(Exception::class)
    fun shouldAddItem() {
        addItem()
        getItems("Testing")
    }

    @Test
    @Throws(Exception::class)
    fun shouldUpdateItem() {
        val json = addItem()
        val id = objectMapper!!.readValue(json, Item::class.java).id
        val updated = item.copy(name = "Item2")
        this.mockMvc!!.perform(put(items_api + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper!!.writeValueAsString(updated)))
                .andDo(print())
                .andExpect(status().isOk)
        getItems("Item2")
    }

    @Test
    @Throws(Exception::class)
    fun shouldDeleteItem() {
        val json = addItem()
        val id = objectMapper!!.readValue(json, Item::class.java).id
        this.mockMvc!!.perform(delete(items_api + "/" + id))
                .andDo(print())
                .andExpect(status().isOk)
        getItems("[]")
    }

    @Throws(Exception::class)
    private fun getItems(expected: String) {
        this.mockMvc!!.perform(get(items_api))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().string(containsString(expected)))
    }

    @Throws(Exception::class)
    private fun addItem(): String {
        return this.mockMvc!!.perform(post(items_api)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper!!.writeValueAsString(item)))
                .andDo(print())
                .andExpect(status().isCreated)
                .andReturn()
                .response
                .contentAsString
    }
}
