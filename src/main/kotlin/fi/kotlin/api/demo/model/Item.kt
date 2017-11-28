package fi.kotlin.api.demo.model

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Table(name="ITEM")
data class Item (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.EAGER, cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "CATEGORY_ID")
    val categoryId: Category? = null,

    @get: NotEmpty
    @get: Column(name = "NAME")
    val name: String = "",

    @get: NotNull
    @get: Column(name = "COUNT")
    val count: Int = 0,

    @get: Column(name = "CREATED")
    val created: Date = Date(),

    @get: Column(name = "UPDATED")
    val modified: Date? = null
)