package fi.kotlin.api.demo

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import fi.kotlin.api.demo.model.Category
import fi.kotlin.api.demo.model.Item
import org.junit.Test

import java.util.Date

import org.hamcrest.Matchers.containsString
import org.junit.Assert.assertThat

class MappingTest {
    private val category = Category(1L, "Testing", null)

    @Test
    @Throws(JsonProcessingException::class)
    fun testCategoryMapping() {
        val result = ObjectMapper()
                .writerWithView(Category::class.java)
                .writeValueAsString(category)

        assertThat(result, containsString("\"id\":1,\"name\":\"Testing\""))
    }

    @Test
    @Throws(JsonProcessingException::class)
    fun testItemMapping() {
        val item = Item(1L, category, "Testing", 123, Date(), null)
        val result = ObjectMapper()
                .writerWithView(Item::class.java)
                .writeValueAsString(item)

        assertThat(result, containsString("\"id\":1,\"name\":\"Testing\""))
    }
}