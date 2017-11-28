package fi.kotlin.api.demo.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@Table(name="CATEGORY")
data class Category (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @get: NotEmpty
        @get: Column(name = "NAME")
        val name: String = "",

        @OneToMany(mappedBy = "categoryId")
        @JsonIgnore
        val items: Set<Item>? = null
)