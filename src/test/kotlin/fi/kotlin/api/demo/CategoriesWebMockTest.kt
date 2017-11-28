package fi.kotlin.api.demo

import com.fasterxml.jackson.databind.ObjectMapper
import fi.kotlin.api.demo.model.Category
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

import org.hamcrest.Matchers.containsString
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class CategoriesWebMockTest {

    @Autowired
    private val mockMvc: MockMvc? = null

    @Autowired
    private val objectMapper: ObjectMapper? = null

    private val categories_api = "/api/categories"

    private val category = Category(name = "Testing")

    @After
    @Throws(Exception::class)
    fun clear() {
        this.mockMvc!!.perform(delete(categories_api + "/deleteAll")).andExpect(status().isOk)
    }

    @Test
    @Throws(Exception::class)
    fun shouldAddCategory() {
        addCategory()
        getCategories("Testing")
    }

    @Test
    @Throws(Exception::class)
    fun shouldUpdateCategory() {
        val id = addCategory().replace("\\D+".toRegex(), "")
        val updated = category.copy(name = "Veggies")
        this.mockMvc!!.perform(put(categories_api + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper!!.writeValueAsString(updated)))
                .andDo(print())
                .andExpect(status().isOk)
        getCategories("Veggies")
    }

    @Test
    @Throws(Exception::class)
    fun shouldDeleteCategory() {
        val id = addCategory().replace("\\D+".toRegex(), "")
        this.mockMvc!!.perform(delete(categories_api + "/" + id))
                .andDo(print())
                .andExpect(status().isOk)
        getCategories("[]")
    }

    @Throws(Exception::class)
    private fun getCategories(expected: String) {
        this.mockMvc!!.perform(get(categories_api))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().string(containsString(expected)))
    }

    @Throws(Exception::class)
    private fun addCategory(): String {
        return this.mockMvc!!.perform(post(categories_api)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper!!.writeValueAsString(category)))
                .andDo(print())
                .andExpect(status().isCreated)
                .andReturn()
                .response
                .contentAsString
    }
}
