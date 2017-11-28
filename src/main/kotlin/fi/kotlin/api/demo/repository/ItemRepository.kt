package fi.kotlin.api.demo.repository

import fi.kotlin.api.demo.model.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<Item, Long>

